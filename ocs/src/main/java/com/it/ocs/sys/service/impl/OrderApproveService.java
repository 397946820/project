package com.it.ocs.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.ex.BussinessException;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.sys.dao.IOrderApproveDao;
import com.it.ocs.sys.model.OrderApproveModel;
import com.it.ocs.sys.model.ReturnOrderItemModel;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.IOrderApproveService;
import com.it.ocs.sys.service.IReturnOrderService;

@Service
public class OrderApproveService extends BaseService implements IOrderApproveService {
	@Autowired
	private IOrderApproveDao iOrderApproveDao;

	@Autowired
	private IReturnOrderService returnOrderService;

	@Override
	public ResponseResult<OrderApproveModel> list(RequestParam param) {
		ResponseResult<OrderApproveModel> list = new ResponseResult<>();
		Map<String, Object> params = param.getParam();
		params.put("userId", this.getCurentUser().getId());
		list.setRows(iOrderApproveDao.queryDataByPage(param.getParam(), param.getStartRow(), param.getEndRow()));
		list.setTotal(iOrderApproveDao.queryDataCount(param.getParam()));
		return list;
	}

	@Override
	public List<ReturnOrderItemModel> getInfoById(Map<String, Object> map) {
		return iOrderApproveDao.getInfoById(map);
	}

	@Override
	public OperationResult saveApproveData(Map<String, Object> data) {
		OperationResult or = new OperationResult();
		data.put("userId", this.getCurentUser().getId());
		int isPass = Integer.parseInt(data.get("isPass").toString());
		int type = Integer.parseInt(data.get("type").toString());

		// 补发单审批通过后直接进入发货流程，不需要确认步骤
		if (isPass == 1) {
			if (type == 1) {
				data.put("isReissue", 1);
			} else {
				data.put("isReissue", 0);
			}
			if (type == 2) {
				data.put("isConfirmOrder", 1);
			} else {
				data.put("isConfirmOrder", 0);
			}

			// 插入最终表
			iOrderApproveDao.addDataToFinal(data);
		} else {
			data.put("isReissue", 0);
			data.put("isConfirmOrder", 0);
		}
		int count = iOrderApproveDao.saveApproveData(data);
		if (isPass == 1 && type == 1) {
			// 插入客户管理表
			iOrderApproveDao.addReplaceOrRefound(data);
		}
		if (isPass == 1 && type == 0) {
			Map<String, Object> order = iOrderApproveDao.getOrderById(data.get("id"));
			if (!order.get("platform").toString().equals("ebay")) {
				order.put("tracking_Num", "");
				order.put("receipt_No", "");
				order.put("receiving_Time", "");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("order", order);
				map.put("items", iOrderApproveDao.getItemsByParentId(data.get("id")));
				returnOrderService.orderRefund(map);
			}
		}
		or.setData(count);
		return or;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean cancel(Long id) throws Exception {
		Assert.notNull(id, "The parameter 'id' is null.");
		
		UserModel um = this.getCurentUser();
		if(um == null) {
			throw new BussinessException("无法[取消]来自一个未登录或身份认证已过期的请求的单。");
		}
		
		if(this.iOrderApproveDao.refcount(id) > 0) {
			throw new BussinessException("无法[取消]一个已被后续业务引用的单。");
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", id);
		data.put("canceller", um.getId());
		return this.iOrderApproveDao.cancelOrder(data) > 0;
	}
}
