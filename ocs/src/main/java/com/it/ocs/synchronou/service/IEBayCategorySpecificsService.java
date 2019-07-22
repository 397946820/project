package com.it.ocs.synchronou.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayCategorySpecificsModel;
import com.it.ocs.synchronou.vo.CategorySpecificsVO;

public interface IEBayCategorySpecificsService {
	
	public OperationResult synchronouCategorySpecifics(Long marketplace_id);
	
	public OperationResult insertCategorySpecificsList(List<EBayCategorySpecificsModel> eBayCategorySpecificsModels);
	
	public OperationResult updateCategorySpecificsList(List<EBayCategorySpecificsModel> eBayCategorySpecificsModels);

	public List<CategorySpecificsVO> findCategorySpecificsByCIDAndMID(Long category_Id,Long marketplace_id);
	
	public ResponseResult<CategorySpecificsVO> findCategorySpecificsByMID(Long category_Id,Long marketplace_id,RequestParam param);
	
	public ResponseResult<CategorySpecificsVO> internalFindCategorySpecificsList(Long category_Id,Long marketplace_id);

}
