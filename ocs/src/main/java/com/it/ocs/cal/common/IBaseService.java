package com.it.ocs.cal.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cal.common.model.ShippingFeeModel;
import com.it.ocs.cal.common.model.StorageCostModel;
import com.it.ocs.cal.dao.ICurrencyRateDao;
import com.it.ocs.cal.dao.IFbaRuleDao;
import com.it.ocs.cal.dao.IPricePlanDao;
import com.it.ocs.cal.dao.IProductCostDao;
import com.it.ocs.cal.dao.IProductEntityDao;
import com.it.ocs.cal.dao.IProductOtherDao;
import com.it.ocs.cal.dao.IShippingDao;
import com.it.ocs.cal.dao.ITaxDao;
import com.it.ocs.cal.model.CurrencyRateModel;
import com.it.ocs.cal.model.FbaRuleModel;
import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.cal.model.ProductCostModel;
import com.it.ocs.cal.model.ProductEntityModel;
import com.it.ocs.cal.model.ProductOtherModel;
import com.it.ocs.cal.model.ShippingModel;
import com.it.ocs.cal.model.TaxModel;
import com.it.ocs.cal.utils.PricePlanUtils;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.common.util.CollectionUtil;

public class IBaseService {

	@Autowired
	protected IPricePlanDao pricePlanDao;
	@Autowired
	protected IFbaRuleDao fbaRuleDao;
	@Autowired
	protected ICurrencyRateDao currencyRateDao;
	@Autowired
	protected ITaxDao taxDao;
	@Autowired
	protected IShippingDao shippingDao;
	@Autowired
	protected IProductOtherDao productOtherDao;
	@Autowired
	protected IProductCostDao productCostDao;
	@Autowired
	protected IProductEntityDao productEntityDao;

	protected static List<String> listShippingType = new ArrayList<>();

	static {
		listShippingType.add("co");
		listShippingType.add("sf");
		listShippingType.add("af");
		listShippingType.add("co_efn");
		listShippingType.add("sf_efn");
		listShippingType.add("af_efn");
		listShippingType.add("co_peu");
		listShippingType.add("sf_peu");
		listShippingType.add("af_peu");
	}

	protected static List<String> listCountry = new ArrayList<>();

	static {
		listCountry.add("DE");
		listCountry.add("US");
		listCountry.add("GB");
		listCountry.add("FR");
		listCountry.add("IT");
		listCountry.add("ES");
		listCountry.add("JP");
		listCountry.add("CA");
	}
	protected DecimalFormat df = new DecimalFormat("0.0000");

	protected <T> void batchAdd(List<T> list, IBaseDao<T> baseDao) {
		if (!CollectionUtil.isNullOrEmpty(list)) {
			int batchCount = 50;
			int sourListSize = list.size();
			int subCount = sourListSize % batchCount == 0 ? sourListSize / batchCount : sourListSize / batchCount + 1;
			int startIndext = 0;
			int stopIndext = 0;
			for (int i = 0; i < subCount; i++) {
				stopIndext = (i == subCount - 1) ? sourListSize : stopIndext + batchCount;
				List<T> subList = list.subList(startIndext, stopIndext);
				baseDao.addAll(subList);
				startIndext = stopIndext;
			}
		}
	}

