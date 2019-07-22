package com.it.ocs.task.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.EbayOrderModel;

@Repository
public interface IEbayOrderDao extends IBaseDAO<EbayOrderModel> {

	public void insertEbayOrders(List<EbayOrderModel> ebayOrderModels);
	
	public void updateEbayOrders(List<EbayOrderModel> ebayOrderModels);
	
	public List<EbayOrderModel> findEbayOrders();
	
	public String selectMaxLastFetchTime();
	
	
	
}
