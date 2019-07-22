package com.it.ocs.sys.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.vo.UserVO;
import com.it.ocs.task.service.ISysCommonService;

public interface IUserService extends ISysCommonService<UserVO> {
	public ResponseResult<UserVO> queryUser(RequestParam param);

	public OperationResult saveUser(UserVO userVO);

	public OperationResult removeUser(Long userId);
	
	public OperationResult castRole(UserVO userVO);	
	
	public UserModel getByName(String userName);
	
	public UserModel getConcurrentUser();

	public String md5(String password);
	
	public OperationResult changePwd(String password);
	
	public boolean valPwdIsTrue(String password);
	
	public OperationResult resetPwd(Long id);
	
	/**
	 * 根据当前用户所在部门获取部门用户，超级管理员获取所有用户
	 * @return
	 */
	public ResponseResult<UserVO> query(RequestParam param);
	
}
