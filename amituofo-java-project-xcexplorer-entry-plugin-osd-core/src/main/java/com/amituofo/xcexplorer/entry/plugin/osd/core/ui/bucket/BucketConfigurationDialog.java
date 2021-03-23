package com.amituofo.xcexplorer.entry.plugin.osd.core.ui.bucket;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import com.amituofo.common.ui.swingexts.component.SimpleDialogContentPanel;
import com.amituofo.common.ui.swingexts.component.TabbedPanel;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDBucketspace;

public class BucketConfigurationDialog<T extends OSDBucketspace> extends SimpleDialogContentPanel {
	// private final BucketConfigurationPanel[] bucketconfpanels = new BucketConfigurationPanel[] { new VersioningConfigurationPanel(),
	// new LoggingConfigurationPanel(),
	// new AccelerateConfigurationPanel(),
	// new PolicyConfigurationPanel() };

	private final BucketConfigurationPanel<T>[] bucketconfpanels;
	private TabbedPanel tabbedPane;

	public BucketConfigurationDialog(T s3DefaultBucketspace, BucketConfigurationPanel<T>[] bucketconfpanels) {
		setLayout(new BorderLayout(0, 0));

		tabbedPane = new TabbedPanel(JTabbedPane.RIGHT);
		add(tabbedPane, BorderLayout.CENTER);

		this.bucketconfpanels = bucketconfpanels;// getBucketConfigurationPanels();
		if (bucketconfpanels != null && bucketconfpanels.length > 0) {
			for (BucketConfigurationPanel bucketConfigurationPanel : bucketconfpanels) {
				tabbedPane.addTab(bucketConfigurationPanel, bucketConfigurationPanel.getTitleIcon());
				bucketConfigurationPanel.setBucketspace(s3DefaultBucketspace);
			}
		}

		tabbedPane.lazyInitialize();
		BucketConfigurationPanel bucketConf = ((BucketConfigurationPanel) tabbedPane.getActiveTabPanel());
		bucketConf.showConfiguration();
		bucketConf.configurationLoaded = true;
	}

	// protected abstract BucketConfigurationPanel[] getBucketConfigurationPanels();

	public void switchTo(String title) {
		tabbedPane.switchTab(title);
	}

	@Override
	public boolean okPressed() {
		if (bucketconfpanels != null && bucketconfpanels.length > 0) {
			for (BucketConfigurationPanel bucketConfigurationPanel : bucketconfpanels) {
				if (bucketConfigurationPanel.isConfigurationModified()) {
					try {
						if (!bucketConfigurationPanel.applyConfiguration()) {
							return false;
						}
					} catch (Exception e) {
						// e.printStackTrace();
						UIUtils.openAndLogError("Unknown error when apply configuration at " + bucketConfigurationPanel.getTitle() + "!", e);
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean cancelPressed() {
		return true;
	}

	@Override
	public String getTitle() {
		return "Bucket Configuration";
	}

	@Override
	public ImageIcon getIcon() {
		return GlobalIcons.ICON_PREFERENCE_16x16;
	}

}
