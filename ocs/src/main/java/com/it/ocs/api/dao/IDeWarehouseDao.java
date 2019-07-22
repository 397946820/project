package com.it.ocs.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.api.model.DeAbnormalReasonModel;
import com.it.ocs.api.model.DeInWmsOrderDetailModel;
import com.it.ocs.api.model.DeInWmsOrderMainModel;
import com.it.ocs.api.model.DeOutWmsOrderDetailModel;
import com.it.ocs.api.model.DeOutWmsOrderMainModel;
import com.it.ocs.api.model.DeWmsOperateLogModel;
import com.it.ocs.api.vo.AbnormalReasonVO;
import com.it.ocs.api.vo.OperateLogVO;
import com.it.ocs.api.vo.OutOrderVO;

/**
* @ClassName: IDeWarehouseDao 
* @Description: 对接德国仓库DAO层接口
* @author wgc 
* @date 2018年4月9日 上午10:15:55 
*
 */
public interface IDeWarehouseDao {
	
	/**
	* @Title: getDENoShipOrderInfo 
	* @Description: 获取德国仓ebay平台待发货订单
	* @param @return    设定文件 
	* @return List<Map<String, Object>>    返回类型 
	* @throws
	 */
	public List<Map<String, Object>> getDENoShipOrderInfoFromEbay();
	
	/**
	* @Title: getDeNoshipOrderInfoFromLight 
	* @Description: 获取德国仓官网平台待发货订单
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map<String,Object>> getDeNoshipOrderInfoFromLight();

	/**
	 * 获取Amazon平台订单数据
	 * @return
	 */
	public List<Map<String, Object>> getDeNoshipOrderInfoFromAmazon();

	/**
	 * 获取VC订单数据
	 * @return
	 */
	public List<Map<String, Object>> getDeNoshipOrderInfoFromVC();
	
	/**
	* @Title: getDeInWmsOrderInfo 
	* @Description: 获取德国仓入库单
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map<String,Object>> getDeInWmsOrderInfos();
	
	/**
	* @Title: insertDeOutWmsOrderMain
	* @Description: 保存出库单主表
	* @param @param deOutWmsOrderMainModels    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void batchInsertDeOutWmsOrderMain(List<DeOutWmsOrderMainModel> deOutWmsOrderMainModels);
	
	/**
	* @Title: batchInsertDeInWmsOrderMain 
	* @Description: 保存入库单主表
	* @param @param deInWmsOrderMainModels    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void batchInsertDeInWmsOrderMain(List<DeInWmsOrderMainModel> deInWmsOrderMainModels);
	
	/**
	* @Title: batchUpdateDeOutWmsOrderMain 
	* @Description: 批量更新出库单主表
	* @param @param deOutWmsOrderMainModels    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void batchUpdateDeOutWmsOrderMain(List<DeOutWmsOrderMainModel> deOutWmsOrderMainModels);
	
	/**
	* @Title: updateDeOutWmsOrderMainByMap 
	* @Description: 通过map更新出库实体
	* @param @param map    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateDeOutWmsOrderMainByMap(Map<String,Object> map);
	
	/**
	* @Title: updateDeInWmsOrderMainByMap 
	* @Description: 通过map更新入库实体
	* @param @param map    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateDeInWmsOrderMainByMap(Map<String,Object> map);
	
	/**
	* @Title: batchUpdateDeInWmsOrderMain 
	* @Description: 批量更新入库单主表
	* @param @param deInWmsOrderMainModels    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void batchUpdateDeInWmsOrderMain(List<DeInWmsOrderMainModel> deInWmsOrderMainModels);
	
	/**
	* @Title: getOutWmsOrderMailByOrderId 
	* @Description: 查询出库订单主表
	* @param @param map
	* @param @return    设定文件 
	* @return DeOutWmsOrderMainModel    返回类型 
	* @throws
	 */
	public DeOutWmsOrderMainModel getOutWmsOrderMailByMap(Map<String, Object> map);
	
	/**
	* @Title: getOutWmsOrderDetailByMap 
	* @Description: 获取德国仓库出库单详情
	* @param @param map
	* @param @return    设定文件 
	* @return List<DeOutWmsOrderDetailModel>    返回类型 
	* @throws
	 */
	public List<DeOutWmsOrderDetailModel> getOutWmsOrderDetailByMap(Map<String,Object> map);
	
