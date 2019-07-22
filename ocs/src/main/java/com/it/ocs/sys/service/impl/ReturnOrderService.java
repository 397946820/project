package com.it.ocs.sys.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.OrderStatusEnum;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.customerCenter.dao.ICustomerTicketsDao;
import com.it.ocs.paymentManagement.model.PayPalRefundModel;
import com.it.ocs.paymentManagement.service.inner.IPayPalService;
import com.it.ocs.salesStatistics.dao.ILightSaleOrderDao;
import com.it.ocs.salesStatistics.service.ILightSaleOrderService;
import com.it.ocs.salesStatistics.service.IWalmartSaleOrderService;
import com.it.ocs.salesStatistics.vo.ReturnGoodsOrderVo;
import com.it.ocs.synchronou.service.IEbaySellerListService;
import com.it.ocs.sys.dao.IReturnOrderDao;
import com.it.ocs.sys.model.ReturnOrderModel;
import com.it.ocs.sys.service.IReturnOrderService;
import com.it.ocs.sys.utils.ReturnOrderSupport;
import com.it.ocs.sys.vo.ReturnOrderVo;

/**
 * 
 * @author yecaiqing
 *
 */

@Service
@Transactional
public class ReturnOrderService implements IReturnOrderService {

	private final Logger log = Logger.getLogger(ReturnOrderService.class);

	@Autowired
	private IReturnOrderDao returnOrderDao;

	@Autowired
	private ILightSaleOrderDao lightSaleOrderDao;

	@Autowired
	private IWalmartSaleOrderService walmartSaleOrderService;

	@Autowired
	private ILightSaleOrderService lightSaleOrderService;

	@Autowired
	private IPayPalService paypalService;

	@Autowired
	private IEbaySellerListService ebaySellerListService;

	@Autowired
	private ICustomerTicketsDao customerTickets;

	private static Map<String, String> platformCountryMap = Maps.newHashMap();

	static {
		platformCountryMap.put("amazon", "Amazon");
		platformCountryMap.put("light", "LE");
		platformCountryMap.put("ebay", "eBay");
		platformCountryMap.put("walmart", "Walmart");
		platformCountryMap.put("Amazon.com", "US");
		platformCountryMap.put("Amazon.de", "DE");
		platformCountryMap.put("Amazon.fr", "FR");
		platformCountryMap.put("Amazon.es", "ES");
		platformCountryMap.put("Amazon.co.uk", "UK");
		platformCountryMap.put("Amazon.it", "IT");
		platformCountryMap.put("Amazon.co.jp", "JP");
		platformCountryMap.put("Amazon.ca", "CA");
		platformCountryMap.put("US", "US");
		platformCountryMap.put("Germany", "DE");
		platformCountryMap.put("France", "FR");
		platformCountryMap.put("eBayMotors", "US");
		platformCountryMap.put("Italy", "IT");
		platformCountryMap.put("UK", "UK");
		platformCountryMap.put("DE", "DE");
		platformCountryMap.put("USA", "US");
	}

	@Override
	public ResponseResult<ReturnOrderVo> findAll(RequestParam param) {
		ResponseResult<ReturnOrderVo> result = new ResponseResult<>();
		ReturnOrderVo orderVo = BeanConvertUtil.mapToObject(param.getParam(), ReturnOrderVo.class);
		if (orderVo == null) {
			orderVo = new ReturnOrderVo();
		}
		orderVo.setCreateBy(UserUtils.getUserId());
		List<ReturnOrderModel> list = returnOrderDao.findAll(orderVo,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder(),
				param.getStartRow(), param.getEndRow());
		result.setRows(ReturnOrderSupport.getRows(list, "<br/>"));
		result.setTotal(returnOrderDao.getTotal(orderVo));
		return result;
	}

	@Override
	public List<Map<String, String>> getReturnOrderCause(String id) {
		return returnOrderDao.getReturnOrderCause(new Long(id));
	}

	@Override
	public List<Map<String, String>> getReturnOrderCauseAll() {
		return returnOrderDao.getReturnOrderCauseAll();
	}

	@Override
	public List<String> getAccountByPlatform(String platform) {
		return returnOrderDao.getAccountByPlatform(platform);
	}

