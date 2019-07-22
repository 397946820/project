package com.it.ocs.synchronou.service;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayAllProductDetailsModel;
import com.it.ocs.synchronou.vo.AllProductDetailsVO;

public interface IEBayAllProductDetailsService {
	
	public OperationResult synchronouAllProductDetail(String user_id,Long site_id);
	
	public OperationResult insertAllProductDetail(EBayAllProductDetailsModel allProductDetailsModel);
	
	public OperationResult updateAllProductDetail(EBayAllProductDetailsModel allProductDetailsModel);
	
	public ResponseResult<AllProductDetailsVO> selectAllProductDetails(RequestParam param);

	public List<EBayAllProductDetailsModel> internalSelectAllProductDetails();
}
