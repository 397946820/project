package com.it.ocs.mongo.sync.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.it.ocs.common.RequestParam;
import com.it.ocs.mongo.model.AmazonOrderModel;
import com.it.ocs.mongo.sync.dao.IAmazonSaleOrderDAO;
import com.it.ocs.mongo.sync.service.IDBSyncService;
public class DBSyncService implements IDBSyncService {
	private final String AMAZON_COLLECTION = "AMAZON_SALE_ORDER";
	private final String EBAY_COLLECTION = "EBAY_SALE_ORDER";
	private final String LIGHT_COLLECTION = "LIGHT_SALE_ORDER";
	@Autowired
	private IAmazonSaleOrderDAO amazonOrderDAO;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Override
	public void syncAmazonOrder() {
		RequestParam requestParam = new RequestParam();
		for (int i = 1;i <= 205;i++) {
			requestParam.setPage(i);
			requestParam.setRows(10000);
			List<AmazonOrderModel> amazonOrders = amazonOrderDAO.query(requestParam.getStartRow(), requestParam.getEndRow());
			mongoTemplate.insert(amazonOrders, AMAZON_COLLECTION);
		}
		
	}

	@Override
	public void syncEbayOrder() {
		
	}

	@Override
	public void syncLightOrder() {
		
	}

}
