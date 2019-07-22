package com.it.ocs.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.it.ocs.cache.UserCache;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.model.PermissionModel;
import com.it.ocs.sys.model.RoleModel;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.IPermissionService;
import com.it.ocs.sys.service.IRoleService;
import com.it.ocs.sys.service.IUserService;

public class LoginRealm extends AuthorizingRealm {
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IPermissionService permissionService;

	/**
	 * 为当前登录的Subject授予角色和权限
	 *
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser.getSession().getAttribute("user")) {
			UserModel userModel = (UserModel) currentUser.getSession().getAttribute("user");
			SimpleAuthorizationInfo cacheInfo = UserCache.getAuthMap().get(userModel.getUserName());
			if (null != cacheInfo) {
				return cacheInfo;
			}
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<RoleModel> roleModels = roleService.queryByUserId(userModel.getId());
			List<String> roleCodes = Lists.newArrayList();
			CollectionUtil.each(roleModels, new IAction<RoleModel>() {
				@Override
				public void excute(RoleModel roleModel) {
					if (!Strings.isNullOrEmpty(roleModel.getCode())) {
						roleCodes.add(roleModel.getCode());
					}
				}
			});
			if (!CollectionUtil.isNullOrEmpty(roleCodes)) {
				info.addRoles(roleCodes);
			}
			List<PermissionModel> permissionModels = permissionService.queryByUserId(userModel.getId());
			List<String> permissionCodes = Lists.newArrayList();
			CollectionUtil.each(permissionModels, new IAction<PermissionModel>() {
				@Override
				public void excute(PermissionModel permissionModel) {
					if (!Strings.isNullOrEmpty(permissionModel.getCode())) {
						permissionCodes.add(permissionModel.getCode());
					}
				}
			});
			if (!CollectionUtil.isNullOrEmpty(permissionCodes)) {
				info.addStringPermissions(permissionCodes);
			}
			UserCache.getAuthMap().put(userModel.getUserName(), info);
			return info;
		} else {
			throw new AuthorizationException();
		}
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		UserModel userModel = userService.getByName(token.getUsername());
		if (userModel != null) {
			String inputPassword = String.valueOf(token.getPassword());
			String md5Password = userService.md5(inputPassword);
			token.setPassword(md5Password.toCharArray());
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.getSession().setAttribute("user", userModel);
			currentUser.getSession().setTimeout(-1000L);
			return new SimpleAuthenticationInfo(userModel.getUserName(), userModel.getPassword(), getName());
		}

		return null;
	}
}
