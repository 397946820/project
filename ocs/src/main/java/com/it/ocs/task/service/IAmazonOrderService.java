package com.it.ocs.task.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.AmazonOrderItemModel;
import com.it.ocs.task.model.AmazonOrderModel;

public interface IAmazonOrderService {

	
	public OperationResult synchronouAmazonOrders(List<AmazonOrderModel> amazonOrderModels,List<AmazonOrderItemModel> amazonOrderItemModels,String pathParma);

	public OperationResult insertAmazonOrders(List<AmazonOrderModel> amazonOrderModels);
	
	public OperationResult updateAmazonOrders(List<AmazonOrderModel> amazonOrderModels);
	
	public List<AmazonOrderModel> findAmazonOrders(List<AmazonOrderModel> amazonOrderModels);
	
	public String selectMaxCreatedAt();

	public OperationResult handleReissueTranNumber(Map<String, Object> param);
}
