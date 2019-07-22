package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.AmazonOrderItemModel;

public interface IAmazonOrderItemService {

	public OperationResult insertAmazonOrderItems(List<AmazonOrderItemModel> amazonOrderItemModels);
	
	public OperationResult updateAmazonOrderItems(List<AmazonOrderItemModel> amazonOrderItemModels);
	
	public List<AmazonOrderItemModel> findAmazonOrderItems(List<AmazonOrderItemModel> amazonOrderItemModels);
}
