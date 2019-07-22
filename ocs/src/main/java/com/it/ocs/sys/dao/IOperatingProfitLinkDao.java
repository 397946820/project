package com.it.ocs.sys.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.sys.model.OperatingProfitLinkModel;

@Repository
public interface IOperatingProfitLinkDao extends IBaseDao<OperatingProfitLinkModel> {

	int getCount(@Param(value = "parentId") Object parentId, @Param(value = "country") String country);

	Long getUserIdByNick(String nick);

	void updateCountry(Map<String, Object> map);

	void addCountry(Map<String, Object> map);

}
