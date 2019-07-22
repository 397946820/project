package com.it.ocs.publication.service.external;

import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.vo.TimingPlanVO;

public interface IEBayTimingPlanService {
  public ResponseResult<TimingPlanVO> selectTimingPlans(RequestParam param);
  public OperationResult countPub(Map<String, Object> map);
  public ResponseResult<TimingPlanVO> selectLineTimingPlans(RequestParam param);
  public OperationResult countLinePub(Map<String, Object> map);
}
