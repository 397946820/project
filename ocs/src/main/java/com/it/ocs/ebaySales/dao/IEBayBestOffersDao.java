package com.it.ocs.ebaySales.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.ebaySales.model.EBayBestOffersModel;

public interface IEBayBestOffersDao extends IBaseDAO<EBayBestOffersModel> {

	public void updateEBayBestOffers(List<EBayBestOffersModel> eBayBestOffersModels);
	
	public void insertEBayBestOffers(List<EBayBestOffersModel> eBayBestOffersModels);
	
	public List<EBayBestOffersModel> selectEBayBestOffersByItemId(Map<String, Object> map);
	
	public EBayBestOffersModel interiorSelectBestOfferByBestId(@Param("bestOfferId")String bestOfferId);

	public void updateEnabledFlagByBestId(EBayBestOffersModel eBayBestOffersModel);
	
	public List<EBayBestOffersModel> selectRecycleEBayBestOffersByItemId(@Param("itemId")String itemId);

	public List<EBayBestOffersModel> selectGroupbyBestOffers(@Param("ct")Map<String, Object> map ,@Param(value="startRow") Integer starRow,@Param(value="endRow") Integer endRow);

	public Integer getTotal(@Param("ct")Map<String, Object> map);
	
	public List<EBayBestOffersModel> selectBestOfferPendingByUserId(String userId);
	
	public void updateEbayBestOfferByBest(EBayBestOffersModel eBayBestOffersModel);

	public int exist(Map<String, Object> offer);

	public void addOffer(Map<String, Object> offer);

	public void updateOffer(Map<String, Object> offer);

	public List<Map<String, Object>> getActiveOfferByAccount(String account);
	
}
