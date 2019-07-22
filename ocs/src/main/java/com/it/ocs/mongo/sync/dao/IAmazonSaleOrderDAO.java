package com.it.ocs.mongo.sync.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.it.ocs.mongo.model.AmazonOrderModel;
@Component
public interface IAmazonSaleOrderDAO {
	public List<AmazonOrderModel> query(@Param(value = "starRow") Integer starRow,
			@Param(value = "endRow") Integer endRow);
}
