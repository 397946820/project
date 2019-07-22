package com.it.ocs.compare.service.impl.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.google.common.collect.Lists;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.compare.excel.CompareDataExcelImport;
import com.it.ocs.compare.model.AmazonDBModel;
import com.it.ocs.compare.model.AmazonInputEXModel;
import com.it.ocs.compare.model.AmazonTotalModel;
import com.it.ocs.synchronou.util.ListUtil;
import com.it.ocs.synchronou.util.ValidationUtil;

public class AmazonExcelCompareSupport {
	public static Map<String,SXSSFWorkbook> map = new HashMap<>();
	
	private static List<AmazonInputEXModel> dbNotExist(Map<String,AmazonDBModel> dbMap,Map<String,AmazonInputEXModel> excelMap) {
		List<AmazonInputEXModel> list = Lists.newArrayList();
		CollectionUtil.each(excelMap.keySet(), new IAction<String>() {

			@Override
			public void excute(String excelKey) {
				if (!dbMap.containsKey(excelKey)) {
					list.add(excelMap.get(excelKey));
				}
			}
		});
		return list;
	}

	private static List<AmazonDBModel> excelNotExist(Map<String,AmazonDBModel> dbMap,Map<String,AmazonInputEXModel> excelMap) {
		List<AmazonDBModel> list = Lists.newArrayList();
		CollectionUtil.each(dbMap.keySet(), new IAction<String>() {

			@Override
			public void excute(String dbKey) {
				if (!excelMap.containsKey(dbKey)) {
					list.add(dbMap.get(dbKey));
				}
			}
		});
		return list;
	}
	public static SXSSFWorkbook compareDate(List<AmazonInputEXModel> excelModels,List<AmazonDBModel> dbModels,
			HttpServletRequest request, HttpServletResponse response,List<String> type ) throws Exception{
		SXSSFWorkbook swb = new SXSSFWorkbook(10);
		Map<String, List<AmazonDBModel>> dbMap = new HashMap<>();
		Map<String,List<AmazonInputEXModel>> excelMap = new HashMap<>();
		Map<String,AmazonDBModel> dbMapModel = new HashMap<>();
		Map<String,AmazonInputEXModel> excelMapModel = new HashMap<>();
		
		CollectionUtil.each(dbModels, new IAction<AmazonDBModel>() {
			@Override
			public void excute(AmazonDBModel dbModel) {
				String key = dbModel.getSelfDate()+"-"+dbModel.getType()+"-"+dbModel.getSettlementId()+"-"+dbModel.getOrderId()
				+"-";
				
				if(dbMap.get(key)!=null){
					dbMap.get(key).add(dbModel);
				}else{
					List<AmazonDBModel> dbModels2 = Lists.newArrayList();
					dbModels2.add(dbModel);
					dbMap.put(key, dbModels2);
				}
				key= key + Double.parseDouble(dbModel.getTotal())+"|0";
				int count = 1;
				while(dbMapModel.containsKey(key)) {
					key=key.substring(0,key.lastIndexOf("|")+1)+count;
					count++;
				}
				dbMapModel.put(key, dbModel);
			}
		});
		CollectionUtil.each(excelModels, new IAction<AmazonInputEXModel>() {

			@Override
			public void excute(AmazonInputEXModel excelModel) {
				// TODO Auto-generated method stub
				String orderId = excelModel.getOrder_id();
				if(ValidationUtil.isNullOrEmpty(orderId)){
					orderId = null;
				}
				String key = excelModel.getDate_time()+"-"+excelModel.getType()+"-"+excelModel.getSettlement_id()+"-"+orderId
				+"-";
				key= key + Double.parseDouble(excelModel.getTotal())+"|0";
				if(excelMap.get(key)!=null){
					excelMap.get(key).add(excelModel);
				}else{
					List<AmazonInputEXModel> excelModels2 = Lists.newArrayList();
					excelModels2.add(excelModel);
					excelMap.put(key, excelModels2);
				}
				int count = 1;
				while(excelMapModel.containsKey(key)) {
					key=key.substring(0,key.lastIndexOf("|")+1)+count;
					count++;
				}
				excelMapModel.put(key, excelModel);
			}
		});
	
		
		CompareDataExcelImport.createExcel(excelModels, AmazonInputEXModel.class, swb, "Amazon报表数据");
		CompareDataExcelImport.createExcel(dbModels, AmazonDBModel.class, swb, "数据库中的数据");
		// excel中不存在的数据
		List<AmazonDBModel> excelNotExistModels =  excelNotExist(dbMapModel,excelMapModel);
		//List<AmazonDBModel> excelNotExistModels =  excelNotExistData(excelModels, dbModels,dbMap);
		//db中不存在的数据
		//List<AmazonInputEXModel> dbNotExistModels = dbNotExistData(excelModels, dbModels,excelMap);
		List<AmazonInputEXModel> dbNotExistModels = dbNotExist(dbMapModel, excelMapModel);
		//价格不同的数据
		List<AmazonInputEXModel> deffPriceModels = diffPriceData(dbMap, excelMap);
		Map<String,Object> totalMap = getTotalModel(excelModels, dbModels, excelNotExistModels, dbNotExistModels,deffPriceModels,type);
		
		CompareDataExcelImport.createExcel(excelNotExistModels, AmazonDBModel.class, swb, "同一个时间段，excel中不存在的数据");
		CompareDataExcelImport.createExcel(dbNotExistModels, AmazonInputEXModel.class, swb, "同一个时间段，数据库中不存在的数据");
		CompareDataExcelImport.createExcel(deffPriceModels, AmazonInputEXModel.class, swb, "价格不同的数据");
		//CompareDataExcelImport.createExcel(totalModels, AmazonTotalModel.class, swb, "价格信息汇总");
		CompareDataExcelImport.craeteExcel(totalMap, swb, "价格信息汇总");
		return swb;
	}
	private static Map<String,Object> getTotalModel(List<AmazonInputEXModel> excelModels,List<AmazonDBModel> dbModels,
		
		List<AmazonDBModel> excelNotExistModels, List<AmazonInputEXModel> dbNotExistModels, List<AmazonInputEXModel>  deffPriceModels,List<String> type) throws Exception{
		Map<String, Object> result = new HashMap<>();
		
		result.put("excel中的总金额", getModelTotal(excelModels));
		result.put("数据库中的总金额", getModelTotal(dbModels));
		result.put("数据库中不存在的总金额", getModelTotal(excelNotExistModels));
		result.put("excel中不存在的总金额", getModelTotal(dbNotExistModels));
		result.put("价格不同数据的差异数据", getModelTotal(deffPriceModels));
		for (String string : type) {
			
			result.put("数据库不存在总金额("+string+")", getModelTotal(getModelsByType(string,excelNotExistModels)));
			result.put("excel中不存在总金额("+string+")", getModelTotal(getModelsByType(string,dbNotExistModels)));
		    
		}
		
		//result.put("", totalModel.);
		return result;
	}
	private static <T> List<T> getModelsByType(String type, List<T> models ){
		Map<String, List<T>> map = new HashMap<>();
		for (T obj : models) {
			if(obj instanceof AmazonDBModel){
				AmazonDBModel adbm = (AmazonDBModel) obj;
				if(map.get(adbm.getType())!=null){
					map.get(adbm.getType()).add(obj);
				}else{
					List<T> list = Lists.newArrayList();
					list.add(obj);
					map.put(adbm.getType(), list);
				}
		   }else{
			   AmazonInputEXModel adbm = (AmazonInputEXModel) obj;
				if(map.get(adbm.getType())!=null){
					map.get(adbm.getType()).add(obj);
				}else{
					List<T> list = Lists.newArrayList();
					list.add(obj);
					map.put(adbm.getType(), list);
				}
		   }
		}
		
		return map.get(type);
	}
	private static <T>  Double getModelTotal(List<T> models){
		if (!CollectionUtil.isNullOrEmpty(models)) {
			return getTotalPrice(models, 0);
		}
		return null;
	}
	private static List<AmazonInputEXModel> diffPriceData(Map<String, List<AmazonDBModel>> dbMap,Map<String, List<AmazonInputEXModel>> excelMap){
		List<AmazonInputEXModel> result =Lists.newArrayList();
		
		CollectionUtil.each(excelMap.keySet(), new IAction<String>() {

			@Override
			public void excute(String excelKey) {
				if (dbMap.containsKey(excelKey)) {
					List<AmazonDBModel> dbModels = dbMap.get(excelKey);
					List<AmazonInputEXModel> excelModels = excelMap.get(excelKey);
					for (AmazonInputEXModel amazonInputEXModel : excelModels) {
						Integer info = 0;
						for (AmazonDBModel amazonDBModel : dbModels) {
							
							if(!ValidationUtil.isEquals(Double.parseDouble(amazonInputEXModel.getTotal()), Double.parseDouble(amazonDBModel.getTotal()))){
								if(!info.equals("1")){
									info = 0;
								}
							}else{
								info=1;
							}
						}
						if(info.equals("0")){
							result.add(amazonInputEXModel);
						}
					}
				}
			}
		});
		return result;
		
	}
	private static List<AmazonDBModel> excelNotExistData(List<AmazonInputEXModel> excelModels,
			List<AmazonDBModel> dbModels,Map<String, List<AmazonDBModel>> map){
		
		    List<AmazonDBModel> result = ListUtil.getListByMap(map);
		    List<AmazonDBModel> updateModel = Lists.newArrayList();
		    for(AmazonInputEXModel excelModel : excelModels){
		    	
		    	if(map.get(excelModel.getOrder_id())!=null){
		    		List<AmazonDBModel> dbModels2 = map.get(excelModel.getOrder_id());
		    	
		    		for (AmazonDBModel amazonDBModel : dbModels2) {
		    			if(ValidationUtil.isEquals(amazonDBModel.getSelfDate(), excelModel.getDate_time())
								&&ValidationUtil.isEquals(amazonDBModel.getSettlementId(),excelModel.getSettlement_id())
								&&ValidationUtil.isEquals(amazonDBModel.getType(), excelModel.getType())
								&&ValidationUtil.isEquals(amazonDBModel.getSku(), excelModel.getSku())){
						updateModel.add(amazonDBModel);
					}
		    		
				}
		    	}
		    }
			
		
			result.removeAll(updateModel);
			return result;
		
	}
	private static List<AmazonInputEXModel> dbNotExistData(List<AmazonInputEXModel> excelModels,
			List<AmazonDBModel> dbModels,Map<String, List<AmazonInputEXModel>> map){
		List<AmazonInputEXModel> result =ListUtil.getListByMap(map);
		List<AmazonInputEXModel> updateModel = Lists.newArrayList();
		
		 for(AmazonDBModel dbModel : dbModels){
		    	if(map.get(dbModel.getOrderId())!=null){
		    		List<AmazonInputEXModel> excelModels2 = map.get(dbModel.getOrderId());
		    		for (AmazonInputEXModel amazonInputEXModel : excelModels2) {
		    			if(ValidationUtil.isEquals(amazonInputEXModel.getDate_time(), dbModel.getSelfDate())
								&&ValidationUtil.isEquals(amazonInputEXModel.getSettlement_id(),dbModel.getSettlementId())
								&&ValidationUtil.isEquals(amazonInputEXModel.getType(), dbModel.getType())
								&&ValidationUtil.isEquals(amazonInputEXModel.getSku(), dbModel.getSku())){
						updateModel.add(amazonInputEXModel);
					}
		    		
				}
		    	}
		    }
			
		
			result.removeAll(updateModel);
			return result;
		
	}
	
	private static <T> void handleDifference(String name, AmazonTotalModel totalModel, List<T> models) throws Exception {
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
		String kk ="";
		try {
			
		
		if (!CollectionUtil.isNullOrEmpty(models)) {
			for (T obj : models) {
				if (obj instanceof AmazonInputEXModel) {
					AmazonInputEXModel lm = (AmazonInputEXModel) obj;
					if (!ValidationUtil.isNullOrEmpty(lm.getTotal())) {
						String resultS = lm.getTotal().replace("\"", "").replace(",", "");
						kk = resultS;
						totalPrice += Double.parseDouble(resultS);
					} else {
						
					}
				} else {
					AmazonDBModel lm = (AmazonDBModel) obj;
					if (!ValidationUtil.isNullOrEmpty(lm.getTotal())) {
						String resultS = lm.getTotal().replace("\"", "").replace(",", "");
						totalPrice += Double.parseDouble(resultS);
					}
				}
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("异常："+kk);
			e.printStackTrace();
		}
		return totalPrice;
	}
}
