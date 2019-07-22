package com.it.ocs.cal.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.cal.constant.CalTypeEnum;
import com.it.ocs.cal.dao.ICurrencyRateDao;
import com.it.ocs.cal.dao.IHandlingChargesDao;
import com.it.ocs.cal.dao.ILightEbayCustomerDao;
import com.it.ocs.cal.dao.ILightEbayRateDao;
import com.it.ocs.cal.dao.ILightEbaySundryDao;
import com.it.ocs.cal.dao.ILightEbayTaxDao;
import com.it.ocs.cal.dao.ILightTaximeterDao;
import com.it.ocs.cal.excel.LightEbayTaximeterExcelExport;
import com.it.ocs.cal.excel.LightEbayTaximeterExcelImport;
import com.it.ocs.cal.excel.utils.LeExcelUtils;
import com.it.ocs.cal.excel.vo.LECalculateExcelVO;
import com.it.ocs.cal.excel.vo.LECustomerVo;
import com.it.ocs.cal.model.CurrencyRateModel;
import com.it.ocs.cal.model.LightEbayCustomerModel;
import com.it.ocs.cal.model.LightEbayRateModel;
import com.it.ocs.cal.model.LightEbaySundryModel;
import com.it.ocs.cal.model.LightHandlingChargesModel;
import com.it.ocs.cal.model.LightTaximeterModel;
import com.it.ocs.cal.service.ILightTaximeterService;
import com.it.ocs.cal.service.impl.support.CalculateErrorMsgSupport;
import com.it.ocs.cal.service.impl.support.LightTaximeterSupport;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.LightTaximeterVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.sys.vo.PermissionVO;

@Service
@Transactional
public class LightTaximeterService implements ILightTaximeterService {

	@Autowired
	private ILightTaximeterDao lightTaximeterDao;

	@Autowired
	private ILightEbayTaxDao lightEbayTaxDao;

	@Autowired
	private IHandlingChargesDao handlingChargesDao;

	@Autowired
	private ILightEbaySundryDao lightEbaySundryDao;

	@Autowired
	private ILightEbayCustomerDao lightEbayCustomerDao;

	@Autowired
	private ILightEbayRateDao lightEbayRateDao;

	@Autowired
	private ICurrencyRateDao currencyRateDao;

	private Map<Long, List<?>> map = new HashMap<>();

	private static Map<String, String> NAME_MAPPING = new HashMap<>();
	static {
		NAME_MAPPING.put("template", "价格推算模板");
		NAME_MAPPING.put("template_variant", "价格推算变体模板");
		NAME_MAPPING.put("count", "大客户价格推算模板");
		NAME_MAPPING.put("variant", "保本销量变体模板");
	}

