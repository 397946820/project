package com.it.ocs.compare.service.impl.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.google.common.collect.Lists;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.compare.excel.CompareDataExcelImport;
import com.it.ocs.compare.model.LightDBModel;
import com.it.ocs.compare.model.LightExcelModel;
import com.it.ocs.compare.model.LightTotalModel;
import com.it.ocs.compare.utils.RegexUtil;
import com.it.ocs.synchronou.util.ValidationUtil;

public class LightExcelCompareSupport {
	public static Map<String, SXSSFWorkbook> map = new HashMap<>();

	public static SXSSFWorkbook compareData(List<LightExcelModel> excelModels, List<LightDBModel> dbModels,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		SXSSFWorkbook swb = new SXSSFWorkbook(10);
		CompareDataExcelImport.createExcel(excelModels, LightExcelModel.class, swb, "官网报表数据");
		CompareDataExcelImport.createExcel(dbModels, LightDBModel.class, swb, "数据库中数据");
		// CompareDataExcelImport.createExcel(ctimeIsBlank(excelModels),
		// LightExcelModel.class, swb, "excel中订单生成日期为空的数据");
		List<LightDBModel> excelNotExistModels = excelNotExistData(excelModels, dbModels);// excel中不存在的数据
		List<LightExcelModel> dbNotExistModels = dbNotExistData(excelModels, dbModels);// db中不存在的数据
		List<LightExcelModel> deffPriceModels = diffPriceData(excelModels, dbModels);// 价格不同的数据
		List<LightTotalModel> totalModels = getTotalModel(excelModels, dbModels, excelNotExistModels, dbNotExistModels,
				deffPriceModels);
		CompareDataExcelImport.createExcel(excelNotExistModels, LightDBModel.class, swb, "同一个时间段，excel中不存在的数据");
		CompareDataExcelImport.createExcel(dbNotExistModels, LightExcelModel.class, swb, "同一个时间段，数据库中不存在的数据");
		CompareDataExcelImport.createExcel(deffPriceModels, LightExcelModel.class, swb, "价格不同的数据");
		CompareDataExcelImport.createExcel(totalModels, LightTotalModel.class, swb, "价格信息汇总");
		map.put("userKey", swb);
		return swb;
	}

	private static List<LightTotalModel> getTotalModel(List<LightExcelModel> excelModels, List<LightDBModel> dbModels,
			List<LightDBModel> excelNotExistModels, List<LightExcelModel> dbNotExistModels,
			List<LightExcelModel> deffPriceModels) throws Exception {
		List<LightTotalModel> result = new ArrayList<>();
		LightTotalModel totalModel = new LightTotalModel();
		handleDifference("excelTotalPrice", totalModel, excelModels);
		handleDifference("dbTotalPrice", totalModel, dbModels);
		handleDifference("excelNotExistTotalPrice", totalModel, excelNotExistModels);
		handleDifference("dbNotExistTotalPrice", totalModel, dbNotExistModels);
		handleDifference("diffPriceTotalPrice", totalModel, deffPriceModels);
		
		handleDifference("excelBSkuTotalPrice", totalModel, getModelsBySku(excelModels,"^B"));
		handleDifference("excelOSSkuTotalPrice", totalModel, getModelsBySku(excelModels,"^OS"));
		
		result.add(totalModel);
		return result;
	}

