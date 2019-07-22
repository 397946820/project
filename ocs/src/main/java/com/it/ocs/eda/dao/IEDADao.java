package com.it.ocs.eda.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.eda.model.EBayOrderShippingModel;
import com.it.ocs.eda.model.EDAOrderModel;
import com.it.ocs.eda.model.InventoryHistoryModel;
import com.it.ocs.eda.model.OrderExportOfUSWestModel;
import com.it.ocs.eda.model.ShipOrderInfo;
import com.it.ocs.eda.model.WestShippingModel;
import com.it.ocs.eda.vo.EDAOrderVO;
import com.it.ocs.eda.vo.EDAStockVO;
import com.it.ocs.eda.vo.SKULinkVO;
import com.it.ocs.publication.vo.ComboBoxVO;

public interface IEDADao {

	public void addOrderToEDA();

	public List<Map<String, Object>> getCreateEDAOrder();

	public Map<String, Object> getEDAAccount(@Param("methodName")String methodName, @Param("edaAccount")String edaAccount);

	public void updateEDAOrder(Map<String, Object> order);

	public List<Map<String, Object>> getEDAOrder();

	public int isExist(String parentId);

	public void addEDAOrderInfo(Map<String, Object> data);

	public void updateEDAOrderInfo(Map<String, Object> data);

	public void updateEDAOrderInfoGetStatus(String id);

	public List<EDAOrderVO> queryEDAOrderByPage(@Param("param")Map<String, Object> map,@Param("start")int start,@Param("end")int end);

	public int countEDAOrder(@Param("param")Map<String, Object> map);

	public Map<String, Object> getEDAOrderByEDAId(String edaId);

	public Map<String, Object> getEDAOrderById(String id);

	public void updateEDAOrderNoCreate(String id);

	public List<Map<String, Object>> getAllAccount(String method);

	public int skuStockIsExist(Map<String, Object> map);
	
	public void addSkuStockInfoHis(Map<String, Object> map);

	public void addSkuStockInfo(Map<String, Object> map);

	public void updateSkuStockInfo(Map<String, Object> map);

	public int countEDAStockInfo(@Param("param")Map<String, Object> map);

	public List<EDAStockVO> queryEDAStockInfoByPage(@Param("param")Map<String, Object> map, @Param("start")int startRow, @Param("end")int endRow);
	
	public int countEDAStockInfoHis(@Param("param")Map<String, Object> map);

	public List<EDAStockVO> queryEDAStockInfoHisByPage(@Param("param")Map<String, Object> map, @Param("start")int startRow, @Param("end")int endRow);
	
	public List<SKULinkVO> productListByPage(@Param("param")Map<String, Object> map, @Param("start")int startRow, @Param("end")int endRow);

	public int productListCount(@Param("param")Map<String, Object> map);

	public void updatePSkuLinkInfo(Map<String, Object> map);

	public void deletePSkuLinkInfo(Map<String, Object> sku);

	public List<SKULinkVO> skuLinkListByPage(@Param("param")Map<String, Object> map, @Param("start")int startRow, @Param("end")int endRow);

	public int skuLinkListCount(@Param("param")Map<String, Object> map);

	public List<SKULinkVO> getSkusByPSku(String psku);

	public List<Map<String, Object>> getPSkuLinkDatas();
	
	public List<EDAOrderModel> getEDAOrderPlatform();
	
	public List<EDAOrderModel> getEDAOrderByPlatform(String platform);
	
	public List<EBayOrderShippingModel> getEbayOrder(EDAOrderModel edaModel);

	public int countPSku(String pSku);

	public int skuIsExist(String sku0);

	public void updateEbayOrderShipInfo(EBayOrderShippingModel skuOrder);

	public List<ShipOrderInfo> getEBayUSShipOrders();

	public void addEdaOrders(ShipOrderInfo edaOrder);

	public List<ShipOrderInfo> getWalmartShipOrders();

	public List<ShipOrderInfo> getLightShipOrders();

	public Double getSKUInfo(String sku);

	public int isExistInWest(@Param("param")Map<String, Object> west);

