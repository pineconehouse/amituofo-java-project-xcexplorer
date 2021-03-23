package com.amituofo.xcexplorer.entry.plugin.osd.core.ui;

import com.amituofo.common.ui.swingexts.component.ToggleButton;
import com.amituofo.xcexplorer.entry.plugin.osd.core.OSDACLPermission;

public class OSDACLToggleButton extends ToggleButton {
	OSDACLPermission aclPermission;

	public OSDACLToggleButton(OSDACLPermission aclPermission) {
		super();
		this.aclPermission = aclPermission;
	}

	public OSDACLPermission getAclPermission() {
		return aclPermission;
	}
	
	
}