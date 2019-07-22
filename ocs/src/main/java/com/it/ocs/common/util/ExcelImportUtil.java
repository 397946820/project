package com.it.ocs.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.it.ocs.common.enums.ExcelVersion;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.excel.annotation.ExcelLink;

public class ExcelImportUtil {
	private static final Logger log = Logger.getLogger(ExcelImportUtil.class);
	private static final int HEADER = 0;
	/**
	 * 默认的日期格式
	 */
	private String date_format = "yyyy-MM-dd'T'HH:mm:ss'+'";
	private static final int START = 1;

	public ExcelImportUtil(Map<String, String> associations) {
		this.associations = associations;
		format = new SimpleDateFormat(date_format);
	}

	public ExcelImportUtil(Map<String, String> associations, String date_format) {
		this.associations = associations;
		this.date_format = date_format;
		format = new SimpleDateFormat(date_format);
	}

	private ExcelVersion excelVersion;
	private XSSFWorkbook book;
	private HSSFWorkbook hbook;
	/**
	 * key:excel对应标题 ,value:对象属性
	 */
	private Map<String, String> associations;
	/**
	 * 装换失败的数据信息，记录行数
	 */
	private StringBuffer error = new StringBuffer(0);

	private Map<Integer, String> header;

	private SimpleDateFormat format;

	/**
	 * 初始化工作簿
	 * 
	 * @param file
	 */
	public void init(InputStream xls, ExcelVersion excelVersion) {
		InputStream in;
		try {
			in = xls;
			switch (excelVersion) {
			case OLD:
				hbook = new HSSFWorkbook(in);
				break;
			default:
				book = new XSSFWorkbook(in);
				break;
			}
			this.excelVersion = excelVersion;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return true 存在错误，false 不存在错误
	 */
	public boolean hasError() {
		return error.capacity() > 0;
	}

	public StringBuffer getError() {
		return error;
	}

	/**
	 * 获取第一行标题栏数据
	 * 
	 * @param sheet
	 * @return map key：标题栏列下标（0开始） value 标题栏值
	 */
	private void loadHeader(Sheet sheet) {
		this.header = new HashMap<Integer, String>();
		Row row = sheet.getRow(HEADER);
		int columns = row.getLastCellNum();
		for (int i = 0; i < columns; i++) {
			// log.debug("加载标题栏:" + row.getCell(i).getStringCellValue());
			String value = row.getCell(i).getStringCellValue();
			if (null == value) {
				throw new RuntimeException("标题栏不能为空！");
			}
			header.put(i, value);
		}
		// log.debug("<<<<<<<<<<<<标题栏加载完毕>>>>>>>>>>>");
	}

	private void loadHeader(HSSFSheet sheet) {
		this.header = new HashMap<Integer, String>();
		HSSFRow row = sheet.getRow(HEADER);
		int columns = row.getLastCellNum();
		for (int i = 0; i < columns; i++) {
			// log.debug("加载标题栏:" + row.getCell(i).getStringCellValue());
			String value = row.getCell(i).getStringCellValue();
			if (null == value) {
				throw new RuntimeException("标题栏不能为空！");
			}
			header.put(i, value);
		}
	}

	/**
	 * 
	 * @param clazz
	 * @param required
	 *            是否每个属性都是必须的
	 * @return
	 */
	public <T> List<T> bindToModels(Class clazz, boolean required) throws Exception {
		if (excelVersion.equals(ExcelVersion.NEW)) {
			return bindToModelsByNewExcel(clazz, required);
		} else {
			return bindToModelsByOldExcel(clazz, required);
		}
	}
	
	private <T> List<T> bindToModelsByOldExcel(Class clazz, boolean required) throws Exception {
		// 获取第一页
		HSSFSheet sheet = this.hbook.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();// 获取行数

		if (rowNum < 1) {
			return new ArrayList<T>();
		}
		// 加载标题栏数据
		this.loadHeader(sheet);
		List<T> result = new ArrayList<T>();

		for (int i = START; i < rowNum; i++) {
			HSSFRow row = sheet.getRow(i);
			int cellNum = row.getLastCellNum();
			T instance = (T) clazz.newInstance();
			for (int columns = 0; columns < cellNum; columns++) {
				HSSFCell cell = row.getCell(columns);
				String key = header.get(columns);
				// 判断单元格的数据类型
				Object value = loadCellType(cell, key, clazz);

				// 获取单元格的值
				if (null == value) {
					// 如果为必填的则将错误信息记录
					if (required) {
						this.error.append("第" + (i + 1) + "行，" + header.get(columns) + "字段，数据为空，跳过！").append("\n");
						log.debug("第" + (i + 1) + "行，" + header.get(columns) + "字段，数据为空，跳过！");
						continue;
					}
				} else {

					if (associations.containsKey(key)) {
						// 加载实际值
						if (null == value || StringUtils.isBlank(value.toString())) {
							continue;
						}
						this.loadValue(clazz, instance, this.associations.get(key), value);
					}
				}
			}
			result.add(instance);
		}
		log.debug("<<<<<装换完成" + (this.hasError() ? "有错误信息" : "") + "，共有对象:" + result.size() + "个" + ">>>>>>");
		return result;
	}

	private <T> List<T> bindToModelsByNewExcel(Class clazz, boolean required) throws Exception {
		List<T> result = new ArrayList<T>();
		for (int j = 0; j < this.book.getNumberOfSheets(); j++) {
			this.book.getNumberOfSheets();
			// 获取第一页
			XSSFSheet sheet = this.book.getSheetAt(j);
			int rowNum = sheet.getLastRowNum();// 获取行数
			if (rowNum < 1) {
				return new ArrayList<T>();
			}
			// 加载标题栏数据
			this.loadHeader(sheet);
			for (int i = START; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				int cellNum = row.getLastCellNum();
				T instance = (T) clazz.newInstance();
				for (int columns = 0; columns < cellNum; columns++) {
					Cell cell = row.getCell(columns);
					String key = header.get(columns);
					// 判断单元格的数据类型
					Object value = loadCellType(cell, key, clazz);

					// 获取单元格的值
					if (null == value) {
						// 如果为必填的则将错误信息记录
						if (required) {
							this.error.append("第" + (i + 1) + "行，" + header.get(columns) + "字段，数据为空，跳过！").append("\n");
							log.debug("第" + (i + 1) + "行，" + header.get(columns) + "字段，数据为空，跳过！");
							continue;
						}
					} else {
						if (associations.containsKey(key)) {
							// 加载实际值
							this.loadValue(clazz, instance, this.associations.get(key), value);
						}
					}
				}
				result.add(instance);
			}
			log.debug("<<<<<装换完成" + (this.hasError() ? "有错误信息" : "") + "，共有对象:" + result.size() + "个" + ">>>>>>");
		}
		return result;
	}
	

	/**
	 * 将单元格数据转换成string类型
	 * 
	 * @param cellType
	 * @param cell
	 * @return
	 */
	private Object loadCellType(Cell cell, String excelKey, Class clz) {
		if (null == cell)
			return null;
		Field[] fields = clz.getDeclaredFields();
		Field searchField = CollectionUtil.search(fields, new IFunction<Field, Boolean>() {
			@Override
			public Boolean excute(Field obj) {
				ExcelLink el = obj.getAnnotation(ExcelLink.class);
				return null != el && el.title().equals(excelKey);
			}
		});
		if (null != searchField) {
			if (searchField.getType().toString().equals("class java.lang.String")) {
				if (XSSFCell.CELL_TYPE_NUMERIC == cell.getCellType() && HSSFDateUtil.isCellDateFormatted(cell)) {
					return this.formateDate(cell.getDateCellValue());
				}
				String cellValue = String.valueOf(cell);
				if (cellValue.contains(".") && "sku".equals(searchField.getName())) {
					cellValue = cellValue.substring(0,cellValue.indexOf("."));
				}
				return cellValue;
			} else {
				return Double.valueOf(cell.toString());
			}
		}
		return null;
	}

	/**
	 * 将单元格数据转换成string类型
	 * 
	 * @param cellType
	 * @param cell
	 * @return
	 */
	private String loadCellType(XSSFCell cell) {
		String value = null;
		if (null == cell)
			return null;
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			// 判断当前的cell是否为Date
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				value = this.formateDate(cell.getDateCellValue());
			} else {
				value = String.valueOf((long) cell.getNumericCellValue());
			}
			break;
		case XSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			log.debug("不支持函数！");
			break;
		}

		return value;
	}

