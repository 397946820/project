package com.it.ocs.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.AmazonProductQaModel;
@Repository
public interface IAmazonProductQaDao extends IBaseDAO<AmazonProductQaModel> {
	
	public void insertAmazonProductQas(List<AmazonProductQaModel> amazonProductQaModels);
	public void updateAmazonProductQas(List<AmazonProductQaModel> amazonProductQaModels);
	public String selectMaxDate();
	public List<AmazonProductQaModel> selectMySqlDate(@Param("date") String date);
	public List<AmazonProductQaModel> selectAmazonProductQaModels(List<AmazonProductQaModel> amazonProductQaModels);
}
