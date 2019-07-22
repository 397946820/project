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
import com.it.ocs.customerCenter.dao.ICustomerProductDao;
import com.it.ocs.customerCenter.model.CustomerProductModel;
import com.it.ocs.customerCenter.service.ICustomerProductService;
import com.it.ocs.customerCenter.vo.CustomerProductVO;
import com.it.ocs.synchronou.util.ValidationUtil;
@Service
public class CustomerProductService extends BaseService implements ICustomerProductService {

	@Autowired
	private ICustomerProductDao customerProductDao;
	@Override
	public ResponseResult<CustomerProductVO> selectCustomerProducts(RequestParam param) {
		ResponseResult<CustomerProductVO> result = new ResponseResult<>();
		Map<String, Object> map = param.getParam();
		List<CustomerProductVO> customerProductVOs = Lists.newArrayList();
		List<CustomerProductModel> customerProductModels = customerProductDao.selectCustomerProduct(map, param.getStartRow(), param.getEndRow(), param.getOrder(), param.getSort());
		convertList(customerProductModels, customerProductVOs);
		Integer total = customerProductDao.getTotal(map);
		result.setRows(customerProductVOs);
		result.setTotal(total);
		return result;
	}
	

	private void convertList(List<CustomerProductModel> source, final List<CustomerProductVO> target) {
		CollectionUtil.each(source, new IAction<CustomerProductModel>() {
			@Override
			public void excute(CustomerProductModel obj) {
				CustomerProductVO customerProductVO = new CustomerProductVO();
				BeanUtils.copyProperties(obj, customerProductVO);
				target.add(customerProductVO);
			}
		});
	}


	@Override
	public OperationResult remove(Long product_id) {
		
		OperationResult result = new OperationResult();
		try {
			customerProductDao.remove(product_id);
			result.setDescription("删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			result.setDescription("删除失败！");
			e.printStackTrace();
			return result;
		}
		return result;
	}


	@Override
	public OperationResult batchSaveProducts(CustomerProductVO[] customerProductVOs) {
		
		OperationResult result = new OperationResult();
		List<CustomerProductVO> customerProductList = Arrays.asList(customerProductVOs);
		List<CustomerProductVO> insertProducts = Lists.newArrayList();
		List<CustomerProductVO> updateProduct = Lists.newArrayList();
		for (CustomerProductVO customerProductVO : customerProductList) {
			if(!ValidationUtil.isNull(customerProductVO.getProduct_id())){
				updateProduct.add(customerProductVO);
			}else{
				insertProducts.add(customerProductVO);
			}
		}
		try{
			if(insertProducts.size()>0){
			 OperationResult result2 = insertProducts(insertProducts);
			 
			 if(result2.getData().toString().equals("0")){
				 result.setData("0");
				}
			}
			if(updateProduct.size()>0){
				OperationResult result2 =updateProducts(updateProduct);
				if(result2.getData().toString().equals("0")){
					result.setData("0");
				}
			}
			result.setDescription("保存成功！");
		}catch (Exception e) {
			// TODO: handle exception
			result.setDescription("保持失败！");
			e.printStackTrace();
			result.setError("0");
		}
		return result;
	}


	@Override
	public OperationResult insertProducts(List<CustomerProductVO> customerProductVOs) {
		
		OperationResult result = new OperationResult();
		try {
			List<CustomerProductModel> customerProductModels = Lists.newArrayList();
			if(customerProductVOs.size()>0){
				
				for (CustomerProductVO customerProductVO : customerProductVOs) {
					customerProductModels.add(customerProductVO);
				}
				customerProductDao.insertProducts(customerProductModels);
			}
			result.setDescription("添加成功！");
			result.setData("1");
		} catch (Exception e) {
			// TODO: handle exception
			result.setDescription("添加失败");
			e.printStackTrace();
			result.setData("0");
			return result;
		}
		return result;
	}


	@Override
	public OperationResult updateProducts(List<CustomerProductVO> customerProductVOs) {
		
		OperationResult result = new OperationResult();
		try {
			List<CustomerProductModel> customerProductModels = Lists.newArrayList();
			
			if(customerProductVOs.size()>0){
				for (CustomerProductVO customerProductVO : customerProductVOs) {
					customerProductModels.add(customerProductVO);
				}
				customerProductDao.updateProducts(customerProductModels);
			}
			result.setDescription("更改成功！");
			result.setData("1");
			
		} catch (Exception e) {
			// TODO: handle exception
			result.setDescription("更改失败！");
			result.setData("0");
			e.printStackTrace();
			return result;
		}
		return result;
	}


}
