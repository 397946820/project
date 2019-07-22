package com.it.ocs.synchronou.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.dom4j.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.salesStatistics.vo.SaleOrderRefundVo;
import com.it.ocs.synchronou.dao.IEbaySellerListDao;
import com.it.ocs.synchronou.model.EbayAccountModel;
import com.it.ocs.synchronou.model.EbayLabelModel;
import com.it.ocs.synchronou.model.EbayShipUploadModel;
import com.it.ocs.synchronou.model.ParseOrderXMLModel;
import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.RequestModel;
import com.it.ocs.synchronou.model.SaleInfoModel;
import com.it.ocs.synchronou.model.SaleSubItemModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.IEbayMessageService;
import com.it.ocs.synchronou.service.IEbaySellerListService;
import com.it.ocs.synchronou.util.UTCTimeUtils;
import com.it.ocs.synchronou.vo.SaleInfoVO;
import com.it.ocs.sys.dao.IReturnOrderDao;
import com.it.ocs.sys.model.UserModel;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Sale;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.HttpMethod;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;
import com.paypal.base.rest.RESTUtil;

import net.sf.json.JSONObject;

@Service
public class EbaySellerListService implements IEbaySellerListService {

	private static final Logger log = Logger.getLogger(EbaySellerListService.class);

	@Autowired
	private IEbaySellerListDao ebaySellerListDao;
	
	@Autowired
	private IReturnOrderDao returnOrderDao;
	
	@Autowired
	private IEDADao iEDADao;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private BaseHttpsService baseHttpService;

	@Autowired
	private EbayAccountService ebayAccountService;
	
	@Autowired
	private IEbayMessageService ebayMessageService;
	
