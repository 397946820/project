package com.it.ocs.fourPX.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.fourPX.vo.FPXStockVO;
import com.it.ocs.fourPX.vo.SellerInventoryLogVO;

public interface SellerInventoryLogService {

	ResponseResult<SellerInventoryLogVO> selectByPage(RequestParam param);
	
	OperationResult save(List<Map<String, Object>> data, String busarea);

	boolean save(Map<String, Object> vo, String busarea) throws Exception;

	Date selectLastSyncTime(String busarea);
	
	OperationResult modifyCodeType(RequestParam param);
	
	List<Map<String,String>> getCodeMap();
	
	/**
	 * 同步4PX的库存流水
	 * @param increment 是否采用增量同步：true-增量；false-全量
	 * @param busarea 业务区域：DE | US
	 * @return
	 */
	OperationResult syncFrom4pxSil(boolean increment, String busarea);

	public ResponseResult<FPXStockVO> stockListHis(RequestParam param);

}
