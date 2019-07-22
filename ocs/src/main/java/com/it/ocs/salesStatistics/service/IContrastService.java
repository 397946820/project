package com.it.ocs.salesStatistics.service;

import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;

public interface IContrastService {

	/**
	 * 导入
	 * @param file  导入的文件
	 * @return
	 */
	OperationResult uploadExcel(MultipartFile file);

	ResponseResult<SalesStatisticsVo> findAllOriginal(RequestParam param) throws Exception;

	ResponseResult<SalesStatisticsVo> findAllOracle(RequestParam param) throws Exception;

	ResponseResult<SalesStatisticsVo> findAllMysql(RequestParam param) throws Exception;

}
