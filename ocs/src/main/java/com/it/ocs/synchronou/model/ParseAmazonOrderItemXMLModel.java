package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class ParseAmazonOrderItemXMLModel extends ParseXMLModel{

	public ParseAmazonOrderItemXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode[] columns = {
				XMLNode.getInstance("OrderItemId"),
				XMLNode.getInstance("Title"),
				XMLNode.getInstance("ASIN"),
				XMLNode.getInstance("SellerSKU"),
				XMLNode.getInstance("QuantityOrdered"),
				XMLNode.getInstance("ConditionId"),
				XMLNode.getInstance("ItemPrice_Amount"),    
				XMLNode.getInstance("ItemTax_Amount"),   
				XMLNode.getInstance("ShippingPrice_Amount"),
				XMLNode.getInstance("ShippingDiscount_Amount"),
				XMLNode.getInstance("ShippingTax_Amount"),
				XMLNode.getInstance("GiftWrapPrice_Amount"),
				XMLNode.getInstance("PromotionDiscount_Amount"),
				XMLNode.getInstance("PromotionIds_PromotionId"),	        
		};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("OrderItems_OrderItem");
		for (Element element : elements) {
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			
			result.add(map);
		}
		return result;
	}

}
