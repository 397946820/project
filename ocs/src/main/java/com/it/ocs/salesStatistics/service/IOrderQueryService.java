package com.it.ocs.salesStatistics.service;

import java.util.List;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;
import com.it.ocs.sys.vo.PermissionVO;

public interface IOrderQueryService {

	/**
	 * 查找
	 * @param param		条件以及分页信息
	 * @param columns	列的权限
	 * @param permissionVO	所有拥有的权限
	 * @param allSourceFlag	是否拥有所有权限
	 * @param string	标记
	 * @param columns2	统计的权限
	 * @return
	 */
	ResponseResult<SalesStatisticsVo> findAll(RequestParam param, List<String> columns, PermissionVO permissionVO,
			Boolean allSourceFlag, String string, List<String> columns2) throws Exception;

}
