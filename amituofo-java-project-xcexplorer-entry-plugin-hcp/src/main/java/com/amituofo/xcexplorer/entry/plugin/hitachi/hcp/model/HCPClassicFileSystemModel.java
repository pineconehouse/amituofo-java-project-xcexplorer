package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.amituofo.common.ui.lang.DataCell;
import com.amituofo.common.ui.lang.RowDataVector;
import com.amituofo.common.ui.swingexts.TableStyle;
import com.amituofo.common.util.StringUtils;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.model.ItemModel;
import com.amituofo.xcexplorer.core.ui.model.RemoteItemModel;
import com.amituofo.xcexplorer.util.SystemConfigUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPFileItem;
import com.amituofo.xfs.service.FolderItem;
import com.amituofo.xfs.service.Item;
import com.amituofo.xfs.service.ItemType;
import com.amituofo.xfs.util.ItemSizeColumn;
import com.amituofo.xfs.util.ItemTypeColumn;
import com.hitachivantara.hcp.standard.model.HCPObjectSummary;
import com.hitachivantara.hcp.standard.model.metadata.Annotation;

public class HCPClassicFileSystemModel extends RemoteItemModel { // AbstractTableModel
//	private final static Class[] columnTypes = new Class[]    { DataCell.class, ItemTypeColumn.class, ItemSizeColumn.class,  String.class,          String.class,          String.class,          String.class,          String.class,          Integer.class,         String.class,          String.class,          String.class,          };
//	private final static String[] columns = new String[]      { "Name",         "Type",               "Size",                "Ingest Time",         "Statistics",           "Retention",           "Owner",               "Metas",               "Dpl",                 "Ingest Protocol",     "Version Id",          "ETag",                };
//	private final static int[] columnWidths = new int[]       { 400,            80,                   100,                   135,                   220,                   120,                   60,                    40,                    40,                    50,                    125,                   250,                   };
//	private final static int[] columnHAlignments = new int[]  { -1,             -1,                   SwingConstants.RIGHT,  SwingConstants.CENTER, SwingConstants.LEFT,   SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.LEADING  };
	private final static Class[] columnTypes = new Class[]    { DEFAULT_COLUMN_TYPES[0],       DEFAULT_COLUMN_TYPES[1],       DEFAULT_COLUMN_TYPES[2],        DEFAULT_COLUMN_TYPES[3],       DEFAULT_COLUMN_TYPES[4],       String.class,          String.class,          String.class,          Integer.class,         String.class,          String.class,          String.class,          };
	private final static String[] columns = new String[]      { DEFAULT_COLUMNS[0],            DEFAULT_COLUMNS[1],            DEFAULT_COLUMNS[2],             "Ingest Time",                 DEFAULT_COLUMNS[4],            "Retention",           "Owner",               "Metas",               "Dpl",                 "Ingest Protocol",     "Version Id",          "ETag",                };
	private final static int[] columnWidths = new int[]       { 400,                           DEFAULT_COLUMN_WIDTHS[1],      DEFAULT_COLUMN_WIDTHS[2],       DEFAULT_COLUMN_WIDTHS[3],      DEFAULT_COLUMN_WIDTHS[4],      120,                   60,                    40,                    40,                    50,                    125,                   250,                   };
	private final static int[] columnHAlignments = new int[]  { DEFAULT_COLUMN_HALIGNMENTS[0], DEFAULT_COLUMN_HALIGNMENTS[1], DEFAULT_COLUMN_HALIGNMENTS[2],  DEFAULT_COLUMN_HALIGNMENTS[3], DEFAULT_COLUMN_HALIGNMENTS[4], SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.LEADING  };
	private final static TableStyle DEFAULT_TABLE_STYLE = new TableStyle().setAutoResizeMode(JTable.AUTO_RESIZE_OFF).setEnableDefaultSorter(true);

	public HCPClassicFileSystemModel(WorkingSpace workingSpace, FolderItem workingDirectory) {
		super(DEFAULT_TABLE_STYLE, columnTypes, columns, columnWidths, columnHAlignments, workingSpace, workingDirectory);
	}

//	@Override
//	public ItemModel cloneModel(FolderItem workingDirectory) {
//		return new HCPClassicFileSystemModel(workingDirectory);
//	}

	@Override
	protected RowDataVector createRowData(Item item) {
		RowDataVector row = super.createRowData(item, columnTypes.length);

		if (item.isDirectory()) {
//			row.addElement(new ItemTypeColumn(ItemType.Directory, null));
//			row.addElement(ItemSizeColumn.EMPTY);
//			row.addElement("");
			row.addElement("");
			row.addElement("");
			row.addElement("");
			row.addElement("");
			row.addElement("");
			row.addElement("");
			row.addElement("");
		} else {
			HCPObjectSummary summary = ((HCPFileItem)item).getSummary();
//			row.addElement(new ItemTypeColumn(item.getType(), item.getExt()));
//			row.addElement(new ItemSizeColumn(item.getSize()));
//			row.addElement(summary.getIngestTime() == null ? "" : SystemConfigUtils.formatDatetime(summary.getIngestTime()));
			row.addElement(summary.getRetentionString());
			row.addElement(StringUtils.isNotEmpty(summary.getDomain()) ? summary.getOwner() + "/" + summary.getDomain() : summary.getOwner());
			Annotation[] metas = summary.getCustomMetadatas();
			row.addElement((metas == null ? "0" : String.valueOf(metas.length)));
			row.addElement(summary.getDpl());
			row.addElement(summary.getIngestProtocol());
			row.addElement(summary.getVersionId());
			row.addElement(summary.getETag());
		}

		return row;
	}

}
