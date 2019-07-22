package com.it.ocs.amazon.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import com.it.ocs.amazon.utils.AmazonUtils;

public class AmazonRequestMode {
	private String serviceURL;
	private String accessKey;
	private String secretKey;
	private String sellerId;
	private String nextToken;
	private List<String> marketplaceId;
	private XMLGregorianCalendar createdAfter;
	private XMLGregorianCalendar createdBefore;
	private XMLGregorianCalendar updatedAfter;
	private XMLGregorianCalendar updatedBefore;
	private Map<String,Object> param;
	public String getServiceURL() {
		return serviceURL;
	}
	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public List<String> getMarketplaceId() {
		return marketplaceId;
	}
	public void setMarketplaceId(List<String> marketplaceId) {
		this.marketplaceId = marketplaceId;
	}
	public XMLGregorianCalendar getCreatedAfter() {
		return createdAfter;
	}
	/**
	 * YYYY-MM-ddTHH:MM:SS.000Z
	 * @param createdAfter
	 */
	public void setCreatedAfter(String createdAfter) {
		this.createdAfter = formateXMLGregorianCalendar(createdAfter);
	}
	public XMLGregorianCalendar getCreatedBefore() {
		return createdBefore;
	}
	public void setCreatedBefore(String createdBefore) {
		this.createdBefore = formateXMLGregorianCalendar(createdBefore);
	}
	public static int toInt(String intStr){
		return Integer.parseInt(intStr);
	}
	public static XMLGregorianCalendar formateXMLGregorianCalendar(String dateTimeStr){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {
			Date da = format.parse(dateTimeStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(da);
			cal.add(Calendar.MINUTE, -2);
			dateTimeStr = format.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String dateTime[] = dateTimeStr.split("T");
		String date[] = dateTime[0].split("-");
		String time[] = dateTime[1].split(":");
		return AmazonUtils.getDTF().newXMLGregorianCalendar(toInt(date[0]), toInt(date[1]), toInt(date[2]), toInt(time[0]), toInt(time[1]), toInt(time[2].substring(0, time[2].indexOf("."))), 0, 0);

	}
	
	public static void main(String[] args) {
		/*AmazonRequestMode a = new AmazonRequestMode();
		a.setUpdatedAfter(UTCTimeUtils.getUTCTimeStrByMINUTE(-2));
		System.out.println(a.getUpdatedAfter().toString());*/
		System.out.println(AmazonRequestMode.formateXMLGregorianCalendar("2017-09-01T00:00:00.000Z"));
	}
	public String getNextToken() {
		return nextToken;
	}
	public void setNextToken(String nextToken) {
		this.nextToken = nextToken;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	public XMLGregorianCalendar getUpdatedAfter() {
		return updatedAfter;
	}
	public void setUpdatedAfter(String updatedAfter) {
		this.updatedAfter = formateXMLGregorianCalendar(updatedAfter);
	}
	public XMLGregorianCalendar getUpdatedBefore() {
		return updatedBefore;
	}
	public void setUpdatedBefore(String updatedBefore) {
		this.updatedBefore = formateXMLGregorianCalendar(updatedBefore);
	}

	
}
