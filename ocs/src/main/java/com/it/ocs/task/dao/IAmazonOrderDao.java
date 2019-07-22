package com.it.ocs.task.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.AmazonOrderModel;
@Repository
public interface IAmazonOrderDao extends IBaseDAO<AmazonOrderModel> {
	
	public List<Long> getOrderSeqs(@Param("size")Long size);
	
	public List<Long> getOrderItemSeqs(@Param("size")Long size);
	
	public void insertAmazonOrders(List<AmazonOrderModel> amazonOrderModels);
	
	public void updateAmazonOrders(List<AmazonOrderModel> amazonOrderModels);
	
	public List<AmazonOrderModel> findAmazonOrders(List<AmazonOrderModel> amazonOrderModels);
	
	public String selectMaxCreatedAt();

	public void handleReissueTranNumber(@Param(value = "param") Map<String, Object> param);
}
