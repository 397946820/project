package com.it.ocs.synchronou.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.synchronou.dao.IEBayShippingServiceDetailsDao;
import com.it.ocs.synchronou.model.EBayShippingServiceDetailsModel;
import com.it.ocs.synchronou.model.EbayAccountModel;
import com.it.ocs.synchronou.model.ParseEbayDetailShippingLocationXMLModel;
import com.it.ocs.synchronou.model.ParseEbayDetailShippingServiceXMLModel;
import com.it.ocs.synchronou.model.RequestModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.IEBayShippingServiceDetailsService;
import com.it.ocs.synchronou.vo.ShippingServiceDetailsVO;

@Service
public class EBayShippingServiceDetailsService extends BaseService implements IEBayShippingServiceDetailsService {
	//运输服务
	private static final String SHIPPING_SERVICE_DETAILS = "ShippingServiceDetails";
	//运输服务的国家
	private static final String SHIPPING_LOCATIOM_DETAILS = "ShippingLocationDetails";
	
	private static final Logger log = Logger.getLogger(EBayShippingServiceDetailsService.class);
	@Autowired
	private IEBayShippingServiceDetailsDao shippingServiceDao;
	
	@Autowired
	private TemplateService templateService;

	@Autowired
	private BaseHttpsService baseHttpService;
	
	@Autowired
	private EbayAccountService ebayAccountService;

	public void syncEbayShippingServiceBySite(int siteId){
		ParseEbayDetailShippingServiceXMLModel parse = new ParseEbayDetailShippingServiceXMLModel(getEBayDetails(siteId,SHIPPING_SERVICE_DETAILS),"urn:ebay:apis:eBLBaseComponents");
		updateShippingService(parse.getResult(),siteId);
	}
	
	public void syncEbayShippingLocationBySite(int siteId){
		ParseEbayDetailShippingLocationXMLModel parse = new ParseEbayDetailShippingLocationXMLModel(getEBayDetails(siteId,SHIPPING_LOCATIOM_DETAILS),"urn:ebay:apis:eBLBaseComponents");
		updateShippingLocation(parse.getResult(),siteId);
	}
	
	/**
	 * 运输服务的运输位置更新
	 * @param result
	 * @param siteId
	 */
	private void updateShippingLocation(List<Map<String, Object>> result, int siteId) {
		for(Map<String, Object> ship :result){
			ship.put("siteId", siteId);
			if(shippingServiceDao.shippingLocationIsExist(ship) == 0){
				shippingServiceDao.addShipLocation(ship);
			}else{
				shippingServiceDao.updateShipLocation(ship);
			}
		}
		
	}

	public Document getEBayDetails(int siteId,String detailNameCodeType) {
		EbayAccountModel account = ebayAccountService.getAccountModelByName("uk.nm");
		//account = new EbayAccountModel();
		//account.setAccount("uk.nm");
		//account.setToken("AgAAAA**AQAAAA**aAAAAA**3vplWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGl4SpD5eFpwydj6x9nY+seQ**Xq8DAA**AAMAAA**iydAm7Kq16duV/Musk4kXJRQmu77ZJqV7oQ+dPkGGilARK6NLpQuu9x+kS+2j7CcOVtGZfPg4jla4w+JwBnrr1vVtzQeV7/VwKuynHuEK4nqhWPi/JZhZ6zSNM+NbbIbI/a3TgvFkP5fyS2YSIALc6OCXH46jiQ77l+Qf7q2AkR8feOgTjlXRybuvDPXEk0MjfuAxb+wryC4JqiRkd6Y7gPHUAllXUBqI8JqL0RlD+Ewr3Hvsl6S892wnqSzlEkmrZm9YcO5hOco8xoMUDovZfXMAzcl56WlE+yZ6ghlJXTmX6BLXmj8VXSWGguG62ICT2waID1eM+aazxoRFgvqhua+tPKTJysql2TDrj/Rh0kT7O4VS+dVxrfHpzeovv+9O1DYyhSrOJfmi/gzvxAqTQt9hqpSe04omCXT4I04IUk+ZKPt0fYmFxH3T2bkscBUyXsxdbakRVWYGCr7jA4iegCSXUMbj4KQ1BRuVVRvAX2a63qcIfMWu9nB9IC4PdSogI3dccd1N6VmLPW/AlQSsGoPTtM2t4L8X6Gu1oitB6mkhvBWQ3CYfz8AjuV1CT2P/VJnSDplkqrYxxN7JeZL1/xeIETcBJ3KxWbhFEta4ERue9EYmBZtHCJ3BEmURV65kPpVfp8AZSGAGiXzOFGT2aYPLKUOtVe/qs9zte2GYSCdmvZ0tXIpZmMDY+zwSxClZ0WP+itTKzkXnrJSuhOGPCo3yITjpj7uO1r/0uJKOLwZ2MmIKH+yIhB3dYDg56MI");
		account.setSiteId(String.valueOf(siteId));
		TemplateModel template = templateService.getTemplateContent("GeteBayDetails", "ebay"); 
		Map<String,String> xmlValue = new HashMap<>();
		xmlValue.put("dataType", detailNameCodeType);
		RequestModel request = new RequestModel(template,account,xmlValue);
		return baseHttpService.getPesponseXml(request.getUrl(), request.getRequestHead(), request.getRequestXML());
	}
	
