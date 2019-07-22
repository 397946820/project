package com.it.ocs.salereport.service.support;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.cache.OrderCache;
import com.it.ocs.cache.PricePlanCache;
import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.cal.utils.PricePlanUtils;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.MapUtil;
import com.it.ocs.listener.ProjectApplicationContext;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.salesStatistics.utils.Tools;
import com.it.ocs.sys.service.impl.PermissionService;
import com.it.ocs.sys.vo.PermissionVO;
	
public class AmazonSaleReportSupport {
	private static final String PLATFORM_DATAFILTER_PERMISSION_CODE = "SJGLX_PF";
	private static final String AMAZON_DATAFILTER_PERMISSION_CODE = "SJGLX_PF_YMX";
	private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("0.0000");//价格计算
	private static final DecimalFormat RATE_FORMAT = new DecimalFormat("0.00");//汇率计算
	private static final String[] SHIP_TYPES = { "af", "sf", "co" };
	
	/**
	 * 获取平台
	 * @param requestParamVO
	 * @return
	 */
	public static String getPlatForm(Map<String, Object> param) {
		
		if (param.containsKey("platform") && StringUtils.isNotBlank((param.get("platform").toString()))) {
			//优先从请求参数中取
			return doPlateForm(param.get("platform").toString());
		}
		PermissionService permissionService = ProjectApplicationContext.getBean("permissionService",PermissionService.class);
		PermissionVO permission = permissionService.getCurrentUserPermissionByCode(PLATFORM_DATAFILTER_PERMISSION_CODE);
		if (null != permission && permission.getChildren().size() == 1) {
			//如果只有一个平台权限，则取该平台,否则返回为null
			return doPlateForm(permission.getChildren().get(0).getName());
		}
		return null;
	}
	public static List<SalesStatisticsModel> filterDataByParam(Map<String, Object> param) throws Exception {
		param = MapUtil.removeNullOrEmpty(param);
		handleParam(param);
		MongoTemplate mongoTemplate = ProjectApplicationContext.getBean("mongoTemplate",MongoTemplate.class);
		Query query = new Query(Criteria.where("purchaseat").gte(param.get("begintime")).lt(param.get("endtime")));
		List<SalesStatisticsModel> list = mongoTemplate.find(query, SalesStatisticsModel.class, "AMAZON_SALE_ORDER");
		//获取条件搜索的数据,条件包含站点、SKU、ASIN,不包含时间
		List<SalesStatisticsModel> filterList = filterByParam(list, param);
		//获取当前页面时间数据
		List<SalesStatisticsModel> pageTimeDatas = filterDataByTime(param, filterList, null);
		//数据合并
		Map<String, SalesStatisticsModel> mergeDatas = dataMerge(pageTimeDatas);
		//计算同比、周环比、月环比
		calculate(mergeDatas, param, filterList);
		//计算利润率和利润额
		calculateRate(mergeDatas);
		//构建统计数据
		
		return new ArrayList<>(mergeDatas.values());
	}
	private static void calculateRate(Map<String,SalesStatisticsModel> result) {
		/*Map<String, PricePlanModel> map = PricePlanCache.getMap();
		for(SalesStatisticsModel model : result.values()) {
			model.setAf(new Double(RATE_FORMAT.format(model.getPrice() * 0.15)));
			model.setSf(new Double(RATE_FORMAT.format(model.getPrice() * 0.22)));
			model.setCo(new Double(RATE_FORMAT.format(model.getPrice() * 0.15)));
			if (StringUtils.isNotBlank(model.getSku()) && model.getCount() > 0) {
				Double unitprice = model.getPrice() / model.getCount();
				for(String shiptype : SHIP_TYPES) {
					PricePlanModel pricePlanModel = map.get(Tools.getCountry(model.getStation()) + model.getSku() + shiptype);
					if (pricePlanModel != null) {
						Double finalPrice = pricePlanModel.getFinalPrice();
						if (pricePlanModel.getProfitRateAction() != 1) {
							pricePlanModel.setProfitRateAction(1d);
							finalPrice = PricePlanUtils.getFinalPrice(pricePlanModel);
						}
						if (shiptype.equals("af")) {
							model.setAfRate(new Double(PRICE_FORMAT.format(unitprice / finalPrice)));
						} else if (shiptype.equals("sf")) {
							model.setSfRate(new Double(PRICE_FORMAT.format(unitprice / finalPrice)));
						} else if (shiptype.equals("co")) {
							model.setCoRate(new Double(PRICE_FORMAT.format(unitprice / finalPrice)));
						}
					}
				}
			}
		}*/
	}
	/**
	 * 计算同比、周环比、月环比
	 * @param result 计算的结果
	 * @param param 请求参数
	 * @param list 通过站点、SKU、ASIN过滤之后的数据
	 * @throws Exception 
	 */
	private static void calculate(Map<String, SalesStatisticsModel> result,Map<String,Object> param,List<SalesStatisticsModel> list) throws Exception {
		String[] dataTypeFlag = new String[]{"YEAR","WEEK","MONTH"};
		for (String flag : dataTypeFlag) {
			List<SalesStatisticsModel> timeDatas = filterDataByTime(param, list,flag);
			Map<String, SalesStatisticsModel> mergeDatas = dataMerge(timeDatas);
			for(Entry<String, SalesStatisticsModel> entry : result.entrySet()) {
				if (mergeDatas.containsKey(entry.getKey())) {
					SalesStatisticsModel model = mergeDatas.get(entry.getKey());
					Double rateResult = new Double(PRICE_FORMAT.format((entry.getValue().getPrice()-model.getPrice())/model.getPrice()));
					if (flag.equals("YEAR")) {
						entry.getValue().setSametermrate(rateResult);
					} else if (flag.equals("WEEK")) {
						entry.getValue().setWeekrate(rateResult);
					} else {
						entry.getValue().setMonthrate(rateResult);
					}
				}
			}
		}
	}
	/**
	 * 通过时间段过滤数据
	 * @param param 包含时间段的map
	 * @param list 通过过滤条件（站点、搜索条件）过滤之后的数据
	 * @param dataTypeFlag 时间从定义的标识：YEAR 同比、WEEK 周环比、MONTH 月环比、null 当前时间段数据
	 * @return
	 * @throws Exception 
	 */
	private static List<SalesStatisticsModel> filterDataByTime(Map<String,Object> param,List<SalesStatisticsModel> list,String dataTypeFlag) throws Exception {
		List<SalesStatisticsModel> result = Lists.newArrayList();
		Date beginTime = (Date)param.get("begintime");
		Date endTime = (Date)param.get("endtime");
		Date compareBeginTime = null;
		Date compareEndTime = null;
		if ("YEAR".equals(dataTypeFlag)) {
			compareBeginTime = TimeTools.getChangeYear(beginTime);
			compareEndTime = TimeTools.getChangeYear(endTime);
		} else if ("WEEK".equals(dataTypeFlag)) {
			compareBeginTime = TimeTools.getChangeDay(beginTime, -7);
			compareEndTime = TimeTools.getChangeDay(endTime, -7);
		}  else if ("MONTH".equals(dataTypeFlag)) {
			compareBeginTime = TimeTools.getChangeMonth(beginTime, -1);
			compareEndTime = TimeTools.getChangeMonth(endTime, -1);
		} else {
			compareBeginTime = beginTime;
			compareEndTime = endTime;
		}
		Map<String, Object> filterParam = Maps.newConcurrentMap();
		filterParam.put("begintime", compareBeginTime);
		filterParam.put("endtime", compareEndTime);
		filterParam.put("swichTime", param.get("swichTime"));
		if (!CollectionUtil.isNullOrEmpty(list)) {
			for (SalesStatisticsModel model : list) {
				if (checkTime(model, filterParam)) {
					result.add(model);
				}
			}
		}
		return result;
	}
	private static Map<String,SalesStatisticsModel> dataMerge(List<SalesStatisticsModel> list) {
		Map<String, SalesStatisticsModel> keyMaps = new HashMap<>();
		CollectionUtil.each(list, new IAction<SalesStatisticsModel>() {
			@Override
			public void excute(SalesStatisticsModel sourceModel) {
				String key = getKey(sourceModel);
				if (!keyMaps.containsKey(key)) {
					SalesStatisticsModel newModel = new SalesStatisticsModel();
					BeanUtils.copyProperties(sourceModel, newModel);
					newModel.setQty(1);
					keyMaps.put(key, newModel);
				} else {
					SalesStatisticsModel targetModel = keyMaps.get(key);
					targetModel.setQty(targetModel.getQty() + 1);
					targetModel.setCount(targetModel.getCount() + sourceModel.getCount());
					targetModel.setPrice(new Double(PRICE_FORMAT.format(targetModel.getPrice() + sourceModel.getPrice())));
					targetModel.setDeduction(new Double(PRICE_FORMAT.format(sourceModel.getDeduction() + targetModel.getDeduction())));
					targetModel.setTaxrate(new Double(PRICE_FORMAT.format(sourceModel.getTaxrate() + targetModel.getTaxrate())));
				}
			}
		});
		return keyMaps;
	}
	private static String getKey(SalesStatisticsModel obj) {
		return obj.getSku() + obj.getAsin() + obj.getPlatform() + obj.getStation() + obj.getCurrencycode()
		+ obj.getStatus();
	}
	private static List<SalesStatisticsModel> filterByParam(List<SalesStatisticsModel> list,Map<String,Object> param) {
		List<SalesStatisticsModel> result = Lists.newArrayList();
		@SuppressWarnings("unchecked")
		List<String> stations = (List<String>) param.get("stations");
		CollectionUtil.each(list, new IAction<SalesStatisticsModel>() {
			@Override
			public void excute(SalesStatisticsModel model) {
				if (stations.contains(model.getStation()) && checkParam(model, param)) {
					result.add(model);
				}
			}
		});
		return result;
	}
	private static boolean checkParam(SalesStatisticsModel model,Map<String,Object> param) {
		boolean result = true;
		if (param.containsKey("sku")) {
			if (!model.getSku().equals(param.get("sku").toString())) {
				result = false;
			}
		}
		if (result && param.containsKey("asin")) {
			if (!model.getAsin().equals(param.get("asin").toString())) {
				result = false;
			}
		}
		if (result && param.containsKey("orderStatus")) {
			if (!model.getStatus().equals(param.get("orderStatus").toString())) {
				result = false;
			}
		}
//		if (result) {
//			result = checkTime(model, param);
//		}
		
		return result;
	}
	private static boolean checkTime(SalesStatisticsModel model,Map<String,Object> param) throws ParseException {
		Date beginDate = (Date) param.get("begintime");
		Date endDate = (Date) param.get("endtime");
		Date stationBegin = TimeTools.getTimeByStation(beginDate, model.getStation());
		Date endBegin = TimeTools.getTimeByStation(endDate, model.getStation());
		if (!param.containsKey("swichTime")) {
			return model.getPurchaseat().getTime() >= stationBegin.getTime() && model.getPurchaseat().getTime() < endBegin.getTime();
		} else if(param.get("swichTime").equals("orderUpdateTime")) {
			return model.getUpdatedat().getTime() >= stationBegin.getTime() && model.getUpdatedat().getTime() < endBegin.getTime();
		} else if(param.get("swichTime").equals("shipTime")) {
			return model.getLastestshipdate().getTime() >= stationBegin.getTime() && model.getLastestshipdate().getTime() < endBegin.getTime();
		} else {
			return model.getPurchaseat().getTime() >= stationBegin.getTime() && model.getPurchaseat().getTime() < endBegin.getTime();
		}
		
	}
	
