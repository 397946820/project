package com.it.ocs.publication.service.inner.impl;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.publication.model.ParseImageUploadResultXMLModel;
import com.it.ocs.synchronou.model.EbayAccountModel;
import com.it.ocs.synchronou.model.RequestModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.impl.BaseHttpsService;
import com.it.ocs.synchronou.service.impl.EbayAccountService;
import com.it.ocs.synchronou.service.impl.TemplateService;
import com.it.ocs.upload.dao.IUploadDao;
import com.it.ocs.upload.vo.FileVO;

@Service
public class EbayImageUploadService {
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private EbayAccountService ebayAccountService;
	
	@Autowired
	private BaseHttpsService baseHttpService;
	
	@Autowired
	private IUploadDao iUploadDao;
	
	public String imageUploadEbay(FileVO image, String ebayAccount) {
		String serverUrl = image.getServerUrl();
		if(null != serverUrl && !"".equals(serverUrl)){
			EbayAccountModel account = ebayAccountService.getAccountModelByName(ebayAccount);
			account.setSiteId("0");
			TemplateModel  template = templateService.getTemplateContent("UploadSiteHostedPictures", "ebay");
			Map<String,String> xmlValueMap = new HashMap<>();
			xmlValueMap.put("name", image.getName());
			xmlValueMap.put("url", serverUrl);
			RequestModel requetModel = new RequestModel(template, account, xmlValueMap);
			Document doc = baseHttpService.getPesponseXml(requetModel.getUrl(), requetModel.getRequestHead(),requetModel.getRequestXML());
			ParseImageUploadResultXMLModel parse = new ParseImageUploadResultXMLModel(doc, "urn:ebay:apis:eBLBaseComponents");
			Map<String,String> map = parse.getEbayUrlInfo();
			String url = map.get("url");
			if(!"".equals(url)){
				image.setEbayUrl(url);
				image.setEbayReturnInfo(map.get("xml"));
				iUploadDao.updateImageInfo(image);
				return url;
			}
			
		}
		return null;
	}

}
