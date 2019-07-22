package com.it.ocs.customerCenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.customerCenter.model.CustomerProductModel;
import com.it.ocs.customerCenter.model.CustomerTicketsModel;
@Repository
public interface ICustomerTicketsDao extends IBaseDAO<CustomerTicketsModel> {
	
	public List<CustomerTicketsModel> selectCustomerTickets(@Param("tickets")Map<String , Object> map,@Param("startRow") int startRow,@Param("endRow") int endRow,@Param("order")String order,@Param("sort")String sort);

	public void remove(@Param("tickets_id")Long tickets_id);
	
	public Integer getTotal(@Param("tickets")Map<String,Object> map);
	
	public List<Map<String, Object>> selectCustomerT(@Param("tickets")Map<String, String> map);
	
	public void updateTickets(List<CustomerTicketsModel> customerTicketsModels);
	
	public void insertTickets(List<CustomerTicketsModel> customerTicketsModels);
	
	public List<CustomerTicketsModel> selectTicketByOrderID(String orderId);

	public void insertCustomer(Map<String, Object> customer);
}
