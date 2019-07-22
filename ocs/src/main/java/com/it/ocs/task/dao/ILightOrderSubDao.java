package com.it.ocs.task.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.LightOrderSubModel;

@Repository
public interface ILightOrderSubDao extends IBaseDAO<LightOrderSubModel> {

	public void insertLightOrderSubs(List<LightOrderSubModel> lightOrderSubModels);
	
	public void updateLightOrderSubs(List<LightOrderSubModel> lightOrderSubModels);
	
	public List<LightOrderSubModel> findLightOrderSubs();
	
	public Long getOrderSubSeqs();
	
}
