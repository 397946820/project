package com.it.ocs.fourPX.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 4PX出库单状态
 * @author zhouyancheng
 *
 */
public enum OutWarehouseStatus {
	/**
	 * 待推送
	 */
	PENDING("pending", "待推送"),
	/**
	 * 已推送
	 */
	PUSHED("pushed", "已推送"),
	/**
	 * 已反馈
	 */
	FEEDBACK("feedback", "已反馈"),
	/**
	 * 发生异常
	 */
	ABNORMAL("abnormal", "异常单"),
	/**
	 * 包裹超重
	 */
	OVERWEIGHT("overweight", "超重单"),
	/**
	 * 已取消
	 */
	CANCELLED("cancelled", "已取消");
	
	private String value;
	private String text;

	private OutWarehouseStatus(String value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getValue() {
		return value;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static Map<String, String> asMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (OutWarehouseStatus status : OutWarehouseStatus.values()) {
			map.put(status.getValue(), status.getText());
		}
		return map;
	}
}