	// 梳理数据
	protected Map<String, Map<String, Object>> tease(Long userId) {
		Map<String, Map<String, Object>> map = new HashedMap();

		// 税
		map.put("taxs", getTaxs());

		// 汇率
		Map<String, Object> rates = new HashedMap();
		List<CurrencyRateModel> currencyRates = currencyRateDao.findByTemplate();
		for (CurrencyRateModel model : currencyRates) {
			rates.put(model.getCountryId(), model);
		}
		map.put("rates", rates);

		// 运费
		Map<String, Object> shippings = new HashedMap();
		List<ShippingModel> shippingModels = shippingDao.findByTemplate();
		for (ShippingModel model : shippingModels) {
			shippings.put(model.getCountryId(), model);
		}
		map.put("shippings", shippings);

		// FBA规则
		Map<String, Object> rules = new HashedMap();
		List<FbaRuleModel> temp = new ArrayList<>();
		for (String country : listCountry) {
			// 根据国家查出相对应的规则
			temp = fbaRuleDao.findRulesByCountry(country);
			rules.put(country, temp);
		}
		map.put("rules", rules);

		// 产品其他
		Map<String, Object> others = new HashedMap();
		List<ProductOtherModel> productOtherModels = productOtherDao.findByUserId(userId);
		for (ProductOtherModel model : productOtherModels) {
			others.put(model.getCountryId() + model.getParentId(), model);
		}
		map.put("others", others);

		// 产品成本
		Map<String, Object> costs = new HashedMap();
		List<ProductCostModel> productCostModels = productCostDao.findByUserId(userId);
		for (ProductCostModel model : productCostModels) {
			costs.put(model.getParentId() + "", model);
		}
		map.put("costs", costs);

		// 产品
		Map<String, Object> entitys = new HashedMap();
		Map<String, Object> entitySkus = new HashedMap();
		List<ProductEntityModel> productEntityModels = productEntityDao.findAllByUser(userId);
		for (ProductEntityModel model : productEntityModels) {
			entitySkus.put(model.getSku(), model);
			entitys.put(model.getSku() + "_" + model.getUserId(), model);
		}
		map.put("entitySkus", entitySkus);
		map.put("entitys", entitys);

		return map;
	}

	public Map<String, Object> getTaxs() {
		Map<String, Object> hashMap = new HashedMap();
		List<TaxModel> taxModels = taxDao.findByTemplate();
		for (TaxModel model : taxModels) {
			hashMap.put(model.getCountryId(), model);
		}
		return hashMap;
	}

