package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.common.util.DigestUtils;
import com.amituofo.xcexplorer.core.ui.frame.EntryConfigPanel;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.HCPFileSystemEntryConfig;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.HCPFileSystemEntryConfig.AuthenticationType;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class HCPEntryConfigPanel extends EntryConfigPanel<HCPFileSystemEntryConfig> {

	private JTextField textName;
	private JTextField textDomain;
	private JTextField textTenant;
	private JTextField textNamespace;
	private JTextField textUser;
	private JPasswordField txtPassword;
	private JCheckBox chckbxUsingSsl;
	private JComboBox comboHCPVersion;
	private JTextPane textComment;
	private HCPFileSystemEntryConfig setting;
	private JComboBox<AuthenticationType> comboBoxAuthType;

	public HCPEntryConfigPanel() {
		super(true, new HCPEntryAdvanceConfigsPanel());

		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		textName = new JTextField();
		textName.setColumns(10);

		JLabel lblDomain = new JLabel("Domain:");
		lblDomain.setHorizontalAlignment(SwingConstants.RIGHT);

		textDomain = new JTextField();
		textDomain.setColumns(10);

		JLabel lblNamespace = new JLabel("Tenant:");
		lblNamespace.setHorizontalAlignment(SwingConstants.RIGHT);

		textTenant = new JTextField();
		textTenant.setColumns(10);

		JLabel lblNamespace_1 = new JLabel("Namespace:");
		lblNamespace_1.setHorizontalAlignment(SwingConstants.RIGHT);

		textNamespace = new JTextField();
		textNamespace.setColumns(10);

		JLabel lblUser = new JLabel("Base64 Username:");
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);

		textUser = new JTextField();
		textUser.setColumns(10);

		JLabel lblPassword = new JLabel("MD5 Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);

		chckbxUsingSsl = new JCheckBox("Using TLS/SSL for Connection");

		txtPassword = new JPasswordField();

		JLabel lblVersion = new JLabel("Version:");
		lblVersion.setHorizontalAlignment(SwingConstants.RIGHT);

		comboHCPVersion = new JComboBox();
		comboHCPVersion.setModel(new DefaultComboBoxModel(HCPFileSystemEntryConfig.HCP_VERSIONS));
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC,
						ColumnSpec.decode("117px"),
						FormSpecs.UNRELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.PARAGRAPH_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.PARAGRAPH_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.PARAGRAPH_GAP_ROWSPEC,
						RowSpec.decode("68px"),
						RowSpec.decode("94px"), }));
		add(lblNewLabel, "2, 2, fill, fill");
		add(textName, "4, 2, 5, 1, fill, fill");

		JSeparator separator = new JSeparator();
		add(separator, "1, 3, 9, 1, fill, center");
		add(lblDomain, "2, 6, fill, fill");
		add(textDomain, "4, 6, 5, 1, fill, fill");
		add(lblNamespace, "2, 8, fill, fill");
		add(textTenant, "4, 8, 5, 1, fill, fill");
		add(lblNamespace_1, "2, 10, fill, fill");
		add(textNamespace, "4, 10, 5, 1, fill, fill");

		JLabel lblNewLabel_1 = new JLabel("Authorization");
		add(lblNewLabel_1, "2, 12, right, center");

		comboBoxAuthType = new JComboBox<AuthenticationType>();
		comboBoxAuthType.setModel(new DefaultComboBoxModel(AuthenticationType.values()));
		comboBoxAuthType.setSelectedIndex(0);

		add(comboBoxAuthType, "4, 12, fill, fill");
		add(lblUser, "2, 14, fill, fill");
		add(textUser, "4, 14, 3, 1, fill, center");

		final JButton btnToBase = new JButton("To Base64");
		// btnToBase.setBackground(SystemColor.control);
		btnToBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println(DigestUtils.encodeBase64(textUser.getText()));
				textUser.setText(DigestUtils.encodeBase64(textUser.getText()));
			}
		});
		add(btnToBase, "8, 14, fill, center");
		add(lblPassword, "2, 16, fill, fill");

		final JButton btnToMd = new JButton("To MD5   ");
		// btnToMd.setBackground(SystemColor.control);
		btnToMd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtPassword.setText(DigestUtils.format2Hex(DigestUtils.calcMD5(txtPassword.getText())).toLowerCase());
			}
		});
		add(btnToMd, "8, 16, fill, center");
		add(chckbxUsingSsl, "4, 18, fill, top");
		add(txtPassword, "4, 16, 3, 1, fill, center");

		JSeparator separator_1 = new JSeparator();
		add(separator_1, "1, 20, 9, 1, fill, center");

		JLabel lblComment = new JLabel("Comment:");
		lblComment.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblComment, "2, 21, fill, top");

		textComment = new JTextPane();
		add(textComment, "4, 21, 5, 1, fill, fill");
		add(lblVersion, "2, 4, fill, fill");
		add(comboHCPVersion, "4, 4, fill, fill");

		comboBoxAuthType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AuthenticationType authType = (AuthenticationType) comboBoxAuthType.getSelectedItem();
				if (authType == AuthenticationType.Anonymous) {
					textUser.setEnabled(false);
					txtPassword.setEnabled(false);
					btnToBase.setEnabled(false);
					btnToMd.setEnabled(false);
				} else if (authType == AuthenticationType.Local) {
					textUser.setEnabled(true);
					txtPassword.setEnabled(true);
					btnToBase.setEnabled(true);
					btnToMd.setEnabled(true);
				} else if (authType == AuthenticationType.Active_Directory) {
					textUser.setEnabled(true);
					txtPassword.setEnabled(true);
					btnToBase.setEnabled(false);
					btnToMd.setEnabled(false);
				}
			}
		});
	}

	@Override
	protected HCPFileSystemEntryConfig getEntryConfig() {
		if (this.setting == null) {
			this.setting = new HCPFileSystemEntryConfig();
		}

		HCPFileSystemEntryConfig entryConfig = this.setting;

		entryConfig.setName(textName.getText());
		entryConfig.setDomain(textDomain.getText());
		entryConfig.setTenant(textTenant.getText());
		entryConfig.setNamespace(textNamespace.getText());

		AuthenticationType authType = (AuthenticationType) comboBoxAuthType.getSelectedItem();
		entryConfig.setAuthenticationType(authType);

		if (authType == AuthenticationType.Anonymous) {
			entryConfig.setAccesskey("Hi... (^_^)");
			try {
				entryConfig.setSecretkey("NA-MO-A-MI-TUO-FO");
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		} else {
			entryConfig.setAccesskey(textUser.getText());
			try {
				entryConfig.setSecretkey(new String(txtPassword.getPassword()));
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}

		entryConfig.setHCPVersion(comboHCPVersion.getSelectedIndex());
		entryConfig.setUseSSL(chckbxUsingSsl.isSelected());

		entryConfig.setDescription(textComment.getText());

		super.updateAdvanceConfig(entryConfig);

		return entryConfig;
	}

	// @Override
	// public void testEntry(HCPFileSystemEntryConfig entryConfig) throws ServiceException {
	//
	// ClientConfiguration clientConfig = new ClientConfiguration();
	//
	// if (entryConfig.getConnectionTimeout() > 0) {
	// clientConfig.setConnectTimeout(entryConfig.getConnectionTimeout());
	// }
	//
	// HCPNamespace hcpClient = HCPClientFactory.getInstance().getHCPClient(ClientType.REST,
	// entryConfig.getProtocol(),
	// entryConfig.getNamespace(),
	// entryConfig.getTenant(),
	// entryConfig.getDomain(),
	// entryConfig.getAccesskey(),
	// entryConfig.getSecretkey(),
	// clientConfig,
	// true);
	// ObjectEntryIterator it = null;
	// try {
	// HCPObjectEntrys entrys = hcpClient.listDirectory("/");
	// it = entrys.iterator();
	// } catch (InvalidResponseException e) {
	// throw new ServiceException(e.getMessage(), e);
	// } catch (HSCException e) {
	// throw new ServiceException(e);
	// } finally {
	// if (it != null) {
	// it.close();
	// }
	// }
	// }

	@Override
	public void showEntryConfig(HCPFileSystemEntryConfig setting) {
		this.setting = setting;

		textName.setText(setting.getName());
		textDomain.setText(setting.getDomain());
		textTenant.setText(setting.getTenant());
		comboBoxAuthType.setSelectedItem(setting.getAuthenticationType());
		textNamespace.setText(setting.getNamespace());
		textUser.setText(setting.getAccesskey());
		try {
			txtPassword.setText(setting.getSecretkey());
		} catch (ServiceException e) {
			// e.printStackTrace();
			UIUtils.openAndLogError("Unable to get password setting.", e);
		}

		chckbxUsingSsl.setSelected(setting.isUseSSL());
		comboHCPVersion.setSelectedIndex(setting.getHCPVersion());

		textComment.setText(setting.getDescription());

		super.showAdvanceConfig(setting);
	}

	@Override
	public void clear() {
		this.setting = null;

		textName.setText("");
		textDomain.setText("");
		textTenant.setText("");
		textNamespace.setText("");
		comboBoxAuthType.setSelectedIndex(0);
		textUser.setText("");
		txtPassword.setText("");

		chckbxUsingSsl.setSelected(false);
		comboHCPVersion.setSelectedIndex(0);

		textComment.setText("");

		super.clear();
	}
}
