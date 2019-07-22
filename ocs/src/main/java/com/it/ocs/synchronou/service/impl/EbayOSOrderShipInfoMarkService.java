package com.it.ocs.synchronou.service.impl;

import java.util.ArrayList;
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
import com.it.ocs.synchronou.model.EbayOSOrderNoShipModel;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.sys.model.UserModel;

@Service("ebayOSOrderShipInfoMarkService")
public class EbayOSOrderShipInfoMarkService extends AExcelImport{
	@Autowired
	private EbayOrderShipNumberUploadService ebayOrderShipNumberUploadService;
	
	@Autowired
	private NoShipOrderDao noShipOrderDao;
	
	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		String rowNumber = String.valueOf(row.get("rowNumber"));
		String value = "";
		// 验证数据
		Object leOrderId = row.get("leOrderId");
		if (ValidationUtil.isNullOrEmpty(leOrderId)) {
			errorsMsg.add("第" + rowNumber + "行LE销售订单号为空");
			return false;
		}else{
			//数据为数字类型自动转换字符串
			value = formatString(leOrderId);
			row.put("leOrderId", value);
		}
		Object iSaleRecordNumber = row.get("iSaleRecordNumber");
		if (ValidationUtil.isNullOrEmpty(iSaleRecordNumber)) {
			errorsMsg.add("第" + rowNumber + "行订单详情SaleRecordNumber为空");
			return false;
		}else{
			value = formatString(iSaleRecordNumber);
			row.put("iSaleRecordNumber", value);
		}
		
		Object shipService = row.get("shipService");
		if (ValidationUtil.isNullOrEmpty(shipService)) {
			errorsMsg.add("第" + rowNumber + "行运输渠道为空");
			return false;
		}
		return true;
	}

	private String formatString(Object leOrderId) {
		String value = String.valueOf(leOrderId).trim();
		if(value.endsWith(".0")||value.endsWith(".00")){
			value = value.substring(0, value.lastIndexOf(".")-1);
		}
		return value;
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
		//orderId transactionId account itemId trackingNo carrier
		// 获取当前操作人
		Subject currentUser = SecurityUtils.getSubject();
		UserModel user = null;
		if (null != currentUser.getSession().getAttribute("user")) {
			user = (UserModel) currentUser.getSession().getAttribute("user");
		} else {
			throw new RuntimeException();
		}
		for(Map<String, Object> map : rows){
			String leOrderId =  map.get("leOrderId").toString();
			String accountShort = leOrderId.substring(0, 1);
			String orderSaleRecordNumber = leOrderId.substring(1);
			String orderInfoSaleRecordNumber = map.get("iSaleRecordNumber").toString();
			Map<String,String> orderParam = new HashMap<>();
			orderParam.put("accountShort", accountShort);
			orderParam.put("orderSaleRecordNumber",orderSaleRecordNumber);
			orderParam.put("orderInfoSaleRecordNumber", orderInfoSaleRecordNumber);
			Map<String,Object> order =  noShipOrderDao.getEBayOrderInfo(orderParam);
			if(null == order){
				errorsMsg.add("LE订单号："+leOrderId+"的订单不存在");
				continue;
			}
			map.put("orderId", order.get("ORDERID"));
			map.put("transactionId", order.get("TRANSACTIONID"));
			map.put("account",order.get("ACCOUNT"));
			map.put("itemId", order.get("ITEMID"));
			map.put("trackingNo", "");
			map.put("carrier", map.get("shipService"));
			map.put("uploadBy",user.getId());
			map.put("type", "美国OS单标记发货");
			//插入上传记录
			ebayOrderShipNumberUploadService.addUploadData(map);
		}
		return rows.size();//ebayOrderShipNumberUploadService.uploadShipNumber(shipInfos,errorsMsg);
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return EbayOSOrderNoShipModel.class;
	}
}
