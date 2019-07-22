package com.it.ocs.sys.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;

import com.it.ocs.cache.UserCache;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.listener.ProjectApplicationContext;
import com.it.ocs.sys.model.DepartmentModel;
import com.it.ocs.sys.model.RoleModel;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.impl.DepartmentService;
import com.it.ocs.sys.service.impl.RoleService;

public class CodeUtils {

	public static String getCode() {
		Subject subject = SecurityUtils.getSubject();
		UserModel userModel = (UserModel) subject.getSession().getAttribute("user");
		if (userModel.getDepartmentId() == null) {
			return "1";
		}
		RoleService roleService = ProjectApplicationContext.getBean("roleService", RoleService.class);
		List<RoleModel> roleModels = roleService.queryByUserId(userModel.getId());
		Set<String> roles = new HashSet<>();
		CollectionUtil.each(roleModels, new IAction<RoleModel>() {
			@Override
			public void excute(RoleModel role) {
				roles.add(role.getCode());
			}
		});
		if (roles.contains("ADMINISTRATOR")) {
			return "1";
		}
		DepartmentService departmentService = ProjectApplicationContext.getBean("departmentService",
				DepartmentService.class);
		DepartmentModel model = departmentService.getById(userModel.getDepartmentId());
		if (model.getCode().equals("AMAZONYY")) {
			if (roles.contains("BMJL")) {
				return "2";
			}
			return "3";
		} else if (model.getCode().equals("EBAYYY")) {
			return "4";
		} else if (model.getCode().equals("GW")) {
			return "5";
		} else if (model.getCode().equals("CPB")) {
			if (roles.contains("CPZJ")) {
				return "6";
			} else if (roles.contains("CPJL")) {
				return "7";
			}
		} else if (model.getCode().equals("CWB")) {
			if (roles.contains("CWZJ") || roles.contains("SWZG") || roles.contains("CWZG")) {
				return "1";
			}
		} else if (model.getCode().equals("ZJB")) {
			if (roles.contains("CEO") || roles.contains("ZJB")) {
				return "1";
			}
		}

		return null;
	}

}
