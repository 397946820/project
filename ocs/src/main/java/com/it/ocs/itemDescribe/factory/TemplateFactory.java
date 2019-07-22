package com.it.ocs.itemDescribe.factory;

import java.util.HashMap;

import com.it.ocs.itemDescribe.template.TemplateA;

public class TemplateFactory {
	
	private  static HashMap<String,Object> templateMap = new HashMap<String,Object>();
	
	/**
	 * 模板种类 
	 */
	public  static void InitializeMap(){
		
		templateMap.put("templateA", new TemplateA());
		
	}
	
	/**
	 * 
	 * @param templateName
	 * @return 
	 * 描述：通过模板名字获取模板
	 */
	public static <T> T createTemplate(String templateName){
			//Object templateA =  templateMap.get(templateName);
			T template = null;
			template = (T) templateMap.get(templateName);
		    return template;
	}
	
	
}
