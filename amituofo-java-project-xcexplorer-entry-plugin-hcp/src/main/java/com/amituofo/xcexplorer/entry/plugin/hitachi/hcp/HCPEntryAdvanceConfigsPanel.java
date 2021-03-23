package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import javax.swing.JCheckBox;

import com.amituofo.common.ex.InvalidConfigException;
import com.amituofo.xcexplorer.core.ui.frame.EntryAdvanceConfigPanel;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.HCPFileSystemEntryConfig;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class HCPEntryAdvanceConfigsPanel extends EntryAdvanceConfigPanel<HCPFileSystemEntryConfig> {
	private JCheckBox configShowDeletedObjects;
	private JCheckBox configEnablePurgeDelete;

	public HCPEntryAdvanceConfigsPanel() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("346px"),},
			new RowSpec[] {
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				RowSpec.decode("max(2dlu;min)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));

		configShowDeletedObjects = new JCheckBox("Show deleted objects");
		configShowDeletedObjects.setEnabled(false);
		add(configShowDeletedObjects, "2, 2, fill, top");
		
		configEnablePurgeDelete = new JCheckBox("Enable purge on object deletion.");
		configEnablePurgeDelete.setEnabled(false);
		add(configEnablePurgeDelete, "2, 5");
	}

	@Override
	public void showConfig(HCPFileSystemEntryConfig setting) {
		configShowDeletedObjects.setSelected(setting.isShowDeletedObjects());
		configEnablePurgeDelete.setSelected(setting.isPurgeDeletionEnabled());
	}

	@Override
	public void clear() {
		configShowDeletedObjects.setSelected(false);
		configEnablePurgeDelete.setSelected(false);
	}

	@Override
	public void updateConfig(HCPFileSystemEntryConfig entryConfig) {
		entryConfig.setShowDeletedObjects(configShowDeletedObjects.isSelected());
		entryConfig.setEnablePurgeDeletion(configEnablePurgeDelete.isSelected());
	}

	@Override
	public void validateConfig() throws InvalidConfigException {
		// TODO Auto-generated method stub
		
	}

}
