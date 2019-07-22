package com.it.ocs.amazon.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.it.ocs.amazon.dao.IInboundShipmentDAO;
import com.it.ocs.amazon.model.AmazonRequestConfig;
import com.it.ocs.amazon.utils.AmazonHttpUtil;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.TimeConvertUtil;
import com.it.ocs.synchronou.dao.ISyncAmazonOrderDao;
import com.it.ocs.synchronou.model.AmazonAccountModel;
import com.it.ocs.synchronou.model.ParseXMLModel2;
import com.it.ocs.synchronou.model.XMLNode;

@Service
public class InboundShipmentService {
	// https://www.cnblogs.com/wangzhongqiu/p/6402940.html
	@Autowired
	private IInboundShipmentDAO inboundShipmentDAO;
	private final static Logger log = Logger.getLogger(InboundShipmentService.class);
	@Autowired
	private ISyncAmazonOrderDao syncAmazonOrderDao;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String[] SHIPMENT_STATUS_LIST = new String[] { "WORKING", "SHIPPED", "IN_TRANSIT", "DELIVERED",
			"CHECKED_IN", "RECEIVING", "CLOSED", "CANCELLED", "DELETED", "ERROR" };

	private Map<String, String> getDefaultParam() {
		Map<String, String> queryMap = Maps.newConcurrentMap();
		for (int i = 0; i < SHIPMENT_STATUS_LIST.length; i++) {
			queryMap.put("ShipmentStatusList.member." + (i + 1), SHIPMENT_STATUS_LIST[i]);
		}
		return queryMap;
	}

	private Map<String, String> getRequestTimeParam(boolean isIncrement) {
		Map<String, String> queryMap = getDefaultParam();
		Map<String, Object> lastModel = inboundShipmentDAO.getLastUpdate();
		if (!isIncrement) {
			return getDefaultParam();
		}
		Date lastDate = (Date) lastModel.get("UPDATED_AT");
		try {
			String beforeTime = TimeConvertUtil.timeConvert(sdf.format(lastDate), "Asia/Shanghai", "UTC");
			String endTime = TimeConvertUtil.timeConvert(sdf.format(new Date()), "Asia/Shanghai", "UTC");
			queryMap.put("LastUpdatedAfter", timeConvertUTCFormat(beforeTime));
			queryMap.put("LastUpdatedBefore", timeConvertUTCFormat(endTime));
		} catch (ParseException e) {
			log.error("time format error,lastDate:" + lastDate);
			e.printStackTrace();
		}
		return queryMap;
	}

	private String timeConvertUTCFormat(String time) {
		if (Strings.isNullOrEmpty(time))
			return time;
		return time.replace(" ", "T") + "+00:00";
	}

	public void downloadInboundShipment() {
		CollectionUtil.each(this.syncAmazonOrderDao.getAmazonAccounts(), new IAction<AmazonAccountModel>() {
			public void excute(AmazonAccountModel account) {
				AmazonRequestConfig config = new AmazonRequestConfig(account, "ListInboundShipments", "2010-10-01");
				config.setUrl(account.getUrl() + "/FulfillmentInboundShipment");
				config.setQueryParameters(getRequestTimeParam(false));
				String resp = AmazonHttpUtil.amazonPOST(config);
				Map<String, Object> result = parseAmazonFBAInventoryResponseXml(account, resp);
				handleNextToken(account, result);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void handleData(Map<String, Object> requestResult) {
		if (requestResult.containsKey("data")) {
			List<Map<String, Object>> datas = (List<Map<String, Object>>) requestResult.get("data");
			List<Map<String, Object>> insertDatas = screenNeedHandleData(datas);
			CollectionUtil.each(datas, new IAction<Map<String, Object>>() {
				@Override
				public void excute(Map<String, Object> data) {
					inboundShipmentDAO.update(data);
				}
			});
			if (!CollectionUtil.isNullOrEmpty(insertDatas)) {
				inboundShipmentDAO.batchAdd(insertDatas);
			}
		}
	}

	private List<Map<String, Object>> screenNeedHandleData(List<Map<String, Object>> datas) {
		List<String> shipmentIds = new ArrayList<>();
		CollectionUtil.each(datas, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> data) {
				if (data.containsKey("ShipmentId") && null != data.get("ShipmentId")) {
					shipmentIds.add(data.get("ShipmentId").toString());
				}
			}
		});
		if (!CollectionUtil.isNullOrEmpty(shipmentIds)) {
			List<Map<String, Object>> list = inboundShipmentDAO.getByShipmentIds(shipmentIds);
			return CollectionUtil.searchList(datas, new IFunction<Map<String, Object>, Boolean>() {
				@Override
				public Boolean excute(Map<String, Object> data) {
					if (!data.containsKey("ShipmentId"))
						throw new RuntimeException("api data has problem,shipmentId is null,data:" + data.toString());

					return null == CollectionUtil.search(list, new IFunction<Map<String, Object>, Boolean>() {
						@Override
						public Boolean excute(Map<String, Object> dbdata) {
							return data.get("ShipmentId").toString().equals(dbdata.get("SHIPMENTID").toString());
						}
					});
				}
			});
		}
		return null;
	}

	private void handleNextToken(AmazonAccountModel account, Map<String, Object> result) {
		// ---对结果进行处理---
		handleData(result);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (result.containsKey("NextToken")) {
			AmazonRequestConfig config = new AmazonRequestConfig(account, "ListInboundShipmentsByNextToken",
					"2010-10-01");
			config.setUrl(account.getUrl() + "/FulfillmentInboundShipment");
			Map<String, String> paramMap = getDefaultParam();
			paramMap.put("NextToken", result.get("NextToken").toString());
			config.setQueryParameters(paramMap);
			String resp = AmazonHttpUtil.amazonPOST(config);
			result = parseAmazonFBAInventoryResponseXml(account, resp);
			handleNextToken(account, result);
		}
	}

	public Map<String, Object> parseAmazonFBAInventoryResponseXml(AmazonAccountModel account, String response) {
		Map<String, Object> map = null;
		try {
			ParseXMLModel2 parser = new ParseXMLModel2(response,
					"http://mws.amazonaws.com/FulfillmentInboundShipment/2010-10-01/") {
				@Override
				public List<Map<String, Object>> getResult() {
					XMLNode[] columns = { XMLNode.getInstance("DestinationFulfillmentCenterId"),
							XMLNode.getInstance("LabelPrepType"), XMLNode.getInstance("City"),
							XMLNode.getInstance("CountryCode"), XMLNode.getInstance("PostalCode"),
							XMLNode.getInstance("Name"), XMLNode.getInstance("AddressLine1"),
							XMLNode.getInstance("AddressLine2"), XMLNode.getInstance("StateOrProvinceCode"),
							XMLNode.getInstance("ShipmentId"), XMLNode.getInstance("AreCasesRequired"),
							XMLNode.getInstance("ShipmentName"), XMLNode.getInstance("ShipmentStatus") };

					List<Map<String, Object>> list = new ArrayList<>();
					List<Element> elements = this.getElementChild("member");
					for (Element element : elements) {
						element = this.formateElement(element);
						Map<String, Object> map = parseRecord(element, columns);
						map.put("platForm", account.getPlatform());
						list.add(map);
					}
					return list;
				}
			};
			map = Maps.newConcurrentMap();
			map.put("data", parser.getResult());
			String nextToken = parser.getNextToken();
			if (!Strings.isNullOrEmpty(nextToken)) {
				map.put("NextToken", parser.getNextToken());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("解析Amazon请求返回的Xml数据失败", e);
		}
		return map;
	}
}