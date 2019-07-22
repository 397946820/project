package com.it.ocs.sys.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.sys.model.ReturnOrderItemModel;
import com.it.ocs.sys.model.ReturnOrderModel;
import com.it.ocs.sys.vo.ReturnOrderVo;

public class ReturnOrderSupport {

	public static List<ReturnOrderVo> getRows(List<ReturnOrderModel> list, String string) {
		List<ReturnOrderVo> rows = CollectionUtil.beansConvert(list, ReturnOrderVo.class);
		CollectionUtil.each(rows, new IAction<ReturnOrderVo>() {
			@Override
			public void excute(ReturnOrderVo returnOrder) {
				StringBuffer tarckingService = new StringBuffer();
				StringBuffer sku = new StringBuffer();
				StringBuffer qty = new StringBuffer();
				StringBuffer returnCost = new StringBuffer();
				StringBuffer productCaseType = new StringBuffer();
				StringBuffer useHourDay = new StringBuffer();
				CollectionUtil.each(returnOrder.getItems(), new IAction<ReturnOrderItemModel>() {
					@Override
					public void excute(ReturnOrderItemModel item) {
						sku.append(item.getSku() + " " + string);
						qty.append(item.getQty() + "、");
						if (!returnOrder.getPlatform().equals("light") && !returnOrder.getOrderType().equals("2")) {
							returnCost.append(item.getReturnCost() + "、");
						}
						if (!returnOrder.getOrderType().equals("2")) {
							productCaseType.append(item.getProductCaseType() + string);
							useHourDay.append(item.getUseHourDay() + "、");
						}
						if(item.getTarckingService() != null && item.getTarckingNum() != null) {
							tarckingService.append(item.getTarckingService() + " : " + item.getTarckingNum() + " " + string);
						}
					}
				});
				returnOrder.setSku(sku.toString());
				returnOrder.setQty(qty.toString().substring(0, qty.toString().lastIndexOf("、")));
				if (!returnOrder.getPlatform().equals("light")) {
					if (!returnOrder.getOrderType().equals("2")) {
						returnOrder.setReturnCost(
								returnCost.toString().substring(0, returnCost.toString().lastIndexOf("、")));
					}
				} else {
					returnOrder.setReturnCost(returnOrder.getAdjustmentPositive() == null ? ""
							: returnOrder.getAdjustmentPositive() + "");
				}
				if (!returnOrder.getOrderType().equals("2")) {
					returnOrder.setProductCaseType(productCaseType.toString());
					returnOrder
							.setUseHourDay(useHourDay.toString().substring(0, useHourDay.toString().lastIndexOf("、")));
				}
				returnOrder.setTarckingService(tarckingService == null ? null : tarckingService.toString());
			}
		});
		return rows;
	}
}
