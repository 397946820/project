package com.it.ocs.synchronou.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayCategoryModel;
import com.it.ocs.synchronou.model.EBayStoreCategoryModel;
import com.it.ocs.synchronou.vo.StoreCategoryVO;

public interface IEBayStoreCategoryService {

	
	public List<StoreCategoryVO> selectStoreCategorys();
	
	public OperationResult insertStoreCategorys();
	
	public OperationResult addOrEdit(StoreCategoryVO storeStoreCategoryVO);
	
	public OperationResult insertStoreCategory(StoreCategoryVO storeStoreCategoryVO);
	
	public OperationResult updateStoreCategory(StoreCategoryVO storeStoreCategoryVO);

	public List<StoreCategoryVO> selectChildStoreCategorysByCIDAndMID(Long categoryId, String userId);

	public List<StoreCategoryVO> selectStoreCategorysByMarketplaceId(Long marketplaceId);
	
	public EBayStoreCategoryModel selectStoreCategoryByCIDAndMID(Long categoryId,String userId);
	
	public EBayStoreCategoryModel selectStoreCategoryByPIDAndMIDAndName(Long parentCategoryId,Long marketplaceId,String category_name);

	public OperationResult synchronouStoreCategory(Long marketplace_id, String user_name);
	
	public OperationResult insertStoreCategoryList(List<EBayStoreCategoryModel> eBayStoreCategoryModels);
	
	public OperationResult updateStoreCategoryList(List<EBayStoreCategoryModel> eBayStoreCategoryModels);

	public List<EBayStoreCategoryModel> internalSelectStoreCategorys(EBayStoreCategoryModel eBayStoreCategoryModel);
	
	public EBayStoreCategoryModel selectStoreUrlByUserId(String userId);
}
