package com.it.ocs.task.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.cache.OrderCache;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.task.dao.IAmazonOrderDao;
import com.it.ocs.task.dao.IEbayOrderDao;
import com.it.ocs.task.dao.impl.OracleData;
import com.it.ocs.task.model.AmazonOrderModel;
import com.it.ocs.task.model.EbayOrderItemModel;
import com.it.ocs.task.model.EbayOrderModel;
import com.it.ocs.task.model.EbayOrderSubModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonOrderService;
import com.it.ocs.task.service.IEbayOrderItemService;
import com.it.ocs.task.service.IEbayOrderService;
import com.it.ocs.task.service.IEbayOrderSubService;
import com.it.ocs.task.util.OutInfo;

@Service
public class EbayOrderService extends BaseService implements IEbayOrderService {

	@Autowired
	private IEbayOrderDao ebayOrderDao;
	@Autowired
	private IAmazonOrderDao amazonOrderDao;
	@Autowired
	private IEbayOrderItemService ebayOrderItemService;
	@Autowired
	private IEbayOrderSubService ebayOrderSubService;
	
	@Override
	public OperationResult synchronouEbayOrders(List<EbayOrderModel> ebayOrderModels,
			List<EbayOrderItemModel> ebayOrderItemModels,String pathParam) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.MASTER);
		
		List<EbayOrderModel> updateEbayOrderModels = Lists.newArrayList();
		List<EbayOrderModel> oracleOrderModels = findEbayOrders();
		List<EbayOrderItemModel> updateEbayOrderItemModels = Lists.newArrayList();
		List<EbayOrderItemModel> oracleOrderItemModels = ebayOrderItemService.findEbayOrderItems();
		
		HashMap<String, EbayOrderModel> ebayOrderMap = new HashMap<>();
		
		long insertCount = ebayOrderModels.size();
		long orderItemCount = ebayOrderItemModels.size();
		List<Long> sql = amazonOrderDao.getOrderSeqs(insertCount);
		HashMap<String, Long> ebayMap = new HashMap<>();
		for(EbayOrderModel ebay : oracleOrderModels){
			
			ebayMap.put(ebay.getOrder_id(), ebay.getId());
			
		}
		
		for (int i = 0; i < insertCount; i++) {
			String orderId = ebayOrderModels.get(i).getOrder_id();
			
			if(ebayMap.get(ebayOrderModels.get(i).getOrder_id())!=null){
				
				ebayOrderModels.get(i).setId(ebayMap.get(ebayOrderModels.get(i).getOrder_id()));
			
			}else{
				
				ebayOrderModels.get(i).setId(sql.get(i));
				
			}
			ebayOrderMap.put(orderId, ebayOrderModels.get(i));
			
		}
		
		for (EbayOrderItemModel ebayOrderItemModel : ebayOrderItemModels) {
			if(ebayOrderMap.get(ebayOrderItemModel.getOrder_id())!=null){
				ebayOrderItemModel.setParent_id(ebayOrderMap.get(ebayOrderItemModel.getOrder_id()).getId());
			}
		}
		
		HashMap<String, EbayOrderModel> updateOrderMap = new HashMap<>();
		
		HashMap<Long, EbayOrderItemModel> updateOrderItemMap = new HashMap<>();
		
		for (EbayOrderModel ebayOrderModel : ebayOrderModels) {
			
			updateOrderMap.put(ebayOrderModel.getOrder_id(), ebayOrderModel);
			
		}
		
		for(EbayOrderItemModel ebayOrderItemModel : ebayOrderItemModels){
			
			updateOrderItemMap.put(ebayOrderItemModel.getId(), ebayOrderItemModel);
		}
		
		for (EbayOrderModel ebayOrderModel : oracleOrderModels) {
			
			if (updateOrderMap.get(ebayOrderModel.getOrder_id())!=null){
				
				updateEbayOrderModels.add(updateOrderMap.get(ebayOrderModel.getOrder_id()));
			}
		}
		for (EbayOrderItemModel ebayOrderItemModel : oracleOrderItemModels) {
			
			if(updateOrderItemMap.get(ebayOrderItemModel.getSource_id())!=null){
				
				updateEbayOrderItemModels.add(updateOrderItemMap.get(ebayOrderItemModel.getSource_id()));
			
			}
		}
		
		if(updateEbayOrderModels.size()>0){
		ebayOrderModels.removeAll(updateEbayOrderModels);
		}
		
		if(updateEbayOrderItemModels.size()>0){
			
			ebayOrderItemModels.removeAll(updateEbayOrderItemModels);
		}
		
		orderItemCount = ebayOrderItemModels.size();
		long updateItemCount = updateEbayOrderItemModels.size();
		insertCount = ebayOrderModels.size();
		long updateCount = updateEbayOrderModels.size();
		
		
		List<EbayOrderSubModel> ebayOrderSubModels = Lists.newArrayList();
		List<EbayOrderSubModel> updateEbayOrderSubModels = Lists.newArrayList();
		
		OperationResult result = new OperationResult();
		try {
			if(updateCount>0){
				
				for (EbayOrderModel ebayOrderModel : updateEbayOrderModels) {
					
					updateEbayOrderSubModels.add(orderModelAddToSubModel(ebayOrderModel));
				
				}
			}
			
			for (EbayOrderModel ebayOrderModel : ebayOrderModels) {
				ebayOrderSubModels.add(orderModelAddToSubModel(ebayOrderModel));
				
			}
			
			
			if (insertCount>0) {
				
				int orderK=0;
				for(int i=0; i<=insertCount/500+1;i++){
					List<EbayOrderModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,orderK++){
						if(orderK<insertCount){
						  list.add(ebayOrderModels.get(orderK));
						}
					}
					if (list.size()!=0){
						insertEbayOrders(list);
					}
					
				}
				
			
			}
			if (updateCount>0) {
				int orderK=0;
				for(int i=0; i<=updateCount/500+1;i++){
					List<EbayOrderModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,orderK++){
						if(orderK<updateCount){
						  list.add(updateEbayOrderModels.get(orderK));
						}
					}
					if (list.size()!=0){
						updateEbayOrders(list);
					}
					
				}
			}
			if (orderItemCount>0) {
				
				int k=0;
				
				for(int i=0; i<=orderItemCount/500+1;i++){
					List<EbayOrderItemModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<orderItemCount){
						  String[] string = ebayOrderItemModels.get(k).getTransaction_price().split(" ");
						  if(string.length>1){
						  ebayOrderItemModels.get(k).setCurrencycode(string[0]);
						  ebayOrderItemModels.get(k).setPrice(Double.parseDouble(string[1].trim()));
						  }
						  list.add(ebayOrderItemModels.get(k));
						}
					}
					if (list.size()!=0){
						ebayOrderItemService.insertEbayOrderItems(list);
					}
					
				}
			}
			if (updateItemCount>0) {
				
				int k=0;
				
				for(int i=0; i<=updateItemCount/500+1;i++){
					List<EbayOrderItemModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<updateItemCount){
						  String[] string = updateEbayOrderItemModels.get(k).getTransaction_price().split(" ");
						  if(string.length>1){
							  updateEbayOrderItemModels.get(k).setCurrencycode(string[0]);
							  updateEbayOrderItemModels.get(k).setPrice(Double.parseDouble(string[1].trim()));
						  }
						  list.add(updateEbayOrderItemModels.get(k));
						}
					}
					if (list.size()!=0){
						ebayOrderItemService.updateEbayOrderItems(list);
					}
					
				}
			}
			if(insertCount>0){
				int subK=0;
				for(int i=0; i<=insertCount/500+1;i++){
					List<EbayOrderSubModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,subK++){
						if(subK<insertCount){
						  list.add(ebayOrderSubModels.get(subK));
						}
					}
					if (list.size()!=0){
						OracleData.insertEbaySub(list);
					}
					
				}
			}
			if (updateCount>0) {
				int subK=0;
				for(int i=0; i<=updateCount/500+1;i++){
					List<EbayOrderSubModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,subK++){
						if(subK<updateCount){
						  list.add(updateEbayOrderSubModels.get(subK));
						}
					}
					if (list.size()!=0){
						OracleData.updateEbaySub(list);
					}
					
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			OutInfo.Out(e.toString()+"\n", OrderRecord.path);
			e.printStackTrace();
		}
		
		OutInfo.Out("      ebay_order添加的总数："+insertCount+"\n", OrderRecord.path);
		OutInfo.Out("      ebay_order修改的总数："+updateCount+"\n", OrderRecord.path);
		OutInfo.Out("      ebay_order_item添加的总数："+orderItemCount+"\n", OrderRecord.path);
		OutInfo.Out("      ebay_order_item修改的总数："+updateItemCount+"\n", OrderRecord.path);
		OutInfo.Out("      ebay_order_sub添加的总数："+insertCount+"\n", OrderRecord.path);
		OutInfo.Out("      ebay_order_sub修改的总数："+updateCount+"\n", OrderRecord.path);
		result.setDescription("      ebay_order添加的总数：" + insertCount+"<br>"
	       		+  "      ebay_order修改的总数：" + updateCount+"<br>"
	       		+  "      ebay_order_item添加的总数：" + orderItemCount+"<br>"
	       		+  "      ebay_order_item修改的总数：" + updateItemCount+"<br>"
	       		+ "      ebay_order_sub添加的总数：" + insertCount+"<br>"
	       		+ "    ebay_order_sub修改的总数：" + updateCount+"<br>");

		return result;
	}

	@Override
	public OperationResult insertEbayOrders(List<EbayOrderModel> ebayOrderModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			
			ebayOrderDao.insertEbayOrders(ebayOrderModels);
		
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			
			result.setDescription("insert error");
			OutInfo.Out(e.toString()+"\n", OrderRecord.path);	
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult updateEbayOrders(List<EbayOrderModel> ebayOrderModels) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.MASTER);
		OperationResult result = new OperationResult();
		try {
			
			ebayOrderDao.updateEbayOrders(ebayOrderModels);
		
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			
			result.setDescription("update error");
			OutInfo.Out(e.toString()+"\n", OrderRecord.path);		
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<EbayOrderModel> findEbayOrders() {
		// TODO Auto-generated method stub
		return ebayOrderDao.findEbayOrders();
	}

	protected EbayOrderSubModel orderModelAddToSubModel(EbayOrderModel ebayOrderModel){
		
		EbayOrderSubModel ebayOrderSubModel = new EbayOrderSubModel();
		
		ebayOrderSubModel.setParent_id(ebayOrderModel.getId());
		ebayOrderSubModel.setCheckout_status(ebayOrderModel.getCheckout_status());
		ebayOrderSubModel.setShipping_details(ebayOrderModel.getShipping_details());
		ebayOrderSubModel.setShipping_address(ebayOrderModel.getShipping_address());
		ebayOrderSubModel.setShipping_service_selected(ebayOrderModel.getShipping_service_selected());
		ebayOrderSubModel.setExternal_transaction(ebayOrderModel.getExternal_transaction());
		ebayOrderSubModel.setTransaction_array(ebayOrderModel.getTransaction_array());
		ebayOrderSubModel.setMonetary_details(ebayOrderModel.getMonetary_details());
		ebayOrderSubModel.setOrder_id(ebayOrderModel.getOrder_id());
		return ebayOrderSubModel;
	}

	@Override
	public String selectMaxLastFetchTime() {
		// TODO Auto-generated method stub
		return ebayOrderDao.selectMaxLastFetchTime();
	}
	
	

}
