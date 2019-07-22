package com.it.ocs.fourPX.vo;

import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.fourPX.model.FpxOutWarehouseModel;

/**
 * 4PX出库单信息Value-Object
 * @author zhouyancheng
 *
 */
public class FpxOutWarehouseVO extends FpxOutWarehouseModel implements java.io.Serializable {

	private static final long serialVersionUID = -2894100733144410013L;
	
	private Long detailId;
	@ExcelLink(title = "SKU", index = 20)
	private String sku;
	@ExcelLink(title = "SKU数量", index = 21)
	private Integer quantity;
	private String skuLabelCode;
	private String skuValue;
	
	private Long consigneeId;
	@ExcelLink(title = "客户名", index = 30)
	private String fullName;
	@ExcelLink(title = "国家代码", index = 31)
	private String countryCode;
	@ExcelLink(title = "街道", index = 32)
	private String street;
	@ExcelLink(title = "城市", index = 33)
	private String city;
	@ExcelLink(title = "省份", index = 34)
	private String state;
	@ExcelLink(title = "邮编", index = 35)
	private String postalCode;
	@ExcelLink(title = "Email", index = 36)
	private String email;
	@ExcelLink(title = "电话", index = 37)
	private String phone;
	@ExcelLink(title = "公司", index = 38)
	private String company;
	@ExcelLink(title = "门牌号", index = 39)
	private String doorplate;
	@ExcelLink(title = "身份证", index = 40)
	private String cardId;
	@ExcelLink(title = "异常信息", index = 41)
	private String abnormal;

	public FpxOutWarehouseVO() {
		
	}

	public FpxOutWarehouseVO(FpxOutWarehouseModel model) {
		super(model);
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String getSkuLabelCode() {
		return skuLabelCode;
	}

	public void setSkuLabelCode(String skuLabelCode) {
		this.skuLabelCode = skuLabelCode;
	}

	public String getSkuValue() {
		return skuValue;
	}

	public void setSkuValue(String skuValue) {
		this.skuValue = skuValue;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Long getConsigneeId() {
		return consigneeId;
	}

	public void setConsigneeId(Long consigneeId) {
		this.consigneeId = consigneeId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDoorplate() {
		return doorplate;
	}

	public void setDoorplate(String doorplate) {
		this.doorplate = doorplate;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(String abnormal) {
		this.abnormal = abnormal;
	}
}
