package com.it.ocs.task.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.task.model.EbayOrderItemModel;

public interface IMEBayOrderItemService {
	
	public List<EbayOrderItemModel> selectEbayOrderItemByStartTAndEndT(String startT,String endT);
}
