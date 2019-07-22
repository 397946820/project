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
import com.it.ocs.customerCenter.dao.ICustomerRefundDao;
import com.it.ocs.customerCenter.model.CustomerRefundModel;
import com.it.ocs.customerCenter.service.ICustomerRefundService;
import com.it.ocs.customerCenter.vo.CustomerRefundVO;
import com.it.ocs.synchronou.util.ValidationUtil;

@Service
public class CustomerRefundService extends BaseService implements ICustomerRefundService {

	@Autowired
	private ICustomerRefundDao refundDao;
	@Override
	public ResponseResult<CustomerRefundVO> selectCustomerRefunds(RequestParam param) {
		
		ResponseResult<CustomerRefundVO> result = new ResponseResult<>();
		Map<String, Object> map = param.getParam();
		List<CustomerRefundModel> customerRefundModels = refundDao.selectCustomerRefund(map, param.getStartRow(), param.getEndRow(), param.getOrder(), param.getSort());
		List<CustomerRefundVO> customerRefundVos = Lists.newArrayList();
		convertList(customerRefundModels, customerRefundVos);
		Integer total = refundDao.getTotal(map);
		result.setRows(customerRefundVos);
		result.setTotal(total);
		return result;
	}

	@Override
	public OperationResult remove(Long report_id) {
		
		OperationResult result = new OperationResult();
		try {
			refundDao.remove(report_id);
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
	public OperationResult batchSaveRefunds(CustomerRefundVO[] customerRefundVos) {
		
		OperationResult result = new OperationResult();
		List<CustomerRefundVO> customerRefundList = Arrays.asList(customerRefundVos);
		List<CustomerRefundVO> insertRefunds = Lists.newArrayList();
		List<CustomerRefundVO> updateRefunds = Lists.newArrayList();
		for (CustomerRefundVO customerRefundVo : customerRefundList) {
			if(!ValidationUtil.isNull(customerRefundVo.getReport_id())){
				updateRefunds.add(customerRefundVo);
			}else{
				insertRefunds.add(customerRefundVo);
			}
		}
		try{
			if(insertRefunds.size()>0){
			OperationResult result2 = insertRefunds(insertRefunds);
			if(result2.getData().toString().equals("0")){
				result.setData("0");
			}
			}
			if(updateRefunds.size()>0){
				OperationResult result2 = updateRefunds(updateRefunds);
				if(result2.getData().toString().equals("0")){
					result.setData("0");
				}
			}
			
			result.setDescription("保存成功！");
		}catch (Exception e) {
			// TODO: handle exception
			result.setDescription("保持失败！");
			e.printStackTrace();
			result.setData("0");
		}
		return result;
	}

	@Override
	public OperationResult insertRefunds(List<CustomerRefundVO> customerRefundVos) {
		
		OperationResult result = new OperationResult();
		try {
			List<CustomerRefundModel> customerRefundModels = Lists.newArrayList();
			if(customerRefundVos.size()>0){
				for (CustomerRefundVO customerRefundVo : customerRefundVos) {
					customerRefundModels.add(customerRefundVo);
				}
			}
			refundDao.insertRefunds(customerRefundModels);
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
	public OperationResult updateRefunds(List<CustomerRefundVO> customerRefundVos) {
		
		OperationResult result = new OperationResult();
		try {
			List<CustomerRefundModel> customerRefundModels = Lists.newArrayList();
			if(customerRefundVos.size()>0){
				for (CustomerRefundVO customerRefundVo : customerRefundVos) {
					customerRefundModels.add(customerRefundVo);
				}
				refundDao.updateRefunds(customerRefundModels);
			}
			result.setDescription("更改成功！");
			result.setData("1");
		} catch (Exception e) {
			// TODO: handle exception
			result.setDescription("更改失败！");
			e.printStackTrace();
			result.setData("1");
			return result;
		}
		return result;
	}
	
	private void convertList(List<CustomerRefundModel> source, final List<CustomerRefundVO> target) {
		CollectionUtil.each(source, new IAction<CustomerRefundModel>() {
			@Override
			public void excute(CustomerRefundModel obj) {
				CustomerRefundVO customerRefundVo = new CustomerRefundVO();
				BeanUtils.copyProperties(obj, customerRefundVo);
				target.add(customerRefundVo);
			}
		});
	}

}
