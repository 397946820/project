package com.it.ocs.api.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.it.ocs.api.dao.IDeWarehouseDao;
import com.it.ocs.api.model.DeWmsOperateLogModel;
import com.it.ocs.api.service.IDeOperLogService;
import com.it.ocs.api.vo.OperateLogVO;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.sys.model.UserModel;

@Service
public class DeOperLogServiceImpl extends BaseService implements IDeOperLogService {

	private static final Logger logger = Logger.getLogger(DeOperLogServiceImpl.class);
	
	@Autowired
	private IDeWarehouseDao deWarehouseDao;
	
	@Override
	public ResponseResult<OperateLogVO> queryByPage(RequestParam param) {
		Map<String, Object> filter = param.getParam();
		int count = this.deWarehouseDao.countOperLog(filter);
		ResponseResult<OperateLogVO> result = new ResponseResult<OperateLogVO>();
		result.setRows(this.deWarehouseDao.queryOperLogs(filter, param.getStartRow(), param.getEndRow()));
		result.setTotal(count);
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 * @see 日志添加不成功发生异常也不应该影响调用者的事务，所以在该方法内部将异常处理掉，不往外抛了，且该方法的事务独立于其他事务。
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void recordOperFailedLog(String target, Long objectId, String description) {
		this.recordOperLog(target, objectId, "failed", description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recordOperSuccessLog(String target, Long objectId, String description) {
		this.recordOperLog(target, objectId, "success", description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recordOperLog(String target, Long objectId, String result, String description) {
		this.log(target, objectId, result, description);
	}

	/**
	 * 记录日志
	 * @see 日志添加不成功发生异常也不应该影响调用者的事务，所以在该方法内部将异常处理掉，不往外抛了，且该方法的事务独立于其他事务。
	 * @param target 操作方式
	 * @param objectId 操作对象
	 * @param result 操作结果: success | failed
	 * @param description 操作详情
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private void log(String target, Long objectId, String result, String description) {
		Long operator = null;
		try {
			UserModel user = getCurentUser();
			operator = null == user ? null : user.getId();
		} catch (Exception e) {
			logger.warn(">>> get current user >>> " + e.getMessage());
		}
		
		try {
			if(StringUtils.isNotBlank(description) && description.length() > 3000) {
				description = description.substring(0, 3000) + " ......";
			}
			this.deWarehouseDao.addOperLog(new DeWmsOperateLogModel(target, objectId, operator, result, description));
		} catch (Exception e) {
			logger.error(">>> recordOperFailedLog >>> " + e.getMessage(), e);
		}
	}
}
