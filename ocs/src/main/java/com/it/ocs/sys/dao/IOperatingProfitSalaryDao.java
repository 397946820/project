package com.it.ocs.sys.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.sys.model.OperatingProfitSalaryModel;

@Repository
public interface IOperatingProfitSalaryDao extends IBaseDao<OperatingProfitSalaryModel> {

	Map<String, Object> findByParam(@Param(value = "param") OperatingProfitSalaryModel operatingProfitSalaryModel);

	Map<String, Object> getByParam(@Param(value = "param") OperatingProfitSalaryModel operatingProfitSalaryModel);

}
