package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.common.ui.swingexts.component.TabPanel;
import com.amituofo.common.util.StringUtils;
import com.amituofo.task.TaskManagement;
import com.amituofo.task.TaskRuntimeStatusListener;
import com.amituofo.task.TaskStatus;
import com.amituofo.xcexplorer.core.global.GlobalResource;
import com.amituofo.xcexplorer.core.global.GlobalUI;
import com.amituofo.xcexplorer.core.lang.ItemPackage;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.HCPTaskFactory;
import com.amituofo.xcexplorer.util.ItemUtils;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPFileItem;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPItemBase;
import com.amituofo.xfs.service.Item;
import com.hitachivantara.hcp.common.ex.InvalidResponseException;
import com.hitachivantara.hcp.standard.api.HCPNamespace;
import com.hitachivantara.hcp.standard.model.HCPObjectSummary;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class HCPSystemMetadataPanel extends TabPanel {
	private JTextField textFieldOwner;
	private JTextField textFieldRetention;
	private JTextField textFieldItems;
	private JCheckBox chckbxUpdateIndex;
	private JCheckBox chckbxUpdateHold;
	private JCheckBox chckbxUpdateShred;
	private final List<Item> hcpItems = new ArrayList<Item>();
	private JComboBox comboBoxIndex;
	private JComboBox comboBoxShred;
	private JComboBox comboBoxHold;
	private JLabel lblIndex;
	private JLabel lblHold;
	private JLabel lblShred;
	private JSeparator separator;
	private JCheckBox chckbxUpdateOwner;
	private JSeparator separator_1;
	private JCheckBox chckbxUpdateRetention;
	private JSeparator separator_2;
	private JComboBox comboBoxRetention;
	private JScrollPane scrollPane;
	private JTextArea txtrRetentionSettingThat;

	public HCPSystemMetadataPanel() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(43dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(72dlu;pref):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(49dlu;pref)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(12dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:44dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(136dlu;default):grow"),
				ColumnSpec.decode("right:4dlu"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(128dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,}));

		JLabel lblItems = new JLabel("Items:");
		add(lblItems, "2, 2, right, default");

		textFieldItems = new JTextField();
		textFieldItems.setEditable(false);
		add(textFieldItems, "4, 2, 9, 1, fill, default");
		textFieldItems.setColumns(10);

		separator = new JSeparator();
		add(separator, "2, 4, 11, 1");

		lblIndex = new JLabel("Index:");
		add(lblIndex, "2, 6, right, default");

		chckbxUpdateIndex = new JCheckBox("Update Index");
		chckbxUpdateIndex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxIndex.setEnabled(chckbxUpdateIndex.isSelected());
			}
		});
		add(chckbxUpdateIndex, "4, 6, left, default");

		comboBoxIndex = new JComboBox();
		comboBoxIndex.setModel(new DefaultComboBoxModel(new String[] { "Default", "Enable", "Disable" }));
		add(comboBoxIndex, "6, 6, fill, center");

		JTextPane txtpnSpecifiesWhetherThe = new JTextPane();
		txtpnSpecifiesWhetherThe.setForeground(Color.GRAY);
//		txtpnSpecifiesWhetherThe.setBackground(SystemColor.control);
		txtpnSpecifiesWhetherThe.setText("The metadata query engine uses the index setting to determine whether to index custom metadata for an object.");
		txtpnSpecifiesWhetherThe.setEditable(false);
		add(txtpnSpecifiesWhetherThe, "4, 7, 9, 1, fill, fill");

		lblHold = new JLabel("Hold:");
//		lblHold.setBackground(new Color(144, 238, 144));
//		lblHold.setOpaque(true);
		add(lblHold, "2, 9, right, default");

		chckbxUpdateHold = new JCheckBox("Update Hold");
		chckbxUpdateHold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxHold.setEnabled(chckbxUpdateHold.isSelected());
			}
		});
		add(chckbxUpdateHold, "4, 9, left, default");

		comboBoxHold = new JComboBox();
		comboBoxHold.setModel(new DefaultComboBoxModel(new String[] { "Default", "Enable", "Disable" }));
		add(comboBoxHold, "6, 9, fill, default");

		JTextPane txtpnIfYouHave = new JTextPane();
		txtpnIfYouHave.setForeground(Color.GRAY);
//		txtpnIfYouHave.setBackground(SystemColor.control);
		txtpnIfYouHave.setEditable(false);
		txtpnIfYouHave.setText(
				"Place an object on hold. An object that is on hold cannot be deleted, even by a privileged delete operation. Also, you cannot store new versions of an object that is on hold.");
		add(txtpnIfYouHave, "4, 10, 9, 1, fill, fill");

		lblShred = new JLabel("Shred:");
		add(lblShred, "2, 12, right, default");

		chckbxUpdateShred = new JCheckBox("Update Shred");
		chckbxUpdateShred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxShred.setEnabled(chckbxUpdateShred.isSelected());
			}
		});
		add(chckbxUpdateShred, "4, 12, left, default");

		comboBoxShred = new JComboBox();
		comboBoxShred.setModel(new DefaultComboBoxModel(new String[] { "Default", "Enable", "Disable" }));
		add(comboBoxShred, "6, 12, fill, default");

		JTextPane txtpnAlsoCalledSecure = new JTextPane();
		txtpnAlsoCalledSecure.setForeground(Color.GRAY);
