package com.it.ocs.fourPX.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum FpxStatus {
	/**
	 * 待审核
	 */
	O("待审核"),
	/**
	 * 已审核
	 */
	R("已审核"),
	/**
	 * 待发货
	 */
	P("待发货"),
	/**
	 * 已发货
	 */
	S("已发货"),
	/**
	 * 已取消
	 */
	X("已取消"),
	/**
	 * 已删除
	 */
	D("已删除"),
	/**
	 * 已冻结
	 */
	F("已冻结"),
	/**
	 * 已签收
	 */
	Q("已签收"),
	/**
	 * 异常单
	 */
	E("异常单");
	
	private String text;
	
	private FpxStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * 判断指定的枚举名是否有效
	 * @param name 待判断的枚举名
	 * @return
	 */
	public static boolean validName(String name) {
		if(StringUtils.isBlank(name)) {
			return false;
		}
		
		for (FpxStatus fs : FpxStatus.values()) {
			if(name.equalsIgnoreCase(fs.toString())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Map<String, String> asMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (FpxStatus fs : FpxStatus.values()) {
			map.put(fs.toString(), fs.getText());
		}
		return map;
	}
}
