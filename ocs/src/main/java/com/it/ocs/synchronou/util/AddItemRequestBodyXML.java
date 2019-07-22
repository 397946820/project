//package com.it.ocs.synchronou.util;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.IdentityHashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//import com.ebay.soap.eBLBaseComponents.CategoryType;
//import com.ebay.soap.eBLBaseComponents.ItemType;
//import com.ebay.soap.eBLBaseComponents.ListingDesignerType;
//import com.ebay.soap.eBLBaseComponents.PaymentDetailsType;
//import com.ebay.soap.eBLBaseComponents.PictureDetailsType;
//import com.ebay.soap.eBLBaseComponents.RefundOptionsCodeType;
//import com.ebay.soap.eBLBaseComponents.ReturnPolicyType;
//import com.ebay.soap.eBLBaseComponents.ShippingDetailsType;
//import com.ebay.soap.eBLBaseComponents.SiteCodeType;
//import com.ebay.soap.eBLBaseComponents.SiteDetailsType;
//import com.ebay.soap.eBLBaseComponents.StorefrontType;
//
//public class AddItemRequestBodyXML {
//	
//	 public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
//		
//		 ReturnPolicyType returnPolicyType = new ReturnPolicyType();
//		 ItemType item = new ItemType();
//		PictureDetailsType detailsType2 = new PictureDetailsType();
//		ListingDesignerType listingDesignerType = new ListingDesignerType();
//		CategoryType categoryType = new CategoryType();
//		StorefrontType cFontType = new StorefrontType();
//		PaymentDetailsType paymentDetailsType = new PaymentDetailsType();
//		objectToModel(item);
//		
//		 
//	}
//	 public static void objectToModel(Object object){
//		 /** 
//		   * 基本类型、包装类型、String类型 
//		   */  
//		  String[] types = {"java.lang.Integer",  
//		        "java.lang.Double",  
//		        "java.lang.Float",  
//		        "java.lang.Long",  
//		        "java.lang.Short",  
//		        "java.lang.Byte", 
//		        "java.lang.Boolean",  
//		        "java.lang.Character",  
//		        "java.lang.String",  
//		        "int","double","long","short","byte","boolean","char","float"};  
//		  Field[] fields = object.getClass().getDeclaredFields();
//		  
//		  for(Field field : fields){
//				// System.out.println("private " + field.getType().getName() + " "+ field.getName()+";");
//			  
//			  if(field.getType().getName().equals("com.ebay.soap.eBLBaseComponents.AmountType")){
//				  System.out.println("private " + field.getType().getName() + " "+ field.getName()+";"); 
//			  }
//			 /* for(String str : types){
//				  
//				  if(field.getType().getName().equals(str)){
//				
//					 System.out.println("private " + field.getType().getName() + " "+ field.getName()+";");
//					  //System.out.println("object------->"+field.getType() + " " + field.getName());
//				 }
//			  
//			   }*/
//			  }
//	 }
//	 public static String parameterToXML(Map<String, Object> map){
//		 StringBuffer xml = new StringBuffer();
//		 String start ="<";
//		 String end = ">";
//		 String endStart="</";
//		 Set<Map.Entry<String, Object>> allSet = map.entrySet();
//		 Iterator<Map.Entry<String, Object>> iter = allSet.iterator();
//		 
//		 while (iter.hasNext()) {
//			 
//			Map.Entry<String, Object> me = iter.next();
//			
//			xml.append(start+me.getKey()+end+me.getValue()+endStart+me.getKey()+end+"\n");
//			
//		 }
//
//		 return xml.toString();
//	}
//	
//	public  static Map<String, Object> VoToMap(Object vo){
//		 Map<String, Object> map = new IdentityHashMap<>();
//		 Class iClass = vo.getClass();
//		 
//		 Field[] fields = iClass.getDeclaredFields();
//		 
//		 for(int i=0; i < fields.length;i++){
//			 
//			 Field field = fields[i];
//			 
//			 field.setAccessible(true);
//			 Object value;
//			 String name ;
//			 try {
//				 
//				value = field.get(vo);
//				
//				if(value!=null){
//					
//				  if (field.getType().equals("interface java.util.List")) {
//					  name = field.getName();
//					  Type type = field.getGenericType();
//					  
//					  if(type instanceof ParameterizedType){
//						  name = startToUpperCase(name);
//						  System.out.println(name);
//						  
//					  }
//					  
//					  
//					}{
//						
//					 name = field.getName();
//					 
//					 name = startToUpperCase(name);
//					 
//					 map.put(new String(name), value);
//					}
//				}
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		 }
//		return map;
//	}
//	
//	public static String startToUpperCase(String string){
//		
//		if(Character.isUpperCase(string.charAt(0))){
//			 
//		 }else{
//			 
//			 String startString = string.substring(0, 1).toUpperCase();
//			 
//			 string = startString+string.substring(1, string.length());
//			 
//		 }
//		return string;
//	}
//}
