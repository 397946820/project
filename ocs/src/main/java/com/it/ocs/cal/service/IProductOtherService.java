package com.it.ocs.cal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.vo.ProductOtherVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface IProductOtherService {

	/**
	 * 搜索查找
	 * @param param
	 * @param flag 
	 * @return
	 */
	ResponseResult<ProductOtherVo> findAll(RequestParam param, Boolean flag);

	/**
	 * 修改
	 * @param productOther
	 * @return
	 */
	OperationResult edit(ProductOtherVo productOther);
	
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

	/**
	 * 根据父id查找
	 * @param param
	 * @return
	 */
	ResponseResult<ProductOtherVo> findByParentId(RequestParam param);

}
