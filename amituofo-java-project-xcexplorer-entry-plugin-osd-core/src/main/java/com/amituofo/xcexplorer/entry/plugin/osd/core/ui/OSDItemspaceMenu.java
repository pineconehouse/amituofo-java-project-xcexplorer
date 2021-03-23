package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.amituofo.common.ui.action.PerformAction;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;
import com.amituofo.xcexplorer.core.ui.ItemExplorer;
import com.amituofo.xcexplorer.core.ui.ItemExplorerContainer;
import com.amituofo.xcexplorer.core.ui.ItemViewer;
import com.amituofo.xcexplorer.core.ui.compoent.container.ClassicItemExplorerContainer;
import com.amituofo.xcexplorer.core.ui.compoent.menu.ContentExplorerMenuBuilder;
import com.amituofo.xcexplorer.core.ui.compoent.menu.StandardMenuItemBuilder;
import com.amituofo.xcexplorer.entry.plugin.osd.core.action.OSDItemspaceAction;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDBucketspace;

public class OSDItemspaceMenu<ACTION extends OSDItemspaceAction> extends ContentExplorerMenuBuilder {
	public final static String MENU_ID = "S3_BUCKET";
	public final StandardMenuItemBuilder CREATE_BUCKET;
	public final StandardMenuItemBuilder DELETE_BUCKET;
	public final StandardMenuItemBuilder RELOAD;
	public final StandardMenuItemBuilder DELETE_ALL;
	public final StandardMenuItemBuilder UNCOMPLETED_MULTIPART_UPLOADS;
//	public final StandardMenuItemBuilder CONFIGURATION;
	public final StandardMenuItemBuilder CONFIGURATION_LIFECYCLE;
	public final StandardMenuItemBuilder CONFIGURATION_CORS;
	public final StandardMenuItemBuilder PROPERTIES;

	protected final ACTION action;

	public OSDItemspaceMenu(String title, final ACTION action) {
		super(MENU_ID, title);
		this.action = action;

		CREATE_BUCKET = new StandardMenuItemBuilder("Create " + title + "...", newItemExplorerViewerActionListener(new PerformAction<ItemExplorer, ItemViewer>() {

			@Override
			public void actionPerformed(ItemExplorer ie, ItemViewer viewer) {
				WorkingSpace ws = viewer.getWorkingSpace();
				action.createBucket(ie, ws);
			}
		}));

		DELETE_BUCKET = new StandardMenuItemBuilder("Delete " + title, newItemExplorerViewerActionListener(new PerformAction<ItemExplorer, ItemViewer>() {

			@Override
			public void actionPerformed(ItemExplorer ie, ItemViewer viewer) {
				WorkingSpace ws = viewer.getWorkingSpace();
				action.deleteBucket(ie, ws);
			}
		}));

		// UIUtils.addSeparator(ITEM_MENU);

		RELOAD = new StandardMenuItemBuilder("Reload " + title + " List", GlobalIcons.ICON_NEW_FOLDER_16x16, newItemExplorerViewerActionListener(new PerformAction<ItemExplorer, ItemViewer>() {

					@Override
					public void actionPerformed(ItemExplorer ie, ItemViewer viewer) {
						// final ItemViewer viewer = ie.getActiveContentViewer();
						// Itemspace itemspace = viewer.getWorkingSpace().getItemspace();
						// action.reloadBucketList(itemspace);

						if (ie instanceof ClassicItemExplorerContainer) {
							((ClassicItemExplorerContainer) ie).getEntryChooseBar().getCurrentWorkingSpaceChooseBar().refresh();
						}
					}
				}));

		DELETE_ALL = new StandardMenuItemBuilder("Delete All Objects", GlobalIcons.ICON_DELETE_FILE_16x16, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContentExplorerContainer container = getExplorerContainer();
				if (container instanceof ItemExplorerContainer) {
					final ItemExplorer ie = ((ItemExplorerContainer) container).getActiveContentExplorer();
					final ItemViewer viewer = ie.getActiveContentViewer();
					WorkingSpace ws = viewer.getWorkingSpace();
					action.deleteBucketObjects(ie, ws);
				}
			}
		});

		UNCOMPLETED_MULTIPART_UPLOADS = new StandardMenuItemBuilder("Uncompleted Multipart Uploads", GlobalIcons.ICON_LIST_16x16, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContentExplorerContainer container = getExplorerContainer();
				if (container instanceof ItemExplorerContainer) {
					final ItemExplorer ie = ((ItemExplorerContainer) container).getActiveContentExplorer();
					final ItemViewer viewer = ie.getActiveContentViewer();
					WorkingSpace ws = viewer.getWorkingSpace();

					action.listUncompletedMultipartUploads(ws);
				}
			}
		});

//		CONFIGURATION = new StandardMenuItemBuilder("Configuration", null, new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				ContentExplorerContainer container = getExplorerContainer();
//				if (container instanceof ItemExplorerContainer) {
//					final ItemExplorer ie = ((ItemExplorerContainer) container).getActiveContentExplorer();
//					final ItemViewer viewer = ie.getActiveContentViewer();
//					WorkingSpace ws = viewer.getWorkingSpace();
//
//					action.openConfiguration(ie, ws);
//				}
//			}
//		});
		// -----------------------------------------------------------------------------------------
		CONFIGURATION_LIFECYCLE = new StandardMenuItemBuilder("Lifecycle", null, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContentExplorerContainer container = getExplorerContainer();
				if (container instanceof ItemExplorerContainer) {
					final ItemExplorer ie = ((ItemExplorerContainer) container).getActiveContentExplorer();
					final ItemViewer viewer = ie.getActiveContentViewer();
					WorkingSpace ws = viewer.getWorkingSpace();

					action.openLifecycleConfiguration((OSDBucketspace)ws.getItemspace());
				}
			}
		});
		CONFIGURATION_CORS = new StandardMenuItemBuilder("CORS", null, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContentExplorerContainer container = getExplorerContainer();
				if (container instanceof ItemExplorerContainer) {
					final ItemExplorer ie = ((ItemExplorerContainer) container).getActiveContentExplorer();
					final ItemViewer viewer = ie.getActiveContentViewer();
					WorkingSpace ws = viewer.getWorkingSpace();

					action.openCORSConfiguration((OSDBucketspace)ws.getItemspace());
				}
			}
		});

		PROPERTIES = new StandardMenuItemBuilder("Properties", null, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContentExplorerContainer container = getExplorerContainer();
				if (container instanceof ItemExplorerContainer) {
					final ItemExplorer ie = ((ItemExplorerContainer) container).getActiveContentExplorer();
					final ItemViewer viewer = ie.getActiveContentViewer();

					// GlobalAction.standard().openPropertiesDialog(viewer);
				}
			}
		});

	}

	@Override
	protected void prepareMenuItems() {
		super.addMenuItems(CREATE_BUCKET);
		super.addMenuItems(DELETE_BUCKET);
		super.addMenuItems(RELOAD);
		super.addSeparator();
		super.addMenuItems(DELETE_ALL);
		// super.addSeparator();
		// super.addMenuItems(UNCOMPLETED_MULTIPART_UPLOADS);
		// super.addMenuItems(CONFIGURATION);
		// super.addSeparator();
		// super.addMenuItems(PROPERTIES);
	}
}
