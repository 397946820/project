package com.it.ocs.salesStatistics.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;
import com.it.ocs.sys.vo.PermissionVO;

public interface ISalesOrderService {
	
	/**
	 * 
	 * @param param
	 * @param permissionVO
	 * @param allSourceFlag
	 * @param string 
	 * @return
	 */
	ResponseResult<SalesStatisticsVo> findAll(RequestParam param, PermissionVO permissionVO, Boolean allSourceFlag, String string) throws Exception;

	/**
	 * 
	 * @param request
	 * @param response
	 * @param param 
	 * @param flag 
	 * @param permissionVO 
	 */
	void exportExcel(HttpServletRequest request, HttpServletResponse response, String param, PermissionVO permissionVO, Boolean flag) throws Exception;

}
