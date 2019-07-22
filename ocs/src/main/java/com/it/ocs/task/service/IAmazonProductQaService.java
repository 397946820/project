package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.AmazonProductQaModel;

public interface IAmazonProductQaService {

	public OperationResult synchronouAmazonProductQa(List<AmazonProductQaModel> amazonProductQaModels);
	public OperationResult insertAmazonProductQas(List<AmazonProductQaModel> amazonProductQaModels);
	public OperationResult updateAmazonProductQas(List<AmazonProductQaModel> amazonProductQaModels);
	public String selectMaxDate();
	public List<AmazonProductQaModel> selectMySqlDate(String date);
	public List<AmazonProductQaModel> selectAmazonProductQaModels(List<AmazonProductQaModel> amazonProductQaModels);
}
