package com.it.ocs.cal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.excel.vo.LECalculateExcelVO;
import com.it.ocs.cal.model.LightTaximeterModel;
import com.it.ocs.cal.vo.LightTaximeterVo;

@Repository
public interface ILightTaximeterDao extends IBaseDao<LightTaximeterModel> {

	void refresh(@Param(value = "sku") String sku, @Param(value = "userId") Long userId);

	List<LightTaximeterModel> queryByCountry(@Param(value = "param") LightTaximeterModel model,
			@Param(value = "list") List<String> countrys, @Param(value = "sort") String sort,
			@Param(value = "order") String order);
	
	List<Map<String, Object>> queryByObjectAndCountrys(@Param(value = "param") LightTaximeterVo model,
			@Param(value = "list") List<String> countrys);

	List<LightTaximeterModel> findAllLess();

	List<String> findAllSku();

	List<Map<String, Object>> queryCustomer();

	List<Map<String, Object>> getPriceTestData(@Param(value = "param") LECalculateExcelVO vo);

	List<Map<String, Object>> fobexw(String country);

}
