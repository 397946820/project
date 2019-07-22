package com.it.ocs.api.service;

import com.it.ocs.api.vo.OperateLogVO;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface IDeOperLogService {

	/**
	 * 查询操作日志
	 * @param param 过滤参数（支持分页）
	 * @return {@link com.it.ocs.common.ResponseResult}
	 */
	public ResponseResult<OperateLogVO> queryByPage(RequestParam param);
	
	/**
	 * 记录操作失败的日志
	 * @param target 操作方式
	 * @param objectId 操作对象
	 * @param description 操作详情
	 */
	public void recordOperFailedLog(String target, Long objectId, String description);

	/**
	 * 记录操作成功的日志
	 * @param target 操作方式
	 * @param objectId 操作对象
	 * @param description 操作详情
	 */
	public void recordOperSuccessLog(String target, Long objectId, String description);
	
	/**
	 * 记录操作日志
	 * @param target 操作方式
	 * @param objectId 操作对象
	 * @param result 操作结果: success | failed
	 * @param description 操作详情
	 */
	public void recordOperLog(String target, Long objectId, String result, String description);
}
