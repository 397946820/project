package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.AmazonProductModel;

public interface IAmazonProductService {

	public OperationResult synchronouAmazonProduct(List<AmazonProductModel> amazonProductModels);
	
	public OperationResult insertAmazonProducts(List<AmazonProductModel> amazonProductModels);
	
	public OperationResult updateAmazonProducts(List<AmazonProductModel> amazonProductModels);
	
	public String selectMaxDate();
	
	public List<AmazonProductModel> selectMySqlDate(String date);
	
	public List<AmazonProductModel> selectAmazonProducts(List<AmazonProductModel> amazonProductModels);
	public OperationResult updateInsertData(List<AmazonProductModel> amazonProductModels);
}
