package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.EbayOrderItemModel;

public interface IEbayOrderItemService {
	
	public OperationResult insertEbayOrderItems(List<EbayOrderItemModel> ebayOrderItemModels);
	
	public OperationResult updateEbayOrderItems(List<EbayOrderItemModel> ebayOrderItemModels);
	
	public List<EbayOrderItemModel> findEbayOrderItems();
}
