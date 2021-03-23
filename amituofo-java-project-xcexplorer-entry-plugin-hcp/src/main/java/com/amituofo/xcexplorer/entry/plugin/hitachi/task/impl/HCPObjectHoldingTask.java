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

public class HCPObjectHoldingTask extends StandardFileItemHandleTask {
	public final static String TASK_CATEGORY = "HCP Holding Task";

	public final static String PARAM_IS_HOLD = HCPObjectSystemMetadataUpdatingTask.PARAM_IS_HOLD;

	private Boolean isHold = null;
	
	private final class PrivateProcesser extends BaseItemProcesser<FileItem> {
		public PrivateProcesser(ItemGroup<FileItem> itemGroup, ProcesserPreference preference) {
			super(itemGroup, preference);
		}

		@Override
		public void execute() throws ServiceException {
//			HandleResult result = new SingleItemHandleResult();
			try {
				((HCPFileItem) item).setSystemMetadata(isHold, null, null, null, null);
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

	public HCPObjectHoldingTask() {
		super(ProgressUnit.FILE_COUNT, PerformanceUnit.FILE_COUNT, "Holded", "Holding");
	}

	@Override
	public boolean initialize(TaskParameter parameter) throws TaskException {
		if (super.initialize(parameter)) {
			isHold = parameter.getBoolean(PARAM_IS_HOLD);
			return true;
		}

		return false;
	}
	
	@Override
	protected ItemProcesser createItemProcesser(ItemGroup<FileItem> itemGroup) throws ServiceException {
		return new PrivateProcesser(itemGroup, this);
	}

}
