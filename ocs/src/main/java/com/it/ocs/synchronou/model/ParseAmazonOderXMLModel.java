package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.util.ValidationUtil;

public class ParseAmazonOderXMLModel extends ParseXMLModel{

	public ParseAmazonOderXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode[] columns = {
					    XMLNode.getInstance("LatestShipDate",XMLNode.EBAY_DATE),
					    XMLNode.getInstance("PurchaseDate",XMLNode.EBAY_DATE),
				        XMLNode.getInstance("AmazonOrderId"),
				        XMLNode.getInstance("LastUpdateDate",XMLNode.EBAY_DATE),
				        XMLNode.getInstance("OrderStatus"),
				        XMLNode.getInstance("SalesChannel"),
				        XMLNode.getInstance("LatestDeliveryDate",XMLNode.EBAY_DATE),
				        XMLNode.getInstance("BuyerName"),
				        XMLNode.getInstance("BuyerEmail"),
				        XMLNode.getInstance("PaymentMethod"),
			            XMLNode.getInstance("CurrencyCode"),
			            XMLNode.getInstance("Amount"),
			            XMLNode.getInstance("Name"),
			            XMLNode.getInstance("StateOrRegion"),
			            XMLNode.getInstance("AddressLine1"),
			            XMLNode.getInstance("AddressLine2"),
			            XMLNode.getInstance("AddressLine3"),
			            XMLNode.getInstance("City"),
			            XMLNode.getInstance("Phone"),
			            XMLNode.getInstance("CountryCode"),
			            XMLNode.getInstance("PostalCode"),
		};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("Orders_Order");
		for (Element element : elements) {
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			
			result.add(map);
		}
		return result;
	}
	
	public String getNextToken() {
		return this.getValueByName(this.getRoot(), "NextToken");
	}
}
