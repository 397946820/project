package com.it.ocs.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.it.ocs.cache.UserCache;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.model.DepartmentModel;
import com.it.ocs.sys.model.RoleModel;
import com.it.ocs.sys.model.RolePermissionModel;
import com.it.ocs.sys.model.RoleUserModel;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.IRoleService;
import com.it.ocs.sys.vo.RoleVO;

@Service
public class RoleService extends BaseService implements IRoleService {

	@Override
	public ResponseResult<RoleVO> queryRole(RequestParam param) {
		ResponseResult<RoleVO> result = new ResponseResult<>();
		List<RoleModel> roleModels = roleDAO.queryByPage(null, param.getStartRow(), param.getEndRow());
		List<RoleVO> roleVOs = CollectionUtil.beansConvert(roleModels, RoleVO.class);
		setDepartmentName(roleVOs);
		int count = roleDAO.count(null);
		result.setRows(roleVOs);
		result.setTotal(count);
		return result;
	}

	private void setDepartmentName(List<RoleVO> roleVOs) {
		List<Long> departmentIds = Lists.newArrayList();
		CollectionUtil.each(roleVOs, new IAction<RoleVO>() {
			@Override
			public void excute(RoleVO roleVO) {
				departmentIds.add(roleVO.getDepartmentId());
			}
		});
		if (!CollectionUtil.isNullOrEmpty(departmentIds)) {
			List<DepartmentModel> departmentModels = departmentDAO.queryByIds(departmentIds);
			CollectionUtil.each(roleVOs, new IAction<RoleVO>() {
				@Override
				public void excute(RoleVO roleVO) {
					DepartmentModel searchModel = CollectionUtil.search(departmentModels,
							new IFunction<DepartmentModel, Boolean>() {
								@Override
								public Boolean excute(DepartmentModel departmentModel) {
									return departmentModel.getId() == roleVO.getDepartmentId()
											|| departmentModel.getId().equals(roleVO.getDepartmentId());
								}
							});
					if (null != searchModel) {
						roleVO.setDepartmentName(searchModel.getName());
					}
				}
			});
		}
	}

	@Override
	public OperationResult saveRole(RoleVO roleVO) {
		OperationResult result = new OperationResult();
		try {
			if (null != roleVO.getId()) {
				RoleModel roleModel = roleDAO.getById(roleVO.getId());
				BeanUtils.copyProperties(roleModel, roleVO, new String[] { "departmentId", "code", "name" });
				updateInit(roleVO);
				roleDAO.update(roleVO);
			} else {
				insertInit(roleVO);
				roleDAO.add(roleVO);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("save error");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult removeRole(Long roleId) {
		OperationResult result = new OperationResult();
		try {
			roleDAO.delete(roleId);
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("remove error");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<RoleVO> findByDepartmentId(Long departmentId,Long userId) {
		RoleModel param = new RoleModel();
		param.setDepartmentId(departmentId);
		List<RoleModel> models = roleDAO.query(param);
		List<RoleVO> result = CollectionUtil.beansConvert(models, RoleVO.class);
		List<RoleModel> roleModels = queryByUserId(userId);
		CollectionUtil.each(result, new IAction<RoleVO>() {
			@Override
			public void excute(RoleVO roleVO) {
				RoleModel roleM = CollectionUtil.search(roleModels, new IFunction<RoleModel, Boolean>() {
					@Override
					public Boolean excute(RoleModel roleModel) {
						return roleVO.getCode().equals(roleModel.getCode());
					}
				});
				if (null != roleM) {
					roleVO.setChecked(true);
				}
			}
		});
		return result;
	}

	@Override
	public OperationResult authorize(RoleVO roleVO) {
		OperationResult result = new OperationResult();
		String[] idArray = roleVO.getPermissionIds().split(",");
		List<String> idStr = new ArrayList<String>();
		for (String id : idArray) {
			if (!idStr.contains(id)) {
				idStr.add(id);
			}
		}
		List<RolePermissionModel> rolePermissionModels = Lists.newArrayList();
		CollectionUtil.each(idStr, new IAction<String>() {
			@Override
			public void excute(String obj) {
				if (!StringUtils.isEmpty(obj)) {
					RolePermissionModel model = new RolePermissionModel(roleVO.getId(), Long.parseLong(obj));
					rolePermissionModels.add(model);
				}
			}
		});
		if (!CollectionUtil.isNullOrEmpty(rolePermissionModels)) {
			rolePermissionDAO.removeByRoleId(roleVO.getId());
			rolePermissionDAO.batchAdd(rolePermissionModels);
			result.setDescription("授权成功");
			UserCache.getAuthMap().clear();
		} else {
			result.setDescription("授权失败");
		}
		return result;
	}

	@Override
	public boolean existByParam(RoleVO param) {
		RoleModel roleModel = roleDAO.getByParam(param);
		if (null == param.getId()) {
			return null != roleModel;
		} else {
			return null != roleModel && roleModel.getId().longValue() != param.getId().longValue();
		}

	}

	@Override
	public List<RoleModel> queryByUserId(Long userId) {
		List<RoleUserModel> roleUserModels = roleUserDAO.queryByUserId(userId);
		if (!CollectionUtil.isNullOrEmpty(roleUserModels)) {
			List<Long> roleIds = Lists.newArrayList();
			CollectionUtil.each(roleUserModels, new IAction<RoleUserModel>() {
				@Override
				public void excute(RoleUserModel roleUserModel) {
					roleIds.add(roleUserModel.getRoleId());
				}
			});
			if (!CollectionUtil.isNullOrEmpty(roleIds)) {
				return roleDAO.queryByIds(roleIds);
			}
		}
		return null;
	}

}