	@Autowired
	private PaypalTokenService paypalTokenService;

	
	public Document syncRequest(RequestModel requetModel) {
		return baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(),
				requetModel.getRequestXML());
	}

	public void taskOrderList() {
		syncOrderList();
	}

	public void taskOrderRepair() {
		TemplateModel xml = templateService.getTemplateContent("GetOrdersRepair", "ebay");
		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
		String timeFrom = UTCTimeUtils.getUTCTimeStr(- 35 * 24);
		String timeTo = UTCTimeUtils.getUTCTimeStr(-12);
		for (EbayAccountModel account : accounts) {
			account.setSiteId("201");// 设置站点为美国，返回英语信息
			getOrderListByAccount(account, xml, timeFrom, timeTo);
		}
	}
	
	@Override
	public OperationResult syncOrderListByPage() {
		TemplateModel xml = templateService.getTemplateContent("GetOrders", "ebay");
		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
		String timeFrom = UTCTimeUtils.getUTCTimeStr(-4);
		String timeTo = UTCTimeUtils.getUTCTimeStr(0);
		for (EbayAccountModel account : accounts) {
			account.setSiteId("201");// 设置站点为美国，返回英语信息
			getOrderListByAccount(account, xml, timeFrom, timeTo);
		}
		log.info("----------页面点击同步ebay订单成功---------");
		OperationResult result = new OperationResult();
		result.setData("success");
		return result;
	}
	
	/**
	 * 同步订单
	 * 
	 * @return
	 */
	@Override
	public OperationResult syncOrderList() {
		TemplateModel xml = templateService.getTemplateContent("GetOrders", "ebay");
		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
		// 查询ebay最后更新时间
		String timeFrom = ebaySellerListDao.getStartTime();
		int id = ebaySellerListDao.getTimeSetId();
		Map<String, Object> timeSetMap = new HashMap<>();
		timeSetMap.put("id", id);
		if (null == timeFrom || "".equals(timeFrom)) {
			timeFrom = UTCTimeUtils.getUTCTimeStr(-2);
			String timeFromFormate = timeFrom.replace("T", " ");
			timeFromFormate = timeFromFormate.replace("Z", "");
			timeSetMap.put("timeFrom", timeFromFormate);
			ebaySellerListDao.insertTimeSet(timeSetMap);
		} else {
			timeSetMap.put("timeFrom", timeFrom);
			ebaySellerListDao.insertTimeSet(timeSetMap);
			timeFrom = timeFrom.replace(" ", "T");
			timeFrom = timeFrom + "Z";
		}
		String timeTo = UTCTimeUtils.getUTCTimeStr(0);
		for (EbayAccountModel account : accounts) {
			account.setSiteId("201");// 设置站点为美国，返回英语信息
			getOrderListByAccount(account, xml, timeFrom, timeTo);
		}
		timeTo = timeTo.replace("T", " ");
		timeTo = timeTo.replace("Z", "");
		timeSetMap.put("timeTo", timeTo);
		ebaySellerListDao.udpateTimeSet(timeSetMap);
		OperationResult result = new OperationResult();
		result.setData("success");
		return result;
	}

	private void getOrderListByAccount(EbayAccountModel account, TemplateModel template, String timeForm,
			String timeTo) {
		// 第一次请求
		Map<String, String> xmlValueMap = new HashMap<>();
		// xmlValueMap.put("days", "1");

		xmlValueMap.put("timeFrom", timeForm);
		xmlValueMap.put("timeTo", timeTo);
		xmlValueMap.put("pageCount", "100");
		xmlValueMap.put("pageNumber", "1");
		RequestModel requetModel = new RequestModel(template, account, xmlValueMap);
		ParseOrderXMLModel parse = new ParseOrderXMLModel(syncRequest(requetModel), "urn:ebay:apis:eBLBaseComponents");
		List<Map<String, Object>> parseResult = parse.getResult();
		saveSaleData(parseResult, account);
		// 获取总页数全部获取
		int totalPage = parse.getPage();
		for (int i = 2; i <= totalPage; i++) {
			xmlValueMap.put("pageNumber", String.valueOf(i));
			requetModel.setParam(xmlValueMap);
			ParseOrderXMLModel parse1 = new ParseOrderXMLModel(syncRequest(requetModel),
					"urn:ebay:apis:eBLBaseComponents");
			saveSaleData(parse1.getResult(), account);
		}

	}

	/**
	 * 保存更新
	 * 
	 * @param parseResult
	 * @param account
	 */
	private void saveSaleData(List<Map<String, Object>> parseResult, EbayAccountModel account) {
		for (Map<String, Object> map : parseResult) {
			String orderId = (String) map.get("OrderID");
			// 新增
			Long id = ebaySellerListDao.getIdByOrderId(orderId);
			if (null == id || id == 0) {
				id = ebaySellerListDao.getId();
				map.put("id", id);
				map.put("account", account.getAccount());
				// 订单不存在，保存信息
				ebaySellerListDao.addOrderInfo(map);
				// 订单所有sku信息
				List<Map<String, Object>> items = (List<Map<String, Object>>) map.get("items");
				if (items.size() > 0) {
					ebaySellerListDao.addOrderSkuInfo(items, id);
				}
			} else {// 更新
				map.put("id", id);
				map.put("account", account.getAccount());
				// 订单存在，更新信息
				ebaySellerListDao.updateOrderInfo(map);
				// 订单所有sku信息更新
				List<Map<String, Object>> items = (List<Map<String, Object>>) map.get("items");
				List<Map<String, Object>> updateItems = new ArrayList<>();
				List<Map<String, Object>> addItems = new ArrayList<>();
				for (Map<String, Object> item : items) {
					item.put("parentId", id);
					if (ebaySellerListDao.isExist(item) > 0) {
						updateItems.add(item);
					} else {
						addItems.add(item);
					}
				}
				if (addItems.size() > 0) {
					ebaySellerListDao.addOrderSkuInfo(addItems, id);
				}
				if (updateItems.size() > 0) {
					ebaySellerListDao.updateOrderSkuInfo(updateItems, id);
				}
			}
		}

	}
	
	private boolean checkParamHasValue(Object paramObject){
		if(null == paramObject || "".equals(paramObject.toString())){
			return false;
		}
		return true;
	}
	/**
	 * 销售列表
	 */
	@Override
	public ResponseResult<SaleInfoVO> getList(RequestParam param) {
		ResponseResult<SaleInfoVO> result = new ResponseResult<>();
		Map<String, Object> map = param.getParam();
		//参数特殊处理，item表
		//sku itemId title shipNumber transactionId
		boolean hasItemParam = checkParamHasValue(map.get("sku"))||checkParamHasValue(map.get("itemId"))||checkParamHasValue(map.get("title"))||checkParamHasValue(map.get("shipNumber"))||checkParamHasValue(map.get("transactionId"));
		if(hasItemParam){
			map.put("hasItemParam", "y");
		}else{
			map.put("hasItemParam", "");
		}
		//sub表 buyerName postCode street
		boolean hasSubParam = checkParamHasValue(map.get("buyerName"))||checkParamHasValue(map.get("postCode"))||checkParamHasValue(map.get("street"));
		if(hasSubParam){
			map.put("hasSubParam", "y");
		}else{
			map.put("hasSubParam", "");
		}
		//自定义标签
		int label = 0;
		Object orderAllStatus = map.get("orderAllStatus");
		if(null != orderAllStatus && !"".equals(orderAllStatus.toString())){
			label = Integer.parseInt(orderAllStatus.toString());
		}
		if(label > 100){
			map.put("orderAllStatus", 0);
			map.put("label", label);
		}else{
			map.put("label", 0);
		}
		List<SaleInfoModel> saleInfoModels = ebaySellerListDao.queryByPage(map, param.getStartRow(), param.getEndRow());
		List<SaleInfoVO> saleInfoVOs = new ArrayList<>();
		convertList(saleInfoModels, saleInfoVOs);
		int count = ebaySellerListDao.count(map);
		result.setRows(saleInfoVOs);
		result.setTotal(count);
		return result;
	}

	private void convertList(List<SaleInfoModel> source, final List<SaleInfoVO> target) {
		CollectionUtil.each(source, new IAction<SaleInfoModel>() {
			@Override
			public void excute(SaleInfoModel obj) {
				SaleInfoVO saleInfoVO = new SaleInfoVO();
				BeanUtils.copyProperties(obj, saleInfoVO);
				target.add(saleInfoVO);
			}
		});
	}

	@Override
	public OperationResult syncOrderByOrderId(String orderId, String account) {
		TemplateModel template = templateService.getTemplateContent("GetOrdersByOrderId", "ebay");
		EbayAccountModel accountModel = ebayAccountService.getAccountModelByName(account);
		accountModel.setSiteId("201");
		Map<String, String> xmlValueMap = new HashMap<>();
		xmlValueMap.put("orderId", orderId);
		RequestModel requetModel = new RequestModel(template, accountModel, xmlValueMap);
		ParseOrderXMLModel parse = new ParseOrderXMLModel(syncRequest(requetModel), "urn:ebay:apis:eBLBaseComponents");
		List<Map<String, Object>> parseResult = parse.getResult();
		saveSaleData(parseResult, accountModel);
		OperationResult res = new OperationResult();
		res.setData("success");
		return res;
	}

	@Override
	public List<SaleSubItemModel> getSubItemById(String parentId) {
		return ebaySellerListDao.getSubItemById(parentId);
	}

	@Override
	public OperationResult uploadTranNumber(Map<String, Object> map) {
		OperationResult or = new OperationResult();
		List<Map<String, Object>> rows = (ArrayList<Map<String, Object>>) map.get("row");
		TemplateModel template = templateService.getTemplateContent("CompleteSale", "ebay");
		for (Map<String, Object> row : rows) {
			EbayAccountModel account = ebayAccountService.getAccountModelByName(row.get("ebayAccount").toString());
			account.setSiteId("0");// 设置站点为美国，返回英语信息
			Map<String, String> xmlValueMap = new HashMap<>();
			String transactionId = (String) row.get("transactionId");
			xmlValueMap.put("transactionID", transactionId);

			String orderId = (String) row.get("orderId");
			xmlValueMap.put("orderId", orderId);
			String itemId = (String) row.get("itemId");
			xmlValueMap.put("orderLineItemId", itemId + "-" + transactionId);

			StringBuffer shipment = new StringBuffer();
			String trackingNumber = (String) row.get("trackingNumber");
			String carrier = (String) row.get("carrier");
			if (!"".equals(trackingNumber) && null != trackingNumber) {
				shipment.append("<ShipmentTrackingDetails>");
				shipment.append("<ShipmentTrackingNumber>" + trackingNumber + "</ShipmentTrackingNumber>");
				shipment.append("<ShippingCarrierUsed>" + carrier + "</ShippingCarrierUsed>");
				shipment.append("</ShipmentTrackingDetails>");
			}
			String trackingNumber2 = (String) row.get("trackingNumber2");
			String carrier2 = (String) row.get("carrier2");
			if (!"".equals(trackingNumber2) && null != trackingNumber2) {
				shipment.append("<ShipmentTrackingDetails>");
				shipment.append("<ShipmentTrackingNumber>" + trackingNumber2 + "</ShipmentTrackingNumber>");
				shipment.append("<ShippingCarrierUsed>" + carrier2 + "</ShippingCarrierUsed>");
				shipment.append("</ShipmentTrackingDetails>");
			}
			String trackingNumber3 = (String) row.get("trackingNumber3");
			String carrier3 = (String) row.get("carrier3");
			if (!"".equals(trackingNumber3) && null != trackingNumber3) {
				shipment.append("<ShipmentTrackingDetails>");
				shipment.append("<ShipmentTrackingNumber>" + trackingNumber3 + "</ShipmentTrackingNumber>");
				shipment.append("<ShippingCarrierUsed>" + carrier3 + "</ShippingCarrierUsed>");
				shipment.append("</ShipmentTrackingDetails>");
			}
			String shipTime = UTCTimeUtils.getUTCTimeStr(0);
			shipment.append("<ShippedTime>" + shipTime + "</ShippedTime>");
			xmlValueMap.put("shipment", shipment.toString());

			RequestModel requetModel = new RequestModel(template, account, xmlValueMap);
			Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(),
					requetModel.getRequestXML());
			String message = ParseXMLModel.getRequstMessage(doc, "urn:ebay:apis:eBLBaseComponents");
			if ("".equals(message)) {
				shipTime = shipTime.replace("T", " ");
				shipTime = shipTime.replace("Z", "");
				row.put("shipTime", shipTime);
				// 更新数据库
				ebaySellerListDao.updateOrderTrackingNumber(row);
			} else {
				String error = "orderId:" + orderId + " 上传快递运输号失败，错误信息：" + message;
				log.info(error);
				or.setData(error);
			}
		}
		return or;
	}

	@Override
	public OperationResult getSendBillCount(String orderId) {
		OperationResult or = new OperationResult();
		Integer count = this.ebaySellerListDao.getSendBillCount(orderId);
		if (null == count) {
			count = 0;
		}
		or.setData(count);
		return or;
	}

	@Override
	public OperationResult sendBill(List<Map<String,Object>> list) {
		OperationResult re = new OperationResult();
		StringBuffer sbMessage = new StringBuffer();
		for(Map<String,Object> map:list){
			String orderId = String.valueOf(map.get("orderId"));
			OperationResult or = sendBillByOrderId(map);
			sbMessage.append(orderId).append(":" + or.getData() + "  ");
		}
		re.setData(sbMessage.toString());
		return re;

	}

	private OperationResult sendBillByOrderId(Map<String, Object> map) {
		OperationResult or = new OperationResult();
		SaleInfoModel sale = ebaySellerListDao.getOrderByOrderId(map);
		TemplateModel template = templateService.getTemplateContent("SendInvoice", "ebay");
		EbayAccountModel accountModel = ebayAccountService.getAccountModelByName(sale.getAccount());
		String siteName = String.valueOf(map.get("siteId"));
		String siteId = ebaySellerListDao.getSiteIdBySiteName(siteName);
		if(null == siteId || "".equals(siteId)){
			or.setData("此订单的站点"+siteName+"找不到站点数字编码，请联系管理员维护！");
			return or;
		}
		accountModel.setSiteId(siteId);
		String orderId = String.valueOf(map.get("orderId"));
		Map<String, String> xmlValueMap = new HashMap<>();
		xmlValueMap.put("orderId", orderId);
		
		//国内运输运费

		JSONObject jsonObject = JSONObject.fromObject(sale.getShippingDetails());
		JSONObject shipOptions = jsonObject.getJSONObject("ShippingServiceOptions");
		// ShippingService
		String shippingService = shipOptions.getString("ShippingService");
		xmlValueMap.put("shippingService", shippingService);
		// cost
		shipOptions = shipOptions.getJSONObject("ShippingServiceCost");
		Iterator iterator = shipOptions.keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (key.indexOf("currencyID") != -1) {
				String currencyID = shipOptions.getString(key);
				xmlValueMap.put("currencyID", currencyID);
			} else {
				String cost = shipOptions.getString(key);
				xmlValueMap.put("cost", cost);
			}

		}
		
		StringBuffer isb = new StringBuffer();
		isb.append("<ShippingServiceOptions>");
		isb.append("<ShippingService>"+xmlValueMap.get("shippingService")+"</ShippingService>");
		isb.append("<ShippingServiceCost currencyID=\""+xmlValueMap.get("currencyID")+"\">"+xmlValueMap.get("cost")+"</ShippingServiceCost>");
		isb.append("<ShippingServicePriority>1</ShippingServicePriority>");
		isb.append("<ExpeditedService>false</ExpeditedService>");
		isb.append("</ShippingServiceOptions>");
		
		double icost = paseNumber(xmlValueMap.get("cost"));
		if(icost > 0){
			xmlValueMap.put("shippingServiceOptions", isb.toString());
		}else{
			//国际运输费用InternationalShippingServiceOption
			String iShipOptionsStr = jsonObject.toString();
			if(iShipOptionsStr.indexOf("InternationalShippingServiceOption")>-1){
				JSONObject internationalShipOptions = jsonObject.getJSONObject("InternationalShippingServiceOption");
				if(null !=internationalShipOptions &&!internationalShipOptions.isEmpty()){
					StringBuffer sb = new StringBuffer();
					sb.append("<InternationalShippingServiceOptions>");
					//运输服务
					String internationalShipService = internationalShipOptions.getString("ShippingService");
					sb.append(" <ShippingService>"+internationalShipService+"</ShippingService>");
					String shippingServicePriority = internationalShipOptions.getString("ShippingServicePriority");
					sb.append(" <ShippingServicePriority>"+shippingServicePriority+"</ShippingServicePriority>");
					//费用
					internationalShipOptions = internationalShipOptions.getJSONObject("ShippingServiceCost");
					Iterator iterator1 = internationalShipOptions.keys();
					String currencyID  = "";
					String cost = "";
					double ocost = 0;
					while (iterator1.hasNext()) {
						String key = (String) iterator1.next();
						if (key.indexOf("currencyID") != -1) {
							currencyID = internationalShipOptions.getString(key);
						} else {
							cost = shipOptions.getString(key);
						}
					}
					ocost = paseNumber(cost);
					sb.append(" <ShippingServiceCost currencyID=\""+currencyID+"\">"+cost+"</ShippingServiceCost>");
					sb.append("</InternationalShippingServiceOptions>");
					if(ocost > 0){
						xmlValueMap.put("shippingServiceOptions", sb.toString());
					}else{
						xmlValueMap.put("shippingServiceOptions", isb.toString());
					}
				}else{
					xmlValueMap.put("shippingServiceOptions", isb.toString());
				}
			}else{
				xmlValueMap.put("shippingServiceOptions", isb.toString());
			}
			
		}
		
		RequestModel requetModel = new RequestModel(template, accountModel, xmlValueMap);
		Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(),
				requetModel.getRequestXML());
		if (null == doc) {
			or.setData("Connection timed out,please try again.");
			return or;
		}
		String message = ParseXMLModel.getRequstMessage(doc, "urn:ebay:apis:eBLBaseComponents");
		if ("".equals(message)) {
			or.setData("success");
			if (ebaySellerListDao.hasSend(map) > 0) {
				ebaySellerListDao.updateSendRecord(map);
			} else {
				ebaySellerListDao.addSendRecord(map);
			}
		} else {
			or.setData(message);
		}
		return or;
	}
	
	public static double paseNumber(String num){
		if(null == null || "".equals(num.trim())){
			return 0;
		}
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+|-?[0-9]+");
	    if(pattern.matcher(num).matches()){
	    	return Double.parseDouble(num);
	    }
		return 0;
	}
	
	public static Map<String,String> ebayPostAPIHead(EbayAccountModel accountModel){
		//EBAY_US, EBAY_UK, EBAY_DE, EBAY_AU, and EBAY_CA
		/*String siteId = accountModel.getSiteId();
		String siteCode = "EBAY_US";
		if(siteId.equals("Germany")){
			siteCode = "EBAY_US";
		}else if(siteId.equals("France")){
			siteCode = "";
		}else if(siteId.equals("UK")){
			siteCode = "";
		}else if(siteId.equals("")){
			siteCode = "";
		}else{
			siteCode = "EBAY_US";
		}*/
		Map<String,String> map = new HashMap<>();
		map.put("Authorization", "TOKEN "+accountModel.getToken());
		map.put("X-EBAY-C-MARKETPLACE-ID", "EBAY_US");
		map.put("Content-Type", "application/json");
		map.put("Accept", "application/json");
		return map;
	}
	
	@Override
	public OperationResult cancelOrder(String id, String cause) {
		OperationResult or = new OperationResult();
		Subject currentUser = SecurityUtils.getSubject();
		UserModel user = null;
		if (null != currentUser.getSession().getAttribute("user")) {
			user = (UserModel)currentUser.getSession().getAttribute("user");
		}
		if(null == user){
			or.setData("fail");
			or.setDescription("您的登录已失效，请刷新页面重新登录！");
			return or;
		}
		SaleInfoModel sale = ebaySellerListDao.getOrderById(id);
		EbayAccountModel accountModel = ebayAccountService.getAccountModelByName(sale.getAccount());
		Map<String,String> postOrderApiHead = ebayPostAPIHead(accountModel);
		TemplateModel template = templateService.getTemplateContent("CheckOrderCancel", "ebay");
		//检查订单是否可以取消
		JSONObject requestJson = new JSONObject();
		requestJson.put("legacyOrderId", sale.getOrderId());
		JSONObject res = baseHttpService.getResponseJson(template.getUrl(), postOrderApiHead, requestJson.toString());
		if(null == res || res.toString().indexOf("eligible")== -1){
			or.setData("fail");
			or.setDescription("网络异常，请重试！");
			return or;
		}
		boolean isPass = res.getBoolean("eligible");
		//取消订单
		if(isPass){
			requestJson.put("cancelReason", cause);
			template = templateService.getTemplateContent("cancelOrder", "ebay");
			res = baseHttpService.getResponseJson(template.getUrl(), postOrderApiHead, requestJson.toString());
			log.error(res.toString());
			if(null != res&&res.toString().indexOf("cancelId")> -1){
				String cancelId = res.getString("cancelId");//cancelId
				or.setData("success");
				//保存记录
				Map<String,String> map = new HashMap<>();
				map.put("cancelId", cancelId);
				map.put("account", sale.getAccount());
				map.put("orderId", sale.getOrderId());
				map.put("cancelReason", cause);
				map.put("userId", String.valueOf(user.getId()));
				ebaySellerListDao.addCancelRecord(map);
			}else{
				or.setData("fail");
				or.setDescription("网络异常，请重试！");
			}
			
		}else{
			or.setData("fail");
			or.setDescription(res.get("failureReason").toString());
		}
		//TemplateModel template = templateService.getTemplateContent("AddDispute", "ebay");
		//EbayAccountModel accountModel = ebayAccountService.getAccountModelByName(sale.getAccount());
		/*accountModel.setSiteId("0");
		Map<String, String> xmlValueMap = new HashMap<>();
		xmlValueMap.put("orderLineItemID", sale.getOrderLineItemID());
		xmlValueMap.put("cause", cause);
		OperationResult or = new OperationResult();
		RequestModel requetModel = new RequestModel(template, accountModel, xmlValueMap);
		Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(),
				requetModel.getRequestXML());
		if (null == doc) {
			or.setData("Connection timed out,please try again.");
			return or;
		}
		String message = ParseXMLModel.getRequstMessage(doc, "urn:ebay:apis:eBLBaseComponents");
		if ("".equals(message)) {
			or.setData("success");
		} else {
			or.setData(message);
		}*/
		return or;
	}

	@Override
	public OperationResult getOrderTranInfo(String parentId) {
		Map<String,Object> map = ebaySellerListDao.getOrderTranInfo(parentId);
		Map<String, String> data = new HashMap<>();
		OperationResult or = new OperationResult();
		Object obj = map.get("SHIPADDRESS");
		if(null != obj){
			String shipAddress = String.valueOf(obj);
			JSONObject json = JSONObject.fromObject(shipAddress);
			data.put("NAME", getValueByJson(json, "Name"));
			data.put("STREET1", getValueByJson(json, "Street1"));
			data.put("STREET2", getValueByJson(json, "Street2"));
			data.put("CITYNAME", getValueByJson(json, "CityName"));
			data.put("StateOrProvince", getValueByJson(json, "StateOrProvince"));
			data.put("POSTALCODE", getValueByJson(json, "PostalCode"));
			String pv = getValueByJson(json, "Phone");
			if(null ==pv || "".equals(pv)||"Invalid Request".equals(pv)){
				pv = "--";
			}
			data.put("PHONE", pv);
			data.put("COUNTRY", getValueByJson(json, "Country"));
			
		}else{
			data.put("NAME", "");
			data.put("STREET1", "");
			data.put("STREET2", "");
			data.put("CITYNAME", "");
			data.put("StateOrProvince", "");
			data.put("POSTALCODE", "");
			data.put("PHONE", "");
			data.put("COUNTRY", "");
		}
		obj = map.get("PAYPALTRANNUM");
		if(null != obj){
			data.put("paypayTranNum", String.valueOf(obj));
		}else{
			data.put("paypayTranNum", "");
		}
		obj = map.get("EMAIL");
		if(null != obj){
			data.put("email", String.valueOf(obj));
		}else{
			data.put("email", "");
		}
		or.setData(data);
		return or;
	}

	public static String getValueByJson(JSONObject json, String key) {
		String value = "";
		try {
			value = String.valueOf(json.getString(key));
			if ("null".equals(value)) {
				value = "";
			}
		} catch (Exception e) {
			value = "";
		}
		return value;
	}

	@Override
	public List<Map<String, Object>> getOrderItemById(String parentId) {
		List<Map<String, Object>> orderItems = ebaySellerListDao.getOrderItemById(parentId);
		for(Map<String, Object> map : orderItems){
			String itemId = map.get("ITEMID").toString();
			Map<String,Object> itemInfo = ebaySellerListDao.getItemInfo(itemId);
			if(null != itemInfo){
				map.put("siteId", itemInfo.get("SITE_ID"));
				map.put("publicationType", itemInfo.get("PUBLICATION_TYPE"));
				map.put("productUrl", itemInfo.get("EBAY_PRODUCT_URL"));//
				map.put("ebayImages", itemInfo.get("EBAY_IMAGES"));
			}else{
				map.put("siteId", "");
				map.put("publicationType", "");
				map.put("productUrl", "");//
				map.put("ebayImages", "");
			}
			
		}
		return orderItems;
	}

	@Override
	public OperationResult getItemInfo(String itemId) {
		Map<String,Object> map = ebaySellerListDao.getItemInfo(itemId);
		OperationResult or = new OperationResult();
		or.setData(map);
		return or;
	}

	@Override
	public OperationResult countOrderByStatus(String num,Map<String, Object> map) {
		OperationResult or = new OperationResult();
		if (StringUtils.isBlank(num)) {
			or.setData(0);
			return or;
		}
		//参数特殊处理，item表
		//sku itemId title shipNumber transactionId
		boolean hasItemParam = checkParamHasValue(map.get("sku"))||checkParamHasValue(map.get("itemId"))||checkParamHasValue(map.get("title"))||checkParamHasValue(map.get("shipNumber"))||checkParamHasValue(map.get("transactionId"));
		if(hasItemParam){
			map.put("hasItemParam", "y");
		}else{
			map.put("hasItemParam", "");
		}
		//sub表 buyerName postCode street
		boolean hasSubParam = checkParamHasValue(map.get("buyerName"))||checkParamHasValue(map.get("postCode"))||checkParamHasValue(map.get("street"));
		if(hasSubParam){
			map.put("hasSubParam", "y");
		}else{
			map.put("hasSubParam", "");
		}
		//自定义标签
		int label = Integer.parseInt(num);
		if(label > 100){
			map.put("orderAllStatus", 0);
			map.put("label", label);
		}else{
			map.put("label", 0);
			map.put("orderAllStatus", num);
		}
		
		or.setData(ebaySellerListDao.count(map));
		return or;
	}

	@Override
	public Map<String, Object> getSaleOrderRefundByParentId(Map<String, Object> map) {
		Map<String, Object> result = Maps.newHashMap();
		List<SaleOrderRefundVo> list = ebaySellerListDao.getSaleOrderRefundByParentId(new Long(map.get("id").toString()));
		result.put("rows", list);
		result.put("total", list.size());
		String transactionSiteId = list.get(0).getTransactionSiteId();
		int count = iEDADao.countWestOrderList(map);
		if(("US".equals(transactionSiteId) && count != 0) || !"US".equals(transactionSiteId)) {
			result.put("edaOrderNum", returnOrderDao.findOrderReturnSeq());
		}
		return result;
	}

	@Override
	public OperationResult sendUseMessage(Map<String, Object> messages) {
		List<Map<String,String>> list = new ArrayList<>();
		Map<String,Object> xmlValueMap = new HashMap<>();
		xmlValueMap.put("messageTxt", (String)messages.get("bodyContent"));
		xmlValueMap.put("QuestionTypeCodeType", (String)messages.get("questionType"));
		xmlValueMap.put("subject", (String)messages.get("subject"));
		xmlValueMap.put("media",(String)messages.get("media"));
		//批量发送
		List<Map<String,Object>> orderInfo = (List<Map<String,Object>>)messages.get("orderInfo");
		String itemIds = (String)messages.get("itemId");
		String itemIdArray[] = itemIds.split(",");
		for(Map<String,Object> map : orderInfo){
			for(int i=0;i<itemIdArray.length;i++){
				map.put("itemId", itemIdArray[i]);
				if(ebaySellerListDao.itemExistOrder(map)>0){
					xmlValueMap.put("account", (String)map.get("account"));
					xmlValueMap.put("itemId", itemIdArray[i]);
					xmlValueMap.put("buyerId", (String)map.get("buyerId"));
					OperationResult result = ebayMessageService.sendUseMessage(xmlValueMap);
					Map<String,String> resultMap = new HashMap<>();
					resultMap.put("account", (String)map.get("account"));
					resultMap.put("orderId", (String)map.get("orderId"));
					resultMap.put("itemId", itemIdArray[i]);
					if(null != result){
						resultMap.put("result", String.valueOf(result.getData()));
					}else{
						resultMap.put("result", "fail");
					}
					
					list.add(resultMap);
				}
			}
		}
		OperationResult or = new OperationResult();
		or.setData(list);
		return or;
	}

	@Override
	public List<ComboBoxVO> getItemIdById(String parentId) {
		return ebaySellerListDao.getItemIdById(parentId);
	}

	@Override
	public OperationResult getItemEbayImage(String itemId) {
		OperationResult or = new OperationResult();
		or.setData(ebaySellerListDao.getItemEbayImage(itemId));
		return or;
	}

	@Override
	public List<Map<String, String>> getItemInfoByOrderSEQId(Map<String, Object> ids) {
		String idsStr = (String)ids.get("ids");
		String idsArray[] = idsStr.split(",");
		return ebaySellerListDao.getItemInfoByOrderSEQId(idsArray);
	}

	@Override
	public OperationResult remarkNoItem(Map<String, Object> map) {
		List<String> ids = (List<String>)map.get("ids");
		String flag = (String)map.get("flag");
		if(flag.equals("tag")){
			ebaySellerListDao.remarkNoItem(ids);
		}else{
			ebaySellerListDao.unRemarkNoItem(ids);
		}
		
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}

	@Override
	public OperationResult remarkContent(Map<String, Object> model) {
		int optCount = ebaySellerListDao.remarkContent(model);
		OperationResult or = new OperationResult();
		or.setData(optCount);
		return or;
	}
	
	public void syncNoEmailOrder(){
		List<SaleInfoModel> orders = ebaySellerListDao.getNoEmailOrders();
		
		for(SaleInfoModel  sale :orders){
			try {
				String token = paypalTokenService.getPaypalAccessTokenByPaypalAccount(sale.getPaypalAccount());
				Map<String,Object> paypalAccountInfo = ebaySellerListDao.getPaypalAccountInfo(sale.getPaypalAccount());
				String clientId = paypalAccountInfo.get("CLIENT").toString();
				String clientSecret =  paypalAccountInfo.get("SECRET").toString();
				APIContext apiContext = new APIContext(clientId,clientSecret,"live");
				Object parameters[] = {sale.getPaypalTrantionCode()};
		        String pattern = "v1/payments/sale/{0}";
		        String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
		        String payLoad = "";
		        Sale paypalSale =  (Sale)PayPalResource.configureAndExecute(apiContext, HttpMethod.GET, resourcePath, payLoad, Sale.class,token);
				JSONObject json = JSONObject.fromObject(paypalSale.toString());
				String paymentId = json.getString("parent_payment");
				Object parameters1[] = {paymentId};
			    pattern = "v1/payments/payment/{0}";
		        resourcePath = RESTUtil.formatURIPath(pattern, parameters1);
		        Payment payment = (Payment)PayPalResource.configureAndExecute(apiContext, HttpMethod.GET, resourcePath, payLoad,Payment.class,token);
				String email = payment.getPayer().getPayerInfo().getEmail();
				if(null != email && !"".equals(email)){
					sale.setBuyerEmail(email);
					ebaySellerListDao.updateOrderUserEmail(sale);
				}
			} catch (PayPalRESTException e) {
				log.error("PAYPAL获取邮箱失败："+sale.getPaypalAccount(), e);
			}
			
		}
	}
	public static void main(String[] args) throws Exception {
		BaseHttpsService bb = new BaseHttpsService();
		Map<String,String> map = new HashMap<>();
		map.put("X-EBAY-API-SITEID", "0");
		map.put("X-EBAY-API-COMPATIBILITY-LEVEL", "967");
		map.put("X-EBAY-API-CALL-NAME", "GetOrders");
//		bb.getPesponseXml("https://api.ebay.com/ws/api.dll", map,
//				requetModel.getRequestXML());
		
		/*Map<String,String> map = new HashMap<>();
		map.put("Authorization", "TOKEN AgAAAA**AQAAAA**aAAAAA**u1RjWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEmYSiC5SDpQ2dj6x9nY+seQ**Xq8DAA**AAMAAA**nzc7lCXNjRxjfZiTVjNJ6hYFYlZxK+5R6x7rr5WSGrTGJTjr3ykfT4YvBuhdDheZSpftIpfgfQMA//5Y1AhRHhckFpPnVU+7t2R7pViACV9wyiDGh2UjZ/XRuh1v5dr6zqUQN8AIo47gJE+AXeeehIC7G77E0DY2/0p+IxIWCtqP9MNjAfMIPWYsOhX4OnqMQgBIQdcMZZ5/3PoQRj/0ELiTn3RqltZq/T9BnyNv14qXBfXcUiijizx2a0OZBv1dfIrog7/ohP/sqBgvgyl31Ebi78Qp/TgIZtjP9r44tbw7kmsk/X1jtjeeXYfKZ+JelJlYZLZX4DTCCobYAZ7E5sbPI7frJFyRBsm7gxV5sZeBPdwDC5hB9pOTQpM27CdOsz2e089ughAEQw8H9ZV+DXUR4Aw3BNr40QwAkh34dBDF7UFo6JU8bWYTeweFT2vAdxUhvEfFjb5FRMcU5A9LK6SUkcoXP5f2DOD9nloZQOw6X6rczVsDeXd2dCPGTK+vpf82gjY+NWbVanPjICAEwFQZqPQe2OesAcbbK5ztYmeftzAvIEoWR71fHMrKDNrVeEFzzP9a9Pdl/C1UZ5Ys/xlLU07BnqL/idcx0+2WdcW3ZKpwMD7M6NobbqAuHBE7pvezSZ1DAVl+h5mrEURUQdOUMCRV5nKNkOYooeORqtKVclyNAOMPnvJwgn1sr/gdLuzoYtgsREiKBv1wJmni2YwcU80d+uLmCk41nqG3n8unVN0CURR+8g5BGSNFVvl1");
		map.put("X-EBAY-C-MARKETPLACE-ID", "EBAY_US");
		map.put("Content-Type", "application/json");
		map.put("Accept", "application/json");
		BaseHttpsService baseHttpService = new BaseHttpsService();
		JSONObject requestJson = new JSONObject();
		requestJson.put("legacyOrderId", "151927866548-1700871172005");
		requestJson.put("cancelReason", "BUYER_ASKED_CANCEL");
		JSONObject res = baseHttpService.getResponseJson("https://api.ebay.com/post-order/v2/cancellation/check_eligibility""https://api.ebay.com/post-order/v2/cancellation", map, requestJson.toString());
		System.out.println(res.toString());*/
		
		//PayPalAPIInterfaceServiceService
		//PayPalResource
		/*String clientId = "ATPwvD23gkaF9z3SzVQl9tdzYJdOCPCpovE2Nr5Ul6-V-HjWp7ednrvj6MGAPGRZLzpxroBPHXMzafQV";
		String clientSecret = "ENzT_aQFiXCg67TKvib7c6Tvg5NHtOFxWI0EStha36KOnPbkqIego1IbDixYtae5CU45R-GMrva2uyDj";
		APIContext apiContext = new APIContext(clientId,clientSecret,"live");
		String token = apiContext.fetchAccessToken();//"Bearer A21AAFlj7o8u0GFL7rjzh71eC3Mg_sBSxp57cXXJ7WLfcbvZEzj16QQmIlPJORwTE-y7V1Z3-s-QrsUE7pPvZRq-XSJuPekZQ";//;
		Object parameters[] = {"7LS83651GW907871S"};
        String pattern = "v1/payments/sale/{0}";
        String resourcePath = RESTUtil.formatURIPath(pattern, parameters);
        String payLoad = "";
        Sale sale =  (Sale)PayPalResource.configureAndExecute(apiContext, HttpMethod.GET, resourcePath, payLoad, Sale.class,token);
		JSONObject json = JSONObject.fromObject(sale.toString());
		String paymentId = json.getString("parent_payment");
		Object parameters1[] = {paymentId};
	    pattern = "v1/payments/payment/{0}";
        resourcePath = RESTUtil.formatURIPath(pattern, parameters1);
        Payment payment = (Payment)PayPalResource.configureAndExecute(apiContext, HttpMethod.GET, resourcePath, payLoad,Payment.class,token);
		System.out.println(payment.getPayer().getPayerInfo().getEmail());*/
		
		
        
	}

	@Override
	public ResponseResult<EbayShipUploadModel> shipUploadRecord(RequestParam param) {
		ResponseResult<EbayShipUploadModel> result = new ResponseResult<>();
		Map<String, Object> map = param.getParam();
		List<EbayShipUploadModel> shipRecords = ebaySellerListDao.shipUploadRecord(map, param.getStartRow(), param.getEndRow());
		int count = ebaySellerListDao.shipUploadRecordCount(map);
		result.setRows(shipRecords);
		result.setTotal(count);
		return result;
	}

	@Override
	public OperationResult cancelUpload(String id) {
		ebaySellerListDao.cancelUpload(id);
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}

	@Override
	public List<EbayLabelModel> getMoveMenuList() {
		return ebaySellerListDao.getMoveMenuList();
	}

	@Override
	public OperationResult labelOrder(Map<String, Object> label) {
		ebaySellerListDao.labelOrder(label);
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}

	@Override
	public OperationResult addLabel(EbayLabelModel label) {
		if(null == label.getId()){
			ebaySellerListDao.addLabel(label);
		}else{
			ebaySellerListDao.updateLabel(label);
		}
		
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}

	@Override
	public OperationResult deleteLabel(String id) {
		ebaySellerListDao.deleteLabel(id);
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}
	
	
}
