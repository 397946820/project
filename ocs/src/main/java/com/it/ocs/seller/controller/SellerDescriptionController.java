package com.it.ocs.seller.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.seller.service.IEBaySellerDescriptionService;
import com.it.ocs.seller.vo.SellerDescriptionVO;

@Controller
@RequestMapping(value="/SellerDescription")
public class SellerDescriptionController {

	@Autowired
	private IEBaySellerDescriptionService sellerDescriptionService;
	
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/moduleManagement/sellerDescription";
	}
	
	@RequestMapping(value="/addOrEdit")
	@ResponseBody
	public OperationResult addOrEdit(@RequestBody Map<String, Object> map){
		
		return sellerDescriptionService.addOrEdit(map);
	}
	@RequestMapping(value="/findSellerDescriptionList")
	@ResponseBody 
	public ResponseResult<SellerDescriptionVO> findSellerDescriptionList(Integer page,Integer rows){
		RequestParam param = new RequestParam();
		param.setPage(page);
		param.setRows(rows);
		return sellerDescriptionService.findSellerDescriptionList(param);
	}
	
	@RequestMapping(value="/deleteSellerDescriptionByIds")
	@ResponseBody
	public OperationResult deleteSellerDescriptionByIds(String ids){
		
		return sellerDescriptionService.deleteSellerDescriptionByIds(ids);
	}
	
	
}
