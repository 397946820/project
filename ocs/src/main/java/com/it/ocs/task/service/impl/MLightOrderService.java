package com.it.ocs.task.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.task.dao.IMLightOrderDao;
import com.it.ocs.task.model.LightOrderModel;
import com.it.ocs.task.service.IMLightOrderService;

@Service
public class MLightOrderService implements IMLightOrderService {
	
	@Autowired
	private IMLightOrderDao mLightOrderDao;
	@Override
	public String selectMaxCreatedAt() {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.SLAVE);
		return mLightOrderDao.selectMaxCreatedAt();
	}
	@Override
	public List<LightOrderModel> selectLightOrderByStartTAndEndT(String startT, String entT) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.SLAVE);
		return mLightOrderDao.selectLightOrderByStartTAndEndT(startT, entT);
	}

}
