package com.it.ocs.task.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.EbayOrderSubModel;

@Repository
public interface IEbayOrderSubDao extends IBaseDAO<EbayOrderSubModel>{

	public void insertEbayOrderSubs(List<EbayOrderSubModel> ebayOrderSubModels);
	
	public void updateEbayOrderSubs(List<EbayOrderSubModel> ebayOrderSubModels);
	
	public List<EbayOrderSubModel> findEbayOrderSubs();
	public Long getOrderSubSeqs();
}
