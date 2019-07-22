package com.it.ocs.fourPX.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.fourPX.model.FpxAbnormalModel;

public interface FpxAbnormalDao {

	Long generatePrimaryKey();
	
	void insert(@Param("item") FpxAbnormalModel abnormal);

	int count(@Param("item") Map<String, Object> param);

	List<Map<String, Object>> selectByPage(@Param("item") Map<String, Object> param, @Param("start") int start, @Param("end") int end);

}
