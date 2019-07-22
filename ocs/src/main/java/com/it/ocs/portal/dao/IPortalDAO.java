package com.it.ocs.portal.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IPortalDAO {
	public Double total(@Param(value="param") Map<String,String> param);
}
