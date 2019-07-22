package com.it.ocs.cal.service;

import com.it.ocs.cal.vo.PackagingRuleVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface IPackagingRuleService {

	ResponseResult<PackagingRuleVo> findAll(RequestParam param);

	OperationResult saveEdit(PackagingRuleVo packagingRule);

}
