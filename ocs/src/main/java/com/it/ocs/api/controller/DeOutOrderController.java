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

import com.it.ocs.api.constant.WarehouseConstant;
import com.it.ocs.api.ex.Oms2WmsException;
import com.it.ocs.api.ex.SkuInventoryException;
import com.it.ocs.api.model.DeAbnormalReasonModel;
import com.it.ocs.api.service.IDeWarehouseService;
import com.it.ocs.api.utils.AbnormalReasonUtils;
import com.it.ocs.api.utils.SupportTimestampCustomDateEditor;
import com.it.ocs.api.vo.AbnormalReasonVO;
import com.it.ocs.api.vo.OutOrderChangeVO;
import com.it.ocs.api.vo.OutOrderVO;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.StringUtil;

@Controller
@RequestMapping("/wms/deoutorder")
public class DeOutOrderController {
	
	private static final Logger logger = Logger.getLogger(DeOutOrderController.class);
	
	@Autowired
	private IDeWarehouseService deWarehouseService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	    dateFormat.setLenient(false);  
	    binder.registerCustomEditor(Date.class, new SupportTimestampCustomDateEditor(dateFormat));  
	}
	
	@RequestMapping("/index")
	public String index() {
		return "admin/wms/deoutorder";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody 
	public ResponseResult<OutOrderVO> list(com.it.ocs.common.RequestParam param) {
		return deWarehouseService.findDeOutOrders(param);
	}

	@RequestMapping(value="/saveChanges")
	@ResponseBody 
	public OperationResult saveChanges(OutOrderChangeVO vo) {
		logger.debug(StringUtil.instanceDetail(vo));
		try {
			return this.deWarehouseService.changeBeforeSendWms(vo);
		} catch(Oms2WmsException e) {
			logger.error(e.getMessage());
			this.deWarehouseService.recordOperFailedLog("change-out-order", vo == null ? null : vo.getId(), e.getMessage() + ": " + StringUtil.instanceDetail(vo));
			return new OperationResult(1, e.getMessage(), null, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.deWarehouseService.recordOperFailedLog("change-out-order", vo == null ? null : vo.getId(), e.getMessage() + ": " + StringUtil.instanceDetail(vo));
			return new OperationResult(1, "修改失败，请稍后再试", null, null);
		}
	}
	
	@RequestMapping(value="/send")
	@ResponseBody 
	public OperationResult send(@RequestParam("id") Long id, @RequestParam("orderId") String orderId, @RequestParam("orderOcsId") Long orderOcsId) {
		logger.debug(">>> send >>> id=" + id + "; orderId=" + orderId + "; orderOcsId=" + orderOcsId);
		try {
			return this.deWarehouseService.sendOutOrder(id, orderId, orderOcsId);
		} catch(SkuInventoryException e) {
			this.deWarehouseService.recordOperFailedLog("send-out-order", id, e.getMessage());
			try {
				DeAbnormalReasonModel reason = AbnormalReasonUtils.newOut(id, AbnormalReasonUtils.ACTION_SOM_CSI, e.getMessage());
				this.deWarehouseService.afterOperateDeOutOrder(id, WarehouseConstant.IS_SEND_WMS_FAILED, WarehouseConstant.IS_ABNORMAL_1, reason);
			} catch (Exception e1) {
				this.deWarehouseService.recordOperFailedLog("after-send-out-order", id, e.getMessage());
				logger.error(">>> send >>> " + e.getMessage(), e);
			}
			return new OperationResult(2, e.getMessage(), null, null);
		} catch(Oms2WmsException e) {
			logger.error(e.getMessage());
			try {
				DeAbnormalReasonModel reason = AbnormalReasonUtils.newOut(id, AbnormalReasonUtils.ACTION_SOT_OIE, e.getMessage());
				this.deWarehouseService.afterOperateDeOutOrder(id, WarehouseConstant.IS_SEND_WMS_FAILED, WarehouseConstant.IS_ABNORMAL_1, reason);
			} catch (Exception e1) {
				this.deWarehouseService.recordOperFailedLog("after-send-out-order", id, e.getMessage());
				logger.error(">>> send >>> " + e.getMessage(), e);
			}
			this.deWarehouseService.recordOperFailedLog("send-out-order", id, e.getMessage());
			return new OperationResult(1, e.getMessage(), null, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.deWarehouseService.recordOperFailedLog("send-out-order", id, e.getMessage());
			return new OperationResult(1, "推送失败，请稍后再试", null, null);
		}
	}

	@RequestMapping(value="/cancel")
	@ResponseBody 
	public OperationResult cancel(@RequestParam("id") Long id, @RequestParam("orderId") String orderId, @RequestParam("orderOcsId") Long orderOcsId) {
		logger.debug(">>> cancel >>> id=" + id + "; orderId=" + orderId + "; orderOcsId=" + orderOcsId);
		try {
			return this.deWarehouseService.cancelOutOrder(id, orderId, orderOcsId);
		} catch(Oms2WmsException e) {
			logger.error(e.getMessage());
			this.deWarehouseService.recordOperFailedLog("cancel-out-order", id, e.getMessage());
			return new OperationResult(1, e.getMessage(), null, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.deWarehouseService.recordOperFailedLog("cancel-out-order", id, e.getMessage());
			return new OperationResult(1, "出库单取消失败，请稍后再试", null, null);
		}
	}
	
	@RequestMapping(value="/abnormalReasonList")
	@ResponseBody
	public ResponseResult<AbnormalReasonVO> abnormalReasonList(com.it.ocs.common.RequestParam param) {
		return deWarehouseService.findAbnormalReasons(param);
	}

	@RequestMapping(value="/syncWmsDisease")
	@ResponseBody
	public OperationResult syncWmsDisease() {
		return deWarehouseService.syncWmsDiseaseOutOrders();
	}
}
