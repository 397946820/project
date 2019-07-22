package com.it.ocs.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.task.dao.ILightOrderItemDao;
import com.it.ocs.task.model.AmazonOrderItemModel;
import com.it.ocs.task.model.LightOrderItemModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonOrderItemService;
import com.it.ocs.task.service.ILightOrderItemService;
import com.it.ocs.task.util.OutInfo;
@Service
public class LightOrderItemService extends BaseService implements ILightOrderItemService {

	@Autowired
	private ILightOrderItemDao lightOrderItemDao;
	@Override
	public OperationResult insertLightOrderItems(List<LightOrderItemModel> lightOrderItemModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			lightOrderItemDao.insertLightOrderItems(lightOrderItemModels);
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			result.setDescription("insert error");
			OutInfo.Out(e.toString()+"\n", OrderRecord.path);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult updateLightOrderItems(List<LightOrderItemModel> lightOrderItemModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			lightOrderItemDao.updateLightOrderItems(lightOrderItemModels);
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			result.setDescription("update error");
			OutInfo.Out(e.toString()+"\n", OrderRecord.path);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<LightOrderItemModel> findLightOrderItems(List<LightOrderItemModel> lightOrderItemModels) {
		// TODO Auto-generated method stub
		return lightOrderItemDao.findLightOrderItems(lightOrderItemModels);
	}

	

}
