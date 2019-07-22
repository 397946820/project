package com.it.ocs.cal.dao;

import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.CurrencyRateModel;

@Repository
public interface ICurrencyRateDao extends IBaseDao<CurrencyRateModel> {

	Double getCurrencyRateByCountry(String country);

}
