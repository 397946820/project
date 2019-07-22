package com.it.ocs.ebaySales.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.XMLNode;
import com.it.ocs.synchronou.util.ValidationUtil;

public class ParseBaseOffersXMLModel extends ParseXMLModel {

	public ParseBaseOffersXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode[] columns = {XMLNode.getInstance("Role"), 
							XMLNode.getInstance("BuyItNowPrice",XMLNode.EBAY_PRICE,"currencyID"),
							XMLNode.getInstance("ItemID"),
							XMLNode.getInstance("Title")
							};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("ItemBestOffersArray_ItemBestOffers");
		for (Element element : elements) {
			Map<String, Object> map = parseRecord(element, columns);
			String newPrice = map.get("BuyItNowPrice").toString();
			if(null != newPrice && newPrice.contains(" ")){
				String str[] = newPrice.split(" ");
				map.put("currencyID", str[0]);
				map.put("BuyItNowPrice", str[1]);
			}else{
				map.put("currencyID", "");
			}
			List<Map<String,Object>> bestOffers = getBestOffers(this.getElementChildByElement(element, "BestOfferArray_BestOffer"));
			for(Map<String,Object> offer : bestOffers){
				offer.putAll(map);
				result.add(offer);
			}
		}
		return result;
	}
	private List<Map<String,Object>> getBestOffers(List<Element> elementChild) {
		XMLNode [] columns = {XMLNode.getInstance("BestOfferID"),
							  XMLNode.getInstance("ExpirationTime",XMLNode.EBAY_DATE),
							  XMLNode.getInstance("Email"),
							  XMLNode.getInstance("FeedbackScore"),
							  XMLNode.getInstance("RegistrationDate",XMLNode.EBAY_DATE),
							  XMLNode.getInstance("UserID"),
							  XMLNode.getInstance("StateOrProvince"),
							  XMLNode.getInstance("CountryName"),
							  XMLNode.getInstance("PostalCode"),
							  XMLNode.getInstance("Price"),
							  XMLNode.getInstance("Status"),
							  XMLNode.getInstance("Quantity"),
							  XMLNode.getInstance("BuyerMessage"),
							  XMLNode.getInstance("SellerMessage"),
							  XMLNode.getInstance("BestOfferCodeType")
							 };
		List<Map<String,Object>> list = new ArrayList<>();
		for(Element element :elementChild){
			list.add(parseRecord(element,columns));
		}
		return list;
	}
	public int getTotalPages() {
		String count = this.getValueByName(this.getRoot(), "TotalNumberOfPages");
		if (ValidationUtil.isNullOrEmpty(count)) {
			return 0;
		} else {
			return Integer.parseInt(count);
		}
	}

}
