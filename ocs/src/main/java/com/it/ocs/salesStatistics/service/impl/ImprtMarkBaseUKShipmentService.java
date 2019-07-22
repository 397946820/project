package com.it.ocs.salesStatistics.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.it.ocs.salesStatistics.dao.ILightMarkShipmentDao;
import com.it.ocs.salesStatistics.model.LightMarkShipmentModel;
import com.it.ocs.salesStatistics.model.UKNoShipOrderExportModel;

public abstract class ImprtMarkBaseUKShipmentService extends BaseAExcelImportService<UKNoShipOrderExportModel> {
	private final static Logger logger = Logger.getLogger(ImprtMarkBaseUKShipmentService.class);
	
	@Autowired
	private ILightMarkShipmentDao lightMarkShipmentDao;
	
	protected abstract String getPlatform();

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return UKNoShipOrderExportModel.class;
	}
	
	@Override
	protected void updateRow(Map<String, Object> row) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("platform", this.getPlatform());
		param.put("orderId", row.get("orderNo"));
		param.put("itemId", row.get("itemId"));
		param.put("account", row.get("platformAccount"));
		//如果数据已经存在，就不需要再标记这么一个动作了。
		if(null == lightMarkShipmentDao.queryOne(param)) {
			param.put("actions", "uk-mark-shipment");
			param.put("marker", getCurrentUser().getId());
			String trackingNo = (String) row.get("trackingNo");
			param.put("tn01", trackingNo);
			param.put("carrier01", row.get("carrier"));
			param.put("tnUploadStatus", LightMarkShipmentModel.TNUPLOADSTATUS_WAITING);
			param.put("tnInitAt", StringUtils.isNotBlank(trackingNo) ? new Date() : null);
			lightMarkShipmentDao.add(param);
		}
	}

	@Override
	protected void catchUpdateRowEx(Exception e, Map<String, Object> row, List<String> errorsMsg) {
		errorsMsg.add(String.format("订单（orderId=%s; itemId=%s）标记失败（%s）！", (String) row.get("orderNo"), String.valueOf(row.get("itemId")), e.getMessage()));
		logger.error(e.getMessage(), e);
	}
}
