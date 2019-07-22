package com.it.ocs.synchronou.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.service.IEBayFeedbackService;
import com.it.ocs.synchronou.vo.FeedbackVO;

@Controller
@RequestMapping(value="/Feedback")
public class FeedbackController {

//	@Autowired 
	private IEBayFeedbackService feedbackService;
	@RequestMapping(value="/show")
	public String show(){
		return "admin/ebaySynchronous/feedback";
	}
	
	@RequestMapping(value="/feedbackSynchronous")
	@ResponseBody
	public OperationResult feedbackSynchronous(){
		
		return feedbackService.feedbackSynchronous();
	}
	@RequestMapping(value="/list")
	@ResponseBody
	public ResponseResult<FeedbackVO> list(RequestParam param){
		return feedbackService.list(param);
	}
	@RequestMapping(value="/replyFollowUp")
	@ResponseBody
	public OperationResult replyFollowUp(FeedbackVO feedbackVO){
		return feedbackService.replyFollowUp(feedbackVO);
	}
	@RequestMapping(value="/comments")
	@ResponseBody
	public OperationResult comments(FeedbackVO feedbackVO){
		
		return feedbackService.comments(feedbackVO);
	}
	@RequestMapping(value="/note")
	@ResponseBody
	public OperationResult note(FeedbackVO feedbackVO){
		return feedbackService.note(feedbackVO);
	}
}
