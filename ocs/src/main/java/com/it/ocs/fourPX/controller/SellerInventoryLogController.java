package com.it.ocs.fourPX.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.fourPX.service.SellerInventoryLogService;
import com.it.ocs.fourPX.vo.FPXStockVO;
import com.it.ocs.fourPX.vo.SellerInventoryLogVO;

@Controller
public class SellerInventoryLogController {
	
	@Autowired
	private SellerInventoryLogService silService;

	@RequestMapping("/fourpx/sil/index")
	public String index() {
		return "admin/fourpx/sil";
	}

	@RequestMapping(value="/fourpx/sil/list")
	@ResponseBody 
	public ResponseResult<SellerInventoryLogVO> list(RequestParam param) {
		return this.silService.selectByPage(param);
	}
	
	@RequestMapping(value="/fourpx/sil/getCodeType")
	@ResponseBody 
	public List<Map<String,String>> getCodeType() {
		return this.silService.getCodeMap();
	}
	@RequestMapping(value="/fourpx/sil/modifyCodeType")
	@ResponseBody 
	public OperationResult modifyCodeType(RequestParam param) {
		return this.silService.modifyCodeType(param);
	}
	
	@RequestMapping(value="/fourpx/sil/syncFrom4pxSil")
	@ResponseBody 
	public OperationResult syncFrom4pxSil() {
		return this.silService.syncFrom4pxSil(true, "US");
	}

	@RequestMapping(value="/fourpx/sil/syncDeFrom4pxSil")
	@ResponseBody 
	public OperationResult syncDeFrom4pxSil() {
		return this.silService.syncFrom4pxSil(true, "DE");
	}
	
	@RequestMapping("/fourpx/stockHisShow")
	public String stockHisShow() {
		return "admin/fourpx/fpxStockHisManager";
	}
	
	@RequestMapping(value="/fourpx/stockListHis")
	@ResponseBody 
	public ResponseResult<FPXStockVO> stockListHis(RequestParam param){  
		return silService.stockListHis(param);
	}
}
