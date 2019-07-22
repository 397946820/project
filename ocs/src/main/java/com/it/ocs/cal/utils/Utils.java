package com.it.ocs.cal.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.vo.PermissionVO;

public class Utils {

	// 判断一个字符串是否有大写字母 ，然后截取并拼接,排序用
	public static String changeString(String string) {
		char[] charArray = string.toCharArray();
		int flag = -1;
		for (int i = 0; i < charArray.length; i++) {
			if (!Character.isLowerCase(charArray[i])) {
				flag = i;
				break;
			}
		}

		if (flag != -1) {
			return string.substring(0, flag) + "_" + string.substring(flag).toLowerCase();
		}

		return string;

	}

	// 日期转时间
	public static String dateToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	// 文件名
	public static String getFileName() {
		Calendar date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sdf.format(date.getTime());
		return str;
	}

	public static List<String> getCountrys(PermissionVO permission) {
		List<String> list = new ArrayList<>();
		if (permission != null) {
			List<PermissionVO> children = permission.getChildren();
			if(!CollectionUtil.isNullOrEmpty(children)) {
				for (PermissionVO permissionVO : children) {
					list.add(getName(permissionVO.getName()));
				}
			}
		} else {
			list.add("US");
			list.add("JP");
			list.add("DE");
			list.add("FR");
			list.add("GB");
			list.add("ES");
			list.add("IT");
			list.add("CA");
			list.add("AU");
		}

		return list;
	}

	private static String getName(String name) {
		if(name.equals("United States")) {
			return "US";
		} else if(name.equals("Japan")) {
			return "JP";
		} else if(name.equals("German")) {
			return "DE";
		} else if(name.equals("France")) {
			return "FR";
		} else if(name.equals("United Kingdom")) {
			return "GB";
		} else if(name.equals("Spain")) {
			return "ES";
		} else if(name.equals("Italy")) {
			return "IT";
		} else if(name.equals("Canada")) {
			return "CA";
		} else if(name.equals("Australia")) {
			return "AU";
		}
		return null;
	}
	
	public static String getPercent(Double a,Double b){
		if(b == 0){
			return "0";
		}
		double percent = a / b;
	    NumberFormat nt = NumberFormat.getPercentInstance();
	    nt.setMinimumFractionDigits(2);
	    return nt.format(percent);
	}
	
}
