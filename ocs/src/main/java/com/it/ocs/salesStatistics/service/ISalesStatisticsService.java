package com.it.ocs.salesStatistics.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.vo.SalesStatisticsAllVo;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;
import com.it.ocs.sys.vo.PermissionVO;

public interface ISalesStatisticsService {

	/**
	 * 分页以及条件搜索查询
	 * 
	 * @param param
	 * @param list
	 * @param permissionVO
	 * @param flag
	 * @param list2 
	 * @return
	 */
	ResponseResult<SalesStatisticsVo> findAll(RequestParam param, List<String> list, PermissionVO permissionVO,
			Boolean flag, String string, List<String> list2) throws Exception;
	
	/**
	 * 全平台汇总
	 * @param param
	 * @param columns
	 * @param permissionVO
	 * @param allSourceFlag
	 * @param string
	 * @param columns2
	 * @return
	 */
	ResponseResult<SalesStatisticsAllVo> findAllSoure(RequestParam param, List<String> columns,
			PermissionVO permissionVO, Boolean allSourceFlag, String string, List<String> columns2) throws Exception;

	/**
	 * sku详情
	 * 
	 * @param param
	 * @return
	 */
	ResponseResult<SalesStatisticsVo> querySkuDetails(RequestParam param,String string) throws Exception;

	/**
	 * 销售列表
	 * 
	 * @param param
	 * @return
	 */
	ResponseResult<SalesStatisticsVo> findOrderDetails(RequestParam param) throws Exception;

	/**
	 * 导出
	 * 
	 * @param request
	 * @param response
	 * @param list
	 * @param flag
	 * @param permissionVO
	 * @param list2 
	 */
	void exportExcel(HttpServletRequest request, HttpServletResponse response, String source, List<String> list,
			PermissionVO permissionVO, Boolean flag, List<String> list2);

	/**
	 * 导出详情
	 * 
	 * @param request
	 * @param response
	 * @param list
	 */
	void detailsExcel(HttpServletRequest request, HttpServletResponse response, String param, List<String> list);

	List<String> getEbayStation();


}
