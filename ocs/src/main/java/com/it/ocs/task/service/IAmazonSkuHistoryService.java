package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.AmazonSkuHistoryModel;

public interface IAmazonSkuHistoryService {

	public OperationResult synchronouAmazonSkuHistory(List<AmazonSkuHistoryModel> amazonSkuHistoryModels);
	
	public OperationResult insertAmazonSkuHistorys(List<AmazonSkuHistoryModel> amazonSkuHistoryModels);

	public OperationResult updateAmazonSkuHistorys(List<AmazonSkuHistoryModel> amazonSkuHistoryModels);
	
	public String selectMaxDate();
	
	public List<AmazonSkuHistoryModel> selectMySqlDate(String date);
	
	public List<AmazonSkuHistoryModel> selectAmazonSkuHistoryModels(List<AmazonSkuHistoryModel> amazonSkuHistoryModels);

	public OperationResult updateInsertData(List<AmazonSkuHistoryModel> amazonSkuHistoryModels);

}
