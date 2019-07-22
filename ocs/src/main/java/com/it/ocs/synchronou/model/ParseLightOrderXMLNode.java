package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;

public class ParseLightOrderXMLNode extends ParseXMLModel{

	public ParseLightOrderXMLNode(Document document, String nameSpace) {
		super(document, nameSpace);
	}
	
	
	@Override
	public List<Map<String, Object>> getResult() {
		List<Map<String, Object>> result = new ArrayList<>();
		Element root = this.getRoot();
		while(root.getName() != "callReturn"){
			Iterator<Element> iterator = root.elementIterator();   
            Element e = iterator.next();  
            root = e;
		}
		List<Element> elements = this.getElementChild(root,"item");//this.getElementChild("callReturn_item");
		for(Element element : elements){
			//System.out.println(element.asXML());
			List<Element> childNodes = this.getElementChildByElement(element, "item_item");
			Map<String,Object> row = formatData(childNodes);
			result.add(row);
		}
		
		return result;
	}

	private Map<String, Object> formatData(List<Element> childNodes) {
		Map<String, Object> map = new HashMap<>();
		Map<String,String> relationMap = this.getDataRelation();
		for(Element element:childNodes){
			element = this.formateElement(element);
			String key = this.getValueByName(element, "key");
			String exist = relationMap.get(key);
			if(null != exist){
				String value = this.getValueByName(element, "value");
				if(null == value || value.contains("@xsi:nil")){
					value ="";
				}
				map.put(exist, value);
			}
			
		}
		return map;
	}
	
	private Map<String,String> getDataRelation(){
		Map<String,String> map = new HashMap<>();
		map.put("increment_id","order_id");
        map.put("order_id","light_order_id");
        map.put("state","state");
        map.put("status","status");
        map.put("discount_amount","discount_amount");
        map.put("subtotal","subtotal");
        map.put("grand_total","grand_total");
        map.put("shipping_amount","shipping_amount");
        map.put("tax_amount","tax_amount");
        map.put("total_canceled","total_canceled");
        map.put("total_invoiced","total_invoiced");
        map.put("total_paid","total_paid");
        map.put("total_qty_ordered","total_qty_ordered");
        map.put("total_refunded","total_refunded");
        map.put("gift_message","gift_message");
        map.put("weight","weight");
        map.put("customer_email","customer_email");
        map.put("customer_firstname","customer_firstname");
        map.put("customer_lastname","customer_lastname");
        map.put("customer_middlename","customer_middlename");
        map.put("global_currency_code","global_currency_code");
        map.put("order_currency_code","order_currency_code");
        map.put("remote_ip","remote_ip");
        map.put("shipping_description","shipping_description");
        map.put("shipping_method","shipping_method");
        map.put("customer_note_notify","customer_note");
        map.put("avs","avs");
        map.put("method","payment_method");
        map.put("updated_at", "light_updated_at");
        map.put("created_at", "light_created_at");
		return map;
	}

}
