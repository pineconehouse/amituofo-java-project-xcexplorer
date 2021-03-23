package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp;

import java.awt.BorderLayout;

import javax.swing.JTextArea;

import com.amituofo.xcexplorer.core.ui.ContentExplorerContainer;

public class HCPQueryScriptAdvancePanel extends HCPQueryScriptPanel {

	private JTextArea textArea;
	private ContentExplorerContainer explorerContainer;

	public HCPQueryScriptAdvancePanel() {
		setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		add(textArea, BorderLayout.CENTER);
		// textArea.setText("+(namespace:\"ns1.tenant1\") +(objectPath:log)");
	}

	@Override
	public void clear() {
		textArea.setText("");
	}

	@Override
	public String toExpression() {
		return textArea.getText();
	}

	public void setDefaultQueryScript(String string) {
		textArea.setText(string);
	}

	public void setExplorerContainer(ContentExplorerContainer explorerContainer) {
		this.explorerContainer = explorerContainer;
	}

}
