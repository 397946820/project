package com.it.ocs.task.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.task.dao.IAmazonOfferDao;
import com.it.ocs.task.model.AmazonBestsellerAsinModel;
import com.it.ocs.task.model.AmazonOfferModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonOfferService;
import com.it.ocs.task.util.OutInfo;
@Service
public class AmazonOfferService extends BaseService implements IAmazonOfferService {

	@Autowired
	private IAmazonOfferDao amazonOfferDao;
	@Override
	public OperationResult synchronouAmazonOffer(List<AmazonOfferModel> amazonOfferModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			DataSourceTypeManager.set(DataSources.MASTER);
			List<AmazonOfferModel> updateAmazonOfferModels = Lists.newArrayList();
			List<AmazonOfferModel> oracleModels = Lists.newArrayList();
			int count = amazonOfferModels.size();
			if(count>0){
				int k=0;
				for(int i=0; i<=count/500+1;i++){
					List<AmazonOfferModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<count){
							list.add(amazonOfferModels.get(k));
						}
					}
					if(list.size()!=0){
						if(selectAmazonOfferModels(list).size()>0){
							oracleModels.addAll(selectAmazonOfferModels(list));
						}
					}
				}
			}
			HashMap<Long, Long> oracleMap = new HashMap<>();
			for(AmazonOfferModel amazonOfferModel : oracleModels){
				oracleMap.put(amazonOfferModel.getSource_id(), amazonOfferModel.getSource_id());
			}
			for (AmazonOfferModel amazonOfferModel : amazonOfferModels) {
				if(oracleMap.get(amazonOfferModel.getEntity_id())!=null){
					updateAmazonOfferModels.add(amazonOfferModel);
				}
			}
			if(updateAmazonOfferModels.size()>0){
				amazonOfferModels.removeAll(updateAmazonOfferModels);
			}
			int insertCount = amazonOfferModels.size();
			int updateCount = updateAmazonOfferModels.size();
			if (insertCount>0) { 
				
				int k=0;
				
				for(int i=0; i<=insertCount/500+1;i++){
					List<AmazonOfferModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<insertCount){
						  list.add(amazonOfferModels.get(k));
						}
					}
					if (list.size()!=0){
						insertAmazonOffers(list);
					}
					
				}
			}
			if(updateCount>0){
				
				int k=0;
				for(int i=0; i<=updateCount/500+1;i++){
					List<AmazonOfferModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<updateCount){
						  list.add(updateAmazonOfferModels.get(k));
						}
					}
					if (list.size()!=0){
						updateAmazonOffers(list);
					}
					
				}
			}
			OutInfo.Out("      amazon_offer添加的总数："+insertCount+"\n", OrderRecord.otherPath);
			OutInfo.Out("      amazon_offer修改的总数："+updateCount+"\n", OrderRecord.otherPath);
		} catch (Exception e) {
			// TODO: handle exception
			OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			e.printStackTrace();
			
		}
		return result;
	}

	@Override
	public OperationResult insertAmazonOffers(List<AmazonOfferModel> amazonOfferModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			amazonOfferDao.insertAmazonOffers(amazonOfferModels);
			result.setDescription("添加成功！");
			
		} catch (Exception e) {
			// TODO: handle exception
			result.setDescription("添加失败！");
			OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult updateAmazonOffers(List<AmazonOfferModel> amazonOfferModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
	    try {
			amazonOfferDao.updateAmazonOffers(amazonOfferModels);
			result.setDescription("更改成功！");
		} catch (Exception e) {
			// TODO: handle exception
			 result.setDescription("更改失败！");
			   OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			   e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<AmazonOfferModel> selectMySqlDate(String date) {
		// TODO Auto-generated method stub
		return amazonOfferDao.selectMySqlDate(date);
	}

	@Override
	public List<AmazonOfferModel> selectAmazonOfferModels(List<AmazonOfferModel> amazonOfferModels) {
		// TODO Auto-generated method stub
		return amazonOfferDao.selectAmazonOfferModels(amazonOfferModels);
	}

	@Override
	public String selectMaxDate() {
		// TODO Auto-generated method stub
		return amazonOfferDao.selectMaxDate();
	}

}
