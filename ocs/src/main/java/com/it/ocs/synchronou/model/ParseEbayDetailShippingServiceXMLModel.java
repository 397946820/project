package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class ParseEbayDetailShippingServiceXMLModel extends ParseXMLModel {

	public ParseEbayDetailShippingServiceXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode[] columns = { XMLNode.getInstance("Description"),
							  XMLNode.getInstance("InternationalService"),
							  XMLNode.getInstance("ShippingService"),
							  XMLNode.getInstance("ShippingServiceID"),
							  XMLNode.getInstance("ShippingTimeMax"),
							  XMLNode.getInstance("ShippingTimeMin"),
							  XMLNode.getInstance("ValidForSellingFlow"),
							  XMLNode.getInstance("DetailVersion"),
							  XMLNode.getInstance("UpdateTime",XMLNode.EBAY_DATE),
							  XMLNode.getInstance("ShippingCategory"),
							  XMLNode.getInstance("DimensionsRequired"),
							  XMLNode.getInstance("WeightRequired")
							};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("ShippingServiceDetails");
		for (Element element : elements) {
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			//ServiceType
			List<Element> serviceTypes = this.getElementChildByElement(element, "ServiceType");
			StringBuffer serviceTypeBuffer = new StringBuffer();
			if(null != serviceTypes){
				for(Element e :serviceTypes){
					if(!"".equals(serviceTypeBuffer.toString())){
						serviceTypeBuffer.append(",");
					}
					serviceTypeBuffer.append(e.getTextTrim());
				}
			}
			map.put("ServiceType", serviceTypeBuffer.toString());
			
			//ShippingPackage
			List<Element> shippingPackages = this.getElementChildByElement(element, "ShippingPackage");
			StringBuffer shippingPackageBuffer = new StringBuffer();
			if(null != shippingPackages){
				for(Element e :shippingPackages){
					if(!"".equals(shippingPackageBuffer.toString())){
						shippingPackageBuffer.append(",");
					}
					shippingPackageBuffer.append(e.getTextTrim());
				}
			}
			map.put("ShippingPackage", serviceTypeBuffer.toString());
			
			result.add(map);
		}
		return result;
	}

}
