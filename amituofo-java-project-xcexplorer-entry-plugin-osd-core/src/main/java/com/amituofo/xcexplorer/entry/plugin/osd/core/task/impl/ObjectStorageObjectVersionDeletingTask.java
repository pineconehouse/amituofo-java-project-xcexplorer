package com.amituofo.xcexplorer.entry.plugin.osd.core.task.impl;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.xcexplorer.task.core.processer.BaseItemProcesser;
import com.amituofo.xcexplorer.task.core.processer.ItemProcesser;
import com.amituofo.xcexplorer.task.core.processer.ProcesserPreference;
import com.amituofo.xcexplorer.task.impl.StandardItemDeletingTask;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDVersionFileItem;
import com.amituofo.xfs.service.FileItem;
import com.amituofo.xfs.util.ItemGroup;

public class ObjectStorageObjectVersionDeletingTask extends StandardItemDeletingTask {
	public final static String TASK_CATEGORY = "Object Version Deleting Task";

	public ObjectStorageObjectVersionDeletingTask() {
	}

	private final class PrivateProcesser extends BaseItemProcesser<FileItem> {
		public PrivateProcesser(ItemGroup<FileItem> itemGroup, ProcesserPreference preference) {
			super(itemGroup, preference);
		}

		@Override
		public void execute() throws ServiceException {
//			HandleResult result = new SingleItemHandleResult();
			boolean deleted = item.delete();

			if (deleted) {
//				result.addSucceededItem(item);
				PROGRESS_LISTENER.succeedHandledItem(item);
			} else {
				logger.error("Unable to delete version (" + ((OSDVersionFileItem) item).getVersionId() + ") " + item.getPath());
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

	@Override
	protected ItemProcesser createItemProcesser(ItemGroup<FileItem> itemGroup) throws ServiceException {
		return new PrivateProcesser(itemGroup, this);
	}

}
