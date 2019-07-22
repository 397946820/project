package com.it.ocs.publication.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.XMLNode;

public class ParseItemSellXMLModel extends ParseXMLModel {

	public ParseItemSellXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);

	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode[] columns = { 	XMLNode.getInstance("ItemID"), 
								XMLNode.getInstance("ListingDetails_StartTime",XMLNode.EBAY_DATE),
								XMLNode.getInstance("ListingDetails_EndTime",XMLNode.EBAY_DATE),
								XMLNode.getInstance("SellingStatus_BidCount"),
								XMLNode.getInstance("SellingStatus_CurrentPrice"),
								XMLNode.getInstance("SellingStatus_QuantitySold"),
								XMLNode.getInstance("SellingStatus_ListingStatus"),
								XMLNode.getInstance("Site"),
								XMLNode.getInstance("Quantity"),
								XMLNode.getInstance("Currency"),
								XMLNode.getInstance("ListingType"),
								XMLNode.getInstance("BestOfferDetails_BestOfferCount"),
								XMLNode.getInstance("BestOfferDetails_BestOfferEnabled"),
								XMLNode.getInstance("HitCounter")
							};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("ItemArray_Item");
		for (Element element : elements) {
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			List<Element>  variationsElements = this.getElementChildByElement(element, "Variations_Variation");
			if(null != variationsElements && variationsElements.size() > 0){
				map.put("variations", getVariations(variationsElements));
			}else{
				map.put("variations", null);
			}
			result.add(map);
		}
		return result;
	}
	private List<Map<String,Object>> getVariations(List<Element> elementChild) {
		XMLNode [] columns = {XMLNode.getInstance("Quantity"),
							  XMLNode.getInstance("SKU"),
							  XMLNode.getInstance("QuantitySold")
							 };
		List<Map<String,Object>> list = new ArrayList<>();
		for(Element element :elementChild){
			Map<String,Object> map = parseRecord(element,columns);
			element = this.formateElement(element);
			List<Element>  variationsElements = this.getElementChildByElement(element, "VariationSpecifics_NameValueList");
			List<String> values = new ArrayList<>();
			for(Element ele : variationsElements){
				ele = this.formateElement(ele);
				values.add(this.getValueByName(ele, "Value"));
			}
			map.put("values", values);
			list.add(map);
		}
		return list;
	}

}
