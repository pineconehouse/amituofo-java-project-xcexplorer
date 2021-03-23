package com.amituofo.xcexplorer.entry.plugin.hitachi.hcpconsole;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.amituofo.xcexplorer.core.ui.ContentExplorer;
import com.amituofo.xcexplorer.core.ui.frame.ContentExplorerContainerPanel;
import com.amituofo.xcexplorer.core.ui.frame.ContentExplorerFrame;

public class HCPManagementContainer extends ContentExplorerContainerPanel<HCPNamespaceManagement> {

	public HCPManagementContainer(ContentExplorerFrame frame) {
		super(frame);
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new HCPNamespaceManagement(this);
		add(panel, BorderLayout.CENTER);
	}

	@Override
	public HCPNamespaceManagement getActiveContentExplorer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HCPNamespaceManagement[] getContentExplorers() {
		// TODO Auto-generated method stub
		return null;
	}

}
