package com.amituofo.xcexplorer.entry.plugin.hitachi.mqe;

import com.amituofo.common.ex.InvalidConfigException;
import com.amituofo.xcexplorer.core.ui.frame.EntryAdvanceConfigPanel;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.MQEFileSystemEntryConfig;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class MQEEntryAdvanceConfigsPanel extends EntryAdvanceConfigPanel<MQEFileSystemEntryConfig> {

	public MQEEntryAdvanceConfigsPanel() {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("346px"), },
				new RowSpec[] { FormSpecs.PARAGRAPH_GAP_ROWSPEC,
						RowSpec.decode("20px"),
						RowSpec.decode("max(2dlu;min)"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));
	}

	@Override
	public void showConfig(MQEFileSystemEntryConfig setting) {
	}

	@Override
	public void clear() {
	}

	@Override
	public void updateConfig(MQEFileSystemEntryConfig entryConfig) {
	}

	@Override
	public void validateConfig() throws InvalidConfigException {
		// TODO Auto-generated method stub

	}

}
