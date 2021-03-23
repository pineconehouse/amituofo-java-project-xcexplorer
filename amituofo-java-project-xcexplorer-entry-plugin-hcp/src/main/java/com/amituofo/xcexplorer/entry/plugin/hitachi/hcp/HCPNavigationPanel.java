package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.ItemExplorer;
import com.amituofo.xcexplorer.core.ui.frame.ItemTreeNavigationPanel;
import com.amituofo.xcexplorer.core.ui.frame.NavigationFunctionPanel;
import com.amituofo.xcexplorer.core.ui.model.ItemModel;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model.HCPClassicFileSystemModel;
import com.amituofo.xcexplorer.entry.plugin.osd.core.action.OSDItemspaceAction;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDNavToolbarPanel;

public class HCPNavigationPanel extends ItemTreeNavigationPanel {

	private HCPNamespaceStatisticsPanel namespaceStatisticsPanel;

	public HCPNavigationPanel(ItemExplorer ie, WorkingSpace workingSpace) {
		super(ie, workingSpace);
	}

	@Override
	protected NavigationFunctionPanel createFooterNavFunctionPanel() {
		namespaceStatisticsPanel = new HCPNamespaceStatisticsPanel();
		return namespaceStatisticsPanel;
	}

	protected ItemModel createItemModel(WorkingSpace workingSpace) {
		return new HCPClassicFileSystemModel(workingSpace, workingSpace.getHomeFolder());
	}

	@Override
	protected NavigationFunctionPanel createHeaderNavFunctionPanel() {
		return new OSDNavToolbarPanel(this.getItemExplorer(), new OSDItemspaceAction("namespace"));
	}

}
