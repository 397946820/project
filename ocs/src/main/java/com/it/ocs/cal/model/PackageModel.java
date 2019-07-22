package com.it.ocs.cal.model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class PackageModel implements Serializable {
	private int lqty;
	private int wqty;
	private int hqty;
	
	private Double fLength;
	private Double fWidth;
	private Double fHeight;
	private Double weight = 0d;
	
	private Double baseWeight  = 0d;
	private int qty = 1;//包装数量
	private SKUModel sku ;//sku信息
	private String countryId;
	private Double fba;
	
	
	
	public Double getfLength() {
		return fLength;
	}
	public void setfLength(Double fLength) {
		this.fLength = fLength;
	}
	public Double getfWidth() {
		return fWidth;
	}
	public void setfWidth(Double fWidth) {
		this.fWidth = fWidth;
	}
	public Double getfHeight() {
		return fHeight;
	}
	public void setfHeight(Double fHeight) {
		this.fHeight = fHeight;
	}
	
	
	
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public boolean isOk(){
		return lqty*wqty*hqty == qty;
	}
	
	public int getLqty() {
		return lqty;
	}

	public void setLqty(int lqty) {
		this.lqty = lqty;
	}

	public int getWqty() {
		return wqty;
	}

	public void setWqty(int wqty) {
		this.wqty = wqty;
	}

	public int getHqty() {
		return hqty;
	}

	public void setHqty(int hqty) {
		this.hqty = hqty;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.weight = format(this.baseWeight *qty);
		this.qty = qty;
	}

	public SKUModel getSku() {
		return sku;
	}

	public void setSku(SKUModel sku) {
		this.fLength = format(lqty * sku.getLength());
		this.fWidth =  format(wqty * sku.getWidth());
		this.fHeight = format(hqty * sku.getHeight());
		this.weight = format(this.qty * sku.getGw());
		this.baseWeight = format(sku.getGw());
		this.sku = sku;
	}
	
	public Double getFba() {
		return fba;
	}
	public void setFba(Double fba) {
		this.fba = fba;
	}
	
	public static Double format(Double b){
		DecimalFormat df = new DecimalFormat("0.000000000");
		return new Double(df.format(b));
	}
	
	public static void main(String[] args) {
		
		Double a = 0.145;
		System.out.println(a*3);
		System.out.println(format(a*3));
	}
	
}
