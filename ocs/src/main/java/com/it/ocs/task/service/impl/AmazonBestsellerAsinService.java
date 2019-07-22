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
import com.it.ocs.task.dao.IAmazonBestsellerAsinDao;
import com.it.ocs.task.model.AmazonBestsellerAsinModel;
import com.it.ocs.task.model.AmazonOrderModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonBestsellerAsinService;
import com.it.ocs.task.util.OutInfo;
@Service
public class AmazonBestsellerAsinService extends BaseService implements IAmazonBestsellerAsinService {

	@Autowired
	private IAmazonBestsellerAsinDao amazonBestsellerAsinDao;
	@Override
	public OperationResult synchronouAmazonBestsellerAsin(List<AmazonBestsellerAsinModel> amazonBestsellerAsinModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			
			DataSourceTypeManager.set(DataSources.MASTER);
			List<AmazonBestsellerAsinModel> updateAmazonBestsellerAsinModels = Lists.newArrayList();
			List<AmazonBestsellerAsinModel> oracleModels = Lists.newArrayList();
			
			int count = amazonBestsellerAsinModels.size();
			if(count>0){
				int k=0;
				for(int i=0; i<=count/500+1;i++){
					List<AmazonBestsellerAsinModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<count){
							list.add(amazonBestsellerAsinModels.get(k));
						}
					}
					if(list.size()!=0){
						if(selectAmazonBestsellerAsinModels(list).size()>0){
							oracleModels.addAll(selectAmazonBestsellerAsinModels(list));
						}
					}
				}
			}
			HashMap<Long, Long> oracleMap = new HashMap<>();
			
			for(AmazonBestsellerAsinModel amazonBestsellerAsinModel : oracleModels){
				oracleMap.put(amazonBestsellerAsinModel.getSource_id(), amazonBestsellerAsinModel.getSource_id());
			}
			
			for(AmazonBestsellerAsinModel amazonBestsellerAsinModel : amazonBestsellerAsinModels){
				
				if(oracleMap.get(amazonBestsellerAsinModel.getEntity_id())!=null){
					updateAmazonBestsellerAsinModels.add(amazonBestsellerAsinModel);
				}
			}
			
			if(updateAmazonBestsellerAsinModels.size()>0){
				
				amazonBestsellerAsinModels.removeAll(updateAmazonBestsellerAsinModels);
				
			}
			
			int insertCount = amazonBestsellerAsinModels.size();
			int updateCount = updateAmazonBestsellerAsinModels.size();
			if (insertCount>0) { 
				
				int k=0;
				
				for(int i=0; i<=insertCount/500+1;i++){
					List<AmazonBestsellerAsinModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<insertCount){
						  list.add(amazonBestsellerAsinModels.get(k));
						}
					}
					if (list.size()!=0){
						insertAmazonBestsellerAsins(list);
					}
					
				}
			}
			if(updateCount>0){
				
				int k=0;
				for(int i=0; i<=updateCount/500+1;i++){
					List<AmazonBestsellerAsinModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<updateCount){
						  list.add(updateAmazonBestsellerAsinModels.get(k));
						}
					}
					if (list.size()!=0){
						updateAmazonBestsellerAsins(list);
					}
					
				}
			}

		
		OutInfo.Out("      amazon_bestseller_asin添加的总数："+insertCount+"\n", OrderRecord.otherPath);
		OutInfo.Out("      amazon_bestseller_asin修改的总数："+updateCount+"\n", OrderRecord.otherPath);
		} catch (Exception e) {
			// TODO: handle exception
			OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			e.printStackTrace();
			
		}
		return result;
	}

	@Override
	public OperationResult insertAmazonBestsellerAsins(List<AmazonBestsellerAsinModel> amazonBestsellerAsinModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			amazonBestsellerAsinDao.insertAmazonBestsellerAsins(amazonBestsellerAsinModels);
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
	public OperationResult updateAmazonBestsellerAsins(List<AmazonBestsellerAsinModel> amazonBestsellerAsinModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			amazonBestsellerAsinDao.updateAmazonBestsellerAsins(amazonBestsellerAsinModels);
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
	public List<AmazonBestsellerAsinModel> selectMySqlDate(String date) {
		// TODO Auto-generated method stub
		return amazonBestsellerAsinDao.selectMySqlDate(date);
	}

	@Override
	public String selectMaxDate() {
		// TODO Auto-generated method stub
		return amazonBestsellerAsinDao.selectMaxDate();
	}

	@Override
	public List<AmazonBestsellerAsinModel> selectAmazonBestsellerAsinModels(
			List<AmazonBestsellerAsinModel> amazonBestsellerAsinModels) {
		// TODO Auto-generated method stub
		return amazonBestsellerAsinDao.selectAmazonBestsellerAsinModels(amazonBestsellerAsinModels);
	}

}
