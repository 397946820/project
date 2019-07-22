package com.it.ocs.customerCenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.customerCenter.model.CustomerProductModel;
@Repository
public interface ICustomerProductDao extends IBaseDAO<CustomerProductModel> {
	
	public List<CustomerProductModel> selectCustomerProduct(@Param("product")Map<String , Object> map,@Param("startRow") int startRow,@Param("endRow") int endRow,@Param("order")String order,@Param("sort")String sort);
    
	public List<CustomerProductModel> selectCustomerPR(@Param("product")Map<String, String> map);
	
	public void remove(@Param("product_id")Long product_id);
	
	public Integer getTotal(@Param("product")Map<String,Object> map);
	
	public void updateProducts(List<CustomerProductModel> customerProductModels);
	
	public void insertProducts(List<CustomerProductModel> customerProductModels);
}
