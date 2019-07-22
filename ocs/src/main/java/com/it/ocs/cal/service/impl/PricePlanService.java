package com.it.ocs.cal.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cache.PricePlanCache;
import com.it.ocs.cache.UserCache;
import com.it.ocs.cal.common.IBaseService;
import com.it.ocs.cal.dao.ICalculateDao;
import com.it.ocs.cal.dao.ITaximeterDao;
import com.it.ocs.cal.model.CalculateModel;
import com.it.ocs.cal.model.CostModel;
import com.it.ocs.cal.model.PricePlanModel;
import com.it.ocs.cal.model.ProductEntityModel;
import com.it.ocs.cal.model.ProductOtherModel;
import com.it.ocs.cal.model.TaxModel;
import com.it.ocs.cal.service.IPricePlanService;
import com.it.ocs.cal.service.IProductEntityService;
import com.it.ocs.cal.utils.ExportExcelUtil;
import com.it.ocs.cal.utils.ImportExcelUtil;
import com.it.ocs.cal.utils.PricePlanUtils;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.PricePlanVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.RequestParamUtils;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.listener.CurrencyRateListener;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.salesStatistics.utils.ReflectUtils;
import com.it.ocs.sys.dao.IDepartmentDAO;
import com.it.ocs.sys.model.DepartmentModel;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.vo.PermissionVO;

@Service
@Transactional
public class PricePlanService extends IBaseService implements IPricePlanService {

	@Autowired
	private ITaximeterDao taximeterDao;

	@Autowired
	private ICalculateDao calculateDao;

	@Autowired
	private IDepartmentDAO departmentDAO;

	private Map<String, List> calculates = new HashMap<>();

