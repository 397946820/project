package com.it.ocs.excel.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.it.ocs.excel.model.ExcelImportDemoModel;
import com.it.ocs.excel.service.AExcelImport;

@Service("excelImportDemo")
public class ExcelImportDemo extends AExcelImport{

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return true;
	}

	@Override
	protected boolean isExist(Map<String, Object> row,List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows,List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows,List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		// TODO Auto-generated method stub
		return 0;
	}

}
