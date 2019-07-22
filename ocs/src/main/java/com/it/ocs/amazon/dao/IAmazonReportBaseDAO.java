package com.it.ocs.amazon.dao;

import java.util.Map;

public interface IAmazonReportBaseDAO {
	public void save(Map<String, Object> saveData);
	
	public void delByReportId(String reportGetId);
}