	@Override
	public List<String> getSiteByPlatformAndAccount(String platform, String account) {
		return returnOrderDao.getSiteByPlatformAndAccount(platform, account);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OperationResult orderRefund(Map<String, Object> map) {
		OperationResult result = new OperationResult();
		Map<String, Object> order = (Map<String, Object>) map.get("order");
		List<Map<String, Object>> items = (List<Map<String, Object>>) map.get("items");
		String platform = order.get("platform").toString();
		try {
			if (platform.equals("walmart")) {
				List<Map<String, Object>> list = Lists.newArrayList();
				CollectionUtil.each(items, new IAction<Map<String, Object>>() {
					@Override
					public void excute(Map<String, Object> model) {
						Map<String, Object> hash = Maps.newHashMap();
						hash.put("purchaseOrderId", order.get("orderId"));
						hash.put("lineNumber", model.get("lineNumber"));
						hash.put("itemPriceCurrency", order.get("currencyCode"));
						hash.put("itemPrice_", "-" + model.get("returnCost"));
						list.add(hash);
					}
				});
				Map<String, Object> param = Maps.newHashMap();
				param.put("param", list);
				// 调沃尔玛退款的接口
				result = walmartSaleOrderService.refundOrder(param);
			} else if (platform.equals("light")) {
				/*String orderId = order.get("orderId").toString();
				if(lightSaleOrderDao.getDays(orderId) > 180) {
					result.setErrorCode(2);
				} else {
					Map<String, Object> param = Maps.newHashMap();
					param.put("orderId", orderId);
					param.put("adjustmentPositive", order.get("adjustmentPositive"));
					param.put("orderItemId", items.get(0).get("orderItemId"));
					result = lightSaleOrderService.refundOrder(param);
				}*/
			} else if (platform.equals("ebay")) {
				Double refundAmount = 0d;
				if (order.get("isConsumerPaid").equals("1")) {
					refundAmount = new Double(order.get("shipCost").toString());
				}
				for (Map<String, Object> hash : items) {
					refundAmount += new Double(hash.get("returnCost").toString());
				}
				String transaction = returnOrderDao.getPaypalTransactionnumber(order.get("account"),
						order.get("orderId"));
				if (StringUtils.isBlank(transaction)) {
					throw new RuntimeException("paypal交易号为空");
				}
				if (refundAmount != 0) {
					PayPalRefundModel model = new PayPalRefundModel();
					model.setRefundAmount(refundAmount);
					model.setCurrency(order.get("currencyCode").toString());
					model.setPayPalAccount(returnOrderDao.getPaypalAccount(order.get("account"), order.get("orderId")));
					model.setTransaction(transaction);
					result = paypalService.payPalRefund(model);
					if (result.getErrorCode() == 0) {
						ebaySellerListService.syncOrderByOrderId(order.get("orderId").toString(),
								order.get("account").toString());
					}
				}
			} else {
				CollectionUtil.each(items, new IAction<Map<String, Object>>() {
					@Override
					public void excute(Map<String, Object> hash) {
						hash.put("parentId", order.get("id"));
						hash.put("orderType", "0");
						hash.put("platform", order.get("platform"));
						hash.put("account", order.get("account"));
						hash.put("site", order.get("site"));
						hash.put("orderId", order.get("orderId"));
						hash.put("receiptNo", order.get("receiptNo"));
						hash.put("receivingTime", order.get("receivingTime").toString().equals("") ? new Date()
								: order.get("receivingTime"));
						hash.put("createBy", UserUtils.getUserId());
						hash.put("updateBy", UserUtils.getUserId());
						returnOrderDao.addRefundOrder(hash);
					}
				});
			}
			if (result.getErrorCode() != 1) {
				// 改变订单状态是否确认收货收款
				Map<String, Object> updateMap = Maps.newHashMap();
				updateMap.put("id", order.get("id"));
				if (StringUtils.isNotBlank(order.get("trackingNum").toString())) {
					updateMap.put("trackingNum", order.get("trackingNum"));
				}
				updateMap.put("isReceiving", 1);
				updateMap.put("updateDate", new Date());
				returnOrderDao.updateReturnOrder(updateMap);
				order.put("solution", "退款");
				insertCustomerTickets(order, items);
				return result;
			} else {
				log.error("平台为 " + platform + "确认退款收货失败!");
			}
		} catch (Exception e) {
			log.error("平台为 " + platform + "确认退款收货失败!", e);
		}
		return result;
	}

	@Override
	public OperationResult orderReissue(Map<String, Object> map, String id) {
		Map<String, Object> order = (Map<String, Object>) map.get("order");
		List<Map<String, Object>> items = (List<Map<String, Object>>) map.get("items");
		String platform = order.get("platform").toString();
		try {
			CollectionUtil.each(items, new IAction<Map<String, Object>>() {
				@Override
				public void excute(Map<String, Object> hash) {
					hash.put("parentId", order.get("id"));
					hash.put("orderType", id);
					hash.put("platform", order.get("platform"));
					hash.put("account", order.get("account"));
					hash.put("site", order.get("site"));
					hash.put("orderId", order.get("orderId"));
					hash.put("createBy", UserUtils.getUserId());
					hash.put("updateBy", UserUtils.getUserId());
					returnOrderDao.addRefundOrder(hash);
				}
			});

			Map<String, Object> updateMap = Maps.newHashMap();
			updateMap.put("id", order.get("id"));
			if (StringUtils.isNotBlank(order.get("trackingNum") == null ? null : order.get("trackingNum").toString())) {
				updateMap.put("trackingNum", order.get("trackingNum"));
			}
			if (id.equals("1")) {
				updateMap.put("isReissue", 1);
				order.put("solution", "补发");
				insertCustomerTickets(order, items);
			} else if (id.equals("2")) {
				updateMap.put("isConfirmOrder", 1);
			}
			updateMap.put("updateDate", new Date());
			returnOrderDao.updateReturnOrder(updateMap);
			return new OperationResult();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private void insertCustomerTickets(Map<String, Object> order, List<Map<String, Object>> items) {
		for (int i = 0; i < items.size(); i++) {
			Map<String, Object> map = items.get(i);
			String sku = returnOrderDao.getSku(map.get("sku").toString());
			sku = sku == null ? map.get("sku").toString() : sku;
			if (sku.contains("-")) {
				sku = sku.substring(0, sku.indexOf("-"));
			}
			Map<String, Object> customer = returnOrderDao.getCatagories(sku);
			if (customer == null) {
				customer = Maps.newHashMap();
			}
			if (order.get("platform").toString().equals("amazon")
					|| order.get("platform").toString().equals("ebay")) {
				customer.put("country", platformCountryMap.get(order.get("site").toString()));
			} else {
				customer.put("country", platformCountryMap.get(order.get("account").toString()));
			}
			customer.put("orderNumber", order.get("orderId"));
			customer.put("sku", map.get("sku"));
			customer.put("orderDate",
					returnOrderDao.getOrderTime(order.get("platform"), order.get("orderId"), order.get("account")));
			customer.put("defectiveQuantity", map.get("qty"));
			customer.put("problemTypeLvl2", map.get("productCaseType"));
			/* customer.put("totalDays", map.get("useHourDay")); */
			customer.put("problemDate", returnOrderDao.getDate(order.get("id")));
			customer.put("solution", order.get("solution"));
			customer.put("replacementTimes", 0);
			customer.put("operators", returnOrderDao.getNickName(order.get("id")));
			customer.put("remark", order.get("cause"));
			if(order.get("platform").toString().equals("light")) {
				if(i == 0) {
					customer.put("amount", order.get("adjustmentPositive"));
				} else {
					customer.put("amount", 0);
				}
			} else {
				customer.put("amount", map.get("returnCost"));
			}
			customer.put("platform", platformCountryMap.get(order.get("platform").toString()));
			customer.put("currency", returnOrderDao.getCurrency(customer.get("country").equals("UK") ? "GB":customer.get("country")));
			if (order.containsKey("id") && null != order.get("id")) {
				Long id = ((BigDecimal) order.get("id")).longValue();
				List<Map<String,String>> queryResult = returnOrderDao.getById(id);
				if (!CollectionUtil.isNullOrEmpty(queryResult)) {
					Map<String,String> ro = queryResult.get(0);
					customer.put("payMethod", ro.get("PAY_METHOD"));
				}
			}
			
			customerTickets.insertCustomer(customer);
		}
		
	}

	@Override
	public OperationResult cancelApplication(String id) {
		try {
			returnOrderDao.cancelApplication(new Long(id));
			return new OperationResult();
		} catch (Exception e) {
			log.error("取消申请失败!", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public OperationResult saveEditReturnOrder(Map<String, Object> map, String orderType) {
		Map<String, Object> order = (Map<String, Object>) map.get("order");
		List<Map<String, Object>> items = (List<Map<String, Object>>) map.get("items");
		try {
			String id = order.get("id").toString();
			order.put("updateBy", UserUtils.getUserId());
			if (!orderType.equals("0") && order.get("name") != null && order.get("postalCode") != null) {
				Map<String, String> deliveryAddress = new LinkedHashMap<>();
				deliveryAddress.put("addressLine1", order.get("addressLine1").toString());
				deliveryAddress.put("addressLine2", order.get("addressLine2").toString());
				deliveryAddress.put("city", order.get("city").toString());
				deliveryAddress.put("country", order.get("country").toString());
				deliveryAddress.put("name", order.get("name").toString());
				deliveryAddress.put("phone", order.get("phone").toString());
				deliveryAddress.put("postalCode", order.get("postalCode").toString());
				deliveryAddress.put("provState", order.get("provState").toString());
				order.put("deliveryAddress", JSONUtils.toJSONString(deliveryAddress));
			} else {
				order.put("deliveryAddress", null);
			}
			if (StringUtils.isBlank(id)) {
				if (StringUtils.isBlank(order.get("site").toString())) {
					order.put("site", order.get("account"));
				}
				order.put("orderType", orderType);
				order.put("createBy", UserUtils.getUserId());
				if (orderType.equals("2")) {
					order.put("orderId", "OCS" + new Date().getTime());
				}
				returnOrderDao.addReturnOrder(order);
			} else {
				order.put("updateDate", new Date());
				returnOrderDao.updateReturnOrder(order);
			}
			CollectionUtil.each(items, new IAction<Map<String, Object>>() {
				@Override
				public void excute(Map<String, Object> orderItem) {
					orderItem.put("sku", orderItem.get("sku") == null ? null : orderItem.get("sku").toString().trim());
					String entityId = orderItem.get("entityId") == null ? null : orderItem.get("entityId").toString();
					if (StringUtils.isBlank(entityId)) {
						returnOrderDao.addReturnOrderItem(orderItem);
					} else {
						order.put("updateDate", new Date());
						returnOrderDao.updateReturnOrderItem(orderItem);
					}
				}
			});
			return new OperationResult();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public OperationResult getAddress(Map<String, Object> map) {
		OperationResult result = new OperationResult();
		Map<String, Object> hashMap = returnOrderDao.getAddress(map);
		if (hashMap == null) {
			result.setErrorCode(1);
		} else {
			result.setData(hashMap);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public OperationResult saveEditReturnGoodsOrder2(Map<String, Object> map, String orderType) throws Exception {
		Map<String, Object> paramOrder = (Map<String, Object>) map.get("order");
		List<Map<String, Object>> paramOrderItems = (List<Map<String, Object>>) map.get("items");
		String orderId = (String) paramOrder.get("orderId");
		String cause = (String) paramOrder.get("cause");
		Map<String, Integer> sku2qty = new HashMap<String, Integer>();

		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		CollectionUtil.each(paramOrderItems, new IAction<Map<String, Object>>() {
			public void excute(Map<String, Object> paramOrderItem) {
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("createBy", UserUtils.getUserId());
				item.put("createDate", new Date());
				item.put("updateBy", UserUtils.getUserId());
				item.put("updateDate", new Date());
				String sku = (String) paramOrderItem.get("sku");
				item.put("sku", sku);
				int qty = Integer.parseInt((String) paramOrderItem.get("retqty"));
				item.put("qty", qty);
				item.put("productCaseType", cause);
				item.put("qtyOrdered", paramOrderItem.get("qtyOrdered"));
				item.put("orderItemId", paramOrderItem.get("orderItemId"));
				if (sku2qty.containsKey(sku)) {
					sku2qty.put(sku, sku2qty.get(sku) + qty);
				} else {
					sku2qty.put(sku, qty);
				}
				items.add(item);
			}
		});

		Integer total, used, apply;
		StringBuilder error = new StringBuilder();
		for (Entry<String, Integer> entry : sku2qty.entrySet()) {
			total = returnOrderDao.findOrderSkuQty(orderId, String.valueOf(paramOrder.get("entityId")), entry.getKey()); // 订单sku总数量
			if (total == null) {
				throw new RuntimeException(String.format("当前订单[ { orderId=%s, entityId=%s, sku=%s } ]找不到sku总数量",
						orderId, String.valueOf(paramOrder.get("entityId")), entry.getKey()));
			}
			used = returnOrderDao.findAlreadyReturnedSkuQty(orderId, entry.getKey().toString()); // 已使用的sku数量
			apply = entry.getValue().intValue(); // 当前申请的sku数量
			if (apply > total - (used == null ? 0 : used)) {
				error.append(String.format(" { total=%s, used=%s, apply=%s } ", total, used, apply));
			}
		}

		OperationResult result = new OperationResult();
		if (error.length() > 0) {
			result.setErrorCode(1);
			result.setDescription(error.insert(0, "当前申请的sku数量超出了可用sku数量【").append("】！").toString());
			result.setError("SKU_NUMBER_ERROR");
			return result;
		} else {
			Map<String, Object> order = new HashMap<String, Object>();
			order.put("createBy", UserUtils.getUserId());
			order.put("createDate", new Date());
			order.put("updateBy", UserUtils.getUserId());
			order.put("orderType", 0);
			order.put("updateDate", new Date());
			String account = (String) paramOrder.get("account");
			order.put("platform", paramOrder.get("platform"));
			order.put("account", account);
			order.put("orderId", orderId);
			order.put("platform", paramOrder.get("platform"));
			order.put("edaOrderNum", paramOrder.get("rmaSeq"));
			String site = (String) paramOrder.get("site");
			order.put("site", StringUtils.isBlank(site) ? account : site);
			order.put("cause", cause);
			order.put("trackingNum", paramOrder.get("trackingNum"));
			order.put("isConfirmOrder", 0);
			order.put("isConsumerPaid", 0);
			order.put("approveUser", 0);
			this.returnOrderDao.addReturnOrder(order);
			CollectionUtil.each(items, new IAction<Map<String, Object>>() {
				public void excute(Map<String, Object> item) {
					returnOrderDao.addReturnOrderItem(item);
				}
			});
		}
		return result;
	}

	/**
	 * @deprecated 已过时，请使用同类的<code>saveEditReturnGoodsOrder2</code>函数替代
	 */
	@Override
	public OperationResult saveEditReturnGoodsOrder(Map<String, Object> map, String orderType) {
		@SuppressWarnings("unchecked")
		Map<String, Object> order = (Map<String, Object>) map.get("order");
		OperationResult result = new OperationResult();
		try {
			order.put("updateBy", UserUtils.getUserId());
			order.put("orderType", orderType);
			order.put("status", OrderStatusEnum.processing.getName());
			order.put("createDate", new Date());
			order.put("updateDate", new Date());
			order.put("createBy", UserUtils.getUserId());
			int alreadyApplySkuNum = returnOrderDao.findAlreadyApplyReturnSkuQty(order);
			// 考虑数据安全性，从数据库重新获取原订单的sku数量,进行验证
			int initSkuNum = returnOrderDao.findInitSkuQty(order);
			// 当前申请的sku数量
			int currentSkuNum = order.get("totalQtyOrdered") != null
					? Integer.valueOf(String.valueOf(order.get("totalQtyOrdered"))) : 0;
			int skuResult = initSkuNum - alreadyApplySkuNum - currentSkuNum;

			// 验证申请的退货量是否已超出总订单量
			if (skuResult < 0) {
				order.put("alreadyApplySkuNum", alreadyApplySkuNum);
				order.put("initSkuNum", initSkuNum);
				order.put("currentSkuNum", currentSkuNum);
				result.setData(order);
				result.setError("SKU_NUMBER_ERROR");
				return result;
			} else {
				returnOrderDao.addReturnGoodsOrder(order);
			}

			return result;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Integer findOrderReturnSeq() {
		return returnOrderDao.findOrderReturnSeq();
	}

	@Override
	public Integer findAlreadyApplyReturnSkuNum(Map<String, Object> map) {
		return returnOrderDao.findAlreadyApplyReturnSkuQty(map);
	}

	@Override
	public ResponseResult<ReturnGoodsOrderVo> findAppliedReturnOrders(String entityId) {
		ResponseResult<ReturnGoodsOrderVo> result = new ResponseResult<>();
		List<ReturnGoodsOrderVo> list = returnOrderDao.findAppliedReturnOrders(new Long(entityId));
		result.setRows(list);
		result.setTotal(list.size());
		return result;
	}

}
