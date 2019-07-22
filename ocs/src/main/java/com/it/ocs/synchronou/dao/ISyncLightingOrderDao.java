package com.it.ocs.synchronou.dao;

import java.util.List;
import java.util.Map;

import com.it.ocs.synchronou.model.LightAccountModel;

public interface ISyncLightingOrderDao {

	public List<LightAccountModel> getLightAccount();
	
	public LightAccountModel getLightAccountByPlatform(String platform);

	public Integer getIdByOrderId(Map<String, Object> map);
	
	public int getId();

	public void insertOrderData(Map<String, Object> map);

	public void updateOrderData(Map<String, Object> map);

	public Integer getIdByOrderItemId(Map<String, Object> map);

	public void insertOrderItemData(Map<String, Object> map);

	public int getOrderItemId();

	public void updateOrderItemData(Map<String, Object> map);

	public String getLastUpdateDate(String platform);

	public String getTimeSetStartTime(String platform);

	public int getTimeSetId();

	public void addTimeSet(Map<String, Object> timeSetMap);

	public void updateTimeSet(Map<String, Object> timeSetMap);

	public Integer getIdByOrderItemIdForBSKU(Map<String, Object> map);

	public void insertOrderItemDataForBSKU(Map<String, Object> map);

	public void updateOrderItemDataForBSKU(Map<String, Object> map);

	public int hasOldAdd(Map<String, Object> map);

	public int countBySKU(Map<String, Object> map);

	public int orderSkuHasExist(Map<String, Object> map);
}
