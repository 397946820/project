package com.it.ocs.task.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.task.dao.IEbayOrderSubDao;
import com.it.ocs.task.model.AmazonOrderModel;
import com.it.ocs.task.model.EbayOrderItemModel;
import com.it.ocs.task.model.EbayOrderModel;
import com.it.ocs.task.model.EbayOrderSubModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IEbayOrderService;
import com.it.ocs.task.service.IEbayOrderSubService;
import com.it.ocs.task.util.OutInfo;

@Service
public class EbayOrderSubService extends BaseService implements IEbayOrderSubService {

	@Autowired 
	private IEbayOrderSubDao ebayOrderSubDao;
	
	@Override
	public OperationResult insertEbayOrderSubs(List<EbayOrderSubModel> ebayOrderSubModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			ebayOrderSubDao.insertEbayOrderSubs(ebayOrderSubModels);
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
	public OperationResult updateEbayOrderSubs(List<EbayOrderSubModel> ebayOrderSubModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			ebayOrderSubDao.updateEbayOrderSubs(ebayOrderSubModels);
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
	public List<EbayOrderSubModel> findEbayOrderSubs() {
		// TODO Auto-generated method stub
		return ebayOrderSubDao.findEbayOrderSubs();
	}

	@Override
	public List<Long> getOrderSubSeqs(Long size) {
		// TODO Auto-generated method stub
				List<Long> seqs = new ArrayList<>();
				try{
				for (int i=1; i <= size; i++) {
					Long seq = ebayOrderSubDao.getOrderSubSeqs();
					seqs.add(seq);
				}
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return seqs;
	}

	

}
