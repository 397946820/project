package com.it.ocs.task.service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.task.model.StateTableModel;

public interface IStateTableService {
	
	public StateTableModel selectStateTableById(Long id);
	
	public OperationResult updateStateTableById(StateTableModel stateTableModel);
}
