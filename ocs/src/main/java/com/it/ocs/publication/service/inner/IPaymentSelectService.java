package com.it.ocs.publication.service.inner;

import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.vo.PaymentVO;

public interface IPaymentSelectService {
	
	public ResponseResult<PaymentVO> queryPayment(RequestParam param);

	public OperationResult paymentSaveAs(Map<String, Object> map);

	public OperationResult payNameIsExist(String payName);
}
