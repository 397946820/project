package com.it.ocs.ebaySales.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetSupportingSqlParameter;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.ebaySales.model.EBayBestOffersModel;
import com.it.ocs.ebaySales.vo.BestOffersVo;
import com.it.ocs.publication.vo.PublicationVO;

public interface IEBayBestOffersService {

	/**
	 * 同步所有议价信息
	 * @return
	 */
	public OperationResult synchronousBestOffers();
	/**
	 * 同步指定产品的议价信息。
	 * @return
	 */
	public OperationResult synchronousBestOffer(String itemId,String siteName,String sellerId,String bestOfferId);
	public OperationResult insertEBayBestOffers(List<EBayBestOffersModel> eBayBestOffersModels);
	
	public OperationResult updateEBayBestOffers(List<EBayBestOffersModel> eBayBestOffersModels);

	public ResponseResult<PublicationVO> selectBestOfferItems(RequestParam param);

	public ResponseResult<BestOffersVo> selectGroupbyBestOffers(RequestParam param);
	public ResponseResult<BestOffersVo> selectEBayBestOffersByItemId(Map<String, Object> map);
	
	public OperationResult bestOfferMain(Map<String, Object> map);
	
	public EBayBestOffersModel interiorSelectBestOfferByBestId(String bestOfferId);
	
	public OperationResult updateEnabledFlagByBestId(EBayBestOffersModel eBayBestOffersModel);

	public OperationResult restoreBestOfferByBestId(EBayBestOffersModel eBayBestOffersModel);
	
	public OperationResult deleteBestOfferByBestId(EBayBestOffersModel eBayBestOffersModel);
	
	public ResponseResult<BestOffersVo> selectRecycleEBayBestOffersByItemId(String itemId);
}
