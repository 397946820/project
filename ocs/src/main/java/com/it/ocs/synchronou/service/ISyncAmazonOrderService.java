package com.it.ocs.synchronou.service;

import com.it.ocs.common.OperationResult;

public interface ISyncAmazonOrderService {

	public OperationResult syncAmazonOrder(String startTime, String endTime);

}
