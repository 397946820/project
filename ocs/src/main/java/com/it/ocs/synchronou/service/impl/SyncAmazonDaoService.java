package com.it.ocs.synchronou.service.impl;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.synchronou.dao.ISyncAmazonOrderDao;

@Service
public class SyncAmazonDaoService {
	@Autowired
	private ISyncAmazonOrderDao syncAmazonOrderDao;

	public synchronized void insertData(Map<String, Object> map) {
        syncAmazonOrderDao.insertData(map);
	}

	public synchronized void updateData(Map<String, Object> map) {
        syncAmazonOrderDao.updateData(map);
	}

	public synchronized void insertItemData(Map<String, Object> map) {
        syncAmazonOrderDao.insertItemData(map);
	}

	public synchronized void updateItemData(Map<String, Object> map) {
        syncAmazonOrderDao.updateItemData(map);
	}
	
}
