package com.it.ocs.sys.vo;

import java.io.Serializable;

import com.it.ocs.sys.model.UserModel;

public class UserVO extends UserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -196920510906914609L;

	private String departmentName;

	private String roleName;

	private String roleIds;

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
