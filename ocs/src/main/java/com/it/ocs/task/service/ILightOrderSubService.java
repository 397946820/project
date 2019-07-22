package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.LightOrderSubModel;

public interface ILightOrderSubService {
	
	public OperationResult insertLightOrderSubs(List<LightOrderSubModel> lightOrderSubModels);
	
	public OperationResult updateLightOrderSubs(List<LightOrderSubModel> lightOrderSubModels);
	
	public List<LightOrderSubModel> findLightOrderSubs();
	
	public List<Long> getOrderSeqsBySize(Long size);
}
