package com.it.ocs.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.AmazonBestsellerAsinModel;
@Repository
public interface IAmazonBestsellerAsinDao extends IBaseDAO<AmazonBestsellerAsinModel> {

	public void insertAmazonBestsellerAsins(List<AmazonBestsellerAsinModel> amazonBestsellerAsinModels);
	public void updateAmazonBestsellerAsins(List<AmazonBestsellerAsinModel> amazonBestsellerAsinModels);
	public String selectMaxDate();
	public List<AmazonBestsellerAsinModel> selectMySqlDate(@Param("date") String date);
	public List<AmazonBestsellerAsinModel> selectAmazonBestsellerAsinModels(List<AmazonBestsellerAsinModel> amazonBestsellerAsinModels);
}
