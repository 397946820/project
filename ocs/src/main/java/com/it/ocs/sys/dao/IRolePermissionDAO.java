package com.it.ocs.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.sys.model.RolePermissionModel;

@Component
public interface IRolePermissionDAO extends IBaseDAO<RolePermissionModel> {
	public List<RolePermissionModel> findByRoleId(@Param(value = "roleId") Long roleId);

	public int removeByRoleId(@Param(value = "roleId") Long roleId);
	
	public int batchAdd(@Param(value="list") List<RolePermissionModel> rolePermissionModels);
	
	public List<RolePermissionModel> findByRoleIds(@Param(value="roleIds") List<Long> roleIds);
}
