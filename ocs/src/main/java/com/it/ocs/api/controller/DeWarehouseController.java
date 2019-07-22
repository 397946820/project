package com.it.ocs.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.it.ocs.api.ex.Oms2WmsException;
import com.it.ocs.api.service.IDeWarehouseService;
import com.it.ocs.api.utils.CommonUtils;
import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.util.JsonConvertUtil;

/**
* @ClassName: DeWarehouseController 
* @Description: 德国仓控制器
* @author wgc 
* @date 2018年4月9日 下午3:57:36 
*
 */

@RestController
@RequestMapping("/WMS/DE/")
public class DeWarehouseController {
	private static final Logger logger = Logger.getLogger(DeWarehouseController.class);
	@Autowired
	private IDeWarehouseService deWarehouseService;
	
	@Autowired
	private CommonUtils commonUtils;
	
	/**
	* @Title: outWarehouseFeedback 
	* @Description: 出库反馈接口
	* @param @param dataBytes
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="outWmsFeedback",method = RequestMethod.POST)
	public Map<String,Object> outWmsFeedback(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info(">>> outWmsFeedback >>> request data=" + JsonConvertUtil.getJsonString(map));
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> validateResultMap = new HashMap<String,Object>();
		
		validateResultMap = commonUtils.validateRequestToken((String)map.get("token"));
		if(!validateResultMap.get("resultFlag").equals("true")){
			return validateResultMap;
	    }
		
		Map<String, Object> data = null;
		try {
			data = (Map<String, Object>) map.get("data");
			resultMap = deWarehouseService.receiveOutOrderFeedback(data);
			logger.info("WMS出库反馈的数据是:"+JsonConvertUtil.getJsonString(data));
		} catch (Exception e) {
			logger.error("出库反馈失败，请稍后再试或者联系系统管理员查看!"+ e.getMessage(), e);
			resultMap.put("resultFlag", "false");
			resultMap.put("error", "出库反馈失败，请稍后再试或者联系系统管理员查看!");
			resultMap.put("resultCode", "1000");
			this.deWarehouseService.recordOperFailedLog("receive-out-order-feedback", null, e.getMessage() + ": parameter data=" + JsonConvertUtil.getJsonString(data));
		}
		
		return resultMap;
	}
	
	/**
	* @Title: outWarehouseFeedback 
	* @Description: 入库反馈接口
	* @param @param dataBytes
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="inWmsFeedback",method = RequestMethod.POST)
	public Map<String,Object> inWmsFeedback(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = commonUtils.validateRequestToken((String) map.get("token"));
		if(!"true".equals(result.get("resultFlag"))) {
			return result;
	    }
		
		Map<String, Object> data = null;
		try {
			OperationResult or = deWarehouseService.receiveInOrderFeedback(data = (Map<String, Object>) map.get("data"));
			String bv = String.valueOf(0 == or.getErrorCode());
			result.put("resultFlag", bv);
			result.put(0 == or.getErrorCode() ? "success" : "error", bv);
			result.put("resultCode", or.getDescription());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("resultFlag", "false");
			result.put("error", e instanceof Oms2WmsException ? e.getMessage() :  "入库反馈失败，请稍后再试或者联系系统管理员查看！");
			result.put("resultCode", e instanceof Oms2WmsException ? ((Oms2WmsException) e).getCode() : "1100");
			String description = "request=" + JsonConvertUtil.getJsonString(data) + "; response=" + JsonConvertUtil.getJsonString(result);
			this.deWarehouseService.recordOperFailedLog("receive-in-order-feedback", null, description);
		}
		return result;
	}
}
