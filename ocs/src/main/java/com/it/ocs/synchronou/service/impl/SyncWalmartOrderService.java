package com.it.ocs.synchronou.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.synchronou.dao.ISyncWalmartOrderDao;
import com.it.ocs.synchronou.model.ParseWalmartOrderXMLModel;
import com.it.ocs.synchronou.model.WalmartAccountModel;
import com.it.ocs.synchronou.service.ISyncWalmartOrderService;
import com.it.ocs.synchronou.util.UTCTimeUtils;
import com.it.ocs.walmart.model.WalmartRequestModel;
import com.it.ocs.walmart.utils.WalmartHttpUtil;

@Service
public class SyncWalmartOrderService implements ISyncWalmartOrderService {
	
	private final static Logger log = Logger.getLogger(SyncWalmartOrderService.class);
	
	@Autowired
	private ISyncWalmartOrderDao syncWalmartOrderDao;
	
	public void syncOrder(){
		List<WalmartAccountModel> accounts = syncWalmartOrderDao.getAccounts();
		for(WalmartAccountModel account:accounts){
			syncOrderByAccount(account,UTCTimeUtils.getUTCTimeStr(-24),UTCTimeUtils.getUTCTimeStr(0));
		}
	}
	
	public void syncRepairOrder(){
		List<WalmartAccountModel> accounts = syncWalmartOrderDao.getAccounts();
		for(WalmartAccountModel account:accounts){
			String fromTime = UTCTimeUtils.getUTCTimeStr(-24*35);
			fromTime = fromTime.replace("T", " ");
			fromTime = fromTime.replace("Z", "");
			List<Map<String,String>> timeList = UTCTimeUtils.splitTimeForOneHourList(fromTime, 24);
			for(Map<String,String> map : timeList){
				syncOrderByAccount(account,map.get("fromTime"),map.get("toTime"));
				log.info("walmart订单按照修补("+account.getAccount()+"):"+map.get("fromTime")+"--"+map.get("toTime"));
			}
			
		}
	}
	
	private void syncOrderByAccount(WalmartAccountModel account,String startTime,String endTime) {
		WalmartRequestModel wm = new WalmartRequestModel(account.getAccount(),account.getUrl(),account.getToken(),WalmartRequestModel.GET,account.getNextUrl());
		Map<String,String> map = new HashMap<>();
		map.put("createdStartDate", startTime);
		map.put("createdEndDate", endTime);
		map.put("limit", "100");
		wm.setRequestParam(map);
		String response = WalmartHttpUtil.httpGet(wm);
		//同步失败，记录日志信息
		if(null == response){
			log.info("沃尔玛同步订单数据解析失败----startTime："+startTime +"---endTime:"+endTime);
			return ;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(response);
			ParseWalmartOrderXMLModel parse = new ParseWalmartOrderXMLModel(doc,"http://walmart.com/mp/v3/orders");
			List<Map<String, Object>> data = parse.getResult();
			saveWalmartData(data);
			//根据nextCursor获取分页数据
			String nextCursor = parse.getNextCursor();
			while(null != nextCursor&&!"".equals(nextCursor)){
				nextCursor = syncOrderByNextCursor(wm,nextCursor);
			}
			
		} catch (DocumentException e) {
			log.info("沃尔玛订单数据解析失败",e);
		}
	}

	private String syncOrderByNextCursor(WalmartRequestModel wm, String nextCursor) {
		wm.setNextCursor(nextCursor);
		String response = WalmartHttpUtil.httpGet(wm);
		//同步失败，记录日志信息
		if(null == response){
			log.info("沃尔玛同步订单数据解析失败----nextCursor："+nextCursor);
			return null;
		}
		Document doc = null;
		ParseWalmartOrderXMLModel parse = null;
		try {
			doc = DocumentHelper.parseText(response);
			parse = new ParseWalmartOrderXMLModel(doc,"http://walmart.com/mp/v3/orders");
			List<Map<String, Object>> data = parse.getResult();
			saveWalmartData(data);
			
		} catch (DocumentException e) {
			log.info("沃尔玛订单数据解析失败",e);
		}
		
		return parse.getNextCursor();
	}

	public void saveWalmartData(List<Map<String, Object>> data) {
		for(Map<String, Object> order : data){
			String purchaseOrderId = (String)order.get("purchaseOrderId");
			Integer id = syncWalmartOrderDao.getWalamrtId(purchaseOrderId);
			if(null == id||id== 0){
				id = syncWalmartOrderDao.getId();
				order.put("id", id);
				syncWalmartOrderDao.addOrder(order);
			}else{
				order.put("id", id);
				syncWalmartOrderDao.updateOrder(order);
			}
			//详情
			List<Map<String, Object>> lines = (List<Map<String, Object>>)order.get("lines");
			for(Map<String, Object> line:lines){
				line.put("parentId", String.valueOf(id));
				saveWalmartLinesData(line);
			}
			
		}
	}

	private void saveWalmartLinesData(Map<String, Object> line) {
		String parentId = (String)line.get("parentId");
		String lineNumber = (String)line.get("lineNumber");
		if(syncWalmartOrderDao.lineIsExist(parentId,lineNumber) == 0){
			syncWalmartOrderDao.addOrderLine(line);
		}else{
			syncWalmartOrderDao.updateOrderLine(line);
		}
		
	}
}
