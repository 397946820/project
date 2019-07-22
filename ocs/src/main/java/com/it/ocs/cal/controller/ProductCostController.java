package com.it.ocs.cal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.service.IProductCostService;
import com.it.ocs.cal.vo.ProductCostVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.controller.IBaseController;
import com.it.ocs.common.util.UserUtils;

@Controller
@RequestMapping("/productCost")
public class ProductCostController extends IBaseController {

	@Autowired
	private IProductCostService productCostService;

	// 跳转到折扣主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/taximeter/productCost";
	}

	@RequestMapping("/findAll")
	@ResponseBody
	public ResponseResult<ProductCostVo> findAll(RequestParam param) {
		return productCostService.findAll(param,isSYSorCWRY(UserUtils.getUserId()));
	}

	@RequestMapping("/edit")
	@ResponseBody
	public OperationResult edit(ProductCostVo productCost) {
		return productCostService.edit(productCost);
	}

	// 导出
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template) {
		productCostService.exportExcel(request, response, template,isSYSorCWRY(UserUtils.getUserId()),getColumns(UserUtils.getUserId(), "CPCBGL_"));
	}

	// 导入
	@RequestMapping("/uploadExcel")
	@ResponseBody
	public OperationResult uploadExcel(@org.springframework.web.bind.annotation.RequestParam("myfile") MultipartFile file) {
		return productCostService.uploadExcel(file);
	}
}
