package com.it.ocs.task.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

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
import com.it.ocs.task.model.StateTableModel;
import com.it.ocs.task.record.OrderRecord;
import com.it.ocs.task.service.IAmazonOrderService;
import com.it.ocs.task.service.IEbayOrderService;
import com.it.ocs.task.service.IInternalPerformTask;
import com.it.ocs.task.service.ILightOrderService;
import com.it.ocs.task.service.IMAmazonOrderItemService;
import com.it.ocs.task.service.IMAmazonOrderService;
import com.it.ocs.task.service.IMEBayOrderItemService;
import com.it.ocs.task.service.IMEBayOrderService;
import com.it.ocs.task.service.IMLightOrderItemService;
import com.it.ocs.task.service.IMLightOrderService;
import com.it.ocs.task.service.IStateTableService;
import com.it.ocs.task.util.DateToList;
import com.it.ocs.task.util.OutInfo;
@Service
public class InternalPerformTask implements IInternalPerformTask{
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
	private IStateTableService stateTableService;
	@Autowired
	private ProjectApplicationContext pac;
	
	@Override
	public void teskMain() {
		StateTableModel stateTableModel = stateTableService.selectStateTableById(1L);
		
		if(stateTableModel.getState().equals("end")){
			stateTableModel.setState("synchronou");
			stateTableService.updateStateTableById(stateTableModel);
			taskMainPerform();
			stateTableModel.setState("end");
			stateTableService.updateStateTableById(stateTableModel);
			
		}
		
	}
	
	public void taskMainPerform(){
		
		OutInfo.Out("开始执行时间：" + new Date().toString() + "\n", OrderRecord.path);
		try {
			String amazonStartT = amazonService.selectMaxCreatedAt();
			String ebayStartT = ebayService.selectMaxLastFetchTime();
			String lightStartT = lightService.selectMaxCreatedAt();
			DataSourceTypeManager.set(DataSources.SLAVE);
			String amazonEndT = mAmazonService.selectMaxCreatedAt();
			String ebayEndT = mEbayService.selectMaxLastFetchTime();
			String lightEndT = mLightService.selectMaxCreatedAt();

			if (amazonStartT != null) {
				OutInfo.Out("   amazon_order获取数据的时间段 : " + amazonStartT + "< time >=" + amazonEndT + "\n", OrderRecord.path);
				OutInfo.Out("   amazon_order_item获取数据的时间段 : " + amazonStartT + "< time >=" + amazonEndT + "\n", OrderRecord.path);

				List<AmazonOrderModel> amazonOrderModels = query("select * from amazon_order where updated_at>'" + amazonStartT
						+ "' and updated_at<='" + amazonEndT + "'",AmazonOrderModel.class);
				/*List<AmazonOrderModel> amazonOrderModels = query("select * from amazon_order where updated_at>'" + "2017-07-30 00:00:00"
						+ "' and updated_at<='" + "2017-07-31 00:00:00" + "'",AmazonOrderModel.class);*/
				List<AmazonOrderItemModel> amazonOrderItemModels = query("select aoi.* from amazon_order_item aoi inner join (select id from amazon_order where   updated_at>'"
						+ amazonStartT + "' and updated_at<='" + amazonEndT
						+ "') ao where aoi.parent_id=ao.id",AmazonOrderItemModel.class);
				/*List<AmazonOrderItemModel> amazonOrderItemModels = query("select aoi.* from amazon_order_item aoi inner join (select id from amazon_order where   updated_at>'"
						+ "2017-07-30 00:00:00" + "' and updated_at<='" + "2017-07-31 00:00:00"
						+ "') ao where aoi.parent_id=ao.id",AmazonOrderItemModel.class);*/
				amazonService.synchronouAmazonOrders(amazonOrderModels, amazonOrderItemModels,null);

			} else {

				
			}
			if (lightStartT != null) {

				OutInfo.Out("   light_order获取数据的时间段 : " + lightStartT + "< time >=" + lightEndT + "\n", OrderRecord.path);
				OutInfo.Out("   light_order_item获取数据的时间段 : " + lightStartT + "< time >=" + lightEndT + "\n", OrderRecord.path);
				
				List<LightOrderModel> lightOrderModels = query("select * from light_order where updated_at>'" + lightStartT
						+ "'and updated_at<='" + lightEndT + "'", LightOrderModel.class);
				List<LightOrderItemModel> lightOrderItemModels = query("select loi.* from light_order_item loi inner join (select eo.entity_id from light_order eo where  light_updated_at>'"+lightStartT+"'and light_updated_at<='"+lightEndT+"') eof where loi.parent_id=eof.entity_id and loi.sku not like 'B%' and loi.sku!=''", LightOrderItemModel.class);
				lightService.synchronouLightOrders(lightOrderModels, lightOrderItemModels,null);

			} else {

				
			}
			if (ebayStartT != null) {

				OutInfo.Out("   ebay_order获取数据的时间段 : " + ebayStartT + "< time >=" + ebayEndT + "\n", OrderRecord.path);
				OutInfo.Out("   ebay_order_item获取数据的时间段 : " + ebayStartT + "< time >=" + ebayEndT + "\n", OrderRecord.path);
				List<EbayOrderModel> ebayOrderModels = query("select * from ebay_order_full where last_modified_time>'" + ebayStartT
						+ "' and last_modified_time<='" + ebayEndT + "'", EbayOrderModel.class);
				List<EbayOrderItemModel> ebayOrderItemModels = query("select eofi.* from ebay_order_full_item eofi inner join (select eo.order_id from ebay_order_full eo where  last_modified_time>'"
								+ ebayStartT + "' and last_modified_time<='" + ebayEndT
								+ "') eof where eofi.order_id=eof.order_id", EbayOrderItemModel.class);
				ebayService.synchronouEbayOrders(ebayOrderModels, ebayOrderItemModels,null);

			} else {

				
			}

		} catch (Exception e) {
			// TODO: handle exception
			DataSourceTypeManager.set(DataSources.MASTER);
			StateTableModel stateTableModel = new StateTableModel();
			stateTableModel.setId(1L);
			stateTableModel.setState("end");
			stateTableService.updateStateTableById(stateTableModel);
			OutInfo.Out(e.toString() + "\n", OrderRecord.path);
			e.printStackTrace();
		}

		OutInfo.Out("执行结束时间：" + new Date().toString() + "\n", OrderRecord.path);

		
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
