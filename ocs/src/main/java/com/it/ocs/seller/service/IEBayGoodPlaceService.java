package com.it.ocs.seller.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.seller.vo.GoodPlaceVO;

public interface IEBayGoodPlaceService {
	
	
	public ResponseResult<GoodPlaceVO> findGoodPlaceList(RequestParam param);
	
	public OperationResult addOrEdit(GoodPlaceVO goodPlaceVO);
	
	public OperationResult insertGoodPlace(GoodPlaceVO goodPlaceVO);
	
	public OperationResult updateGoodPlace(GoodPlaceVO goodPlaceVO);
	
	public OperationResult deleteGoodPlaceByIds(String ids);
}
