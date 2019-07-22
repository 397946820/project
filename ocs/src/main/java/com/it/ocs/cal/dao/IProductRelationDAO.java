package com.it.ocs.cal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.ProductEntityModel;
import com.it.ocs.cal.model.ProductUserModel;

public interface IProductRelationDAO extends IBaseDao<ProductEntityModel> {
	public void removeByUserId(@Param(value="userId") Long userId);
	public void batchAdd(@Param(value="list") List<ProductUserModel> list);
	public List<ProductUserModel> queryByUserId(@Param(value="userId") Long userId);
	
	public List<ProductEntityModel> query(@Param(value="param") ProductEntityModel param);
	
	public void cancelSku(@Param(value="userId") Long userId,@Param(value="skuIds") List<Long> skuIds);
}
