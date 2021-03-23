package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Icon;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.common.ui.action.RefreshAction;
import com.amituofo.xcexplorer.core.global.GlobalResource;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.ContentExplorer;
import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;
import com.amituofo.xcexplorer.core.ui.ItemExplorer;
import com.amituofo.xcexplorer.core.ui.ItemFunctionBar;
import com.amituofo.xcexplorer.core.ui.ItemViewer;
import com.amituofo.xcexplorer.core.ui.frame.ItemFunctionTabPanel;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.service.FileItem;
import com.amituofo.xfs.service.Item;

public abstract class OSDObjectFunctionPanel<OBJ extends FileItem> extends ItemFunctionTabPanel implements RefreshAction {
	private OBJ workingItem = null;
	private ItemURLPanel itemUrlPanel;

	public OSDObjectFunctionPanel(String title, Icon icon) {
		super(title, icon);

		this.setLayout(new BorderLayout(0, 0));

		itemUrlPanel = new ItemURLPanel();
		add(itemUrlPanel, BorderLayout.NORTH);

		itemUrlPanel.addRefreshActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				UIUtils.executeAction(new Runnable() {
				UIUtils.executeWaitingAction(new Runnable() {
					@Override
					public void run() {
						if (workingItem != null) {
							try {
								workingItem.upateProperties();
							} catch (ServiceException e1) {
								e1.printStackTrace();
							}
						}
						refresh();
					}
				});
			}
		});
	}

	public OBJ getWorkingItem() {
		return workingItem;
	}

	protected abstract void clear();

	protected abstract void showObjectInfo(OBJ item);

	@Override
	public void activing() {
		super.activing();
		// if (workingItem == null) {
		ContentExplorerContainer ec = this.getExplorerContainer();
		if (ec != null) {
			ContentExplorer ce = ec.getActiveContentExplorer();
			if (ce != null && ce instanceof ItemExplorer) {
				ItemViewer viewer = ((ItemExplorer) ce).getActiveContentViewer();
				List<Item> selectedItems = viewer.getSelections();
				selected(viewer, selectedItems);
			}
		}
		// } else {
		// refresh();
		// }
	}

	@Override
	public void switchWorkingSpace(WorkingSpace workingspace) {
		selected(null, null);
	}

	@Override
	public void selected(ItemViewer viewer, List<Item> items) {
		if (items != null && items.size() > 0) {
			Item item = items.get(0);
			if (item instanceof FileItem) {
				workingItem = (OBJ) items.get(0);
				refresh();
				return;
			}
		}

		workingItem = null;
		itemUrlPanel.clear();
		clear();
	}

	@Override
	public void refresh() {
		// System.out.println(this.getTitle()+this.isActived());
		if (workingItem != null && this.isActived()) {
			itemUrlPanel.setFileItem(workingItem);

//			UIUtils.executeWaitingAction(new Runnable() {
//				 UIUtils.executeAction(new Runnable() {
					 UIUtils.executeActionInThread(new Runnable() {

				@Override
				public void run() {
					boolean exist = false;
					try {
						exist = workingItem.exists();
						workingItem.upateProperties();
					} catch (ServiceException e) {
						e.printStackTrace();

						GlobalResource.getLogger().error("Object does not exist! " + workingItem.getPath(), e);

						workingItem = null;
						itemUrlPanel.clear();
						clear();
						return;
					}

					if (exist) {
						// obsUrlPanel.setOSDFileItem(workingItem);
						try {
							showObjectInfo(workingItem);
						} catch (Throwable e) {
							e.printStackTrace();
							GlobalResource.getLogger().error("Unable to get object information ! " + workingItem.getPath(), e);
						}
					} else {
						itemUrlPanel.clear();
						clear();
					}
				}
			});
		}
	}

	@Override
	public ItemFunctionBar getFunctionBar() {
		return null;
	}

}
