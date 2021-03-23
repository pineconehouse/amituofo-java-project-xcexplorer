package com.amituofo.xcexplorer.entry.plugin.hitachi.mqe;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.amituofo.xcexplorer.core.ui.frame.EntryConfigPanel;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.MQEFileSystemEntryConfig;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class MQEEntryConfigsPanel extends EntryConfigPanel<MQEFileSystemEntryConfig> {

	private JTextField textName;
	private JTextPane textQueryScript;
	private MQEFileSystemEntryConfig setting;
	private JComboBox<String> comboxHCPEntry;

	public MQEEntryConfigsPanel() {
		super(true, new MQEEntryAdvanceConfigsPanel());

		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		textName = new JTextField();
		textName.setColumns(10);

		JLabel lblDomain = new JLabel("HCP Entry:");
		lblDomain.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNamespace = new JLabel("Query Type:");
		lblNamespace.setHorizontalAlignment(SwingConstants.RIGHT);

		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("117px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("280px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("68px:grow"),}));
		add(lblNewLabel, "2, 2, fill, fill");
		add(textName, "4, 2, fill, top");
		
		JSeparator separator = new JSeparator();
		add(separator, "1, 3, 5, 1, fill, center");
		add(lblDomain, "2, 4, right, fill");

		comboxHCPEntry = new JComboBox<String>();
		add(comboxHCPEntry, "4, 4, fill, fill");
		add(lblNamespace, "2, 6, right, fill");

		JComboBox cmbQueryType = new JComboBox();
		add(cmbQueryType, "4, 6, fill, fill");

		JSeparator separator_1 = new JSeparator();
		add(separator_1, "1, 8, 5, 1, fill, center");

		JLabel lblComment = new JLabel("Query Script:");
		lblComment.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblComment, "2, 9, fill, top");

		textQueryScript = new JTextPane();
		add(textQueryScript, "4, 9, fill, fill");
	}

	@Override
	protected MQEFileSystemEntryConfig getEntryConfig() {
		if (this.setting == null) {
			this.setting = new MQEFileSystemEntryConfig();
		}

		MQEFileSystemEntryConfig entryConfig = this.setting;

		entryConfig.setName(textName.getText());
//		entryConfig.setHCPEntryConfigName((String) comboxHCPEntry.getSelectedItem());

		entryConfig.setQueryExpression(textQueryScript.getText());

		super.updateAdvanceConfig(entryConfig);
		
		return entryConfig;
	}

	@Override
	public void showEntryConfig(MQEFileSystemEntryConfig setting) {
		this.setting = setting;

		textName.setText(setting.getName());
//		textDomain.setText(setting.getDomain());
//		textTenant.setText(setting.getTenant());
//		comboxHCPEntry.setSelectedItem(setting.getHCPEntryConfigName());

		textQueryScript.setText(setting.getDescription());

		super.showAdvanceConfig(setting);
	}

	@Override
	public void clear() {
		this.setting = null;

		textName.setText("");
//		textDomain.setText("");
//		textTenant.setText("");
//		comboxHCPEntry.setSelectedIndex(0);
		textQueryScript.setText("");

		super.clear();
	}
	
}
