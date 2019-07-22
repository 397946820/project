package com.it.ocs.task.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.EbayOrderItemModel;

@Repository
public interface IEbayOrderItemDao{

	public void insertEbayOrderItems(List<EbayOrderItemModel> ebayOrderItemModels);
	
	public void updateEbayOrderItems(List<EbayOrderItemModel> ebayOrderItemModels);
	
	public List<EbayOrderItemModel> findEbayOrderItems();
}
