package com.it.ocs.synchronou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.service.IEBayStoreCategoryService;
import com.it.ocs.synchronou.vo.StoreCategoryVO;

@Controller
@RequestMapping(value="/StoreCategory")
public class StoreCategoryController {

//	@Autowired
	private IEBayStoreCategoryService storeCategoryService;
	
	@RequestMapping(value="show")
	public String show(){
		
		return "admin/ebaySynchronous/StoreCategory";
		
	}
	@RequestMapping(value="showInfo")
	public String showInfo(){
		return "admin/ebaySynchronous/storeCategoryInfo";
	}
	@RequestMapping(value="addOrEdit")
	@ResponseBody
	public OperationResult addOrEdit(StoreCategoryVO storeCategoryVO){
		
		return storeCategoryService.addOrEdit(storeCategoryVO);
	}
	
	@RequestMapping(value="synchronouStoreCategory")
	@ResponseBody 
	public OperationResult synchronouStoreCategory(Long marketplace_id, String user_name){
		 
		return storeCategoryService.synchronouStoreCategory(marketplace_id,user_name);
	}
	@RequestMapping(value="selectStoreCategorys")
	@ResponseBody 
	public List<StoreCategoryVO> selectStoreCategorys(){
		
		return storeCategoryService.selectStoreCategorys();
	}
	@RequestMapping(value="insertStoreCategorys")
	@ResponseBody
	public OperationResult insertStoreCategorys( ){
		return storeCategoryService.insertStoreCategorys();
	}
	
	@RequestMapping(value="deleteStoreCategoryByCategoryId") 
	public OperationResult deleteStoreCategoryByCategoryId(Long category_id){
		
		return null;
	}
	
}
