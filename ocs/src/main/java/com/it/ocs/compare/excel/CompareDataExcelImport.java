package com.it.ocs.compare.excel;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.enums.ExcelVersion;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.ExcelImportUtil;
import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.excel.utils.ExcelUtils;

public class CompareDataExcelImport {
	public static <T> List<T> convertBean(InputStream xls,ExcelVersion excelVersion, Class<T> cls) throws Exception {
		Map<String, String> header = new HashMap<>();
		List<Field> fields = ExcelUtils.getField(cls);
		for (Field field : fields) {
			ExcelLink el = field.getAnnotation(ExcelLink.class);
			header.put(el.title(), field.getName());
		}
		ExcelImportUtil eiu = new ExcelImportUtil(header);
		eiu.init(xls,excelVersion);
		List<T> excels = eiu.bindToModels(cls, false);
		if (eiu.hasError()) {
			throw new Exception(eiu.getError().toString());
		}
		return excels;
	}
	
//	public static void excute(HttpServletRequest request, HttpServletResponse response) {
//		ExcelUtils.outputExcel(response, createExcel(request), ExcelUtils.formatDownloadName(fileName, request));
//	}

	/**
	 * 创建excel
	 * 
	 * @param request
	 * @return
	 */
	public static <T> void createExcel(List<T> datas,Class<T> cls,SXSSFWorkbook wb,String sheetName) {
		List<Map<String,Object>> mapDatas = BeanConvertUtil.listToMap(datas);
		Sheet sheet = wb.createSheet(sheetName);
		// 插入表头
		CellStyle cellStyle = ExcelUtils.getTableHeadCellStyle(wb);
		List<Field> fields = ExcelUtils.getField(cls);
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
		if (!CollectionUtil.isNullOrEmpty(datas)) {
			// 插入数据
			for (Map<String,Object> rowData : mapDatas) {
				rowCount++;
				Row dataRow = sheet.createRow(rowCount);
				createRow(dataRow, rowData, dataIndexSet, fields.size());
			}
		}
	}

	/**
	 * 创建行
	 * 
	 * @param sheet
	 * @param rowData
	 */
	private static void createRow(Row row, Map<String,Object> rowData, Map<Integer, String> dataIndexSet,
			int columnMaxIndex) {
		for (int i = 0; i < columnMaxIndex; i++) {
			Cell cell = row.createCell(i);
			
			String dataKey = dataIndexSet.get(i);
			if (null != dataKey) {
				String cellValue = "";
				Object obj = rowData.get(dataKey);
				if (null != obj) {
					if (obj instanceof Double) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue((Double)obj);
					} else {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cellValue = String.valueOf(obj);
						cell.setCellValue(cellValue);
					}
					
				}
				
			}
		}
	}
	public static void craeteExcel(Map<String, Object> map,SXSSFWorkbook wb,String sheetName){
    	Sheet sheet = wb.createSheet(sheetName);
    	// 插入表头
    	CellStyle cellStyle = ExcelUtils.getTableHeadCellStyle(wb);
    	int rowCount = 0;
		Row row = sheet.createRow(rowCount);
		int fields = map.size();
		Map<Integer, String> dataIndexSet = new HashMap<Integer, String>();
		int i=0;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Cell cell = row.createCell(i);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(entry.getKey());
			dataIndexSet.put(i,entry.getKey() );
			i++;
		}
		
			// 插入数据
				rowCount++;
				Row dataRow = sheet.createRow(rowCount);
				createRow(dataRow, map, dataIndexSet, fields);
    }
	
}
