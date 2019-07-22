package com.it.ocs.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.sys.model.RoleModel;

@Component
public interface IRoleDAO extends IBaseDAO<RoleModel> {
	public RoleModel getByNameInDepartment(@Param(value = "roleName") String roleName,
			@Param(value = "departmentId") Long departmentId);

	public RoleModel getByCodeInDepartment(@Param(value = "roleCode") String roleCode,
			@Param(value = "departmentId") Long departmentId);
	
	public List<RoleModel> queryByUserId(@Param(value="userId") Long userId);
}
