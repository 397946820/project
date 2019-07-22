package com.it.ocs.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.it.ocs.common.support.IAction;
import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.excel.utils.ExcelUtils;
import com.it.ocs.listener.ProjectApplicationContext;
import com.it.ocs.sys.service.impl.PermissionService;
import com.it.ocs.sys.vo.PermissionVO;

public class ColumnUtils {

	/**
	 * 获取页面列的权限
	 * @param string
	 * @param clazz  
	 * @return
	 */
	public static List<String> getPrivilegeColumn(String string,Class<?> clazz) {
		List<String> result = new ArrayList<>();
		PermissionService permissionService = ProjectApplicationContext.getBean("permissionService",PermissionService.class);
		PermissionVO permission = permissionService.getCurrentUserPermissionByCode(string);
		if(permission != null) {
			List<Field> fields = ExcelUtils.getField(clazz);
			List<String> list = new ArrayList<>();
			CollectionUtil.each(permission.getChildren(), new IAction<PermissionVO>() {
				@Override
				public void excute(PermissionVO model) {
					if(model.getPermissionType().equals("COLUMN")) {
						list.add(model.getName());
					}
				}
			});
			CollectionUtil.each(fields, new IAction<Field>() {
				@Override
				public void excute(Field field) {
					if(list.contains(field.getAnnotation(ExcelLink.class).title())) {
						result.add(field.getName());
					}
				}
			});
		}
		return result;
	}
	
}
