package com.it.ocs.salesStatistics.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.salesStatistics.dao.ISalesStatisticsDao;
import com.it.ocs.salesStatistics.model.SalesStatisticsExcelModel;

import net.sf.json.JSONObject;

@Service("salesStatisticsExcel")
public class SalesStatisticsExcelService extends AExcelExport {

	@Autowired
	private ISalesStatisticsDao salesStatisticsDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		String fromDate = request.getParameter("date1");
		String toDate = request.getParameter("date2");
		String platform = request.getParameter("platform");
		String station = request.getParameter("station");
		String whichTime = request.getParameter("whichTime");
		List<SalesStatisticsExcelModel> list = salesStatisticsDao.getData2(fromDate, toDate, platform, station,
				whichTime);
		List<Map<String, Object>> result = Lists.newArrayList();
		CollectionUtil.each(list, new IAction<SalesStatisticsExcelModel>() {
			@Override
			public void excute(SalesStatisticsExcelModel obj) {
				JSONObject addressJson = JSONObject.fromObject(obj.getShippingAddress());
				JSONObject paypalTransactionJson = JSONObject.fromObject(obj.getPaypalTransactionInfo());
				obj.setShipToCity(addressJson.getString("CityName"));
				obj.setShipToCountry(addressJson.getString("Country"));
				obj.setShipToState(addressJson.getString("StateOrProvince"));
				obj.setPaypalTransactionId(paypalTransactionJson.getString("ExternalTransactionID"));
				result.add(new BeanMap(obj));
			}
		});
		return result;
	}

	@Override
	protected void init(HttpServletRequest request) {
		super.initModel(SalesStatisticsExcelModel.class, "ebay订单详情信息.xlsx");
	}

}
