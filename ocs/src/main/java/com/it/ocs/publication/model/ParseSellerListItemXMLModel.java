package com.it.ocs.publication.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.XMLNode;
import com.it.ocs.synchronou.util.ValidationUtil;

public class ParseSellerListItemXMLModel extends ParseXMLModel {

	public ParseSellerListItemXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode[] columns = { XMLNode.getInstance("ItemID"), 
							XMLNode.getInstance("ListingDetails_StartTime", XMLNode.EBAY_DATE),
							XMLNode.getInstance("ListingDetails_EndTime", XMLNode.EBAY_DATE),
							XMLNode.getInstance("ListingDetails_ViewItemURL"),
							XMLNode.getInstance("PromotionalSaleDetails_OriginalPrice"),
							XMLNode.getInstance("PromotionalSaleDetails_StartTime", XMLNode.EBAY_DATE),
							XMLNode.getInstance("PromotionalSaleDetails_EndTime", XMLNode.EBAY_DATE),
							XMLNode.getInstance("Quantity"), 
							XMLNode.getInstance("SellingStatus_QuantitySold"),
							XMLNode.getInstance("Site"), 
							XMLNode.getInstance("HitCount"),
							XMLNode.getInstance("SellingStatus_CurrentPrice"),
							XMLNode.getInstance("ListingType") };
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("ItemArray_Item");
		for (Element element : elements) {
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			//DE站点链接更换
			String site = map.get("Site").toString();
			String url = map.get("ListingDetails_ViewItemURL").toString();
			url = url.replace("http:", "https:");
			url = url.replace(".hk", "");
			if("Germany".equals(site)){
				url = url.replace(".com", ".de");
			}else if("UK".equals(site)){
				url = url.replace(".com", ".co.uk");
			}else if("Australia".equals(site)){
				url = url.replace(".com", ".com.au");
			}
			map.put("ListingDetails_ViewItemURL",url);
			List<Element> variationsElements = this.getElementChildByElement(element, "Variations_Variation");
			if (null != variationsElements && variationsElements.size() > 0) {
				List<Map<String, Object>> variations = getVariations(variationsElements);
				int totalQty = 0;
				int totalSold = 0;
				for(Map<String, Object> variation : variations){
					int qty = parseInt(variation.get("Quantity"));
					totalQty = totalQty + qty;
					int sqty = parseInt(variation.get("QuantitySold"));
					totalSold = totalSold + sqty;
				}
				map.put("Quantity",totalQty);
				map.put("SellingStatus_QuantitySold",totalSold);
				map.put("leaveQty", totalQty - totalSold);
				map.put("variations", variations);
			} else {
				
				map.put("leaveQty", parseInt(map.get("Quantity")) - parseInt(map.get("SellingStatus_QuantitySold")) );
				map.put("variations", null);
			}

			result.add(map);
		}
		return result;
	}
	
	public static int  parseInt(Object obj){
		if(null == obj){
			return 0;
		}
		String str = String.valueOf(obj);
		if("".equals(str.trim())){
			return 0;
		}else{
			return Integer.parseInt(str);
		}
	}
	
	private List<Map<String, Object>> getVariations(List<Element> elementChild) {
		XMLNode[] columns = { XMLNode.getInstance("SKU"), 
							  XMLNode.getInstance("Quantity"),
							  XMLNode.getInstance("QuantitySold")};
		List<Map<String, Object>> list = new ArrayList<>();
		for (Element element : elementChild) {
			Map<String, Object> map = parseRecord(element, columns);
			list.add(map);
		}
		return list;
	}

	public int getTotalPage() {
		String count = this.getValueByName(this.getRoot(), "TotalNumberOfPages");
		if(ValidationUtil.isNullOrEmpty(count)){
			return 0;
		}else{
			return Integer.parseInt(count);
		}
	}
}
