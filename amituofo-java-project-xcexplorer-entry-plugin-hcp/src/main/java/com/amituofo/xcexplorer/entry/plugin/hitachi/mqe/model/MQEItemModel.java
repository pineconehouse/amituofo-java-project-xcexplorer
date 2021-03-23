package com.amituofo.xcexplorer.entry.plugin.hitachi.mqe.model;

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
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.item.MQEFileItem;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.item.MQEFolderItem;
import com.amituofo.xfs.service.FolderItem;
import com.amituofo.xfs.service.Item;
import com.amituofo.xfs.service.ItemType;
import com.amituofo.xfs.util.ItemSizeColumn;
import com.amituofo.xfs.util.ItemTypeColumn;
import com.hitachivantara.hcp.query.define.Operation;
import com.hitachivantara.hcp.query.model.ObjectSummary;
import com.hitachivantara.hcp.standard.model.metadata.Annotation;

public class MQEItemModel extends RemoteItemModel { // AbstractTableModel
	private final static Class[] columnTypes = new Class[] { DataCell.class,
			String.class,
			ItemTypeColumn.class,
			ItemSizeColumn.class,
			String.class,
			String.class,
			String.class,
			String.class,
			Integer.class,
			String.class,
			String.class,
			String.class,
			String.class, };
	private final static String[] columns = new String[] { "Name",
			"Namespace",
			"Type",
			"Size",
			"Ingest Time",
			"Retention",
			"Owner",
			"Metas",
			"Dpl",
			"Operation",
			"Permissions",
			"Version Id",
			"Content Hash", };
	private final static int[] columnWidths = new int[] { 400, 120, 80, 100, 135, 110, 60, 40, 40, 100, 90, 120, 230, };
	private final static int[] columnHAlignments = new int[] { -1,
			SwingConstants.CENTER,
			-1,
			SwingConstants.RIGHT,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.LEADING };
	private final static TableStyle DEFAULT_TABLE_STYLE = new TableStyle().setAutoResizeMode(JTable.AUTO_RESIZE_OFF).setEnableDefaultSorter(false);

	public MQEItemModel(WorkingSpace workingSpace, MQEFolderItem workingDirectory) {
		super(DEFAULT_TABLE_STYLE, columnTypes, columns, columnWidths, columnHAlignments, workingSpace, workingDirectory);
	}

	@Override
	public int getDefaultSortColumn() {
		return -1;
	}

//	@Override
//	public ItemModel cloneModel(FolderItem workingDirectory) {
//		return new MQEItemModel((MQEFolderItem) workingDirectory);
//	}

	// public void refresh(FolderItem workingDirectory, final ItemHandler event) {
	// removeRows();
	//
	// this.workingDirectory = workingDirectory;
	//
	// list(new ItemHandler() {
	//
	// @Override
	// public HandleFeedback handle(Integer eventType, Item data) {
	// if (eventType == ItemEvent.ITEM_FOUND) {
	// add(data);
	// }
	// return event.handle(eventType, data);
	// }
	//
	// @Override
	// public void exceptionCaught(Item data, Throwable e) {
	// // TODO Auto-generated method stub
	// GlobalContext.getLogger().error("Failed to list folder " + data.getPath(), e);
	// }
	// });
	// }

	// @Override
	// public void list(final ObjectHandler<Integer, Item> event, Object... args) {
	// try {
	// ((MQEFolderItem) workingDirectory).queryAll(new QueryItemHandler<ObjectQueryResult>() {
	//
	// @Override
	// public void exceptionCaught(Item data, Throwable e) {
	// event.exceptionCaught(data, e);
	// }
	//
	// @Override
	// public HandleFeedback handle(Integer type, Item data) {
	// return event.handle(type, data);
	// }
	//
	// @Override
	// public boolean firstPageResult(ObjectQueryResult pageResult) {
	// int max = 10000 * 1;
	// int total = pageResult.getStatus().getTotalResults();
	// if (total > max) {
	// if (UIUtils.openInfoConfirm("Continue", total + " items found! It's may take times to loading all items, Continue?")) {
	// return true;
	// } else {
	// return false;
	// }
	//
	// }
	// return true;
	// }
	// });
	// } catch (InvalidResponseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	//
	// String additionmsg = "";
	// if(e.getStatusCode()== HCPResponseKey.SERVICE_UNAVAILABLE.code()) {
	// additionmsg="The MQE service may not enabled, Please contact with HCP administrator to enable [SEARCH] fucntion.";
	// }
	//
	// GlobalResource.getLogger().error("Unable to query object from HCP. " + additionmsg, e);
	// } catch (HSCException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// GlobalResource.getLogger().error("Unknown error occured when querying object from HCP.", e);
	// }
	// }

	@Override
	protected RowDataVector createRowData(Item item) {
		MQEFileItem hciItem = (MQEFileItem) item;
		ObjectSummary summary = (ObjectSummary) hciItem.getSummary();
		// STRIKETHROUGH
		// TODO
		Icon icon = GlobalIcons.getIcon(item);
		int style = ((summary.getOperation() == Operation.NOT_FOUND || summary.getOperation() == Operation.DELETED) ? Item.ITEM_STATUS_DELETED : 0);
		Object col1 = new DataCell(item.getPath(), icon, style, null);

		RowDataVector row = new RowDataVector(DEFAULT_COLUMNS.length);
		row.addElement(col1);
		row.addElement(summary.getNamespace());
		row.addElement(hciItem.getType() == ItemType.Directory ? new ItemTypeColumn(ItemType.Directory, null) : new ItemTypeColumn(item.getType(), item.getExt()));
		// row.addElement(hciItem.getType() == ItemType.Directory ? "" : FormatUtils.getPrintSize(hciItem.getSize()) + " ");
		row.addElement(new ItemSizeColumn(item.getSize()));
		row.addElement(summary.getIngestTime() == null ? "" : SystemConfigUtils.formatDatetime(summary.getIngestTime()));
		// row.addElement(summary.getAccessTime() == null ? "" : SystemConfigUtils.formatDatetime(summary.getAccessTime()));
		row.addElement(summary.getRetentionString());
		row.addElement(StringUtils.isNotEmpty(summary.getDomain()) ? summary.getOwner() + "/" + summary.getDomain() : summary.getOwner());
		Annotation[] metas = summary.getCustomMetadatas();
		row.addElement(hciItem.getType() == ItemType.Directory ? "" : (metas == null ? "0" : String.valueOf(metas.length)));
		row.addElement(summary.getDpl());
		row.addElement(summary.getOperation());
		row.addElement(summary.getPermissions());
		row.addElement(summary.getVersionId());
		row.addElement(summary.getContentHash());
		// System.out.println(item.getActualPath()+"\t"+summary.getIngestTime()+"\t"+summary.getAccessTime()+"\t"+summary.getUpdateTime());

		return row;
	}

	public void setQueryOffset(int offset) {
		((MQEFolderItem) workingDirectory).setOffset(offset);
	}

	public void setQueryPageSize(int pagesize) {
		((MQEFolderItem) workingDirectory).setPageSize(pagesize);
	}

}
