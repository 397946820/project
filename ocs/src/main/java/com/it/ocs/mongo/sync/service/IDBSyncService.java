package com.it.ocs.mongo.sync.service;

public interface IDBSyncService {

	public void syncAmazonOrder();
	
	public void syncEbayOrder();
	
	public void syncLightOrder();
}
