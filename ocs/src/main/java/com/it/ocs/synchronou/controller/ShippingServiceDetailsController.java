package com.it.ocs.synchronou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.service.IEBayShippingServiceDetailsService;
import com.it.ocs.synchronou.vo.ShippingServiceDetailsVO;

@Controller
@RequestMapping(value="/ShippingServiceDetails")
public class ShippingServiceDetailsController {
	
	@Autowired
	private IEBayShippingServiceDetailsService shippingService;
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/ebaySynchronous/shippingServiceDetails";
	}
	
	@RequestMapping(value="/synchronouShippingServiceDetails")
	@ResponseBody
	public OperationResult synchronouShippingServiceDetails(){
		
		return shippingService.synchronouShippingServiceDetails();
	}
	
	@RequestMapping(value="selectShippingServiceDetails")
	@ResponseBody
	public ResponseResult<ShippingServiceDetailsVO> selectShippingServiceDetails(RequestParam param){
		return shippingService.selectShippingServiceDetails(param);
	}
	
	@RequestMapping(value="/onOff/{id}/{type}")
	@ResponseBody
	public OperationResult onOff(@PathVariable("id")int id,@PathVariable("type")int type){
		return shippingService.onOff(id,type);
	}
}
