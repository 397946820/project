package com.it.ocs.publication.service.external;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.model.EBayAdvertTemplatesModel;
import com.it.ocs.publication.vo.AdvertTemplatesVO;

public interface IEBayAdvertTemplatesService {

	public OperationResult insertAdvert(AdvertTemplatesVO advertTemplatesVO);
	
	public OperationResult updateAdvert(AdvertTemplatesVO advertTemplatesVO);

	public OperationResult addSeve(AdvertTemplatesVO advertTemplatesVO);
	
	public ResponseResult<AdvertTemplatesVO> selectAdvertTemplates(RequestParam param);
	
	public OperationResult delete(Long id);
	
	public ResponseResult<AdvertTemplatesVO> selectAdvertList();
	
	public EBayAdvertTemplatesModel selectAdvertById(Long id);
}
