package com.it.ocs.synchronou.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.synchronou.model.WalmartAccountModel;

public interface ISyncWalmartOrderDao {

	public List<WalmartAccountModel> getAccounts();

	public Integer getWalamrtId(String purchaseOrderId);
	
	public int getId();

	public void addOrder(Map<String, Object> order);

	public void updateOrder(Map<String, Object> order);

	public void addOrderLine(Map<String, Object> line);

	public void updateOrderLine(Map<String, Object> line);

	public int lineIsExist(@Param("parentId")String parentId,@Param("lineNumber")String lineNumber);

}
