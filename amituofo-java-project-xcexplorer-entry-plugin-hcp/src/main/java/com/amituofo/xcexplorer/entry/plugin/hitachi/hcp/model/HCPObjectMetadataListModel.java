package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model;

import javax.swing.DefaultComboBoxModel;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPFileItem;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPMetadataItem;

public class HCPObjectMetadataListModel extends DefaultComboBoxModel<HCPMetadataItem> { // AbstractTableModel
	private HCPFileItem object;

	public HCPObjectMetadataListModel() {
	}

	public HCPFileItem getWorkingObject() {
		return object;
	}

	public int listMetadata(HCPFileItem object) throws ServiceException {
		this.removeAllElements();
		HCPMetadataItem[] metas;
		metas = object.listMetadatas();
		for (int i = 0; i < metas.length; i++) {
			this.addElement(metas[i]);
		}

		return metas.length;
	}

}
