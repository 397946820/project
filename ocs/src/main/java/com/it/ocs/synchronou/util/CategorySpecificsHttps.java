package com.it.ocs.synchronou.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.model.EBayCategorySpecificsModel;
import com.it.ocs.synchronou.model.EBayCategorySpecificsVModel;
import com.it.ocs.synchronou.service.impl.BaseHttpsService;

public class CategorySpecificsHttps extends BaseHttpsService {

	public static Map<String, Object> xmlToMap(Document document,String marketplace_id){
		Map<String, Object> results = new HashMap<String,Object>();
		List<EBayCategorySpecificsModel> eBayCategorySpecificsModels = new ArrayList<>();
		List<EBayCategorySpecificsVModel> eBayCategorySpecificsVModels = new ArrayList<>();
		Element root = document.getRootElement();
		
		List<Element> Recommendations = root.elements("Recommendations");
		for (Element element : Recommendations) {
			
			Long categoryID = Long.parseLong(element.element("CategoryID").getTextTrim());
			
			Long marketplaceId= Long.parseLong(marketplace_id);
			
			List<Element> NameRecommendation = element.elements("NameRecommendation");
		    
			for (Element element2 : NameRecommendation) {
				EBayCategorySpecificsModel eBayCategorySpecificsModel = new EBayCategorySpecificsModel();
				
				eBayCategorySpecificsModel.setCategory_id(categoryID);
				eBayCategorySpecificsModel.setMarketplace_id(marketplaceId);
				String name = element2.element("Name").getTextTrim();
				eBayCategorySpecificsModel.setName(name);
				String validationRules  = XmlAndJsonUtil.elementSubToJsonString(element2, "ValidationRules");
				eBayCategorySpecificsModel.setValidation_rules(validationRules);
				List<Element> valueRecommendation =element2.elements("ValueRecommendation");
				for (Element element3 : valueRecommendation) {
					
					EBayCategorySpecificsVModel eBayCategorySpecificsVModel = new EBayCategorySpecificsVModel();
					eBayCategorySpecificsVModel.setName(name);
					eBayCategorySpecificsVModel.setValue(element3.element("Value").getTextTrim());
					eBayCategorySpecificsVModel.setCategory_id(categoryID);
					eBayCategorySpecificsVModel.setMarketplace_id(marketplaceId);
					eBayCategorySpecificsVModels.add(eBayCategorySpecificsVModel);
					
				}
				eBayCategorySpecificsModels.add(eBayCategorySpecificsModel);
			}
			
		 
		}
		results.put("eBayCategorySpecificsModel", eBayCategorySpecificsModels);
		results.put("eBayCategorySpecificsVModel", eBayCategorySpecificsVModels);
		
		return results;
	}
}
