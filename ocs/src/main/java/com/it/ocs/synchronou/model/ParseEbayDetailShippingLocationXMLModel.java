package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class ParseEbayDetailShippingLocationXMLModel extends ParseXMLModel {

	public ParseEbayDetailShippingLocationXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode[] columns = { XMLNode.getInstance("ShippingLocation"),
							  XMLNode.getInstance("Description"),
							  XMLNode.getInstance("DetailVersion"),
							  XMLNode.getInstance("UpdateTime",XMLNode.EBAY_DATE)
							};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("ShippingLocationDetails");
		for (Element element : elements) {
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			result.add(map);
		}
		return result;
	}

}
