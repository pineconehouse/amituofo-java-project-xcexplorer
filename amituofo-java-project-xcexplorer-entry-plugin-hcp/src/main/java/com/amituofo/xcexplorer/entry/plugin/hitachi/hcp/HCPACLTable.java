package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import com.amituofo.common.ui.swingexts.component.CustomTable;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model.HCPACLModel;
import com.hitachivantara.hcp.standard.model.metadata.PermissionGrant;

public class HCPACLTable extends CustomTable<HCPACLModel, PermissionGrant> {

	public HCPACLTable(HCPACLModel dm) {
		super(dm);

	}

	public PermissionGrant[] getSelectedItems() {
		int[] rows = getConvertedSelectedRows();
		HCPACLModel model = (HCPACLModel) getModel();

		PermissionGrant[] items = new PermissionGrant[rows.length];

		for (int i = 0; i < rows.length; i++) {
			items[i] = model.getData(rows[i]);
		}

		return items;

	}

//	@Override
//	public List<PermissionGrant> getSelectedItemList() {
//		int[] rows = getConvertedSelectedRows();
//		HCPACLModel model = (HCPACLModel) getModel();
//		return model.getData(rows);
//	}

}
