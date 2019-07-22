package com.it.ocs.cal.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.cal.dao.IProductSaleReportDao;
import com.it.ocs.cal.model.SkuSaleCountExportModel;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.SaleCountReportVO;
import com.it.ocs.excel.service.AExcelExport;
import com.it.ocs.synchronou.util.ValidationUtil;

@Service("skuSaleCountExport")
public class SkuSaleCountExportService extends AExcelExport{
	
	@Autowired
	private IProductSaleReportDao iProductSaleReportDao;
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(ValidationUtil.isNullOrEmpty(startTime) || ValidationUtil.isNullOrEmpty(endTime)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar ca = Calendar.getInstance();
			endTime = df.format(ca.getTime());
			ca.add(Calendar.MONTH, -1);
			startTime = df.format(ca.getTime());
		}
		
		Map<String,Object> map = new HashMap<>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<SaleCountReportVO> skuCount = iProductSaleReportDao.queryByPage(map);
		//获取数据汇总
		Double platform = 0d;
		Double amazon = 0d;
		Double ebay = 0d;
		Double light = 0d;
		for(SaleCountReportVO skuData : skuCount){
			amazon = amazon + Double.parseDouble(skuData.getAmazonPrice());
			ebay = ebay + Double.parseDouble(skuData.getEbayPrice());
			light = light + Double.parseDouble(skuData.getLightPrice());
		}
		platform = amazon + ebay + light;
		List<Map<String, Object>> returnData = new ArrayList<>();
		for(SaleCountReportVO skuData : skuCount){
			Map<String, Object> obj = new HashMap<>();
			Double amazonPrice = Double.parseDouble(skuData.getAmazonPrice());
			Double ebayPrice =Double.parseDouble(skuData.getEbayPrice());
		    Double lightPrice=Double.parseDouble(skuData.getLightPrice());
			Double d = amazonPrice + ebayPrice + lightPrice;
			obj.put("FIELD",skuData.getField());
			obj.put("AMAZONQTY",skuData.getAmazonQty());
			obj.put("AMAZONPRICE",skuData.getAmazonPrice());
			obj.put("AMAZONPEC", Utils.getPercent(amazonPrice, amazon));
			obj.put("EBAYQTY",skuData.getEbayQty());
			obj.put("EBAYPRICE",skuData.getEbayPrice());
			obj.put("EBAYPEC", Utils.getPercent(ebayPrice, ebay));
			obj.put("LIGHTQTY",skuData.getLightQty());
			obj.put("LIGHTPRICE",skuData.getLightPrice());
			obj.put("LIGHTPEC", Utils.getPercent(lightPrice, light));
			obj.put("TOTAL", String.valueOf(d));
			obj.put("TOTALPEC", Utils.getPercent(d, platform));
			returnData.add(obj);
		}
		return returnData;
	}
	
	
	@Override
	protected void init(HttpServletRequest request) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(ValidationUtil.isNullOrEmpty(startTime) || ValidationUtil.isNullOrEmpty(endTime)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar ca = Calendar.getInstance();
			endTime = df.format(ca.getTime());
			ca.add(Calendar.MONTH, -1);
			startTime = df.format(ca.getTime());
		}
		super.initModel(SkuSaleCountExportModel.class, "类目统计"+startTime+"-"+endTime+".xlsx");
		
	}

}