	private static <T> Double getTotalPrice(List<T> models, Integer type) {
		Double totalPrice = 0.0;
		if (!CollectionUtil.isNullOrEmpty(models)) {
			for (T obj : models) {
				if (obj instanceof LightExcelModel) {
					LightExcelModel lm = (LightExcelModel) obj;
					if (0 == type) {
						totalPrice += lm.getRowTotal();
					} else {
						totalPrice += lm.getDbRowTotal();
					}
				} else {
					LightDBModel lm = (LightDBModel) obj;
					totalPrice += lm.getRowTotal();
				}
			}
		}
		return totalPrice;
	}
    private static List<LightExcelModel> getModelsBySku(List<LightExcelModel> models,String regex){
    	List<LightExcelModel> reslut = Lists.newArrayList();
    	if(!ValidationUtil.isNull(models)){
    		for (LightExcelModel lightExcelModel : models) {
				if(RegexUtil.isStringByRegex(lightExcelModel.getSku(), regex)){
					reslut.add(lightExcelModel);
				}
			}
    		return reslut;
    	}
    	
    	return null;
    }
	private static <T> void handleDifference(String name, LightTotalModel totalModel, List<T> models) throws Exception {
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

	/**
	 * 查询excel中订单生成日期为空的数据，该数据从技术层面可能不参与统计计算
	 * 
	 * @param excelModels
	 * @return
	 */
	// private static List<LightExcelModel> ctimeIsBlank(List<LightExcelModel>
	// excelModels) {
	// return CollectionUtil.searchList(excelModels, new
	// IFunction<LightExcelModel, Boolean>() {
	// @Override
	// public Boolean excute(LightExcelModel excelModel) {
	// return StringUtils.isBlank(excelModel.getCreatedAt());
	// }
	// });
	// }

	/**
	 * 通过saleRecordNumber,查询这个时间段中,excel中不存在的数据
	 * 
	 * @param excelModels
	 *            excel中的数据
	 * @param dbModels
	 *            通过条件从数据库中查询的数据
	 * @return
	 */
	private static List<LightDBModel> excelNotExistData(List<LightExcelModel> excelModels,
			List<LightDBModel> dbModels) {
		return CollectionUtil.searchList(dbModels, new IFunction<LightDBModel, Boolean>() {
			@Override
			public Boolean excute(LightDBModel dbModel) {
				if (StringUtils.isNotBlank(dbModel.getOrderId()) && StringUtils.isNotBlank(dbModel.getSku())) {
					return CollectionUtil.search(excelModels, new IFunction<LightExcelModel, Boolean>() {
						@Override
						public Boolean excute(LightExcelModel excelModel) {
							return dbModel.getOrderId().equals(excelModel.getIncrementId())
									&& dbModel.getSku().equals(excelModel.getSku());
						}
					}) == null;
				}
				return false;
			}
		});
	}

	/**
	 * 通过orderId和SKU,查询这个时间段中,数据库中不存在的数据
	 * 
	 * @param excelModels
	 *            excel中的数据
	 * @param dbModels
	 *            通过条件从数据库中查询的数据
	 * @return
	 */
	private static List<LightExcelModel> dbNotExistData(List<LightExcelModel> excelModels,
			List<LightDBModel> dbModels) {
		return CollectionUtil.searchList(excelModels, new IFunction<LightExcelModel, Boolean>() {
			@Override
			public Boolean excute(LightExcelModel excelModel) {
				if (StringUtils.isNotBlank(excelModel.getIncrementId())
						&& StringUtils.isNotBlank(excelModel.getSku())) {
					return CollectionUtil.search(dbModels, new IFunction<LightDBModel, Boolean>() {
						@Override
						public Boolean excute(LightDBModel dbModel) {
							return excelModel.getIncrementId().equals(dbModel.getOrderId())
									&& excelModel.getSku().equals(dbModel.getSku());
						}
					}) == null;
				}
				return false;
			}
		});
	}

	/**
	 * 查询出orderID,SKU相同，但价格不一样的数据
	 * 
	 * @param excelModels
	 * @param dbModels
	 * @return
	 */
	private static List<LightExcelModel> diffPriceData(List<LightExcelModel> excelModels, List<LightDBModel> dbModels) {
		return CollectionUtil.searchList(excelModels, new IFunction<LightExcelModel, Boolean>() {
			@Override
			public Boolean excute(LightExcelModel excelModel) {
				if (StringUtils.isBlank(excelModel.getIncrementId()) || StringUtils.isBlank(excelModel.getSku())) {
					return false;
				}
				return CollectionUtil.search(dbModels, new IFunction<LightDBModel, Boolean>() {
					@Override
					public Boolean excute(LightDBModel dbModel) {
						if (excelModel.getIncrementId().equals(dbModel.getOrderId())
								&& excelModel.getSku().equals(dbModel.getSku())
								&& excelModel.getPrice().doubleValue() == dbModel.getPrice().doubleValue()
								&& excelModel.getQty_ordered().doubleValue() == dbModel.getQtyOrdered().doubleValue()) {
							excelModel.setDbPrice(dbModel.getPrice());
							excelModel.setDbQtyOrdered(dbModel.getQtyOrdered());
							excelModel.setDbRowTotal(dbModel.getRowTotal());
							return dbModel.getRowTotal().doubleValue() != excelModel.getRowTotal().doubleValue();
						}
						return false;
					}
				}) != null;

			}
		});
	}
}
