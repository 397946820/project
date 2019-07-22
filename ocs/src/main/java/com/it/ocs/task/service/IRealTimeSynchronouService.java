package com.it.ocs.task.service;

import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;

/**
 * 
 * @author yangguanbao
 * 描述：实时同步MySQL到Oracle接口
 */
public interface IRealTimeSynchronouService {

	public OperationResult sysnchronouOrderAndOrderItem();
	
	public OperationResult lightSynchronou(Map<String, String> map);
	
	public OperationResult amazonSynchronou(Map<String, String> map);
	
	public OperationResult ebaySynchronou(Map<String, String> map);
	
}
