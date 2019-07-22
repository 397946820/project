package com.it.ocs.salesStatistics.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.it.ocs.salesStatistics.dao.ILightMarkShipmentDao;
import com.it.ocs.salesStatistics.model.LightMarkShipmentModel;
import com.it.ocs.salesStatistics.model.UKNoShipOrderExportModel;
import com.it.ocs.synchronou.util.ValidationUtil;

public abstract class ImportUploadBaseUKTrackNoService extends BaseAExcelImportService<UKNoShipOrderExportModel> {
	
	private final static Logger logger = Logger.getLogger(ImportUploadBaseUKTrackNoService.class);

	@Autowired
	private ILightMarkShipmentDao lightMarkShipmentDao;

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return UKNoShipOrderExportModel.class;
	}
	
	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		boolean pass = super.validate(row, errorsMsg) && this.strValueIsNotBlank(row, "trackingNo", errorsMsg) && this.strValueIsNotBlank(row, "carrier", errorsMsg);
		if(pass) {
			pass = ValidationUtil.checkSpecialCharacter(String.valueOf(row.get("trackingNo")));
			if(!pass) {
				errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行运输号 tracking No不能包含特殊字符(较大数字EXCEL会科学计算法)");
			}
		}
		return pass;
	}
	
	protected abstract String getPlatform();
	
	@Override
	protected void updateRow(Map<String, Object> row) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("platform", this.getPlatform());
		param.put("orderId", row.get("orderNo"));
		param.put("itemId", row.get("itemId"));
		param.put("account", row.get("platformAccount"));
		LightMarkShipmentModel ms = lightMarkShipmentDao.queryOne(param);
		param.put("actions", "uk-upload-tn");
		param.put("marker", getCurrentUser().getId());
		String trackingNo = (String) row.get("trackingNo");
		param.put("tn01", trackingNo);
		param.put("carrier01", row.get("carrier"));
		param.put("tnInitAt", StringUtils.isNotBlank(trackingNo) && null == ms.getTnInitAt() ? new Date() : null);
		if(null == ms) {
			param.put("tnUploadStatus", LightMarkShipmentModel.TNUPLOADSTATUS_WAITING);
			lightMarkShipmentDao.add(param);
		} else if(LightMarkShipmentModel.TNUPLOADSTATUS_WAITING.equals(ms.getTnUploadStatus())) {
			lightMarkShipmentDao.update(param);
		}
	}
	
	@Override
	protected void catchUpdateRowEx(Exception e, Map<String, Object> row, List<String> errorsMsg) {
		errorsMsg.add(String.format("订单（orderId=%s; itemId=%s）上传跟踪号失败（%s）！", (String) row.get("orderNo"), String.valueOf(row.get("itemId")), e.getMessage()));
		logger.error(e.getMessage(), e);
	}
}
