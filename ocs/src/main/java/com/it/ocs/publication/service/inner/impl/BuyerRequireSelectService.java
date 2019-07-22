package com.it.ocs.publication.service.inner.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.publication.model.BuyerRequireModel;
import com.it.ocs.publication.model.PaymentModel;
import com.it.ocs.publication.service.inner.IBuyerRequireSelectService;
import com.it.ocs.publication.vo.BuyerRequireVO;
import com.it.ocs.publication.vo.PaymentVO;


@Service
public class BuyerRequireSelectService extends BaseService implements IBuyerRequireSelectService {

	@Override
	public ResponseResult<BuyerRequireVO> queryDataList(RequestParam param) {
		ResponseResult<BuyerRequireVO> result = new ResponseResult<BuyerRequireVO>();
		BuyerRequireVO buyerRequiredVO = BeanConvertUtil.mapToObject(param.getParam(), BuyerRequireVO.class);
		List<BuyerRequireModel> buyerRequireModels = buyerRequireDAO.queryByPage(buyerRequiredVO,param.getStartRow(),param.getEndRow());
		List<BuyerRequireVO> buyerRequireVOs = new ArrayList<>();
		convertList(buyerRequireModels, buyerRequireVOs);
		int count = buyerRequireDAO.count(buyerRequiredVO);
		result.setRows(buyerRequireVOs);
		result.setTotal(count);
		return result;
	}

	@Override
	public OperationResult saveAs(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationResult isExist(String payName) {
		// TODO Auto-generated method stub
		return null;
	}
	private void convertList(List<BuyerRequireModel> source, final List<BuyerRequireVO> target) {
		CollectionUtil.each(source, new IAction<BuyerRequireModel>() {
			@Override
			public void excute(BuyerRequireModel obj) {
				BuyerRequireVO buyerRequireVO = new BuyerRequireVO();
				BeanUtils.copyProperties(obj, buyerRequireVO);
				target.add(buyerRequireVO);
			}
		});
	}
}
