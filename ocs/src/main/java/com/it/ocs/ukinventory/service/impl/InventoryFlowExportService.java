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
import com.it.ocs.ukinventory.dao.IInventoryFlowUKDAO;
import com.it.ocs.ukinventory.model.InventoryFlowUKModel;
import com.it.ocs.ukinventory.vo.InventoryFlowUKVO;

@Service("inventoryFlowExport")
public class InventoryFlowExportService extends AExcelDynamicExport {
	@Autowired
	private IInventoryFlowUKDAO inventoryFlowDAO;

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
		InventoryFlowUKVO paramVO = BeanConvertUtil.mapToObject(param, InventoryFlowUKVO.class);
		List<InventoryFlowUKModel> models = inventoryFlowDAO.query(paramVO);
		List<Map<String, Object>> result = BeanConvertUtil.listToMap(models);
		CollectionUtil.each(result, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> obj) {
				if (obj.containsKey("flowCreateAt") && null != obj.get("flowCreateAt"))
					obj.put("flowCreateAt", sdf.format((Date) obj.get("flowCreateAt")));
			}
		});
		return result;
	}

	@Override
	protected void init(HttpServletRequest request) {
		String filename = String.format("%s_%s.xlsx", "UKINVENTORYFLOW",
				new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		super.initModel(InventoryFlowUKModel.class, filename, null);
	}

}
