package com.it.ocs.salesStatistics.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.salesStatistics.model.LightShipmentModel;
import com.it.ocs.salesStatistics.vo.LightShipmentVo;

public interface ILightShipmentDao {

	void add(Map<String, Object> map);

	void update(Map<String, Object> map);

	List<LightShipmentModel> getLightShipsByOrderId(String orderId);

	String getShipmentIncrementIdByOrderId(String orderId);

	LightShipmentModel getByObject(@Param(value = "param") LightShipmentVo shipment);

}
