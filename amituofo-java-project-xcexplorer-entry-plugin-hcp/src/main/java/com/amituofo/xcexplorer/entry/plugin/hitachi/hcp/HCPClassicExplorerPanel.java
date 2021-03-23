package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.ItemExplorerContainer;
import com.amituofo.xcexplorer.core.ui.event.ViewACLListener;
import com.amituofo.xcexplorer.core.ui.event.ViewMetadataListener;
import com.amituofo.xcexplorer.core.ui.frame.FunctionTabPanel;
import com.amituofo.xcexplorer.core.ui.frame.NavigationPanel;
import com.amituofo.xcexplorer.core.ui.frame.StandardItemExplorerPanel;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model.HCPObjectVersionModel;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDClassicExplorerPanel;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDObjectVersionFuncPanel;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileItem;
import com.amituofo.xfs.service.FolderItem;
import com.amituofo.xfs.util.ItemUtils;

/**
 * HCP管理界面，带目录树
 * 
 * @author sohan
 *
 */
public class HCPClassicExplorerPanel extends OSDClassicExplorerPanel {
	private OSDObjectVersionFuncPanel versionsPanel;
	private HCPCustomMetadataFuncPanel metadataPanel;
	private HCPAccessControlListFuncPanel aclPanel;
	private HCPQueryFuncPanel queryPanel = new HCPQueryFuncPanel();
	private HCPItemExplorerPanel hcpItemManagementPanel;
	
	public HCPClassicExplorerPanel(ItemExplorerContainer explorerContainer) {
		super(explorerContainer);
	}

	@Override
	protected FunctionTabPanel[] createExtendItemFunctionPanels() {
		FunctionTabPanel[] panels = new FunctionTabPanel[4];

		versionsPanel = new OSDObjectVersionFuncPanel(new HCPObjectVersionModel());
		panels[0] = versionsPanel;
		metadataPanel = new HCPCustomMetadataFuncPanel();
		panels[1] = metadataPanel;
		aclPanel = new HCPAccessControlListFuncPanel();
		panels[2] = aclPanel;
		queryPanel = new HCPQueryFuncPanel();
		panels[3] = queryPanel;

		versionsPanel.setViewMetadataListener(new ViewMetadataListener() {

			@Override
			public void showMetadata(OSDFileItem item) {
				swtichToFunctionTab(metadataPanel);
				metadataPanel.selected(null, ItemUtils.toList(item));
			}
		});
		versionsPanel.setViewACLListener(new ViewACLListener() {

			@Override
			public void showACL(OSDFileItem item) {
				swtichToFunctionTab(aclPanel);
				aclPanel.selected(null, ItemUtils.toList(item));
			}

		});
		return panels;
	}

	@Override
	protected StandardItemExplorerPanel createItemExplorerPanel(WorkingSpace workingSpace) {
		hcpItemManagementPanel = new HCPItemExplorerPanel(super.getExplorerContainer(), workingSpace);
		queryPanel.setExplorerContainer(getExplorerContainer());
//		x
		return hcpItemManagementPanel;
	}

	@Override
	protected NavigationPanel<Integer, FolderItem> createNavigationPanel(WorkingSpace workingSpace) {
		HCPNavigationPanel navigationPanel = new HCPNavigationPanel(this, workingSpace);

		return navigationPanel;
	}

}
