package com.it.ocs.task.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.service.inner.ISynchronizationService;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.listener.ProjectApplicationContext;
import com.it.ocs.task.dao.impl.MySqlData;
import com.it.ocs.task.model.AmazonOrderItemModel;
import com.it.ocs.task.model.AmazonOrderModel;
import com.it.ocs.task.model.EbayOrderItemModel;
import com.it.ocs.task.model.EbayOrderModel;
import com.it.ocs.task.model.LightOrderItemModel;
import com.it.ocs.task.model.LightOrderModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonOrderService;
import com.it.ocs.task.service.IEbayOrderService;
import com.it.ocs.task.service.ILightOrderService;
import com.it.ocs.task.service.IMAmazonOrderItemService;
import com.it.ocs.task.service.IMAmazonOrderService;
import com.it.ocs.task.service.IMEBayOrderItemService;
import com.it.ocs.task.service.IMEBayOrderService;
import com.it.ocs.task.service.IMLightOrderItemService;
import com.it.ocs.task.service.IMLightOrderService;
import com.it.ocs.task.service.IRealTimeSynchronouService;
import com.it.ocs.task.util.DateToList;
import com.it.ocs.task.util.OutInfo;


@Service
public class RealTimeSynchronouService implements IRealTimeSynchronouService {

	@Autowired
	private IAmazonOrderService amazonService;
	@Autowired
	private IEbayOrderService ebayService;
	@Autowired
	private ILightOrderService lightService;
	@Autowired
	private IMAmazonOrderService mAmazonService;
	@Autowired
	private IMAmazonOrderItemService mAmazonItemService;
	@Autowired
	private IMEBayOrderService mEbayService;
	@Autowired
	private IMEBayOrderItemService mEbayItemService;
	@Autowired
	private IMLightOrderService mLightService;
	@Autowired
	private IMLightOrderItemService mLightItemService;
	@Autowired
	private ProjectApplicationContext pac;
	@Autowired 
	private ISynchronizationService synchronizationService;
	
