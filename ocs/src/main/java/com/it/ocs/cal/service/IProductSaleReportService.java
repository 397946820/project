package com.it.ocs.cal.service;

import java.text.ParseException;
import java.util.List;

import com.it.ocs.cal.vo.SaleCatCountReportVO;
import com.it.ocs.cal.vo.SaleCountReportVO;
import com.it.ocs.cal.vo.SaleDisCountReportVO;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;

public interface IProductSaleReportService {

	public List<SaleCountReportVO> findSaleCountReportList(RequestParam param);
	
	public ResponseResult<SaleDisCountReportVO> findSaleDisCountReportList(RequestParam param) throws ParseException;
	
	public ResponseResult<SaleCatCountReportVO> findSaleCatCountReportList(RequestParam param) throws ParseException;
	
	public List<SaleCatCountReportVO> findProductTypes();
	
	public List<SaleCatCountReportVO> querySiteByAccount(String account);
	

}
