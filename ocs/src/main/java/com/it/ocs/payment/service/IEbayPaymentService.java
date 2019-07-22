package com.it.ocs.payment.service;


import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.payment.vo.EbayPaymentVO;

public interface IEbayPaymentService {
	public ResponseResult<EbayPaymentVO> queryPayment(RequestParam param);
	
	public OperationResult savePayment(EbayPaymentVO ebayPaymentVO);
	
	public OperationResult removePayment(Long payId);

	public OperationResult removePayments(String ids);
}
