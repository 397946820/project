package com.it.ocs.synchronou.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.synchronou.model.EBaySiteDetailsModel;

@Repository
public interface IEBaySiteDetailsDao extends IBaseDAO<EBaySiteDetailsModel> {

	public List<EBaySiteDetailsModel> findSiteDetailsList();
	
	public void insertSiteDetails(List<EBaySiteDetailsModel> baySiteDetailsModels);
	
	public void updateSiteDetails(List<EBaySiteDetailsModel> baySiteDetailsModels);

	public List<EBaySiteDetailsModel> internalSelectSiteDetails();
}
