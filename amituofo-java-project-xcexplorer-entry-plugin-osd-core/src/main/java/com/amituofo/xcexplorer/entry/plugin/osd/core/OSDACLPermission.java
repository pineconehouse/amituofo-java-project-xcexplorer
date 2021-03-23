package com.amituofo.xcexplorer.entry.plugin.osd.core;

public enum OSDACLPermission {
	FULL_CONTROL,
	/**
	 * Read Retrieve objects and system metadata Check for object existence List annotations Check for and retrieve custom metadata
	 */
	READ,
	/**
	 * Write Store objects Create directories Set and change system and custom metadata
	 */
	WRITE,
	/**
	 * Read_ACL Check for and retrieve ACLs
	 */
	READ_ACL,
	/**
	 * Write_ACL Set and change ACLs
	 */
	WRITE_ACL,
	/**
	 * Delete Delete objects, custom metadata, and ACLs
	 */
	DELETE
}