package com.it.ocs.synchronou.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

	public static Boolean isEquals(Object o1,Object o2){
		if(isNull(o1)&&isNull(o2)){
			return true;
		}else if(!isNull(o1)&&!isNull(o2)){
			return o1.toString().equals(o2.toString());
		}else{
			return false;
		}
	}
	
	public static String isEmptyToNull(String param){
		if(param==null){
			return null;
		}else if(param.equals("")){
		return null;
		}else{
			return param;
		}
	}
	public static Double getDouble(String param){
		if(param==null){
			return null;
		}else if(param.equals("")){
			return null;
		}else{
			return Double.parseDouble(param);
		}
	}
	public static Boolean isNullOrEmpty(Object param){
		if(param==null || "".equals(String.valueOf(param).trim())){
			return true;
		}else{
			return false;
		}
	}
	public static Boolean getBoolean(String param){
		
		if(param!=null){
			return Boolean.parseBoolean(param);
		}else{
			return null;
		}
	
	}
	public static Boolean isNull(Object param){
		if(param==null){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean isEquals(Object o1,Object... o2){
		for (Object object : o2) {
			if(o1.equals(object.toString())){
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkSpecialCharacter(String str) {
		String regEx = "^[0-9a-zA-Z]*$";
	    // 编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	
}
