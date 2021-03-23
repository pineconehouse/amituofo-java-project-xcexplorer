package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.menu;

import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;
import com.amituofo.xcexplorer.core.ui.compoent.menu.StandardFileItemSelectionMenuBuilder;

public class HCPFileItemSelectionMenuBuilder extends StandardFileItemSelectionMenuBuilder {
	private HCPItemSelectionMenuBuilder s3menu = new HCPItemSelectionMenuBuilder();

	@Override
	protected void prepareMenuItems() {
		super.addExtendFootMenuItem(null);
		super.addExtendFootMenuItem(s3menu.PURGE);
		super.addExtendFootMenuItem(s3menu.CUSTOM_METADATA);
		super.addExtendFootMenuItem(s3menu.SYSTEM_METADATA);

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
