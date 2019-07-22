package com.it.ocs.salesStatistics.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

public class TimeTools {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 获取当前时间
	public static Date getStoptime() throws ParseException {
		Date date = new Date();
		String string = format.format(date);
		string = string + " 00:00:00";
		return sdf.parse(string);
	}

	public static Date getEndTime() throws Exception {
		return format.parse(format.format(new Date()));
	}

	// 月份加减
	public static Date getChangeMonth(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, i);
		return calendar.getTime();

	}

	// 天数加减
	public static Date getChangeDay(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, i);
		return calendar.getTime();
	}

	// 前台传的字符串时间转化成data
	public static Date getTime(String starttime) throws ParseException {
		starttime = starttime + " 00:00:00";
		return sdf.parse(starttime);
	}

	// 前台传的字符串时间转化成data
	public static Date getTime2(String time) throws ParseException {
		if (StringUtils.isNotBlank(time)) {
			return sdf.parse(time);
		}
		return null;
	}

	public static Date strToDate(String time) throws ParseException {
		return format.parse(time);
	}

	// 时间差
	@SuppressWarnings("unused")
	public static int getMistiming(String starTime, String endTime) throws Exception {
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date star = format.parse(starTime);
		Date end = format.parse(endTime);
		calendar.setTime(star);
		long time1 = calendar.getTimeInMillis();
		calendar.setTime(end);
		long time2 = calendar.getTimeInMillis();
		long between_days = (time1 - time2) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	// 前推一年
	@SuppressWarnings({ "unused", "static-access" })
	public static Date getChangeYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(date);
		calendar.add(calendar.YEAR, -1);
		return calendar.getTime();
	}

	// 时间类型转化成String
	public static String dateToString(Date createdat) {
		return format.format(createdat);
	}

	// 时间类型转化成String
	public static String dateToString2(Date createdat) {
		return sdf.format(createdat);
	}

	/**
	 * 时区 时间转换方法:将当前时间（可能为其他时区）转化成目标时区对应的时间
	 * 
	 * @param sourceTime
	 *            时间格式必须为：yyyy-MM-dd HH:mm:ss
	 * @param sourceId
	 *            入参的时间的时区id
	 * @param targetId
	 *            要转换成目标时区id（一般是是零时区：取值UTC）
	 * @return string 转化时区后的时间
	 */
	public static String timeConvert(String sourceTime, String sourceId, String targetId) {
		// 校验入参是否合法
		if (null == sourceId || "".equals(sourceId) || null == targetId || "".equals(targetId) || null == sourceTime
				|| "".equals(sourceTime)) {
			return "";
		}
		// 校验 时间格式必须为：yyyy-MM-dd HH:mm:ss
		String reg = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$";
		if (!sourceTime.matches(reg)) {
			return "";
		}

		try {
			// 时间格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 根据入参原时区id，获取对应的timezone对象
			TimeZone sourceTimeZone = TimeZone.getTimeZone(sourceId);
			// 设置SimpleDateFormat时区为原时区（否则是本地默认时区），目的:用来将字符串sourceTime转化成原时区对应的date对象
			df.setTimeZone(sourceTimeZone);
			// 将字符串sourceTime转化成原时区对应的date对象
			Date sourceDate = df.parse(sourceTime);

			// 开始转化时区：根据目标时区id设置目标TimeZone
			TimeZone targetTimeZone = TimeZone.getTimeZone(targetId);
			// 设置SimpleDateFormat时区为目标时区（否则是本地默认时区），目的:用来将字符串sourceTime转化成目标时区对应的date对象
			df.setTimeZone(targetTimeZone);
			// 得到目标时间字符串
			String targetTime = df.format(sourceDate);
			return targetTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";

	}

	// 时间比较大小
	public static boolean compare1(Date startDate, Date endDate, Date invoke) {
		Boolean boolean1 = false;
		if (invoke.getTime() >= startDate.getTime() && invoke.getTime() < endDate.getTime()) {
			boolean1 = true;
		}
		return boolean1;
	}

	// 时间比较大小
	public static boolean compare2(String startDate, String endDate, String invoke) {
		Boolean boolean1 = false;
		if (invoke.compareTo(startDate) > -1 && invoke.compareTo(endDate) < 1) {
			boolean1 = true;
		}
		return boolean1;
	}

	public static boolean compare3(String startDate, String endDate, String invoke) {
		Boolean boolean1 = false;
		String string = "";
		if (StringUtils.isNotBlank(startDate)) {
			string = startDate;
		} else {
			string = endDate;
		}
		if (invoke.compareTo(string) == 0) {
			boolean1 = true;
		}
		return boolean1;
	}

	/**
	 * 判断两个字符串的时间是否是一个月的第一天和最后一天
	 * 
	 * @param starttime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @return
	 * @throws ParseException
	 */
	public static boolean judge(String starttime, String endtime) throws ParseException {
		Boolean flag = false;
		Date start = format.parse(starttime);
		Date end = format.parse(endtime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(end);
		int a = calendar.get(calendar.YEAR);
		calendar.setTime(start);
		int b = calendar.get(calendar.YEAR);
		if (a == b) {
			a = calendar.get(Calendar.MONTH);
			calendar.setTime(end);
			b = calendar.get(Calendar.MONTH);
			if (a == b) {
				calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
				if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
					calendar.setTime(start);
					int actualMinimum = calendar.get(Calendar.DAY_OF_MONTH);
					if (actualMinimum == 1) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(timeConvert(sdf.format(new Date()), "Asia/Shanghai", "UTC"));
		System.out.println(judge("2017-03-01", "2017-03-31"));
	}

	// 时间转化
	public static Date getTimeByStation(Date date, String key) throws ParseException {
		String sourceId = Tools.getSoreceId(key);
		if(StringUtils.isBlank(sourceId)) {
			return date;
		}
		String timeConvert = timeConvert(dateToString2(date), sourceId, "UTC");
		Date result = getTime2(timeConvert);
		return result;
	}

	/**
	 * 
	 * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
	 * 
	 * @param date
	 * @return
	 */
	public static int getSeason(String date) {
		try {
			int season = 0;
			SimpleDateFormat format_ = new SimpleDateFormat("yyyy-MM");
			Calendar c = Calendar.getInstance();
			c.setTime(format_.parse(date));
			int month = c.get(Calendar.MONTH);
			switch (month) {
			case Calendar.JANUARY:
			case Calendar.FEBRUARY:
			case Calendar.MARCH:
				season = 1;
				break;
			case Calendar.APRIL:
			case Calendar.MAY:
			case Calendar.JUNE:
				season = 2;
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.SEPTEMBER:
				season = 3;
				break;
			case Calendar.OCTOBER:
			case Calendar.NOVEMBER:
			case Calendar.DECEMBER:
				season = 4;
				break;
			default:
				break;
			}
			return season;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	public static String getLastDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month-1);     
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
       return  new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());  
    }
	public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
       return   new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
    }
}
