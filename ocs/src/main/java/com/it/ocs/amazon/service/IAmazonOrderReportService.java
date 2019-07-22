package com.it.ocs.amazon.service;

import java.util.List;

import com.it.ocs.amazon.model.OrderReportVO;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.vo.ComboBoxVO;

public interface IAmazonOrderReportService {

	public ResponseResult<OrderReportVO> findOrderReportList(RequestParam param);

	public List<ComboBoxVO> getOrderTypeBySite(String site);

}
