package com.it.ocs.amazon.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.it.ocs.amazon.dao.IAmazonReportBaseDAO;
import com.it.ocs.amazon.dao.IAmazonReportDao;
import com.it.ocs.amazon.model.ColumnData;
import com.it.ocs.amazon.model.ReportRequestListModel;
import com.it.ocs.amazon.service.InventoryReportParseSupport;
import com.it.ocs.amazon.service.ReportDataSaveSupport;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;

public abstract class AmazonReportParserBaseService {
	private final Logger log = Logger.getLogger(InventoryReportParseSupport.class);
	private final String sites[] = { "amazon.ca", "amazon.jp" ,
			"amazon.com", "amazon.de", "amazon.com.au" };
	@Autowired
	private IAmazonReportDao iAmazonReportDao;
	@Autowired
	private ReportDataSaveSupport reportDataSaveSupport;

	/**
	 * 解析启动的task方法
	 */
	public void parserDate() {
		CollectionUtil.each(sites, new IAction<String>() {
			public void excute(String site) {
				parseData(site);
			}
		});

	}

	protected abstract String getReportType();

	/**
	 * Customer Return Report
	 * 
	 * @param site
	 */
	private void parseData(String site) {
		Map<String, Object> map = new HashMap<>();
		map.put("site", site);
		map.put("reportType", getReportType());
		List<ReportRequestListModel> inventoryReportList = iAmazonReportDao.getParseReport(map);
		CollectionUtil.each(inventoryReportList, new IAction<ReportRequestListModel>() {
			@Override
			public void excute(ReportRequestListModel requestModel) {
				parseReportData(requestModel);
				iAmazonReportDao.updateParseStatus(requestModel);
			}
		});
		
	}

	/**
	 * 解析FBA库存数据
	 * 
	 * @param reportMode
	 */
	private void parseReportData(ReportRequestListModel reportMode) {
		// 清除此报表数据
		getDao().delByReportId(reportMode.getReportGetId());
		// 解析数据
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(reportMode.getFilePathName());
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			String line = "";
			String title[] = null;
			String data[] = null;
			boolean isTitle = true;
			List<String[]> reportData = new ArrayList<>();
			List<ColumnData> columnSet = reportDataSaveSupport.getColumnData(reportMode.getSite(), getReportType());
			while ((line = br.readLine()) != null) {
				data = line.split("\t");
				if (isTitle) {
					title = data;
					isTitle = false;
				} else {
					reportData.add(data);
					if (reportData.size() > 0 && reportData.size() % 100 == 0) {
						saveReportData(reportData, title, columnSet, reportMode);
						reportData.clear();
					}
				}
			}
			if (reportData.size() > 0) {
				// 最后数据不足100条，数据处理
				saveReportData(reportData, title, columnSet, reportMode);
			}

		} catch (Exception e) {
			// iAmazonReportDao.deleteInventoryReportDataById(reportMode.getReportGetId());
			log.info("解析订单报表数据失败", e);
			throw new RuntimeException("解析订单报表数据失败",e);
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != isr) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	protected abstract IAmazonReportBaseDAO getDao();
	protected abstract void saveDate(Map<String,Object> data);

	public void saveReportData(List<String[]> reportData, String[] title, List<ColumnData> columnSet,
			ReportRequestListModel reportMode) {
		for (String[] data : reportData) {
			Map<String, String> map = new HashMap<>();
			for (int i = 0; i < title.length; i++) {
				if (i>=data.length) {
					map.put(title[i], null);
					continue;
				}
				map.put(title[i], data[i]);
			}
			Map<String, Object> saveData = formatData(map, columnSet, reportMode);
			saveDate(saveData);
		}
	}


	/**
	 * 根据映射组装数据存储
	 * 
	 * @param map
	 *            报表数据
	 * @param columnData
	 *            报表标题和数据字段映射关系
	 * @param reportMode
	 *            报表mode
	 * @return
	 */
	private Map<String, Object> formatData(Map<String, String> map, List<ColumnData> columnData,
			ReportRequestListModel reportMode) {
		Map<String, Object> data = new HashMap<>();
		for (ColumnData column : columnData) {
			String value = map.get(column.getLinkName());
			String columnName = column.getColumnName();
			if (null != value && !"".equals(value)) {
				column.setValue(value);
				data.put(columnName, column.getValue());
			} else {
				data.put(columnName, "");
			}
		}
		data.put("REPORT_ID", reportMode.getReportGetId());
		data.put("SITE", reportMode.getSite());
		return data;
	}
}
