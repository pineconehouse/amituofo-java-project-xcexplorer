package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model;

import javax.swing.SwingConstants;

import com.amituofo.common.ui.lang.DataCell;
import com.amituofo.common.ui.lang.RowDataVector;
import com.amituofo.common.util.FormatUtils;
import com.amituofo.common.util.StringUtils;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.model.OSDVersionFileItemModel;
import com.amituofo.xcexplorer.util.SystemConfigUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDVersionFileItem;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPVersionFileItem;
import com.hitachivantara.hcp.standard.model.HCPObjectSummary;

public class HCPObjectVersionModel extends OSDVersionFileItemModel { // AbstractTableModel
	private final static Class[] columnTypes = new Class[] { DataCell.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			Integer.class,
			String.class,
			String.class };
	private final static String[] columns = new String[] { "No", "VersionId", "Size", "Hash Algorithm", "Hash Value", "Owner", "Metadata Count", "Ingest Time", "Status" };
	private final static int[] columnWidths = new int[]  { 40,   120,         180,    100,              300,          100,     60,               130,           100 };
	private final static int[] columnHAlignments = new int[] { SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER  };

	public HCPObjectVersionModel() {
		super(columnTypes, columns, columnWidths, columnHAlignments);
	}

	@Override
	protected RowDataVector createRowData(OSDVersionFileItem item) {
		HCPObjectSummary summary = ((HCPVersionFileItem)item).getSummary();
		Object col1 = new DataCell(this.getRowCount() + 1, GlobalIcons.ICON_XXXX_16x16);

		RowDataVector row = new RowDataVector(columns.length);
		row.addElement(col1);
		row.addElement(summary.getVersionId());
		row.addElement(FormatUtils.getPrintSize(item.getSize(), true));
		row.addElement(summary.getHashAlgorithmName());
		row.addElement(StringUtils.toLowerCase(summary.getContentHash()));
		row.addElement(summary.getOwner());
		row.addElement(summary.getCustomMetadatas() != null ? summary.getCustomMetadatas().length : 0);
		row.addElement(SystemConfigUtils.formatDatetime(summary.getIngestTime()));
		row.addElement((object.getVersionId().equals(summary.getVersionId())?"Last Version":""));//Previous

		return row;
	}

}
