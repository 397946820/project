package com.it.ocs.task.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.task.model.AmazonOrderItemModel;
@Repository
public interface IMAmazonOrderItemDao  {
	
	public List<AmazonOrderItemModel> selectAmazonOrderItemByStartTAndEndT(@Param("startT")String startT,@Param("endT")String endT);
}
