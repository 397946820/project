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
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.synchronou.dao.IEBayCategorySpecificsDao;
import com.it.ocs.synchronou.model.EBayCategorySpecificsModel;
import com.it.ocs.synchronou.model.EBayCategorySpecificsVModel;
import com.it.ocs.synchronou.service.IEBayCategoryService;
import com.it.ocs.synchronou.service.IEBayCategorySpecificsService;
import com.it.ocs.synchronou.service.IEBayCategorySpecificsVService;
import com.it.ocs.synchronou.util.CategorySpecificsHttps;
import com.it.ocs.synchronou.util.ObjectAndJsonUtil;
import com.it.ocs.synchronou.vo.CategorySpecificsVO;
import com.it.ocs.synchronou.vo.CategorySpecificsVVO;
import com.it.ocs.synchronou.vo.CategoryVO;


@Service
public class EBayCategorySpecificsService extends BaseService implements IEBayCategorySpecificsService {
	
	private static final Logger log = Logger.getLogger(EBayCategorySpecificsService.class);
	
	@Autowired
	private IEBayCategorySpecificsDao categorySpecificsDao;
	@Autowired
	private IEBayCategoryService categoryService;
	@Autowired 
	private IEBayCategorySpecificsVService categorySpecificsVService;
	
	
	@Override
	public OperationResult synchronouCategorySpecifics(Long marketplace_id) {
		String url = "https://api.ebay.com/ws/api.dll";
		Map<String, String> map = new HashMap<>();
		map.put("X-EBAY-API-SITEID", marketplace_id.toString());
		map.put("X-EBAY-API-COMPATIBILITY-LEVEL", "967");
		map.put("X-EBAY-API-CALL-NAME", "GetCategorySpecifics");
		CategorySpecificsHttps categorySpecificsHttps = new CategorySpecificsHttps();
		Long category_Id = categorySpecificsVService.selectCategorySpecificsMaxCID(marketplace_id);
		if(category_Id==null){
			category_Id=0L;
		}
		List<CategoryVO> categoryVOs = categoryService.selectCategorysByMIDAndGCID(category_Id, marketplace_id);
		
		int count = categoryVOs.size();
		if (count>0) {
			
			int k=0;
			
			for(int i=0 ; i<count/20+1;i++){
				StringBuffer conditions = new StringBuffer();
				for(int j=0; j<20;k++,j++){
					
					if(k<count){
						
						conditions.append("<CategoryID>"+categoryVOs.get(k).getCategory_id()+"</CategoryID>");
					}
				}
				if (conditions.length()>0) {
					
					String requestXml =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
							 +"<GetCategorySpecificsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">"
							 +"  <RequesterCredentials>"
							 +"   <eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**Mfb+Vw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEmYSiC5SDpQ2dj6x9nY+seQ**q3YDAA**AAMAAA**a+lc7JZgzUBlqZRxivZzjyionMPWq2fGCNHLxQ923k/f6OqqKAIEfj5EmVXCI6QxJrP2pJqMGMgTcp2Yz5O87GGdHS9MiZHt5AxLJ2skN56kAvdtJQlf0q+3Fft1SfWJgE3wdkrm+m2MAU7vKndoLAdTrFv6h2A4UXNUc2Xi3rV1RON4pxxLLxp79NBt2rpx76fC/5A+dTlW/VVFZ3BCRsJ7HC1yUB1DM6Vpb+DGHy+TW4Yp0wCjJQH7V4peHhXKf2W8AK83LC1CqibdkWXvPPOhqzHssfQg6O+gK11s6WHJsee+OxKspS0dOYq9PUEoFgpURwL7teii53PIvJLWNBWDpv2Bxh2UYKKIlc1IkqjcWYVVwa6yalvv0pRBbcRR4ruzOjpUwrCWvV58PyAN7pUlwHabyHHoo5Qfps/QQYTP7Ac6UojJgA+UazSWuX5EwjULppBPPUzMFHdnSV1jobfUOOXHFcYd/Pw/xFZ/NahsygwsGAELZI7hGm/bIYS6pFoT1HNOL4uutBMNECy7fxQiFbiXNYZLLINRqCFtsiBUHa4Dniwss42CoRQLnwyXKfEuPdS9ZTMFnRBKu9dTQHs1P/zLkWeAP8L0mZAVjNSX0pSEVh4TvokcRUTJsAQlEViSCP8ZbJ+P6NoPDe4XRoyPB53FxJirq61Z2Bqe5X9VK4LReb5OfcZGN1MnhuhZ581CcbaLwwWQWynT0YO8pDIiaNFel9WlzhQ8zrq4fEjODDqFU2Bo9MRTs5ouriyG</eBayAuthToken>"
							 +" </RequesterCredentials>"
							 +  conditions.toString()
							 +" <WarningLevel>High</WarningLevel>"
							 +"<CategorySpecificsFileInfo >true</CategorySpecificsFileInfo >"
							 +"</GetCategorySpecificsRequest>";
					
					Document document = categorySpecificsHttps.getPesponseXml(url, map, requestXml);
					Map<String, Object> resultMap = categorySpecificsHttps.xmlToMap(document, marketplace_id.toString());
					List<EBayCategorySpecificsModel> eBayCategorySpecificsModels = (List<EBayCategorySpecificsModel>) resultMap.get("eBayCategorySpecificsModel");
					List<EBayCategorySpecificsVModel> eBayCategorySpecificsVModels = (List<EBayCategorySpecificsVModel>) resultMap.get("eBayCategorySpecificsVModel");
					if(eBayCategorySpecificsModels.size()>0){
						insertCategorySpecificsList(eBayCategorySpecificsModels);
					}
					int k2 = 0;
					int count2 = eBayCategorySpecificsVModels.size();
					for(int i2=0; i2<count2/1000+1; i2++){
						List<EBayCategorySpecificsVModel> list2 = new ArrayList<>();
						for(int j2=0; j2<1000;k2++,j2++){
 							if(k2<count2){
							list2.add(eBayCategorySpecificsVModels.get(k2));
							}
						}
						if(list2.size()!=0){
							categorySpecificsVService.insertCategorySpecificsVList(list2);
						}
					}
					
					
				}
			}
			
		}
		
		return null;
	}

