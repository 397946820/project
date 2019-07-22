package com.it.ocs.task.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.task.dao.IMEbayOrderDao;
import com.it.ocs.task.model.EbayOrderModel;
import com.it.ocs.task.service.IMEBayOrderService;

@Service
public class MEbayOrderService implements IMEBayOrderService {
	
	@Autowired
	private IMEbayOrderDao mEbayOrderDao;
	@Override
	public String selectMaxLastFetchTime() {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.SLAVE);
		return mEbayOrderDao.selectMaxLastFetchTime();
	}
	@Override
	public List<EbayOrderModel> selectEbayOrderByStartTAndEndT(String startT, String endT) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.SLAVE);
		
		return mEbayOrderDao.selectEbayOrderByStartTAndEndT(startT, endT);
	}

}
