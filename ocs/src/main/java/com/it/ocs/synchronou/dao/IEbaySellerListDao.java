package com.it.ocs.synchronou.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.OperationResult;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.salesStatistics.vo.SaleOrderRefundVo;
import com.it.ocs.synchronou.model.EbayLabelModel;
import com.it.ocs.synchronou.model.EbayShipUploadModel;
import com.it.ocs.synchronou.model.SaleInfoModel;
import com.it.ocs.synchronou.model.SaleSubItemModel;
import com.it.ocs.synchronou.vo.SaleInfoVO;

public interface IEbaySellerListDao {

	public int isExist(Map<String, Object> item);

	public void addSaleInfo(Map<String, Object> map);

	public Long getId();

	public void addSaleSkuInfo(@Param("list") List<Map<String, Object>> skus, @Param("id") Long id);

	public Long getIdByOrderId(String orderId);

	public void addOrderInfo(Map<String, Object> map);

	public void addOrderSkuInfo(@Param("list") List<Map<String, Object>> items, @Param("id") Long id);

	public void updateOrderInfo(Map<String, Object> map);

	public List<SaleInfoModel> queryByPage(@Param("saleInfoVO") Map<String, Object> map,
			@Param("startRow") int startRow, @Param("endRow") int endRow);

	public int count(@Param("saleInfoVO") Map<String, Object> map);

	public List<Map<String, Object>> getNoShipData(Map<String, Object> pram);

	public void updateOrderTrackingNumber(Map<String, Object> map);

	public List<SaleSubItemModel> getSubItemById(String parentId);

	public Integer getSendBillCount(String orderId);

	public SaleInfoModel getOrderByOrderId(Map<String, Object> map);

	public int hasSend(Map<String, Object> map);

	public void updateSendRecord(Map<String, Object> map);

	public void addSendRecord(Map<String, Object> map);

	public SaleInfoModel getOrderById(String id);

	public String getStartTime();

	public void insertTimeSet(Map<String, Object> timeSetMap);

	public int getTimeSetId();

	public void udpateTimeSet(Map<String, Object> timeSetMap);

	public Map<String, Object> getOrderTranInfo(String parentId);

	public List<Map<String, Object>> getOrderItemById(String parentId);

	public Map<String, Object> getItemInfo(String itemId);

	public void updateOrderSkuInfo(@Param("list") List<Map<String, Object>> items, @Param("id") Long id);

	public int countOrderNoPaid();

	public int countOrderPaid();

	public int countOrderNoShipped();

	public int countOrderShipped();

	public List<SaleOrderRefundVo> getSaleOrderRefundByParentId(Long parentId);

	public List<ComboBoxVO> getItemIdById(String parentId);

	public void addTrackNumberUploadRecord(Map<String, Object> map);

	public String getItemEbayImage(String itemId);

	public List<Map<String, String>> getItemInfoByOrderSEQId(String[] idsArray);

	public int itemExistOrder(Map<String, Object> map);

	public void updateOrderShipTime(Map<String, Object> map);

	public void remarkNoItem(List<String> ids);

	public void unRemarkNoItem(List<String> ids);

	public int remarkContent(Map<String, Object> model);

	public String getSiteIdBySiteName(String siteName);

	public void addCancelRecord(Map<String, String> map);

	public List<EbayShipUploadModel> getUploadsByAccount(String account);

	public void updateOrderShipNumberByOCS(EbayShipUploadModel ship);

	public void updateUploadData(EbayShipUploadModel ship);

	public void updateUploadDataByFail(EbayShipUploadModel ship);

	public List<EbayShipUploadModel> shipUploadRecord(@Param("param") Map<String, Object> map, @Param("startRow")int startRow, @Param("endRow")int endRow);

	public int shipUploadRecordCount(@Param("param")Map<String, Object> map);

	public void cancelUpload(String id);

	public void updateReplaceOrder(Map<String, String> orderParam);

	public Map<String, Object> getOrderInfoToSendMessage(Map<String, String> orderParam);

	public void updateOrderShipMarkByOCS(EbayShipUploadModel ship);

	public String getPaypalAccessTokenByPaypalAccount(String account);

	public Map<String, Object> getPaypalAccountInfo(String account);

	public void addCurToken(Map<String, Object> paypalAccountInfo);

	public List<SaleInfoModel> getNoEmailOrders();

	public void updateOrderUserEmail(SaleInfoModel sale);

	public List<EbayLabelModel> getMoveMenuList();

	public void labelOrder(Map<String, Object> label);

	public void addLabel(EbayLabelModel label);

	public void updateLabel(EbayLabelModel label);

	public void deleteLabel(String id);

	public void updateOCSOrderInfo(Map<String, String> orderParam);

}
