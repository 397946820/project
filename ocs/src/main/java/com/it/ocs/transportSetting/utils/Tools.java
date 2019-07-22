package com.it.ocs.transportSetting.utils;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.it.ocs.transportSetting.model.DomisticRuleModel;
import com.it.ocs.transportSetting.model.InternationalRuleModel;
import com.it.ocs.transportSetting.vo.DomisticRuleVo;
import com.it.ocs.transportSetting.vo.InternationalRuleVo;

public class Tools {

	public static String getDomisticRule(String string) {
		JSONObject parse = JSONObject.parseObject(string);
		DomisticRuleVo rule = new DomisticRuleVo();
		if (parse.toString().contains("workday")) {
			if (StringUtils.isNotBlank(parse.getString("workday"))) {
				rule.setWorkday(parse.getString("workday"));
			}
		}
		if (parse.toString().contains("quickSend")) {
			if (StringUtils.isNotBlank(parse.getString("quickSend"))) {
				rule.setWorkday(parse.getString("quickSend"));
			}
		}
		if (!parse.toString().contains("[")) {
			rule.getModels().add((JSON.parseObject(string, DomisticRuleModel.class)));
		} else {
			JSONArray dtype = null;
			if (parse.toString().contains("dtype")) {
				if (StringUtils.isNotBlank(parse.getString("dtype"))) {
					dtype = parse.getJSONArray("dtype");
				}
			}

			JSONArray freight = null;
			if (parse.toString().contains("freight")) {
				if (StringUtils.isNotBlank(parse.getString("freight"))) {
					freight = parse.getJSONArray("freight");
				}
			}

			JSONArray added = null;
			if (parse.toString().contains("added")) {
				if (StringUtils.isNotBlank(parse.getString("added"))) {
					added = parse.getJSONArray("added");
				}
			}

			JSONArray extra = null;
			if (parse.toString().contains("extra")) {
				if (StringUtils.isNotBlank(parse.getString("extra"))) {
					extra = parse.getJSONArray("extra");
				}
			}

			String isFree = null;
			if (parse.toString().contains("isFree")) {
				isFree = parse.getString("isFree");
			}

			if (dtype != null) {
				for (int i = 0; i < dtype.size(); i++) {
					DomisticRuleModel model = new DomisticRuleModel();
					model.setDtype(dtype.getString(i));
					model.setIsFree(getIsFree(isFree, i));
					if (freight != null) {
						model.setFreight(freight.getDouble(i));
					}
					if (added != null) {
						model.setAdded(added.getDouble(i));
					}
					if (extra != null) {
						model.setExtra(extra.getDouble(i));
					}
					rule.getModels().add(model);
				}
			}
		}
		return JSON.toJSONString(rule);
	}

	private static String getIsFree(String isFree, int i) {
		if (isFree != null) {
			if (isFree.contains(i + "")) {
				return "isFree" + (i + 1);
			}
		}
		return null;
	}

	public static String getInternationalRule(String string) {
		JSONObject parse = JSONObject.parseObject(string);
		InternationalRuleVo rule = new InternationalRuleVo();
		if (parse.toString().contains("noArrive")) {
			if (StringUtils.isNotBlank(parse.getString("noArrive"))) {
				rule.setNoArrive(parse.getString("noArrive"));
			}
		}

		if(!parse.toString().substring(0, 25).contains("[")) {
			InternationalRuleModel model = JSON.parseObject(string, InternationalRuleModel.class);
			if (parse.toString().contains("global1")) {
				model.setGlobal(parse.getString("global1"));
			} else if (parse.toString().contains("country1")) {
				Object object = parse.get("country1");
				if(object.toString().contains(",")) {
					JSONArray countrys = parse.getJSONArray("country1");
					model.setCountry(getStringCountry(countrys));
				} else {
					model.setCountry(parse.getString("country1"));
				}
			}
			rule.getModels().add(model);
		} else {
			JSONArray ctype = null;
			if (parse.toString().contains("ctype")) {
				if (StringUtils.isNotBlank(parse.getString("ctype"))) {
					ctype = parse.getJSONArray("ctype");
				}
			}
			
			JSONArray freight = null;
			if (parse.toString().contains("freight")) {
				if (StringUtils.isNotBlank(parse.getString("freight"))) {
					freight = parse.getJSONArray("freight");
				}
			}
			
			JSONArray added = null;
			if (parse.toString().contains("added")) {
				if (StringUtils.isNotBlank(parse.getString("added"))) {
					added = parse.getJSONArray("added");
				}
			}
			
			String isFree = null;
			if (parse.toString().contains("isFree")) {
				isFree = parse.getString("isFree");
			}
			
			if (ctype != null) {
				for (int i = 0; i < ctype.size(); i++) {
					InternationalRuleModel model = new InternationalRuleModel();
					model.setCtype(ctype.getString(i));
					model.setIsFree(getIsFree(isFree, i));
					if (freight != null) {
						model.setFreight(freight.getDouble(i));
					}
					
					if (added != null) {
						model.setAdded(added.getDouble(i));
					}
					model.setGlobal(getGlobal(parse, i));
					model.setCountry(getCountry(parse, i));
					rule.getModels().add(model);
				}
			}
		}
		return JSON.toJSONString(rule);
	}

	private static String getCountry(JSONObject parse, int i) {
		String country = "country" + (i + 1);
		if (parse.toString().contains(country)) {
			Object object = parse.get(country);
			if (object.toString().contains(",")) {
				JSONArray countrys = parse.getJSONArray(country);
				return getStringCountry(countrys);
			} else {
				return parse.getString(country);
			}
		}
		return null;
	}

	private static String getStringCountry(JSONArray countrys) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < countrys.size(); j++) {
			sb.append(countrys.getString(j)).append(",");
		}
		return sb.toString().substring(0, sb.toString().lastIndexOf(","));
	}
	private static String getGlobal(JSONObject parse, int i) {
		String global = "global" + (i + 1);
		if (parse.toString().contains(global)) {
			return parse.getString(global);
		}
		return null;
	}
}
