package com.it.ocs.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.task.dao.IEbayOrderItemDao;
import com.it.ocs.task.model.EbayOrderItemModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IEbayOrderItemService;
import com.it.ocs.task.util.OutInfo;

@Service
public class EbayOrderItemService extends BaseService implements IEbayOrderItemService {
	
	@Autowired
	private IEbayOrderItemDao ebayOrderItemDao;
	@Override
	public OperationResult insertEbayOrderItems(List<EbayOrderItemModel> ebayOrderItemModels) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.MASTER);
		OperationResult result = new OperationResult();
		try {
			
			ebayOrderItemDao.insertEbayOrderItems(ebayOrderItemModels);
		
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			
			result.setDescription("insert error");
			
			OutInfo.Out(e.toString()+"\n", OrderRecord.path);
			
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult updateEbayOrderItems(List<EbayOrderItemModel> ebayOrderItemModels) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.MASTER);
		OperationResult result = new OperationResult();
		try {
			
			ebayOrderItemDao.updateEbayOrderItems(ebayOrderItemModels);
		
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			
			result.setDescription("update error");
			OutInfo.Out(e.toString()+"\n", OrderRecord.path);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<EbayOrderItemModel> findEbayOrderItems() {
		// TODO Auto-generated method stub
		return ebayOrderItemDao.findEbayOrderItems();
	}

}
