package com.amituofo.xcexplorer.entry.plugin.hitachi.task.impl;

import java.util.List;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.task.TaskParameter;
import com.amituofo.task.ex.TaskException;
import com.amituofo.xcexplorer.task.core.processer.ItemProcesser;
import com.amituofo.xcexplorer.task.core.processer.ProcesserMigrationPreference;
import com.amituofo.xcexplorer.task.core.processer.StandardItemCoupleMigrationProcesser;
import com.amituofo.xcexplorer.task.impl.ObjectStorage2ObjectStorageCopyingTask;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileItem;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.MQEHandleableItemCoupleList;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.item.MQEFileItem;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.item.MQEQueryRequest;
import com.amituofo.xfs.service.FolderItem;
import com.amituofo.xfs.service.HandleableItemCouple;
import com.amituofo.xfs.service.Item;
import com.amituofo.xfs.service.ItemList;
import com.amituofo.xfs.util.ItemGroup;
import com.amituofo.xfs.util.ItemStatistic;

public class MQEObjectCopyingTask extends ObjectStorage2ObjectStorageCopyingTask {

	public final static String TASK_CATEGORY = "MQE Object Copying Task";
	public static final String PARAM_SOURCE_MQE_QUERY_REQUEST = "PARAM_SOURCE_MQE_QUERY_REQUEST";
	private MQEQueryRequest request;

	public MQEObjectCopyingTask() {
	}

	private final class PrivateProcesser extends StandardItemCoupleMigrationProcesser {

		public PrivateProcesser(ItemGroup<HandleableItemCouple> itemGroup, ProcesserMigrationPreference preference) {
			super(itemGroup, preference);
		}

		protected void actionOnFile(Item sourceItem, Item targetItem) throws ServiceException {
			MQEFileItem osdSource = (MQEFileItem) sourceItem;
			OSDFileItem osdTarget = (OSDFileItem) targetItem;

			if (osdSource.getStatus() != Item.ITEM_STATUS_DELETED) {
				osdTarget.copy(osdSource);
				PROGRESS_LISTENER.succeedHandledItem(sourceItem);
			} else {
				PROGRESS_LISTENER.ignoreHandledItem(sourceItem);
			}
		}
	};

	@Override
	public boolean initialize(TaskParameter parameter) throws TaskException {
		if (super.initialize(parameter)) {
			request = (MQEQueryRequest) parameter.getObject(PARAM_SOURCE_MQE_QUERY_REQUEST);

			if (request == null) {
				return false;
			}

			return true;
		}

		return false;
	}

	@Override
	protected ItemList<HandleableItemCouple> createDefaultItemList() {
		return new MQEHandleableItemCoupleList(request);
	}

	@Override
	protected ItemProcesser createItemProcesser(ItemGroup<HandleableItemCouple> itemGroup) throws ServiceException {
		return new PrivateProcesser(itemGroup, this);
	}

//	@Override
//	protected boolean beforeHandleItems(ItemList<HandleableItemCouple> sourceFileItemList, ItemList<FolderItem> sourceFolderItemList) throws TaskException {
//		// TODO Auto-generated method stub
//		return super.beforeHandleItems(sourceFileItemList, sourceFolderItemList);
//	}

	@Override
	protected boolean collectSourceInformation(ItemList<HandleableItemCouple> tobeHandleFileItemList,
			List<FolderItem> sourceFolderItemList,
			ItemStatistic statisticsTotalRemains) throws TaskException {
		// return super.collectSourceInformation(tobeHandleFileItemList, sourceFolderItemList, statisticsTotalRemains);
		try {
			int totalcount = request.queryCount();
			statisticsTotalRemains.sumFileCount(totalcount);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskException(e);
		}

		runtimeMessageListener.updateRuntimeMessage(" Found " + statisticsTotalRemains.toStringFileCount());

		return true;
	}

}
