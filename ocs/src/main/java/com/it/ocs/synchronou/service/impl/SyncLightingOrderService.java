package com.it.ocs.synchronou.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.dao.ISyncLightingOrderDao;
import com.it.ocs.synchronou.model.LightAccountModel;
import com.it.ocs.synchronou.model.ParseLightOrderItemXMLModel;
import com.it.ocs.synchronou.model.ParseLightOrderXMLNode;
import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.ISyncLightingOrderService;
import com.it.ocs.synchronou.util.HTTPUtils;
import com.it.ocs.synchronou.util.UTCTimeUtils;
import com.it.ocs.task.core.TaskExecutorUtil;
import com.it.ocs.task.core.TaskRunnable;

@Service
public class SyncLightingOrderService implements ISyncLightingOrderService {
	private final static Logger log = Logger.getLogger(SyncLightingOrderService.class);
	@Autowired
	private ISyncLightingOrderDao iSyncLightingOrderDao;
	@Autowired
	private TemplateService templateService;
	
	@Override
	public OperationResult syncLightingOrder() {
		List<LightAccountModel> accounts = iSyncLightingOrderDao.getLightAccount();
		for(LightAccountModel account :accounts){
			createThreadRun(account);
		}
		return null;
	}
	/**
	 * 定时任务调度方法
	 */
	public void taskSyncLightOrder(){
		syncLightingOrder();
	}
	private void createThreadRun(LightAccountModel account){
		TaskExecutorUtil.threadRun(new TaskRunnable() {
			@Override
			public void runTask() {
				SyncLightingOrderServiceSupport syncLightingOrderServiceSupport = new SyncLightingOrderServiceSupport(iSyncLightingOrderDao, templateService);
				syncLightingOrderServiceSupport.getOrderByAccount(account);	
			}
			
		});
	}
	
	/**
	 * 定时任务调度方法(新接口)
	 */
	public void taskSyncLightOrderNew(){
		List<LightAccountModel> accounts = iSyncLightingOrderDao.getLightAccount();
		for(LightAccountModel account :accounts){
			createThreadRunNew(account);
		}
	}
	
	private void createThreadRunNew(LightAccountModel account){
		TaskExecutorUtil.threadRun(new TaskRunnable() {
			@Override
			public void runTask() {
				SyncLightingOrderServiceSupport syncLightingOrderServiceSupport = new SyncLightingOrderServiceSupport(iSyncLightingOrderDao, templateService);
				syncLightingOrderServiceSupport.getOrderByAccountNew(account);	
			}
			
		});
	}
	/**
	 * 修补订单
	 */
	public void taskRepairOrderByCreate(){
		List<LightAccountModel> accounts = iSyncLightingOrderDao.getLightAccount();
		for(LightAccountModel account :accounts){
			createThreadRunRepairOrder(account);
		}
	}
	private void createThreadRunRepairOrder(LightAccountModel account) {
		TaskExecutorUtil.threadRun(new TaskRunnable() {
			@Override
			public void runTask() {
				SyncLightingOrderServiceSupport syncLightingOrderServiceSupport = new SyncLightingOrderServiceSupport(iSyncLightingOrderDao, templateService);
				syncLightingOrderServiceSupport.repairOrder(account);	
			}
			
		});
		
	}
}
