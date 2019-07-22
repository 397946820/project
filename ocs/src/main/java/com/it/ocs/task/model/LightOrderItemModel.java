package com.it.ocs.task.model;

import java.sql.Timestamp;

import com.it.ocs.common.model.BaseModel;

public class LightOrderItemModel extends BaseLightItemModel {
	
	private Long entity_id;
	private Long parent_id;
	private Double weight;
	private String sku;
	private String name;
	private Long free_shipping;
	private Long is_qty_decimal;
	private Long no_discount;
	private Double qty_canceled;
	private Double qty_invoiced;
	private Double qty_ordered;
	private Double qty_refunded;
	private Double qty_shipped;
	private Double price;
	private Double base_price;
	private Double original_price;
	private Double tax_percent;
	private Double tax_amount;
	private Double tax_invoiced;
	private Double discount_percent;
	private Double discount_amount;
	private Double amount_refunded;
	private Double row_total;
	private Long gift_message_id;
	private String postcode;
	private String region;
	private String city;
	private String street;
	private Long gift_message;
	private Timestamp light_created_at;
	private Timestamp light_updated_at;
	private Timestamp ship_at;
	private Long push_status;
	private Timestamp created_at;
	private Timestamp updated_at;
	
	public Long getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(Long entity_id) {
		this.entity_id = entity_id;
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getFree_shipping() {
		return free_shipping;
	}
	public void setFree_shipping(Long free_shipping) {
		this.free_shipping = free_shipping;
	}
	public Long getIs_qty_decimal() {
		return is_qty_decimal;
	}
	public void setIs_qty_decimal(Long is_qty_decimal) {
		this.is_qty_decimal = is_qty_decimal;
	}
	public Long getNo_discount() {
		return no_discount;
	}
	public void setNo_discount(Long no_discount) {
		this.no_discount = no_discount;
	}
	public Double getQty_canceled() {
		return qty_canceled;
	}
	public void setQty_canceled(Double qty_canceled) {
		this.qty_canceled = qty_canceled;
	}
	public Double getQty_invoiced() {
		return qty_invoiced;
	}
	public void setQty_invoiced(Double qty_invoiced) {
		this.qty_invoiced = qty_invoiced;
	}
	public Double getQty_ordered() {
		return qty_ordered;
	}
	public void setQty_ordered(Double qty_ordered) {
		this.qty_ordered = qty_ordered;
	}
	public Double getQty_refunded() {
		return qty_refunded;
	}
	public void setQty_refunded(Double qty_refunded) {
		this.qty_refunded = qty_refunded;
	}
	public Double getQty_shipped() {
		return qty_shipped;
	}
	public void setQty_shipped(Double qty_shipped) {
		this.qty_shipped = qty_shipped;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getBase_price() {
		return base_price;
	}
	public void setBase_price(Double base_price) {
		this.base_price = base_price;
	}
	public Double getOriginal_price() {
		return original_price;
	}
	public void setOriginal_price(Double original_price) {
		this.original_price = original_price;
	}
	public Double getTax_percent() {
		return tax_percent;
	}
	public void setTax_percent(Double tax_percent) {
		this.tax_percent = tax_percent;
	}
	public Double getTax_amount() {
		return tax_amount;
	}
	public void setTax_amount(Double tax_amount) {
		this.tax_amount = tax_amount;
	}
	public Double getTax_invoiced() {
		return tax_invoiced;
	}
	public void setTax_invoiced(Double tax_invoiced) {
		this.tax_invoiced = tax_invoiced;
	}
	public Double getDiscount_percent() {
		return discount_percent;
	}
	public void setDiscount_percent(Double discount_percent) {
		this.discount_percent = discount_percent;
	}
	public Double getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(Double discount_amount) {
		this.discount_amount = discount_amount;
	}
	public Double getAmount_refunded() {
		return amount_refunded;
	}
	public void setAmount_refunded(Double amount_refunded) {
		this.amount_refunded = amount_refunded;
	}
	public Double getRow_total() {
		return row_total;
	}
	public void setRow_total(Double row_total) {
		this.row_total = row_total;
	}
	public Long getGift_message_id() {
		return gift_message_id;
	}
	public void setGift_message_id(Long gift_message_id) {
		this.gift_message_id = gift_message_id;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
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
	public Long getGift_message() {
		return gift_message;
	}
	public void setGift_message(Long gift_message) {
		this.gift_message = gift_message;
	}
	public Timestamp getLight_created_at() {
		return light_created_at;
	}
	public void setLight_created_at(Timestamp light_created_at) {
		this.light_created_at = light_created_at;
	}
	public Timestamp getLight_updated_at() {
		return light_updated_at;
	}
	public void setLight_updated_at(Timestamp light_updated_at) {
		this.light_updated_at = light_updated_at;
	}
	public Timestamp getShip_at() {
		return ship_at;
	}
	public void setShip_at(Timestamp ship_at) {
		this.ship_at = ship_at;
	}
	public Long getPush_status() {
		return push_status;
	}
	public void setPush_status(Long push_status) {
		this.push_status = push_status;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	
}
