package com.amituofo.xcexplorer.entry.plugin.hitachi.hcpcs;

import javax.swing.JLabel;
import javax.swing.JSeparator;

import com.amituofo.common.ex.InvalidConfigException;
import com.amituofo.xcexplorer.core.ui.frame.EntryAdvanceConfigPanel;
import com.amituofo.xfs.plugin.fs.objectstorage.s3amazon.AmazonS3FileSystemEntryConfig;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class HCPCSEntryAdvanceConfigsPanel extends EntryAdvanceConfigPanel<AmazonS3FileSystemEntryConfig> {
//	private JCheckBox forceGlobalBucketAccess;
//	private JCheckBox listHiddenFiles;
//	private JCheckBox utf8Encoding;

	protected HCPCSEntryAdvanceConfigsPanel() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("346px:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				RowSpec.decode("max(2dlu;min)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("Client Mode");
		add(lblNewLabel, "2, 2");
		
		JSeparator separator = new JSeparator();
		add(separator, "1, 3, 2, 1");

//		forceGlobalBucketAccess = new JCheckBox("Enable force global bucket access.");
//		forceGlobalBucketAccess.setSelected(true);
//		add(forceGlobalBucketAccess, "2, 5, fill, top");
		
//		listHiddenFiles = new JCheckBox("");
//		listHiddenFiles.setEnabled(false);
//		add(listHiddenFiles, "2, 8");
//		
//		utf8Encoding = new JCheckBox("");
//		utf8Encoding.setEnabled(false);
//		add(utf8Encoding, "2, 10");
	}

	@Override
	public void showConfig(AmazonS3FileSystemEntryConfig setting) {
//		forceGlobalBucketAccess.setSelected(setting.isForceGlobalBucketAccess());
//		listHiddenFiles.setSelected(setting.isListHiddenFiles());
//		utf8Encoding.setSelected(setting.isEnableUTF8Support());
	}

	@Override
	public void clear() {
//		forceGlobalBucketAccess.setSelected(true);
//		listHiddenFiles.setSelected(true);
//		utf8Encoding.setSelected(true);
	}

	@Override
	public void updateConfig(AmazonS3FileSystemEntryConfig entryConfig) {
//		entryConfig.setForceGlobalBucketAccess(forceGlobalBucketAccess.isSelected());
//		entryConfig.setListHiddenFiles(listHiddenFiles.isSelected());
//		entryConfig.setControlEncoding(utf8Encoding.isSelected()?"utf-8":null);
//		entryConfig.setCharset(utf8Encoding.isSelected()?"utf-8":null);
//		entryConfig.setEnableUTF8Support(utf8Encoding.isSelected());
	}

	@Override
	public void validateConfig() throws InvalidConfigException {
		// TODO Auto-generated method stub
		
	}

}
