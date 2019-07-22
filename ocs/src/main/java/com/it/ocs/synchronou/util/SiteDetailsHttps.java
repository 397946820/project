package com.it.ocs.synchronou.util;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.model.EBaySiteDetailsModel;
import com.it.ocs.synchronou.service.impl.BaseHttpsService;
import com.it.ocs.synchronou.vo.SiteDetailsVO;

public class SiteDetailsHttps extends BaseHttpsService {

	
	
	public List<EBaySiteDetailsModel> xmlToModel(Document document){
		List<EBaySiteDetailsModel> eBaySiteDetailsModels = new ArrayList<>();
		Element root = document.getRootElement();
		
		List<Element> siteDetails = root.elements("SiteDetails");
		for (Element element : siteDetails) {
			
			EBaySiteDetailsModel eBaySiteDetailsModel = new EBaySiteDetailsModel();
			
			eBaySiteDetailsModel.setSite(element.element("Site").getTextTrim());
			eBaySiteDetailsModel.setSite_id(Long.parseLong(element.element("SiteID").getTextTrim()));
			eBaySiteDetailsModel.setDetail_version(Long.parseLong(element.element("DetailVersion").getTextTrim()));
			eBaySiteDetailsModel.setUpdate_time(element.element("UpdateTime").getTextTrim());
			eBaySiteDetailsModels.add(eBaySiteDetailsModel);
		}
		return eBaySiteDetailsModels;
	}
	
	
}
