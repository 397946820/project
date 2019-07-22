package com.it.ocs.api.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.api.ex.SkuInventoryException;
import com.it.ocs.api.model.DeAbnormalReasonModel;
import com.it.ocs.api.model.DeOutWmsOrderDetailModel;
import com.it.ocs.api.model.DeOutWmsOrderMainModel;
import com.it.ocs.api.vo.AbnormalReasonVO;
import com.it.ocs.api.vo.OutOrderChangeVO;
import com.it.ocs.api.vo.OutOrderVO;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

/**
 * 
* @ClassName: IDeWarehouseService 
* @Description: 对接德国仓库服务层接口
* @author wgc 
* @date 2018年4月8日 上午11:37:13 
*
 */
public interface IDeWarehouseService {
	
	/**
	 * 接收德国仓（WMS）出库单反馈
	 * @param data 出库单反馈数据
	 * @return
	 * @throws Exception 事务涉及回滚需要向外抛出的异常
	 */
	public Map<String, Object> receiveOutOrderFeedback(Map<String, Object> data) throws Exception;

	/**
	 * 接收德国仓（WMS）入库单反馈
	 * @param data 入库单反馈数据
	 * @return
	 * @throws Exception 事务涉及回滚需要向外抛出的异常
	 */
	public OperationResult receiveInOrderFeedback(Map<String, Object> data) throws Exception;
	
	/**
	 * 保存德国仓出库单
	 * @param platform 订单来源（1-官网; 2-ebay）
	 * @param data 待推送的出库订单数据
	 * @throws Exception 事务涉及回滚需要向外抛出的异常
	 */
	public void saveDeOutOrders(String platform, Map<String, Object> data) throws Exception;
	
	/**
	 * 保存德国入库单
	 * @param data 待推送的入库订单数据
	 * @throws Exception 事务涉及回滚需要向外抛出的异常
	 */
	public void saveDeInOrders(Map<String, Object> data) throws Exception;

	/**
	 * 向德国仓（WMS）推送出库订单
	 * @param data 待推送的出库订单数据
	 * @param token 用于调用WMS出库订单推送接口的身份认证加密字符串
	 * @throws Exception 事务涉及回滚需要向外抛出的异常
	 */
	public boolean sendDeOutOrders(Map<String, Object> data, String token) throws Exception;
	
	/**
	 * 向德国仓（WMS）推送入库单
	 * @param data 待推送的入库订单数据
	 * @param token 用于调用WMS出库订单推送接口的身份认证加密字符串
	 * @throws Exception 事务涉及回滚需要向外抛出的异常
	 */
	public boolean sendDeInOrders(Map<String, Object> data, String token) throws Exception;

	/**
	 * 扫描出库单数据，得到未上传跟踪号的单，并上传跟踪号
	 * @param platform 平台：1-官网；2-ebay；3-亚马逊；4-沃尔玛；5-VC
	 * @param endUpload	是否结束上传：即使本次上传失败，以后也不上传了。
	 */
	public void scanOutsUploadTrackingNumber(int platform, boolean endUpload);
	
	/**
	 * 对出库单进行操作之后需要进行的相关处理（包括更新状态、时间、异常标记等等）
	 * @param id 出库单主键ID。必须的参数。
	 * @param isSendWms 出库单状态值: 0-未推送; 1-已推送; 2-已反馈; 3-推送失败。如果不需要，该值为null。
	 * @param isAbnormal 出库单是否异常的标记: 0-正常; 1-异常。如果不需要，该值为null。
	 * @param reason 相关操作异常原因，如果不需要，该值为null。
	 * @throws Exception 事务涉及回滚需要向外抛出的异常
	 */
	public void afterOperateDeOutOrder(Long id, String isSendWms, String isAbnormal, DeAbnormalReasonModel reason) throws Exception;

	/**
	 * 对入库单进行操作之后需要进行的相关处理（包括更新状态、时间、异常标记等等）
	 * @param id 入库单主键ID。必须的参数。
	 * @param isSendWms 入库单状态值: 0-未推送; 1-已推送; 2-已反馈; 3-推送失败。如果不需要，该值为null。
	 * @param reason 相关操作异常原因，如果不需要，该值为null。
	 * @throws Exception 事务涉及回滚需要向外抛出的异常
	 */
	public void afterOperateDeInOrder(Long id, String isSendWms, DeAbnormalReasonModel reason) throws Exception;
	
	/**
	 * 查询出库单数据
	 * @param param 过滤参数（支持分页）
	 * @return {@link com.it.ocs.common.ResponseResult}
	 */
	public ResponseResult<OutOrderVO> findDeOutOrders(RequestParam param);

	/**
	 * 在成功推送至WMS仓库之前，对出库单进行修改
	 * @param vo 已经进行编辑的出库单信息VO
	 * @return {@link com.it.ocs.common.OperationResult}
	 */
	public OperationResult changeBeforeSendWms(OutOrderChangeVO vo) throws Exception;

	/**
	 * 推送一张出库单到WMS仓库
	 * @param id 出库单主键ID
	 * @param orderId 出库单order_id（原始订单业务id）
	 * @param orderOcsId 出库单order_ocs_id（原始订单主键ID）
	 * @return {@link com.it.ocs.common.OperationResult}
	 */
	public OperationResult sendOutOrder(Long id, String orderId, Long orderOcsId) throws Exception;

	/**
	 * 取消一张出库单（被取消的出库单即成为废单，无法再进行后续操作）
	 * @param id 出库单主键ID
	 * @param orderId 出库单order_id（原始订单业务id）
	 * @param orderOcsId 出库单order_ocs_id（原始订单主键ID）
	 * @return {@link com.it.ocs.common.OperationResult}
	 */
	public OperationResult cancelOutOrder(Long id, String orderId, Long orderOcsId) throws Exception;

	/**
	 * 查询异常单的异常原因
	 * @param param 过滤参数（支持分页）
	 * @return {@link com.it.ocs.common.ResponseResult}
	 */
	public ResponseResult<AbnormalReasonVO> findAbnormalReasons(RequestParam param);

	/**
	 * 同步WMS仓库系统的异常单（病单）
	 */
	public OperationResult syncWmsDiseaseOutOrders();
	
	public void throwExIfLackInventory(String sku, int minQty, String token) throws SkuInventoryException;
	
	/**
	 * 记录针对出入库单操作失败的日志
	 * @param target 操作的目标
	 * @param objectId 操作的业务对象id（出库单或者入库单的主键ID）
	 * @param description 操作详情描述
	 */
	public void recordOperFailedLog(String target, Long objectId, String description);

	/**
	 * 更新上传跟踪号状态
	 * @param id 出库单主键ID
	 * @param status 跟踪号要更新的状态值（0-未上传；1-已上传；2-上传失败；3-关闭上传）
	 */
	void updateOutUploadStatus(Long id, int status);

	/**
	 * 上传跟踪号(订单状态不用更新,依靠上传跟踪号之后,系统自动同步订单状态下来OMS即可)
	 * @param id 出库单主键ID
	 * @param platform 平台：1-官网；2-ebay；3-亚马逊；4-沃尔玛
	 * @param orderOcsId 对应订单表主键id
	 * @param orderId 对应订单表的order_id
	 * @param trackingNumber 快递跟踪号
	 */
	boolean uploadTrackingNumber(DeOutWmsOrderMainModel main, List<DeOutWmsOrderDetailModel> details);
	
	/**
	 * 用于测试
	 */
	void test();
}
