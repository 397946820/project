package com.it.ocs.fourPX.service;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.fourPX.vo.FpxAbnormalVO;

public interface FpxAbnormalService {

	void recordOutAbnormal(Long id, String action, String message);

	ResponseResult<FpxAbnormalVO> selectByPage(RequestParam param);
	
}
