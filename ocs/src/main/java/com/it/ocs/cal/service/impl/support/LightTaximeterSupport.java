package com.it.ocs.cal.service.impl.support;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.cal.constant.CalTypeEnum;
import com.it.ocs.cal.excel.vo.LECalculateExcelVO;
import com.it.ocs.cal.excel.vo.LECustomerVo;
import com.it.ocs.cal.model.LightEbayCustomerModel;
import com.it.ocs.cal.model.LightEbayRateModel;
import com.it.ocs.cal.model.LightEbaySundryModel;
import com.it.ocs.cal.model.LightHandlingChargesModel;
import com.it.ocs.cal.model.LightTaximeterModel;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.listener.ProjectApplicationContext;
import com.it.ocs.sys.service.impl.PermissionService;
import com.it.ocs.sys.vo.PermissionVO;

/**
 * @author yecaiqing
 */
public class LightTaximeterSupport {

	private final static String CODE = "GWJJQ_SJGLX_PT";
	private final static DecimalFormat SDF = new DecimalFormat("0.0000");
	private final static String[] TYPES = new String[] { "AF", "SF", "CO" };
	private static final String NOTEXIST_MSG = "第{0}行SKU为{1} 数量为{2} 总重量为{3} 没有匹配到物流规则<br/>";
	private static final String PROFITRATE_MSG = "第{0}行运输方式为{1} 利润率不存在<br/>";

	public static void getList(List<LightTaximeterModel> list) {
		CollectionUtil.each(list, new IAction<LightTaximeterModel>() {
			@Override
			public void excute(LightTaximeterModel model) {
				model.setCif(model.getCurrencySymbol() + model.getCif());
			}
		});
	}

