package com.it.ocs.amazon.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.it.ocs.amazon.utils.SiteDateParseUtils;

public class ColumnData {
	private final static int REPORT_STRING = 0;
	private final static int IT_DATE = 1;
	private final static int US_DATE = 2;
	private final static int DE_DATE = 3;
	private final static int CA_DATE = 4;
	private final static int JP_DATE = 5;
	private final static int FR_DATE = 6;
	private final static int ES_DATE = 7;
	private final static int UK_DATE = 8;
	private final static int REPORT_NUMBER = 9;
	private final static int AU_DATE = 10;
	
	private Integer id;
	private String reportType;
	private String columnName;
	private String linkName;
	private Integer columnType;
	private String site;
	private String value;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public Integer getColumnType() {
		return columnType;
	}
	public void setColumnType(Integer columnType) {
		this.columnType = columnType;
	}
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		
		String reValue = value;
		
		switch(columnType){
			case IT_DATE:
				//11/nov/2017 23.06.29 GMT+00.00
				/*value = value.replace("GMT+00.00", "GMT+00:00");
				value = value.replace("GMT+01.00", "GMT+01:00");
				value = value.replace("GMT+02.00", "GMT+02:00");
				reValue = formatDateString("dd/MMM/yyyy HH.mm.ss zzz",Locale.ITALY,value);*/
				reValue = SiteDateParseUtils.it(value);
				break;
			
			case US_DATE:
				//Nov 13, 2017 12:00:08 AM PST
				/*reValue = formatDateString("MMM dd, yyyy hh:mm:ss aaa zzz",Locale.US,value);*/
				reValue = SiteDateParseUtils.us(value);
				break;
			
			case DE_DATE:
				//11.11.2017 23:00:37 GMT+00:00
				/*reValue = formatDateString("dd.MM.yyyy HH:mm:ss zzz",Locale.GERMAN,value);*/
				reValue = SiteDateParseUtils.de(value);
				break;
				
			case CA_DATE:
				//2017-11-10 12:25:54 AM PST
				/*reValue = formatDateString("yyyy-MM-dd hh:mm:ss aaa zzz",Locale.CANADA,value);*/
				reValue = SiteDateParseUtils.ca(value);
				break;
				
			case UK_DATE:
				//12 Nov 2017 00:01:31 GMT+00:00
				/*reValue = formatDateString("dd MMM yyyy HH:mm:ss zzz",Locale.UK,value);*/
				reValue = SiteDateParseUtils.uk(value);
				break;
				
			case FR_DATE:
				//11 nov. 2017 23:13:26 UTC+00:00
				/*reValue = formatDateString("dd MMM yyyy HH:mm:ss zzz",Locale.FRANCE,value);*/
				reValue = SiteDateParseUtils.fr(value);
				break;
				
			case ES_DATE:
				//12/11/2017 13:35:53 GMT+00:00
				/*reValue = formatDateString("dd/MM/yyyy HH:mm:ss zzz",Locale.GERMAN,value);*/
				reValue = SiteDateParseUtils.es(value);
				break;
				
			case JP_DATE:
				//2017/11/11 00:09:37JST
				/*reValue = formatDateString("yyyy/MM/dd HH:mm:sszzz",Locale.JAPAN,value);*/
				reValue = SiteDateParseUtils.jp(value);
				break;
				
			case AU_DATE:
				//02/03/2018 5:20:26 PM GMT+09:00
				/*reValue = formatDateString("dd/MM/yyyy HH:mm:ss aaa zzz",Locale.ROOT,value);*/
				reValue = SiteDateParseUtils.au(value);
				break;
				
			case REPORT_STRING:
				reValue = value;
				break;
			case REPORT_NUMBER:
				if("amazon.com".equals(site) ||"amazon.ca".equals(site)||"amazon.co.uk".equals(site)||"amazon.jp".equals(site)||"amazon.com.au".equals(site)){
					reValue = value.replace(",", "");
				}else{
					reValue = value.replace(".", "").replace(",", ".");
				}
				/////////下面一行未更换特殊字符（类似空格），请勿修改删除//////
				reValue = reValue.replaceAll(" ", "").trim();
				///////////////////////////////////////////////
				reValue = reValue.replace(" ", "");
				break;
			default :	
		}
		return reValue;
	}
	
	public static String getSelfDateValue(String value,int site) {
		String reValue = value;
		String str[] = null;
		String zone = null;
		switch(site){
			case IT_DATE:
				//11/nov/2017 23.06.29 GMT+00.00
				/*str = value.split(" ");
				zone = str[str.length-1];
				value = value.replace(zone, zone.replace(".", ":"));
				reValue = formatSelfDateString("dd/MMM/yyyy HH.mm.ss zzz",Locale.ITALY,value,zone.replace(".", ":"));*/
				reValue = SiteDateParseUtils.it(value);
				break;
			
			case US_DATE:
				//Nov 13, 2017 12:00:08 AM PST
				reValue = SiteDateParseUtils.us(value);
				break;
			
			case DE_DATE:
				//11.11.2017 23:00:37 GMT+00:00
				/*str = value.split(" ");
				zone = str[str.length-1];
				reValue = formatSelfDateString("dd.MM.yyyy HH:mm:ss zzz",Locale.GERMAN,value,zone);*/
				reValue = SiteDateParseUtils.de(value);
				break;
				
			case CA_DATE:
				//2017-11-10 12:25:54 AM PST
				/*str = value.split(" ");
				zone = str[str.length-1];
				reValue = formatSelfDateString("yyyy-MM-dd hh:mm:ss aaa zzz",Locale.CANADA,value,zone);*/
				reValue = SiteDateParseUtils.ca(value);
				break;
				
			case UK_DATE:
				//12 Nov 2017 00:01:31 GMT+00:00
				/*str = value.split(" ");
				zone = str[str.length-1];
				reValue = formatSelfDateString("dd MMM yyyy HH:mm:ss zzz",Locale.UK,value,zone);*/
				reValue = SiteDateParseUtils.uk(value);
				break;
				
			case FR_DATE:
				//11 nov. 2017 23:13:26 UTC+00:00
				/*str = value.split(" ");
				zone = str[str.length-1];
				reValue = formatSelfDateString("dd MMM yyyy HH:mm:ss zzz",Locale.FRANCE,value,zone);*/
				reValue = SiteDateParseUtils.fr(value);
				break;
				
			case ES_DATE:
				//12/11/2017 13:35:53 GMT+00:00
				/*str = value.split(" ");
				zone = str[str.length-1];
				reValue = formatSelfDateString("dd/MM/yyyy HH:mm:ss zzz",Locale.GERMAN,value,zone);*/
				reValue = SiteDateParseUtils.es(value);
				break;
				
			case JP_DATE:
				//2017/11/11 00:09:37JST
				/*reValue = formatSelfDateString("yyyy/MM/dd HH:mm:sszzz",Locale.JAPAN,value,"JST");*/
				reValue = SiteDateParseUtils.jp(value);
				break;
			case AU_DATE:
				//02/03/2018 5:20:26 PM GMT+09:00
				/*str = value.split(" ");
				zone = str[str.length-1];
				reValue = formatSelfDateString("dd/MM/yyyy HH:mm:ss aaa zzz",Locale.ROOT,value,zone);*/
				reValue = SiteDateParseUtils.au(value);
				break;
				
			case REPORT_STRING:
				reValue = value;
				break;
			default :	
		}
		return reValue;
	}
	
	public static String formatDateString(String pattern,Locale locale,String value){
		return formatSelfDateString(pattern,locale,value,"UTC");
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
	
	public static void main(String[] args) throws ParseException {
		/*System.out.println();
		System.out.println("11/nov/2017 23.06.29 GMT+00.00");
		System.out.println(getSelfDateValue("11/nov/2017 23.06.29 GMT+00.00",ColumnData.IT_DATE));//formatSelfDateString("dd/MMM/yyyy HH.mm.ss zzz",Locale.ITALY,value,zone.replace(".", ":")));
	
		System.out.println("Nov 13, 2017 12:00:08 AM PST");
		System.out.println(getSelfDateValue("Nov 13, 2017 12:00:08 AM PST",ColumnData.US_DATE));//formatSelfDateString("MMM dd, yyyy hh:mm:ss aaa zzz",Locale.US,value,zone);
	
		System.out.println("11.11.2017 23:00:37 GMT+00:00");
		System.out.println(getSelfDateValue("11.11.2017 23:00:37 GMT+00:00",ColumnData.DE_DATE));//formatSelfDateString("dd.MM.yyyy HH:mm:ss zzz",Locale.GERMAN,value,zone);
		
		System.out.println("2017-11-10 12:25:54 AM PST");
		System.out.println(getSelfDateValue("2017-11-10 12:25:54 AM PST",ColumnData.CA_DATE));//formatSelfDateString("yyyy-MM-dd hh:mm:ss aaa zzz",Locale.CANADA,value,zone);
		
		System.out.println("12 Nov 2017 00:01:31 GMT+00:00");
		System.out.println(getSelfDateValue("12 Nov 2017 00:01:31 GMT+00:00",ColumnData.UK_DATE));//formatSelfDateString("dd MMM yyyy HH:mm:ss zzz",Locale.UK,value,zone);
		
		System.out.println("11 nov. 2017 23:13:26 UTC+00:00");
		System.out.println(getSelfDateValue("11 nov. 2017 23:13:26 UTC+00:00",ColumnData.FR_DATE));//formatSelfDateString("dd MMM yyyy HH:mm:ss zzz",Locale.FRANCE,value,zone);
		System.out.println("12/11/2017 13:35:53 GMT+00:00");
		System.out.println(getSelfDateValue("12/11/2017 13:35:53 GMT+00:00",ColumnData.ES_DATE));//formatSelfDateString("dd/MM/yyyy HH:mm:ss zzz",Locale.GERMAN,value,zone);
		
		System.out.println("2017/11/11 00:09:37JST");
		System.out.println(getSelfDateValue("2017/11/11 00:09:37JST",ColumnData.JP_DATE));//formatSelfDateString("yyyy/MM/dd HH:mm:sszzz",Locale.JAPAN,value,"JST");
		String dis = " ";
		System.out.println("-43 897,49".replaceAll(" ", ""));*/
		//System.out.println(getSelfDateValue("Nov 13, 2017 12:00:08 AM PST",2));
		
	/*	System.out.println("11 nov. 2017 23:13:26 UTC+00:00");
		System.out.println(getSelfDateValue("11 nov. 2017 23:13:26 UTC+00:00",ColumnData.FR_DATE));//formatSelfDateString("dd MMM yyyy HH:mm:ss zzz",Locale.FRANCE,value,zone);
		System.out.println("12/11/2017 13:35:53 GMT+09:00");
		System.out.println(getSelfDateValue("12/11/2017 13:35:53 GMT+09:00",ColumnData.ES_DATE));//formatSelfDateString("dd/MM/yyyy HH:mm:ss zzz",Locale.GERMAN,value,zone);
	*/
		
		System.out.println(formatDateString("yyyy-MM-dd hh:mm:ss aaa zzz",Locale.US,"2018-05-13 2:33:25 PM PDT"));
		System.out.println(formatSelfDateString("yyyy-MM-dd hh:mm:ss aaa zzz",Locale.US,"2018-05-13 2:33:25 PM PDT","PDT"));
		
		System.out.println(formatDateString("MMM dd, yyyy hh:mm:ss aaa",Locale.US,"May 1, 2018 12:00:37 AM"));
		System.out.println(formatSelfDateString("MMM dd, yyyy hh:mm:ss aaa zzz",Locale.US,"May 1, 2018 12:00:37 AM PDT","PDT"));
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
		Date date = sdf.parse("May 1, 2018 12:00:37 AM");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//sdf1.setTimeZone(TimeZone.getTimeZone(Locale.US));
		System.out.println(sdf1.format(date));
		
	}
}
