package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;
import com.amituofo.xcexplorer.core.ui.compoent.content.DefaultItemViewerPanel;
import com.amituofo.xcexplorer.core.ui.compoent.content.RemoteItemExplorerPanel;
import com.amituofo.xcexplorer.core.ui.compoent.menu.ContentExplorerMenuBuilder;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileSystemPreference;

public class OSDItemExplorerPanel extends RemoteItemExplorerPanel {

	private JToggleButton showVersions;
	private OSDItemspaceMenu bucketMenu;
	public final static int INDEX_TOOLBAR_BTN_LIST_VERSIONS = 4;

	public OSDItemExplorerPanel(ContentExplorerContainer explorerContainer,
			WorkingSpace workingSpace,
			Class<? extends DefaultItemViewerPanel> classItemTableTabPanel,
			OSDItemspaceMenu bucketMenu) {
		this(explorerContainer, workingSpace, classItemTableTabPanel, bucketMenu, 0);
	}

	public OSDItemExplorerPanel(ContentExplorerContainer explorerContainer,
			WorkingSpace workingSpace,
			Class<? extends DefaultItemViewerPanel> classItemTableTabPanel,
			OSDItemspaceMenu bucketMenu,
			int osdUIStyle) {
		super(explorerContainer, classItemTableTabPanel);
		this.bucketMenu = bucketMenu;

		if (!OSDUIStyle.has(osdUIStyle, OSDUIStyle.HIDDEN_LIST_VERSION_BUTTON)) {
			JSeparator separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			toolBarExtend.add(separator);

			showVersions = new JToggleButton("List Versions");
			showVersions.setIcon(GlobalIcons.ICON_LIST_24x24);
			showVersions.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					OSDFileSystemPreference config;
					config = (OSDFileSystemPreference) OSDItemExplorerPanel.this.getActiveContentViewer().getWorkingDirectory().getOperationPreference();
					config.setListByVersions(showVersions.isSelected());

					OSDItemExplorerPanel.this.clearCache();
					OSDItemExplorerPanel.this.refresh();
				}
			});
			toolBarExtend.add(showVersions);
		}

		// if (OSDUIStyle.has(osdUIStyle, OSDUIStyle.HIDDEN_LIST_VERSION_BUTTON)) {
		// super.enableExtendToolbarItem(INDEX_TOOLBAR_BTN_LIST_VERSIONS, false);
		// }
	}

	@Override
	public void refresh() {
		super.refresh();
	}

	@Override
	public List<ContentExplorerMenuBuilder> getCustomMenus() {
		// return new ContentExplorerMenu[] { bucketMenu };
		List<ContentExplorerMenuBuilder> menus = super.getCustomMenus();
		menus.add(0, bucketMenu);
		return menus;
	}

}
