package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.menu;

import javax.swing.JOptionPane;

import com.amituofo.common.ex.InvalidParameterException;
import com.amituofo.common.ui.action.InputValidator;
import com.amituofo.common.ui.action.PerformAction;
import com.amituofo.common.ui.action.RefreshAction;
import com.amituofo.common.ui.swingexts.component.SimpleDialog;
import com.amituofo.common.util.ValidUtils;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.lang.ItemPackage;
import com.amituofo.xcexplorer.core.ui.ItemExplorer;
import com.amituofo.xcexplorer.core.ui.ItemViewer;
import com.amituofo.xcexplorer.core.ui.compoent.menu.StandardAnyItemSelectionMenuBuilder;
import com.amituofo.xcexplorer.core.ui.compoent.menu.StandardMenuItemBuilder;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.HCPCustomMetadataDialog;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.HCPSystemMetadataDialog;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.HCPTaskUtils;
import com.amituofo.xcexplorer.util.UIUtils;

public class HCPItemSelectionMenuBuilder extends StandardAnyItemSelectionMenuBuilder {
	public final StandardMenuItemBuilder PURGE;
	public final StandardMenuItemBuilder CUSTOM_METADATA;
	public final StandardMenuItemBuilder SYSTEM_METADATA;

	public HCPItemSelectionMenuBuilder() {
		super();

		PURGE = new StandardMenuItemBuilder("Purge", GlobalIcons.ICON_CLEAR_16x16, newItemExplorerViewerActionListener(new PerformAction<ItemExplorer, ItemViewer>() {

			@Override
			public void actionPerformed(ItemExplorer ie, final ItemViewer viewer) {
				ItemPackage cpkg = viewer.getItemPackage();
				onPurge(cpkg, viewer);
			}
		}));

		CUSTOM_METADATA = new StandardMenuItemBuilder("Custom Metadata", GlobalIcons.ICON_META_TAB_16x16, newItemExplorerViewerActionListener(new PerformAction<ItemExplorer, ItemViewer>() {

					@Override
					public void actionPerformed(ItemExplorer ie, final ItemViewer viewer) {
						onSetMetadata(viewer);
					}
				}));

		SYSTEM_METADATA = new StandardMenuItemBuilder("System Metadata", GlobalIcons.ICON_PROPERTIES_16x16, newItemExplorerViewerActionListener(new PerformAction<ItemExplorer, ItemViewer>() {

			@Override
			public void actionPerformed(ItemExplorer ie, final ItemViewer viewer) {
				openSystemMetadataDialog(viewer);
			}
		}));

	}

	@Override
	protected void prepareMenuItems() {
		super.addExtendFootMenuItem(null);
		super.addExtendFootMenuItem(PURGE);
		super.addExtendFootMenuItem(CUSTOM_METADATA);
		super.addExtendFootMenuItem(SYSTEM_METADATA);

		super.prepareMenuItems();
	}

	public void openSystemMetadataDialog(ItemViewer viewer) {
		HCPSystemMetadataDialog panel = new HCPSystemMetadataDialog();

		panel.showItemsSystemMetadata(viewer.getSelections());

		SimpleDialog dialog = new SimpleDialog(panel, 750, 660);
//		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	protected void onSetMetadata(ItemViewer viewer) {
		HCPCustomMetadataDialog panel = new HCPCustomMetadataDialog(viewer);

		SimpleDialog dialog = new SimpleDialog(panel, 650, 420);
//		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	protected void onPurge(ItemPackage contentPackage, RefreshAction refresh) {
		if (!contentPackage.hasItem()) {
			return;
		}

		String[] options = { "Purge", "Privileged Purge", "Cancel" };
		int result = JOptionPane.showOptionDialog(JOptionPane.getRootFrame(), // GlobalUI.MAIN_FRAME,
				"All the versions of file will be delete permanently! [Privileged purge] can purge objects that was under retention. Are you sure to continue delete ?",
				"Confirm",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, // no custom icon
				options, // button titles
				options[0] // default button
		);

		if (result == 0) {
			HCPTaskUtils.execPurgeTask(contentPackage, refresh);
		} else if (result == 1) {
			String reason = UIUtils.openInput("Specify a reason for the purge.", "", new InputValidator<String>() {

				@Override
				public void validate(String value) throws InvalidParameterException {
					ValidUtils.invalidIfTrimEmpty(value, "Reason must be specificed.");
					ValidUtils.invalidIfContains(value, new String[] { "\\", "/", ":", "*", "?", "\"", "<", ">", "|" }, "Invalid character \\/:*?\"<>| included.");
				}
			});

			if (reason != null) {
				HCPTaskUtils.execPrivilegedPurgeTask(contentPackage, refresh, reason);
			}
		} else if (result == 2) {
			// do nothing
		}
	}
}
