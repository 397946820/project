package com.it.ocs.cal.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.cal.vo.SmallTypeItemVo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface ISmallTypeItemService {

	ResponseResult<SmallTypeItemVo> findAll(RequestParam param);

	OperationResult saveEdit(SmallTypeItemVo smallTypeItem);

	List<Map<String, Object>> getTypeName();

	List<Map<String, Object>> getShippingTypeByTypeName(Map<String, Object> map);

}
