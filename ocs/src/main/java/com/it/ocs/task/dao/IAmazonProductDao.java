package com.it.ocs.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.AmazonProductModel;

@Repository
public interface IAmazonProductDao extends IBaseDAO<AmazonProductModel> {
	
	public void insertAmazonProducts(List<AmazonProductModel> amazonProductModels);
	public void updateAmazonProducts(List<AmazonProductModel> amazonProductModels);
	public String selectMaxDate();
	public List<AmazonProductModel> selectMySqlDate(@Param("date") String date);
	public List<AmazonProductModel> selectAmazonProducts(List<AmazonProductModel> amazonProductModels);
	public void updateInsertData(List<AmazonProductModel> amazonProductModels);
}