	/**
	 * 运输服务更新
	 * @param result
	 * @param siteId
	 */
	private void updateShippingService(List<Map<String, Object>> result,int siteId) {
		for(Map<String, Object> ship :result){
			ship.put("siteId", siteId);
			String shippingId = ship.get("ShippingServiceID").toString();
			if(shippingServiceDao.isExist(shippingId) == 0){
				shippingServiceDao.addShipService(ship);
			}else{
				shippingServiceDao.updateShipService(ship);
			}
		}
	}


	@Override
	public OperationResult synchronouShippingServiceDetails() {
		syncEbayShippingServiceBySite(0);
		syncEbayShippingLocationBySite(0);
		syncEbayShippingServiceBySite(3);
		syncEbayShippingLocationBySite(3);
		syncEbayShippingServiceBySite(77);
		syncEbayShippingLocationBySite(77);
		syncEbayShippingServiceBySite(15);
		syncEbayShippingLocationBySite(15);
		syncEbayShippingServiceBySite(100);
		OperationResult or = new OperationResult();
		or.setDescription("同步成功！");
		return or;
	}


	@Override
	public ResponseResult<ShippingServiceDetailsVO> selectShippingServiceDetails(RequestParam param) {
		ResponseResult<ShippingServiceDetailsVO> result = new ResponseResult<>();
		List<EBayShippingServiceDetailsModel> eBayShippingServiceDetailsModels = shippingServiceDao.selectShippingServiceDetails(param.getParam(),param.getStartRow(), param.getEndRow());
		int total = shippingServiceDao.getTotal(param.getParam());
		List<ShippingServiceDetailsVO> serviceDetailsVOs = Lists.newArrayList();
		convertList(eBayShippingServiceDetailsModels, serviceDetailsVOs);
		
		result.setRows(serviceDetailsVOs);
		result.setTotal(total);
		return result;
	}
	

	private void convertList(List<EBayShippingServiceDetailsModel> source, final List<ShippingServiceDetailsVO> target) {
		CollectionUtil.each(source, new IAction<EBayShippingServiceDetailsModel>() {
			@Override
			public void excute(EBayShippingServiceDetailsModel obj) {
				ShippingServiceDetailsVO shippingServiceDetailsVO = new ShippingServiceDetailsVO();
				BeanUtils.copyProperties(obj, shippingServiceDetailsVO);
				target.add(shippingServiceDetailsVO);
			}
		});
	}


	@Override
	public OperationResult onOff(int id, int type) {
		EBayShippingServiceDetailsModel ship = shippingServiceDao.getShippingServiceById(id);
		String is = ship.getInternationalService();
		if(null == is || "false".endsWith(is)){
			ship.setInternationalService("1");
		}else{
			ship.setInternationalService("2");
		}
		//取消
		if(type == 0){
			shippingServiceDao.deleteShippingService(ship);
		//启用
		}else{
			shippingServiceDao.addOrUpdateShippingService(ship);
		}
		OperationResult or = new OperationResult();
		or.setDescription("操作成功");
		return or;
	}

}
