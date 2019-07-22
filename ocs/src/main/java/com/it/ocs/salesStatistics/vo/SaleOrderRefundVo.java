package com.it.ocs.salesStatistics.vo;

import java.io.Serializable;

import org.omg.CORBA.PRIVATE_MEMBER;

public class SaleOrderRefundVo implements Serializable {

	private Long entityId;

	private String sku;

	private String mSku;

	private Double qtyOrdered;

	private Double rowTotal;

	private Long qty = 0L;

	private Long returnCost = 0L;

	private Long badProductQty = 0L;

	private String productCaseType;

	private Long useHourDay = 0L;

	private Long lineNumber;

	private String itemPriceCurrency;

	private String asin;

	private String url;

	private String transactionSiteId;

	private String cancellationType;

	private Long orderItemId;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getmSku() {
		return mSku;
	}

	public void setmSku(String mSku) {
		this.mSku = mSku;
	}

	public Double getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(Double qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public Double getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(Double rowTotal) {
		this.rowTotal = rowTotal;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Long getReturnCost() {
		return returnCost;
	}

	public void setReturnCost(Long returnCost) {
		this.returnCost = returnCost;
	}

	public Long getBadProductQty() {
		return badProductQty;
	}

	public void setBadProductQty(Long badProductQty) {
		this.badProductQty = badProductQty;
	}

	public String getProductCaseType() {
		return productCaseType;
	}

	public void setProductCaseType(String productCaseType) {
		this.productCaseType = productCaseType;
	}

	public Long getUseHourDay() {
		return useHourDay;
	}

	public void setUseHourDay(Long useHourDay) {
		this.useHourDay = useHourDay;
	}

	public Long getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getItemPriceCurrency() {
		return itemPriceCurrency;
	}

	public void setItemPriceCurrency(String itemPriceCurrency) {
		this.itemPriceCurrency = itemPriceCurrency;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTransactionSiteId() {
		return transactionSiteId;
	}

	public void setTransactionSiteId(String transactionSiteId) {
		this.transactionSiteId = transactionSiteId;
	}

	public String getCancellationType() {
		return cancellationType;
	}

	public void setCancellationType(String cancellationType) {
		this.cancellationType = cancellationType;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	@Override
	public String toString() {
		return "SaleOrderRefundVo [entityId=" + entityId + ", sku=" + sku + ", qtyOrdered=" + qtyOrdered + ", rowTotal="
				+ rowTotal + ", qty=" + qty + ", returnCost=" + returnCost + ", badProductQty=" + badProductQty
				+ ", productCaseType=" + productCaseType + ", useHourDay=" + useHourDay + ", lineNumber=" + lineNumber
				+ ", itemPriceCurrency=" + itemPriceCurrency + ", asin=" + asin + ", url=" + url
				+ ", transactionSiteId=" + transactionSiteId + ", cancellationType=" + cancellationType
				+ ", orderItemId=" + orderItemId + "]";
	}

}
