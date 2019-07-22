package com.it.ocs.salesStatistics.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.model.LightMarkShipmentModel;
import com.it.ocs.salesStatistics.vo.LightMarkShipmentVO;

public interface ILightMarkShipmentService {

	/**
	 * 分页查询官网标记发货数据
	 * @param param
	 * @return
	 */
	ResponseResult<LightMarkShipmentVO> queryByPage(RequestParam param, String platform);

	/**
	 * 取消标记发货数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	OperationResult cancel(String platform, Long id) throws Exception;

	/**
	 * 扫描待上传跟踪号的标记发货数据
	 * @return
	 */
	List<LightMarkShipmentModel> scanWaitingUploads(String platform);

	Map<String, Object> queryProduct(String platform, String orderId, Long detailId);
	
	OperationResult uploadTrackingNumbers(String platform, List<LightMarkShipmentModel> models);
}
