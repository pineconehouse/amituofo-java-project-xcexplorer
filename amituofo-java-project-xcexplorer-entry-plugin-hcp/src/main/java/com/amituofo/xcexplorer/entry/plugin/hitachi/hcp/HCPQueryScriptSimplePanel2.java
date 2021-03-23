package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.DateFormatter;

import org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton;

import com.amituofo.common.ui.swingexts.component.FilterComboBox;
import com.amituofo.common.ui.swingexts.component.JIntegerTextField;
import com.amituofo.common.util.DateUtils;
import com.amituofo.common.util.StringUtils;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.action.SwitchWorkingSpaceAction;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.HCPFileSystemEntryConfig;

public class HCPQueryScriptSimplePanel2 extends HCPQueryScriptPanel implements SwitchWorkingSpaceAction {
	private JIntegerTextField textFieldSizeMin;
	private JIntegerTextField textFieldSizeMax;
	private JTextArea tfMetadataContent;
	private JCalendarButton datePickerFrom;
	private JCalendarButton datePickerTo;
	private FilterComboBox comboBoxPathFilter;
	private JCheckBox chckbxQueryInCurrentNS;
	private String currentNamespace;
	private ButtonGroup buttonGroup;
	private JFormattedTextField txtDateFrom;
	private JFormattedTextField txtDateTo;

	private enum FileFilter {
		ALL("*", "All types"), Office("*.xls; *.xlsx; *.doc; *.docx; *.ppt; *.pptx; *.rtf; *.wps; *.pdf", "Office files"), Image("*.bmp; *.jpg; *.jpeg; *.png; *.gif; *.psd; *.tif",
				"Image files"), Video("*.avi; *.mov; *.asf; *.rmvb; *.flv; *.mp4; *.wmv; *.mpg; *.3gp", "Video files"), Compressed(
						"*.7z; *.gz; *.rar; *.rpm; *.tar.gz; *.tar; *.zip; *.iso",
						"Compressed Files"), Text("*.txt; *.log", "Text files"), Data("*.dat; *.csv; *.xml", "Data files"), Exe("*.exe; *.bat", "Executable files");

		private String ext;
		private String desc;

		FileFilter(String ext, String desc) {
			this.ext = ext;
			this.desc = desc;
		}

		public String getExt() {
			return ext;
		}

		public String getDesc() {
			return desc;
		}

		@Override
		public String toString() {
			return desc;
		}

	}

	public HCPQueryScriptSimplePanel2() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Type:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(21, 15, 46, 14);
		panel.add(lblNewLabel_1);

		chckbxQueryInCurrentNS = new JCheckBox("Query in current namespace.");
		chckbxQueryInCurrentNS.setSelected(true);
		chckbxQueryInCurrentNS.setBounds(73, 71, 237, 23);
		panel.add(chckbxQueryInCurrentNS);

		JLabel lblPath = new JLabel("Path Filter:");
		lblPath.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPath.setBounds(10, 43, 57, 19);
		panel.add(lblPath);

		JLabel lblSize = new JLabel("Size:");
		lblSize.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSize.setBounds(358, 15, 46, 14);
		panel.add(lblSize);

		textFieldSizeMin = new JIntegerTextField();
		textFieldSizeMin.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldSizeMin.setBounds(410, 40, 160, 24);
		panel.add(textFieldSizeMin);

		textFieldSizeMax = new JIntegerTextField();
		textFieldSizeMax.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldSizeMax.setBounds(410, 70, 160, 24);
		textFieldSizeMax.setColumns(10);
		panel.add(textFieldSizeMax);

		JLabel lblAtLeast = new JLabel("At least:");
		lblAtLeast.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAtLeast.setBounds(358, 45, 46, 14);
		panel.add(lblAtLeast);

		JLabel lblAtMost = new JLabel("At most:");
		lblAtMost.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAtMost.setBounds(346, 75, 58, 14);
		panel.add(lblAtMost);

