package com.it.ocs.synchronou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.model.EBayStoreCategoryModel;
import com.it.ocs.synchronou.service.IEBayCategoryService;
import com.it.ocs.synchronou.vo.CategoryVO;

@Controller
@RequestMapping(value="/IndexSynchronou")
public class CategoryController {

	@Autowired
	private IEBayCategoryService  categoryService;
	
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/ebaySynchronous/synchronouCategory";
		
	}
	
	@RequestMapping(value="/synchronouCategory")
	public OperationResult synchronouCategory(CategoryVO categoryVO){
		
		
		return categoryService.synchronouCategory(categoryVO);
	}
	
	@RequestMapping(value="/treeByMarketplaceId")
	@ResponseBody
	public List<CategoryVO> selectTreeByMarketplaceId(CategoryVO categoryVO){
		
		
		
		return categoryService.selectCategorysByMarketplaceId(categoryVO.getId(),categoryVO.getMarketplace_id());
	}
	
}
