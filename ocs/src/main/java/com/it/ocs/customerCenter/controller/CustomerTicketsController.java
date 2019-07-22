package com.it.ocs.customerCenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.model.CustomerTicketsModel;
import com.it.ocs.customerCenter.service.ICustomerTicketsService;
import com.it.ocs.customerCenter.vo.CustomerTicketsVO;

@Controller
@RequestMapping(value="/Tickets")
public class CustomerTicketsController {
	
	@Autowired
	private ICustomerTicketsService ticketService;
	@RequestMapping(value="/show")
	public String show(){
		return "admin/customerCenter/Tickets";
	}
	@RequestMapping(value="/list")
	@ResponseBody
	public ResponseResult<CustomerTicketsVO> list(RequestParam param){
		return ticketService.selectCustomerTickets(param);
	}
	@RequestMapping(value="/remove")
	@ResponseBody
	public OperationResult remove(Long tickets_id){
		return ticketService.remove(tickets_id);
	}
	@RequestMapping(value="/selectTicketByOrderID")
	@ResponseBody
	public CustomerTicketsModel selectTicketByOrderID(String orderId){
		return ticketService.selectTicketByOrderID(orderId);
	}
	@RequestMapping(value="/batchSaveTickets")
	@ResponseBody
	public OperationResult batchSaveTickets(@RequestBody CustomerTicketsVO[] customerTicketsVOs){
		return ticketService.batchSaveTickets(customerTicketsVOs);
	}
}
