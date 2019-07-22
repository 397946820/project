package com.it.ocs.sys.service;

import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.vo.OperatingProfitViewVo;

public interface IOperatingProfitViewService {

	ResponseResult<OperatingProfitViewVo> findAll(RequestParam param);

	List<Map<String, Object>> getCategory();

	List<Map<String, Object>> getSite();

	List<Map<String, Object>> getSkuByCategory(Map<String, Object> map);

	List<Map<String, Object>> getPersonnelByDepartment(Map<String, Object> map);

	OperationResult refresh();

	OperationResult sure();

	Integer refreshBefore();

	OperationResult exportBefore(Map<String, Object> map);

	OperationResult generateDataBefore();

	OperationResult generateData();

	OperationResult mappingSku();

	OperationResult categorySku();

	OperationResult syncCategory();

	Map<String, Object> findAmazonFee(RequestParam param);

}
