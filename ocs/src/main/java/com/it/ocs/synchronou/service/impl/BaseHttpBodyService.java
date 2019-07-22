package com.it.ocs.synchronou.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.it.ocs.synchronou.service.IBaseHttpBody;

public class BaseHttpBodyService implements IBaseHttpBody {

	@Override
	public String startToUpperCase(String string) {
		// TODO Auto-generated method stub

		if(Character.isUpperCase(string.charAt(0))){
			 
		 }else{
			 
			 String startString = string.substring(0, 1).toUpperCase();
			 
			 string = startString+string.substring(1, string.length());
			 
		 }
		return string;
	}

	@Override
	public Map<String, Object> VoToMap(Object vo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new IdentityHashMap<>();
		 Class iClass = vo.getClass();
		 
		 Field[] fields = iClass.getDeclaredFields();
		 
		 for(int i=0; i < fields.length;i++){
			 
			 Field field = fields[i];
			 
			 field.setAccessible(true);
			 Object value;
			 String name ;
			 try {
				 
				value = field.get(vo);
				
				if(value!=null){
					
				  /*if (field.getType().equals("interface java.util.List")) {
					  name = field.getName();
					  Type type = field.getGenericType();
					  
					  if(type instanceof ParameterizedType){
						  name = startToUpperCase(name);
						  System.out.println(name);
						  
					  }
					  
					  
					}else{*/
						
					 name = field.getName();
					 
					 name = startToUpperCase(name);
					 
					 map.put(new String(name), value);
					//}
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 }
		return map;
	}

	@Override
	public String parameterToXML(Map<String, Object> map,String root) {
		// TODO Auto-generated method stub
		 StringBuffer xml = new StringBuffer();
		 String start ="<";
		 String end = ">";
		 String endStart="</";
		 Set<Map.Entry<String, Object>> allSet = map.entrySet();
		 Iterator<Map.Entry<String, Object>> iter = allSet.iterator();
		 if(root!=null){
			 xml.append(start+root+end);
		 }
		 while (iter.hasNext()) {
			 
			Map.Entry<String, Object> me = iter.next();
			
			xml.append(start+me.getKey()+end+me.getValue()+endStart+me.getKey()+end+"\n");
			
		 }
		 if(root!=null){
			 xml.append(endStart+root+end);
		 }
		 return xml.toString();
	}

	@Override
	public String resultXML(List<String> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String combination(String root, List<String> list) {
		// TODO Auto-generated method stub
		StringBuffer xml = new StringBuffer();
		String start ="<";
		String end = ">";
		String endStart="</";
		if(root!=null){
			 xml.append(start+root+end);
		 }
		for (String string : list) {
			xml.append(string);
			
		}
		if(root!=null){
			 xml.append(endStart+root+end);
		 }
		return xml.toString();
	}
	
	public String listToXml(String root,List<Map<String,Object>> listMap){
		StringBuffer xml = new StringBuffer();
		String start ="<";
		String end = ">";
		String endStart="</";
		if(root!=null){
			 xml.append(start+root+end);
		 }
		for (Map<String, Object> map : listMap) {
			
			 Set<Map.Entry<String, Object>> allSet = map.entrySet();
			 Iterator<Map.Entry<String, Object>> iter = allSet.iterator();
			 while (iter.hasNext()) {
				 
					Map.Entry<String, Object> me = iter.next();
					
					xml.append(start+me.getKey()+end+me.getValue()+endStart+me.getKey()+end+"\n");
					
				 }
		}
		if(root!=null){
			 xml.append(endStart+root+end);
		 }
		return xml.toString();
	}
	
	public String listRootToXml(String root,List<Map<String,Object>> listMap){
		StringBuffer xml = new StringBuffer();
		String start ="<";
		String end = ">";
		String endStart="</";
		
		for (Map<String, Object> map : listMap) {
			 if(root!=null){
				 xml.append(start+root+end);
			 }
			 Set<Map.Entry<String, Object>> allSet = map.entrySet();
			 Iterator<Map.Entry<String, Object>> iter = allSet.iterator();
			 while (iter.hasNext()) {
					
					Map.Entry<String, Object> me = iter.next();
					
					xml.append(start+me.getKey()+end+me.getValue()+endStart+me.getKey()+end+"\n");
					
				 }
			 if(root!=null){
				 xml.append(endStart+root+end);
			}
			 
		}
		
		return xml.toString();
	}
}
