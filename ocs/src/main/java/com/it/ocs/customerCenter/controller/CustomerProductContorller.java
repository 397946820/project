package com.it.ocs.customerCenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.service.ICustomerProductService;
import com.it.ocs.customerCenter.vo.CustomerProductVO;

@Controller
@RequestMapping(value="/ProductReview")
public class CustomerProductContorller {

	@Autowired
	private ICustomerProductService productService;
	@RequestMapping(value="/show")
	public String show(){
		return "admin/customerCenter/ProductReview";
	}
	@RequestMapping(value="/list")
	@ResponseBody
	public ResponseResult<CustomerProductVO> list(RequestParam param){
		
		return productService.selectCustomerProducts(param);
	}
	@RequestMapping(value="/remove")
	@ResponseBody
	public OperationResult remove(Long product_id){
		return productService.remove(product_id);
	}
	
	@RequestMapping(value="/batchSaveProduct")
	@ResponseBody
	public OperationResult batchSaveProduct(@RequestBody CustomerProductVO[] customerProductVOs){
		
		return productService.batchSaveProducts(customerProductVOs);
	}
}
