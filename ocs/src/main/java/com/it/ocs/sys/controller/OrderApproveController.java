package com.it.ocs.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.ex.BussinessException;
import com.it.ocs.sys.model.OrderApproveModel;
import com.it.ocs.sys.model.ReturnOrderItemModel;
import com.it.ocs.sys.service.IOrderApproveService;

@RequestMapping("/orderApprove")
@Controller
public class OrderApproveController {

	private static final Logger logger = Logger.getLogger(OrderApproveController.class);
	
	@Autowired
	private IOrderApproveService orderApproveService;
	
	@RequestMapping("/show")
	public String show() {
		return "admin/sys/orderApprove";
	}

	@RequestMapping("/list")
	@ResponseBody
	public ResponseResult<OrderApproveModel> list(RequestParam param) throws Exception {
		return orderApproveService.list(param);
	}
	
	@RequestMapping("/getInfoById/{id}/{type}")
	@ResponseBody
	public List<ReturnOrderItemModel> getInfoById(@PathVariable("id")Integer id,@PathVariable("type")Integer type){
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		map.put("type", type);
		return orderApproveService.getInfoById(map);
	}
	
	@RequestMapping("/saveApproveData")
	@ResponseBody
	public OperationResult saveApproveData(@RequestBody Map<String,Object> data){
		return orderApproveService.saveApproveData(data);
	}

	
	private static final String EX_PREFIX_CANCEL = "[取消]失败：";
	
	@RequestMapping("/cancel/{id}")
	@ResponseBody
	public OperationResult cancel(@PathVariable("id") Long id) {
		OperationResult result = new OperationResult();
		try {
			boolean success = this.orderApproveService.cancel(id);
			result.setErrorCode(success ? 0 : 1);
			result.setDescription(success ? "[取消]成功！" : "[取消]失败！");
		} catch (Exception e) {
			result.setErrorCode(1);
			if(e instanceof BussinessException) {
				result.setDescription(EX_PREFIX_CANCEL + e.getMessage());
			} else {
				result.setDescription(EX_PREFIX_CANCEL + "发生异常，请稍后再试或者联系管理员！");
				logger.error("[cancel(...)] - " + e.getMessage(), e);
			}
		}
		return result;
	}
}
