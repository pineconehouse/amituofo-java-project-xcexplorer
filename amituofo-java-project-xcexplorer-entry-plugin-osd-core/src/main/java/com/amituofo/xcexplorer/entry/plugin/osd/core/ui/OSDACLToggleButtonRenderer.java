package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.amituofo.common.ui.swingexts.component.ToggleButton;
import com.amituofo.xcexplorer.entry.plugin.osd.core.OSDACLStatusCell;

public class OSDACLToggleButtonRenderer implements TableCellRenderer {
	private final ToggleButton buttonInner = new ToggleButton();

	public OSDACLToggleButtonRenderer() {
		super();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value != null && value instanceof OSDACLStatusCell) {
			buttonInner.setSelected(((OSDACLStatusCell) value).isPermissionOn());
		}

		return buttonInner;
	}

}