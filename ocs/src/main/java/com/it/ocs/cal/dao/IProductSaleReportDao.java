package com.it.ocs.cal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.cal.vo.SaleCatCountReportVO;
import com.it.ocs.cal.vo.SaleCountReportVO;
import com.it.ocs.cal.vo.SaleDisCountReportVO;

public interface IProductSaleReportDao {

	public List<SaleCountReportVO> queryByPage(@Param("param")Map<String, Object> map);
	
	public List<SaleCatCountReportVO> queryCatCount(@Param("param") Map<String,Object> map);
	
	public List<SaleDisCountReportVO> queryDisCount(@Param("param") Map<String,Object> map);
	
	public int countcat(@Param("param")Map<String,Object> map);
	
	public List<SaleCatCountReportVO> findProductTypes();
	
	public List<SaleCatCountReportVO> findSitesByAccount(@Param("account") String account);
	

}
