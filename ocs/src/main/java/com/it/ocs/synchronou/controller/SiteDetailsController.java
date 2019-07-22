package com.it.ocs.synchronou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.model.EBaySiteDetailsModel;
import com.it.ocs.synchronou.service.IEBaySiteDetailsService;
import com.it.ocs.synchronou.vo.SiteDetailsVO;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="/SiteDetails")
public class SiteDetailsController {

	@Autowired
	private IEBaySiteDetailsService siteDetailsService;
	@RequestMapping(value="show")
	public String show(){
		
		return "admin/ebaySynchronous/siteDetails";
		
	}
	
	@RequestMapping(value="synchronouSiteDetails")
	@ResponseBody
	public OperationResult synchronouSiteDetails(){
		
		return siteDetailsService.synchronouSiteDetails();
	}
	
	@RequestMapping(value="findSiteDetailsList")
	@ResponseBody 
	public List<SiteDetailsVO> findSiteDetailsList(){
		
		return siteDetailsService.findSiteDetailsList();
	}
	
	@RequestMapping(value="selectSiteDetailsJson")
	@ResponseBody
	public JSONArray selectSiteDetailsJson(){
		
		return siteDetailsService.selectSiteDetailsJson();
	}
	
	@RequestMapping(value="/internalSelectSiteDetails")
	@ResponseBody
	public List<EBaySiteDetailsModel> internalSelectSiteDetails(){
		
		return siteDetailsService.internalSelectSiteDetails();
	}
	
	
	
}
