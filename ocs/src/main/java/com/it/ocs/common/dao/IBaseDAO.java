package com.it.ocs.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.model.BaseModel;

public interface IBaseDAO<T extends BaseModel> {
	public int add(T model);
	
	public int delete(Long id);
	
	public int update(T model);
	
	public List<T> query(@Param(value="param") T param);
	
	public int count(@Param(value="param") T param);
	
	public T getById(Long id);

	public Long getSeq();
	public List<T> queryByPage(@Param(value="param") T param,@Param(value="starRow") Integer starRow,@Param(value="endRow") Integer endRow);

	public int batchDel(@Param(value="ids") List<Long> ids);
	
	public List<T> queryByIds(@Param(value="ids") List<Long> ids);
	
	public Long getId();
	
	public T getByParam(@Param(value="param") T param);
}