	/**
	 * 注入属性值
	 * 
	 * @param instance
	 * @param pro
	 *            属性对象
	 * @param value
	 *            属性值
	 */
	@SuppressWarnings("unchecked")
	private <T> void loadValue(Class clazz, T instance, String pro, Object value)
			throws SecurityException, NoSuchMethodException, Exception {
		String getMethod = this.initGetMethod(pro);
		Class type = null;
		if (null != getMethod) {
			type = clazz.getDeclaredMethod(getMethod, null).getReturnType();
		}
		Method method = clazz.getMethod(this.initSetMethod(pro), type);
		if (type == String.class) {
			method.invoke(instance, value.toString());
		} else if (type == int.class || type == Integer.class) {
			method.invoke(instance, Integer.parseInt((value.toString())));

		} else if (type == float.class || type == Float.class) {
			method.invoke(instance, (Float) value);

		} else if (type == double.class || type == Double.class) {
			method.invoke(instance, (Double) value);

		} else if (type == Date.class) {
			method.invoke(instance, this.parseDate(value.toString()));
		}

	}

	private Date parseDate(String value) throws ParseException {
		return format.parse(value);
	}

	private String formateDate(Date date) {
		return format.format(date);
	}

	public String initSetMethod(String field) {
		if (null != field)
			return "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
		return null;
	}

	public String initGetMethod(String field) {
		if (null != field)
			return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
		return null;
	}

	public String getDate_format() {
		return date_format;
	}

	public void setDate_format(String date_format) {
		this.date_format = date_format;
	}

}
