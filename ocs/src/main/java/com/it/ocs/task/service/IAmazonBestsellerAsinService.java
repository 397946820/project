package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.AmazonBestsellerAsinModel;

public interface IAmazonBestsellerAsinService {

	public OperationResult synchronouAmazonBestsellerAsin(List<AmazonBestsellerAsinModel> amazonBestsellerAsinModels);
	
	public OperationResult insertAmazonBestsellerAsins(List<AmazonBestsellerAsinModel> amazonBestsellerAsinModels);
	
	public OperationResult updateAmazonBestsellerAsins(List<AmazonBestsellerAsinModel> amazonBestsellerAsinModels);
	
	public String selectMaxDate();
	
	public List<AmazonBestsellerAsinModel> selectMySqlDate(String date);
	
	public List<AmazonBestsellerAsinModel> selectAmazonBestsellerAsinModels(List<AmazonBestsellerAsinModel> amazonBestsellerAsinModels);
}
