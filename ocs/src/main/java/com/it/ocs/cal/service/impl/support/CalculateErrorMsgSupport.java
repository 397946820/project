package com.it.ocs.cal.service.impl.support;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import com.it.ocs.cal.excel.vo.LECalculateExcelVO;
import com.it.ocs.cal.excel.vo.LECustomerVo;
import com.it.ocs.cal.model.LightEbayCustomerModel;
import com.it.ocs.cal.model.LightTaximeterModel;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.salesStatistics.utils.ReflectUtils;

public class CalculateErrorMsgSupport {
	private static final String PLATFORM_NOTEXIST_MSG = "第{0}行的平台:{1} 不存在<br/>";
	private static final String SKU_NOTEXIST_MSG = "第{0}行的SKU:{1} 不存在<br/>";
	private static final String COUNTRY_NOTEXIST_MSG = "第{0}行的国家:{1} 不存在<br/>";
	private static final String SHIPPINGTYPE_NOTEXIST_MSG = "第{0}行的运输方式:{1} 不存在<br/>";
	private static final String QTY_SKU_SHIPPINGTYPE_UNEQUAL_MSG = "第{0}行的捆绑数量与SKU与运输方式的个数不一致<br/>";
	private static final String ISCOSTOF_NOTEXIST_MSG = "第{0}行的是否用占用费比不存在,只能是0或者1<br/>";
	private static final String ISSTORAGECHARGES_NOTEXIST_MSG = "第{0}行的是否用仓租费不存在,只能是0或者1<br/>";
	private static final String TRANSACTIONMODE_NOTEXIST_MSG = "第{0}行的交易模式不存在,只能是0或者1<br/>";
	private static final String BUNDLEQTY_NOTEXIST_MSG = "第{0}行的数量为空或者0<br/>";
	private static final String PROFITRATE_NOTEXIST_MSG = "第{0}行的利润系数为空<br/>";
	private static final String PRICE_NOTEXIST_MSG = "第{0}行的建议售价为空或者0<br/>";
	private static final String DATA_NOTEXIST_MSG = "第{0}行的SKU:{1},国家:{2} 运输方式:{3} 交易方式:{4} 是否用占用费比:{5} 是否用仓租费:{6},在计价器数据中不存在<br/>";

	private static final String REGION_NOTEXIST_MSG = "第{0}行的地区:{1} 不存在<br/>";
	private static final String COUNTRY_SKU_NOTEXIST_MSG = "第{0}行的国家:{1} SKU:{2},在数据库不存在<br/>";
	private static final String COUNTRY_REGION_SHIPPINGTYPE_NOTEXIST_MSG = "第{0}行的国家:{1} 地区:{2} 运输方式:{3},在物流规则中不存在<br/>";

	public static void validata_data(List<?> datas, String errorMsg) {
		if (CollectionUtil.isNullOrEmpty(datas)) {
			throw new RuntimeException(errorMsg);
		}
	}

	public static boolean canCalFinalPrice(int rowIndex, StringBuffer errorMsgBuffer, LECalculateExcelVO excelVO,
			List<LightTaximeterModel> modelList, Map<String, List<String>> platformCountrys,
			Map<String, LightTaximeterModel> maps) {

		return notExist(rowIndex, errorMsgBuffer, excelVO, modelList, platformCountrys, maps)
				&& profitRateNotExist(excelVO, errorMsgBuffer, rowIndex);
	}

	public static boolean canCalProfitRate(int rowIndex, StringBuffer errorMsgBuffer, LECalculateExcelVO excelVO,
			List<LightTaximeterModel> modelList, Map<String, List<String>> platformCountrys,
			Map<String, LightTaximeterModel> maps) {

		return notExist(rowIndex, errorMsgBuffer, excelVO, modelList, platformCountrys, maps)
				&& priceNotExist(excelVO, errorMsgBuffer, rowIndex);
	}

	private static boolean notExist(int rowIndex, StringBuffer errorMsgBuffer, LECalculateExcelVO excelVO,
			List<LightTaximeterModel> modelList, Map<String, List<String>> platformCountrys,
			Map<String, LightTaximeterModel> maps) {
		if (!platformCountrys.keySet()
				.contains(excelVO.getPlatform() == null ? "" : excelVO.getPlatform().toLowerCase())) {
			errorMsgBuffer.append(MessageFormat.format(PLATFORM_NOTEXIST_MSG, rowIndex, excelVO.getPlatform()));
			return false;
		} else {
			List<String> list = platformCountrys.get(excelVO.getPlatform().toLowerCase());
			if (!list.contains(excelVO.getCountryId() == null ? "" : excelVO.getCountryId().toUpperCase())) {
				errorMsgBuffer.append(MessageFormat.format(COUNTRY_NOTEXIST_MSG, rowIndex, excelVO.getCountryId()));
				return false;
			} else {
				return (isExist(rowIndex, errorMsgBuffer, excelVO)) ? false
						: (!qtySkuShippingTypeUnequal(rowIndex, errorMsgBuffer, excelVO, modelList, maps)) ? false
								: true;
			}
		}
	}

