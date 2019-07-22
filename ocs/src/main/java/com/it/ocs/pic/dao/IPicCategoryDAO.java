package com.it.ocs.pic.dao;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.pic.model.PicCategoryModel;
@Repository
public interface IPicCategoryDAO extends IBaseDAO<PicCategoryModel> {

	public void deleteCategoryById(Long picCategoryId);
}
