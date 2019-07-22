package com.it.ocs.fourPX.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.fourPX.model.FpxOperLogModel;

public interface FpxOperLogDao {

	Long generatePrimaryKey();
	
	void insert(@Param("item") FpxOperLogModel model);

	int count(@Param("item") Map<String, Object> param);

	List<Map<String, Object>> selectByPage(@Param("item") Map<String, Object> param, @Param("start") int startRow, @Param("end") int endRow);

}