	@Override
	public OperationResult insertCategorySpecificsList(List<EBayCategorySpecificsModel> eBayCategorySpecificsModels) {
		OperationResult result = new OperationResult();
		try{
			categorySpecificsDao.insertCategorySpecificsList(eBayCategorySpecificsModels);
		}catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("insert error");
			log.error(e);
		}
		
		return result;
	}

	@Override
	public OperationResult updateCategorySpecificsList(List<EBayCategorySpecificsModel> eBayCategorySpecificsModels) {
		OperationResult result = new OperationResult();
		try{
			categorySpecificsDao.updateCategorySpecificsList(eBayCategorySpecificsModels);
		}catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("update error");
			log.error(e);
		}
		return result;
	}
	@Override
	public ResponseResult<CategorySpecificsVO> findCategorySpecificsByMID(Long category_Id,Long marketplace_id, RequestParam param) {
		ResponseResult<CategorySpecificsVO> result = new ResponseResult<>();
		CategorySpecificsVO categorySpecificsVO = BeanConvertUtil.mapToObject(param.getParam(), CategorySpecificsVO.class);
		List<EBayCategorySpecificsModel>  ebayCategorySpecificsModels = Lists.newArrayList();
		int total = 0;
		if(marketplace_id==null&&category_Id==null){
			marketplace_id=0L;
			ebayCategorySpecificsModels = categorySpecificsDao.findCategorySpecificsByMID(param.getStartRow(), param.getEndRow(), marketplace_id,category_Id);
			
		}else{
			ebayCategorySpecificsModels = categorySpecificsDao.findCategorySpecificsByMID(param.getStartRow(), param.getEndRow(), marketplace_id,category_Id);
		}
		total = categorySpecificsDao.getTotal(marketplace_id, category_Id);
		List<CategorySpecificsVO> categorySpecificsVOs = Lists.newArrayList();
		convertList(ebayCategorySpecificsModels, categorySpecificsVOs);
		for (CategorySpecificsVO categorySpecificsVO2 : categorySpecificsVOs) {
			StringBuffer valueResult = new StringBuffer();
			valueResult.append("<select name=\"value\" style=\"width:240px\" >");
			List<CategorySpecificsVVO> eBayCategorySpecificsVModels = categorySpecificsVService.findSpecificsVByNameAanCIDAanMID(categorySpecificsVO2.getName(), categorySpecificsVO2.getCategory_id(), marketplace_id);
			for (CategorySpecificsVVO categorySpecificsVVO : eBayCategorySpecificsVModels) {
				valueResult.append("<option value=\""+categorySpecificsVVO.getValue()+"\">");
				valueResult.append(categorySpecificsVVO.getValue()+"</option>");
			}
			valueResult.append("</select>");
			categorySpecificsVO2.setValue(valueResult.toString());
		}
		result.setRows(categorySpecificsVOs);
		result.setTotal(total);
		return result;
	}
	@Override
	public List<CategorySpecificsVO> findCategorySpecificsByCIDAndMID(Long category_Id, Long marketplace_id) {
		List<EBayCategorySpecificsModel> eBayCategorySpecificsModels = new ArrayList<>();
		List<CategorySpecificsVO> categorySpecificsVOs = new ArrayList<>();
		if(category_Id==null&&marketplace_id==null){
			category_Id=1L;
			marketplace_id=0L;
			eBayCategorySpecificsModels = categorySpecificsDao.findCategorySpecificsByCIDAndMID(category_Id, marketplace_id);
		}else if(category_Id==null&&marketplace_id!=null){
			category_Id=1L;
			eBayCategorySpecificsModels = categorySpecificsDao.findCategorySpecificsByCIDAndMID(category_Id, marketplace_id);
		}else if(category_Id!=null&&marketplace_id==null){
			marketplace_id=0L;
			eBayCategorySpecificsModels = categorySpecificsDao.findCategorySpecificsByCIDAndMID(category_Id, marketplace_id);
		}else{
			
			eBayCategorySpecificsModels = categorySpecificsDao.findCategorySpecificsByCIDAndMID(category_Id, marketplace_id);
		}
		convertList(eBayCategorySpecificsModels, categorySpecificsVOs);
		for (CategorySpecificsVO categorySpecificsVO : categorySpecificsVOs) {
			StringBuffer valueResult = new StringBuffer();
			List<Map<String, String>> maps = new ArrayList<>();
			List<CategorySpecificsVVO> eBayCategorySpecificsVModels = categorySpecificsVService.findSpecificsVByNameAanCIDAanMID(categorySpecificsVO.getName(), category_Id, marketplace_id);
			
			for (CategorySpecificsVVO categorySpecificsVVO : eBayCategorySpecificsVModels) {
				Map<String, String> map = new HashMap<>();
				map.put("value", categorySpecificsVVO.getValue());
				maps.add(map);
			}
			categorySpecificsVO.setValue(ObjectAndJsonUtil.MapsToJsonArray(maps,null));
			
		}
		return categorySpecificsVOs;
	}
	private void convertList(List<EBayCategorySpecificsModel> source, final List<CategorySpecificsVO> target) {
		CollectionUtil.each(source, new IAction<EBayCategorySpecificsModel>() {
			@Override
			public void excute(EBayCategorySpecificsModel obj) {
				CategorySpecificsVO categorySpecificsVO = new CategorySpecificsVO();
				BeanUtils.copyProperties(obj, categorySpecificsVO);
				target.add(categorySpecificsVO);
			}
		});
	}

	@Override
	public ResponseResult<CategorySpecificsVO> internalFindCategorySpecificsList(Long category_Id,
			Long marketplace_id) {
		ResponseResult<CategorySpecificsVO> result = new ResponseResult<>();
		List<EBayCategorySpecificsModel>  ebayCategorySpecificsModels = Lists.newArrayList();
		int total = 0;
		if(marketplace_id==null&&category_Id==null){
			marketplace_id=0L;
			ebayCategorySpecificsModels = categorySpecificsDao.internalFindCategorySpecificsList(category_Id, marketplace_id);
			
		}else{
			ebayCategorySpecificsModels = categorySpecificsDao.internalFindCategorySpecificsList(category_Id, marketplace_id);
		}
		List<CategorySpecificsVO> categorySpecificsVOs = Lists.newArrayList();
		if(ebayCategorySpecificsModels.size()>0){
			convertList(ebayCategorySpecificsModels, categorySpecificsVOs);
		}
		result.setRows(categorySpecificsVOs);
		return result;
	}

	
}
