package com.it.ocs.salesStatistics.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.salesStatistics.dao.ILightMarkShipmentDao;
import com.it.ocs.salesStatistics.vo.LightMarkShipmentExportVO;

/**
 * 官网UK跟踪号上传记录数据导出服务
 * @author zhouyancheng
 *
 */
@Service("UKUploadRecordExportService")
public class UKUploadRecordExportService extends AExcelExport {

	@Autowired
	private ILightMarkShipmentDao lightMarkShipmentDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			param.put(entry.getKey(), null == entry.getValue() || entry.getValue().length == 0 ? null : entry.getValue()[0]);
		}
		param.put("platform", "light");
		
		List<Map<String, Object>> data = this.lightMarkShipmentDao.queryExportData(param);
		
		if(data != null && !data.isEmpty()) {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Map<String, Object> map : data) {
//				oracle.sql.TIMESTAMP createdat = (oracle.sql.TIMESTAMP) map.get("CREATED_AT"), initat = (oracle.sql.TIMESTAMP) map.get("TN_INIT_AT"), shippedat = (oracle.sql.TIMESTAMP) map.get("SHIPPED_AT");
//				try {
//					if(createdat != null) {
//						map.put("CREATED_AT", sdf.format(new Date(createdat.dateValue().getTime())));
//					}
//					if(initat != null) {
//						map.put("TN_INIT_AT", sdf.format(new Date(initat.dateValue().getTime())));
//					}
//					if(shippedat != null) {
//						map.put("SHIPPED_AT", sdf.format(new Date(shippedat.dateValue().getTime())));
//					}
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		}
		
		return data;
	}
	
	@Override
	protected void init(HttpServletRequest request) {
		String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		super.initModel(LightMarkShipmentExportVO.class, "官网UK跟踪号上传记录-" + dateStr.replace(":", "-") + ".xlsx");
	}

}
