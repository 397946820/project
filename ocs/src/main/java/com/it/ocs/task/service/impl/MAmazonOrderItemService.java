package com.it.ocs.task.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.task.dao.IMAmazonOrderItemDao;
import com.it.ocs.task.model.AmazonOrderItemModel;
import com.it.ocs.task.service.IMAmazonOrderItemService;
@Service
public class MAmazonOrderItemService implements IMAmazonOrderItemService {

	@Autowired
	private IMAmazonOrderItemDao mAmazonOrderItemDao;
	@Override
	public List<AmazonOrderItemModel> selectAmazonOrderItemByStartTAndEndT(String startT, String endT) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.SLAVE);
		
		return mAmazonOrderItemDao.selectAmazonOrderItemByStartTAndEndT(startT, endT);
	}

	

}