	@Override
	public ResponseResult<LightTaximeterVo> findAll(RequestParam param) {

		ResponseResult<LightTaximeterVo> result = new ResponseResult<>();

		LightTaximeterModel model = BeanConvertUtil.mapToObject(param.getParam(), LightTaximeterModel.class);

		List<String> countrys = getCountrys(model);

		PageHelper.startPage(param.getPage(), param.getRows());

		List<LightTaximeterModel> list = lightTaximeterDao.queryByCountry(model, countrys,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<LightTaximeterModel> pageInfo = new PageInfo<>(list);

		List<LightTaximeterModel> models = pageInfo.getList();

		LightTaximeterSupport.getList(pageInfo.getList());

		result.setRows(CollectionUtil.beansConvert(models, LightTaximeterVo.class));

		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Override
	public OperationResult refresh() {
		OperationResult result = new OperationResult();
		try {
			List<Map<String, Object>> list = lightEbayTaxDao.findByUserId(-1L);
			CollectionUtil.each(list, new IAction<Map<String, Object>>() {
				@Override
				public void excute(Map<String, Object> map) {
					lightTaximeterDao.refresh(map.get("SKU").toString(), new Long(map.get("USERID").toString()));
				}
			});

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	@Override
	public List<String> getCountrys(LightTaximeterModel taximeterModel) {
		List<String> result = null;
		if (StringUtils.isBlank(taximeterModel.getCountry())) {
			PermissionVO permission = LightTaximeterSupport.getPermission();
			result = LightTaximeterSupport.getCountrys(permission, taximeterModel.getPlatform());
		} else {
			result = Lists.newArrayList();
			result.add(taximeterModel.getCountry());
		}
		return result;
	}

	@Override
	public List<Map<String, String>> findAllSku() {
		List<String> strings = lightTaximeterDao.findAllSku();
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

	// 反推
	@Override
	public LightTaximeterVo editLightTaximeter(LightTaximeterVo lightTaximeter) {
		// 根据id查找数据
		LightTaximeterModel model = lightTaximeterDao.getById(lightTaximeter.getEntityId());
		if (!model.getProfitRateAction().equals(lightTaximeter.getProfitRateAction())) {
			// 修改了利润系数，反推最终售价
			model.setProfitRate(model.getProfitRate() * lightTaximeter.getProfitRateAction());
			lightTaximeter.setFinalPrice(LightTaximeterSupport.getFinalPrice(model));
		} else {
			// 修改了最终售价，反推利润系数
			model.setFinalPrice(lightTaximeter.getFinalPrice());
			lightTaximeter.setProfitRateAction(LightTaximeterSupport.getProfitRateAction(model));
		}
		lightTaximeter.setUpdatedAt(new Date());
		// 更新数据
		lightTaximeterDao.update(lightTaximeter);
		return lightTaximeter;
	}

	@Override
	public Double changeFinalPrice(LECalculateExcelVO vo) {
		LightTaximeterModel model = lightTaximeterDao.queryByParam(new LightTaximeterModel(vo.getPlatform(),
				vo.getCountryId(), vo.getSku(), vo.getShippingType().toLowerCase(), new Short(vo.getTransactionMode()),
				new Short(vo.getIsCostOf()), new Short(vo.getIsStorageCharges())));
		return model == null ? 0 : LightTaximeterSupport.getFinalPrice(model);
	}

	@Override
	public List<Double> calculateAllFinalPrice(LECalculateExcelVO vo) {
		List<Double> result = Lists.newArrayList();
		String[] skus = vo.getSku().split(",");
		String[] shippingTypes = vo.getShippingType().split(",");
		for (int i = 0; i < skus.length; i++) {
			LightTaximeterModel model = lightTaximeterDao.queryByParam(new LightTaximeterModel(vo.getPlatform(),
					vo.getCountryId(), skus[i], shippingTypes[i].toLowerCase(), new Short(vo.getTransactionMode()),
					new Short(vo.getIsCostOf()), new Short(vo.getIsStorageCharges())));
			if (model == null) {
				result.add(0d);
			} else {
				result.add(LightTaximeterSupport.getFinalPrice(model));
			}
		}
		return result;
	}

	@Override
	public Double calculateFinalPrice(LECalculateExcelVO vo) {
		return calculate(vo, CalTypeEnum.PRICE);
	}

	@Override
	public Double calculateProfitRateAction(LECalculateExcelVO vo) {
		return calculate(vo, CalTypeEnum.PROFITRATE);
	}

	private Double calculate(LECalculateExcelVO vo, CalTypeEnum type) {
		Double result = 0d;
		List<LightTaximeterModel> list = Lists.newArrayList();
		String[] skus = vo.getSku().split(",");
		for (int i = 0; i < skus.length; i++) {
			LightTaximeterModel model = lightTaximeterDao
					.queryByParam(new LightTaximeterModel(vo.getPlatform(), vo.getCountryId(), skus[i],
							vo.getShippingType().split(",")[i].toLowerCase(), new Short(vo.getTransactionMode()),
							new Short(vo.getIsCostOf()), new Short(vo.getIsStorageCharges())));
			if (model == null) {
				continue;
			}
			model.setQty(new Long(vo.getQty().split(",")[i]));
			list.add(model);
		}
		if (CollectionUtil.isNullOrEmpty(list)) {
			return result;
		}
		List<LightHandlingChargesModel> models = handlingChargesDao.query(null, null, null);
		LightEbaySundryModel sundryModel = lightEbaySundryDao.queryByCountry("UK");
		LightTaximeterModel lightTaximeterModel = list.get(0);
		lightTaximeterModel.setFinalCost(LightTaximeterSupport.getFinalCost(vo, list, models, sundryModel));
		switch (type) {
		case PRICE:
			lightTaximeterModel.setProfitRate(vo.getProfitRateAction() * lightTaximeterModel.getProfitRate());
			result = LightTaximeterSupport.getFinalPrice(lightTaximeterModel);
			break;
		case PROFITRATE:
			lightTaximeterModel.setFinalPrice(vo.getFinalPrice());
			result = LightTaximeterSupport.getProfitRateAction(lightTaximeterModel);
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public OperationResult recover() {
		OperationResult result = new OperationResult();
		try {
			List<LightTaximeterModel> list = lightTaximeterDao.findAllLess();
			CollectionUtil.each(list, new IAction<LightTaximeterModel>() {
				@Override
				public void excute(LightTaximeterModel model) {
					model.setProfitRateAction(1d);
					model.setFinalPrice(LightTaximeterSupport.getFinalPrice(model));
					model.setUpdatedAt(new Date());
					lightTaximeterDao.update(model);
				}
			});

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	@Override
	public void reckonExport(HttpServletRequest request, HttpServletResponse response) {
		List lvs = map.get(UserUtils.getUserId());
		List<Map<String, Object>> datas = Lists.newArrayList();
		CollectionUtil.each(lvs, new IAction<Object>() {
			@Override
			public void excute(Object object) {
				try {
					Map<String, Object> data = org.apache.commons.beanutils.BeanUtils.describe(object);
					if (null != data && !data.isEmpty()) {
						datas.add(data);
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		});
		if (!CollectionUtil.isNullOrEmpty(datas)) {
			Class clazz = lvs.get(0).getClass();
			request.setAttribute("clazz",
					clazz.equals(LECustomerVo.class) ? LECustomerVo.class : LECalculateExcelVO.class);
			request.setAttribute("data", datas);
			request.setAttribute("fileName", "计算数据" + Utils.getFileName() + ".xlsx");
			LightEbayTaximeterExcelExport.getInstance().excute(request, response);
		}
	}

	private boolean isExportTemplate(String exportInfo) {
		return !(exportInfo.contains("{") || exportInfo.contains("}"));
	}

	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template) {
		if (isExportTemplate(template)) {
			// 输出的文件名称
			String fileName = NAME_MAPPING.get(template) + ".xlsx";
			List<Map<String, Object>> datas = Lists.newArrayList();
			datas.add(LeExcelUtils.constructTemplateObj(template));
			LightEbayTaximeterExcelExport leExport = LightEbayTaximeterExcelExport.getInstance();
			request.setAttribute("clazz", template.equals("count") ? LECustomerVo.class : LECalculateExcelVO.class);
			request.setAttribute("fileName", fileName);
			request.setAttribute("data", datas);
			leExport.excute(request, response);
		}
	}

	private OperationResult handleCalFile(MultipartFile file, CalTypeEnum type) {
		OperationResult result = new OperationResult();
		try {
			PermissionVO permission = LightTaximeterSupport.getPermission();
			if (permission == null) {
				result.setData("你没有权限,请联系管理员");
			} else {
				List<LECalculateExcelVO> list = LightEbayTaximeterExcelImport.convertBean(file.getInputStream(),
						new LECalculateExcelVO());
				CalculateErrorMsgSupport.validata_data(list, "excel中数据为空或解析excel失败!");

				Map<String, List<String>> platformCountrys = Maps.newHashMap();
				List<String> platforms = LightTaximeterSupport.getPlatForms(permission);

				List<LightTaximeterModel> modelList = Lists.newArrayList();
				CollectionUtil.each(platforms, new IAction<String>() {
					@Override
					public void excute(String platform) {
						List<String> countrys = LightTaximeterSupport.getCountrys(permission, platform);
						platformCountrys.put(platform, countrys);
						modelList.addAll(lightTaximeterDao.queryByCountry(new LightTaximeterModel(platform), countrys,
								null, null));
					}
				});
				CalculateErrorMsgSupport.validata_data(modelList, "计价器数据库表中无数据!");

				Map<String, LightTaximeterModel> maps = Maps.newHashMap();
				CollectionUtil.each(modelList, new IAction<LightTaximeterModel>() {
					@Override
					public void excute(LightTaximeterModel model) {
						maps.put(model.getPlatform() + model.getCountry() + model.getSku() + model.getShippingType()
								+ model.getTransactionMode() + model.getIsCostOf() + model.getIsStorageCharges(),
								model);
					}
				});
				List<LightHandlingChargesModel> models = handlingChargesDao.query(null, null, null);
				LightEbaySundryModel sundryModel = lightEbaySundryDao.queryByCountry("UK");
				StringBuffer errorMsg = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					LightTaximeterSupport.handleCal(type, i + 2, errorMsg, list.get(i), modelList, platformCountrys,
							maps, models, sundryModel);
				}
				if (!StringUtils.isEmpty(errorMsg.toString())) {
					result.setData(errorMsg.toString());
				}
				map.put(UserUtils.getUserId(), list);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public OperationResult priceCalculate(MultipartFile file) {
		return handleCalFile(file, CalTypeEnum.PRICE);
	}

	@Override
	public OperationResult profitRatCalculate(MultipartFile file) {
		return handleCalFile(file, CalTypeEnum.PROFITRATE);
	}

	@Override
	public OperationResult priceVariantCalculate(MultipartFile file) {
		return handleCalFile(file, CalTypeEnum.PRICEVARIANT);
	}

	@Override
	public OperationResult customerPrice(MultipartFile file) {
		return customer(file, CalTypeEnum.PRICE);
	}

	@Override
	public OperationResult customerProfitRate(MultipartFile file) {

		return customer(file, CalTypeEnum.PROFITRATE);
	}

	private OperationResult customer(MultipartFile file, CalTypeEnum type) {
		OperationResult result = new OperationResult();
		try {
			List<LECustomerVo> list = LightEbayTaximeterExcelImport.convertBean(file.getInputStream(),
					new LECustomerVo());
			CalculateErrorMsgSupport.validata_data(list, "excel中数据为空或解析excel失败!");
			List<Map<String, Object>> data = lightTaximeterDao.queryCustomer();
			CalculateErrorMsgSupport.validata_data(data, "数据库表中无数据!");
			List<LightEbayCustomerModel> customers = lightEbayCustomerDao.query(null, null, null);
			CalculateErrorMsgSupport.validata_data(customers, "物流规则表中无数据!");
			List<LightEbayRateModel> rateModels = lightEbayRateDao.query(null, null, null);
			StringBuffer errorMsg = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				LightTaximeterSupport.customerCal(type, i + 2, errorMsg, list.get(i), customers, data, rateModels);
			}
			if (!StringUtils.isEmpty(errorMsg.toString())) {
				result.setData(errorMsg.toString());
			}
			map.put(UserUtils.getUserId(), list);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<String> lePricePlanTest(LECalculateExcelVO vo) {
		List<String> result = Lists.newArrayList();
		List<Map<String, Object>> data = lightTaximeterDao.getPriceTestData(vo);
		List<LightHandlingChargesModel> models = handlingChargesDao.query(null, null, null);
		LightEbaySundryModel sundryModel = lightEbaySundryDao.queryByCountry("UK");
		LightTaximeterSupport.getTestMessages(vo, data, models, sundryModel, result);
		return result;
	}

	@Override
	public List<Map<String, Object>> fobexwReverse(Map<String, Object> map) {
		Map<String, Object> param = (Map<String, Object>) map.get("row");
		String country = param.get("country").toString();
		List<Map<String, Object>> data = lightTaximeterDao.fobexw(country);
		Map<String, Map<String, Object>> datas = Maps.newHashMap();
		CollectionUtil.each(data, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> hash) {
				datas.put(hash.get("SKU").toString(), hash);
			}
		});
		Long sumQty = 0L;
		Double sumVolume = 0d;
		List<Map<String, Object>> rows = (List<Map<String, Object>>) param.get("rows");
		for (Map<String, Object> row : rows) {
			Long qty = new Long(row.get("qty").toString());
			sumQty += qty;
			sumVolume += new Double(datas.get(row.get("sku").toString()).get("VOLUME").toString()) * qty;
		}
		List<CurrencyRateModel> list = currencyRateDao.findByTemplate();
		Map<String, Double> rates = new HashMap<>();
		for (CurrencyRateModel model : list) {
			if (!rates.containsKey(model.getCurrencyCode())) {
				rates.put(model.getCurrencyCode(), model.getCurrencyRate());
			}
		}
		Boolean isRefundDuty = param.get("refundDuty").toString().equals("0") ? false : true;
		return LightTaximeterSupport.getFobexw(param,rates,rows,sumQty,sumVolume,datas,isRefundDuty);
	}
}
