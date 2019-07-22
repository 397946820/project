package com.it.ocs.synchronou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.dao.IEBayStoreProductDao;
import com.it.ocs.synchronou.service.IEBayStoreProductService;
import com.it.ocs.synchronou.vo.StoreProductVO;

@Controller
@RequestMapping(value="/StoresProduct")
public class StoreProductController {

//	@Autowired
	private IEBayStoreProductService storeProductService;
	@RequestMapping(value="/show")
	public String show(){
		return "admin/ebaySynchronous/storeProduct";
	}
	
	@RequestMapping(value="/synchronouStoresProduct")
	@ResponseBody
	public OperationResult synchronouStoresProduct(String global_id,String store_name){
		
		return storeProductService.synchronouStoreProduct(global_id, store_name);
	}
	
	@RequestMapping(value="/selectStoreProdcuts")
	@ResponseBody
	public ResponseResult<StoreProductVO> selectStoreProductsByGlobalIdAndStoreName(String global_id,String store_name,RequestParam param ){
		
		return storeProductService.selectStoreProductsByGlobalIdAndStoreName(global_id, store_name, param);
	}
}
