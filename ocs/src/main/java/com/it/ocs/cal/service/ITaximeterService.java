package com.it.ocs.cal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.it.ocs.cal.vo.TaximeterVo;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.model.PermissionModel;
import com.it.ocs.sys.model.RoleModel;

public interface ITaximeterService {

	/**
	 * 搜索
	 * @param param
	 * @param flag 是否是系统管理员或者财务
	 * @param string 
	 * @return
	 */
	ResponseResult<TaximeterVo> findAll(RequestParam param, Boolean flag, String string) throws Exception;

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param template
	 * @param strings 用户字段的权限
	 * @param flag 
	 */
	void exportExcel(HttpServletRequest request, HttpServletResponse response, String template, List<String> strings, Boolean flag) throws Exception ;

}
