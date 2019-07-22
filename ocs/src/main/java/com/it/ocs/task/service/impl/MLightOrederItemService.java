package com.it.ocs.task.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.task.dao.IMLightOrderItemDao;
import com.it.ocs.task.model.LightOrderItemModel;
import com.it.ocs.task.service.IMLightOrderItemService;


@Service
public class MLightOrederItemService implements IMLightOrderItemService {

	@Autowired
	private IMLightOrderItemDao mLightOrderItemDao;
	@Override
	public List<LightOrderItemModel> selectLightOrderItemByStartTAndEndT(String startT, String endT) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.SLAVE);
		
		return mLightOrderItemDao.selectLightOrderItemByStartTAndEndT(startT, endT);
	}

}
