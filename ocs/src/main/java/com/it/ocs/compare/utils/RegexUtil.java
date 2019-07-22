package com.it.ocs.compare.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.it.ocs.synchronou.util.ValidationUtil;

public class RegexUtil {

	public static Boolean isStringByRegex(String str,String regex){
		if(!ValidationUtil.isNull(str)){
			Pattern pt = Pattern.compile(regex);
			Matcher mat =  pt.matcher(str);
			return mat.find();
		}
		else{
			return false;
		}
		
	}
}
