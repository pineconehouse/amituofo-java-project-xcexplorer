package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import java.util.List;

import com.amituofo.xcexplorer.core.lang.ItemPackage;
import com.amituofo.xcexplorer.core.ui.compoent.basic.table.PackageableCustomTable;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.model.OSDVersionFileItemModel;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDVersionFileItem;
import com.amituofo.xfs.service.Item;

public class OSDObjectVersionItemTable extends PackageableCustomTable<OSDVersionFileItemModel, OSDVersionFileItem> {
	// public ItemTable() {
	// }

	public OSDObjectVersionItemTable(OSDVersionFileItemModel dm) {
		super(dm);
		// TableColumnModel cm = this.getColumnModel();
		// cm.getColumn(0).setCellRenderer(new DataCellRenderer());
	}

	// @Override
	// public OSDVersionFileItem[] getSelectedItems() {
	// int[] rows = getConvertedSelectedRows();
	// OSDVersionFileItemModel model = (OSDVersionFileItemModel) getModel();
	//
	// OSDVersionFileItem[] items = new OSDVersionFileItem[rows.length];
	//
	// for (int i = 0; i < rows.length; i++) {
	// items[i] = model.getData(rows[i]);
	// }
	//
	// return items;
	// }

	// @Override
	// public List<OSDVersionFileItem> getSelectedItemList() {
	// int[] rows = getConvertedSelectedRows();
	// OSDVersionFileItemModel model = (OSDVersionFileItemModel) getModel();
	// return model.getData(rows);
	// }

	@Override
	public ItemPackage getItemPackage() {

		List<OSDVersionFileItem> itemlist = getSelectedItemList();

		Item[] items = new OSDVersionFileItem[itemlist.size()];
		for (int i = 0; i < items.length; i++) {
			items[i] = itemlist.get(i);
		}

		ItemPackage ip = new ItemPackage(null, items);

		return ip;
	}
}
