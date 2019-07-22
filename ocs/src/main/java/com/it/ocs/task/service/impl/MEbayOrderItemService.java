package com.it.ocs.task.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.task.dao.IMEbayOrderItemDao;
import com.it.ocs.task.model.EbayOrderItemModel;
import com.it.ocs.task.service.IMEBayOrderItemService;

@Service
public class MEbayOrderItemService implements IMEBayOrderItemService {

	@Autowired
	private IMEbayOrderItemDao mEbayOrderItemDao;
	@Override
	public List<EbayOrderItemModel> selectEbayOrderItemByStartTAndEndT(String startT, String endT) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.SLAVE);
		return mEbayOrderItemDao.selectEbayOrderItemByStartTAndEndT(startT, endT);
	}

}
