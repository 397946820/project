package com.it.ocs.cal.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.beanutils.ConvertUtils;

import com.it.ocs.cal.dao.IPricePlanDao;
import com.it.ocs.cal.model.FbaRuleModel;
import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.cal.model.ProductEntityModel;
import com.it.ocs.cal.model.ShippingModel;
import com.it.ocs.cal.model.TaxModel;
import com.it.ocs.cal.service.impl.support.LightTaximeterSupport;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.listener.ProjectApplicationContext;
import com.it.ocs.synchronou.util.ValidationUtil;
public class PricePlanUtils {


	private static final String[] TYPES = new String[] { "af", "sf", "co" };

	// 根据国家和运输方式判断此该国家的运顺方式是否计算
	public static boolean getFlag1(String country, String type) {
		boolean flag = false;
		if (country.equals("US") || country.equals("DE") || country.equals("JP") || country.equals("CA")) {
			if (type.equals("co_efn") || type.equals("sf_efn") || type.equals("af_efn") || type.equals("co_peu")
					|| type.equals("sf_peu") || type.equals("af_peu")) {
				flag = true;
			}
		}
		return flag;
	}

	// 根据国家和运输方式判断此该国家的运顺方式是否计算
	public static boolean getFlag2(String type, boolean bool) {
		boolean flag = false;
		if (type.equals("co_peu") || type.equals("sf_peu") || type.equals("af_peu")) {
			if (!bool) {
				flag = true;
			}
		}
		return flag;
	}

	// 根据国家和运输方式判断此该国家的运顺方式是否计算
	public static boolean getFlag3(String country, String type) {
		boolean flag = false;
		if (country.equals("GB") || country.equals("FR") || country.equals("IT") || country.equals("ES")) {
			if (type.equals("co_peu") || type.equals("sf_peu") || type.equals("af_peu") || type.equals("co_efn")
					|| type.equals("sf_efn") || type.equals("af_efn")) {
				flag = true;
			}
		}
		return flag;
	}

	// 根据国家和运输方式判断此该国家的运顺方式是否计算
	public static boolean getFlag4(String country, String type) {
		boolean flag = false;
		if (country.equals("GB") || country.equals("FR") || country.equals("IT") || country.equals("ES")) {
			if (type.equals("co_efn") || type.equals("sf_efn") || type.equals("af_efn")) {
				flag = true;
			}
		}
		return flag;
	}

	public static String getShippingFee(ShippingModel shippingModel, String type) {
		String shippingFee = null;

		if (type.length() > 0) {
			type = type.substring(0, 2);
		}

		if (type.equals("af")) {
			shippingFee = shippingModel.getAfShippingFee();
		} else if (type.equals("sf")) {
			shippingFee = shippingModel.getSfShippingFee();
		} else if (type.equals("co")) {
			shippingFee = shippingModel.getCoShippingFee();
		}
		return shippingFee;
	}

	public static Double getFluctuation(TaxModel taxModel, String type) {

		Double fluctuation = null;

		if (type.length() > 0) {
			type = type.substring(0, 2);
		}

		if (type.equals("af")) {
			fluctuation = taxModel.getAfFluctuation();
		} else if (type.equals("sf")) {
			fluctuation = taxModel.getSfFluctuation();
		} else if (type.equals("co")) {
			fluctuation = taxModel.getCoFluctuation();
		}
		return fluctuation;
	}

	public static ProductEntityModel getSortEntity(ProductEntityModel entity) {
		List<Double> list = new ArrayList<>();
		list.add(entity.getHeight());
		list.add(entity.getWidth());
		list.add(entity.getLength());
		Collections.sort(list);
		entity.setLength(list.get(2));
		entity.setWidth(list.get(1));
		entity.setHeight(list.get(0));
		return entity;
	}

	public static Double getEntity(ProductEntityModel entity) {

		Double volume = (entity.getLength() * 39.37) * (entity.getWidth() * 39.37) * (entity.getHeight() * 39.37);
		Double volume_weight = volume / 166;
		// LB转KG
		volume_weight = volume_weight * 0.4535924; // KG
		return volume_weight >= entity.getGrossWeight() ? volume_weight : entity.getGrossWeight();

	}

