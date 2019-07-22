package com.it.ocs.synchronou.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.it.ocs.common.util.CollectionUtil;

public class ParseWalmartOrderXMLModel extends ParseXMLModel{

	public ParseWalmartOrderXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode[] columns = {
					    XMLNode.getInstance("purchaseOrderId"),
					    XMLNode.getInstance("customerOrderId"),
					    XMLNode.getInstance("customerEmailId"),
		    			XMLNode.getInstance("orderDate",XMLNode.EBAY_DATE),
						XMLNode.getInstance("shippingInfo_phone"),
						XMLNode.getInstance("shippingInfo_estimatedDeliveryDate",XMLNode.EBAY_DATE),
						XMLNode.getInstance("shippingInfo_estimatedShipDate",XMLNode.EBAY_DATE),
						XMLNode.getInstance("shippingInfo_methodCode"),
						XMLNode.getInstance("shippingInfo_postalAddress_name"),
						XMLNode.getInstance("shippingInfo_postalAddress_address1"),
						XMLNode.getInstance("shippingInfo_postalAddress_address2"),
						XMLNode.getInstance("shippingInfo_postalAddress_city"),
						XMLNode.getInstance("shippingInfo_postalAddress_state"),
						XMLNode.getInstance("shippingInfo_postalAddress_postalCode"),
						XMLNode.getInstance("shippingInfo_postalAddress_country"),
						XMLNode.getInstance("shippingInfo_postalAddress_addressType") 
					    
		};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("elements_order");
		if(CollectionUtil.isNullOrEmpty(elements)) {
			elements = this.getElementChild("order");
		}
		for (Element element : elements) {
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			List<Map<String,Object>> lines = getOrderLines(this.getElementChildByElement(element, "orderLines_orderLine"));
			for(Map<String,Object> obj:lines){
				obj.put("purchaseOrderId", map.get("purchaseOrderId"));
				obj.put("customerOrderId", map.get("customerOrderId"));
			}
			map.put("line_total", lines.size());
			map.put("lines", lines);
			result.add(map);
		}
		return result;
	}
	private List<Map<String, Object>> getOrderLines(List<Element> elementChild) {
		XMLNode [] columns = {
				XMLNode.getInstance("lineNumber"),
				XMLNode.getInstance("item_productName"),
				XMLNode.getInstance("item_sku"),
				XMLNode.getInstance("orderLineQuantity_unitOfMeasurement"),
				XMLNode.getInstance("orderLineQuantity_amount"),
				XMLNode.getInstance("statusDate",XMLNode.EBAY_DATE),
				XMLNode.getInstance("orderLineStatuses_orderLineStatus_status"),
				XMLNode.getInstance("orderLineStatuses_orderLineStatus_statusQuantity_unitOfMeasurement"),
				XMLNode.getInstance("orderLineStatuses_orderLineStatus_statusQuantity_amount"),
				XMLNode.getInstance("orderLineStatuses_orderLineStatus_trackingInfo_shipDateTime",XMLNode.EBAY_DATE),
				XMLNode.getInstance("orderLineStatuses_orderLineStatus_trackingInfo_carrierName_carrier"),
				XMLNode.getInstance("orderLineStatuses_orderLineStatus_trackingInfo_methodCode"),
				XMLNode.getInstance("orderLineStatuses_orderLineStatus_trackingInfo_trackingNumber"),
				XMLNode.getInstance("orderLineStatuses_orderLineStatus_trackingInfo_trackingURL")			  
		};
		List<Map<String,Object>> list = new ArrayList<>();
		for(Element element :elementChild){
			Map<String,Object> map = parseRecord(element,columns);
			List<Map<String,Object>> charges = getCharges(this.getElementChildByElement(element, "charges_charge"));
			String itemPrice = "";
			String itemPrice_currency = "";
			String shippingPrice = "";
			String shipping_currency = "";
			for(Map<String,Object> obj : charges){
				if("ItemPrice".equals(obj.get("chargeName"))){
					itemPrice = (String)obj.get("chargeAmount_amount");
					itemPrice_currency = (String)obj.get("chargeAmount_currency");
				}
				if("Shipping".equals(obj.get("chargeName"))){
					shippingPrice = (String)obj.get("chargeAmount_amount");
					shipping_currency = (String)obj.get("chargeAmount_currency");
				}
			}
			map.put("itemPrice", itemPrice);
			map.put("itemPrice_currency", itemPrice_currency);
			map.put("shippingPrice", shippingPrice);
			map.put("shipping_currency", shipping_currency);
			list.add(map);
		}
		return list;
	}

	private List<Map<String, Object>> getCharges(List<Element> elementChild) {
		XMLNode [] columns = {
				XMLNode.getInstance("chargeType"),
				XMLNode.getInstance("chargeName"),
				XMLNode.getInstance("chargeAmount_currency"),
				XMLNode.getInstance("chargeAmount_amount")			  
		};
		List<Map<String,Object>> list = new ArrayList<>();
		for(Element element :elementChild){
			Map<String,Object> map = parseRecord(element,columns);
			list.add(map);
		}
		return list;
	}

	public String getNextCursor() {
		return this.getValueByName(this.getRoot(), "nextCursor");
	}
	public static void main(String[] args) {
		File file = new File("d:\\data.xml");
		 SAXReader reader=new SAXReader();
		 try {
			Document doc=reader.read(file);
			ParseWalmartOrderXMLModel parse = new ParseWalmartOrderXMLModel(doc,"http://walmart.com/mp/v3/orders");
			System.out.println(parse.getNextCursor());
			parse.getResult();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
