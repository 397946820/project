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

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest;
import com.it.ocs.amazon.model.AmazonRequestMode;
import com.it.ocs.amazon.order.AmazonClientRun;
import com.it.ocs.amazon.order.AmazonRequest;
import com.it.ocs.amazon.order.GetAmazonClient;
import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.dao.ISyncAmazonOrderDao;
import com.it.ocs.synchronou.model.AmazonAccountModel;
import com.it.ocs.synchronou.model.ParseAmazonOrderItemXMLModel;
import com.it.ocs.synchronou.service.ISyncAmazonOrderService;
import com.it.ocs.synchronou.util.UTCTimeUtils;
import com.it.ocs.task.core.TaskExecutorUtil;
import com.it.ocs.task.core.TaskRunnable;
import com.it.ocs.task.core.Timer;
import com.it.ocs.task.model.AmazonOrderModel;
import com.it.ocs.task.support.spring.SpringUtils;

@Service
public class SyncAmazonOrderService implements ISyncAmazonOrderService {
	
	private final static Logger log = Logger.getLogger(SyncAmazonOrderService.class);
	
	
	@Autowired
	private ISyncAmazonOrderDao syncAmazonOrderDao;
	
	@Autowired
	private SyncAmazonDaoService syncAmazonDaoService;
	
	/**
	 * 定时任务调度方法，同步订单
	 */
	public void runSyncAmazonOrder(){
		List<AmazonAccountModel> accounts = syncAmazonOrderDao.getAmazonAccounts();
		String endTime = UTCTimeUtils.getUTCTimeStrByMINUTE(-2);
		for(AmazonAccountModel account : accounts){
			String startTime = syncAmazonOrderDao.getStartTime(account.getPlatform());
			//第一次默认时间
			if(null == startTime||"".endsWith(startTime)){
				startTime = UTCTimeUtils.getUTCTimeStr(-4);
			}else{
				startTime = startTime.replace(" ", "T");
				startTime = startTime+"Z";
			}
			createThreadRun(startTime, endTime, account);
		}
	}
	/**
	 *  定时任务调度方法，同步没有详情的订单
	 */
	public void runSyncOrderItemForNoItem(){
		List<AmazonAccountModel> accounts = syncAmazonOrderDao.getAmazonAccounts();
		for(AmazonAccountModel account : accounts){
			//根据账号查询
			createOrderItemThreadRun(account);
		}
	}
	
