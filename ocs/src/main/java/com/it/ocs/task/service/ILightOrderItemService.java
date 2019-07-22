package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.LightOrderItemModel;

public interface ILightOrderItemService {
	
	public OperationResult insertLightOrderItems(List<LightOrderItemModel> lightOrderItemModels);
	
	public OperationResult updateLightOrderItems(List<LightOrderItemModel> lightOrderItemModels);
	
	public List<LightOrderItemModel> findLightOrderItems(List<LightOrderItemModel> lightOrderItemModels);
}
