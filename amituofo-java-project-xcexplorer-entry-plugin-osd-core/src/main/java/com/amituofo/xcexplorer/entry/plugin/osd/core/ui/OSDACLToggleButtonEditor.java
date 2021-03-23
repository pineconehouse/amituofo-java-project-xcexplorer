package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.amituofo.common.ui.swingexts.component.ToggleButton;
import com.amituofo.xcexplorer.entry.plugin.osd.core.OSDACLStatusCell;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileItem;

public class OSDACLToggleButtonEditor extends AbstractCellEditor implements TableCellEditor {

	private final ToggleButton buttonInner = new ToggleButton();
	private OSDACLStatusCell aclStatusCell;

	public OSDACLToggleButtonEditor() {
		buttonInner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (aclStatusCell != null) {
					boolean on = buttonInner.isSelected();
//					System.out.println("actionPerformed = " + ((Grant) aclStatusCell.getGrantModel()).getPermission() + " " + on);
					try {
						aclStatusCell.getOSDACLModel().updateObjectACL(aclStatusCell, on);
					} catch (Exception e1) {
						e1.printStackTrace();
						OSDFileItem object = aclStatusCell.getObject();
						UIUtils.openAndLogError("Unable to set object ACL ." + object.getPath(), e1);
					}
				}

				OSDACLToggleButtonEditor.this.stopCellEditing();
			}
		});
	}

	@Override
	public Object getCellEditorValue() {
		if (aclStatusCell != null) {
//			System.out.println("getCellEditorValue = " + ((Grant) aclStatusCell.getGrantModel()).getPermission() );
			aclStatusCell.setPermissionOn(buttonInner.isSelected());
			return aclStatusCell;
		}

//		System.out.println("getCellEditorValue = null" );
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value != null && value instanceof OSDACLStatusCell) {
			aclStatusCell = (OSDACLStatusCell) value;
//			System.out.println("getTableCellEditorComponent = " + ((Grant) aclStatusCell.getGrantModel()).getPermission() + " row=" + row + " col=" + column);
			buttonInner.setSelected(aclStatusCell.isPermissionOn());
		}

		return buttonInner;
	}

}