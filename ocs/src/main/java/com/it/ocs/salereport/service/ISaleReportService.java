package com.it.ocs.salereport.service;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salereport.vo.SaleReportVO;

public interface ISaleReportService {
	ResponseResult<SaleReportVO> queryByPage(RequestParam param);
}
