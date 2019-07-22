package com.it.ocs.cache;

import java.util.Map;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

import com.google.common.collect.Maps;

public class UserCache {
	private static final Map<String, SimpleAuthorizationInfo> AUTHMAP = Maps.newConcurrentMap();
	
	public static Map<String,SimpleAuthorizationInfo> getAuthMap() {
		return AUTHMAP;
	}
}
