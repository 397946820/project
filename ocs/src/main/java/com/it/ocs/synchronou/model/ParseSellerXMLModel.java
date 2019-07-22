package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.springframework.beans.BeanUtils;

import com.it.ocs.synchronou.util.ValidationUtil;

public class ParseSellerXMLModel {
	private Document document;
	private Element root;
	
	public ParseSellerXMLModel(Document document){
		this.document = document;
		root = document.getRootElement();
	}

	/**
	 * 获取解析的数据集合
	 * @return
	 */
	public List<Map<String,Object>> getParseSaleResult(){
		List<Map<String,Object>> list = new ArrayList<>();
		List<Element> elements = getSaleRecord();
		for(Element element :elements){
			try {
				Document doc = DocumentHelper.parseText(element.asXML());
				list.add(parseSaleRecord(doc.getRootElement()));
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 构建xpath
	 * @param xpth
	 * @return
	 */
	private XPath getXPth(Element element,String xpth){
		Map map = new HashMap();  
        map.put("design","urn:ebay:apis:eBLBaseComponents");  
        //更新xpath,设置命名空间
        xpth =  xpth.replace("//", "--design:");
        xpth =  xpth.replace("/", "/design:");
        xpth =  xpth.replace("--", "//");
        XPath x = element.createXPath(xpth);  
        x.setNamespaceURIs(map); 
        return x;
	}

	/**
	 * 解析单个销售记录
	 * @param element
	 * @return
	 */
	private Map<String, Object> parseSaleRecord(Element element) {
		String[] cloumns = {"OrderID","ShippingAddress_Name","ShippingAddress_PostalCode","ShippingType",
							"TotalAmount","TotalQuantity","ActualShippingCost","CheckoutStatus","PaidStatus",
							"ShippedStatus","PaymentMethodUsed","FeedbackSent","TotalEmailsSent","ShippedTime",
							"PaidTime","SalePrice","DaysSinceSale","BuyerID","BuyerEmail","SaleRecord_SaleRecordID","CreationTime"
						   };
		Map<String, Object> map = getValuesByNames(element,cloumns);
		String xml = element.asXML();
		if(xml.contains("'")){
			xml = xml.replaceAll("'", "''");
		}
		map.put("xml",xml);//element.asXML()
		map.put("currency", getCurrency(element));
		map.put("saleSKUInfo",parseSKUInfo(getElementChild(element,"SellingManagerSoldTransaction")));
		return map;
	}
	
	/**
	 * 解析销售订单单个sku信息
	 * @param element
	 * @return
	 */
	private Map<String, Object> parseSKURecord(Element element) {
		String[] cloumns = {"OrderLineItemID","TransactionID","SaleRecordID","ItemID","ItemTitle","PaidStatus",
							"ShippedStatus","CreationTime","QuantitySold","ListingType","Relisted","SecondChanceOfferSent",
							"CustomLabel","SoldOn","ListedOn","CharityListing"};
		return getValuesByNames(element,cloumns);
	}
	
	/**
	 * 解析一个订单每个SKU的信息
	 * @param elements
	 * @return
	 */
	private List<Map<String,Object>> parseSKUInfo(List<Element> elements){
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		for(Element element : elements){
			try {
				Document doc = DocumentHelper.parseText(element.asXML());
				mapList.add(parseSKURecord(doc.getRootElement()));
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return mapList;
		
	}
	/**
	 * 获取一个节点的一个值
	 * @param element
	 * @param name
	 * @return
	 */
	private String getNodeValueByName(Element element,String name){
		XPath xpath = getXPth(element,name);
		Node node = xpath.selectSingleNode(element);
		if(null == node){
			return "";
		}

		String value = node.getText();
		if(name.equals("ShippingAddress_PostalCode")){
			System.out.println(value);
		}
		if(ValidationUtil.isNullOrEmpty(value)){
			return "";
		}else{
			return value;
		}
	}
	/**
	 * 获取一个xml节点下指定的每个节点的值
	 * @param element
	 * @param names
	 * @return
	 */
	private Map<String, Object> getValuesByNames(Element element,String[] names){
		//System.out.println(element.getDocument().getRootElement().asXML());
		Map<String, Object> map = new HashMap<>();
		for(int i=0;i<names.length;i++){
			String name = names[i];
			String key = name;
			if(name.contains("_")){
				name = name.replaceAll("_", "/");
			}
			name = "//"+name;
			map.put(key, getNodeValueByName(element,name));
		}
		return map;
	}

	/**
	 * 获取所以的销售记录
	 * @return
	 */
	private List<Element> getSaleRecord(){
		return getElementChild(root,"SaleRecord");
	}
	
	/**
	 * 找到一个节点的子节点
	 * @param element
	 * @param name
	 * @return
	 */
	private List<Element> getElementChild(Element element,String name){
		List<Element> listElement = element.elements();
		List<Element> returnList = new ArrayList<>();
		for (Element e : listElement) {// 遍历所有一级子节点  
        	if(name.equals(e.getName())){
        		returnList.add(e);
        	}
        } 
		return returnList;
	}
	
	/**
	 * 全遍历方式获取第一个找的name的节点的值
	 * @param name
	 * @return
	 */
	public String getNodeValueByName(String name){
		List<Element> listElement = root.elements();
		return getValueByName(listElement,name);
	}
	
	/**
	 * 根据名称获取第一个找到的节点的属性值（节点遍历方式）
	 * @param listElement
	 * @param name
	 * @return
	 */
	private String getValueByName(List<Element> listElement,String name){
		String value = null;  
        for (Element e : listElement) {// 遍历所有一级子节点  
        	if(name.equals(e.getName())){
        		List<Element> elements = e.elements();
            	if(null != elements && elements.size()>0){
            		value = e.asXML();
            	}else{
            		value = e.getStringValue();
            	}
        	}else{
        		List<Element> elements = e.elements();
            	if(null != elements && elements.size()>0){
            		value = getValueByName(elements,name);// 递归  
            	}
        	}
        	if(null != value){
        		return value;
        	}
        	
        } 
        return value;
	}
	
	/**
	 * 总页数
	 * @return
	 */
	public int getPage(){
		XPath xpath = getXPth(root,"//TotalNumberOfPages");
		Node node = xpath.selectSingleNode(root);
		String count = node.getText();
		if(ValidationUtil.isNullOrEmpty(count)){
			return 0;
		}else{
			return Integer.parseInt(count);
		}
	}
	
	private String getCurrency(Element element){
		XPath xpath = getXPth(element,"//TotalAmount");
		Node node = xpath.selectSingleNode(element);
		if(null == node) {return "";}
		Element ele = (Element)node;
		String value = ele.attributeValue("currencyID");
		if(null == value){
			value ="";
		}
		return value;
	}
}
