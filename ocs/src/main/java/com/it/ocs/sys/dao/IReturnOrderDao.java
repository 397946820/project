package com.it.ocs.sys.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.salesStatistics.vo.ReturnGoodsOrderVo;
import com.it.ocs.sys.model.ReturnOrderModel;
import com.it.ocs.sys.vo.ReturnOrderVo;

@Repository
public interface IReturnOrderDao {

	List<Map<String, String>> getReturnOrderCause(@Param(value = "causeType") Long causeType);
	
	List<Map<String,String>> getById(@Param(value="id") Long id);
	
	List<Map<String, String>> getReturnOrderCauseAll();

	void addReturnOrder(Map<String, Object> order);

	void addReturnOrderItem(Map<String, Object> orderItem);

	List<ReturnOrderModel> findAll(@Param(value = "param") ReturnOrderVo orderVo, @Param(value = "sort") String sort,
			@Param(value = "order") String order, @Param(value = "startRow") int startRow,
			@Param(value = "endRow") int endRow);

	int getTotal(@Param(value = "param") ReturnOrderVo orderVo);

	List<String> getAccountByPlatform(@Param(value = "platform") String platform);

	List<String> getSiteByPlatformAndAccount(@Param(value = "platform") String platform,
			@Param(value = "account") String account);

	void updateReturnOrder(Map<String, Object> order);

	void updateReturnOrderItem(Map<String, Object> orderItem);

	void addRefundOrder(Map<String, Object> hash);

	void cancelApplication(Long id);
	
	Map<String, Object> getAddress(Map<String, Object> map);

	String getPaypalTransactionnumber(@Param(value = "account") Object account,
			@Param(value = "orderId") Object orderId);

	String getPaypalAccount(@Param(value = "account") Object account, @Param(value = "orderId") Object orderId);

	Map<String, Object> getCatagories(String sku);

	String getSku(String sku);

	String getNickName(Object id);

	String getCurrency(Object countryId);

	Date getOrderTime(@Param(value = "platform") Object platform, @Param(value = "orderId") Object orderId,
			@Param(value = "account") Object account);

	Date getDate(Object id);
	/**
	 * 添加退款申请单数据
	 * @User wangqiang
	 * @date 2018年3月22日
	 * @param order
	 */
	void addReturnGoodsOrder(Map<String, Object> order);
	/**
	 * 返回RMA的序列
	 * @User wangqiang
	 * @date 2018年3月22日
	 * @return
	 */
	Integer findOrderReturnSeq();
	 
	/**|
	 * 获取原订单的sku数量 
	 * @User wangqiang
	 * @date 2018年3月23日
	 * @param order
	 * @deprecated 已过时， 建议使用同类的<code>findOrderSkuQty</code>函数处理相关业务
	 * @return
	 */
	int findInitSkuQty(Map<String, Object> order);
	
	/**|
	 * 查找已申请退货订单的sku数量 
	 * @User wangqiang
	 * @date 2018年3月23日
	 * @param order
	 * @deprecated 已过时， 建议使用同类的<code>findAlreadyReturnedSkuQty</code>函数处理相关业务
	 * @return
	 */
	int findAlreadyApplyReturnSkuQty(Map<String, Object> order);


	/**|
	 * 获取原订单的sku数量 
	 * @param orderId 订单Id
	 * @param entityId
	 * @param sku 退单货品sku代码
	 * @return {@link java.lang.Integer} 已申请退货的数量
	 */
	Integer findOrderSkuQty(@Param("orderId") String orderId, @Param("entityId") String entityId, @Param("sku") String sku);
	
	/**
	 * 查找已经申请退货的sku数量
	 * @param orderId 订单Id
	 * @param sku 退单货品sku代码
	 * @return {@link java.lang.Integer} 已申请退货的数量
	 */
	Integer findAlreadyReturnedSkuQty(@Param("orderId") String orderId, @Param("sku") String sku);

	List<ReturnGoodsOrderVo> findAppliedReturnOrders(@Param("entityId") Long entityId);

}
