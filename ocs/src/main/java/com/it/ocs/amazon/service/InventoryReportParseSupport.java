package com.it.ocs.amazon.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.amazon.dao.IAmazonReportDao;
import com.it.ocs.amazon.model.ColumnData;
import com.it.ocs.amazon.model.ReportRequestListModel;

/**
 * Amazon FBA Inventory 
 * @author chenyong
 *
 */
@Service
public class InventoryReportParseSupport {
	private final static Logger log = Logger.getLogger(InventoryReportParseSupport.class);
	private final static String sites[] = {"amazon.ca","amazon.jp","amazon.es","amazon.it","amazon.fr","amazon.co.uk","amazon.com","amazon.de","amazon.com.au"};
	
	@Autowired
	private IAmazonReportDao iAmazonReportDao;
	
	@Autowired
	private ReportDataSaveSupport reportDataSaveSupport;
	
	/**
	 * 解析启动的task方法
	 */
	public void paseInventoryReport(){
		
		for(String site : sites){
			//解析FBAInventory
			parseFBAInventoryReportData(site);
			//解析 Inventory Reserved
			parseInventoryReservedReportData(site);
		}
		
	}
	
	/**
	 * InventoryReserved
	 * @param site
	 */
	private void parseInventoryReservedReportData(String site) {
		Map<String,Object> map = new HashMap<>();
		map.put("site", site);
		map.put("reportType", reportDataSaveSupport._GET_RESERVED_INVENTORY_DATA_);
		List<ReportRequestListModel> inventoryReportList = iAmazonReportDao.getParseReport(map);
		for(ReportRequestListModel report : inventoryReportList){
			parseInventoryReservedReport(report);
		}
		
	}
	/**
	 * 解析 InventoryReserved
	 * @param report
	 */
	private void parseInventoryReservedReport(ReportRequestListModel reportMode) {
		//清除此报表数据
		iAmazonReportDao.deleteInventoryReservedReportDataById(reportMode.getReportGetId());
		//解析数据
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(reportMode.getFilePathName());
			isr= new InputStreamReader(fis, "UTF-8");
	        br = new BufferedReader(isr);
	        String line="";
	        String title[] = null;
	        String data[] = null;
	        boolean isTitle = true;
	        List<String[]> reportData = new ArrayList<>();
	        List<ColumnData> columnSet = reportDataSaveSupport.getColumnData(reportMode.getSite(),reportDataSaveSupport._GET_RESERVED_INVENTORY_DATA_);
	        
	        //计数，避免同一个report被多次解析
	        int count = 0;
	        while ((line=br.readLine())!=null) {
	        	data = line.split("\t");
	        	if(isTitle){
        			title = data;
        			isTitle = false;
        		}else{
        			count++;
        			reportData.add(data);
        			if(reportData.size()>0&&reportData.size()%100==0){
        				reportDataSaveSupport.saveInventoryReservedData(reportData,title,columnSet,reportMode);
        				reportData.clear();
        			}
        		}
	        }

	        if(reportData.size()> 0){
	        	//最后数据不足100条，数据处理
		        reportDataSaveSupport.saveInventoryReservedData(reportData,title,columnSet,reportMode);
	        }
	        //验证计数与数据库数量是否匹配
	        if(iAmazonReportDao.countThisInventoryReservedReportData(reportMode.getReportGetId()) > count){
	        	iAmazonReportDao.deleteInventoryReservedReportDataById(reportMode.getReportGetId());
	        }else{
	        	iAmazonReportDao.updateParseStatus(reportMode);
	        }
	        
		} catch (Exception e) {
			iAmazonReportDao.deleteInventoryReservedReportDataById(reportMode.getReportGetId());
			log.info("解析订单报表数据失败",e);
		}finally {
			if(null !=br){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != isr){
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	        if(null != fis){
	        	try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
		
	}

	/**
	 * FBA库存数据
	 * @param site
	 */
	private void parseFBAInventoryReportData(String site) {
		Map<String,Object> map = new HashMap<>();
		map.put("site", site);
		map.put("reportType", reportDataSaveSupport._GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_);
		List<ReportRequestListModel> inventoryReportList = iAmazonReportDao.getParseReport(map);
		for(ReportRequestListModel report : inventoryReportList){
			parseInventoryReport(report);
		}
		
	}


	/**
	 * 解析FBA库存数据
	 * @param reportMode
	 */
	private void parseInventoryReport(ReportRequestListModel reportMode) {
		//清除此报表数据
		iAmazonReportDao.deleteInventoryReportDataById(reportMode.getReportGetId());
		//解析数据
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(reportMode.getFilePathName());
			isr= new InputStreamReader(fis, "UTF-8");
	        br = new BufferedReader(isr);
	        String line="";
	        String title[] = null;
	        String data[] = null;
	        boolean isTitle = true;
	        List<String[]> reportData = new ArrayList<>();
	        List<ColumnData> columnSet = reportDataSaveSupport.getColumnData(reportMode.getSite(),reportDataSaveSupport._GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_);
	        
	        //计数，避免同一个report被多次解析
	        int count = 0;
	        while ((line=br.readLine())!=null) {
	        	data = line.split("\t");
	        	if(isTitle){
        			title = data;
        			isTitle = false;
        		}else{
        			count++;
        			reportData.add(data);
        			if(reportData.size()>0&&reportData.size()%100==0){
        				reportDataSaveSupport.saveInventoryData(reportData,title,columnSet,reportMode);
        				reportData.clear();
        			}
        		}
	        }

	        if(reportData.size()> 0){
	        	//最后数据不足100条，数据处理
		        reportDataSaveSupport.saveInventoryData(reportData,title,columnSet,reportMode);
	        }
	        //验证计数与数据库数量是否匹配
	        if(iAmazonReportDao.countThisInventoryReportData(reportMode.getReportGetId()) > count){
	        	iAmazonReportDao.deleteInventoryReportDataById(reportMode.getReportGetId());
	        }else{
	        	iAmazonReportDao.updateParseStatus(reportMode);
	        }
	        
		} catch (Exception e) {
			iAmazonReportDao.deleteInventoryReportDataById(reportMode.getReportGetId());
			log.info("解析订单报表数据失败",e);
		}finally {
			if(null !=br){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != isr){
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	        if(null != fis){
	        	try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
		
	}
	
	/**
	 * Spring Schedule: 刷新亚马逊FBA库存报表UK站点
	 * <p>
	 * 因业务需要，亚马逊UK下的FBA库存是基于DE请求列表产生的，其解析保存的数据也是DE（"amazon.de"）。</br>
	 * 该Task的作用就是，定时扫描FBA库存数据表，将本属于UK的数据的站点更新为"amazon.co.uk"。
	 * </p>
	 */
	public void refreshFBAInventoryUKSiste() {
		this.iAmazonReportDao.refreshReportUKSiste();
		this.iAmazonReportDao.refreshReservedUKSiste();
	}
}