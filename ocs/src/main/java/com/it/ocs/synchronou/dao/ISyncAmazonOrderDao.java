package com.it.ocs.synchronou.dao;

import java.util.List;
import java.util.Map;

import com.it.ocs.synchronou.model.AmazonAccountModel;
import com.it.ocs.task.model.AmazonOrderModel;

public interface ISyncAmazonOrderDao {

	public Integer isExist(String orderId);

	public void insertData(Map<String, Object> map);

	public int getId();

	public void updateData(Map<String, Object> map);

	public int itemIsExist(String itemId);

	public void insertItemData(Map<String, Object> map);

	public void updateItemData(Map<String, Object> map);

	public String getOrderPrice(int id);

	public int orderItemIsExistByPid(int id);

	public List<AmazonAccountModel> getAmazonAccounts();

	public String getStartTime(String platform);

	public List<AmazonOrderModel> getNoItemOrder(String platform);

	public String getTimeForm(String account);

	public void insertTimeSet(Map<String, Object> timeSetMap);

	public int getTimeSetId();

	public void udpateTimeSet(Map<String, Object> timeSetMap);

}
