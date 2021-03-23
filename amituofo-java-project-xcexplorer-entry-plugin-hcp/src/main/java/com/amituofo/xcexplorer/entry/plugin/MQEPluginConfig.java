package com.amituofo.xcexplorer.entry.plugin;

import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.plugin.PluginConfig;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.HCPClassicExplorerPanel;
import com.amituofo.xcexplorer.entry.plugin.hitachi.mqe.MQEEntryConfigsPanel;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.MQEFileSystemEntryConfig;

public class MQEPluginConfig extends PluginConfig {

	public MQEPluginConfig() {
		super("MQECSPlugin",
				MQEFileSystemEntryConfig.SYSTEM_NAME,
				"1.2.1",
				GlobalIcons.ICON_FS_HCP_MQE_16x16,
				MQEFileSystemEntryConfig.SYSTEM_ID,
//				MQEFileSystemEntryConfig.class,
				MQEEntryConfigsPanel.class,
//				RemoteItemExplorerPanel.class,
				HCPClassicExplorerPanel.class,
				null,
				"");
	}

}
