package com.it.ocs.eda.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.eda.model.InventoryHistoryModel;
import com.it.ocs.eda.model.OrderExportOfUSWestModel;
import com.it.ocs.eda.model.WestShippingModel;
import com.it.ocs.eda.vo.EDAOrderVO;
import com.it.ocs.eda.vo.EDAStockVO;
import com.it.ocs.eda.vo.SKULinkVO;
import com.it.ocs.publication.vo.ComboBoxVO;

public interface IEDAManagerService {

	public ResponseResult<EDAOrderVO> findEDAOrderList(RequestParam param);

	public OperationResult getEDAOrderInfoById(String edaId);

	public OperationResult cancelEDAOrderById(String id);

	public ResponseResult<EDAStockVO> stockList(RequestParam param);

	public ResponseResult<SKULinkVO> skuLinkList(RequestParam param);

	public ResponseResult<SKULinkVO> productList(RequestParam param);

	public OperationResult skuLinkSave(Map<String, Object> map);

	public List<SKULinkVO> getSkusByPSku(String psku);

	public OperationResult pSkuIsLikeSku(String psku);

	public ResponseResult<WestShippingModel> westOrderList(RequestParam param);

	public OperationResult cancelWestOrderById(String id);

	public OperationResult toEastShipping(String id);

	public OperationResult addressUpdate(Map<String,Object> west);

	public OperationResult edaAddressUpdate(Map<String, Object> eda);

	public List<Map<String, Object>> getOrder(Map<String, Object> eda);

	public OperationResult addOrder(Map<String, Object> map);

	public ResponseResult<InventoryHistoryModel> inventoryHistoryList(RequestParam param);

	public List<ComboBoxVO> getRecordType();

	public OperationResult getEdaInfoForToEastOrder(String orderId);

	public ResponseResult<EDAStockVO> stockListHis(RequestParam param);


}
