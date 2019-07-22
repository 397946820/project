package com.it.ocs.seller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.seller.service.IEBayGoodPlaceService;
import com.it.ocs.seller.vo.GoodPlaceVO;

@Controller
@RequestMapping(value="/GoodPlace")
public class GoodPlaceController {

	@Autowired
	private IEBayGoodPlaceService bayGoodPlaceService;
	
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/moduleManagement/goodPlace";
	}
	
	@RequestMapping(value="/findGoodPlaceList")
	@ResponseBody 
	public ResponseResult<GoodPlaceVO>  findGoodPlaceList(RequestParam param){
		
		ResponseResult<GoodPlaceVO> goodPlaceList = bayGoodPlaceService.findGoodPlaceList(param);
		
		return goodPlaceList;
	}
	
	@RequestMapping(value="/addOrEdit")
	@ResponseBody
	public OperationResult addOrEdit(GoodPlaceVO goodPlaceVO){
		
		return bayGoodPlaceService.addOrEdit(goodPlaceVO);
		
	}
	@RequestMapping(value="/deleteGoodPlaceByIds")
	@ResponseBody
	public OperationResult deleteGoodPlaceByIds(String ids){
		
		return bayGoodPlaceService.deleteGoodPlaceByIds(ids);
	}
	
}
