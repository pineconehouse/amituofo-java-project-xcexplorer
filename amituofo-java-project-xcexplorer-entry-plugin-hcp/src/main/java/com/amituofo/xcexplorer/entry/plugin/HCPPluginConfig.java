package com.amituofo.xcexplorer.entry.plugin;

import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.plugin.PluginConfig;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.HCPClassicExplorerPanel;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.HCPEntryConfigPanel;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.HCPTaskFactory;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.HCPFileSystemEntryConfig;

public class HCPPluginConfig extends PluginConfig {

	public HCPPluginConfig() {
		super("HCPPlugin",
				HCPFileSystemEntryConfig.SYSTEM_NAME,
				"2.3.1",
				GlobalIcons.ICON_FS_HCP_16x16,
				HCPFileSystemEntryConfig.SYSTEM_ID,
//				HCPFileSystemEntryConfig.class,
				HCPEntryConfigPanel.class,
//				RemoteItemExplorerPanel.class,
				HCPClassicExplorerPanel.class,
				HCPTaskFactory.class,
				"");
	}

}
