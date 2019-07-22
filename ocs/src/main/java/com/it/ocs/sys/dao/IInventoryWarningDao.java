package com.it.ocs.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.amazon.model.MyiUnsuppressedVO;
import com.it.ocs.salereport.model.InventoryWarningModel;
import com.it.ocs.salereport.model.PlanBaseModel;

public interface IInventoryWarningDao {

	public int countInventoryWarning(@Param("param")Map<String, Object> map);

	public List<InventoryWarningModel> queryInventoryWarningByPage(@Param("param")Map<String, Object> map, @Param("start")int startRow, @Param("end")int endRow);
	
	public void addInventoryWarning(InventoryWarningModel iwm);

	public String isOversize(Map<String, String> map);

	public PlanBaseModel getPlanTimeBaseByCode(String scode_ship_oversize);

	public Double getWarnCoeff(Map<String, String> map);

	public int getUS01SaleQtyBySku(Map<String, Object> param);

	public int getUS02SaleQtyBySku(Map<String, Object> param);

	public int getDE01SaleQtyBySku(Map<String, Object> param);

	public int getDE02SaleQtyBySku(Map<String, Object> param);

	public int getUK01SaleQtyBySku(Map<String, Object> param);

	public int getUK02SaleQtyBySku(Map<String, Object> param);

	public int getJP01SaleQtyBySku(Map<String, Object> param);

	public int getCA01SaleQtyBySku(Map<String, Object> param);

	public MyiUnsuppressedVO getUSInventory(Map<String, Object> param);

	public int countBadDay(Map<String, Object> map);

	public Integer getSKUDis(Map<String, String> map);

	public Integer getBadDayOfLastMonth(Map<String, Object> param);

	public void clearInventoryWarnDataThisDay();

	public void updateErpData();

	public int countInventoryWarningSku();

	public List<InventoryWarningModel> queryInventoryWarningSKUByPage(@Param("start")int i, @Param("end")int j);

	public MyiUnsuppressedVO getUKInventory(Map<String, Object> param);

	public MyiUnsuppressedVO getWMSInventory(Map<String, Object> param);

	public int countInventoryWarningTime(Map<String, Object> map);

	public void updateInventoryWarningTime(Map<String, Object> map);

	public void addInventoryWarningTime(Map<String, Object> map);

	public int countInventoryWarningCoeff(Map<String, Object> map);

	public void updateInventoryWarningCoeff(Map<String, Object> map);

	public void addInventoryWarningCoeff(Map<String, Object> map);

	public List<Map<String, Object>> queryInventoryWarning(@Param("param")Map<String, Object> map);

	public int inventorySetExist(Map<String, Object> row);

	public void addInventoryWarningSet(Map<String, Object> row);

	public void updateInventoryWarningSet(Map<String, Object> row);
}