		buttonGroup = new ButtonGroup();
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Bytes");
		rdbtnNewRadioButton_1.setToolTipText("0");
		rdbtnNewRadioButton_1.setBounds(407, 11, 57, 23);
		buttonGroup.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_1);

		JRadioButton rdbtnKb = new JRadioButton("KB");
		rdbtnKb.setToolTipText("1024");
		rdbtnKb.setBounds(466, 11, 49, 23);
		buttonGroup.add(rdbtnKb);
		panel.add(rdbtnKb);

		JRadioButton rdbtnMb = new JRadioButton("MB");
		rdbtnMb.setToolTipText("1048576");
		rdbtnMb.setSelected(true);
		rdbtnMb.setBounds(517, 11, 46, 23);
		buttonGroup.add(rdbtnMb);
		panel.add(rdbtnMb);

		JRadioButton rdbtnGb = new JRadioButton("GB");
		rdbtnGb.setToolTipText("1073741824");
		rdbtnGb.setBounds(565, 11, 46, 23);
		buttonGroup.add(rdbtnGb);
		panel.add(rdbtnGb);

		JLabel lblBetween = new JLabel("From:");
		lblBetween.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBetween.setBounds(633, 44, 37, 14);
		panel.add(lblBetween);

		JLabel lblTime = new JLabel("Time:");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTime.setBounds(633, 14, 37, 14);
		panel.add(lblTime);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Ingest time" }));
		comboBox.setBounds(680, 9, 141, 24);
		panel.add(comboBox);

		final JComboBox<FileFilter> comboBox_1 = new JComboBox<FileFilter>();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxPathFilter.setSelectedItem(((FileFilter) comboBox_1.getSelectedItem()).getExt());
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel<FileFilter>(FileFilter.values()));
		comboBox_1.setBounds(77, 10, 141, 24);
		panel.add(comboBox_1);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(346, 15, 2, 87);
		panel.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(624, 17, 2, 85);
		panel.add(separator_1);

		// DatePickerSettings dateSettings1 = new DatePickerSettings();
		// dateSettings1.displayFormatterAD = null;
		// dateSettings1.displayFormatterAD = new DateTimeFormatter();
		// dateSettings1.setFormatForDatesCommonEra("yyyy/MM/dd");
		// dateSettings1.setFormatForDatesBeforeCommonEra("uuuu/MM/dd");

		datePickerFrom = new JCalendarButton() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				super.propertyChange(evt);
				Date date = datePickerFrom.getTargetDate();
				if (date != null)
					try {
						txtDateFrom.setText(txtDateFrom.getFormatter().valueToString(date));
					} catch (ParseException e) {
						e.printStackTrace();
					}
			}
		};
		// !!!!!!!!!!!!!!!!!!!!!!!Unsupport jdk 1.7.x
		// datePickerFrom.getComponentToggleCalendarButton().setText("");
		// datePickerFrom.getComponentToggleCalendarButton().setIcon(Icons.ICON_SELECT_16x16);
		// datePickerFrom.getComponentDateTextField().setHorizontalAlignment(SwingConstants.CENTER);
		datePickerFrom.setBounds(798, 40, 24, 24);
		panel.add(datePickerFrom);

		// DatePickerSettings dateSettings2 = new DatePickerSettings();
		// dateSettings2.setFormatForDatesCommonEra("yyyy/MM/dd");
		// dateSettings2.setFormatForDatesBeforeCommonEra("uuuu/MM/dd");
		datePickerTo = new JCalendarButton() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				super.propertyChange(evt);
				Date date = datePickerTo.getTargetDate();
				if (date != null)
					try {
						txtDateTo.setText(txtDateTo.getFormatter().valueToString(date));
					} catch (ParseException e) {
						e.printStackTrace();
					}
			}
		};
		// datePickerTo.getComponentToggleCalendarButton().setText("");
		// datePickerTo.getComponentToggleCalendarButton().setIcon(Icons.ICON_SELECT_16x16);
		// datePickerTo.getComponentDateTextField().setLocation(0, 70);
		// datePickerTo.getComponentDateTextField().setHorizontalAlignment(SwingConstants.CENTER);
		datePickerTo.setBounds(798, 70, 24, 24);
		panel.add(datePickerTo);

		JLabel lblTo = new JLabel("To:");
		lblTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTo.setBounds(633, 74, 37, 14);
		panel.add(lblTo);

		comboBoxPathFilter = new FilterComboBox();
		comboBoxPathFilter.setModel(new DefaultComboBoxModel(new String[] { "*", "*.pdf", "*.log", "*.xls; *.xlsx" }));
		comboBoxPathFilter.setBounds(77, 40, 248, 24);
		panel.add(comboBoxPathFilter);

		tfMetadataContent = new JTextArea();
		tfMetadataContent.setBounds(853, 39, 208, 54);
		panel.add(tfMetadataContent);

		JLabel lblMetadataContent = new JLabel("Metadata Contains:");
		lblMetadataContent.setBounds(853, 12, 130, 19);
		panel.add(lblMetadataContent);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(841, 8, 2, 85);
		panel.add(separator_2);

		JButton btnNewButton = new JButton();
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldSizeMin.setValue(null);
				textFieldSizeMin.setText("");
			}
		});
		btnNewButton.setIcon(GlobalIcons.ICON_EARSER_16x16);
		btnNewButton.setBounds(574, 40, 24, 24);
		panel.add(btnNewButton);

		JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldSizeMax.setValue(null);
				textFieldSizeMax.setText("");
			}
		});
		button.setIcon(GlobalIcons.ICON_EARSER_16x16);
		button.setBounds(574, 70, 24, 24);
		panel.add(button);

		txtDateFrom = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy/MM/dd")));
		txtDateFrom.setToolTipText("Input format : yyyy/MM/dd");
		txtDateFrom.setHorizontalAlignment(SwingConstants.CENTER);
		txtDateFrom.setBounds(680, 40, 113, 24);
		panel.add(txtDateFrom);

		txtDateTo = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy/MM/dd")));
		txtDateTo.setToolTipText("Input format : yyyy/MM/dd");
		txtDateTo.setHorizontalAlignment(SwingConstants.CENTER);
		txtDateTo.setBounds(680, 70, 113, 24);
		panel.add(txtDateTo);
	}

	private long getSizeUnitFactor() {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				return Long.parseLong(button.getToolTipText());
			}
		}

		return 1;
	}

	@Override
	public void clear() {
		comboBoxPathFilter.setSelectedItem("");
		chckbxQueryInCurrentNS.setSelected(true);

		textFieldSizeMin.setValue(null);
		textFieldSizeMax.setValue(null);
		textFieldSizeMin.setText("");
		textFieldSizeMax.setText("");

		datePickerFrom.setText("");
		datePickerTo.setText("");

		tfMetadataContent.setText("");
	}

	@Override
	public String toExpression() {
		StringBuilder buf = new StringBuilder();
		if (chckbxQueryInCurrentNS.isSelected()) {
			buf.append("+(namespace:\"" + currentNamespace + "\")");
		}

		String selectedPathFilter = (String) comboBoxPathFilter.getSelectedItem();
		selectedPathFilter = selectedPathFilter.trim().replace("**", "*");
		if (StringUtils.isNotEmpty(selectedPathFilter)
				&& !selectedPathFilter.equals("*")
				&& !selectedPathFilter.equals("*.*")
				&& !selectedPathFilter.equals(".*")
				&& !selectedPathFilter.equals("*.")) {
			buf.append("+(");
			String[] pathFilters = selectedPathFilter.split(";");
			for (String pathFilter : pathFilters) {
				pathFilter = pathFilter.trim();
				if (StringUtils.isNotEmpty(pathFilter) && !pathFilter.equals("*") && !pathFilter.equals("*.*") && !pathFilter.equals(".*") && !pathFilter.equals("*.")) {

					if (pathFilter.charAt(0) == '*') {
						pathFilter = "\\/" + pathFilter;
					}

					buf.append("(objectPath:" + pathFilter + ")");
				}
			}
			buf.append(")");
		}

		Integer sizeMinObj = (Integer) textFieldSizeMin.getValue();
		Integer sizeMaxObj = (Integer) textFieldSizeMax.getValue();
		// +(size:[* TO 7000])
		if (sizeMinObj != null || sizeMaxObj != null) {
			buf.append("+(size:[");
			if (sizeMinObj != null) {
				buf.append(Long.parseLong(sizeMinObj.toString()) * getSizeUnitFactor());
			} else {
				buf.append("*");
			}
			buf.append(" TO ");
			if (sizeMaxObj != null) {
				buf.append(Long.parseLong(sizeMaxObj.toString()) * getSizeUnitFactor());
			} else {
				buf.append("*");
			}
			buf.append("])");
		}

		if (txtDateFrom.getText().length() > 8 || txtDateTo.getText().length() > 8) {
			int[] dateFromObj = DateUtils.splitDateToInt(txtDateFrom.getText(), "/");
			int[] dateToObj = DateUtils.splitDateToInt(txtDateTo.getText(), "/");

			buf.append("+(ingestTime:[");
			if (dateFromObj != null && dateFromObj.length == 3) {
				buf.append((int) (DateUtils.createDate(dateFromObj[0], dateFromObj[1], dateFromObj[2]).getTime() / 1000));
			} else {
				buf.append("*");
			}
			buf.append(" TO ");
			if (dateToObj != null && dateToObj.length == 3) {
				buf.append((int) (DateUtils.createDateAt23h59m59s(dateToObj[0], dateToObj[1], dateToObj[2]).getTime() / 1000));
			} else {
				buf.append("*");
			}
			buf.append("])");
		}

		// +(customMetadataContent:xxx)
		String meta = tfMetadataContent.getText();
		if (StringUtils.isNotEmpty(meta)) {
			buf.append("+(customMetadataContent:" + meta + ")");
		}

//		System.out.println(buf);

		return buf.toString();
	}

	// public void setCurrentNamespace(String currentNamespace) {
	// this.currentNamespace = currentNamespace;
	// }

	@Override
	public void switchWorkingSpace(WorkingSpace workingspace) {
		String tenant = ((HCPFileSystemEntryConfig) workingspace.getFileSystemEntry().getEntryConfig()).getTenant();
		String bucket = workingspace.getItemspace().getName();
		this.currentNamespace = bucket + "." + tenant;
	}
}
