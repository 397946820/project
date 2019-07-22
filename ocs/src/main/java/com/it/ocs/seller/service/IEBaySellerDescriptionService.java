package com.it.ocs.seller.service;

import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.seller.vo.SellerDescriptionVO;

public interface IEBaySellerDescriptionService {

	public ResponseResult<SellerDescriptionVO> findSellerDescriptionList(RequestParam param);

	public OperationResult insertSellerDescription(Map<String, Object> map);
	
	public OperationResult updateSellerDescription(Map<String, Object> map);
	
	public OperationResult addOrEdit(Map<String, Object> map);
	
	public OperationResult deleteSellerDescriptionByIds(String ids);
}
