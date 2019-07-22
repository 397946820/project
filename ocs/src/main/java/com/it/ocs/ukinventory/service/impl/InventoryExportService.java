package com.it.ocs.ukinventory.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelDynamicExport;
import com.it.ocs.ukinventory.dao.IInventoryUKDAO;
import com.it.ocs.ukinventory.model.InventoryUKModel;
import com.it.ocs.ukinventory.vo.InventoryUKVO;
@Service("inventoryExport")
public class InventoryExportService extends AExcelDynamicExport {
	@Autowired
	private IInventoryUKDAO inventoryUKDAO;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Map<String, Object> param = new HashMap<String, Object>();
		CollectionUtil.each(request.getParameterMap().entrySet(), new IAction<Entry<String, String[]>>() {
			@Override
			public void excute(Entry<String, String[]> entry) {
				param.put(entry.getKey(), CollectionUtil.isNullOrEmpty(entry.getValue()) ? null : entry.getValue()[0]);
			}
		});
		InventoryUKVO paramVO = BeanConvertUtil.mapToObject(param, InventoryUKVO.class);
		List<InventoryUKModel> models = inventoryUKDAO.query(paramVO);
		List<Map<String, Object>> result = BeanConvertUtil.listToMap(models);
		CollectionUtil.each(result, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> obj) {
				if (obj.containsKey("uploadDate") && null != obj.get("uploadDate"))
					obj.put("uploadDate", sdf.format((Date) obj.get("uploadDate")));
			}
		});
		return result;
	}

	@Override
	protected void init(HttpServletRequest request) {
		String filename = String.format("%s_%s.xlsx", "UKINVENTORY",
				new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		super.initModel(InventoryUKModel.class, filename, null);
	}

}
