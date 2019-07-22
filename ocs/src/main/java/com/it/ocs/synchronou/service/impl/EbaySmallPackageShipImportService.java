package com.it.ocs.synchronou.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.salesStatistics.dao.NoShipOrderDao;
import com.it.ocs.synchronou.model.EbaySmallPackageExportModel;
import com.it.ocs.synchronou.model.EbaySmallPackageNewExportModel;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.sys.model.UserModel;

@Service("ebaySmallPackageShipService")
public class EbaySmallPackageShipImportService extends AExcelImport{
	@Autowired
	private EbayOrderShipNumberUploadService ebayOrderShipNumberUploadService;
	
	@Autowired
	private NoShipOrderDao noShipOrderDao;
	
	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		String rowNumber = String.valueOf(row.get("rowNumber"));
		// 验证数据
		Object account = row.get("account");
		if (ValidationUtil.isNullOrEmpty(account)) {
			errorsMsg.add("第" + rowNumber + "行ebay账号为空");
			return false;
		}
		
		Object orderId = row.get("orderId");
		if (ValidationUtil.isNullOrEmpty(orderId)) {
			errorsMsg.add("第" + rowNumber + "行订单号为空");
			return false;
		}
		
		Object transactionId = row.get("transactionId");
		if (ValidationUtil.isNullOrEmpty(transactionId)) {
			errorsMsg.add("第" + rowNumber + "行交易号为空");
			return false;
		}
		
		Object itemId = row.get("itemId");
		if (ValidationUtil.isNullOrEmpty(itemId)) {
			errorsMsg.add("第" + rowNumber + "行物品号为空");
			return false;
		}
		
		
		Object carrier1 = row.get("carrier");
		if (ValidationUtil.isNullOrEmpty(carrier1)) {
			errorsMsg.add("第" + rowNumber + "行运输渠道为空");
			return false;
		}
		
		Object tranckingNumber1 = row.get("tranckingNumber");
		if (ValidationUtil.isNullOrEmpty(tranckingNumber1)) {
			errorsMsg.add("第" + rowNumber + "行快递号为空");
			return false;
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
		Subject currentUser = SecurityUtils.getSubject();
		UserModel user = null;
		if (null != currentUser.getSession().getAttribute("user")) {
			user = (UserModel) currentUser.getSession().getAttribute("user");
		} else {
			throw new RuntimeException();
		}
		for(Map<String, Object> map : rows){
			String leOrderId =  map.get("orderId").toString();
	
			//补发单不需要上传
			if(leOrderId.indexOf("W_")>-1){
				String orderSaleRecordNumber = leOrderId.substring(3,leOrderId.lastIndexOf("_"));
				String times = leOrderId.substring(leOrderId.lastIndexOf("_")+1);
				Map<String,String> orderParam = new HashMap<>();
				//补发单更新信息
				orderParam.put("account", map.get("account").toString());
				orderParam.put("orderId", orderSaleRecordNumber);
				orderParam.put("trackingNo", map.get("tranckingNumber").toString());
				orderParam.put("carrier", map.get("carrier").toString());
				orderParam.put("times", times);
				orderParam.put("platform", "ebay");
				orderParam.put("itemId", map.get("itemId").toString());
				ebayOrderShipNumberUploadService.updateWOrderInfo(orderParam);
				continue;
			}
			
			
			map.put("orderId", map.get("orderId"));
			map.put("transactionId", map.get("transactionId"));
			map.put("account",map.get("account"));
			map.put("itemId", map.get("itemId"));
			map.put("trackingNo", map.get("tranckingNumber"));
			map.put("carrier", map.get("carrier"));
			map.put("uploadBy",user.getId());
			map.put("type", "小包发货上传跟踪号");
			//插入上传记录
			ebayOrderShipNumberUploadService.addUploadData(map);
		}
		return rows.size();
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return EbaySmallPackageNewExportModel.class;
	}

}
