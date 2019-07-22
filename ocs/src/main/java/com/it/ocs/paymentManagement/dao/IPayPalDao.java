package com.it.ocs.paymentManagement.dao;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.paymentManagement.model.PayPalModel;

@Repository
public interface IPayPalDao extends IBaseDAO<PayPalModel> {
	public PayPalModel getPayPalByEId(String ebayId);
	public PayPalModel getPayPalByPAccount(String account);
}
