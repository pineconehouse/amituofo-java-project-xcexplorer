package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.common.ui.action.RefreshAction;
import com.amituofo.common.ui.listener.RightClickListener;
import com.amituofo.xcexplorer.core.global.GlobalAction;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.global.GlobalResource;
import com.amituofo.xcexplorer.core.lang.ItemPackage;
import com.amituofo.xcexplorer.core.lang.OpenWith;
import com.amituofo.xcexplorer.core.ui.event.ViewACLListener;
import com.amituofo.xcexplorer.core.ui.event.ViewMetadataListener;
import com.amituofo.xcexplorer.entry.plugin.osd.core.task.TaskUtils;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.model.OSDVersionFileItemModel;
import com.amituofo.xcexplorer.util.ItemUtils;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileItem;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDVersionFileItem;

public class OSDObjectVersionFuncPanel extends OSDObjectFunctionPanel<OSDFileItem> {
	private JToolBar toolBar;
	private OSDObjectVersionItemTable versionTable;
	private OSDVersionFileItemModel model;

	private ViewMetadataListener viewMetadataListener = null;
	private ViewACLListener viewACLListener = null;
	private JPopupMenu fileAndFolderPopupMenu;

	// private boolean withMetadata;
	// private boolean withACL;
	// private boolean withRestore;

	private final ActionListener openAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			List<OSDVersionFileItem> selectedItems = versionTable.getSelectedItemList();
			ItemUtils.openFilesInThread(selectedItems, OpenWith.SystemAssociatedProgram, false);
		}
	};

	private final ActionListener viewContentAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			List<OSDVersionFileItem> selectedItems = versionTable.getSelectedItemList();
			ItemUtils.openFilesInThread(selectedItems, OpenWith.AutoViewer, false);
		}
	};

	private final ActionListener copyAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			ItemPackage sourceContentPackage = OSDObjectVersionFuncPanel.this.getContentPackage();
			GlobalAction.standard().copyItems(sourceContentPackage);
		}
	};

	private final ActionListener restoreAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			ItemPackage sourceContentPackage = OSDObjectVersionFuncPanel.this.getContentPackage();
			if (sourceContentPackage.getItems().length != 1) {
				UIUtils.openWarning("Select one version please!");
			} else {
				OSDVersionFileItem obj = (OSDVersionFileItem) sourceContentPackage.getItems()[0];
				if (UIUtils.openInfoConfirm("Restore object to selected version (" + obj.getVersionId() + ") ?")) {
					TaskUtils.execVersionRestoreTask(sourceContentPackage, OSDObjectVersionFuncPanel.this);
				}
			}
		}
	};

	private final ActionListener downloadAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			ItemPackage sourceContentPackage = OSDObjectVersionFuncPanel.this.getContentPackage();
			TaskUtils.confirmExecDownload2FolderTask(sourceContentPackage);
		}
	};

	private final ActionListener viewMetadataAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (viewMetadataListener != null) {
				List<OSDVersionFileItem> selectedItems = versionTable.getSelectedItemList();
				if (selectedItems.size() > 0) {
					viewMetadataListener.showMetadata(selectedItems.get(0));
				}
			}
		}
	};

	private final ActionListener viewACLAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (viewACLListener != null) {
				List<OSDVersionFileItem> selectedItems = versionTable.getSelectedItemList();
				if (selectedItems.size() > 0) {
					viewACLListener.showACL(selectedItems.get(0));
				}
			}
		}
	};

	private final ActionListener deleteAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			confirmOBSVersionDelete(OSDObjectVersionFuncPanel.this.getContentPackage(), OSDObjectVersionFuncPanel.this);
		}
	};
