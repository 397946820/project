package com.it.ocs.customerCenter.service;

import java.util.List;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.model.CustomerTicketsModel;
import com.it.ocs.customerCenter.vo.CustomerTicketsVO;

public interface ICustomerTicketsService {

	public ResponseResult<CustomerTicketsVO> selectCustomerTickets(RequestParam param);
	
	public OperationResult remove(Long tickets_id);
	
	public OperationResult batchSaveTickets(CustomerTicketsVO[] customerTicketsVOs);
	
	public OperationResult insertTickets(List<CustomerTicketsVO> customerTicketsVOs);
	
	public OperationResult updateTickets(List<CustomerTicketsVO> customerTicketsVOs);
	
	public CustomerTicketsModel selectTicketByOrderID(String orderId);
}
