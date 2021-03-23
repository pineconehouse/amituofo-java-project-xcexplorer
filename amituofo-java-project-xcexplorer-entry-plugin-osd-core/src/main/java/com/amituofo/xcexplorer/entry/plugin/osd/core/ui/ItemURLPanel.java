package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import com.amituofo.common.ui.swingexts.component.DefaultPanel;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileItem;
import com.amituofo.xfs.service.FileItem;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class ItemURLPanel extends DefaultPanel {
	private JTextField textField;
	private FileItem fileitem;
	private JButton btnRefresh;

	public ItemURLPanel() {
		setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.PREF_COLSPEC, ColumnSpec.decode("207px:grow"), FormSpecs.PREF_COLSPEC, }, new RowSpec[] { RowSpec.decode("33px"), }));

		JLabel lblNewLabel = new JLabel("Key:");
		add(lblNewLabel, "1, 1, left, fill");

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(UIManager.getColor("TextField.background"));
		add(textField, "2, 1, fill, center");
		textField.setColumns(10);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, "3, 1, left, fill");

		btnRefresh = new JButton("Refresh");
		btnRefresh.setIcon(GlobalIcons.ICON_XXXX_16x16);
		toolBar.add(btnRefresh);
	}

	public void addRefreshActionListener(ActionListener listener) {
		btnRefresh.addActionListener(listener);
	}

	public void setFileItem(FileItem fileitem) {
		this.fileitem = fileitem;

		showUrl();
	}

	private void showUrl() {
		if (fileitem == null) {
			textField.setText("");
		} else {
			String url;
			if (fileitem instanceof OSDFileItem) {
				url = ((OSDFileItem) fileitem).getURL().toString();
			} else {
				url = fileitem.getPath();
			}

			textField.setText(url);
		}
	}

	public void clear() {
		setFileItem(null);
	}

}
