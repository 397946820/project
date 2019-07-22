package com.it.ocs.sys.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.sys.dao.IReturnOrderDao;
import com.it.ocs.sys.model.ReturnOrderModel;
import com.it.ocs.sys.utils.ReturnOrderSupport;
import com.it.ocs.sys.vo.ReturnOrderVo;

@Service("returnOrderExport")
public class ReturnOrderExportService extends AExcelExport {

	@Autowired
	private IReturnOrderDao returnOrderDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		ReturnOrderVo returnOrder = new ReturnOrderVo();
		returnOrder.setPlatform(request.getParameter("platform"));
		returnOrder.setAccount(request.getParameter("account"));
		returnOrder.setSite(request.getParameter("site"));
		returnOrder.setOrderId(request.getParameter("orderId"));
		returnOrder.setCreateName(request.getParameter("createName"));
		returnOrder
				.setOrderType(request.getParameter("orderType").equals("") ? null : request.getParameter("orderType"));
		returnOrder.setCause(request.getParameter("cause"));
		returnOrder.setCreateBy(UserUtils.getUserId());
		List<ReturnOrderModel> list = returnOrderDao.findAll(returnOrder, null, null, 1, 999999999);
		List<Map<String, Object>> result = Lists.newArrayList();
		CollectionUtil.each(ReturnOrderSupport.getRows(list, "\r\n"), new IAction<ReturnOrderVo>() {
			@Override
			public void excute(ReturnOrderVo returnOrder) {
				if(returnOrder.getOrderType().equals("2")) {
					returnOrder.setReturnCost(returnOrder.getDeliveryWarehouse());
					returnOrder.setCause("");
				}
				returnOrder.setOrderType(returnOrder.getOrderType().equals("0") ? "退货退款单"
						: returnOrder.getOrderType().equals("1") ? "补发单"
								: returnOrder.getOrderType().equals("2") ? "线下单" : "");
				returnOrder.setApproveResult(returnOrder.getEnabledFlag().equals("N") ? "已取消申请"
						: returnOrder.getApproveResult() == null ? "未审核"
								: returnOrder.getApproveResult().equals("0") ? "审核未通过"
										: returnOrder.getApproveResult().equals("1") ? "审核通过" : "");
				result.add(new BeanMap(returnOrder));
			}
		});
		return result;
	}

	@Override
	protected void init(HttpServletRequest request) {
		super.initModel(ReturnOrderModel.class, "我的申请数据.xlsx");

	}

}
