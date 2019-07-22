package com.it.ocs.eda.model;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ShipOrderInfo implements Cloneable {
	private String platform;
	private String account;
	private String orderOCSId;
	private String orderId;
	private String itemId;
	private String edaAccount;
	private String edaOrderId;
	private String shippingMethod;
	private String shippingAddress;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String country;
	private String name;
	private String phone;
	private String postalCode;
	private String provState;
	private String orderItemOCSId;
	private String warehouseSku;
	private Integer quantity;
	private String unitPrice;
	private String productId;
	private String description;
	private String email;
	private String createJsonStr;
	private String items;
	private String createTime;
	// sku 的长宽高
	private Double width;
	private Double length;
	private Double height;
	private Double weight;
	
	private String newAddress;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getOrderOCSId() {
		return orderOCSId;
	}

	public void setOrderOCSId(String orderOCSId) {
		this.orderOCSId = orderOCSId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getEdaAccount() {
		return edaAccount;
	}

	public void setEdaAccount(String edaAccount) {
		this.edaAccount = edaAccount;
	}

	public String getEdaOrderId() {
		return edaOrderId;
	}

	public void setEdaOrderId(String edaOrderId) {
		this.edaOrderId = edaOrderId;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getShippingAddress() {
		// { "addressLine1" : "P.o. box 1464", "addressLine2" : "", "city" :
		// "kurtistown", "country" : "USA", "name" : "janelle glorioso", "phone"
		// : "8082175122", "postalCode" : "96760", "provState" : "HI"}
		if(null != this.newAddress && !"".equals(this.newAddress)){
			return this.newAddress;
		}
		JSONObject json = new JSONObject();
		json.put("addressLine1", addressLine1);
		json.put("addressLine2", addressLine2);
		json.put("city", city);
		json.put("country", formatCountry(country));
		json.put("name", name);
		json.put("phone", phone);
		json.put("postalCode", postalCode);
		json.put("provState", provState);
		return json.toString();
	}

	public String formatCountry(String str) {
		if ("USA".equals(str)) {
			return "US";
		}
		return str;
	}

	public void setShippingAddress(String shippingAddress) {
		JSONObject json = JSONObject.fromObject(shippingAddress);
		this.name = json.getString("name");
		this.addressLine1 = json.getString("addressLine1");
		this.addressLine2 = json.getString("addressLine2");
		this.city = json.getString("city");
		this.country = json.getString("country");
		this.provState = json.getString("provState");
		this.postalCode = json.getString("postalCode");
		this.phone = json.getString("phone");
		this.shippingAddress = shippingAddress;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getProvState() {
		return provState;
	}

	public void setProvState(String provState) {
		this.provState = provState;
	}

	public String getOrderItemOCSId() {
		return orderItemOCSId;
	}

	public void setOrderItemOCSId(String orderItemOCSId) {
		this.orderItemOCSId = orderItemOCSId;
	}

	public String getWarehouseSku() {
		return warehouseSku;
	}

	public void setWarehouseSku(String warehouseSku) {
		this.warehouseSku = warehouseSku;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateJsonStr() {
		return createJsonStr;
	}

	public void setCreateJsonStr(String createJsonStr) {
		this.createJsonStr = createJsonStr;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getItems() {
		return items;
	}

	public void setItems(JSONArray items) {
		this.items = items.toString();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		if (null == weight) {
			weight = 0d;
		}
		this.weight = weight;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public JSONObject getItemInfo() {
		// [{ "warehouseSku" : "3600006", "quantity" : 1, "unitPrice" : "",
		// "productId" : "", "description" : ""}]
		JSONObject json = new JSONObject();
		json.put("warehouseSku", warehouseSku);
		json.put("quantity", quantity);
		json.put("unitPrice", unitPrice);
		json.put("productId", productId);
		json.put("description", description);
		return json;
	}

	@Override
	public Object clone() {
		ShipOrderInfo stu = null;
		try {
			stu = (ShipOrderInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return stu;
	}

	public String getNewAddress() {
		return newAddress;
	}

	public void setNewAddress(String newAddress) {
		this.newAddress = newAddress;
	}
	
	

}
