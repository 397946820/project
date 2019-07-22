package com.it.ocs.amazon.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IAmazonFinancialDAO {
	public List<Long> queryGeneratedReportId(@Param(value="site") String site,
			@Param(value="startT") String startT,@Param(value="endT") String endT);
}
