package com.it.ocs.customerCenter.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.customerCenter.dao.ICustomerTicketsDao;
import com.it.ocs.customerCenter.model.CustomerTicketsModel;
import com.it.ocs.customerCenter.service.ICustomerTicketsService;
import com.it.ocs.customerCenter.vo.CustomerTicketsVO;
import com.it.ocs.synchronou.util.ValidationUtil;

@Service
public class CustomerTicketsService extends BaseService implements ICustomerTicketsService {

	@Autowired
	private ICustomerTicketsDao ticketsDao;
	@Override
	public ResponseResult<CustomerTicketsVO> selectCustomerTickets(RequestParam param) {
		
		ResponseResult<CustomerTicketsVO> result = new ResponseResult<>();
		Map<String, Object> map = param.getParam();
		List<CustomerTicketsModel> customerTicketsModels = ticketsDao.selectCustomerTickets(map, param.getStartRow(), param.getEndRow(), param.getOrder(), param.getSort());
		List<CustomerTicketsVO> customerTicketsVOs = Lists.newArrayList();
		convertList(customerTicketsModels, customerTicketsVOs);
		Integer total = ticketsDao.getTotal(map);
		result.setRows(customerTicketsVOs);
		result.setTotal(total);
		
		return result;
	}

	@Override
	public OperationResult remove(Long tickets_id) {
		
		OperationResult result = new OperationResult();
		try {
			ticketsDao.remove(tickets_id);
			result.setDescription("删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setDescription("删除失败！");
			return result;
		}
		return result;
	}

	@Override
	public OperationResult batchSaveTickets(CustomerTicketsVO[] customerTicketsVOs) {
		
		OperationResult result = new OperationResult();
		List<CustomerTicketsVO> customerTicketsList = Arrays.asList(customerTicketsVOs);
		List<CustomerTicketsVO> insertTickets = Lists.newArrayList();
		List<CustomerTicketsVO> updateTickets = Lists.newArrayList();
		for (CustomerTicketsVO customerTicketsVO : customerTicketsList) {
			if(!ValidationUtil.isNull(customerTicketsVO.getTickets_id())){
				updateTickets.add(customerTicketsVO);
			}else{
				insertTickets.add(customerTicketsVO);
			}
		}
		try {
			if(insertTickets.size()>0){
			OperationResult result2 = insertTickets(insertTickets);
			if(result2.getData().toString().equals("0")){
				result.setData("0");
			}
			}
			if(updateTickets.size()>0){
			OperationResult result2 = updateTickets(updateTickets);
			if(result2.getData().toString().equals("0")){
				result.setData("0");
			}
			}
			result.setDescription("保存成功！");
		} catch (Exception e) {
			// TODO: handle exception
			result.setDescription("保持失败！");
			result.setError("0");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult insertTickets(List<CustomerTicketsVO> customerTicketsVOs) {
		
		OperationResult result = new OperationResult();
		try {
			List<CustomerTicketsModel> customerTicketsModels = Lists.newArrayList();
			if(customerTicketsVOs.size()>0){
				for (CustomerTicketsVO customerTicketsVO : customerTicketsVOs) {
					customerTicketsModels.add(customerTicketsVO);
				}
			}
			ticketsDao.insertTickets(customerTicketsModels);
			result.setDescription("添加成功！");
			result.setData("1");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setData("0");
			result.setDescription("添加失败！");
			return result;
		}
		return result;
	}

	@Override
	public OperationResult updateTickets(List<CustomerTicketsVO> customerTicketsVOs) {
		
		OperationResult result = new OperationResult();
		try {
			List<CustomerTicketsModel> customerTicketsModels = Lists.newArrayList();
			if(customerTicketsVOs.size()>0){
				for (CustomerTicketsVO customerTicketsVO : customerTicketsVOs) {
					customerTicketsModels.add(customerTicketsVO);
				}
			}
			ticketsDao.updateTickets(customerTicketsModels);
			result.setDescription("更改成功！");
			result.setData("1");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setData("0");
			result.setDescription("更改失败");
			return result;
		}
		return result;
	}


	private void convertList(List<CustomerTicketsModel> source, final List<CustomerTicketsVO> target) {
		CollectionUtil.each(source, new IAction<CustomerTicketsModel>() {
			@Override
			public void excute(CustomerTicketsModel obj) {
				CustomerTicketsVO customerTicketsVO = new CustomerTicketsVO();
				BeanUtils.copyProperties(obj, customerTicketsVO);
				target.add(customerTicketsVO);
			}
		});
	}

	@Override
	public CustomerTicketsModel selectTicketByOrderID(String orderId) {
		
		List<CustomerTicketsModel> customerTicketsModels = ticketsDao.selectTicketByOrderID(orderId);
		if(customerTicketsModels.size()>=1){
			return customerTicketsModels.get(0);
		}else{
		return null;
		}
	}

}
