package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.common.util.FormatUtils;
import com.amituofo.common.util.StreamUtils;
import com.amituofo.common.util.StringUtils;
import com.amituofo.xcexplorer.core.global.GlobalAction;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.global.GlobalResource;
import com.amituofo.xcexplorer.core.global.SystemConfig;
import com.amituofo.xcexplorer.core.global.SystemConfigKeys;
import com.amituofo.xcexplorer.core.lang.ItemPackage;
import com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model.HCPObjectMetadataListModel;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.OSDObjectFunctionPanel;
import com.amituofo.xcexplorer.task.util.TaskUtils;
import com.amituofo.xcexplorer.util.ItemUtils;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPFileItem;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPMetadataItem;
import com.hitachivantara.hcp.standard.model.metadata.HCPMetadataSummary;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class HCPCustomMetadataFuncPanel extends OSDObjectFunctionPanel<HCPFileItem> {
	private HCPObjectMetadataListModel model;

	private JTextField textField_Hash;
	private JTextField textField_HashAlgo;
	private JTextField textField_ContentType;
	private JTextField textField_ChangeTime;
	private JTextField textField_Size;

	private HCPMetadataItem currentMeta = null;
	private byte[] currentMetaContentInBytes = null;
	private JComboBox<HCPMetadataItem> list;

	private HCPCustomMetadataPanel hcpCustomMetaPanel;

	private JToolBar toolBar;

	public HCPCustomMetadataFuncPanel() {
		super("Metadata", GlobalIcons.ICON_META_TAB_16x16);
		model = new HCPObjectMetadataListModel();

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.25);
		add(splitPane, BorderLayout.CENTER);

		JPanel panel_4 = new JPanel();
		splitPane.setLeftComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_4.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.PREF_COLSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
						ColumnSpec.decode("86px:grow"),
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.PREF_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("141px:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("top:14px"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.PREF_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.PREF_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.PREF_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.PREF_ROWSPEC, }));

		JLabel lblNewLabel_3 = new JLabel("Metadata:");
		panel_3.add(lblNewLabel_3, "2, 2, fill, top");

		list = new JComboBox<HCPMetadataItem>();
		panel_3.add(list, "2, 4, 7, 1, fill, fill");
		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HCPMetadataItem meta = (HCPMetadataItem) list.getSelectedItem();
				if (meta != null) {
					showContent(meta);
				}
			}
		});
		list.setModel(model);

		JLabel lblNewLabel_7 = new JLabel("Size:");
		panel_3.add(lblNewLabel_7, "2, 6, right, center");

		textField_Size = new JTextField();
		textField_Size.setEditable(false);
		panel_3.add(textField_Size, "4, 6, fill, fill");
		textField_Size.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("Changed Time:");
		panel_3.add(lblNewLabel_6, "6, 6, right, center");

		textField_ChangeTime = new JTextField();
		textField_ChangeTime.setEditable(false);
		panel_3.add(textField_ChangeTime, "8, 6, fill, fill");
		textField_ChangeTime.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Content Type:");
		panel_3.add(lblNewLabel_4, "2, 8, left, center");

		textField_ContentType = new JTextField();
		textField_ContentType.setEditable(false);
		panel_3.add(textField_ContentType, "4, 8, fill, fill");
		textField_ContentType.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Hash Algorithm:");
		panel_3.add(lblNewLabel_2, "6, 8, left, center");

		textField_HashAlgo = new JTextField();
		textField_HashAlgo.setEditable(false);
		panel_3.add(textField_HashAlgo, "8, 8, fill, fill");
		textField_HashAlgo.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Hash:");
		panel_3.add(lblNewLabel_1, "2, 10, right, center");

		textField_Hash = new JTextField();
		textField_Hash.setEditable(false);
		panel_3.add(textField_Hash, "4, 10, 5, 1, fill, fill");
		textField_Hash.setColumns(10);

		JPanel panel_7 = new JPanel();
		panel_4.add(panel_7, BorderLayout.SOUTH);
		panel_7.setLayout(new BorderLayout(0, 0));

		toolBar = new JToolBar();
		panel_7.add(toolBar);
		toolBar.setFloatable(false);
		JButton btnNew = new JButton("New");
		btnNew.setIcon(GlobalIcons.ICON_ADD_16x16);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getWorkingItem() != null) {
					String metaName = GlobalAction.standard().inputMetadataName("");
					if (StringUtils.isNotEmpty(metaName)) {
						HCPMetadataItem newMeta = getWorkingItem().createMetadata(metaName);
						model.addElement(newMeta);
						list.setSelectedIndex(model.getSize() - 1);
						hcpCustomMetaPanel.setEnabled(true);
						// metaContentText.setText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
						hcpCustomMetaPanel.showMetadata(metaName, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
					}
				}
			}
		});
		toolBar.add(btnNew);
		// components.add(btnNew);

		JButton btnNewButton_1 = new JButton("Download");
		btnNewButton_1.setIcon(GlobalIcons.ICON_DOWNLOAD_16x16);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final HCPMetadataItem metaItem = (HCPMetadataItem) list.getSelectedItem();
				if (metaItem != null)
					TaskUtils.confirmExecDownload2SingleFileTask(metaItem);
			}
		});
		toolBar.add(btnNewButton_1);
		// components.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.setIcon(GlobalIcons.ICON_DELETE_FILE_16x16);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getWorkingItem() != null) {
					final HCPMetadataItem metaItem = (HCPMetadataItem) list.getSelectedItem();

					if (metaItem != null && UIUtils.openInfoConfirm("Are you sure to delete selected meta [" + metaItem.getName() + "] ?")) {
						if (metaItem != null) {
							try {
								metaItem.delete();
							} catch (Exception e1) {
								UIUtils.openAndLogError("Failed to delete metadata " + metaItem.getName(), e1);
							}

							refresh();
						}
					}
				}
			}
		});
		toolBar.add(btnNewButton_2);
		// components.add(btnNewButton_2);

		hcpCustomMetaPanel = new HCPCustomMetadataPanel(false, true, true);
		splitPane.setRightComponent(hcpCustomMetaPanel);

	}

	private void showContent(HCPMetadataItem meta) {
		this.currentMeta = meta;

		ItemPackage pkg = ItemUtils.toItemPackage(meta);
		hcpCustomMetaPanel.setItemPackage(pkg);

		HCPMetadataSummary metaSummary = meta.getMetadataSummary();
		this.textField_Size.setText(metaSummary.getSize() != null ? FormatUtils.getPrintSize(metaSummary.getSize(), true) : "");
		this.textField_ChangeTime.setText(metaSummary.getChangeTime() != null ? SystemConfig.getInstance().getDateFormat(SystemConfigKeys.INTERFACE_DATETIME_FORMAT)
				.format(new Date(metaSummary.getChangeTime())) : "");
		this.textField_ContentType.setText(metaSummary.getContentType());
		this.textField_HashAlgo.setText(metaSummary.getHashAlgorithmName());
		this.textField_Hash.setText(metaSummary.getContentHash());

		if (metaSummary.getSize() != null) {
			final long SIZE_30MB = 1024 * 1024 * 10;
			if (metaSummary.getSize() <= SIZE_30MB) {
				try {
					InputStream in = meta.getContent();
					this.currentMetaContentInBytes = StreamUtils.inputStreamToBytes(in, true);
					// this.metaContentText.setText(new String(currentMetaContentInBytes, Charset.forName((String) encodeCombox.getSelectedItem())));
					// this.metaContentText.setCaretPosition(0);

					hcpCustomMetaPanel.showMetadata(meta.getName(), currentMetaContentInBytes);

				} catch (Exception e) {
					// e.printStackTrace();
					UIUtils.openAndLogError("Failed when loding metadata.", e);
					// GlobalContext.getLogger().error("Failed when loding metadata " + meta.getName(), e);
				}
			} else {
				GlobalResource.getLogger().warn("Metadata size too large for showing content. Please download to view. " + meta.getName());
				hcpCustomMetaPanel.showMetadata(meta.getName(), "Metadata size too large for showing content. Please download to view.");
				// metaContentText.setText("Metadata size too large for showing content. Please download to view.");
			}
		}
	}

	public void clear() {
		this.textField_Size.setText("");
		this.textField_ChangeTime.setText("");
		this.textField_ContentType.setText("");
		this.textField_HashAlgo.setText("");
		this.textField_Hash.setText("");
		this.currentMeta = null;
		this.currentMetaContentInBytes = null;
		this.model.removeAllElements();
		this.setEnabled(false);

		hcpCustomMetaPanel.clear();
		hcpCustomMetaPanel.setEnabled(false);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		Component[] components = toolBar.getComponents();
		for (Component jComponent : components) {
			jComponent.setEnabled(enabled);
		}
	}

	@Override
	protected void showObjectInfo(HCPFileItem item) {
		try {
			clear();

			if (item.getStatus() == HCPFileItem.ITEM_STATUS_DELETED) {
				hcpCustomMetaPanel.setEnabled(false);
				return;
			}

			int count = model.listMetadata(item);

			this.setEnabled(true);
			hcpCustomMetaPanel.setEnabled(count > 0);

			if (count == 0) {
				hcpCustomMetaPanel.showMetadata("", "!NO METADATA FOUND!");
				// metaContentText.setText("!NO METADATA FOUND!");
			}
		} catch (ServiceException e) {
			GlobalResource.getLogger().error("Error when trying to get metadata from " + item.getPath(), e);
		}
	}

}
