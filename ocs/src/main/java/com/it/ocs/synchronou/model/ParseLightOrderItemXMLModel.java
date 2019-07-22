package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class ParseLightOrderItemXMLModel extends ParseXMLModel{
	
	public ParseLightOrderItemXMLModel(Document document, String nameSpace) {
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
		List<Element> itemNodes  = this.getElementChild(root,"item");
		Map<String, Object> ship = null;
		for(Element element : itemNodes){
			element = this.formateElement(element);
			String key = this.getValueByName(element, "item_key");
			if("items".equals(key)){
				List<Element> elements = this.getElementChild(element,"value");
				elements = this.getElementChild(elements.get(0),"item");
				for(Element item :elements){
					List<Element> items = this.getElementChild(item,"item");
					Map<String, Object> map = formatData(items,0);
					result.add(map);
				}
			}
			if("shipping_address".equals(key)){
				List<Element> elements = this.getElementChild(element,"value");
				elements = this.getElementChild(elements.get(0),"item");
				ship = formatData(elements,1);
			}
		}
		for(Map<String, Object> map : result){
			for(Map.Entry<String, Object> entry:ship.entrySet()){
				map.put(entry.getKey(),entry.getValue());
			}
		}
		return result;
	}
	
	private Map<String, Object> formatData(List<Element> childNodes,int type) {
		Map<String, Object> map = new HashMap<>();
		Map<String,String> relationMap = null;
		if(type == 0){
			relationMap = this.getItemDataRelation();
		}else{
			relationMap = this.getShipDataRelation();
		}
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

	private Map<String, String> getItemDataRelation() {
		Map<String,String> map = new HashMap<>();
		map.put("item_id","light_item_id");
        map.put("weight","weight");
        map.put("sku","sku");
        map.put("name","name");
        map.put("free_shipping","free_shipping");
        map.put("is_qty_decimal","is_qty_decimal");
        map.put("no_discount","no_discount");
        map.put("qty_canceled","qty_canceled");
        map.put("qty_invoiced","qty_invoiced");
        map.put("qty_ordered","qty_ordered");
        map.put("qty_refunded","qty_refunded");
        map.put("qty_shipped","qty_shipped");
        map.put("price","price");
        map.put("base_price","base_price");
        map.put("original_price","original_price");
        map.put("tax_percent","tax_percent");
        map.put("tax_amount","tax_amount");
        map.put("tax_invoiced","tax_invoiced");
        map.put("discount_percent","discount_percent");
        map.put("discount_amount","discount_amount");
        map.put("amount_refunded","amount_refunded");
        map.put("row_total","row_total");
        map.put("gift_message_id","gift_message_id");
        map.put("gift_message","gift_message");
        map.put("created_at","light_created_at");
        map.put("updated_at","light_updated_at");
        map.put("ship_date","ship_at");
		return map;
	}
	private Map<String, String> getShipDataRelation() {
		Map<String,String> map = new HashMap<>();
		map.put("postcode","postcode");
		map.put("region","region");
        map.put("region_id","region_id");
        map.put("city","city");
        map.put("street","street");
        map.put("country_id","country_id");
        map.put("firstname", "shipping_firstname");
        map.put("middlename", "shipping_middlename");
        map.put("lastname", "shipping_lastname");
        map.put("telephone","telephone");
        return map;
	}
}
