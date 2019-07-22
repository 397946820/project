package com.it.ocs.fourPX.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.fourPX.model.FpxStatus;
import com.it.ocs.fourPX.model.OutWarehouseStatus;
import com.it.ocs.fourPX.model.SellerInventoryLogModel;
import com.it.ocs.fourPX.model.Unqualified;
import com.it.ocs.fourPX.utils.ReflectUtils;
import com.mysql.jdbc.StringUtils;

public class FpxServiceUtils {
	
	private static Map<String,String> CODE_TYPE_MAP = Maps.newConcurrentMap();
	
	
	
	public static Map<String, String> getCODE_TYPE_MAP() {
		return CODE_TYPE_MAP;
	}

	static {
//		CODE_TYPE_MAP.put("R", "R - 新建入库预报");
//		CODE_TYPE_MAP.put("T", "T - 取消入库预报");
//		CODE_TYPE_MAP.put("M", "M - 修改入库预报");
//		CODE_TYPE_MAP.put("I", "I - 入库预报上架");
//		CODE_TYPE_MAP.put("D", "D - 订单发布");
//		CODE_TYPE_MAP.put("E", "E - 发布后取消订单");
//		CODE_TYPE_MAP.put("O", "O - 订单出货");
//		CODE_TYPE_MAP.put("S", "S - 库存调整(仓库盘点)");
//		CODE_TYPE_MAP.put("B", "B - 退件直接上架");
//		CODE_TYPE_MAP.put("C", "C - 退件重新上架");
//		CODE_TYPE_MAP.put("A", "A - 货权转移审核");
//		CODE_TYPE_MAP.put("F", "F - 货权转移上架");
//		CODE_TYPE_MAP.put("G", "G - 转仓审核");
//		CODE_TYPE_MAP.put("H", "H - 转仓出货");
//		CODE_TYPE_MAP.put("K", "K - 转仓上架");
		CODE_TYPE_MAP.put("GRS", "GRS - 亚马逊良品转入");
		CODE_TYPE_MAP.put("TRS", "TRS - 转出到亚马逊");
	}
	
	public static final String[] codetypedetails = new String[] { "GRS", "TRS" };
	
	public static boolean validCodetypedetail(String codetypedetail) {
		if(StringUtils.isNullOrEmpty(codetypedetail)) {
			return false;
		}
		
		for (String value : codetypedetails) {
			if(value.equals(codetypedetail)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 4PX获取库存流水接口签名
	 */
	public static final String GET_SELLER_INVENTORY_LOG = "getSellerInventoryLog";
	
	/**
	 * 4PX计算费用接口签名
	 */
	public static final String GET_CALCULATE_FEE = "getCalculateFee";
	
	/**
	 * 4PX查询库存接口签名
	 */
	public static final String GET_INVENTORY = "getInventory";
	
	/**
	 * 4PX建立并审核订单接口签名
	 */
	public static final String CREATE_DELIVERY_ORDER = "createDeliveryOrder";
	
	/**
	 * 4PX查询单票订单接口签名
	 */
	public static final String GET_DELIVERY_ORDER = "getDeliveryOrder";

	/**
	 * 4PX查询批量货品接口签名
	 */
	public static final String GET_ITEM_LIST = "GetItemList";
	
	public static void groupByUnqualified(List<Map<String, Object>> data, IAction<Map<String, Object>> listEachItemAction) {
		if(CollectionUtils.isNotEmpty(data)) {
			List<Unqualified> unqualifieds;
			for (int i = 0, l = data.size(); i < l; i++) {
				Map<String, Object> item = data.get(i);
				if(null != listEachItemAction) {
					listEachItemAction.excute(item);
				}
				
				unqualifieds = buildUnqualifieds((String) item.get(SellerInventoryLogModel.JSON_UNQUALIFIED));
				if(CollectionUtils.isNotEmpty(unqualifieds)) {
					int index = 0;
					for (Unqualified unqualified : unqualifieds) {
						final Map<String, Object> temp = index > 0 ? new HashMap<String, Object>() : item;
						if(index > 0) {
							temp.putAll(item);
							data.add(i + 1, temp);
							i++;
							l++;
						}
						
						ReflectUtils.scanFields(Unqualified.class, new IAction<Field>() {
							@Override
							public void excute(Field field) {
								try {
									temp.put(field.getName().toUpperCase(), field.get(unqualified));
								} catch (IllegalArgumentException | IllegalAccessException e) {
									throw new RuntimeException(e);
								}
							}
						});
						
						index++;
					}
				}
			}
		}
	}
	
	public static String formatCodeType(String codeType) {
		if(StringUtils.isNullOrEmpty(codeType)) {
			return codeType;
		}
		if (CODE_TYPE_MAP.containsKey(codeType.trim())) {
			return CODE_TYPE_MAP.get(codeType.trim());
		}
		return codeType;
	}

	public static List<Unqualified> buildUnqualifieds(String json) {
		if(StringUtils.isNullOrEmpty(json)) {
			return null;
		}

		List<?> list = (List<?>) JSON.parse(json);
		if(CollectionUtil.isNullOrEmpty(list)) {
			return null;
		}
		
		List<Unqualified> unqualifieds = new ArrayList<Unqualified>();
		for (Object o : list) {
			unqualifieds.add(JSON.toJavaObject((JSON) o, Unqualified.class));
		}
		return unqualifieds;
	}
	
	public static final String[] POSTALCODE_USEAST_PREFIX = 
		{ "0", "1", "2", "3", "4", "50", "51", "52", "53", "54", "55", "56", "60", "61", "62", "63", "64", "65", "70", "71", "72" };
	
	
	/**
	 * 判断指定的邮编是否属于美东区
	 * @param postalCode
	 * @return 是否属于美东区：true - 属于；false - 不属于
	 */
	public static boolean isUSEastCode(String postalCode) {
		if(StringUtils.isNullOrEmpty(postalCode)) {
			return false;
		}
		
		for (String prefix : POSTALCODE_USEAST_PREFIX) {
			if(postalCode.startsWith(prefix)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 仓库代码：美东仓
	 */
	public static final String WAREHOUSECODE_USNY = "USNY";

	/**
	 * 仓库代码：美西仓
	 */
	public static final String WAREHOUSECODE_USLA = "USLA";
	
	/**
	 * 根据邮编映射仓库代码
	 * @param postalCode
	 * @return
	 */
	public static String mappingWarehouseCode(String postalCode) {
		return WAREHOUSECODE_USLA;
		//由于4PX美东搬仓，即日起推送改为4PX美西仓优先，一直持续到9/25之后再根据另行通知改为按邮编计算仓库。
		/*
		if(isUSEastCode(postalCode)) {
			return WAREHOUSECODE_USNY;
		} else {
			return WAREHOUSECODE_USLA;
		}
		*/
	}
	
	public static OutWarehouseStatus mappingOmsStatus(FpxStatus fs) {
		//4PX的订单，处于SXDQ这四个状态，我们可以认为是完结状态，不需要再去同步订单状态。
		if(FpxStatus.S == fs || FpxStatus.X == fs || FpxStatus.D == fs || FpxStatus.Q == fs) {
			return OutWarehouseStatus.FEEDBACK;
		}
		
		//异常单
		if(FpxStatus.E == fs) {
			return OutWarehouseStatus.ABNORMAL;
		}
		
		//其他的状态没，OMS有具体对应的状态
		return null;
	}
}
