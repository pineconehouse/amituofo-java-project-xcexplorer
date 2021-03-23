package com.amituofo.xcexplorer.entry.plugin.osd.core.ui.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.amituofo.common.api.ObjectHandler;
import com.amituofo.common.define.HandleFeedback;
import com.amituofo.common.ui.lang.DataCell;
import com.amituofo.common.ui.lang.RowDataVector;
import com.amituofo.common.ui.swingexts.TableStyle;
import com.amituofo.common.ui.swingexts.model.BasicTableModel;
import com.amituofo.common.ui.swingexts.render.DataCellRenderer;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.entry.plugin.osd.core.OSDACLPermission;
import com.amituofo.xcexplorer.entry.plugin.osd.core.OSDACLStatusCell;
import com.amituofo.xcexplorer.entry.plugin.osd.core.OSDACLUserType;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDACLToggleButtonEditor;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDACLToggleButtonRenderer;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileItem;
import com.amituofo.xfs.service.ItemEvent;

public abstract class OSDACLModel<GRANT_MODEL> extends BasicTableModel<GRANT_MODEL> { // AbstractTableModel
	private final static Class[] columnTypes = new Class[] { DataCell.class,
			String.class,
			OSDACLStatusCell.class,
			OSDACLStatusCell.class,
			OSDACLStatusCell.class,
			OSDACLStatusCell.class,
			OSDACLStatusCell.class,
			OSDACLStatusCell.class };
	private final static String[] columns = new String[] { "Type", "User", "FULL CONTROL", "READ", "WRITE", "DELETE", "READ_ACL", "WRITE_ACL" };
	private final static int[] columnWidths = new int[] { 80, 200, 100, 100, 100, 100, 100, 100 };
	private final static int[] columnHAlignments = new int[] { SwingConstants.CENTER,
			SwingConstants.LEFT,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER,
			SwingConstants.CENTER };
	// public final static TableCellRenderer[] DEFAULT_COLUMN_CELL_RENDERERS = new TableCellRenderer[] { new DataCellRenderer(),
	// null,
	// new OSDACLToggleButtonRenderer(),
	// new OSDACLToggleButtonRenderer(),
	// new OSDACLToggleButtonRenderer(),
	// new OSDACLToggleButtonRenderer(),
	// new OSDACLToggleButtonRenderer(),
	// new OSDACLToggleButtonRenderer() };
	// public final static TableCellEditor[] DEFAULT_COLUMN_CELL_EDITORS = new TableCellEditor[] { null,
	// null,
	// new OSDACLToggleButtonEditor(),
	// new OSDACLToggleButtonEditor(),
	// new OSDACLToggleButtonEditor(),
	// new OSDACLToggleButtonEditor(),
	// new OSDACLToggleButtonEditor(),
	// new OSDACLToggleButtonEditor() };
	public final static TableStyle TABLE_STYLE = new TableStyle().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION).setAutoResizeMode(JTable.AUTO_RESIZE_OFF)
			.setEnableDefaultSorter(false);

	public static final int COL_FULL_CONTROL = 2;
	public static final int COL_DELETE = 5;

	protected OSDFileItem object;

	public final static OSDACLPermission[] ACL_PERMISSIONS = new OSDACLPermission[] { OSDACLPermission.FULL_CONTROL,
			OSDACLPermission.READ,
			OSDACLPermission.WRITE,
			OSDACLPermission.DELETE,
			OSDACLPermission.READ_ACL,
			OSDACLPermission.WRITE_ACL };

	public OSDACLModel() {
		super(TABLE_STYLE, columnTypes, columns, columnWidths, columnHAlignments, createTableCellRenderer(), createTableCellEditor());
	}

	private final Map<String, RowDataVector> aclUserMap = new HashMap<String, RowDataVector>();

	private static TableCellEditor[] createTableCellEditor() {
		TableCellEditor[] DEFAULT_COLUMN_CELL_EDITORS = new TableCellEditor[] { null,
				null,
				new OSDACLToggleButtonEditor(),
				new OSDACLToggleButtonEditor(),
				new OSDACLToggleButtonEditor(),
				new OSDACLToggleButtonEditor(),
				new OSDACLToggleButtonEditor(),
				new OSDACLToggleButtonEditor() };

		return DEFAULT_COLUMN_CELL_EDITORS;
	}

	private static TableCellRenderer[] createTableCellRenderer() {
		TableCellRenderer[] DEFAULT_COLUMN_CELL_RENDERERS = new TableCellRenderer[] { new DataCellRenderer(),
				null,
				new OSDACLToggleButtonRenderer(),
				new OSDACLToggleButtonRenderer(),
				new OSDACLToggleButtonRenderer(),
				new OSDACLToggleButtonRenderer(),
				new OSDACLToggleButtonRenderer(),
				new OSDACLToggleButtonRenderer() };
		return DEFAULT_COLUMN_CELL_RENDERERS;
	}

	public abstract void updateObjectACL(OSDACLStatusCell<GRANT_MODEL> aclStatusCell, boolean on) throws Exception;

	public abstract GRANT_MODEL[] newArray(int size);

	public OSDFileItem getWorkingObject() {
		return object;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return (column >= 2 && column <= 6);
	}

	public void listACL(OSDFileItem object) {
		removeRows();
		aclUserMap.clear();

		this.object = object;

		list(new ObjectHandler<Integer, GRANT_MODEL>() {

			@Override
			public HandleFeedback handle(Integer eventType, GRANT_MODEL grant) {

				if (eventType == ItemEvent.ITEM_FOUND) {
					if (!updateExist(grant)) {
						RowDataVector rowData = add(grant);
						aclUserMap.put(getGrantUserId(grant), rowData);
					}
				}
				return null;
			}

			@Override
			public void exceptionCaught(GRANT_MODEL data, Throwable e) {
			}
		});
	}

	protected boolean updateExist(GRANT_MODEL grant) {
		String uid = getGrantUserId(grant);
		RowDataVector row = aclUserMap.get(uid);
		if (row == null) {
			return false;
		}

		// 第三列full control 开始
		OSDACLPermission osdACLPermission = toOSDACLPermission(grant);
		int column;
		// 映射到table的相应列
		switch (osdACLPermission) {
			case FULL_CONTROL:
				column = 2;
				break;
			case READ:
				column = 3;
				break;
			case WRITE:
				column = 4;
				break;
			case DELETE:
				column = 5;
				break;
			case READ_ACL:
				column = 6;
				break;
			case WRITE_ACL:
				column = 7;
				break;
			default:
				return false;
		}

		// 设置这个列的权限为on
		OSDACLStatusCell<GRANT_MODEL> aclStatusCell = (OSDACLStatusCell<GRANT_MODEL>) row.get(column);
		aclStatusCell.setPermissionOn(true);
		
		return true;
	}

	// @Override
	// public boolean delete(HCPVersionFileItem item) throws ServiceException {
	// return item.delete(null);
	// }

	// @Override
	// public void list(ObjectHandler<Integer, ACL_MODEL> event, Object... args) {
	// if (object != null)
	// object.listACL(event);
	// }

	protected abstract OSDACLUserType getGrantUserType(GRANT_MODEL grant);

	protected abstract String getGrantUserId(GRANT_MODEL grant);
	
//	protected abstract GRANT_MODEL toGrantModel(OSDACLStatusCell<GRANT_MODEL> osdAclStatusCell);

	protected abstract boolean hasPermission(GRANT_MODEL grant, OSDACLPermission osdACLPermission);

	protected abstract OSDACLPermission toOSDACLPermission(GRANT_MODEL grant);

	@Override
	protected RowDataVector createRowData(GRANT_MODEL grant) {
		OSDACLUserType type = getGrantUserType(grant);
		String user = getGrantUserId(grant);
		Object col1 = new DataCell(type.name(), GlobalIcons.ICON_XXXX_16x16);

		RowDataVector row = new RowDataVector(columns.length);
		row.addElement(col1);
		row.addElement(getGrantUserId(grant));
		for (OSDACLPermission osdACLPermission : ACL_PERMISSIONS) {
			boolean permissionOn = hasPermission(grant, osdACLPermission);
			row.addElement(new OSDACLStatusCell<GRANT_MODEL>(osdACLPermission, this, object, type , user, permissionOn));
		}

		return row;
	}

	@Override
	public Comparator getComparator(int column) {
		return null;
	}

}
