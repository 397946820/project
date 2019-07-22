package com.it.ocs.ukinventory.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.IUserService;
import com.it.ocs.ukinventory.dao.IInventoryUKDAO;
import com.it.ocs.ukinventory.model.InventoryUKModel;
import com.it.ocs.ukinventory.vo.InventoryUKVO;
@Service("inventoryUpload")
public class InventoryUploadService extends AExcelImport {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private IInventoryUKDAO inventoryDAO;
	@Autowired
	private IUserService userService;

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return true;
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		InventoryUKVO paramVO = new InventoryUKVO();
		if (null != row.get("sku")) {
			paramVO.setSku(row.get("sku").toString());
		}
		paramVO.setInventoryUploadDate(sdf.format(new Date()));
		List<InventoryUKModel> models = inventoryDAO.query(paramVO);
		CollectionUtil.each(models, new IAction<InventoryUKModel>() {
			@Override
			public void excute(InventoryUKModel model) { 
				inventoryDAO.delete(model.getId());
			}
		});
		return false;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		UserModel concurrentUser = userService.getConcurrentUser();
		CollectionUtil.each(rows, new IAction<Map<String,Object>>() {
			@Override
			public void excute(Map<String, Object> row) {
				row.put("lastUpdateBy", concurrentUser.getId());
				row.put("createBy", concurrentUser.getId());
				String sku = row.get("sku").toString();
				if (sku.contains(".")) {
					row.put("sku", sku.substring(0, sku.indexOf(".")));
				}
			}
		});
		if (!CollectionUtil.isNullOrEmpty(rows)) {
			return inventoryDAO.batchInsert(rows);
		}
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		return 0;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return InventoryUKModel.class;
	}
}
