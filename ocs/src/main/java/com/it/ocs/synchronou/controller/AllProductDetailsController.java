package com.it.ocs.synchronou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.service.IEBayAllProductDetailsService;
import com.it.ocs.synchronou.vo.AllProductDetailsVO;

@Controller
@RequestMapping(value="/AllProductDetails")
public class AllProductDetailsController {

//	@Autowired
	private IEBayAllProductDetailsService allProductDetailsService;
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/ebaySynchronous/allProductDetails";
	}
	
	@RequestMapping(value="/synchronouAllProductDetail")
	@ResponseBody
	public OperationResult synchronouAllProductDetail(String user_id,Long site_id){
		
		return allProductDetailsService.synchronouAllProductDetail(user_id, site_id);
	}
	
	@RequestMapping(value="/selectAllProductDetails")
	@ResponseBody
	public ResponseResult<AllProductDetailsVO> selectAllProductDetails(RequestParam param){
		
		return allProductDetailsService.selectAllProductDetails(param);
	}
}
