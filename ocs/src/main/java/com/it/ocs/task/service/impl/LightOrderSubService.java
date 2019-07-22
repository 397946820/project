package com.it.ocs.task.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.task.dao.ILightOrderSubDao;
import com.it.ocs.task.model.LightOrderSubModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.ILightOrderSubService;
import com.it.ocs.task.util.OutInfo;

@Service
public class LightOrderSubService extends BaseService implements ILightOrderSubService {

	@Autowired
	private ILightOrderSubDao lightOrderSubDao;
	@Override
	public OperationResult insertLightOrderSubs(List<LightOrderSubModel> lightOrderSubModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			lightOrderSubDao.insertLightOrderSubs(lightOrderSubModels);
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
	public OperationResult updateLightOrderSubs(List<LightOrderSubModel> lightOrderSubModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			lightOrderSubDao.updateLightOrderSubs(lightOrderSubModels);
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
	public List<LightOrderSubModel> findLightOrderSubs() {
		// TODO Auto-generated method stub
		return lightOrderSubDao.findLightOrderSubs();
	}

	@Override
	public List<Long> getOrderSeqsBySize(Long size) {
		// TODO Auto-generated method stub
		List<Long> seqs = new ArrayList<>();
		try{
		for (int i=1; i <= size; i++) {
			Long seq = lightOrderSubDao.getOrderSubSeqs();
			seqs.add(seq);
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return seqs;
	}

}
