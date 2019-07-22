package com.it.ocs.fourPX.vo;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
/**
 * 子类实体转换JSON
 * @author chenyong
 *
 */
public class Base4PXVO {
	public String toJSONString(){
		return toJSON(this).toString();
	}
	
	protected JSONObject toJSON(Object vo){
		return JSONObject.fromObject(vo);
	}
	
	protected Object jsonStringToBean(String jsonStr,Class clazz){
		return toBean(JSONObject.fromObject(jsonStr),clazz);
	}
	
	protected Object toBean(JSONObject json,Class clazz){
		return JSONObject.toBean(json, clazz);
	}
	
	public static void main(String[] args) {
		DeliveryOrderRequestVO dv = new DeliveryOrderRequestVO();
		dv.setWarehouseCode("HAHAHHAH");
		ConsigneeVO cv = new ConsigneeVO();
		cv.setFullName("chen yong");
		dv.setConsignee(cv);
		List<DeliveryOrderItemVO> items = new ArrayList<>();
		DeliveryOrderItemVO item = new DeliveryOrderItemVO();
		item.setSku("3200001");
		items.add(item);
		dv.setItems(items);
		System.out.println(dv.toJSONString());
	}
}
