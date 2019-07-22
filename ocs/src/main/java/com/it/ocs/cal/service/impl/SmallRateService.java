package com.it.ocs.cal.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.ISmallRateDao;
import com.it.ocs.cal.service.ISmallRateService;
import com.it.ocs.cal.vo.SmallRateVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.ResponseResultUtils;

@Service
public class SmallRateService implements ISmallRateService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ISmallRateDao smallRateDao;

	@Override
	public ResponseResult<SmallRateVo> findAll(RequestParam param) {
		return ResponseResultUtils.getResponseResult(smallRateDao, param, SmallRateVo.class);
	}

	@Override
	public OperationResult saveEdit(SmallRateVo smallRateVo) {
		return ResponseResultUtils.saveEdit(logger, smallRateDao, smallRateVo);
	}

}
