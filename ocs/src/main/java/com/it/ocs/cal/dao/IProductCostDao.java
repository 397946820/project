package com.it.ocs.cal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.ProductCostModel;

@Repository
public interface IProductCostDao extends IBaseDao<ProductCostModel> {

	int findCountByParent(Long parentId);

	Long findIdByParentId(Long parentId);

	List<ProductCostModel> findByUserId(@Param(value = "userId")Long userId);

	List<ProductCostModel> findByParentId(@Param(value = "parentId") Long parentId);
	
	void updateByParentId(@Param(value = "cost") ProductCostModel cost);

	public Integer getIdBySKU(String sku);

	public void updateInfo(Map<String, Object> m);

	public void updateAvgMonth(Map<String, Object> map);

	public void recordCostChangeLog(@Param(value = "log") ProductCostModel cost);
}
