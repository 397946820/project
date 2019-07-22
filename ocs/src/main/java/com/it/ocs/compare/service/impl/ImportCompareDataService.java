package com.it.ocs.compare.service.impl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.compare.dao.IAmazonOrderCompareDAO;
import com.it.ocs.compare.model.AmazonExcelModel;
import com.it.ocs.compare.service.IImportCompareDataService;
import com.it.ocs.excel.annotation.ExcelLink;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.excel.utils.ExcelUtils;
@Service
public class ImportCompareDataService extends AExcelImport implements IImportCompareDataService {
	@Autowired
	private IAmazonOrderCompareDAO compareDAO;
	private SimpleDateFormat UTC_FORMATE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'");
	@Override
	public void importAmazonCompareData(MultipartFile file) {
		super.excute(file, null);
	}
	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return row.containsKey("orderId") && null != row.get("orderId")
				&& row.containsKey("sku") && null != row.get("sku");
	}
	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		try {
			typeConvert(row);
			AmazonExcelModel acModel = BeanConvertUtil.mapToObject(row, AmazonExcelModel.class);
			List<AmazonExcelModel> models = compareDAO.queryByParam(acModel);
			return !CollectionUtil.isNullOrEmpty(models);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	private void typeConvert(Map<String,Object> row) throws ParseException {
		List<Field> fieldList = ExcelUtils.getField(getTemplateClazz(null));
		for (Field field : fieldList) {
			if (field.getType().getName().equals("java.util.Date")) {
				row.put(field.getName(), UTC_FORMATE.parse(row.get(field.getName()).toString()));
			}
		}
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		List<AmazonExcelModel> models = new ArrayList<>();
		CollectionUtil.each(rows, new IAction<Map<String,Object>>() {
			@Override
			public void excute(Map<String, Object> obj) {
				AmazonExcelModel model = BeanConvertUtil.mapToObject(obj, AmazonExcelModel.class);
				models.add(model);
			}
		});
		if (!CollectionUtil.isNullOrEmpty(models)) {
			return compareDAO.batchAdd(models);
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		return 0;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		rows = null;
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return AmazonExcelModel.class;
	}

}
