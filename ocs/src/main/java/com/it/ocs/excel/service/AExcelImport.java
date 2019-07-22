package com.it.ocs.excel.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.excel.utils.ExcelUtils;

/**
 * excel导入
 * @author chenyong
 *
 */
public abstract class AExcelImport{
	private final static Logger log = Logger.getLogger(AExcelImport.class);
	private static Class<?> clazz = null;
	
	public Map<String,Object> excute(MultipartFile file, HttpServletRequest request) {
		//初始化
		initModel(request);
		Map<String,Object> result = new HashMap<String,Object>();
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(file.getInputStream());
		} catch (IOException e) {
			log.info("读取Excel失败,fileName:"+ file.getName(), e);
			result.put("message", "读取Excel失败");
			return result;
		}
		XSSFSheet sheet = null;
		if(null != wb){
			sheet = wb.getSheetAt(0);
		}else{
			log.info("excel中没找到工作簿,fileName:"+ file.getName());
			result.put("message", "excel中没找到工作簿");
			return result;
		}
		if(null == sheet){
			log.info("excel中没找到sheet,fileName:"+ file.getName());
			result.put("message", "excel中没找到sheet");
			return result;
		}
		parseSheet(sheet,result);
		return result;
	}
	
	/**
	 * 解析sheet
	 * @param sheet
	 * @return
	 */
	private void parseSheet(XSSFSheet sheet,Map<String,Object> result) {
		List<Map<String, Object> > updateRows = new ArrayList<>();
		List<Map<String, Object> > insertRows = new ArrayList<>();
		List<Map<String, Object> > rows = new ArrayList<>();
		//验证行
		List<String> errorsMsg = new ArrayList();
		int sheetCount = sheet.getPhysicalNumberOfRows();
		for(int i = 1;i<sheetCount;i++){
			XSSFRow row = sheet.getRow(i);
			if(row == null) {
				break;
			}
			Map<String, Object>  rowData = parseRow(row);
			rowData.put("rowNumber",i+1);
			if(validate(rowData,errorsMsg)){
				//更新
				if(isExist(rowData,errorsMsg)){
					updateRows.add(rowData);
				}else{//插入
					insertRows.add(rowData);
				}
				rows.add(rowData);
			}
			//批量插入
			if(insertRows.size()%20==0){
				insert(insertRows,errorsMsg);
				insertRows.clear();
			}
			
			//批量更新
			if(updateRows.size()%20==0){
				update(updateRows,errorsMsg);
				updateRows.clear();
			}
		}
		insert(insertRows,errorsMsg);
		update(updateRows,errorsMsg);
		refresh(rows);
		result.put("message", errorsMsg);
	}
	
	/**
	 * 解析行
	 * @param row
	 * @return
	 */
	private Map<String, Object>  parseRow(XSSFRow row) {
		Map<String, Object> rowMap = new HashMap<String, Object>();
		List<Field> fieldList = ExcelUtils.getField(clazz);
		for(Field field : fieldList){
			ExcelLink excelLink = field.getAnnotation(ExcelLink.class);
			int index = excelLink.index();
			XSSFCell  cell = row.getCell(index);
			if(null != cell){
				rowMap.put(field.getName(), getValue(cell));
			}else{
				rowMap.put(field.getName(), "");
			}
		}
		return rowMap;
	}
	
	private String getValue(XSSFCell cell){
		String value = "";
		int type = cell.getCellType();
		switch(type){
			case XSSFCell.CELL_TYPE_BOOLEAN :
				value = String.valueOf(cell.getBooleanCellValue());
				break;
			case XSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				value = String.valueOf(cell.getNumericCellValue());
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				value = "";
				break;
			default :
				value = cell.toString();
				break;
		}
		return value;
	}
	
	/**
	 * 验证行数据
	 * @param row
	 * @return
	 */
	protected abstract boolean validate(Map<String, Object>  row,List<String> errorsMsg);
	
	/**
	 * 判断数据是否存在
	 * @param row
	 * @param errorsMsg
	 * @return
	 */
	protected abstract boolean isExist(Map<String, Object>  row,List<String> errorsMsg);
	
	/**
	 * 插入数据
	 * @param rows
	 * @return
	 */
	protected abstract int insert(List<Map<String, Object> > rows,List<String> errorsMsg);
	
	/**
	 * 更新数据
	 * @param rows
	 * @return
	 */
	protected abstract int update(List<Map<String, Object> > rows,List<String> errorsMsg);
	
	/**
	 * 刷新
	 * @param rows
	 * @return
	 */
	protected abstract int refresh(List<Map<String, Object> > rows);
	
	
	private void initModel(HttpServletRequest request){
		this.clazz = getTemplateClazz(request);
	}
	
	/**
	 * 下载模板
	 * @param request
	 * @param response
	 */
	public void getTemplate(HttpServletRequest request, HttpServletResponse response) {
		initModel(request);
		SXSSFWorkbook wb = new SXSSFWorkbook(100); 
		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow(0);
		List<Field> fields = ExcelUtils.getField(clazz);;
		CellStyle cellStyle = ExcelUtils.getTableHeadCellStyle(wb);
		for(int i=0;i<fields.size();i++){
			Cell cell = row.createCell(i);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			ExcelLink excelLink = fields.get(i).getAnnotation(ExcelLink.class);
			cell.setCellValue(excelLink.title());
		}
		ExcelUtils.outputExcel(response, wb, ExcelUtils.formatDownloadName("template.xlsx", request));
	}
	
	protected abstract Class<?> getTemplateClazz(HttpServletRequest request);
}
