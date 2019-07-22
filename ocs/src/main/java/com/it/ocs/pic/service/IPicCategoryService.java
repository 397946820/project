package com.it.ocs.pic.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.pic.vo.PicCategoryVO;

public interface IPicCategoryService {
	public List<PicCategoryVO> query();
	
	public OperationResult savePicCategory(PicCategoryVO picCategoryVO);
	
	public OperationResult removePicCategory(Long picCategoryId);
	
	public List<PicCategoryVO> combotree();
	
	public List<PicCategoryVO> conditionCombotree();
	
}
