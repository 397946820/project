package com.it.ocs.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.sys.model.PermissionModel;
import com.it.ocs.sys.vo.PermissionVO;

@Component
public interface IPermissionDAO extends IBaseDAO<PermissionModel> {
	public int refresh();
	
	public List<PermissionModel> queryMenuByProjectId(@Param(value = "projectId") Long projectId);

	public List<PermissionModel> queryFilterByParam(@Param(value = "param") PermissionModel permissionModel);

	public void batchModifyProjectId(@Param(value = "permissions") List<PermissionVO> permissions,@Param(value="projectId") Long projectId);
}
