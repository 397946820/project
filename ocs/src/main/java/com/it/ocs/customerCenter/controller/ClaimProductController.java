package com.it.ocs.customerCenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.service.IClaimProductService;
import com.it.ocs.customerCenter.vo.ClaimProductVO;

@Controller
@RequestMapping(value="/ClaimProduct")
public class ClaimProductController {

	@Autowired
	private IClaimProductService claimProductService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "admin/customerCenter/ClaimProduct";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public ResponseResult<ClaimProductVO> list(RequestParam param){
		return claimProductService.selectClaimProducts(param);
	}
	@RequestMapping(value="/batchSaveClaimProduct")
	@ResponseBody
	public OperationResult batchSaveClaimProduct(@RequestBody ClaimProductVO[] claimProductVOs){
		return claimProductService.batchSaveClaimProduct(claimProductVOs);
	}
	@RequestMapping(value="/remove")
	@ResponseBody
	public OperationResult remove(Long id){
		return claimProductService.remove(id);
	}
	@RequestMapping(value="/selectClaimProductByOID")
	@ResponseBody
	public OperationResult selectClaimProductByOID(String orderId,Long id){
		return claimProductService.selectClaimProductByOID(orderId,id);
	}
}
