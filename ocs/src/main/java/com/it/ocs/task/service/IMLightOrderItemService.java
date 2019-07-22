package com.it.ocs.task.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.task.model.LightOrderItemModel;

public interface IMLightOrderItemService {
	
	public List<LightOrderItemModel> selectLightOrderItemByStartTAndEndT(String startT,String endT);

}
