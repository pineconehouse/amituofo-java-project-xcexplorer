package com.amituofo.xcexplorer.entry.plugin.hitachi.task;

import com.amituofo.common.ui.action.RefreshAction;
import com.amituofo.task.TaskManagement;
import com.amituofo.xcexplorer.core.global.GlobalResource;
import com.amituofo.xcexplorer.core.lang.ItemPackage;

public class HCPTaskUtils extends com.amituofo.xcexplorer.entry.plugin.osd.core.task.TaskUtils{

	public static void execPurgeTask(ItemPackage contentPackage, final RefreshAction refresh) {
		final TaskManagement taskMgr = GlobalResource.getTaskmanagement();
		taskMgr.add(GlobalResource.getTaskFactory(HCPTaskFactory.class).createPurgingTask(contentPackage), com.amituofo.xcexplorer.task.util.TaskUtils.newListenerForRefreshAfterTaskDone(refresh));
	}
	
	public static void execPrivilegedPurgeTask(ItemPackage contentPackage, final RefreshAction refresh, String reason) {
		final TaskManagement taskMgr = GlobalResource.getTaskmanagement();
		taskMgr.add(GlobalResource.getTaskFactory(HCPTaskFactory.class).createPrivilegedPurgingTask(contentPackage, reason), com.amituofo.xcexplorer.task.util.TaskUtils.newListenerForRefreshAfterTaskDone(refresh));
	}


}
