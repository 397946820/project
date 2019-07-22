package com.it.ocs.synchronou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.synchronou.model.EBayCategorySpecificsVModel;
import com.it.ocs.synchronou.service.IEBayCategorySpecificsVService;

@Controller
@RequestMapping(value="/CategorySpecificsV")
public class CategorySpecificsVController {
	
	@Autowired
	private IEBayCategorySpecificsVService categorySpecificsVService;
	
	@RequestMapping(value="/internalFindSpecificsVByNameAanCIDAanMID")
	@ResponseBody
	public List<EBayCategorySpecificsVModel> internalFindSpecificsVByNameAanCIDAanMID(String name,Long category_Id,Long marketplace_id){
		
		return categorySpecificsVService.internalFindSpecificsVByNameAanCIDAanMID(name, category_Id, marketplace_id);
	}
}
