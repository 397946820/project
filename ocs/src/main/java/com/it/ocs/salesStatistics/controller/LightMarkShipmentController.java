package com.it.ocs.salesStatistics.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.ex.BussinessException;
import com.it.ocs.salesStatistics.service.ILightMarkShipmentService;
import com.it.ocs.salesStatistics.vo.LightMarkShipmentVO;

@Controller
@RequestMapping("/markshipment")
public class LightMarkShipmentController {
	
	private final static Logger logger = Logger.getLogger(LightMarkShipmentController.class);
	
	@Autowired
	private ILightMarkShipmentService lightMarkShipmentService;

	@RequestMapping("/lightlist")
	@ResponseBody
	public ResponseResult<LightMarkShipmentVO> lightlist(RequestParam param) throws Exception {
		return lightMarkShipmentService.queryByPage(param, "light");
	}
	
	@RequestMapping("/amazonlist")
	@ResponseBody
	public ResponseResult<LightMarkShipmentVO> amazonlist(RequestParam param) throws Exception {
		return lightMarkShipmentService.queryByPage(param, "amazon");
	}
	
	@RequestMapping(value="/cancel/{request}",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult cancel(@PathVariable("request") String request) {
		try {
			String[] params = request.split("-");
			return this.lightMarkShipmentService.cancel(params[0], Long.parseLong(params[1]));
		} catch(BussinessException e) {
			logger.warn(e.getMessage(), e);
			return new OperationResult(1, "取消失败：" + e.getMessage(), null, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new OperationResult(1, "取消失败，请联系管理员！", null, null);
		}
	}
}
