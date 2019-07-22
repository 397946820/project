package com.it.ocs.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.payment.service.IEbayPaymentService;
import com.it.ocs.payment.vo.EbayPaymentVO;

@Controller
@RequestMapping(value = "/payment")
public class PaymentController {
	@Autowired
	private IEbayPaymentService paymentService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<EbayPaymentVO> list(RequestParam param) {
		ResponseResult<EbayPaymentVO> paymentPageResult = paymentService.queryPayment(param);
        return paymentPageResult;
    }
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult savePayment(EbayPaymentVO param) {
		return paymentService.savePayment(param);
	}
	
	@RequestMapping(value = "/remove",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult removePayment(Long id) {
		return paymentService.removePayment(id);
	}
	
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index() {
		return "admin/ebayPayment/list";
	}
	@RequestMapping(value = "/removeAll",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult removeAll(String ids) {
		return paymentService.removePayments(ids);
	}
}
