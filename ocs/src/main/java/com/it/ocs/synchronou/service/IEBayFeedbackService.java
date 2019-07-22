package com.it.ocs.synchronou.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayFeedbackModel;
import com.it.ocs.synchronou.vo.FeedbackVO;

public interface IEBayFeedbackService {

	public OperationResult feedbackSynchronous();
	
	public OperationResult insertFeedbacks(List<EBayFeedbackModel> eBayFeedbackModels);
	
	public EBayFeedbackModel selectEBayFeedbackModelByFeedbackId(EBayFeedbackModel eBayFeedbackModel);
	
	public OperationResult updateEBayFeedbackModels(List<EBayFeedbackModel> eBayFeedbackModels);

	public ResponseResult<FeedbackVO> list(RequestParam param);
	
	public OperationResult userSynchronous();
	
	public OperationResult replyFollowUp(FeedbackVO feedbackVO);
	
	public OperationResult updateFeedbackById(EBayFeedbackModel eBayFeedbackModel);

	public OperationResult comments(FeedbackVO feedbackVO);
	
	public OperationResult note(FeedbackVO feedbackVO);
}
