package com.it.ocs.cal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.SmallTypeItemModel;

@Repository
public interface ISmallTypeItemDao extends IBaseDao<SmallTypeItemModel> {

	List<Map<String, Object>> getTypeName();

	List<Map<String, Object>> getShippingTypeByTypeName(String typeName);

}
