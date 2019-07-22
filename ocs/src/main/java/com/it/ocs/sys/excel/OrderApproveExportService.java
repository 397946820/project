package com.it.ocs.sys.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.excel.service.AExcelDynamicExport;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.sys.dao.IOrderApproveDao;
import com.it.ocs.sys.model.OrderApproveModel;

@Service("orderApproveExcel")
public class OrderApproveExportService extends AExcelDynamicExport {

	@Autowired
	private IOrderApproveDao orderApproveDao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("userId", UserUtils.getUserId());
		params.put("orderId", request.getParameter("orderId"));
		params.put("platform", request.getParameter("platform"));
		params.put("orderType", request.getParameter("orderType"));
		params.put("startCreateDate", request.getParameter("startCreateDate"));
		params.put("endCreateDate", request.getParameter("endCreateDate"));
		List<Map<String, Object>> data = orderApproveDao.queryData(params);
		CollectionUtil.each(data, new IAction<Map<String, Object>>() {
			@Override
			public void excute(Map<String, Object> map) {
				map.put("ORDERTYPE",
						map.get("ORDERTYPE") == null ? null
								: map.get("ORDERTYPE").toString().equals("0") ? "退货退款单"
										: map.get("ORDERTYPE").toString().equals("1") ? "补发单"
												: map.get("ORDERTYPE").toString().equals("2") ? "线下单" : "");
				map.put("APPROVERESULT",
						map.get("APPROVERESULT") == null ? "未审批"
								: map.get("APPROVERESULT").toString().equals("0") ? "未通过"
										: map.get("APPROVERESULT").toString().equals("1") ? "通过" : "未审批");
			}
		});
		return data;
	}

	@Override
	protected void init(HttpServletRequest request) {
		String paramIncludeFields = request.getParameter("includeFields");
		String[] includeFields = StringUtils.isBlank(paramIncludeFields) ? null : paramIncludeFields.split(",");
		super.initModel(OrderApproveModel.class, "我的待办数据.xlsx", includeFields);
	}

}
