package com.it.ocs.compare.service.impl.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.compare.excel.CompareDataExcelImport;
import com.it.ocs.compare.model.EbayDBModel;
import com.it.ocs.compare.model.EbayExcelModel;
import com.it.ocs.compare.model.EbayTotalModel;
import com.it.ocs.compare.vo.DataCompareVO;

public class EbayExcelCompareSupport {
	public static SXSSFWorkbook compareData(DataCompareVO param, List<EbayExcelModel> excelModels,
			List<EbayDBModel> dbModels, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SXSSFWorkbook swb = new SXSSFWorkbook(10);
		CompareDataExcelImport.createExcel(excelModels, EbayExcelModel.class, swb,
				param.getAccount() + "-" + param.getSite() + "报表数据");
		CompareDataExcelImport.createExcel(dbModels, EbayDBModel.class, swb, "数据库中数据");
		List<EbayDBModel> excelNotExistModels = excelNotExistData(excelModels, dbModels);// excel中不存在的数据
		List<EbayExcelModel> dbNotExistModels = dbNotExistData(excelModels, dbModels);// db中不存在的数据
		List<EbayExcelModel> deffPriceModels = diffPriceData(excelModels, dbModels);// 价格不同的数据
		List<EbayTotalModel> totalModels = getTotalModel(excelModels, dbModels, excelNotExistModels, dbNotExistModels,
				deffPriceModels);
		// CompareDataExcelImport.createExcel(srnIsBlank(dbModels),
		// EbayDBModel.class, swb, "数据库中saleRecordNumber为空数据");
		// CompareDataExcelImport.createExcel(ctimeIsBlank(excelModels),
		// EbayExcelModel.class, swb, "excel中订单生成日期为空的数据");
		CompareDataExcelImport.createExcel(excelNotExistData(excelModels, dbModels), EbayDBModel.class, swb,
				"excel中不存在的数据");
		CompareDataExcelImport.createExcel(dbNotExistData(excelModels, dbModels), EbayExcelModel.class, swb,
				"数据库中不存在的数据");
		CompareDataExcelImport.createExcel(diffPriceData(excelModels, dbModels), EbayExcelModel.class, swb, "价格不同的数据");
		CompareDataExcelImport.createExcel(totalModels, EbayTotalModel.class, swb, "价格信息汇总");
		return swb;
		// ExcelUtils.outputExcel(response, swb,
		// ExcelUtils.formatDownloadName("EBAY报表数据核对结果", request));
	}

	private static List<EbayTotalModel> getTotalModel(List<EbayExcelModel> excelModels, List<EbayDBModel> dbModels,
			List<EbayDBModel> excelNotExistModels, List<EbayExcelModel> dbNotExistModels,
			List<EbayExcelModel> deffPriceModels) throws Exception {
		List<EbayTotalModel> result = new ArrayList<>();
		EbayTotalModel totalModel = new EbayTotalModel();
		handleDifference("excelTotalPrice", totalModel, excelModels);
		handleDifference("dbTotalPrice", totalModel, dbModels);
		handleDifference("excelNotExistTotalPrice", totalModel, excelNotExistModels);
		handleDifference("dbNotExistTotalPrice", totalModel, dbNotExistModels);
		handleDifference("diffPriceTotalPrice", totalModel, deffPriceModels);
		result.add(totalModel);
		return result;
	}

	private static <T> void handleDifference(String name, EbayTotalModel totalModel, List<T> models) throws Exception {
		if (!CollectionUtil.isNullOrEmpty(models)) {
			String fieldName = name.substring(0, 1).toUpperCase() + name.substring(1);
			if (!"DiffPriceTotalPrice".equals(fieldName)) {
				Method m = totalModel.getClass().getMethod("set" + fieldName, Double.class);
				m.invoke(totalModel, getTotalPrice(models, 0));
			} else {
				Double dbTotaPrice = getTotalPrice(models, 1);
				Double excelTotalPrice = getTotalPrice(models, 0);
				Method m = totalModel.getClass().getMethod("set" + fieldName, String.class);
				m.invoke(totalModel, "dbTotal: " + dbTotaPrice + ",excelTotal: " + excelTotalPrice);
			}
		}
	}

	private static <T> Double getTotalPrice(List<T> models, Integer type) {
		Double totalPrice = 0.0;
		if (!CollectionUtil.isNullOrEmpty(models)) {
			for (T obj : models) {
				if (obj instanceof EbayExcelModel) {
					EbayExcelModel lm = (EbayExcelModel) obj;
					if (0 == type) {
						totalPrice += replatePriceCode(lm.getSalePrice());
					} else {
						totalPrice += lm.getDbTotalPrice();
					}
				} else {
					EbayDBModel lm = (EbayDBModel) obj;
					totalPrice += lm.getItemTotalPrice();
				}
			}
		}
		return totalPrice;
	}

