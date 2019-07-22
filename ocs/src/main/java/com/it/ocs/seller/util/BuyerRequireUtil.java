package com.it.ocs.seller.util;

import net.sf.json.JSONObject;

public class BuyerRequireUtil {

	public  String getTextByJsonString(Object obj){
		StringBuffer result =  new StringBuffer();
		JSONObject jsonObject = JSONObject.fromObject(obj);
		if(jsonObject.containsKey("allowAllBuyer") && jsonObject.getString("allowAllBuyer").equals("N")){
			result.append("不允许以下买家购买我的物品:<br>");
			if(jsonObject.has("payPal")){
				result.append("没有 PayPal 账户<br>");
			}
			if(jsonObject.has("transportScope")){
				result.append("主要运送地址在我的运送范围之外<br>");
			}
			if(jsonObject.has("case1")){
				result.append("曾收到"+jsonObject.getString("case11")+"个弃标个案，在过去");
				result.append(jsonObject.getString("case12")+"天<br>");
				
			}
			if(jsonObject.has("case2")){
				result.append("曾收到"+jsonObject.getString("case21")+"个违反政策检举，在过去");
				result.append(jsonObject.getString("case22")+"天<br>");
			}
			if(jsonObject.has("case3")){
				result.append("信用指标等于或低于"+jsonObject.getString("case31")+"<br>");
			}
			if(jsonObject.has("case4")){
				result.append("在过去10天内曾出价或购买我的物品，已达到我所设定的限制"+jsonObject.getString("case41")+"<br>");
			}
			if(jsonObject.has("case5")){
				
				result.append("这项限制只适用于买家信用指数等于或低于"+jsonObject.getString("case51")+"<br>");
			}
		}else{
			result.append("允许所有买家购买我的物品:<br>");
		}
		return result.toString();
	}
	
}
