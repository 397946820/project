package com.it.ocs.sys.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.vo.ProjectInfoVO;
import com.it.ocs.task.service.ISysCommonService;

public interface IProjectInfoService extends ISysCommonService<ProjectInfoVO> {
	public ResponseResult<ProjectInfoVO> queryProject(RequestParam param);

	public OperationResult saveProject(ProjectInfoVO projectInfoVO);

	public OperationResult removeProject(Long projectId);

	public List<ProjectInfoVO> findAll();
	
	public List<ProjectInfoVO> findUserProject();
}
