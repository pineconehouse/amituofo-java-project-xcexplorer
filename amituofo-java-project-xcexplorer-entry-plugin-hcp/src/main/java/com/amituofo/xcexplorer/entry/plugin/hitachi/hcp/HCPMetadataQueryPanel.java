package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.amituofo.common.ui.action.ClearAction;
import com.amituofo.common.ui.swingexts.component.DefaultPanel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class HCPMetadataQueryPanel extends DefaultPanel implements ClearAction {
	private JTextArea tfMetadataContent;

	public HCPMetadataQueryPanel() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(82dlu;default):grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("14px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("max(47dlu;default):grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,}));

		JLabel lblMetadataContent = new JLabel("Metadata Contains:");
		add(lblMetadataContent, "1, 2, fill, top");

		tfMetadataContent = new JTextArea();
		add(tfMetadataContent, "1, 4, 3, 1, fill, fill");
	}

	@Override
	public void clear() {
		tfMetadataContent.setText("");
	}

	public String getMetaExpression() {

		// +(customMetadataContent:xxx)
		String meta = tfMetadataContent.getText();

		return meta;
	}
}