	public void addWest(Map<String, Object> west);

	public List<WestShippingModel> westOrderList(@Param("param")Map<String, Object> map, @Param("start")int startRow,  @Param("end")int endRow);

	public int countWestOrderList(@Param("param")Map<String, Object> map);

	public List<Map<String, Object>> exportDataByParam(@Param("param")Map<String, Object> param);

	public void cancelWestOrderById(Map<String, Object> map);

	public void updateTranckingNumber(Map<String, Object> row);

	public List<Map<String, Object>> exportChooseData(String[] ids);

	public List<ShipOrderInfo> getEDAEastOrderById(String id);

	public void updateWestToEast(String id);

	public List<Map<String, Object>> getWalmartOrderInfoByShip(Map<String, Object> order);

	public String getLightOrderForShip(Map<String, Object> order);

	public void updateUploadStatus(Map<String, Object> order);

	public void updateUploadStatusFail(Map<String, Object> order);

	public List<Map<String, Object>> getImportShips();

	public EBayOrderShippingModel getEbayOrderItemById(Map<String, Object> map);
	
	public List<EBayOrderShippingModel> getEbayOrderItemByParentId(@Param("parentId") Long parentId);

	public void updateWestShipStatus(Map<String, Object> map);

	public List<Map<String, Object>> getWalmartOrderItemInfoByShip(Map<String, Object> map);

	public List<Map<String, Object>> noUploadOrder();

	public int edaOrderIsExist(Map<String, String> param);

	public Integer getIQty(String warehouseSku);

	public int isCreateInWest(ShipOrderInfo edaOrder);

	public int isCreateInEast(Map<String, Object> west);

	public void addressUpdate(Map<String,Object> west);

	public void edaAddressUpdate(Map<String, Object> eda);

	public String getLightOrderItemIdBySku(Map<String, Object> map);

	public List<Map<String, Object>> getOrder(Map<String, Object> eda);

	public Map<String, Object> getOrderItemInfoInEast(Map<String, Object> sku);

	public List<Map<String, Object>> getOrderItemInfoInWest(Map<String, Object> sku);

	public int hasWest(String orderId);

	public int hasHand(String orderId);

	public void updateSysWestOrder(String orderId);

	public int shipIsExist(Map<String, Object> row);

	public String getEDAOrderShipInfoByParentId(String id);

	public void updateEDAOrderIsUpload(String id);

	public int shippingNumberHasUpload(Map<String, Object> map);

	public List<ShipOrderInfo> getWShipOrders();

	public List<WestShippingModel> getWestShipByShipOrderId(String id);

	public void addEDAOrder(EDAOrderModel edaOrder);

	public void updateWestWOrderToEast(String orderId);

	public void updateWOrderTranckNumber(Map<String, Object> map);

	public Map<String, Object> getOrderByWOrder(Map<String, Object> map);

	public void updateEbayOrderRemark(Map<String, Object> map);

	public int inventoryHistoryExist(InventoryHistoryModel ih);

	public void insertInventoryHistory(InventoryHistoryModel ih);

	public int inventoryHistoryExist2(InventoryHistoryModel ih);

	public int countInventoryHistory(@Param("param")Map<String, Object> map);

	public List<InventoryHistoryModel> queryInventoryHistoryByPage(@Param("param")Map<String, Object> map,  @Param("start")int startRow, @Param("end")int endRow);

	public List<ComboBoxVO> getRecordType();

	public List<Map<String, Object>> exportInventoryHistoryDataByParam(@Param("param")Map<String, Object> param);

	public Map<String, Object> getEDAOrderInfoByORAId(String billNum);

	public String getEdaInfoForToEastOrder(String orderId);

	public List<EDAOrderVO> getOrderShippingEastByCondition(@Param("param")Map<String, Object> param);

	public List<ShipOrderInfo> getOCSShipOrders();

	public String getCOrderId(String orderId);

	public void updateTranckingNumberMPS(Map<String, Object> row);

	public List<Map<String, Object>> getTotalByRecordType(@Param("param")Map<String, Object> map);



}
