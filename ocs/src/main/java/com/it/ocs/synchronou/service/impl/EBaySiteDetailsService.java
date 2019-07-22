package com.it.ocs.synchronou.service.impl;

import java.util.ArrayList;
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
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.synchronou.dao.IEBaySiteDetailsDao;
import com.it.ocs.synchronou.model.EBayCategoryModel;
import com.it.ocs.synchronou.model.EBaySiteDetailsModel;
import com.it.ocs.synchronou.service.IEBaySiteDetailsService;
import com.it.ocs.synchronou.util.SiteDetailsHttps;
import com.it.ocs.synchronou.vo.CategoryVO;
import com.it.ocs.synchronou.vo.SiteDetailsVO;

import net.sf.json.JSONArray;
@Service
public class EBaySiteDetailsService extends BaseService implements IEBaySiteDetailsService {
    private static final Logger log = Logger.getLogger(EBaySiteDetailsService.class);
	@Autowired
	private IEBaySiteDetailsDao siteDetailsDao;
	@Override
	public OperationResult synchronouSiteDetails() {
		SiteDetailsHttps detailsHttps = new SiteDetailsHttps();
		OperationResult result = new OperationResult();
		String url = "https://api.ebay.com/ws/api.dll";
		Map<String, String> map = new HashMap<>();
		map.put("X-EBAY-API-SITEID", "0");
		map.put("X-EBAY-API-COMPATIBILITY-LEVEL", "967");
		map.put("X-EBAY-API-CALL-NAME", "GeteBayDetails");
		String requestBodyXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
								+"<GeteBayDetailsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">"
								+"<RequesterCredentials>"
								+"<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**Mfb+Vw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEmYSiC5SDpQ2dj6x9nY+seQ**q3YDAA**AAMAAA**a+lc7JZgzUBlqZRxivZzjyionMPWq2fGCNHLxQ923k/f6OqqKAIEfj5EmVXCI6QxJrP2pJqMGMgTcp2Yz5O87GGdHS9MiZHt5AxLJ2skN56kAvdtJQlf0q+3Fft1SfWJgE3wdkrm+m2MAU7vKndoLAdTrFv6h2A4UXNUc2Xi3rV1RON4pxxLLxp79NBt2rpx76fC/5A+dTlW/VVFZ3BCRsJ7HC1yUB1DM6Vpb+DGHy+TW4Yp0wCjJQH7V4peHhXKf2W8AK83LC1CqibdkWXvPPOhqzHssfQg6O+gK11s6WHJsee+OxKspS0dOYq9PUEoFgpURwL7teii53PIvJLWNBWDpv2Bxh2UYKKIlc1IkqjcWYVVwa6yalvv0pRBbcRR4ruzOjpUwrCWvV58PyAN7pUlwHabyHHoo5Qfps/QQYTP7Ac6UojJgA+UazSWuX5EwjULppBPPUzMFHdnSV1jobfUOOXHFcYd/Pw/xFZ/NahsygwsGAELZI7hGm/bIYS6pFoT1HNOL4uutBMNECy7fxQiFbiXNYZLLINRqCFtsiBUHa4Dniwss42CoRQLnwyXKfEuPdS9ZTMFnRBKu9dTQHs1P/zLkWeAP8L0mZAVjNSX0pSEVh4TvokcRUTJsAQlEViSCP8ZbJ+P6NoPDe4XRoyPB53FxJirq61Z2Bqe5X9VK4LReb5OfcZGN1MnhuhZ581CcbaLwwWQWynT0YO8pDIiaNFel9WlzhQ8zrq4fEjODDqFU2Bo9MRTs5ouriyG</eBayAuthToken>"
								+"</RequesterCredentials>"
								+"<ErrorLanguage>en_US</ErrorLanguage>"
								+"<WarningLevel>High</WarningLevel>"
								+"<DetailName>SiteDetails</DetailName>"
								+"</GeteBayDetailsRequest>";
		Document document= detailsHttps.getPesponseXml(url, map, requestBodyXml);
		List<EBaySiteDetailsModel> eBaySiteDetailsModels = detailsHttps.xmlToModel(document);
		List<EBaySiteDetailsModel> eBaySiteDetailsModels2 = internalSelectSiteDetails();
		List<EBaySiteDetailsModel> updateSiteDetailsModels = Lists.newArrayList();
		HashMap<Long, EBaySiteDetailsModel> siteMap = new HashMap<>();
		for (EBaySiteDetailsModel eBaySiteDetailsModel : eBaySiteDetailsModels) {
			siteMap.put(eBaySiteDetailsModel.getSite_id(), eBaySiteDetailsModel);
		}
		for (EBaySiteDetailsModel eBaySiteDetailsModel : eBaySiteDetailsModels2) {
			if (siteMap.get(eBaySiteDetailsModel.getSite_id())!=null) {
				updateSiteDetailsModels.add(siteMap.get(eBaySiteDetailsModel.getSite_id()));
			}
			
		}
		eBaySiteDetailsModels.removeAll(updateSiteDetailsModels);
		StringBuffer description = new StringBuffer();
		if (eBaySiteDetailsModels.size()>0) {
			description.append(insertSiteDetails(eBaySiteDetailsModels).getDescription());
		}
		description.append(updateSiteDetails(updateSiteDetailsModels).getDescription());
		result.setDescription(description.toString());
		return result;
	}

