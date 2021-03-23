package com.amituofo.xcexplorer.entry.plugin.osd.core.ui.bucket;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.amituofo.common.ex.InvalidParameterException;
import com.amituofo.common.ui.swingexts.component.TabPanel;
import com.amituofo.xcexplorer.core.global.GlobalResource;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDBucketspace;

public abstract class BucketConfigurationPanel<T extends OSDBucketspace> extends TabPanel {
	protected boolean configurationLoaded = false;
	protected boolean isConfigurationModified = false;
	protected T bucketspace;
	protected String bucketName;

	public BucketConfigurationPanel() {

		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("");

		ImageIcon icon = getConfigurationIcon();
		if (icon == null) {
			icon = new ImageIcon(BucketConfigurationPanel.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png"));
		}
		lblNewLabel.setIcon(icon);
		panel.add(lblNewLabel, BorderLayout.WEST);

		// JTextArea textArea = new JTextArea();
		JTextArea textArea = new JTextArea(2, 20);
		textArea.setText(getDescription());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.setBackground(UIManager.getColor("Label.background"));
		textArea.setFont(UIManager.getFont("Label.font"));
		textArea.setBorder(UIManager.getBorder("Label.border"));
		panel.add(textArea, BorderLayout.CENTER);

		JSeparator separator = new JSeparator();
		panel.add(separator, BorderLayout.SOUTH);
	}

	public void setBucketspace(T bucketspace) {
		this.bucketspace = bucketspace;
		this.bucketName = bucketspace.getName();
	}

	public T getBucketspace() {
		return bucketspace;
	}

	@Override
	public void activing() {
		super.activing();
		if (!configurationLoaded) {
			UIUtils.executeWaitingAction(new Runnable() {
				@Override
				public void run() {
					try {
						showConfiguration();
						configurationLoaded = true;
					} catch (Exception e) {
						e.printStackTrace();
						GlobalResource.getLogger().error("Failed to get " + getTitle() + " configuration. " + e.getMessage(), e);
					}
				}
			});
		}
	}

	public boolean isConfigurationLoaded() {
		return configurationLoaded;
	}

	protected void setConfigurationModified(boolean isConfigurationModified) {
		this.isConfigurationModified = isConfigurationModified;
	}

	protected abstract String getDescription();

	protected abstract ImageIcon getConfigurationIcon();

	public abstract boolean applyConfiguration();

	public abstract void validateConfiguration() throws InvalidParameterException;

	public abstract boolean showConfiguration();

	public abstract Icon getTitleIcon();

	public boolean isConfigurationModified() {
		return isConfigurationLoaded() && isConfigurationModified;
	}

}
