package com.it.ocs.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.model.RoleUserModel;
import com.it.ocs.sys.service.IRoleUserService;
import com.it.ocs.sys.vo.RoleUserVO;
@Service
public class RoleUserService extends BaseService implements IRoleUserService {
	@Override
	public OperationResult batchAdd(RoleUserVO roleUserVO) {
		OperationResult result = new OperationResult();
		try {
			String[] roleIds = roleUserVO.getRoleIds().split(",");
			List<RoleUserModel> roleUserModels = Lists.newArrayList();
			CollectionUtil.each(roleIds, new IAction<String>() {
				@Override
				public void excute(String obj) {
					if (!StringUtils.isEmpty(obj.trim())) {
						RoleUserModel roleUserModel = new RoleUserModel(roleUserVO.getUserId(),Long.parseLong(obj));
						roleUserModels.add(roleUserModel);
					}
				}
			});
			if (!CollectionUtil.isNullOrEmpty(roleUserModels)) {
				roleUserDAO.batchAdd(roleUserModels);
				result.setDescription("save success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorCode(1);
			result.setDescription("save error");
		}
		return result;
	}
	
}
