package com.amituofo.xcexplorer.entry.plugin.hitachi.mqe;

import com.amituofo.common.define.HandleFeedback;
import com.amituofo.common.ex.ServiceException;
import com.amituofo.common.ui.listener.PagingListener;
import com.amituofo.common.ui.swingexts.component.DefaultPanel;
import com.amituofo.xcexplorer.core.global.SystemConfigKeys;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.compoent.PagingPanel;
import com.amituofo.xcexplorer.core.ui.frame.StandardItemExplorerPanel;
import com.amituofo.xcexplorer.core.ui.model.ItemModel;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.HCPItemViewerPanel;
import com.amituofo.xcexplorer.entry.plugin.hitachi.mqe.model.MQEItemModel;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.QueryStatusHandler;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.item.MQEFolderItem;
import com.amituofo.xfs.service.FolderItem;
import com.hitachivantara.hcp.query.model.QueryStatus;

public class MQEQueryResutViewerPanel extends HCPItemViewerPanel {
	private static int index = 1;
	private PagingPanel pagingPanel = new PagingPanel();;
	private MQEItemModel model;
	private WorkingSpace workingSpace;

	// public QueryResutTablePanel() throws ServiceException {
	// super();
	// }

	public MQEQueryResutViewerPanel(StandardItemExplorerPanel parentContentPanel) throws ServiceException {
		super(parentContentPanel);
	}

	@Override
	protected DefaultPanel createHeaderPanel(WorkingSpace workingSpace) {
		// return super.createHeaderPanel(workingSpace);
		this.workingSpace = workingSpace;
		return null;
	}

	@Override
	public FolderItem getWorkingDirectory() {
		return workingSpace.getHomeFolder();
	}

	protected ItemModel createItemModel(WorkingSpace workingSpace) {
		MQEFolderItem query = (MQEFolderItem) workingSpace.getHomeFolder();

		query.setStatusHandler(new QueryStatusHandler() {

			@Override
			public HandleFeedback queryStatusChanged(QueryStatus status) {
				pagingPanel.setTotalCount(status.getTotalResults());
				return HandleFeedback.succeed;
			}
		});

		model = new MQEItemModel(workingSpace, query);
		model.setQueryOffset(0);
		return model;
	}

	@Override
	public void deactiving() {
		super.deactiving();
		// HCPItemExplorerPanel contentPanel = (HCPItemExplorerPanel) this.getContentExplorer();
		StandardItemExplorerPanel contentPanel = (StandardItemExplorerPanel) this.getContentExplorer();
		contentPanel.enableDefaultToolbarItem(StandardItemExplorerPanel.INDEX_TOOLBAR_BTN_NEW_FOLDER, true);
		// contentPanel.enableBasicToolbarItem(HCPItemManagementPanel.INDEX_TOOLBAR_BTN_CUT, true);
		// contentPanel.enableBasicToolbarItem(HCPItemManagementPanel.INDEX_TOOLBAR_BTN_COPY, true);
		contentPanel.enableDefaultToolbarItem(StandardItemExplorerPanel.INDEX_TOOLBAR_BTN_PASTE, true);
		// contentPanel.enableExtendToolbarItem(HCPItemExplorerPanel.INDEX_TOOLBAR_BTN_UPLOAD, true);
		// contentPanel.enableExtendToolbarItem(HCPItemExplorerPanel.INDEX_TOOLBAR_BTN_DOWNLOAD, true);
		// contentPanel.enableExtendToolbarItem(HCPItemExplorerPanel.INDEX_TOOLBAR_BTN_SHOW_DELETED, true);

		// super.enableMenuItem(PopupCategory.FILE_POPUP_MENU, InnerItemManagementPanel.INDEX_FILE_POPUP_MENU_REFRESH, true);
		// super.enableMenuItem(PopupCategory.FILE_ITEM_SELECTION_POPUP_MENU, DefaultItemViewerPanel.INDEX_FILE_POPUP_MENU_PASTE, true);
		// super.enableMenuItem(PopupCategory.FILE_ITEM_SELECTION_POPUP_MENU, DefaultItemViewerPanel.INDEX_FILE_POPUP_MENU_CLIPBOARD_PASTE, true);
		// super.enableMenuItem(PopupCategory.FILE_ITEM_SELECTION_POPUP_MENU, DefaultItemViewerPanel.INDEX_FILE_POPUP_MENU_NEWFOLDER, true);
		// // super.enableMenuItem(PopupCategory.FILE_POPUP_MENU, InnerItemManagementPanel.INDEX_FILE_POPUP_MENU_CUT, true);
		// // super.enableMenuItem(PopupCategory.FILE_POPUP_MENU, InnerItemManagementPanel.INDEX_FILE_POPUP_MENU_COPY, true);
		// super.enableMenuItem(PopupCategory.FILE_ITEM_SELECTION_POPUP_MENU, DefaultItemViewerPanel.INDEX_FILE_POPUP_MENU_PASTE, true);
		//
		// super.enableMenu(PopupCategory.NO_SELECTION_POPUP_MENU, true);
		// super.enableMenu(PopupCategory.ANY_ITEM_SELECTION_POPUP_MENU, true);
		// super.enableMenu(PopupCategory.FOLDER_ITEM_SELECTION_POPUP_MENU, true);
	}

	@Override
	public String getTitle() {
		return "Query Result " + (index++);
	}

