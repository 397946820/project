package com.it.ocs.task.service.impl;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
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
import com.it.ocs.task.dao.IAmazonReportsBestsellerDao;
import com.it.ocs.task.model.AmazonBestsellerAsinModel;
import com.it.ocs.task.model.AmazonReportsBestsellerModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonReportsBestsellerService;
import com.it.ocs.task.util.OutInfo;
@Service
public class AmazonReportsBestsellerService extends BaseService implements IAmazonReportsBestsellerService {

	@Autowired
	private IAmazonReportsBestsellerDao amazonReportsBestsellerDao;
	@Autowired
	private ProjectApplicationContext pac;
	@Override
	public OperationResult synchronouAmazonReportsBestseller(
			List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			
			DataSourceTypeManager.set(DataSources.MASTER);
			List<AmazonReportsBestsellerModel> updateAmazonReportsBestsellerModels = Lists.newArrayList();
			List<AmazonReportsBestsellerModel> oracleModels = Lists.newArrayList();
			int count = amazonReportsBestsellerModels.size();
			if(count>0){
				int k=0;
				for(int i=0; i<=count/500+1;i++){
					List<AmazonReportsBestsellerModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<count){
							list.add(amazonReportsBestsellerModels.get(k));
						}
					}
					if(list.size()!=0){
						if(selectAmazonReportsBestsellerModels(list).size()>0){
							oracleModels.addAll(selectAmazonReportsBestsellerModels(list));
						}
					}
				}
			}
			HashMap<Long, Long> oracleMap = new HashMap<>();
			for (AmazonReportsBestsellerModel amazonReportsBestsellerModel : oracleModels) {
				oracleMap.put(amazonReportsBestsellerModel.getSource_id(), amazonReportsBestsellerModel.getSource_id());
			}
			for (AmazonReportsBestsellerModel amazonReportsBestsellerModel : amazonReportsBestsellerModels) {
				if(oracleMap.get(amazonReportsBestsellerModel.getEntity_id())!=null){
					updateAmazonReportsBestsellerModels.add(amazonReportsBestsellerModel);
				}
			}
			
			if(updateAmazonReportsBestsellerModels.size()>0){
				amazonReportsBestsellerModels.removeAll(updateAmazonReportsBestsellerModels);
			}
			int insertCount = amazonReportsBestsellerModels.size();
			int updateCount = updateAmazonReportsBestsellerModels.size();
			if (insertCount>0) { 
				
				int k=0;
				
				for(int i=0; i<=insertCount/2+1;i++){
					List<AmazonReportsBestsellerModel> list = Lists.newArrayList();
					for(int j=0;j<2;j++,k++){
						if(k<insertCount){
						  list.add(amazonReportsBestsellerModels.get(k));
						}
					}
					if (list.size()!=0){
						insertAmazonReportsBestsellers(list);
						updateInsertData(list);
					}
					
				}
			}
			if(updateCount>0){
				
				int k=0;
				for(int i=0; i<=updateCount/500+1;i++){
					List<AmazonReportsBestsellerModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<updateCount){
						  list.add(updateAmazonReportsBestsellerModels.get(k));
						}
					}
					if (list.size()!=0){
						updateAmazonReportsBestsellers(list);
					}
					
				}
			}
			
			
			OutInfo.Out("      amazon_reports_bestsellers添加的总数："+insertCount+"\n", OrderRecord.otherPath);
			OutInfo.Out("      amazon_reports_bestsellers修改的总数："+updateCount+"\n", OrderRecord.otherPath);

			
		} catch (Exception e) {
			// TODO: handle exception
			OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult insertAmazonReportsBestsellers(
			List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels) {
		// TODO Auto-generated method stub
		OperationResult result  = new OperationResult();
		try {
			DataSource dataSource = (DataSource) pac.getBean("dataSource");
			DataSourceTypeManager.set(DataSources.MASTER);
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			JdbcTemplate jt = new JdbcTemplate(dataSource);
			String sql=" INSERT INTO amazon_reports_bestseller (ENTITY_ID,PLATFORM,URL,CATEGORY_NAME,CATEGORY_NAME_CN,"
					+ "TOP_HUNDRED,TOP_TWENTY,TOP_TEN,TOP_FIVE,CREATED_AT,UPDATED_AT,"
					+ "AVERAGE_RATE, REVIEWS_TOTAL,ASIN,BRAND_NAME,PRICE,"
					+ "BESTSELLER_CREATED,BESTSELLER_PRODUCT_CREATED,"
					+ "BESTSELLER_CREATED_AT,BESTSELLER_PRODUCT_CREATED_AT,PIC,SOURCE_ID,CREATION_DATE) "
					+ " values(amazon_reports_bestseller_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			jt.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					String platform = amazonReportsBestsellerModels.get(i).getPlatform();
					if(ValidationUtil.isNull(platform)){
						ps.setNull(1, Types.VARCHAR);
					}else{
						ps.setObject(1, platform);
					}	
					String url = amazonReportsBestsellerModels.get(i).getUrl();
					if(ValidationUtil.isNull(url)){
						ps.setNull(2, Types.VARCHAR);
					}else{
						ps.setObject(2, url);
					}
					String category_name =  amazonReportsBestsellerModels.get(i).getCategory_name();
					if(ValidationUtil.isNull(category_name)){
						ps.setNull(3, Types.VARCHAR);
					}else{
						ps.setObject(3, category_name);
					}
					String category_name_cn =  amazonReportsBestsellerModels.get(i).getCategory_name_cn();
					if(ValidationUtil.isNull(category_name_cn)){
						ps.setNull(4, Types.VARCHAR);
					}else{
						ps.setObject(4, category_name_cn);
					}
					String top_hundred =  amazonReportsBestsellerModels.get(i).getTop_hundred();
					if(ValidationUtil.isNull(top_hundred)){
						ps.setNull(5, Types.VARCHAR);
					}else{
						ps.setCharacterStream(5, new StringReader(top_hundred), top_hundred.length());
					}
					String top_twenty =  amazonReportsBestsellerModels.get(i).getTop_twenty();
					if(ValidationUtil.isNull(top_twenty)){
						ps.setNull(6, Types.VARCHAR);
					}else{
						ps.setCharacterStream(6, new StringReader(top_twenty), top_twenty.length());
					}
					String top_ten  =  amazonReportsBestsellerModels.get(i).getTop_ten();
					if(ValidationUtil.isNull(top_ten)){
						ps.setNull(7, Types.VARCHAR);
					}else{
						ps.setCharacterStream(7, new StringReader(top_ten), top_ten.length());
					}
					String top_five =  amazonReportsBestsellerModels.get(i).getTop_five(); 
					if(ValidationUtil.isNull(top_five)){
						ps.setNull(8, Types.VARCHAR);
					}else{
						ps.setCharacterStream(8, new StringReader(top_five), top_five.length());
					}
					/*String brand_distribution =  amazonReportsBestsellerModels.get(i).getBrand_distribution();
					if(ValidationUtil.isNull(brand_distribution)){
						ps.setNull(9, Types.VARCHAR);
					}else{
						ps.setCharacterStream(9, new StringReader(brand_distribution), brand_distribution.length());
					}*/
					Timestamp created_at  =  amazonReportsBestsellerModels.get(i).getCreated_at();
					if(ValidationUtil.isNull(created_at)){
						ps.setNull(9, Types.TIMESTAMP);
					}else{
						ps.setTimestamp(9, created_at);;
					}
					Timestamp updated_at  =  amazonReportsBestsellerModels.get(i).getUpdated_at();
					if(ValidationUtil.isNull(updated_at)){
						ps.setNull(10, Types.TIMESTAMP);
					}else{
						ps.setTimestamp(10, updated_at);;
					}
					/*String url_product =  amazonReportsBestsellerModels.get(i).getUrl_product();
					if(ValidationUtil.isNull(url_product)){
						ps.setNull(12, Types.VARCHAR);
					}else{
						ps.setCharacterStream(12, new StringReader(url_product), url_product.length());
					}*/
					/*String category_ranking =  amazonReportsBestsellerModels.get(i).getCategory_ranking();
					if(ValidationUtil.isNull(category_ranking)){
						ps.setNull(13, Types.VARCHAR);
					}else{
						ps.setCharacterStream(13, new StringReader(category_ranking), category_ranking.length());
					}*/
					String average_rate =  amazonReportsBestsellerModels.get(i).getAverage_rate();
					if(ValidationUtil.isNull(average_rate)){
						ps.setNull(11, Types.VARCHAR);
					}else{
						ps.setCharacterStream(11, new StringReader(average_rate), average_rate.length());
					}
					String reviews_total =  amazonReportsBestsellerModels.get(i).getReviews_total();
					if(ValidationUtil.isNull(reviews_total)){
						ps.setNull(12, Types.VARCHAR);
					}else{
						ps.setCharacterStream(12, new StringReader(reviews_total), reviews_total.length());
					}
					String asin =  amazonReportsBestsellerModels.get(i).getAsin();
					if(ValidationUtil.isNull(asin)){
						ps.setNull(13, Types.VARCHAR);
					}else{
						ps.setCharacterStream(13, new StringReader(asin), asin.length());
					}
					String brand_name =  amazonReportsBestsellerModels.get(i).getBrand_name();
					if(ValidationUtil.isNull(asin)){
						ps.setNull(14, Types.VARCHAR);
					}else{
						ps.setCharacterStream(14, new StringReader(brand_name), brand_name.length());
					}
					String price =  amazonReportsBestsellerModels.get(i).getPrice();
					if(ValidationUtil.isNull(price)){
						ps.setNull(15, Types.VARCHAR);
					}else{
						ps.setCharacterStream(15, new StringReader(price), price.length());
					}
					Long bestseller_created =  amazonReportsBestsellerModels.get(i).getBestseller_created();
					if(ValidationUtil.isNull(bestseller_created)){
						ps.setNull(16, Types.BIGINT);
					}else{
						ps.setLong(16, bestseller_created);
					}
					Long bestseller_product_created =  amazonReportsBestsellerModels.get(i).getBestseller_product_created();
					if(ValidationUtil.isNull(bestseller_product_created)){
						ps.setNull(17, Types.BIGINT);
					}else{
						ps.setLong(17, bestseller_product_created);
					}
					Timestamp bestseller_created_at =  amazonReportsBestsellerModels.get(i).getBestseller_created_at();
					if(ValidationUtil.isNull(bestseller_created_at)){
						ps.setNull(18, Types.TIMESTAMP);
					}else{
						ps.setTimestamp(18, bestseller_created_at);
					}
					Timestamp bestseller_product_created_at =  amazonReportsBestsellerModels.get(i).getBestseller_product_created_at();
					if(ValidationUtil.isNull(bestseller_product_created_at)){
						ps.setNull(19, Types.TIMESTAMP);
					}else{
						ps.setTimestamp(19, bestseller_product_created_at);
					}
					/*String top_asin =  amazonReportsBestsellerModels.get(i).getTop_asin();
					if(ValidationUtil.isNull(top_asin)){
						ps.setNull(23, Types.VARCHAR);
					}else{
						ps.setCharacterStream(23, new StringReader(top_asin), top_asin.length());
					}*/
					String pic =  amazonReportsBestsellerModels.get(i).getPic();
					if(ValidationUtil.isNull(pic)){
						ps.setNull(20, Types.VARCHAR);
					}else{
						ps.setCharacterStream(20, new StringReader(pic), pic.length());
					}
					Long entity_id =  amazonReportsBestsellerModels.get(i).getEntity_id();
					if(ValidationUtil.isNull(entity_id)){
						ps.setNull(21, Types.BIGINT);
					}else{
						ps.setLong(21, entity_id);
					}
					
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return amazonReportsBestsellerModels.size();
				}
			});
			//amazonReportsBestsellerDao.insertAmazonReportsBestsellers(amazonReportsBestsellerModels);
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
	public OperationResult updateAmazonReportsBestsellers(
			List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			amazonReportsBestsellerDao.updateAmazonReportsBestsellers(amazonReportsBestsellerModels);
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
		return amazonReportsBestsellerDao.selectMaxDate();
	}

	@Override
	public List<AmazonReportsBestsellerModel> selectAmazonReportsBestsellerModels(
			List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels) {
		// TODO Auto-generated method stub
		
		return amazonReportsBestsellerDao.selectAmazonReportsBestsellerModels(amazonReportsBestsellerModels);
	}

	@Override
	public List<AmazonReportsBestsellerModel> selectMySqlDate(String date) {
		// TODO Auto-generated method stub
		return amazonReportsBestsellerDao.selectMySqlDate(date);
	}

	@Override
	public OperationResult updateInsertData(List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			amazonReportsBestsellerDao.updateInsertData(amazonReportsBestsellerModels);
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
