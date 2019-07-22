package com.it.ocs.fourPX.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.fourPX.vo.FpxOrderPlatform;
import com.it.ocs.fourPX.vo.FpxOutWarehouseVO;

public interface 
FpxOutWarehouseService {

	/**
	 * 查询适应于4px的出库单，支持分页
	 * @param param 参数，包含分页参数
	 * @return
	 */
	ResponseResult<FpxOutWarehouseVO> selectByPage(RequestParam param);

	/**
	 * 将OMS订单表中的未发货订单同步到OMS的4PX出库单管理平台，形成待推送4PX的出库单数据
	 * @param platform 源订单所属平台，如果传null将代表不限定平台
	 */
	OperationResult syncOutFromUnshippedOrder(FpxOrderPlatform platform);
	
	/**
	 * 批量创建待推送4px的出库单数据
	 * @param unshippeds 未发货的源订单信息列表
	 */
	OperationResult createPendingPushOut(List<Map<String, Object>> unshippeds);
	
	/**
	 * 创建一条待推送4px的出库单数据
	 * @param unshipped 未发货的源订单信息
	 */
	Long createPendingPushOut(Map<String, Object> unshipped) throws Exception;

	/**
	 * 将4PX出库单的状态等相关信息同步到OMS（即更新OMS对应4PX出库单、源订单状态相关信息）
	 * @return
	 */
	OperationResult syncOutFrom4px();

	/**
	 * 批量推送出库单至4px平台
	 * @param id 指定的出库单的主键ID
	 * @return
	 */
	OperationResult batchPush4px();

	/**
	 * 将指定的出库单推送至4px平台
	 * @param id 指定的出库单的主键ID
	 * @return
	 */
	OperationResult push4px(Long id);

	/**
	 * 将指定的出库单取消
	 * @param id 指定的出库单的主键ID
	 * @return
	 */
	OperationResult cancel(Long id);

	/**
	 * 保存修改的出库单数据
	 * @param vo
	 * @return
	 */
	OperationResult saveChanges(FpxOutWarehouseVO vo);

}
