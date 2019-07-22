package com.it.ocs.amazon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IInboundShipmentDAO extends IAmazonReportBaseDAO {
	public void add(@Param(value = "data") Map<String, Object> data);

	public void batchAdd(List<Map<String, Object>> list);

	public Map<String, Object> getByShipmentId(@Param(value = "shipmentId") String shipmentId);

	public List<Map<String, Object>> getByShipmentIds(@Param(value = "list") List<String> shimentIds);

	public Map<String, Object> getLastUpdate();

	public int update(@Param(value = "data") Map<String, Object> data);
}
