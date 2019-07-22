package com.it.ocs.site.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.site.vo.SiteVO;

public interface ISiteService {
	public ResponseResult<SiteVO> querySite(RequestParam param);
	
	public OperationResult saveSite(SiteVO siteVO);
	
	public OperationResult removeSite(Long siteId);
}
