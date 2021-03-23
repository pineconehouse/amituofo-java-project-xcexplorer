package com.amituofo.xcexplorer.entry.plugin.osd.core.task;

import com.amituofo.common.ui.action.RefreshAction;
import com.amituofo.task.TaskManagement;
import com.amituofo.xcexplorer.core.global.GlobalResource;
import com.amituofo.xcexplorer.core.lang.ItemPackage;

public class TaskUtils extends com.amituofo.xcexplorer.task.util.TaskUtils{

	public static void execVersionRestoreTask(ItemPackage contentPackage, final RefreshAction refresh) {
		if (contentPackage.hasItem()) {
			final TaskManagement taskMgr = GlobalResource.getTaskmanagement();
			taskMgr.add(GlobalResource.getTaskFactory(OSDTaskFactory.class).createOSBVersionRestoringTask(contentPackage), TaskUtils.newListenerForRefreshAfterTaskDone(refresh));
		}
	}

//	public static void execBatchDeleteTask(ItemPackage contentPackage, final RefreshAction refresh) {
//		if (contentPackage.hasItem()) {
//			final TaskManagement taskMgr = GlobalResource.getTaskmanagement();
//			taskMgr.add(GlobalResource.getTaskFactory(OSDTaskFactory.class).createS3BatchDeletingTask(contentPackage), TaskUtils.newListenerForRefreshAfterTaskDone(refresh));
//		}
//	}

	public static void execOBSVersionDeleteTask(ItemPackage contentPackage, final RefreshAction refresh) {
		if (contentPackage.hasItem()) {
			final TaskManagement taskMgr = GlobalResource.getTaskmanagement();
			taskMgr.add(GlobalResource.getTaskFactory(OSDTaskFactory.class).createOSBVersionDeletingTask(contentPackage), TaskUtils.newListenerForRefreshAfterTaskDone(refresh));
		}
	}


}
