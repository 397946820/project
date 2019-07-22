package com.it.ocs.synchronou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayProductListingModel;
import com.it.ocs.synchronou.service.IEBayCategorySpecificsService;
import com.it.ocs.synchronou.service.IEBayProductListingService;
import com.it.ocs.synchronou.vo.ProductListingVO;

@Controller
@RequestMapping(value="/ProductListing")
public class ProductListingController {

	@Autowired
	private IEBayProductListingService productListingService;
	@Autowired
	private IEBayCategorySpecificsService categorySpecificsService;
	
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/ebaySynchronous/productListing";
	}
	
	@RequestMapping(value="synchronouProductListing")
	@ResponseBody
	public OperationResult synchronouProductListing(Long marketplace_id,Long category_id){
		
		return  productListingService.synchronouProductListing(marketplace_id,category_id);
	}
	
	@RequestMapping(value="selectProductListings")
	@ResponseBody
	public ResponseResult<ProductListingVO> selectProductListingsByMIDOrCID(Long category_Id,Long marketplace_id,RequestParam param){

		return productListingService.selectProductListingsByMIDOrCID(category_Id,marketplace_id, param);
	}
	
	@RequestMapping(value="internalSelectProductListingByMIDOrCID")
	@ResponseBody
	public EBayProductListingModel internalSelectProductListingByMIDOrCID(Long category_Id, Long marketplace_id){
		EBayProductListingModel eBayProductListingModel = productListingService.internalSelectProductListingByMIDOrCID(category_Id, marketplace_id);
		if(null != eBayProductListingModel){
			eBayProductListingModel.setRows(categorySpecificsService.internalFindCategorySpecificsList(category_Id, marketplace_id));
		}
		return eBayProductListingModel;
	}
}
