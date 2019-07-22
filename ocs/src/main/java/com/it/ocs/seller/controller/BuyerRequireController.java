package com.it.ocs.seller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.seller.service.IEBayBuyerRequireService;
import com.it.ocs.seller.vo.BuyerRequiredVO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/BuyerRequire")
public class BuyerRequireController {

	@Autowired
	private IEBayBuyerRequireService requireService;
	
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/moduleManagement/buyerRequire";
	}
	@RequestMapping(value="/findBuyerRequireList")
	@ResponseBody public ResponseResult<BuyerRequiredVO> findBuyerRequireList(RequestParam param){
			    
		ResponseResult<BuyerRequiredVO> buyerRequireList = requireService.findBuyerRequireList(param);
		
		return buyerRequireList;
	}
	
	@RequestMapping(value="/findBuyerRequireById")
	@ResponseBody public BuyerRequiredVO  findBuyerRequireById(Long id){
		
		return requireService.findBuyerRequireById(id);
		//return null;
	}
	
	@RequestMapping(value="/insertBuyerRequire")
	@ResponseBody public OperationResult insertBuyerRequire(BuyerRequiredVO buyerRequiredVO){
		
		
			
			return requireService.insertBuyerRequire(buyerRequiredVO);
			
	}
	
	@RequestMapping(value="/deleteBuyerRequireById")
	@ResponseBody public OperationResult deleteBuyerRequireById(String ids){
		
		OperationResult result =  requireService.deleteBuyerRequireById(ids);
		
		return result;
	}
}