	@Override
	public List<SiteDetailsVO> findSiteDetailsList() {
		List<EBaySiteDetailsModel> eBaySiteDetailsModels = siteDetailsDao.findSiteDetailsList();
		List<SiteDetailsVO> siteDetailsVOs = new ArrayList<>();
		convertList(eBaySiteDetailsModels, siteDetailsVOs);
		return siteDetailsVOs;
	}

	@Override
	public OperationResult insertSiteDetails(List<EBaySiteDetailsModel> eBaySiteDetailsModels) {
		OperationResult result = new OperationResult();
		try {
			siteDetailsDao.insertSiteDetails(eBaySiteDetailsModels);
			result.setDescription("同步数据成功！新增的站点有："+eBaySiteDetailsModels.size()+"个");
		} catch (Exception e) {
			result.setDescription("新增 同步数据失败！");
			log.error(e);
		}
		return null;
	}

	@Override
	public OperationResult updateSiteDetails(List<EBaySiteDetailsModel> eBaySiteDetailsModels) {
		OperationResult result = new OperationResult();
		try {
			siteDetailsDao.updateSiteDetails(eBaySiteDetailsModels);
			result.setDescription("同步数据成功！更新的数据有： "+eBaySiteDetailsModels.size()+"个");
		} catch (Exception e) {
			result.setDescription("更新数据失败！");
			log.error(e);
		}
		return result;
	}
	private void convertList(List<EBaySiteDetailsModel> source, final List<SiteDetailsVO> target) {
		CollectionUtil.each(source, new IAction<EBaySiteDetailsModel>() {
			@Override
			public void excute(EBaySiteDetailsModel obj) {
				SiteDetailsVO siteDetailsVO = new SiteDetailsVO();
				BeanUtils.copyProperties(obj, siteDetailsVO);
				target.add(siteDetailsVO);
			}
		});
	}

	@Override
	public List<EBaySiteDetailsModel> internalSelectSiteDetails() {
		return siteDetailsDao.internalSelectSiteDetails();
	}

	@Override
	public JSONArray selectSiteDetailsJson() {
		List<EBaySiteDetailsModel> eBaySiteDetailsModels = siteDetailsDao.internalSelectSiteDetails();
		List<Map<String, String>> jsonMaps = new ArrayList<>();
		for (EBaySiteDetailsModel eBaySiteDetailsModel : eBaySiteDetailsModels) {
			Map<String, String> jsonMap = new HashMap<>();
			jsonMap.put("id", eBaySiteDetailsModel.getSite_id().toString());
			jsonMap.put("text", eBaySiteDetailsModel.getSite());
			jsonMaps.add(jsonMap);
		}
		JSONArray jsonArray = JSONArray.fromObject(jsonMaps);
		return jsonArray;
	}
	
	
}
