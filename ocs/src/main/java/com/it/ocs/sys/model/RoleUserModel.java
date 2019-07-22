package com.it.ocs.sys.model;

import com.it.ocs.common.model.BaseModel;

public class RoleUserModel extends BaseModel {
	private Long userId;
	private Long roleId;
	
	public RoleUserModel(){}
	public RoleUserModel(Long userId,Long roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