	protected PricePlanModel calculatePlanPrice(PricePlanModel planModel, String sku, String country, String type,
			Double price, Long userId, Map<String, Map<String, Object>> map) throws Exception {

		if (planModel == null) {
			planModel = new PricePlanModel();
		}

		// 获取美元转人民币的汇率
		Double parities = CurrencyRateCache.getCurrencyRate("RMB");

		// 汇率
		CurrencyRateModel rateModel = (CurrencyRateModel) map.get("rates").get(country);
		// 运费
		ShippingModel shippingModel = (ShippingModel) map.get("shippings").get(country);
		// 税
		TaxModel taxModel = (TaxModel) map.get("taxs").get(country);

		// 产品基本
		ProductEntityModel entityModel = null;
		if (userId == null) {
			entityModel = (ProductEntityModel) map.get("entitySkus").get(sku);
		} else {
			entityModel = (ProductEntityModel) map.get("entitys").get(sku);
		}

		if (entityModel != null) {
			// 获取产品的id
			Long entityId = entityModel.getEntityId();
			// 产品成本
			ProductCostModel costModel = (ProductCostModel) map.get("costs").get(entityId + "");
			// 产品其他
			ProductOtherModel otherModel = (ProductOtherModel) map.get("others").get(country + entityId);

			// FBA规则
			List<FbaRuleModel> fbaRules = (List<FbaRuleModel>) map.get("rules").get(country);

			if (rateModel != null && shippingModel != null && taxModel != null && costModel != null
					&& otherModel != null && !CollectionUtil.isNullOrEmpty(fbaRules)) {

				Double fbaFee = 0d;
				// 通过运输方式获取到具体的运输方式的运费
				String shippingFee = PricePlanUtils.getShippingFee(shippingModel, type);
				// 通过运输方式获取到具体运输方式的税
				Double fluctuation = PricePlanUtils.getFluctuation(taxModel, type);
				// 运费成本
				Double freight = getFreight(shippingFee, rateModel, entityModel, type, parities);
				// 采购成本
				Double sourcingCost = getSourcingCost(costModel, parities, price);
				// 清关价
				Double clearPrice = getClearPrice(taxModel, otherModel, sourcingCost);
				// 到岸价(默认是USD)
				Double CIFUSD = sourcingCost + (clearPrice * otherModel.getDutyRate() * fluctuation) + freight;
				// 到当前国家的到岸价
				Double CIF = null;
				if (PricePlanUtils.getFlag3(country, type) && userId != null) {
					if (planModel.getCif() != null) {
						CIF = new Double(planModel.getCif().replaceAll("€", "").replaceAll("£", ""));
					} else {
						if (!country.equals("US")) {
							CIF = CIFUSD / rateModel.getCurrencyRate() * rateModel.getRiskFactor();
						} else {
							CIF = CIFUSD;
						}
					}
				} else {
					if (!country.equals("US")) {
						CIF = CIFUSD / rateModel.getCurrencyRate() * rateModel.getRiskFactor();
					} else {
						CIF = CIFUSD;
					}
				}

				// 是否Oversize以及fbaFee、amzFee
				planModel = getPlanModel(fbaRules, country, otherModel, planModel, entityModel, type);
				fbaFee = planModel.getFbaFee();
				// 仓储成本费
				StorageCostModel storageCostModel = getCost(shippingModel.getStorageCost());
				// 仓储费
				Double storageFee = null;
				if (planModel.getStorageFee() == null) {
					boolean isOversize = false;
					if (planModel.getIsOversize().equals("1")) {
						isOversize = true;
					}
					storageFee = new Double(df.format(
							getStorageFee(country, isOversize, storageCostModel, otherModel, rateModel, entityModel)));

				}

				// 数据设置到planModel中
				planModel.setCountryId(country);
				planModel.setShippingType(type);

				planModel.setCif(df.format(CIF));
				planModel.setCurrencySymbol(rateModel.getCurrencySymbol());// 到本国的货币符号
				planModel.setCifusd("$" + df.format(CIFUSD));// 美元的到岸价
				planModel.setCifrmb("￥" + df.format(CIFUSD * parities));// 人民币的到岸价
				if (price == null) {
					planModel.setSourcingCost(
							Tools.getCurrencySymbol(costModel.getCurrency()) + df.format(costModel.getPrice()));
				}
				if (storageFee != null) {
					planModel.setStorageFee(new Double(df.format(storageFee)));
				}
				double fba = 0d;
				if (PricePlanUtils.getFlag4(country, type)) {
					fba = otherModel.getEfnFee() > 0 ? otherModel.getEfnFee() : fbaFee;
					planModel.setAmzFba(otherModel.getEfnFee());

				} else {
					fba = otherModel.getAmzFba() > 0 ? otherModel.getAmzFba() : fbaFee;
					planModel.setAmzFba(otherModel.getAmzFba());
				}
				planModel.setFbaFee(fbaFee);
				planModel.setFinalCost(new Double(df.format(CIF + planModel.getStorageFee() + fba)));
				// 推荐费
				planModel.setReferralRate(shippingModel.getOperatingFee());
				// 损坏率
				planModel.setUnfulliableRate(otherModel.getUnfulliableRate());
				// 补发率
				planModel.setReplacementRate(otherModel.getReplacementRate());
				// 利润率
				planModel.setProfitRate(JSON.parseArray(shippingFee, ShippingFeeModel.class).get(0).getProfit_rate());
				// 可用利润率
				planModel.setProfitRateAction(1d);
				// 税率
				planModel.setVat(taxModel.getVat());
				// 最终价格
				planModel.setFinalPrice(new Double(df.format(PricePlanUtils.getFinalPrice(planModel))));
				if (userId != null) {
					planModel.setUserId(userId);
				}
				planModel.setCreatedAt(new Date());
				planModel.setUpdatedAt(new Date());
				return planModel;
			}
		}
		return null;

	}

