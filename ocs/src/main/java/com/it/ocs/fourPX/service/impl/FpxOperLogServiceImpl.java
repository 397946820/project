package com.it.ocs.fourPX.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.fourPX.dao.FpxOperLogDao;
import com.it.ocs.fourPX.model.FpxOperLogModel;
import com.it.ocs.fourPX.service.FpxOperLogService;
import com.it.ocs.fourPX.utils.ReflectUtils;
import com.it.ocs.fourPX.vo.FpxOperateLogVO;
import com.it.ocs.sys.model.UserModel;

@Service
public class FpxOperLogServiceImpl implements FpxOperLogService {
	
	private static final Logger logger = Logger.getLogger(FpxOperLogServiceImpl.class);

	@Autowired
	private FpxOperLogDao fpxOperLogDao;

	@Override
	public ResponseResult<FpxOperateLogVO> selectByPage(RequestParam param) {
		Map<String, Object> filter = param.getParam();
		
		ResponseResult<FpxOperateLogVO> result = new ResponseResult<FpxOperateLogVO>();
		
		result.setTotal(this.fpxOperLogDao.count(filter));
		
		List<Map<String, Object>> data = this.fpxOperLogDao.selectByPage(filter, param.getStartRow(), param.getEndRow());
		if(null != data && !data.isEmpty()) {
			List<FpxOperateLogVO> rows = new ArrayList<FpxOperateLogVO>();
			FpxOperateLogVO vo;
			for (Map<String, Object> map : data) {
				ReflectUtils.fillingTarget(vo = new FpxOperateLogVO(), map, true, false);
				rows.add(vo);
			}
			result.setRows(rows);
		}
		
		return result;
	}
	
	@Override
	public void recordSuccess(String target, Long objectid, String request, String response) {
		this.record(target, objectid, "success", request, response);
	}

	@Override
	public void recordFailed(String target, Long objectid, String request, String response) {
		this.record(target, objectid, "failed", request, response);
	}

	@Override
	public void record(String target, Long objectid, String result, String request, String response) {
		FpxOperLogModel model = new FpxOperLogModel();
		model.setTarget(target);
		model.setObjectid(objectid);
		model.setOperator(this.getCurentUserId());
		model.setResult(result);
		model.setRequest(request);
		model.setResponse(response);
		model.setCreatedat(new Date());
		
		try {
			model.setId(this.fpxOperLogDao.generatePrimaryKey());
			this.tranInsert(model);
		} catch (Exception e) {
			logger.error("[FpxOperLogServiceImpl.record(...)] - " + e.getMessage(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private void tranInsert(FpxOperLogModel model) throws Exception {
		this.fpxOperLogDao.insert(model);
	}

	private Long getCurentUserId() {
		try {
			Object user = SecurityUtils.getSubject().getSession().getAttribute("user");
			return null == user ? null : ((UserModel) user).getId();
		} catch (Exception e) {
//			logger.error("[FpxOperLogServiceImpl.getCurentUserId(...)] - " + e.getMessage(), e);
			return null;
		}
	}
}
