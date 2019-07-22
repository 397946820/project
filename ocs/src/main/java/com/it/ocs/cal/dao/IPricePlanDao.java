package com.it.ocs.cal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.cal.vo.PricePlanVo;
import com.it.ocs.common.RequestParam;
import com.it.ocs.publication.vo.ComboBoxVO;

@Repository("pricePlanDao")
public interface IPricePlanDao extends IBaseDao<PricePlanModel> {
	
	public Double getFinalPrice(@Param(value="param")Map<String,Object> param);
	
	public List<Map<String,Object>> getReturnRate(@Param(value="param")Map<String,Object> param);

	void insertPlanToTemp(@Param(value = "userId") Long userId);

	void deletePricePlan(@Param(value = "userId") Long userId);

	void addTemp(PricePlanModel model);

	List<PricePlanModel> findAllLessByUserId(@Param(value = "userId") Long userId,
			@Param(value = "status") String status);

	List<PricePlanModel> findAllByUserId(@Param(value = "userId") Long userId);

	List<PricePlanModel> queryBySkuAndCountry(@Param(value = "sku") String sku,
			@Param(value = "country") String country);

	PricePlanModel queryBySkuAndCountryAndType(@Param(value = "sku") String sku,
			@Param(value = "country") String country, @Param(value = "shippingType") String shippingType);

	List<PricePlanModel> query(@Param(value = "param") PricePlanModel param, @Param(value = "starRow") Integer starRow,
			@Param(value = "endRow") Integer endRow, @Param(value = "sort") String sort,
			@Param(value = "order") String order, @Param(value = "list") List<String> countrys);

	int total(@Param(value = "param") PricePlanVo pricePlan, @Param(value = "list") List<String> countrys,
			@Param(value = "flag") String string);

	void refresh(@Param(value = "sku") String sku, @Param(value = "userId") Long userId);

	List<PricePlanModel> findBySkus(@Param(value = "list") List<String> skus);

	List<Map<String, Object>> getPriceTestData(@Param(value = "param") PricePlanModel model);

	List<PricePlanModel> findBySkusAndStatus(@Param(value = "list") List<String> skus,
			@Param(value = "status") String status);

	List<String> findAllSkuByStatus(@Param(value = "status") String status);

	public List<ComboBoxVO> getProductField();

	public String getProductTypeBySku(String sku);

	public String getDISInfo(Map<String, Object> map);

	public Double getReturnRateBySkuAndCountry(@Param("param")Map<String, Object> map);

}
