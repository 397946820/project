package com.it.ocs.amazon.utils;

public class SiteDateParseUtils {
	
	/**
	 * 意大利
	 * @param value 11/nov/2017 23.06.29 GMT+00.00
	 * @return
	 */
	public static String it(String value){
		String year = "";
		String month = "";
		String day = "";
		String hour = "";
		String minute = "";
		String second = "";
		String str[] = value.split(" ");
		String ymd[] = str[0].split("/");
		String hms[] = str[1].split("\\.");
		day = addZero(ymd[0]);
		month = formateMonth(ymd[1]);
		year = ymd[2];
		hour = addZero(hms[0]);
		minute = addZero(hms[1]);
		second = addZero(hms[2]);
		return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
	}
	
	/**
	 * 美国
	 * @param value Nov 13, 2017 12:00:08 AM PST
	 * @return
	 */
	public static String us(String value){
		String year = "";
		String month = "";
		String day = "";
		String str[] = value.split(" ");
		day = addZero(str[1].replace(",", ""));
		month = formateMonth(str[0]);
		year = str[2];

		return year+"-"+month+"-"+day+" "+str[3];
	}
	
	/**
	 * 德国
	 * @param value 11.11.2017 23:00:37 GMT+00:00
	 * @return
	 */
	public static String de(String value){
		String year = "";
		String month = "";
		String day = "";
		String str[] = value.split(" ");
		String dmy[] = str[0].split("\\.");
		day = addZero(dmy[0]);
		month = formateMonth(dmy[1]);
		year = dmy[2];

		return year+"-"+month+"-"+day+" "+str[1];
	}
	
	/**
	 * 加拿大
	 * @param value 2017-11-10 12:25:54 AM PST
	 * @return
	 */
	public static String ca(String value){
		String str[] = value.split(" ");
		return str[0]+" "+str[1];
	}
	
	/**
	 * 英国
	 * @param value 12 Nov 2017 00:01:31 GMT+00:00
	 * @return
	 */
	public static String uk(String value){
		String year = "";
		String month = "";
		String day = "";
		String str[] = value.split(" ");
		day = addZero(str[0]);
		month = formateMonth(str[1]);
		year = str[2];

		return year+"-"+month+"-"+day+" "+str[3];
	}
	
	/**
	 * 法国
	 * @param value 11 nov. 2017 23:13:26 UTC+00:00
	 * @return
	 */
	public static String fr(String value){
		String year = "";
		String month = "";
		String day = "";
		String str[] = value.split(" ");
		day = addZero(str[0]);
		month = formateMonth(str[1]);
		year = str[2];

		return year+"-"+month+"-"+day+" "+str[3];
	}
	
	/**
	 * 西班牙
	 * @param value 12/11/2017 13:35:53 GMT+00:00
	 * @return
	 */
	public static String es(String value){
		String year = "";
		String month = "";
		String day = "";
		String str[] = value.split(" ");
		String dmy[] = str[0].split("/");
		day = addZero(dmy[0]);
		month = formateMonth(dmy[1]);
		year = dmy[2];

		return year+"-"+month+"-"+day+" "+str[1];
	}
	
	/**
	 * 日本
	 * @param value 2017/11/11 00:09:37JST
	 * @return
	 */
	public static String jp(String value){
		String year = "";
		String month = "";
		String day = "";
		String str[] = value.split(" ");
		String dmy[] = str[0].split("/");
		day = addZero(dmy[2]);
		month = formateMonth(dmy[1]);
		year = dmy[0];

		return year+"-"+month+"-"+day+" "+str[1].replace("JST", "");
	}
	
	/**
	 * 澳大利亚
	 * @param value 02/03/2018 5:20:26 PM GMT+09:00
	 * @return
	 */
	public static String au(String value){
		String year = "";
		String month = "";
		String day = "";
		String hour = "";
		String minute = "";
		String second = "";
		String str[] = value.split(" ");
		String ymd[] = str[0].split("/");
		String hms[] = str[1].split(":");
		day = addZero(ymd[0]);
		month = formateMonth(ymd[1]);
		year = ymd[2];
		hour = addZero(hms[0]);
		minute = addZero(hms[1]);
		second = addZero(hms[2]);
		return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
	}
	
	
	public static String addZero(String s){
		if(s.length() == 1){
			return "0"+s;
		}
		return s;
	}
	public static String formateMonth(String m){
		//Jan
		if(m.toUpperCase().indexOf("JAN")> -1){
			return "01";
		}
		if(m.toUpperCase().indexOf("FEB")> -1){
			return "02";
		}
		if(m.toUpperCase().indexOf("MAR")> -1){
			return "03";
		}
		if(m.toUpperCase().indexOf("APR")> -1||m.toUpperCase().indexOf("AVR")> -1){
			return "04";
		}
		if(m.toUpperCase().indexOf("MAY")> -1||m.toUpperCase().indexOf("MAG")> -1||m.toUpperCase().indexOf("MAI")> -1){
			return "05";
		}
		if(m.toUpperCase().indexOf("JUN")> -1||m.toUpperCase().indexOf("JUIN")> -1||m.toUpperCase().indexOf("GIU")> -1){
			return "06";
		}
		if(m.toUpperCase().indexOf("JUL")> -1||m.toUpperCase().indexOf("LUG")> -1||m.toUpperCase().indexOf("JUIL")> -1){
			return "07";
		}
		if(m.toUpperCase().indexOf("AUG")> -1||m.toUpperCase().indexOf("AGO")> -1||m.toUpperCase().indexOf("AOUT")> -1||m.indexOf("août")> -1){
			return "08";
		}
		if(m.toUpperCase().indexOf("SEP")> -1||m.toUpperCase().indexOf("SET")> -1||m.toUpperCase().indexOf("SEPT")> -1){
			return "09";
		}
		if(m.toUpperCase().indexOf("OCT")> -1||m.toUpperCase().indexOf("OTT")> -1){
			return "10";
		}
		if(m.toUpperCase().indexOf("NOV")> -1){
			return "11";
		}
		if(m.toUpperCase().indexOf("DEC")> -1||m.toUpperCase().indexOf("DIC")> -1){
			return "12";
		}
		return m;
	}
	public static void main(String[] args) {
		System.out.println(it("11/nov/2017 23.06.29 GMT+00.00"));
		System.out.println(us("Nov 13, 2017 12:00:08 AM PDT"));
		System.out.println(de("11.11.2017 23:00:37 GMT+00:00"));
		System.out.println(ca("2017-11-10 12:25:54 AM PST"));
		System.out.println(uk("12 Nov 2017 00:01:31 GMT+00:00"));
		System.out.println(fr("11 nov. 2017 23:13:26 UTC+00:00"));
		System.out.println(es("12/11/2017 13:35:53 GMT+00:00"));
		System.out.println(jp("2017/11/11 00:09:37JST"));
		System.out.println(au("02/03/2018 5:20:26 PM GMT+09:00"));
	}
}
