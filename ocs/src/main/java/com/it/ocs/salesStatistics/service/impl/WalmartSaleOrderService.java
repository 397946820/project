package com.it.ocs.salesStatistics.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.salesStatistics.dao.IWalmartSaleOrderDao;
import com.it.ocs.salesStatistics.model.WalmartOrderLineModel;
import com.it.ocs.salesStatistics.model.WalmartOrderModel;
import com.it.ocs.salesStatistics.service.IWalmartSaleOrderService;
import com.it.ocs.salesStatistics.utils.Tools;
import com.it.ocs.salesStatistics.vo.SaleOrderRefundVo;
import com.it.ocs.salesStatistics.vo.WalmartOrderLineVo;
import com.it.ocs.salesStatistics.vo.WalmartOrderVo;
import com.it.ocs.synchronou.dao.ISyncWalmartOrderDao;
import com.it.ocs.synchronou.model.ParseWalmartOrderXMLModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.model.WalmartAccountModel;
import com.it.ocs.synchronou.service.impl.SyncWalmartOrderService;
import com.it.ocs.synchronou.service.impl.TemplateService;
import com.it.ocs.sys.dao.IReturnOrderDao;
import com.it.ocs.walmart.model.WalmartRequestModel;
import com.it.ocs.walmart.utils.WalmartHttpUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WalmartSaleOrderService implements IWalmartSaleOrderService {

	private final static Logger log = Logger.getLogger(WalmartSaleOrderService.class);

	@Autowired
	private IWalmartSaleOrderDao walmartSaleOrderDao;

	@Autowired
	private ISyncWalmartOrderDao syncWalmartOrderDao;

	@Autowired
	private IReturnOrderDao returnOrderDao;

	@Autowired
	private IEDADao iEDADao;

	@Autowired
	private SyncWalmartOrderService syncWalmartOrderService;

	@Autowired
	private TemplateService templateService;

	@Override
	public ResponseResult<WalmartOrderVo> findAll(RequestParam param) {
		ResponseResult<WalmartOrderVo> result = new ResponseResult<>();
		WalmartOrderModel model = BeanConvertUtil.mapToObject(param.getParam(), WalmartOrderModel.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<WalmartOrderModel> list = walmartSaleOrderDao.queryList(model,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<WalmartOrderModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), WalmartOrderVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public ResponseResult<WalmartOrderLineVo> getWalmartOrderLineById(String parentId, int page, int rows) {
		ResponseResult<WalmartOrderLineVo> result = new ResponseResult<>();
		PageHelper.startPage(page, rows);
		List<WalmartOrderLineModel> list = walmartSaleOrderDao.getWalmartOrderLineById(new Long(parentId));
		PageInfo<WalmartOrderLineModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), WalmartOrderLineVo.class));
		result.setTotal((int) pageInfo.getTotal());
		result.setSource(walmartSaleOrderDao.getTotalPriceById(new Long(parentId)) + "");
		return result;
	}

	@Override
	public OperationResult cancelOrder(String id, String orderCase) {
		try {
			/*TemplateModel template = templateService.getTemplateContent("cancelWalmartOrder", "walmart");
			WalmartOrderLineModel model = walmartSaleOrderDao.getById(new Long(id));
			WalmartAccountModel account = syncWalmartOrderDao.getAccounts().get(0);
			WalmartRequestModel wm = new WalmartRequestModel(account.getAccount(),
					"https://marketplace.walmartapis.com/v3/orders/" + model.getPurchaseOrderId() + "/cancel",
					account.getToken(), WalmartRequestModel.POST, "");
			Map<String, String> map = Maps.newHashMap();
			map.put("lineNumber", model.getLineNumber().toString());
			map.put("orderLineStatus", model.getOrderLineStatus());
			map.put("cancellationReason", orderCase);
			map.put("statusQuantityMeas", model.getStatusQuantityMeas());
			map.put("statusQuantityAmount", model.getStatusQuantityAmount().toString());
			String content = templateService.formatTemplat(map, template.getContent());
			synchronizationWalmartOrder(wm, content);*/
			return new OperationResult();
		} catch (Exception e) {
			log.error("取消订单失败", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Boolean isExist(String id) {
		List<String> strings = walmartSaleOrderDao.isExist(new Long(id));
		return CollectionUtil.isNullOrEmpty(strings) ? false : true;
	}

	@Override
	public ResponseResult<WalmartOrderLineVo> getExistWalmartOrderLineById(String id, int page, int rows) {
		ResponseResult<WalmartOrderLineVo> result = new ResponseResult<>();
		PageHelper.startPage(page, rows);
		List<WalmartOrderLineModel> list = walmartSaleOrderDao.getExistWalmartOrderLineById(new Long(id));
		PageInfo<WalmartOrderLineModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), WalmartOrderLineVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public OperationResult updateOrder(String purchaseOrderId) {
		try {
			WalmartAccountModel account = syncWalmartOrderDao.getAccounts().get(0);
			WalmartRequestModel wm = new WalmartRequestModel(account.getAccount(),
					"https://marketplace.walmartapis.com/v3/orders/" + purchaseOrderId, account.getToken(),
					WalmartRequestModel.GET, "");
			wm.setRequestParam(new HashMap<>());
			String response = WalmartHttpUtil.httpGet(wm);
			Document doc = DocumentHelper.parseText(response);
			ParseWalmartOrderXMLModel parse = new ParseWalmartOrderXMLModel(doc, "http://walmart.com/mp/v3/orders");
			List<Map<String, Object>> data = parse.getResult();
			syncWalmartOrderService.saveWalmartData(data);
			return new OperationResult();
		} catch (Exception e) {
			log.error("获取订单失败", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public OperationResult refundOrder(Map<String, Object> map) {
		try {
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("param");
			WalmartAccountModel account = syncWalmartOrderDao.getAccounts().get(0);
			WalmartRequestModel wm = new WalmartRequestModel(account.getAccount(),
					"https://marketplace.walmartapis.com/v3/orders/" + list.get(0).get("purchaseOrderId") + "/refund",
					account.getToken(), WalmartRequestModel.POST, "");
			TemplateModel template = templateService.getTemplateContent("refundWalmartOrder", "walmart");
			TemplateModel lineTemp = templateService.getTemplateContent("refundWalmartOrderLine", "walmart");
			StringBuffer buffer = new StringBuffer();
			for (Map<String, Object> param : list) {
				buffer.append(templateService.formatTemplat(Tools.getMap(param), lineTemp.getContent()));
			}
			Map<String, Object> tempMap = list.get(0);
			tempMap.put("lines", buffer);
			String content = templateService.formatTemplat(Tools.getMap(tempMap), template.getContent());
			synchronizationWalmartOrder(wm, content);
			return new OperationResult();
		} catch (Exception e) {
			log.error("退款失败", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public ResponseResult<WalmartOrderLineVo> getById(String id) {
		ResponseResult<WalmartOrderLineVo> result = new ResponseResult<>();
		List<WalmartOrderLineModel> list = Lists.newArrayList();
		list.add(walmartSaleOrderDao.getById(new Long(id)));
		result.setRows(CollectionUtil.beansConvert(list, WalmartOrderLineVo.class));
		result.setTotal(1);
		return result;
	}

	@Override
	public OperationResult uploadWalmartSaleTranNumber(Map<String, Object> map) {
		try {
			/*Map<String, Object> param = (Map<String, Object>) map.get("param");
			String format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
			param.put("shipDateTimeUtc", format);
			TemplateModel template = templateService.getTemplateContent("uploadWalmartSaleTranNumber", "walmart");
			TemplateModel templateLine = templateService.getTemplateContent("uploadWalmartSaleTranNumberLine", "walmart");
			String orderLines = templateService.formatTemplat(Tools.getMap(param), templateLine.getContent());
			param.put("orderLines", orderLines);
			String content = templateService.formatTemplat(Tools.getMap(param), template.getContent());
			WalmartAccountModel account = syncWalmartOrderDao.getAccounts().get(0);
			WalmartRequestModel wm = new WalmartRequestModel(account.getAccount(),
					"https://marketplace.walmartapis.com/v3/orders/" + param.get("purchaseOrderId") + "/shipping",
					account.getToken(), WalmartRequestModel.POST, "");
			synchronizationWalmartOrder(wm, content);*/
			return new OperationResult();
		} catch (Exception e) {
			log.error("上传跟踪号失败", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public OperationResult uploadTranNumber(String orderId, List<Map<String, Object>> list) {
		try {
			WalmartOrderModel orderModel = walmartSaleOrderDao.getByOrderId(orderId);
			TemplateModel template = templateService.getTemplateContent("uploadWalmartSaleTranNumber", "walmart");
			TemplateModel templateLine = templateService.getTemplateContent("uploadWalmartSaleTranNumberLine",
					"walmart");
			Boolean flag = true;
			for (Map<String, Object> map : list) {
				List<Map<String, Object>> subList = Lists.newArrayList();
				subList.add(map);
				flag = tranNumber(orderId, subList, orderModel, template, templateLine) && flag;
			}
			if (!flag) {
				return null;
			}
			return new OperationResult();
		} catch (Exception e) {
			log.error("沃尔玛订单号" + orderId + "上传跟踪号失败", e);
		}
		return null;
	}

	private Boolean tranNumber(String orderId, List<Map<String, Object>> list, WalmartOrderModel orderModel,
			TemplateModel template, TemplateModel templateLine) {
		StringBuffer orderLines = new StringBuffer();
		CollectionUtil.each(list, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> map) {
				WalmartOrderLineModel model = walmartSaleOrderDao.getWalmartOrderLineModelByLineNumber(orderId,
						new Integer(map.get("lineNumber").toString()));
				Map<String, Object> param = Maps.newHashMap();
				param.put("purchaseOrderId", orderId);
				param.put("shipDateTimeUtc", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));
				param.put("lineNumber", map.get("lineNumber"));
				param.put("orderLineStatus", model.getOrderLineStatus());
				param.put("statusQuantityMeas", model.getStatusQuantityMeas());
				param.put("statusQuantityAmount", model.getStatusQuantityAmount());
				param.put("shipCarrier", map.get("shipCarrier"));
				param.put("shipMethodCode", orderModel.getMethodCode());
				param.put("shipTrackingUrl", "http://walmart.narvar.com");
				param.put("shipTrackingNumber_", map.get("shipTrackingNumber"));
				orderLines.append(templateService.formatTemplat(Tools.getMap(param), templateLine.getContent()));
			}
		});
		WalmartAccountModel account = syncWalmartOrderDao.getAccounts().get(0);
		WalmartRequestModel wm = new WalmartRequestModel(account.getAccount(),
				"https://marketplace.walmartapis.com/v3/orders/" + orderId + "/shipping", account.getToken(),
				WalmartRequestModel.POST, "");
		Map<String, String> map = Maps.newHashMap();
		map.put("orderLines", orderLines.toString());
		String content = templateService.formatTemplat(map, template.getContent());
		Map<String, String> params = Maps.newHashMap();
		params.put("content", content);
		wm.setRequestParam(params);
		String response = WalmartHttpUtil.httpPost(wm);
		if (response == null) {
			log.error("请求沃尔玛失败!");
			return false;
		}
		return true;
	}

	private void synchronizationWalmartOrder(WalmartRequestModel wm, String content) {
		try {
			Map<String, String> params = Maps.newHashMap();
			params.put("content", content);
			wm.setRequestParam(params);
			String response = WalmartHttpUtil.httpPost(wm);
			if (response != null) {
				Document doc = DocumentHelper.parseText(response);
				ParseWalmartOrderXMLModel parse = new ParseWalmartOrderXMLModel(doc, "http://walmart.com/mp/v3/orders");
				List<Map<String, Object>> data = parse.getResult();
				syncWalmartOrderService.saveWalmartData(data);
			} else {
				log.error("请求沃尔玛失败!");
			}
		} catch (DocumentException e) {
			log.error("解析失败", e);
		}

	}

	@Override
	public Map<String, Object> getSaleOrderRefundByParentId(Map<String, Object> map) {
		Map<String, Object> result = Maps.newHashMap();
		List<SaleOrderRefundVo> list = walmartSaleOrderDao
				.getSaleOrderRefundByParentId(new Long(map.get("id").toString()));
		result.put("rows", list);
		result.put("total", list.size());
		int count = iEDADao.countWestOrderList(map);
		String account = map.get("account").toString();
		if (("US".equals(account) && count != 0) || !"US".equals(account)) {
			result.put("edaOrderNum", returnOrderDao.findOrderReturnSeq());
		}
		return result;
	}

	@Override
	public Long countOrderByStatus(Map<String, Object> map) {
		return walmartSaleOrderDao.getCount(BeanConvertUtil.mapToObject(map, WalmartOrderModel.class));
	}

	@Override
	public OperationResult uploadTranNumberByJSON(String orderId, List<Map<String, Object>> list) {
		OperationResult or = new OperationResult();
		WalmartAccountModel account = syncWalmartOrderDao.getAccounts().get(0);
		WalmartRequestModel wm = new WalmartRequestModel(account.getAccount(),
				"https://marketplace.walmartapis.com/v3/orders/"+orderId+"/shipping", account.getToken(),
				WalmartRequestModel.POST, "");
		Map<String, String> params = Maps.newHashMap();
		String content = getShippingUploadContent(orderId,list);
		//检测所有行都已经上传
		if(null == content){
			or.setData("success");
			return or;
		}
		params.put("content", content);
		wm.setRequestParamByJSON(params);
		String response = WalmartHttpUtil.httpPostByJSON(wm);
		if (response == null) {
			log.error("请求沃尔玛失败!"+orderId+":"+content);
			return null;
		}else{
			return or;
		}
		
	}

	private String getShippingUploadContent(String orderId, List<Map<String, Object>> list) {
		WalmartOrderModel orderModel = walmartSaleOrderDao.getByOrderId(orderId);
		JSONObject orderShipment = new JSONObject();
		JSONObject orderLines = new JSONObject();
		JSONArray orderLine = new JSONArray();
		for(Map<String, Object> map : list){
			map.put("orderId", orderId);
			//判断此lineNumber是否上传成功
			if(walmartSaleOrderDao.hasSuccess(map)==0){
				continue;
			}
			JSONObject ol = new JSONObject();
			ol.put("lineNumber", map.get("lineNumber").toString());
			
			JSONObject ols = new JSONObject();
			ols.put("status", "Shipped");
			JSONObject statusQuantity  = new JSONObject();
			statusQuantity.put("unitOfMeasurement", "EACH");
			statusQuantity.put("amount", 1);
			ols.put("statusQuantity", statusQuantity);
			
			JSONObject trackingInfo = new JSONObject();
			trackingInfo.put("shipDateTime", System.currentTimeMillis());
			JSONObject carrierName = new JSONObject();
			carrierName.put("carrier", map.get("shipCarrier"));
			trackingInfo.put("carrierName", carrierName);
			trackingInfo.put("methodCode", orderModel.getMethodCode());
			trackingInfo.put("trackingNumber", map.get("shipTrackingNumber"));
			trackingInfo.put("shipTrackingUrl", "http://walmart.narvar.com");
			ols.put("trackingInfo", trackingInfo);
			JSONArray orderLineStatus  = new JSONArray();
			orderLineStatus.add(ols);
			
			JSONObject orderLineStatuses = new JSONObject();
			orderLineStatuses.put("orderLineStatus", orderLineStatus);
			ol.put("orderLineStatuses", orderLineStatuses);
			
			orderLine.add(ol);
			
		}
		//没有可上传的linenumber行
		if(orderLine.size() == 0){
			return null;
		}
		orderLines.put("orderLine", orderLine);
		orderShipment.put("orderLines", orderLines);
		JSONObject re = new JSONObject();
		re.put("orderShipment", orderShipment);
		return re.toString();
	}
}
