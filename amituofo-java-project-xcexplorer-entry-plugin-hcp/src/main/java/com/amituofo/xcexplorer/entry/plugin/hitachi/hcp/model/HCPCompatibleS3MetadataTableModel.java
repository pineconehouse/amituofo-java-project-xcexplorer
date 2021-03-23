package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model;

import java.util.Set;

import com.amituofo.common.api.ObjectHandler;
import com.amituofo.common.kit.value.KeyValue;
import com.amituofo.common.ui.swingexts.model.KeyValueTableModel;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPFileItem;
import com.hitachivantara.hcp.standard.api.HCPNamespace;
import com.hitachivantara.hcp.standard.model.metadata.S3CompatibleMetadata;

public class HCPCompatibleS3MetadataTableModel extends KeyValueTableModel<String> { // AbstractTableModel

	private HCPFileItem object;

	public HCPFileItem getWorkingObject() {
		return object;
	}

	public void setWorkingObject(HCPFileItem item) {
		this.object = item;
	}

	@Override
	protected void list(ObjectHandler<Integer, KeyValue<String>> event, Object... args) {
		if (object != null) {
			this.removeRows();

			HCPNamespace hcpClient = object.getHcpClient();

			S3CompatibleMetadata s3Meta;
			try {
				s3Meta = hcpClient.getMetadata(object.getKey());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			Set<String> keys = s3Meta.keySet();
			for (String key : keys) {
				String value = s3Meta.get(key);
				add(new KeyValue<String>(key, value));
			}
		}
	}

}