	/**
	* @Title: getInWmsOrderDetailByMap 
	* @Description: 获取德国仓库入库单详情
	* @param @param map
	* @param @return    设定文件 
	* @return List<DeInWmsOrderDetailModel>    返回类型 
	* @throws
	 */
	public List<DeInWmsOrderDetailModel> getInWmsOrderDetailByMap(Map<String,Object> map);
	
	/**
	* @Title: getOutWmsOrderMailByOrderId 
	* @Description: 查询入库订单主表
	* @param @param map
	* @param @return    设定文件 
	* @return DeOutWmsOrderMainModel    返回类型 
	* @throws
	 */
	public DeInWmsOrderMainModel getInWmsOrderMailByMap(Map<String, Object> map);
	
	/**
	* @Title: batchInsertDeOutWmsOrderDetail
	* @Description: 保存出库单详情表
	* @param @param deOutWmsOrderDetailModels    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void batchInsertDeOutWmsOrderDetail(List<DeOutWmsOrderDetailModel> deOutWmsOrderDetailModels);
	
	/**
	* @Title: batchInsertDeInWmsOrderDetail 
	* @Description: 保存入库单详情表
	* @param @param deInWmsOrderDetailModels    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void batchInsertDeInWmsOrderDetail(List<DeInWmsOrderDetailModel> deInWmsOrderDetailModels);
	
	/**
	* @Title: batchUpdateDeOutWmsOrderDetail 
	* @Description: 批量更新出库单详情表
	* @param @param deOutWmsOrderDetailModels    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void batchUpdateDeOutWmsOrderDetail(List<DeOutWmsOrderDetailModel> deOutWmsOrderDetailModels);
	
	/**
	* @Title: batchUpdateDeInWmsOrderDetail 
	* @Description: 批量更新入库单详情表
	* @param @param deInWmsOrderDetailModels    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void batchUpdateDeInWmsOrderDetail(List<DeInWmsOrderDetailModel> deInWmsOrderDetailModels);
	
	/**
	* @Title: getOutWmsOrderSequences 
	* @Description: 获取德国出库单主表序列
	* @param @return    设定文件 
	* @return long    返回类型 
	* @throws
	 */
	public long getOutWmsOrderSequences();
	
	/**
	 * @Title: getOutWmsOrderSequences 
	 * @Description: 获取德国入库单主表序列
	 * @param @return    设定文件 
	 * @return long    返回类型 
	 * @throws
	 */
	public long getInWmsOrderSequences();
	
	/**
	* @Title: getSkusByPSku 
	* @Description: 查找sku映射关系
	* @param @param map
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public Map<String,Object> getSkusByPSku(Map<String,Object> map);
	
	/**
	 * 获取待推送到wms的出库单
	 * @param platform 订单来源于哪个平台：1-官网；2-ebay；3-亚马逊；4-沃尔玛； 5-VC
	 * @return {@link java.util.List}，该集合元素为{@link java.util.Map}（key={@link java.lang.String}; value={@link java.lang.Object}）
	 */
	public List<Map<String,Object>> getSendDeOutWmsOrders(@Param("platform") int platform);
	
