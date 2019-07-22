package com.it.ocs.fourPX.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.fourPX.model.BaseModel;

public interface Base4pxDao<T extends BaseModel> {

	Long generatePrimaryKey();
	
	void insert(@Param("item") T model);
	
	void update(@Param("item") T model);

	int count(@Param("item") Map<String, Object> param);

	List<Map<String, Object>> selectByPage(@Param("item") Map<String, Object> param, @Param("start") int startRow, @Param("end") int endRow);

	List<Map<String, Object>> select(@Param("item") Map<String, Object> param);
}
