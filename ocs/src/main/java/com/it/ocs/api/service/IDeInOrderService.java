package com.it.ocs.api.service;

import com.it.ocs.api.vo.InOrderVO;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

/**
 * 德国入库单服务接口
 * @author zhouyancheng
 *
 */
public interface IDeInOrderService {

	/**
	 * 分页查询入库单数据
	 * @param param 分页参数，包含相关过滤条件
	 * @return 
	 * {@link com.it.ocs.common.ResponseResult}，
	 * 其属性<code>rows</code>数据集为以{@link com.it.ocs.api.vo.InOrderVO}对象实例为元素的集合（{@link java.util.List}）。
	 */
	ResponseResult<InOrderVO> queryByPage(RequestParam param);

	/**
	 * 取消指定的入库单
	 * @param id 入库单主键ID
	 * @param orderId 原始订单ID
	 * @param orderOcsId 原始订单主键ID
	 * @return {@link com.it.ocs.common.OperationResult}
	 */
	OperationResult cancel(Long id, String orderId, Long orderOcsId) throws Exception;

	/**
	 * 重新推送入库单到WMS仓库
	 * @param id
	 * @param orderId
	 * @param orderOcsId
	 * @return
	 */
	OperationResult resend(Long id, String orderId, Long orderOcsId) throws Exception;

	ResponseResult<InOrderVO> queryWmsClaimOrderByPage(com.it.ocs.common.RequestParam param);

	ResponseResult<InOrderVO> queryWmsClaimOwnerOrderByPage(com.it.ocs.common.RequestParam param);

	OperationResult claiming(Long claim, Long owner) throws Exception;

	ResponseResult<InOrderVO> queryMainById(Long id);
}
