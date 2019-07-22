package com.it.ocs.synchronou.util;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.model.EBayCountryDetailsModel;
import com.it.ocs.synchronou.service.impl.BaseHttpsService;

public class CountryDetailsHttps extends BaseHttpsService {

		public List<EBayCountryDetailsModel> xmlToModel(Document document){
			List<EBayCountryDetailsModel>  eBayCountryDetailsModels = new ArrayList<>();
			Element root = document.getRootElement();
			List<Element> countryDetails = root.elements("CountryDetails");
			for (Element element : countryDetails) {
				
				EBayCountryDetailsModel eBayCountryDetailsModel = new EBayCountryDetailsModel();
				eBayCountryDetailsModel.setCountry(element.element("Country").getTextTrim());
				eBayCountryDetailsModel.setDescription(element.element("Description").getTextTrim());
				eBayCountryDetailsModel.setDetail_version(Long.parseLong(element.element("DetailVersion").getTextTrim()));
				eBayCountryDetailsModel.setUpdate_time(element.element("UpdateTime").getTextTrim());
				eBayCountryDetailsModels.add(eBayCountryDetailsModel);
			}
			return eBayCountryDetailsModels;
		}
}
