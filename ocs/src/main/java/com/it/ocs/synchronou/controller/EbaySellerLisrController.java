package com.it.ocs.synchronou.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.synchronou.model.EbayLabelModel;
import com.it.ocs.synchronou.model.EbayShipUploadModel;
import com.it.ocs.synchronou.model.SaleSubItemModel;
import com.it.ocs.synchronou.service.IEbaySellerListService;
import com.it.ocs.synchronou.vo.SaleInfoVO;

@Controller
@RequestMapping(value = "/ebaySeller")
public class EbaySellerLisrController {

	@Autowired
	private IEbaySellerListService ebaySellerListService;

	@RequestMapping(value = "/show")
	public String show() {
		return "admin/ebaySynchronous/syncSaleInfo";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseResult<SaleInfoVO> findSaleList(RequestParam param) {
		return ebaySellerListService.getList(param);
	}

	@RequestMapping(value = "/syncOrderList", method = RequestMethod.GET)
	@ResponseBody
	public OperationResult syncOrderListByPage() {
		return ebaySellerListService.syncOrderListByPage();
	}

	@RequestMapping(value = "/syncOrderByOrderId/{orderId}/{account}/1", method = RequestMethod.GET)
	@ResponseBody
	public OperationResult syncOrderByOrderId(@PathVariable("orderId") String orderId,
			@PathVariable("account") String account) {
		return ebaySellerListService.syncOrderByOrderId(orderId, account);
	}

	@RequestMapping(value = "/getSubItemById/{parentId}")
	@ResponseBody
	public List<SaleSubItemModel> getSubItemById(@PathVariable("parentId") String parentId) {
		return ebaySellerListService.getSubItemById(parentId);
	}

	@RequestMapping(value = "/uploadTranNumber", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult uploadTranNumber(@RequestBody Map<String, Object> row) {
		return ebaySellerListService.uploadTranNumber(row);
	}

	@RequestMapping(value = "/getSendBillCount/{orderId}", method = RequestMethod.GET)
	@ResponseBody
	public OperationResult getSendBillCount(@PathVariable("orderId") String orderId) {
		return ebaySellerListService.getSendBillCount(orderId);
	}

	@RequestMapping(value = "/sendBill", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult sendBill(@RequestBody List<Map<String, Object>> list) {
		return ebaySellerListService.sendBill(list);
	}

	@RequestMapping(value = "/cancelOrder/{parentId}/{cause}", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult cancelOrder(@PathVariable("parentId") String parentId, @PathVariable("cause") String cause) {
		return ebaySellerListService.cancelOrder(parentId, cause);
	}

	@RequestMapping(value = "/getOrderTranInfo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OperationResult getOrderTranInfo(@PathVariable("id") String parentId) {
		return ebaySellerListService.getOrderTranInfo(parentId);
	}

	@RequestMapping(value = "/getOrderItemById/{id}", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getOrderItemById(@PathVariable("id") String parentId) {
		return ebaySellerListService.getOrderItemById(parentId);
	}

	@RequestMapping(value = "/getItemInfo/{itemId}", method = RequestMethod.GET)
	@ResponseBody
	public OperationResult getItemInfo(@PathVariable("itemId") String itemId) {
		return ebaySellerListService.getItemInfo(itemId);
	}

	@RequestMapping(value = "/countOrderByStatus/{num}", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult countOrderByStatus(@RequestBody Map<String, Object> map,@PathVariable("num") String num) {
		return ebaySellerListService.countOrderByStatus(num,map);
	}

	@RequestMapping("/getSaleOrderRefundByParentId")
	@ResponseBody
	public Map<String, Object> getSaleOrderRefundByParentId(@RequestBody Map<String, Object> map) {
		return ebaySellerListService.getSaleOrderRefundByParentId(map);
	}
	
	@RequestMapping(value="/sendUseMessage",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult sendUseMessage(@RequestBody Map<String,Object> messages){
		return ebaySellerListService.sendUseMessage(messages);
	}
	
	@RequestMapping(value = "/getItemIdById/{id}", method = RequestMethod.POST)
	@ResponseBody
	public List<ComboBoxVO> getItemIdById(@PathVariable("id") String parentId) {
		return ebaySellerListService.getItemIdById(parentId);
	}
	
	
	@RequestMapping(value = "/getItemEbayImage/{itemId}", method = RequestMethod.GET)
	@ResponseBody
	public OperationResult getItemEbayImage(@PathVariable("itemId") String itemId) {
		return ebaySellerListService.getItemEbayImage(itemId);
	}
	
	@RequestMapping(value="/getItemInfoByOrderSEQId",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,String>> getItemInfoByOrderSEQId(@RequestBody Map<String,Object> ids){
		return ebaySellerListService.getItemInfoByOrderSEQId(ids);
	}
	
	@RequestMapping(value="/remarkNoItem",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult remarkNoItem(@RequestBody Map<String,Object> map){
		return ebaySellerListService.remarkNoItem(map);
	}
	
	@RequestMapping(value="/remarkContent",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult remarkContent(@RequestBody Map<String,Object> model){
		return ebaySellerListService.remarkContent(model);
	}
	
	@RequestMapping(value = "/shipUploadRecord")
	@ResponseBody
	public ResponseResult<EbayShipUploadModel> shipUploadRecord(RequestParam param) {
		return ebaySellerListService.shipUploadRecord(param);
	}
	
	@RequestMapping(value="/cancelUpload/{id}",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult cancelUpload(@PathVariable("id")String id){
		return ebaySellerListService.cancelUpload(id);
	}
	
	
	@RequestMapping(value = "/getMoveMenuList", method = RequestMethod.POST)
	@ResponseBody
	public List<EbayLabelModel> getMoveMenuList() {
		return ebaySellerListService.getMoveMenuList();
	}
	
	
	@RequestMapping(value="/labelOrder",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult labelOrder(@RequestBody Map<String,Object> label){
		return ebaySellerListService.labelOrder(label);
	}
	
	@RequestMapping(value="/addLabel",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult addLabel(@RequestBody EbayLabelModel label){
		return ebaySellerListService.addLabel(label);
	}
	
	@RequestMapping(value="/deleteLabel/{id}",method=RequestMethod.POST)
	@ResponseBody
	public OperationResult deleteLabel(@PathVariable("id")String id){
		return ebaySellerListService.deleteLabel(id);
	}
}

