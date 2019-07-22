package com.it.ocs.salesStatistics.model;

import java.util.Date;

import com.it.ocs.cal.model.ImportModel;
import com.it.ocs.cal.utils.ExcelRead;
import com.it.ocs.cal.utils.ModelProp;

public class SalesStatisticsModel extends ImportModel {

	@ExcelRead(title = "sku")
	@ModelProp(name = "sku", colIndex = 11, nullable = false)
	private String sku;

	@ExcelRead(title = "Asin")
	@ModelProp(name = "asin", colIndex = 12, nullable = false)
	private String asin;

	@ExcelRead(title = "账号")
	private String platform;

	@ExcelRead(title = "站点")
	@ModelProp(name = "sales-channel", colIndex = 6, nullable = false)
	private String station;

	private String account;

	@ExcelRead(title = "订单数量")
	@ModelProp(name = "quantity", colIndex = 14, nullable = false)
	private int qty;

	@ExcelRead(title = "币种")
	@ModelProp(name = "currency", colIndex = 15, nullable = false)
	private String currencycode;

	@ExcelRead(title = "折扣额")
	private double deduction;

	@ExcelRead(title = "税额")
	private double taxrate;

	@ExcelRead(title = "金额（含税）")
	@ModelProp(name = "item-price", colIndex = 16, nullable = false)
	private double price;

	@ExcelRead(title = "金额(含税)")
	private double priceTax;

	@ExcelRead(title = "金额(不含税)")
	private double priceExcludingTax;

	@ExcelRead(title = "总数")
	private int count;

	// 同比
	@ExcelRead(title = "同比")
	private double sametermrate;
	// 周环比
	@ExcelRead(title = "周环比")
	private double weekrate;
	// 月环比
	@ExcelRead(title = "月环比")
	private double monthrate;

	@ExcelRead(title = "订单状态")
	@ModelProp(name = "order-status", colIndex = 4, nullable = false)
	private String status;

	// af
	@ExcelRead(title = "af毛利额(15%)")
	private Double af;
	// sf
	@ExcelRead(title = "sf毛利额(22%)")
	private Double sf;
	// co
	@ExcelRead(title = "co毛利额(15%)")
	private Double co;
	// af利润率
	@ExcelRead(title = "af利润率")
	private Double afRate;
	// sf利润率
	@ExcelRead(title = "sf利润率")
	private Double sfRate;
	// co利润率
	@ExcelRead(title = "co利润率")
	private Double coRate;
	// af
	@ExcelRead(title = "af总毛利额(美元)")
	private Double totalAf;
	// sf
	@ExcelRead(title = "sf总毛利额(美元)")
	private Double totalSf;
	// co
	@ExcelRead(title = "co总毛利额(美元)")
	private Double totalCo;
	@ExcelRead(title = "af总毛润率")
	private Double totalAfRate;
	@ExcelRead(title = "sf总毛润率")
	private Double totalSfRate;
	@ExcelRead(title = "co总毛润率")
	private Double totalCoRate;

	// 真实售价
	@ExcelRead(title = "真实售价")
	private Double actualSalesPrice;
	// 真实售价利润率
	@ExcelRead(title = "真实售价的利润率")
	private Double actualYield;
	// 实际利润系数
	@ExcelRead(title = "利润系数")
	private Double actualProfitCoefficient;

	private double unitprice;

	private int tempCount;

	private long parentId;

	// 购买时间
	private Date purchaseat;
	// 发货时间
	private Date lastestshipdate;
	// 更新时间
	private Date updatedat;
	// 支付时间
	private Date paidtime;
	// 最新拉取数据时间
	private Date lastfetchtime;
	// 创建时间
	private Date createdat;

	// 开始时间
	private Date fromtime;
	// 结束时间
	private Date totime;

	private String url;
	// 产品的图片
	private String productImg;

	@ModelProp(name = "amazon-order-id", colIndex = 0, nullable = false)
	private String orderId;

	private String title;

	private String customerName;

	private double amount;

	private String amountPaid;

	private String itemId;

	private String name;

	private String stateOrRegion;

	private String postalCode;

	private String phone;

	private String city;

	private String street;