	// 仓储费
	private Double getStorageFee(String country, boolean isOversize, StorageCostModel storageCostModel,
			ProductOtherModel otherModel, CurrencyRateModel rateModel, ProductEntityModel entityModel) {
		// 体积
		Double tiji = entityModel.getLength() * entityModel.getWidth() * entityModel.getHeight()
				+ entityModel.getOuterVolume() / entityModel.getPackingQty();

		Double storageFee = 0d;

		switch (country) {
		case "US":
			// = (体积 * 单位换算) * 平均存储月数 * 收费标准
			Double size = isOversize ? storageCostModel.getOver_size() : storageCostModel.getStandard_size();
			storageFee = new Double(tiji * storageCostModel.getParam_one() * otherModel.getAverageMonth() * size);
			break;

		case "GB":
		case "DE":
		case "FR":
		case "ES":
		case "IT":
			// = 体积 * 平均存储月数 * (货币汇率 * 当地货币收费标准)
			storageFee = new Double(tiji * otherModel.getAverageMonth() * storageCostModel.getStandard_size());
			break;

		case "JP":
		case "CA":
			// = 体积 * 平均存储月数 * (货币汇率 * 当地货币收费标准 * 当地税)
			storageFee = new Double(tiji * otherModel.getAverageMonth() * storageCostModel.getStandard_size()
					* storageCostModel.getParam_one());
			break;
		default:
			break;
		}
		return storageFee;
	}

	// 仓储成本费
	public static StorageCostModel getCost(String storageCost) {
		List<StorageCostModel> costs = JSON.parseArray(storageCost, StorageCostModel.class);
		// 获取当前月份
		Calendar calendar = Calendar.getInstance();
		String nowMonth = calendar.get(Calendar.MONTH) + 1 + "";

		for (StorageCostModel cost : costs) {
			String month = cost.getMonth();
			String[] split = month.split(",");
			boolean b = Arrays.asList(split).contains(nowMonth);
			if (b) {
				return cost;
			}
		}

		return null;
	}

	private PricePlanModel getPlanModel(List<FbaRuleModel> fbaRules, String country, ProductOtherModel otherModel,
			PricePlanModel planModel, ProductEntityModel entityModel, String type) throws Exception {

		Calendar calendar = Calendar.getInstance();
		String nowMonth = calendar.get(Calendar.MONTH) + 1 + "";
		String title = "";
		Boolean b = false;
		Double fbaFee = 0d, extraFee = 0d;
		FbaRuleModel model = null;
		// 长宽高排序
		entityModel = PricePlanUtils.getSortEntity(entityModel);
		for (FbaRuleModel fbaRule : fbaRules) {
			if (Arrays.asList(fbaRule.getIsMonth().split(",")).contains(nowMonth)) {
				Double weight = 0d;
				if (country.equals("US")) {
					weight = PricePlanUtils.getEntity(entityModel);
				} else {
					weight = entityModel.getGrossWeight();
				}
				if (PricePlanUtils.getRule(entityModel, fbaRule, weight)) {
					title = fbaRule.getTitle();
					extraFee = fbaRule.getExtraFee();
					String flag = fbaRule.getFlag();
					String price = fbaRule.getPrice();// $price=3.96+0.39*ceil($weight/0.45359+0.25-2);
					if (price.startsWith("$price")) {
						price = PricePlanUtils.getResult(price, weight, otherModel.getQtyOrdered()) + "";
					}
					if (!flag.startsWith("$flag")) {// $flag=$length>0.45||$width>0.35||$height>0.2;
						if (!flag.equals("0")) {
							fbaFee = new Double(price);
							if (country.equals("JP")) {
								fbaFee += fbaRule.getPickpackFee();
							}
							model = fbaRule;
							break;
						}
					} else if (flag.startsWith("$flag")) {
						Boolean bool = PricePlanUtils.getFlag(flag, entityModel.getLength(), entityModel.getWidth(),
								entityModel.getHeight(), entityModel.getGrossWeight());
						if (bool) {
							fbaFee = new Double(price);
							if (country.equals("JP")) {
								fbaFee += fbaRule.getPickpackFee();
							}
							model = fbaRule;
							break;
						}
					}
				}

			}
		}

		if (PricePlanUtils.getFlag4(country, type)) {
			if (model != null) {
				if (!country.equals("GB")) {
					fbaFee = model.getEfnFee();
				}
			}
		}

		if (fbaFee == 0) {
			fbaFee = otherModel.getEfnFee();
		}

		fbaFee = fbaFee / otherModel.getQtyOrdered();

		if (extraFee > 0) {
			fbaFee *= extraFee;
		}

		if (title.contains("Oversize")) {
			b = true;
		}
		if (b) {
			planModel.setIsOversize("1");
		} else {
			planModel.setIsOversize("0");
		}
		planModel.setFbaFee(fbaFee);
		return planModel;

	}

