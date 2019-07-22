package com.it.ocs.synchronou.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import com.it.ocs.salesStatistics.utils.TimeTools;

public class ConversionDateUtil {

	public static Calendar getCalendarByDateAndSiteId(String date,Long siteId){
		TimeZone tz = searchZoneBySiteID(siteId);
        TimeZone.setDefault(tz);
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(tz);
        Date time = charToDate(date, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        calendar.setTime(time);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		return calendar;
	}
	public static String getForToFor(String dateStr,String forStart,String forEnd){
		Date date = charToDate(dateStr, forStart);
		return dateToCharFormat(date, forEnd);
	}
	public static Date SiteDateConversionUTC(String date,Long siteId){
		 TimeZone tz = searchZoneBySiteID(siteId);
         TimeZone.setDefault(tz);
         Calendar calendar = new GregorianCalendar();
         calendar.setTimeZone(tz);
         Date time = charToDate(date, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
         calendar.setTime(time);
         calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
         Date result = calendar.getTime();
         result.setYear(calendar.get(Calendar.YEAR)-1900);
         result.setMonth(calendar.get(Calendar.MONTH));
         result.setDate(calendar.get(Calendar.DATE));
         result.setHours(calendar.get(Calendar.HOUR_OF_DAY));
         result.setMinutes(calendar.get(Calendar.MINUTE));
         result.setSeconds(calendar.get(Calendar.SECOND));
		return result;
	}
	public static TimeZone searchZoneBySiteID(Long siteId){
		HashMap<Long, TimeZone> map = new HashMap<>();
		map.put(0L, TimeZone.getTimeZone("America/Los_Angeles"));
		map.put(2L,null);
		map.put(3L, TimeZone.getTimeZone("Europe/London"));
		map.put(77L, TimeZone.getTimeZone("Europe/Berlin"));
		map.put(15L, null);
		map.put(71L, TimeZone.getTimeZone("Europe/Paris"));
		map.put(100L,null);
		map.put(101L, TimeZone.getTimeZone("Europe/Rome"));
		map.put(146L, null);
		map.put(186L, null);
		map.put(203L, null);
		map.put(201L, null);
		map.put(216L,null);
		map.put(207L, null);
		map.put(211L, null);
		map.put(210L, null);
		map.put(212L, null);
		map.put(123L, null);
		map.put(23L, null);
		map.put(16L, null);
		map.put(193L, null);
		map.put(205L, null);
		if(map.get(siteId)!=null){
			
			return map.get(siteId);
					
		}else{
			return null;
		}
	}

	public static String dateToCharFormat(Date param,String format){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String result = simpleDateFormat.format(param);
		return result;
	}
	
	public static Date getDateByCZ(Calendar calendar, String zone){
		String sourceEnd = dateToCharFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
		String endString = TimeTools.timeConvert(sourceEnd, calendar.getTimeZone().getID(),zone);
		return charToDate(endString, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date charToDate(String param,String format){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date result = null;
		try {
			 result = simpleDateFormat.parse(param);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Calendar getCBySTF(String date,String sZ,String sF){
		Calendar result = Calendar.getInstance();
		result.setTimeZone(TimeZone.getTimeZone(sZ));
		Date time = charToDate(date, sF);
		result.setTime(time);
		return result;
	}
}
