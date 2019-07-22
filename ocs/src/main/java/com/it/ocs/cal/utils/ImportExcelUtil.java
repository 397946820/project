package com.it.ocs.cal.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.it.ocs.cal.model.CalculateModel;
import com.it.ocs.cal.model.CostModel;
import com.it.ocs.cal.model.ImportModel;
import com.it.ocs.cal.model.ProductEntityModel;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;

public class ImportExcelUtil {

	final static String notnullerror = "请填入第{0}行的{1},{2}不能为空";
	final static String errormsg = "第{0}行的{1}数据导入错误";

	@SuppressWarnings("rawtypes")
	public static List importExcel2(Class<?> clazz, InputStream xls) throws Exception {
		try {
			// 取得Excel
			XSSFWorkbook wb = new XSSFWorkbook(xls);
			XSSFSheet sheet = wb.getSheetAt(0);
			Field[] fields = ReflectUtils.getAllField(clazz);
			List<Field> fieldList = new ArrayList<Field>(fields.length);
			for (Field field : fields) {
				if (field.isAnnotationPresent(ModelProp.class)) {
					ModelProp modelProp = field.getAnnotation(ModelProp.class);
					if (modelProp.colIndex() != -1) {
						fieldList.add(field);
					}
				}
			}

			// 行循环
			List<ImportModel> modelList = new ArrayList<ImportModel>(sheet.getPhysicalNumberOfRows() * 2);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				// 数据模型
				ImportModel model = (ImportModel) clazz.newInstance();
				int nullCount = 0;
				Exception nullError = null;
				for (Field field : fieldList) {
					ModelProp modelProp = field.getAnnotation(ModelProp.class);
					XSSFCell cell = sheet.getRow(i).getCell((short) modelProp.colIndex());
					try {
						if (cell == null || cell.toString().length() == 0) {
							continue;
						} else if (field.getType().equals(Date.class)) {
							if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(), new Date(parseDate2(parseString(cell))));
							} else {
								BeanUtils.setProperty(model, field.getName(),
										new Date(cell.getDateCellValue().getTime()));
							}
						} else if (field.getType().equals(java.lang.Integer.class)) {
							if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(), (int) cell.getNumericCellValue());
							} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(), Integer.parseInt(parseString(cell)));
							}
						} else if (field.getType().toString().equals("double")) {
							BeanUtils.setProperty(model, field.getName(), Double.parseDouble(cell.getCTCell().getV()));
						} else {
							if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(),
										new BigDecimal(cell.getNumericCellValue()));
							} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(), parseString(cell));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new Exception(StringUtil.format(errormsg, new String[] { "" + (1 + i), modelProp.name() })
								+ "," + e.getMessage());
					}
				}
				modelList.add(model);
			}
			return modelList;
		} finally {
			xls.close();
		}
	}

	/**
	 * 导入Excel
	 * 
	 * @param clazz
	 * @param xls
	 * @return * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List importExcel(Class<?> clazz, InputStream xls) throws Exception {
		try {
			// 取得Excel
			XSSFWorkbook wb = new XSSFWorkbook(xls);
			XSSFSheet sheet = wb.getSheetAt(0);
			Field[] fields = ReflectUtils.getAllField(clazz);
			List<Field> fieldList = new ArrayList<Field>(fields.length);
			for (Field field : fields) {
				if (field.isAnnotationPresent(ModelProp.class)) {
					ModelProp modelProp = field.getAnnotation(ModelProp.class);
					if (modelProp.colIndex() != -1) {
						fieldList.add(field);
					}
				}
			}

			// 行循环
			List<ImportModel> modelList = new ArrayList<ImportModel>(sheet.getPhysicalNumberOfRows() * 2);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				// 数据模型
				ImportModel model = (ImportModel) clazz.newInstance();
				int nullCount = 0;
				Exception nullError = null;
				Boolean flag = true;
				for (int j = 0; j < fieldList.size(); j++) {
					Field field = fieldList.get(j);
					ModelProp modelProp = field.getAnnotation(ModelProp.class);

					XSSFCell cell = sheet.getRow(i).getCell((short) modelProp.colIndex());
					try {
						if (cell == null || cell.toString().length() == 0) {
							if (clazz.equals(SalesStatisticsModel.class)
									|| clazz.equals(com.it.ocs.cal.model.PricePlanModel.class)
									|| clazz.equals(ProductEntityModel.class) || clazz.equals(CalculateModel.class)
									|| clazz.equals(CostModel.class)) {
								if (clazz.equals(ProductEntityModel.class)) {
									if (j == 0 && (cell == null || cell.toString().length() == 0)) {
										flag = false;
										break;
									}
									if (field.getName().equals("clearPrice")) {
										continue;
									}
								} else {
									continue;
								}
							} else {
								nullCount++;
								if (!modelProp.nullable()) {

									nullError = new Exception(StringUtil.format(notnullerror,
											new String[] { "" + (1 + i), modelProp.name(), modelProp.name() }));
								}
							}
						} else if (field.getType().equals(Date.class)) {
							if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(), new Date(parseDate(parseString(cell))));
							} else {
								BeanUtils.setProperty(model, field.getName(),
										new Date(cell.getDateCellValue().getTime()));
							}
						} else if (field.getType().equals(Timestamp.class)) {
							if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(),
										new Timestamp(parseDate(parseString(cell))));
							} else {
								BeanUtils.setProperty(model, field.getName(),
										new Timestamp(cell.getDateCellValue().getTime()));
							}
						} else if (field.getType().equals(java.sql.Date.class)) {
							if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(),
										new java.sql.Date(parseDate(parseString(cell))));
							} else {
								BeanUtils.setProperty(model, field.getName(),
										new java.sql.Date(cell.getDateCellValue().getTime()));
							}
						} else if (field.getType().equals(java.lang.Integer.class)) {
							if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(), (int) cell.getNumericCellValue());
							} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(), Integer.parseInt(parseString(cell)));
							}
						} else if (field.getType().equals(java.math.BigDecimal.class)) {
							if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(),
										new BigDecimal(cell.getNumericCellValue()));
							} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(), new BigDecimal(parseString(cell)));
							}
						} else {
							if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(),
										new BigDecimal(cell.getNumericCellValue()));
							} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
								BeanUtils.setProperty(model, field.getName(), parseString(cell));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new Exception(StringUtil.format(errormsg, new String[] { "" + (1 + i), modelProp.name() })
								+ "," + e.getMessage());
					}
				}
				if (nullCount == fieldList.size()) {
					break;
				}
				if (nullError != null) {
					throw nullError;
				}
				if (flag) {
					modelList.add(model);
				}
			}
			return modelList;
		} finally {
			xls.close();
		}
	}

	private static String parseString(XSSFCell cell) {
		return String.valueOf(cell).trim();
	}

	private static long parseDate(String dateString) throws ParseException {
		if (dateString.indexOf("/") == 4) {
			return new SimpleDateFormat("yyyy/MM/dd").parse(dateString).getTime();
		} else if (dateString.indexOf("-") == 4) {
			return new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime();
		} else if (dateString.indexOf("年") == 4) {
			return new SimpleDateFormat("yyyy年MM月dd").parse(dateString).getTime();
		} else if (dateString.length() == 8) {
			return new SimpleDateFormat("yyyyMMdd").parse(dateString).getTime();
		} else {
			return System.currentTimeMillis();
		}
	}

	private static long parseDate2(String dateString) throws ParseException {
		if (dateString.indexOf("/") == 4) {
			return new SimpleDateFormat("yyyy/MM/dd").parse(dateString).getTime();
		} else if (dateString.indexOf("-") == 4) {
			dateString = dateString.replace("T", " ");
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString).getTime();
		} else if (dateString.indexOf("年") == 4) {
			return new SimpleDateFormat("yyyy年MM月dd").parse(dateString).getTime();
		} else if (dateString.length() == 8) {
			return new SimpleDateFormat("yyyyMMdd").parse(dateString).getTime();
		} else {
			return System.currentTimeMillis();
		}
	}
}
