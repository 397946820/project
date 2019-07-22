package com.it.ocs.task.service;

import java.sql.Timestamp;
import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.AmazonOrderModel;
import com.it.ocs.task.model.EbayOrderItemModel;
import com.it.ocs.task.model.EbayOrderModel;

public interface IEbayOrderService {
	
	public OperationResult synchronouEbayOrders(List<EbayOrderModel> ebayOrderModels,List<EbayOrderItemModel> ebayOrderItemModels,String pathParam);

	public OperationResult insertEbayOrders(List<EbayOrderModel> ebayOrderModels);
	
	public OperationResult updateEbayOrders(List<EbayOrderModel> ebayOrderModels);
	
	public List<EbayOrderModel> findEbayOrders();
	
	public String selectMaxLastFetchTime();
}