	public static Boolean getRule(ProductEntityModel entity, FbaRuleModel fbaRule, Double weight) {
		boolean flag = false;
		if (entity.getLength() <= fbaRule.getLength() && entity.getWidth() <= fbaRule.getWidth()
				&& entity.getHeight() <= fbaRule.getHeight() && weight <= fbaRule.getMaxWeight()
				&& weight > fbaRule.getFromWeight() && weight <= fbaRule.getToWeight()) {
			flag = true;
		}
		return flag;
	}

	public static Double getResult(String price, Double grossWeight, Double qtyOrdered) throws Exception {
		price = price.substring(price.lastIndexOf("=") + 1, price.lastIndexOf(";"));
		price = price.replace("ceil", "Math.ceil");
		if (price.contains("$weight")) {
			price = price.replace("$weight", grossWeight + "");
		}
		if (price.contains("$number")) {
			price = price.replace("$number", qtyOrdered + "");
		}

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByExtension("js");
		Bindings bindings = engine.createBindings();
		bindings.put("expression", price);
		Double value = (Double) engine.eval("eval(expression)", bindings);
		return value;
	}

	public static Boolean getFlag(String flag, Double length, Double width, Double height, Double grossWeight)
			throws ScriptException {
		flag = flag.substring(flag.indexOf("=") + 1, flag.lastIndexOf(";"));
		if (flag.contains("$length")) {
			flag = flag.replace("$length", length + "");
		}
		if (flag.contains("$width")) {
			flag = flag.replace("$width", width + "");
		}
		if (flag.contains("$height")) {
			flag = flag.replace("$height", height + "");
		}
		if (flag.contains("$weight")) {
			flag = flag.replace("$weight", grossWeight + "");
		}
		if (flag.contains("&gt;")) {
			flag = flag.replace("&gt;", ">");
		}
		if (flag.contains("&amp;")) {
			flag = flag.replace("&amp;", "&");
		}
		if (flag.contains("&lt;")) {
			flag = flag.replace("&lt;", "<");
		}

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		engine.put("a", 4);
		Object result = engine.eval(flag);
		return (Boolean) result;
	}

