package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.amituofo.common.ui.swingexts.component.CustomTable;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.ui.ItemFunctionBar;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.model.OSDACLModel;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileItem;

public class OSDAccessControlListFuncPanel extends OSDObjectFunctionPanel<OSDFileItem> {
	protected final CustomTable table;
	protected final OSDACLModel model;

	public OSDAccessControlListFuncPanel(OSDACLTable osdAclTable) {
		super("ACL", GlobalIcons.ICON_ACL_TAB_16x16);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		add(panel, BorderLayout.SOUTH);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panel.add(toolBar);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.setIcon(GlobalIcons.ICON_ADD_16x16);
		toolBar.add(btnNewButton);
		btnNewButton.setEnabled(false);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		table = osdAclTable;
		model = (OSDACLModel) table.getModel();
		scrollPane.setViewportView(table);
	}

	@Override
	protected void clear() {
		model.removeRows();
	}

	@Override
	protected void showObjectInfo(OSDFileItem item) {
		model.listACL(item);
	}

	@Override
	public ItemFunctionBar getFunctionBar() {
		return null;
	}

}
