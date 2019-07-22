package com.it.ocs.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.AmazonOfferModel;

@Repository
public interface IAmazonOfferDao extends IBaseDAO<AmazonOfferModel> {
	public void insertAmazonOffers(List<AmazonOfferModel> amazonOfferModels);
	public void updateAmazonOffers(List<AmazonOfferModel> amazonOfferModels);
	public String selectMaxDate();
	public List<AmazonOfferModel> selectMySqlDate(@Param("date") String date);
	public List<AmazonOfferModel> selectAmazonOfferModels(List<AmazonOfferModel> amazonOfferModels);

}
