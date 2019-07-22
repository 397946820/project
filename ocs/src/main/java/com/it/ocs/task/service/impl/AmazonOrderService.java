package com.it.ocs.task.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.cache.OrderCache;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.salesStatistics.model.LightOrderModel;
import com.it.ocs.site.dao.ISiteDAO;
import com.it.ocs.site.model.SiteModel;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.task.dao.IAmazonOrderDao;
import com.it.ocs.task.dao.IAmazonOrderItemDao;
import com.it.ocs.task.model.AmazonOrderItemModel;
import com.it.ocs.task.model.AmazonOrderModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonOrderItemService;
import com.it.ocs.task.service.IAmazonOrderService;
import com.it.ocs.task.util.OutInfo;
@Service
public class AmazonOrderService extends BaseService implements IAmazonOrderService {

	@Autowired
	private IAmazonOrderDao amazonOrderDao;
	
	@Autowired
	private IAmazonOrderItemDao amazonOrderItemDao;
	
	@Autowired
	private IAmazonOrderItemService amazonOrderItemService;
	
	@Override
	public OperationResult synchronouAmazonOrders(List<AmazonOrderModel> amazonOrderModels,List<AmazonOrderItemModel> amazonOrderItemModels,String pathParam) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.MASTER);
		String path = null;
		if(ValidationUtil.isNull(pathParam)){
			path=OrderRecord.path;
		}else{
			path = pathParam;
		}
		/*Collections.sort(amazonOrderItemModels, new Comparator<AmazonOrderItemModel>() {

			@Override
			public int compare(AmazonOrderItemModel o1, AmazonOrderItemModel o2) {
				// TODO Auto-generated method stub
				return o1.getUpdated_at().compareTo(o2.getUpdated_at());
			}
		});*/
		
		/*Map<String, AmazonOrderItemModel> amazonOrderItemMap = new HashMap<>();
		for (AmazonOrderItemModel amazonOrderItemModel : amazonOrderItemModels) {
			amazonOrderItemMap.put(amazonOrderItemModel.getAmazon_item_id(), amazonOrderItemModel);
		}*/
		/*amazonOrderItemModels.clear();
		for (Map.Entry<String, AmazonOrderItemModel> entry : amazonOrderItemMap.entrySet()) { 
			amazonOrderItemModels.add(entry.getValue()); 
		}*/
		OperationResult result = new OperationResult();
		try {
			List<AmazonOrderModel> updateAmazon = new ArrayList<>();
			List<AmazonOrderModel> oracleAmazon =  Lists.newArrayList();
			int oracleCount = amazonOrderModels.size();
			if (oracleCount>0) {
				int k=0;
				for(int i=0; i<=oracleCount/500+1;i++){
					List<AmazonOrderModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<oracleCount){
						  list.add(amazonOrderModels.get(k));
						}
					}
					if (list.size()!=0){
						if (findAmazonOrders(list).size()>0) {
							oracleAmazon.addAll(findAmazonOrders(list));
						}
						
					}
					
				}
			}

			List<AmazonOrderItemModel> updateAmazonItem = new ArrayList<>();
			
			List<AmazonOrderItemModel> oracleAmazonItem = Lists.newArrayList();
			
			int oracleItemCount = amazonOrderItemModels.size();
			if (oracleItemCount>0) {
				int k=0;
				for(int i=0; i<=oracleItemCount/500+1;i++){
					List<AmazonOrderItemModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<oracleItemCount){
						  list.add(amazonOrderItemModels.get(k));
						}
					}
					if (list.size()!=0){
						if(amazonOrderItemService.findAmazonOrderItems(list).size()>0){
							
							oracleAmazonItem.addAll(amazonOrderItemService.findAmazonOrderItems(list));
						}
						
					}
					
				}
			}
			
			long count =  amazonOrderModels.size();
			long countItem = (long) amazonOrderItemModels.size();
			
			HashMap<Long,AmazonOrderModel> amazonOrderHash = new HashMap();
			
			List<Long> sql = amazonOrderDao.getOrderSeqs(count);
			
			HashMap<String, Long> oracleMap = new HashMap<>();
			
			for (AmazonOrderModel amazon : oracleAmazon) {
				oracleMap.put(amazon.getOrder_id(), amazon.getId());
			}
			
			
			for (int i = 0; i < count; i++) {
				Long id = amazonOrderModels.get(i).getId();
				
				if(oracleMap.get(amazonOrderModels.get(i).getOrder_id())!=null){
					
					amazonOrderModels.get(i).setId(oracleMap.get(amazonOrderModels.get(i).getOrder_id()));
				}else{
					amazonOrderModels.get(i).setId(sql.get(i));
				}
				amazonOrderHash.put(id, amazonOrderModels.get(i));
				
			}
			
			for (AmazonOrderItemModel amazonOrderItemModel : amazonOrderItemModels) {
				
				if(amazonOrderHash.get(amazonOrderItemModel.getParent_id())!=null){
					
					amazonOrderItemModel.setParent_id(amazonOrderHash.get(amazonOrderItemModel.getParent_id()).getId());
				}
			}
			
			HashMap<String, AmazonOrderModel> amazonOrderHash2= new HashMap<>();
			HashMap<String, AmazonOrderItemModel> amazonOrderItemHash = new HashMap<>();
			
			
			for (AmazonOrderModel amazonOrderModel : amazonOrderModels) {
				amazonOrderHash2.put(amazonOrderModel.getOrder_id(), amazonOrderModel);
			}
				
			
			for (AmazonOrderModel amazonOrderModel : oracleAmazon) {
				
				if (amazonOrderHash2.get(amazonOrderModel.getOrder_id())!=null) {
					
					updateAmazon.add(amazonOrderHash2.get(amazonOrderModel.getOrder_id()));
				}
				
			}
			for (AmazonOrderItemModel amazonOrderItemModel : amazonOrderItemModels) {
				amazonOrderItemHash.put(amazonOrderItemModel.getAmazon_item_id(), amazonOrderItemModel);
			}
			for (AmazonOrderItemModel amazonOrderItemModel : oracleAmazonItem) {
				
				if(amazonOrderItemHash.get(amazonOrderItemModel.getAmazon_item_id())!=null){
					
					updateAmazonItem.add(amazonOrderItemHash.get(amazonOrderItemModel.getAmazon_item_id()));
				}
			}
			if (updateAmazon.size()>0) {
				amazonOrderModels.removeAll(updateAmazon);
			}
			if(updateAmazonItem.size()>0){
				amazonOrderItemModels.removeAll(updateAmazonItem);
			}
			count=amazonOrderModels.size();
			long updateCount = updateAmazon.size();
			countItem=amazonOrderItemModels.size();
			long updateCountItem = updateAmazonItem.size();
			
			
			if (count>0) {
				
				int k=0;
				
				for(int i=0; i<=count/500+1;i++){
					List<AmazonOrderModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<count){
						  list.add(amazonOrderModels.get(k));
						}
					}
					if (list.size()!=0){
						insertAmazonOrders(list);
					}
					
				}
			}
			
			
			if (updateCount>0) {
				int k=0;
				
				for(int i=0; i<=updateCount/500+1;i++){
					List<AmazonOrderModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<updateCount){
						  list.add(updateAmazon.get(k));
						}
					}
					if (list.size()!=0){
						updateAmazonOrders(list);
					}
					
				}
			}
			if (countItem>0) {
				
				int k=0;
				
				for(int i=0; i<=countItem/500+1;i++){
					List<AmazonOrderItemModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<countItem){
						  list.add(amazonOrderItemModels.get(k));
						}
					}
					if (list.size()!=0){
						amazonOrderItemService.insertAmazonOrderItems(list);
					}
					
				}
			}
			if (updateCountItem>0) {
				
				int k=0;
				
				for(int i=0; i<=updateCountItem/500+1;i++){
					List<AmazonOrderItemModel> list = Lists.newArrayList();
					for(int j=0;j<500;j++,k++){
						if(k<updateCountItem){
						  list.add(updateAmazonItem.get(k));
						}
					}
					if (list.size()!=0){
						amazonOrderItemService.updateAmazonOrderItems(list);
					}
					
				}
			}
			Long repeat = amazonOrderItemDao.selectOrderItemRepeat();
			while (repeat>0) {
				
				amazonOrderItemDao.deleteOrderItemRepeat();
				
				repeat=amazonOrderItemDao.selectOrderItemRepeat();
				
			}
			OutInfo.Out("      amazon_order添加的总数："+count+"\n", path);
			OutInfo.Out("      amazon_order修改的总数："+updateCount+"\n", path);
			OutInfo.Out("      amazon_order_item添加的总数："+countItem+"\n", path);
			OutInfo.Out("      amazon_order_item修改的总数："+updateCountItem+"\n", path);
			result.setDescription("      amazon_order添加的总数：" + count+"<br>"
		       		+  "      amazon_order修改的总数：" + updateCount+"<br>"
		       		+  "      amazon_order_item添加的总数：" + countItem+"<br>"
		       		+  "      amazon_order_item修改的总数：" + updateCountItem+"<br>");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			OutInfo.Out(e.toString()+"\n", path);
		}
		
		
		
		return result;
	}

	@Override
	public OperationResult insertAmazonOrders(List<AmazonOrderModel> amazonOrderModels) {
		// TODO Auto-generated method stub
		for (AmazonOrderModel amazonOrderModel : amazonOrderModels) {
			 if (amazonOrderModel.getCreated_at()==null&&amazonOrderModel.getUpdated_at()!=null) {
				amazonOrderModel.setCreated_at(amazonOrderModel.getUpdated_at());
			}else if(amazonOrderModel.getCreated_at()==null&&amazonOrderModel.getUpdated_at()!=null){
				Timestamp time = new Timestamp(System.currentTimeMillis());
				amazonOrderModel.setCreated_at(time);;
			}
			
		}
		OperationResult result = new OperationResult();
		try {
			
			amazonOrderDao.insertAmazonOrders(amazonOrderModels);
			
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
	public OperationResult updateAmazonOrders(List<AmazonOrderModel> amazonOrderModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		for (AmazonOrderModel amazonOrderModel : amazonOrderModels) {
			 if (amazonOrderModel.getCreated_at()==null&&amazonOrderModel.getUpdated_at()!=null) {
				amazonOrderModel.setCreated_at(amazonOrderModel.getUpdated_at());
			}else if(amazonOrderModel.getCreated_at()==null&&amazonOrderModel.getUpdated_at()!=null){
				Timestamp time = new Timestamp(System.currentTimeMillis());
				amazonOrderModel.setCreated_at(time);;
			}
			
		}
		
		try {
			
			amazonOrderDao.updateAmazonOrders(amazonOrderModels);
			
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			
			result.setDescription("update error");
			OutInfo.Out(e.toString()+"\n", OrderRecord.path);	
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AmazonOrderModel> findAmazonOrders(List<AmazonOrderModel> amazonOrderModels) {
		// TODO Auto-generated method stub
		return amazonOrderDao.findAmazonOrders(amazonOrderModels);
	}

	protected List<Long> getOrderSeqsBySize(Long size) {
		// TODO Auto-generated method stub
		List<Long> seqs = new ArrayList<>();
		try{
		for (int i=1; i <= size; i++) {
			//Long seq = amazonOrderDao.getOrderSeqs();
			seqs.add(2L);
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return seqs;
	}

	
	private List<Long> getOrderItemSeqsBySize(Long size) {
		// TODO Auto-generated method stub
		List<Long> seqs = new ArrayList<>();
		for(int i=1; i<=size; i++){
			//Long seq = amazonOrderDao.getOrderItemSeqs();
			seqs.add(2L);
		}
		return seqs;
	}

	@Override
	public String selectMaxCreatedAt() {
		// TODO Auto-generated method stub
		return amazonOrderDao.selectMaxCreatedAt();
	}

	@Override
	public OperationResult handleReissueTranNumber(Map<String, Object> param) {
		try {
			this.amazonOrderDao.handleReissueTranNumber(param);
			return new OperationResult();
		} catch (Exception e) {
			return new OperationResult(1, "处理亚马逊补发单跟踪号失败", null, null);
		}
	}

}
