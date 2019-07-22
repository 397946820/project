package com.it.ocs.task.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.task.dao.IAmazonOrderItemDao;
import com.it.ocs.task.model.AmazonOrderItemModel;
import com.it.ocs.task.model.AmazonOrderModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonOrderItemService;
import com.it.ocs.task.util.OutInfo;
@Service
public class AmazonOrderItemService extends BaseService implements IAmazonOrderItemService {

	@Autowired
	private IAmazonOrderItemDao amazonOrderItemDao;

	@Override
	public OperationResult insertAmazonOrderItems(List<AmazonOrderItemModel> amazonOrderItemModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		
		for (AmazonOrderItemModel amazonOrderItemModel : amazonOrderItemModels) {
			 if (amazonOrderItemModel.getCreated_at()==null&&amazonOrderItemModel.getUpdated_at()!=null) {
				 amazonOrderItemModel.setCreated_at(amazonOrderItemModel.getUpdated_at());
			}else if(amazonOrderItemModel.getCreated_at()==null&&amazonOrderItemModel.getUpdated_at()!=null){
				Timestamp time = new Timestamp(System.currentTimeMillis());
				amazonOrderItemModel.setCreated_at(time);;
			}
			
		}
		
		try {
			
			amazonOrderItemDao.insertAmazonOrderItems(amazonOrderItemModels);
		
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
	public OperationResult updateAmazonOrderItems(List<AmazonOrderItemModel> amazonOrderItemModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		for (AmazonOrderItemModel amazonOrderItemModel : amazonOrderItemModels) {
			 if (amazonOrderItemModel.getCreated_at()==null&&amazonOrderItemModel.getUpdated_at()!=null) {
				 amazonOrderItemModel.setCreated_at(amazonOrderItemModel.getUpdated_at());
			}else if(amazonOrderItemModel.getCreated_at()==null&&amazonOrderItemModel.getUpdated_at()!=null){
				Timestamp time = new Timestamp(System.currentTimeMillis());
				amazonOrderItemModel.setCreated_at(time);;
			}
			
		}
		
		try {
			amazonOrderItemDao.updateAmazonOrderItems(amazonOrderItemModels);
			
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
	public List<AmazonOrderItemModel> findAmazonOrderItems(List<AmazonOrderItemModel> amazonOrderItemModels) {
		// TODO Auto-generated method stub
		return amazonOrderItemDao.findAmazonOrderItems(amazonOrderItemModels);
	}

	

}
