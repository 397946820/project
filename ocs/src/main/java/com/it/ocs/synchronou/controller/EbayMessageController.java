package com.it.ocs.synchronou.controller;

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
import com.it.ocs.synchronou.model.MemberMessageInfoModel;
import com.it.ocs.synchronou.service.IEbayMessageService;
import com.it.ocs.synchronou.vo.MemberMessageInfoVO;
import com.it.ocs.synchronou.vo.MessageInfoVO;

@Controller
@RequestMapping("/message")
public class EbayMessageController {
	
	@Autowired
	private IEbayMessageService ebayMessageService;
	
	@RequestMapping(value="/show")
	public String show(){
		return "admin/ebaySynchronous/syncMessageInfo";
	}
	@RequestMapping(value="/messageAnswer")
	public String messageAnswer(){
		return "admin/ebaySynchronous/messageAnswer";
	}
	@RequestMapping(value="/syncEbayMessgaeList",method=RequestMethod.GET)
	@ResponseBody
	public OperationResult syncEbayMessgaeList(){
		return ebayMessageService.syncEbayMessgaeList();
	}
	
	@RequestMapping(value="/list")
	@ResponseBody 
	public ResponseResult<MessageInfoVO> ebayMesageList(RequestParam param){  
		return ebayMessageService.getList(param);
	}
	
	@RequestMapping(value="/remark",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult remark(@RequestBody Map<String,Object> messageModel){
		return ebayMessageService.remark(messageModel);
	}
	
	@RequestMapping(value="/syncEbayMemberMessgaeList",method=RequestMethod.GET)
	@ResponseBody
	public OperationResult syncEbayMemberMessgaeList(){
		return ebayMessageService.syncEbayMemberMessgaeList();
	}
	
	@RequestMapping(value="/memberShow")
	public String memberShow(){
		return "admin/ebaySynchronous/syncMemberMessageInfo";
	}
	
	@RequestMapping(value="/memberMessagelist")
	@ResponseBody 
	public ResponseResult<MemberMessageInfoVO> memberMessagelist(RequestParam param){  
		return ebayMessageService.memberMessagelist(param);
	}
	@RequestMapping(value="/memberMessageById")
	@ResponseBody
	public MemberMessageInfoModel memberMessageById(Long id){
		return ebayMessageService.memberMessageById(id);
	}
	@RequestMapping(value="/countEmm")
	@ResponseBody
	public OperationResult countEmm(@RequestBody Map<String, Object> param){
		return ebayMessageService.countEmm(param);
	}
	@RequestMapping(value="/memberRemark",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult memberRemark(@RequestBody Map<String,Object> messageModel){
		return ebayMessageService.memberRemark(messageModel);
	}
	
	@RequestMapping(value="/getOldQuestion/{id}",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult getOldQuestion(@PathVariable("id") String id){
		return ebayMessageService.getOldQuestion(id);
	}
	
	@RequestMapping(value="/memberAnswer",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult memberAnswer(@RequestBody Map<String,Object> amswerModel){
		return ebayMessageService.memberAnswer(amswerModel);
	}
	@RequestMapping(value="/updateRead")
	@ResponseBody
	public OperationResult updateRead(String id,Integer read){
		
		return ebayMessageService.updateRead(id, read);
	}
	@RequestMapping(value="/updateOcsReadStatus")
	@ResponseBody
	public OperationResult updateOcsReadStatus(@RequestBody Map<String, Object> map) {
		return ebayMessageService.updateOcsReadStatus(map);
	}
	
	@RequestMapping(value="/findMessageBodyText/{messageId}")
	@ResponseBody
	public OperationResult findMessageBodyText(@PathVariable("messageId") String messageId) {
		return ebayMessageService.findMessageBodyText(messageId);
	}
}

