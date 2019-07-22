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
import com.it.ocs.task.dao.IAmazonSkuHistoryDao;
import com.it.ocs.task.model.AmazonReportsBestsellerModel;
import com.it.ocs.task.model.AmazonSkuHistoryModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonSkuHistoryService;
import com.it.ocs.task.util.OutInfo;
@Service
public class AmazonSkuHistoryService extends BaseService implements IAmazonSkuHistoryService {

	@Autowired
	private IAmazonSkuHistoryDao amazonSkuHistoryDao;
	@Override
	public OperationResult synchronouAmazonSkuHistory(List<AmazonSkuHistoryModel> amazonSkuHistoryModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			
			DataSourceTypeManager.set(DataSources.MASTER);
			
			List<AmazonSkuHistoryModel> updateAmazonSkuHistoryModels = Lists.newArrayList();
			List<AmazonSkuHistoryModel> oracleModels = Lists.newArrayList();
			int count = amazonSkuHistoryModels.size();
			if(count>0){
				int k=0;
				for(int i=0; i<=count/500+1;i++){
					List<AmazonSkuHistoryModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<count){
							list.add(amazonSkuHistoryModels.get(k));
						}
					}
					if(list.size()!=0){
						if(selectAmazonSkuHistoryModels(list).size()>0){
							oracleModels.addAll(selectAmazonSkuHistoryModels(list));
						}
					}
				}
			}
			HashMap<Long, Long> oracleMap = new HashMap<>();
			for (AmazonSkuHistoryModel amazonSkuHistoryModel : oracleModels) {
				oracleMap.put(amazonSkuHistoryModel.getSource_id(), amazonSkuHistoryModel.getSource_id());
			}
			for (AmazonSkuHistoryModel amazonSkuHistoryModel : amazonSkuHistoryModels) {
				if(oracleMap.get(amazonSkuHistoryModel.getEntity_id())!=null){
					updateAmazonSkuHistoryModels.add(amazonSkuHistoryModel);
				}
			}
			if(updateAmazonSkuHistoryModels.size()>0){
				amazonSkuHistoryModels.removeAll(updateAmazonSkuHistoryModels);
			}
			int insertCount = amazonSkuHistoryModels.size();
			int updateCount = updateAmazonSkuHistoryModels.size();
			if (insertCount>0) { 
				
				int k=0;
				
				for(int i=0; i<=insertCount/500+1;i++){
					List<AmazonSkuHistoryModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<insertCount){
						  list.add(amazonSkuHistoryModels.get(k));
						}
					}
					if (list.size()!=0){
						insertAmazonSkuHistorys(list);
					}
				}
			}
			if(updateCount>0){
				
				int k=0;
				for(int i=0; i<=updateCount/500+1;i++){
					List<AmazonSkuHistoryModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<updateCount){
						  list.add(updateAmazonSkuHistoryModels.get(k));
						}
					}
					if (list.size()!=0){
						updateAmazonSkuHistorys(list);
					}
					
				}
			}
			OutInfo.Out("      amazon_sku_history添加的总数："+insertCount+"\n", OrderRecord.otherPath);
			OutInfo.Out("      amazon_sku_history修改的总数："+updateCount+"\n", OrderRecord.otherPath);

		}catch (Exception e) {
			// TODO: handle exception
			OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult insertAmazonSkuHistorys(List<AmazonSkuHistoryModel> amazonSkuHistoryModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			amazonSkuHistoryDao.insertAmazonSkuHistorys(amazonSkuHistoryModels);
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
	public OperationResult updateAmazonSkuHistorys(List<AmazonSkuHistoryModel> amazonSkuHistoryModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			amazonSkuHistoryDao.updateAmazonSkuHistorys(amazonSkuHistoryModels);
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
	public String selectMaxDate() {
		// TODO Auto-generated method stub
		return amazonSkuHistoryDao.selectMaxDate();
	}

	@Override
	public List<AmazonSkuHistoryModel> selectMySqlDate(String date) {
		// TODO Auto-generated method stub
		return amazonSkuHistoryDao.selectMySqlDate(date);
	}

	@Override
	public List<AmazonSkuHistoryModel> selectAmazonSkuHistoryModels(
			List<AmazonSkuHistoryModel> amazonSkuHistoryModels) {
		// TODO Auto-generated method stub
		return amazonSkuHistoryDao.selectAmazonSkuHistoryModels(amazonSkuHistoryModels);
	}

	@Override
	public OperationResult updateInsertData(List<AmazonSkuHistoryModel> amazonSkuHistoryModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			amazonSkuHistoryDao.updateInsertDate(amazonSkuHistoryModels);
			result.setDescription("更改成功！");
		} catch (Exception e) {
			// TODO: handle exception
			 result.setDescription("更改失败！");
			 OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			 e.printStackTrace();
		}
		return result;
	}

}
