package com.it.ocs.sys.service.impl;

import java.security.MessageDigest;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.model.DepartmentModel;
import com.it.ocs.sys.model.RoleModel;
import com.it.ocs.sys.model.RoleUserModel;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.IUserService;
import com.it.ocs.sys.vo.UserVO;

@Service
public class UserService extends BaseService implements IUserService {
	private final String DEFAULT_PWD = "123";
	@Override
	public ResponseResult<UserVO> queryUser(RequestParam param) {
		ResponseResult<UserVO> result = new ResponseResult<>();
		List<UserModel> userModels = userDAO.queryByPage(null, param.getStartRow(), param.getEndRow());
		List<UserVO> userVOs = CollectionUtil.beansConvert(userModels, UserVO.class);
		setRoleName(userVOs);
		setDepartmentName(userVOs);
		int count = userDAO.count(null);
		result.setRows(userVOs);
		result.setTotal(count);
		return result;
	}
	private void setDepartmentName(List<UserVO> userVOs) {
		List<Long> departmentIds = Lists.newArrayList();
		CollectionUtil.each(userVOs, new IAction<UserVO>() {
			@Override
			public void excute(UserVO userVO) {
				if (null != userVO.getDepartmentId()) {
					departmentIds.add(userVO.getDepartmentId());
				}
			}
		});
		if (!CollectionUtil.isNullOrEmpty(departmentIds)) {
			List<DepartmentModel> departmentModels = departmentDAO.queryByIds(departmentIds);
			CollectionUtil.each(userVOs, new IAction<UserVO>() {
				@Override
				public void excute(UserVO userVO) {
					DepartmentModel searchModel = CollectionUtil.search(departmentModels,
							new IFunction<DepartmentModel, Boolean>() {
								@Override
								public Boolean excute(DepartmentModel departmentModel) {
									return departmentModel.getId() == userVO.getDepartmentId()
											|| departmentModel.getId().equals(userVO.getDepartmentId());
								}
							});
					if (null != searchModel) {
						userVO.setDepartmentName(searchModel.getName());
					}
				}
			});
		}
	}

	private void setRoleName(List<UserVO> userVOs) {
		List<Long> userIds = Lists.newArrayList();
		CollectionUtil.each(userVOs, new IAction<UserVO>() {
			@Override
			public void excute(UserVO userVO) {
				userIds.add(userVO.getId());
			}
		});
		if (!CollectionUtil.isNullOrEmpty(userIds)) {
			List<RoleUserModel> roleUserModels = roleUserDAO.queryByUserIds(userIds);
			List<Long> roleIds = Lists.newArrayList();
			CollectionUtil.each(roleUserModels, new IAction<RoleUserModel>() {
				@Override
				public void excute(RoleUserModel roleUserModel) {
					roleIds.add(roleUserModel.getRoleId());
				}
			});
			if (!CollectionUtil.isNullOrEmpty(roleIds)) {
				List<RoleModel> roleModels = roleDAO.queryByIds(roleIds);
				CollectionUtil.each(userVOs, new IAction<UserVO>() {
					@Override
					public void excute(UserVO userVO) {
						List<RoleUserModel> searchRoleUserModels = CollectionUtil.searchList(roleUserModels,
								new IFunction<RoleUserModel, Boolean>() {
									@Override
									public Boolean excute(RoleUserModel roleUserModel) {
										return roleUserModel.getUserId().equals(userVO.getId())
												|| roleUserModel.getUserId() == userVO.getId();
									}
								});
						List<RoleModel> searchRoleModels = CollectionUtil.searchList(roleModels,
								new IFunction<RoleModel, Boolean>() {
									@Override
									public Boolean excute(RoleModel roleModel) {
										return null != CollectionUtil.search(searchRoleUserModels,
												new IFunction<RoleUserModel, Boolean>() {
													@Override
													public Boolean excute(RoleUserModel ruModel) {
														return roleModel.getId() == ruModel.getRoleId()
																|| roleModel.getId().equals(ruModel.getRoleId());
													}
												});
									}
								});
						if (!CollectionUtil.isNullOrEmpty(searchRoleModels)) {
							StringBuffer roleNameBuffer = new StringBuffer();
							CollectionUtil.each(searchRoleModels, new IAction<RoleModel>() {
								@Override
								public void excute(RoleModel roleModel) {
									roleNameBuffer.append(roleModel.getName() + ",");
								}
							});
							userVO.setRoleName(roleNameBuffer.substring(0, roleNameBuffer.lastIndexOf(",")));
						}
					}
				});
			}
		}
	}

