package com.amituofo.xcexplorer.entry.plugin.hitachi.task;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.task.TaskDetail;
import com.amituofo.task.TaskParameter;
import com.amituofo.xcexplorer.core.global.SystemConfig;
import com.amituofo.xcexplorer.core.global.SystemConfigKeys;
import com.amituofo.xcexplorer.core.lang.ItemMigrationOperation;
import com.amituofo.xcexplorer.core.lang.ItemPackage;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.impl.HCPObjectCustomMetadataUpdatingTask;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.impl.HCPObjectHoldingTask;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.impl.HCPObjectPurgingTask;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.impl.HCPObjectS3CompatibleMetadataUpdatingTask;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.impl.HCPObjectSystemMetadataUpdatingTask;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.impl.MQEObjectCopyingTask;
import com.amituofo.xcexplorer.entry.plugin.osd.core.task.OSDTaskFactory;
import com.amituofo.xcexplorer.task.core.StandardAnyItemHandleTask;
import com.amituofo.xcexplorer.task.core.StandardItemMigrationTask;
import com.amituofo.xcexplorer.task.util.MapperType;
import com.amituofo.xcexplorer.task.util.TaskUtils;
import com.amituofo.xcexplorer.util.ItemUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.item.MQEQueryRequest;
import com.hitachivantara.hcp.standard.model.metadata.S3CompatibleMetadata;

public class HCPTaskFactory extends OSDTaskFactory {

