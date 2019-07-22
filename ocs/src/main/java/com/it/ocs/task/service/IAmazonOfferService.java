package com.it.ocs.task.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.AmazonOfferModel;

public interface IAmazonOfferService {
	
	public OperationResult synchronouAmazonOffer(List<AmazonOfferModel> amazonOfferModels);
	public OperationResult insertAmazonOffers(List<AmazonOfferModel> amazonOfferModels);
	public OperationResult updateAmazonOffers(List<AmazonOfferModel> amazonOfferModels);
	public List<AmazonOfferModel> selectMySqlDate(String date);
	public String selectMaxDate();
	public List<AmazonOfferModel> selectAmazonOfferModels(List<AmazonOfferModel> amazonOfferModels);
	
}
