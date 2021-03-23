package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.ImageIcon;

import com.amituofo.common.ui.swingexts.component.SimpleDialogContentPanel;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xfs.service.Item;

public class HCPSystemMetadataDialog extends SimpleDialogContentPanel {

	private HCPSystemMetadataPanel panel_2;

	public HCPSystemMetadataDialog() {
		setLayout(new BorderLayout(0, 0));
		
//		JPanel panel = new JPanel();
//		add(panel, BorderLayout.NORTH);
//		
//		JPanel panel_1 = new JPanel();
//		add(panel_1, BorderLayout.SOUTH);
		
//		TabbedPanel tabbedPane = new TabbedPanel(JTabbedPane.TOP);
//		add(tabbedPane, BorderLayout.CENTER);
		
		panel_2 = new HCPSystemMetadataPanel();
		add(panel_2, BorderLayout.CENTER);
		// TODO Auto-generated constructor stub
	}
	
	public void showItemsSystemMetadata(List<Item> items) {
		panel_2.showItemsSystemMetadata(items);
	}

	@Override
	public boolean okPressed() {
		return panel_2.saveSystemMetadata();
	}

	@Override
	public boolean cancelPressed() {
		return true;
	}

	@Override
	public String getTitle() {
		return "System Metadata";
	}

	@Override
	public ImageIcon getIcon() {
		return GlobalIcons.ICON_META_TAB_16x16;
	}

}