	// 清关价
	private Double getClearPrice(TaxModel taxModel, ProductOtherModel otherModel, Double sourcingCost) {
		Double clearPrice = null;
		// 关税
		Double dutyRate = otherModel.getDutyRate();
		if (dutyRate == 0) {
			dutyRate = 1d;
		}
		Double clear = otherModel.getClearPrice();
		if (clear == 0) {
			clearPrice = sourcingCost * taxModel.getClearCoefficient();
		} else {
			clearPrice = clear;
		}
		return clearPrice;
	}

	// 计算采购成本
	private Double getSourcingCost(ProductCostModel costModel, Double parities, Double price) {
		Double sourcingCost = null;
		if (price == null) {
			// 获取货币
			String currency = costModel.getCurrency();
			if (currency.equals("RMB") || currency.equals("CNY")) {
				Double taxRebateRate = costModel.getTaxRebateRate() > 0 ? costModel.getTaxRebateRate() : 1;
				sourcingCost = (costModel.getPrice()
						- (costModel.getPrice() / 1.17 * taxRebateRate) * (1 - costModel.getInterestRate())) / parities;
			} else if (currency.equals("USD")) {
				sourcingCost = costModel.getPrice();
			}
		} else {
			Double taxRebateRate = costModel.getTaxRebateRate() > 0 ? costModel.getTaxRebateRate() : 1;
			sourcingCost = (price - (price / 1.17 * taxRebateRate) * (1 - costModel.getInterestRate())) / parities;
		}
		return sourcingCost;
	}

	// 计算运费
	private Double getFreight(String shippingFee, CurrencyRateModel rateModel, ProductEntityModel entityModel,
			String type, Double parities) {

		// json格式的转化成对象
		ShippingFeeModel model = JSON.parseArray(shippingFee, ShippingFeeModel.class).get(0);
		// 运费单价
		Double cost = model.getCost();
		// 浮动因子
		Double fluctuation = model.getCost_fluctuation();
		// 体积
		Double volume = entityModel.getLength() * entityModel.getWidth() * entityModel.getHeight()
				+ entityModel.getOuterVolume() / entityModel.getPackingQty();
		// 产品体积重量
		Double volumeWeight = entityModel.getGrossWeight() + entityModel.getOuterWeight() / entityModel.getPackingQty();
		// 运费
		Double freight = null;
		switch (type) {
		case "af":
		case "af_efn":
		case "af_peu":
			// 体积重量
			Double volumeWeightAF = volume * 1000000 / 6000; // (af)
			freight = cost * (volumeWeightAF > volumeWeight ? volumeWeightAF : volumeWeight) * fluctuation;
			break;
		case "sf":
		case "sf_efn":
		case "sf_peu":
			freight = cost * volume * fluctuation;
			break;
		case "co":
		case "co_efn":
		case "co_peu":
			// 体积重量
			Double volumeWeightCO = volume * 1000000 / 5000; // (co)
			freight = cost * (volumeWeightCO > volumeWeight ? volumeWeightCO : volumeWeight) / parities * fluctuation
					* rateModel.getRiskFactor();
			break;
		default:
			break;
		}
		return freight;
	}

}
