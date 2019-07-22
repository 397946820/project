package com.it.ocs.cal.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.it.ocs.cal.common.FileUtils;
import com.it.ocs.common.util.CollectionUtil;


/**
 * 
 * @Description: excel导出封装类
 */
public class ExportExcelUtil {
	/**
	 * 
	 * @param response
	 * @param request
	 * @param fileName		输出的文件名
	 * @param list			要导出的数据
	 * @param cls
	 * @param one			价格管理计划页面需要的判断
	 * @param strings		列的权限的判断
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unused")
	public static <Q> void writeExcel(HttpServletResponse response, HttpServletRequest request, String fileName,
			List<Q> list, Class<Q> cls, String one,List<String> strings)
					throws IOException, IllegalArgumentException, IllegalAccessException {
		SXSSFWorkbook wb = new SXSSFWorkbook(100);  

		Field[] fields = ReflectUtils.getAllField(cls);
		ArrayList<String> headList = new ArrayList<String>();

		for (Field f : fields) {
			ExcelRead field = f.getAnnotation(ExcelRead.class);
			if (field != null) {
				headList.add(field.title());
				if (field.title().equals("建议售价")) {
					if (cls.getName().equals("com.it.ocs.cal.model.PricePlanModel")) {
						if (one.equals("template")) {
							break;
						}
					}
				}
			}
		}

		CellStyle style = getCellStyle(wb);
		Sheet sheet = wb.createSheet("第1个工作簿");
		/**
		 * 设置Excel表的第一行即表头
		 */
		Row row = null; 
		row	= getRow(sheet, headList,style,strings);
		
		int z = 0;   //页行号
		
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				if(i%300000==0){  
					sheet = wb.createSheet("第" + ((i/300000) + 1) +"个工作簿");//建立新的sheet对象  
					sheet = wb.getSheetAt(i/300000);        //动态指定当前的工作表  
					row = getRow(sheet, headList,style,strings);
					z = 0;      //每当新建了工作表就将当前工作表的行号重置为0  
				}  
			}
			Row rowdata = sheet.createRow(++z);// 创建数据行
			Q q = list.get(i);
			Field[] ff = ReflectUtils.getAllField(q.getClass());
			int j = 0;
			for (Field f : ff) {
				ExcelRead field = f.getAnnotation(ExcelRead.class);
				if (field == null) {
					continue;
				}
				if(!CollectionUtil.isNullOrEmpty(strings)) {
					if(strings.contains(field.title().toString())) {
						setValue(f,q,j,rowdata);
						j++;
					}
				} else {
					setValue(f,q,j,rowdata);
					j++;
				}
				if (field.title().equals("建议售价")) {
					if (cls.getName().equals("com.it.ocs.cal.model.PricePlanModel")) {
						if (one.equals("template")) {
							break;
						}
					}
				}
			}
		}
		fileName = FileUtils.encodeDownloadFilename(fileName, request.getHeader("user-agent"));
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		OutputStream ouputStream = null;
		try {
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} finally {
			ouputStream.close();
		}
	}

	private static <Q> void setValue(Field f, Q q, int j, Row rowdata) throws IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		Object obj = f.get(q);
		Cell cell = rowdata.createCell((short) j);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		if (obj == null) {
			cell.setCellValue("");
		}
		// 当数字时
		if (obj instanceof Integer) {
			cell.setCellValue((Integer) obj);
		} else if (obj instanceof String) {
			// 当为字符串时
			cell.setCellValue((String) obj);
		} else if (obj instanceof Boolean) {
			// 当为布尔时
			cell.setCellValue((Boolean) obj);
		} else if (obj instanceof Date) {
			// 当为时间时
			cell.setCellValue(Utils.dateToString((Date) obj));
		} else if (obj instanceof Calendar) {
			// 当为时间时
			cell.setCellValue((Calendar) obj);
		} else if (obj instanceof Double) {
			// 当为小数时
			cell.setCellValue((Double) obj);
		} else if (obj instanceof Long) {
			// 当为小数时
			cell.setCellValue((Long) obj);
		}
	}

	/** 
     * @Description:设置表头样式 
     */  
    public static CellStyle getCellStyle(SXSSFWorkbook wb)  
    {  
        CellStyle style = wb.createCellStyle(); 
        Font font = wb.createFont();  
        font.setFontName("宋体");  
        font.setFontHeightInPoints((short) 12);//设置字体大小    
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//加粗    
        style.setFillForegroundColor((short) 10);// 设置背景色    
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);  
        style.setAlignment(CellStyle.SOLID_FOREGROUND);//让单元格居中    
        style.setAlignment(CellStyle.ALIGN_CENTER);// 左右居中     
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中     
        style.setWrapText(true);//设置自动换行    
        style.setFont(font);  
        return style;  
    }  
	
	public static Row getRow(Sheet sheet,ArrayList<String> headList,CellStyle style, List<String> strings) {
		Row row = sheet.createRow(0);
		int j = 0;
		for (int i = 0; i < headList.size(); i++) {
			if(!CollectionUtil.isNullOrEmpty(strings)) {
				if(strings.contains(String.valueOf(headList.get(i)))) {
					Cell headCell = row.createCell(j);  
					row.createCell((short) j).setCellType(Cell.CELL_TYPE_STRING);
					headCell.setCellStyle(style);//设置表头样式
					row.createCell((short) j).setCellValue(String.valueOf(headList.get(i)));
					sheet.setColumnWidth(j, 15 * 196); 
					j++;
				}
			} else {
				Cell headCell = row.createCell(j);  
				row.createCell((short) j).setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);//设置表头样式
				row.createCell((short) j).setCellValue(String.valueOf(headList.get(i)));
				sheet.setColumnWidth(j, 15 * 196); 
				j++;
			}
		}
		return row;
	}

}
