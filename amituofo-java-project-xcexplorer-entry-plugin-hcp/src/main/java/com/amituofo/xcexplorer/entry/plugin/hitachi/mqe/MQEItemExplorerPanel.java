package com.amituofo.xcexplorer.entry.plugin.hitachi.mqe;

import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;
import com.amituofo.xcexplorer.core.ui.compoent.content.DefaultItemViewerPanel.ItemViewerPopupMenu;
import com.amituofo.xcexplorer.core.ui.compoent.menu.StandardItemMenuBuilder;
import com.amituofo.xcexplorer.core.ui.frame.StandardItemExplorerPanel;

public class MQEItemExplorerPanel extends StandardItemExplorerPanel {

//	@Override
//	public void activing() {
//		super.activing();
//
//		StandardItemMenu itemMenu = super.getPopupMenu(ItemViewerPopupMenu.FILE_ITEM_SELECTION_POPUP_MENU);
//		itemMenu.PASTE.setEnabled(false);
//		itemMenu.PASTE_CLIPBOARD.setEnabled(false);
//		itemMenu.NEW_FOLDER.setEnabled(false);
//		itemMenu.UPLOAD.setEnabled(false);
//	}
//
//	@Override
//	public void deactiving() {
//		super.deactiving();
//		
//		StandardItemMenu itemMenu = super.getPopupMenu(ItemViewerPopupMenu.FILE_ITEM_SELECTION_POPUP_MENU);
//		itemMenu.PASTE.setEnabled(true);
//		itemMenu.PASTE_CLIPBOARD.setEnabled(true);
//		itemMenu.NEW_FOLDER.setEnabled(true);
//		itemMenu.UPLOAD.setEnabled(true);
//
//	}

	public MQEItemExplorerPanel(ContentExplorerContainer explorerContainer) {
		super(explorerContainer, MQEQueryResutViewerPanel.class);
		
		StandardItemMenuBuilder popupMenu = new MQEFileItemSelectionMenuBuilder();
		popupMenu.setExplorerContainer(explorerContainer);
		super.setPopupMenuBuilder(ItemViewerPopupMenu.FILE_ITEM_SELECTION_POPUP_MENU, popupMenu);
		super.setPopupMenuBuilder(ItemViewerPopupMenu.FOLDER_ITEM_SELECTION_POPUP_MENU, null);
		super.setPopupMenuBuilder(ItemViewerPopupMenu.ANY_ITEM_SELECTION_POPUP_MENU, null);
		super.setPopupMenuBuilder(ItemViewerPopupMenu.NO_SELECTION_POPUP_MENU, null);
	}

}
