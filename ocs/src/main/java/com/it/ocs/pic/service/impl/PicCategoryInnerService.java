package com.it.ocs.pic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.pic.dao.IPicCategoryDAO;
import com.it.ocs.pic.model.PicCategoryModel;
import com.it.ocs.pic.service.IPicCategoryInnerService;
@Service
public class PicCategoryInnerService implements IPicCategoryInnerService {
	@Autowired
	private IPicCategoryDAO categoryDAO;
	@Override
	public List<Long> findChildIds(Long parentId) {
		List<Long> result = null;
		if (null != parentId) {
			List<PicCategoryModel> categoryModels = categoryDAO.query(null);
			result = Lists.newArrayList();
			findChildIds(categoryModels, result, parentId);
		}
		return result;
	}
	private void findChildIds(List<PicCategoryModel> models,List<Long> result,Long parentId) {
		result.add(parentId);
		CollectionUtil.each(CollectionUtil.searchList(models, new IFunction<PicCategoryModel, Boolean>() {
			@Override
			public Boolean excute(PicCategoryModel obj) {
				return null != obj.getPid() && (obj.getPid() == parentId || obj.getPid().equals(parentId));
			}
		}), new IAction<PicCategoryModel>() {
			@Override
			public void excute(PicCategoryModel obj) {
				findChildIds(models, result, obj.getId());
			}
		});
	}
	@Override
	public List<PicCategoryModel> findByIds(List<Long> categoryIds) {
		List<PicCategoryModel> result = null;
		if (!CollectionUtil.isNullOrEmpty(categoryIds)) {
			result = categoryDAO.queryByIds(categoryIds);
		}
		return result;
	}

}
