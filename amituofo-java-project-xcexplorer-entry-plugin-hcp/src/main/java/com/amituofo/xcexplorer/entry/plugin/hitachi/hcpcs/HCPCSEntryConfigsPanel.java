package com.amituofo.xcexplorer.entry.plugin.hitachi.hcpcs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.xcexplorer.core.ui.frame.EntryConfigPanel;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.s3compatible.CompatibleS3FileSystemEntryConfig;
import com.amituofo.xfs.plugin.fs.objectstorage.s3compatible.SignerType;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class HCPCSEntryConfigsPanel extends EntryConfigPanel<CompatibleS3FileSystemEntryConfig> {

	private JTextField textName;
	private JTextField textEndpoint;
	private JTextField textNamespace;
	private JTextField textUser;
	private JPasswordField txtPassword;
	private JCheckBox chckbxUsingSsl;
	private JComboBox<SignerType> comboSignerType;
	private JTextPane textComment;
	private CompatibleS3FileSystemEntryConfig setting;
	private JCheckBox chckbxIgnoreSSLCertification;

	public HCPCSEntryConfigsPanel() {
		super(true, null);

		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		textName = new JTextField();
		textName.setColumns(10);

		JLabel lblEndpoint = new JLabel("Endpoint:");
		lblEndpoint.setHorizontalAlignment(SwingConstants.RIGHT);

		textEndpoint = new JTextField();
		textEndpoint.setColumns(10);

		JLabel lblUser = new JLabel("Accesskey:");
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);

		textUser = new JTextField();
		textUser.setColumns(10);

		JLabel lblPassword = new JLabel("Secretkey:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);

		chckbxUsingSsl = new JCheckBox("Using TLS/SSL for Connection");
		chckbxUsingSsl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxIgnoreSSLCertification.setEnabled(chckbxUsingSsl.isSelected());
			}
		});
		chckbxUsingSsl.setSelected(true);

		txtPassword = new JPasswordField();
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("117px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("152px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
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
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("68px"),
				RowSpec.decode("94px"),}));
		add(lblNewLabel, "2, 2, fill, fill");
		add(textName, "4, 2, 3, 1, fill, fill");

		JSeparator separator = new JSeparator();
		add(separator, "1, 3, 7, 1, fill, center");
		add(lblEndpoint, "2, 4, fill, fill");
		add(textEndpoint, "4, 4, 3, 1, fill, fill");
		
				JLabel lblVersion = new JLabel("Signature:");
				lblVersion.setHorizontalAlignment(SwingConstants.RIGHT);
				add(lblVersion, "2, 6, fill, fill");
		
				comboSignerType = new JComboBox<SignerType>();
				comboSignerType.setModel(new DefaultComboBoxModel<SignerType>(SignerType.values()));
				add(comboSignerType, "4, 6, fill, fill");
		add(lblUser, "2, 8, fill, fill");
		add(textUser, "4, 8, 3, 1, fill, center");
		add(lblPassword, "2, 10, fill, fill");
				
						JLabel lblNamespace_1 = new JLabel("Default Bucket:");
						lblNamespace_1.setHorizontalAlignment(SwingConstants.RIGHT);
						add(lblNamespace_1, "2, 12, fill, fill");
		
				textNamespace = new JTextField();
				textNamespace.setColumns(10);
				add(textNamespace, "4, 12, 3, 1, fill, fill");
		add(chckbxUsingSsl, "4, 14, 3, 1, left, fill");
		add(txtPassword, "4, 10, 3, 1, fill, center");

		chckbxIgnoreSSLCertification = new JCheckBox("Ignore TLS/SSL Certificates");
		chckbxIgnoreSSLCertification.setSelected(true);
		chckbxIgnoreSSLCertification.setEnabled(chckbxUsingSsl.isSelected());
		add(chckbxIgnoreSSLCertification, "4, 16, 3, 1, left, fill");

		JSeparator separator_1 = new JSeparator();
		add(separator_1, "1, 18, 7, 1, fill, center");

		JLabel lblComment = new JLabel("Comment:");
		lblComment.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblComment, "2, 19, fill, top");

		textComment = new JTextPane();
		add(textComment, "4, 19, 3, 1, fill, fill");
	}

	@Override
	protected CompatibleS3FileSystemEntryConfig getEntryConfig() {
		if (this.setting == null) {
			this.setting = new CompatibleS3FileSystemEntryConfig();
		}

		CompatibleS3FileSystemEntryConfig entryConfig = setting;

		entryConfig.setName(textName.getText());
		entryConfig.setEndpoint(textEndpoint.getText());
		entryConfig.setBucketName(textNamespace.getText());
		entryConfig.setAccesskey(textUser.getText());
		try {
			entryConfig.setSecretkey(new String(txtPassword.getPassword()));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SignerType signerType = ((SignerType) comboSignerType.getSelectedItem());
		if (signerType != SignerType.Default) {
			entryConfig.setSignerType(signerType.getSignerType());
		}
		entryConfig.setUseSSL(chckbxUsingSsl.isSelected());
		entryConfig.setIgnoreSSLCertification(chckbxIgnoreSSLCertification.isSelected());

		entryConfig.setDescription(textComment.getText());

		return entryConfig;
	}

	// @Override
	// public void testEntry(CompatibleS3FileSystemEntryConfig entryConfig) throws ServiceException {
	// ClientConfiguration clientConfig = new ClientConfiguration();
	//
	// if (entryConfig.getConnectionTimeout() > 0) {
	// clientConfig.setRequestTimeout(entryConfig.getConnectionTimeout());
	// clientConfig.setConnectionTimeout(entryConfig.getConnectionTimeout());
	// }
	//
	// com.hitachivantara.core.http.Protocol hcpProtocol = entryConfig.getProtocol();
	// com.amazonaws.Protocol awsProtocol = com.amazonaws.Protocol.valueOf(hcpProtocol.name());
	//
	// AmazonS3 hcpClient = S3ClientFactory.getInstance().getS3Client(awsProtocol,
	// entryConfig.isIgnoreSSLCertification(),
	// entryConfig.getBucketName(),
	// entryConfig.getEndpoint(),
	// entryConfig.getAccesskey(),
	// entryConfig.getSecretkey(),
	// clientConfig,
	// true);
	// try {
	// ObjectListing objlisting = hcpClient.listObjects(entryConfig.getBucketName());
	// List<S3ObjectSummary> objs = objlisting.getObjectSummaries();
	// for (S3ObjectSummary s3ObjectSummary : objs) {
	// // System.out.println(s3ObjectSummary.getSize() + "\t" + s3ObjectSummary.getETag() + "\t" + s3ObjectSummary.getKey());
	// break;
	// }
	//
	// } catch (AmazonServiceException e) {
	// throw new ServiceException(e.getMessage(), e);
	// } catch (Exception e) {
	// throw new ServiceException(e);
	// }
	// }

	@Override
	public void showEntryConfig(CompatibleS3FileSystemEntryConfig setting) {
		this.setting = setting;

		textName.setText(setting.getName());
		textEndpoint.setText(setting.getEndpoint());
		textNamespace.setText(setting.getBucketName());
		textUser.setText(setting.getAccesskey());
		try {
			txtPassword.setText(setting.getSecretkey());
		} catch (ServiceException e) {
			UIUtils.openAndLogError("Unable to get password setting.", e);
		}

		chckbxUsingSsl.setSelected(setting.isUseSSL());
		chckbxIgnoreSSLCertification.setSelected(setting.isIgnoreSSLCertification());

		SignerType st = SignerType.valueOfSignerType(setting.getSignerType());
		comboSignerType.setSelectedItem(st);

		textComment.setText(setting.getDescription());
	}

	@Override
	public void clear() {
		this.setting = null;

		textName.setText("");
		textEndpoint.setText("");
		textNamespace.setText("");
		textUser.setText("");
		txtPassword.setText("");

		chckbxUsingSsl.setSelected(true);
		chckbxIgnoreSSLCertification.setSelected(true);
		comboSignerType.setSelectedIndex(0);

		textComment.setText("");
	}

}
