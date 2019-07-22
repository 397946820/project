package com.it.ocs.paymentManagement.service.inner.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.paymentManagement.dao.IPayPalDao;
import com.it.ocs.paymentManagement.model.PayPalModel;
import com.it.ocs.paymentManagement.model.PayPalRefundModel;
import com.it.ocs.paymentManagement.service.inner.IPayPalService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Capture;
import com.paypal.api.payments.Refund;
import com.paypal.api.payments.RefundRequest;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PayPalService extends BaseService implements IPayPalService {

	private static final Logger logger = Logger.getLogger(PayPalService.class);
	
	@Autowired
	private IPayPalDao payPalDao;
	@Override
	public OperationResult payPalRefund(PayPalRefundModel palRefundModel) {
		
		OperationResult result = new OperationResult();
		RefundRequest refundRequest = new RefundRequest();
		String account = palRefundModel.getPayPalAccount();
		PayPalModel palModel = payPalDao.getPayPalByPAccount(account);
		String mode = "";
		if(account.equalsIgnoreCase("941201063@qq.com")){
			mode="sandbox";
		}else{
			mode="live";
		}
		APIContext apiContext = new APIContext(palModel.getClient_id(), palModel.getSecret(), mode);
		Double total = palRefundModel.getRefundAmount();
		String currency = palRefundModel.getCurrency();
		Amount amount = new Amount(currency, total.toString());
		refundRequest.setAmount(amount);
		Capture capture;
		try {
			capture = Capture.get(apiContext, palRefundModel.getTransaction());
			Refund refund = capture.refund(apiContext, refundRequest);
			result.setDescription("操作成功！");
		} catch (PayPalRESTException e) {
			result.setDescription(e.getMessage());
			result.setErrorCode(1);
			logger.error("paypal付款失败", e);
			
		}
		return result;
	}

	
}
