package com.it.ocs.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.AmazonReportsBestsellerModel;
@Repository
public interface IAmazonReportsBestsellerDao extends IBaseDAO<AmazonReportsBestsellerModel> {
	
	public void insertAmazonReportsBestsellers(List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels);
	
	public void updateAmazonReportsBestsellers(List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels);
	
	public String selectMaxDate();
	
	public List<AmazonReportsBestsellerModel> selectMySqlDate(@Param("date") String date);
	
	public List<AmazonReportsBestsellerModel> selectAmazonReportsBestsellerModels(List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels);
	
	public void updateInsertData(List<AmazonReportsBestsellerModel> amazonReportsBestsellerModels);
}