	@Override
	public ResponseResult<PricePlanVo> findAll(RequestParam param, PermissionVO permission, String str, String status) {
		ResponseResult<PricePlanVo> result = new ResponseResult<>();

		param = Tools.getRequestParam(param);

		PricePlanVo pricePlan = BeanConvertUtil.mapToObject(param.getParam(), PricePlanVo.class);

		List<String> countrys = null;

		if (pricePlan == null) {
			pricePlan = new PricePlanVo();
		}
		pricePlan.setStatus(status);

		if (permission != null) {
			if (StringUtils.isNotBlank(pricePlan.getCountryId())) {
				countrys = new ArrayList<>();
				countrys.add(pricePlan.getCountryId());
			} else {
				countrys = Utils.getCountrys(permission);
			}
		} else {
			if (isCPB()) {
				pricePlan.setUserId(UserUtils.getUserId());
			} else {
				return result;
			}
		}

		if (StringUtils.isBlank(str)) {
			PageHelper.startPage(param.getPage(), param.getRows());
		}

		List<PricePlanModel> list = pricePlanDao.query(pricePlan, param.getStartRow(), param.getEndRow(),
				param.getSort(), param.getOrder(), countrys);

		for (PricePlanModel model : list) {
			if (StringUtils.isBlank(str)) {
				model.setShippingType(Tools.getShippingType(model.getShippingType()));
			}
			model.setCif(model.getCurrencySymbol() + model.getCif());
			model.setFinalPriceStr(model.getCurrencySymbol() + model.getFinalPrice());
			model.setProductType(pricePlanDao.getProductTypeBySku(model.getSku()));
			Map<String,Object> map = new HashMap<>();
			map.put("sku", model.getSku());
			//退货率
			map.put("countryId", model.getCountryId());
			model.setReturnRate(pricePlanDao.getReturnRateBySkuAndCountry(map));
			if("GB".equals(model.getCountryId())){
				map.put("countryId", "UK");
			}
			model.setDiscontinue(pricePlanDao.getDISInfo(map));
			
		}

		if (StringUtils.isBlank(str)) {
			list = Tools.changeList(list);

			PageInfo<PricePlanModel> pageInfo = new PageInfo<>(list);

			result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), PricePlanVo.class));

			result.setTotal((int) pageInfo.getTotal());
		} else {
			result.setRows(CollectionUtil.beansConvert(list, PricePlanVo.class));
		}
		
		return result;
	}

	@Override
	public String lePricePlanTest(PricePlanModel model) {
		List<Map<String, Object>> data = pricePlanDao.getPriceTestData(model);
		return PricePlanUtils.getLePricePlanTest(data);
	}

	@Override
	public List<Map<String, String>> findAllSku(String status) {
		List<String> strings = pricePlanDao.findAllSkuByStatus(status);
		List<Map<String, String>> list = Lists.newArrayList();
		CollectionUtil.each(strings, new IAction<String>() {
			@Override
			public void excute(String string) {
				Map<String, String> map = Maps.newHashMap();
				map.put("name", string);
				map.put("value", string);
				list.add(map);
			}
		});
		return list;
	}

	@Override
	public Boolean isCPB() {
		Boolean flag = false;
		Subject subject = SecurityUtils.getSubject();
		UserModel userModel = (UserModel) subject.getSession().getAttribute("user");
		if (userModel.getDepartmentId() != null) {
			DepartmentModel model = departmentDAO.getById(userModel.getDepartmentId());
			if (model != null) {
				if (model.getCode().equals("CPB")) {
					SimpleAuthorizationInfo cacheInfo = UserCache.getAuthMap().get(userModel.getUserName());
					Set<String> roles = cacheInfo.getRoles();
					if (!CollectionUtil.isNullOrEmpty(roles)) {
						if (!roles.contains("CPZJ")) {
							flag = true;
						}
					}
				}
			}
		}
		return flag;
	}
	public void timingRefresh() {
		refresh(true, "1");
	}

	@Override
	public OperationResult refresh(Boolean f, String status) {
		OperationResult result = new OperationResult();
		try {
			CurrencyRateListener.onApplicationEvent();
			Map<String, Object> taxs = getTaxs();
			Long userId = UserUtils.getUserIdByFlag(f);
			List<ProductEntityModel> list = productEntityDao.findAllByUserAndStatus(userId, status);
			if (!CollectionUtil.isNullOrEmpty(list)) {
				CollectionUtil.each(list, new IAction<ProductEntityModel>() {
					@Override
					public void excute(ProductEntityModel model) {
						/*List<ProductOtherModel> list = productOtherDao.findByParentId(model.getEntityId());
						CollectionUtil.each(list, new IAction<ProductOtherModel>() {
							@Override
							public void excute(ProductOtherModel otherModel) {
								otherModel.setClearPrice(getNewClearPrice(model, otherModel, taxs));
								productOtherDao.update(otherModel);
							}
						});*/
						pricePlanDao.refresh(model.getSku(), model.getUserId());
					}
				});

			}

		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("刷新失败");
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	private Double getNewClearPrice(ProductEntityModel model, ProductOtherModel otherModel, Map<String, Object> taxs) {
		Double result = 0d;
		String currency = model.getCurrency();
		TaxModel taxModel = (TaxModel) taxs.get(otherModel.getCountryId());
		if (currency.equals("USD")) {
			result = new Double(df.format(model.getPrice() * taxModel.getClearCoefficient()));
		} else if (currency.equals("RMB") || currency.equals("CNY")) {
			result = new Double(df.format(model.getPrice() / 1.17 / CurrencyRateCache.getCurrencyRate("RMB")
					* taxModel.getClearCoefficient()));
		}
		return result;
	}

	@Override
	public OperationResult allRefresh() {
		return null;
	}

	@Override
	public PricePlanModel editPricePlan(PricePlanVo pricePlan) {

		PricePlanModel model = pricePlanDao.getById(pricePlan.getEntityId());
		// 修改了可用利润率,重新计算最终价格
		if ((double) model.getProfitRateAction() != (double) pricePlan.getProfitRateAction()) {
			model.setProfitRateAction(pricePlan.getProfitRateAction());
			// 重新设置利润率
			Double profitRate = model.getProfitRate();
			model.setProfitRate(new Double(df.format(profitRate * pricePlan.getProfitRateAction())));
			// 重新计算最终价格
			model.setFinalPrice(new Double(df.format(PricePlanUtils.getFinalPrice(model))));
			// 重新设置回来
			model.setProfitRate(profitRate);
		} else if ((double) model.getFinalPrice() != (double) pricePlan.getFinalPrice()) {
			// 修改了最终价格，反推利润率
			model.setFinalPrice(pricePlan.getFinalPrice());
			model.setProfitRateAction(new Double(df.format(PricePlanUtils.getProfitRateAction(model))));
		}
		model.setUpdatedAt(new Date());
		// 修改表
		pricePlanDao.update(model);
		// 保存到临时表
		pricePlanDao.addTemp(model);
		// 保存到批次号的表
		CalculateModel calculateModel = new CalculateModel();
		BeanUtils.copyProperties(model, calculateModel);
		calculateModel.setUserId(UserUtils.getUserId());
		calculateModel.setBatchNumber("2");
		calculateModel.setCreatedAt(new Date());
		calculateModel.setUpdatedAt(new Date());
		calculateDao.add(calculateModel);

		return model;
	}

	@Override
	public OperationResult recover(Boolean flag, String status) {
		OperationResult result = new OperationResult();
		try {
			// 查找出可用利润率不等于1的
			Long userId = UserUtils.getUserIdByFlag(flag);
			List<PricePlanModel> plans = pricePlanDao.findAllLessByUserId(userId, status);
			for (PricePlanModel pricePlan : plans) {
				// 设置可用利润率为100%
				pricePlan.setProfitRateAction(1d);
				// 计算最终价格
				pricePlan.setFinalPrice(new Double(df.format(PricePlanUtils.getFinalPrice(pricePlan))));
				// 更新时间
				pricePlan.setUpdatedAt(new Date());
				// 修改表
				pricePlanDao.update(pricePlan);
			}

		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("恢复失败");
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template,
			List<String> strings, PermissionVO permission, String status) {
		// 输出的文件名称
		String fileName = "";
		// 要导出的
		List<PricePlanModel> result = new ArrayList<>();
		try {
			if (template.equals("template") || template.equals("template_variant")) {
				// 导出模板
				CalculateModel model = new CalculateModel();
				model.setCountryId("DE");
				if (template.equals("template")) {
					fileName = "价格推算模板" + Utils.getFileName() + ".xlsx";
					model.setShippingType("af");
					model.setSku("400019-WW-US");
					model.setQty("1");
				} else {
					fileName = "价格推算变体模板" + Utils.getFileName() + ".xlsx";
					model.setShippingType("af,af");
					model.setSku("400019-WW-US,400019-WW-US");
					model.setQty("1,1");
				}
				model.setProfitRateAction1(1d);
				model.setProfitRateAction2(1d);
				model.setProfitRateAction3(1d);
				List<CalculateModel> models = new ArrayList<>();
				models.add(model);
				ExportExcelUtil.writeExcel(response, request, fileName, models, CalculateModel.class, template, null);
			} else if (template.equals("count") || template.equals("variant")) {
				CostModel model = new CostModel();
				model.setCountryId("DE");
				if (template.equals("count")) {
					fileName = "保本销量模板" + Utils.getFileName() + ".xlsx";
					model.setSku("400019-WW-US");
					model.setShippingType("af");
					model.setPrice("7.99");
				} else {
					fileName = "保本销量变体模板" + Utils.getFileName() + ".xlsx";
					model.setSku("400019-WW-US,400019-WW-US");
					model.setShippingType("af,af");
					model.setPrice("7.99,7.99");
				}
				model.setCost(30.0);
				model.setQty(20);
				List<CostModel> list = new ArrayList<>();
				list.add(model);
				ExportExcelUtil.writeExcel(response, request, fileName, list, CostModel.class, null,
						getCostStrings("template"));

			} else {
				// 导出全部
				RequestParam param = RequestParamUtils.getRequestParam(template, PricePlanVo.class);
				ResponseResult<PricePlanVo> responseResult = findAll(param, permission, "excel", status);
				result.addAll(CollectionUtil.beansConvert(responseResult.getRows(), PricePlanModel.class));
				fileName = "价格计划数据" + Utils.getFileName() + ".xlsx";
				ExportExcelUtil.writeExcel(response, request, fileName, result, PricePlanModel.class, template,
						strings);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	private List<String> getCostStrings(String string) {
		List<String> list = new ArrayList<>();
		list.add("国家");
		list.add("SKU");
		list.add("运输方式");
		list.add("亚马逊售价");
		list.add("亚马逊活动费");
		list.add("预计销量");
		if (string.equals("")) {
			list.add("销售利润");
			list.add("推算结果");
			list.add("保本销量");
		}
		return list;
	}

	@Override
	public void reckonExport(HttpServletRequest request, HttpServletResponse response, String string, String status) {
		try {
			Long userId = UserUtils.getUserId();
			List list = calculates.get(userId + string + status);
			String fileName = "";
			if (string.equals("reckon")) {
				fileName = "推算数据" + Utils.getFileName() + ".xlsx";
				ExportExcelUtil.writeExcel(response, request, fileName, list, CalculateModel.class, null, null);
			} else if (string.equals("reckonCount")) {
				fileName = "保本销量数据" + Utils.getFileName() + ".xlsx";
				ExportExcelUtil.writeExcel(response, request, fileName, list, CostModel.class, null,
						getCostStrings(""));
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
	@Override
	public OperationResult uploadExcel(MultipartFile file, String str, PermissionVO permission, String status) {
		OperationResult result = new OperationResult();
		try {
			InputStream inputStream = file.getInputStream();
			List<CalculateModel> list = ImportExcelUtil.importExcel(CalculateModel.class, inputStream);
			if (!CollectionUtil.isNullOrEmpty(list)) {
				Set<String> skus = new HashSet<>();
				CollectionUtil.each(list, new IAction<CalculateModel>() {
					@Override
					public void excute(CalculateModel obj) {
						if (StringUtils.isNotBlank(obj.getSku())) {
							CollectionUtil.each(obj.getSku().split(","), new IAction<String>() {
								@Override
								public void excute(String sku) {
									skus.add(sku);
								}
							});
						}
					}
				});
				Map<String, PricePlanModel> map = PricePlanCache
						.getMap(pricePlanDao.findBySkusAndStatus(new ArrayList<>(skus), status));
				Map<String, Object> errorString = getErrorString(permission, list, str, map, status);
				if (errorString.containsKey("noCountrys")) {
					result.setData(errorString.get("noCountrys"));
				} else {
					for (int j = 0; j < list.size(); j++) {
						if (errorString.containsKey("numbers")) {
							Set<Integer> numbers = (Set<Integer>) errorString.get("numbers");
							if (numbers.contains(j)) {
								continue;
							}
						}
						CalculateModel model = list.get(j);
						String countryId = model.getCountryId();
						String sku = model.getSku();
						String type = model.getShippingType().toLowerCase();
						String qty = model.getQty();
						if (str.equals("price")) {
							String[] split = sku.split(",");
							Double d1 = 0d, d2 = 0d, d3 = 0d;
							for (int i = 0; i < split.length; i++) {
								PricePlanModel planModel = map
										.get(countryId.toUpperCase() + split[i] + type.split(",")[i].toLowerCase());
								if (planModel != null) {
									Double initProfitRate = planModel.getProfitRate();
									if (model.getProfitRateAction1() != null) {
										planModel.setProfitRate(initProfitRate * model.getProfitRateAction1());
										d1 += PricePlanUtils.getFinalPrice(planModel) * new Integer(qty.split(",")[i]);
									}
									if (model.getProfitRateAction2() != null) {
										planModel.setProfitRate(initProfitRate * model.getProfitRateAction2());
										d2 += PricePlanUtils.getFinalPrice(planModel) * new Integer(qty.split(",")[i]);
									}
									if (model.getProfitRateAction3() != null) {
										planModel.setProfitRate(initProfitRate * model.getProfitRateAction3());
									}
									d3 += PricePlanUtils.getFinalPrice(planModel) * new Integer(qty.split(",")[i]);
									planModel.setProfitRate(initProfitRate);
								}
							}
							if (d1 != 0d) {
								model.setFinalPrice1(new Double(df.format(d1)));
							}
							if (d2 != 0d) {
								model.setFinalPrice2(new Double(df.format(d2)));
							}
							if (d3 != 0d) {
								model.setFinalPrice3(new Double(df.format(d3)));
							}
						} else if (str.equals("rate")) {
							PricePlanModel planModel = map.get(countryId.toUpperCase() + sku + type.toLowerCase());
							if (planModel != null) {
								Double finalPrice = planModel.getFinalPrice();
								if (model.getFinalPrice1() != null) {
									planModel.setFinalPrice(model.getFinalPrice1() / new Integer(qty));
									model.setProfitRateAction1(
											new Double(df.format(PricePlanUtils.getProfitRateAction(planModel))));
								}
								if (model.getFinalPrice2() != null) {
									planModel.setFinalPrice(model.getFinalPrice2() / new Integer(qty));
									model.setProfitRateAction2(
											new Double(df.format(PricePlanUtils.getProfitRateAction(planModel))));
								}
								if (model.getFinalPrice3() != null) {
									planModel.setFinalPrice(model.getFinalPrice3() / new Integer(qty));
									model.setProfitRateAction3(
											new Double(df.format(PricePlanUtils.getProfitRateAction(planModel))));
								}
								planModel.setFinalPrice(finalPrice);
							}
						}
					}
					if (!CollectionUtil.isNullOrEmpty(list)) {
						// 获取用户的id
						Long userId = UserUtils.getUserId();
						for (int i = 0; i < list.size(); i++) {
							if (errorString.containsKey("numbers")) {
								Set<Integer> numbers = (Set<Integer>) errorString.get("numbers");
								if (numbers.contains(i)) {
									continue;
								}
							}
							CalculateModel model = list.get(i);
							model.setBatchNumber("1");
							model.setUserId(userId);
							model.setCreatedAt(new Date());
							model.setUpdatedAt(new Date());
							if (model.getProfitRateAction1() != null && model.getFinalPrice1() != null) {
								model.setProfitRateAction(model.getProfitRateAction1());
								model.setFinalPrice(model.getFinalPrice1());
								calculateDao.add(model);
							}
							if (model.getProfitRateAction2() != null && model.getFinalPrice2() != null) {
								model.setProfitRateAction(model.getProfitRateAction2());
								model.setFinalPrice(model.getFinalPrice2());
								calculateDao.add(model);
							}
							if (model.getProfitRateAction3() != null && model.getFinalPrice3() != null) {
								model.setProfitRateAction(model.getProfitRateAction3());
								model.setFinalPrice(model.getFinalPrice3());
								calculateDao.add(model);
							}
						}
						result.setData(errorString.get("errors"));
						calculates.put(userId + "reckon" + status, list);
					}
				}
			}

		} catch (Exception e) {
			result.setErrorCode(1);
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	private Map<String, Object> getErrorString(PermissionVO permission, List list, String str,
			Map<String, PricePlanModel> map2, String status) throws Exception {
		Map<String, Object> map = new HashMap<>();

		List<String> countrys = new ArrayList<>();
		if (permission != null) {
			countrys = Utils.getCountrys(permission);
		} else {
			if (isCPB()) {
				countrys = Utils.getCountrys(permission);
			} else {
				map.put("noCountrys", "你没有权限,请联系管理员");
				return map;
			}
		}

		if (!CollectionUtil.isNullOrEmpty(list)) {
			List<String> strings = new ArrayList<>();
			Set<Integer> numbers = new HashSet<>();
			// 查找所有的sku
			List<String> skus = pricePlanDao.findAllSkuByStatus(status);
			for (int i = 0; i < list.size(); i++) {
				String country = ReflectUtils.getValue(list.get(i), "countryid").toString().toUpperCase();
				String sku = ReflectUtils.getValue(list.get(i), "sku").toString();
				String shippingType = ReflectUtils.getValue(list.get(i), "shippingtype").toString().toLowerCase();
				getErrorSkuOrCountry(country, "country", countrys, strings, i, numbers);
				getErrorSkuOrCountry(sku, "sku", skus, strings, i, numbers);
				getErrorSkuOrCountry(shippingType, "shippingType", listShippingType, strings, i, numbers);
				getExistsString(country, sku, shippingType, strings, i, numbers, map2);
				if (!str.equals("count")) {
					String string = ReflectUtils.getValue(list.get(i), "qty").toString();
					getErrorQty(string, sku, strings, i, str, numbers);
					if (StringUtils.isNotBlank(shippingType) && StringUtils.isNotBlank(string)) {
						if (shippingType.split(",").length != string.split(",").length) {
							strings.add("第" + (i + 2) + "行	运输方式与捆绑个数不一致;<br/>");
							numbers.add(i);
						}
					}
				} else if (str.equals("count")) {
					String string = ReflectUtils.getValue(list.get(i), "price").toString();
					if (StringUtils.isNotBlank(string)) {
						if (StringUtils.isNotBlank(string) && StringUtils.isNotBlank(sku)) {
							if (string.split(",").length != sku.split(",").length) {
								strings.add("第" + (i + 2) + "行	sku与亚马逊售价的个数不一致;<br/>");
								numbers.add(i);
							}
						}
						String[] split = string.split(",");
						for (String s : split) {
							if (new Double(s) == 0) {
								strings.add("第" + (i + 2) + "行	亚马逊售价不可为0;<br/>");
								numbers.add(i);
								break;
							}
						}
					} else {
						strings.add("第" + (i + 2) + "行	亚马逊售价为空;<br/>");
					}
					if (StringUtils.isBlank(ReflectUtils.getValue(list.get(i), "cost").toString())) {
						strings.add("第" + (i + 2) + "行	亚马逊活动费为空;<br/>");
						numbers.add(i);
					} else if (new Double(ReflectUtils.getValue(list.get(i), "cost").toString()) == 0) {
						strings.add("第" + (i + 2) + "行	亚马逊活动费不可为0;<br/>");
						numbers.add(i);
					}
				}

			}
			map.put("errors", strings);
			map.put("numbers", numbers);
		}
		return map;

	}

	private void getExistsString(String country, String sku, String shippingType, List<String> strings, int i,
			Set<Integer> numbers, Map<String, PricePlanModel> map) {
		if (StringUtils.isNotBlank(sku) && StringUtils.isNotBlank(shippingType) && StringUtils.isNotBlank(country)) {
			String[] split1 = sku.split(",");
			String[] split2 = shippingType.split(",");
			if (split1.length == split2.length) {
				for (int j = 0; j < split1.length; j++) {
					PricePlanModel pricePlanModel = map.get(country + split1[j] + split2[j]);
					if (pricePlanModel == null) {
						strings.add("第" + (i + 2) + "行现了国家为<B>" + country + "</B>,sku为<B>" + split1[j] + "</B>,运输方式为<B>"
								+ split2[j] + "</B>的行数据不存在");
						numbers.add(i);
					}
				}
			} else {
				strings.add("第" + (i + 2) + "行	sku与运输方式的个数不一致;<br/>");
				numbers.add(i);
			}
		}
	}

	private void getErrorProfitrateaction(Object object, int i, String str, List<String> strings, Set<Integer> numbers)
			throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("profitrateaction", "利润系数");
		map.put("finalprice", "建议售价");
		for (int j = 1; j < 4; j++) {
			if (StringUtils.isBlank(ReflectUtils.getValue(object, str + j).toString())) {
				strings.add("第" + (i + 2) + "行	" + map.get(str) + j + "为空;<br/>");
				numbers.add(i);
			}
			if (str.equals("finalPrice")) {
				if (new Double(ReflectUtils.getValue(object, str + j).toString()) == 0) {
					strings.add("第" + (i + 2) + "行	" + map.get(str) + j + "为0;<br/>");
					numbers.add(i);
				}
			}
		}

	}

	private void getErrorSkuOrCountry(String string, String flag, List<String> list, List<String> strings, int i,
			Set<Integer> numbers) {
		Map<String, String> map = new HashMap<>();
		map.put("country", "国家");
		map.put("sku", "sku");
		map.put("shippingType", "运输方式");
		if (StringUtils.isBlank(string)) {
			strings.add("第" + (i + 2) + "行	" + map.get(flag) + "为空;<br/>");
			numbers.add(i);
		} else {
			if (!CollectionUtil.isNullOrEmpty(list)) {
				String[] split = string.split(",");
				for (String s : split) {
					if (!list.contains(s)) {
						if (flag.equals("country")) {
							strings.add(
									"第" + (i + 2) + "行	出现的" + map.get(flag) + "    <B>" + s + "</B>,你没有此国家的权限;<br/>");
						} else {
							strings.add("第" + (i + 2) + "行	出现了不存在的" + map.get(flag) + "  <B>" + s + "</B>;<br/>");
						}
						numbers.add(i);
						break;
					}
				}
			}

		}

	}

	private void getErrorQty(String string, String sku, List<String> strings, int i, String str, Set<Integer> numbers)
			throws Exception {
		if (StringUtils.isNotBlank(string)) {
			String[] split = string.split(",");
			if (!str.equals("count")) {
				if (StringUtils.isNotBlank(sku) && sku.split(",").length != split.length) {
					strings.add("第" + (i + 2) + "行	sku与捆绑个数不一致;<br/>");
					numbers.add(i);
				}
			}
			for (String flag : split) {
				if (new Double(flag) <= 0) {
					if (str.equals("count")) {
						strings.add("第" + (i + 2) + "行	预计销量应大于0;<br/>");
						numbers.add(i);
					} else {
						strings.add("第" + (i + 2) + "行	捆绑个数应大于0;<br/>");
						numbers.add(i);
					}
					break;
				}
			}
		} else {
			if (str.equals("count")) {
				strings.add("第" + (i + 2) + "行	预计销量为空;<br/>");
				numbers.add(i);
			} else {
				strings.add("第" + (i + 2) + "行	捆绑个数为空;<br/>");
				numbers.add(i);
			}
		}

	}

	@Override
	public OperationResult uploadGetCount(MultipartFile file, PermissionVO permissionVO, String status) {
		OperationResult result = new OperationResult();
		try {
			InputStream inputStream = file.getInputStream();
			List<CostModel> list = ImportExcelUtil.importExcel(CostModel.class, inputStream);
			if (!CollectionUtil.isNullOrEmpty(list)) {
				Set<String> skus = new HashSet<>();
				CollectionUtil.each(list, new IAction<CostModel>() {
					@Override
					public void excute(CostModel obj) {
						if (StringUtils.isNotBlank(obj.getSku())) {
							CollectionUtil.each(obj.getSku().split(","), new IAction<String>() {
								@Override
								public void excute(String sku) {
									skus.add(sku);
								}
							});
						}
					}
				});
				Map<String, PricePlanModel> map = PricePlanCache
						.getMap(pricePlanDao.findBySkusAndStatus(new ArrayList<>(skus), status));
				Map<String, Object> errorString = getErrorString(permissionVO, list, "count", map, status);
				if (errorString.containsKey("noCountrys")) {
					result.setData(errorString.get("noCountrys"));
				} else {
					for (int j = 0; j < list.size(); j++) {
						if (errorString.containsKey("numbers")) {
							Set<Integer> numbers = (Set<Integer>) errorString.get("numbers");
							if (numbers.contains(j)) {
								continue;
							}
						}
						CostModel model = list.get(j);
						String country = model.getCountryId();
						String sku = model.getSku();
						String shippingType = model.getShippingType().toLowerCase();
						String[] split1 = sku.split(",");
						String[] split2 = model.getPrice().split(",");
						String[] split3 = shippingType.split(",");
						double d = 0d;
						for (int i = 0; i < split1.length; i++) {
							PricePlanModel planModel = map
									.get(country.toUpperCase() + split1[i] + split3[i].toLowerCase());
							if (planModel != null) {
								Double finalPrice = planModel.getFinalPrice();
								planModel.setFinalPrice(new Double(split2[i]));
								d += PricePlanUtils.getProfitRateAction(planModel) * 0.15 * new Double(split2[i]);
								planModel.setFinalPrice(finalPrice);
							}
						}
						if (model.getQty() == 0) {
							model.setResults("不能保本");
							if (d > 0) {
								model.setCount((int) Math.ceil(model.getCost() / d) + "");
							} else {
								model.setCount("0");
							}
						} else {
							double profit = new Double(df.format(d * model.getQty() - model.getCost()));
							model.setSalesProfit(profit);
							if (profit > 0) {
								model.setResults("能保本");
								model.setCount((int) Math.ceil(model.getCost() / d) + "");
							} else {
								model.setResults("不能保本");
								model.setCount("0");
							}
						}
					}
				}
				result.setData(errorString.get("errors"));
				calculates.put(UserUtils.getUserId() + "reckonCount" + status, list);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取产品分类列表下拉数据
	 */
	@Override
	public List<ComboBoxVO> getProductField() {
		return pricePlanDao.getProductField();
	}
}
