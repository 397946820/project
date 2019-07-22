package com.it.ocs.payment.dao;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.payment.model.EbayPaymentModel;
@Repository
public interface IEbayPaymentDao  extends IBaseDAO<EbayPaymentModel> {
	
}