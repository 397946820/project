package com.it.ocs.task.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.LightOrderModel;

@Repository
public interface ILightOrderDao extends IBaseDAO<LightOrderModel>{

	public void insertLightOrders(List<LightOrderModel> lightOrderModels);
	
	public void updateLightOrders(List<LightOrderModel> lightOrderModels);
	
	public List<LightOrderModel> findLightOrders(List<LightOrderModel> lightOrderModels);
	
	public Long getOrderSubSeqs(Long size);
	
	public String selectMaxCreatedAt();
}
