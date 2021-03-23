package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import com.amituofo.common.ui.swingexts.component.CustomTable;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.model.OSDACLModel;

public class OSDACLTable<MODEL extends OSDACLModel<PERMISSION_MODEL>, PERMISSION_MODEL> extends CustomTable<MODEL, PERMISSION_MODEL> {

	public OSDACLTable(MODEL dm) {
		super(dm);
	}

	public PERMISSION_MODEL[] getSelectedItems() {
		int[] rows = getConvertedSelectedRows();
		MODEL model = (MODEL) getModel();

		PERMISSION_MODEL[] items = (PERMISSION_MODEL[])model.newArray(rows.length);//new PERMISSION_MODEL[rows.length];

		for (int i = 0; i < rows.length; i++) {
			items[i] = (PERMISSION_MODEL)model.getData(rows[i]);
		}

		return items;

	}

//	@Override
//	public List<PERMISSION_MODEL> getSelectedItemList() {
//		int[] rows = getConvertedSelectedRows();
//		MODEL model = (MODEL) getModel();
//		return model.getData(rows);
//	}

}
