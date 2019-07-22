package com.it.ocs.salereport.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.salereport.model.InventoryWarningSetModel;
import com.it.ocs.salereport.utils.StockCodeUtil;
import com.it.ocs.sys.dao.IInventoryWarningDao;
import com.it.ocs.sys.model.UserModel;

@Service("inventoryWarningSetImportService")
public class InventoryWarningSetImportService extends AExcelImport{
	private static final Logger log = Logger.getLogger(InventoryWarningSetImportService.class);
	@Autowired
	private IInventoryWarningDao iInventoryWarningDao;
	
	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		Object sku = row.get("sku");
		if(null == sku || "".equals(sku.toString())){
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行SKU为空");
			return false;
		}else{
			String skun = sku.toString();
			if(skun.indexOf(".")> -1){
				errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行SKU必须为文本数据");
				return false;
			}
		}
		Object ship_type = row.get("ship_type");
		if(null == ship_type || "".equals(ship_type.toString())){
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行运输方式为空");
			return false;
		}else{
			String shipType = ship_type.toString();
			if("AFSFCO".indexOf(shipType) == -1){
				errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行运输方式只能为AF、SF、CO");
				return false;
			}
		}

		Object scode = row.get("scode");
		if(null == scode || "".equals(scode.toString())){
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行仓库编码为空");
			return false;
		}else{
			String scodestr = scode.toString();
			if(StockCodeUtil.format(scodestr)==0){
				errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行仓库编码不存在");
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		return true;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		long userId = getCurUserId();
		for(Map<String, Object> row : rows){
			row.put("userId", userId);
			if(this.iInventoryWarningDao.inventorySetExist(row) > 0){
				this.iInventoryWarningDao.updateInventoryWarningSet(row);
			}else{
				this.iInventoryWarningDao.addInventoryWarningSet(row);
			}
			
		}
		return rows.size();
	}

	private long getCurUserId() {
		//获取当前用户
		Subject currentUser = SecurityUtils.getSubject();
		UserModel user = null;
		if (null != currentUser.getSession().getAttribute("user")) {
			user = (UserModel) currentUser.getSession().getAttribute("user");
		} else {
			log.info("当前用户未登陆,请登陆后操作");
			throw new RuntimeException();
		}
		return user.getId();
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return InventoryWarningSetModel.class;
	}

}
