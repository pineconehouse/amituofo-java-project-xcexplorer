package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;

import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.lang.ItemPackage;
import com.amituofo.xcexplorer.core.ui.ItemViewer;
import com.amituofo.xcexplorer.core.ui.frame.ItemsBatchDialogPanel;

public class HCPCustomMetadataDialog extends ItemsBatchDialogPanel {
	private HCPCustomMetadataPanel metaPanel;

	public HCPCustomMetadataDialog(ItemViewer viewer) {
		super(viewer);
		setLayout(new BorderLayout(0, 0));

		metaPanel = new HCPCustomMetadataPanel(true, false, false);
		add(metaPanel, BorderLayout.CENTER);
		
		ItemPackage pkg = viewer.getItemPackage();
		metaPanel.setItemPackage(pkg);
		
//		if(pkg.getItems().length==1) {
//			Item item = pkg.getItems()[0];
//			if(item.isFile()) {
//				HCPFileItem hcpobj = (HCPFileItem)item;
//				
//				hcpobj.getm
//			}
//		}
	}

	@Override
	public boolean okPressed() {
		return metaPanel.save();
	}

	@Override
	public boolean cancelPressed() {
		return true;
	}

	@Override
	public String getTitle() {
		return "Batch Update Metadata";
	}

	@Override
	public ImageIcon getIcon() {
		return GlobalIcons.ICON_META_TAB_16x16;
	}

}
