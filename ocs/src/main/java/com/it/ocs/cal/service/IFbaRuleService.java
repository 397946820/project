package com.it.ocs.cal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.vo.FbaRuleVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface IFbaRuleService {
	
	/**
	 * 查找
	 * @param param
	 * @param string 
	 * @return
	 */
	ResponseResult<FbaRuleVo> findAll(RequestParam param, String string);

	/**
	 * 保存和修改
	 * @param fbaRule
	 * @return
	 */
	OperationResult saveEdit(FbaRuleVo fbaRule);

	/**
	 * 导入
	 * @param file
	 * @return
	 */
	OperationResult uploadExcel(MultipartFile file);

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param template
	 */
	void exportExcel(HttpServletRequest request, HttpServletResponse response, String template);


}
