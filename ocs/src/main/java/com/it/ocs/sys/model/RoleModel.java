package com.it.ocs.sys.model;

import com.it.ocs.common.model.BaseModel;

public class RoleModel extends BaseModel {
	private Long departmentId;
	private String code;

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
