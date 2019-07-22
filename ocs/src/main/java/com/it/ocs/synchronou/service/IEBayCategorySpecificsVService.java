package com.it.ocs.synchronou.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.model.EBayCategorySpecificsVModel;
import com.it.ocs.synchronou.vo.CategorySpecificsVVO;

public interface IEBayCategorySpecificsVService {

	public OperationResult insertCategorySpecificsVList(List<EBayCategorySpecificsVModel> eBayCategorySpecificsVModels);
	
	public OperationResult updateCategorySpecificsVList(List<EBayCategorySpecificsVModel> eCategorySpecificsVModels);

	public Long selectCategorySpecificsMaxCID(Long marketplaceId);
	
	public List<CategorySpecificsVVO> findSpecificsVByNameAanCIDAanMID(String name,Long category_Id,Long marketplace_id);
	
	public List<EBayCategorySpecificsVModel> internalFindSpecificsVByNameAanCIDAanMID(String name,Long category_Id,Long marketplace_id);
}
