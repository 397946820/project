package com.it.ocs.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IResponseDao<T> {

	public int add(T model);

	public int add(Map<String, Object> map);

	public int update(T model);

	public int update(Map<String, Object> map);

	public T getById(Long entityId);

	public List<T> query(@Param(value = "param") Map<String, Object> map, @Param(value = "sort") String sort,
			@Param(value = "order") String order);

	public List<Map<String, Object>> queryByObject(@Param(value = "param") T param);

	public int addAll(List<T> list);

	public T queryByParam(@Param(value = "param") T param);

	public int deleteById(Long entityId);
}
