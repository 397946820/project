package com.it.ocs.cal.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cal.common.IBaseService;
import com.it.ocs.cal.dao.ILightTaximeterDao;
import com.it.ocs.cal.dao.ISmallTaximeterDao;
import com.it.ocs.cal.model.ProductCostModel;
import com.it.ocs.cal.model.ProductEntityModel;
import com.it.ocs.cal.model.ProductOtherModel;
import com.it.ocs.cal.model.ProductPriceModel;
import com.it.ocs.cal.model.SKUDiscontinueModel;
import com.it.ocs.cal.model.TaxModel;
import com.it.ocs.cal.service.IProductEntityService;
import com.it.ocs.cal.utils.ExportExcelUtil;
import com.it.ocs.cal.utils.ImportExcelUtil;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.ProductEntityVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.RequestParamUtils;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.listener.CurrencyRateListener;
import com.it.ocs.sys.model.UserModel;

@Service
@Transactional
public class ProductEntityService extends IBaseService implements IProductEntityService {

	private static final Logger logger = Logger.getLogger(ProductEntityService.class);
	
	@Autowired
	private ILightTaximeterDao lightTaximeterDao;
	
	@Autowired
	private ISmallTaximeterDao smallTaximeterDao;

	@Override
	public ResponseResult<ProductEntityVo> findAll(RequestParam param, Boolean flag, String string) {
		ResponseResult<ProductEntityVo> result = new ResponseResult<>();

		param = Tools.getRequestParam(param);

		ProductEntityVo entity = BeanConvertUtil.mapToObject(param.getParam(), ProductEntityVo.class);

		if (!flag) {
			if (entity == null) {
				entity = new ProductEntityVo();
			}
			entity.setUserId(UserUtils.getUserId());
		}

		if (StringUtils.isBlank(string)) {
			PageHelper.startPage(param.getPage(), param.getRows());
		}

		List<ProductEntityModel> list = productEntityDao.queryByPage(entity, param.getStartRow(), param.getEndRow(),
				param.getSort(), param.getOrder());

		if (StringUtils.isBlank(string)) {
			PageInfo<ProductEntityModel> pageInfo = new PageInfo<>(list);

			result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), ProductEntityVo.class));

			result.setTotal((int) pageInfo.getTotal());
		} else {
			result.setRows(CollectionUtil.beansConvert(list, ProductEntityVo.class));
		}

		return result;
	}

	@Override
	public OperationResult saveEdit(ProductEntityVo entity) {
		OperationResult result = new OperationResult();
		try {

			// 取出产品成本信息
			ProductCostModel cost = new ProductCostModel();
			cost.setCurrency(entity.getCurrency());
			cost.setPrice(entity.getPrice());
			cost.setTaxRebateRate(entity.getTaxRebateRate());
			cost.setInterestRate(entity.getInterestRate());
			// 取出产品其他信息
			String others = entity.getOthers();
			List<ProductOtherModel> models = JSON.parseArray(others, ProductOtherModel.class);
			CurrencyRateListener.onApplicationEvent();
			models = changeOtherModels(entity, models, getTaxs());
			Long entityId = entity.getEntityId();
			if (entityId == null) {
				// 保存
				// 查找所有的sku
				List<String> strings = productEntityDao.findAllSku();
				if (strings.contains(entity.getSku())) {
					throw new RuntimeException(entity.getSku() + "，此sku已经存在,无法再次添加");
				}
				// 基本
				entity.setUserId(UserUtils.getUserId());
				entity.setCreatedAt(new Date());
				entity.setUpdatedAt(new Date());
				productEntityDao.add(entity);
				// 成本
				cost.setCreatedAt(new Date());
				cost.setUpdatedAt(new Date());
				productCostDao.add(cost);
				// 其他
				for (ProductOtherModel model : models) {
					model.setCreatedAt(new Date());
					model.setUpdatedAt(new Date());
				}
				batchAdd(models, productOtherDao);
			} else {
				// 修改
				// 基本
				ProductEntityModel entityModel = productEntityDao.getById(entityId);
				entity.setUserId(entityModel.getUserId());
				entity.setUpdatedAt(new Date());
				productEntityDao.update(entity);
				// 成本
				// 根据父id找到id
				cost.setEntityId(productCostDao.findIdByParentId(entityId));
				cost.setUpdatedAt(new Date());
				productCostDao.update(cost);
				// 其他
				for (ProductOtherModel model : models) {
					model.setUpdatedAt(new Date());
					productOtherDao.update(model);
				}
			}
			pricePlanDao.refresh(entity.getSku(), entity.getUserId());
			lightTaximeterDao.refresh(entity.getSku(), entity.getUserId());
			smallTaximeterDao.refresh(entity.getSku());
		} catch (Exception e) {
			result.setErrorCode(1);
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	// 导出
	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template, Boolean flag,
			List<String> strings) {
		// 输出的文件名称
		String fileName = "";
		// 要导出的
		List<ProductEntityModel> result = new ArrayList<>();
		try {
			if (template.equals("template")) {
				// 导出模板
				fileName = "产品模板" + Utils.getFileName() + ".xlsx";
				ProductEntityModel model = new ProductEntityModel();
				model.setSku("400019-WW-US");
				model.setLength(0.072);
				model.setWidth(0.068);
				model.setHeight(0.11);
				model.setNetWeight(0.15);
				model.setGrossWeight(0.15);
				model.setCurrency("USD");
				model.setPrice(4.91);
				model.setPackingQty(50d);
				model.setOuterVolume(0.01);
				model.setOuterWeight(0.46);
				model.setIsMultiOne("0");
				model.setIsPeu("0");
				model.setStatus("1");
				model.setTaxRebateRate(0.13);
				model.setInterestRate(0.04);
				model.setCountryId("US,GB,DE,FR,IT,ES,JP,CA,AU");
				model.setCategory(
						"Tools&HomeImprovement,Everythingelse,Allesandere,Touteslesautrescatégories,Tuttoilresto,Todolodemás,Home&Kitchen,Home&Garden,AU_Category");
				model.setTurnoverRate("60.0,60.0,60.0,60.0,60.0,60.0,60.0,60.0,60.0");
				model.setQtyOrdered("1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0");
				model.setAverageMonth("3.1367,2.9459,2.9459,2.9459,2.9459,2.9459,4.3072,3.7569,3");
				model.setUnfulliableRate("0.0238,0.0343,0.0067,0.0534,0.0431,0.0603,0.0152,0.0152,0.01");
				model.setReplacementRate("0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01,0.01");
				model.setClearPrice("8.1015,8.1015,8.1015,8.1015,8.1015,8.1015,8.1015,8.1015,8.1015");
				model.setAmzFba("0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0");
				model.setEfnFee("0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0");
				model.setDutyRate("0.039,0.027,0.037,0.037,0.037,0.037,0.0,0.07,0.05");
				model.setReturnRate("0.0238,0.0343,0.0067,0.0534,0.0431,0.0603,0.0152,0.0152,0.01");
				result.add(model);
			} else {
				// 导出全部
				RequestParam param = RequestParamUtils.getRequestParam(template, ProductEntityModel.class);
				ResponseResult<ProductEntityVo> responseResult = findAll(param, flag, "excel");
				result.addAll(CollectionUtil.beansConvert(responseResult.getRows(), ProductEntityModel.class));
				changeList(result, "flag");
				fileName = "产品数据" + Utils.getFileName() + ".xlsx";
			}

			strings.add("实际外箱体积");
			strings.add("实际外箱重量");
			strings.add("国家");
			strings.add("产品分类");
			strings.add("库存周转率");
			strings.add("订单数量");
			strings.add("平均存储月份");
			strings.add("不可用率");
			strings.add("补发比率");
			strings.add("清关价");
			strings.add("亚马逊FBA费用");
			strings.add("EFN费用");
			strings.add("关税税率");
			strings.add("退货率");
			ExportExcelUtil.writeExcel(response, request, fileName, result, ProductEntityModel.class, template,
					strings);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	private void changeList(List<ProductEntityModel> list, String string) {
		// 查出其他信息并拼接
		for (ProductEntityModel model : list) {
			List<ProductOtherModel> others = productOtherDao.findByParentId(model.getEntityId());
			if (!CollectionUtil.isNullOrEmpty(others)) {
				StringBuffer country = new StringBuffer();
				StringBuffer category = new StringBuffer();
				StringBuffer turnoverRate = new StringBuffer();
				StringBuffer qtyOrdered = new StringBuffer();
				StringBuffer averageMonth = new StringBuffer();
				StringBuffer unfulliableRate = new StringBuffer();
				StringBuffer replacementRate = new StringBuffer();
				StringBuffer clearPrice = new StringBuffer();
				StringBuffer amzFba = new StringBuffer();
				StringBuffer efnFee = new StringBuffer();
				StringBuffer dutyRate = new StringBuffer();
				StringBuffer returnRate = new StringBuffer();
				for (int i = 0; i < others.size(); i++) {
					country.append(others.get(i).getCountryId());
					category.append(others.get(i).getCategory());
					turnoverRate.append(others.get(i).getTurnoverRate());
					qtyOrdered.append(others.get(i).getQtyOrdered());
					averageMonth.append(others.get(i).getAverageMonth());
					unfulliableRate.append(others.get(i).getUnfulliableRate());
					replacementRate.append(others.get(i).getReplacementRate());
					clearPrice.append(others.get(i).getClearPrice());
					amzFba.append(others.get(i).getAmzFba());
					efnFee.append(others.get(i).getEfnFee());
					dutyRate.append(others.get(i).getDutyRate());
					returnRate.append(others.get(i).getReturnRate());
					if (i < others.size() - 1) {
						country.append(",");
						category.append(",");
						turnoverRate.append(",");
						qtyOrdered.append(",");
						averageMonth.append(",");
						unfulliableRate.append(",");
						replacementRate.append(",");
						clearPrice.append(",");
						amzFba.append(",");
						efnFee.append(",");
						dutyRate.append(",");
						returnRate.append(",");
					}
				}
				model.setCountryId(country.toString());
				model.setCategory(category.toString());
				model.setTurnoverRate(turnoverRate.toString());
				model.setQtyOrdered(qtyOrdered.toString());
				model.setAverageMonth(averageMonth.toString());
				model.setUnfulliableRate(unfulliableRate.toString());
				model.setReplacementRate(replacementRate.toString());
				model.setClearPrice(clearPrice.toString());
				model.setAmzFba(amzFba.toString());
				model.setEfnFee(efnFee.toString());
				model.setDutyRate(dutyRate.toString());
				model.setReturnRate(returnRate.toString());
			}
		}
	}

	// 导入
	@SuppressWarnings("unchecked")
	@Override
	public OperationResult uploadExcel(MultipartFile file, Boolean lean) {
		OperationResult result = new OperationResult();
		try {
			InputStream inputStream = file.getInputStream();
			List<ProductEntityModel> list = ImportExcelUtil.importExcel(ProductEntityModel.class, inputStream);
			Map<String, Object> taxs = getTaxs();
			// 查找所有的sku
			List<String> strings = productEntityDao.findAllSku();
			CurrencyRateListener.onApplicationEvent();
			if (!CollectionUtil.isNullOrEmpty(list)) {
				CollectionUtil.each(list, new IAction<ProductEntityModel>() {
					@Override
					public void excute(ProductEntityModel model) {
						model.setUpdatedAt(new Date());

						// 成本
						ProductCostModel costModel = new ProductCostModel();
						costModel.setCurrency(model.getCurrency());
						costModel.setPrice(model.getPrice());
						costModel.setTaxRebateRate(model.getTaxRebateRate());
						costModel.setInterestRate(model.getInterestRate());
						costModel.setUpdatedAt(new Date());

						if (strings.contains(model.getSku())) {
							ProductEntityModel entityModel = productEntityDao.queryEntityBySku(model.getSku());
							model.setEntityId(entityModel.getEntityId());
							Long entityId = productCostDao.findIdByParentId(model.getEntityId());
							costModel.setEntityId(entityId);
							if (entityModel.getStatus().equals("1") && lean) {
								model.setStatus("1");
								productEntityDao.update(model);
								productCostDao.update(costModel);
								update(model, costModel, taxs);
							} else if (entityModel.getStatus().equals("2")
									&& ((!lean && entityModel.getUserId().equals(UserUtils.getUserId())) || lean)) {
								model.setStatus("2");
								productEntityDao.update(model);
								productCostDao.update(costModel);
								update(model, costModel, taxs);
							} else {
								throw new RuntimeException("您没有权限修改  " + model.getSku() + " 此sku");
							}
							pricePlanDao.refresh(model.getSku(), entityModel.getUserId());
							lightTaximeterDao.refresh(model.getSku(), entityModel.getUserId());
							smallTaximeterDao.refresh(model.getSku());
						} else {
							// 基本
							model.setStatus("2");
							model.setUserId(UserUtils.getUserId());
							model.setCreatedAt(new Date());
							productEntityDao.add(model);

							// 成本
							costModel.setCreatedAt(new Date());
							productCostDao.add(costModel);

							// 其他
							List<ProductOtherModel> models = model.getProductOtherModels();
							if (!CollectionUtil.isNullOrEmpty(models)) {
								if (models.size() == 9) {
									models = changeOtherModels(model, models, taxs);
									batchAdd(models, productOtherDao);
								} else {
									throw new RuntimeException("产品其他数据不是 9 条");
								}
							} else {
								throw new RuntimeException("产品其他数据为空");
							}
							pricePlanDao.refresh(model.getSku(), UserUtils.getUserId());
							lightTaximeterDao.refresh(model.getSku(), UserUtils.getUserId());
							smallTaximeterDao.refresh(model.getSku());
						}
					}
				});
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	private List<ProductOtherModel> changeOtherModels(ProductEntityModel model, List<ProductOtherModel> models,
			Map<String, Object> taxs) {
		for (ProductOtherModel productOtherModel : models) {
			String currency = model.getCurrency();
			TaxModel taxModel = (TaxModel) taxs.get(productOtherModel.getCountryId());
			if (currency.equals("USD")) {
				productOtherModel
						.setClearPrice(new Double(df.format(model.getPrice() * taxModel.getClearCoefficient())));
			} else if (currency.equals("RMB") || currency.equals("CNY")) {
				productOtherModel.setClearPrice(new Double(df.format(model.getPrice() / 1.17
						/ CurrencyRateCache.getCurrencyRate("RMB") * taxModel.getClearCoefficient())));
			}
		}
		return models;
	}

	private void update(ProductEntityModel model, ProductCostModel costModel, Map<String, Object> taxs) {
		List<ProductOtherModel> models = model.getProductOtherModels();
		if (!CollectionUtil.isNullOrEmpty(models)) {
			models = changeOtherModels(model, models, taxs);
			List<ProductOtherModel> list = productOtherDao.findByParentId(model.getEntityId());
			if (!CollectionUtil.isNullOrEmpty(list)) {
				for (ProductOtherModel otherModel : models) {
					for (ProductOtherModel productOtherModel : list) {
						if (otherModel.getCountryId().equals(productOtherModel.getCountryId())) {
							otherModel.setEntityId(productOtherModel.getEntityId());
							otherModel.setCreatedAt(productOtherModel.getCreatedAt());
							productOtherDao.update(otherModel);
							break;
						}
					}
				}
			} else {
				for (ProductOtherModel productOtherModel : models) {
					productOtherModel.setParentId(model.getEntityId());
					productOtherDao.add(productOtherModel);
				}
			}

		} else {
			throw new RuntimeException("产品其他数据为空");
		}
	}
	
	private List<ProductEntityModel> findProduct(List<ProductEntityModel> source, String matchSku) {
		return StringUtils.isBlank(matchSku) ? null : CollectionUtil.searchList(source, new IFunction<ProductEntityModel, Boolean>() {
			@Override
			public Boolean excute(ProductEntityModel product) {
				return matchSku.equals(product.getSku());
			}
		});
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public OperationResult importProductPrice(MultipartFile file, Boolean lean) {
		OperationResult result = new OperationResult();
		try {
			List<ProductPriceModel> source = ImportExcelUtil.importExcel(ProductPriceModel.class, file.getInputStream());
			int successCount = 0;
			if(!CollectionUtil.isNullOrEmpty(source)) {
				List<ProductEntityModel> products = productEntityDao.findAllActivated(); //查询所有已激活的产品
				List<ProductEntityModel> sameProducts = null;
				List<ProductCostModel> costs = null;
				List<ProductPriceModel> updateds = new ArrayList<ProductPriceModel>();
				for (ProductPriceModel data : source) {
					try {
						boolean invalid = StringUtils.isBlank(data.getSku()) || null == data.getPrice(); 
						boolean repeat = !invalid && null != CollectionUtil.search(updateds, new IFunction<ProductPriceModel, Boolean>() {
							@Override
							public Boolean excute(ProductPriceModel ppm) {
								return data.getSku().equals(ppm.getSku()) && data.getPrice().doubleValue() == ppm.getPrice().doubleValue();
							}
						});
						
						if(repeat) {
							successCount++;
						} else {
							sameProducts = invalid || repeat ? null : this.findProduct(products, data.getSku());
							if(!CollectionUtil.isNullOrEmpty(sameProducts)) {
								boolean successFlag = false;
								for (ProductEntityModel temp : sameProducts) {
									costs = this.productCostDao.findByParentId(temp.getEntityId());
									if(!CollectionUtil.isNullOrEmpty(costs)) {
										for (ProductCostModel cost : costs) {
											cost.setPrice(data.getPrice());
											cost.setUpdatedBy(this.getCurentUserId());
											if(StringUtils.isNotBlank(data.getCurrency())) {
												cost.setCurrency(data.getCurrency());	
											}
											this.productCostDao.updateByParentId(cost);
											try {
												this.productCostDao.recordCostChangeLog(cost);
											} catch (Exception e) {
												logger.error(e.getMessage(), e);
											}
											successFlag = true;
										}
									}
								}
								if(successFlag) {
									successCount++;
								}
								updateds.add(data);
							}
						}
					} catch (Exception e) {
						logger.error("导入产品（sku=" + data.getSku() + "）价格是失败：" + e.getMessage(), e);
					}
				}
			}
			int totalCount = null == source ? 0 : source.size();
			result.setDescription("导入产品价格详情：总行数=" + totalCount + "；成功行数=" +  successCount + "；失败行数=" + (totalCount - successCount) + "。");
		} catch (Exception e) {
			result.setErrorCode(1);
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	private Long getCurentUserId() {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser.getSession().getAttribute("user")) {
			return ((UserModel) currentUser.getSession().getAttribute("user")).getId();
		}
		return null;
	}

	@Override
	public OperationResult getSkuDisInfo(String sku, String platform) {
		List<SKUDiscontinueModel> skuDises = productEntityDao.getSkuDisInfoBySKUAndPlatform(sku,platform);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sku", sku);
		map.put("platform", platform);
		//有些国家无设置数据，默认初始化数据
		map.put("US", -1);
		map.put("CA", -1);
		map.put("JP", -1);
		map.put("DE", -1);
		map.put("UK", -1);
		map.put("FR", -1);
		map.put("IT", -1);
		map.put("ES", -1);
		map.put("AU", -1);
		
		for(SKUDiscontinueModel sd : skuDises){
			map.put(sd.getCountryId(), sd.getIsDis());
		}
		OperationResult or = new OperationResult();
		or.setData(map);
		return or;
	}

	@Override
	public OperationResult saveSkuDisInfo(Map<String, Object> map) {
		List<Map<String,Object>> list = (List<Map<String,Object>>)map.get("skuDisInfo");
		for(Map<String,Object> sd : list){
			sd.put("userId", getCurentUserId());
			if(productEntityDao.skuDisInfoIsExist(sd)==0){
				productEntityDao.addSkuDisInfo(sd);
			}else{
				productEntityDao.updateSkuDisInfo(sd);
			}
		}
		return new OperationResult();
	}

	@Override
	public OperationResult skuExist(String sku) {
		OperationResult or = new OperationResult();
		or.setData(productEntityDao.skuExist(sku));
		return or;
	}
}
