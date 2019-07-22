package com.it.ocs.ukinventory.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.ukinventory.model.InventoryFlowUKModel;

public interface IInventoryFlowUKDAO extends IBaseDAO<InventoryFlowUKModel> {
	public int batchInsert(List<Map<String, Object>> rows);
	
	public int add(@Param(value="param") Map<String,Object> map);
	
	public List<Map<String,Object>> queryForSummary();
	
	public List<Map<String,Object>> getUnSyncRefound();
	
	public int valicount(@Param(value="param") Map<String,Object> param);
	
	public int updateUKRefound();
}
