package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.frame.StandardItemExplorerPanel;
import com.amituofo.xcexplorer.core.ui.model.ItemModel;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.menu.HCPFileItemSelectionMenuBuilder;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.menu.HCPFolderItemSelectionMenuBuilder;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.menu.HCPItemSelectionMenuBuilder;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model.HCPClassicFileSystemModel;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDItemViewerPanel;

public class HCPItemViewerPanel extends OSDItemViewerPanel {

	public HCPItemViewerPanel(StandardItemExplorerPanel parentContentPanel) throws ServiceException {
		super(parentContentPanel);
	}

	protected ItemModel createItemModel(WorkingSpace workingSpace) {
		return new HCPClassicFileSystemModel(workingSpace, workingSpace.getHomeFolder());
	}

}
