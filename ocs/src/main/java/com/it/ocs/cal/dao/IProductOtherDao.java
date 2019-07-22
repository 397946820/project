package com.it.ocs.cal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.ProductOtherModel;

@Repository
public interface IProductOtherDao extends IBaseDao<ProductOtherModel> {

	int findCountByParent(Long parentId);

	List<ProductOtherModel> findByParentId(Long parentId);

	List<ProductOtherModel> findByUserId(@Param(value = "userId")Long userId);

}
