package com.it.ocs.synchronou.util;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.model.EBayStoreCategoryModel;
import com.it.ocs.synchronou.service.impl.BaseHttpsService;

public class StoreCategoryHttps extends BaseHttpsService {

	private List<EBayStoreCategoryModel> eBayStoreCategoryModels = new ArrayList<>();
	
	public List<EBayStoreCategoryModel> xmlToModel(Document doc){
		Element roElement = doc.getRootElement();
		Element store = roElement.element("Store");
		Element customCategories = store.element("CustomCategories");
		List<Element> customCategory = customCategories.elements("CustomCategory");
		
		parsingXml(customCategory,0L);
		return eBayStoreCategoryModels;
	}
	
public  void  parsingXml(List<Element> customCategory,Long parent_category_id){
		
		for(int i=0; i<customCategory.size();i++){
			EBayStoreCategoryModel storeModel = new EBayStoreCategoryModel();
			storeModel.setName(customCategory.get(i).element("Name").getTextTrim().toString());
			storeModel.setCategory_id(Long.parseLong(customCategory.get(i).element("CategoryID").getTextTrim().toString()));
			storeModel.setCategory_order(Long.parseLong(customCategory.get(i).element("Order").getTextTrim().toString()));
			storeModel.setParent_category_id(parent_category_id);
			List<Element> ChildCategory =  customCategory.get(i).elements("ChildCategory");
			if(ChildCategory.size()>0){
				
				storeModel.setChild_category("true");
				eBayStoreCategoryModels.add(storeModel);
				parsingXml(ChildCategory,Long.parseLong(customCategory.get(i).element("CategoryID").getTextTrim().toString()));
				
			}else{
				storeModel.setChild_category("flase");
				eBayStoreCategoryModels.add(storeModel);
			}
			
			
		}
	}

public List<EBayStoreCategoryModel> geteBayStoreCategoryModels() {
	return eBayStoreCategoryModels;
}

public void seteBayStoreCategoryModels(List<EBayStoreCategoryModel> eBayStoreCategoryModels) {
	this.eBayStoreCategoryModels = eBayStoreCategoryModels;
}


}
