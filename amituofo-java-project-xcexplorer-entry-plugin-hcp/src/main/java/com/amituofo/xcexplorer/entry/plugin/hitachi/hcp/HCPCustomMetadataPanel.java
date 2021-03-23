package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.amituofo.common.kit.value.KeyValue;
import com.amituofo.common.ui.listener.DataSavingListener;
import com.amituofo.common.ui.swingexts.component.DefaultPanel;
import com.amituofo.common.ui.swingexts.component.KeyValuePanel;
import com.amituofo.common.ui.swingexts.component.XMLEditPanel;
import com.amituofo.common.ui.swingexts.model.KeyValueTableModel;
import com.amituofo.common.util.StringUtils;
import com.amituofo.task.TaskDetail;
import com.amituofo.task.TaskManagement;
import com.amituofo.task.TaskRuntimeStatusListener;
import com.amituofo.task.TaskStatus;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.global.GlobalResource;
import com.amituofo.xcexplorer.core.lang.ItemPackage;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model.HCPCompatibleS3MetadataTableModel;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.HCPTaskFactory;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.util.ItemUtils;
import com.hitachivantara.hcp.standard.model.converter.DefaultMetadataHandler;
import com.hitachivantara.hcp.standard.model.metadata.S3CompatibleMetadata;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class HCPCustomMetadataPanel extends DefaultPanel {
	private XMLEditPanel metaContentPanel;
	private KeyValuePanel<String> s3MetadataPanel;
	private JTextField filePathTextField;

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final static SAXParserFactory factory = SAXParserFactory.newInstance();

	private String metadataName;
	private Object metadataContent;

	public static enum Meta {
		textMeta("XML/Text Metadata"), s3CompatibleMeta("S3 Compatible Metadata"), fileMeta("File Metadata");

		private String title;

		// private JRadioButton radioButton;

		Meta(String title) {
			this.title = title;
			// radioButton = new JRadioButton(title);
		}

		// public JRadioButton getRadioButton() {
		// return radioButton;
		// }

		// public void select() {
		// radioButton.setSelected(true);
		// }
	}

	private Meta current = Meta.textMeta;

	private ItemPackage itemPackage;
	private JPanel layeredPane;
	private JRadioButton rdbtnTextMetadata;
	private JRadioButton rdbtnS3CompatibleMetadata;
	private JRadioButton rdbtnFileMetadata;
	private JPanel panel;
	private JToolBar toolBar;

	public HCPCustomMetadataPanel(boolean showXMLEditorNameField, boolean showXMLEditorSaveButton, boolean showsS3CompatibleSaveButton) {
		setLayout(new BorderLayout(0, 0));

		HCPCompatibleS3MetadataTableModel model = new HCPCompatibleS3MetadataTableModel();

		layeredPane = new JPanel();
		layeredPane.setLayout(new CardLayout(0, 0));
		add(layeredPane, BorderLayout.CENTER);

		metaContentPanel = new XMLEditPanel(showXMLEditorNameField, showXMLEditorSaveButton);
		layeredPane.add(metaContentPanel, Meta.textMeta.name());
		metaContentPanel.setDataSavingListener(new DataSavingListener<String, byte[]>() {

			@Override
			public boolean savingData(String meta, byte[] data) {
				String metadataname = meta;
				byte[] metadata = data;

				if (StringUtils.isEmpty(metadataname)) {
					UIUtils.openError("Metadata name must be specificed!");
					return false;
				}

				if (metadata == null || metadata.length == 0) {
					UIUtils.openError("Metadata content must be specificed!");
					return false;
				}

				String msg = "";
				if (ItemUtils.containsFolderItem(itemPackage.getItems())) {
					msg = " include objects in subfolder!";
				}

				if (UIUtils.openInfoConfirm("Metadata ["
								+ metadataname
								+ "] will be update to each objects"
								+ msg
								+ ", Metadata content will be OVERWRITE if object has metadata with same name, continue?")) {

					TaskDetail copytask;
					copytask = GlobalResource.getTaskFactory(HCPTaskFactory.class).createHCPObjectSetMetadataTask(itemPackage, metadataname, metadata);

					TaskManagement taskMgr = GlobalResource.getTaskmanagement();
					taskMgr.add(copytask);
				} else {
					return false;
				}
				return true;
			}
		});

		s3MetadataPanel = new KeyValuePanel<String>(model, showsS3CompatibleSaveButton);
		s3MetadataPanel.setDataSavingListener(new DataSavingListener<Void, List<KeyValue<String>>>() {

			@Override
			public boolean savingData(Void meta, List<KeyValue<String>> keyvalues) {
				if (keyvalues.size() == 0) {
					UIUtils.openError("Key/Value must be specificed!");
					return false;
				}

				String msg = "";
				if (ItemUtils.containsFolderItem(itemPackage.getItems())) {
					msg = " include objects in subfolder!";
				}

				if (UIUtils.openInfoConfirm("Metadata will be update to each objects" + msg + ", continue?")) {
					S3CompatibleMetadata s3CompatibleMetadata = new S3CompatibleMetadata();

					for (KeyValue<String> keyValue : keyvalues) {
						s3CompatibleMetadata.put(keyValue.getKey(), keyValue.getValue());
					}

					TaskDetail copytask;
					copytask = GlobalResource.getTaskFactory(HCPTaskFactory.class).createHCPObjectSetMetadataTask(itemPackage, s3CompatibleMetadata);

					TaskManagement taskMgr = GlobalResource.getTaskmanagement();
					taskMgr.add(copytask);
					return true;
				} else {
					return false;
				}
			}
		});
		layeredPane.add(s3MetadataPanel, Meta.s3CompatibleMeta.name());

		JPanel fileUploadPanel = new JPanel();
		layeredPane.add(fileUploadPanel, Meta.fileMeta.name());
		fileUploadPanel.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		fileUploadPanel.add(panel);
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblNewLabel = new JLabel("File:");
		panel.add(lblNewLabel, "2, 2, fill, fill");

		filePathTextField = new JTextField();
		panel.add(filePathTextField, "4, 2, fill, fill");
		filePathTextField.setEnabled(false);
		filePathTextField.setColumns(10);

		JButton btnSelect = new JButton("Select");
		panel.add(btnSelect, "6, 2, fill, fill");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		fileUploadPanel.add(toolBar, BorderLayout.SOUTH);

		JButton btnUpload = new JButton("Upload");
		btnUpload.setIcon(GlobalIcons.ICON_UPLOAD_16x16);
		toolBar.add(btnUpload);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Metadata Format", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_1, BorderLayout.NORTH);

		rdbtnTextMetadata = new JRadioButton(Meta.textMeta.title);
		rdbtnTextMetadata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchMetaPanel(Meta.textMeta, false);
			}
		});
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));
		rdbtnTextMetadata.setSelected(true);
		buttonGroup.add(rdbtnTextMetadata);
		panel_1.add(rdbtnTextMetadata);

		rdbtnS3CompatibleMetadata = new JRadioButton(Meta.s3CompatibleMeta.title);
		rdbtnS3CompatibleMetadata.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnS3CompatibleMetadata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchMetaPanel(Meta.s3CompatibleMeta, false);
			}
		});
		buttonGroup.add(rdbtnS3CompatibleMetadata);
		panel_1.add(rdbtnS3CompatibleMetadata);

		rdbtnFileMetadata = new JRadioButton(Meta.fileMeta.title);
		rdbtnFileMetadata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchMetaPanel(Meta.fileMeta, false);
			}
		});
		buttonGroup.add(rdbtnFileMetadata);
		panel_1.add(rdbtnFileMetadata);
	}

	public void setItemPackage(ItemPackage itemPackage) {
		this.itemPackage = itemPackage;
	}

	public boolean save() {
		if (current == Meta.textMeta) {
			return metaContentPanel.save();
		} else if (current == Meta.s3CompatibleMeta) {
			return s3MetadataPanel.save();
		} else {
			filePathTextField.getText();
		}

		return true;
	}

	private void switchMetaPanel(Meta meta, boolean selectRadio) {
		current = meta;

		if (selectRadio) {
			if (meta == Meta.textMeta) {
				rdbtnTextMetadata.setSelected(true);
			} else if (meta == Meta.s3CompatibleMeta && S3CompatibleMetadata.DEFAULT_METADATA_NAME.equalsIgnoreCase(metadataName)) {
				rdbtnS3CompatibleMetadata.setSelected(true);
			} else {
				rdbtnFileMetadata.setSelected(true);
			}
		}
		((CardLayout) layeredPane.getLayout()).show(layeredPane, current.name());

		showMetadataContent(meta);
	}

	public void showMetadata(String name, String value) {
		this.metadataName = name;
		this.metadataContent = value;

		Meta meta;
		if (S3CompatibleMetadata.DEFAULT_METADATA_NAME.equalsIgnoreCase(metadataName)) {
			meta = Meta.s3CompatibleMeta;
		} else {
			meta = Meta.textMeta;
		}

		switchMetaPanel(meta, true);
	}

	public void showMetadata(String name, byte[] value) {
		this.metadataName = name;
		this.metadataContent = value;

		Meta meta;
		if (S3CompatibleMetadata.DEFAULT_METADATA_NAME.equalsIgnoreCase(metadataName)) {
			meta = Meta.s3CompatibleMeta;
		} else {
			meta = Meta.textMeta;
		}

		switchMetaPanel(meta, true);
	}

	private boolean isSuitableForDisplay(int length) {
		final long SIZE_30MB = 1024 * 1024 * 10;
		if (length > SIZE_30MB) {
			GlobalResource.getLogger().warn("Metadata size too large for showing content. Please download to view. " + metadataName);
			metadataContent = "Metadata size too large for showing content. Please download to view.";
			return false;
		}

		return true;
	}

	private void showMetadataContent(Meta meta) {
		if (metadataContent == null) {
			return;
		}

		if (meta == Meta.textMeta) {
			metaContentPanel.setName(metadataName);
			metaContentPanel.setXMLContent(metadataContent);
		} else if (meta == Meta.s3CompatibleMeta && S3CompatibleMetadata.DEFAULT_METADATA_NAME.equalsIgnoreCase(metadataName)) {
			if (metadataContent != null) {
				InputStream metain = null;
				if (metadataContent instanceof String) {
					metain = new ByteArrayInputStream(metadataContent.toString().getBytes());
				} else if (metadataContent instanceof byte[]) {
					metain = new ByteArrayInputStream((byte[]) metadataContent);
				}

				KeyValueTableModel<String> model = s3MetadataPanel.getKeyValueModel();
				s3MetadataPanel.clear();

				try {
					DefaultMetadataHandler handler = new DefaultMetadataHandler();
					SAXParser parser = factory.newSAXParser();
					parser.parse(metain, handler);
					S3CompatibleMetadata kvm = handler.getMetadata();
					Set<String> keyset = kvm.keySet();
					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {
							for (String key : keyset) {
								model.add(new KeyValue<String>(key, kvm.get(key)));
							}
						}
					});
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			// Do Nothong
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		metaContentPanel.setEnabled(enabled);
		s3MetadataPanel.setEnabled(enabled);
		toolBar.setEnabled(enabled);

		rdbtnTextMetadata.setEnabled(enabled);
		rdbtnS3CompatibleMetadata.setEnabled(enabled);
		rdbtnFileMetadata.setEnabled(enabled);
	}

	public void clear() {
		metadataName = null;
		metadataContent = null;
		metaContentPanel.clear();
		s3MetadataPanel.clear();
		filePathTextField.setText("");
	}
}
