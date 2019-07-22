package com.it.ocs.compare.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.compare.model.LightDBModel;

public interface ILightOrderCompareDAO {
	public List<LightDBModel> queryByParam(@Param(value="param") Map<String, Object> param);
}
