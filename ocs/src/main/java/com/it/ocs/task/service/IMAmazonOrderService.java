package com.it.ocs.task.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.task.model.AmazonOrderModel;

public interface IMAmazonOrderService {
	
	public String selectMaxCreatedAt();
	
	public List<AmazonOrderModel> selectAmazonOrderByStartTAndEndT(String startT,String endT);

}
