package com.it.ocs.sys.vo;

import java.io.Serializable;

import com.it.ocs.sys.model.RoleUserModel;

public class RoleUserVO extends RoleUserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8818806615117859068L;
	
	private String roleIds;

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	

}
