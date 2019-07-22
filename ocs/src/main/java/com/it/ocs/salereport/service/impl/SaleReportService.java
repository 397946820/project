package com.it.ocs.salereport.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salereport.service.ISaleReportService;
import com.it.ocs.salereport.service.support.AmazonSaleReportSupport;
import com.it.ocs.salereport.vo.SaleReportVO;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
public class SaleReportService implements ISaleReportService {

	private MongoTemplate mongoTemplate;
	@Override
	public ResponseResult<SaleReportVO> queryByPage(RequestParam param) {
		ResponseResult<SaleReportVO> result = new ResponseResult<>();
		String platform = AmazonSaleReportSupport.getPlatForm(param.getParam());
		if (StringUtils.isBlank(platform)) {
			platform="amazon";
		}
		param.getParam().put("platform", platform);
		try {
			List<SalesStatisticsModel> list = AmazonSaleReportSupport.filterDataByParam(param.getParam());
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return null;
	}
	
}