	private static Double replatePriceCode(String priceInfo) {
		if (StringUtils.isNotBlank(priceInfo)) {
			return Double.parseDouble(priceInfo.replaceAll("GBP", "").replaceAll("EUR", "").replaceAll("USD", ""));
		}
		return 0.0;
	}

	/**
	 * 查询该时间段数据库中数据，saleRecordNumber为空的数据，该数据从技术层面不参与统计计算
	 * 
	 * @param dbModels
	 * @return
	 */
//	private static List<EbayDBModel> srnIsBlank(List<EbayDBModel> dbModels) {
//		return CollectionUtil.searchList(dbModels, new IFunction<EbayDBModel, Boolean>() {
//			@Override
//			public Boolean excute(EbayDBModel dbModel) {
//				return StringUtils.isBlank(dbModel.getSalesRecordNumber());
//			}
//		});
//	}

	/**
	 * 查询excel中订单生成日期为空的数据，该数据从技术层面可能不参与统计计算
	 * 
	 * @param excelModels
	 * @return
	 */
//	private static List<EbayExcelModel> ctimeIsBlank(List<EbayExcelModel> excelModels) {
//		return CollectionUtil.searchList(excelModels, new IFunction<EbayExcelModel, Boolean>() {
//			@Override
//			public Boolean excute(EbayExcelModel excelModel) {
//				return StringUtils.isBlank(excelModel.getShippedOnDate());
//			}
//		});
//	}

	/**
	 * 通过saleRecordNumber,查询这个时间段中,excel中不存在的数据
	 * 
	 * @param excelModels
	 *            excel中的数据
	 * @param dbModels
	 *            通过条件从数据库中查询的数据
	 * @return
	 */
	private static List<EbayDBModel> excelNotExistData(List<EbayExcelModel> excelModels, List<EbayDBModel> dbModels) {
		return CollectionUtil.searchList(dbModels, new IFunction<EbayDBModel, Boolean>() {
			@Override
			public Boolean excute(EbayDBModel dbModel) {
				if (StringUtils.isNotBlank(dbModel.getSalesRecordNumber())) {
					return CollectionUtil.search(excelModels, new IFunction<EbayExcelModel, Boolean>() {
						@Override
						public Boolean excute(EbayExcelModel excelModel) {
							return dbModel.getSalesRecordNumber().equals(excelModel.getSaleRecordNumber());
						}
					}) == null;
				}
				return false;
			}
		});
	}

	/**
	 * 通过saleRecordNumber,查询这个时间段中,数据库中不存在的数据
	 * 
	 * @param excelModels
	 *            excel中的数据
	 * @param dbModels
	 *            通过条件从数据库中查询的数据
	 * @return
	 */
	private static List<EbayExcelModel> dbNotExistData(List<EbayExcelModel> excelModels, List<EbayDBModel> dbModels) {
		return CollectionUtil.searchList(excelModels, new IFunction<EbayExcelModel, Boolean>() {
			@Override
			public Boolean excute(EbayExcelModel excelModel) {
				if (StringUtils.isNotBlank(excelModel.getSaleRecordNumber())) {
					return CollectionUtil.search(dbModels, new IFunction<EbayDBModel, Boolean>() {
						@Override
						public Boolean excute(EbayDBModel dbModel) {
							return excelModel.getSaleRecordNumber().equals(dbModel.getSalesRecordNumber());
						}
					}) == null;
				}
				return false;
			}
		});
	}

	/**
	 * 查询出sale_record_number相同，但价格不一样的数据
	 * 
	 * @param excelModels
	 * @param dbModels
	 * @return
	 */
	private static List<EbayExcelModel> diffPriceData(List<EbayExcelModel> excelModels, List<EbayDBModel> dbModels) {
		return CollectionUtil.searchList(excelModels, new IFunction<EbayExcelModel, Boolean>() {
			@Override
			public Boolean excute(EbayExcelModel excelModel) {
				if (StringUtils.isBlank(excelModel.getSaleRecordNumber())) {
					return false;
				}
				return CollectionUtil.search(dbModels, new IFunction<EbayDBModel, Boolean>() {
					@Override
					public Boolean excute(EbayDBModel dbModel) {
						if (excelModel.getSaleRecordNumber().equals(dbModel.getSalesRecordNumber())) {
							excelModel.setDbTotalPrice(replatePriceCode(dbModel.getTotal()));
							excelModel.setDbSubTotalPrice(replatePriceCode(dbModel.getSubTotal()));
							return dbModel.getItemTotalPrice()
									.doubleValue() != replatePriceCode(excelModel.getSalePrice()).doubleValue();
						}
						return false;
					}
				}) != null;

			}
		});
	}
}
