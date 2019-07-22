package com.it.ocs.synchronou.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayProductListingModel;
import com.it.ocs.synchronou.vo.ProductListingVO;

public interface IEBayProductListingService {

	public OperationResult synchronouProductListing(Long marketplace_id,Long category_id);
	
	public OperationResult insertProductListing(EBayProductListingModel ebayProductListingModel);
	
	public OperationResult updateProductListing(EBayProductListingModel ebayProductListingModel);
	
	public EBayProductListingModel internalSelectProductListingsByMIDAndCID(Long category_id,Long marketplace_id);

	public ResponseResult<ProductListingVO> selectProductListingsByMIDOrCID(Long category_Id,Long marketplace_id,RequestParam param);
	
	public EBayProductListingModel internalSelectProductListingByMIDOrCID(Long category_Id, Long marketplace_id);
}