	@Override
	public OperationResult saveUser(UserVO userVO) {
		OperationResult result = new OperationResult();
		try {
			if (null != userVO.getId()) {
				UserModel userModel = userDAO.getById(userVO.getId());
				BeanUtils.copyProperties(userModel, userVO, new String[] {"departmentId", "userCode", "email", "mobile","nick","userName" });
				updateInit(userVO);
				userDAO.update(userVO);
			} else {
				insertInit(userVO);
				userVO.setPassword(md5(userVO.getPassword()));
				userDAO.add(userVO);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("save error");
			e.printStackTrace();
		}
		return result;
	}
	@Override
	@Transactional
	public OperationResult removeUser(Long userId) {
		OperationResult result = new OperationResult();
		roleUserDAO.removeByUserId(userId);
		userDAO.delete(userId);
		result.setDescription("remove success");
		return result;
	}

	@Override
	public OperationResult castRole(UserVO userVO) {
		OperationResult result = new OperationResult();
		if (!StringUtils.isEmpty(userVO.getRoleIds())) {
			String[] roleIds = userVO.getRoleIds().split(",");
			List<RoleUserModel> datas = Lists.newArrayList();
			CollectionUtil.each(roleIds, new IAction<String>() {
				@Override
				public void excute(String roleIdStr) {
					if (!StringUtils.isEmpty(roleIdStr.trim())) {
						RoleUserModel roleUserModel = new RoleUserModel();
						roleUserModel.setUserId(userVO.getId());
						roleUserModel.setRoleId(Long.parseLong(roleIdStr));
						datas.add(roleUserModel);
					}
				}
			});
			if (!CollectionUtil.isNullOrEmpty(datas)) {
				roleUserDAO.removeByUserId(userVO.getId());
				roleUserDAO.batchAdd(datas);
			}
		}
		result.setDescription("用户授权成功");
		return result;
	}

	@Override
	public boolean existByParam(UserVO param) {
		UserModel userModel = userDAO.getByParam(param);
		if (null == param.getId()) {
			return null != userModel;
		} else {
			return null != userModel && userModel.getId().longValue() != param.getId().longValue();
		}
	}

	@Override
	public UserModel getByName(String userName) {
		return userDAO.getByName(userName);
	}
	/**
     * 密码MD5加密
     *
     * @param password
     * @return
     */
	@Override
    public String md5(String password) {
        StringBuffer sb = new StringBuffer(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes("utf-8"));

            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .toUpperCase().substring(1, 3));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

	@Override
	public OperationResult changePwd(String password) {
		OperationResult result = new OperationResult();
		UserModel userModel = getCurentUser();
		updateInit(userModel);
		userModel.setPassword(md5(password));
		userDAO.update(userModel);
		result.setDescription("密码修改成功!");
		return result;
	}

	@Override
	public boolean valPwdIsTrue(String password) {
		UserModel userModel = getCurentUser();
		return userModel.getPassword().equals(md5(password));
	}


	@Override
	public OperationResult resetPwd(Long id) {
		OperationResult result = new OperationResult();
		String md5String = md5(DEFAULT_PWD);
		UserModel userModel = userDAO.getById(id);
		if (null != userModel) {
			userModel.setPassword(md5String);
			userDAO.update(userModel);
		}
		result.setDescription("重置密码成功");
		return result;
	}


	@Override
	public ResponseResult<UserVO> query(RequestParam param) {
		UserModel userModel = getCurentUser();
		ResponseResult<UserVO> result = new ResponseResult<>();
		UserModel paramModel = new UserModel();
		if (!isAdministrator()) {
			paramModel.setDepartmentId(userModel.getDepartmentId());
		}
		List<UserModel> userModels = userDAO.queryByPage(paramModel, param.getStartRow(), param.getEndRow());
		List<UserVO> userVOs = CollectionUtil.beansConvert(userModels, UserVO.class);
		setRoleName(userVOs);
		setDepartmentName(userVOs);
		int count = userDAO.count(paramModel);
		result.setRows(userVOs);
		result.setTotal(count);
		return result;
	}
	public static void main(String[] args) {
		UserService us = new UserService();
		System.out.println(us.md5("123"));
	}
	@Override
	public UserModel getConcurrentUser() {
		return super.getCurentUser();
	}
	
}
