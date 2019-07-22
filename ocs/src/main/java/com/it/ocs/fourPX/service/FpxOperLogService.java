package com.it.ocs.fourPX.service;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.fourPX.vo.FpxOperateLogVO;

public interface FpxOperLogService {

	void record(String target, Long objectid, String result, String request, String response);
	
	void recordSuccess(String target, Long objectid, String request, String response);
	
	void recordFailed(String target, Long objectid, String request, String response);

	ResponseResult<FpxOperateLogVO> selectByPage(RequestParam param);

}
