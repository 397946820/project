package com.it.ocs.cal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.vo.ProductCostVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface IProductCostService {

	/**
	 * 搜索
	 * @param param
	 * @param flag 
	 * @return
	 */
	ResponseResult<ProductCostVo> findAll(RequestParam param, Boolean flag);

	/**
	 * 修改
	 * @param productCost
	 * @return
	 */
	OperationResult edit(ProductCostVo productCost);

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param template
	 * @param flag 
	 * @param list 
	 */
	void exportExcel(HttpServletRequest request, HttpServletResponse response, String template, Boolean flag, List<String> list);

	/**
	 * 导入
	 * @param file
	 * @return
	 */
	OperationResult uploadExcel(MultipartFile file);

}
