package com.amituofo.xcexplorer.entry.plugin.hitachi.task.impl;

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
import com.amituofo.xfs.service.FileItem;
import com.amituofo.xfs.util.ItemGroup;
import com.amituofo.xfs.util.PerformanceCalculator.PerformanceUnit;

public class HCPObjectSystemMetadataUpdatingTask extends StandardFileItemHandleTask {
	public final static String TASK_CATEGORY = "HCP System Metadata Update Task";

	public final static String PARAM_IS_HOLD = "PARAM_IS_HOLD";
	public final static String PARAM_IS_INDEX = "PARAM_IS_INDEX";
	public final static String PARAM_IS_SHRED = "PARAM_IS_SHRED";
	public final static String PARAM_OWNER = "PARAM_OWNER";
	public final static String PARAM_RETENTION = "PARAM_RETENTION";

	private Boolean isHold = null;
	private Boolean isIndex = null;
	private Boolean isShred = null;
	private String owner = null;
	private String retention = null;
	
	private final class PrivateProcesser extends BaseItemProcesser<FileItem> {
		public PrivateProcesser(ItemGroup<FileItem> itemGroup, ProcesserPreference preference) {
			super(itemGroup, preference);
		}

		@Override
		public void execute() throws ServiceException {
//			HandleResult result = new SingleItemHandleResult();
			try {
				((HCPFileItem) item).setSystemMetadata(isHold, isIndex, isShred, owner, retention);
//				result.addSucceededItem(item);
				PROGRESS_LISTENER.succeedHandledItem(item);
			} catch (Exception e) {
				logger.error("Failed to update system metadata. " + item.getPath(), e);
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

	public HCPObjectSystemMetadataUpdatingTask() {
		super(ProgressUnit.FILE_COUNT, PerformanceUnit.FILE_COUNT, "Updated", "Updating");
	}

	@Override
	public boolean initialize(TaskParameter parameter) throws TaskException {
		if (super.initialize(parameter)) {
			isHold = parameter.getBoolean(PARAM_IS_HOLD);
			isIndex = parameter.getBoolean(PARAM_IS_INDEX);
			isShred = parameter.getBoolean(PARAM_IS_SHRED);
			owner = parameter.getString(PARAM_OWNER);
			retention = parameter.getString(PARAM_RETENTION);

			if (StringUtils.isEmpty(owner)) {
				owner = null;
			}
			if (StringUtils.isEmpty(retention)) {
				retention = null;
			}
			return true;
		}

		return false;
	}
	
	@Override
	protected ItemProcesser createItemProcesser(ItemGroup<FileItem> itemGroup) throws ServiceException {
		return new PrivateProcesser(itemGroup, this);
	}

}