	/**
	* @Title: getSendDeInWmsOrders 
	* @Description: 获取待推送到wms的入库单
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map<String,Object>> getSendDeInWmsOrders();
	
	/**
	* @Title: getSkuInfoFromItemEbay 
	* @Description: 获取ebay内件sky价格和数量信息
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map<String,Object>> getSkuInfoFromItemEbay(Map<String,Object> map);
	
	public List<Map<String, Object>> getSkuInfoFromItemAmazon(Map<String,Object> map);
	
	/**
	* @Title: getSkuInfoFromItemLight 
	* @Description: 获取ebay内件sky价格和数量信息
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map<String,Object>> getSkuInfoFromItemLight(Map<String,Object> map);
	
	/**
	* @Title: getCalCurrencyCodeList 
	* @Description: 获取汇率
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map<String,Object>> getCalCurrencyCodeList();
	
	/**
	* @Title: getLightOrderInfosByMap 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param map
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public Map<String,Object> getLightOrderInfosByMap(Map<String,Object> map);
	
	/**
	* @Title: getEbayOrderInfosByMap 
	* @Description: 
	* @param @param map
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public Map<String,Object> getEbayOrderInfosByMap(Map<String,Object> map);
	
	/**
	* @Title: getWmsSkuBySku 
	* @Description: 根据sku去查找仓库的sku
	* @param @param map
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map<String,Object>> getWmsSkuBySku(@Param("sku") String sku);
	
	/**
	* @Title: getSkuByWmsSku 
	* @Description: 根据wms的sku去找到oms的sku
	* @param @param itemNumber
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map<String,Object>> getSkuByWmsSku(@Param("itemNumber") String itemNumber);

	/**
	 * 跟新出库单上传跟踪号字段状态值
	 * @param 
	 * map {@link java.util.Map}: <code>key</code>={@link java.lang.String}; <code>value</code>={@link java.lang.Object}</br>
	 * <p>
	 * 必须包含: <code>key</code>="<code>id</code>" && <code>value</code>={@link java.lang.Long} （出库单主键ID）</br>
	 * 必须包含: <code>key</code>="<code>stutus</code>" && <code>value</code>={@link java.lang.Integer} （跟踪号上传状态: 0-未上传; 1-已上传; 2-上传失败）
	 * </p>	
	 * @return void
	 */
	public void updateOutUploadStatus(Map<String, Object> map);

	/**
	 * 查询没有上传跟踪号的出库单
	 */
	public List<DeOutWmsOrderMainModel> queryNoUploadTrackingNumberOuts(@Param("platform") String platform);

	/**
	 * 添加异常原因
	 * @param reason
	 */
	public void addAbnormalReason(@Param("reason") DeAbnormalReasonModel reason);

	public int countDeOutOrder(@Param("param") Map<String, Object> param);

	public List<OutOrderVO> findDeOutOrders(@Param("param") Map<String, Object> param, @Param("start") int startRow, @Param("end") int endRow);
	
	public List<Map<String, Object>> getDeOutOrderExportData(@Param("param") Map<String, Object> param);

	/**
	 * 在成功推送至WMS仓库之前，对出库单进行修改
	 * @param out 已经进行编辑的出库单信息
	 * @return void
	 */
	public void changeBeforeSendWms(@Param("out") DeOutWmsOrderMainModel out);

	/**
	 * 使用出库单id、orderId、orderOcsId进行联合查询，获取出库单
	 * @param 
	 * param {@link java.util.Map}: <code>key</code>={@link java.lang.String}; <code>value</code>={@link java.lang.Object}
	 * <p>
	 * 必须包含<code>key</code>="<code>id</code>", <code>value</code>={@link java.lang.Long}（出库单主键Id）；</br>
	 * 必须包含<code>key</code>="<code>orderId</code>", <code>value</code>={@link java.lang.String}（出库单oderId）；</br>
	 * 必须包含<code>key</code>="<code>orderOcsId</code>", <code>value</code>={@link java.lang.Long}（出库单orderOcsId）</br>
	 * </p>
	 * @return {@link java.util.Map}（key={@link java.lang.String}; value={@link java.lang.Object}）
	 */
	public Map<String,Object> getDeOutOrder(@Param("param") Map<String, Object> param);

	public int countAbnormalReason(@Param("param") Map<String, Object> param);

	public List<AbnormalReasonVO> findAbnormalReasons(@Param("param") Map<String, Object> param, @Param("start") int startRow, @Param("end") int endRow);

	public void addOperLog(@Param("log") DeWmsOperateLogModel log);
	
	public int countOperLog(@Param("param") Map<String, Object> param);

	public List<OperateLogVO> queryOperLogs(@Param("param") Map<String, Object> param, @Param("start") int startRow, @Param("end") int endRow);

	public Map<String, Object> getProduct(@Param("sku") String sku, @Param("qty") int qty);
	
	public List<Map<String, Object>> queryAbnormalReasons(@Param("param") Map<String, Object> param);

	public DeOutWmsOrderMainModel getOut(@Param("orderOcsId") Long orderOcsId, @Param("orderId") String orderId);

	/**
	 * 上传VC订单的跟踪号
	 * @param param
	 */
	public void uploadVcTranNumber(@Param("param") Map<String, Object> param);

}