	public static Double getFinalPrice(PricePlanModel planModel) {
		/**
		 *  {call SKU_STATIC_PRICE.REFRESH_LIGHT_STATIC_TAXIMETER(
		        #{sku,mode=IN,jdbcType=VARCHAR}, 
		        #{userId,mode=IN,jdbcType=DECIMAL}
	      )}
		 */
		Map<String,Object> paramMap = BeanConvertUtil.objectToMap(planModel);
		paramMap.put("result", 0.0);
		IPricePlanDao pricePlanDao = (IPricePlanDao) ProjectApplicationContext.getBean("pricePlanDao");
		List<Map<String,Object>> rates = pricePlanDao.getReturnRate(paramMap);
		CollectionUtil.each(rates, new IAction<Map<String,Object>>() {
			@Override
			public void excute(Map<String, Object> map) {
				paramMap.put("returnRate", map.get("RETURNRATE"));
			}
		});
		pricePlanDao.getFinalPrice(paramMap);
		Double finalPrice = Double.parseDouble(paramMap.get("result").toString());
		return finalPrice;
//		String country = planModel.getCountryId();
//		Double finalPrice = null;
//		// 最终价格
//		if (country.equals("US")) {
//
//			finalPrice = (planModel.getFinalCost() / (1 - planModel.getReferralRate() - planModel.getProfitRate()
//					- planModel.getUnfulliableRate() - planModel.getReplacementRate()))
//					* planModel.getReferralRate() <= 1
//							? (planModel.getFinalCost() + 1) / (1 - planModel.getUnfulliableRate()
//									- planModel.getReplacementRate() - planModel.getProfitRate())
//							: planModel.getFinalCost() / (1 - planModel.getReferralRate() - planModel.getProfitRate()
//									- planModel.getUnfulliableRate() - planModel.getReplacementRate());
//
//		} else if (country.equals("GB")) {
//
//			finalPrice = planModel.getFinalCost() * (1 + planModel.getVat())
//					/ (1 - planModel.getReferralRate() - planModel.getUnfulliableRate() - planModel.getReplacementRate()
//							- planModel.getProfitRate())
//					* planModel.getReferralRate() <= 0.4
//							? (planModel.getFinalCost() + 0.4) / (1 - planModel.getUnfulliableRate()
//									- planModel.getReplacementRate() - planModel.getProfitRate())
//									* (1 + planModel.getVat())
//							: planModel.getFinalCost() * (1 + planModel.getVat())
//									/ (1 - (planModel.getReferralRate() + planModel.getUnfulliableRate()
//											+ planModel.getReplacementRate() + planModel.getProfitRate())
//											* (1 + planModel.getVat()));
//
//		} else if (country.equals("DE") || country.equals("FR") || country.equals("ES") || country.equals("IT")) {
//
//			finalPrice = planModel.getFinalCost() * (1 + planModel.getVat())
//					/ (1 - planModel.getReferralRate() - planModel.getUnfulliableRate() - planModel.getReplacementRate()
//							- planModel.getProfitRate())
//					* planModel.getReferralRate() <= 0.5
//							? (planModel.getFinalCost() + 0.5) / (1 - planModel.getUnfulliableRate()
//									- planModel.getReplacementRate() - planModel.getProfitRate())
//									* (1 + planModel.getVat())
//							: planModel.getFinalCost() * (1 + planModel.getVat())
//									/ (1 - (planModel.getReferralRate() + planModel.getUnfulliableRate()
//											+ planModel.getReplacementRate() + planModel.getProfitRate())
//											* (1 + planModel.getVat()));
//
//		} else if (country.equals("JP")) {
//
//			finalPrice = planModel.getFinalCost() / (1 - planModel.getReferralRate() - planModel.getUnfulliableRate()
//					- planModel.getReplacementRate() - planModel.getProfitRate() - planModel.getVat());
//
//		} else if (country.equals("CA")) {
//
//			finalPrice = planModel.getFinalCost() * (1 + planModel.getVat())
//					/ (1 - planModel.getReferralRate() - planModel.getUnfulliableRate() - planModel.getReplacementRate()
//							- planModel.getProfitRate())
//					* planModel.getReferralRate() <= 1
//							? (planModel.getFinalCost() + 1) / (1 - planModel.getUnfulliableRate()
//									- planModel.getReplacementRate() - planModel.getProfitRate())
//									* (1 + planModel.getVat())
//							: planModel.getFinalCost() * (1 + planModel.getVat())
//									/ (1 - (planModel.getReferralRate() + planModel.getUnfulliableRate()
//											+ planModel.getReplacementRate() + planModel.getProfitRate())
//											* (1 + planModel.getVat()));
//		} else if (country.equals("AU")) {
//			finalPrice = planModel.getFinalCost() * (1 + planModel.getVat())
//					/ (1 - (planModel.getReferralRate() + planModel.getUnfulliableRate()
//							+ planModel.getReplacementRate() + planModel.getProfitRate()) * (1 + planModel.getVat()));
//		}
//		return finalPrice;
		
	}

