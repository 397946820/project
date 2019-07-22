package com.it.ocs.task.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.task.model.LightOrderModel;

public interface IMLightOrderService {
	
	public String selectMaxCreatedAt();
	
	public List<LightOrderModel> selectLightOrderByStartTAndEndT(String startT,String entT);

}
