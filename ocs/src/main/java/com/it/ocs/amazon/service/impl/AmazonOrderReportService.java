package com.it.ocs.amazon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.amazon.dao.IAmazonReportDao;
import com.it.ocs.amazon.model.OrderReportVO;
import com.it.ocs.amazon.service.IAmazonOrderReportService;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.vo.ComboBoxVO;

@Service
public class AmazonOrderReportService implements IAmazonOrderReportService {
	
	@Autowired
	private IAmazonReportDao iAmazonReportDao;
	
	@Override
	public ResponseResult<OrderReportVO> findOrderReportList(RequestParam param) {
		Map<String,Object> map = param.getParam();
		int count = iAmazonReportDao.countOrderReport(map);
		ResponseResult<OrderReportVO> result = new ResponseResult<>();
		result.setRows(iAmazonReportDao.queryOrderReportByPage(map,param.getStartRow(),param.getEndRow()));
		result.setTotal(count);
		result.setFooter(this.getTotalFooter(map));
		return result;
	}

	private List getTotalFooter(Map<String, Object> map) {
		List<Map<String,String>> footer = new ArrayList<>();
		String site = map.get("marketplace").toString();
		List<Map<String,Object>> data = new ArrayList<>();
				
		//合计头信息
		if(null == site || "".equals(site)){
			//统计所有国家
			data = iAmazonReportDao.getTotalByContry(map);
			
		}else{
			//统计单个国家
			data = iAmazonReportDao.getTotalByType(map);
		}
		footer = this.formatData(data);
		return footer;
	}

	private List<Map<String, String>> formatData(List<Map<String, Object>> data) {
		String[] title = {"marketplace","quantity","productSales","shippingCredits","giftWrapCredits",
				  "promotionalRebates","salesTaxCollected","sellingFees","fbaFees",
				  "otherTransactionFees","other","total"};
		List<Map<String, String>> list = new ArrayList<>();
		for(Map<String, Object> map : data){
			Map<String, String> row = new HashMap<>();
			for(int i= 0;i<title.length;i++){
				Object obj = map.get(title[i].toUpperCase());
				if(null == obj){
					obj = 0;
				}
				row.put(title[i], String.valueOf(obj));
			}
			list.add(row);
		}
		return list;
	}
	

	
	@Override
	public List<ComboBoxVO> getOrderTypeBySite(String site) {
		return iAmazonReportDao.getOrderTypeBySite(site);
	}

}
