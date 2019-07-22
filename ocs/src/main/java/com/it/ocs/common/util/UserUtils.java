package com.it.ocs.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.it.ocs.sys.model.UserModel;

public class UserUtils {

	//获取用户的id
	public static Long getUserId() {
		Subject subject = SecurityUtils.getSubject();
		UserModel userModel = (UserModel) subject.getSession().getAttribute("user");
		return userModel.getId();
	}
	
	public static Long getUserIdByFlag(Boolean flag) {
		Long userId = null;
		if(flag) {
			userId = -1L;
		} else {
			userId = getUserId();
		}
		return userId;
	}
}
