package com.it.ocs.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.model.PermissionModel;
import com.it.ocs.sys.model.ProjectInfoModel;
import com.it.ocs.sys.model.RolePermissionModel;
import com.it.ocs.sys.model.RoleUserModel;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.IPermissionService;
import com.it.ocs.sys.service.IProjectInfoService;
import com.it.ocs.sys.service.support.PermissionSupport;
import com.it.ocs.sys.vo.PermissionVO;
import com.it.ocs.sys.vo.ProjectInfoVO;

@Service
public class PermissionService extends BaseService implements IPermissionService {
	@Autowired
	private IProjectInfoService projectSerivce;

	@Override
	public List<PermissionVO> query() {
		List<PermissionVO> result = PermissionSupport.dataRestructure(findAll());
		PermissionSupport.sort(result);
		return result;
	}

	private List<PermissionVO> findAll() {
		List<PermissionModel> allPermissions = permissionDAO.query(null);
		List<Long> projectIds = Lists.newArrayList();
		Map<Long, ProjectInfoModel> maps = Maps.newConcurrentMap();
		List<PermissionVO> result = CollectionUtil.beansConvert(allPermissions, PermissionVO.class);
		CollectionUtil.each(allPermissions, new IAction<PermissionModel>() {
			@Override
			public void excute(PermissionModel permissionModel) {
				projectIds.add(permissionModel.getProjectId());
			}
		});
		if (!CollectionUtil.isNullOrEmpty(projectIds)) {
			CollectionUtil.each(projectInfoDAO.queryByIds(projectIds), new IAction<ProjectInfoModel>() {
				@Override
				public void excute(ProjectInfoModel projectModel) {
					maps.put(projectModel.getId(), projectModel);
				}
			});
			CollectionUtil.each(result, new IAction<PermissionVO>() {
				@Override
				public void excute(PermissionVO permissionVO) {
					permissionVO.setProjectName(maps.get(permissionVO.getProjectId()).getName());
				}
			});
		}
		return result;
	}

