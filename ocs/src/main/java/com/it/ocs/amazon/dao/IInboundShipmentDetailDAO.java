package com.it.ocs.amazon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IInboundShipmentDetailDAO {
	public List<Map<String,String>> getShipmentIds(@Param(value = "platform") String platform);

	public int add(Map<String, Object> map);
	
	public int update(Map<String,Object> map);

	public Map<String, Object> getShipIdSku(@Param(value = "shipmentId") String shipmentId,
			@Param(value = "sku") String sku);

}
