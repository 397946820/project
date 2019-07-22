package com.it.ocs.synchronou.util;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.model.EBayProductListingModel;
import com.it.ocs.synchronou.service.impl.BaseHttpsService;

public class ProductListingHttps extends BaseHttpsService{

	public EBayProductListingModel xmlToModel(Document document,Long marketplace_id){
		
		
		
			Element root = document.getRootElement();
			
			String ack = root.element("Ack").getTextTrim();
			if(ack.equalsIgnoreCase("Success")){
				
				EBayProductListingModel ebayProductListingModel = new EBayProductListingModel();
				
				ebayProductListingModel.setMarketplace_id(marketplace_id);
				
				Element categoryElement = root.element("Category");
				
				ebayProductListingModel.setCategory_id(Long.parseLong(categoryElement.element("CategoryID").getTextTrim()));
			
				ebayProductListingModel.setEan(categoryElement.element("EANEnabled").getTextTrim());
				
				ebayProductListingModel.setIsbn(categoryElement.element("ISBNEnabled").getTextTrim());
				
				ebayProductListingModel.setUpc(categoryElement.element("UPCEnabled").getTextTrim());
				
				Element conditionValues = categoryElement.element("ConditionValues");
				
				ebayProductListingModel.setCondition_help_url(conditionValues.element("ConditionHelpURL").getTextTrim());
				
				List<Element> conditionElements = conditionValues.elements("Condition");
				List<String> tags  = new ArrayList<>();
				tags.add("ID");
				tags.add("DisplayName");
				ebayProductListingModel.setConditions(XmlAndJsonUtil.elementsToJsonString(conditionElements, tags));
				return ebayProductListingModel;
		}else{
			return null;
		}
	}
}
