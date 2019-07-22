package com.it.ocs.task.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.serializer.DateCodec;
import com.google.common.collect.Lists;
import com.it.ocs.cache.OrderCache;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.task.dao.IAmazonOrderDao;
import com.it.ocs.task.dao.ILightOrderDao;
import com.it.ocs.task.model.AmazonOrderModel;
import com.it.ocs.task.model.EbayOrderItemModel;
import com.it.ocs.task.model.EbayOrderModel;
import com.it.ocs.task.model.EbayOrderSubModel;
import com.it.ocs.task.model.LightOrderItemModel;
import com.it.ocs.task.model.LightOrderModel;
import com.it.ocs.task.model.LightOrderSubModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonOrderService;
import com.it.ocs.task.service.ILightOrderItemService;
import com.it.ocs.task.service.ILightOrderService;
import com.it.ocs.task.service.ILightOrderSubService;
import com.it.ocs.task.util.OutInfo;

@Service
public class LightOrderService extends BaseService implements ILightOrderService {

	@Autowired
	private ILightOrderDao lightOrderDao;
	@Autowired
	private ILightOrderItemService lightOrderItemService;
	@Autowired
	private ILightOrderSubService lightOrderSubService;
	
	@Autowired
	private IAmazonOrderDao amazonOrderDao;
	@Override
	public OperationResult synchronouLightOrders(List<LightOrderModel> lightOrderModels,
			List<LightOrderItemModel> lightOrderItemModels,String pathParam) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.MASTER);
		OperationResult result = new OperationResult();
		String path = null;
		if (ValidationUtil.isNull(pathParam)) {
			path = OrderRecord.path;
		}else{
			path=pathParam;
		}
		
		List<LightOrderModel> updateLightOrderModels = Lists.newArrayList();
		List<LightOrderModel> oracleLightDates = Lists.newArrayList();
		int oracleCount = lightOrderModels.size();
		if (oracleCount>0) {
			int k=0;
			for(int i=0; i<=oracleCount/500+1;i++){
				List<LightOrderModel> list = Lists.newArrayList();
				for(int j=0;j<500;j++,k++){
					if(k<oracleCount){
					  list.add(lightOrderModels.get(k));
					}
				}
				if (list.size()!=0){
					if (findLightOrders(list).size()>0) {
						oracleLightDates.addAll(findLightOrders(list));
					}
					
				}
				
			}
		}
		
		
		List<LightOrderItemModel> updateLightOrderItemModels = Lists.newArrayList();
		List<LightOrderItemModel> oracleLightItemDates = Lists.newArrayList();
		
		int oracleItemCount = lightOrderItemModels.size();
		if (oracleItemCount>0) {
			int k=0;
			for(int i=0; i<=oracleItemCount/500+1;i++){
				List<LightOrderItemModel> list = Lists.newArrayList();
				for(int j=0;j<500;j++,k++){
					if(k<oracleItemCount){
					  list.add(lightOrderItemModels.get(k));
					}
				}
				if (list.size()!=0){
					if (lightOrderItemService.findLightOrderItems(list).size()>0) {
						oracleLightItemDates.addAll(lightOrderItemService.findLightOrderItems(list));
					}
					
				}
				
			}
		}
		HashMap<Long, LightOrderModel> lightOrderMap = new HashMap<>();
		
		long insertCount = lightOrderModels.size();
		long insertItemCount = lightOrderItemModels.size();
		List<Long> sql = amazonOrderDao.getOrderSeqs(insertCount);
		HashMap<String, Long> lightMap = new HashMap<>();
		
		for(LightOrderModel light : oracleLightDates){
			
			lightMap.put(light.getOrder_id(), light.getEntity_id());
		}
		for (int i = 0; i < insertCount; i++) {
			
			Long entityId = lightOrderModels.get(i).getEntity_id();
			
			if(lightMap.get(lightOrderModels.get(i).getOrder_id())!=null){
				
				lightOrderModels.get(i).setEntity_id(lightMap.get(lightOrderModels.get(i).getOrder_id()));
			}else{
				lightOrderModels.get(i).setEntity_id(sql.get(i));
			}
			
			lightOrderMap.put(entityId, lightOrderModels.get(i));
			
		}
		
		for (LightOrderItemModel lightOrderItemModel : lightOrderItemModels) {
			
			if(lightOrderMap.get(lightOrderItemModel.getParent_id())!=null){
				
				lightOrderItemModel.setParent_id(lightOrderMap.get(lightOrderItemModel.getParent_id()).getEntity_id());
				
			}
		}
		
		HashMap<String, LightOrderModel> updateLightOrderMap = new HashMap<>();
		HashMap<Long, LightOrderItemModel> updateLightOrderItemMap = new HashMap<>();
		
		for (LightOrderModel lightOrderModel : lightOrderModels) {
			
			 updateLightOrderMap.put(lightOrderModel.getOrder_id(), lightOrderModel);
		}
		for (LightOrderItemModel lightOrderItemModel : lightOrderItemModels){
			
			updateLightOrderItemMap.put(lightOrderItemModel.getEntity_id(), lightOrderItemModel);
		}
		
		for (LightOrderModel lightOrderModel : oracleLightDates){
			
			if(updateLightOrderMap.get(lightOrderModel.getOrder_id())!=null){
				
				updateLightOrderModels.add(updateLightOrderMap.get(lightOrderModel.getOrder_id()));
			}
			
		}
		
		for (LightOrderItemModel lightOrderItemModel : oracleLightItemDates) {
			
			if(updateLightOrderItemMap.get(lightOrderItemModel.getSource_id())!=null){
				
				updateLightOrderItemModels.add(updateLightOrderItemMap.get(lightOrderItemModel.getSource_id()));
			}
		}
		
		if(updateLightOrderModels.size()>0){
			lightOrderModels.removeAll(updateLightOrderModels);
		}
		
		if(updateLightOrderItemModels.size()>0){
			
			lightOrderItemModels.removeAll(updateLightOrderItemModels);
		}
		
		insertItemCount = lightOrderItemModels.size();
		long updateItemCount = updateLightOrderItemModels.size();
		insertCount = lightOrderModels.size();
		long updateCount = updateLightOrderModels.size();
		
		
		List<LightOrderSubModel> lightOrderSubModels = Lists.newArrayList();
		List<LightOrderSubModel> updateLightOrderSubModels = Lists.newArrayList();
		
		
		try {
			
			if (updateCount>0) {
				
				for (LightOrderModel lightOrderModel : updateLightOrderModels) {
					
					updateLightOrderSubModels.add(oracleModelToSubModel(lightOrderModel));
				}
			}
			if (insertCount>0){
				
				for(LightOrderModel lightOrderModel : lightOrderModels){
					
					lightOrderSubModels.add(oracleModelToSubModel(lightOrderModel));
				}
			}
			
			
			if (insertCount>0) {
				
				int k=0;
				
				for(int i=0; i<=insertCount/500+1;i++){
					List<LightOrderModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<insertCount){
						  list.add(lightOrderModels.get(k));
						}
					}
					if (list.size()!=0){
						insertLightOrders(list);
					}
					
				}
			}

			if (updateCount>0) {
				
				int k=0;
				
				for(int i=0; i<=updateCount/500+1;i++){
					List<LightOrderModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<updateCount){
						  list.add(updateLightOrderModels.get(k));
						}
					}
					if (list.size()!=0){
						updateLightOrders(list);
					}
					
				}
			}
			if (insertCount>0) {
				
				int k=0;
				
				for(int i=0; i<=insertCount/500+1;i++){
					List<LightOrderSubModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<insertCount){
						  list.add(lightOrderSubModels.get(k));
						}
					}
					if (list.size()!=0){
						lightOrderSubService.insertLightOrderSubs(list);
					}
					
				}
			}
			if (updateCount>0) {
				
				int k=0;
				
				for(int i=0; i<=updateCount/500+1;i++){
					List<LightOrderSubModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<updateCount){
						  list.add(updateLightOrderSubModels.get(k));
						}
					}
					if (list.size()!=0){
						lightOrderSubService.updateLightOrderSubs(list);
					}
					
				}
			}
			if (insertItemCount>0) {
				
				int k=0;
				
				for(int i=0; i<=insertItemCount/500+1;i++){
					List<LightOrderItemModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<insertItemCount){
						  
						  list.add(lightOrderItemModels.get(k));
						}
					}
					if (list.size()!=0){
						lightOrderItemService.insertLightOrderItems(list);
					}
					
				}
			}
			if (updateItemCount>0) {
				
				int k=0;
				
				for(int i=0; i<=updateItemCount/10+1;i++){
					List<LightOrderItemModel> list = Lists.newArrayList();
					for(int j=0;j<10;j++,k++){
						if(k<updateItemCount){
						  
						  list.add(updateLightOrderItemModels.get(k));
						}
					}
					if (list.size()!=0){
						lightOrderItemService.updateLightOrderItems(list);
					}
					
				}
			}
			OutInfo.Out("      light_order添加的总数：" + insertCount+"\n", path);
			OutInfo.Out("      light_order修改的总数：" + updateCount+"\n",  path);
			OutInfo.Out("      light_order_item添加的总数：" + insertItemCount+"\n", path);
			OutInfo.Out("      light_order_item修改的总数：" + updateItemCount+"\n",  path);
			OutInfo.Out("      light_order_sub添加的总数：" + insertCount+"\n",  path);
			OutInfo.Out("      light_order_sub修改的总数：" + updateCount+"\n",  path);
			result.setDescription("      light_order添加的总数：" + insertCount+"<br>"
						       		+  "      light_order修改的总数：" + updateCount+"<br>"
						       		+  "      light_order_item添加的总数：" + insertItemCount+"<br>"
						       		+  "      light_order_item修改的总数：" + updateItemCount+"<br>"
						       		+ "      light_order_sub添加的总数：" + insertCount+"<br>"
						       		+ "    light_order_sub修改的总数：" + updateCount+"<br>");
			
		} catch (Exception e) {
			// TODO: handle exception
			OutInfo.Out(e.toString()+"\n",  path);
			e.printStackTrace();
			
		}
		return result;
	}

	@Override
	public OperationResult insertLightOrders(List<LightOrderModel> lightOrderModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			lightOrderDao.insertLightOrders(lightOrderModels);
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			result.setDescription("insert error");
			OutInfo.Out(e.toString()+"\n",  OrderRecord.path);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult updateLightOrders(List<LightOrderModel> lightOrderModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			lightOrderDao.updateLightOrders(lightOrderModels);
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			result.setDescription("update error");
			OutInfo.Out(e.toString()+"\n",  OrderRecord.path);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<LightOrderModel> findLightOrders(List<LightOrderModel> lightOrderModels) {
		// TODO Auto-generated method stub
		return lightOrderDao.findLightOrders(lightOrderModels);
	}
	protected LightOrderSubModel oracleModelToSubModel(LightOrderModel lightOrderModel){
		LightOrderSubModel lightOrderSubModel = new LightOrderSubModel();
		lightOrderSubModel.setParent_id(lightOrderModel.getEntity_id());
		lightOrderSubModel.setCustomer_note(lightOrderModel.getCustomer_note());
		lightOrderSubModel.setOrder_id(lightOrderModel.getOrder_id());
		return lightOrderSubModel;
	}

	protected List<Long> getOrderSeqsBySize(Long size) {
		// TODO Auto-generated method stub
		List<Long> seqs = new ArrayList<>();
		try{
		for (int i=1; i <= size; i++) {
			//Long seq = amazonOrderDao.getOrderSeqs();
			seqs.add(1L);
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return seqs;
	}

	@Override
	public String selectMaxCreatedAt() {
		// TODO Auto-generated method stub
		return lightOrderDao.selectMaxCreatedAt();
	}
}
