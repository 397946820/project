package com.it.ocs.cal.dao;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.TaxModel;

public interface ITaxDao extends IBaseDao<TaxModel> {

	Double queryClearCoefficient(String country);

}
