package com.it.ocs.vc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.vc.model.AmazonVcModel;

public interface IAmazonVcDAO extends IBaseDAO<AmazonVcModel> {
	public int add(@Param(value = "data") Map<String, Object> data);

	public int getCount(@Param(value = "param") Map<String, Object> param);

	public long getOcsOrderId();

	public List<AmazonVcModel> queryByParam(@Param(value = "param") Map<String, Object> param);
	
	public List<AmazonVcModel> queryByFormParam(@Param(value = "param") Map<String, Object> param);
	
}