	private static String doPlateForm(String platForm) {
		if(StringUtils.isNotBlank(platForm)) {
			if(platForm.equals("亚马逊")) {
				return "amazon";
			} else if (platForm.equals("Ebay")) {
				return "ebay";
			} else if (platForm.equals("官网")) {
				return "light";
			}
		}
		return platForm;
	}
	private static void handleParam(Map<String,Object> param) throws ParseException {
		handleParamTime(param);
		handleParamAccount(param);
	}
	private static void handleParamAccount(Map<String,Object> param) {
		List<String> stations = Lists.newArrayList();
		if (param.containsKey("station")) {
			stations.add(param.get("station").toString());
			param.put("stations", stations);
			return;
		}
		PermissionService permissionService = ProjectApplicationContext.getBean("permissionService",PermissionService.class);
		PermissionVO permissionVO = permissionService.getCurrentUserPermissionByCode(AMAZON_DATAFILTER_PERMISSION_CODE);
		if (null == permissionVO) {
			throw new RuntimeException("用户没有亚马逊平台的权限");
		}
		if (param.containsKey("account")) {
			CollectionUtil.each(CollectionUtil.search(permissionVO.getChildren(),new IFunction<PermissionVO, Boolean>(){
				@Override
				public Boolean excute(PermissionVO obj) {
					return obj.getName().equals(param.get("account").toString());
				}
				
			}).getChildren(), new IAction<PermissionVO>() {
				@Override
				public void excute(PermissionVO stationPermissionVO) {
					stations.add(stationPermissionVO.getName());
				}
			});
			param.put("stations", stations);
			return;
		}
		CollectionUtil.each(permissionVO.getChildren(), new IAction<PermissionVO>() {
			@Override
			public void excute(PermissionVO obj) {
				CollectionUtil.each(obj.getChildren(), new IAction<PermissionVO>() {
					@Override
					public void excute(PermissionVO stationPermission) {
						stations.add(stationPermission.getName());
					}
				});
			}
		});
		param.put("stations", stations);
	}
	private static void handleParamTime(Map<String,Object> param) throws ParseException {
		if (param.containsKey("starttimestr") || param.containsKey("endtimestr")) {
			if(param.containsKey("starttimestr")) {
				Date beginDate =  TimeTools.getTime(param.get("starttime").toString());
				param.put("begintime", beginDate);
				if (!param.containsKey("endtimestr")) {
					Date endDate = TimeTools.getChangeMonth(beginDate, 1);
					param.put("endtime", endDate);
				}
			}
			if (param.containsKey("endtimestr")) {
				Date endDate =  TimeTools.getTime(param.get("endtime").toString());
				param.put("endtime", endDate);
				if (!param.containsKey("starttimestr")) {
					Date startDate = TimeTools.getChangeMonth(endDate, -1);
					param.put("begintime", startDate);
				}
			}
		} else {
			param.put("endtime", new Date());
			if (param.containsKey("timeQuantum")) {
				if ("-30".equals(param.get("timeQuantum"))) {
					param.put("begintime", TimeTools.getChangeMonth(new Date(), -1));
				}
//				switch ((int) param.get("timeQuantum")) {
//				case 30:
//					param.put("begintime", TimeTools.getChangeMonth(new Date(), -1));
//					break;
//				case 7:
//					param.put("begintime", TimeTools.getChangeDay(new Date(), -7));
//					break;
//				case 15:
//					param.put("begintime", TimeTools.getChangeDay(new Date(), -15));
//					break;
//				}
			} else {
				param.put("begintime", TimeTools.getChangeMonth(new Date(), -1));
			}
		}
	}
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(sf.format(TimeTools.getChangeMonth(new Date(), -1)));
		
	}
}
