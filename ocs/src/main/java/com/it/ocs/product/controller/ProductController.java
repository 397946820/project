package com.it.ocs.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.product.service.IProductService;
import com.it.ocs.product.vo.ProductVo;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private IProductService productService;

	// 跳转到产品主页
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/product/list";
	}
	
	@RequestMapping("/findAll")
	public @ResponseBody ResponseResult<ProductVo> findAll(RequestParam param) {
		return productService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	public @ResponseBody OperationResult saveEdit(ProductVo product) {
		return productService.saveEdit(product);
	}
	
	@RequestMapping("/remove")
	public @ResponseBody OperationResult remove(String ids) {
		return productService.remove(ids);
	}
}
