package com.it.ocs.publication.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.service.inner.IPaymentSelectService;
import com.it.ocs.publication.vo.PaymentVO;
import com.it.ocs.publication.vo.PublicationPageVO;

/**
 * 选择支付方式
 * @author chenyong
 *
 */
@Controller
@RequestMapping("/paymentSelect")
public class PaymentSelectController {
	
	@Autowired
	private IPaymentSelectService paymentSelectService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<PaymentVO> list(RequestParam param) {
		return paymentSelectService.queryPayment(param);
    }
	
	@RequestMapping(value = "/saveAs",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult saveAs(@RequestBody Map<String, Object> map) {
		return paymentSelectService.paymentSaveAs(map);
	}
	
	@RequestMapping(value = "/payNameIsExist",method = RequestMethod.GET)
	@ResponseBody
	public OperationResult payNameIsExist(String payName){
		return paymentSelectService.payNameIsExist(payName);
	}
}
