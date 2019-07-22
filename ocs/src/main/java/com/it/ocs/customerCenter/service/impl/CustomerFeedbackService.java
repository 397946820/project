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
import com.it.ocs.customerCenter.dao.ICustomerFeedbackDao;
import com.it.ocs.customerCenter.model.CustomerFeedbackModel;
import com.it.ocs.customerCenter.service.ICustomerFeedbackService;
import com.it.ocs.customerCenter.vo.CustomerFeedbackVO;
import com.it.ocs.synchronou.util.ValidationUtil;

@Service
public class CustomerFeedbackService extends BaseService implements ICustomerFeedbackService {

	@Autowired
	private ICustomerFeedbackDao customerFeedbackDao;
	@Override
	public ResponseResult<CustomerFeedbackVO> selectCustomerFeecBacks(RequestParam param) {
		ResponseResult<CustomerFeedbackVO> result = new ResponseResult<>();
		Map<String, Object> map = param.getParam();
		List<CustomerFeedbackModel> customerFeedbackModels = customerFeedbackDao.selectCustomerFeecBacks(map, param.getStartRow(), param.getEndRow(), param.getOrder(), param.getSort());
		List<CustomerFeedbackVO> customerFeedbackVOs = Lists.newArrayList();
		convertList(customerFeedbackModels, customerFeedbackVOs);
		Integer total = customerFeedbackDao.getTotal(map);
		result.setRows(customerFeedbackVOs);
		result.setTotal(total);
		return result;
	}
	
	private void convertList(List<CustomerFeedbackModel> source, final List<CustomerFeedbackVO> target) {
		CollectionUtil.each(source, new IAction<CustomerFeedbackModel>() {
			@Override
			public void excute(CustomerFeedbackModel obj) {
				CustomerFeedbackVO customerFeedbackVO = new CustomerFeedbackVO();
				BeanUtils.copyProperties(obj, customerFeedbackVO);
				target.add(customerFeedbackVO);
			}
		});
	}

	@Override
	public OperationResult remove(Long feedbacks_id) {
		
		OperationResult result = new OperationResult();
		try {
			customerFeedbackDao.remove(feedbacks_id);
			result.setDescription("删除成功！");
		} catch (Exception e) {
			result.setDescription("删除失败！"); 
			e.printStackTrace();
			return result ;
		}
		return result;
	}

	@Override
	public OperationResult batchSaveFeedbacks(CustomerFeedbackVO[] customerFeedbackVOs) {
		
		OperationResult result = new OperationResult();
		List<CustomerFeedbackVO> customerFeedbackList = Arrays.asList(customerFeedbackVOs);
		List<CustomerFeedbackVO> insertFeedbacks = Lists.newArrayList();
		List<CustomerFeedbackVO> updateFeedbacks = Lists.newArrayList();
		for (CustomerFeedbackVO customerFeedbackVO : customerFeedbackList) {
			if(!ValidationUtil.isNull(customerFeedbackVO.getFeedbacks_id())){
				updateFeedbacks.add(customerFeedbackVO);
			}else{
				insertFeedbacks.add(customerFeedbackVO);
			}
		}
		try {
			if (insertFeedbacks.size()>0) {
				
				OperationResult result2 = insertFeedbacks(insertFeedbacks);
				if(result2.getData().toString().equals("0")){
					result.setData("0");
				}
			}
			if (updateFeedbacks.size()>0) {
				OperationResult result2 =  updateFeedbacks(updateFeedbacks);
				if(result2.getData().toString().equals("0")){
					result.setData("0");
				}
			}
			
		} catch (Exception e) {
			result.setDescription("保存失败！");
			e.printStackTrace();
			result.setData("0");
		}
		
		return result;
	}

	@Override
	public OperationResult updateFeedbacks(List<CustomerFeedbackVO> customerFeedbackVOs) {
		
		OperationResult result = new OperationResult();
		try {
			customerFeedbackDao.updateFeedbacks(customerFeedbackVOs);
			result.setDescription("更改成功！");
			result.setData("1");
		} catch (Exception e) {
			// TODO: handle exception
			result.setDescription("更改失败！");
			e.printStackTrace();
			result.setData("0");
			return result;
		}
		return result;
	}

	@Override
	public OperationResult insertFeedbacks(List<CustomerFeedbackVO> customerFeedbackVOs) {
		OperationResult result = new OperationResult();
		try {
			customerFeedbackDao.insertFeedback(customerFeedbackVOs);
			result.setDescription("添加成功！");
			result.setData("1");
		} catch (Exception e) {
			result.setDescription("添加失败！");
			e.printStackTrace();
			result.setData("0");
		}
		return result;
	}

}