	// 反推利润率
	public static Double getProfitRateAction(PricePlanModel plan) {
		Map<String,Object> paramMap = BeanConvertUtil.objectToMap(plan);
		IPricePlanDao pricePlanDao = (IPricePlanDao) ProjectApplicationContext.getBean("pricePlanDao");
		List<Map<String,Object>> rates = pricePlanDao.getReturnRate(paramMap);
		CollectionUtil.each(rates, new IAction<Map<String,Object>>() {
			@Override
			public void excute(Map<String, Object> map) {
				paramMap.put("returnRate", map.get("RETURNRATE"));
			}
		});
		double returnRate = 0.0;
		if (!ValidationUtil.isNullOrEmpty(paramMap.get("returnRate"))) {
			returnRate = ((BigDecimal)paramMap.get("returnRate")).doubleValue();
		}
		
		Double profitRateAction = 0d;
		switch (plan.getCountryId()) {
		case "US":
			if (plan.getFinalPrice() >= 1 / 0.15) {
				// =F4/(1-G4-J4-I4))
				profitRateAction = 1 - plan.getReferralRate() - plan.getUnfulliableRate() - plan.getReplacementRate() - returnRate
						- plan.getFinalCost() / plan.getFinalPrice();
			} else {
				// =(F4+1)/(1-I4-J4)
				profitRateAction = 1 - plan.getUnfulliableRate() - plan.getReplacementRate() - returnRate
						- (plan.getFinalCost() + 1) / plan.getFinalPrice();
			}
			break;
		case "GB":
			if (plan.getFinalPrice() >= 0.4 / 0.15) {
				// =E4*(1+I4)/(1-(F4+G4+H4)*(1+I4)))
				profitRateAction = (1 - plan.getFinalCost() * (1 + plan.getVat()) / plan.getFinalPrice())
						/ (1 + plan.getVat()) - plan.getReferralRate() - plan.getUnfulliableRate() - returnRate
						- plan.getReplacementRate();
			} else {
				// =(E4+0.4)/(1-G4-H4)*(1+I4)
				// $profitRateAction =
				// 1-plan.getUnfulliableRate()-plan.getReplacementRate()-plan.getProfitRate()-(plan.getFinalCost()+0.4)/plan.getFinalPrice()/(1+plan.getVat());
				profitRateAction = 1 - (plan.getFinalCost() + 0.4) * (1 + plan.getVat()) / plan.getFinalPrice()
						- plan.getUnfulliableRate() - plan.getReplacementRate() - returnRate;
			}
			break;
		case "DE":
		case "FR":
		case "IT":
		case "ES":
			if (plan.getFinalPrice() >= 0.5 / 0.15) {
				// =E4*(1+I4)/(1-(F4+G4+H4)*(1+I4)))
				profitRateAction = (1 - plan.getFinalCost() * (1 + plan.getVat()) / plan.getFinalPrice())
						/ (1 + plan.getVat()) - plan.getReferralRate() - plan.getUnfulliableRate() - returnRate
						- plan.getReplacementRate();
			} else {
				// =(E4+0.5)/(1-G4-H4)*(1+I4)
				profitRateAction = 1 - plan.getUnfulliableRate() - plan.getReplacementRate() - returnRate
						- (plan.getFinalCost() + 0.5) / plan.getFinalPrice() / (1 + plan.getVat());
			}
			break;
		case "JP":
			// =E4/(1-F4-G4-I4-J4)
			profitRateAction = 1 - plan.getUnfulliableRate() - plan.getReplacementRate() - plan.getReferralRate() - returnRate
					- plan.getVat() - plan.getFinalCost() / plan.getFinalPrice();
			break;
		case "CA":
			if (plan.getFinalPrice() >= 1 / 0.15) {
				// =E4*(1+I4)/(1-(F4+G4+H4)*(1+I4)))
				profitRateAction = (1 - plan.getFinalCost() * (1 + plan.getVat()) / plan.getFinalPrice())
						/ (1 + plan.getVat()) - plan.getReferralRate() - plan.getUnfulliableRate()
						- plan.getReplacementRate() - returnRate;
			} else {
				// =(E4+1)/(1-G4-I4)*(1+J4)
				profitRateAction = 1 - plan.getUnfulliableRate() - plan.getReplacementRate() - returnRate
						- (plan.getFinalCost() + 1) / plan.getFinalPrice() / (1 + plan.getVat());
			}
			break;
		case "AU":
			profitRateAction = (1 - plan.getFinalCost() * (1 + plan.getVat()) / plan.getFinalPrice())
					/ (1 + plan.getVat()) - plan.getReferralRate() - plan.getUnfulliableRate() - returnRate
					- plan.getReplacementRate();
			break;
		default:
			break;
		}
		return profitRateAction / plan.getProfitRate();
	}

