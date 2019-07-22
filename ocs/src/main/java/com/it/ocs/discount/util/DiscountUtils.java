package com.it.ocs.discount.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DiscountUtils {

	public static String getTime(Date timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.format(timestamp);
	}
}
