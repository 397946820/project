package com.it.ocs.sys.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.vo.DepartmentVO;
import com.it.ocs.task.service.ISysCommonService;

public interface IDepartmentService extends ISysCommonService<DepartmentVO> {
	public ResponseResult<DepartmentVO> queryDepartment(RequestParam param);
	
	public OperationResult saveDepartment(DepartmentVO departmentVO);
	
	public OperationResult removeDepartment(Long departmentId);
	
	public List<DepartmentVO> findAll();
	
}
