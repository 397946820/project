package com.it.ocs.synchronou.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EbayUserCaseModel;

public interface IEbayDisputeService {

	public OperationResult syncEbayDisputeList();

	public ResponseResult<EbayUserCaseModel> ebayDisputeList(RequestParam param);

	public OperationResult remark(Map<String, Object> disputeModel);

	public OperationResult syncUpdate(Map<String, Object> disputeModel);

	public OperationResult getOrderTransInfo(String orderLineItemId);

	public OperationResult answer(Map<String, Object> answerModel);

	public OperationResult getOldDisputeMessage(String id);

	public List<Map<String, Object>> getAppealListByParentId(String parentId);

	public List<Map<String, Object>> getMoneyMovementListByParentId(String parentId);

	public List<Map<String, Object>> getResponseHistoryListByParentId(String parentId);

	

}
