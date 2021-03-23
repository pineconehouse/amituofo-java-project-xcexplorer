package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.amituofo.common.util.StringUtils;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;
import com.amituofo.xcexplorer.core.ui.action.SwitchWorkingSpaceAction;
import com.amituofo.xcexplorer.core.ui.compoent.StandardItemSearchConditionPanel;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.HCPFileSystemEntryConfig;
import com.amituofo.xfs.service.FolderItem;

public class HCPQueryScriptSimplePanel extends HCPQueryScriptPanel implements SwitchWorkingSpaceAction {
	private StandardItemSearchConditionPanel itemSearchPanel;
	private HCPMetadataQueryPanel hcpMetadataQueryPanel;

	public HCPQueryScriptSimplePanel() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		scrollPane.setViewportView(panel_2);

		itemSearchPanel = new StandardItemSearchConditionPanel();
		itemSearchPanel.getFileScopePanel().setEnableFindInSub(false);
		panel_2.add(itemSearchPanel);

		hcpMetadataQueryPanel = new HCPMetadataQueryPanel();
		panel_2.add(hcpMetadataQueryPanel);
	}

	@Override
	public void clear() {
		itemSearchPanel.clear();
		hcpMetadataQueryPanel.clear();
	}

	@Override
	public String toExpression() {
		StringBuilder bufCondNamespace = new StringBuilder();
		StringBuilder bufCondObjectPath = new StringBuilder();
		StringBuilder bufCondName = new StringBuilder();
		StringBuilder bufCondSize = new StringBuilder();
		StringBuilder bufCondDate = new StringBuilder();
		StringBuilder bufCondMeta = new StringBuilder();
		StringBuilder[] conds = new StringBuilder[] { bufCondNamespace, bufCondObjectPath, bufCondName, bufCondSize, bufCondDate, bufCondMeta };

		// --------------------------------------------------------------------------------------------------------------------
		List<FolderItem> searchInFolders = itemSearchPanel.getFileScopePanel().getSearchInFolders();
		if (searchInFolders != null) {
			// 添加搜索桶条件
			// bufCondNamespace.append("+(");
			for (FolderItem folderItem : searchInFolders) {
				String tenant = ((HCPFileSystemEntryConfig) folderItem.getFileSystemEntry().getEntryConfig()).getTenant();
				String bucket = folderItem.getItemspace().getName();
				String currentNamespace = bucket + "." + tenant;

				// 去除重复的
				if (bufCondNamespace.indexOf(currentNamespace) == -1) {
					bufCondNamespace.append("(namespace:\"" + currentNamespace + "\")");
				}
			}
			// bufCondNamespace.append(")");
		}
		// --------------------------------------------------------------------------------------------------------------------

		// --------------------------------------------------------------------------------------------------------------------
		if (searchInFolders != null) {
			// 添加搜索目录
			// bufCondObjectPath.append("+(");
			for (FolderItem folderItem : searchInFolders) {
				String path = folderItem.getPath();

				// 去除重复的和根目录
				if (!"".equals(path) && !"/".equals(path) && !"\\".equals(path) && bufCondObjectPath.indexOf(path) == -1) {
					bufCondObjectPath.append("(objectPath:\"" + path + "\")");
				}
			}
			// bufCondObjectPath.append(")");
		}

		// --------------------------------------------------------------------------------------------------------------------
		String selectedPathFilter = itemSearchPanel.getFilenamePanel().getFilenamePattern();
		selectedPathFilter = selectedPathFilter.trim().replace("**", "*");
		if (StringUtils.isNotEmpty(selectedPathFilter)
				&& !selectedPathFilter.equals("*")
				&& !selectedPathFilter.equals("*.*")
				&& !selectedPathFilter.equals(".*")
				&& !selectedPathFilter.equals("*.")) {
			String[] pathFilters = selectedPathFilter.split(";");
			for (String pathFilter : pathFilters) {
				pathFilter = pathFilter.trim();
				if (StringUtils.isNotEmpty(pathFilter) && !pathFilter.equals("*") && !pathFilter.equals("*.*") && !pathFilter.equals(".*") && !pathFilter.equals("*.")) {

					if (pathFilter.charAt(0) == '*') {
						pathFilter = "\\/" + pathFilter;
					}

					bufCondName.append("(objectPath:" + pathFilter + ")");
				}
			}
			// buf.append(")");
		}
		// --------------------------------------------------------------------------------------------------------------------

		Long sizeMinObj = itemSearchPanel.getFilesizePanel().getMinSizeInByte();
		Long sizeMaxObj = itemSearchPanel.getFilesizePanel().getMaxSizeInByte();
		// +(size:[* TO 7000])
		if (sizeMinObj != null || sizeMaxObj != null) {
			bufCondSize.append("size:[");
			if (sizeMinObj != null) {
				bufCondSize.append(sizeMinObj);
			} else {
				bufCondSize.append("*");
			}
			bufCondSize.append(" TO ");
			if (sizeMaxObj != null) {
				bufCondSize.append(sizeMaxObj);
			} else {
				bufCondSize.append("*");
			}
			bufCondSize.append("]");
		}
		// --------------------------------------------------------------------------------------------------------------------

		Long datetimeFrom = itemSearchPanel.getFiledatePanel().getDatetimeFrom();
		Long datetimeTo = itemSearchPanel.getFiledatePanel().getDatetimeTo();
		if (datetimeFrom != null || datetimeTo != null) {
			bufCondDate.append("ingestTime:[");
			if (datetimeFrom != null) {
				bufCondDate.append((long) (datetimeFrom / 1000));
			} else {
				bufCondDate.append("*");
			}
			bufCondDate.append(" TO ");
			if (datetimeTo != null) {
				bufCondDate.append((long) (datetimeTo / 1000));
			} else {
				bufCondDate.append("*");
			}
			bufCondDate.append("]");
		}
		// --------------------------------------------------------------------------------------------------------------------

		// +(customMetadataContent:xxx)
		String meta = hcpMetadataQueryPanel.getMetaExpression();
		if (StringUtils.isNotEmpty(meta)) {
			bufCondMeta.append("+(customMetadataContent:" + meta + ")");
		}
		// --------------------------------------------------------------------------------------------------------------------

		StringBuilder exp = new StringBuilder();
		for (StringBuilder cond : conds) {
			if (cond.length() != 0) {
				exp.append("+(");
				exp.append(cond);
				exp.append(")");
			}
		}

		System.out.println(exp);

		return exp.toString();
	}

	@Override
	public void switchWorkingSpace(WorkingSpace workingspace) {
		itemSearchPanel.getFileScopePanel().setFileSystemEntry(workingspace.getFileSystemEntry(), workingspace.getItemspace());
	}

	public void setExplorerContainer(ContentExplorerContainer explorerContainer) {
		itemSearchPanel.setExplorerContainer(explorerContainer);
	}
}
