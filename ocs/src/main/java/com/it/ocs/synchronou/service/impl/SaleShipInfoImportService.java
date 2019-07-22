package com.it.ocs.synchronou.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.salesStatistics.model.UKNoShipOrderExportModel;
import com.it.ocs.synchronou.dao.IEbaySellerListDao;
import com.it.ocs.synchronou.model.EbayAccountModel;
import com.it.ocs.synchronou.model.EbayOrderTrackNumberImportModel;
import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.RequestModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.util.UTCTimeUtils;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.sys.model.UserModel;

@Service("saleShipInfoImport")
public class SaleShipInfoImportService extends AExcelImport {
	private final static Logger log = Logger.getLogger(SaleShipInfoImportService.class);

	@Autowired
	private IEbaySellerListDao ebaySellerListDao;

	@Autowired
	private EbayOrderShipNumberUploadService ebayOrderShipNumberUploadService;

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		if (row.get("stockCode") != null && row.get("stockCode").toString().toUpperCase().equals("CTN")) {
			return false;
		}
		Object orderId = row.get("orderNo");
		if (ValidationUtil.isNullOrEmpty(orderId)) {
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行订单ID Order No为空");
			return false;
		}
		// 快递号不为空
		Object trackingNumber = row.get("trackingNo");
		if (ValidationUtil.isNullOrEmpty(trackingNumber)) {
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行运输号 tracking No为空");
			return false;
		}
		if (!ValidationUtil.checkSpecialCharacter(String.valueOf(trackingNumber))) {
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行运输号 tracking No不能包含特殊字符(较大数字EXCEL会科学计算法)");
			return false;
		}
		Object carrier = row.get("carrier");
		if (ValidationUtil.isNullOrEmpty(carrier)) {
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行运输服务名称 carrier为空");
			return false;
		}else{
			String str = carrier.toString();
			if("RM/RoyalMail".indexOf(str.trim())== -1){
				return false;
			}
		}
		if (ValidationUtil.isNullOrEmpty(row.get("platformAccount"))) {
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行Ebay账号 Platform Account为空");
			return false;
		}
		if (ValidationUtil.isNullOrEmpty(row.get("transactionId"))) {
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行交易号 Transaction Id为空");
			return false;
		}
		if (ValidationUtil.isNullOrEmpty(row.get("itemId"))) {
			errorsMsg.add("第" + String.valueOf(row.get("rowNumber")) + "行物品号 item Id为空");
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
		// 获取当前操作人
		Subject currentUser = SecurityUtils.getSubject();
		UserModel user = null;
		if (null != currentUser.getSession().getAttribute("user")) {
			user = (UserModel) currentUser.getSession().getAttribute("user");
		} else {
			log.info("当前用户未登陆,请登陆后操作");
			throw new RuntimeException();
		}
		// 同步到ebay
		for (Map<String, Object> map : rows) {
			String orderId = map.get("orderNo").toString();
			//补发单
			if(orderId.indexOf("W_")> -1){
				Map<String,String> orderParam = new HashMap<>();
				orderParam.put("orderId", orderId.substring(2,orderId.lastIndexOf("_")));
				orderParam.put("account", map.get("platformAccount").toString());
				orderParam.put("trackingNo", map.get("trackingNo").toString());
				orderParam.put("carrier", map.get("carrier").toString());
				orderParam.put("times",orderId.substring(orderId.lastIndexOf("_")+1));
				orderParam.put("platform", "ebay");
				orderParam.put("itemId", map.get("itemId").toString());
				ebayOrderShipNumberUploadService.updateWOrderInfo(orderParam);
				continue;
			}
			//线下单
			if(orderId.indexOf("OCS")> -1){
				Map<String,String> orderParam = new HashMap<>();
				orderParam.put("orderId", orderId);
				orderParam.put("account", map.get("platformAccount").toString());
				orderParam.put("trackingNo", map.get("trackingNo").toString());
				orderParam.put("carrier", map.get("carrier").toString());
				orderParam.put("platform", "ebay");
				orderParam.put("sku", map.get("itemId").toString());
				ebaySellerListDao.updateOCSOrderInfo(orderParam);
				continue;
			}
			map.put("orderId", map.get("orderNo"));
			map.put("account",map.get("platformAccount"));
			map.put("uploadBy",user.getId());
			map.put("type", "英国上传跟踪号");
			// 插入数据库
			ebaySellerListDao.addTrackNumberUploadRecord(map);
		}

		return rows.size();
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return UKNoShipOrderExportModel.class;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		// TODO Auto-generated method stub
		return 0;
	}

}
