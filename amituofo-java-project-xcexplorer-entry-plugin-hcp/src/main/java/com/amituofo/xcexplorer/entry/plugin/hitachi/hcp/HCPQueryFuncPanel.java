package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.amituofo.common.ex.ServiceException;
import com.amituofo.common.ui.action.RefreshAction;
import com.amituofo.common.ui.action.SelectionAction;
import com.amituofo.common.ui.swingexts.component.CardContainerPanel;
import com.amituofo.common.ui.swingexts.component.JIntegerTextField;
import com.amituofo.common.ui.swingexts.component.SimpleDialog;
import com.amituofo.common.util.FormatUtils;
import com.amituofo.common.util.StringUtils;
import com.amituofo.common.util.SystemUtils;
import com.amituofo.task.TaskManagement;
import com.amituofo.xcexplorer.core.global.GlobalAction;
import com.amituofo.xcexplorer.core.global.GlobalContext;
import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.global.GlobalResource;
import com.amituofo.xcexplorer.core.global.SystemConfig;
import com.amituofo.xcexplorer.core.global.SystemConfigKeys;
import com.amituofo.xcexplorer.core.lang.ItemMigrationOperation;
import com.amituofo.xcexplorer.core.lang.ItemPackage;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;
import com.amituofo.xcexplorer.core.ui.ContentViewer;
import com.amituofo.xcexplorer.core.ui.FunctionBar;
import com.amituofo.xcexplorer.core.ui.compoent.ChooseTargetFolderItemPanel;
import com.amituofo.xcexplorer.core.ui.compoent.container.LogicItemExplorerContainer;
import com.amituofo.xcexplorer.core.ui.frame.FunctionTabPanel;
import com.amituofo.xcexplorer.entry.plugin.hitachi.mqe.MQEWorkingSpaceBuilder;
import com.amituofo.xcexplorer.entry.plugin.hitachi.task.HCPTaskFactory;
import com.amituofo.xcexplorer.util.ItemUtils;
import com.amituofo.xcexplorer.util.UIUtils;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.HCPFileSystemEntry;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.HCPFileSystemEntryConfig;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.MQEFileSystemEntry;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.MQEFileSystemEntryConfig;
import com.amituofo.xfs.plugin.fs.objectstorage.mqe.item.MQEQueryRequest;
import com.amituofo.xfs.service.FolderItem;
import com.amituofo.xfs.service.Item;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

public class HCPQueryFuncPanel extends FunctionTabPanel {
	private JIntegerTextField textFieldPageSize;
	private HCPQueryScriptAdvancePanel textAreaQueryString;
	private HCPQueryScriptSimplePanel simpleQuery;
	private WorkingSpace workingspace;

