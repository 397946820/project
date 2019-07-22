package com.it.ocs.cal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cache.OrderCache;
import com.it.ocs.cache.TimeCache;
import com.it.ocs.cal.common.IBaseService;
import com.it.ocs.cal.dao.ITaximeterDao;
import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.cal.model.TaximeterModel;
import com.it.ocs.cal.service.ITaximeterService;
import com.it.ocs.cal.utils.ExportExcelUtil;
import com.it.ocs.cal.utils.PricePlanUtils;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.TaximeterVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.RequestParamUtils;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.listener.CurrencyRateListener;
import com.it.ocs.salesStatistics.dao.ISalesStatisticsDao;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;
import com.it.ocs.sys.dao.IDepartmentDAO;
import com.it.ocs.sys.model.DepartmentModel;
import com.it.ocs.sys.model.UserModel;

@Service
public class TaximeterService extends IBaseService implements ITaximeterService {

	@Autowired
	private ITaximeterDao taximeterDao;

	@Autowired
	private IDepartmentDAO departmentDAO;

	@Autowired
	private ISalesStatisticsDao salesStatisticsDao;

	@Override
	public ResponseResult<TaximeterVo> findAll(RequestParam param, Boolean flag, String string) throws Exception {
		ResponseResult<TaximeterVo> result = new ResponseResult<>();

		TaximeterVo taximeter = BeanConvertUtil.mapToObject(param.getParam(), TaximeterVo.class);

		if (!isAMAZONYY() && !flag) {
			if (taximeter == null) {
				taximeter = new TaximeterVo();
			}
			taximeter.setUserId(UserUtils.getUserId());
		}

		CurrencyRateListener.onApplicationEvent();
		
		// 条件搜索 并排序
		List<TaximeterModel> rows = taximeterDao.query(taximeter, param.getSort() == null ? null :
			param.getSort().equals("price") ? "unitPrice" : param.getSort(), param.getOrder());

		if (!CollectionUtil.isNullOrEmpty(rows)) {
			if (StringUtils.isBlank(string)) {
				// 分页
				List<TaximeterModel> models = CollectionUtil.pageList(rows, param.getStartRow(), param.getEndRow());
				models = changeList(models, rows, flag);
				result.setRows(CollectionUtil.beansConvert(models, TaximeterVo.class));

				result.setTotal(rows.size());
			} else {

				List<TaximeterModel> models = changeList(rows, rows, flag);

				result.setRows(CollectionUtil.beansConvert(models, TaximeterVo.class));
			}
		}

		return result;
	}

