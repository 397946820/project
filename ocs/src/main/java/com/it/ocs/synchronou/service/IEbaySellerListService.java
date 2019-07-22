package com.it.ocs.synchronou.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.synchronou.model.EbayLabelModel;
import com.it.ocs.synchronou.model.EbayShipUploadModel;
import com.it.ocs.synchronou.model.SaleSubItemModel;
import com.it.ocs.synchronou.vo.SaleInfoVO;

public interface IEbaySellerListService {

	public ResponseResult<SaleInfoVO> getList(RequestParam param);

	public OperationResult syncOrderList();

	public OperationResult syncOrderByOrderId(String orderId, String account);

	public List<SaleSubItemModel> getSubItemById(String parentId);

	public OperationResult uploadTranNumber(Map<String, Object> map);

	public OperationResult getSendBillCount(String orderId);

	public OperationResult sendBill(List<Map<String,Object>> list);

	public OperationResult cancelOrder(String parentId, String cause);

	public OperationResult getOrderTranInfo(String parentId);

	public List<Map<String,Object>> getOrderItemById(String parentId);

	public OperationResult getItemInfo(String itemId);

	public OperationResult countOrderByStatus(String num, Map<String, Object> map);

	public Map<String, Object> getSaleOrderRefundByParentId(Map<String, Object> map);

	public OperationResult sendUseMessage(Map<String, Object> messages);

	public List<ComboBoxVO> getItemIdById(String parentId);

	public OperationResult syncOrderListByPage();

	public OperationResult getItemEbayImage(String itemId);

	public List<Map<String, String>> getItemInfoByOrderSEQId(Map<String, Object> ids);

	public OperationResult remarkNoItem(Map<String, Object> map);

	public OperationResult remarkContent(Map<String, Object> model);

	public ResponseResult<EbayShipUploadModel> shipUploadRecord(RequestParam param);

	public OperationResult cancelUpload(String id);

	public List<EbayLabelModel> getMoveMenuList();

	public OperationResult labelOrder(Map<String, Object> label);

	public OperationResult addLabel(EbayLabelModel label);

	public OperationResult deleteLabel(String id);
}