	private Date itemUpdatedat;

	@ModelProp(name = "purchase-date", colIndex = 2, nullable = false)
	private String purchasedate;

	@ModelProp(name = "last-updated-date", colIndex = 3, nullable = false)
	private String updateddate;

	// 数据比对的时候导入用的
	@ModelProp(name = "item-tax", colIndex = 17, nullable = false)
	private double itemTax;

	@ModelProp(name = "shipping-price", colIndex = 18, nullable = false)
	private double shippingPrice;

	@ModelProp(name = "shipping-tax", colIndex = 19, nullable = false)
	private double shippingTax;

	@ModelProp(name = "gift-wrap-price", colIndex = 20, nullable = false)
	private double giftWrapPrice;

	@ModelProp(name = "gift-wrap-tax", colIndex = 21, nullable = false)
	private double giftWrapTax;

	@ModelProp(name = "item-promotion-discount", colIndex = 22, nullable = false)
	private double itemPromotionDiscount;

	@ModelProp(name = "ship-promotion-discount", colIndex = 23, nullable = false)
	private double shipPromotionDiscount;

	private double lastYearPrice;

	private double lastMonthPrice;

	private double lastWeekPrice;

	private String type;

	private String shipmentTrackingNumber;

	public String getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStateOrRegion() {
		return stateOrRegion;
	}

