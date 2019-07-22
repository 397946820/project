package com.it.ocs.synchronou.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.publication.service.inner.impl.EbayImageUploadService;
import com.it.ocs.synchronou.dao.IEbayMessageDao;
import com.it.ocs.synchronou.model.EbayAccountModel;
import com.it.ocs.synchronou.model.MemberMessageInfoModel;
import com.it.ocs.synchronou.model.MessageInfoModel;
import com.it.ocs.synchronou.model.ParseMemberMessageListXMLModel;
import com.it.ocs.synchronou.model.ParseMessageListXMLModel;
import com.it.ocs.synchronou.model.ParseMessageSummaryXMLModel;
import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.RequestModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.IEbayMessageService;
import com.it.ocs.synchronou.util.UTCTimeUtils;
import com.it.ocs.synchronou.vo.MemberMessageInfoVO;
import com.it.ocs.synchronou.vo.MessageInfoVO;
import com.it.ocs.upload.vo.FileVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class EbayMessageService implements IEbayMessageService{
	
	private static final Logger log = Logger.getLogger(EbayMessageService.class);
	
	@Autowired
	private IEbayMessageDao iEbayMessageDao;
	@Autowired
	private TemplateService templateService;
	@Autowired
	private EbayAccountService ebayAccountService;
	@Autowired
	private BaseHttpsService baseHttpService;
	@Autowired
	private EbayImageUploadService ebayImageUploadService;
	
	public void syncMessage(){
		syncEbayMemberMessgaeList();
		syncEbayMessgaeList();
	}
	
	@Override
	public OperationResult syncEbayMessgaeList() {
		//获取模板
		TemplateModel template = templateService.getTemplateContent("GetMyMessagesList", "ebay");
		//获取账号，并且遍历
		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
		for(EbayAccountModel account:accounts){
			account.setSiteId("201");//设置站点为美国，返回英语信息
			syncMessageByAccount(account,template);
		}
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}

	private void syncMessageByAccount(EbayAccountModel account, TemplateModel template) {
		Map<String,String> xmlValueMap = new HashMap<>();
		xmlValueMap.put("startTime", UTCTimeUtils.getUTCTimeStr(-24*2));
		xmlValueMap.put("endTime", UTCTimeUtils.getUTCTimeStr(0));
		//获取时间段内总条数
		TemplateModel summaryTemplate = templateService.getTemplateContent("GetMyMessagesSummary", "ebay");
		RequestModel summaryRequest = new RequestModel(summaryTemplate,account,xmlValueMap);
		Document summaryDoc = baseHttpService.getPesponseXml(summaryRequest.getUrl(), summaryRequest.getRequestHead(), summaryRequest.getRequestXML());
		ParseMessageSummaryXMLModel sumParse = new ParseMessageSummaryXMLModel(summaryDoc,"urn:ebay:apis:eBLBaseComponents");
		int pages = sumParse.getTotalPages();
		if(pages > 0){
			xmlValueMap.put("pageCount", "100");//分页是按照100条计数的，请勿修改此值
			for(int i= 1;i<=pages;i++){
				xmlValueMap.put("pageNumber", String.valueOf(i));
				RequestModel requetModel = new RequestModel(template,account,xmlValueMap);
				Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(), requetModel.getRequestXML());
				ParseMessageListXMLModel parse = new ParseMessageListXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
				List<Map<String,Object>> parseResult = parse.getResult();
				saveOrUpdate(parseResult,account);
			}
		}
	}

	private void saveOrUpdate(List<Map<String, Object>> parseResult,EbayAccountModel account) {
		if(null == parseResult){
			return;
		}
		//获取content
		TemplateModel template = templateService.getTemplateContent("GetMyMessages", "ebay");
		for(Map<String, Object> map : parseResult){
			String messageId = map.get("MessageID").toString();
			if(iEbayMessageDao.isExist(messageId)>0){
				iEbayMessageDao.updateData(map);
			}else{
				iEbayMessageDao.insertData(map);
				Map<String,String> xmlValueMap = new HashMap<>();
				xmlValueMap.put("messageId", messageId);
				RequestModel requetModel = new RequestModel(template,account,xmlValueMap);
				Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(), requetModel.getRequestXML());
				ParseMessageListXMLModel parse = new ParseMessageListXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
				List<Map<String,Object>> messages = parse.getResult();
				for(Map<String,Object> message:messages){
					iEbayMessageDao.updateMessageText(message);
				}
			}
		}
	}

	@Override
	public ResponseResult<MessageInfoVO> getList(RequestParam param) {
		ResponseResult<MessageInfoVO> result = new ResponseResult<>();
		Map<String,Object> map = param.getParam();
		List<MessageInfoModel> messageInfoModels = iEbayMessageDao.queryByPage(map, param.getStartRow(), param.getEndRow());
		List<MessageInfoVO> messageInfoVOs = new ArrayList<>();
		convertList(messageInfoModels, messageInfoVOs);
		int count = iEbayMessageDao.count(map);		
		result.setRows(messageInfoVOs);
		result.setTotal(count);
		return result;
	}
	private void convertList(List<MessageInfoModel> source, final List<MessageInfoVO> target) {
		CollectionUtil.each(source, new IAction<MessageInfoModel>() {
			@Override
			public void excute(MessageInfoModel obj) {
				MessageInfoVO messageInfoVO = new MessageInfoVO();
				BeanUtils.copyProperties(obj, messageInfoVO);
				target.add(messageInfoVO);
			}
		});
	}


	@Override
	public OperationResult remark(Map<String, Object> message) {
		int optCount = iEbayMessageDao.remark(message);
		OperationResult or = new OperationResult();
		or.setData(optCount);
		return or;
	}

	@Override
	public OperationResult syncEbayMemberMessgaeList() {
		//获取模板
		TemplateModel template = templateService.getTemplateContent("GetMemberMessages", "ebay");
		//获取账号，并且遍历
		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
		for(EbayAccountModel account:accounts){
			account.setSiteId("201");//设置站点为美国，返回英语信息
			syncMemberMessageByAccount(account,template);
		}
		
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}

	private void syncMemberMessageByAccount(EbayAccountModel account, TemplateModel template) {
		Map<String,String> xmlValueMap = new HashMap<>();
		xmlValueMap.put("startTime", UTCTimeUtils.getUTCTimeStr(-24*2));
		xmlValueMap.put("endTime", UTCTimeUtils.getUTCTimeStr(0));
		xmlValueMap.put("pageCount", "100");
		xmlValueMap.put("pageNumber", "1");
		RequestModel requetModel = new RequestModel(template,account,xmlValueMap);
		Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(), requetModel.getRequestXML());
		ParseMemberMessageListXMLModel parse = new ParseMemberMessageListXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
		saveOrUpdateMemberMessage(parse.getResult(),account);
		
		//获取总页数全部获取
		int totalPage = parse.getPage();
		for(int i = 2;i<=totalPage;i++){
			xmlValueMap.put("pageNumber", String.valueOf(i));
			requetModel.setParam(xmlValueMap);
			Document doc1 = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(), requetModel.getRequestXML());
			ParseMemberMessageListXMLModel parse1 = new ParseMemberMessageListXMLModel(doc1,"urn:ebay:apis:eBLBaseComponents");
			saveOrUpdateMemberMessage(parse1.getResult(),account);
		}
		
	}

	private void saveOrUpdateMemberMessage(List<Map<String, Object>> parseResult, EbayAccountModel account) {
		for(Map<String, Object> map : parseResult){
			String messageId = map.get("MessageID").toString();
			if(iEbayMessageDao.isExistForMember(messageId)>0){
				iEbayMessageDao.updateMemberData(map);
			}else{
				iEbayMessageDao.insertMemberData(map);
			}
			
		}
	}

	@Override
	public ResponseResult<MemberMessageInfoVO> memberMessagelist(RequestParam param) {
		ResponseResult<MemberMessageInfoVO> result = new ResponseResult<>();
		Map<String,Object> map = param.getParam();
		List<MemberMessageInfoModel> memberMessageInfoModels = iEbayMessageDao.memberMessageQueryByPage(map, param.getStartRow(), param.getEndRow());
		List<MemberMessageInfoVO> memberMessageInfoVOs = new ArrayList<>();
		convertMemberList(memberMessageInfoModels, memberMessageInfoVOs);
		int count = iEbayMessageDao.memberMessageCount(map);		
		result.setRows(memberMessageInfoVOs);
		result.setTotal(count);
		return result;
	}

	private void convertMemberList(List<MemberMessageInfoModel> source,List<MemberMessageInfoVO> target) {
		CollectionUtil.each(source, new IAction<MemberMessageInfoModel>() {
			@Override
			public void excute(MemberMessageInfoModel obj) {
				MemberMessageInfoVO memberMessageInfoVO = new MemberMessageInfoVO();
				BeanUtils.copyProperties(obj, memberMessageInfoVO);
				target.add(memberMessageInfoVO);
			}
		});
		
	}

	@Override
	public OperationResult memberRemark(Map<String, Object> messageModel) {
		int optCount = iEbayMessageDao.memberRemark(messageModel);
		OperationResult or = new OperationResult();
		or.setData(optCount);
		return or;
	}

	@Override
	public OperationResult getOldQuestion(String id) {
		OperationResult or = new OperationResult();
		List<MemberMessageInfoModel> messages = iEbayMessageDao.getOldQuestion(id);
		CollectionUtil.each(messages, new IAction<MemberMessageInfoModel>() {
			@Override
			public void excute(MemberMessageInfoModel model) {
				if(StringUtils.isNotBlank(model.getMessagemedia()) && !"Me".equals(model.getSenderID())) {
					Map<String, String> map  = (Map<String, String>) JSON.parse(model.getMessagemedia());
					if(StringUtils.isNotBlank(map.get("MediaURL"))) {
						model.setMessagemedia(map.get("MediaURL"));
					} else {
						model.setMessagemedia(null);
					}
				}
				if(!"Me".equals(model.getSenderID()) && null != model.getBody() && model.getBody().indexOf("@media")>= 0){
					model.setBody(findMessageBodyText(model.getMessageID()).getData().toString());
				}
			}
		});
		or.setData(messages);
		return or;
	}

	@Override
	public OperationResult memberAnswer(Map<String, Object> amswerModel) {
		
		Map<String,String> xmlValueMap = new HashMap<>();
		String recipientID = (String)amswerModel.get("recipientID");
		String senderID = (String)amswerModel.get("senderID");
		xmlValueMap.put("senderID", senderID);
		String itemID = (String)amswerModel.get("itemID");
		if(null != itemID && !"".equals(itemID)){
			xmlValueMap.put("itemIdXML", "<ItemID>"+itemID+"</ItemID>");
		}else{
			xmlValueMap.put("itemIdXML", "");
		}
		String answer = (String)amswerModel.get("answer");
		xmlValueMap.put("answer", answer);
		int isView = (Integer)amswerModel.get("isView");
		if(isView == 1){
			xmlValueMap.put("isView", "true");
		}else{
			xmlValueMap.put("isView", "false");
		}
		String messageId = (String)amswerModel.get("messageId");
		xmlValueMap.put("messageId", messageId);
		
		//图片信息
		Object imgStrObj = amswerModel.get("imgs");
		String media = "";
		if(null != imgStrObj){
			String imgs = imgStrObj.toString();
			if(!"".equals(imgs)){
				StringBuffer medias = new StringBuffer();
				String imgUrls[] = imgs.split(",");
				for(String ocsUrl : imgUrls){
					String imgId = ocsUrl.substring(ocsUrl.lastIndexOf("/")+1);
					if("".equals(media)){
						media = imgId;
					}else{
						media = media + ","+imgId;
					}
					//根据id获取图片的名称和链接
					FileVO image = iEbayMessageDao.getImgById(imgId);
					String ebayUrl = ebayImageUploadService.imageUploadEbay(image, recipientID);
					medias.append("<MessageMedia>");
					medias.append("<MediaName>"+image.getName()+"</MediaName>");
					medias.append("<MediaURL>"+ebayUrl+"</MediaURL>");
					medias.append("</MessageMedia>");
				}
				
				xmlValueMap.put("messageMedia", medias.toString());
			}else{
				xmlValueMap.put("messageMedia", "");
			}
		}else{
			xmlValueMap.put("messageMedia", "");
		}
		
		TemplateModel template = templateService.getTemplateContent("AddMemberMessageRTQ", "ebay");
		EbayAccountModel ebayAccountModel = ebayAccountService.getAccountModelByName(recipientID);
		
		ebayAccountModel.setSiteId("201");
		RequestModel requetModel = new RequestModel(template,ebayAccountModel,xmlValueMap);
		Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(), requetModel.getRequestXML());
		String message = ParseXMLModel.getRequstMessage(doc, "urn:ebay:apis:eBLBaseComponents");
		OperationResult or = new OperationResult();
		if("".equals(message)){
			String answerDate = UTCTimeUtils.getUTCTimeStr(0);
			answerDate = answerDate.replace("T", " ");
			answerDate = answerDate.replace("Z", "");
			amswerModel.put("answerDate", answerDate);
			amswerModel.put("media", media);
			//成功
			iEbayMessageDao.addAnswer(amswerModel);
			or.setData("success");
		}
		
		return or;
	}

	@Override
	public OperationResult countEmm(Map<String, Object> param) {
		OperationResult result = new OperationResult();
		int count = iEbayMessageDao.memberMessageCount(param);
		result.setData(count);
		return result;
	}

	@Override
	public OperationResult updateRead(String id, Integer read) {
		OperationResult result = new OperationResult();
		try {
			iEbayMessageDao.updateRead(id, read);
			result.setDescription("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setDescription("操作失败！");
			log.error(e);
		}
		return result;
	}

	@Override
	public MemberMessageInfoModel memberMessageById(Long id) {
		MemberMessageInfoModel  model = iEbayMessageDao.memberMessageById(id);
		return model;
	}
	
	/**
	*	主动发送站内消息
	*/
	@Override
	public OperationResult sendUseMessage(Map<String, Object> messages) {
		//AddMemberMessageAAQToPartner
		TemplateModel template = templateService.getTemplateContent("AddMemberMessageAAQToPartner", "ebay");
		String account = (String)messages.get("account");
		EbayAccountModel accountModel = ebayAccountService.getAccountModelByName(account);
		if(null == accountModel){
			return null;
		}
		accountModel.setSiteId("201");
		Map<String,String> xmlValueMap = new HashMap<>();
		for(Map.Entry<String, Object> entry:messages.entrySet()){
			xmlValueMap.put(entry.getKey(), String.valueOf(entry.getValue()));
		}
		String medias = (String)messages.get("media");
		if(null != medias && medias.contains("}")){
			JSONArray jsonArray = JSONArray.fromObject(medias);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < jsonArray.size(); i++) {
				sb.append("<MessageMedia>");
				JSONObject jb = jsonArray.getJSONObject(i);
				sb.append("<MediaName>"+jb.getString("name")+"</MediaName>");
				sb.append("<MediaURL>"+jb.getString("url")+"</MediaURL>");
				sb.append("</MessageMedia>");
			}
			xmlValueMap.put("media", sb.toString());
		}else{
			xmlValueMap.put("media", "");
			messages.put("media", "");
		}
		RequestModel requetModel = new RequestModel(template,accountModel,xmlValueMap);
		Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(), requetModel.getRequestXML());
		String message = ParseXMLModel.getRequstMessage(doc, "urn:ebay:apis:eBLBaseComponents");
		OperationResult or = new OperationResult();
		if(null == message||"".equals(message)){
			or.setData("success");
			//保存发送的信息
			iEbayMessageDao.addAskInfo(messages);
		}else{
			or.setData(message);
		}
		return or;
	}

	@Override
	@Transactional
	public OperationResult updateOcsReadStatus(Map<String, Object> map) {
		List<String> ids = Arrays.asList(map.get("ids").toString().split(","));
		String ocsStatus = map.get("ocsStatus").toString();
		iEbayMessageDao.updateOcsReadStatus(ids, ocsStatus);
		return new OperationResult();
	}

	@Override
	public OperationResult findMessageBodyText(String messageId) {
		String body = iEbayMessageDao.getMessageBody(messageId);
		org.jsoup.nodes.Document doc = Jsoup.parse(body);
		//UserInputtedText
		Element ele = doc.getElementById("UserInputtedText");
		OperationResult or = new OperationResult();
		or.setData(ele.html());
		return or;
	}
}
