package com.it.ocs.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.sys.model.RoleUserModel;

public interface IRoleUserDAO extends IBaseDAO<RoleUserModel> {
	public int batchAdd(List<RoleUserModel> roleUserModels);

	public int removeByUserId(@Param(value = "userId") Long userId);

	public List<RoleUserModel> queryByUserId(@Param(value = "userId") Long userId);

	public List<RoleUserModel> queryByUserIds(@Param(value = "userIds") List<Long> userIds);
}
