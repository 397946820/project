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

public abstract class ParseXMLModel {
	private Document document;
	private Element root;
	private String nameSpace;
	
	protected ParseXMLModel(Document document,String nameSpace){
		this.document = document;
		root = document.getRootElement();
		this.nameSpace = nameSpace;
	}
	
	protected Element getRoot(){
		return root;
	}
	/**
	 * 
	 * @param target 目标节点名称
	 * @param columns 要获取的节点名称集合
	 * @return
	 */
	protected abstract List<Map<String,Object>>  getResult();
	
	/**
	 * 
	 * @param target 目标节点名称
	 * @param columns 要获取的节点名称集合
	 * @return
	 */
	protected List<Map<String,Object>> getRootResult(String target,XMLNode[] columns){
		List<Map<String,Object>> list = new ArrayList<>();
		List<Element> elements = this.getElementChild(root, target);
		for(Element element :elements){
			element = this.formateElement(element);
			list.add(parseRecord(element,columns));
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
        map.put("design",nameSpace);  
        //更新xpath,设置命名空间
        xpth =  xpth.replace("//", "--design:");
        xpth =  xpth.replace("/", "/design:");
        xpth =  xpth.replace("--", "//");
        XPath x = element.createXPath(xpth);  
        x.setNamespaceURIs(map); 
        return x;
	}
	
	/**
	 * 解析单个记录
	 * @param element
	 * @return
	 */
	protected Map<String, Object> parseRecord(Element element,XMLNode[] columns) {
		element = this.formateElement(element);
		Map<String, Object> map = getValuesByNames(element,columns);
		String xml = element.asXML();
		if(xml.contains("'")){
			xml = xml.replaceAll("'", "''");
		}
		map.put("xml",xml);//element.asXML()
		return map;
	}
	
	/**
	 * 获取一个节点的一个值
	 * @param element
	 * @param xmlNode
	 * @return
	 */
	private String getNodeValueByName(Element element,XMLNode xmlNode){
		XPath xpath = getXPth(element,xmlNode.getXpathName());
		Node node = xpath.selectSingleNode(element);
		if(null == node){
			return "";
		}else{
			Element ele = (Element)node;
			xmlNode.setElement(ele);
			if(null != ele.getText()&&!"".equals(ele.getText())){
				xmlNode.setHasChild(false);
			}else{
				xmlNode.setHasChild(this.hasChild(ele));
			}
			
		}
		return xmlNode.getValue();
	}
	/**
	 * 获取一个xml节点下指定的每个节点的值
	 * @param element
	 * @param columns
	 * @return
	 */
	private Map<String, Object> getValuesByNames(Element element,XMLNode[] columns){
		Map<String, Object> map = new HashMap<>();
		for(int i=0;i<columns.length;i++){
			XMLNode xmlNode = columns[i];
			map.put(xmlNode.getName(), getNodeValueByName(element,xmlNode));
		}
		return map;
	}

	
	/**
	 * 找到一个节点的子节点(xpth方式获取整个xml根节点)
	 * @param element
	 * @param name
	 * @return
	 */
	protected List<Element> getElementChild(String name){
		XPath xpath = getXPth(root,formatXpath(name));
		return (List<Element>) xpath.selectNodes(root);
	}
	/**
	 * 找到一个节点的子节点(xpth方式获取节点)
	 * @param element
	 * @param name
	 * @return
	 */
	protected List<Element> getElementChildByElement(Element element,String name){
		element = formateElement(element);
		XPath xpath = getXPth(element,formatXpath(name));
		return (List<Element>) xpath.selectNodes(element);
	}
	/**
	 * 找到一个节点的子节点(遍历方式)
	 * @param element
	 * @param name
	 * @return
	 */
	protected List<Element> getElementChild(Element element,String name){
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
	 * 格式化Element为单独的xml
	 * @param element
	 * @return
	 */
	protected Element formateElement(Element element){
		Element result = null;
		try {
			Document doc = DocumentHelper.parseText(element.asXML());
			result = doc.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取节点属性值
	 * @param element
	 * @param name
	 * @param attrName
	 * @return
	 */
	protected String getNodeAttributes(Element element,String name,String attrName){
		XPath xpath = getXPth(element,"//"+name);
		Node node = xpath.selectSingleNode(element);
		if(null == node) {return "";}
		Element ele = (Element)node;
		String value = ele.attributeValue(attrName);
		if(null == value){
			value ="";
		}
		return value;
	}
	/**
	 * 获取节点value
	 * @param element
	 * @param name
	 * @return
	 */
	protected String getValueByName(Element element,String name){
		return getNodeValueByName(element,XMLNode.getInstance(name));
	}
	
	/**
	 * 判断是否有子节点
	 * @param element
	 * @return
	 */
	private boolean hasChild(Element element){
		element = formateElement(element);
		XPath xpath = getXPth(element,"//*");
		List list = xpath.selectNodes(element);
		if(null != list && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 转化XPATH
	 * @param name
	 * @return
	 */
	private String formatXpath(String name){
		if(name.contains("_")){
			name = name.replaceAll("_", "/");
		}
		name = "//"+name;
		return name;
	}
	
	public static String getRequstMessage(Document doc,String nameSpace){
		String message = "";
		Map map = new HashMap();  
        map.put("design",nameSpace);  
        XPath x = doc.createXPath("//design:Ack");  
        x.setNamespaceURIs(map); 
        Node node = x.selectSingleNode(doc);
        String ack = node.getText();
        if(!"Success".equals(ack)){
        	x = doc.createXPath("//design:ShortMessage"); 
        	x.setNamespaceURIs(map); 
        	List<Node> nodes = x.selectNodes(doc);
        	for(Node node1: nodes){
        		message = message + node1.getText()+";";
        	}
        }
        return message;
	}
	
	public static String getNodeValue(Document doc,String nameSpace,String nodeName){
		Map map = new HashMap();  
        map.put("design",nameSpace);  
        XPath x = doc.createXPath("//design:"+nodeName);  
        x.setNamespaceURIs(map); 
        Node node = x.selectSingleNode(doc);
        return node.getText();
	}
	public static String getNodeValue(Document doc,String nameSpace,String nodeName,String nameSpaceShort){
		Map map = new HashMap();  
        map.put(nameSpaceShort,nameSpace);  
        XPath x = doc.createXPath("//"+nameSpaceShort+":"+nodeName);  
        x.setNamespaceURIs(map); 
        Node node = x.selectSingleNode(doc);
        return node.getText();
	}
	public static String getNodeValue(String xml,String nameSpace,String nodeName){
		try {
			Document doc = DocumentHelper.parseText(xml);
			return getNodeValue(doc,nameSpace,nodeName);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getNodeStringInXMLString(String xml,String nodeName){
		int indexStart = xml.indexOf("<"+nodeName);
		int indexEnd = xml.indexOf("</"+nodeName+">");
		return xml.substring(indexStart, indexEnd+nodeName.length()+3);
	}
	public static void main(String[] args) {
		String str = "<a><b><c></c></b></a>";
		System.out.println(getNodeStringInXMLString(str,"b"));
	}
}
