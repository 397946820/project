package com.it.ocs.amazon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.amazon.dao.IInboundShipmentDetailDAO;
import com.it.ocs.amazon.model.AmazonRequestConfig;
import com.it.ocs.amazon.utils.AmazonHttpUtil;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.synchronou.dao.ISyncAmazonOrderDao;
import com.it.ocs.synchronou.model.AmazonAccountModel;
import com.it.ocs.synchronou.model.ParseXMLModel2;
import com.it.ocs.synchronou.model.XMLNode;

@Service
public class InboundShipmentDetailService {
	// https://www.cnblogs.com/wangzhongqiu/p/6402940.html
	@Autowired
	private IInboundShipmentDetailDAO inboundShipmentDetailDAO;
	private final static Logger log = Logger.getLogger(InboundShipmentDetailService.class);
	@Autowired
	private ISyncAmazonOrderDao syncAmazonOrderDao;

	public void downloadInboundShipmentDetail() {
		Map<String, String> param = Maps.newConcurrentMap();
		CollectionUtil.each(this.syncAmazonOrderDao.getAmazonAccounts(), new IAction<AmazonAccountModel>() {
			public void excute(AmazonAccountModel account) {
				CollectionUtil.each(inboundShipmentDetailDAO.getShipmentIds(account.getPlatform()),
						new IAction<Map<String, String>>() {
							@Override
							public void excute(Map<String, String> shipModel) {
								
								try {
									param.put("ShipmentId", shipModel.get("SHIPMENTID"));
									AmazonRequestConfig config = new AmazonRequestConfig(account,
											"ListInboundShipmentItems", "2010-10-01");
									config.setUrl(account.getUrl() + "/FulfillmentInboundShipment");
									config.setQueryParameters(param);
									String resp = AmazonHttpUtil.amazonPOST(config);
									List<Map<String, Object>> result = parseAmazonFBAInventoryResponseXml(account, resp);
									handleData(result);
									Thread.sleep(3000);
								} catch (Exception e) {
									log.error(" handle error,ShipmentId:"+shipModel.get("SHIPMENTID")+",error msg:"+e.getMessage());
								}
							}
						});
			}
		});
	}
	
	private void handleData(List<Map<String, Object>> datas) {
		CollectionUtil.each(datas, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> obj) {
				Map<String, Object> map = inboundShipmentDetailDAO.getShipIdSku(obj.get("ShipmentId").toString(),
						obj.get("SellerSKU").toString());
				if (null == map) {
					inboundShipmentDetailDAO.add(obj);
				} else {
					inboundShipmentDetailDAO.update(obj);
				}
			}
		});
	}

	public List<Map<String, Object>> parseAmazonFBAInventoryResponseXml(AmazonAccountModel account, String response) {
		List<Map<String, Object>> result = Lists.newArrayList();
		try {
			ParseXMLModel2 parser = new ParseXMLModel2(response,
					"http://mws.amazonaws.com/FulfillmentInboundShipment/2010-10-01/") {
				@Override
				public List<Map<String, Object>> getResult() {
					XMLNode[] columns = { XMLNode.getInstance("ShipmentId"), XMLNode.getInstance("SellerSKU"),
							XMLNode.getInstance("QuantityShipped"), XMLNode.getInstance("QuantityInCase"),
							XMLNode.getInstance("QuantityReceived"), XMLNode.getInstance("FulfillmentNetworkSKU") };
					List<Map<String, Object>> list = new ArrayList<>();
					List<Element> elements = this.getElementChild("member");
					CollectionUtil.each(elements, new IAction<Element>() {
						@Override
						public void excute(Element element) {
							element = formateElement(element);
							Map<String, Object> map = parseRecord(element, columns);
							list.add(map);
						}
					});
					return list;
				}
			};
			result = parser.getResult();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("解析Amazon请求返回的Xml数据失败", e);
		}
		return result;
	}

}
