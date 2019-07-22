package com.it.ocs.compare.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.compare.model.AmazonDBModel;
import com.it.ocs.compare.model.AmazonExcelModel;

public interface IAmazonOrderCompareDAO {
	public List<AmazonExcelModel> queryPageByParam(@Param(value="param") AmazonExcelModel param);
	
	public int batchAdd(@Param(value="list") List<AmazonExcelModel> datas);
	
	public int batchUpdate(@Param(value="list") List<AmazonExcelModel> datas);
	
	public List<AmazonExcelModel> queryByParam(@Param(value="param") AmazonExcelModel param);
	
	public List<AmazonDBModel> queryByInuptParam(@Param(value="param") Map<String, Object> param);
	
	public List<String> queryTypeByPlatform(@Param(value="param") Map<String, Object> param);
}
