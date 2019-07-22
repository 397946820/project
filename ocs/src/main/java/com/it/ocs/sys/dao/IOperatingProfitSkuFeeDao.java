package com.it.ocs.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.sys.model.OperatingProfitSkuFeeModel;

@Repository
public interface IOperatingProfitSkuFeeDao extends IBaseDao<OperatingProfitSkuFeeModel> {

	String getSkuBySku(String sku);

	List<Map<String, Object>> noParticipationPnOperation();

}
