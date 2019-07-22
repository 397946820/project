package com.it.ocs.sys.model;

import com.it.ocs.common.model.BaseModel;

public class RolePermissionModel extends BaseModel {
	private Long roleId;
	private Long permissionId;

	public RolePermissionModel(){}
	public RolePermissionModel(Long roleId,Long permissionId) {
		this.roleId = roleId;
		this.permissionId = permissionId;
	}
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

}