	private static boolean isExist(int rowIndex, StringBuffer errorMsgBuffer, LECalculateExcelVO excelVO) {
		if (StringUtils.isBlank(excelVO.getIsCostOf())
				|| !(new Double(excelVO.getIsCostOf()) == 0 || new Double(excelVO.getIsCostOf()) == 1)) {
			errorMsgBuffer.append(MessageFormat.format(ISCOSTOF_NOTEXIST_MSG, rowIndex));
			return true;
		}
		if (StringUtils.isBlank(excelVO.getIsStorageCharges()) || !(new Double(excelVO.getIsStorageCharges()) == 0
				|| new Double(excelVO.getIsStorageCharges()) == 1)) {
			errorMsgBuffer.append(MessageFormat.format(ISSTORAGECHARGES_NOTEXIST_MSG, rowIndex));
			return true;
		}
		if (StringUtils.isBlank(excelVO.getTransactionMode())
				|| !(new Double(excelVO.getTransactionMode()) == 0 || new Double(excelVO.getTransactionMode()) == 1)) {
			errorMsgBuffer.append(MessageFormat.format(TRANSACTIONMODE_NOTEXIST_MSG, rowIndex));
			return true;
		}
		return false;
	}

	private static boolean qtySkuShippingTypeUnequal(int rowIndex, StringBuffer errorMsgBuffer,
			LECalculateExcelVO excelVO, List<LightTaximeterModel> modelList, Map<String, LightTaximeterModel> maps) {
		if (nullOrEmpty(excelVO, errorMsgBuffer, rowIndex)) {
			return false;
		} else {
			if (!(excelVO.getSku().split(",").length == excelVO.getShippingType().split(",").length
					&& excelVO.getShippingType().split(",").length == excelVO.getQty().split(",").length)) {
				errorMsgBuffer.append(MessageFormat.format(QTY_SKU_SHIPPINGTYPE_UNEQUAL_MSG, rowIndex));
				return false;
			} else {
				return (skuNotExist(excelVO, modelList, errorMsgBuffer, rowIndex)) ? false
						: (shippingTypeNotExist(excelVO, modelList, errorMsgBuffer, rowIndex, maps)) ? false
								: (bundleQtyNotExist(excelVO, modelList, errorMsgBuffer, rowIndex)) ? false : true;

			}
		}
	}

