package com.it.ocs.api.constant;

/**
 * 
* @ClassName: WarehouseConstant 
* @Description: 仓库常量类
* @author wgc 
* @date 2018年4月8日 上午11:25:44 
*
 */
public class WarehouseConstant {
	/**
	 * 检查并获取用户的基本信息
	 */
	public final static String CHECKANDGETUSERINFO = "CheckAndGetUserInfo";
	
	/**
	 * 保存出库订单
	 */
	public final static String SAVESHIPPINGORDER = "SaveShippingOrder";
	
	/**
	 * 查询WMS出库订单状态和异常
	 */
	public final static String GETSHIPPINGORDERLISTBYQUERY = "GetShippingOrderListByQuery";
	
	/**
	 * 保存入库订单
	 */
	public final static String SAVERECEIPT = "SaveReceipt";
	
	/**
	 * 查询库存
	 */
	public final static String GETINVENTORYLISTBYQUERY = "GetInventoryListByQuery";
	
	
	/**
	 * 问题：以下关于 DEOWNERCODE、DESTORECODE、DEWMSAPPKEY、DEWMSAPPSECRET的值，测试环境与生产环境是不同的。
	 * 		 放入到java代码常量会导致每次开发与提交都需要修改这些常量，这是不合适的。本质上讲这些就不是常量。
	 * 修改：测试环境与生产环境是不同的常量值，已经移到basic.properties文件中，通过判断相应环境，从属性文件中取到相应环境的值
	 */
	
	/**
	 * 德国仓货主代码(业务主体)
	 */
	//public final static String DEOWNERCODE = "LE"; // dev
	//public final static String DEOWNERCODE = "GB"; //produce
	
	/**
	 * 德国仓仓库代码 LE.wh2
	 */
	//public final static String DESTORECODE = "LE.wh2";
	
	/**
	 * 德国WMS仓库LE.wh2接口的用户名
	 */
	//public final static String DEWMSAPPKEY = "leadmin"; // dev
	//public final static String DEWMSAPPKEY = "wmsadmin"; // produce
	
	/**
	 * 德国WMS仓库LE.wh2接口的密码
	 */
	//public final static String DEWMSAPPSECRET = "666666"; //dev 
	//public final static String DEWMSAPPSECRET = "Le.*123456"; //produce
	

	/**
	 * 德国仓VC出库订单类型：销售出库：HME_VC_SHIPMENT

	 */
	public final static String DE_OUT_HME_VC_SHIPMENT="HME_VC_SHIPMENT";
	
	/**
	 * 德国仓出库订单类型：销售出库：HME_INV_SHIPMENT

	 */
	public final static String DE_OUT_HME_INV_SHIPMENT="HME_INV_SHIPMENT";
	
	/**
	 * 德国仓出库订单类型：补发出库：Reissue_Normal
	 */
	public final static String DE_OUT_REISSUE_NORMAL="Reissue_Normal";
	
	/**
	 * 德国仓出库订单类型：换货出库：Transfer_Out
	 */
	public final static String DE_OUT_TRANSFER_OUT="Transfer_Out";
	
	/**
	 * 德国仓出库订单类型：补发矫正出库：Reissue_Correct
	 */
	public final static String DE_OUT_REISSUE_CORRECT="HME_INV_SHIPMENT";
	
	/**
	 * 物流承运商 DHL
	 */
	public final static String CARRIER_ID_DHL = "DHL";
	
	/**
	 * 德国出入库单是否推送wms：0-未推送
	 */
	public final static String IS_SEND_WMS_NO = "0";
	
	/**
	 * 德国出入库单是否推送wms：1-已推送
	 */
	public final static String IS_SEND_WMS_YES = "1";
	
	/**
	 * 德国出入库单是否推送wms：2-已反馈
	 */
	public final static String IS_SEND_WMS_FEEDBACK = "2";
	
	/**
	 * 德国出入库单是否推送wms：3-推送失败
	 */
	public final static String IS_SEND_WMS_FAILED = "3";
	
	public final static String IS_SEND_WMS_CANCELLED = "4";
	
	/**
	 * 正常单
	 */
	public final static String IS_ABNORMAL_0 = "0";
	
	/**
	 * 病单
	 */
	public final static String IS_ABNORMAL_1 ="1";
	
	/**
	 * 德国仓入库单类型 退货入库
	 */
	public final static String DE_IN_ORDER_TYPE_RETURNGOODS = "1";
	
	/**
	 * 德国仓入库单认领类型: 与WMS认领单无关
	 */
	public final static String DEIN_OMS_NONCLAIM = "oms_nonclaim";
	
	/**
	 * 德国仓入库单认领类型: 已绑定WMS认领单
	 */
	public final static String DEIN_OMS_BINDCLAIM = "oms_bindclaim";
	
	/**
	 * 德国仓入库单认领类型: WMS退货待认领单
	 */
	public final static String DEIN_WMS_UNCLAIMED = "wms_unclaimed";
	
	/**
	 * 德国仓入库单认领类型: WMS退货已认领单
	 */
	public final static String DEIN_WMS_CLAIMED = "wms_claimed";
	
}
