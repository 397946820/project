package com.it.ocs.ebaySales.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.ebaySales.model.EBayBestOffersModel;
import com.it.ocs.ebaySales.service.IEBayBestOffersService;
import com.it.ocs.ebaySales.vo.BestOffersVo;
import com.it.ocs.publication.vo.PublicationVO;

@Controller
@RequestMapping(value="BestOffers")
public class BestOffersController {

//	@Autowired
	private IEBayBestOffersService bestOffersService;
	

	@RequestMapping(value="/selectGroupbyBestOffers")
	@ResponseBody
	public ResponseResult<BestOffersVo> selectGroupbyBestOffers(RequestParam param){
		return bestOffersService.selectGroupbyBestOffers(param);
	}
	@RequestMapping(value="/show/{conditions}",method = RequestMethod.GET)
	public String show(){
		return "admin/ebaySales/bestOffers";
	}
	
	@RequestMapping(value="/recycleShow")
	public String recycleShow(){
		return "admin/ebaySales/recycleBestOffers";
	}
	@RequestMapping(value="/synchronousBestOffers")
	@ResponseBody
	public OperationResult synchronousBestOffers(){
		
		return bestOffersService.synchronousBestOffers();
	}
	@RequestMapping(value="/selectBestOfferItems")
	@ResponseBody
	public ResponseResult<PublicationVO> selectBestOfferItems(RequestParam param){
		
		return bestOffersService.selectBestOfferItems(param);
	}
	
	@RequestMapping(value="/selectEBayBestOffersByItemId")
	@ResponseBody
	public ResponseResult<BestOffersVo> selectEBayBestOffersByItemId(RequestParam param){
		
		return bestOffersService.selectEBayBestOffersByItemId(param.getParam());
	}
	@RequestMapping(value="selectRecycleEBayBestOffersByItemId")
	@ResponseBody
	public ResponseResult<BestOffersVo> selectRecycleEBayBestOffersByItemId(String itemId){
		return bestOffersService.selectRecycleEBayBestOffersByItemId(itemId);
	}
	@RequestMapping(value="/bestOfferMain",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult bestOfferMain(@RequestBody Map<String, Object> map){
		
		return bestOffersService.bestOfferMain(map);
	}
	
	@RequestMapping(value="/deleteBestOfferByBestId")
	@ResponseBody
	public OperationResult deleteBestOfferByBestId(EBayBestOffersModel eBayBestOffersModel){
		return bestOffersService.deleteBestOfferByBestId(eBayBestOffersModel);
	}
	@RequestMapping(value="/restoreBestOfferByBestId")
	@ResponseBody
	public OperationResult restoreBestOfferByBestId(EBayBestOffersModel eBayBestOffersModel){
		
		return bestOffersService.restoreBestOfferByBestId(eBayBestOffersModel);
	}
	@RequestMapping(value="/synchronousBestOffer")
	@ResponseBody
	public OperationResult synchronousBestOffer(String itemId,String siteName,String sellerId){
		return bestOffersService.synchronousBestOffer(itemId, siteName,sellerId,null);
	}
}
