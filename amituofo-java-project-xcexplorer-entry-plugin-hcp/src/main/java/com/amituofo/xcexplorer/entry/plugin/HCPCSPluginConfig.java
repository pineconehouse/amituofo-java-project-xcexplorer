package com.amituofo.xcexplorer.entry.plugin;

import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.plugin.PluginConfig;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcpcs.HCPCSEntryConfigsPanel;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.HCPTaskFactory;
import com.amituofo.xcexplorer.entry.plugin.s3.compatible.CompatibleS3ClassicExplorerPanel;
import com.amituofo.xfs.plugin.fs.objectstorage.s3compatible.CompatibleS3FileSystemEntryConfig;

public class HCPCSPluginConfig extends PluginConfig {

	public HCPCSPluginConfig() {
		super("HCP-CSPlugin",
				"Hitachi Content Platform for Cloud Scale (HCP-CS)",
				"1.0.1",
				GlobalIcons.ICON_FS_COMPATIBLE_S3_16x16,
				CompatibleS3FileSystemEntryConfig.SYSTEM_ID,
//				CompatibleS3FileSystemEntryConfig.class,
				HCPCSEntryConfigsPanel.class,
//				RemoteItemExplorerPanel.class,
				CompatibleS3ClassicExplorerPanel.class,
				HCPTaskFactory.class,
				"");
	}

}
