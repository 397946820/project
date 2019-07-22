package com.it.ocs.synchronou.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.synchronou.model.EBayCountryDetailsModel;

@Repository
public interface IEBayCountryDetailsDao extends IBaseDAO<EBayCountryDetailsModel> {

	public List<EBayCountryDetailsModel> findCountryDetailsList();
	
	public void insertCountryDetails(List<EBayCountryDetailsModel> bayCountryDetailsModels);
	
	public void updateCountryDetails(List<EBayCountryDetailsModel> bayCountryDetailsModels);
	
	public List<EBayCountryDetailsModel> selectCountryDetailsList();
	
	public List<EBayCountryDetailsModel> internalSelectCountryDetailsList();

}
