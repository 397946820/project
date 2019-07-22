package com.it.ocs.synchronou.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayShippingServiceDetailsModel;
import com.it.ocs.synchronou.vo.ShippingServiceDetailsVO;

public interface IEBayShippingServiceDetailsService {
	
	public OperationResult synchronouShippingServiceDetails();

	public ResponseResult<ShippingServiceDetailsVO> selectShippingServiceDetails(RequestParam param);

	public OperationResult onOff(int id, int type);

}
