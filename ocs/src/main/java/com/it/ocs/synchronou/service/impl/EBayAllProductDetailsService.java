//package com.it.ocs.synchronou.service.impl;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.call.GetItemCall;
//import com.ebay.soap.eBLBaseComponents.ItemType;
//import com.ebay.soap.eBLBaseComponents.ListingDetailsType;
//import com.ebay.soap.eBLBaseComponents.PictureDetailsType;
//import com.ebay.soap.eBLBaseComponents.ShippingDetailsType;
//import com.google.common.collect.Lists;
//import com.it.ocs.common.BeanConvertUtil;
//import com.it.ocs.common.OperationResult;
//import com.it.ocs.common.RequestParam;
//import com.it.ocs.common.ResponseResult;
//import com.it.ocs.common.service.BaseService;
//import com.it.ocs.common.support.IAction;
//import com.it.ocs.common.util.CollectionUtil;
//import com.it.ocs.synchronou.dao.IEBayAllProductDetailsDao;
//import com.it.ocs.synchronou.mapping.SiteIDEnum;
//import com.it.ocs.synchronou.mapping.UserIDToken;
//import com.it.ocs.synchronou.model.EBayAllProductDetailsModel;
//import com.it.ocs.synchronou.model.EBayStoreProductModel;
//import com.it.ocs.synchronou.service.IEBayAllProductDetailsService;
//import com.it.ocs.synchronou.service.IEBayStoreProductService;
//import com.it.ocs.synchronou.util.ObjectAndJsonUtil;
//import com.it.ocs.synchronou.vo.AllProductDetailsVO;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//@Service
//public class EBayAllProductDetailsService extends BaseService implements IEBayAllProductDetailsService {
//	private static final Logger log = Logger.getLogger(EBayAllProductDetailsService.class);
//	@Autowired
//	private IEBayStoreProductService storeProductService;
//	@Autowired
//	private IEBayAllProductDetailsDao allProductDetailsDao;
//	@Override
//	public OperationResult synchronouAllProductDetail(String user_id,Long site_id) {
//		OperationResult result = new OperationResult();
//		try {
//			BaseEbaySDKService ebaySDKService = new BaseEbaySDKService();
//			String token = UserIDToken.searchTokenByUserID(user_id);
//			String global_id = SiteIDEnum.searchGlobalIDBySiteID(site_id);
//			List<EBayStoreProductModel> eBayStoreProductModels = storeProductService.interiorSelectStoreProductsByGlobalId(global_id);
//			ApiContext apiContext = ebaySDKService.getApiContext(token, "https://api.ebay.com/wsapi");
//			for (EBayStoreProductModel eBayStoreProductModel : eBayStoreProductModels) {
//				EBayAllProductDetailsModel allProductDetailsModel = new EBayAllProductDetailsModel();
//				GetItemCall getItemCall = new GetItemCall(apiContext);
//				getItemCall.setSite(SiteIDEnum.searchBySiteID(site_id));
//				String itemID = eBayStoreProductModel.getItem_id();
//				getItemCall.setItemID(itemID);
//				ItemType itemType = getItemCall.getItem();
//				allProductDetailsModel.setCountry(itemType.getCountry().toString());
//				allProductDetailsModel.setCurrency(itemType.getCurrency().toString());
//				allProductDetailsModel.setItem_id(itemID);
//				allProductDetailsModel.setStart_price(itemType.getStartPrice().getValue()+" "+itemType.getStartPrice().getCurrencyID().toString());
//				ListingDetailsType listingDetailsType = itemType.getListingDetails();
//				allProductDetailsModel.setStart_time(listingDetailsType.getStartTime().getTime().toString());
//				allProductDetailsModel.setEnd_time(listingDetailsType.getEndTime().getTime().toString());
//				allProductDetailsModel.setView_item_url(listingDetailsType.getViewItemURL());
//				allProductDetailsModel.setListing_type(itemType.getListingType().toString());
//				allProductDetailsModel.setLocation(itemType.getLocation());
//				allProductDetailsModel.setTitle(itemType.getTitle());
//				ShippingDetailsType shippingDetailsType = itemType.getShippingDetails();
//				allProductDetailsModel.setShippingtype(shippingDetailsType.getShippingType().toString());
//				allProductDetailsModel.setTitle(itemType.getTitle());
//				allProductDetailsModel.setSite(itemType.getSite().toString());
//				PictureDetailsType pictureDetailsType = itemType.getPictureDetails();
//				String[] pictureURLs = pictureDetailsType.getPictureURL();
//				Map<String, String[]> array = new HashMap<>();
//				array.put("pictureURL", pictureURLs);
//				allProductDetailsModel.setPicture_url(ObjectAndJsonUtil.ArrayToJsonArray(array));
//				List<EBayAllProductDetailsModel> eBayAllProductDetailsModels = internalSelectAllProductDetails();
//				Map<String, EBayAllProductDetailsModel> maps = new HashMap<>();
//				for (EBayAllProductDetailsModel eBayAllProductDetailsModel : eBayAllProductDetailsModels) {
//					maps.put(eBayAllProductDetailsModel.getItem_id(), eBayAllProductDetailsModel);
//				}
//				if (maps.get(allProductDetailsModel.getItem_id())!=null) {
//					result = updateAllProductDetail(allProductDetailsModel);
//				}else{
//					result = insertAllProductDetail(allProductDetailsModel);
//				}
//			}
//		} catch (Exception e) {
//			log.error(e);
//		}
//		return result;
//	}
//
//	@Override
//	public OperationResult insertAllProductDetail(EBayAllProductDetailsModel allProductDetailsModel) {
//		OperationResult result = new OperationResult();
//		try {
//			allProductDetailsDao.insertAllProductDetail(allProductDetailsModel);
//		} catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("insert error!");
//			log.error(e);
//		}
//		return result;
//	}
//
//	@Override
//	public OperationResult updateAllProductDetail(EBayAllProductDetailsModel allProductDetailsModel) {
//		OperationResult result = new OperationResult();
//		try {
//			allProductDetailsDao.updateAllProductDetail(allProductDetailsModel);
//		} catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("update error!");
//			log.error(e);
//		}
//		return result;
//	}
//
//	@Override
//	public ResponseResult<AllProductDetailsVO> selectAllProductDetails(RequestParam param) {
//		ResponseResult<AllProductDetailsVO> result = new ResponseResult<>();
//		AllProductDetailsVO allProductDetailsVO = BeanConvertUtil.mapToObject(param.getParam(), AllProductDetailsVO.class);
//		List<EBayAllProductDetailsModel> eBayAllProductDetailsModels = allProductDetailsDao.selectAllProductDetails(param.getStartRow(), param.getEndRow());
//		int total = allProductDetailsDao.getTotal();
//		List<AllProductDetailsVO> allProductDetailsVOs = Lists.newArrayList();
//		convertList(eBayAllProductDetailsModels, allProductDetailsVOs);
//		for (AllProductDetailsVO allProductDetailsVO2 : allProductDetailsVOs) {
//			StringBuffer product = new StringBuffer();
//			String site = allProductDetailsVO2.getSite();
//			JSONArray imgURLs = JSONArray.fromObject(allProductDetailsVO2.getPicture_url());
//			JSONObject imgURL = imgURLs.getJSONObject(0);
//			product.append("<ul class=\"also_like_nav left\" ><li class=\"also_like_nav_mes\" style=\"padding: 5px;margin:20px auto;border:solid 1px #ffffff;width:500px;overflow: hidden;\" ><img src=\"");
//			product.append(imgURL.get("pictureURL"));
//			allProductDetailsVO2.setPicture_url(null);
//			product.append("\"/><p class=\"title\" style=\"color:#000000;font-size:12px;\"><a href=\"");
//			product.append(allProductDetailsVO2.getView_item_url());
//			product.append("\"><span>");
//			product.append(allProductDetailsVO2.getTitle());
//			allProductDetailsVO2.setTitle(null);
//			product.append("</span></a></p><p class=\"price\" style=\"color:#000000;font-size:12px;\"><a href=\"");
//			product.append(allProductDetailsVO2.getView_item_url());
//			product.append("\"><span>");
//			product.append(allProductDetailsVO2.getStart_price());
//			allProductDetailsVO2.setStart_price(null);
//			product.append("</span></a></p><p class=\"buyNow\" style=\"color:#000000;font-size:12px;\"><a href=\"");
//			product.append(allProductDetailsVO2.getView_item_url());
//			product.append("\"><span>");
//			product.append(allProductDetailsVO2.getListing_type());
//			allProductDetailsVO2.setListing_type(null);
//			product.append("</span></a></p><p class=\"shipping\" style=\"color:#000000;font-size:12px;\"><a href=\"");
//			product.append(allProductDetailsVO2.getView_item_url());
//			product.append("\"><span>");
//			product.append(allProductDetailsVO2.getShippingtype());
//			allProductDetailsVO2.setShippingtype(null);
//			product.append("</span></a></p>");
//			product.append("<p class=\"startTime\" style=\"color:#000000;font-size:12px;\">开始时间：");
//			String format = "EEE MMM dd HH:mm:ss z yyyy";
//			String format2 = "yyyy-MM-dd HH:mm:ss";
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format,Locale.US);
//			SimpleDateFormat  simpleDateFormat2 = new SimpleDateFormat(format2);
//			try {
//				Date  startTime =  simpleDateFormat.parse(allProductDetailsVO2.getStart_time());
//				product.append(simpleDateFormat2.format(startTime));
//				product.append("</p><p class=\"endTime\" style=\"color:#000000;font-size:12px;\">结束时间：");
//				Date endTime =  simpleDateFormat.parse(allProductDetailsVO2.getEnd_time());
//				product.append(simpleDateFormat2.format(endTime));
//				product.append("</p>");
//			} catch (ParseException e) {
//				log.error(e);
//			}
//			product.append("</li></ul>");
//			if(site.equalsIgnoreCase("GERMANY")){
//				allProductDetailsVO2.setDe_product(product.toString());
//			}else if(site.equalsIgnoreCase("UK")){
//				allProductDetailsVO2.setUk_product(product.toString());
//			}else if(site.equalsIgnoreCase("US")){
//				allProductDetailsVO2.setUs_product(product.toString());
//			}
//		}
//		result.setRows(allProductDetailsVOs);
//		result.setTotal(total);
//		return result;
//	}
//	
//	
//	private void convertList(List<EBayAllProductDetailsModel> source, final List<AllProductDetailsVO> target) {
//		CollectionUtil.each(source, new IAction<EBayAllProductDetailsModel>() {
//			@Override
//			public void excute(EBayAllProductDetailsModel obj) {
//				AllProductDetailsVO allProductDetailsVO = new AllProductDetailsVO();
//				BeanUtils.copyProperties(obj, allProductDetailsVO);
//				target.add(allProductDetailsVO);
//			}
//		});
//	}
//
//	@Override
//	public List<EBayAllProductDetailsModel> internalSelectAllProductDetails() {
//		return allProductDetailsDao.internalSelectAllProductDetails();
//	}
//
//}
