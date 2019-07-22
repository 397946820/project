package com.it.ocs.synchronou.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayCategoryModel;
import com.it.ocs.synchronou.vo.CategoryVO;


public interface IEBayCategoryService {

	public OperationResult insertCategoryList(List<EBayCategoryModel> ebayCategoryModels);
	
	public OperationResult updateCategoryList(List<EBayCategoryModel> ebayCategoryModels);
	
	public OperationResult synchronouCategory(CategoryVO categoryVO);
	
	public List<CategoryVO> selectCategorysByMarketplaceId(Long categoryId,Long marketplaceId);
	
	public ResponseResult<CategoryVO> selectChildCategorysByCIDAndMID(@Param(value="categoryId") Long categoryId,@Param(value="marketplaceId") Long marketplaceId);

	public List<CategoryVO> selectCategorysByMarketplaceIds(Long marketplaceId);
	
	public EBayCategoryModel selectCategoryByCIDAndMID(@Param(value="categoryId") Long categoryId,@Param(value="marketplaceId") Long marketplaceId);
	
	public EBayCategoryModel selectCategoryByPIDAndMIDAndName(Long parentCategoryId,Long marketplaceId,String category_name);
	
	public List<CategoryVO> selectCategorysByMarketplaceId(Long marketplaceId);

	public List<CategoryVO> selectCategorysByMIDAndGCID(Long categoryId,Long marketplaceId);

	public List<EBayCategoryModel> selectCategoryIDByMID(Long marketplaceId);
	
	public List<EBayCategoryModel> selectCategoryIDByMIDAndCIDMIN(Long categoryId,Long marketplaceId);
	
	
}