	@Override
	public OperationResult sysnchronouOrderAndOrderItem() {
		// TODO Auto-generated method stub
		
		//String path = "D:/testInof.txt";
				String path = "/home/oracle/Public/oracle表与oracleItem表数据对接日记.txt";
				String url = "jdbc:mysql://192.168.2.23:3306/leamazon?relaxAutoCommit=true&zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=gbk&useSSL=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";
				String user = "leamazon_read";
				String password = "38T7YXB528";
				OutInfo.Out("开始执行时间："+ new Date().toString()+"\n", path);
				try {
				MySqlData amazonDao = new MySqlData(url, user, password);
				MySqlData amazonDao2 = new MySqlData(url, user, password);
				MySqlData mySqlDao = new MySqlData(url,user,password);
				MySqlData mySqlDao2 = new MySqlData(url,user,password);
				MySqlData lightDao = new MySqlData(url, user, password);
				MySqlData lightDao2 = new MySqlData(url, user, password);
				
				
				String amazonStartT = amazonService.selectMaxCreatedAt();
				String ebayStartT = ebayService.selectMaxLastFetchTime();
				String lightStartT = lightService.selectMaxCreatedAt();
				
				String amazonEndT = mAmazonService.selectMaxCreatedAt();
				String ebayEndT = mEbayService.selectMaxLastFetchTime();
				String lightEndT = mLightService.selectMaxCreatedAt();
				
				
			  if (amazonStartT!=null) {
					OutInfo.Out("   amazon_order获取数据的时间段 : "+amazonStartT+"< time >="+amazonEndT+"\n", path);
					OutInfo.Out("   amazon_order_item获取数据的时间段 : "+amazonStartT+"< time >="+amazonEndT+"\n", path);
					
					ResultSet amazonOrderSet = amazonDao.getMySqlDate("select * from amazon_order where updated_at>'"+amazonStartT+"' and updated_at<='"+amazonEndT+"'");
					ResultSet amazonOrderItemSet = amazonDao2.getMySqlDate("select aoi.* from amazon_order_item aoi inner join (select id from amazon_order where   updated_at>'"+amazonStartT+"' and updated_at<='"+amazonEndT+"') ao where aoi.parent_id=ao.id");
					List<AmazonOrderModel> amazonOrderModels = DateToList.toListModel(amazonOrderSet, AmazonOrderModel.class);
					List<AmazonOrderItemModel> amazonOrderItemModels = DateToList.toListModel(amazonOrderItemSet, AmazonOrderItemModel.class);
					amazonService.synchronouAmazonOrders(amazonOrderModels, amazonOrderItemModels,OrderRecord.amazonOrderPath);
					

				}else{
					
					ResultSet amazonOrderSet = amazonDao.getMySqlDate("select * from amazon_order where updated_at<='2016-09-13 13:11:44'");
					ResultSet amazonOrderItemSet = amazonDao2.getMySqlDate("select aoi.* from amazon_order_item aoi inner join (select id from amazon_order where   updated_at<='2016-09-13 13:11:44') ao where aoi.parent_id=ao.id");
					List<AmazonOrderModel> amazonOrderModels = DateToList.toListModel(amazonOrderSet, AmazonOrderModel.class);
					List<AmazonOrderItemModel> amazonOrderItemModels = DateToList.toListModel(amazonOrderItemSet, AmazonOrderItemModel.class);
					amazonService.synchronouAmazonOrders(amazonOrderModels, amazonOrderItemModels,OrderRecord.amazonOrderPath);
				}
				if(lightStartT!=null){
					
					OutInfo.Out("   light_order获取数据的时间段 : "+lightStartT+"< time >="+lightEndT+"\n", path);
					OutInfo.Out("   light_order_item获取数据的时间段 : "+lightStartT+"< time >="+lightEndT+"\n", path);
					ResultSet lightSet = lightDao.getMySqlDate("select * from light_order where updated_at>'"+lightStartT+"'and updated_at<='"+lightEndT+"'");
					ResultSet lightOrderSet = lightDao2.getMySqlDate("select loi.* from light_order_item loi inner join (select eo.entity_id from light_order eo where  updated_at>'"+lightStartT+"'and updated_at<='"+lightEndT+"') eof where loi.parent_id=eof.entity_id");
					List<LightOrderModel> lightOrderModels = DateToList.toListModel(lightSet, LightOrderModel.class);
					List<LightOrderItemModel> lightOrderItemModels = DateToList.toListModel(lightOrderSet, LightOrderItemModel.class);
					
					lightService.synchronouLightOrders(lightOrderModels, lightOrderItemModels,null);

				}else{
					
					ResultSet lightSet = lightDao.getMySqlDate("select * from light_order where  updated_at<='2017-03-13 13:11:44'");
					ResultSet lightOrderSet = lightDao2.getMySqlDate("select loi.* from light_order_item loi inner join (select eo.entity_id from light_order eo where  updated_at<='2017-03-13 13:11:44') eof where loi.parent_id=eof.entity_id");
					
					List<LightOrderModel> lightOrderModels = DateToList.toListModel(lightSet, LightOrderModel.class);
					List<LightOrderItemModel> lightOrderItemModels = DateToList.toListModel(lightOrderSet, LightOrderItemModel.class);

					lightService.synchronouLightOrders(lightOrderModels, lightOrderItemModels,null);
				}
				if(ebayStartT!=null){
					
					OutInfo.Out("   ebay_order获取数据的时间段 : "+ebayStartT+"< time >="+ebayEndT+"\n", path);
					OutInfo.Out("   ebay_order_item获取数据的时间段 : "+ebayStartT+"< time >="+ebayEndT+"\n", path);
					ResultSet  ebayOrderSet = mySqlDao.getMySqlDate("select * from ebay_order_full where last_modified_time>'"+ebayStartT+"' and last_modified_time<='"+ebayEndT+"'");
					ResultSet ebayOrderItemSet = mySqlDao2.getMySqlDate("select eofi.* from ebay_order_full_item eofi inner join (select eo.order_id from ebay_order_full eo where  last_modified_time>'"+ebayStartT+"' and last_modified_time<='"+ebayEndT+"') eof where eofi.order_id=eof.order_id");
					List<EbayOrderModel> ebayOrderModels = DateToList.toListModel(ebayOrderSet, EbayOrderModel.class);
					List<EbayOrderItemModel> ebayOrderItemModels = DateToList.toListModel(ebayOrderItemSet, EbayOrderItemModel.class);
					ebayService.synchronouEbayOrders(ebayOrderModels, ebayOrderItemModels,null);
				
				}else{
					
					ResultSet ebayOrderSet = mySqlDao.getMySqlDate("select * from ebay_order_full where  last_modified_time<='2017-06-13 13:11:44'");
					ResultSet ebayOrderItemSet = mySqlDao2.getMySqlDate("select eofi.* from ebay_order_full_item eofi inner join (select eo.order_id from ebay_order_full eo where   last_modified_time<='2017-06-13 13:11:44') eof where eofi.order_id=eof.order_id");
					List<EbayOrderModel> ebayOrderModels = DateToList.toListModel(ebayOrderSet, EbayOrderModel.class);
					List<EbayOrderItemModel> ebayOrderItemModels = DateToList.toListModel(ebayOrderItemSet, EbayOrderItemModel.class);
					
					ebayService.synchronouEbayOrders(ebayOrderModels, ebayOrderItemModels,null);
				}
				
				
				
				
				} catch (Exception e) {
					// TODO: handle exception
					OutInfo.Out(e.toString()+"\n", path);
					e.printStackTrace();
				}
				
				OutInfo.Out("执行结束时间："+new Date().toString()+"\n", path);
				
			
		return null;
	}
	
