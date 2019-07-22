package com.it.ocs.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.AmazonSkuHistoryModel;
@Repository
public interface IAmazonSkuHistoryDao extends IBaseDAO<AmazonSkuHistoryModel> {
	
	public void insertAmazonSkuHistorys(List<AmazonSkuHistoryModel> amazonSkuHistoryModels);
	
	public void updateAmazonSkuHistorys(List<AmazonSkuHistoryModel> amazonSkuHistoryModels);
	
	public String selectMaxDate();
	
	public List<AmazonSkuHistoryModel> selectMySqlDate(@Param("date") String date);
	
	public List<AmazonSkuHistoryModel> selectAmazonSkuHistoryModels(List<AmazonSkuHistoryModel> amazonSkuHistoryModels);
	
	public void updateInsertDate(List<AmazonSkuHistoryModel> amazonSkuHistoryModels);
}
