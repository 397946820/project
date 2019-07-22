package com.it.ocs.salesStatistics.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.salesStatistics.dao.ILightSaleOrderDao;
import com.it.ocs.salesStatistics.dao.ILightShipmentDao;
import com.it.ocs.salesStatistics.model.LightOrderItemModel;
import com.it.ocs.salesStatistics.model.LightOrderModel;
import com.it.ocs.salesStatistics.model.LightShipmentModel;
import com.it.ocs.salesStatistics.service.ILightSaleOrderService;
import com.it.ocs.salesStatistics.utils.Tools;
import com.it.ocs.salesStatistics.vo.LightOrderItemVo;
import com.it.ocs.salesStatistics.vo.LightOrderVo;
import com.it.ocs.salesStatistics.vo.LightShipmentVo;
import com.it.ocs.salesStatistics.vo.ReturnGoodsOrderVo;
import com.it.ocs.salesStatistics.vo.SaleOrderRefundVo;
import com.it.ocs.synchronou.dao.ISyncLightingOrderDao;
import com.it.ocs.synchronou.model.LightAccountModel;
import com.it.ocs.synchronou.model.ParseLightOrderItemXMLModel;
import com.it.ocs.synchronou.model.ParseLightOrderXMLNodeOne;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.impl.SyncLightingOrderServiceSupport;
import com.it.ocs.synchronou.service.impl.TemplateService;
import com.it.ocs.synchronou.util.HTTPUtils;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.sys.dao.IReturnOrderDao;

/**
 * 
 * @author yecaiqing
 *
 */

@Service
public class LightSaleOrderService implements ILightSaleOrderService {

	private final static Logger log = Logger.getLogger(LightSaleOrderService.class);

	@Autowired
	private ILightSaleOrderDao lightSaleOrderDao;

	@Autowired
	private IReturnOrderDao returnOrderDao;

	@Autowired
	private IEDADao iEDADao;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private ISyncLightingOrderDao syncLightingOrderDao;

	@Autowired
	private ILightShipmentDao lightShipmentDao;

