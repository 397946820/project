package com.it.ocs.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.model.PermissionModel;
import com.it.ocs.sys.model.RoleModel;
import com.it.ocs.sys.service.IPermissionService;
import com.it.ocs.sys.service.IRoleService;
import com.it.ocs.sys.vo.PermissionVO;

public class IBaseController {

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private IRoleService roleService;

	/**
	 * 获取用户字段的权限
	 * 
	 * @param userId
	 *            用户id
	 * @param string
	 *            哪个页面的字段
	 * @return
	 */
	public List<String> getColumns(Long userId, String string) {
		List<PermissionModel> models = permissionService.queryByUserId(userId);
		List<String> strings = new ArrayList<>();
		List<PermissionModel> result = new ArrayList<>();
		if (!CollectionUtil.isNullOrEmpty(models)) {
			for (PermissionModel model : models) {
				if (model.getPermissionType().equals("COLUMN")) {
					if (model.getCode().startsWith(string)) {
						if (string.equals("XSTJ_") || string.equals("XSTJXQ_")) {
							if (model.getOrderNum() == null) {
								continue;
							}
							result.add(model);
						} else {
							strings.add(model.getName());
						}
					}
				}
			}
			if (!CollectionUtil.isNullOrEmpty(result)) {
				try {
					CollectionUtil.sort(result, "orderNum", "asc");
					for (PermissionModel model : result) {
						strings.add(model.getName());
					}
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
		}

		return strings;
	}

	/**
	 * 判断用户是否是超级管理员或者财务人员
	 * 
	 * @param userId
	 *            用户的id
	 * @return
	 */
	public Boolean isSYSorCWRY(Long userId) {
		Boolean flag = false;
		List<RoleModel> models = roleService.queryByUserId(userId);
		List<String> strings = new ArrayList<>();
		if (!CollectionUtil.isNullOrEmpty(models)) {
			for (RoleModel model : models) {
				strings.add(model.getCode());
			}
		}
		if (!CollectionUtil.isNullOrEmpty(strings)) {
			if (strings.contains("ADMINISTRATOR") || strings.contains("CWZJ") || strings.contains("CEO")
					|| strings.contains("CWZJ") || strings.contains("SWZG") || strings.contains("SWCW")
					|| strings.contains("CWZG2") || strings.contains("ZZKJ") || strings.contains("CWZG") 
					|| strings.contains("CPZJ")) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 判断用户是否有所有平台的权限
	 * 
	 * @param userId
	 *            用户的id
	 * @return
	 */
	public Boolean isAllSourceFlag(Long userId) {
		Boolean flag = false;
		List<RoleModel> models = roleService.queryByUserId(userId);
		List<String> strings = new ArrayList<>();
		if (!CollectionUtil.isNullOrEmpty(models)) {
			for (RoleModel model : models) {
				strings.add(model.getCode());
			}
		}

		if (!CollectionUtil.isNullOrEmpty(strings)) {
			if (strings.contains("ADMINISTRATOR") || strings.contains("CEO") || strings.contains("SJZXZG")
					|| strings.contains("SJZXZZ") || strings.contains("CWZJ") || strings.contains("SWZG")
					|| strings.contains("SWCW") || strings.contains("CWZG2") || strings.contains("ZZKJ")
					|| strings.contains("CWZG")) {
				flag = true;
			}
		}
		return flag;
	}

	public PermissionVO getPermissionVO(String string) {
		return permissionService.getCurrentUserPermissionByCode(string);
	}
}
