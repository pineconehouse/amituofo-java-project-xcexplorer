package com.amituofo.xcexplorer.entry.plugin.osd.core;

import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.model.OSDACLModel;
import com.amituofo.xfs.plugin.fs.objectstorage.OSDFileItem;

/**
 * @author sohan
 *
 * @param <GRANT_MODEL>
 */
public class OSDACLStatusCell<GRANT_MODEL> {
	private final OSDACLUserType type;
	private final String user;
	// private final String domain;
	private final OSDACLPermission osdAclPermission;
	private final OSDFileItem object;
	// private final GRANT_MODEL grantModel;
	private final OSDACLModel<GRANT_MODEL> osdaclModel;

	private boolean permissionOn;

	public OSDACLStatusCell(OSDACLPermission aclPermission, OSDACLModel<GRANT_MODEL> osdaclModel, OSDFileItem object, OSDACLUserType type, String user, boolean isPermissionOn) {
		super();
		this.osdaclModel = osdaclModel;
		this.object = object;
		// this.grantModel = grantModel;
		this.type = type;
		// this.domain = domain;
		this.user = user;
		this.osdAclPermission = aclPermission;
		this.permissionOn = isPermissionOn;
	}

	public OSDACLUserType getType() {
		return type;
	}

	public String getUser() {
		return user;
	}

	public OSDFileItem getObject() {
		return object;
	}

	public OSDACLModel<GRANT_MODEL> getOSDACLModel() {
		return osdaclModel;
	}

	public boolean isPermissionOn() {
		return permissionOn;
	}

	public void setPermissionOn(boolean hasPermission) {
		this.permissionOn = hasPermission;
	}

	public OSDACLPermission getDefinedAclPermission() {
		return osdAclPermission;
	}

	// public GRANT_MODEL getGrantModel() {
	// return grantModel;
	// }

	// public void setPermissionModel(PERMISSION_MODEL permissionModel) {
	// this.permissionModel = permissionModel;
	// }

	// public void setAclPermission(OSDACLPermission aclPermission) {
	// this.aclPermission = aclPermission;
	// }

}
