package com.it.ocs.task.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.task.model.AmazonOrderItemModel;

public interface IMAmazonOrderItemService {
	public List<AmazonOrderItemModel> selectAmazonOrderItemByStartTAndEndT(String startT,String endT);
}
