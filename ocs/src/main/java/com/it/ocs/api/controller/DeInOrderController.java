package com.it.ocs.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.api.ex.Oms2WmsException;
import com.it.ocs.api.service.IDeInOrderService;
import com.it.ocs.api.service.IDeOperLogService;
import com.it.ocs.api.service.IDeWarehouseService;
import com.it.ocs.api.utils.SupportTimestampCustomDateEditor;
import com.it.ocs.api.vo.AbnormalReasonVO;
import com.it.ocs.api.vo.InOrderVO;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;

@Controller
@RequestMapping("/wms/deinorder")
public class DeInOrderController {
	
	private static final Logger logger = Logger.getLogger(DeInOrderController.class);
	
	@Autowired
	private IDeInOrderService deInOrderService;

	@Autowired
	private IDeWarehouseService deWarehouseService;
	
	@Autowired
	private IDeOperLogService deOperLogService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	    dateFormat.setLenient(false);  
	    binder.registerCustomEditor(Date.class, new SupportTimestampCustomDateEditor(dateFormat));  
	}
	
	@RequestMapping("/index")
	public String index() {
		return "admin/wms/deinorder";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody 
	public ResponseResult<InOrderVO> list(com.it.ocs.common.RequestParam param) {
		return this.deInOrderService.queryByPage(param);
	}

	@RequestMapping(value="/send")
	@ResponseBody 
	public OperationResult send(@RequestParam("id") Long id, @RequestParam("orderId") String orderId, @RequestParam("orderOcsId") Long orderOcsId) {
		logger.debug(">>> send >>> id=" + id + "; orderId=" + orderId + "; orderOcsId=" + orderOcsId);
		try {
			return this.deInOrderService.resend(id, orderId, orderOcsId);
		} catch(Oms2WmsException e) {
			logger.error(e.getMessage());
			this.deOperLogService.recordOperFailedLog("send-in-order", id, e.getMessage());
			return new OperationResult(1, e.getMessage(), null, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.deOperLogService.recordOperFailedLog("send-in-order", id, e.getMessage());
			return new OperationResult(1, "入库单推送失败，请联系管理员！", null, null);
		}
	}

	@RequestMapping(value="/cancel")
	@ResponseBody 
	public OperationResult cancel(@RequestParam("id") Long id, @RequestParam("orderId") String orderId, @RequestParam("orderOcsId") Long orderOcsId) {
		logger.debug(">>> cancel >>> id=" + id + "; orderId=" + orderId + "; orderOcsId=" + orderOcsId);
		try {
			return this.deInOrderService.cancel(id, orderId, orderOcsId);
		} catch(Oms2WmsException e) {
			logger.error(e.getMessage());
			this.deWarehouseService.recordOperFailedLog("cancel-in-order", id, e.getMessage());
			return new OperationResult(1, e.getMessage(), null, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.deWarehouseService.recordOperFailedLog("cancel-in-order", id, e.getMessage());
			return new OperationResult(1, "入库单取消失败，请稍后再试！", null, null);
		}
	}
	
	@RequestMapping(value="/abnormalReasonList")
	@ResponseBody
	public ResponseResult<AbnormalReasonVO> abnormalReasonList(com.it.ocs.common.RequestParam param) {
		return deWarehouseService.findAbnormalReasons(param);
	}

	@RequestMapping(value="/claimList")
	@ResponseBody
	public ResponseResult<InOrderVO> claimList(com.it.ocs.common.RequestParam param) {
		return this.deInOrderService.queryWmsClaimOrderByPage(param);
	}

	@RequestMapping(value="/claimOwnerList")
	@ResponseBody
	public ResponseResult<InOrderVO> claimOwnerList(com.it.ocs.common.RequestParam param) {
		return this.deInOrderService.queryWmsClaimOwnerOrderByPage(param);
	}

	@RequestMapping(value="/main")
	@ResponseBody
	public ResponseResult<InOrderVO> main(@RequestParam("id") Long id) {
		return this.deInOrderService.queryMainById(id);
	}

	@RequestMapping(value="/claim")
	@ResponseBody
	public OperationResult claim(@RequestParam("claim") Long claim, @RequestParam("owner") Long owner) {
		logger.debug(">>> claim >>> claim=" + claim + "; owner=" + owner);
		try {
			return this.deInOrderService.claiming(claim, owner);
		} catch(Oms2WmsException e) {
			logger.error(e.getMessage());
			this.deWarehouseService.recordOperFailedLog("claim-in-order", claim, e.getMessage());
			return new OperationResult(1, e.getMessage(), null, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.deWarehouseService.recordOperFailedLog("claim-in-order", claim, e.getMessage());
			return new OperationResult(1, "认领操作失败，请稍后再试！", null, null);
		}
	}
	
}