	@Override
	public OperationResult amazonSynchronou(Map<String, String> map) {
		// TODO Auto-generated method stub
		OperationResult reslut = new OperationResult();
		try {
			OutInfo.Out("开始执行时间：" + new Date().toString() + "\n", OrderRecord.amazonOrderPath);
			String startDate = map.get("startDate");
			String startOperator = map.get("startOperator");
			String endDate = map.get("endDate");
			String endOperator = map.get("endOperator");
			String dateType = map.get("dateType");
			OutInfo.Out("   amazon_order获取数据的时间段 : " + startDate + startOperator+" time "+endOperator +endDate + "\n", OrderRecord.amazonOrderPath);
			OutInfo.Out("   amazon_order_item获取数据的时间段 : "+ startDate + startOperator+" time "+endOperator +endDate + "\n", OrderRecord.amazonOrderPath);
			List<AmazonOrderModel> amazonOrderModels = query("select * from amazon_order where " +dateType + startOperator+"'"+startDate
					+ "' and "+dateType+endOperator+"'" + endDate + "'",AmazonOrderModel.class);
			
			List<AmazonOrderItemModel> amazonOrderItemModels = query("select aoi.* from amazon_order_item aoi inner join (select id from amazon_order where  " +dateType + startOperator+"'"+startDate
					+ "' and "+dateType+endOperator+"'" + endDate + "') ao where aoi.parent_id=ao.id",AmazonOrderItemModel.class);
			reslut = amazonService.synchronouAmazonOrders(amazonOrderModels, amazonOrderItemModels,OrderRecord.amazonOrderPath);
			OutInfo.Out("结束执行时间：" + new Date().toString() + "\n", OrderRecord.amazonOrderPath);
			synchronizationService.synchronization();
		} catch (Exception e) {
			// TODO: handle exception
			reslut.setDescription("服务器错误！");
			e.printStackTrace();
			return reslut;
		}
		return reslut;
	}
	
