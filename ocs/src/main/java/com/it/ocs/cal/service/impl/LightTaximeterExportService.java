package com.it.ocs.cal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.ILightTaximeterDao;
import com.it.ocs.cal.model.LightTaximeterModel;
import com.it.ocs.cal.service.ILightTaximeterService;
import com.it.ocs.cal.service.impl.support.LightTaximeterSupport;
import com.it.ocs.cal.vo.LightTaximeterVo;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.ColumnUtils;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.excel.utils.ExcelUtils;
import com.it.ocs.listener.ProjectApplicationContext;
import com.it.ocs.sys.service.impl.PermissionService;

@Service("lightTaximeterExport")
public class LightTaximeterExportService extends AExcelExport {

	@Autowired
	private ILightTaximeterDao lightTaximeterDao;

	@Autowired
	private ILightTaximeterService lightTaximeterService;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		List<Map<String, Object>> result = new ArrayList<>();
		LightTaximeterVo model = ExcelUtils.getObject(request, LightTaximeterVo.class);
		List<String> countrys = lightTaximeterService.getCountrys(model);
		List<Map<String, Object>> list = lightTaximeterDao.queryByObjectAndCountrys(model, countrys);
		LightTaximeterSupport.getList2(list);
		List<String> strings = ColumnUtils.getPrivilegeColumn("GWJJQ", LightTaximeterModel.class);
		CollectionUtil.each(list, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> obj) {
				Map<String, Object> map = new HashMap<>();
				for (String string : strings) {
					map.put(string.toUpperCase(), obj.get(string.toUpperCase()));
				}
				result.add(map);
			}
		});
		return result;
	}

	@Override
	protected void init(HttpServletRequest request) {
		String fileName = "官网、Ebay计价器数据.xlsx";
		super.initModel(LightTaximeterModel.class, fileName);
	}

}
