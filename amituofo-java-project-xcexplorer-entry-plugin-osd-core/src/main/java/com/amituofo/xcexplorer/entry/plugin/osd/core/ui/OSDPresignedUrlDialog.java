package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DateFormatter;

import org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.common.util.StringUtils;
import com.amituofo.common.util.SystemUtils;
import com.amituofo.xcexplorer.core.global.GlobalResource;
import com.amituofo.xcexplorer.core.lang.ItemPackage;
import com.amituofo.xcexplorer.core.ui.ItemViewer;
import com.amituofo.xcexplorer.core.ui.frame.ItemsBatchDialogPanel;
import com.amituofo.xcexplorer.entry.plugin.osd.core.OSDPresignedUrlGenerator;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.service.FileItem;
import com.amituofo.xfs.service.Item;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class OSDPresignedUrlDialog extends ItemsBatchDialogPanel {

	private JFormattedTextField txtDateFrom;
	private JCalendarButton datePickerFrom;
	private JComboBox<String> comboBox;
	final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnExpireAfter;
	private JRadioButton rdbtnExpireAt;
	private JSpinner spinner;
	private JComboBox<Action> comboBoxMethod;
	private String title;
	private ImageIcon icon;
	private OSDPresignedUrlGenerator generator;

	public static enum Action {
		Download("[GET]"), Upload("[PUT]"), Delete("[DELETE]");

		private String httpmethod;

		Action(String httpmethod) {
			this.httpmethod = httpmethod;
		}

		public String toString() {
			return httpmethod;
		}
	}

	public OSDPresignedUrlDialog(ItemViewer viewer, String title, ImageIcon icon, String description, OSDPresignedUrlGenerator generator, Action... actions) {
		super(viewer);
		this.title = title;
		this.icon = icon;
		this.generator = generator;
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.PREF_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));

		if (StringUtils.isNotEmpty(description)) {
			JLabel lblAUserWho = new JLabel("<html><body>" + description + "<body></html>");
			lblAUserWho.setHorizontalAlignment(SwingConstants.LEFT);
			add(lblAUserWho, "2, 2, left, fill");
		}

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Http Method", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panel_1, "2, 4, fill, fill");
		panel_1.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("20px"), FormSpecs.RELATED_GAP_ROWSPEC, }));

		final JLabel lblNewLabel = new JLabel();
		comboBoxMethod = new JComboBox<Action>();
		comboBoxMethod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = comboBoxMethod.getSelectedIndex();
				if (selected == 0) {
					lblNewLabel.setText("Presigned url for download object.");
				} else if (selected == 1) {
					lblNewLabel.setText("Presigned url for upload data to object.");
				} else {
					lblNewLabel.setText("Presigned url for delete object.");
				}
			}
		});
		comboBoxMethod.setModel(new DefaultComboBoxModel<Action>((actions == null || actions.length == 0) ? Action.values() : actions));
		comboBoxMethod.setSelectedIndex(0);
		panel_1.add(comboBoxMethod, "2, 2, fill, fill");

		panel_1.add(lblNewLabel, "4, 2, left, fill");

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Expiration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "2, 6, fill, fill");
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(39dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(31dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		rdbtnExpireAfter = new JRadioButton("Expire after");
		rdbtnExpireAfter.setSelected(true);
		buttonGroup.add(rdbtnExpireAfter);
		panel.add(rdbtnExpireAfter, "2, 2, fill, fill");

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(5, 1, 10000, 1));
		panel.add(spinner, "4, 2, fill, fill");

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Minutes", "Hours" }));
		panel.add(comboBox, "6, 2, fill, fill");

		rdbtnExpireAt = new JRadioButton("Expire At");
		buttonGroup.add(rdbtnExpireAt);
		panel.add(rdbtnExpireAt, "2, 4, fill, fill");

		txtDateFrom = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")));
		txtDateFrom.setToolTipText("Input format : yyyy/MM/dd hh:mm:ss");
		txtDateFrom.setHorizontalAlignment(SwingConstants.CENTER);
		Date date = new Date();
		date.setTime(date.getTime() + 5 * 60 * 1000);
		try {
			txtDateFrom.setText(txtDateFrom.getFormatter().valueToString(date));
		} catch (ParseException e1) {
		}

		panel.add(txtDateFrom, "4, 4, 3, 1, fill, fill");

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
		panel.add(datePickerFrom, "8, 4, fill, fill");

		rdbtnExpireAfter.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				comboBox.setEnabled(rdbtnExpireAfter.isSelected());
				spinner.setEnabled(rdbtnExpireAfter.isSelected());
				txtDateFrom.setEnabled(rdbtnExpireAt.isSelected());
				datePickerFrom.setEnabled(rdbtnExpireAt.isSelected());
			}
		});

		rdbtnExpireAt.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				comboBox.setEnabled(rdbtnExpireAfter.isSelected());
				spinner.setEnabled(rdbtnExpireAfter.isSelected());
				txtDateFrom.setEnabled(rdbtnExpireAt.isSelected());
				datePickerFrom.setEnabled(rdbtnExpireAt.isSelected());
			}
		});

		comboBox.setEnabled(rdbtnExpireAfter.isSelected());
		spinner.setEnabled(rdbtnExpireAfter.isSelected());
		txtDateFrom.setEnabled(rdbtnExpireAt.isSelected());
		datePickerFrom.setEnabled(rdbtnExpireAt.isSelected());
	}

	@Override
	public boolean okPressed() {
		Date expireAt = new Date();
		if (rdbtnExpireAfter.isSelected()) {
			int timeUnit = 1000;
			String unit = (String) comboBox.getSelectedItem();
			if ("Minutes".equals(unit)) {
				timeUnit = timeUnit * 60;
			} else {
				timeUnit = timeUnit * 3600;
			}

			int time = ((Number) spinner.getValue()).intValue();

			long expireAtMillis = expireAt.getTime();
			expireAtMillis += (time * timeUnit);
			expireAt.setTime(expireAtMillis);
		} else {
			Date pickDate = datePickerFrom.getTargetDate();
			if (pickDate.compareTo(expireAt) <= 0) {
				UIUtils.openError("Expire date time must be future!");
				return false;
			}
			expireAt = pickDate;
		}

		List<URL> genUrls = new ArrayList<URL>();
		try {
			ItemPackage pkg = this.getItemViewer().getItemPackage();
			Item[] items = pkg.getItems();
			for (Item item : items) {
				if (item.isFile()) {
					Action selected = (Action) comboBoxMethod.getSelectedItem();
					boolean read = (Action.Download == selected);
					boolean write = (Action.Upload == selected);
					boolean delete = (Action.Delete == selected);

					URL url = generator.generatePresignedUrl((FileItem) item, read, write, delete, expireAt);
					// System.out.println(expireAt + " url=" + url);
					GlobalResource.getLogger().info(url);
					genUrls.add(url);
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			UIUtils.openAndLogError("Failed to generate presigned url!", e);
		}

		if (genUrls.size() > 0) {
			SystemUtils.setClipboardString(genUrls);
			UIUtils.openInformation("Presigned url copied to [Clipboard], You can get them from [Notification] tab also!");
		}

		return true;
	}

	@Override
	public boolean cancelPressed() {
		return true;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public ImageIcon getIcon() {
		return icon;
	}

}
