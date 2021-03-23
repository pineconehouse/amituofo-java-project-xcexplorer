package com.amituofo.xcexplorer.entry.plugin.hitachi.hcp.model;

import java.util.ArrayList;

import com.amituofo.common.api.ObjectHandler;
import com.amituofo.common.define.HandleFeedback;
import com.amituofo.common.util.StringUtils;
import com.amituofo.xcexplorer.entry.plugin.osd.core.OSDACLPermission;
import com.amituofo.xcexplorer.entry.plugin.osd.core.OSDACLStatusCell;
import com.amituofo.xcexplorer.entry.plugin.osd.core.OSDACLUserType;
import com.amituofo.xcexplorer.entry.plugin.osd.core.ui.model.OSDACLModel;
import com.amituofo.xfs.plugin.fs.objectstorage.hcp.item.HCPFileItem;
import com.amituofo.xfs.service.ItemEvent;
import com.hitachivantara.hcp.standard.define.ACLDefines;
import com.hitachivantara.hcp.standard.define.ACLDefines.ACLPermission;
import com.hitachivantara.hcp.standard.model.metadata.AccessControlList;
import com.hitachivantara.hcp.standard.model.metadata.PermissionGrant;

public class HCPACLModel extends OSDACLModel<PermissionGrant> { // AbstractTableModel
	public HCPACLModel() {
	}

	@Override
	public void updateObjectACL(OSDACLStatusCell<PermissionGrant> aclStatusCell, boolean on) throws Exception {
		HCPFileItem object = (HCPFileItem) aclStatusCell.getObject();
		AccessControlList acl = object.getHcpClient().getObjectACL(object.getKey());

		ACLPermission permission = toAclPermission(aclStatusCell.getDefinedAclPermission());
		Object[] userInfo = getUserInfo(aclStatusCell);
		if (on) {
			acl.grantPermissions((ACLDefines.Type) userInfo[0], (String) userInfo[1], (String) userInfo[2], permission);
		} else {
			acl.removePermissions((ACLDefines.Type) userInfo[0], (String) userInfo[1], (String) userInfo[2], permission);
		}

		object.getHcpClient().setObjectACL(object.getKey(), acl);
	}

	private Object[] getUserInfo(OSDACLStatusCell<PermissionGrant> osdAclStatusCell) {
		Object[] value = new Object[3];

		OSDACLUserType type = osdAclStatusCell.getType();
		if (OSDACLUserType.USER == type) {
			value[0] = ACLDefines.Type.USER;
		} else if (OSDACLUserType.GROUP == type) {
			value[0] = ACLDefines.Type.GROUP;
		}

		String userName = osdAclStatusCell.getUser();
		int atIndex = userName.indexOf('@');
		value[1] = (atIndex == -1 ? userName : userName.substring(0, atIndex));
		value[2] = (atIndex == -1 ? "" : userName.substring(atIndex + 1));

		return value;
	}

	@Override
	protected boolean updateExist(PermissionGrant data) {
		return false;
	}

	private ACLPermission toAclPermission(OSDACLPermission osdACLPermission) {
		switch (osdACLPermission) {
			case READ:
				return ACLPermission.READ;
			case WRITE:
				return ACLPermission.WRITE;
			case READ_ACL:
				return ACLPermission.READ_ACL;
			case WRITE_ACL:
				return ACLPermission.WRITE_ACL;
			case DELETE:
				return ACLPermission.DELETE;
		}
		return null;
	}

	@Override
	public PermissionGrant[] newArray(int size) {
		return new PermissionGrant[size];
	}

	@Override
	protected OSDACLUserType getGrantUserType(PermissionGrant permission) {
		switch (permission.getType()) {
			case USER:
				return OSDACLUserType.USER;
			case GROUP:
				return OSDACLUserType.GROUP;
		}

		return null;
	}

	@Override
	protected String getGrantUserId(PermissionGrant permission) {
		return permission.getUserName() + (StringUtils.isNotEmpty(permission.getDomain()) ? "@" + permission.getDomain() : "");
	}

	@Override
	protected OSDACLPermission toOSDACLPermission(PermissionGrant permission) {
		return null;
	}

	@Override
	protected void list(com.amituofo.common.api.ObjectHandler<Integer, PermissionGrant> event, Object... args) {
		if (object != null) {
			final ArrayList<PermissionGrant> userlist = new ArrayList<PermissionGrant>();
			final ArrayList<PermissionGrant> alluserlist = new ArrayList<PermissionGrant>();
			final ArrayList<PermissionGrant> authuserlist = new ArrayList<PermissionGrant>();

			((HCPFileItem) object).listACL(new ObjectHandler<Integer, PermissionGrant>() {

				@Override
				public HandleFeedback handle(Integer eventType, PermissionGrant data) {

					if (eventType == ItemEvent.ITEM_FOUND) {
						if (data.getType() == ACLDefines.Type.GROUP) {
							if (ACLDefines.Name.ALL_USERS.equals(data.getUserName())) {
								alluserlist.add(data);
							} else if (ACLDefines.Name.AUTHENTICATED.equals(data.getUserName())) {
								authuserlist.add(data);
							}
						} else {
							userlist.add(data);
						}
					}
					return null;
				}

				@Override
				public void exceptionCaught(PermissionGrant data, Throwable e) {
				}
			});

			// PermissionGrant _ROW_PERMISSION_OWNER = (new PermissionGrant(ACLDefines.Type.GROUP, "", null, null));
			if (alluserlist.size() == 0) {
				PermissionGrant _ROW_PERMISSION_ALL_USERS = (new PermissionGrant(ACLDefines.Type.GROUP, ACLDefines.Name.ALL_USERS, null, null));
				alluserlist.add(_ROW_PERMISSION_ALL_USERS);
			}

			if (authuserlist.size() == 0) {
				PermissionGrant _ROW_PERMISSION_AUTHENTICATED = (new PermissionGrant(ACLDefines.Type.GROUP, ACLDefines.Name.AUTHENTICATED, null, null));
				authuserlist.add(_ROW_PERMISSION_AUTHENTICATED);
			}
			event.handle(ItemEvent.ITEM_FOUND, alluserlist.get(0));
			event.handle(ItemEvent.ITEM_FOUND, authuserlist.get(0));

			for (PermissionGrant permissionGrant : userlist) {
				event.handle(ItemEvent.ITEM_FOUND, permissionGrant);
			}
		}
	}

	@Override
	protected boolean hasPermission(PermissionGrant permission, OSDACLPermission osdACLPermission) {
		return permission.hasPermission(toAclPermission(osdACLPermission));
	}

}
