package com.it.ocs.task.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.AmazonOrderModel;
@Repository
public interface IMAmazonOrderDao extends IBaseDAO<AmazonOrderModel> {
	
	public String selectMaxCreatedAt();
	
	public List<AmazonOrderModel> selectAmazonOrderByStartTAndEndT(@Param("startT")String startT,@Param("endT")String endT);


}
