package com.it.ocs.synchronou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.synchronou.model.EBayCategoryModel;

@Repository
public interface IEBayCategoryDao extends IBaseDAO<EBayCategoryModel> {

	void insertCategoryList(@Param("list")List<EBayCategoryModel> ebayCategoryModels);
	
	void updateCategoryList(List<EBayCategoryModel> ebayCategoryModels);
	
	List<EBayCategoryModel> selectCategoryList();
	
	List<EBayCategoryModel> selectCategorysByMarketplaceId(Long marketplaceId);
	
	List<EBayCategoryModel> selectChildCategorysByCIDAndMID(@Param(value="categoryId") Long categoryId,@Param(value="marketplaceId") Long marketplaceId);
	
	EBayCategoryModel selectCategoryByCIDAndMID(@Param(value="categoryId") Long categoryId,@Param(value="marketplaceId") Long marketplaceId);
	
	EBayCategoryModel selectCategoryByPIDAndMIDAndName(@Param(value="parentCategoryId")Long parentCategoryId,@Param(value="marketplaceId")Long marketplaceId,@Param(value="categoryName")String categoryName);
	
	List<EBayCategoryModel> selectCategorysByMIDAndGCID(@Param(value="category_Id") Long category_Id,@Param(value="marketplaceId") Long marketplaceId);
	
	List<EBayCategoryModel> selectCategoryIDByMID(@Param(value="marketplaceId") Long marketplaceId);
	
	/**
	 * 
	 * @param categoryId
	 * @param marketplaceId
	 * @return 获取大于categoryId值的100行数据
	 */
	List<EBayCategoryModel> selectCategoryIDByMIDAndCIDMIN(@Param(value="categoryId")Long categoryId,@Param(value="marketplaceId")Long marketplaceId);
}
