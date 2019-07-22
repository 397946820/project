package com.it.ocs.sys.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.model.RoleModel;
import com.it.ocs.sys.vo.RoleVO;
import com.it.ocs.task.service.ISysCommonService;

public interface IRoleService extends ISysCommonService<RoleVO> {
	public ResponseResult<RoleVO> queryRole(RequestParam param);

	public OperationResult saveRole(RoleVO roleVO);

	public OperationResult removeRole(Long roleId);
	
	public List<RoleVO> findByDepartmentId(Long departmentId,Long userId);
	
	public OperationResult authorize(RoleVO roleVO);
	
	public List<RoleModel> queryByUserId(Long userId);
	
}
