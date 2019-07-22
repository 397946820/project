package com.it.ocs.sys.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.service.BaseService;
import com.it.ocs.sys.dao.ISystemLogDao;
import com.it.ocs.sys.model.SystemLogModel;

@Service
public class SystemLogService extends BaseService{
	@Autowired
	private ISystemLogDao iSystemLogDao;
	
	public void addLog(SystemLogModel logModel) {
		super.insertInit(logModel);
		iSystemLogDao.addLog(logModel);
	}

}
