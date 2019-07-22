package com.it.ocs.task.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.task.model.EbayOrderModel;

public interface IMEBayOrderService {

	public String selectMaxLastFetchTime();
	public List<EbayOrderModel> selectEbayOrderByStartTAndEndT(String startT,String endT);

}
