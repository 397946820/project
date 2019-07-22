package com.it.ocs.synchronou.util;

import java.util.List;

import org.dom4j.Element;
/**
 * 
 * @author yangguanbao
 * 描述：xml与Json互相转换的工具
 */
public class XmlAndJsonUtil {

/**
 * 
 * @param elements
 * @param sub（sub节点组成的值）
 * @return String
 * 描述：一个Element集合返回集合组成的Json字符串
 */
public static String elementToJsonString(List<Element> elements,String sub){
		
		String  results = new String();
		
		String name = elements.get(0).getName();
		
		StringBuffer value = new StringBuffer();
		for (Element element : elements) {
			
		
		List<Element> subElement = element.elements(sub);
		for (Element element2 : subElement) {
				
				value.append(element2.getText()+",");
			}
		}
		value.deleteCharAt(value.length()-1);
		results =  "{\""+name+"\":\""+value+"\"}" ;
		return results;
	}
/**
 * 
 * @param element
 * @param parent
 * @return String
 * 描述：获取element的子节点parent的所有子节点组成的字符串。
 */
 public static String elementSubToJsonString(Element element,String parent){
	   String results = new String();
	   StringBuffer value = new StringBuffer();
	   Element element3 = element.element(parent);
		List<Element> list = element3.elements();
		for (Element element4 : list) {
			value.append("\""+element4.getName()+"\":\""+element4.getText()+"\",");
		}
		results ="{" +value.deleteCharAt(value.length()-1)+"}";
	   return results;
   }
 
 public static String elementsToJsonString(List<Element> elements, List<String> tags ){
	 StringBuffer jsonString = new StringBuffer();
	 jsonString.append("[");
	 
	 for (Element element : elements) {
		 jsonString.append("{");
		 for (String name : tags) {
			jsonString.append(name+":\"");	
			 
			jsonString.append(element.element(name).getTextTrim()+"\",");
			 
		}
		 jsonString.deleteCharAt(jsonString.length()-1);
		jsonString.append("},");
	 }
	 jsonString.deleteCharAt(jsonString.length()-1);
	 jsonString.append("]");
	 
	 return jsonString.toString();
 }
}
