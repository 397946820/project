package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import com.it.ocs.synchronou.util.ValidationUtil;

public class ParseOrderXMLModel extends ParseXMLModel{

	public ParseOrderXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode [] columns = {XMLNode.getInstance("OrderID"),
						      XMLNode.getInstance("OrderStatus"),
						      XMLNode.getInstance("AdjustmentAmount",XMLNode.EBAY_PRICE,"currencyID"),
						      XMLNode.getInstance("AmountPaid",XMLNode.EBAY_PRICE,"currencyID"),
						      XMLNode.getInstance("AmountSaved",XMLNode.EBAY_PRICE,"currencyID"),
						      XMLNode.getInstance("CreatedTime",XMLNode.EBAY_DATE),
						      XMLNode.getInstance("PaymentMethods"),
						      XMLNode.getInstance("SellerEmail"),
						      XMLNode.getInstance("Subtotal",XMLNode.EBAY_PRICE,"currencyID"),
						      XMLNode.getInstance("Total",XMLNode.EBAY_PRICE,"currencyID"),
						      XMLNode.getInstance("BuyerUserID"),
						      XMLNode.getInstance("PaidTime",XMLNode.EBAY_DATE),
						      XMLNode.getInstance("ShippedTime",XMLNode.EBAY_DATE),
						      XMLNode.getInstance("IntegratedMerchantCreditCardEnabled",XMLNode.EBAY_BOOLEAN),
						      XMLNode.getInstance("EIASToken"),
						      XMLNode.getInstance("PaymentHoldStatus"),
						      XMLNode.getInstance("IsMultiLegShipping",XMLNode.EBAY_BOOLEAN),
						      XMLNode.getInstance("SellerUserID"),
						      XMLNode.getInstance("SellerEIASToken"),
						      XMLNode.getInstance("CancelStatus"),
						      XMLNode.getInstance("ExtendedOrderID"),
						      XMLNode.getInstance("ContainseBayPlusTransaction",XMLNode.EBAY_BOOLEAN),
						      XMLNode.getInstance("CheckoutStatus_LastModifiedTime",XMLNode.EBAY_DATE),
						      XMLNode.getInstance("CheckoutStatus"),
						      XMLNode.getInstance("ShippingDetails"),
						      XMLNode.getInstance("ShippingAddress"),
						      XMLNode.getInstance("ShippingServiceSelected"),
						      XMLNode.getInstance("ExternalTransaction"),
						      XMLNode.getInstance("TransactionArray"),
						      XMLNode.getInstance("MonetaryDetails"),
						      XMLNode.getInstance("Order_ShippingDetails_SellingManagerSalesRecordNumber"),
						      XMLNode.getInstance("CheckoutStatus_Status"),
						      XMLNode.getInstance("ShippingServiceSelected_ShippingService"),
						      XMLNode.getInstance("ExternalTransaction_ExternalTransactionID"),
						      XMLNode.getInstance("TransactionArray_Transaction_Buyer_Email")
						      };
		List<Map<String,Object>> list = new ArrayList<>();
		List<Element> elements = this.getElementChild("OrderArray_Order");
		for(Element element :elements){
			element = this.formateElement(element);
			Map<String,Object> map = parseRecord(element,columns);
			List<Map<String,Object>> trans = getItems(this.getElementChildByElement(element, "TransactionArray_Transaction"));
			List<Map<String,Object>> addMap = new ArrayList<>();
			for(Map<String,Object> one : trans){
				one.put("OrderID", map.get("OrderID"));
				String price = (String)one.get("TransactionPrice");
				String str[] = price.split(" ");
				one.put("currencycode",str[0]);
				one.put("TransactionPrice",str[1]);
				String variationSKU = (String)one.get("Variation_SKU");
				if(null != variationSKU && !"".equals(variationSKU)){
					if(variationSKU.contains(",")){
						String skus[] = variationSKU.split(",");
						//多个sku 复制节点
						for(int i=0;i<skus.length;i++){
							if(i == 0){
								one.put("Item_SKU",skus[i]);
							}else{
								Map<String,Object> clone = new HashMap<>();
								BeanUtils.copyProperties(one, clone);
								clone.put("Item_SKU",skus[i]);
								clone.put("TransactionPrice", "0");
								addMap.add(clone);
							}
						}
					}else{
						one.put("Item_SKU",variationSKU);
					}
				}
			}
			trans.addAll(addMap);
			map.put("items", trans);
			list.add(map);
		}
		return list;
	}

	private List<Map<String,Object>> getItems(List<Element> elementChild) {
		XMLNode [] columns = {XMLNode.getInstance("TransactionID"),
							  XMLNode.getInstance("Item_Site"),
							  XMLNode.getInstance("Item_ItemID"),
							  XMLNode.getInstance("Item_SKU"),
							  XMLNode.getInstance("Item_Title"),
							  XMLNode.getInstance("Variation_SKU"),
							  XMLNode.getInstance("Variation_Title"),
							  XMLNode.getInstance("QuantityPurchased"),
							  XMLNode.getInstance("TransactionPrice",XMLNode.EBAY_PRICE,"currencyID"),
							  XMLNode.getInstance("ShippingCarrierUsed"),
							  XMLNode.getInstance("ShipmentTrackingNumber"),
							  XMLNode.getInstance("SellingManagerSalesRecordNumber")
							 };
		List<Map<String,Object>> list = new ArrayList<>();
		for(Element element :elementChild){
			//element = this.formateElement(element);
			Map<String,Object> map = parseRecord(element,columns);
			list.add(map);
		}
		return list;
	}

	public int getPage() {
		String count = this.getValueByName(this.getRoot(), "TotalNumberOfPages");
		if(ValidationUtil.isNullOrEmpty(count)){
			return 0;
		}else{
			return Integer.parseInt(count);
		}
	}

}
