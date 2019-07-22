package com.it.ocs.task.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.listener.ProjectApplicationContext;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.task.dao.IAmazonProductQaDao;
import com.it.ocs.task.model.AmazonBestsellerAsinModel;
import com.it.ocs.task.model.AmazonProductQaModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonProductQaService;
import com.it.ocs.task.util.OutInfo;
@Service
public class AmazonProductQaService extends BaseService implements IAmazonProductQaService {
	
	@Autowired
	private IAmazonProductQaDao amazonProductQaDao;
	@Autowired
	private ProjectApplicationContext pac;
	@Override
	public OperationResult synchronouAmazonProductQa(List<AmazonProductQaModel> amazonProductQaModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			DataSourceTypeManager.set(DataSources.MASTER);
			List<AmazonProductQaModel> updateAmazonProductQas = Lists.newArrayList();
			List<AmazonProductQaModel> oracleModels = Lists.newArrayList();
			int count = amazonProductQaModels.size();
			if(count>0){
				int k=0;
				for(int i=0; i<=count/500+1;i++){
					List<AmazonProductQaModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<count){
							list.add(amazonProductQaModels.get(i));
						}
					}
					if(list.size()!=0){
						if(selectAmazonProductQaModels(list).size()>0){
							oracleModels.addAll(selectAmazonProductQaModels(list));
						}
					}
				}
			}
			HashMap<Long, Long> oracleMap = new HashMap<>();
			for (AmazonProductQaModel amazonProductQaModel : oracleModels) {
				Long source_id = amazonProductQaModel.getSource_id();
				oracleMap.put(source_id, source_id);
			}
			for (AmazonProductQaModel amazonProductQaModel : amazonProductQaModels) {
				if(oracleMap.get(amazonProductQaModel.getEntity_id())!=null){
					updateAmazonProductQas.add(amazonProductQaModel);
				}
			}
			if(updateAmazonProductQas.size()>0){
				amazonProductQaModels.removeAll(updateAmazonProductQas);
			}
			int insertCount = amazonProductQaModels.size();
			int updateCount = updateAmazonProductQas.size();
			
			if (insertCount>0) { 
				int k=0;
				for(int i=0; i<=insertCount/500+1;i++){
					List<AmazonProductQaModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<insertCount){
						  list.add(amazonProductQaModels.get(k));
						}
					}
					if (list.size()!=0){
						insertAmazonProductQas(list);
					}
					
				}
			}
			if(updateCount>0){
				
				int k=0;
				for(int i=0; i<=updateCount/500+1;i++){
					List<AmazonProductQaModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<updateCount){
						  list.add(updateAmazonProductQas.get(k));
						}
					}
					if (list.size()!=0){
						updateAmazonProductQas(list);
					}
					
				}
			}
			OutInfo.Out("      amazon_product_qa添加的总数："+insertCount+"\n", OrderRecord.otherPath);
			OutInfo.Out("      amazon_product_qa修改的总数："+updateCount+"\n", OrderRecord.otherPath);
		} catch (Exception e) {
			// TODO: handle exception
			OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult insertAmazonProductQas(List<AmazonProductQaModel> amazonProductQaModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try{
			/*DataSource dataSource = (DataSource) pac.getBean("dataSource");
			DataSourceTypeManager.set(DataSources.MASTER);
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			JdbcTemplate jt = new JdbcTemplate(dataSource);
			String sql = "INSERT INTO amazon_product_qa (ENTITY_ID,"
					+ "PLATFORM,ASIN,PRODUCT_ID,QUESTION,FORUM,ANSWERS,"
					+ "ANSWER_TOTAL,ANSWER_UPDATED_AT,HAS_NEW,QA_TOTAL,CREATED_AT,"
					+ "UPDATED_AT,SOURCE_ID,CREATION_DATE)   "
					+ " values(amazon_product_qa_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			jt.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					String platform = amazonProductQaModels.get(i).getPlatform();
					if(ValidationUtil.isNull(platform)){
						ps.setNull(1, Types.VARCHAR);
					}else{
						ps.setObject(1, platform);
					}	
					String asin =amazonProductQaModels.get(i).getAsin();
					if (ValidationUtil.isNull(asin)) {
						ps.setNull(2,Types.VARCHAR );
					}else{
						ps.setObject(2,asin);
					}
					Long product_id = amazonProductQaModels.get(i).getProduct_id();
					if (ValidationUtil.isNull(product_id)) {
						ps.setNull(3,Types.BIGINT);
					}else{
						ps.setLong(3,product_id );
					}
					String question = amazonProductQaModels.get(i).getQuestion();
					if (ValidationUtil.isNull(question)) {
						ps.setNull(4,Types.VARCHAR);
					}else{
						ps.setObject(4, question);
					}
					String forum = amazonProductQaModels.get(i).getForum();
					if (ValidationUtil.isNull(forum)) {
						ps.setNull(5,Types.VARCHAR);
					}else{
						ps.setObject(5, forum);
					}
					String answers = amazonProductQaModels.get(i).getAnswers();
					if (ValidationUtil.isNull(answers)) {
						ps.setNull(6,Types.VARCHAR);
					}else{
						ps.setObject(6, answers);
					}
					Long answer_total = amazonProductQaModels.get(i).getAnswer_total();
					if (ValidationUtil.isNull(answer_total)) {
						ps.setNull(7,Types.BIGINT);
					}else{
						ps.setLong(7, answer_total);
					}
					Timestamp answer_updated_at = amazonProductQaModels.get(i).getAnswer_updated_at();
					if (ValidationUtil.isNull(answer_updated_at)) {
						ps.setNull(8, Types.TIMESTAMP);
					}else{

						ps.setTimestamp(8,answer_updated_at );
					}
					Long has_new = amazonProductQaModels.get(i).getHas_new();
					if (ValidationUtil.isNull(has_new)) {
						ps.setNull(9,Types.BIGINT);
					}else{
						ps.setLong(9, has_new);
					}
					Long qa_total = amazonProductQaModels.get(i).getQa_total();
					if (ValidationUtil.isNull(qa_total)) {
						ps.setNull(10,Types.BIGINT);
					}else{
						ps.setLong(10, qa_total);
					}
					Timestamp created_at = amazonProductQaModels.get(i).getCreated_at();
					if (ValidationUtil.isNull(created_at)) {
						ps.setNull(11, Types.TIMESTAMP);
					}else{

						ps.setTimestamp(11,created_at );
					}
					Timestamp updated_at = amazonProductQaModels.get(i).getUpdated_at();
					if (ValidationUtil.isNull(updated_at)) {
						ps.setNull(12, Types.TIMESTAMP);
					}else{

						ps.setTimestamp(12,updated_at );
					}
					Long source_id = amazonProductQaModels.get(i).getEntity_id();
					System.out.println();
					ps.setLong(13, source_id);
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return amazonProductQaModels.size();
				}
			});*/
			amazonProductQaDao.insertAmazonProductQas(amazonProductQaModels);
			result.setDescription("添加成功！");
		}catch (Exception e) {
			// TODO: handle exception
			result.setDescription("添加失败！");
			OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult updateAmazonProductQas(List<AmazonProductQaModel> amazonProductQaModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			
			amazonProductQaDao.updateAmazonProductQas(amazonProductQaModels);
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
		return amazonProductQaDao.selectMaxDate();
	}

	@Override
	public List<AmazonProductQaModel> selectMySqlDate(String date) {
		// TODO Auto-generated method stub
		return amazonProductQaDao.selectMySqlDate(date);
	}

	@Override
	public List<AmazonProductQaModel> selectAmazonProductQaModels(List<AmazonProductQaModel> amazonProductQaModels) {
		// TODO Auto-generated method stub
		return amazonProductQaDao.selectAmazonProductQaModels(amazonProductQaModels);
	}

}