	public void setStateOrRegion(String stateOrRegion) {
		this.stateOrRegion = stateOrRegion;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Date getItemUpdatedat() {
		return itemUpdatedat;
	}

	public void setItemUpdatedat(Date itemUpdatedat) {
		this.itemUpdatedat = itemUpdatedat;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPurchasedate() {
		return purchasedate;
	}

	public void setPurchasedate(String purchasedate) {
		this.purchasedate = purchasedate;
	}

	public String getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(String updateddate) {
		this.updateddate = updateddate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin == null ? null : asin.trim();
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station == null ? null : station.trim();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}

	public double getDeduction() {
		return deduction;
	}

	public void setDeduction(double deduction) {
		this.deduction = deduction;
	}

	public double getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(double taxrate) {
		this.taxrate = taxrate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPriceTax() {
		return priceTax;
	}

	public void setPriceTax(double priceTax) {
		this.priceTax = priceTax;
	}

	public double getPriceExcludingTax() {
		return priceExcludingTax;
	}

	public void setPriceExcludingTax(double priceExcludingTax) {
		this.priceExcludingTax = priceExcludingTax;
	}

	public double getSametermrate() {
		return sametermrate;
	}

	public void setSametermrate(double sametermrate) {
		this.sametermrate = sametermrate;
	}

	public double getMonthrate() {
		return monthrate;
	}

	public void setMonthrate(double monthrate) {
		this.monthrate = monthrate;
	}

	public double getWeekrate() {
		return weekrate;
	}

	public void setWeekrate(double weekrate) {
		this.weekrate = weekrate;
	}

	public Date getPurchaseat() {
		return purchaseat;
	}

	public void setPurchaseat(Date purchaseat) {
		this.purchaseat = purchaseat;
	}

	public Date getLastestshipdate() {
		return lastestshipdate;
	}

	public void setLastestshipdate(Date lastestshipdate) {
		this.lastestshipdate = lastestshipdate;
	}

	public Date getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}

	public Date getPaidtime() {
		return paidtime;
	}

	public void setPaidtime(Date paidtime) {
		this.paidtime = paidtime;
	}

	public Date getLastfetchtime() {
		return lastfetchtime;
	}

	public void setLastfetchtime(Date lastfetchtime) {
		this.lastfetchtime = lastfetchtime;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public Date getFromtime() {
		return fromtime;
	}

	public void setFromtime(Date fromtime) {
		this.fromtime = fromtime;
	}

	public Date getTotime() {
		return totime;
	}

	public void setTotime(Date totime) {
		this.totime = totime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public double getItemTax() {
		return itemTax;
	}

	public void setItemTax(double itemTax) {
		this.itemTax = itemTax;
	}

	public double getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public double getShippingTax() {
		return shippingTax;
	}

	public void setShippingTax(double shippingTax) {
		this.shippingTax = shippingTax;
	}

	public double getGiftWrapPrice() {
		return giftWrapPrice;
	}

	public void setGiftWrapPrice(double giftWrapPrice) {
		this.giftWrapPrice = giftWrapPrice;
	}

	public double getGiftWrapTax() {
		return giftWrapTax;
	}

	public void setGiftWrapTax(double giftWrapTax) {
		this.giftWrapTax = giftWrapTax;
	}

	public double getItemPromotionDiscount() {
		return itemPromotionDiscount;
	}

	public void setItemPromotionDiscount(double itemPromotionDiscount) {
		this.itemPromotionDiscount = itemPromotionDiscount;
	}

	public double getShipPromotionDiscount() {
		return shipPromotionDiscount;
	}

	public void setShipPromotionDiscount(double shipPromotionDiscount) {
		this.shipPromotionDiscount = shipPromotionDiscount;
	}

	public Double getAf() {
		return af;
	}

	public void setAf(Double af) {
		this.af = af;
	}

	public Double getSf() {
		return sf;
	}

	public void setSf(Double sf) {
		this.sf = sf;
	}

	public Double getCo() {
		return co;
	}

	public void setCo(Double co) {
		this.co = co;
	}

	public Double getAfRate() {
		return afRate;
	}

	public void setAfRate(Double afRate) {
		this.afRate = afRate;
	}

	public Double getSfRate() {
		return sfRate;
	}

	public void setSfRate(Double sfRate) {
		this.sfRate = sfRate;
	}

	public Double getCoRate() {
		return coRate;
	}

	public void setCoRate(Double coRate) {
		this.coRate = coRate;
	}

	public Double getTotalAf() {
		return totalAf;
	}

	public void setTotalAf(Double totalAf) {
		this.totalAf = totalAf;
	}

	public Double getTotalSf() {
		return totalSf;
	}

	public void setTotalSf(Double totalSf) {
		this.totalSf = totalSf;
	}

	public Double getTotalCo() {
		return totalCo;
	}

	public void setTotalCo(Double totalCo) {
		this.totalCo = totalCo;
	}

	public Double getTotalAfRate() {
		return totalAfRate;
	}

	public void setTotalAfRate(Double totalAfRate) {
		this.totalAfRate = totalAfRate;
	}

	public Double getTotalSfRate() {
		return totalSfRate;
	}

	public void setTotalSfRate(Double totalSfRate) {
		this.totalSfRate = totalSfRate;
	}

	public Double getTotalCoRate() {
		return totalCoRate;
	}

	public void setTotalCoRate(Double totalCoRate) {
		this.totalCoRate = totalCoRate;
	}

	public Double getActualSalesPrice() {
		return actualSalesPrice;
	}

	public void setActualSalesPrice(Double actualSalesPrice) {
		this.actualSalesPrice = actualSalesPrice;
	}

	public Double getActualYield() {
		return actualYield;
	}

	public void setActualYield(Double actualYield) {
		this.actualYield = actualYield;
	}

	public Double getActualProfitCoefficient() {
		return actualProfitCoefficient;
	}

	public void setActualProfitCoefficient(Double actualProfitCoefficient) {
		this.actualProfitCoefficient = actualProfitCoefficient;
	}

	public double getLastYearPrice() {
		return lastYearPrice;
	}

	public void setLastYearPrice(double lastYearPrice) {
		this.lastYearPrice = lastYearPrice;
	}

	public double getLastMonthPrice() {
		return lastMonthPrice;
	}

	public void setLastMonthPrice(double lastMonthPrice) {
		this.lastMonthPrice = lastMonthPrice;
	}

	public double getLastWeekPrice() {
		return lastWeekPrice;
	}

	public void setLastWeekPrice(double lastWeekPrice) {
		this.lastWeekPrice = lastWeekPrice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTempCount() {
		return tempCount;
	}

	public void setTempCount(int tempCount) {
		this.tempCount = tempCount;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getShipmentTrackingNumber() {
		return shipmentTrackingNumber;
	}

	public void setShipmentTrackingNumber(String shipmentTrackingNumber) {
		this.shipmentTrackingNumber = shipmentTrackingNumber;
	}

}