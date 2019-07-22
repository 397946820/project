package com.it.ocs.cal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.service.IProductOtherService;
import com.it.ocs.cal.vo.ProductOtherVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.controller.IBaseController;
import com.it.ocs.common.util.UserUtils;

@Controller
@RequestMapping("/productOther")
public class ProductOtherController extends IBaseController {

	@Autowired
	private IProductOtherService productOtherService;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/productOther";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<ProductOtherVo> findAll(RequestParam param) {
		return productOtherService.findAll(param,isSYSorCWRY(UserUtils.getUserId()));
	}

	@RequestMapping("/edit")
	@ResponseBody
	public OperationResult edit(ProductOtherVo productOther) {
		return productOtherService.edit(productOther);
	}
	
	@RequestMapping("/findByParentId")
	@ResponseBody
	public ResponseResult<ProductOtherVo> findByParentId(RequestParam param) {
		return productOtherService.findByParentId(param);
	}

	// 导出
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template) {
		productOtherService.exportExcel(request, response, template,isSYSorCWRY(UserUtils.getUserId()),getColumns(UserUtils.getUserId(), "CPQTGL_"));
	}

	// 导入
	@RequestMapping("/uploadExcel")
	@ResponseBody
	public OperationResult uploadExcel(@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) {
		return productOtherService.uploadExcel(file);
	}
}
