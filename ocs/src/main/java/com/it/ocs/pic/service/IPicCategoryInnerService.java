package com.it.ocs.pic.service;

import java.util.List;

import com.it.ocs.pic.model.PicCategoryModel;

public interface IPicCategoryInnerService {
	public List<Long> findChildIds(Long parentId);
	
	public List<PicCategoryModel> findByIds(List<Long> categoryIds);
}
