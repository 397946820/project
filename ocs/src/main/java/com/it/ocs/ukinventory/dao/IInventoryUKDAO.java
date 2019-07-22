package com.it.ocs.ukinventory.dao;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.ukinventory.model.InventoryUKModel;

public interface IInventoryUKDAO extends IBaseDAO<InventoryUKModel> {
	public int batchInsert(List<Map<String, Object>> rows);
}
