package com.it.ocs.sys.service;

import org.dom4j.Document;

public interface IEbayGetDataService {
	
	public Document getItemById(String itemId,String siteId,String account);
	
	public void parseItemData(String itemId,String siteId,String account);
	
	public void synchronizeEBayOnlineData();
	
	public void loadPAExcel();

	public void uopdateEBayOnlineData();
}
