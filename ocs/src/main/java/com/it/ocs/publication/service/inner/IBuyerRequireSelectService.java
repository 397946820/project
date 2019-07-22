package com.it.ocs.publication.service.inner;

import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.vo.BuyerRequireVO;

public interface IBuyerRequireSelectService {

	public ResponseResult<BuyerRequireVO> queryDataList(RequestParam param);

	public OperationResult saveAs(Map<String, Object> map);

	public OperationResult isExist(String payName);

}
