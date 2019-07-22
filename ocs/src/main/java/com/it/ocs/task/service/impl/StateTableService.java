package com.it.ocs.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.task.dao.IStateTableDao;
import com.it.ocs.task.model.StateTableModel;
import com.it.ocs.task.service.IStateTableService;

@Service
public class StateTableService extends BaseService implements IStateTableService {

	@Autowired
	private IStateTableDao stateTableDao;
	@Override
	public StateTableModel selectStateTableById(Long id) {
		// TODO Auto-generated method stub
		StateTableModel result=null ;
		try {
			result = stateTableDao.selectStateTableById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult updateStateTableById(StateTableModel stateTableModel) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			stateTableDao.updateStateTableById(stateTableModel);
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			result.setDescription("update error!");
			e.printStackTrace();
		}
		return result;
	}

}