	@Override
	public void activing() {
		super.activing();
		// ContentExplorerMenuBar menubar = GlobalUI.Elements.GLOBAL_MENU_BAR.getInstance();
		// StandardItemMenu menuItem = (StandardItemMenu) menubar.getMenu(StandardItemMenu.MENU_ID);
		// if (menuItem != null) {
		//// menuItem.DOWNLOAD.setEnabled(false);
		// menuItem.UPLOAD.setEnabled(false);
		// }

		// HCPItemExplorerPanel contentPanel = (HCPItemExplorerPanel) this.getContentExplorer();
		StandardItemExplorerPanel contentPanel = (StandardItemExplorerPanel) this.getContentExplorer();
		// protected final int INDEX_TOOLBAR_BTN_NEWFOLDER = 0;
		// protected final int INDEX_TOOLBAR_BTN_CUT = 1;
		// protected final int INDEX_TOOLBAR_BTN_COPY = 2;
		// protected final int INDEX_TOOLBAR_BTN_PASTE = 3;
		// protected final int INDEX_TOOLBAR_BTN_DELETER = 4;
		// protected final int INDEX_TOOLBAR_BTN_REFRESH = 5;
		// protected final int INDEX_TOOLBAR_BTN_SELECT = 6;
		// protected final int INDEX_TOOLBAR_BTN_PROPERTIES = 7;

		contentPanel.enableDefaultToolbarItem(StandardItemExplorerPanel.INDEX_TOOLBAR_BTN_NEW_FOLDER, false);
		// contentPanel.enableBasicToolbarItem(HCPItemManagementPanel.INDEX_TOOLBAR_BTN_CUT, false);
		// contentPanel.enableBasicToolbarItem(HCPItemManagementPanel.INDEX_TOOLBAR_BTN_COPY, false);
		contentPanel.enableDefaultToolbarItem(StandardItemExplorerPanel.INDEX_TOOLBAR_BTN_PASTE, false);
		// contentPanel.enableBasicToolbarItem(BaseItemManagementPanel.INDEX_TOOLBAR_BTN_PASTE, false);
		// contentPanel.enableExtendToolbarItem(HCPItemExplorerPanel.INDEX_TOOLBAR_BTN_UPLOAD, false);
		// contentPanel.enableExtendToolbarItem(HCPItemExplorerPanel.INDEX_TOOLBAR_BTN_DOWNLOAD, false);
		// contentPanel.enableExtendToolbarItem(HCPItemExplorerPanel.INDEX_TOOLBAR_BTN_SHOW_DELETED, false);

		// super.enableMenuItem(PopupCategory.FILE_POPUP_MENU, InnerItemManagementPanel.INDEX_FILE_POPUP_MENU_REFRESH, false);
		// super.enableMenuItem(PopupCategory.FILE_ITEM_SELECTION_POPUP_MENU, DefaultItemViewerPanel.INDEX_FILE_POPUP_MENU_PASTE, false);
		// super.enableMenuItem(PopupCategory.FILE_ITEM_SELECTION_POPUP_MENU, DefaultItemViewerPanel.INDEX_FILE_POPUP_MENU_CLIPBOARD_PASTE, false);
		// super.enableMenuItem(PopupCategory.FILE_ITEM_SELECTION_POPUP_MENU, DefaultItemViewerPanel.INDEX_FILE_POPUP_MENU_NEWFOLDER, false);
		// // super.enableMenuItem(PopupCategory.FILE_POPUP_MENU, InnerItemManagementPanel.INDEX_FILE_POPUP_MENU_CUT, false);
		// // super.enableMenuItem(PopupCategory.FILE_POPUP_MENU, InnerItemManagementPanel.INDEX_FILE_POPUP_MENU_COPY, false);
		// super.enableMenuItem(PopupCategory.FILE_ITEM_SELECTION_POPUP_MENU, DefaultItemViewerPanel.INDEX_FILE_POPUP_MENU_PASTE, false);
		//
		// super.enableMenu(PopupCategory.NO_SELECTION_POPUP_MENU, false);
		// super.enableMenu(PopupCategory.ANY_ITEM_SELECTION_POPUP_MENU, false);
		// super.enableMenu(PopupCategory.FOLDER_ITEM_SELECTION_POPUP_MENU, false);
		// refresh();
	}

	@Override
	protected DefaultPanel createFooterPanel(WorkingSpace workingSpace) {
		pagingPanel.setPageSize(workingSpace.getIntValue(SystemConfigKeys._HIDDEN_PAGE_SIZE_));
		pagingPanel.setPagingListener(new PagingListener() {

			@Override
			public void gotoPage(int offset, int pagesize) {
				model.setQueryOffset(offset);
				model.setQueryPageSize(pagesize);
				refresh();
			}

			@Override
			public void loadingAll() {
				model.setQueryOffset(-1);
				model.setQueryPageSize(-1);
				refresh();
			}

			@Override
			public void cancel() {
				interruptWorking();
			}
		});

		return pagingPanel;
	}

	// @Override
	// protected void onCopyAction() {
	// List<Item> items = this.getSelections();
	// for (Item item : items) {
	// MQEFileItem mqeitem = (MQEFileItem) item;
	// if (Item.ITEM_STATUS_DELETED == mqeitem.getStatus()) {
	// UIUtils.openWarning("Alert", "Unavailable item selected! Please ignore [DELETED] items");
	// return;
	// }
	// }
	//
	// GlobalAction.standard().flatCopy(this);
	// }
	//
	// @Override
	// protected void onCutAction() {
	// List<Item> items = this.getSelections();
	// for (Item item : items) {
	// MQEFileItem mqeitem = (MQEFileItem) item;
	// if (Item.ITEM_STATUS_DELETED == mqeitem.getStatus()) {
	// UIUtils.openWarning("Alert", "Unavailable item selected! Please ignore [DELETED] items");
	// return;
	// }
	// }
	//
	// GlobalAction.standard().flatCut(this);
	// }

}
