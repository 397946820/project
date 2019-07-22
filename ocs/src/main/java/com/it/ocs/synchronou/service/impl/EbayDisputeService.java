package com.it.ocs.synchronou.service.impl;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.synchronou.dao.IEbayDisputeDao;
import com.it.ocs.synchronou.model.EbayAccountModel;
import com.it.ocs.synchronou.model.EbayDisputeModel;
import com.it.ocs.synchronou.model.EbayUserCaseModel;
import com.it.ocs.synchronou.model.ParseDisputeInfoXMLModel;
import com.it.ocs.synchronou.model.ParseDisputeListXMLModel;
import com.it.ocs.synchronou.model.ParseUserCaseEBPXMLModel;
import com.it.ocs.synchronou.model.ParseUserCaseListXMLModel;
import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.RequestModel;
import com.it.ocs.synchronou.model.SaleSubItemModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.IEbayDisputeService;
import com.it.ocs.synchronou.util.UTCTimeUtils;
import com.it.ocs.synchronou.vo.EbayDisputelVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class EbayDisputeService implements IEbayDisputeService {
	
	@Autowired
	private IEbayDisputeDao iEbayDisputeDao;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private EbayAccountService ebayAccountService;
	
	@Autowired
	private BaseHttpsService baseHttpService;

	@Override
	public OperationResult syncEbayDisputeList() {
		TemplateModel template = templateService.getTemplateContent("getUserCases", "ebay");
		//获取账号，并且遍历
		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
		for(EbayAccountModel account:accounts){
			account.setSiteId("0");//设置站点为美国，返回英语信息
			syncUserCaseByAccount(account,template);
		}
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}
	
	private void syncDisputeByAccount(EbayAccountModel account, TemplateModel template) {
		//更新未关闭的纠纷数据
		updateUnCloseDispute(account);
		
		Map<String,String> xmlValueMap = new HashMap<>();
		xmlValueMap.put("pageCount", "200");
		xmlValueMap.put("pageNumber", "1");
		xmlValueMap.put("timeFrom", UTCTimeUtils.getUTCTimeStr(-300*24));
		xmlValueMap.put("timeTo", UTCTimeUtils.getUTCTimeStr(0));
		RequestModel requetModel = new RequestModel(template,account,xmlValueMap);
		Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(), requetModel.getRequestXML());
		ParseDisputeListXMLModel parse = new ParseDisputeListXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
		List<Map<String,Object>> parseResult = parse.getResult();
		saveOrUpdate(parseResult,account);
		
		//获取总页数全部获取
		int totalPage = parse.getPage();
		for(int i = 2;i<=totalPage;i++){
			xmlValueMap.put("pageNumber", String.valueOf(i));
			requetModel.setParam(xmlValueMap);
			Document doc1 = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(), requetModel.getRequestXML());
			ParseDisputeListXMLModel parse1 = new ParseDisputeListXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
			saveOrUpdate(parse1.getResult(),account);
		}
	}
	private void updateUnCloseDispute(EbayAccountModel account){
		List<EbayDisputeModel> disputeUnClose = iEbayDisputeDao.getDisputeUnClose(account.getAccount());
		TemplateModel template = templateService.getTemplateContent("GetDispute", "ebay");
		for(EbayDisputeModel dispute : disputeUnClose){
			syncDisputeInfo(account,template,dispute.getDisputeID(),dispute.getdId());
		}
	}
	private void saveOrUpdate(List<Map<String, Object>> parseResult, EbayAccountModel account) {
		TemplateModel template = templateService.getTemplateContent("GetDispute", "ebay");
		for(Map<String, Object> map : parseResult){
			String disputeID = map.get("DisputeID").toString();
			if(iEbayDisputeDao.isExistForDispute(disputeID) ==0){
				int id = iEbayDisputeDao.getDisputeId();
				map.put("id", id);
				map.put("account", account.getAccount());
				iEbayDisputeDao.insertDispute(map);
				//同步详情信息
				syncDisputeInfo(account,template,disputeID,id);
			}
		}
		
	}

	private void syncDisputeInfo(EbayAccountModel account,TemplateModel template, String disputeID,int id) {
		Map<String,String> xmlValueMap = new HashMap<>();
		xmlValueMap.put("disputeID", disputeID);
		RequestModel requetModel = new RequestModel(template,account,xmlValueMap);
		Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(), requetModel.getRequestXML());
		ParseDisputeInfoXMLModel parse = new ParseDisputeInfoXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
		List<Map<String,Object>> parseResult = parse.getResult();
		for(Map<String,Object> map : parseResult){
			map.put("parentId", id);
			if(iEbayDisputeDao.isExistForDisputeInfo(disputeID) ==0){
				iEbayDisputeDao.insertDisputeInfo(map);
			}else{
				iEbayDisputeDao.updateDisputeInfo(map);
			}
			//更新消息
			List<Map<String, Object>> disputeMessages = (List<Map<String, Object>>)map.get("DisputeMessage");
			for(Map<String, Object> dm : disputeMessages){
				String messageId = (String)dm.get("MessageID");
				if(iEbayDisputeDao.disputeMessageIsExist(messageId) == 0){
					dm.put("disputeID", disputeID);
					dm.put("parentId", id);
					iEbayDisputeDao.insetDisputeMessage(dm);
				}
			}
		}
	}

	@Override
	public ResponseResult<EbayUserCaseModel> ebayDisputeList(RequestParam param) {
		ResponseResult<EbayUserCaseModel> result = new ResponseResult<>();
		Map<String,Object> map = param.getParam();
		List<EbayUserCaseModel> disputelModels = iEbayDisputeDao.queryByPage(map, param.getStartRow(), param.getEndRow());
		int count = iEbayDisputeDao.count(map);		
		result.setRows(disputelModels);
		result.setTotal(count);
		return result;
	}
	
	private void convertList(List<EbayDisputeModel> source, final List<EbayDisputelVO> target) {
		CollectionUtil.each(source, new IAction<EbayDisputeModel>() {
			@Override
			public void excute(EbayDisputeModel obj) {
				EbayDisputelVO disputeVO = new EbayDisputelVO();
				BeanUtils.copyProperties(obj, disputeVO);
				target.add(disputeVO);
			}
		});
	}

	@Override
	public OperationResult remark(Map<String, Object> disputeModel) {
		int optCount = iEbayDisputeDao.remark(disputeModel);
		OperationResult or = new OperationResult();
		or.setData(optCount);
		return or;
	}

	@Override
	public OperationResult syncUpdate(Map<String, Object> disputeModel) {
		TemplateModel template = templateService.getTemplateContent("GetDispute", "ebay");
		TemplateModel templateEBP = templateService.getTemplateContent("getEBPCaseDetail", "ebay");
		String account = (String)disputeModel.get("account");
		EbayAccountModel accountModel = ebayAccountService.getAccountModelByName(account);
		
		//accountModel = new EbayAccountModel();
		//accountModel.setAccount("uk.nm");
		//accountModel.setToken("AgAAAA**AQAAAA**aAAAAA**3vplWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGl4SpD5eFpwydj6x9nY+seQ**Xq8DAA**AAMAAA**iydAm7Kq16duV/Musk4kXJRQmu77ZJqV7oQ+dPkGGilARK6NLpQuu9x+kS+2j7CcOVtGZfPg4jla4w+JwBnrr1vVtzQeV7/VwKuynHuEK4nqhWPi/JZhZ6zSNM+NbbIbI/a3TgvFkP5fyS2YSIALc6OCXH46jiQ77l+Qf7q2AkR8feOgTjlXRybuvDPXEk0MjfuAxb+wryC4JqiRkd6Y7gPHUAllXUBqI8JqL0RlD+Ewr3Hvsl6S892wnqSzlEkmrZm9YcO5hOco8xoMUDovZfXMAzcl56WlE+yZ6ghlJXTmX6BLXmj8VXSWGguG62ICT2waID1eM+aazxoRFgvqhua+tPKTJysql2TDrj/Rh0kT7O4VS+dVxrfHpzeovv+9O1DYyhSrOJfmi/gzvxAqTQt9hqpSe04omCXT4I04IUk+ZKPt0fYmFxH3T2bkscBUyXsxdbakRVWYGCr7jA4iegCSXUMbj4KQ1BRuVVRvAX2a63qcIfMWu9nB9IC4PdSogI3dccd1N6VmLPW/AlQSsGoPTtM2t4L8X6Gu1oitB6mkhvBWQ3CYfz8AjuV1CT2P/VJnSDplkqrYxxN7JeZL1/xeIETcBJ3KxWbhFEta4ERue9EYmBZtHCJ3BEmURV65kPpVfp8AZSGAGiXzOFGT2aYPLKUOtVe/qs9zte2GYSCdmvZ0tXIpZmMDY+zwSxClZ0WP+itTKzkXnrJSuhOGPCo3yITjpj7uO1r/0uJKOLwZ2MmIKH+yIhB3dYDg56MI");

		accountModel.setSiteId("0");
		int id =  (Integer)disputeModel.get("id");
		String caseId = (String)disputeModel.get("caseId");
		String caseType = (String)disputeModel.get("caseType");
		if(caseType.indexOf("EBP") > -1){
			Map<String,String> xmlValueMap = new HashMap<>();
			xmlValueMap.put("caseId", caseId);
			xmlValueMap.put("caseType", caseType);
			syncEBPCaseInfo(accountModel,templateEBP,xmlValueMap,id);
		}else{
			//同步详情信息
			syncDisputeInfo(accountModel,template,caseId,id);
		}
		OperationResult res = new OperationResult();
		res.setData("success");
		return res;
	}

	@Override
	public OperationResult getOrderTransInfo(String orderLineItemId) {
		OperationResult or = new OperationResult();
		SaleSubItemModel trans = iEbayDisputeDao.getOrderTransInfo(orderLineItemId);
		or.setData(trans);
		return or;
	}

	@Override
	public OperationResult answer(Map<String, Object> answerModel) {
		TemplateModel template = templateService.getTemplateContent("AddDisputeResponse", "ebay");
		String account = (String)answerModel.get("account");
		EbayAccountModel accountModel = ebayAccountService.getAccountModelByName(account);
		accountModel.setSiteId("0");
		Map<String,String> xmlValueMap = new HashMap<>();
		xmlValueMap.put("DisputeID", (String)answerModel.get("disputeID"));
		String disputeActivity = (String)answerModel.get("DisputeActivity");
		xmlValueMap.put("DisputeActivity", disputeActivity);
		if("SellerShippedItem".equals(disputeActivity)){
			StringBuffer sb = new StringBuffer();
			sb.append("<ShipmentTrackNumber>"+(String)answerModel.get("ShipmentTrackNumber")+"</ShipmentTrackNumber>");
			sb.append("<ShippingCarrierUsed>"+(String)answerModel.get("ShippingCarrierUsed")+"</ShippingCarrierUsed>");
			String shipTime = (String)answerModel.get("ShippingTime");
			shipTime = shipTime.replace(" ", "T");
			shipTime = shipTime+".000Z";
			sb.append("<ShippingTime>"+shipTime+"</ShippingTime>");
			xmlValueMap.put("transInfo", sb.toString());
		}else{
			xmlValueMap.put("transInfo", "");
		}
		xmlValueMap.put("MessageText", (String)answerModel.get("MessageText"));
		RequestModel requetModel = new RequestModel(template,accountModel,xmlValueMap);
		Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(), requetModel.getRequestXML());
		String message = ParseXMLModel.getRequstMessage(doc, "urn:ebay:apis:eBLBaseComponents");
		OperationResult or = new OperationResult();
		if("".equals(message)){
			syncUpdate(answerModel);
			or.setData("success");
		}else{
			or.setData(message);
		}
		
		return or;
	}

	@Override
	public OperationResult getOldDisputeMessage(String id) {
		OperationResult or = new OperationResult();
		List<EbayDisputeModel> messages = iEbayDisputeDao.getOldDisputeMessage(id);
		or.setData(messages);
		return or;
	}
	
	/**
	 * 获取用户纠纷信息
	 */
	public void syncUserCase(){
		//获取模板
		TemplateModel template = templateService.getTemplateContent("getUserCases", "ebay");
		//获取账号，并且遍历
		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
		for(EbayAccountModel account:accounts){
			account.setSiteId("0");//设置站点为美国，返回英语信息
			//account.setAccount("uk.nm");
			//account.setToken("AgAAAA**AQAAAA**aAAAAA**3vplWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGl4SpD5eFpwydj6x9nY+seQ**Xq8DAA**AAMAAA**iydAm7Kq16duV/Musk4kXJRQmu77ZJqV7oQ+dPkGGilARK6NLpQuu9x+kS+2j7CcOVtGZfPg4jla4w+JwBnrr1vVtzQeV7/VwKuynHuEK4nqhWPi/JZhZ6zSNM+NbbIbI/a3TgvFkP5fyS2YSIALc6OCXH46jiQ77l+Qf7q2AkR8feOgTjlXRybuvDPXEk0MjfuAxb+wryC4JqiRkd6Y7gPHUAllXUBqI8JqL0RlD+Ewr3Hvsl6S892wnqSzlEkmrZm9YcO5hOco8xoMUDovZfXMAzcl56WlE+yZ6ghlJXTmX6BLXmj8VXSWGguG62ICT2waID1eM+aazxoRFgvqhua+tPKTJysql2TDrj/Rh0kT7O4VS+dVxrfHpzeovv+9O1DYyhSrOJfmi/gzvxAqTQt9hqpSe04omCXT4I04IUk+ZKPt0fYmFxH3T2bkscBUyXsxdbakRVWYGCr7jA4iegCSXUMbj4KQ1BRuVVRvAX2a63qcIfMWu9nB9IC4PdSogI3dccd1N6VmLPW/AlQSsGoPTtM2t4L8X6Gu1oitB6mkhvBWQ3CYfz8AjuV1CT2P/VJnSDplkqrYxxN7JeZL1/xeIETcBJ3KxWbhFEta4ERue9EYmBZtHCJ3BEmURV65kPpVfp8AZSGAGiXzOFGT2aYPLKUOtVe/qs9zte2GYSCdmvZ0tXIpZmMDY+zwSxClZ0WP+itTKzkXnrJSuhOGPCo3yITjpj7uO1r/0uJKOLwZ2MmIKH+yIhB3dYDg56MI");
			syncUserCaseByAccount(account,template);
			//break;
		}
	}

	private void syncUserCaseByAccount(EbayAccountModel account, TemplateModel template) {
		Map<String,String> xmlValueMap = new HashMap<>();
		xmlValueMap.put("pageCount", "200");
		xmlValueMap.put("pageNumber", "1");
		//同步一年内纠纷
		xmlValueMap.put("timeFrom", UTCTimeUtils.getUTCTimeStr(-7*24));
		xmlValueMap.put("timeTo", UTCTimeUtils.getUTCTimeStr(0));
		RequestModel requetModel = new RequestModel(template,account,xmlValueMap);
		ParseUserCaseListXMLModel parse = paseAndSave(requetModel,account);
		
		//获取总页数全部获取
		int totalPage = parse.getPage();
		for(int i = 2;i<=totalPage;i++){
			xmlValueMap.put("pageNumber", String.valueOf(i));
			requetModel.setParam(xmlValueMap);
			paseAndSave(requetModel,account);
		}
		
	}
	private ParseUserCaseListXMLModel paseAndSave(RequestModel requetModel,EbayAccountModel account){
		Map<String,String> headMap = new HashMap<>();
		headMap.put("X-EBAY-SOA-SERVICE-NAME","ResolutionCaseManagementService");
		headMap.put("X-EBAY-SOA-OPERATION-NAME","getUserCases");
		headMap.put("X-EBAY-SOA-SERVICE-VERSION","1.3.0");
		headMap.put("X-EBAY-SOA-SECURITY-TOKEN",account.getToken());
		headMap.put("X-EBAY-SOA-REQUEST-DATA-FORMAT","XML");
		Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), headMap, requetModel.getRequestXML());
		ParseUserCaseListXMLModel parse = new ParseUserCaseListXMLModel(doc,"http://www.ebay.com/marketplace/resolution/v1/services");
		List<Map<String,Object>> parseResult = parse.getResult();
		saveOrUpdateUseCase(parseResult,account);
		return parse;
	}

	private void saveOrUpdateUseCase(List<Map<String, Object>> parseResult, EbayAccountModel account) {
		TemplateModel template = templateService.getTemplateContent("GetDispute", "ebay");
		TemplateModel templateEBP = templateService.getTemplateContent("getEBPCaseDetail", "ebay");
		for(Map<String,Object> map:parseResult){
			JSONObject json = JSONObject.fromObject(map.get("status"));
			Iterator<String> sIterator = json.keys();  
			while(sIterator.hasNext()){  
			    // 获得key  
			    String key = sIterator.next();  
			    if(key.indexOf("Status")>0){
			    	map.put("status", json.getString(key));
			    	break;
			    }
			} 
			Integer id = null;
			String caseId = (String)map.get("caseId_id");
			String type = (String)map.get("caseId_type");
			if(iEbayDisputeDao.countUserCase(map) == 0){
				id = iEbayDisputeDao.getUseCaseId();
				map.put("id", id);
				String dataAccount = (String)map.get("user_userId");
				if(!dataAccount.equals(account.getAccount())){
					String buyer = (String)map.get("otherParty_userId");
					map.put("user_userId", buyer);
					map.put("otherParty_userId", dataAccount);
				}
				iEbayDisputeDao.addUserCase(map);
			}else{
				Map<String,String> param = new HashMap<>();
				param.put("caseId", caseId);
				param.put("account", account.getAccount());
				id = iEbayDisputeDao.getUseCaseIdByAccountAndCaseId(param);
				iEbayDisputeDao.updateUserCase(map);
			}
			
			if(type.indexOf("EBP") > -1){
				Map<String,String> xmlValueMap = new HashMap<>();
				xmlValueMap.put("caseId", caseId);
				xmlValueMap.put("caseType", type);
				syncEBPCaseInfo(account,templateEBP,xmlValueMap,id);
			}else{
				//同步详情信息
				syncDisputeInfo(account,template,caseId,id);
			}
		}
	}
	private void syncEBPCaseInfo(EbayAccountModel account, TemplateModel templateEBP, Map<String,String> xmlValueMap, int id) {
		RequestModel requetModel = new RequestModel(templateEBP,account,xmlValueMap);
		Map<String,String> headMap = new HashMap<>();
		headMap.put("X-EBAY-SOA-SERVICE-NAME","ResolutionCaseManagementService");
		headMap.put("X-EBAY-SOA-OPERATION-NAME","getEBPCaseDetail");
		headMap.put("X-EBAY-SOA-SERVICE-VERSION","1.3.0");
		headMap.put("X-EBAY-SOA-SECURITY-TOKEN",account.getToken());
		headMap.put("X-EBAY-SOA-REQUEST-DATA-FORMAT","XML");
		Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), headMap, requetModel.getRequestXML());
		if(null == doc){
			return;
		}
		ParseUserCaseEBPXMLModel parse = new ParseUserCaseEBPXMLModel(doc, "http://www.ebay.com/marketplace/resolution/v1/services");
        List<Map<String, Object>> result = parse.getResult();
        for(Map<String, Object> map : result){
        	//保存数据
        	saveEBPCaseInfo(account,map,id);
        }
	}
	
	private void saveEBPCaseInfo(EbayAccountModel account,Map<String, Object> map,int id) {
		/*String caseId = (String)map.get("case_id");
		String account = (String)map.get("user_userId");
		Map<String,String> param = new HashMap<>();
		param.put("caseId", caseId);
		param.put("account", account);
		Integer id = this.iEbayDisputeDao.getUseCaseIdByAccountAndCaseId(param);*/
		map.put("parent_id", id);
		if(this.iEbayDisputeDao.userCaseEBPExist(id) == 0){
			this.iEbayDisputeDao.addUserCaseEBP(map);
		}else{
			this.iEbayDisputeDao.updateUserCaseEBP(map);
			
		}
		//summary
		Map<String, Object> summaryMap = (Map<String, Object>)map.get("caseSummary");
		JSONObject json = JSONObject.fromObject(summaryMap.get("status"));
		Iterator<String> sIterator = json.keys();  
		while(sIterator.hasNext()){  
		    // 获得key  
		    String key = sIterator.next();  
		    if(key.indexOf("Status")>0){
		    	summaryMap.put("status", json.getString(key));
		    	break;
		    }
		}
		//处理XML数据user下的userId下存放的是买家名称的问题
		String dataAccount = (String)summaryMap.get("user_userId");
		if(!dataAccount.equals(account.getAccount())){
			String buyer = (String)summaryMap.get("otherParty_userId");
			summaryMap.put("user_userId", buyer);
			summaryMap.put("otherParty_userId", dataAccount);
		}
		iEbayDisputeDao.updateUserCase(summaryMap);
		
		//responseHistory
		List<Map<String, Object>> responseHistoryList = (List<Map<String, Object>>)map.get("responseHistory");
		for(Map<String, Object> responseHistory:responseHistoryList){
			responseHistory.put("parent_id", id);
			if(iEbayDisputeDao.countResponseHistory(responseHistory) == 0){
				iEbayDisputeDao.addResponseHistory(responseHistory);
			}
		}
		
		//caseDocumentInfo
		List<Map<String, Object>> caseDocumentInfoList = (List<Map<String, Object>>)map.get("caseDocumentInfo");
		for(Map<String, Object> caseDocumentInfo:caseDocumentInfoList){
			caseDocumentInfo.put("parent_id", id);
			if(iEbayDisputeDao.countCaseDocumentInfo(caseDocumentInfo) == 0){
				iEbayDisputeDao.addCaseDocumentInfo(caseDocumentInfo);
			}
		}
		
		//appeal
		List<Map<String, Object>> appealList = (List<Map<String, Object>>)map.get("appeal");
		for(Map<String, Object> appeal:appealList){
			appeal.put("parent_id", id);
			if(iEbayDisputeDao.countAppeal(appeal) == 0){
				iEbayDisputeDao.addAppeal(appeal);
			}else{
				iEbayDisputeDao.updateAppeal(appeal);
			}
		}
		
		//moneyMovement
		List<Map<String, Object>> moneyMovementList = (List<Map<String, Object>>)map.get("moneyMovement");
		for(Map<String, Object> moneyMovement:moneyMovementList){
			moneyMovement.put("parent_id", id);
			if(iEbayDisputeDao.countMoneyMovement(moneyMovement) == 0){
				iEbayDisputeDao.addMoneyMovement(moneyMovement);
			}else{
				iEbayDisputeDao.updateMoneyMovement(moneyMovement);
			}
		}
	}
	
	@Override
	public List<Map<String, Object>> getAppealListByParentId(String parentId) {
		return iEbayDisputeDao.getAppealListByParentId(parentId);
	}

	@Override
	public List<Map<String, Object>> getMoneyMovementListByParentId(String parentId) {
		return iEbayDisputeDao.getMoneyMovementListByParentId(parentId);
	}

	@Override
	public List<Map<String, Object>> getResponseHistoryListByParentId(String parentId) {
		return iEbayDisputeDao.getResponseHistoryListByParentId(parentId);
	}

	
}
