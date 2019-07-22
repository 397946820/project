package com.it.ocs.synchronou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.synchronou.model.EBayStoreCategoryModel;
import com.it.ocs.synchronou.model.EBayStoreCategoryModel;

@Repository
public interface IEBayStoreCategoryDao extends IBaseDAO<EBayStoreCategoryModel> {
   
	
	
	void deleteStoreCategoryAll();
	List<EBayStoreCategoryModel> selectStoreCategoryList();
	
	List<EBayStoreCategoryModel> selectStoreCategorysByMarketplaceId(@Param(value="marketplaceId")Long marketplaceId);
	
	List<EBayStoreCategoryModel> selectChildStoreCategorysByCIDAndMID(@Param(value="categoryId") Long categoryId,@Param(value="userId")String userId);
	
	EBayStoreCategoryModel selectStoreCategoryByCIDAndMID(@Param(value="categoryId") Long categoryId,@Param(value="userId")String userId);
	
	EBayStoreCategoryModel selectStoreCategoryByPIDAndMIDAndName(@Param(value="parentCategoryId")Long parentCategoryId,@Param(value="marketplaceId")Long marketplaceId,@Param(value="categoryName")String categoryName);
	
    void insertStoreCategoryList(List<EBayStoreCategoryModel> eBayStoreCategoryModels);
	
	void updateStoreCategoryList(@Param("list")List<EBayStoreCategoryModel> eBayStoreCategoryModels);

	List<EBayStoreCategoryModel> internalSelectStoreCategorys(@Param("param")EBayStoreCategoryModel eBayStoreCategoryModel);

	EBayStoreCategoryModel selectStoreUrlByUserId(@Param("userId")String userId);
	
	
}