	@Override
	public OperationResult ebaySynchronou(Map<String, String> map) {
		// TODO Auto-generated method stub
		OperationResult reslut = new OperationResult();
		try {
			OutInfo.Out("开始执行时间：" + new Date().toString() + "\n", OrderRecord.ebayOrderPath);
			String startDate = map.get("startDate");
			String startOperator = map.get("startOperator");
			String endDate = map.get("endDate");
			String endOperator = map.get("endOperator");
			String dateType = map.get("dateType");
			OutInfo.Out("   ebay_order获取数据的时间段 : " + startDate + startOperator+" time "+endOperator +endDate + "\n", OrderRecord.lightOrderPath);
			OutInfo.Out("   ebay_order_item获取数据的时间段 : "+ startDate + startOperator+" time "+endOperator +endDate + "\n", OrderRecord.lightOrderPath);
			List<EbayOrderModel> ebayOrderModels = query("select * from ebay_order_full where "+dateType+startOperator+"'" + startDate
					+ "' and "+dateType+endOperator+"'" + endDate + "'", EbayOrderModel.class);
			List<EbayOrderItemModel> ebayOrderItemModels = query("select eofi.* from ebay_order_full_item eofi inner join (select eo.order_id from ebay_order_full eo where  "+dateType+startOperator+"'" + startDate
					+ "' and "+dateType+endOperator+"'" + endDate + "') eof where eofi.order_id=eof.order_id", EbayOrderItemModel.class);
			reslut = ebayService.synchronouEbayOrders(ebayOrderModels, ebayOrderItemModels,null);
			OutInfo.Out("结束执行时间：" + new Date().toString() + "\n", OrderRecord.lightOrderPath);
			synchronizationService.synchronization();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			reslut.setDescription("服务器出现错误！");
			return reslut;
		}
		return reslut;
	}
	@Override
	public OperationResult lightSynchronou(Map<String, String> map) {
		// TODO Auto-generated method stub
		OperationResult reslut = new OperationResult();
		try{
			OutInfo.Out("开始执行时间：" + new Date().toString() + "\n", OrderRecord.lightOrderPath);
			String startDate = map.get("startDate");
			String startOperator = map.get("startOperator");
			String endDate = map.get("endDate");
			String endOperator = map.get("endOperator");
			String dateType = map.get("dateType");
			OutInfo.Out("   light_order获取数据的时间段 : " + startDate + startOperator+" time "+endOperator +endDate + "\n", OrderRecord.lightOrderPath);
			OutInfo.Out("   light_order_item获取数据的时间段 : "+ startDate + startOperator+" time "+endOperator +endDate + "\n", OrderRecord.lightOrderPath);
			List<LightOrderModel> lightOrderModels = query("select * from light_order where "+dateType+startOperator+"'" + startDate
						+ "' and "+dateType+endOperator+"'" + endDate + "'", LightOrderModel.class);
			List<LightOrderItemModel> lightOrderItemModels = query("select loi.* from light_order_item loi inner join (select eo.entity_id from light_order eo where  "+dateType+startOperator+"'" + startDate
					+ "' and "+dateType+endOperator+"'" + endDate +"') eof where loi.parent_id=eof.entity_id and loi.sku not like 'B%' and loi.sku!=''", LightOrderItemModel.class);
			reslut = lightService.synchronouLightOrders(lightOrderModels, lightOrderItemModels, OrderRecord.lightOrderPath);
			OutInfo.Out("结束执行时间：" + new Date().toString() + "\n", OrderRecord.lightOrderPath);
			synchronizationService.synchronization();
		}catch (Exception e) {
			// TODO: handle exception
			reslut.setDescription("服务器错误！");
			e.printStackTrace();
			return reslut;
			
		}
		return reslut;
	}

	private <T> List<T> query(String sql,Class<T> cls) {
		DataSource dataSource = (DataSource) pac.getBean("dataSource");
		DataSourceTypeManager.set(DataSources.SLAVE);
		JdbcTemplate jt = new JdbcTemplate(dataSource);
		return jt.query(sql,new ResultSetExtractor<List<T>>(){
					@Override
					public List<T> extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						try {
							return DateToList.toListModel(rs,cls);
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
							return null;
						}
					}});
	}

	

	
}