//	private JProgressBar progressBar;

	public OSDObjectVersionFuncPanel(final OSDVersionFileItemModel model) {
		this(model, true, true, true);
	}

	public OSDObjectVersionFuncPanel(final OSDVersionFileItemModel model, boolean withMetadata, boolean withACL, boolean withRestore) {
		super("Version", GlobalIcons.ICON_VERSION_TAB_16x16);
		this.model = model;
		// this.withMetadata=withMetadata;
		// this.withACL=withACL;
		// this.withRestore=withRestore;

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panel_1.add(toolBar);
//		progressBar = new JProgressBar();
//		panel_1.add(progressBar, BorderLayout.SOUTH);

		JButton btnOpen = new JButton("Open");
		btnOpen.setIcon(GlobalIcons.ICON_OPEN_FILE_16x16);
		btnOpen.addActionListener(openAction);
		toolBar.add(btnOpen);

		JButton btnNewButton = new JButton("View Content");
		btnNewButton.setIcon(GlobalIcons.ICON_VIEW_FILE_16x16);
		btnNewButton.addActionListener(viewContentAction);
		toolBar.add(btnNewButton);

		if (withMetadata) {
			JButton btnViewMetadata = new JButton("View Metadata");
			btnViewMetadata.setIcon(GlobalIcons.ICON_META_TAB_16x16);
			btnViewMetadata.addActionListener(viewMetadataAction);
			toolBar.add(btnViewMetadata);
		}

		if (withACL) {
			JButton btnViewACL = new JButton("View ACL");
			btnViewACL.setIcon(GlobalIcons.ICON_ACL_TAB_16x16);
			btnViewACL.addActionListener(viewACLAction);
			toolBar.add(btnViewACL);
		}

		JButton btnNewButton_1 = new JButton("Download");
		btnNewButton_1.setIcon(GlobalIcons.ICON_DOWNLOAD_16x16);
		btnNewButton_1.addActionListener(downloadAction);
		toolBar.add(btnNewButton_1);

		JButton btnCopy = new JButton("Copy");
		btnCopy.setIcon(GlobalIcons.ICON_COPY_16x16);
		btnCopy.addActionListener(copyAction);
		toolBar.add(btnCopy);

		if (withRestore) {
			JButton btnRestore = new JButton("Restore");
			btnRestore.setIcon(GlobalIcons.ICON_RESTORE_16X16);
			btnRestore.addActionListener(restoreAction);
			toolBar.add(btnRestore);
		}

		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.setIcon(GlobalIcons.ICON_DELETE_FILE_16x16);
		btnNewButton_2.addActionListener(deleteAction);
		toolBar.add(btnNewButton_2);

		final JToggleButton chckbxSkipEmpty = new JToggleButton("Skip Empty");
		chckbxSkipEmpty.setIcon(GlobalIcons.ICON_FILTER_16x16);
		chckbxSkipEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setSkipEmptyItem(chckbxSkipEmpty.isSelected());
				UIUtils.executeAction(new Runnable() {
					@Override
					public void run() {
						refresh();
					}
				});
			}
		});
		chckbxSkipEmpty.setSelected(true);
		model.setSkipEmptyItem(chckbxSkipEmpty.isSelected());
		toolBar.add(chckbxSkipEmpty);

		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);
		versionTable = new OSDObjectVersionItemTable(model);
		versionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		versionTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		versionTable.setRowHeight(22);

		// versionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		//
		// @Override
		// public void valueChanged(ListSelectionEvent e) {
		//
		// }
		// });

		scrollPane.setViewportView(versionTable);

		addPopup(withMetadata, withACL, withRestore);
	}

	public void setViewMetadataListener(ViewMetadataListener viewMetadataListener) {
		this.viewMetadataListener = viewMetadataListener;
	}

	public void setViewACLListener(ViewACLListener viewACLListener) {
		this.viewACLListener = viewACLListener;
	}

	public ItemPackage getContentPackage() {
		return versionTable.getItemPackage();
	}

	protected void confirmOBSVersionDelete(ItemPackage contentPackage, RefreshAction refresh) {
		if (contentPackage.hasItem()) {
			if (UIUtils.openInfoConfirm("Are you sure to delete selected version(s) ?")) {
				TaskUtils.execOBSVersionDeleteTask(contentPackage, refresh);
			}
		}
	}

	@Override
	protected void showObjectInfo(OSDFileItem item) {
		try {
			setEnabled(true);
//			progressBar.setValue(0);
//			progressBar.setIndeterminate(true);
			Thread.sleep(5);
			model.listVersions(item, null);
		} catch (Exception e) {
			GlobalResource.getLogger().error("Error when trying to get version information from " + item.getPath(), e);
		} finally {
//			progressBar.setIndeterminate(false);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		Component[] components = toolBar.getComponents();
		for (Component jComponent : components) {
			jComponent.setEnabled(enabled);
		}
	}

	public void clear() {
		model.removeRows();
		setEnabled(false);
	}

	private void addPopup(boolean withMetadata, boolean withACL, boolean withRestore) {
		createPopupMenu(withMetadata, withACL, withRestore);

		versionTable.setRightClickListener(new RightClickListener<OSDVersionFileItem>() {

			@Override
			public void rightClicked(MouseEvent e, List<OSDVersionFileItem> selectedItems) {
				if (ItemUtils.isEmpty(selectedItems)) {
				} else {
					fileAndFolderPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	private void createPopupMenu(boolean withMetadata, boolean withACL, boolean withRestore) {
		// emptyPopupMenu = new JPopupMenu();
		fileAndFolderPopupMenu = new JPopupMenu();
		UIUtils.addMenuItem(fileAndFolderPopupMenu, "Open", openAction);
		UIUtils.addMenuItem(fileAndFolderPopupMenu, "View Content", GlobalIcons.ICON_VIEW_FILE_16x16, viewContentAction);
		if (withMetadata) {
			UIUtils.addMenuItem(fileAndFolderPopupMenu, "View Metadata", viewMetadataAction);
		}
		if (withACL) {
			UIUtils.addMenuItem(fileAndFolderPopupMenu, "View ACL", viewACLAction);
		}
		UIUtils.addMenuItem(fileAndFolderPopupMenu, "Download", GlobalIcons.ICON_DOWNLOAD_16x16, downloadAction);
		UIUtils.addMenuItem(fileAndFolderPopupMenu, "Copy", GlobalIcons.ICON_COPY_16x16, copyAction);
		if (withRestore) {
			UIUtils.addMenuItem(fileAndFolderPopupMenu, "Restore", GlobalIcons.ICON_RESTORE_16X16, restoreAction);
		}
		UIUtils.addMenuItem(fileAndFolderPopupMenu, "Delete", GlobalIcons.ICON_DELETE_FILE_16x16, deleteAction);

	}

}
