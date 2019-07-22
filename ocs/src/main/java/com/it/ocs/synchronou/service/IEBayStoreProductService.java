package com.it.ocs.synchronou.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayStoreProductModel;
import com.it.ocs.synchronou.vo.StoreProductVO;

public interface IEBayStoreProductService {
	
	public OperationResult synchronouStoreProduct(String globalId, String storeName);
	public OperationResult insertStoreProducts(List<EBayStoreProductModel> eBayStoreProductModels);
	public OperationResult updateStoreProducts(List<EBayStoreProductModel> eBayStoreProductModels);
	public ResponseResult<StoreProductVO> selectStoreProductsByGlobalIdAndStoreName(String globalID,String storeName,RequestParam param);
	public List<EBayStoreProductModel> interiorSelectStoreProductsByGlobalId(String globalID);

}
