package com.it.ocs.fourPX.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.fourPX.model.FpxStatus;
import com.it.ocs.fourPX.model.OutWarehouseStatus;
import com.it.ocs.fourPX.service.FpxOutWarehouseService;
import com.it.ocs.fourPX.vo.FpxOutWarehouseVO;
import com.it.ocs.synchronou.util.JsonConvertUtil;

@Controller
public class FpxOutWarehouseController {
	
	@Autowired
	private FpxOutWarehouseService fpxOutWarehouseService;
	
	@RequestMapping("/fourpx/outw/index")
	public String index() {
		return "admin/fourpx/outw";
	}

	@RequestMapping(value="/fourpx/outw/list")
	@ResponseBody 
	public ResponseResult<FpxOutWarehouseVO> list(com.it.ocs.common.RequestParam param) {
		return this.fpxOutWarehouseService.selectByPage(param);
	}
	
	@RequestMapping(value="/fourpx/outw/saveChanges")
	@ResponseBody 
	public OperationResult saveChanges(FpxOutWarehouseVO vo) {
		return this.fpxOutWarehouseService.saveChanges(vo);
	}
	
	@RequestMapping(value="/fourpx/outw/push")
	@ResponseBody 
	public OperationResult push(@RequestParam("id") Long id) {
		return this.fpxOutWarehouseService.push4px(id);
	}
	
	@RequestMapping(value="/fourpx/outw/batchPush")
	@ResponseBody 
	public OperationResult batchPush4px() {
		return this.fpxOutWarehouseService.batchPush4px();
	}

	@RequestMapping(value="/fourpx/outw/cancel")
	@ResponseBody 
	public OperationResult cancel(@RequestParam("id") Long id) {
		return this.fpxOutWarehouseService.cancel(id);
	}

	@RequestMapping(value="/fourpx/outw/syncOutsFromOrder")
	@ResponseBody 
	public OperationResult syncOutsFromOrder() {
		return this.fpxOutWarehouseService.syncOutFromUnshippedOrder(null);
	}
	
	@RequestMapping(value="/fourpx/outw/syncOutFrom4px")
	@ResponseBody
	public OperationResult syncOutFrom4px() {
		return this.fpxOutWarehouseService.syncOutFrom4px();
	}
	
	@RequestMapping(value="/fourpx/outw/enums")
	@ResponseBody
	public String enums() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fpxstatus", FpxStatus.asMap());
		map.put("status", OutWarehouseStatus.asMap());
		return JsonConvertUtil.obj2Json(map);
	}
}
