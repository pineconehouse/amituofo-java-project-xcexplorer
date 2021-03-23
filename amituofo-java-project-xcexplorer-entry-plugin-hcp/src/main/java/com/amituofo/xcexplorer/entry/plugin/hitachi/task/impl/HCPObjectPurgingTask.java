package com.amituofo.xcexplorer.entry.plugin.hitachi.task.impl;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.task.TaskParameter;
import com.amituofo.task.ex.TaskException;
import com.amituofo.xcexplorer.task.core.processer.BaseItemProcesser;
import com.amituofo.xcexplorer.task.core.processer.ItemProcesser;
import com.amituofo.xcexplorer.task.core.processer.ProcesserPreference;
import com.amituofo.xcexplorer.task.impl.StandardItemDeletingTask;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPFileItem;
import com.amituofo.xfs.service.FileItem;
import com.amituofo.xfs.util.ItemGroup;

public class HCPObjectPurgingTask extends StandardItemDeletingTask {
	public final static String TASK_CATEGORY = "HCP Purge Deleting Task";

	public final static String PARAM_PRIVILEGED_PURGE = "PARAM_PRIVILEGED_PURGE";
	public final static String PARAM_PRIVILEGED_PURGE_REASON = "PARAM_PRIVILEGED_PURGE_REASON";

	private boolean privilegedPurge = false;
	private String privilegedPurgeReason = "";

	private final class PrivateProcesser extends BaseItemProcesser<FileItem> {
		public PrivateProcesser(ItemGroup<FileItem> itemGroup, ProcesserPreference preference) {
			super(itemGroup, preference);
		}

		@Override
		public void execute() throws ServiceException {
//			HandleResult result = new SingleItemHandleResult();
			boolean deleted;

			if (privilegedPurge) {
				deleted = ((HCPFileItem) item).privilegedPurge(privilegedPurgeReason);
			} else {
				deleted = ((HCPFileItem) item).purge();
			}

			if (deleted) {
//				result.addSucceededItem(item);
				PROGRESS_LISTENER.succeedHandledItem(item);
			} else {
				logger.error("Failed to purge " + item.getPath());
//				result.addFailedItem(item);
				PROGRESS_LISTENER.failHandledItem(item, null);
			}

//			return result;
		}

		@Override
		protected boolean tryStop() throws InterruptedException {
			return true;
		}
	};

	public HCPObjectPurgingTask() {
	}

	@Override
	public boolean initialize(TaskParameter parameter) throws TaskException {
		if (super.initialize(parameter)) {
			privilegedPurge = parameter.getBoolean(PARAM_PRIVILEGED_PURGE, false);
			privilegedPurgeReason = parameter.getString(PARAM_PRIVILEGED_PURGE_REASON);

			return true;
		}

		return false;
	}

	@Override
	protected ItemProcesser createItemProcesser(ItemGroup<FileItem> itemGroup) throws ServiceException {
		return new PrivateProcesser(itemGroup, this);
	}

}