	public static String getLePricePlanTest(List<Map<String, Object>> data) {
		StringBuffer buffer = new StringBuffer();
		Map<String, Object> dataMap = data.get(0);
		buffer.append("国家: " + dataMap.get("COUNTRYID") + "<br/>").append("SKU: " + dataMap.get("SKU") + "<br/>")
				.append("长: " + dataMap.get("LENGTH") + "<br/>").append("宽: " + dataMap.get("WIDTH") + "<br/>")
				.append("高: " + dataMap.get("HEIGHT") + "<br/>").append("净重: " + dataMap.get("NETWEIGHT") + "<br/>")
				.append("毛重: " + dataMap.get("GROSSWEIGHT") + "<br/>")
				.append("装箱数量: " + dataMap.get("PACKINGQTY") + "<br/>").append("体积: " + dataMap.get("VOLUME") + "<br/>")
				.append("实重: " + dataMap.get("PRODUCTWEIGHT") + "<br/>")
				.append("外箱体积: " + dataMap.get("OUTERVOLUME") + "<br/>")
				.append("外箱重量: " + dataMap.get("OUTERWEIGHT") + "<br/>")
				.append("汇率: " + dataMap.get("CURRENCYRATE") + "<br/>")
				.append("RMB汇率: " + dataMap.get("RMBCURRENCYRATE") + "<br/>")
				.append("退税率: " + dataMap.get("TAX_REBATE_RATE") + "<br/>")
				.append("资金占用率: " + dataMap.get("INTEREST_RATE") + "<br/>")
				.append("平均存储月份: " + dataMap.get("AVERAGE_MONTH") + "<br/>")
				.append("不可用率: " + dataMap.get("UNFULLIABLE_RATE") + "<br/>")
				.append("退货率: " + dataMap.get("RETURNRATE") + "<br/>")
				.append("补发率: " + dataMap.get("REPLACEMENT_RATE") + "<br/>")
				.append("清关关税税率: " + dataMap.get("DUTYRATE") + "<br/>")
				.append("清关系数: " + dataMap.get("CALCULATE_FACTOR") + "<br/>")
				.append("清关价: " + dataMap.get("CLEARPRICE") + "<br/>")
				.append("ERP采购成本: " + dataMap.get("SOURCINGCOST") + "<br/>")
				.append("真实运算成本: $" + dataMap.get("CPRICE") + "<br/>")
				.append("推算FBA费用: " + dataMap.get("FBA") + "<br/>")
				.append("实际FBA费用: " + dataMap.get("AMZFBA") + "<br/>").append("是否Oversize: "
						+ (dataMap.get("ISOVERSIZE").toString().equals("0") ? "否" : "是") + "<br/><br/><br/>");
		CollectionUtil.each(TYPES, new IAction<String>() {
			@Override
			public void excute(String type) {
				Map<String, Object> map = CollectionUtil.search(data, new IFunction<Map<String, Object>, Boolean>() {
					@Override
					public Boolean excute(Map<String, Object> obj) {
						return obj.get("SHIPPINGTYPE").toString().equals(type);
					}
				});
				buffer.append("<br/><br/>" + type + ": ----------<br/>体积重: ");
				switch (type) {
				case "af":
					buffer.append(map.get("AFVOLUMEWEIGHT") + "<br/>仓储费: " + map.get("STORAGEFEE") + " <br/>运费: "
							+ map.get("AFCOST") + " * " + map.get("AFPRODUCTVOLWEIGHT") + " * "
							+ map.get("AFCOSTFLUCTUATION") + " = " + map.get("AF") + "<br/>到岸价: (" + map.get("CPRICE")
							+ " + (" + map.get("CLEARPRICE") + " * " + map.get("DUTYRATE") + " * "
							+ map.get("AFFLUCTUATION") + " ) + " + map.get("AF") + ") / " + map.get("CURRENCYRATE")
							+ " * " + map.get("RISKFACTOR") + " = " + map.get("CIF"));
					break;
				case "sf":
					buffer.append(map.get("VOLUME") + "<br/>仓储费: " + map.get("STORAGEFEE") + " <br/>运费: "
							+ map.get("SFCOST") + " * " + map.get("VOLUME") + " * " + map.get("SFCOSTFLUCTUATION")
							+ " = " + map.get("SF") + "<br/>到岸价: " + map.get("CPRICE") + " + (" + map.get("CLEARPRICE")
							+ " * " + map.get("DUTYRATE") + " * " + map.get("SFFLUCTUATION") + " ) + " + map.get("SF")
							+ ") / " + map.get("CURRENCYRATE") + " * " + map.get("RISKFACTOR") + " = "
							+ map.get("CIF"));
					break;
				case "co":
					buffer.append(map.get("COVOLUMEWEIGHT") + "<br/>仓储费: " + map.get("STORAGEFEE") + " <br/>运费: "
							+ map.get("COCOST") + " * " + map.get("COPRODUCTVOLWEIGHT") + " * "
							+ map.get("COCOSTFLUCTUATION") + " / " + map.get("RMBCURRENCYRATE") + " * "
							+ map.get("RMBRISKFACTOR") + " = " + map.get("CO") + "<br/>到岸价: " + map.get("CPRICE")
							+ " + (" + map.get("CLEARPRICE") + " * " + map.get("DUTYRATE") + " * "
							+ map.get("COFLUCTUATION") + " ) + " + map.get("CO") + ") / " + map.get("CURRENCYRATE")
							+ " * " + map.get("RISKFACTOR") + " = " + map.get("CIF"));
					break;

				default:
					break;
				}

				buffer.append("<br/>最终成本: " + map.get("CIF") + " + "
						+ (strToBoolean(map.get("AMZFBA") + " >  0") ? map.get("AMZFBA") : map.get("FBA")) + " + "
						+ map.get("STORAGEFEE") + " = " + map.get("FINALCOST") + "<br/>建议售价: ");
				buffer.append(getFinalPriceMessage(map));
			}

		});

		return buffer.toString();
	}

