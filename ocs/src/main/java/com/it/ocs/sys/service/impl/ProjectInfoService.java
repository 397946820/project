package com.it.ocs.sys.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.model.PermissionModel;
import com.it.ocs.sys.model.ProjectInfoModel;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.IPermissionService;
import com.it.ocs.sys.service.IProjectInfoService;
import com.it.ocs.sys.vo.ProjectInfoVO;

@Service
public class ProjectInfoService extends BaseService implements IProjectInfoService {
	@Autowired
	private IPermissionService permissionService;
	@Override
	public ResponseResult<ProjectInfoVO> queryProject(RequestParam param) {
		ResponseResult<ProjectInfoVO> result = new ResponseResult<>();
		List<ProjectInfoModel> projectModels = projectInfoDAO.queryByPage(null, param.getStartRow(), param.getEndRow());
		List<ProjectInfoVO> projectVOs = CollectionUtil.beansConvert(projectModels, ProjectInfoVO.class);
		int count = projectInfoDAO.count(null);
		result.setRows(projectVOs);
		result.setTotal(count);
		return result;
	}

	@Override
	public OperationResult saveProject(ProjectInfoVO projectVO) {
		OperationResult result = new OperationResult();
		ProjectInfoModel projectModel = projectInfoDAO.getByProjectCode(projectVO.getCode());
		if (null != projectVO.getId()) {
			updateInit(projectVO);
			if (null != projectModel && !projectModel.getId().equals(projectVO.getId())) {
				result.setErrorCode(1);
				result.setDescription("项目code已存在，不能重复添加");
			} else {
				projectInfoDAO.update(projectVO);
			}
		} else {
			insertInit(projectVO);
			if (null == projectModel) {
				projectInfoDAO.add(projectVO);
			} else {
				result.setErrorCode(1);
				result.setDescription("项目code已存在，不能重复添加");
			}
		}
		return result;
	}

	@Override
	public OperationResult removeProject(Long projectId) {
		OperationResult result = new OperationResult();
		int count = projectInfoDAO.delete(projectId);
		if (count > 0) {
			result.setDescription("remove success");
		} else {
			result.setErrorCode(1);
			result.setDescription("remove error");
		}
		return result;
	}

	@Override
	public List<ProjectInfoVO> findUserProject() {
		Subject currentUser = SecurityUtils.getSubject();
		UserModel userModel = (UserModel)currentUser.getSession().getAttribute("user");
		List<PermissionModel> permissions = permissionService.queryByUserId(userModel.getId());
		List<ProjectInfoModel> models = projectInfoDAO.query(null);
		List<ProjectInfoModel> result = Lists.newArrayList();
		CollectionUtil.each(models, new IAction<ProjectInfoModel>() {
			@Override
			public void excute(ProjectInfoModel projectModel) {
				PermissionModel searchModel = CollectionUtil.search(permissions, new IFunction<PermissionModel, Boolean>() {
					@Override
					public Boolean excute(PermissionModel permissionModel) {
						return permissionModel.getProjectId() == projectModel.getId();
					}
				});
				if (null != searchModel) {
					result.add(projectModel);
				}
			}
		});
		if (!CollectionUtil.isNullOrEmpty(result) && null == currentUser.getSession().getAttribute("project")) {
			currentUser.getSession().setAttribute("project", result.get(0));
		}
		return CollectionUtil.beansConvert(result, ProjectInfoVO.class);
	}

	@Override
	public boolean existByParam(ProjectInfoVO param) {
		ProjectInfoModel model = projectInfoDAO.getByParam(param);
		if (null == param.getId()) {
			return null != model;
		} else {
			return null != model && model.getId() != param.getId();
		}
	}

	@Override
	public List<ProjectInfoVO> findAll() {
		List<ProjectInfoModel> models = projectInfoDAO.query(null);
		return CollectionUtil.beansConvert(models, ProjectInfoVO.class);
	}
}
