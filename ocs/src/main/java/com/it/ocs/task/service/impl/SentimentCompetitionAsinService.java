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
import com.it.ocs.task.dao.ISentimentCompetitionAsinDao;
import com.it.ocs.task.model.AmazonReportsBestsellerModel;
import com.it.ocs.task.model.SentimentCompetitionAsinModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.ISentimentCompetitionAsinService;
import com.it.ocs.task.util.OutInfo;
@Service
public class SentimentCompetitionAsinService extends BaseService implements ISentimentCompetitionAsinService {
	@Autowired
	private ISentimentCompetitionAsinDao sentimentCompetitionAsinDao;
	@Override
	public OperationResult synchronouSentimentCompetitionAsin(
			List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			DataSourceTypeManager.set(DataSources.MASTER);
			List<SentimentCompetitionAsinModel> updateSentimentCompetitionAsinModels = Lists.newArrayList();
			List<SentimentCompetitionAsinModel> oracleModels = Lists.newArrayList();
			int count = sentimentCompetitionAsinModels.size();
			
			if(count>0){
				int k=0;
				for(int i=0; i<=count/500+1;i++){
					List<SentimentCompetitionAsinModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<count){
							list.add(sentimentCompetitionAsinModels.get(k));
						}
					}
					if(list.size()!=0){
						if(selectSentimentCompetitionAsinModels(list).size()>0){
							oracleModels.addAll(selectSentimentCompetitionAsinModels(list));
						}
					}
				}
			}
			
			HashMap<Long, Long> oracleMap = new HashMap<>();
			for (SentimentCompetitionAsinModel sentimentCompetitionAsinModel : oracleModels) {
				oracleMap.put(sentimentCompetitionAsinModel.getSource_id(), sentimentCompetitionAsinModel.getSource_id());
			}
			
			for (SentimentCompetitionAsinModel sentimentCompetitionAsinModel : sentimentCompetitionAsinModels) {
				
				if(oracleMap.get(sentimentCompetitionAsinModel.getEntity_id())!=null){
					
					updateSentimentCompetitionAsinModels.add(sentimentCompetitionAsinModel);
				}
			}
			if(updateSentimentCompetitionAsinModels.size()>0){
				sentimentCompetitionAsinModels.removeAll(updateSentimentCompetitionAsinModels);
			}
			int insertCount = sentimentCompetitionAsinModels.size();
			int updateCount = updateSentimentCompetitionAsinModels.size();
			if (insertCount>0) { 
				
				int k=0;
				
				for(int i=0; i<=insertCount/2+1;i++){
					List<SentimentCompetitionAsinModel> list = Lists.newArrayList();
					for(int j=0;j<2;j++,k++){
						if(k<insertCount){
						  list.add(sentimentCompetitionAsinModels.get(k));
						}
					}
					if (list.size()!=0){
						insertSentimentCompetitionAsins(list);
						updateInsertData(list);
					}
					
				}
			}
			if(updateCount>0){
				
				int k=0;
				for(int i=0; i<=updateCount/500+1;i++){
					List<SentimentCompetitionAsinModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<updateCount){
						  list.add(updateSentimentCompetitionAsinModels.get(k));
						}
					}
					if (list.size()!=0){
						updateSentimentCompetitionAsins(list);
					}
					
				}
			}
			OutInfo.Out("      sentiment_competition_asin添加的总数："+insertCount+"\n", OrderRecord.otherPath);
			OutInfo.Out("      sentiment_competition_asin修改的总数："+updateCount+"\n", OrderRecord.otherPath);

		}catch (Exception e) {
			// TODO: handle exception
			OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult insertSentimentCompetitionAsins(
			List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels) {
		// TODO Auto-generated method stub
		OperationResult result  = new OperationResult();
		try {
			sentimentCompetitionAsinDao.insertSentimentCompetitionAsins(sentimentCompetitionAsinModels);
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
	public OperationResult updateSentimentCompetitionAsins(
			List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			sentimentCompetitionAsinDao.updateSentimentCompetitionAsins(sentimentCompetitionAsinModels);
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
		return sentimentCompetitionAsinDao.selectMaxDate();
	}

	@Override
	public List<SentimentCompetitionAsinModel> selectMySqlDate(String date) {
		// TODO Auto-generated method stub
		return sentimentCompetitionAsinDao.selectMySqlDate(date);
	}

	@Override
	public List<SentimentCompetitionAsinModel> selectSentimentCompetitionAsinModels(
			List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels) {
		// TODO Auto-generated method stub
		return sentimentCompetitionAsinDao.selectSentimentCompetitionAsinModels(sentimentCompetitionAsinModels);
	}

	@Override
	public OperationResult updateInsertData(List<SentimentCompetitionAsinModel> sentimentCompetitionAsinModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			sentimentCompetitionAsinDao.updateInsertData(sentimentCompetitionAsinModels);
			result.setDescription("更改成功！");
		}catch (Exception e) {
			// TODO: handle exception
			result.setDescription("更改失败！");
			OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			e.printStackTrace();
		}
		return result;
	}

}
