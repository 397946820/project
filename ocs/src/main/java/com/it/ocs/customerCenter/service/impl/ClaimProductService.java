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
import com.it.ocs.customerCenter.dao.IClaimProductDao;
import com.it.ocs.customerCenter.model.ClaimProductModel;
import com.it.ocs.customerCenter.service.IClaimProductService;
import com.it.ocs.customerCenter.vo.ClaimProductVO;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.sys.model.UserModel;

@Service
public class ClaimProductService extends BaseService implements IClaimProductService{

	@Autowired
	private IClaimProductDao claimProductDao;
	@Override
	public ResponseResult<ClaimProductVO> selectClaimProducts(RequestParam param) {
		
		ResponseResult<ClaimProductVO> result = new ResponseResult<>();
		Map<String, Object> map = param.getParam();
		List<ClaimProductModel> claimProductModels =  claimProductDao.selectClaimProducts(map, param.getStartRow(),param.getEndRow(), param.getOrder(), param.getSort());
		List<ClaimProductVO> claimProductVOs = Lists.newArrayList();
		convertList(claimProductModels, claimProductVOs);
		Integer total = claimProductDao.getTotal(map);
		result.setRows(claimProductVOs);
		result.setTotal(total);
		return result;
	}
	private void convertList(List<ClaimProductModel> source, final List<ClaimProductVO> target) {
		CollectionUtil.each(source, new IAction<ClaimProductModel>() {
			@Override
			public void excute(ClaimProductModel obj) {
				ClaimProductVO claimProductVO = new ClaimProductVO();
				BeanUtils.copyProperties(obj, claimProductVO);
				target.add(claimProductVO);
			}
		});
	}
	@Override
	public OperationResult batchSaveClaimProduct(ClaimProductVO[] claimProductVOs) {
		
		OperationResult result = new OperationResult();
		List<ClaimProductVO> claimProductList = Arrays.asList(claimProductVOs);
		List<ClaimProductVO> insertClaimProductList = Lists.newArrayList();
		List<ClaimProductVO> updateClaimProduct = Lists.newArrayList();
		UserModel userModel =  super.getCurentUser();
		Long userId = userModel.getId();
		for (ClaimProductVO claimProductVO : claimProductList) {
			claimProductVO.setCreate_by(userId);
			if(!ValidationUtil.isNull(claimProductVO.getId())){
				
				updateClaimProduct.add(claimProductVO);
			}else{
				insertClaimProductList.add(claimProductVO);
			}
		}
		try {
			if(insertClaimProductList.size()>0){
				OperationResult result2 = insertClaimProduct(insertClaimProductList);
				if(result2.getData().toString().equals("0")){
					result.setData(0);
				}
			}
			if(updateClaimProduct.size()>0){
				OperationResult  result2 = updateClaimProduct(updateClaimProduct);
				if(result2.getData().toString().equals("0")){
					result.setData("0");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setDescription("保存失败！");
			e.printStackTrace();
			result.setData("0");
		}
		return result;
	}
	@Override
	public OperationResult insertClaimProduct(List<ClaimProductVO> claimProductVOs) {
		
		OperationResult result = new OperationResult();
		try {
			claimProductDao.insertClaimProduct(claimProductVOs);
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
	public OperationResult updateClaimProduct(List<ClaimProductVO> claimProductVOs) {
		
		OperationResult result = new OperationResult();
		try {
			claimProductDao.updateClaimProduct(claimProductVOs);
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
	public OperationResult remove(Long id) {
		
		OperationResult result = new OperationResult();
		try {
			UserModel userModel = super.getCurentUser();
			claimProductDao.remove(id,userModel.getId());
			result.setDescription("删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			result.setDescription("删除失败！");
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public OperationResult selectClaimProductByOID(String orderId,Long id) {
		
		OperationResult result = new OperationResult();
		List<ClaimProductModel> claimProductModels = claimProductDao.selectClaimProductByOID(orderId,id);
		result.setData(claimProductModels.size());
		result.setDescription(orderId);
		return result;
	}
	
	
}
