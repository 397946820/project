package com.it.ocs.sys.vo;

import java.io.Serializable;

import com.it.ocs.sys.model.RoleModel;

public class RoleVO extends RoleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1924552933263146951L;

	private String departmentName;
	private String permissionIds;
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
