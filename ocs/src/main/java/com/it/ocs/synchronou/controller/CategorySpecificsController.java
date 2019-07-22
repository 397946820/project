package com.it.ocs.synchronou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.service.IEBayCategorySpecificsService;
import com.it.ocs.synchronou.vo.CategorySpecificsVO;

@Controller
@RequestMapping(value="/CategorySpecifics")
public class CategorySpecificsController {

	@Autowired
	private IEBayCategorySpecificsService categorySpecificsService;
	
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/ebaySynchronous/categorySpecifics";
	}
	
	@RequestMapping(value="synchronouCategorySpecifics")
	@ResponseBody
	public OperationResult synchronouCategorySpecifics(Long marketplace_id){
		
		return categorySpecificsService.synchronouCategorySpecifics(marketplace_id);
	}
	@RequestMapping(value="findCategorySpecificsList")
	@ResponseBody
	public ResponseResult<CategorySpecificsVO> findCategorySpecificsList(Long category_Id,Long marketplace_id,RequestParam param){
		
		return categorySpecificsService.findCategorySpecificsByMID(category_Id,marketplace_id, param);
	}
	
	@RequestMapping(value="internalFindCategorySpecificsList")
	@ResponseBody
	public ResponseResult<CategorySpecificsVO> internalFindCategorySpecificsList(Long category_Id,Long marketplace_id){
		
		return categorySpecificsService.internalFindCategorySpecificsList(category_Id, marketplace_id);
	}
	
}
