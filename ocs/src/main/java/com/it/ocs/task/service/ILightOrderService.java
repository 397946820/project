package com.it.ocs.task.service;

import java.sql.Timestamp;
import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.LightOrderItemModel;
import com.it.ocs.task.model.LightOrderModel;

public interface ILightOrderService {
	
	public OperationResult synchronouLightOrders(List<LightOrderModel> lightOrderModels,List<LightOrderItemModel> lightOrderItemModels ,String pathParam);

	public OperationResult insertLightOrders(List<LightOrderModel> lightOrderModels);
	
	public OperationResult updateLightOrders(List<LightOrderModel> lightOrderModels);
	
	public List<LightOrderModel> findLightOrders(List<LightOrderModel> lightOrderModels);
	
	public String selectMaxCreatedAt();
	
}
