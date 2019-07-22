package com.it.ocs.salesStatistics.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.salesStatistics.model.LightMarkShipmentModel;

/**
 * 标记官网发货Dao层
 * @author zhouyancheng
 *
 */
public interface ILightMarkShipmentDao {

	/**
	 * 插入一条标记发货的数据
	 * @param data 待插入的数据
	 */
	public void add(@Param("param") Map<String, Object> param);
	
	public void update(@Param("param") Map<String, Object> param);

	public LightMarkShipmentModel queryOne(@Param("param") Map<String, Object> param);

	public int count(@Param("param") Map<String, Object> param);

	public List<LightMarkShipmentModel> queryByPage(@Param("param") Map<String, Object> param, @Param("start") int startRow, @Param("end") int endRow);
	
	public List<Map<String, Object>> queryExportData(@Param("param") Map<String, Object> param);

	public LightMarkShipmentModel get(@Param("id") Long id);

	public List<LightMarkShipmentModel> query(@Param("param") Map<String, Object> param);

	public Map<String, Object> queryLightProduct(@Param("param") Map<String, Object> param);
	
}
