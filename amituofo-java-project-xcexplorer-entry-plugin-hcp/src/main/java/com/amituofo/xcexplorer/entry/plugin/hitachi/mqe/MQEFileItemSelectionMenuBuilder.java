package com.amituofo.xcexplorer.entry.plugin.hitachi.mqe;

import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;
import com.amituofo.xcexplorer.core.ui.compoent.menu.LogicItemMenuBuilder;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.menu.HCPItemSelectionMenuBuilder;

public class MQEFileItemSelectionMenuBuilder extends LogicItemMenuBuilder {
	private HCPItemSelectionMenuBuilder s3menu = new HCPItemSelectionMenuBuilder();

	@Override
	protected void prepareMenuItems() {
		super.addExtendFootMenuItem(s3menu.PURGE);
		super.addExtendFootMenuItem(s3menu.CUSTOM_METADATA);
		super.addExtendFootMenuItem(s3menu.SYSTEM_METADATA);
		super.addExtendFootMenuItem(null);

		super.prepareMenuItems();
	}

	public ContentExplorerContainer getExplorerContainer() {
		return s3menu.getExplorerContainer();
	}

	public void setExplorerContainer(ContentExplorerContainer container) {
		s3menu.setExplorerContainer(container);
		super.setExplorerContainer(container);
	}
}