	public HCPQueryFuncPanel() {
		super("Query Object", GlobalIcons.ICON_MQE_QUERY_TAB_16x16);
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		final CardContainerPanel<HCPQueryScriptPanel, String> layeredPane = new CardContainerPanel<HCPQueryScriptPanel, String>();
		// JPanel layeredPane = new JPanel();
		add(layeredPane, BorderLayout.CENTER);
		// layeredPane.setLayout(new CardLayout(0, 0));
		// layeredPane.setLayout(new CardLayout(0, 0));

		textAreaQueryString = new HCPQueryScriptAdvancePanel();
		layeredPane.addDataComponent("advanceQuery", textAreaQueryString, "");
		// layeredPane.add("advanceQuery",textAreaQueryString);

		// layeredPane.add("simpleQuery",simpleQuery);
		simpleQuery = new HCPQueryScriptSimplePanel();
		layeredPane.addDataComponent("simpleQuery", simpleQuery, "");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("641px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.PREF_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("25px"),
				FormSpecs.PREF_ROWSPEC,
				RowSpec.decode("25px:grow"),}));

		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setFloatable(false);
		panel.add(toolBar_1, "1, 1, left, fill");

		ButtonGroup buttonGroup = new ButtonGroup();

		JRadioButton rdbtnSimpleQuery = new JRadioButton("Simple Query");
		rdbtnSimpleQuery.setSelected(true);
		rdbtnSimpleQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchTo("simpleQuery");
				// ((CardLayout) layeredPane.getLayout()).show(layeredPane, "simpleQuery");
			}
		});
		buttonGroup.add(rdbtnSimpleQuery);
		toolBar_1.add(rdbtnSimpleQuery);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Structured Query");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setEnabled(false);
		toolBar_1.add(rdbtnNewRadioButton);

		JRadioButton radioButton = new JRadioButton("Advanced Query");
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchTo("advanceQuery");
				// ((CardLayout) layeredPane.getLayout()).show(layeredPane, "advanceQuery");
			}
		});
		buttonGroup.add(radioButton);
		toolBar_1.add(radioButton);

		JToolBar toolBar_2 = new JToolBar();
		toolBar_2.setFloatable(false);
		panel.add(toolBar_2, "3, 1, right, fill");

		JButton btnAdvance = new JButton("Settings");
		btnAdvance.setEnabled(false);
		btnAdvance.setIcon(GlobalIcons.ICON_XXXX_16x16);
		toolBar_2.add(btnAdvance);

		JSeparator separator_1 = new JSeparator();
		panel.add(separator_1, "1, 2, 3, 1, fill, center");

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panel.add(toolBar, "1, 3, left, fill");

		JButton btnQuery = new JButton("Query");
		btnQuery.setIcon(GlobalIcons.ICON_XXXX_16x16);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = layeredPane.getTopComponent().getComponent().toExpression();
				if (StringUtils.isEmpty(query)) {
					UIUtils.openWarning("Query script must be specificed!");
					return;
				}

				try {
					MQEFileSystemEntryConfig mqeConfig = new MQEFileSystemEntryConfig((HCPFileSystemEntry) workingspace.getFileSystemEntry());
					mqeConfig.setQueryExpression(query);
					mqeConfig.setPageSize((Integer) textFieldPageSize.getValue());

					WorkingSpace queryWorkingSpace = new MQEWorkingSpaceBuilder().createWorkingSpace(mqeConfig);
					LogicItemExplorerContainer logicItemExplorer = GlobalAction.standard().openLogicItemExplorer();
					ContentViewer viewer = logicItemExplorer.getActiveContentExplorer().openContentViewer(null, queryWorkingSpace);
					viewer.refresh();
				} catch (ServiceException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnQuery);

		JButton btnQueryCountOnly = new JButton("Count Only");
		btnQueryCountOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = layeredPane.getTopComponent().getComponent().toExpression();
				if (StringUtils.isEmpty(query)) {
					UIUtils.openWarning("Query script must be specificed!");
					return;
				}

				try {
					// xxxx
					MQEFileSystemEntryConfig mqeConfig = new MQEFileSystemEntryConfig((HCPFileSystemEntry) workingspace.getFileSystemEntry());
					mqeConfig.setQueryExpression(query);
					mqeConfig.setPageSize((Integer) textFieldPageSize.getValue());

					MQEFileSystemEntry entry = (MQEFileSystemEntry) mqeConfig.createFileSystemEntry();
					entry.open();
					// MQEQueryRequest request = entry.createBasedQueryRequest(query);
					MQEQueryRequest request = entry.getDefaultItemspace();
					// entry.getBasedQueryRequest().setQuery(query);
					// WorkingSpace queryFSSession = new WorkingSpace(entry);
					// queryFSSession.setTrue(SystemConfigKeys._DISABLE_RESET_ITEMTAB_TITLE_NAME_TO_WORKING_FOLDER_NAME_);
					// queryFSSession.setTrue(SystemConfigKeys._HIDDEN_CONTENT_PANEL_PATH_SELECTOR_);
					// queryFSSession.setTrue(SystemConfigKeys._HIDDEN_CONTENT_PANEL_ROOT_SELECTOR_);
					// HCPQuery hcpquery = entry.getHCPQuery();
					Integer count = request.queryCount();
					// Integer count = result.getStatus().getTotalResults();
					UIUtils.openInformation("Total [" + FormatUtils.formatNumber(count) + "] items found from HCP by query expression :" + query);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator);
		btnQueryCountOnly.setIcon(GlobalIcons.ICON_XXXX_16x16);
		toolBar.add(btnQueryCountOnly);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator_2);

		JButton btnExport = new JButton("Export");
		btnExport.setEnabled(false);
		btnExport.setIcon(GlobalIcons.ICON_XXXX_16x16);
		toolBar.add(btnExport);

		JButton btnCopyTo = new JButton("Copy To");
		btnCopyTo.setIcon(GlobalIcons.ICON_XXXX_16x16);
		btnCopyTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = layeredPane.getTopComponent().getComponent().toExpression();
				if (StringUtils.isEmpty(query)) {
					UIUtils.openWarning("Query script must be specificed!");
					return;
				}

				SimpleDialog dialog = (SimpleDialog) GlobalContext.getSession().getAttribute("TARGET_FOLDER_SELECTOR");
				if (dialog == null) {
					// GlobalAction.standard().copyItems(viewer);
					ChooseTargetFolderItemPanel panel = new ChooseTargetFolderItemPanel();
					dialog = new SimpleDialog(panel, 1000, 650);
					// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					GlobalContext.getSession().setAttribute("TARGET_FOLDER_SELECTOR", dialog);
				}
				((ChooseTargetFolderItemPanel) dialog.getContentPanel()).setSelectionListener(new SelectionAction<Item>() {

					@Override
					public boolean selected(Item item) {
						ItemPackage targetContentPackage = new ItemPackage((FolderItem) item, new Item[] {});

						ItemMigrationOperation operation = ItemMigrationOperation.valueOf(SystemConfig.getInstance()
								.getString(SystemConfigKeys.MESSAGE_CONFIRMATIONS_DEFAULT_ITEMS_MIGRATION_OPERATION, ItemMigrationOperation.OverwriteAll.name()));
						if (SystemConfig.getInstance().getBoolean(SystemConfigKeys.MESSAGE_CONFIRMATIONS_ITEMS_MIGRATION)) {
							operation = ItemMigrationOperation.AskMeFirst;
						}

						try {
							MQEFileSystemEntryConfig mqeConfig = new MQEFileSystemEntryConfig((HCPFileSystemEntry) workingspace.getFileSystemEntry());
							mqeConfig.setQueryExpression(query);
							mqeConfig.setPageSize((Integer) textFieldPageSize.getValue());

							MQEFileSystemEntry entry = (MQEFileSystemEntry) mqeConfig.createFileSystemEntry();
							entry.open();
							MQEQueryRequest request = entry.getDefaultItemspace();

							if (item.getSystemName().equals(HCPFileSystemEntryConfig.SYSTEM_NAME)
									&& ((HCPFileSystemEntry) item.getFileSystemEntry()).getEntryConfig().getEndpoint()
											.equals(((HCPFileSystemEntry) workingspace.getFileSystemEntry()).getEntryConfig().getEndpoint())) {
								final TaskManagement taskMgr = GlobalResource.getTaskmanagement();
								taskMgr.add(GlobalResource.getTaskFactory(HCPTaskFactory.class).createMQEObjectCopyingTask(request, targetContentPackage, operation));
								return true;
							} else {
								UIUtils.openInformation("Copy data to other file system supported by Enterprise Edition only!");
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						return false;
						// GlobalAction.standard().doCopyingItems(sourceContentPackage, targetContentPackage, null, operation);
					}
				});
				dialog.setVisible(true);
			}
		});
		toolBar.add(btnCopyTo);

		JButton btnCopy = new JButton("Copy Expression");
		btnCopy.setIcon(GlobalIcons.ICON_XXXX_16x16);
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = layeredPane.getTopComponent().getComponent().toExpression();
				SystemUtils.setClipboardString(query);
			}
		});

		JSeparator separator_2_1 = new JSeparator();
		separator_2_1.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator_2_1);
		toolBar.add(btnCopy);

		JButton btnReset = new JButton("Reset");
		btnReset.setIcon(GlobalIcons.ICON_CLEAR_16x16);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.getTopComponent().getComponent().clear();
			}
		});

		JSeparator separator_2_1_1 = new JSeparator();
		separator_2_1_1.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator_2_1_1);
		toolBar.add(btnReset);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, "3, 3, fill, fill");
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		panel_2.add(menuBar, BorderLayout.CENTER);
		
		JMenu mnResultAction = new JMenu("Query Result Action");
		menuBar.add(mnResultAction);
		
		JMenuItem mntmCopyTo = new JMenuItem("Copy To");
		mnResultAction.add(mntmCopyTo);
		layeredPane.switchTo("simpleQuery");

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("57px"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("20px"), FormSpecs.RELATED_GAP_ROWSPEC, }));

		JLabel lblNewLabel = new JLabel("Page Size:");
		panel_1.add(lblNewLabel, "2, 2, right, center");

		textFieldPageSize = new JIntegerTextField(1, 1000, true);
		textFieldPageSize.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(textFieldPageSize, "4, 2, fill, center");
		textFieldPageSize.setText("100");
		textFieldPageSize.setColumns(5);
		// textFieldPageSize.setEnabled(false);
	}

	@Override
	public void switchWorkingSpace(WorkingSpace workingspace) {
		this.workingspace = workingspace;
		simpleQuery.switchWorkingSpace(workingspace);
	}

	@Override
	public FunctionBar getFunctionBar() {
		return null;
	}

	@Override
	public void setExplorerContainer(ContentExplorerContainer explorerContainer) {
		super.setExplorerContainer(explorerContainer);
		textAreaQueryString.setExplorerContainer(explorerContainer);
		simpleQuery.setExplorerContainer(explorerContainer);
	}

	// public void setWorkingSpace(WorkingSpace workingSpace) {
	// // TODO Auto-generated method stub
	//
	// }
}
