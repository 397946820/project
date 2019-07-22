package com.it.ocs.cal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.model.CurrencyRateModel;
import com.it.ocs.cal.vo.CurrencyRateVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface ICurrencyRateService {

	/**
	 * 分页以及搜索查找
	 * @param param
	 * @param string 
	 * @return
	 */
	ResponseResult<CurrencyRateVo> findAll(RequestParam param, String string);

	/**
	 * 保存和修改
	 * @param currencyRate
	 * @return
	 */
	OperationResult saveEdit(CurrencyRateVo currencyRate);

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

	List<CurrencyRateModel> findByTemplate();

}
