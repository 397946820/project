package com.it.ocs.synchronou.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class UTCTimeUtils {
	private final static String US = "US";
	private final static String UK = "UK";
	private final static String DE = "DE";
	
	private final static Logger log = Logger.getLogger(UTCTimeUtils.class);
	/**
	 * 获取当前UTC时间增加或者减少一小时时间的UTC时间
	 * @param offset
	 * @return
	 */
    public static String getUTCTimeStr(int hour) {
        Calendar calender = getUTCCalendar();
        calender.add(Calendar.HOUR, hour);
    	return getTimeString(calender);
       
    }

	public static Calendar getUTCCalendar() {
		// 1、取得本地时间：
        Calendar calender = Calendar.getInstance() ;
        // 2、取得时间偏移量：
        int zoneOffset = calender.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = calender.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        calender.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return calender;
	}
    
    /**
	 * 获取当前UTC时间增加或者减少一小时时间的UTC时间
	 * @param offset
	 * @return
	 */
    public static String getUTCTimeStrByMINUTE(int MINUTE) {
        Calendar calender = getUTCCalendar();
        calender.add(Calendar.MINUTE, MINUTE);
    	return getTimeString(calender);
       
    }

	public static String getTimeString(Calendar calender) {
        return formartTime(calender,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//format.format(calender.getTime()) ;
	}

	public static int getTimeFixByCountry(String platform) {
		if(UTCTimeUtils.US.equals(platform)){
			return -7;
		}
		if(UTCTimeUtils.UK.equals(platform)){
			return -1;
		}
		if(UTCTimeUtils.DE.equals(platform)){
			return -2;
		}
		return 0;
	}
	
	public static String getSiteTimeString(String site,int hour){
		 Calendar calender = getUTCCalendar();
		 calender.add(Calendar.HOUR, getTimeFixByCountry(site)+hour);
		 return formartTime(calender,"yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formartTime(Calendar calender,String rule){
		SimpleDateFormat format = new SimpleDateFormat(rule);
        return format.format(calender.getTime()) ;
	}
	
	public static void main(String[] args) {
		System.out.println(getSiteTimeString(UTCTimeUtils.US,0));
	}

	public static String addHour(String startTime, int hour) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {
			Date date = format.parse(startTime);
			long cur = System.currentTimeMillis();
			if(date.getTime() > cur){
				return null;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR, hour);
			if(calendar.getTime().getTime() > cur){
				date.setTime(cur);
				calendar.setTime(date);
			}
			return getTimeString(calendar);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Map<String,String>> splitTimeForOneHourList(String fromTime,int hour){
		List<Map<String,String>> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {
			Date fromDate = sdf.parse(fromTime);
			Calendar curCalendar = Calendar.getInstance();
			curCalendar.add(Calendar.HOUR, -8);
			curCalendar.add(Calendar.MINUTE, -2);
			Date toDate = curCalendar.getTime();
			long l = toDate.getTime() - fromDate.getTime();
			if(l > hour*60*60*1000){
				int count = (int) (l/(hour*60*60*1000));
				long y = l%(hour*60*60*1000);
				if(y>0){
					count = count + 1;
				}
				curCalendar.setTime(fromDate);
				String start = sdfUTC.format(fromDate);
				String end = "";
				for(int i=0;i<count;i++){
					curCalendar.add(Calendar.HOUR, hour);
					if(i == count-1){
						end = sdfUTC.format(toDate);
					}else{
						end = sdfUTC.format(curCalendar.getTime());
					}
					
					Map<String,String> map = new HashMap<>();
					map.put("fromTime", start);
					map.put("toTime", end);
					log.info(start + "----"+end);
					start = end;
					list.add(map);
				}
			}else{
				Map<String,String> map = new HashMap<>();
				map.put("fromTime", sdfUTC.format(fromDate));
				map.put("toTime", sdfUTC.format(toDate));
				log.info(sdfUTC.format(fromDate) + "----"+sdfUTC.format(toDate));
				list.add(map);
			}
		} catch (ParseException e) {
			log.info("解析较长时间分段失败:"+fromTime+"--"+hour);
		}
		return list;
	}
}
