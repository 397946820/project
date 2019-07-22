package com.it.ocs.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.sys.model.OperatingProfitNewSkuModel;
import com.it.ocs.sys.model.OperatingProfitSkuModel;

@Repository
public interface IOperatingProfitSkuDao extends IBaseDao<OperatingProfitSkuModel> {

	void addNewSku(Map<String, Object> map);

	OperatingProfitNewSkuModel queryNewSkuByParam(@Param(value = "param") OperatingProfitNewSkuModel model);

	void updateNewSku(Map<String, Object> map);

	List<Map<String, Object>> queryPrice(@Param(value = "flag") Double flag);

	void updatePriceS(Map<String, Object> map);
	
	void updatePriceH(Map<String, Object> map);

}
