package com.it.ocs.api.utils;

import java.util.HashMap;
import java.util.Map;

import com.it.ocs.api.model.DeAbnormalReasonModel;

public class AbnormalReasonUtils {

	public static final String TYPE_OUT = "out";
	
	public static final String TYPE_IN = "in";
	
	static final Map<String, String> parentType2text = new HashMap<String, String>();
	
	static {
		parentType2text.put(TYPE_OUT, "出库单");
		parentType2text.put(TYPE_IN, "入库单");
	}
	
	public static final String ACTION_SOM_CSI = "send-out-manually:check-sku-inventory";
	
	public static final String ACTION_SOT_CSI = "send-out-task:check-sku-inventory";
	
	public static final String ACTION_SOT_OIE = "send-out-task:oms-internal-ex";
	
	public static final String ACTION_SOR_HWR = "send-out-return:http-post-wms-response";
	
	public static final String ACTION_SOD_GWD = "sync-out-disease:get-wms-disease";
	
	public static final String ACTION_SIR_HWR =  "send-in-return:http-post-wms-response";
	
	
	static final Map<String, String> action2text = new HashMap<String, String>();
	
	static {
		action2text.put(ACTION_SOM_CSI, "人工干预手动推送出库单时，调用WMS API接口检查SKU库存出现问题");
		action2text.put(ACTION_SOT_CSI, "定时任务调度推送出库单时，调用WMS API接口检查SKU库存出现问题");
		action2text.put(ACTION_SOR_HWR, "推送出库单接收到返回值时，调用WMS API接口推送出库单后出现问题");
		action2text.put(ACTION_SOT_OIE, "定时任务调度推送出库单时，OMS系统内部错误");
		action2text.put(ACTION_SOD_GWD, "同步出库单异常单或病单时，调用WMS API接口获取所有开放状态的单");
		action2text.put(ACTION_SIR_HWR, "推送入库单接收到返回值时，调用WMS API接口推送入库单后出现问题");
	}
	
	public static String parentTypeText(String parentType) {
		return parentType2text.get(parentType);
	}
	
	public static String actionText(String action) {
		return action2text.get(action);
	}
	
	public static DeAbnormalReasonModel newOut(Long parentId, String action, String reason) {
		return new DeAbnormalReasonModel(null, parentId, TYPE_OUT, action, reason);
	}
	
	public static DeAbnormalReasonModel newIn(Long parentId, String action, String reason) {
		return new DeAbnormalReasonModel(null, parentId, TYPE_IN, action, reason);
	}
}
