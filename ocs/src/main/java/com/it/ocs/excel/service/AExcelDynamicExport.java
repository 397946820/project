package com.it.ocs.excel.service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.excel.utils.ExcelUtils;

import net.sf.json.JSONObject;

public abstract class AExcelDynamicExport {
	private final static Logger logger = Logger.getLogger(AExcelDynamicExport.class);
	private Class<?> clazz = null;
	private String[] includeFields = null;
	private String fileName = "";

	public void excute(HttpServletRequest request, HttpServletResponse response) {
		this.init(request);
		SXSSFWorkbook wb = null;
		try {
			wb = this.createExcel(request);
		} catch (Exception e) {
			logger.error(">>> 创建Excel Workbook出现错误 >>> " + e.getMessage(), e);
		}
		ExcelUtils.outputExcel(response, wb, ExcelUtils.formatDownloadName(fileName, request));
	}

	
	private boolean isExclude(Field field) {
		if(null == includeFields || includeFields.length == 0) {
			return false;
		}
		
		return null == CollectionUtil.search(includeFields, new IFunction<String, Boolean>() {
			public Boolean excute(String include) {
				return field.getName().equals(include);
			}
		});
	}
	
	/**
	 * 创建excel
	 * 
	 * @param request
	 * @return
	 */
	private SXSSFWorkbook createExcel(HttpServletRequest request) {
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		List<Map<String, Object>> data = this.getData(request);
		Sheet sheet = wb.createSheet();
		// 插入表头
		CellStyle cellStyle = ExcelUtils.getTableHeadCellStyle(wb);
		List<Field> fields = ExcelUtils.getField(clazz);
		ListIterator<Field> iterator = fields.listIterator();
		if (!CollectionUtil.isNullOrEmpty(data)) {
			Map<String, Object> map = data.get(0);
			while (iterator.hasNext()) {
				Field field = iterator.next();
				ExcelLink excel = field.getAnnotation(ExcelLink.class);
				if ((!map.keySet().contains(field.getName().toUpperCase()) && excel.isAuto()) || this.isExclude(field)) {
					iterator.remove();
				}
			}
		}
		int rowCount = 0;
		Row row = sheet.createRow(rowCount);
		Map<Integer, String> dataIndexSet = new HashMap<Integer, String>();
		for (int i = 0; i < fields.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			ExcelLink excelLink = fields.get(i).getAnnotation(ExcelLink.class);
			cell.setCellValue(excelLink.title());
			dataIndexSet.put(i, fields.get(i).getName());
		}
		if (!CollectionUtil.isNullOrEmpty(data)) {
			// 插入数据
			for (Map<String, Object> rowData : data) {
				rowCount++;
				Row dataRow = sheet.createRow(rowCount);
				createRow(dataRow, rowData, dataIndexSet, fields.size());
			}
		}
		return wb;

	}

	/**
	 * 创建行
	 * 
	 * @param sheet
	 * @param rowData
	 */
	private void createRow(Row row, Map<String, Object> rowData, Map<Integer, String> dataIndexSet,
			int columnMaxIndex) {
		for (int i = 0; i < columnMaxIndex; i++) {
			Cell cell = row.createCell(i);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String dataKey = dataIndexSet.get(i);
			if (null != dataKey) {
				String cellValue = "";
				Object obj = rowData.get(dataKey.toUpperCase()) == null ? rowData.get(dataKey) : rowData.get(dataKey.toUpperCase());
				if (null != obj) {
					try {
//						if(obj instanceof oracle.sql.TIMESTAMP) {
//							obj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(((oracle.sql.TIMESTAMP) obj).dateValue().getTime()));
//						} else if(obj instanceof oracle.sql.DATE) {
//							obj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(((oracle.sql.DATE) obj).dateValue().getTime()));
//						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					cellValue = String.valueOf(obj);
				}
				cell.setCellValue(cellValue);
			}
		}
	}

	/**
	 * 导出数据
	 * 
	 * @param request
	 * @return
	 */
	protected abstract List<Map<String, Object>> getData(HttpServletRequest request);

	/**
	 * 设置映射实体类
	 * 
	 * @param clazz
	 */
	protected abstract void init(HttpServletRequest request);

	protected void initModel(Class<?> clazz, String fileName, String[] includeFields) {
		this.clazz = clazz;
		this.fileName = fileName;
		this.includeFields = includeFields;
	}

	protected String getValueByJson(JSONObject json, String key) {
		String value = "";
		try {
			value = String.valueOf(json.getString(key));
			if ("null".equals(value)) {
				value = "";
			}
		} catch (Exception e) {
			value = "";
		}
		return value;
	}
}
