package com.it.ocs.cal.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.LightEbaySundryModel;

@Repository
public interface ILightEbaySundryDao extends IBaseDao<LightEbaySundryModel> {

	LightEbaySundryModel queryByCountry(@Param("country")String country);

}
