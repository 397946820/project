package com.it.ocs.publication.service.inner.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.payment.model.EbayPaymentModel;
import com.it.ocs.payment.vo.EbayPaymentVO;
import com.it.ocs.publication.dao.IPaymentDAO;
import com.it.ocs.publication.model.PaymentModel;
import com.it.ocs.publication.service.inner.IPaymentSelectService;
import com.it.ocs.publication.service.inner.IPublicationAddService;
import com.it.ocs.publication.vo.PaymentVO;
import com.it.ocs.publication.vo.PublicationSaveVO;

@Service
public class PaymentSelectService extends BaseService implements IPaymentSelectService {
	
	@Autowired
	private IPaymentDAO iPaymentDAO;
	

	@Override
	public ResponseResult<PaymentVO> queryPayment(RequestParam param) {
		ResponseResult<PaymentVO> result = new ResponseResult<>();
		PaymentVO paymentVO = BeanConvertUtil.mapToObject(param.getParam(), PaymentVO.class);
		List<PaymentModel> payments = iPaymentDAO.queryByPage(paymentVO,param.getStartRow(),param.getEndRow());
		List<PaymentVO> paymentVOs = new ArrayList<>();
		convertList(payments, paymentVOs);
		int count = iPaymentDAO.count(paymentVO);
		result.setRows(paymentVOs);
		result.setTotal(count);
		return result;
	}
	
	private void convertList(List<PaymentModel> source, final List<PaymentVO> target) {
		CollectionUtil.each(source, new IAction<PaymentModel>() {
			@Override
			public void excute(PaymentModel obj) {
				PaymentVO paymentVO = new PaymentVO();
				BeanUtils.copyProperties(obj, paymentVO);
				target.add(paymentVO);
			}
		});
	}

	@Override
	public OperationResult paymentSaveAs(Map<String, Object> map) {
		OperationResult operationResult = new OperationResult();
		PaymentModel paymentModel = BeanConvertUtil.mapToObject2(map, PaymentModel.class);
		super.insertInit(paymentModel);
		paymentModel.setId(iPaymentDAO.getId());
		iPaymentDAO.add(paymentModel);
		operationResult.setData(paymentModel);
		return operationResult;
	}

	@Override
	public OperationResult payNameIsExist(String payName) {
		OperationResult operationResult = new OperationResult();
		Integer count = iPaymentDAO.payNameIsExist(payName);
		operationResult.setData(count);
		return operationResult;
	}
	
	
}
