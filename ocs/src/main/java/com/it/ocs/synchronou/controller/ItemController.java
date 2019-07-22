//package com.it.ocs.synchronou.controller;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.it.ocs.common.OperationResult;
//import com.it.ocs.common.RequestParam;
//import com.it.ocs.publication.vo.PublicationVO;
//import com.it.ocs.synchronou.service.IEBayItemService;
//
//@Controller
//@RequestMapping(value="/Item")
//public class ItemController {
//	
//	@Autowired
//	private IEBayItemService itemService;
//	
//	@RequestMapping(value="/publishedItem")
//	@ResponseBody
//	public OperationResult publishedItem(Long id){
//		
//		return itemService.publishedItem(id);
//	}
//	@RequestMapping(value="/synchronouVariations")
//	@ResponseBody
//	public OperationResult synchronouVariations(){
//		return itemService.synchronouVariations();
//	}
//	@RequestMapping(value="/updatePublishedItem")
//	@ResponseBody
//	public OperationResult updatePublishedItem(Long id){
//		return itemService.updateItemType(id);
//	}
//	
//	@RequestMapping(value="/verifyAddItem")
//	@ResponseBody
//	public OperationResult verifyAddItem(Long id){
//		return itemService.verifyAddItem(id);
//	}
//	@RequestMapping(value="/verifyLineItem")
//	@ResponseBody
//	public OperationResult verifyLineItem(Long id){
//		return itemService.verifyLineItem(id);
//	}
//	
//	@RequestMapping(value="/timingAddItem")
//	@ResponseBody
//	public OperationResult timingAddItem(PublicationVO publicationVO){
//		return itemService.timingAddItem(publicationVO);
//	}
//	
//	@RequestMapping(value="/verifyItem",method = RequestMethod.POST)
//	@ResponseBody
//	public OperationResult verifyAddItem(@RequestBody Map<String, Object> map){
//		return itemService.verifyAddItem(map);
//	}
//	@RequestMapping(value="/endItem")
//	@ResponseBody
//	public OperationResult endItem(@RequestBody Map<String, Object> map){
//		return itemService.endItem(map);
//	}
//	@RequestMapping(value="/reviseInventoryStatus")
//	@ResponseBody
//	public OperationResult reviseInventoryStatus(@RequestBody Map<String,Object> map){
//		return itemService.reviseInventoryStatus(map);
//	}
//	@RequestMapping(value="/relistPublicationItem")
//	@ResponseBody
//	public OperationResult relistPublicationItem(Long id){
//		return itemService.relistPublicationItem(id);
//	}
//	@RequestMapping(value="getSiteDateById")
//	@ResponseBody
//	public OperationResult getSiteDateById(Long siteId){
//		return itemService.getSiteDateById(siteId);
//	}
//	@RequestMapping(value="getLocalBySiteId")
//	@ResponseBody
//	public OperationResult getLocalBySiteId(String siteDate,Long siteId){
//		return itemService.getLocalBySiteId(siteDate, siteId);
//	}
//}
