package com.it.ocs.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.common.RequestParam;
import com.it.ocs.sys.model.OperatingProfitViewModel;
import com.it.ocs.sys.vo.OperatingProfitViewVo;

@Repository
public interface IOperatingProfitViewDao extends IBaseDao<OperatingProfitViewModel> {

	List<Map<String, Object>> getCategoryByCode(@Param(value = "code") String code,
			@Param(value = "userId") Long userId);

	List<Map<String, Object>> getSiteByCode(@Param(value = "code") String code, @Param(value = "userId") Long userId);

	List<Map<String, Object>> getSkuByCategory(@Param(value = "categorys") Object categorys);

	List<Map<String, Object>> getPersonnelByDepartment(@Param(value = "department") String department);

	List<OperatingProfitViewModel> findAll(@Param(value = "param") Map<String, Object> param,
			@Param(value = "sort") String sort, @Param(value = "order") String order);

	Integer refreshBefore();

	void refresh();

	void sure();

	OperatingProfitViewVo getFooter(@Param(value = "param") Map<String, Object> map);

	List<Map<String, Object>> exportBackups(@Param(value = "type") String type,
			@Param(value = "fromDate") String fromDate, @Param(value = "toDate") String toDate);

	List<Map<String, Object>> exportData(@Param(value = "code") String code, @Param(value = "userId") Long userId,
			@Param(value = "fromDate") String fromDate, @Param(value = "toDate") String toDate);

	String exportBefore(Map<String, Object> map);

	int generateDataBefore();

	void generateData();

	int mappingSku();

	int categorySku();

	void syncCategory();

	List<Map<String, Object>> exportSku(@Param(value = "type") String type);

	int getCount(@Param(value = "param") Map<String, Object> map);

	List<Map<String, Object>> queryAmazonFee(@Param(value = "param") Map<String, Object> param,
			@Param(value = "startRow") int startRow, @Param(value = "endRow") int endRow);

	List<Map<String, Object>> getAmazonFeeFooter(@Param(value = "param") Map<String, Object> param);

	List<Map<String, Object>> exportAmazonData(@Param(value = "type") String type,
			@Param(value = "fromDate") String fromDate, @Param(value = "toDate") String toDate);
}
