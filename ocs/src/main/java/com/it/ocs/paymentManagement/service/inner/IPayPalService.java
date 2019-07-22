package com.it.ocs.paymentManagement.service.inner;

import com.it.ocs.common.OperationResult;
import com.it.ocs.paymentManagement.model.PayPalRefundModel;

public interface IPayPalService {

	
	public OperationResult payPalRefund(PayPalRefundModel palRefundModel);
}
