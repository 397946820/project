package com.it.ocs.eda.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.util.UserUtils;
import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.eda.model.PSkuLinkImportModel;
import com.it.ocs.excel.service.AExcelImport;
/**
 * sku映射导入消费类
 * @author chenyong
 *
 */
@Service("pSkuLinkImport")
public class PSkuLinkImport extends AExcelImport{
	
	@Autowired
	private IEDADao iEDADao;
	
	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		String rowNum = String.valueOf(row.get("rowNumber"));
		boolean result = true;
		String pSku = (String)row.get("pSku");
		result = result&&checkBaseRuleForCellValue(errorsMsg,pSku,rowNum);
		if(result){
			int pSkuXCount = iEDADao.skuIsExist(pSku);
			if(pSkuXCount > 0){
				errorsMsg.add("第"+rowNum+"行销售SKU与已存在的原始产品SKU名称一样");
			}
			result = result && (pSkuXCount==0);
			if(pSku.contains(",")){
				int length = pSku.split(",").length;
				for(int i = 0;i<length;i++){
					String sku0 = (String)row.get("sku"+i);
					result = result&&checkBaseRuleForCellValue(errorsMsg,sku0,rowNum);
					result = checkOtherRule(errorsMsg, rowNum, result, pSku, sku0);
				}
			}else{
				String sku0 = (String)row.get("sku0");
				result = result&&checkBaseRuleForCellValue(errorsMsg,sku0,rowNum);
				result = checkOtherRule(errorsMsg, rowNum, result, pSku, sku0);
			}
		}

		return result;
	}

	private boolean checkOtherRule(List<String> errorsMsg, String rowNum, boolean result, String pSku, String sku0) {
		//销售SKU不能与原始SKU一致
		if(pSku.equalsIgnoreCase(sku0)){
			errorsMsg.add("第"+rowNum+"行销售SKU跟原始SKU一样");
		}
		result = result&&!pSku.equalsIgnoreCase(sku0);
		//验证原始sku是否存在
		int exist = iEDADao.skuIsExist(sku0);
		if(exist==0){
			errorsMsg.add("第"+rowNum+"行原始SKU不存在");
		}
		result = result&&(exist>0);
		
		//销售SKU必须包含原始SKU
		int index = pSku.indexOf(sku0);
		if(index== -1){
			errorsMsg.add("第"+rowNum+"行销售sku必须包含原始sku");
		}
		result = result&&(index > 0);
		return result;
	}
	
	private boolean checkBaseRuleForCellValue(List<String> errorsMsg,String sku,String rowNum){
		if(null == sku||"".equals(sku.trim())){
			errorsMsg.add("第"+rowNum+"行sku为空");
			return false;
		}else{
			//^[0-9a-zA-Z\\-]$
			if(!checkSpecialCharacter(sku)){
				errorsMsg.add("第"+rowNum+"行SKU名称包含特殊字符(只能输入数字、字母、英文逗号、英文横杆、星号，多个sku请用英文逗号分隔)");
				return false;
			}
		}
		
		return true;
	}
	private boolean checkSpecialCharacter(String pSku) {
		String regEx = "^[0-9a-zA-Z\\-\\*,]*$";
	    // 编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    // 忽略大小写的写法
	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(pSku);
	    return matcher.matches();
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		long userId = UserUtils.getUserId();
		int update = 0;
		for(Map<String, Object> row : rows){
			String rowNum = String.valueOf(row.get("rowNumber"));
			String psku = (String)row.get("pSku");
			if(psku.contains(",")){
				int length = psku.split(",").length;
				for(int i = 0;i<length;i++){
					String sku = (String)row.get("sku"+i);
					Map<String,Object> data = new HashMap<>();
					data.put("pSku", psku);
					data.put("userId", userId);
					data.put("sku", sku);
					Object qty = row.get("qty0");
					if(null != qty){
						String q = String.valueOf(qty);
						if(q.contains(".")){
							errorsMsg.add("第"+rowNum+"行qty0值格式不正确");
						}else{
							data.put("qty", qty);
						    iEDADao.updatePSkuLinkInfo(data);
						    update++;
						}
					}
				}
			}else{
				//必须唯一
				String sku = (String)row.get("sku0");
				Map<String,Object> data = new HashMap<>();
				data.put("pSku", psku);
				data.put("userId", userId);
				data.put("sku", sku);
				Object qty = row.get("qty0");
				if(null != qty){
					String q = String.valueOf(qty);
					if(q.contains(".")){
						errorsMsg.add("第"+rowNum+"行qty0值格式不正确");
					}else{
						data.put("qty", qty);
					    iEDADao.updatePSkuLinkInfo(data);
					    update++;
					}
				}
			}
			
		}
		return update;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return PSkuLinkImportModel.class;
	}



}
