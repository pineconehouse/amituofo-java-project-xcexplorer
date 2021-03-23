package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;
import com.amituofo.xcexplorer.core.ui.compoent.content.DefaultItemViewerPanel.ItemViewerPopupMenu;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.menu.HCPFileItemSelectionMenuBuilder;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.menu.HCPFolderItemSelectionMenuBuilder;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.menu.HCPItemSelectionMenuBuilder;
import com.amituofo.xcexplorer.entry.plugin.osd.core.action.OSDItemspaceAction;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDItemExplorerPanel;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDItemspaceMenu;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDUIStyle;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.HCPFileSystemPreference;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPBucketspace;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPItemBase;
import com.hitachivantara.hcp.standard.api.HCPNamespace;
import com.hitachivantara.hcp.standard.model.NamespaceBasicSetting;

public class HCPItemExplorerPanel extends OSDItemExplorerPanel {

	private JToggleButton showDeletedButton;
	public final static int INDEX_TOOLBAR_BTN_SHOW_DELETED = 3;

	public HCPItemExplorerPanel(ContentExplorerContainer explorerContainer, WorkingSpace workingSpace) {
		super(explorerContainer,
				workingSpace,
				HCPItemViewerPanel.class,
				new OSDItemspaceMenu("Namespace", new OSDItemspaceAction("namespace")),
				OSDUIStyle.HIDDEN_LIST_VERSION_BUTTON);
		
		super.setPopupMenuBuilder(ItemViewerPopupMenu.FILE_ITEM_SELECTION_POPUP_MENU, new HCPFileItemSelectionMenuBuilder());
		super.setPopupMenuBuilder(ItemViewerPopupMenu.FOLDER_ITEM_SELECTION_POPUP_MENU, new HCPFolderItemSelectionMenuBuilder());
		super.setPopupMenuBuilder(ItemViewerPopupMenu.ANY_ITEM_SELECTION_POPUP_MENU, new HCPItemSelectionMenuBuilder());

		showDeletedButton = new JToggleButton("Show Deleted");
		showDeletedButton.setIcon(GlobalIcons.ICON_SHOW_DELETED_24x24);
		showDeletedButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				HCPBucketspace ns;
				ns = (HCPBucketspace) HCPItemExplorerPanel.this.getActiveContentViewer().getWorkingSpace().getItemspace();

				HCPFileSystemPreference config;
				config = (HCPFileSystemPreference) ns.getFileSystemPreference();
				config.setShowDeletedObjects(showDeletedButton.isSelected());

				HCPItemExplorerPanel.this.clearCache();
				HCPItemExplorerPanel.this.refresh();
			}
		});

		try {
			HCPNamespace client = ((HCPItemBase) workingSpace.getHomeFolder()).getHcpClient();
			NamespaceBasicSetting ns = client.getNamespaceSetting();
			showDeletedButton.setEnabled(ns.isVersioningEnabled());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		toolBarExtend.add(showDeletedButton);
	}

}
