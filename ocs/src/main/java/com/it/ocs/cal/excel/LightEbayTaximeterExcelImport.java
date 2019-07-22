package com.it.ocs.cal.excel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.it.ocs.cal.excel.vo.LECalculateExcelVO;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.excel.utils.ExcelUtils;

public class LightEbayTaximeterExcelImport {
	private static <T> T constructBean(XSSFRow row, List<Field> fields, T t)
			throws IllegalAccessException, InvocationTargetException {
		try {
			T excelVO = (T) t.getClass().newInstance();
			for (Field field : fields) {
				ExcelLink excelLink = field.getAnnotation(ExcelLink.class);
				int index = excelLink.index();
				XSSFCell cell = row.getCell(index);
				if (cell == null) {
					BeanUtils.setProperty(excelVO, field.getName(), null);
				} else {
					BeanUtils.setProperty(excelVO, field.getName(), cell.toString());
				}
			}
			return excelVO;
		} catch (InstantiationException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	public static <T> List<T> convertBean(InputStream xls, T t)
			throws IOException, IllegalAccessException, InvocationTargetException {
		XSSFWorkbook wb = new XSSFWorkbook(xls);
		XSSFSheet sheet = wb.getSheetAt(0);
		List<Field> fields = ExcelUtils.getField(t.getClass());
		List<T> modelList = new ArrayList<>();
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			T excelVO = constructBean(sheet.getRow(i), fields, t);
			modelList.add(excelVO);
		}
		return modelList;
	}

}