	private void createOrderItemThreadRun(AmazonAccountModel account) {
		TaskExecutorUtil.threadRun(new TaskRunnable() {
			@Override
			public void runTask() {
				syncOrderItemForNoItemByAccount(account);	
			}
			
			public void syncOrderItemForNoItemByAccount(AmazonAccountModel account) {
				Timer timer = new Timer(1800000L);
			    int count = 0;
			    //Thread.currentThread().setName("Thread-SYNC-Item-"+account.getSellerId());
			    log.info("同步账号"+account.getSellerId()+"没有订单详情的订单的详情信息开始");
				AmazonRequestMode requestMode = createAmazonRequestMode(account);
		        MarketplaceWebServiceOrdersClient client = GetAmazonClient.createClient(requestMode);
		        while(timer.isContinue()&&count < 600){
		        	List<AmazonOrderModel> orders = syncAmazonOrderDao.getNoItemOrder(account.getPlatform());
		        	if(null == orders || orders.size() == 0){
		        		break;
		        	}else{
		        		for(AmazonOrderModel order : orders){
		        			syncOrderItem(order.getParentId(),order.getOrder_id(),requestMode,client);
		        		}
		        		count = count + orders.size();
		        	}

		        }
		        log.info("同步账号"+account.getSellerId()+"没有订单详情的订单的详情信息结束，总共同步了"+count+"条，用时"+timer.getUseTotalTime()+"毫秒");
			}
			
			/**
			 * 同步订单详情信息
			 * @param parentId
			 * @param orderId
			 * @param requestMode
			 * @param client
			 */
			private void syncOrderItem(int parentId,String orderId, AmazonRequestMode requestMode,
					MarketplaceWebServiceOrdersClient client) {
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("AmazonOrderId", orderId);
				requestMode.setParam(param);
				ListOrderItemsRequest request = AmazonRequest.createOrderItemRequest(requestMode);
				String responseXML = AmazonClientRun.invokeListOrderItems(client, request);
				//开始计时
				long startTime = System.currentTimeMillis();
				try {
					Document doc = DocumentHelper.parseText(responseXML);
					ParseAmazonOrderItemXMLModel parse = new ParseAmazonOrderItemXMLModel(doc,"https://mws.amazonservices.com/Orders/2013-09-01");
					List<Map<String,Object>> parseResult = parse.getResult();
					for(Map<String,Object> map : parseResult){
						String createDate = UTCTimeUtils.getUTCTimeStr(0);
						createDate = createDate.replace("T", " ");
						createDate = createDate.replace("Z", "");
						map.put("createDate", createDate);
						map.put("parentId", parentId);
						saveOrUpdateOrderItem(map);
					}
					
				} catch (DocumentException e) {
					log.info("订单详情解析失败", e);
				}
				long endTime = System.currentTimeMillis();
				long time = endTime-startTime;
				if(time < 2500){
					try {
						Thread.sleep(2500-time);
					} catch (InterruptedException e) {
						
					}
				}
				
			}
			
			/**
			 * 保存订单详情信息到数据库
			 * @param map
			 */
			private  void saveOrUpdateOrderItem(Map<String, Object> map) {
				String itemId = (String)map.get("OrderItemId");
				if(syncAmazonOrderDao.itemIsExist(itemId) == 0){
					syncAmazonDaoService.insertItemData(map);
				}else{
					syncAmazonDaoService.updateItemData(map);
				}
				
			}
			
			private AmazonRequestMode createAmazonRequestMode(AmazonAccountModel account) {
				AmazonRequestMode requestMode = new AmazonRequestMode();
				requestMode.setServiceURL(account.getUrl());
				requestMode.setAccessKey(account.getAccessKey());//"AKIAIQJPPHYX7ZMKPTNQ"
				requestMode.setSecretKey(account.getSecretKey());//"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"
				requestMode.setSellerId(account.getSellerId());//AV7KSH7XB8RNM
		        requestMode.setMarketplaceId(account.getMarketplaceId());
				return requestMode;
			}
			
		});
	}

	

	@Override
	public OperationResult syncAmazonOrder(String startTime, String endTime) {
		OperationResult or = new OperationResult();
		List<AmazonAccountModel> accounts = syncAmazonOrderDao.getAmazonAccounts();
		for(AmazonAccountModel account : accounts){
			createThreadRun(startTime, endTime, account);
		}
		or.setData("success");
		return or;
	}

	private void createThreadRun(String startTime, String endTime, AmazonAccountModel account) {
		String beanType = account.getPlatform();
		SyncAmazonOrderServiceSupport amazonOrderService = null;
		String beanName = "";
		if("US".equals(beanType)){
			beanName = "syncOrderServiceUS";
		}
		if("DE".equals(beanType)){
			beanName = "syncOrderServiceDE";
		}
		if("CA".equals(beanType)){
			beanName = "syncOrderServiceCA";
		}
		if("JP".equals(beanType)){
			beanName = "syncOrderServiceJP";
		}
		amazonOrderService = SpringUtils.getBean(beanName);
		TaskExecutorUtil.threadRun(new TaskRunnable(amazonOrderService) {
			@Override
			public void runTask() {
				log.info("-----------"+account.getSellerId()+"同步开始");
				SyncAmazonOrderServiceSupport service = (SyncAmazonOrderServiceSupport)this.getService();
				service.syncOrderDataByAccount(account,startTime,endTime);
				log.info("-----------"+account.getSellerId()+"同步结束");
			}
		});
	}
	
	
	
	/**
	 * 为保存的订单增加默认信息
	 * @param account
	 * @param map
	 */
	private void formateOderData(AmazonAccountModel account, Map<String, Object> map) {
		map.put("platform", account.getPlatform());
		String createDate = UTCTimeUtils.getUTCTimeStr(0);
		createDate = createDate.replace("T", " ");
		createDate = createDate.replace("Z", "");
		map.put("createDate", createDate);
		String street = "";
		String addressLine1 = (String)map.get("AddressLine1");
		if(!"".equals(addressLine1)){
			street = addressLine1;
		}
		String addressLine2 = (String)map.get("AddressLine2");
		if(!"".equals(addressLine2)){
			street = street +" "+ addressLine2;
		}
		String addressLine3 = (String)map.get("AddressLine3");
		if(!"".equals(addressLine3)){
			street = street +" "+  addressLine3;
		}
		map.put("street", street);
	}

}


