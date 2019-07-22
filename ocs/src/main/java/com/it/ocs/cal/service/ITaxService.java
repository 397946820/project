package com.it.ocs.cal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.vo.TaxVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface ITaxService {

	/**
	 * 分页 搜索
	 * @param param
	 * @param string 
	 * @return
	 */
	ResponseResult<TaxVo> findAll(RequestParam param, String string);

	/**
	 * 保存、修改
	 * @param tax 实体类
	 * @return 
	 */
	OperationResult saveEdit(TaxVo tax);

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param template
	 */
	void exportExcel(HttpServletRequest request, HttpServletResponse response, String template);

	/**
	 * 导入
	 * @param file
	 * @return
	 */
	OperationResult uploadExcel(MultipartFile file);

}
