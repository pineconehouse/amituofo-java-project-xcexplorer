package com.amituofo.xcexplorer.entry.plugin.osd.core.task.impl;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.xcexplorer.task.core.processer.BaseItemProcesser;
import com.amituofo.xcexplorer.task.core.processer.ItemProcesser;
import com.amituofo.xcexplorer.task.core.processer.ProcesserPreference;
import com.amituofo.xcexplorer.task.impl.StandardItemDeletingTask;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileItem;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDVersionFileItem;
import com.amituofo.xfs.service.FileItem;
import com.amituofo.xfs.service.ItemHiddenFunction;
import com.amituofo.xfs.util.ItemGroup;

public class ObjectStorageObjectVersionRestoreTask extends StandardItemDeletingTask {
	public final static String TASK_CATEGORY = "Object Version Restoring Task";

	public ObjectStorageObjectVersionRestoreTask() {
	}

	private final class PrivateProcesser extends BaseItemProcesser<FileItem> {
		public PrivateProcesser(ItemGroup<FileItem> itemGroup, ProcesserPreference preference) {
			super(itemGroup, preference);
		}

		@Override
		public void execute() throws ServiceException {
			OSDVersionFileItem osdSource = (OSDVersionFileItem) item;

			OSDFileItem osdTargetTemp = (OSDFileItem) item.clone();
			osdTargetTemp.setKey(item.getName() + "-" + osdSource.getVersionId() + ".tmp");
			((ItemHiddenFunction) osdTargetTemp).setName(osdTargetTemp.getKey());
			osdTargetTemp.copy(osdSource);

			OSDFileItem osdTarget = (OSDFileItem) item.clone();
			osdTarget.copy(osdTargetTemp);

			boolean deleted = osdTargetTemp.delete();

			if (!deleted) {
				logger.error("Failed to delete temp restored file " + osdTargetTemp.getPath());
				PROGRESS_LISTENER.failHandledItem(item, null);
			} else {
				PROGRESS_LISTENER.succeedHandledItem(item);
			}
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
