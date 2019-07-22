package com.it.ocs.excel.utils;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.it.ocs.cal.utils.ReflectUtils;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.annotation.ExcelLink;

import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

public class ExcelUtils {
	private final static Logger log = Logger.getLogger(ExcelUtils.class);

	/**
	 * 获取最大列数
	 * 
	 * @param fields
	 * @return
	 */
	public static int getColumnMaxIndex(List<Field> fields) {
		int maxIndex = 0;
		for (Field field : fields) {
			if (field.isAnnotationPresent(ExcelLink.class)) {
				ExcelLink excelLink = field.getAnnotation(ExcelLink.class);
				if (excelLink.index() > maxIndex) {
					maxIndex = excelLink.index();
				}
			}
		}
		return maxIndex + 1;
	}

	/**
	 * 获取Excel映射
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getField(Class<?> clazz) {
		Map<Integer, Field> map = new TreeMap<>();
		 CollectionUtil.each(CollectionUtil.searchList((List<Field>) Arrays.asList(ReflectUtils.getAllField(clazz)),
					new IFunction<Field, Boolean>() {
				@Override
				public Boolean excute(Field field) {
					return field.isAnnotationPresent(ExcelLink.class);
				}
			}), new IAction<Field>() {
				@Override
				public void excute(Field field) {
					map.put(field.getAnnotation(ExcelLink.class).index(), field);
				}
			});
		return new ArrayList<>(map.values());
	}

	/**
	 * 获取Excel标题通用样式
	 * 
	 * @param wb
	 * @return
	 */
	public static CellStyle getTableHeadCellStyle(SXSSFWorkbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		return cellStyle;
	}

	public static String formatDownloadName(String filename, HttpServletRequest request) {
		String agent = request.getHeader("user-agent");
		try {
			if (null != agent && agent.contains("Firefox")) { // 火狐浏览器
				filename = "=?UTF-8?B?" + new BASE64Encoder().encode(filename.getBytes("utf-8")) + "?=";
				filename = filename.replaceAll("\r\n", "");
			} else { // IE及其他浏览器
				filename = URLEncoder.encode(filename, "utf-8");
				filename = filename.replace("+", " ");
			}
		} catch (Exception e) {
			log.info("处理下载浏览器兼容问题失败");
		}

		return filename;
	}

	public static void outputExcel(HttpServletResponse response, SXSSFWorkbook wb, String fileName) {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		OutputStream ouputStream = null;

		try {
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch (Exception e) {
			log.info("创建Excel并写出失败", e);
		} finally {
			IOUtils.closeQuietly(ouputStream);
		}
	}

	public static <T> T getObject(HttpServletRequest request, Class<T> clazz) {
		Map<String, String[]> map = request.getParameterMap();
		String[] strings = new String[] {};
		if (map.containsKey("json")) {
			strings = map.get("json");
		}
		String country = "";
		String string = strings[0];
		if (!string.equals("{}")) {
			JSONObject fromObject = JSONObject.fromObject(string);
			String str = fromObject.get("param").toString();
			return JSON.parseObject(str, clazz);
		}
		return null;

	}

	public static Boolean validate(Map<String, Object> row, List<String> errorsMsg, String[] fields,
			Map<String, String> map) {
		Boolean flag = true;
		for (String string : fields) {
			String str = row.get(string) == null ? null : row.get(string).toString();
			if (StringUtils.isBlank(str)) {
				String temp = "第" + row.get("rowNumber") + "行" + map.get(string) + "为空";
				if (!errorsMsg.contains(temp)) {
					errorsMsg.add(temp);
				}
				flag = false;
			}
		}
		return flag;
	}
}
