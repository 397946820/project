package com.it.ocs.compare.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.compare.model.EbayDBModel;

public interface IEbayOrderCompareDAO {
	public List<EbayDBModel> queryByParam(@Param(value="param") Map<String, Object> param);
	
	public List<String> queryCountryBySN(@Param(value="list") List<String> saleRecordNumber);
	
	public List<String> queryLightAccount();
	
	public List<String> queryEbayAccount();
	
	public List<String> queryAmazonAccount();
	
	public Date getDateBySaleNumber(@Param(value="srn") String srn);
}