	private static <T> boolean nullOrEmpty(T t, StringBuffer errorMsgBuffer, int rowIndex) {
		try {
			String sku = ReflectUtils.getValue(t, "sku").toString();
			String shippingType = ReflectUtils.getValue(t, "shippingtype").toString().toLowerCase();
			String qty = ReflectUtils.getValue(t, "qty").toString().toLowerCase();
			if (StringUtils.isBlank(sku)) {
				errorMsgBuffer.append(MessageFormat.format(SKU_NOTEXIST_MSG, rowIndex, sku));
				return true;
			}
			if (StringUtils.isBlank(shippingType)) {
				errorMsgBuffer.append(MessageFormat.format(SHIPPINGTYPE_NOTEXIST_MSG, rowIndex, shippingType));
				return true;
			}
			if (StringUtils.isBlank(qty)) {
				errorMsgBuffer.append(MessageFormat.format(BUNDLEQTY_NOTEXIST_MSG, rowIndex));
				return true;
			} else {
				if(t.getClass().equals(LECustomerVo.class) && new Double(qty) == 0) {
					errorMsgBuffer.append(MessageFormat.format(BUNDLEQTY_NOTEXIST_MSG, rowIndex));
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private static boolean priceNotExist(LECalculateExcelVO excelVO, StringBuffer errorMsgBuffer, int rowIndex) {
		if ((StringUtils.isBlank(excelVO.getFinalPrice1()) || new Double(excelVO.getFinalPrice1()) == 0)
				&& (StringUtils.isBlank(excelVO.getFinalPrice2()) || new Double(excelVO.getFinalPrice2()) == 0)
				&& (StringUtils.isBlank(excelVO.getFinalPrice3()) || new Double(excelVO.getFinalPrice3()) == 0)) {
			errorMsgBuffer.append(MessageFormat.format(PRICE_NOTEXIST_MSG, rowIndex, excelVO.getSku()));
			return false;
		}
		return true;

	}

	private static boolean profitRateNotExist(LECalculateExcelVO excelVO, StringBuffer errorMsgBuffer, int rowIndex) {
		if (StringUtils.isBlank(excelVO.getProfitRateAction1()) && StringUtils.isBlank(excelVO.getProfitRateAction2())
				&& StringUtils.isBlank(excelVO.getProfitRateAction3())) {
			errorMsgBuffer.append(MessageFormat.format(PROFITRATE_NOTEXIST_MSG, rowIndex, excelVO.getSku()));
			return false;
		}
		return true;
	}

	private static boolean skuNotExist(LECalculateExcelVO excelVO, List<LightTaximeterModel> modelList,
			StringBuffer errorMsgBuffer, int rowIndex) {
		StringBuffer buffer = new StringBuffer();
		List<String> skus = Arrays.asList(excelVO.getSku().split(","));
		for (String sku : skus) {
			LightTaximeterModel model = CollectionUtil.search(modelList, new IFunction<LightTaximeterModel, Boolean>() {
				@Override
				public Boolean excute(LightTaximeterModel lightModel) {
					return lightModel.getSku().equals(sku)
							&& lightModel.getPlatform().equals(excelVO.getPlatform().toLowerCase())
							&& lightModel.getCountry().equals(excelVO.getCountryId().toUpperCase());
				}
			});
			if (null == model) {
				buffer.append(MessageFormat.format(SKU_NOTEXIST_MSG, rowIndex, sku));
			}
		}
		errorMsgBuffer.append(buffer.toString());
		return StringUtils.isNotBlank(buffer.toString());

	}

	private static boolean shippingTypeNotExist(LECalculateExcelVO excelVO, List<LightTaximeterModel> modelList,
			StringBuffer errorMsgBuffer, int rowIndex, Map<String, LightTaximeterModel> maps) {
		StringBuffer buffer = new StringBuffer();
		List<String> shippingTypes = Arrays.asList(excelVO.getShippingType().split(","));
		List<String> skus = Arrays.asList(excelVO.getSku().split(","));
		for (int i = 0; i < shippingTypes.size(); i++) {
			String shippingType = shippingTypes.get(i);
			String sku = skus.get(i);
			LightTaximeterModel model = maps.get(excelVO.getPlatform().toLowerCase()
					+ excelVO.getCountryId().toUpperCase() + sku + shippingType.toLowerCase()
					+ excelVO.getTransactionMode() + excelVO.getIsCostOf() + excelVO.getIsStorageCharges());
			if (null == model) {
				buffer.append(
						MessageFormat.format(DATA_NOTEXIST_MSG, rowIndex, sku, excelVO.getCountryId(), shippingType,
								excelVO.getTransactionMode(), excelVO.getIsCostOf(), excelVO.getIsStorageCharges()));
			}
		}
		errorMsgBuffer.append(buffer.toString());
		return StringUtils.isNotBlank(buffer.toString());
	}

	private static boolean bundleQtyNotExist(LECalculateExcelVO excelVO, List<LightTaximeterModel> modelList,
			StringBuffer errorMsgBuffer, int rowIndex) {
		String[] qtys = excelVO.getQty().split(",");
		for (String qty : qtys) {
			if (new Double(qty) == 0) {
				errorMsgBuffer.append(MessageFormat.format(BUNDLEQTY_NOTEXIST_MSG, rowIndex, excelVO.getQty()));
				return true;
			}
		}
		return false;
	}

	public static boolean customerFinalPrice(int rowIndex, StringBuffer errorMsg, LECustomerVo lv,
			List<LightEbayCustomerModel> customers, List<Map<String, Object>> data) {

		return customerExist(rowIndex, errorMsg, lv, customers, data) && profitRateCustomer(lv, rowIndex, errorMsg);
	}

	public static boolean customerProfitRate(int rowIndex, StringBuffer errorMsg, LECustomerVo lv,
			List<LightEbayCustomerModel> customers, List<Map<String, Object>> data) {

		return customerExist(rowIndex, errorMsg, lv, customers, data) && finalPriceCustomer(lv, rowIndex, errorMsg);
	}

	private static boolean profitRateCustomer(LECustomerVo lv, int rowIndex, StringBuffer errorMsg) {
		if (StringUtils.isBlank(lv.getProfitRateAction())) {
			errorMsg.append(MessageFormat.format(PROFITRATE_NOTEXIST_MSG, rowIndex));
			return false;
		}
		return true;
	}

	private static boolean finalPriceCustomer(LECustomerVo lv, int rowIndex, StringBuffer errorMsg) {
		if (StringUtils.isBlank(lv.getFinalPrice()) || new Double(lv.getFinalPrice()) == 0) {
			errorMsg.append(MessageFormat.format(PRICE_NOTEXIST_MSG, rowIndex));
			return false;
		}
		return true;
	}

	private static boolean customerExist(int rowIndex, StringBuffer errorMsg, LECustomerVo lv,
			List<LightEbayCustomerModel> customers, List<Map<String, Object>> data) {
		return nullCountryOrRegion(rowIndex, errorMsg, lv, customers) ? false
				: nullOrEmpty(lv, errorMsg, rowIndex) ? false
						: customerSku(rowIndex, errorMsg, lv, data) ? false
								: customerShippingType(rowIndex, errorMsg, lv, customers) ? false : true;
	}

	private static boolean customerShippingType(int rowIndex, StringBuffer errorMsg, LECustomerVo lv,
			List<LightEbayCustomerModel> customers) {
		LightEbayCustomerModel model = CollectionUtil.search(customers,
				new IFunction<LightEbayCustomerModel, Boolean>() {
					@Override
					public Boolean excute(LightEbayCustomerModel model) {
						return model.getCountry().equals(lv.getCountry().toUpperCase())
								&& model.getRegion().equals(lv.getRegion().toUpperCase())
								&& model.getShippingType().equals(lv.getShippingType().toLowerCase());
					}
				});
		if (model == null) {
			errorMsg.append(MessageFormat.format(COUNTRY_REGION_SHIPPINGTYPE_NOTEXIST_MSG, rowIndex, lv.getCountry(),
					lv.getRegion(), lv.getShippingType()));
		}
		return model == null;
	}
	public static class MapSearch implements IFunction<Map<String,Object>, Boolean>{
		LECustomerVo lv;
		private static MapSearch instance = null;
		@Override
		public Boolean excute(Map<String, Object> map) {
			return map.get("SKU").toString().equals(lv.getSku())
					&& map.get("COUNTRY").toString().equals(lv.getCountry().toUpperCase());
		}
		private MapSearch() {}
		public static MapSearch getInstance(LECustomerVo lv) {
			if (null == instance) {
				instance = new MapSearch();
			}
			return instance;
		}
		
	}
	private static boolean customerSku(int rowIndex, StringBuffer errorMsg, LECustomerVo lv,
			List<Map<String, Object>> data) {
		Map<String, Object> map = CollectionUtil.search(data, MapSearch.getInstance(lv));
		if (map == null) {
			errorMsg.append(MessageFormat.format(COUNTRY_SKU_NOTEXIST_MSG, rowIndex, lv.getCountry(), lv.getSku()));
		}
		return map == null;
	}

	private static boolean nullCountryOrRegion(int rowIndex, StringBuffer errorMsg, LECustomerVo lv,
			List<LightEbayCustomerModel> customers) {
		if (StringUtils.isBlank(lv.getCountry())) {
			errorMsg.append(MessageFormat.format(COUNTRY_NOTEXIST_MSG, rowIndex, lv.getCountry()));
			return true;
		} else {
			LightEbayCustomerModel model = CollectionUtil.search(customers,
					new IFunction<LightEbayCustomerModel, Boolean>() {
						@Override
						public Boolean excute(LightEbayCustomerModel model) {
							return model.getCountry().equals(lv.getCountry().toUpperCase());
						}
					});
			if (model == null) {
				errorMsg.append(MessageFormat.format(COUNTRY_NOTEXIST_MSG, rowIndex, lv.getCountry()));
				return true;
			}
		}

		if (StringUtils.isBlank(lv.getRegion())) {
			errorMsg.append(MessageFormat.format(REGION_NOTEXIST_MSG, rowIndex, lv.getRegion()));
			return true;
		} else {
			LightEbayCustomerModel model = CollectionUtil.search(customers,
					new IFunction<LightEbayCustomerModel, Boolean>() {
						@Override
						public Boolean excute(LightEbayCustomerModel model) {
							return model.getCountry().equals(lv.getCountry().toUpperCase())
									&& model.getRegion().equals(lv.getRegion().toUpperCase());
						}
					});
			if (model == null) {
				errorMsg.append(MessageFormat.format(REGION_NOTEXIST_MSG, rowIndex, lv.getRegion()));
				return true;
			}
		}
		return false;
	}
}
