package com.it.ocs.synchronou.model;

import org.dom4j.Element;

import net.sf.json.xml.XMLSerializer;

public class XMLNode {
	public final static int EBAY_DATE = 1;
	public final static int EBAY_PRICE = 2;
	public final static int STRING = 0;
	public final static int EBAY_BOOLEAN = 3;
	public final static int AMAZON_REPORT_DATE = 4;
	/**
	 * UTC时间，格式如"yyyy-MM-dd'T'HH:mm:ss+hh:mm"
	 */
	public final static int DATE_UTC = 11;
	
	private String name;
	private String value;
	private String AttributeName;
	private String AttributeValue;
	private int type = 0;
	private Element element;
	private boolean hasChild;
	
	public XMLNode(String name){
		this.name = name;
	}
	public XMLNode(String name,int type){
		this.name = name;
		this.type = type;
	}
	public XMLNode(String name,int type,String AttributeName){
		this.name = name;
		this.type = type;
		this.AttributeName = AttributeName;
	}
	public static XMLNode getInstance(String name){
		return new XMLNode(name);
	}
	public static XMLNode getInstance(String name,int type){
		return new XMLNode(name,type);
	}
	public static XMLNode getInstance(String name,int type,String AttributeName){
		return new XMLNode(name,type,AttributeName);
	}
	public String getName() {
		return name;
	}

	public String getAttributeName() {
		return AttributeName;
	}

	public String getAttributeValue() {
		if(null != element){
			return element.attributeValue(AttributeName);
		}
		return AttributeValue;
	}
	
	public int getType() {
		return type;
	}
	
	public String getValue() {
		if(hasChild){
			value = new XMLSerializer().read(element.asXML()).toString();
		}else{
			value = element.getText();
		}
		
		
		if(null == value || "".equals(value)){
			return "";
		}
		switch(type){
			case EBAY_DATE: 
				value = value.replace("Z", "");
				value = value.replace("T", " ");
				break;
			case DATE_UTC:
				value = value.replace("T", " ");
				value = value.split("\\+")[0];
				break;
			case EBAY_PRICE:
				if(null !=AttributeName){
					String attrValue = getAttributeValue();
					if(null != attrValue && !"".equals(attrValue)){
						value = getAttributeValue() + " "+ value;
					}
				}
				break;
			case EBAY_BOOLEAN :
				if("true".toUpperCase().equals(value.toUpperCase())){
					value = "1";
				}else if("false".toUpperCase().equals(value.toUpperCase())){
					value = "0";
				}else{
					value = "";
				}
				break;
			case AMAZON_REPORT_DATE:
				value = value.replace("Z", "");
				value = value.replace("T", " ");
				break;
			default:
				break;
		}
		return value;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}
	public String getXpathName() {
		String key = name;
		if(key.contains("_")){
			key = key.replaceAll("_", "/");
		}
		key = "//"+key;
		return key;
	}
	public boolean isHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	
}
