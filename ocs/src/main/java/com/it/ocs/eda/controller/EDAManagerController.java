package com.it.ocs.eda.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.eda.model.InventoryHistoryModel;
import com.it.ocs.eda.model.WestShippingModel;
import com.it.ocs.eda.service.IEDAManagerService;
import com.it.ocs.eda.vo.EDAOrderVO;
import com.it.ocs.eda.vo.EDAStockVO;
import com.it.ocs.eda.vo.SKULinkVO;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.sys.core.SystemLog;

@RequestMapping("/edaManager")
@Controller
public class EDAManagerController {
	
	@Autowired
	private IEDAManagerService eDAManagerService;
	
	@RequestMapping("/show")
	public String show() {
		return "admin/eda/edaManager";
	}
	
	@RequestMapping("/westShippingShow")
	public String westShippingShow() {
		return "admin/eda/USWestShipping";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody 
	public ResponseResult<EDAOrderVO> findEDAOrderList(RequestParam param){  
		return eDAManagerService.findEDAOrderList(param);
	}	
	
	@RequestMapping(value="/westOrderList")
	@ResponseBody 
	public ResponseResult<WestShippingModel> westOrderList(RequestParam param){  
		return eDAManagerService.westOrderList(param);
	}
	
	@RequestMapping(value="/getEDAOrderInfoById/{edaId}")
	@ResponseBody 
	public OperationResult getEDAOrderInfoById(@PathVariable("edaId")String edaId){  
		return eDAManagerService.getEDAOrderInfoById(edaId);
	}
	
	@SystemLog("取消EDA发货单")
	@RequestMapping(value="/cancelEDAOrderById/{id}")
	@ResponseBody 
	public OperationResult cancelEDAOrderById(@PathVariable("id")String id){  
		return eDAManagerService.cancelEDAOrderById(id);
	}
	
	@SystemLog("取消美西仓发货单")
	@RequestMapping(value="/cancelWestOrderById/{id}")
	@ResponseBody 
	public OperationResult cancelWestOrderById(@PathVariable("id")String id){  
		return eDAManagerService.cancelWestOrderById(id);
	}
	
	@RequestMapping("/stockShow")
	public String stockShow() {
		return "admin/eda/edaStockManager";
	}
	@RequestMapping("/stockHisShow")
	public String stockHisShow() {
		return "admin/eda/edaStockHisManager";
	}
	
	@RequestMapping(value="/stockList")
	@ResponseBody 
	public ResponseResult<EDAStockVO> stockList(RequestParam param){  
		return eDAManagerService.stockList(param);
	}
	
	@RequestMapping(value="/stockListHis")
	@ResponseBody 
	public ResponseResult<EDAStockVO> stockListHis(RequestParam param){  
		return eDAManagerService.stockListHis(param);
	}
	
	@RequestMapping("/skuLinkShow")
	public String skuLinkShow() {
		return "admin/eda/skuLinkManager";
	}
	
	@RequestMapping(value="/skuLinkList")
	@ResponseBody 
	public ResponseResult<SKULinkVO> skuLinkList(RequestParam param){  
		return eDAManagerService.skuLinkList(param);
	}
	
	@RequestMapping(value="/productList")
	@ResponseBody 
	public ResponseResult<SKULinkVO> productList(RequestParam param){  
		return eDAManagerService.productList(param);
	}
	
	@RequestMapping(value="/skuLinkSave")
	@ResponseBody 
	public OperationResult skuLinkSave(@RequestBody Map<String,Object> map){  
		return eDAManagerService.skuLinkSave(map);
	}
	
	@RequestMapping(value="/getSkusByPSku/{pSku}")
	@ResponseBody 
	public List<SKULinkVO> getSkusByPSku(@PathVariable("pSku")String psku){  
		return eDAManagerService.getSkusByPSku(psku);
	}
	
	
	@RequestMapping(value="/pSkuIsLikeSku/{pSku}")
	@ResponseBody 
	public OperationResult pSkuIsLikeSku(@PathVariable("pSku")String psku){  
		return eDAManagerService.pSkuIsLikeSku(psku);
	}
	
	@RequestMapping(value="/toEastShipping/{id}")
	@ResponseBody 
	public OperationResult toEastShipping(@PathVariable("id")String id){  
		return eDAManagerService.toEastShipping(id);
	}
	
	
	@RequestMapping(value="/addressUpdate")
	@ResponseBody 
	public OperationResult addressUpdate(@RequestBody Map<String,Object> west){  
		return eDAManagerService.addressUpdate(west);
	}
	
	@RequestMapping(value="/edaAddressUpdate")
	@ResponseBody 
	public OperationResult edaAddressUpdate(@RequestBody Map<String,Object> eda){  
		return eDAManagerService.edaAddressUpdate(eda);
	}
	
	@RequestMapping(value="/getOrder")
	@ResponseBody 
	public List<Map<String,Object>> getOrder(@RequestBody Map<String,Object> eda){  
		return eDAManagerService.getOrder(eda);
	}
	
	
	@RequestMapping(value="/addOrder")
	@ResponseBody 
	public OperationResult addOrder(@RequestBody Map<String,Object> map){  
		return eDAManagerService.addOrder(map);
	}
	
	@RequestMapping("/inventoryHistoryPage")
	public String inventoryHistoryPage() {
		return "admin/eda/inventoryHistory";
	}
	
	@RequestMapping(value="/inventoryHistoryList")
	@ResponseBody 
	public ResponseResult<InventoryHistoryModel> inventoryHistoryList(RequestParam param){  
		return eDAManagerService.inventoryHistoryList(param);
	}
	
	@RequestMapping("/getRecordType")
	@ResponseBody
	public List<ComboBoxVO> getRecordType(){
		return eDAManagerService.getRecordType();
	}
	
	@RequestMapping(value="/getEdaInfoForToEastOrder/{orderId}")
	@ResponseBody 
	public OperationResult getEdaInfoForToEastOrder(@PathVariable("orderId") String orderId){  
		return eDAManagerService.getEdaInfoForToEastOrder(orderId);
	}
}
