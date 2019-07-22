package com.it.ocs.sys.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.sys.model.PermissionModel;
import com.it.ocs.sys.vo.PermissionVO;
import com.it.ocs.task.service.ISysCommonService;

public interface IPermissionService extends ISysCommonService<PermissionVO> {
	public List<PermissionVO> query();
	
	public OperationResult savePermission(PermissionVO permissionVO);
	
	public OperationResult removePermission(Long picCategoryId);
	
	public List<PermissionVO> menu(Long projectId);
	
	public List<PermissionVO> queryAuthorize(Long roleId);
	
	public List<PermissionModel> queryByUserId(Long userId);
	
	public PermissionVO getCurrentUserPermissionByCode(String permissionCode);
}
