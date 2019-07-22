package com.it.ocs.common.util;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it.ocs.compare.model.AmazonExcelModel;
import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.excel.utils.ExcelUtils;

public class ExcelTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		Map<String,String> header = new HashMap<>();
		List<Field> fields = ExcelUtils.getField(AmazonExcelModel.class);
		for(Field field : fields) {
			ExcelLink el = field.getAnnotation(ExcelLink.class);
			header.put(el.title(),field.getName());
		}
		ExcelImportUtil eiu = new ExcelImportUtil(header);
//		eiu.init(new FileInputStream("D:\\work\\订单数据核对\\亚马逊\\US.xlsx"));
		List<AmazonExcelModel> excels = eiu.bindToModels(AmazonExcelModel.class, false);
		if (eiu.hasError()) {
			System.out.println(eiu.getError().toString());
		}
	}


}
