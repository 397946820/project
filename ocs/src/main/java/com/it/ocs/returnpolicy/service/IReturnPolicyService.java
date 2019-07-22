package com.it.ocs.returnpolicy.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.returnpolicy.vo.ReturnPolicyVO;
import com.it.ocs.common.RequestParam;

public interface IReturnPolicyService {
	public ResponseResult<ReturnPolicyVO> queryReturnPolicy(RequestParam param);
	
	public OperationResult saveReturnPolicy(ReturnPolicyVO returnPolicy);
	
	public OperationResult removeReturnPolicy(String Ids);
}
