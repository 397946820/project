package com.it.ocs.task.dao;

import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.task.model.StateTableModel;

@Repository
public interface IStateTableDao extends IBaseDao<StateTableModel>{

	public StateTableModel selectStateTableById(Long id);
	
	public void updateStateTableById(StateTableModel stateTableModel);
}
