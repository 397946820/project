package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class ParseDisputeInfoXMLModel extends ParseXMLModel{

	public ParseDisputeInfoXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode[] columns = {
					    XMLNode.getInstance("DisputeID"),
					    XMLNode.getInstance("DisputeRecordType"),
					    XMLNode.getInstance("DisputeState"),
					    XMLNode.getInstance("DisputeStatus"),
					    XMLNode.getInstance("BuyerUserID"),
					    XMLNode.getInstance("SellerUserID"),
					    XMLNode.getInstance("TransactionID"),
					    XMLNode.getInstance("ItemID"),
					    XMLNode.getInstance("Quantity"),
					    XMLNode.getInstance("ConvertedCurrentPrice",XMLNode.EBAY_PRICE,"currencyID"),
					    XMLNode.getInstance("CurrentPrice",XMLNode.EBAY_PRICE,"currencyID"),
					    XMLNode.getInstance("Site"),
					    XMLNode.getInstance("Title"),
					    XMLNode.getInstance("DisputeReason"),
					    XMLNode.getInstance("DisputeExplanation"),
					    XMLNode.getInstance("DisputeCreditEligibility"),
					    XMLNode.getInstance("DisputeCreatedTime",XMLNode.EBAY_DATE),
					    XMLNode.getInstance("DisputeModifiedTime",XMLNode.EBAY_DATE),
					    XMLNode.getInstance("OrderLineItemID")
		};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("Dispute");
		for (Element element : elements) {
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			List<Map<String, Object>> messages = getDisputeMessages(this.getElementChildByElement(element, "DisputeMessage"));
			map.put("DisputeMessage", messages);
			result.add(map);
		}
		return result;
	}
	
	public List<Map<String, Object>> getDisputeMessages(List<Element> elements){
		XMLNode [] columns = {XMLNode.getInstance("MessageID"),
				  XMLNode.getInstance("MessageSource"),
				  XMLNode.getInstance("MessageCreationTime",XMLNode.EBAY_DATE),
				  XMLNode.getInstance("MessageText")
				 };
		List<Map<String,Object>> list = new ArrayList<>();
		for(Element element :elements){
			element = this.formateElement(element);
			Map<String,Object> map = parseRecord(element,columns);
			list.add(map);
		}
		return list;
	}
}
