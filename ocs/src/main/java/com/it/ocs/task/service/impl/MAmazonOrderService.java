package com.it.ocs.task.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.task.dao.IMAmazonOrderDao;
import com.it.ocs.task.model.AmazonOrderModel;
import com.it.ocs.task.service.IMAmazonOrderService;
@Service
public class MAmazonOrderService implements IMAmazonOrderService {

	@Autowired
	private IMAmazonOrderDao mAmazonOrderDao;
	@Override
	public String selectMaxCreatedAt() {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.SLAVE);
		
		return mAmazonOrderDao.selectMaxCreatedAt();
	}
	@Override
	public List<AmazonOrderModel> selectAmazonOrderByStartTAndEndT(String startT, String endT) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.SLAVE);
		
		return mAmazonOrderDao.selectAmazonOrderByStartTAndEndT(startT, endT);
	}

}
