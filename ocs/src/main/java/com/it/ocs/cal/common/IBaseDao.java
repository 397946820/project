package com.it.ocs.cal.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IBaseDao<T> {

	public int add(T model);
	
	public int add(Map<String, Object> map);

	public int update(T model);
	
	public int update(Map<String, Object> map);

	public int count(@Param(value = "param") T param);

	public T getById(Long id);

	public List<T> queryByPage(@Param(value = "param") T param, @Param(value = "starRow") Integer starRow,
			@Param(value = "endRow") Integer endRow, @Param(value = "sort") String sort,
			@Param(value = "order") String order);
	
	public List<T> query(@Param(value = "param") T param,
			@Param(value = "sort") String sort, @Param(value = "order") String order);
	
	public List<Map<String, Object>> queryByObject(@Param(value = "param") T param);
	
	public List<T> findByTemplate();
	
	public void addAll(List<T> list);
	
	T queryByParam(@Param(value = "param")T t);
	
}
