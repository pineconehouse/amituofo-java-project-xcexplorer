package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import com.amituofo.xcexplorer.core.global.GlobalIcons;
import com.amituofo.xcexplorer.core.space.WorkingSpace;
import com.amituofo.xcexplorer.core.ui.ItemExplorer;
import com.amituofo.xcexplorer.core.ui.frame.NavigationFunctionPanel;
import com.amituofo.xcexplorer.entry.plugin.osd.core.action.OSDItemspaceAction;

public class OSDNavToolbarPanel extends NavigationFunctionPanel {

	private JLabel namespaceName;

	public OSDNavToolbarPanel(final ItemExplorer itemExplorer, final OSDItemspaceAction action) {
		setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		JButton delBucketBtn = new JButton();
		delBucketBtn.setToolTipText("Delete " + action.getCustomItemspaceName());
		delBucketBtn.setIcon(GlobalIcons.ICON_DEL_NODE_16x16);
		delBucketBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action.deleteBucket(itemExplorer, workingspace);
			}
		});
		toolBar.add(delBucketBtn);

		JButton newBucketBtn = new JButton();
		newBucketBtn.setIcon(GlobalIcons.ICON_NEW_NODE_16x16);
		newBucketBtn.setToolTipText("Create new " + action.getCustomItemspaceName());
		newBucketBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action.createBucket(itemExplorer, workingspace);
			}
		});
		toolBar.add(newBucketBtn);
		add(toolBar, BorderLayout.EAST);

		namespaceName = new JLabel(GlobalIcons.ICON_DRIVER_16x16);
		add(namespaceName, BorderLayout.WEST);
	}

	@Override
	public void switchWorkingSpace(WorkingSpace workingspace) {
		super.switchWorkingSpace(workingspace);
		namespaceName.setText(workingspace.getItemspace().getName());
	}

}