	private Boolean isAMAZONYY() {
		Boolean flag = false;
		Subject subject = SecurityUtils.getSubject();
		UserModel userModel = (UserModel) subject.getSession().getAttribute("user");
		if (userModel.getDepartmentId() != null) {
			DepartmentModel model = departmentDAO.getById(userModel.getDepartmentId());
			if (model != null) {
				if (model.getCode().equals("AMAZONYY")) {
					flag = true;
				}
			}
		}
		return flag;
	}

	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template,
			List<String> strings, Boolean flag) throws Exception {
		try {
			RequestParam param = RequestParamUtils.getRequestParam(template, TaximeterVo.class);
			ResponseResult<TaximeterVo> responseResult = findAll(param, flag, "excel");
			List<TaximeterModel> list = CollectionUtil.beansConvert(responseResult.getRows(), TaximeterModel.class);
			// 输出的文件名称
			String fileName = "动态计价器数据" + Utils.getFileName() + ".xlsx";
			ExportExcelUtil.writeExcel(response, request, fileName, list, TaximeterModel.class, null, strings);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<TaximeterModel> changeList(List<TaximeterModel> list, List<TaximeterModel> tModels, Boolean flag)
			throws Exception {
		// 获取亚马逊跟官网的订单
		List<SalesStatisticsModel> lightOrder = OrderCache.getOrder("light2017");
		List<String> lightString = TimeCache.getTimes("lighttime");
		// 计算前先加载
		Map<String, Map<String, Object>> map = tease(-1L);

		// 获取美元转人民币的汇率
		Double parities = CurrencyRateCache.getCurrencyRate("RMB");
		PricePlanModel planModel = null;

		for (TaximeterModel model : list) {
			// 国家处理
			String country = getCountry(model.getCountry());
			// 采购加权单价
			// 通过国家 、sku、以及时间搜素
			List<TaximeterModel> models = getListByModel(model, parities, lightOrder, lightString, tModels);
			// 采购单价
			model.setPrice(Tools.getCurrencySymbol(model.getCurrencyCode()) + " " + model.getUnitPrice());

			// 结存库存单价
			model.setBalanceprice(getBalanceprice(models, map, country));

			planModel = calculatePlanPrice(planModel, model.getSku(), country, model.getShippingtype(),
					model.getWeightedprice(), null, map);
			if (planModel == null) {
				continue;
			}
			Double cost = new Double(df.format(planModel.getFinalCost()));
			model.setAmount(new Double(df.format(cost * model.getBalanceamount())));

			if (model.getBalanceprice() != null) {
				planModel.setFinalCost(model.getBalanceprice());
			}
			planModel.setFinalPrice(PricePlanUtils.getFinalPrice(planModel));
			if (planModel.getProfitRate() == 0.15) {
				model.setOprofit(new Double(df.format(planModel.getFinalPrice())));
				planModel.setProfitRate(0.22);
				model.setTprofit(new Double(df.format(PricePlanUtils.getFinalPrice(planModel))));
			} else if (planModel.getProfitRate() == 0.22) {
				model.setTprofit(new Double(df.format(planModel.getFinalPrice())));
				planModel.setProfitRate(0.15);
				model.setOprofit(new Double(df.format(PricePlanUtils.getFinalPrice(planModel))));
			}

			String type = model.getShippingtype();
			if (type.equals("af")) {
				model.setAf(Tools.getCurrencyCode(country) + " " + cost);
				planModel = null;
				planModel = calculatePlanPrice(planModel, model.getSku(), country, "sf", model.getWeightedprice(), null,
						map);
				model.setSf(Tools.getCurrencyCode(country) + " " + planModel.getFinalCost());
				planModel = null;
				planModel = calculatePlanPrice(planModel, model.getSku(), country, "co", model.getWeightedprice(), null,
						map);
				model.setCo(Tools.getCurrencyCode(country) + " " + planModel.getFinalCost());
			} else if (type.equals("sf")) {
				model.setSf(Tools.getCurrencyCode(country) + " " + cost);
				planModel = null;
				planModel = calculatePlanPrice(planModel, model.getSku(), country, "af", model.getWeightedprice(), null,
						map);
				model.setAf(Tools.getCurrencyCode(country) + " " + planModel.getFinalCost());
				planModel = null;
				planModel = calculatePlanPrice(planModel, model.getSku(), country, "co", model.getWeightedprice(), null,
						map);
				model.setCo(Tools.getCurrencyCode(country) + " " + planModel.getFinalCost());
			} else if (type.equals("co")) {
				model.setCo(Tools.getCurrencyCode(country) + " " + cost);
				planModel = null;
				planModel = calculatePlanPrice(planModel, model.getSku(), country, "sf", model.getWeightedprice(), null,
						map);
				model.setSf(Tools.getCurrencyCode(country) + " " + planModel.getFinalCost());
				planModel = null;
				planModel = calculatePlanPrice(planModel, model.getSku(), country, "af", model.getWeightedprice(), null,
						map);
				model.setAf(Tools.getCurrencyCode(country) + " " + planModel.getFinalCost());
			}
			planModel = null;
		}

		return list;
	}

	private String getCountry(String country) {
		String result = country.substring(0, 2);
		if (result.equals("UK")) {
			result = "GB";
		} else if (result.equals("EU")) {
			result = "DE";
		}
		return result;
	}

	private Double getBalanceprice(List<TaximeterModel> models, Map<String, Map<String, Object>> map, String country)
			throws Exception {
		Long balanceamount = 0L; // 累计实际库存
		Double amount = 0d; // 累计库存金额
		PricePlanModel pricePlan = null;

		if (!CollectionUtil.isNullOrEmpty(models)) {
			for (TaximeterModel model : models) {
				pricePlan = calculatePlanPrice(pricePlan, model.getSku(), country, model.getShippingtype(),
						model.getWeightedprice(), null, map);
				if (pricePlan == null) {
					continue;
				}
				// 库存金额
				model.setAmount(new Double(df.format(model.getBalanceamount() * pricePlan.getFinalCost())));
				if (model.getBalanceamount() != null) {
					balanceamount += model.getBalanceamount();
				}
				if (model.getAmount() != null) {
					amount += model.getAmount();
				}
				pricePlan = null;
			}
		}

		if (balanceamount != 0L) {
			return new Double(df.format(amount / balanceamount));
		}
		return 0d;
	}

	private Long getActualSalesByModel(TaximeterModel model, String time, List<SalesStatisticsModel> orders,
			List<String> strings) {
		Long actualSales = 0L;
		String country = model.getCountry().substring(0, 2);
		int start = -1, end = -1;
		if (!CollectionUtil.isNullOrEmpty(strings)) {
			if (time.equals("")) {
				start = strings.indexOf(model.getDeliveryTime());
				end = strings.lastIndexOf(model.getDeliveryTime());
			} else {
				start = strings.indexOf(time);
				end = strings.lastIndexOf(model.getDeliveryTime());
			}
			if (!CollectionUtil.isNullOrEmpty(orders)) {
				if (start != -1 && end != -1) {
					for (int i = start; i <= end; i++) {
						SalesStatisticsModel salesStatisticsModel = orders.get(i);
						if (StringUtils.isNotBlank(salesStatisticsModel.getSku())) {
							if (salesStatisticsModel.getSku().equals(model.getSku())
									&& salesStatisticsModel.getPlatform().equals(country)) {
								actualSales += orders.get(i).getCount();
							}
						}
					}
				}
			}
		}
		return actualSales;
	}

	private List<TaximeterModel> getListByModel(TaximeterModel model, Double parities,
			List<SalesStatisticsModel> lightOrder, List<String> lightString, List<TaximeterModel> tModels)
			throws Exception {

		List<TaximeterModel> result = new ArrayList<>();
		if (!CollectionUtil.isNullOrEmpty(tModels)) {
			result = CollectionUtil.searchList(tModels, new IFunction<TaximeterModel, Boolean>() {
				@Override
				public Boolean excute(TaximeterModel taximeterModel) {
					return taximeterModel.getSku().equals(model.getSku())
							&& taximeterModel.getCountry().equals(model.getCountry())
							&& taximeterModel.getDeliveryTime().compareTo(model.getDeliveryTime()) < 1;
				}
			});
			if (!CollectionUtil.isNullOrEmpty(result)) {
				Double sumprice = 0d; // 总价
				Double sumqty = 0d; // 总数
				Double unitPrice = 0d; // 单价
				String flag = ""; // 标记
				String time = ""; // 时间
				Long actualSales = 0L; // 实际销量
				for (int i = 0; i < result.size(); i++) {
					for (int j = i; j < result.size(); j++) {
						// 算出实际销量的开始时间
						if (StringUtils.isBlank(flag)) {
							if (!result.get(j).getDeliveryTime().equals(result.get(i).getDeliveryTime())) {
								time = result.get(j).getDeliveryTime();
								flag = "flag";
							}
						}
						// 计算采购加权单价
						if (result.get(j).getCurrencyCode().equals("USD")) {
							// 采购单价换成人民币
							unitPrice = result.get(j).getUnitPrice() * parities;
						} else {
							unitPrice = result.get(j).getUnitPrice();
						}
						sumprice += unitPrice * result.get(j).getQty();
						sumqty += result.get(j).getQty();
					}
					TaximeterModel taximeterModel = result.get(i);
					// 采购加权单价
					if (sumqty != 0d) {
						taximeterModel.setWeightedprice(new Double(df.format(sumprice / sumqty)));
					}
					// 实际销量
					if (model.getCountry().contains("2")) {
						// 取官网的
						actualSales = getActualSalesByModel(taximeterModel, time, lightOrder, lightString);
					} else {
						// 取亚马逊的
						SalesStatisticsVo statisticsvo = new SalesStatisticsVo();
						statisticsvo.setSku(taximeterModel.getSku());
						statisticsvo.setPlatform(taximeterModel.getCountry().substring(0, 2));
						statisticsvo.setStoptime(TimeTools.strToDate(taximeterModel.getDeliveryTime()));
						if (!time.equals("")) {
							statisticsvo.setBegintime(TimeTools.strToDate(time));
						} else {
							statisticsvo.setBegintime(TimeTools.strToDate(taximeterModel.getDeliveryTime()));
						}
						actualSales = salesStatisticsDao.getCountBySkuAndCountry(statisticsvo);
					}
					taximeterModel.setActualSales(actualSales);

					// 判断到货时间是否为空
					if (taximeterModel.getArrivalTime() != null) {
						// ERP库存 = ERP库存 + 发货数量
						taximeterModel.setRepertory(taximeterModel.getRepertory() + taximeterModel.getQty());
					}
					// 实际库存 = ERP库存 - 实际销量
					taximeterModel.setBalanceamount(taximeterModel.getRepertory() - taximeterModel.getActualSales());

					if (i == 0) {
						model.setWeightedprice(new Double(df.format(sumprice / sumqty)));
						model.setActualSales(actualSales); // 判断到货时间是否为空
						if (model.getArrivalTime() != null) {
							// ERP库存 = ERP库存 + 发货数量
							model.setRepertory(model.getRepertory() + model.getQty());
						}
						// 实际库存 = ERP库存 - 实际销量
						model.setBalanceamount(model.getRepertory() - model.getActualSales());
					}

					// 全部重置
					sumprice = 0d;
					sumqty = 0d;
					unitPrice = 0d;
					flag = "";
					time = "";
					actualSales = 0L;
				}
			}
		}

		return result;
	}
}
