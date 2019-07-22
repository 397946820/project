package com.it.ocs.sys.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.XMLNode;

public class ParseEbayItemXMLModel extends ParseXMLModel {

	public ParseEbayItemXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode[] columns = {
						XMLNode.getInstance("ItemID"),
					    XMLNode.getInstance("ListingDetails_StartTime",XMLNode.EBAY_DATE),
					    XMLNode.getInstance("ListingDetails_EndTime",XMLNode.EBAY_DATE)
					    
		};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("Item");
		for (Element element : elements) {
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			result.add(map);
		}
		return result;
	}

}
