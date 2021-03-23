package com.amituofo.xcexplorer.entry.plugin.hitachi.task.impl;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.task.TaskParameter;
import com.amituofo.task.ex.TaskException;
import com.amituofo.xcexplorer.task.core.StandardFileItemHandleTask;
import com.amituofo.xcexplorer.task.core.processer.BaseItemProcesser;
import com.amituofo.xcexplorer.task.core.processer.ItemProcesser;
import com.amituofo.xcexplorer.task.core.processer.ProcesserPreference;
import com.amituofo.xcexplorer.task.core.processer.ProgressUnit;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPFileItem;
import com.amituofo.xfs.service.FileItem;
import com.amituofo.xfs.util.ItemGroup;
import com.amituofo.xfs.util.PerformanceCalculator.PerformanceUnit;
import com.hitachivantara.hcp.standard.model.metadata.S3CompatibleMetadata;

public class HCPObjectS3CompatibleMetadataUpdatingTask extends StandardFileItemHandleTask {
	public final static String TASK_CATEGORY = "HCP S3 Compatible Metadata Update Task";

	public final static String PARAM_OBJECT_METADATA = "PARAM_OBJECT_METADATA";

	private S3CompatibleMetadata objectMetadata = null;

	private final class PrivateProcesser extends BaseItemProcesser<FileItem> {
		public PrivateProcesser(ItemGroup<FileItem> itemGroup, ProcesserPreference preference) {
			super(itemGroup, preference);
		}

		@Override
		public void execute() throws ServiceException {
//			HandleResult result = new SingleItemHandleResult();
			try {
				((HCPFileItem) item).putMetadata(objectMetadata);
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

	public HCPObjectS3CompatibleMetadataUpdatingTask() {
		super(ProgressUnit.FILE_COUNT, PerformanceUnit.FILE_COUNT, "Updated", "Updating");
	}

	@Override
	public boolean initialize(TaskParameter parameter) throws TaskException {
		if (super.initialize(parameter)) {
			objectMetadata = (S3CompatibleMetadata)parameter.getObject(PARAM_OBJECT_METADATA);
		} else {
			return false;
		}
		
		return true;
	}

	@Override
	protected ItemProcesser createItemProcesser(ItemGroup<FileItem> itemGroup) throws ServiceException {
		return new PrivateProcesser(itemGroup, this);
	}

}
