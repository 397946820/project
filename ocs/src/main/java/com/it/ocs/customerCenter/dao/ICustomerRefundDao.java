package com.it.ocs.customerCenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.customerCenter.model.CustomerRefundModel;
import com.it.ocs.customerCenter.model.CustomerTicketsModel;

@Repository
public interface ICustomerRefundDao extends IBaseDao<CustomerRefundModel>{
	
	public List<CustomerRefundModel> selectCustomerRefund(@Param("refund")Map<String , Object> map,@Param("startRow") int startRow,@Param("endRow") int endRow,@Param("order")String order,@Param("sort")String sort);
    
	public List<Map<String, Object>> selectCustomerR(@Param("refund")Map<String, String> map);
	
	public void remove(@Param("report_id")Long tickets_id);
	
	public Integer getTotal(@Param("refund")Map<String,Object> map);
	
	public void updateRefunds(List<CustomerRefundModel> customerRefundModels);
	
	public void insertRefunds(List<CustomerRefundModel> customerRefundModels);
}
