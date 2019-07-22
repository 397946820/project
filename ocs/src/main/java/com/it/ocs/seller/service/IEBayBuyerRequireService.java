package com.it.ocs.seller.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.seller.model.EBayBuyerRequiredModel;
import com.it.ocs.seller.vo.BuyerRequiredVO;

public interface IEBayBuyerRequireService {
	
	public ResponseResult<BuyerRequiredVO> findBuyerRequireList(RequestParam param);
	
	public OperationResult insertBuyerRequire(BuyerRequiredVO buyerRequiredVO);
	
	public OperationResult updateBuyerRequireById(BuyerRequiredVO byRequiredVO);

	public OperationResult deleteBuyerRequireById(String ids);
	
	public BuyerRequiredVO findBuyerRequireById(Long id);
}
