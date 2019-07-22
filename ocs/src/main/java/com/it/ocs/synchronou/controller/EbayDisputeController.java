package com.it.ocs.synchronou.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EbayUserCaseModel;
import com.it.ocs.synchronou.service.IEbayDisputeService;

@Controller
@RequestMapping("/dispute")
public class EbayDisputeController {
	
	@Autowired
	private IEbayDisputeService ebayDisputeService;
	
	@RequestMapping(value="/syncEbayDisputeList",method=RequestMethod.GET)
	@ResponseBody
	public OperationResult syncEbayDisputeList(){
		return ebayDisputeService.syncEbayDisputeList();
	}
	
	@RequestMapping(value="/show")
	public String show(){
		return "admin/ebaySynchronous/syncDisputeList";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody 
	public ResponseResult<EbayUserCaseModel> ebayDisputeList(RequestParam param){  
		return ebayDisputeService.ebayDisputeList(param);
	}
	
	@RequestMapping(value="/remark",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult remark(@RequestBody Map<String,Object> disputeModel){
		return ebayDisputeService.remark(disputeModel);
	}
	
	@RequestMapping(value="/syncUpdate",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult syncUpdate(@RequestBody Map<String,Object> disputeModel){
		return ebayDisputeService.syncUpdate(disputeModel);
	}
	
	@RequestMapping(value="/getOrderTransInfo/{orderLineItemId}",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult getOrderTransInfo(@PathVariable("orderLineItemId") String orderLineItemId){
		return ebayDisputeService.getOrderTransInfo(orderLineItemId);
	}
	@RequestMapping(value="/answer",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult answer(@RequestBody Map<String,Object> answerModel){
		return ebayDisputeService.answer(answerModel);
	}
	
	@RequestMapping(value="/getOldDisputeMessage/{id}",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult getOldDisputeMessage(@PathVariable("id") String id){
		return ebayDisputeService.getOldDisputeMessage(id);
	}
	
	@RequestMapping(value="/getAppealListByParentId/{parentId}",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getAppealListByParentId(@PathVariable("parentId") String parentId){
		return ebayDisputeService.getAppealListByParentId(parentId);
	}
	
	@RequestMapping(value="/getMoneyMovementListByParentId/{parentId}",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getMoneyMovementListByParentId(@PathVariable("parentId") String parentId){
		return ebayDisputeService.getMoneyMovementListByParentId(parentId);
	}
	
	@RequestMapping(value="/getResponseHistoryListByParentId/{parentId}",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getResponseHistoryListByParentId(@PathVariable("parentId") String parentId){
		return ebayDisputeService.getResponseHistoryListByParentId(parentId);
	}
}
