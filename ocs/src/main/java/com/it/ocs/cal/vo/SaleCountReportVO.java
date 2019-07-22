package com.it.ocs.cal.vo;

import java.io.Serializable;
import java.text.DecimalFormat;

public class SaleCountReportVO implements Serializable{
	private Integer id;
	private String field;
    private String amazonQty;
    private String amazonPrice;
    private String ebayQty;
    private String ebayPrice;
    private String lightQty;
    private String lightPrice;
    private String amazonPEC;
    private String ebayPEC;
    private String lightPEC;
    private String total;
    private String totalPEC;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getAmazonQty() {
		return amazonQty;
	}
	public void setAmazonQty(String amazonQty) {
		this.amazonQty = amazonQty;
	}
	public String getAmazonPrice() {
		return amazonPrice;
	}
	public void setAmazonPrice(String amazonPrice) {
		this.amazonPrice = formatDouble(amazonPrice);
	}
	
	public String getEbayQty() {
		return ebayQty;
	}
	public void setEbayQty(String ebayQty) {
		this.ebayQty = ebayQty;
	}
	public String getEbayPrice() {
		return ebayPrice;
	}
	public void setEbayPrice(String ebayPrice) {
		this.ebayPrice = formatDouble(ebayPrice);
	}
	public String getLightQty() {
		return lightQty;
	}
	public void setLightQty(String lightQty) {
		this.lightQty = lightQty;
	}
	public String getLightPrice() {
		return lightPrice;
	}
	public void setLightPrice(String lightPrice) {
		this.lightPrice = formatDouble(lightPrice);
	}
    
	
    public String getAmazonPEC() {
		return amazonPEC;
	}
	public void setAmazonPEC(String amazonPEC) {
		this.amazonPEC = amazonPEC;
	}
	public String getEbayPEC() {
		return ebayPEC;
	}
	public void setEbayPEC(String ebayPEC) {
		this.ebayPEC = ebayPEC;
	}
	public String getLightPEC() {
		return lightPEC;
	}
	public void setLightPEC(String lightPEC) {
		this.lightPEC = lightPEC;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTotalPEC() {
		return totalPEC;
	}
	public void setTotalPEC(String totalPEC) {
		this.totalPEC = totalPEC;
	}
	private String formatDouble(String value){
    	//Double d = Double.parseDouble(value); 
		//DecimalFormat df = new DecimalFormat("#.00");  
    	return value;//df.format(d);
    }
	
	
}
