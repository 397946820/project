package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.AmazonBestsellerAsinModel;
import com.it.ocs.task.model.AmazonReportsBestsellerModel;

public interface IAmazonReportsBestsellerService {

	public OperationResult synchronouAmazonReportsBestseller(List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels);
	
	public OperationResult insertAmazonReportsBestsellers(List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels);

	public OperationResult updateAmazonReportsBestsellers(List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels);
	
	public String selectMaxDate();
	
	public List<AmazonReportsBestsellerModel> selectMySqlDate(String date);
	public List<AmazonReportsBestsellerModel> selectAmazonReportsBestsellerModels(List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels);
	public OperationResult updateInsertData(List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels);

}
