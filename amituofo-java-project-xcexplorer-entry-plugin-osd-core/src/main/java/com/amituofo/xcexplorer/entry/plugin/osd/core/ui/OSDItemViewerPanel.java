package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.xcexplorer.core.ui.compoent.content.DefaultFolderItemViewerPanel;
import com.amituofo.xcexplorer.core.ui.frame.StandardItemExplorerPanel;

public abstract class OSDItemViewerPanel extends DefaultFolderItemViewerPanel {

	public OSDItemViewerPanel(StandardItemExplorerPanel parentContentPanel) throws ServiceException {
		super(parentContentPanel);
	}

}
