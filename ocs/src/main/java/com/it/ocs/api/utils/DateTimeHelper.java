package com.it.ocs.api.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateTimeHelper {
	private static final String[] MONTHS_STRING = { "Jan", "Feb", "Mar", "Apr",
			"May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	private static final String UTC_0800 = "UTC 0800 ";

	/**
	 * 指定一个Date对象date，返回该日期“YYYY-MM-DD”的字符串
	 * 
	 * @deprecated 请使用<code>DateTimeHelper.formatDate(Date)</code>代替
	 * 
	 * @param date
	 *            指定的Date类型对象
	 * @return String 要返回的“YYYY-MM-DD”的字符串
	 */
	static public String getDateStr(Date date) {
		if (null == date || "".equals(date.toString())) {
			return "";
		}
		return formatDate(date);
	}

	/**
	 * 取当前日期的短格式, 格式为<tt>yyMMdd</tt>
	 * 
	 * @deprecated 请使用<code>DateTimeHelper.getCurrentDateShort()</code>代替
	 * 
	 * @return String 格式为<tt>yyMMdd</tt>的字符串
	 * @author zhengshumin
	 */
	static public String getSortDate() {
		return formatDate(new Date(), PATTERN_DATESHORT);
	}

	/**
	 * 取当前日期的短格式, 格式为<tt>yyMMdd</tt>
	 * 
	 * @deprecated 请使用<code>DateTimeHelper.getCurrentDateShort()</code>代替
	 * 
	 * @return String 格式为<tt>yyMMdd</tt>的字符串
	 */
	static public String getCurrentDateShort() {
		return formatDate(new Date(), PATTERN_DATESHORT);
	}

	/**
	 * 指定一个Date对象date，返回该日期YYYY-MM-DD HH:MM:SS的字符串
	 * 
	 * 请使用
	 * <code>DateTimeHelper.formatDate(Date, DateTimeHelper.PATTERN_DATETIME)</code>
	 * 代替
	 * 
	 * @param date
	 *            指定Date类型对象
	 * @return 返回该日期YYYY-MM-DD HH:MM:SS的字符串
	 */
	static public String getDateTimeStr(Date date) {
		return formatDate(date, DateTimeHelper.PATTERN_DATETIME);
	}
	
	/**
	 * 返回该日期YYYY-MM-DD HH:MM的字符串
	 * 
	 * @param       @param date
	 * @param       @return
	 * @return      String
	 * @throws
	 * 日期 2016-03-02
	 */
	public static String getDateTimeStrNoSS(Date date){
		return formatDate(date, DateTimeHelper.PATTERN_SHORT_DATE);
	}

	/**
	 * 指定一个Date对象date，返回该日期YYYYMMDDHHMMSS的字符串
	 * 
	 * @deprecated 请使用<code>DateTimeHelper.formatDate(Date, 
	 * DateTimeHelper.PATTERN_DATETIME_COMPACT)</code>代替
	 * 
	 * @param datetime
	 *            指定Date类型对象
	 * @return 返回该日期YYYYMMDDHHMMSS的字符串
	 */
	static public String FormatDateTime(Date datetime) {
		return DateTimeHelper.formatDate(datetime,
				DateTimeHelper.PATTERN_DATETIME_COMPACT);
	}

	/**
	 * 
	 * 返回中文日期类型
	 * 
	 */
	static public String ChineseDate(Date date) {
		DateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		String datetime = df2.format(date);
		return datetime;
	}
	
	/**
	 * <b> 将默认格式(<code>yyyy-MM-dd</code>)的日期字符串转换成<code>java.util.Date</code>类型
	 * 
	 * @deprecated 该方法已过时,请使用<code>DateTimeHelper.parseDate(String)</code>代替
	 */
	static public Date FormatStrToDate(String date) {
		return parseDate(date);
	}

	/**
	 * 取得当前时间的字符串,格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @deprecated 该方法已过时,请使用<tt>DateTimeHelper.currentDateTime()</tt>代替
	 */
	static public String getCurrentDateTimeStr() {
		// String StartTime = getDateTimeStr(new
		// Date(System.currentTimeMillis()));
		// return StartTime;
		return currentDateTime();
	}

	/**
	 * 取得当前时间的字符串,格式为yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static String currentDateTime() {
		return formatDate(new java.util.Date(), DateTimeHelper.PATTERN_DATETIME);
	}

	/**
	 * 取得当前时间的字符串,格式为yyyyMM
	 */
	public static String currentYearMonth() {
		return formatDate(new java.util.Date(), DateTimeHelper.PATTERN_YEARMONTH);
	}

	/**
	 * 取得当前时间的字符串,格式为"yyyy-MM-dd"
	 */
	public static String currentDay() {
		return formatDate(new Date(), DateTimeHelper.PATTERN_DEFAULT);
	}
	
	

	/**
	 * 返回两个日期（DATE类型）相差的天数
	 * 
	 * @param dateStart
	 *            开始日期
	 * @param dateEnd
	 *            截止日期
	 * @return 截止日期与开始日期相差的天数(用截止日期减去开始日期)
	 */
	public static Integer CompareTo(Date dateStart, Date dateEnd) {
		if (dateStart == null || dateEnd == null) {
			return null;
		}
		return new Long((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 / 60 / 24).intValue();
	}
	
	/**
	 * 两个日期相差的小时
	 * 
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 * @return Integer
	 */
	public static Integer CompareToHour(Date dateStart, Date dateEnd){
		if(dateStart == null || dateEnd == null){
			return null;
		}
		return new Long((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 / 60).intValue();
	}
	
	/**
	 * 返回两个日期（DATE类型）相差的分数
	 * 
	 * @param dateStart
	 *            开始日期
	 * @param dateEnd
	 *            截止日期
	 * @return 截止日期与开始日期相差的天数(用截止日期减去开始日期)
	 */
	public static Integer CompareToMin(Date dateStart, Date dateEnd) {
		if (dateStart == null || dateEnd == null) {
			return null;
		}
		return new Long((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 ).intValue();
	}
	
	/**
	 * 返回两个日期（DATE类型）相差的秒
	 * 
	 * @param dateStart
	 *            开始日期
	 * @param dateEnd
	 *            截止日期
	 * @return 截止日期与开始日期相差的天数(用截止日期减去开始日期)
	 */
	public static Long CompareToSec(Date dateStart, Date dateEnd) {
		if (dateStart == null || dateEnd == null) {
			return null;
		}
		return new Long((dateEnd.getTime() - dateStart.getTime()) / 1000);
	}

	/**
	 * 根据给出的日期，计算n天后的日期,n可以为负整数，表示n天前
	 * 
	 * @param givenDate
	 *            给定日期
	 * @param n
	 *            偏移量
	 * @return n天后的日期
	 */
	public static Date addNday(Date givenDate, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(givenDate);
		c.add(Calendar.DATE, n);

		return c.getTime();
	}
	/**
	 * 根据给出的日期，计算n天后的日期,n可以为负整数，表示n天前
	 *
	 * @param givenDate
	 *            给定日期
	 * @param n
	 *            偏移量
	 * @return n天后的日期
	 */
	public static Date addMonth(Date givenDate, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(givenDate);
		c.add(Calendar.MONTH, n);

		return c.getTime();
	}

	/**
	 * 计算日期
	 * 
	 * @param givenDate
	 *            需要计算的日期
	 * @param amount
	 *            数量(LONG类型) 可以为负数
	 * @param unit
	 *            单位 (TimeUnit.YEAR/TimeUnit.MONTH/TimeUnit.DAY)
	 * @see <tt>DateTimeHelper.accountDate(Date givenDate, int amount, String unit)</tt>
	 * @author LiuYuan
	 * @return 新日期
	 */
	public static Date accountDate(Date givenDate, long amount, String unit) {

		int temp = Integer.valueOf(String.valueOf(amount));
		return accountDate(givenDate, temp, unit);

	}




	/**
	 * 统计日期当年有多少天
	 * 
	 * 日期 2016-03-02
	 */
	public static int getLenientDays(Date date) throws Exception {
		GregorianCalendar c = (GregorianCalendar) GregorianCalendar
				.getInstance();
		c.setTime(date);

		return c.isLeapYear(c.get(Calendar.YEAR)) ? 366 : 365;
	}

	/**
	 * 计算日期月份内有多少天
	 * 
	 * @throws Exception
	 * 日期 2016-03-02
	 */
	public static int getDateDays(Date date) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	
	/**
	 * 计算两个日期之间相差多少月 精确到日期
	 * 
	 * （截止日期－开始日期 的最小月数) + (剩余天数/开始日期所属月的总天数)；
	 * 
	 * @throws Exception
	 * 日期 2016-03-02
	 */
	public static double getDiscrepantMonth(Date date1, Date date2)
			throws Exception {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DATE);
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		int day2 = c2.get(Calendar.DATE);
		if (c1.getActualMaximum(Calendar.DAY_OF_MONTH) == day1
				&& c2.getActualMaximum(Calendar.DAY_OF_MONTH) == day2) {
			return DateTimeHelper.getDiscrepantMonthLeap(date1, date2);
		}
		double month = ((year1 - year2) * 12) + (month1 - month2)
				+ (day1 - day2)
				/ (double) c2.getActualMaximum(Calendar.DAY_OF_MONTH);
		return month;
	}

	/**
	 * 精确计算方式计算两个日期之间的月份差，支持闰年
	 * 计算方法：整月数+(date1.day/date1.max_month_day)+(1-date2.
	 * day/date2.max_month_day)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static double getDiscrepantMonthLeap(Date date1, Date date2)
			throws Exception {
		double month = 0d;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int day1 = c1.get(Calendar.DATE);
		int day2 = c2.get(Calendar.DATE);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		int months = ((year1 - year2) * 12) + (month1 - month2) - 1;
		int max1 = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
		int max2 = c2.getActualMaximum(Calendar.DAY_OF_MONTH);
		month = months + (day1 / (double) max1);
		day2 = max2 - day2;
		month += day2 / (double) max2;
		return month;
	}

	/**
	 * 计算两个日期之间相差多少月 精确到月
	 *  
	 * 日期 2016-03-02
	 */
	public static double getDiscrepantMonthI(Date date1, Date date2)
			throws Exception {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		double month = ((year1 - year2) * 12) + (month1 - month2);

		return month;
	}

	/**
	 * 计算两个日期之间相差多少月 精确到月
	 * 
	 * 日期 2016-03-02
	 */
	public static int getDiscrepantMonthInt(Date date1, Date date2)
			throws Exception {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		int month = ((year1 - year2) * 12) + (month1 - month2);

		return month;
	}

	/**
	 * 计算两个日期之间相差多少月 精确到日期 yy
	 * 
	 * （截止日期－开始日期 的最小月数) + (剩余天数/31)；
	 * 
	 * 日期 2016-03-02
	 */
	public static double getDiscrepantMonthII(Date date1, Date date2)
			throws Exception {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DATE);
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		int day2 = c2.get(Calendar.DATE);
		double month = ((year1 - year2) * 12) + (month1 - month2)
				+ (day1 - day2) / 31d;
		return month;
	}

	/**
	 * 比较两个日期是否相同
	 * 
	 * @throws Exception
	 */
	public static boolean compareDateTime(Date date1, Date date2)
			throws Exception {
		return (date1.before(date2) || date2.before(date1)) ? false : true;
	}

	/**
	 * 比较两个日期是否相同, 只比较年月日
	 * 
	 * @throws Exception
	 */
	public static boolean compareDay(Date date1, Date date2) throws Exception {
		// return (date1.before(date2) || date2.before(date1)) ? false : true;
		// Calendar c1 = Calendar.getInstance();
		// Calendar c2 = Calendar.getInstance();
		// c1.setTime(date1);
		// c2.setTime(date2);
		//
		// return c1.get(Calendar.DATE) == c2.get(Calendar.DATE)
		// && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
		// && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);

		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
					.get(Calendar.DAY_OF_YEAR) == cal2
				.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * <b> 根据指定格式转换字符串为日期 </b> <br>
	 * 
	 * 如果字符串格式不正确,则返回null
	 * 
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            指定格式,参照DateTimeHelper类中常量定义
	 * @return java.util.Date
	 */
	public static Date parseDate(String date, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException pe) {
			// TODO 临时去掉错误日志,因为没有什么意义
			// log.error("", pe);
			return null;
		}
	}

	/**
	 * 将形如 <li>"Thu Dec 6 11:45:13 UTC 0800 2007", 或 <li>
	 * "Thu 12 6 11:45:13 UTC 0800 2007", 或 <li>"Thu Dec 6 11:45:13 2007", 或 <li>
	 * "Thu 12 6 11:45:13 2007" <br>
	 * 的字符串解析为Date对象
	 * 
	 * @return Date对象，解析失败将返回 null
	 * 日期 2016-03-02
	 */
	public static Date parseDateUTC(String date) {
		date = date.substring(4);
		date = date.replace(UTC_0800, "");
		for (int i = 0; i < MONTHS_STRING.length; i++) {
			if (date.startsWith(MONTHS_STRING[i])) {
				date = date.replace(MONTHS_STRING[i], String.valueOf(i + 1));
				break;
			}
		}

		try {
			SimpleDateFormat df = new SimpleDateFormat("MM dd HH:mm:ss yyyy");
			return df.parse(date);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * <b> 将默认格式(<code>yyyy-MM-dd</code>)的日期字符串转换成<code>java.util.Date</code>类型
	 * </b>
	 * 
	 * @param date
	 *            日期字符串
	 * @return java.util.Date
	 */
	public static Date parseDate(String date) {
		return parseDate(date, PATTERN_DEFAULT);
	}
	

	/**
	 * <b> 将默认格式(<code>yyyy-MM-dd HH:mm:ss</code>)的日期字符串转换成<code>java.util.Date</code>类型
	 * </b>
	 * 
	 * @param date
	 *            日期字符串
	 * @return java.util.Date
	 */
	public static Date parseDateAll(String date) {
		return parseDate(date, PATTERN_FULL_DATE);
	}

	/**
	 * 根据日期得到当天结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getEndDay(Date date) {
		return formatDate(date) + " 23:59:59";
	}

	/**
	 * 根据指定格式,格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            指定格式,参照DateTimeHelper类中常量定义
	 * @return java.util.Date
	 */
	public static String formatDate(Date date, String pattern) {
		if(date == null){
			return null;
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 取得今年年底日期,返回Date对象
	 */
	public static Date getEndOfYear() {
		return parseDate(Calendar.getInstance().get(Calendar.YEAR) + "-12-31");// 停止计费时间为年底
	}

	/**
	 * 取得给定年份的年底日期,返回Date对象
	 * 
	 * @param year
	 *            给定年份
	 */
	public static Date getEndOfSpecifyYear(String year) {
		return parseDate(year + "-12-31");// 停止计费时间为年底
	}

	/**
	 * 取得今年年底日期,返回字符串
	 */
	public static String getEndOfYearStr() {
		return Calendar.getInstance().get(Calendar.YEAR) + "-12-31";// 停止计费时间为年底
	}

	/**
	 * 取得当前日期的下个月末的日期,返回Date对象
	 * 
	 * @throws Exception
	 */
	public static Date getEndOfNextMonth() throws Exception {
		Calendar calendar = Calendar.getInstance();
		int nextMonth = calendar.get(Calendar.MONTH) + 2;
		Date startOfNextMonth = parseDate(calendar.get(Calendar.YEAR) + "-"
				+ nextMonth + "-1");

		int nextMonthDays = getDateDays(startOfNextMonth);

		return parseDate(calendar.get(Calendar.YEAR) + "-" + nextMonth + "-"
				+ nextMonthDays);

		// return getEndOfNextMonth(new Date());
	}

	/**
	 * 取得给定日期的下个月末的日期,返回Date对象
	 * 
	 * @param date
	 *            给定日期
	 * @throws Exception
	 */
	public static Date getEndOfNextMonth(Date date) throws Exception {
		Calendar calendar = Calendar.getInstance();//
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);

		int nextMonth = calendar.get(Calendar.MONTH) + 2;

		if (nextMonth > 12) {
			nextMonth = nextMonth - 12;
			year = year + 1;
		} else {

		}
		Date startOfNextMonth = parseDate(year + "-" + nextMonth + "-1");// 停止计费时间为年底
		int nextMonthDays = getDateDays(startOfNextMonth);
		return parseDate(year + "-" + nextMonth + "-" + nextMonthDays);

		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(date);
		// calendar.add(Calendar.MONTH, 1);
		// calendar.set(Calendar.DATE,
		// calendar.getActualMaximum(Calendar.DATE));
		// calendar.set(Calendar.HOUR_OF_DAY, 0);
		// calendar.set(Calendar.MINUTE, 0);
		// calendar.set(Calendar.SECOND, 0);
		//
		// return calendar.getTime();
	}

	/**
	 * 判断两个日期的大小关系,忽略时分秒，只比较年月日
	 * 
	 * @param d1
	 * @param d2
	 * @return -1 d1在d2之前, 0 d1与d2相等, 1 d1在d2之后
	 * @throws Exception
	 */
	public static int DateRelation(Date d1, Date d2) throws Exception {
		if (d1 == null)
			throw new Exception("判断两个日期的大小关系时d1不能为空!");
		if (d2 == null)
			throw new Exception("判断两个日期的大小关系时d2不能为空!");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		c1.set(Calendar.HOUR, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		c2.set(Calendar.HOUR, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
				&& c1.get(Calendar.DATE) == c2.get(Calendar.DATE))
			return 0;
		else if (c1.before(c2))
			return -1;
		else
			return 1;
	}

	/**
	 * 取得给定日期的年份
	 * 
	 * @param d
	 *            给定日期
	 * @throws Exception
	 */
	public static int getYear(Date d) throws Exception {
		if (d == null)
			throw new Exception("获取日期年份时d不能为空!");
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 根据默认格式(<code>yyyy-MM-dd</code>),格式化日期
	 * 
	 * @param date
	 *            日期
	 * @return java.util.Date
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return null;
		return new SimpleDateFormat(PATTERN_DEFAULT).format(date);
	}

	/**
	 * 获取指定日期的当月月底的号数 modify by fyy 注意还要加上这个c.set(Calendar.DATE,
	 * 1);，否则如7.31号想查其他月份的最后一天都是31了
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份（注意要当前月份-1）
	 * @return 最后一天的号数
	 * @throws Exception
	 */
	// public static String getEndDayOfMonth(String year, String month)
	// throws Exception {
	// if (StringUtil.isnull(year)) {
	// throw new Exception("传入参数year不能为空！");
	// }
	// if (StringUtil.isnull(month)) {
	// throw new Exception("传入参数month不能为空！");
	// }
	// Calendar c = Calendar.getInstance();
	// c.set(Calendar.YEAR, Integer.parseInt(year));
	// c.set(Calendar.DATE, 1);
	// c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
	// return String.valueOf(c.getActualMaximum(Calendar.DAY_OF_MONTH));
	// }

	/**
	 * 获取指定日期的月底的时间
	 * 
	 * @param d
	 * @return 月底的时间
	 * @throws Exception
	 */
	// public static Date getMonthEndDay(Date d) throws Exception {
	// if (null == d)
	// throw new Exception("时间不能为空！");
	// Calendar c1 = Calendar.getInstance();
	// c1.setTime(d);
	// c1.set(Calendar.DATE,
	// Integer.valueOf(getEndDayOfMonth(
	// String.valueOf(c1.get(Calendar.YEAR)),
	// String.valueOf(c1.get(Calendar.MONTH) + 1))));
	// return DateTimeHelper.parseDate(DateTimeHelper.formatDate(c1.getTime()));
	// }

	/**
	 * 获取指定日期的月初的时间
	 * 
	 * @param d
	 * @return 月底的时间
	 * @throws Exception
	 * 日期 2016-03-02
	 */
	public static Date getMonthStartDay(Date d) throws Exception {
		if (null == d)
			throw new Exception("时间不能为空！");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d);
		c1.set(Calendar.DATE, 1);
		c1.set(Calendar.MONTH, 1);
		return DateTimeHelper.parseDate(DateTimeHelper.formatDate(c1.getTime()));
	}

	/**
	 * 获取指定日期、前后n年的日期 月初时间
	 * 
	 * @param d
	 * @return 月底的时间
	 * @throws Exception
	 * 日期 2016-03-02
	 */
	public static Date getYearStartDay(int year) throws Exception {
		// if (null == d)
		// throw new Exception("时间不能为空！");
		Calendar c1 = Calendar.getInstance();
		// c1.setTime(d);
		c1.set(Calendar.DATE, 1);
		c1.set(Calendar.MONTH, 0);
		c1.set(Calendar.YEAR, year);
		return DateTimeHelper.parseDate(DateTimeHelper.formatDate(c1.getTime()));

	}

	/**
	 * 根据上下半月获取指定日期的月底的时间，例如2009-11-15-》2009-10-31；2009-11-16-》2009-11-30
	 * 
	 * @param d
	 * @return 月底的时间
	 * @throws Exception
	 */
	// public static Date getMonthEndDayFor15(Date d) throws Exception {
	// if (null == d)
	// throw new Exception("时间不能为空！");
	// Calendar c1 = Calendar.getInstance();
	// c1.setTime(d);
	// if (c1.get(Calendar.DATE) < 16) {
	// c1.add(Calendar.MONTH, -1);
	// }
	// c1.set(Calendar.DATE,
	// Integer.valueOf(getEndDayOfMonth(
	// String.valueOf(c1.get(Calendar.YEAR)),
	// String.valueOf(c1.get(Calendar.MONTH) + 1))));
	// return DateTimeHelper.parseDate(DateTimeHelper.formatDate(c1.getTime()));
	// }

	/**
	 * 根据上下半月规则获取两个日期相差的月数(未经测试，慎用)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static double getDiscrepantMonthIFor15(Date d1, Date d2)
			throws Exception {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		if (c1.get(Calendar.DATE) > 15) {
			c1.set(Calendar.MONTH, c1.get(Calendar.MONTH) + 1);
		}
		if (c2.get(Calendar.DATE) > 15) {
			c2.set(Calendar.MONTH, c2.get(Calendar.MONTH) + 1);
		}
		return getDiscrepantMonthI(d1, d2);
	}

	/**
	 * 根据上下半月规则，比较sdate和edate相差多少个月，返回绝对值 modify by fyy for
	 * 模拟业务订购的时候，上下半月的日期在这里算的，修改在这里修改
	 * 
	 * @param sdate
	 *            比较的转换日期，格式YYYYMMDD
	 * @param edate
	 *            产品的截止日期，格式YYYYMMDD
	 * @return feemonth 相差的月数 返回的是正整数
	 * @throws Exception
	 */
	public static int countFeeMonth(String sdate, String edate)
			throws Exception {
		/**
		 * num1 = sdate的int型，num2 = edate的int型，feemonth = 存放返回的月数
		 */
		int num1 = Integer.valueOf(sdate);
		int num2 = Integer.valueOf(edate);
		int feemonth = 0;
		/**
		 * 存放比较的时间，biggerdate为较大的日期，smallerdate为较少的日期，相等直接返回相差月数为0
		 */
		String biggerdate = null;
		String smallerdate = null;
		if (num1 > num2) {
			biggerdate = sdate;
			smallerdate = edate;
		} else if (num1 < num2) {
			biggerdate = edate;
			smallerdate = sdate;
		} else {
			return feemonth;
		}
		/**
		 * 计算biggerdate，smallerdate相差多少月。***************** 规则:1.feemonth =
		 * biggerdate - smallerdate 相差的月数减1; ****2.if biggerdate的号数 >= 16
		 * feemonth += 1; ****3.if smallerdate的号数 < 16 feemonth += 1;
		 */
		feemonth = (Integer.valueOf(biggerdate.substring(0, 4)) - Integer
				.valueOf(smallerdate.substring(0, 4))) * 12;
		feemonth += (Integer.valueOf(biggerdate.substring(4, 6)) - Integer
				.valueOf(smallerdate.substring(4, 6))) - 1;
		if (16 <= Integer.valueOf(biggerdate.substring(6, 8))) {
			feemonth += 1;
		}
		if (16 > Integer.valueOf(smallerdate.substring(6, 8))) {
			feemonth += 1;
		}
		return feemonth;
	}

	/**
	 * 根据上下半月规则，比较sdate和edate相差多少个月，sdate<edate 返回正数，sdate>edate 返回负数 modify by
	 * fyy for 模拟业务订购的时候，上下半月的日期在这里算的，修改在这里修改
	 * 
	 * @param sdate
	 *            日期型
	 * @param edate
	 *            日期型
	 * @return feemonth 相差的月数，正负根据sdate是否大于edate决定
	 * @throws Exception
	 */
	public static int countFeeMonth(Date sdate, Date edate) throws Exception {
		return countFeeMonth(sdate, edate, 16);
	}

	/**
	 * 根据上下半月(根据临界日把月份分为上下半月)规则，比较sdate和edate相差多少个月，sdate<edate 返回正数，sdate>edate
	 * 返回负数
	 * 
	 * @param sdate
	 *            日期型
	 * @param edate
	 *            日期型
	 * @param circles
	 *            整形
	 * @return feemonth 相差的月数，正负根据sdate是否大于edate决定
	 * @throws Exception
	 */
	public static int countFeeMonth(Date sdate, Date edate, int circles)
			throws Exception {
		/**
		 * num1 = sdate的int型，num2 = edate的int型，feemonth = 存放返回的月数
		 */
		String ssdate = DateTimeHelper.formatDate(sdate,
				DateTimeHelper.PATTERN_DATE_COMPACT);
		String eedate = DateTimeHelper.formatDate(edate,
				DateTimeHelper.PATTERN_DATE_COMPACT);
		int num1 = Integer.valueOf(ssdate);
		int num2 = Integer.valueOf(eedate);
		int feemonth = 0;
		/**
		 * 存放比较的时间，biggerdate为较大的日期，smallerdate为较少的日期，相等直接返回相差月数为0
		 */
		String biggerdate = null;
		String smallerdate = null;
		if (num1 > num2) {
			biggerdate = ssdate;
			smallerdate = eedate;
		} else if (num1 < num2) {
			biggerdate = eedate;
			smallerdate = ssdate;
		} else {
			return feemonth;
		}
		/**
		 * 计算biggerdate，smallerdate相差多少月。***************** 规则:1.feemonth =
		 * biggerdate - smallerdate 相差的月数减1; ****2.if biggerdate的号数 >= 16
		 * feemonth += 1; ****3.if smallerdate的号数 < 16 feemonth += 1;
		 */
		feemonth = (Integer.valueOf(biggerdate.substring(0, 4)) - Integer
				.valueOf(smallerdate.substring(0, 4))) * 12;
		feemonth += (Integer.valueOf(biggerdate.substring(4, 6)) - Integer
				.valueOf(smallerdate.substring(4, 6))) - 1;
		if (circles <= Integer.valueOf(biggerdate.substring(6, 8))) {
			feemonth += 1;
		}
		if (circles > Integer.valueOf(smallerdate.substring(6, 8))) {
			feemonth += 1;
		}
		if (num1 > num2) {
			feemonth = feemonth * -1;
		}
		return feemonth;
	}

	/**
	 * 上下半月规则加月数
	 * 
	 * @param d1
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static Date addMonthFor15(Date d1, int month) throws Exception {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		if (c1.get(Calendar.DATE) > 15) {
			c1.set(Calendar.DATE, 1);
			c1.set(Calendar.MONTH, c1.get(Calendar.MONTH) + month);
		} else {
			c1.set(Calendar.MONTH, c1.get(Calendar.MONTH) + month - 1);
		}
		c1.set(Calendar.DATE, getDateDays(c1.getTime()));
		return c1.getTime();

	}

	/**
	 * 默认日期格式, <code>yyyy-MM-dd</code>
	 */
	public static final String PATTERN_DEFAULT = "yyyy-MM-dd";
	
	public static final String PATTERN_FULL_DATE = "yyyy-MM-dd HH:mm:ss";
	
	public static final String PATTERN_SHORT_DATE = "yyyy-MM-dd HH:mm";
	
	public static final String PATTERN_FULL_TIME = "HH:mm:ss";
	
	public static final String PATTERN_SHORT_TIME = "HH:mm";

	/**
	 * 路径格式, <code>yyyy\MM\dd\</code>
	 */
	public static final String PATTERN_DAYPATH = "yyyy\\MM\\dd\\";

	/**
	 * 日期时间格式, <code>yyyy-MM-dd HH:mm:ss</code>, 24小时制
	 */
	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 无间隔符的日期时间格式, <code>yyyyMMddHHmmss</code>, 24小时制
	 */
	public static final String PATTERN_DATETIME_COMPACT = "yyyyMMddHHmmss";

	/**
	 * 无间隔符日期格式, <code>yyyyMMdd</code>
	 */
	public static final String PATTERN_DATE_COMPACT = "yyyyMMdd";

	/**
	 * 无间隔符日期短格式, <code>yyMMdd</code>
	 */
	public static final String PATTERN_DATESHORT = "yyMMdd";

	/**
	 * 年月格式, <code>yyyyMM</code>
	 */
	public static final String PATTERN_YEARMONTH = "yyyyMM";

	/**
	 * 获取下一天，精确到时分秒
	 * 
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 李国平
	 * @date 2015年5月7日
	 */
	@SuppressWarnings("static-access")
	public static Date getNextDate() {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}

	/**
	 * 比较两个日期之间的大小 , 前面日期参数（第一个）大于等于后面（第二个）参数时,返回 true
	 * 
	 * @param @param d1
	 * @param @param d2
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author 李国平
	 * @date 2015年5月7日
	 */
	public static boolean compareDate(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		if (c1.compareTo(c2) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将String时间转为Date格式返回
	 * 
	 * @param @param date 被转换的日期
	 * @param @param format 转换的日期格式，如
	 * @param @return
	 * @return Date
	 * @throws
	 * 日期 2016-03-02
	 */
	public static Date strTimeToDate(String date, String format) {
		Date result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			result = sdf.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * date推迟duration后的时间
	 * 
	 * @param @param date 当前时间
	 * @param @param duration 时长
	 * @param @return
	 * @return Date 推迟（时长）后的时间
	 * @throws
	 * 日期 2016-03-02
	 */
	public static Date delay(Date date, Integer durDay, Integer durHour, Integer durMin) {
		Date result = null;
		try {
			Calendar dar = Calendar.getInstance();
			dar.setTime(date);
			
			if(durDay != null){
				dar.add(java.util.Calendar.DAY_OF_YEAR, durDay);
			}
			if(durHour != null){
				dar.add(java.util.Calendar.HOUR_OF_DAY, durHour);
			}
			
			if(durMin != null){
				dar.add(java.util.Calendar.MINUTE, durMin);
			}
			
			dar.add(java.util.Calendar.SECOND, -1);//往前推1s，结束时间为59s后缀
			result = dar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 *  date推迟1s后的时间
	 * 
	 * @param       @param date
	 * @param       @return
	 * @return      Date
	 * @throws
	 * 日期 2016-03-02
	 */
	public static Date afterOneSecond(Date date) {
		Date result = null;
		try {
			Calendar dar = Calendar.getInstance();
			dar.setTime(date);
			dar.add(java.util.Calendar.SECOND, 1);// 往后推迟1s
			result = dar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String formatEndDate(Date date){
		date = afterOneSecond(date);
		String result = formatDate(date, PATTERN_SHORT_TIME);
		if(result.equals("00:00")){
			result = "24:00";
		}
		return result;
	}
	
	/**
	 * 获取某年，某月，某日号为周几
	 * @param       @return 返回值为0是周日，1是周一，依次类推
	 * @return      Integer
	 * @throws
	 * 日期 2016-03-02
	 */
	public static Integer getWeekOfDate(Integer year, Integer month, Integer day){
		Integer result = 0;
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(year, month - 1, day);
		result = currentDate.get(Calendar.DAY_OF_WEEK) - 1;
		return result;
	}
	
	/**
	 * 获取某年某月，月天数
	 * @param       @param year
	 * @param       @param month
	 * @param       @return
	 * @return      Integer
	 * @throws
	 * 日期 2016-03-02
	 */
	public static Integer getDaysOfMonth(Integer year, Integer month){
		Integer result = 0;
		switch(month){
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				result = 31;break;
			case 2:
				if(isLeepYear(year)){
					result = 29;
				}else{
					result = 28;
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				result = 30;break;
		}
		return result;
	}
	
	/**
	 * 判断是否闰年
	 * @param       @param year
	 * @param       @return
	 * @return      boolean
	 * @throws
	 * 日期 2016-03-02
	 */
	public static boolean isLeepYear(Integer year){
		return ( 0 == year%4 && ((year%100 != 0)||(year%400 == 0)));
	}

	/**
	 * 
	 * 日期 2015年6月5日 
	 * 方法名称 dateDiff
	 *       @param startTime 起始时间
	 *       @param endTime   结束时间
	 *       @param format    形如："yyyy-MM-dd HH:mm:ss"
	 * 描述 比较两个时间差，返回时间相差：0天X小时XX分钟XX秒。
	 */
	public static void dateDiff(String startTime, String endTime, String format) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff / nd;// 计算差多少天
			long hour = diff % nd / nh;// 计算差多少小时
			long min = diff % nd % nh / nm;// 计算差多少分钟
			long sec = diff % nd % nh % nm / ns;// 计算差多少秒
			// 输出结果
//			System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟"
//					+ sec + "秒。");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  
	 * 方法名称 getDateDiffByHour
	 *       @param startTime
	 *       @param endTime
	 *       @param format
	 * 描述 比较两个时间差，返回，时间值。
	 * 日期 2016-03-02
	 */
	public static float getDateDiffByHour(String startTime, String endTime, String format) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long diff;
		float result = 0f;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long hour = diff % nd / nh;// 计算差多少小时
			float min = diff % nd % nh / nm;// 计算差多少分钟 
			result = hour + min / 60; 
			 
			result   =   (float)(Math.round(result*100))/100;
		 
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	
	/**
	 *  
	 * 方法名称 getDateDiffBySeconds
	 *       @param startTime
	 *       @param endTime
	 *       @param format
	 *       @return
	 * 描述 计算相差多少秒
	 * 日期 2016-03-02
	 */
	public static float getDateDiffBySeconds(String startTime, String endTime, String format) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format); 
		long diff = 0;
		float result = 0f;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime(); 
			result = diff / 1000; // 计算差多少秒   
		 
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	
	/**   
	 * 方法名称 getEndDate
	 *       @param startDate
	 *       @param duration
	 *       @return
	 * 描述 从当前起始时间，加上时长后的结束时间，精确到小时
	 */
	@SuppressWarnings("static-access")
	public static Date getEndDate(Date startDate,Integer durHour,Integer durMin) { 
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		calendar.add(calendar.HOUR, durHour);// 把日期往后增加一个小时.整数往后推,负数往前移动
		calendar.add(calendar.MINUTE, durMin);// 把日期往后增加一个小时.整数往后推,负数往前移动
		return calendar.getTime();
	}
	
	
	
	/**
	 * returns the current timestamp
	 * 
	 * @return current timestamp in ISO 8601 format
	 */
	public static String getCurrentTimestamp(Date date) {
		final TimeZone tz = TimeZone.getTimeZone("UTC");
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
		df.setTimeZone(tz);
		final String nowAsISO = df.format(date);
		return nowAsISO;
	}
	
	public static Date TimeLongStr2Timestr(String str){
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date da = null;
        try {
            da = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
	    return da;
	}


	public static void main(String[] args) throws ParseException {
//	  float result =  DateTimeHelper.getDateDiffByHour( 
//				"2015-06-05 12:00:00", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),"yyyy-MM-dd HH:mm:ss");
//	  
//	  System.out.println("result is :" + result);
//	  
//	   result =  DateTimeHelper.getDateDiffBySeconds( 
//				"2015-06-10 21:33:00", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),"yyyy-MM-dd HH:mm:ss");
//	  
//	  System.out.println("result is :" + result);
//	  
//	  System.out.println("结束时间为:" + getEndDate(new Date(),2,30));
//	  
//	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	  Date date1 = new Date();
//	  Date date2 = sdf.parse("2015-06-18 22:00:00");
//	  System.out.println(date2.compareTo(date1));
//		System.out.println(ChineseDate(new Date()));
	    
	}
}
