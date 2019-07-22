package com.it.ocs.eda.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.util.UserUtils;
import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.eda.model.OrderExportOfUSWestModel;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.synchronou.util.ValidationUtil;

/**
 * 美国西部仓库发货物流号导入
 * @author chenyong
 *
 */
@Service("orderShippingWestImportService")
public class OrderShippingWestImportService extends AExcelImport{
	
	@Autowired
	private IEDADao iEDADao;
	
	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		String rowNumber = String.valueOf(row.get("rowNumber"));
		Object ocsItemId = row.get("orderId");
		if (ValidationUtil.isNullOrEmpty(ocsItemId)) {
			errorsMsg.add("第" + rowNumber + "行OMS订单ID为空");
			return false;
		}
		Object carrier = row.get("CarrierServiceTypeCode");
		if (ValidationUtil.isNullOrEmpty(carrier)) {
			errorsMsg.add("第" + rowNumber + "行运输服务为空");
			return false;
		}
		Object tranckingNumber = row.get("tranckingNumber");
		if (ValidationUtil.isNullOrEmpty(tranckingNumber)) {
			errorsMsg.add("第" + rowNumber + "行运输号为空");
			return false;
		}else{
			String tn = tranckingNumber.toString();
			if(tn.contains(".")){
				errorsMsg.add("第" + rowNumber + "行运输号必须为文本类型");
				return false;
			}
		}
		
		return true;
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
		for(Map<String, Object> row : rows){
			String rowNumber = String.valueOf(row.get("rowNumber"));
			row.put("userId", userId);
			//判断数据是否存在
			if(iEDADao.shipIsExist(row)>0){
				Object isMPS = row.get("isMPS");
				if(null == isMPS || "".equals(isMPS.toString().trim())){
					iEDADao.updateTranckingNumber(row);
				}else{
					iEDADao.updateTranckingNumberMPS(row);
				}
				
			}else{
				errorsMsg.add("第"+rowNumber+"行订单不存在或者被取消或者被转东仓发货。");
			}
			
		}
		return rows.size();
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return OrderExportOfUSWestModel.class;
	}

}