//		txtpnAlsoCalledSecure.setBackground(SystemColor.control);
		txtpnAlsoCalledSecure.setText(
				"Also called secure deletion, is the process of deleting an object and overwriting the places where its copies were stored in such a way that none of its data or metadata, including custom metadata, can be reconstructed.");
		txtpnAlsoCalledSecure.setEditable(false);
		add(txtpnAlsoCalledSecure, "4, 13, 9, 1, fill, fill");

		separator_1 = new JSeparator();
		add(separator_1, "2, 15, 11, 1");

		JLabel lblOwner = new JLabel("Owner:");
		add(lblOwner, "2, 17, right, default");

		chckbxUpdateOwner = new JCheckBox("Update Owner");
		chckbxUpdateOwner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldOwner.setEnabled(chckbxUpdateOwner.isSelected());
			}
		});
		add(chckbxUpdateOwner, "4, 17");

		textFieldOwner = new JTextField();
		add(textFieldOwner, "6, 17, 7, 1, fill, default");
		textFieldOwner.setColumns(10);

		separator_2 = new JSeparator();
		add(separator_2, "2, 19, 11, 1");

		JLabel lblRetention = new JLabel("Retention:");
		add(lblRetention, "2, 21, right, default");

		chckbxUpdateRetention = new JCheckBox("Update Retention");
		chckbxUpdateRetention.setEnabled(false);
		chckbxUpdateRetention.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxRetention.setEnabled(chckbxUpdateRetention.isSelected());
				textFieldRetention.setEnabled(chckbxUpdateRetention.isSelected());
			}
		});
		add(chckbxUpdateRetention, "4, 21, 1, 3");

		comboBoxRetention = new JComboBox();
		comboBoxRetention.setModel(
				new DefaultComboBoxModel(new String[] { "Default", "Deletion Allowed", "Deletion Prohibited", "Initial Unspecified", "Datetime", "Offset", "Retention Class" }));
		add(comboBoxRetention, "6, 21, 3, 1, fill, default");

		textFieldRetention = new JTextField();
		add(textFieldRetention, "6, 23, 7, 1, fill, default");
		textFieldRetention.setColumns(10);

		scrollPane = new JScrollPane();
		add(scrollPane, "2, 25, 11, 1, fill, fill");

		txtrRetentionSettingThat = new JTextArea();
		txtrRetentionSettingThat.setForeground(Color.GRAY);
