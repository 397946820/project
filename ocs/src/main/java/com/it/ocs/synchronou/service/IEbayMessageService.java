package com.it.ocs.synchronou.service;

import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.MemberMessageInfoModel;
import com.it.ocs.synchronou.model.MessageInfoModel;
import com.it.ocs.synchronou.vo.MemberMessageInfoVO;
import com.it.ocs.synchronou.vo.MessageInfoVO;

public interface IEbayMessageService {

	public OperationResult syncEbayMessgaeList();

	public ResponseResult<MessageInfoVO> getList(RequestParam param);

	public OperationResult remark(Map<String, Object> messageModel);

	public OperationResult syncEbayMemberMessgaeList();

	public ResponseResult<MemberMessageInfoVO> memberMessagelist(RequestParam param);

	public OperationResult memberRemark(Map<String, Object> messageModel);

	public OperationResult getOldQuestion(String id);

	public OperationResult memberAnswer(Map<String, Object> amswerModel);
	
	public OperationResult countEmm(Map<String, Object> param);
	
	public OperationResult updateRead(String id,Integer read);
	
	public MemberMessageInfoModel memberMessageById(Long id);
	
	public OperationResult sendUseMessage(Map<String, Object> messages);

	public OperationResult updateOcsReadStatus(Map<String, Object> map);

	public OperationResult findMessageBodyText(String messageId);
	
}
