package com.it.ocs.cal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.LightEbayTaxModel;

@Repository
public interface ILightEbayTaxDao extends IBaseDao<LightEbayTaxModel> {

	/**
	 * 根据国家和sku查找
	 * 
	 * @param country
	 * @param sku
	 * @return
	 */
	public LightEbayTaxModel queryByCountryAndSku(@Param("country") String country, @Param("sku") String sku);

	public List<Map<String, Object>> findByUserId(long userId);

}
