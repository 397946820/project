package com.it.ocs.synchronou.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.dao.IEbaySellerListDao;
import com.it.ocs.synchronou.model.EbayAccountModel;
import com.it.ocs.synchronou.model.EbayShipUploadModel;
import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.RequestModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.IEbayMessageService;
import com.it.ocs.synchronou.util.UTCTimeUtils;
import com.it.ocs.task.core.TaskExecutorUtil;
import com.it.ocs.task.core.TaskRunnable;

@Service
public class EbayOrderShipNumberUploadService {

	private final static Logger log = Logger.getLogger(EbayOrderShipNumberUploadService.class);

	@Autowired
	private IEbaySellerListDao ebaySellerListDao;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private BaseHttpsService baseHttpService;

	@Autowired
	private EbayAccountService ebayAccountService;
	
	@Autowired
	private IEbayMessageService ebayMessageService;
	
	public void uploadShipNumber() {
		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
		for(EbayAccountModel account : accounts){
			account.setSiteId("201");// 设置站点为美国，返回英语信息
			createThreadUploadByAccount(account);
		}
	}

	private void createThreadUploadByAccount(EbayAccountModel account) {
		TaskExecutorUtil.threadRun(
			new TaskRunnable(){

				@Override
				protected void runTask() {
					// 获取账号下要上传的列表
					List<EbayShipUploadModel> ships = ebaySellerListDao.getUploadsByAccount(account.getAccount());
					if(null != ships && ships.size() > 0){
						TemplateModel template = templateService.getTemplateContent("CompleteSale", "ebay");
						for(EbayShipUploadModel ship : ships){
							Map<String, String> xmlValueMap = new HashMap<>();
							xmlValueMap.put("transactionID", ship.getTransactionId());
							xmlValueMap.put("orderId", ship.getOrderId());
							xmlValueMap.put("orderLineItemId", ship.getItemId() + "-" + ship.getTransactionId());

							StringBuffer shipment = new StringBuffer();
							String trackingNumber = ship.getTrackingNumber();
							String carrier = ship.getCarrier();
							if (null != trackingNumber && !"".equals(trackingNumber)) {
								shipment.append("<ShipmentTrackingDetails>");
								shipment.append("<ShipmentTrackingNumber>" + trackingNumber + "</ShipmentTrackingNumber>");
								shipment.append("<ShippingCarrierUsed>" + carrier + "</ShippingCarrierUsed>");
								shipment.append("</ShipmentTrackingDetails>");
							}else{
								shipment.append("<ShipmentTrackingDetails>");
								shipment.append("<ShipmentTrackingNumber></ShipmentTrackingNumber>");
								shipment.append("<ShippingCarrierUsed>" + carrier + "</ShippingCarrierUsed>");
								shipment.append("</ShipmentTrackingDetails>");
							}
							
							String shipTime = UTCTimeUtils.getUTCTimeStr(0);
							shipment.append("<ShippedTime>" + shipTime + "</ShippedTime>");
							xmlValueMap.put("shipment", shipment.toString());
							
							RequestModel requetModel = new RequestModel(template, account, xmlValueMap);
							Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(),requetModel.getRequestXML());
							String message = ParseXMLModel.getRequstMessage(doc, "urn:ebay:apis:eBLBaseComponents");
							if ("".equals(message)) {
								shipTime = shipTime.replace("T", " ");
								shipTime = shipTime.replace("Z", "");
								ship.setShippedTime(shipTime);
								ship.setToLine(1);
								if (null != trackingNumber && !"".equals(trackingNumber)){
									ebaySellerListDao.updateOrderShipNumberByOCS(ship);
								}else{
									ebaySellerListDao.updateOrderShipMarkByOCS(ship);
								}
								ebaySellerListDao.updateUploadData(ship);
								
								
							} else {
								String error = "orderId:" + ship.getOrderId() + " 上传快递运输号失败，错误信息：" + message;
								log.info(error);
								ship.setCause(message);
								ebaySellerListDao.updateUploadDataByFail(ship);
							}
						}
					}
				}
			}
		);
		
	}

	public void addUploadData(Map<String, Object> map) {
		ebaySellerListDao.addTrackNumberUploadRecord(map);
	}

	public void updateWOrderInfo(Map<String, String> orderParam) {
		ebaySellerListDao.updateReplaceOrder(orderParam);
		//自动发送消息
		Map<String,Object> order = ebaySellerListDao.getOrderInfoToSendMessage(orderParam);
		Map<String,Object> xmlValueMap = new HashMap<>();
		xmlValueMap.put("QuestionTypeCodeType", "Shipping");
		xmlValueMap.put("subject", "Tracking number of the replacement");
		xmlValueMap.put("media","");
		xmlValueMap.put("account", orderParam.get("account"));
		xmlValueMap.put("itemId", orderParam.get("itemId"));
		xmlValueMap.put("buyerId", order.get("BUYERID"));
		String site = order.get("SITEID").toString();
		//String carrier = orderParam.get("carrier");
		//String trackingNo = orderParam.get("trackingNo");
		TemplateModel template = null;
		if("US".equals(site)||"UK".equals(site)){
			template = templateService.getTemplateContent("ebayShipMessageTempEN", "ebay");
		}else{
			template = templateService.getTemplateContent("ebayShipMessageTempDE", "ebay");
		}
		xmlValueMap.put("messageTxt",TemplateService.formatTemplat(orderParam, template.getContent()));
		OperationResult result = ebayMessageService.sendUseMessage(xmlValueMap);
		if(!"success".equals(result.getData().toString())){
			log.error("补发单上传跟踪号通知客户失败："+site+"--"+orderParam.get("account")+"--"+orderParam.get("orderId")+"--"+orderParam.get("itemId"));
		}
	}

	public OperationResult updateWOrderInfo2(Map<String, String> orderParam) {
		ebaySellerListDao.updateReplaceOrder(orderParam);
		//需要判断是否是线下单（结构与补发单一致，但是没有对应存在订单表的信息），线下单不需要发送消息
		if(((String) orderParam.get("orderId")).startsWith("OCS")) {
			return new OperationResult();
		}
		//自动发送消息
		Map<String,Object> order = ebaySellerListDao.getOrderInfoToSendMessage(orderParam);
		Map<String,Object> xmlValueMap = new HashMap<>();
		xmlValueMap.put("QuestionTypeCodeType", "Shipping");
		xmlValueMap.put("subject", "Tracking number of the replacement");
		xmlValueMap.put("media","");
		xmlValueMap.put("account", orderParam.get("account"));
		xmlValueMap.put("itemId", orderParam.get("itemId"));
		xmlValueMap.put("buyerId", order.get("BUYERID"));
		String site = order.get("SITEID").toString();
		//String carrier = orderParam.get("carrier");
		//String trackingNo = orderParam.get("trackingNo");
		TemplateModel template = null;
		if("US".equals(site)||"UK".equals(site)){
			template = templateService.getTemplateContent("ebayShipMessageTempEN", "ebay");
		}else{
			template = templateService.getTemplateContent("ebayShipMessageTempDE", "ebay");
		}
		xmlValueMap.put("messageTxt",TemplateService.formatTemplat(orderParam, template.getContent()));
		return ebayMessageService.sendUseMessage(xmlValueMap);
	}
	
	/*public static void main(String[] args) {
		org.springframework.context.ApplicationContext context = new org.springframework.context.support.ClassPathXmlApplicationContext("classpath:spring.xml");
		EbayOrderShipNumberUploadService service = (EbayOrderShipNumberUploadService) context.getBean("ebayOrderShipNumberUploadService");
		Map<String, String> orderParam = new HashMap<String, String>();
		orderParam.put("account", "uk.nm");
		orderParam.put("orderId", "OCS1529461172609");
		orderParam.put("trackingNo", "R8888888888");
		orderParam.put("carrier", "RM");
		orderParam.put("times", "1");
		orderParam.put("platform", "ebay");
		OperationResult or = service.updateWOrderInfo2(orderParam);
		System.out.println(StringUtil.instanceDetail(or));
	}*/

}
