package com.it.ocs.synchronou.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.synchronou.dao.IEBayCountryDetailsDao;
import com.it.ocs.synchronou.model.EBayCountryDetailsModel;
import com.it.ocs.synchronou.service.IEBayCountryDetailsService;
import com.it.ocs.synchronou.util.CountryDetailsHttps;
import com.it.ocs.synchronou.vo.CountryDetailsVO;

import net.sf.json.JSONArray;

@Service
public class EBayCountryDetailsService extends BaseService implements IEBayCountryDetailsService {

	@Autowired 
	private IEBayCountryDetailsDao countryDetailsDao;
	
	
	@Override
	public ResponseResult<CountryDetailsVO> findCountryDetailsList(RequestParam param) {
		// TODO Auto-generated method stub
		CountryDetailsVO countryDetailsVO = BeanConvertUtil.mapToObject(param.getParam(), CountryDetailsVO.class);
		ResponseResult<CountryDetailsVO> result = new ResponseResult<>();
		List<EBayCountryDetailsModel> eBayCountryDetailsModels = countryDetailsDao.queryByPage(countryDetailsVO, param.getStartRow(), param.getEndRow());
		List<CountryDetailsVO> countryDetailsVOs = new ArrayList<>();
		convertList(eBayCountryDetailsModels, countryDetailsVOs);
		int count = countryDetailsDao.count(countryDetailsVO);
		result.setRows(countryDetailsVOs);
		result.setTotal(count);
		return result;
	}


	@Override
	public OperationResult insertCountryDetails(List<EBayCountryDetailsModel> eBayCountryDetailsModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		
		try{
			
			countryDetailsDao.insertCountryDetails(eBayCountryDetailsModels);
			result.setDescription("同步成功，新增的国家有："+ eBayCountryDetailsModels.size()+"个");
		
		}catch (Exception e) {
			// TODO: handle exception
			
				result.setErrorCode(1);
						
				result.setDescription("insert error");
						
				e.printStackTrace();
		}
		return result;
		
	}
	@Override
	public EBayCountryDetailsModel selectCountryDetailById(Long id) {
		// TODO Auto-generated method stub
		
		return countryDetailsDao.getById(id);
	}


	@Override
	public OperationResult updateCountryDetails(List<EBayCountryDetailsModel> eBayCountryDetailsModels) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		
		try{
			
			countryDetailsDao.updateCountryDetails(eBayCountryDetailsModels);
			result.setDescription("\n存在的国家有："+eBayCountryDetailsModels.size()+"个");
			 
		}catch (Exception e) {
			// TODO: handle exception
			
				result.setErrorCode(1);
						
				result.setDescription("更新存在的数据失败！");
						
				e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<CountryDetailsVO> selectCountryDetails() {
		// TODO Auto-generated method stub
		List<EBayCountryDetailsModel> eBayCountryDetailsModels = countryDetailsDao.query(null);
		List<CountryDetailsVO> countryDetailsVOs = new ArrayList<>();
		ResponseResult<CountryDetailsVO> result = new ResponseResult<>();
		convertList(eBayCountryDetailsModels, countryDetailsVOs);

		return countryDetailsVOs;
	}
	private void convertList(List<EBayCountryDetailsModel> source, final List<CountryDetailsVO> target) {
		CollectionUtil.each(source, new IAction<EBayCountryDetailsModel>() {
			@Override
			public void excute(EBayCountryDetailsModel obj) {
				CountryDetailsVO countryDetailsVO = new CountryDetailsVO();
				BeanUtils.copyProperties(obj, countryDetailsVO);
				target.add(countryDetailsVO);
			}
		});
	}




	@Override
	public OperationResult synchronouCountryDetails() {
		// TODO Auto-generated method stub
		CountryDetailsHttps countryHttps = new CountryDetailsHttps();
		OperationResult result = new OperationResult();
		Map<String, String> map = new HashMap<>();
		String url = "https://api.ebay.com/ws/api.dll";
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
								+"<DetailName>CountryDetails</DetailName>"
								+"</GeteBayDetailsRequest>";
		Document document = countryHttps.getPesponseXml(url, map, requestBodyXml);
		List<EBayCountryDetailsModel> eBayCountryDetailsModels =  countryHttps.xmlToModel(document);
		List<EBayCountryDetailsModel> eBayCountryDetailsModels2 = internalSelectCountryDetailsList();
		HashMap<String, EBayCountryDetailsModel> countryMap = new HashMap<>();
		for (EBayCountryDetailsModel eBayCountryDetailsModel : eBayCountryDetailsModels) {
			countryMap.put(eBayCountryDetailsModel.getCountry(), eBayCountryDetailsModel);
		}
		
		List<EBayCountryDetailsModel> updateCountryModels = Lists.newArrayList();
		for (EBayCountryDetailsModel eBayCountryDetailsModel : eBayCountryDetailsModels2) {
			if(countryMap.get(eBayCountryDetailsModel.getCountry())!=null){
				updateCountryModels.add(countryMap.get(eBayCountryDetailsModel.getCountry()));
			}
		}
		eBayCountryDetailsModels.removeAll(updateCountryModels);
		StringBuffer description = new StringBuffer();
		if(eBayCountryDetailsModels.size()>0){
			
			description.append(insertCountryDetails(eBayCountryDetailsModels).getDescription());
		 
		} 
		description.append(updateCountryDetails(updateCountryModels).getDescription());
		result.setDescription(description.toString());
		return result;
	}


	@Override
	public List<EBayCountryDetailsModel> internalSelectCountryDetailsList() {
		// TODO Auto-generated method stub
		return countryDetailsDao.internalSelectCountryDetailsList();
	}


	@Override
	public JSONArray selectCountryDetailsListJson() {
		// TODO Auto-generated method stub
		
		List<EBayCountryDetailsModel> eBayCountryDetailsModels = countryDetailsDao.internalSelectCountryDetailsList();
		List<Map<String,String>> jsonMaps = new ArrayList<>();
		
		for(EBayCountryDetailsModel eBayCountryDetailsModel : eBayCountryDetailsModels){
			Map<String, String> jsonMap = new HashMap<>();
			
			jsonMap.put("id", eBayCountryDetailsModel.getCountry());
			jsonMap.put("text", eBayCountryDetailsModel.getDescription());
			jsonMaps.add(jsonMap);
			
		}
		
		return JSONArray.fromObject(jsonMaps);
	}


	



	

}