	public TaskDetail createPurgingTask(ItemPackage sourceContentPackage) {
		String name = TaskUtils.generateTaskName("Purging", sourceContentPackage, null);
		String catalog = HCPObjectPurgingTask.TASK_CATEGORY;
		TaskDetail td = new TaskDetail(name, HCPObjectPurgingTask.class);
		TaskParameter parameter = new TaskParameter();
		parameter.set(StandardAnyItemHandleTask.PARAM_WORKER_COUNT, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_DELETE_TASK_THREADS));
		parameter.set(StandardAnyItemHandleTask.PARAM_RETRY_TIMES, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_RETRY_TIMES));
		parameter.set(StandardAnyItemHandleTask.PARAM_SOURCE_CONTENT_PACKAGE, sourceContentPackage);
		td.setParameter(parameter);
		td.setCatalog(catalog);
		td.setGroupId(GROUP_1);

		return td;
	}
	
	public TaskDetail createPrivilegedPurgingTask(ItemPackage sourceContentPackage, String reason) {
		String name = TaskUtils.generateTaskName("Privileged Purging", sourceContentPackage, null);
		String catalog = HCPObjectPurgingTask.TASK_CATEGORY;
		TaskDetail td = new TaskDetail(name, HCPObjectPurgingTask.class);
		TaskParameter parameter = new TaskParameter();
		parameter.set(StandardAnyItemHandleTask.PARAM_WORKER_COUNT, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_DELETE_TASK_THREADS));
		parameter.set(StandardAnyItemHandleTask.PARAM_RETRY_TIMES, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_RETRY_TIMES));
		parameter.set(StandardAnyItemHandleTask.PARAM_SOURCE_CONTENT_PACKAGE, sourceContentPackage);
		parameter.set(HCPObjectPurgingTask.PARAM_PRIVILEGED_PURGE, Boolean.TRUE);
		parameter.set(HCPObjectPurgingTask.PARAM_PRIVILEGED_PURGE_REASON, reason);
		td.setParameter(parameter);
		td.setCatalog(catalog);
		td.setGroupId(GROUP_1);

		return td;
	}


	public TaskDetail createHCPObjectHoldingTask(ItemPackage sourceContentPackage) {
		String name = TaskUtils.generateTaskName("Holding", sourceContentPackage, null);
		String catalog = HCPObjectHoldingTask.TASK_CATEGORY;
		TaskDetail td = new TaskDetail(name, HCPObjectHoldingTask.class);
		TaskParameter parameter = new TaskParameter();
		parameter.set(StandardAnyItemHandleTask.PARAM_WORKER_COUNT, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_HANDLE_TASK_THREADS));
		parameter.set(StandardAnyItemHandleTask.PARAM_RETRY_TIMES, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_RETRY_TIMES));
		parameter.set(StandardAnyItemHandleTask.PARAM_SOURCE_CONTENT_PACKAGE, sourceContentPackage);
		td.setParameter(parameter);
		td.setCatalog(catalog);
		td.setGroupId(GROUP_1);

		return td;
	}
	
	public TaskDetail createHCPObjectSetMetadataTask(ItemPackage sourceContentPackage, String metadataname, byte[] metadata) {
		String name = TaskUtils.generateTaskName("Update custome metadata", sourceContentPackage, null);
		String catalog = HCPObjectCustomMetadataUpdatingTask.TASK_CATEGORY;
		TaskDetail td = new TaskDetail(name, HCPObjectCustomMetadataUpdatingTask.class);
		TaskParameter parameter = new TaskParameter();
		parameter.set(HCPObjectCustomMetadataUpdatingTask.PARAM_WORKER_COUNT, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_HANDLE_TASK_THREADS));
		parameter.set(HCPObjectCustomMetadataUpdatingTask.PARAM_RETRY_TIMES, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_RETRY_TIMES));
		parameter.set(HCPObjectCustomMetadataUpdatingTask.PARAM_SOURCE_CONTENT_PACKAGE, sourceContentPackage);
		parameter.set(HCPObjectCustomMetadataUpdatingTask.PARAM_METADATA_NAME, metadataname);
		parameter.set(HCPObjectCustomMetadataUpdatingTask.PARAM_METADATA_BYTE_CONTENT, metadata);
		td.setParameter(parameter);
		td.setCatalog(catalog);
		td.setGroupId(GROUP_1);

		return td;
	}

	public TaskDetail createHCPObjectSetMetadataTask(ItemPackage sourceContentPackage, S3CompatibleMetadata s3CompatibleMetadata) {
		String name = TaskUtils.generateTaskName("Update S3 Compatible metadata", sourceContentPackage, null);
		String catalog = HCPObjectCustomMetadataUpdatingTask.TASK_CATEGORY;
		TaskDetail td = new TaskDetail(name, HCPObjectS3CompatibleMetadataUpdatingTask.class);
		TaskParameter parameter = new TaskParameter();
		parameter.set(HCPObjectS3CompatibleMetadataUpdatingTask.PARAM_WORKER_COUNT, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_HANDLE_TASK_THREADS));
		parameter.set(HCPObjectS3CompatibleMetadataUpdatingTask.PARAM_RETRY_TIMES, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_RETRY_TIMES));
		parameter.set(HCPObjectS3CompatibleMetadataUpdatingTask.PARAM_SOURCE_CONTENT_PACKAGE, sourceContentPackage);
		parameter.set(HCPObjectS3CompatibleMetadataUpdatingTask.PARAM_OBJECT_METADATA, s3CompatibleMetadata);
		td.setParameter(parameter);
		td.setCatalog(catalog);
		td.setGroupId(GROUP_1);

		return td;
	}
	
	public TaskDetail createHCPObjectSystemMetadataUpdateTask(ItemPackage sourceContentPackage, Boolean hold, Boolean index, Boolean shred, String owner, String retention) {
		String name = TaskUtils.generateTaskName("Update system metadata", sourceContentPackage, null);
		String catalog = HCPObjectSystemMetadataUpdatingTask.TASK_CATEGORY;
		TaskDetail td = new TaskDetail(name, HCPObjectSystemMetadataUpdatingTask.class);
		TaskParameter parameter = new TaskParameter();
		parameter.set(HCPObjectSystemMetadataUpdatingTask.PARAM_WORKER_COUNT, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_HANDLE_TASK_THREADS));
		parameter.set(HCPObjectSystemMetadataUpdatingTask.PARAM_RETRY_TIMES, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_RETRY_TIMES));
		parameter.set(HCPObjectSystemMetadataUpdatingTask.PARAM_SOURCE_CONTENT_PACKAGE, sourceContentPackage);
		parameter.set(HCPObjectSystemMetadataUpdatingTask.PARAM_IS_HOLD, hold);
		parameter.set(HCPObjectSystemMetadataUpdatingTask.PARAM_IS_INDEX, index);
		parameter.set(HCPObjectSystemMetadataUpdatingTask.PARAM_IS_SHRED, shred);
		parameter.set(HCPObjectSystemMetadataUpdatingTask.PARAM_OWNER, owner);
		parameter.set(HCPObjectSystemMetadataUpdatingTask.PARAM_RETENTION, retention);
		td.setParameter(parameter);
		td.setCatalog(catalog);
		td.setGroupId(GROUP_1);

		return td;
	}

	public TaskDetail createMQEObjectCopyingTask(MQEQueryRequest request, ItemPackage targetContentPackage, ItemMigrationOperation operation) throws ServiceException {
		ItemPackage sourceContentPackage = ItemUtils.toItemPackage(request.getHomeFolder(), request.getHomeFolder());
		String name = TaskUtils.generateTaskName("Copying", sourceContentPackage, targetContentPackage);
		String catalog = MQEObjectCopyingTask.TASK_CATEGORY;
		TaskDetail td = new TaskDetail(name, MQEObjectCopyingTask.class);
		TaskParameter parameter = new TaskParameter();
		parameter.set(MQEObjectCopyingTask.PARAM_WORKER_COUNT, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_COPY_TASK_THREADS));
		parameter.set(MQEObjectCopyingTask.PARAM_RETRY_TIMES, SystemConfig.getInstance().getInt(SystemConfigKeys.TASK_MAX_RETRY_TIMES));
		parameter.set(StandardItemMigrationTask.PARAM_MIGRATION_OPERATION, operation);
		parameter.set(StandardItemMigrationTask.PARAM_SOURCE_CONTENT_PACKAGE, sourceContentPackage);
		parameter.set(StandardItemMigrationTask.PARAM_TARGET_CONTENT_PACKAGE, targetContentPackage);
		parameter.set(StandardItemMigrationTask.PARAM_MIGRATION_MAPPER, TaskUtils.createItemMapper(sourceContentPackage, targetContentPackage, MapperType.DEFAULT));
		
		parameter.set(MQEObjectCopyingTask.PARAM_SOURCE_MQE_QUERY_REQUEST, request);
		td.setParameter(parameter);
		td.setCatalog(catalog);
		td.setGroupId(GROUP_1);

		return td;
	}
	
}
