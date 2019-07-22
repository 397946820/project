package com.it.ocs.salesStatistics.dao;

import java.util.List;
import java.util.Map;

import com.it.ocs.salesStatistics.model.NoShipSKUModel;

public interface NoShipOrderDao {

	public List<Map<String, Object>> getUKNoShipOrderInfo();
	
	public List<Map<String, Object>> getLightUKNoShipOrders();

	public List<Map<String, Object>> getAmazonUKNoShipOrders();

	public List<NoShipSKUModel> getSkusByPSku(String psku);

	public NoShipSKUModel getSkusWeight(String sku);

	public List<Map<String, Object>> getDENoShipOrderInfo();

	public NoShipSKUModel getSku(String psku);

	public List<Map<String, Object>> getOSOrderNoShip();

	public Map<String, Object> getEBayOrderInfo(Map<String, String> orderParam);

	public List<Map<String, Object>> getUSWOrder();

	public String getWOrderNewAddress(Map<String, Object> paramMap);

	public List<Map<String, Object>> getSmallPackageOrder();

	public List<Map<String, Object>> getSmallPackageOrderNew();

	public String getLightWOrderNewAddress(Map<String, Object> paramMap);

	public String getAmazonWOrderNewAddress(Map<String, Object> paramMap);

}
