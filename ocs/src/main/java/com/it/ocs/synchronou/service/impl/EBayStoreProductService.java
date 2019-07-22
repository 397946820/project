//package com.it.ocs.synchronou.service.impl;
//
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.ebay.services.client.ClientConfig;
//import com.ebay.services.client.FindingServiceClientFactory;
//import com.ebay.services.finding.FindItemsIneBayStoresRequest;
//import com.ebay.services.finding.FindItemsIneBayStoresResponse;
//import com.ebay.services.finding.FindingServicePortType;
//import com.ebay.services.finding.PaginationInput;
//import com.ebay.services.finding.PaginationOutput;
//import com.ebay.services.finding.SearchItem;
//import com.ebay.services.finding.SearchResult;
//import com.google.common.collect.Lists;
//import com.it.ocs.common.BeanConvertUtil;
//import com.it.ocs.common.OperationResult;
//import com.it.ocs.common.RequestParam;
//import com.it.ocs.common.ResponseResult;
//import com.it.ocs.common.service.BaseService;
//import com.it.ocs.common.support.IAction;
//import com.it.ocs.common.util.CollectionUtil;
//import com.it.ocs.synchronou.dao.IEBayStoreProductDao;
//import com.it.ocs.synchronou.model.EBayStoreProductModel;
//import com.it.ocs.synchronou.service.IEBayStoreProductService;
//import com.it.ocs.synchronou.vo.StoreProductVO;
//
//@Service
//public class EBayStoreProductService extends BaseService implements IEBayStoreProductService {
//	private static final Logger log = Logger.getLogger(EBayStoreProductService.class);
//
//	@Autowired
//	private IEBayStoreProductDao storeProductDao;
//	@Override
//	public OperationResult synchronouStoreProduct(String globalId, String storeName) {
//		BaseEbaySDKService sdkService = new BaseEbaySDKService();
//		OperationResult operationResult = new OperationResult();
//		ClientConfig clientConfig = sdkService.getClientConfig("yangguan-LedEbay-PRD-9090b840d-c638c240", "http://svcs.ebay.com/services/search/FindingService/v1", globalId);
//		FindingServicePortType requsetService = FindingServiceClientFactory.getServiceClient(clientConfig);
//		FindItemsIneBayStoresRequest storesRequest = new FindItemsIneBayStoresRequest();
//		storesRequest.setStoreName(storeName);
//		Integer productTotle = getStoreTotle(requsetService, storesRequest);
//		Integer number = productTotle/10+1;
//		for (int i = 1; i <= number; i++) {
//			List<EBayStoreProductModel> oracleStoreProducts = interiorSelectStoreProductsByGlobalId(globalId);
//			List<EBayStoreProductModel> eBayStoreProductModels = Lists.newArrayList();
//			List<EBayStoreProductModel> updateStoreProductModels = Lists.newArrayList();
//			PaginationInput pi = new PaginationInput();
//			pi.setEntriesPerPage(10);
//			pi.setPageNumber(i);
//			storesRequest.setPaginationInput(pi);
//			FindItemsIneBayStoresResponse result = requsetService.findItemsIneBayStores(storesRequest);
//			String storeUrl = result.getItemSearchURL();
//			SearchResult searchResult = result.getSearchResult();
//			List<SearchItem> searchItems = searchResult.getItem();
//			if(searchItems.size()>0){
//				for (SearchItem searchItem : searchItems) {
//					EBayStoreProductModel eBayStoreProductModel = new EBayStoreProductModel();
//					eBayStoreProductModel.setItem_id(searchItem.getItemId());
//					eBayStoreProductModel.setItem_search_url(storeUrl);
//					eBayStoreProductModel.setGlobal_id(searchItem.getGlobalId());
//				    eBayStoreProductModel.setStore_name(storeName);
//					eBayStoreProductModels.add(eBayStoreProductModel);
//				}
//				
//			}
//			HashMap<String, EBayStoreProductModel> storeProductMap = new HashMap<>();
//			for (EBayStoreProductModel eBayStoreProductModel : eBayStoreProductModels) {
//				storeProductMap.put(eBayStoreProductModel.getItem_id(), eBayStoreProductModel);
//			}
//			for (EBayStoreProductModel eBayStoreProductModel : oracleStoreProducts) {
//				if(storeProductMap.get(eBayStoreProductModel.getItem_id())!=null){
//					updateStoreProductModels.add(storeProductMap.get(eBayStoreProductModel.getItem_id()));
//				}
//			}
//			eBayStoreProductModels.removeAll(updateStoreProductModels);
//			if (eBayStoreProductModels.size()>0) {
//				operationResult = insertStoreProducts(eBayStoreProductModels);
//			}
//			if(updateStoreProductModels.size()>0){
//				updateStoreProducts(updateStoreProductModels);
//			}
//			
//		}
//		return operationResult;
//	}
//
//	private Integer getStoreTotle(FindingServicePortType requsetService,FindItemsIneBayStoresRequest methodName){
//		Integer totle= null;
//		PaginationInput pi = new PaginationInput();
//		pi.setEntriesPerPage(1);
//		methodName.setPaginationInput(pi);
//		FindItemsIneBayStoresResponse result = requsetService.findItemsIneBayStores(methodName);
//		if(result.getAck().toString().endsWith("SUCCESS")){
//			PaginationOutput paginationOutput = result.getPaginationOutput();
//			totle = paginationOutput.getTotalEntries();
//		}
//		return totle; 
//	}
//
//	@Override
//	public OperationResult insertStoreProducts(List<EBayStoreProductModel> eBayStoreProductModels) {
//		OperationResult result = new OperationResult();
//		try {
//			storeProductDao.insertStoreProducts(eBayStoreProductModels);	
//		} catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("添加商店产品有误！");
//			e.printStackTrace();
//			log.error(e);
//		}
//		return result;
//	}
//
//	@Override
//	public OperationResult updateStoreProducts(List<EBayStoreProductModel> eBayStoreProductModels) {
//		OperationResult result = new OperationResult();
//		try {
//			storeProductDao.updateStoreProducts(eBayStoreProductModels);
//		} catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("更新店铺产品有误！");
//			log.error(e);
//		}
//		return result;
//	}
//
//	@Override
//	public ResponseResult<StoreProductVO> selectStoreProductsByGlobalIdAndStoreName(String globalID,String storeName,RequestParam param) {
//		ResponseResult<StoreProductVO> result = new ResponseResult<>();
//		StoreProductVO storeProductVO = BeanConvertUtil.mapToObject(param.getParam(), StoreProductVO.class);
//		List<EBayStoreProductModel> eBayStoreProductModels = Lists.newArrayList();
//		int total = 0;
//		if(globalID==null){
//			eBayStoreProductModels = storeProductDao.selectStoreProductsByGlobalIdAndStoreName(param.getStartRow(),param.getEndRow(),"EBAY-US", null);
//			total = storeProductDao.getTotal("EBAY-US", null);
//		}else{
//			if(storeName.equals("")){
//				storeName=null;
//			}
//			eBayStoreProductModels = storeProductDao.selectStoreProductsByGlobalIdAndStoreName(param.getStartRow(),param.getEndRow(),globalID, storeName);
//			total = storeProductDao.getTotal(globalID, storeName);
//		}
//		List<StoreProductVO> storeProductVOs = Lists.newArrayList();
//		convertList(eBayStoreProductModels, storeProductVOs);
//		result.setRows(storeProductVOs);
//		result.setTotal(total);
//		return result;
//	}
//
//	@Override
//	public List<EBayStoreProductModel> interiorSelectStoreProductsByGlobalId(String globalID) {
//		return storeProductDao.interiorSelectStoreProductsByGlobalId(globalID);
//	}
//	private void convertList(List<EBayStoreProductModel> source, final List<StoreProductVO> target) {
//		CollectionUtil.each(source, new IAction<EBayStoreProductModel>() {
//			@Override
//			public void excute(EBayStoreProductModel obj) {
//				StoreProductVO storeProductVO = new StoreProductVO();
//				BeanUtils.copyProperties(obj, storeProductVO);
//				target.add(storeProductVO);
//			}
//		});
//	}
//	
//}
