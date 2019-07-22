package com.it.ocs.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.AmazonOrderItemModel;
@Repository
public interface IAmazonOrderItemDao {

	public void insertAmazonOrderItems(List<AmazonOrderItemModel> amazonOrderItemModels);
	
	public void updateAmazonOrderItems(List<AmazonOrderItemModel> amazonOrderItemModels);
	
	public List<AmazonOrderItemModel> findAmazonOrderItems(List<AmazonOrderItemModel> amazonOrderItemModels);
	
	public Long selectOrderItemRepeat();
	
	public void deleteOrderItemRepeat();
}
