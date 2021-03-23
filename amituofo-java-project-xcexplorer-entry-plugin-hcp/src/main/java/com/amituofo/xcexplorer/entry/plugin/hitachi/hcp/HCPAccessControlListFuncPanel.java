package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model.HCPACLModel;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDACLTable;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDAccessControlListFuncPanel;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.model.OSDACLModel;
import com.hitachivantara.hcp.standard.model.metadata.PermissionGrant;

public class HCPAccessControlListFuncPanel extends OSDAccessControlListFuncPanel {

	public HCPAccessControlListFuncPanel() {
		super(new OSDACLTable<HCPACLModel, PermissionGrant>(new HCPACLModel()));
		table.setColumnVisable(OSDACLModel.COL_FULL_CONTROL, false);
	}

}
