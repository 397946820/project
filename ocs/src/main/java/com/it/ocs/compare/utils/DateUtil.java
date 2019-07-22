package com.it.ocs.compare.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
	/**
	 * 
	 * @param value
	 * @param site
	 * @return
	 * 通过站点，把站点的时间格式转为通用是时间格式
	 */
	public static String getSelfDateValue(String value,String site) {
		String reValue = value;
		String str[] = null;
		String zone = null;
		switch(site){
			case "IT":
				//11/nov/2017 23.06.29 GMT+00.00
				str = value.split(" ");
				zone = str[str.length-1];
				value = value.replace(zone, zone.replace(".", ":"));
				reValue = formatSelfDateString("dd/MMM/yyyy HH.mm.ss zzz",Locale.ITALY,value,zone.replace(".", ":"));
				break;
			
			case "US":
				//Nov 13, 2017 12:00:08 AM PST
				if(value.indexOf("PST")>0){
					str = value.split(" ");
					zone = str[str.length-1];
					reValue = formatSelfDateString("MMM dd, yyyy hh:mm:ss aaa zzz",Locale.US,value,zone);
				}else{
					String reValue1 = formatSelfDateString("MMM dd, yyyy hh:mm:ss aaa zzz",Locale.US,value,"UTC");
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Calendar ca = Calendar.getInstance();
					try {
						ca.setTime(sdf1.parse(reValue1));
						ca.add(Calendar.HOUR, -7);
						reValue = sdf1.format(ca.getTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(null == reValue || "".equals(reValue)){
						reValue = reValue1;
					}
				}
				break;
			
			case "DE":
				//11.11.2017 23:00:37 GMT+00:00
				str = value.split(" ");
				zone = str[str.length-1];
				reValue = formatSelfDateString("dd.MM.yyyy HH:mm:ss zzz",Locale.GERMAN,value,zone);
				break;
				
			case "CA":
				//2017-11-10 12:25:54 AM PST
				str = value.split(" ");
				zone = str[str.length-1];
				reValue = formatSelfDateString("yyyy-MM-dd hh:mm:ss aaa zzz",Locale.CANADA,value,zone);
				break;
				
			case "UK":
				//12 Nov 2017 00:01:31 GMT+00:00
				str = value.split(" ");
				zone = str[str.length-1];
				reValue = formatSelfDateString("dd MMM yyyy HH:mm:ss zzz",Locale.UK,value,zone);
				break;
				
			case "FR":
				//11 nov. 2017 23:13:26 UTC+00:00
				str = value.split(" ");
				zone = str[str.length-1];
				reValue = formatSelfDateString("dd MMM yyyy HH:mm:ss zzz",Locale.FRANCE,value,zone);
				break;
				
			case "ES":
				//12/11/2017 13:35:53 GMT+00:00
				str = value.split(" ");
				zone = str[str.length-1];
				reValue = formatSelfDateString("dd/MM/yyyy HH:mm:ss zzz",Locale.GERMAN,value,zone);
				break;
				
			case "JP":
				//2017/11/11 00:09:37JST
				reValue = formatSelfDateString("yyyy/MM/dd HH:mm:sszzz",Locale.JAPAN,value,"JST");
				break;
			default :
				reValue = value;
				break;
		}
		return reValue;
	}
	public static String formatSelfDateString(String pattern,Locale locale,String value,String zone){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern,locale);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf1.setTimeZone(TimeZone.getTimeZone(zone));
		Date date =null;
		try {
			date = sdf.parse(value);
			return sdf1.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
}
