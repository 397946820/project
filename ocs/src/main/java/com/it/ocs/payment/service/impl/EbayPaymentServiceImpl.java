package com.it.ocs.payment.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.it.ocs.payment.dao.IEbayPaymentDao;
import com.it.ocs.payment.model.EbayPaymentModel;
import com.it.ocs.payment.service.IEbayPaymentService;
import com.it.ocs.payment.vo.EbayPaymentVO;
@Service
public class EbayPaymentServiceImpl extends BaseService implements IEbayPaymentService {

	@Autowired
	private IEbayPaymentDao paymentDAO;

	@Override
	public ResponseResult<EbayPaymentVO> queryPayment(RequestParam param) {
		ResponseResult<EbayPaymentVO> result = new ResponseResult<>();
		EbayPaymentVO ebayPaymentVO = BeanConvertUtil.mapToObject(param.getParam(), EbayPaymentVO.class);
		List<EbayPaymentModel> payments = paymentDAO.queryByPage(ebayPaymentVO,param.getStartRow(),param.getEndRow());
		List<EbayPaymentVO> paymentVOs = new ArrayList<>();
		convertList(payments, paymentVOs);
		int count = paymentDAO.count(ebayPaymentVO);
		result.setRows(paymentVOs);
		result.setTotal(count);
		return result;
	}

	@Override
	public OperationResult savePayment(EbayPaymentVO ebayPaymentVO) {
		OperationResult result = new OperationResult();
		try {
			if (null != ebayPaymentVO.getPayid()) {
				updateInit(ebayPaymentVO);
				paymentDAO.update(ebayPaymentVO);
			} else {
				insertInit(ebayPaymentVO);
				paymentDAO.add(ebayPaymentVO);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("save error");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public OperationResult removePayment(Long payId) {
		OperationResult result = new OperationResult();
		try {
			EbayPaymentModel ebayPaymentModel  = new EbayPaymentModel();
			ebayPaymentModel.setEnabledFlag("N");
			ebayPaymentModel.setPayid(payId);
			updateInit(ebayPaymentModel);
			paymentDAO.update(ebayPaymentModel);
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("remove error");
		}
		return result;
	}
	@Override
	public OperationResult removePayments(String ids) {
		OperationResult result = new OperationResult();
		try {
			String[] idarr=ids.split(",");
			for (String id : idarr) {
				EbayPaymentModel ebayPaymentModel  = new EbayPaymentModel();
				ebayPaymentModel.setEnabledFlag("N");
				ebayPaymentModel.setPayid(Long.valueOf(id));
				updateInit(ebayPaymentModel);
				paymentDAO.update(ebayPaymentModel);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("remove error");
		}
		return result;
	}

	private void convertList(List<EbayPaymentModel> source, final List<EbayPaymentVO> target) {
		CollectionUtil.each(source, new IAction<EbayPaymentModel>() {
			@Override
			public void excute(EbayPaymentModel obj) {
				EbayPaymentVO ebayPaymentVO = new EbayPaymentVO();
				BeanUtils.copyProperties(obj, ebayPaymentVO);
				target.add(ebayPaymentVO);
			}
		});
	}
}
