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
import com.it.ocs.salereport.model.InventoryWarningCoeffModel;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.sys.dao.IInventoryWarningDao;
import com.it.ocs.sys.model.UserModel;

@Service("inventoryWarningCoeffImportService")
public class InventoryWarningCoeffImportService extends AExcelImport{
	private static final Logger log = Logger.getLogger(InventoryWarningCoeffImportService.class);
	@Autowired
	private IInventoryWarningDao iInventoryWarningDao;
	
	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		Object field = row.get("field");
		if (ValidationUtil.isNullOrEmpty(field)) {
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行系列为空");
			return false;
		}
		if (ValidationUtil.isNullOrEmpty(row.get("platform"))) {
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行平台为空");
			return false;
		}else{
			String platform = String.valueOf(row.get("platform"));
			if(!"US".equals(platform)&&!"EU".equals(platform)){
				errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行平台只能为US、EU");
				return false;
			}
		}
		if (ValidationUtil.isNullOrEmpty(row.get("ship_type"))) {
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行运输方式为空");
			return false;
		}else{
			String ship_type = String.valueOf(row.get("ship_type"));
			if(!"AF".equals(ship_type)&&!"SF".equals(ship_type)&&!"CO".equals(ship_type)){
				errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行运输方式只能为AF,SF,CO");
				return false;
			}
		}
		if (ValidationUtil.isNullOrEmpty(row.get("coeff"))) {
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行预警系数为空");
			return false;
		}
		return true;
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		//获取当前用户
		Subject currentUser = SecurityUtils.getSubject();
		UserModel user = null;
		if (null != currentUser.getSession().getAttribute("user")) {
			user = (UserModel) currentUser.getSession().getAttribute("user");
		} else {
			log.info("当前用户未登陆,请登陆后操作");
			throw new RuntimeException();
		}
		for(Map<String,Object> map:rows){
			map.put("userId", user.getId());
			if(iInventoryWarningDao.countInventoryWarningCoeff(map)>0){
				iInventoryWarningDao.updateInventoryWarningCoeff(map);
			}else{
				iInventoryWarningDao.addInventoryWarningCoeff(map);
			}
		}
		return rows.size();
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return InventoryWarningCoeffModel.class;
	}

}
