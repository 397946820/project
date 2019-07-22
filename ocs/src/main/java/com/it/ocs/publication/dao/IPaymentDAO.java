package com.it.ocs.publication.dao;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.publication.model.PaymentModel;
@Repository
public interface IPaymentDAO extends IBaseDAO<PaymentModel> {

	public Integer payNameIsExist(String payName);

}
