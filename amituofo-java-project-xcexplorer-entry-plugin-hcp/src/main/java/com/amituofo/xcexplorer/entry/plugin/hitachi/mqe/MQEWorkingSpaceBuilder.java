package com.amituofo.xcexplorer.entry.plugin.hitachi.mqe;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.xcexplorer.core.global.SystemConfigKeys;
import com.amituofo.xcexplorer.core.space.ClassicWorkingSpaceBuilder;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xfs.config.EntryConfig;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.HCPFileSystemEntryConfig;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.MQEFileSystemEntryConfig;

public class MQEWorkingSpaceBuilder extends ClassicWorkingSpaceBuilder {

	public MQEWorkingSpaceBuilder() {
	}

	@Override
	public WorkingSpace createWorkingSpace(EntryConfig entryConfig) throws ServiceException {
		MQEFileSystemEntryConfig mqeConfig = null;
		if (entryConfig instanceof HCPFileSystemEntryConfig) {
			mqeConfig = new MQEFileSystemEntryConfig((HCPFileSystemEntryConfig)entryConfig);
		} else if (entryConfig instanceof MQEFileSystemEntryConfig) {
			mqeConfig = (MQEFileSystemEntryConfig) entryConfig;
		}

		WorkingSpace queryFSSession;
		queryFSSession = super.createWorkingSpace(mqeConfig);
		queryFSSession.setTrue(SystemConfigKeys._DISABLE_RESET_ITEMTAB_TITLE_NAME_TO_WORKING_FOLDER_NAME_);
		queryFSSession.setTrue(SystemConfigKeys._HIDDEN_CONTENT_PANEL_ROOT_SELECTOR_);
		queryFSSession.setValue(SystemConfigKeys._HIDDEN_ITEM_VIEWER_PANEL_CLASS_, MQEQueryResutViewerPanel.class);
		queryFSSession.setValue(SystemConfigKeys._HIDDEN_PAGE_SIZE_, mqeConfig.getPageSize());
		// 禁止使用cache
		queryFSSession.setItemListCacheEnabled(false);
		return queryFSSession;

	}

}