	@Override
	public OperationResult savePermission(PermissionVO permissionVO) {
		OperationResult result = new OperationResult();
		try {
			if (null != permissionVO.getId()) {
				PermissionModel model = permissionDAO.getById(permissionVO.getId());
				if (null != model.getParentId()) {
					BeanUtils.copyProperties(model, permissionVO,
							new String[] { "code", "name", "permissionType", "url", "orderNum" });
				} else {
					BeanUtils.copyProperties(model, permissionVO,
							new String[] { "code", "name", "permissionType", "projectId", "url", "orderNum" });
					if (permissionVO.getProjectId() != model.getProjectId()) {
						List<PermissionModel> allPermissions = permissionDAO.query(null);
						Map<Long, PermissionVO> maps = Maps.newConcurrentMap();
						maps.put(permissionVO.getId(), permissionVO);
						List<PermissionVO> childrens = PermissionSupport
								.findChildren(CollectionUtil.beansConvert(allPermissions, PermissionVO.class), maps);
						if (!CollectionUtil.isNullOrEmpty(childrens)) {
							CollectionUtil.each(childrens, new IAction<PermissionVO>() {
								@Override
								public void excute(PermissionVO permission) {
									permission.setProjectId(permissionVO.getProjectId());
								}
							});
							permissionDAO.batchModifyProjectId(childrens, permissionVO.getProjectId());
						}
					}
				}
				updateInit(permissionVO);
				permissionDAO.update(permissionVO);
			} else {
				insertInit(permissionVO);
				permissionDAO.add(permissionVO);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("save error");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult removePermission(Long permissionId) {
		OperationResult result = new OperationResult();
		try {
			List<Long> ids = Lists.newArrayList();
			List<PermissionModel> allPermissions = permissionDAO.query(null);
			PermissionSupport.findSubIds(ids, permissionId, allPermissions);
			permissionDAO.batchDel(ids);
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("remove error");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<PermissionVO> menu(Long projectId) {
		Subject currentUser = SecurityUtils.getSubject();
		List<PermissionVO> result = null;
		ProjectInfoModel projectInfo = null;
		if (null == projectId) {
			if (null != currentUser.getSession().getAttribute("project")) {
				projectInfo = (ProjectInfoModel) currentUser.getSession().getAttribute("project");
			} else {
				List<ProjectInfoVO> projects = projectSerivce.findAll();
				if (!CollectionUtil.isNullOrEmpty(projects)) {
					projectInfo = projects.get(0);
				}
			}
		} else {
			projectInfo = projectInfoDAO.getById(projectId);
		}
		if (null != projectInfo) {
			currentUser.getSession().setAttribute("project", projectInfo);
			Map<Long, PermissionVO> dataMap = Maps.newConcurrentMap();
			List<PermissionModel> menus = permissionDAO.queryMenuByProjectId(projectInfo.getId());
			CollectionUtil.each(CollectionUtil.beansConvert(menus, PermissionVO.class), new IAction<PermissionVO>() {
				@Override
				public void excute(PermissionVO permission) {
					dataMap.put(permission.getId(), permission);
				}
			});
			UserModel userModel = (UserModel) currentUser.getSession().getAttribute("user");
			List<RoleUserModel> roleUserModels = roleUserDAO.queryByUserId(userModel.getId());
			List<Long> roleIds = Lists.newArrayList();
			CollectionUtil.each(roleUserModels, new IAction<RoleUserModel>() {
				@Override
				public void excute(RoleUserModel roleUserModel) {
					roleIds.add(roleUserModel.getRoleId());
				}
			});
			if (!CollectionUtil.isNullOrEmpty(roleIds)) {
				List<RolePermissionModel> rolePermissionModels = rolePermissionDAO.findByRoleIds(roleIds);
				result = PermissionSupport.dataRestructure(PermissionSupport.filterPermission(rolePermissionModels, dataMap));
				PermissionSupport.sort(result);
			}
		}
		return result;
	}

	@Override
	public List<PermissionVO> queryAuthorize(Long roleId) {
		List<PermissionVO> result = Lists.newArrayList();
		List<PermissionVO> permissionVOs = findAll();
		List<PermissionVO> checkList = new ArrayList<>();
		PermissionSupport.setChecked(permissionVOs, rolePermissionDAO.findByRoleId(roleId));
		for (PermissionVO p : permissionVOs) {
			if (p.isChecked()) {
				System.out.println(p.getName() + ",id:" + p.getId());
				checkList.add(p);
			}
		}
		System.out.println(checkList.size());
		List<PermissionVO> groupDatas = PermissionSupport.dataRestructure(permissionVOs);
		PermissionSupport.sort(groupDatas);
		List<ProjectInfoModel> projectModels = projectInfoDAO.query(null);
		CollectionUtil.each(projectModels, new IAction<ProjectInfoModel>() {
			@Override
			public void excute(ProjectInfoModel projectInfoModel) {
				PermissionVO projectVO = new PermissionVO(projectInfoModel.getId(), projectInfoModel.getName());
				projectVO.getAttributes().put("groupId", "project_" + projectInfoModel.getId());
				projectVO.setState("closed");
				CollectionUtil.each(groupDatas, new IAction<PermissionVO>() {
					@Override
					public void excute(PermissionVO permission) {
						if (permission.getProjectId().equals(projectInfoModel.getId())
								|| permission.getProjectId() == projectInfoModel.getId()) {
							projectVO.getChildren().add(permission);
						}
					}
				});
				result.add(projectVO);
			}
		});
		PermissionSupport.setState(result);
		return result;
	}

	@Override
	public boolean existByParam(PermissionVO param) {
		List<PermissionModel> models = permissionDAO.queryFilterByParam(param);
		if (null == param.getId()) {
			return !CollectionUtil.isNullOrEmpty(models);
		} else {
			return !CollectionUtil.isNullOrEmpty(models)
					&& null == CollectionUtil.search(models, new IFunction<PermissionModel, Boolean>() {
						@Override
						public Boolean excute(PermissionModel obj) {
							return obj.getId().longValue() == param.getId().longValue() || obj.getId().equals(param.getId());
						}
					});
		}
	}

	@Override
	public List<PermissionModel> queryByUserId(Long userId) {
		List<RoleUserModel> roleUserModels = roleUserDAO.queryByUserId(userId);
		List<Long> roleIds = Lists.newArrayList();
		CollectionUtil.each(roleUserModels, new IAction<RoleUserModel>() {
			@Override
			public void excute(RoleUserModel roleUserModel) {
				roleIds.add(roleUserModel.getRoleId());
			}
		});
		if (!CollectionUtil.isNullOrEmpty(roleIds)) {
			List<RolePermissionModel> rolePermissionModels = rolePermissionDAO.findByRoleIds(roleIds);
			Map<Long, PermissionModel> dataMap = Maps.newConcurrentMap();
			CollectionUtil.each(permissionDAO.query(null), new IAction<PermissionModel>() {
				@Override
				public void excute(PermissionModel permission) {
					dataMap.put(permission.getId(), permission);
				}

			});
			return PermissionSupport.filterPermission2(rolePermissionModels, dataMap);
		}
		return null;
	}

	@Override
	public PermissionVO getCurrentUserPermissionByCode(String permissionCode) {
		UserModel userModel = getCurentUser();
		if (null == userModel || StringUtils.isBlank(permissionCode)) {
			return null;
		}
		List<PermissionModel> curentUserPermissions = queryByUserId(userModel.getId());
		List<PermissionVO> result = CollectionUtil.beansConvert(curentUserPermissions, PermissionVO.class);
		PermissionVO codeModel = CollectionUtil.search(result, new IFunction<PermissionVO, Boolean>() {
			@Override
			public Boolean excute(PermissionVO permissionModel) {
				return permissionCode.equals(permissionModel.getCode());
			}
		});
		if (null == codeModel) {
			return null;
		}
		PermissionSupport.setChildren(codeModel, result);
		return codeModel;
	}
	public void refreshP() {
		permissionDAO.refresh();
	}

}
