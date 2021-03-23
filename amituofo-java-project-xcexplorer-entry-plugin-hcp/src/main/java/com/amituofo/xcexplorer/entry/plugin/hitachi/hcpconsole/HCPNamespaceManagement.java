package com.amituofo.xcexplorer.entry.plugin.hitachi.hcpconsole;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.amituofo.common.ui.swingexts.component.PopupMenu;
import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;
import com.amituofo.xcexplorer.core.ui.ContentViewer;
import com.amituofo.xcexplorer.core.ui.compoent.menu.ContentExplorerMenuBuilder;
import com.amituofo.xcexplorer.core.ui.frame.ContentExplorerPanel;
import com.amituofo.xfs.service.Item;

public class HCPNamespaceManagement extends ContentExplorerPanel {

	public HCPNamespaceManagement(ContentExplorerContainer explorerContainer) {
		super(explorerContainer);
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(null);
		splitPane.setResizeWeight(0.5);
		add(splitPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		splitPane.setLeftComponent(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		splitPane.setRightComponent(panel_1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ContentViewer openContentViewer(Object meta, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentViewer getActiveContentViewer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentViewer[] getContentViewers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContentExplorerMenuBuilder> getCustomMenus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PopupMenu getPopupMenu(List<Item> selectedItems) {
		// TODO Auto-generated method stub
		return null;
	}


}
