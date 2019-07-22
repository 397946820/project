package com.it.ocs.salesStatistics.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.salesStatistics.dao.NoShipOrderDao;
import com.it.ocs.salesStatistics.model.NoShipSKUModel;
import com.it.ocs.salesStatistics.model.UKNoShipOrderExportModel;

import net.sf.json.JSONObject;

/**
 * 发货规则：英国目前常规主要有4中发货方式，TPS1（48小时隔日达），TPN1（24小时次日达），PF24（24小时次日达），EPB（发往非UK本土的订单）。对应发货方式填写如上面模板所示。
 * 小于3KG的订单选择TPS1或TPN1发货（eBay目前都是TPS1，官网客户以客户实际选择为准）；大于等于3KG的订单都选择PF24发货方式；发非UK本土的订单不管订单大小，都统一发EPB.
 * 注：英国国家缩写英国模板把GB改成UK；重量把KG换算成g。
 * 
 * @author chenyong
 *
 */
@Service("UKNoShipOrderExportService")
public class UKNoShipOrderExportService extends AExcelExport {

	@Autowired
	private NoShipOrderDao noShipOrderDao;

	// 保留两位小数
	private DecimalFormat sdf = new DecimalFormat("0.00");

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		String paramPlatform = request.getParameter("platform");
		List<Map<String, Object>> noShipOrder = null;
		if("light".equals(paramPlatform)) {
			noShipOrder = noShipOrderDao.getLightUKNoShipOrders();
		} else if("amazon".equals(paramPlatform)) {
			noShipOrder = noShipOrderDao.getAmazonUKNoShipOrders();
		} else {
			noShipOrder = noShipOrderDao.getUKNoShipOrderInfo();
		}
		List<Map<String, Object>> returnData = new ArrayList<>();
		for (Map<String, Object> order : noShipOrder) {
			order.put("STOCKDESC", "Lighting EVER LED Bulbs");
			order.put("COOFORIGIN", "CN");
			order.put("CARRIER", "");
			order.put("TRACKINGNO", "");
			// ShipCC GB 换成UK
			String shipCC = (String) order.get("SHIPCC");
			if ("GB".equals(shipCC)) {
				order.put("SHIPCC", "UK");
				shipCC = "UK";
			}
			String pskus = (String) order.get("SELLINGSKU");
			String pqtys = (String) order.get("QTY");
			String platform = (String) order.get("PLATFORM");
			String skus[] = pskus.split(",");
			String qtys[] = pqtys.split(",");
			String itemIds[] = order.get("ITEMID").toString().split(",");
			Double price = ((BigDecimal) order.get("PRICE")).doubleValue();
			Double totalWeight = 0d;
			List<Map<String, Object>> orderData = new ArrayList<>();
			// 根据重量信息推送发货方式
			Object shipService = order.get("SHIPPINGSERVICE");
			String shipMethod = "";// 发货方式
			String courier = "";// 发货方式
			String format = "";// 发货方式

			//处理未发货订单更新新地址
			String orderNo = order.get("ORDERNO").toString();
			if(orderNo.indexOf("W_") > -1){
				String orderId = orderNo.substring(2,orderNo.lastIndexOf("_"));
				String times = orderNo.substring(orderNo.lastIndexOf("_")+1);
				String  account = order.get("PLATFORMACCOUNT").toString();
				Map<String,Object> paramMap = new HashMap<>();
				paramMap.put("orderId", orderId);
				paramMap.put("times", times);
				paramMap.put("account", account);
				String newAddress = null;
				if("light".equals(paramPlatform)) {
					newAddress = noShipOrderDao.getLightWOrderNewAddress(paramMap);
				} else if("amazon".equals(paramPlatform)) {
					newAddress = noShipOrderDao.getAmazonWOrderNewAddress(paramMap);
				} else {
					newAddress = noShipOrderDao.getWOrderNewAddress(paramMap);
				}
				if(null != newAddress && !"".equals(newAddress)){
					JSONObject json = JSONObject.fromObject(newAddress);
					order.put("SHIPTONAME", json.getString("name"));
					order.put("SHIPTOADDR1", json.getString("addressLine1"));
					order.put("SHIPTOADDR2", json.getString("addressLine2"));
					order.put("SHIPTOCOUNTY", json.getString("city"));
					order.put("SHIPPC", json.getString("postalCode"));
					String phone = json.getString("phone");
					if("light".equals(paramPlatform) && StringUtils.isNotBlank(phone)) {
						//说要加"’"的与要去掉"’"的是同一个业务人员。
						//phone = "’" + phone; 
					}
					order.put("SHIPTEL", phone);
					shipCC = json.getString("country");
					order.put("SHIPCC", json.getString("country"));
				}
			}
			
			if ("ebay".equals(platform)) {
				String transactionIds[] = order.get("TRANSACTIONID").toString().split(",");
				for (int i = 0; i < skus.length; i++) {
					String psku = skus[i];
					if (psku.contains("^")) {
						psku = psku.replace("^", ",");
					}
					
					//getEbayUkNoShip(order, psku, transactionIds[i], itemIds[i], qtys[i], totalWeight, shipMethod,courier, format, orderData);
					String itemId = itemIds[i];
					if(itemId.contains("^")){
						itemId = itemId.replace("^", ",");
					}
					order.put("TRANSACTIONID", transactionIds[i]);
					order.put("ITEMID", itemId);
					int pqty = Integer.parseInt(qtys[i]);
					// 获取sku映射关系
					List<NoShipSKUModel> skuInfo = noShipOrderDao.getSkusByPSku(psku);
					if (null == skuInfo || skuInfo.size() == 0) {
						NoShipSKUModel noShipSku = noShipOrderDao.getSkusWeight(psku);
						if (null == noShipSku) {
							Map<String, Object> row = new HashMap<>();
							row.putAll(order);
							row.put("SELLINGSKU", psku);
							row.put("QTY", pqty);
							row.put("STOCKCODE", "");
							row.put("ORDERQTY", "");
							row.put("WEIGHT", "");
							orderData.add(row);
						} else {
							totalWeight = totalWeight + noShipSku.getWeight() * pqty;
							Map<String, Object> row = new HashMap<>();
							row.putAll(order);
							row.put("SELLINGSKU", psku);
							row.put("QTY", pqty);
							row.put("STOCKCODE", psku);
							row.put("ORDERQTY", pqty);
							row.put("WEIGHT", sdf.format(noShipSku.getWeight() * 1000 * pqty));
							orderData.add(row);
						}
					} else {
						for (NoShipSKUModel noShipSku : skuInfo) {
							totalWeight = totalWeight + noShipSku.getWeight()* pqty;
							Map<String, Object> row = new HashMap<>();
							row.putAll(order);
							row.put("SELLINGSKU", psku);
							row.put("QTY", pqty);
							row.put("STOCKCODE", noShipSku.getSku());
							row.put("ORDERQTY", noShipSku.getQty() * pqty);
							row.put("WEIGHT", sdf.format(noShipSku.getWeight() * 1000 * pqty));
							orderData.add(row);
						}
					}
				}
				if("UK".equals(shipCC)){
					if (totalWeight <= 2) {
						shipMethod = "TPS1";
						courier = "RM01";
						format = "INA";
					} else {
						shipMethod = "PF24";
						courier = "PF01";
						format = "IP";
					}
				}else{
					shipMethod = "EPB";
					courier = "PF01";
					format = "IP";
				}
				
				for (Map<String, Object> row : orderData) {
					row.put("SHIPMETHOD", shipMethod);
					row.put("COURIER", courier);
					row.put("FORMAT", format);
				}

			} else if ("light".equals(platform) || "amazon".equals(platform)) {
				order.put("SHIPTOADDR1", StringUtil.cancelNewline((String) order.get("SHIPTOADDR1")));
				order.put("SHIPTOADDR2", StringUtil.cancelNewline((String) order.get("SHIPTOADDR2")));
				order.put("SHIPTOADDR3", StringUtil.cancelNewline((String) order.get("SHIPTOADDR3")));
				for (int i = 0; i < skus.length; i++) {
					String sku = skus[i];
					order.put("ITEMID", itemIds[i]);
					int qty = Integer.parseInt(qtys[i]);
					// 获取sku映射关系
					List<NoShipSKUModel> skuInfo = noShipOrderDao.getSkusByPSku(sku);
					if(CollectionUtils.isEmpty(skuInfo)) {
						NoShipSKUModel noShipSku = noShipOrderDao.getSkusWeight(sku);
						if (null == noShipSku) {
							Map<String, Object> row = new HashMap<>();
							row.putAll(order);
							row.put("SELLINGSKU", sku);
							row.put("QTY", qty);
							row.put("STOCKCODE", "");
							row.put("ORDERQTY", "");
							row.put("WEIGHT", "");
							orderData.add(row);
						} else {
							totalWeight = totalWeight + noShipSku.getWeight() * qty;
							Map<String, Object> row = new HashMap<>();
							row.putAll(order);
							row.put("SELLINGSKU", sku);
							row.put("QTY", qty);
							row.put("STOCKCODE", sku);
							row.put("ORDERQTY", qty);
							row.put("WEIGHT", sdf.format(noShipSku.getWeight() * 1000 * qty));
							orderData.add(row);
						}
					} else {
						for (NoShipSKUModel noShipSku : skuInfo) {
							totalWeight = totalWeight + noShipSku.getWeight()* qty;
							Map<String, Object> row = new HashMap<>();
							row.putAll(order);
							row.put("SELLINGSKU", sku);
							row.put("QTY", qty);
							row.put("STOCKCODE", noShipSku.getSku());
							row.put("ORDERQTY", noShipSku.getQty() * qty);
							row.put("WEIGHT", sdf.format(noShipSku.getWeight() * 1000 * qty));
							orderData.add(row);
						}
					}
				}
				if("UK".equals(shipCC)){
					if (totalWeight <= 2) {
						if (price < 55) {
							if (null == shipService) {
								shipMethod = "TPS1";
								courier = "RM01";
								format = "INA";
							} else {
								String method = String.valueOf(shipService);
								if (method.indexOf("Next Day") != -1) {
									shipMethod = "TPN1";
									courier = "RM01";
									format = "INA";
								} else {
									shipMethod = "TPS1";
									courier = "RM01";
									format = "INA";
								}
							}
						} else {
							shipMethod = "TPN1";
							courier = "RM01";
							format = "INA";
						}

					} else {
						shipMethod = "PF24";
						courier = "PF01";
						format = "IP";
					}
				}else{
					shipMethod = "EPB";
					courier = "PF01";
					format = "IP";
				}
				
				for (Map<String, Object> row : orderData) {
					row.put("SHIPMETHOD", shipMethod);
					row.put("COURIER", courier);
					row.put("FORMAT", format);
				}
			}

			if (!CollectionUtil.isNullOrEmpty(orderData)) {
				returnData.addAll(orderData);
				Map<String, Object> ctn = new HashMap<>();
				ctn.putAll(orderData.get(orderData.size() - 1));
				ctn.put("STOCKCODE", "CTN");
				ctn.put("ORDERQTY", 1);
				ctn.put("WEIGHT", 0);
				returnData.add(ctn);
			}
		}
		return returnData;
	}
	

	@Override
	protected void init(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		super.initModel(UKNoShipOrderExportModel.class, "UK未发货订单信息-" + dateStr.replace(":", "-") + ".xlsx");

	}

}