//		txtrRetentionSettingThat.setBackground(SystemColor.control);
		txtrRetentionSettingThat.setEditable(false);
		txtrRetentionSettingThat.setFont(new Font(GlobalUI.getDefaultFont().getFamily(), Font.PLAIN, 11));
		txtrRetentionSettingThat.setText(
				"Retention setting that specifies when (or whether) the retention period for the object ends.\r\n\r\nDeletion Allowed:\r\n  Allows the object to be deleted at any time.\r\nDeletion Prohibited:\r\n  Prevents the object from being deleted and its retention setting from being changed.\r\nInitial Unspecified:\r\n  Specifies that the object does not yet have a retention setting.\r\nDatetime:\r\n  Prevents the object from being deleted until the specified date and time.\r\nOffset:\r\n  Specifies a period for which to retain the object.\r\nC+retention_class_name:\r\n  Assigns the object to a retention class.The retention class you assign must already be defined for the namespace.\r\n----------------------------------------------------------------------------------------------------------------------------\r\nOffset syntax\r\nTo use an offset as a retention setting, specify a standard expression that conforms to this syntax:\r\n^([RAN])?([+-]\\d+y)?([+-]\\d+M)?([+-]\\d+w)?([+-]\\d+d)?([+-]\\d+h)?([+-]\\d+m)?([+-]\\d+s)?\r\n\r\nOffset examples\r\nHere are some examples of offset values used to extend a retention period:\r\n•This value sets the retention value to 100 years past the time when the object was stored:\r\n  A+100y\r\n•This value sets the end of the retention period to 20 days minus five hours past the current date and time:\r\n  N+20d-5h\r\n•This value extends the current retention period by two years and one day:\r\n  R+2y+1d");
		scrollPane.setViewportView(txtrRetentionSettingThat);
	}

	@Override
	public String getTitle() {
		return "System Metadata";
	}

	@Override
	public void activing() {
		super.activing();
		showSystemMetadata();
	}

	private void showSystemMetadata() {
		chckbxUpdateIndex.setSelected(false);
		chckbxUpdateHold.setSelected(false);
		chckbxUpdateShred.setSelected(false);
		chckbxUpdateOwner.setSelected(false);
		chckbxUpdateRetention.setSelected(false);
		comboBoxHold.setEnabled(false);
		comboBoxIndex.setEnabled(false);
		comboBoxShred.setEnabled(false);
		comboBoxRetention.setEnabled(false);
		textFieldOwner.setEnabled(false);
		textFieldRetention.setEnabled(false);

		// 显示选择的对象名字
		StringBuilder buf = new StringBuilder();
		for (Item item : hcpItems) {
			buf.append(item.getName());
			buf.append("; ");
		}
		textFieldItems.setText(buf.toString());

		// 如果选择了一个对象文件
		if (hcpItems.size() == 1 && hcpItems.get(0) instanceof HCPFileItem) {
			Item item = hcpItems.get(0);

			HCPNamespace hcpClient = ((HCPItemBase) item).getHcpClient();
			try {
				HCPObjectSummary summary1 = hcpClient.getObjectSummary(item.getPath());

				comboBoxHold.setSelectedItem(summary1.isHold() ? "Enable" : "Disable");
				comboBoxIndex.setSelectedItem(summary1.isIndexed() ? "Enable" : "Disable");
				comboBoxShred.setSelectedItem(summary1.isShred() ? "Enable" : "Disable");

				textFieldOwner.setText(summary1.getOwner());
				textFieldRetention.setText(summary1.getRetention());
			} catch (InvalidResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			textFieldOwner.setText("");
			textFieldRetention.setText("");

			comboBoxHold.setSelectedItem("Default");
			comboBoxIndex.setSelectedItem("Default");
			comboBoxShred.setSelectedItem("Default");
		}
	}

	private class SystemMetadataSetting {
		Boolean hold = null;
		Boolean index = null;
		Boolean shred = null;
		String owner = null;
		String retention = null;

		public void getSettings() {
			if (chckbxUpdateHold.isSelected()) {
				String val = (String) comboBoxHold.getSelectedItem();
				if ("Default".equalsIgnoreCase(val)) {
					hold = Boolean.FALSE;
				} else {
					hold = "Enable".equalsIgnoreCase(val);
				}
			}
			if (chckbxUpdateIndex.isSelected()) {
				String val = (String) comboBoxIndex.getSelectedItem();
				if ("Default".equalsIgnoreCase(val)) {
					index = Boolean.TRUE;
				} else {
					index = "Enable".equalsIgnoreCase(val);
				}
			}
			if (chckbxUpdateShred.isSelected()) {
				String val = (String) comboBoxShred.getSelectedItem();
				if ("Default".equalsIgnoreCase(val)) {
					shred = Boolean.FALSE;
				} else {
					shred = "Enable".equalsIgnoreCase(val);
				}
			}
			if (chckbxUpdateOwner.isSelected()) {
				owner = textFieldOwner.getText();
				if (StringUtils.isEmpty(owner)) {
					owner = null;
					UIUtils.openAndLogError("Owner must be specificed, if you want to update it!");
				}
			}
			if (chckbxUpdateRetention.isSelected()) {
				retention = textFieldRetention.getText();
				if (StringUtils.isEmpty(retention)) {
					retention = null;
					UIUtils.openAndLogError("Retention must be specificed, if you want to update it!");
				}
			}
		}
	}

	public boolean saveSystemMetadata() {
		if (hcpItems.size() == 1 && hcpItems.get(0) instanceof HCPFileItem) {
			Item item = hcpItems.get(0);
			return saveSystemMetadata((HCPFileItem) item);
		} else if (hcpItems.size() > 0) {
			ItemPackage sourceContentPackage;
			try {
				sourceContentPackage = ItemUtils.toItemPackage(null, hcpItems);
			} catch (ServiceException e) {
				e.printStackTrace();
				return false;
			}
			if (sourceContentPackage.hasItem()) {
				if (UIUtils.openInfoConfirm(
						"Are you sure to update system metadata of selected items" + (sourceContentPackage.hasFolderItem() ? ", include items in sub folders" : "") + "?")) {
					TaskManagement taskMgr = GlobalResource.getTaskmanagement();

					SystemMetadataSetting sms = new SystemMetadataSetting();
					sms.getSettings();

					taskMgr.add(GlobalResource.getTaskFactory(HCPTaskFactory.class).createHCPObjectSystemMetadataUpdateTask(sourceContentPackage, sms.hold, sms.index, sms.shred, sms.owner, sms.retention));

					return true;
				}
			}
		}
		return false;
	}

	private boolean saveSystemMetadata(HCPFileItem item) {
		try {
			SystemMetadataSetting sms = new SystemMetadataSetting();
			sms.getSettings();

			((HCPFileItem) item).setSystemMetadata(sms.hold, sms.index, sms.shred, sms.owner, sms.retention);
		} catch (Exception e) {
			UIUtils.openAndLogError("Failed to update system metadata." + item.getPath(), e);
//			GlobalContext.getLogger().error("Failed to update system metadata. " + item.getActualPath(), e);

			return false;
		}
		return true;
	}

	public void showItemsSystemMetadata(List<Item> items) {
		if (items == null || items.size() == 0) {
			hcpItems.clear();
		} else {
			for (Item item : items) {
				if (item instanceof HCPItemBase) {
					hcpItems.add((HCPItemBase) item);
				}
			}

			showSystemMetadata();
		}
	}
}
