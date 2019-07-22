package com.it.ocs.synchronou.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.model.EBaySiteDetailsModel;
import com.it.ocs.synchronou.vo.SiteDetailsVO;
import com.it.ocs.task.model.EbayOrderModel;

import net.sf.json.JSONArray;

public interface IEBaySiteDetailsService {

	
	public OperationResult synchronouSiteDetails();
	
	public List<SiteDetailsVO> findSiteDetailsList();
	
	public OperationResult insertSiteDetails(List<EBaySiteDetailsModel> eBaySiteDetailsModels);
	
	public OperationResult updateSiteDetails(List<EBaySiteDetailsModel> eBaySiteDetailsModels);
	
	public List<EBaySiteDetailsModel> internalSelectSiteDetails();
	
	public JSONArray selectSiteDetailsJson();
}
