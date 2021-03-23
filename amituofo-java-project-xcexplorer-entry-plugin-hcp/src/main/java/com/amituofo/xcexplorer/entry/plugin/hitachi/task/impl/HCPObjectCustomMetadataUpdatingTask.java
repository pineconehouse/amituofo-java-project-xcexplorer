package com.amituofo.xcexplorer.entry.plugin.hitachi.task.impl;

import java.io.ByteArrayInputStream;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.common.util.StringUtils;
import com.amituofo.task.TaskParameter;
import com.amituofo.task.ex.TaskException;
import com.amituofo.xcexplorer.task.core.StandardFileItemHandleTask;
import com.amituofo.xcexplorer.task.core.processer.BaseItemProcesser;
import com.amituofo.xcexplorer.task.core.processer.ItemProcesser;
import com.amituofo.xcexplorer.task.core.processer.ProcesserPreference;
import com.amituofo.xcexplorer.task.core.processer.ProgressUnit;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPFileItem;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPMetadataItem;
import com.amituofo.xfs.service.FileItem;
import com.amituofo.xfs.util.ItemGroup;
import com.amituofo.xfs.util.PerformanceCalculator.PerformanceUnit;

public class HCPObjectCustomMetadataUpdatingTask extends StandardFileItemHandleTask {
	public final static String TASK_CATEGORY = "HCP Custom Metadata Update Task";

	public final static String PARAM_METADATA_NAME = "PARAM_METADATA_NAME";
	public final static String PARAM_METADATA_BYTE_CONTENT = "PARAM_METADATA_BYTE_CONTENT";

	private String metadataName = null;
	private byte[] metadataContent = null;

	private final class PrivateProcesser extends BaseItemProcesser<FileItem> {
		public PrivateProcesser(ItemGroup<FileItem> itemGroup, ProcesserPreference preference) {
			super(itemGroup, preference);
		}

		@Override
		public void execute() throws ServiceException {
//			HandleResult result = new SingleItemHandleResult();
			try {
				HCPMetadataItem metaitem = ((HCPFileItem) item).createMetadata(metadataName);
				metaitem.getContentWriter().write(new ByteArrayInputStream(metadataContent), -1);
//				result.addSucceededItem(item);
				PROGRESS_LISTENER.succeedHandledItem(item);
			} catch (Exception e) {
				logger.error("Failed to update custom metadata. " + item.getPath(), e);
//				result.addFailedItem(item);
				PROGRESS_LISTENER.failHandledItem(item, e);
			}
//			return result;
		}

		@Override
		protected boolean tryStop() throws InterruptedException {
			return true;
		}
	};

	public HCPObjectCustomMetadataUpdatingTask() {
		super(ProgressUnit.FILE_COUNT, PerformanceUnit.FILE_COUNT, "Updated", "Updating");
	}

	@Override
	public boolean initialize(TaskParameter parameter) throws TaskException {
		if (super.initialize(parameter)) {
			metadataName = parameter.getString(PARAM_METADATA_NAME);
			metadataContent = parameter.getBytes(PARAM_METADATA_BYTE_CONTENT);

			if (StringUtils.isEmpty(metadataName) || metadataContent == null || metadataContent.length == 0) {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	@Override
	protected ItemProcesser createItemProcesser(ItemGroup<FileItem> itemgroup) throws ServiceException {
		return new PrivateProcesser(itemgroup, this);
	}

}
