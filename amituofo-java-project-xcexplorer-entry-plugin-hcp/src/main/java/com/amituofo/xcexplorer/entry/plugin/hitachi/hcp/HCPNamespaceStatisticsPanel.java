package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.amituofo.common.ui.util.SystemUtils;
import com.amituofo.common.util.FormatUtils;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.global.GlobalUI;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.frame.NavigationFunctionPanel;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileSystemEntryConfig;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPItemBase;
import com.hitachivantara.hcp.common.ex.InvalidResponseException;
import com.hitachivantara.hcp.standard.api.HCPNamespace;
import com.hitachivantara.hcp.standard.model.HCPObjectSummary;
import com.hitachivantara.hcp.standard.model.NamespaceBasicSetting;
import com.hitachivantara.hcp.standard.model.NamespaceStatistics;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class HCPNamespaceStatisticsPanel extends NavigationFunctionPanel {
	private UsedCapacity usedPercent;
	private JTextField textUsedCapacity;
	private JTextField textTotalCapacity;
	private JTextField textIngestedCount;
	private JTextField textSoftQuotaPercent;
	private JLabel statVersion;
	private JLabel statShred;
	private JLabel statSearch;
	private JLabel statIndex;
	private JTextField textHashScheme;
	private JTextField textDPL;
	private JTextField textHCPVersion;
	private JTextField textDesc;
	private HCPNamespace client;
	private Timer timer = null;
	private String endpoint;

	public HCPNamespaceStatisticsPanel() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);

		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.PREF_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("32dlu:grow"),
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.PREF_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("center:33dlu:grow"), },
				new RowSpec[] { RowSpec.decode("14px"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));

		usedPercent = new UsedCapacity();
		panel.add(usedPercent, "1, 1, 7, 1, fill, fill");

		JLabel lblNewLabel = new JLabel("Used capacity: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel, "1, 3, right, default");

		textUsedCapacity = new JTextField();
		textUsedCapacity.setHorizontalAlignment(SwingConstants.CENTER);
		textUsedCapacity.setText("-");
		textUsedCapacity.setEditable(false);
		panel.add(textUsedCapacity, "3, 3, fill, default");
		textUsedCapacity.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Total capacity: ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_1, "5, 3, right, default");

		textTotalCapacity = new JTextField();
		textTotalCapacity.setHorizontalAlignment(SwingConstants.CENTER);
		textTotalCapacity.setText("-");
		textTotalCapacity.setEditable(false);
		panel.add(textTotalCapacity, "7, 3, fill, default");
		textTotalCapacity.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Ingested count: ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_2, "1, 5, right, default");

		textIngestedCount = new JTextField();
		textIngestedCount.setHorizontalAlignment(SwingConstants.CENTER);
		textIngestedCount.setEditable(false);
		panel.add(textIngestedCount, "3, 5, fill, default");
		textIngestedCount.setColumns(10);
		textIngestedCount.setText("-");

		JLabel lblNewLabel_3 = new JLabel("Soft Quota: ");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_3, "5, 5, right, default");

		textSoftQuotaPercent = new JTextField();
		textSoftQuotaPercent.setHorizontalAlignment(SwingConstants.CENTER);
		textSoftQuotaPercent.setText("-");
		textSoftQuotaPercent.setEditable(false);
		panel.add(textSoftQuotaPercent, "7, 5, fill, default");
		textSoftQuotaPercent.setColumns(10);

		JLabel lblHashalgorithm_1 = new JLabel("Hash Algorithm: ");
		lblHashalgorithm_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblHashalgorithm_1, "1, 7, right, default");

		textHashScheme = new JTextField();
		textHashScheme.setHorizontalAlignment(SwingConstants.CENTER);
		textHashScheme.setEditable(false);
		textHashScheme.setColumns(10);
		textHashScheme.setText("-");
		panel.add(textHashScheme, "3, 7, fill, default");

		JLabel lblDpl = new JLabel("DPL: ");
		lblDpl.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblDpl, "5, 7, right, default");

		textDPL = new JTextField();
		textDPL.setText("-");
		textDPL.setHorizontalAlignment(SwingConstants.CENTER);
		textDPL.setEditable(false);
		textDPL.setColumns(10);
		panel.add(textDPL, "7, 7, fill, default");

		JLabel lblNewLabel_5 = new JLabel("HCP Version:");
		panel.add(lblNewLabel_5, "1, 9, right, default");

		textHCPVersion = new JTextField();
		textHCPVersion.setText("-");
		textHCPVersion.setHorizontalAlignment(SwingConstants.CENTER);
		textHCPVersion.setEditable(false);
		textHCPVersion.setColumns(10);
		panel.add(textHCPVersion, "3, 9, fill, default");

		JLabel lblDescription = new JLabel("Description:");
		panel.add(lblDescription, "5, 9, right, default");

		textDesc = new JTextField();
		textDesc.setText("-");
		textDesc.setHorizontalAlignment(SwingConstants.CENTER);
		textDesc.setEditable(false);
		textDesc.setColumns(10);
		panel.add(textDesc, "7, 9, fill, default");

		JSeparator separator = new JSeparator();
		panel.add(separator, "1, 11, 7, 1");

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 4, 0, 0));

		statVersion = createStatus("Versioning");
		panel_1.add(statVersion);

		statIndex = createStatus("Index");
		panel_1.add(statIndex);

		statSearch = createStatus("Search");
		panel_1.add(statSearch);

		statShred = createStatus("Shred");
		panel_1.add(statShred);

		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panel_2.add(toolBar);

		JButton btnHcpConsole = new JButton();
		btnHcpConsole.setIcon(GlobalIcons.ICON_OPEN_TENANT_CONSOLE_24x24);
		btnHcpConsole.setToolTipText("Open HCP Tenant Console");
		btnHcpConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SystemUtils.openWebpage(new URL("https://" + endpoint + ":8000"));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnHcpConsole);

		JButton button = new JButton();
		button.setIcon(GlobalIcons.ICON_OPEN_SYSTEM_CONSOLE_24x24);
		button.setToolTipText("Open HCP System Console");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String domain = "admin." + endpoint.substring(endpoint.indexOf('.') + 1);
					SystemUtils.openWebpage(new URL("https://" + domain + ":8000"));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(button);

		 JButton btnConfiguration = new JButton();
		 btnConfiguration.setIcon(GlobalIcons.ICON_OPEN_NAMESPACE_SETTING_24x24);
		 btnConfiguration.setEnabled(false);
		 toolBar.add(btnConfiguration);

		JButton btnMonitor = new JButton();
		btnMonitor.setIcon(GlobalIcons.ICON_OPEN_MONIROT_24x24);
		btnMonitor.setEnabled(false);
		toolBar.add(btnMonitor);

	}

	@Override
	public void deactiving() {
		super.deactiving();
		disableAutomaticUpdate();
	}

	@Override
	public void activing() {
		super.activing();
		enableAutomaticUpdate();
	}

	@Override
	public void switchWorkingSpace(WorkingSpace workingSpace) {
		OSDFileSystemEntryConfig config = (OSDFileSystemEntryConfig) workingSpace.getFileSystemEntry().getEntryConfig();
		endpoint = config.getEndpoint();

		client = ((HCPItemBase) workingSpace.getHomeFolder()).getHcpClient();

		updateStatistic();

		disableAutomaticUpdate();
		enableAutomaticUpdate();
	}

	public void enableAutomaticUpdate() {
		if (timer != null) {
			timer.cancel();
		}

		timer = new Timer("HCP Namespace Statistics Update Timer");

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				updateStatistic();
			}
		}, 500, 5000);
	}

	public void disableAutomaticUpdate() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private void updateStatistic() {
		if (client != null) {
			try {
				NamespaceStatistics ns = client.getNamespacesStatistics();

				textUsedCapacity.setText(FormatUtils.getPrintSize(ns.getUsedCapacityBytes(), false));
				textTotalCapacity.setText(FormatUtils.getPrintSize(ns.getTotalCapacityBytes(), false));
				textIngestedCount.setText(FormatUtils.formatNumber(ns.getObjectCount()));
				textSoftQuotaPercent.setText(FormatUtils.getPercent(ns.getSoftQuotaPercent() / (double) 100, 1));

				usedPercent.setCapacity(ns, ns.getSoftQuotaPercent());

				// ------------------------------------

				NamespaceBasicSetting nsetting = client.getNamespaceSetting();
				setEnabled(statVersion, nsetting.isVersioningEnabled());
				setEnabled(statSearch, nsetting.isSearchEnabled());
				setEnabled(statShred, nsetting.isDefaultShredValue());
				setEnabled(statIndex, nsetting.isDefaultIndexValue());

				textHashScheme.setText(nsetting.getHashScheme());
				textDPL.setText(nsetting.getDpl());
				textDesc.setText(nsetting.getDescription());

				HCPObjectSummary summary = client.getObjectSummary("/");
				textHCPVersion.setText(summary.getHcpVersion());

			} catch (InvalidResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private JLabel createStatus(String text) {
		JLabel lblNewLabel_4 = new JLabel(text);
		lblNewLabel_4.setFont(new Font(GlobalUI.getDefaultFont().getFamily(), Font.BOLD, 11));
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBorder(new LineBorder(new Color(230, 230, 250)));

		return lblNewLabel_4;
	}

	private void setEnabled(JLabel lab, boolean enable) {
		if (enable) {
			lab.setForeground(new Color(255, 255, 255));
			// lab.setBackground(new Color(100, 149, 237));
			lab.setBackground(new Color(60, 179, 113));
			lab.setToolTipText("Function Enabled!");
		} else {
			lab.setForeground(new Color(192, 192, 192));
			lab.setBackground(new Color(105, 105, 105));
			lab.setToolTipText("Function Disabled!");
		}
	}

}

class UsedCapacity extends JProgressBar {
	private final static Color CLR_NORMAL = new Color(65, 105, 225);
	private final static Color CLR_ALERT = new Color(255, 99, 71);
	private final static Color CLR_EXCEED = new Color(220, 20, 60);

	public UsedCapacity() {
		super();
		this.setStringPainted(true);
		this.setForeground(CLR_NORMAL);
		this.setMinimum(0);
		this.setMaximum(100);
	}

	public void setCapacity(NamespaceStatistics ns, double softQuote) {
		double percent = (((double) ns.getUsedCapacityBytes() / ns.getTotalCapacityBytes()) * 100);

		if (percent >= 100) {
			this.setForeground(CLR_EXCEED);
			this.setToolTipText(FormatUtils.getPercent(percent / 100, 2) + " Capacity used. EXCEEDED!");
		} else if (percent >= softQuote) {
			this.setForeground(CLR_ALERT);
			this.setToolTipText(FormatUtils.getPercent(percent / 100, 2) + " Capacity used. OVER SOFT QUOTA!");
		} else {
			this.setToolTipText(FormatUtils.getPercent(percent / 100, 2) + " Capacity used.");
		}

		this.setValue((int) percent);
		this.repaint();

	}

}