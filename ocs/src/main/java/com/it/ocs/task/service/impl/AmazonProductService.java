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
import com.it.ocs.task.dao.IAmazonProductDao;
import com.it.ocs.task.dao.impl.OracleData;
import com.it.ocs.task.model.AmazonProductModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonProductService;
import com.it.ocs.task.util.OutInfo;

@Service
public class AmazonProductService extends BaseService implements IAmazonProductService {

	@Autowired
	private IAmazonProductDao amazonProductDao;
	@Autowired
	private ProjectApplicationContext pac;
	@Override
	public OperationResult synchronouAmazonProduct(List<AmazonProductModel> amazonProductModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			
			DataSourceTypeManager.set(DataSources.MASTER);
			List<AmazonProductModel> updateAmazonProducts = Lists.newArrayList();
			List<AmazonProductModel> oracleModels = Lists.newArrayList();
			int count = amazonProductModels.size();
			if(count>0){
				int k=0;
				for(int i=0; i<=count/500+1;i++){
					List<AmazonProductModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<count){
							list.add(amazonProductModels.get(k));
						}
					}
					if(list.size()!=0){
						if(selectAmazonProducts(list).size()>0){
							oracleModels.addAll(selectAmazonProducts(list));
						}
					}
				}
			}
			HashMap<Long, Long> oracleMap = new HashMap<>();
			for(AmazonProductModel amazonProductModel : oracleModels){
				oracleMap.put(amazonProductModel.getSource_id(), amazonProductModel.getSource_id());
			}
			for(AmazonProductModel amazonProductModel : amazonProductModels){
				
				if(oracleMap.get(amazonProductModel.getEntity_id())!=null){
					
					updateAmazonProducts.add(amazonProductModel);
				}
			}
			if(updateAmazonProducts.size()>0){
				amazonProductModels.removeAll(updateAmazonProducts);
			}
			int insertCount = amazonProductModels.size();
			int updateCount = updateAmazonProducts.size();
			if (insertCount>0) { 
				
				int k=0;
				
				for(int i=0; i<=insertCount/2+1;i++){
					List<AmazonProductModel> list = Lists.newArrayList();
					for(int j=0;j<2;j++,k++){
						if(k<insertCount){
						  list.add(amazonProductModels.get(k));
						}
					}
					if (list.size()!=0){
						insertAmazonProducts(list);
						updateInsertData(list);
					}
					
				}
			}
			if(updateCount>0){
				
				int k=0;
				for(int i=0; i<=updateCount/2+1;i++){
					List<AmazonProductModel> list = Lists.newArrayList();
					for(int j=0;j<2;j++,k++){
						if(k<updateCount){
						  list.add(updateAmazonProducts.get(k));
						}
					}
					if (list.size()!=0){
						updateAmazonProducts(list);
					}
					
				}
			}
			OutInfo.Out("      amazon_product添加的总数："+insertCount+"\n", OrderRecord.otherPath);
			OutInfo.Out("      amazon_product修改的总数："+updateCount+"\n", OrderRecord.otherPath);
		} catch (Exception e) {
			// TODO: handle exception
			OutInfo.Out(e.toString()+"\n", OrderRecord.otherPath);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult insertAmazonProducts(List<AmazonProductModel> amazonProductModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		
		try {
			//OracleData.insertAmazonProduct(amazonProductModels);
			//final List<AmazonProductModel> amazonProductModels = amazonProductModels;
			
			DataSourceTypeManager.set(DataSources.MASTER);
			DataSource dataSource = (DataSource) pac.getBean("dataSource");
			KeyHolder keyHolder = new GeneratedKeyHolder();
			JdbcTemplate jt = new JdbcTemplate(dataSource);
			String sql = "INSERT INTO AMAZON_PRODUCT (ENTITY_ID,"
					+ "NAME,CATEGORY,REVIEWS_TOTAL,ASIN,AMAZON_STOCK,AVERAGE_RATE,"
					+ "CREATED_AT,UPDATED_AT,PRODUCT_URL,SKU,HAS_NEW,STATUS,COUNTRY,PRICE,"
					+ "SPECIAL_PRICE,IMAGE,OFFER_TOTAL,STAR_ALL,"
					+ "HAS_UPDATE,IMPORTANT_LEVEL,IF_CONTINUE,TYPE,"
					+ "DEMAND_SCORE,IF_INVITE,INVITE_COUNT,SOURCE_ID,CREATION_DATE)   "
					+ " values(amazon_product_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		
			  jt.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					String name = amazonProductModels.get(i).getName();
					
					if(ValidationUtil.isNull(name)){
						ps.setNull(1, Types.VARCHAR);
					}else{
						ps.setObject(1, name);
					}
					String category = amazonProductModels.get(i).getCategory();
					if (ValidationUtil.isNull(category)) {
						ps.setNull(2,Types.VARCHAR );
					}else{
						ps.setObject(2,category);
					}
					Long reviewsTotal = amazonProductModels.get(i).getReviews_total();
					if (ValidationUtil.isNull(reviewsTotal)) {
						ps.setNull(3,Types.BIGINT);
					}else{
						ps.setLong(3,reviewsTotal );
					}
					String asin = amazonProductModels.get(i).getAsin();
					if (ValidationUtil.isNull(asin)) {
						ps.setNull(4,Types.VARCHAR);
					}else{
						ps.setObject(4, asin);
					}
					Long amazonStock = amazonProductModels.get(i).getAmazon_stock();
					if (ValidationUtil.isNull(amazonStock)) {
						ps.setNull(5,Types.BIGINT);
					}else{
						ps.setLong(5, amazonStock);
					}
					String averageRate = amazonProductModels.get(i).getAverage_rate();
					if (ValidationUtil.isNull(averageRate)) {
						ps.setNull(6,Types.VARCHAR);
					}else{
						ps.setObject(6, averageRate);
					}
					Timestamp createdAt=amazonProductModels.get(i).getCreated_at();
					if (ValidationUtil.isNull(createdAt)) {
						ps.setNull(7, Types.TIMESTAMP);
					}else{

						ps.setTimestamp(7,createdAt );
					}
					Timestamp updatedAt= amazonProductModels.get(i).getUpdated_at();
					if (ValidationUtil.isNull(updatedAt)) {
						ps.setNull(8, Types.TIMESTAMP);
					}else{

						ps.setTimestamp(8,createdAt );
					}
					String productUrl = amazonProductModels.get(i).getProduct_url();
					if (ValidationUtil.isNull(productUrl)) {
						ps.setNull(9,Types.VARCHAR);
					}else{
						ps.setObject(9, productUrl);
					}
					String sku = amazonProductModels.get(i).getSku();
					if (ValidationUtil.isNull(sku)) {
						ps.setNull(10,Types.VARCHAR);
					}else{
						ps.setObject(10, sku);
					}
					Long hasNew = amazonProductModels.get(i).getHas_new();
					if (ValidationUtil.isNull(hasNew)) {
						ps.setNull(11,Types.BIGINT);
					}else{
						ps.setLong(11, hasNew);
					}
					Long status = amazonProductModels.get(i).getStatus();
					if (ValidationUtil.isNull(status)) {
						ps.setNull(12,Types.BIGINT);
					}else{
						ps.setLong(12, status);
					}
					String country = amazonProductModels.get(i).getCountry();
					if (ValidationUtil.isNull(country)) {
						ps.setNull(13,Types.VARCHAR);
					}else{
						ps.setObject(13, country);
					}
					String price = amazonProductModels.get(i).getPrice();
					if (ValidationUtil.isNull(price)) {
						ps.setNull(14,Types.VARCHAR);
					}else{
						ps.setObject(14, price);
					}
					String specialPrice = amazonProductModels.get(i).getSpecial_price();
					if (ValidationUtil.isNull(specialPrice)) {
						ps.setNull(15,Types.VARCHAR);
					}else{
						ps.setObject(15, specialPrice);
					}
					String image = amazonProductModels.get(i).getImage();
					if (ValidationUtil.isNull(image)) {
						ps.setNull(16,Types.VARCHAR);
					}else{
						ps.setObject(16, image);
					}
					Long offerTotal = amazonProductModels.get(i).getOffer_total();
					if (ValidationUtil.isNull(offerTotal)) {
						ps.setNull(17,Types.BIGINT);
					}else{
						ps.setLong(17, offerTotal);
					}
					String starAll = amazonProductModels.get(i).getStar_all();
					if (ValidationUtil.isNull(starAll)) {
						ps.setNull(18,Types.VARCHAR);
					}else{
						ps.setObject(18, starAll);
					}
					/*String bulletPoints = amazonProductModels.get(i).getBullet_points();
					if (ValidationUtil.isNull(bulletPoints)) {
						ps.setNull(19,Types.VARCHAR);
					}else{
						ps.setObject(19, bulletPoints);
					}*/
					/*String shortDescription = amazonProductModels.get(i).getShort_description();
					if (ValidationUtil.isNull(shortDescription)) {
						ps.setNull(20,Types.VARCHAR);
					}else{
						ps.setObject(20, shortDescription);
					}*/
					/*String description = amazonProductModels.get(i).getDescription();
					if (ValidationUtil.isNull(description)) {
						ps.setNull(19,Types.VARCHAR);
					}else{
						ps.setObject(19, description);
					}*/
					/*String details = amazonProductModels.get(i).getDetails();
					if (ValidationUtil.isNull(details)) {
						ps.setNull(22,Types.VARCHAR);
					}else{
						ps.setObject(22, details);
					}*/
					Long hasUpdate = amazonProductModels.get(i).getHas_update();
					if (ValidationUtil.isNull(hasUpdate)) {
						ps.setNull(19,Types.BIGINT);
					}else{
						ps.setLong(19, hasUpdate);
					}
					Long importantLevel = amazonProductModels.get(i).getImportant_level();
					if (ValidationUtil.isNull(importantLevel)) {
						ps.setNull(20,Types.BIGINT);
					}else{
						ps.setLong(20, importantLevel);
					}
					Long ifContinue = amazonProductModels.get(i).getIf_continue();
					if (ValidationUtil.isNull(ifContinue)) {
						ps.setNull(21,Types.BIGINT);
					}else{
						ps.setLong(21, ifContinue);
					}
					Long type = amazonProductModels.get(i).getType();
					if (ValidationUtil.isNull(type)) {
						ps.setNull(22,Types.BIGINT);
					}else{
						ps.setLong(22, type);
					}
					String demandScore = amazonProductModels.get(i).getDemand_score();
					if (ValidationUtil.isNull(demandScore)) {
						ps.setNull(23,Types.VARCHAR);
					}else{
						ps.setObject(23, demandScore);
					}
					Long ifInvite = amazonProductModels.get(i).getIf_invite();
					if (ValidationUtil.isNull(ifInvite)) {
						ps.setNull(24,Types.BIGINT);
					}else{
						ps.setLong(24, ifInvite);
					}
					Long inviteCount = amazonProductModels.get(i).getInvite_count();
					if (ValidationUtil.isNull(inviteCount)) {
						ps.setNull(25,Types.BIGINT);
					}else{
						ps.setLong(25, inviteCount);
					}
					Long entityId = amazonProductModels.get(i).getEntity_id();
					if (ValidationUtil.isNull(entityId)) {
						ps.setNull(26,Types.BIGINT);
					}else{
						ps.setLong(26, entityId);
					}
					
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return amazonProductModels.size();
				}
			});
			//amazonProductDao.insertAmazonProducts(amazonProductModels);
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
	public OperationResult updateAmazonProducts(List<AmazonProductModel> amazonProductModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			amazonProductDao.updateAmazonProducts(amazonProductModels);
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
		return amazonProductDao.selectMaxDate();
	}

	@Override
	public List<AmazonProductModel> selectMySqlDate(String date) {
		// TODO Auto-generated method stub
		return amazonProductDao.selectMySqlDate(date);
	}

	@Override
	public List<AmazonProductModel> selectAmazonProducts(List<AmazonProductModel> amazonProductModels) {
		// TODO Auto-generated method stub
		return amazonProductDao.selectAmazonProducts(amazonProductModels);
	}

	@Override
	public OperationResult updateInsertData(List<AmazonProductModel> amazonProductModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			amazonProductDao.updateInsertData(amazonProductModels);
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
