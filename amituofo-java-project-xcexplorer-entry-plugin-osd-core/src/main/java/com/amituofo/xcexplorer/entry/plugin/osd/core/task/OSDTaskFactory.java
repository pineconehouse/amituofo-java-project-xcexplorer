package com.amituofo.xcexplorer.entry.plugin.osd.core.task;

import com.amituofo.task.TaskDetail;
import com.amituofo.task.TaskParameter;
import com.amituofo.xcexplorer.core.global.SystemConfig;
import com.amituofo.xcexplorer.core.global.SystemConfigKeys;
import com.amituofo.xcexplorer.core.lang.ItemPackage;
import com.amituofo.xcexplorer.entry.plugin.osd.core.task.impl.ObjectStorageObjectVersionDeletingTask;
import com.amituofo.xcexplorer.entry.plugin.osd.core.task.impl.ObjectStorageObjectVersionRestoreTask;
import com.amituofo.xcexplorer.task.DefaultTaskFactory;
import com.amituofo.xcexplorer.task.core.StandardAnyItemHandleTask;
import com.amituofo.xcexplorer.task.util.TaskUtils;

public class OSDTaskFactory extends DefaultTaskFactory {

	public TaskDetail createOSBVersionDeletingTask(ItemPackage sourceContentPackage) {
		String name = TaskUtils.generateTaskName("Deleting version of", sourceContentPackage, null);
		String catalog = ObjectStorageObjectVersionDeletingTask.TASK_CATEGORY;
		TaskDetail td = new TaskDetail(name, ObjectStorageObjectVersionDeletingTask.class);
		TaskParameter parameter = new TaskParameter();
		parameter.set(StandardAnyItemHandleTask.PARAM_WORKER_COUNT, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_DELETE_TASK_THREADS));
		parameter.set(StandardAnyItemHandleTask.PARAM_RETRY_TIMES, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_RETRY_TIMES));
		parameter.set(StandardAnyItemHandleTask.PARAM_SOURCE_CONTENT_PACKAGE, sourceContentPackage);
		td.setParameter(parameter);
		td.setCatalog(catalog);
		td.setGroupId(GROUP_1);

		return td;
	}

	public TaskDetail createOSBVersionRestoringTask(ItemPackage sourceContentPackage) {
		String name = TaskUtils.generateTaskName("Restoring version of", sourceContentPackage, null);
		String catalog = ObjectStorageObjectVersionRestoreTask.TASK_CATEGORY;
		TaskDetail td = new TaskDetail(name, ObjectStorageObjectVersionRestoreTask.class);
		TaskParameter parameter = new TaskParameter();
		parameter.set(StandardAnyItemHandleTask.PARAM_WORKER_COUNT, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_DELETE_TASK_THREADS));
		parameter.set(StandardAnyItemHandleTask.PARAM_RETRY_TIMES, 1);
		parameter.set(StandardAnyItemHandleTask.PARAM_SOURCE_CONTENT_PACKAGE, sourceContentPackage);
		td.setParameter(parameter);
		td.setCatalog(catalog);
		td.setGroupId(GROUP_1);

		return td;
	}

}
