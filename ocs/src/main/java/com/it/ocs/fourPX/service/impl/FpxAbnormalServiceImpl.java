package com.it.ocs.fourPX.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.fourPX.dao.FpxAbnormalDao;
import com.it.ocs.fourPX.model.FpxAbnormalModel;
import com.it.ocs.fourPX.service.FpxAbnormalService;
import com.it.ocs.fourPX.utils.ReflectUtils;
import com.it.ocs.fourPX.vo.FpxAbnormalVO;

@Service
public class FpxAbnormalServiceImpl implements FpxAbnormalService {
	
	private static final Logger logger = Logger.getLogger(FpxAbnormalServiceImpl.class);

	@Autowired
	private FpxAbnormalDao fpxAbnormalDao;

	@Override
	public ResponseResult<FpxAbnormalVO> selectByPage(RequestParam param) {
		Map<String, Object> filter = param.getParam();
		
		ResponseResult<FpxAbnormalVO> result = new ResponseResult<FpxAbnormalVO>();
		
		result.setTotal(this.fpxAbnormalDao.count(filter));
		
		List<Map<String, Object>> data = this.fpxAbnormalDao.selectByPage(filter, param.getStartRow(), param.getEndRow());
		if(null != data && !data.isEmpty()) {
			List<FpxAbnormalVO> rows = new ArrayList<FpxAbnormalVO>();
			FpxAbnormalVO vo;
			for (Map<String, Object> map : data) {
				ReflectUtils.fillingTarget(vo = new FpxAbnormalVO(), map, true, false);
				rows.add(vo);
			}
			result.setRows(rows);
		}
		
		return result;
	}
	
	private FpxAbnormalModel initAbnormal(Long parentId, String parentType, String action, String reason) {
		return new FpxAbnormalModel(this.fpxAbnormalDao.generatePrimaryKey(), parentId, parentType , action, reason);
	}
	
	@Override
	public void recordOutAbnormal(Long id, String action, String message) {
		try {
			this.tranInsert(this.initAbnormal(id, "out-warehouse", action, message));
		} catch (Exception e) {
			logger.error("[异常单的异常信息保存失败] - " + e.getMessage(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void tranInsert(FpxAbnormalModel abnormal) throws Exception {
		this.fpxAbnormalDao.insert(abnormal);
	}

}
