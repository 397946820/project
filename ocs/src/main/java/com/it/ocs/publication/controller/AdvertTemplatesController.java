package com.it.ocs.publication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.model.EBayAdvertTemplatesModel;
import com.it.ocs.publication.service.external.IEBayAdvertTemplatesService;
import com.it.ocs.publication.vo.AdvertTemplatesVO;

@Controller
@RequestMapping("/AdvertTemplates")
public class AdvertTemplatesController {

	@Autowired
	IEBayAdvertTemplatesService advertService;
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/publication/advertTemplates";
	}
	@RequestMapping(value="/list")
	@ResponseBody
	public ResponseResult<AdvertTemplatesVO> selectAdvertTemplates(RequestParam param){
		return advertService.selectAdvertTemplates(param);
	}
	@RequestMapping(value="/remove")
	@ResponseBody
	public OperationResult remove(Long id){
		return advertService.delete(id);
	}
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public OperationResult saveAdd(AdvertTemplatesVO advertTemplatesVO){
		return advertService.addSeve(advertTemplatesVO);
	}
	
	@RequestMapping(value="/selectAdvertList")
	@ResponseBody
	public ResponseResult<AdvertTemplatesVO> selectAdvertList(){
		
		return advertService.selectAdvertList();
	}
}
