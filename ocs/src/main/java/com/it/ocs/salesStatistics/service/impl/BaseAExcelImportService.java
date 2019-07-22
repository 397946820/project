package com.it.ocs.salesStatistics.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;

import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.Counter;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.sys.model.UserModel;

public abstract class BaseAExcelImportService<T extends Object> extends AExcelImport {

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return !"CTN".equals(String.valueOf(row.get("stockCode")))
			   && this.strValueIsNotBlank(row, "orderNo", errorsMsg)
			   && this.strValueIsNotBlank(row, "itemId", errorsMsg)
			   && this.strValueIsNotBlank(row, "platformAccount", errorsMsg);
	}
	
	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * 判断String类型的值不是空值（null/empty/space）
	 * @param source 待判断的数据源
	 * @param key 待判断值的key
	 * @param errors 错误列表
	 * @return true-非空值; false-空值
	 */
	protected boolean strValueIsNotBlank(Map<String, Object> source, String key, List<String> errors) {
		boolean flag = StringUtils.isNotBlank((String) source.get(key));
		if(!flag) {
			errors.add("第" + String.valueOf(source.get("rowNumber")) + "行订单[" + key + "]为空。");
		}
		return flag;
	}

	
	/**
	 * 获取当前登录用户
	 * @return {@link com.it.ocs.sys.model.UserModel}的对象实例
	 */
	protected UserModel getCurrentUser() {
		Object user = SecurityUtils.getSubject().getSession().getAttribute("user");
		if(null == user) {
			throw new RuntimeException("当前用户未登陆，请登陆后操作！");
		}
		return (UserModel) user;
	}
	
	protected abstract void updateRow(Map<String, Object> row);
	
	protected abstract void catchUpdateRowEx(Exception e, Map<String, Object> row, List<String> errorsMsg);
	
	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		Counter counter = new Counter();
		CollectionUtil.each(rows, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> row) {
				try {
					updateRow(row);
					counter.beforePlus();
				} catch (Exception e) {
					catchUpdateRowEx(e, row, errorsMsg);
				}
			}
		});
		return counter.get();
	}
}
