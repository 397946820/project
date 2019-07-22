package com.it.ocs.eda.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.eda.dao.IEDADao;
import com.it.ocs.eda.model.EastShipExportModel;
import com.it.ocs.eda.vo.EDAOrderVO;
import com.it.ocs.excel.service.AExcelExport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("orderShippingEastByConditionExport")
public class OrderShippingEastByConditionExport extends AExcelExport{
	@Autowired
	private IEDADao iEDADao;

	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		Map<String,Object> param = new HashMap<>();
		param.put("edaPlatformOrderId" , request.getParameter("edaPlatformOrderId"));
		param.put("orderId" , request.getParameter("orderId"));
		
		//转换时间为毫秒数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStart = request.getParameter("timeStart");
		String timeEnd = request.getParameter("timeEnd");
		try {
			// 00:00:00
			Date date = null;
			if(!"".equals(timeStart)){
				timeStart = timeStart + " 00:00:00";
				date = sdf.parse(timeStart);
				param.put("timeStart", date.getTime());
			}else{
				param.put("timeStart", null);
			}
			if(!"".equals(timeEnd)){
				timeEnd = timeEnd + " 00:00:00";
				date = sdf.parse(timeEnd);
				param.put("timeEnd", date.getTime()+24*60*60*1000L);
			}else{
				param.put("timeEnd", null);
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<EDAOrderVO> list = iEDADao.getOrderShippingEastByCondition(param);
		 List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		for(EDAOrderVO eda :list){
			String skuInfo = eda.getSkuInfo();
			if(null != skuInfo && !"".equals(skuInfo)&&skuInfo.indexOf("channelSku")> -1){
				JSONArray items = JSONArray.fromObject(skuInfo);
				for(int i=0;i<items.size();i++){
					JSONObject item = items.getJSONObject(i);
					Map<String,Object>  map = new HashMap<>();
					map.put("PLATFORM", eda.getPlatform());
					map.put("ORDERID", eda.getOrderId());
					map.put("OCSEDAORDERID", eda.getEdaPlatformOrderId());
					map.put("EDAORDERID", eda.getEdaOrderId());
					map.put("EDAACCOUNT", eda.getEdaAccount());
					map.put("SKU", item.getString("channelSku"));
					map.put("QTY", item.getInt("quantity"));
					map.put("CREATEDATE", eda.getEdaCreateDate());
					returnList.add(map);
				}
			}
		}
		return returnList;
	}

	@Override
	protected void init(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		super.initModel(EastShipExportModel.class, "EDA发货单按照条件导出信息-" + dateStr.replace(":", "-") + ".xlsx");
		
	}
}