	private static Object getFinalPriceMessage(Map<String, Object> map) {
		StringBuffer buffer1 = new StringBuffer();
		StringBuffer buffer2 = new StringBuffer();
		StringBuffer result = new StringBuffer();
		String country = map.get("COUNTRYID").toString();
		switch (country) {
		case "US":
			buffer1.append("(" + map.get("FINALCOST") + "/(1-" + map.get("REFERRALRATE") + "-" + map.get("PROFITRATE")
					+ "-" + map.get("UNFULLIABLERATE") +"-" + map.get("RETURNRATE") + "-" + map.get("REPLACEMENTRATE") + "))*"
					+ map.get("REFERRALRATE"));
			buffer2.append((LightTaximeterSupport.strToNum(buffer1.toString()) <= 1
					? "(" + map.get("FINALCOST") + "1)/(1-" + map.get("UNFULLIABLERATE") +"-" + map.get("RETURNRATE") + "-"
							+ map.get("REPLACEMENTRATE") + "-" + map.get("PROFITRATE") + ")"
					: map.get("FINALCOST") + "/(1-" + map.get("REFERRALRATE") + "-" + map.get("PROFITRATE") + "-"
							+ map.get("UNFULLIABLERATE") +"-" + map.get("RETURNRATE") + "-" + map.get("REPLACEMENTRATE") + ")"));
			break;
		case "GB":
		case "DE":
		case "FR":
		case "IT":
		case "ES":
		case "CA":
			buffer1.append(map.get("FINALCOST") + "*" + "(1+" + map.get("VAT") + ")/(1-" + map.get("REFERRALRATE") + "-"
					+ map.get("UNFULLIABLERATE") +"-" + map.get("RETURNRATE") + "-" + map.get("REPLACEMENTRATE") + "-" + map.get("PROFITRATE") + ")*"
					+ map.get("REFERRALRATE"));
			StringBuffer temp1 = new StringBuffer();
			StringBuffer temp2 = new StringBuffer();
			temp1.append("/(1-" + map.get("UNFULLIABLERATE") +"-" + map.get("RETURNRATE") + "-" + map.get("REPLACEMENTRATE") + "-"
					+ map.get("PROFITRATE") + ")*(1+" + map.get("VAT") + ")");
			temp2.append(map.get("FINALCOST") + "*" + "(1+" + map.get("VAT") + ")/(1-(" + map.get("REFERRALRATE") + "+"
					+ map.get("UNFULLIABLERATE") +"+" + map.get("RETURNRATE") + "+" + map.get("REPLACEMENTRATE") + "+" + map.get("PROFITRATE") + ")*"
					+ "(1+" + map.get("VAT") + "))");
			buffer2.append((country.equals("GB")
					? (LightTaximeterSupport.strToNum(buffer1.toString()) <= 0.4
							? "(" + map.get("FINALCOST") + "0.4)" + temp1 : temp2)
					: country.equals("CA") ? (LightTaximeterSupport.strToNum(buffer1.toString()) <= 1
							? "(" + map.get("FINALCOST") + "1)" + temp1 : temp2)
							: (LightTaximeterSupport.strToNum(buffer1.toString()) <= 0.5
									? "(" + map.get("FINALCOST") + "0.5)" + temp1 : temp2)));
			break;
		case "JP":
			buffer2.append(map.get("FINALCOST") + "/(1-" + map.get("REFERRALRATE") + "-" + map.get("UNFULLIABLERATE")
					+ "-" + map.get("RETURNRATE") +"-" + map.get("REPLACEMENTRATE") + "-" + map.get("PROFITRATE") + "-" + map.get("VAT") + ")");
			break;
		case "AU":
			buffer2.append(map.get("FINALCOST") + "*" + "(1+" + map.get("VAT") + ")/(1-(" + map.get("REFERRALRATE")
					+ "+" + map.get("UNFULLIABLERATE") + "+" + map.get("RETURNRATE") +"+" + map.get("REPLACEMENTRATE") + "+" + map.get("PROFITRATE")
					+ ")*" + "(1+" + map.get("VAT") + "))");
			break;
		default:
			break;
		}
		return result.append(buffer2 + " = " + LightTaximeterSupport.strToNum(buffer2.toString()));
	}

	private static Boolean strToBoolean(String string) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			return (Boolean) engine.eval(string);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