	public static void getList2(List<Map<String, Object>> list) {
		CollectionUtil.each(list, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> map) {
				map.put("CIF", map.get("CURRENCYSYMBOL").toString() + map.get("CIF").toString());
			}
		});
	}

	public static PermissionVO getPermission() {
		return ProjectApplicationContext.getBean("permissionService", PermissionService.class)
				.getCurrentUserPermissionByCode(CODE);
	}

	public static List<String> getPlatForms(PermissionVO permission) {
		List<String> result = Lists.newArrayList();
		CollectionUtil.each(permission.getChildren(), new IAction<PermissionVO>() {
			@Override
			public void excute(PermissionVO model) {
				result.add(getPlatform(model.getName()));
			}
		});
		return result;
	}

	public static List<String> getCountrys(PermissionVO permission, String platform) {
		List<String> result = Lists.newArrayList();
		CollectionUtil.each(permission.getChildren(), new IAction<PermissionVO>() {
			@Override
			public void excute(PermissionVO model) {
				if (model.getName().equals(getPlatform(platform))) {
					CollectionUtil.each(model.getChildren(), new IAction<PermissionVO>() {
						@Override
						public void excute(PermissionVO obj) {
							result.add(obj.getName());
						}
					});
				}
			}
		});
		return result;
	}

	public static String getPlatform(String platform) {
		if (platform.equals("ebay")) {
			return "Ebay";
		} else if (platform.equals("light")) {
			return "官网";
		} else if (platform.equals("Ebay")) {
			return "ebay";
		} else if (platform.equals("官网")) {
			return "light";
		}
		return null;
	}

	public static void calExcelFinalPrice(LightTaximeterModel model, LECalculateExcelVO lv) {
		Double profitRate = model.getProfitRate();
		if (!StringUtils.isBlank(lv.getProfitRateAction1())) {
			model.setProfitRate(profitRate * Double.parseDouble(lv.getProfitRateAction1()));
			lv.setFinalPrice1(getFinalPrice(model) + "");
		}
		if (!StringUtils.isBlank(lv.getProfitRateAction2())) {
			model.setProfitRate(profitRate * Double.parseDouble(lv.getProfitRateAction2()));
			lv.setFinalPrice2(getFinalPrice(model) + "");
		}
		if (!StringUtils.isBlank(lv.getProfitRateAction3())) {
			model.setProfitRate(profitRate * Double.parseDouble(lv.getProfitRateAction3()));
			lv.setFinalPrice3(getFinalPrice(model) + "");
		}
	}

	public static void calExcelFinalProfitRate(LightTaximeterModel calculateModel, LECalculateExcelVO lv) {
		if (!StringUtils.isBlank(lv.getFinalPrice1())) {
			calculateModel.setFinalPrice(new Double(lv.getFinalPrice1()));
			lv.setProfitRateAction1(getProfitRateAction(calculateModel) + "");
		}
		if (!StringUtils.isBlank(lv.getFinalPrice2())) {
			calculateModel.setFinalPrice(new Double(lv.getFinalPrice2()));
			lv.setProfitRateAction2(getProfitRateAction(calculateModel) + "");
		}
		if (!StringUtils.isBlank(lv.getFinalPrice3())) {
			calculateModel.setFinalPrice(new Double(lv.getFinalPrice3()));
			lv.setProfitRateAction3(getProfitRateAction(calculateModel) + "");
		}
	}

	public static Double getProfitRateAction(LightTaximeterModel model) {
		return new Double(SDF.format(
				((1 - (model.getFinalCost() * (1 + model.getVat())) / model.getFinalPrice()) / (1 + model.getVat())
						- model.getReferralRate() - model.getPaypalFee() - model.getReplacementRate()
						- model.getUnfulliableRate() - model.getAdvertisingRate()) / model.getProfitRate()));
	}

	public static Double getFinalPrice(LightTaximeterModel model) {
		return new Double(SDF.format((model.getFinalCost() * (1 + model.getVat())
				/ (1 - (model.getReferralRate() + model.getReplacementRate() + model.getProfitRate()
						+ model.getUnfulliableRate() + model.getPaypalFee() + model.getAdvertisingRate())
						* (1 + model.getVat())))));
	}

	public static void handleCal(CalTypeEnum type, int rowIndex, StringBuffer errorMsg, LECalculateExcelVO lv,
			List<LightTaximeterModel> modelList, Map<String, List<String>> platformCountrys,
			Map<String, LightTaximeterModel> maps, List<LightHandlingChargesModel> models,
			LightEbaySundryModel sundryModel) {

		switch (type) {
		case PRICE:
			if (CalculateErrorMsgSupport.canCalFinalPrice(rowIndex, errorMsg, lv, modelList, platformCountrys, maps)) {
				List<LightTaximeterModel> list = LightTaximeterSupport.getList3(lv, maps);
				LightTaximeterModel model = list.get(0);
				model.setFinalCost(getFinalCost(lv, list, models, sundryModel));
				LightTaximeterModel newModel = new LightTaximeterModel();
				BeanUtils.copyProperties(model, newModel);
				calExcelFinalPrice(newModel, lv);
			}
			break;
		case PROFITRATE:
			if (CalculateErrorMsgSupport.canCalProfitRate(rowIndex, errorMsg, lv, modelList, platformCountrys, maps)) {
				List<LightTaximeterModel> list = getList3(lv, maps);
				LightTaximeterModel model = list.get(0);
				model.setFinalCost(LightTaximeterSupport.getFinalCost(lv, list, models, sundryModel));
				calExcelFinalProfitRate(model, lv);
			}
			break;
		default:
			break;
		}
	}

	public static void customerCal(CalTypeEnum type, int rowIndex, StringBuffer errorMsg, LECustomerVo lv,
			List<LightEbayCustomerModel> customers, List<Map<String, Object>> data,
			List<LightEbayRateModel> rateModels) {
		switch (type) {
		case PRICE:
			if (CalculateErrorMsgSupport.customerFinalPrice(rowIndex, errorMsg, lv, customers, data)) {
				changeCustomer(customers, lv, data, type, rateModels, rowIndex, errorMsg);
			}
			break;
		case PROFITRATE:
			if (CalculateErrorMsgSupport.customerProfitRate(rowIndex, errorMsg, lv, customers, data)) {
				changeCustomer(customers, lv, data, type, rateModels, rowIndex, errorMsg);
			}
			break;
		default:
			break;
		}
	}

	private static void changeCustomer(List<LightEbayCustomerModel> customers, LECustomerVo lv,
			List<Map<String, Object>> data, CalTypeEnum type, List<LightEbayRateModel> rateModels, int rowIndex,
			StringBuffer errorMsg) {

		Map<String, Object> map = CollectionUtil.search(data, new IFunction<Map<String, Object>, Boolean>() {
			@Override
			public Boolean excute(Map<String, Object> m) {
				return m.get("COUNTRY").toString().equals(lv.getCountry().toUpperCase())
						&& m.get("SKU").toString().equals(lv.getSku());
			}
		});
		LightEbayCustomerModel model = CollectionUtil.search(customers,
				new IFunction<LightEbayCustomerModel, Boolean>() {
					@Override
					public Boolean excute(LightEbayCustomerModel obj) {
						return lv.getShippingType().toLowerCase().equals(obj.getShippingType());
					}
				});
		Double temp = (new Double(map.get("VOLUME").toString()) * 1000000
				/ model.getVolumeWeightCoefficient() > new Double(map.get("WEIGHT").toString())
						? new Double(map.get("VOLUME").toString()) * 1000000 / model.getVolumeWeightCoefficient()
						: new Double(map.get("WEIGHT").toString()))
				* Math.ceil(new Double(lv.getQty()) / new Double(map.get("PACKINGQTY").toString()));

		LightEbayCustomerModel customer = CollectionUtil.search(customers,
				new IFunction<LightEbayCustomerModel, Boolean>() {
					@Override
					public Boolean excute(LightEbayCustomerModel model) {
						return temp >= model.getFromWeight() && temp < model.getToWeight()
								&& lv.getCountry().toUpperCase().equals(model.getCountry())
								&& lv.getRegion().toUpperCase().equals(model.getRegion())
								&& lv.getShippingType().toLowerCase().equals(model.getShippingType());
					}
				});
		if (customer == null) {
			errorMsg.append(MessageFormat.format(NOTEXIST_MSG, rowIndex, lv.getSku(), lv.getQty(), temp));
			return;
		}
		LightEbayRateModel rateModel = CollectionUtil.search(rateModels, new IFunction<LightEbayRateModel, Boolean>() {
			@Override
			public Boolean excute(LightEbayRateModel model) {
				return model.getShippingType().equals(lv.getShippingType().toLowerCase())
						&& model.getPlatform().equals("light")
						&& model.getCountry().equals(lv.getCountry().toUpperCase());
			}
		});
		if (rateModel == null) {
			errorMsg.append(MessageFormat.format(PROFITRATE_MSG, rowIndex, lv.getShippingType()));
			return;
		}
		// 物流成本
		Double price = (customer.getCurrencyCode().equals("USD") ? customer.getUnitPrice()
				: customer.getUnitPrice() / new Double(map.get("CURRENCYRATE").toString())) * temp;//物流成本为何要乘以temp

		switch (type) {
		case PRICE:
			lv.setFinalPrice(SDF.format((price + new Double(map.get("PRICE").toString()) * new Double(lv.getQty()))
					* new Double(map.get("VAT").toString())
					/ (1 - (rateModel.getGrossProfitRate() * new Double(lv.getProfitRateAction())
							+ new Double(map.get("UNAVAILABILITY").toString())
							+ new Double(map.get("REPLACEMENTRATE").toString()))
							* new Double(map.get("VAT").toString()))));
			break;
		case PROFITRATE:
			lv.setProfitRateAction(
					SDF.format(((1 - (price + new Double(map.get("PRICE").toString()) * new Double(lv.getQty()))
							* new Double(map.get("VAT").toString()) / new Double(lv.getFinalPrice()))
							/ new Double(map.get("VAT").toString()) - new Double(map.get("UNAVAILABILITY").toString())
							- new Double(map.get("REPLACEMENTRATE").toString())) / rateModel.getGrossProfitRate()));

			break;
		default:
			break;
		}
		lv.setPackingQty(map.get("PACKINGQTY").toString());
		lv.setCount(Math.ceil(new Double(lv.getQty()) / new Double(map.get("PACKINGQTY").toString())) + "");
		lv.setUnitPrice(SDF.format(new Double(lv.getFinalPrice()) / new Double(lv.getQty())));
		lv.setPrice(SDF.format(new Double(lv.getUnitPrice()) * new Double(map.get("PACKINGQTY").toString())));
	}

	public static Double getFinalCost(LECalculateExcelVO lv, List<LightTaximeterModel> list,
			List<LightHandlingChargesModel> models, LightEbaySundryModel sundryModel) {
		Double finalCost = null;
		String country = lv.getCountryId().toUpperCase();
		String[] skus = lv.getSku().split(",");
		switch (country) {
		case "US":
			finalCost = getUSFinalCost(list, models);
			break;
		case "UK":
			finalCost = getUKFinalCost(list, sundryModel, skus.length - 1);
			break;
		case "DE":
			finalCost = getDEFinalCost(list, models);
			break;
		default:
			break;
		}
		return finalCost;
	}

	private static Double getUSFinalCost(List<LightTaximeterModel> list, List<LightHandlingChargesModel> models) {
		Double cost = 0d, sendWeight = 0d, warehousingWeight = 0d;
		for (LightTaximeterModel model : list) {
			cost += (new Double(model.getCif()) + model.getStorageCharges() + model.getSundryCharges())
					* model.getQty();
			sendWeight += model.getSendWeight() * model.getQty();
			warehousingWeight += model.getWarehousingWeight() * model.getQty();
		}
		if ((int) (sendWeight / 1000 / 67.358) > 0) {
			cost += getFee(models, 3).getCost() * (int) (sendWeight / 1000 / 67.358)
					+ getRuleFee(sendWeight - 67.358 * ((int) (sendWeight / 1000 / 67.358)) * 1000, models, 3);
		} else {
			cost += getRuleFee(sendWeight, models, 3);
		}

		return cost += getRuleFee(warehousingWeight, models, 1) + getRuleFee(warehousingWeight, models, 2);
	}

	private static Double getUKFinalCost(List<LightTaximeterModel> list, LightEbaySundryModel sundryModel, int length) {
		Double cost = 0d, sendWeight = 0d;
		for (LightTaximeterModel model : list) {
			cost += (new Double(model.getCif()) + model.getStorageCharges() + model.getWec()) * model.getQty();
			sendWeight += model.getSendWeight() * model.getQty();
		}
		cost += sundryModel.getHandlingFee() + sundryModel.getPackingCharge() + sundryModel.getExtraFee() * length;
		if (sendWeight > 2) {
			cost += Math.ceil(sendWeight / 30) * sundryModel.getPfPrice();
		} else {
			cost += sundryModel.getTpsDeliveryFee() * sundryModel.getTpsProportion()
					+ sundryModel.getTpnDeliveryFee() * sundryModel.getTpnProportion();
		}
		return cost;
	}

	private static Double getDEFinalCost(List<LightTaximeterModel> list, List<LightHandlingChargesModel> models) {
		Double cost = 0d, sendWeight = 0d;
		for (LightTaximeterModel model : list) {
			cost += (new Double(model.getCif()) + model.getStorageCharges() + model.getConsumable() + model.getLabour())
					* model.getQty();
			sendWeight += model.getSendWeight() * model.getQty();
		}
		if ((int) (sendWeight / 31.5) > 0) {
			cost += getFee(models, 5).getCost() * (int) (sendWeight / 31.5)
					+ getRuleFee(sendWeight - (int) (sendWeight / 31.5) * 31.5, models, 5);
		} else {
			cost += getRuleFee(sendWeight, models, 5);
		}
		return cost;
	}

	private static Double getRuleFee(Double weight, List<LightHandlingChargesModel> models, Integer type) {
		LightHandlingChargesModel model = getLightHandLingChargesModel(weight, models, type);
		return model == null ? 0d : getCost(model, weight);
	}

	private static Double getCost(LightHandlingChargesModel model, Double weight) {
		if (StringUtils.isBlank(model.getAccountingRules())) {
			return model.getCost();
		}
		Double temp = strToNum(model.getAccountingRules().replace("weight", weight + ""));
		return temp + model.getCost() > model.getUltimateCost() ? model.getUltimateCost() : temp + model.getCost();
	}

	public static Double strToNum(String string) {
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			return Double.parseDouble(engine.eval(string).toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private static LightHandlingChargesModel getLightHandLingChargesModel(Double weight,
			List<LightHandlingChargesModel> models, Integer type) {
		LightHandlingChargesModel model = CollectionUtil.search(models,
				new IFunction<LightHandlingChargesModel, Boolean>() {
					@Override
					public Boolean excute(LightHandlingChargesModel model) {
						return model.getType() == type && weight > model.getFromWeight()
								&& weight <= model.getToWeight();
					}
				});
		return model == null ? getFee(models, type) : model;
	}

	private static LightHandlingChargesModel getFee(List<LightHandlingChargesModel> models, Integer type) {
		List<LightHandlingChargesModel> list = CollectionUtil.searchList(models,
				new IFunction<LightHandlingChargesModel, Boolean>() {
					@Override
					public Boolean excute(LightHandlingChargesModel model) {
						return model.getType() == type;
					}
				});
		try {
			CollectionUtil.sort(list, "sortOrder", "desc");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return list.get(0);
	}

	public static List<LightTaximeterModel> getList3(LECalculateExcelVO lv, Map<String, LightTaximeterModel> maps) {
		List<LightTaximeterModel> list = Lists.newArrayList();
		String country = lv.getCountryId().toUpperCase();
		String[] skus = lv.getSku().split(",");
		for (int i = 0; i < skus.length; i++) {
			LightTaximeterModel model = maps.get(lv.getPlatform().toLowerCase() + lv.getCountryId().toUpperCase()
					+ skus[i] + lv.getShippingType().split(",")[i].toLowerCase() + lv.getTransactionMode()
					+ lv.getIsCostOf() + lv.getIsStorageCharges());
			model.setQty(new Long(lv.getQty().split(",")[i]));
			list.add(model);
		}
		return list;
	}

	public static void getTestMessages(LECalculateExcelVO vo, List<Map<String, Object>> data,
			List<LightHandlingChargesModel> models, LightEbaySundryModel sundryModel, List<String> result) {
		Map<String, Double> feeMap = Maps.newHashMap();
		StringBuffer buffer = getBuffer(vo, data, models, result, sundryModel, feeMap);
		CollectionUtil.each(TYPES, new IAction<String>() {
			@Override
			public void excute(String type) {
				Map<String, Object> map = CollectionUtil.search(data, new IFunction<Map<String, Object>, Boolean>() {
					@Override
					public Boolean excute(Map<String, Object> map) {
						return map.get("SHIPPINGTYPE").toString().toUpperCase().equals(type);
					}
				});
				buffer.append("<br/><br/>" + type + ": ----------<br/>到岸价: " + getCifMessage(map, type) + "最终成本: ");
				StringBuffer temp = new StringBuffer();
				switch (vo.getCountryId()) {
				case "US":
					temp.append("(" + map.get("CIF") + " + " + feeMap.get("sundryCharges") + ") * " + vo.getQty()
							+ " + " + feeMap.get("wec") + " + " + feeMap.get("warehouseOutCharge") + " + "
							+ feeMap.get("localDeliveryFee"));
					break;
				case "DE":
					temp.append("(" + map.get("CIF") + " + " + feeMap.get("consumable") + " + " + feeMap.get("labour")
							+ ") * " + vo.getQty() + " + " + feeMap.get("localDeliveryFee"));
					break;
				case "UK":
					temp.append("(" + map.get("CIF") + " + " + feeMap.get("wec") + ") * " + vo.getQty() + " + "
							+ feeMap.get("orderCost") + " + " + feeMap.get("packingExpense") + " + "
							+ feeMap.get("localDeliveryFee"));
					break;
				default:
					break;
				}
				if (vo.getIsStorageCharges().equals("0")) {
					getFeeMessage(temp.toString(), buffer, feeMap, "finalCost");
				} else {
					temp.append(" + " + feeMap.get("storageCharges") + " * " + vo.getQty());
					getFeeMessage(temp.toString(), buffer, feeMap, "finalCost");
				}
				buffer.append("建议售价: ");
				temp = new StringBuffer().append(feeMap.get("finalCost") + " * " + "(1 + " + map.get("VAT")
						+ ") / (1 - (" + map.get("REFERRALRATE") + " + " + map.get("UNFULLIABLERATE") + " + "
						+ map.get("REPLACEMENTRATE") + " + " + map.get("PROFITRATE") + " + " + map.get("PAYPALFEE")
						+ " + " + map.get("ADVERTISINGRATE") + ") * (1 + " + map.get("VAT") + "))");
				buffer.append(temp + " = " + SDF.format(strToNum(temp.toString())));
			}

		});
		result.add(buffer.toString());
	}

	private static Object getCifMessage(Map<String, Object> map, String type) {
		Object object = null;
		switch (type) {
		case "AF":
			object = "(" + map.get("CPRICE") + " + (" + map.get("CLEARPRICE") + " * " + map.get("DUTYRATE") + " * "
					+ map.get("AFFLUCTUATION") + ") + " + map.get("AF") + ") / " + map.get("CURRENCYRATE") + " * "
					+ map.get("RISKFACTOR") + " = " + map.get("CIF") + "<br/>";
			break;
		case "SF":
			object = "(" + map.get("CPRICE") + " + (" + map.get("CLEARPRICE") + " * " + map.get("DUTYRATE") + " * "
					+ map.get("SFFLUCTUATION") + ") + " + map.get("SF") + ") / " + map.get("CURRENCYRATE") + " * "
					+ map.get("RISKFACTOR") + " = " + map.get("CIF") + "<br/>";
			break;
		case "CO":
			object = "(" + map.get("CPRICE") + " + (" + map.get("CLEARPRICE") + " * " + map.get("DUTYRATE") + " * "
					+ map.get("COFLUCTUATION") + ") + " + map.get("CO") + ") / " + map.get("CURRENCYRATE") + " * "
					+ map.get("RISKFACTOR") + " = " + map.get("CIF") + "<br/>";
			break;
		default:
			break;
		}
		return object;
	}

	private static void getNewBuffer(LightHandlingChargesModel model, StringBuffer newBuffer, String string) {
		newBuffer.append(string + ": ----------<br/>");
		if (!string.equals("仓租费规则")) {
			newBuffer.append("起始重量: " + model.getFromWeight() + "<br/>")
					.append("结束重量: " + model.getToWeight() + "<br/>");
		} else {
			newBuffer.append("起始天数: " + model.getFromWeight() + "<br/>")
					.append("结束天数: " + model.getToWeight() + "<br/>");
		}
		newBuffer.append("费用: " + model.getCost() + "<br/>")
				.append("计费规则: " + (model.getAccountingRules() == null ? "" : model.getAccountingRules()) + "<br/>")
				.append("最高费用: " + (model.getUltimateCost() == null ? "" : model.getUltimateCost()) + "<br/><br/>");
	}

	private static StringBuffer getBuffer(LECalculateExcelVO vo, List<Map<String, Object>> data,
			List<LightHandlingChargesModel> models, List<String> result, LightEbaySundryModel sundryModel,
			Map<String, Double> feeMap) {
		StringBuffer buffer = new StringBuffer();
		Map<String, Object> dataMap = data.get(0);
		buffer.append("平台: " + vo.getPlatform() + "<br/>").append("国家: " + vo.getCountryId() + "<br/>")
				.append("SKU: " + vo.getSku() + "<br/>").append("长: " + dataMap.get("LENGTH") + "<br/>")
				.append("宽: " + dataMap.get("WIDTH") + "<br/>").append("高: " + dataMap.get("HEIGHT") + "<br/>")
				.append("毛重: " + dataMap.get("GROSSWEIGHT") + "<br/>")
				.append("装箱数量: " + dataMap.get("PACKINGQTY") + "<br/>").append("体积: " + dataMap.get("VOLUME") + "<br/>")
				.append("外箱体积: " + dataMap.get("OUTERVOLUME") + "<br/>")
				.append("外箱重量: " + dataMap.get("OUTERWEIGHT") + "<br/>")
				.append("体积重: " + dataMap.get("VOLUMEWEIGHT") + "<br/>")
				.append("实重: " + dataMap.get("NETWEIGHT") + "<br/>")
				.append("派送重量: " + dataMap.get("SENDWEIGHT") + "<br/>")
				.append("累计库存数量: " + dataMap.get("INVENTORYQUANTITY") + "<br/>")
				.append("累计销售量: " + dataMap.get("SALETOTAL") + "<br/>")
				.append("储存天数: " + dataMap.get("STORAGEDAYS") + "<br/>")
				.append("采购成本: " + dataMap.get("PURCHASECOST") + "<br/>");
		StringBuffer newBuffer = new StringBuffer();
		LightHandlingChargesModel model = null;
		String rateMessage = null;
		Double weight = null;
		switch (vo.getCountryId()) {
		case "US":
			// 杂费
			buffer.append("杂费: ");
			rateMessage = getRateMessage(vo, dataMap, dataMap.get("SUNDRYCHARGESTOTAL"));
			getFeeMessage(rateMessage, buffer, feeMap, "sundryCharges");
			// 入库费规则
			buffer.append("入库费: ");
			model = getLightHandLingChargesModel(
					new Double(dataMap.get("WAREHOUSINGWEIGHT").toString()) * new Double(vo.getQty()), models, 1);
			getNewBuffer(model, newBuffer, "入库费规则");
			getFeeBuffer(model, buffer, feeMap, vo.getQty(), dataMap.get("WAREHOUSINGWEIGHT").toString(), "wec");
			// 出库费规则
			buffer.append("出库费: ");
			model = getLightHandLingChargesModel(
					new Double(dataMap.get("WAREHOUSINGWEIGHT").toString()) * new Double(vo.getQty()), models, 2);
			getNewBuffer(model, newBuffer, "出库费规则");
			getFeeBuffer(model, buffer, feeMap, vo.getQty(), dataMap.get("WAREHOUSINGWEIGHT").toString(),
					"warehouseOutCharge");

			// 本地派送规则
			buffer.append("本地派送费: ");
			weight = new Double(dataMap.get("SENDWEIGHT").toString()) * new Double(vo.getQty());
			if ((int) (weight / 1000 / 67.358) > 0) {
				int temp = (int) (weight / 1000 / 67.358);
				rateMessage = getFee(models, 3).getCost() + " * " + temp + " + "
						+ getRuleFee(weight - 67.358 * ((int) (weight / 1000 / 67.358)) * 1000, models, 3);
				getFeeMessage(rateMessage, buffer, feeMap, "localDeliveryFee");
				model = getLightHandLingChargesModel(weight - 67.358 * ((int) (weight / 1000 / 67.358)) * 1000, models,
						3);
			} else {
				model = getLightHandLingChargesModel(weight, models, 3);
				buffer.append(model.getCost() + "<br/>");
				feeMap.put("localDeliveryFee", model.getCost());
			}
			getNewBuffer(model, newBuffer, "本地派送规则");

			// 仓租费规则
			buffer.append("仓租费: ");
			model = getLightHandLingChargesModel(new Double(dataMap.get("STORAGEDAYS").toString()), models, 4);
			getNewBuffer(model, newBuffer, "仓租费规则");
			if (vo.getIsStorageCharges().equals("0")) {
				buffer.append("0<br/>");
				feeMap.put("storageCharges", 0d);
			} else {
				rateMessage = dataMap.get("VOLUME").toString() + " * 35.3";
				Double volume = strToNum(rateMessage) > 1 ? strToNum(rateMessage) : 1;
				if (model.getAccountingRules() == null && new Double(dataMap.get("STORAGEDAYS").toString()) <= 90) {
					buffer.append(model.getCost() + "<br/>");
					feeMap.put("storageCharges", model.getCost());
				} else {
					if (model.getAccountingRules() == null) {
						rateMessage = model.getCost() + " * " + volume;
					} else {
						rateMessage = "(" + model.getCost() + " + "
								+ model.getAccountingRules().replace("day", dataMap.get("STORAGEDAYS").toString()) + ")"
								+ " * " + volume;
					}
					getFeeMessage(rateMessage, buffer, feeMap, "storageCharges");
				}
			}
			break;

		case "DE":
			// 仓租费
			buffer.append("仓租费: ");
			if (vo.getIsStorageCharges().equals("0")) {
				buffer.append("0<br/>");
				feeMap.put("storageCharges", 0d);
			} else {
				rateMessage = getRateMessage(vo, dataMap, dataMap.get("RENTALFEETOTAL"));
				getFeeMessage(rateMessage, buffer, feeMap, "storageCharges");
			}
			// 耗材
			buffer.append("耗材 : ");
			rateMessage = getDeMessage(vo, dataMap, dataMap.get("COSTTOTAL"));
			getFeeMessage(rateMessage, buffer, feeMap, "consumable");
			// 人工
			buffer.append("人工 : ");
			rateMessage = getDeMessage(vo, dataMap, dataMap.get("LABOURCOSTTOTAL"));
			getFeeMessage(rateMessage, buffer, feeMap, "labour");

			// 本地派送规则
			buffer.append("本地派送费 : ");
			weight = new Double(dataMap.get("SENDWEIGHT").toString()) * new Double(vo.getQty());
			if ((int) (weight / 31.5) > 0) {
				rateMessage = getFee(models, 5).getCost() + " * " + (int) (weight / 31.5) + " + "
						+ getRuleFee(weight - 31.5 * ((int) (weight / 31.5)), models, 5);
				getFeeMessage(rateMessage, buffer, feeMap, "localDeliveryFee");
				model = getLightHandLingChargesModel(weight - 31.5 * ((int) (weight / 31.5)), models, 5);
			} else {
				model = getLightHandLingChargesModel(weight, models, 5);
				buffer.append(model.getCost() + "<br/>");
				feeMap.put("localDeliveryFee", model.getCost());
			}
			getNewBuffer(model, newBuffer, "本地派送规则");
			break;
		case "UK":
			// 仓租费
			buffer.append("仓租费: ");
			if (vo.getIsStorageCharges().equals("0")) {
				buffer.append("0<br/>");
				feeMap.put("storageCharges", 0d);
			} else {
				rateMessage = getRateMessage(vo, dataMap, dataMap.get("RENTALFEETOTAL"));
				getFeeMessage(rateMessage, buffer, feeMap, "storageCharges");
			}
			// 入库费
			buffer.append("入库费: ");
			rateMessage = getRateMessage(vo, dataMap, dataMap.get("FEETOTAL"));
			getFeeMessage(rateMessage, buffer, feeMap, "wec");
			// 订单费
			buffer.append("订单费: " + dataMap.get("ORDERCOST") + "<br/>");
			feeMap.put("orderCost", new Double(dataMap.get("ORDERCOST").toString()));
			// 包装费
			buffer.append("包装费: " + dataMap.get("PACKINGEXPENSE") + "<br/>");
			feeMap.put("packingExpense", new Double(dataMap.get("PACKINGEXPENSE").toString()));
			// 本地派送费
			buffer.append("本地派送费: ");
			weight = new Double(dataMap.get("SENDWEIGHT").toString()) * new Double(vo.getQty());
			if (weight < 2) {
				rateMessage = sundryModel.getTpnDeliveryFee() + " * " + sundryModel.getTpnProportion() + " + "
						+ sundryModel.getTpsDeliveryFee() + " * " + sundryModel.getTpsProportion();
				getFeeMessage(rateMessage, buffer, feeMap, "localDeliveryFee");
			} else {
				rateMessage = Math.ceil(weight / 30) + " * " + sundryModel.getPfPrice();
				getFeeMessage(rateMessage, buffer, feeMap, "localDeliveryFee");
			}
			break;
		default:
			break;
		}
		result.add(newBuffer.toString());
		return buffer;
	}

	private static void getFeeMessage(String rateMessage, StringBuffer buffer, Map<String, Double> feeMap,
			String string) {
		if (rateMessage.equals("0")) {
			buffer.append(rateMessage + "<br/>");
			feeMap.put(string, new Double(rateMessage));
		} else {
			String num = SDF.format(strToNum(rateMessage));
			buffer.append(rateMessage + " = " + num + "<br/>");
			feeMap.put(string, new Double(num));
		}

	}

	private static void getFeeBuffer(LightHandlingChargesModel model, StringBuffer buffer, Map<String, Double> feeMap,
			String qty, String weight, String string) {
		if (model.getAccountingRules() == null) {
			buffer.append(model.getCost() + "<br/>");
			feeMap.put(string, model.getCost());
		} else {
			String temp = model.getAccountingRules().replace("weight", (new Double(weight) * new Double(qty)) + "")
					+ " + " + model.getCost();
			String format = SDF
					.format((strToNum(temp) > model.getUltimateCost() ? model.getUltimateCost() : strToNum(temp)));
			buffer.append(temp + " = " + format + "<br/>");
			feeMap.put(string, new Double(format));
		}

	}

	private static String getDeMessage(LECalculateExcelVO vo, Map<String, Object> dataMap, Object object) {
		if (new Double(dataMap.get("SALETOTAL").toString()) == 0) {
			return "0";
		} else {
			String temp = dataMap.get("VOLUMEWEIGHT") + " * " + dataMap.get("SALETOTAL") + " / " + dataMap.get("B")
					+ " * " + object + " * " + dataMap.get("VOLATILITYFACTOR") + " / " + dataMap.get("SALETOTAL");
			if (vo.getIsCostOf().equals("1")) {
				// 使用占用费比
				temp += " * " + dataMap.get("COSTTHAN");
			}
			return temp;
		}
	}

	private static String getRateMessage(LECalculateExcelVO vo, Map<String, Object> dataMap, Object object) {
		if (new Double(dataMap.get("INVENTORYQUANTITY").toString()) == 0) {
			return "0";
		} else {
			String temp = dataMap.get("VOLUMEWEIGHT") + " * " + dataMap.get("STORAGEDAYS") + " * "
					+ dataMap.get("INVENTORYQUANTITY") + " / " + dataMap.get("A") + " * " + object + " * "
					+ dataMap.get("VOLATILITYFACTOR") + " / " + dataMap.get("INVENTORYQUANTITY");
			if (vo.getIsCostOf().equals("1")) {
				// 使用占用费比
				temp += " * " + dataMap.get("COSTTHAN");
			}
			return temp;
		}
	}

	public static List<Map<String, Object>> getFobexw(Map<String, Object> param, Map<String, Double> rates,
			List<Map<String, Object>> rows, Long sumQty, Double sumVolume, Map<String, Map<String, Object>> datas,
			Boolean isRefundDuty) {
		List<Map<String, Object>> result = Lists.newArrayList();
		Map<Integer, List<Map<String, Object>>> tempMap = Maps.newHashMap();
		Double clearPrice = getClearPrice(rates, param, "clearPriceCurrency", "clearPrice");
		Double logisticFee = getClearPrice(rates, param, "logisticFeeCurrency", "logisticFeePrice");
		for (Map<String, Object> map : rows) {
			Map<String, Object> hash = Maps.newHashMap();
			Map<String, Object> data = datas.get(map.get("sku").toString());
			Double clearPrice_ = new Double(map.get("qty").toString()) / sumQty * clearPrice;
			Double logisticFee_ = new Double(map.get("qty").toString()) * new Double(data.get("VOLUME").toString())
					/ sumVolume * logisticFee;
			String currency = data.get("CURRENCY").toString();
			Double finalPrice = clearPrice_ + logisticFee_;
			if (isRefundDuty && !currency.equals("USD")) {
				finalPrice += (new Double(data.get("PRICE").toString()) - new Double(data.get("AMOUNT").toString())
						+ new Double(data.get("FUND").toString())) * new Double(map.get("qty").toString());
			} else {
				finalPrice += new Double(data.get("PRICE").toString()) * new Double(map.get("qty").toString());
			}
			hash.put("sku_", map.get("sku"));
			hash.put("qty_", map.get("qty"));
			hash.put("refundDuty", isRefundDuty ? "是" : "否");
			String currencyCode = param.get("quoteCurrency").toString();
			hash.put("totalClearPrice", SDF.format(getPrice(clearPrice_, currencyCode, rates)));
			hash.put("totalLogisticFee", SDF.format(getPrice(logisticFee_, currencyCode, rates)));
			getResult(map, hash, finalPrice, tempMap, rates, currencyCode, data);
		}
		for (int i = 1; i < 6; i++) {
			List<Map<String, Object>> list = tempMap.get(i);
			if (!CollectionUtil.isNullOrEmpty(list)) {
				result.addAll(list);
			}
		}
		return result;
	}

	private static void getResult(Map<String, Object> map, Map<String, Object> hash, Double finalPrice,
			Map<Integer, List<Map<String, Object>>> tempMap, Map<String, Double> rates, String currency,
			Map<String, Object> data) {
		String flag = "profitRateAction";
		for (int i = 1; i < 6; i++) {
			if (StringUtils.isNotBlank(map.get(flag + i).toString())) {
				Double price = finalPrice / (1
						- (new Double(map.get(flag + i).toString()) * new Double(data.get("GROSSPROFITRATE").toString())
								+ new Double(data.get("UNAVAILABILITY").toString())
								+ new Double(data.get("REPLACEMENTRATE").toString())));
				price = getPrice(price, currency, rates);
				Map<String, Object> hMap = new HashMap<>(hash);
				hMap.put(flag, map.get(flag + i));
				hMap.put("price", SDF.format(price));
				hMap.put("unitPrice", SDF.format(price / new Double(map.get("qty").toString())));
				if (!tempMap.containsKey(i)) {
					tempMap.put(i, new ArrayList<Map<String, Object>>());
				}
				tempMap.get(i).add(hMap);
			}
		}

	}

	private static Double getPrice(Double price, String currencyCode, Map<String, Double> rates) {
		if (currencyCode.equals("CNY")) {
			return price * rates.get(currencyCode);
		} else {
			return price / rates.get(currencyCode);
		}
	}

	private static Double getClearPrice(Map<String, Double> rates, Map<String, Object> map, String currency,
			String price) {
		String currencyCode = map.get(currency).toString();
		if (currencyCode.equals("CNY")) {
			return new Double(map.get(price).toString()) / rates.get(currencyCode);
		} else {
			return new Double(map.get(price).toString()) * rates.get(currencyCode);
		}
	}
}
