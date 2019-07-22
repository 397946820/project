package com.it.ocs.fourPX.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.fourPX.model.FpxOutWarehouseConsigneeModel;
import com.it.ocs.fourPX.model.FpxOutWarehouseDetailModel;
import com.it.ocs.fourPX.model.FpxOutWarehouseModel;

public interface FpxOutWarehouseDao extends Base4pxDao<FpxOutWarehouseModel> {

	/**
	 * 查询将要使用4px服务的未发货源订单信息
	 * @param platform 源订单所属平台，传null代表不限定平台
	 */
	List<Map<String, Object>> selectUnshippedOrders(@Param("platform") String platform);
	
	/**
	 * 添加4px出库单明细信息
	 * @param detail
	 */
	void insertOutDetail(@Param("item") FpxOutWarehouseDetailModel detail);

	/**
	 * 添加4px出库单收货人信息
	 * @param consignee
	 */
	void insertConsignee(@Param("item") FpxOutWarehouseConsigneeModel consignee);

	Map<String, Object> load(@Param("id") Long id);
	
	List<Map<String, Object>> loadDetails(@Param("parentId") Long parentId);
	
	Map<String, Object> loadConsignee(@Param("parentId") Long parentId);

	void updateConsignee(@Param("item") FpxOutWarehouseConsigneeModel consignee);

	List<Map<String, Object>> selectExportData(@Param("item") Map<String, Object> param);
	
	List<Map<String, Object>> selectOfflineExportData(@Param("item") Map<String, Object> param);

	void updateShippingNumber(@Param("item") Map<String, Object> param);

	List<Long> selectPendingIds();

}