	@Override
	public ResponseResult<LightOrderVo> findAll(RequestParam param) {
		ResponseResult<LightOrderVo> result = new ResponseResult<>();
		LightOrderModel model = BeanConvertUtil.mapToObject(param.getParam(), LightOrderModel.class);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<LightOrderModel> list = lightSaleOrderDao.queryList(model,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());
		PageInfo<LightOrderModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), LightOrderVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public ResponseResult<LightOrderItemVo> getLightOrderItemById(String parentId, int page, int rows) {
		ResponseResult<LightOrderItemVo> result = new ResponseResult<>();
		PageHelper.startPage(page, rows);
		List<LightOrderItemModel> list = lightSaleOrderDao.getLightOrderItemById(new Long(parentId));
		PageInfo<LightOrderItemModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), LightOrderItemVo.class));
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}

	@Override
	public Map<String, Object> getSaleOrderRefundByParentId(Map<String, Object> map) {
		Map<String, Object> result = Maps.newHashMap();
		List<SaleOrderRefundVo> list = lightSaleOrderDao
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
	public OperationResult updateOrderByOrderId(String orderId, String platform, String entityId) {
		try {
			LightAccountModel account = syncLightingOrderDao.getLightAccountByPlatform(platform);
			Document doc = getDocument(orderId, account, "GetOrderInfo", Maps.newHashMap());
			ParseLightOrderXMLNodeOne parse = new ParseLightOrderXMLNodeOne(doc, "");
			List<Map<String, Object>> parseResult = parse.getResult();
			Map<String, Object> map = parseResult.get(0);
			map.put("platform", platform);
			map.put("entity_id", entityId);
			syncLightingOrderDao.updateOrderData(map);
			ParseLightOrderItemXMLModel parseItem = new ParseLightOrderItemXMLModel(doc, "");
			List<Map<String, Object>> parseItemResult = parseItem.getResult();
			CollectionUtil.each(parseItemResult, new IAction<Map<String, Object>>() {
				@Override
				public void excute(Map<String, Object> hashMap) {
					hashMap.put("parent_id", entityId);
					Integer id = syncLightingOrderDao.getIdByOrderItemId(hashMap);
					hashMap.put("entity_id", id);
					syncLightingOrderDao.updateOrderItemData(hashMap);
				}
			});
			return new OperationResult();
		} catch (Exception e) {
			log.error("官网订单号:" + orderId + " 更新订单失败", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public OperationResult cancelOrderById(String id) {
		OperationResult result = new OperationResult();
		LightOrderModel model = lightSaleOrderDao.getLightOrderById(new Long(id));
		try {
			/*
			 * LightAccountModel account =
			 * syncLightingOrderDao.getLightAccountByPlatform(model.getPlatform(
			 * )); Document doc = getDocument(model.getOrderId(), account,
			 * "CancelLightOrder", Maps.newHashMap()); Element element =
			 * (Element) ((Element) ((Element)
			 * (doc.getRootElement().elements().get(0))).elements()
			 * .get(0)).elements().get(0); String flag =
			 * element.getStringValue(); if ("true".equals(flag)) {
			 * updateOrderByOrderId(model.getOrderId(), model.getPlatform(),
			 * model.getEntityId().toString()); } else { result.setErrorCode(1);
			 * }
			 */
			return result;
		} catch (Exception e) {
			log.error("官网订单号:" + model.getOrderId() + " 取消订单失败", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public ResponseResult<LightOrderVo> getLightOrderById(String entityId) {
		ResponseResult<LightOrderVo> result = new ResponseResult<>();
		List<LightOrderModel> list = Lists.newArrayList();
		list.add(lightSaleOrderDao.getLightOrderById(new Long(entityId)));
		result.setRows(CollectionUtil.beansConvert(list, LightOrderVo.class));
		result.setTotal(1);
		return result;
	}

	@Override
	public OperationResult uploadLightSaleTranNumber(Map<String, Object> map) {
		/*
		 * List<Map<String, Object>> param = (List<Map<String, Object>>)
		 * map.get("param"); String orderId = map.get("orderId").toString();
		 * LightOrderModel model =
		 * lightSaleOrderDao.getLightOrderByOrderId(orderId); Map<String,
		 * String> xmlValueMap = Maps.newHashMap(); String shipmentIncrementId =
		 * getShipmentIncrementId(orderId, xmlValueMap);
		 * xmlValueMap.put("shipmentIncrementId", shipmentIncrementId);
		 * LightAccountModel account =
		 * syncLightingOrderDao.getLightAccountByPlatform(map.get("platform").
		 * toString());
		 */
		try {
			/*
			 * for (Map<String, Object> hashMap : param) {
			 * xmlValueMap.put("carrier", "custom"); xmlValueMap.put("title",
			 * hashMap.get("carrier").toString());
			 * xmlValueMap.put("trackNumber",
			 * hashMap.get("trackNumber").toString()); tranNumber(xmlValueMap,
			 * account); updateOrderByOrderId(orderId, model.getPlatform(),
			 * model.getEntityId().toString()); }
			 */
			return new OperationResult();
		} catch (Exception e) {
			log.error("官网订单号:" + "上传跟踪号失败", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	private void tranNumber(Map<String, String> xmlValueMap, LightAccountModel account) {
		try {
			TemplateModel template = templateService.getTemplateContent("lightAddTrack", "light");
			String requestXml = TemplateService.formatTemplat(xmlValueMap, template.getContent());
			log.info("--------------requestXml--------------:" + requestXml);
			String responseXML = HTTPUtils.httpPostByXML(account.getUrl(), requestXml);
			log.info("--------------responseXML--------------:" + responseXML);
			Document doc = DocumentHelper.parseText(responseXML);
			Element element = (Element) ((Element) ((Element) (doc.getRootElement().elements().get(0))).elements()
					.get(0)).elements().get(0);
			String name = element.getName();
			Map<String, Object> map = Maps.newHashMap();
			map.put("orderId", xmlValueMap.get("orderId"));
			map.put("shipmentIncrementId", xmlValueMap.get("shipmentIncrementId"));
			if (!"faultcode".equals(name)) {
				map.put("trackingNumber", element.getStringValue());
			}
			map.put("trackNumber", xmlValueMap.get("trackNumber"));
			map.put("carrier", xmlValueMap.get("carrier"));
			map.put("title", xmlValueMap.get("title"));
			map.put("updatedAt", new Date());
			map.put("createdAt", new Date());
			lightShipmentDao.add(map);
			if ("faultcode".equals(name)) {
				log.info("官网订单号: " + xmlValueMap.get("orderId") + "上传跟踪号失败");
			}
		} catch (DocumentException e) {
			log.info("官网订单号: " + xmlValueMap.get("orderId") + "上传跟踪号失败");
		}

	}
	@Override
	public OperationResult uploadTranNumber(String orderId, List<Map<String, Object>> list,
			List<Map<String, Object>> itemQty) {
		try {
			Map<String, String> xmlValueMap = Maps.newHashMap();
			String shipmentIncrementId = lightShipmentDao.getShipmentIncrementIdByOrderId(orderId);
			if (ValidationUtil.isNullOrEmpty(shipmentIncrementId)) {
				itemQty = lightSaleOrderDao.getOrderItemIdAndQtyByOrderId(orderId);
				List<Map<String, Object>> qtyMap = CollectionUtil.searchList(itemQty,
						new IFunction<Map<String, Object>, Boolean>() {
							@Override
							public Boolean excute(Map<String, Object> item) {
								if (!ValidationUtil.isNullOrEmpty(item.get("QTY"))) {
									return Integer.parseInt(item.get("QTY").toString()) > 0;
								}
								return false;
							}
						});
				if (CollectionUtil.isNullOrEmpty(qtyMap)) {
					log.error("官网订单号:" + orderId + " 不满足上传跟踪号条件，或者物流已经私下处理过");
					return null;
				} else {
					shipmentIncrementId = getShipmentIncrementId(orderId, xmlValueMap, itemQty);
				}
			}
			if (shipmentIncrementId != null) {
				LightOrderModel model = lightSaleOrderDao.getLightOrderByOrderId(orderId);
				LightAccountModel account = syncLightingOrderDao.getLightAccountByPlatform(model.getPlatform());
				xmlValueMap.put("shipmentIncrementId", shipmentIncrementId);
				CollectionUtil.each(list, new IAction<Map<String, Object>>() {
					@Override
					public void excute(Map<String, Object> map) {
						xmlValueMap.put("carrier", "custom");
						xmlValueMap.put("title", map.get("carrier").toString());
						xmlValueMap.put("trackNumber", map.get("tranNumber").toString());
						tranNumber(xmlValueMap, account);
					}
				});
				return new OperationResult();
			}
		} catch (Exception e) {
			log.error("官网订单号:" + orderId + " 上传跟踪号失败", e);

		}
		return null;
	}

	private String getShipmentIncrementId(String orderId, Map<String, String> xmlValueMap,
			List<Map<String, Object>> itemQty) {
		try {
			LightOrderModel model = lightSaleOrderDao.getLightOrderByOrderId(orderId);
			LightAccountModel account = syncLightingOrderDao.getLightAccountByPlatform(model.getPlatform());
			SyncLightingOrderServiceSupport support = new SyncLightingOrderServiceSupport(syncLightingOrderDao,
					templateService);
			String sessionId = support.getSessionIdByAccount(account);
			xmlValueMap.put("sessionId", sessionId);
			xmlValueMap.put("orderId", orderId);
			TemplateModel template = templateService.getTemplateContent("getOrderShipment", "light");
			String requestXml = TemplateService.formatTemplat(xmlValueMap, template.getContent());
			String temp1 = "<item xsi:type=\"ns2:Map\">${itemsQty}</item>";
			String temp2 = "<item><key xsi:type=\"xsd:int\">${order_item_id}</key><value xsi:type=\"xsd:int\">${qty}</value></item>";
			StringBuffer buffer = new StringBuffer();
			CollectionUtil.each(itemQty, new IAction<Map<String, Object>>() {
				@Override
				public void excute(Map<String, Object> map) {
					buffer.append(temp2.replace("${order_item_id}", map.get("ORDER_ITEM_ID").toString())
							.replace("${qty}", map.get("QTY").toString()));
				}
			});
			requestXml = requestXml.replace("${itemsQty}", temp1.replace("${itemsQty}", buffer.toString()));
			log.info("---------创建发货单requestXml---------:" + requestXml);
			String responseXML = HTTPUtils.httpPostByXML(account.getUrl(), requestXml);
			log.info("---------创建发货单responseXML---------:" + responseXML);
			Document doc = DocumentHelper.parseText(responseXML);
			Element element = (Element) ((Element) ((Element) (doc.getRootElement().elements().get(0))).elements()
					.get(0)).elements().get(0);
			if (element.getName().equals("faultcode")) {
				log.error("官网订单号" + orderId + "创建货运ID失败!");
				return null;
			}
			return element.getStringValue();
		} catch (DocumentException e) {
			log.error("解析失败", e);
		}
		return null;
	}

	@Override
	public ResponseResult<LightShipmentVo> getLightShips(String orderId) {
		ResponseResult<LightShipmentVo> result = new ResponseResult<>();
		List<LightShipmentModel> list = lightShipmentDao.getLightShipsByOrderId(orderId);
		result.setRows(CollectionUtil.beansConvert(list, LightShipmentVo.class));
		result.setTotal(list.size());
		return result;
	}

	private Document getDocument(String orderId, LightAccountModel account, String name,
			Map<String, String> xmlValueMap) {
		try {
			SyncLightingOrderServiceSupport support = new SyncLightingOrderServiceSupport(syncLightingOrderDao,
					templateService);
			String sessionId = support.getSessionIdByAccount(account);
			TemplateModel template = templateService.getTemplateContent(name, "light");
			xmlValueMap.put("sessionId", sessionId);
			xmlValueMap.put("orderId", orderId);
			String requestXml = TemplateService.formatTemplat(xmlValueMap, template.getContent());
			String responseXML = HTTPUtils.httpPostByXML(account.getUrl(), requestXml);
			return DocumentHelper.parseText(responseXML);
		} catch (DocumentException e) {
			log.error("解析失败", e);
		}
		return null;
	}

	@Override
	public Boolean trackNumberExist(LightShipmentVo shipment) {
		return lightShipmentDao.getByObject(shipment) == null;
	}

	@Override
	public OperationResult refundOrder(Map<String, Object> map) {
		OperationResult result = new OperationResult();
		try {
			String orderId = map.get("orderId").toString();
			LightOrderModel model = lightSaleOrderDao.getLightOrderByOrderId(orderId);
			LightAccountModel account = syncLightingOrderDao.getLightAccountByPlatform(model.getPlatform());
			Map<String, String> xmlValueMap = Tools.getMap(map);
			Document doc = getDocument(orderId, account, "refundLightOrder", xmlValueMap);
			Element element = (Element) ((Element) ((Element) (doc.getRootElement().elements().get(0))).elements()
					.get(0));
			Element e1 = (Element) element.elements().get(0);
			if (e1.getName().equals("callReturn")) {
				updateOrderByOrderId(orderId, model.getPlatform(),
						lightSaleOrderDao.getLightOrderByOrderId(orderId).getEntityId().toString());
			} else {
				result.setErrorCode(1);
				e1 = (Element) element.elements().get(1);
				log.error("官网订单号为 " + orderId + " 退款失败,原因为 " + e1.getStringValue());
			}
			return result;
		} catch (Exception e) {
			result.setErrorCode(1);
			log.error("退款失败!", e);
		}
		return result;
	}

	@Override
	public ResponseResult<ReturnGoodsOrderVo> getSaleOrderInformationByParentId(String parentId) {
		ResponseResult<ReturnGoodsOrderVo> result = new ResponseResult<>();
		List<ReturnGoodsOrderVo> list = lightSaleOrderDao.getSaleOrderInformationByParentId(new Long(parentId));
		result.setRows(list);
		result.setTotal(list.size());
		return result;
	}

	@Override
	public ResponseResult<ReturnGoodsOrderVo> findReturnInformationByParentId(String parentId) {
		ResponseResult<ReturnGoodsOrderVo> result = new ResponseResult<>();
		List<ReturnGoodsOrderVo> list = lightSaleOrderDao.findReturnInformationByParentId(new Long(parentId));
		result.setRows(list);
		result.setTotal(list.size());
		return result;
	}

	@Override
	public OperationResult handleReissueTranNumber(Map<String, Object> param) {
		try {
			String orderId = String.valueOf(param.get("orderId"));
			// 需要判断是补发单与线下单（结构也与补发一致，但是在订单表里面没有记录，需要与一般补发单区别对待）
			if (orderId.startsWith("OCS")) {
				param.put("account", this.lightSaleOrderDao.getOCSAccount(param));
			} else {
				LightOrderModel model = lightSaleOrderDao.getLightOrderByOrderId(orderId);
				// LightAccountModel account =
				// syncLightingOrderDao.getLightAccountByPlatform(model.getPlatform());
				// param.put("account", account.getAccount());
				// sys_return_order_final表中light的数据account字段存储的都是UK/DE/US，与LIGHT_ORDER表的platform字段是对应的。
				param.put("account", model.getPlatform());
			}
			this.lightSaleOrderDao.handleReissueTranNumber(param);
			return new OperationResult();
		} catch (Exception e) {
			return new OperationResult(1, "处理补发单跟踪号失败", null, null);
		}
	}

	/*
	 * public static void main(String[] args) {
	 * org.springframework.context.ApplicationContext context = new
	 * org.springframework.context.support.ClassPathXmlApplicationContext(
	 * "classpath:spring.xml"); ILightSaleOrderService service =
	 * (ILightSaleOrderService) context.getBean("lightSaleOrderService");
	 * Map<String, Object> param = new HashMap<String, Object>();
	 * param.put("tarckingNum", "R8989898989"); param.put("orderId",
	 * "OCS1528863134438"); param.put("tarckingService",
	 * WarehouseConstant.CARRIER_ID_DHL); param.put("times", "1");
	 * param.put("platform", "light"); OperationResult or =
	 * service.handleReissueTranNumber(param);
	 * System.out.println(StringUtil.instanceDetail(or)); }
	 */
}
