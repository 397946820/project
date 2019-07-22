package com.it.ocs.synchronou.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayReturnPolicyDetailModel;
import com.it.ocs.synchronou.vo.ReturnPolicyDetailVO;

public interface IEBayReturnPolicyDetailService {
	
	public OperationResult synchronouReturnPolicyDetail(Long site_id);
	
	public OperationResult insertReturnPolicyDetail(EBayReturnPolicyDetailModel returnPolicyDetailModel);
	
	public OperationResult updateReturnPolicyDetail(EBayReturnPolicyDetailModel returnPolicyDetailModel);
	
	public ResponseResult<ReturnPolicyDetailVO> selectReturnPolicyDetails(RequestParam param);
	
	public List<EBayReturnPolicyDetailModel> selectReturnPolicySiteIds();
	
	public List<EBayReturnPolicyDetailModel> selectReturnPolicysBySiteId(Long site_id);
}
