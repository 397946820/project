package com.it.ocs.synchronou.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.synchronou.model.EBayShippingServiceDetailsModel;

public interface IEBayShippingServiceDetailsDao extends IBaseDAO<EBayShippingServiceDetailsModel> {
	
	public void insertShippingServiceDetails(List<EBayShippingServiceDetailsModel> eBayShippingServiceDetailsModels);
	
	public void updateShippingServiceDetails(List<EBayShippingServiceDetailsModel> eBayShippingServiceDetailsModels);
	
	public List<EBayShippingServiceDetailsModel> selectShippingServiceDetails(@Param("param")Map<String, Object> map, @Param("starRow") Integer starRow,@Param("endRow") Integer endRow);
	
	public int getTotal(@Param("param")Map<String, Object> map);

	public int isExist(String shippingId);

	public void addShipService(Map<String, Object> ship);

	public void updateShipService(@Param("param")Map<String, Object> ship);

	public EBayShippingServiceDetailsModel getShippingServiceById(int id);

	public void deleteShippingService(EBayShippingServiceDetailsModel ship);

	public void addOrUpdateShippingService(EBayShippingServiceDetailsModel ship);

	public int shippingLocationIsExist(Map<String, Object> ship);

	public void addShipLocation(Map<String, Object> ship);

	public void updateShipLocation(Map<String, Object> ship);
}
