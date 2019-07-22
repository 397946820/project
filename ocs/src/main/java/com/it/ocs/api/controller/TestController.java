package com.it.ocs.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.api.service.DeWmsTaskService;
import com.it.ocs.api.utils.SpringPropertyUtil;
import com.it.ocs.synchronou.util.JsonConvertUtil;

/**
* @ClassName: TestController 
* @Description: 测试控制器
* @author wgc 
* @date 2018年4月8日 上午10:43:17 
*
 */
@RequestMapping("/apiTest/")
@Controller
public class TestController {
	
	@Autowired
	private DeWmsTaskService deWmsTaskservice;
	
	@RequestMapping("getPropeties")
	@ResponseBody
	public void getPropeties() {
		this.throwExceptionIfProduce();
		System.out.println("测试获取配置文件" + SpringPropertyUtil.getValue(SpringPropertyUtil.NAME_WMS_URL));
	}
	
	/**
	* @Title: selectOutWmsOrders 
	* @Description: 测试获取出库单
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("selectOutWmsOrders")
	@ResponseBody
	public String selectOutWmsOrders() {
		this.throwExceptionIfProduce();
		deWmsTaskservice.selectOutWmsOrders();
		return "true";
	}
	
	/**
	 * @Title: selectInWmsOrders 
	 * @Description: 测试获取出库单
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	@RequestMapping("selectInWmsOrders")
	@ResponseBody
	public String selectInWmsOrders() {
		this.throwExceptionIfProduce();
		deWmsTaskservice.selectInWmsOrders();
		return "true";
	}
	
	/**
	 * 推送德国仓Ebay出库单到WMS系统
	 */
	@RequestMapping("sendDeEbayOutOrderToWms")
	@ResponseBody
	public String sendDeEbayOutOrderToWms() {
		this.throwExceptionIfProduce();
		deWmsTaskservice.sendDeEbayOutOrderToWms();
		return "true";
	}

	/**
	 * 推送德国仓官网（LE）出库单到WMS系统
	 */
	@RequestMapping("sendDeLightOutOrderToWms")
	@ResponseBody
	public String sendDeLightOutOrderToWms() {
		this.throwExceptionIfProduce();
		deWmsTaskservice.sendDeLightOutOrderToWms();
		return "true";
	}
	
	/**
	 * 
	 * @Title: sendInWmsOrders 
	 * @Description: 向WMS推送德国入库单
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	@RequestMapping("sendInWmsOrders")
	@ResponseBody
	public String sendInWmsOrders() {
		this.throwExceptionIfProduce();
		deWmsTaskservice.sendDeInWmsOrder();
		return "true";
	}
	
	public void throwExceptionIfProduce() {
		if(SpringPropertyUtil.isProduceEnv()) throw new RuntimeException("生产环境禁止发起该请求");
	}
	
	public static void setJsonParam(){
		Map<String,Object> mainMap = new HashMap<String,Object>();
		mainMap.put("orderId", "a012345678");
		mainMap.put("warehouseId", "w012345678");
		mainMap.put("trackingNumber", "a012345678");
		mainMap.put("shipDate", "2018-04-10 10:10:35");
		mainMap.put("shipBy", "张三");
		mainMap.put("storeCode", "a012345678");
		
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		Map<String,Object> detailMap1 = new HashMap<String,Object>();
		detailMap1.put("sku", "apple001");
		detailMap1.put("itemNumber", "21001");
		detailMap1.put("itemQty", "2");
		
		Map<String,Object> detailMap2 = new HashMap<String,Object>();
		detailMap2.put("sku", "apple002");
		detailMap2.put("itemNumber", "21002");
		detailMap2.put("itemQty", "3");
		
		listMap.add(detailMap1);
		listMap.add(detailMap2);
		
		mainMap.put("skuDetail", listMap);
		Map<String,Object> lastMap = new HashMap<String,Object>();
		lastMap.put("token", "iSD3t3GzOmRWqaRr");
		lastMap.put("data", mainMap);
		System.out.println(JsonConvertUtil.getJsonString(lastMap));
	}
	
	public static void setJsonParam2(){
		//入库反馈 货主代码:LE 仓库代码:LE.wh2
		
		Map<String,Object> mainMap = new HashMap<String,Object>();
		mainMap.put("ownerCode", "LE");//固定值
		mainMap.put("storeCode", "LE.wh2");//德国仓固定值
		mainMap.put("storeInOrder", "w000001");//WMS流水号
		mainMap.put("storeInorderId", "c000001");//OMS订单号
		mainMap.put("orderType", "1");//入库类型(1退货入库)
		mainMap.put("source", "官网");//官网,亚马逊
		mainMap.put("createDate", "2018-4-10 10:10:48");//制单日期是商品入库的日期还是OMS创建入库单的日期
		mainMap.put("createBy", "张三");
		mainMap.put("orderId", "d000001");//原始订单号
		mainMap.put("trackingNumber", "Sf000001");
		mainMap.put("newTrackingNumber", "fs00001");
		mainMap.put("remark", "sssss");
		
		
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		Map<String,Object> detailMap1 = new HashMap<String,Object>();
		detailMap1.put("sku", "apple001");
		detailMap1.put("qty", "3");
		detailMap1.put("packageCode", "u001");
		detailMap1.put("unit", "个");
		detailMap1.put("skuProperty", "不良品");//这个有多少种值，用中文表示？
		detailMap1.put("returnReason", "产品不符");//这个有多少种值，用中文表示？
		detailMap1.put("itemNumber", "2567565");
		detailMap1.put("customerName", "小明");
		detailMap1.put("mobile", "13723417601");
		detailMap1.put("address", "广东省深圳市宝安广兴源");
		detailMap1.put("picUrl", "http://baidu.com/pic");
		detailMap1.put("badReason", "中途损坏");
		
		Map<String,Object> detailMap2 = new HashMap<String,Object>();
		detailMap2.put("sku", "apple002");
		detailMap2.put("qty", "3");
		detailMap2.put("packageCode", "u001");
		detailMap2.put("unit", "个");
		detailMap2.put("skuProperty", "不良品");//这个有多少种值，用中文表示？
		detailMap2.put("returnReason", "产品不符");//这个有多少种值，用中文表示？
		detailMap2.put("itemNumber", "2567565");
		detailMap2.put("customerName", "小明");
		detailMap2.put("mobile", "13723417601");
		detailMap2.put("address", "广东省深圳市宝安广兴源");
		detailMap2.put("picUrl", "http://baidu.com/pic");
		detailMap2.put("badReason", "中途损坏");
		
		listMap.add(detailMap1);
		listMap.add(detailMap2);
		
		mainMap.put("skuDetail", listMap);
		Map<String,Object> lastMap = new HashMap<String,Object>();
		lastMap.put("token", "iSD3t3GzOmRWqaRr");
		lastMap.put("data", mainMap);
		System.out.println(JsonConvertUtil.getJsonString(lastMap));
	}
}
