package com.it.ocs.synchronou.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSException;

import com.google.common.collect.Lists;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.seller.model.EBayBuyerRequiredModel;
import com.it.ocs.seller.vo.BuyerRequiredVO;
import com.it.ocs.synchronou.dao.IEBayProductListingDao;
import com.it.ocs.synchronou.model.EBayCategoryModel;
import com.it.ocs.synchronou.model.EBayProductListingModel;
import com.it.ocs.synchronou.service.IEBayCategoryService;
import com.it.ocs.synchronou.service.IEBayProductListingService;
import com.it.ocs.synchronou.util.ProductListingHttps;
import com.it.ocs.synchronou.vo.ProductListingVO;
@Service
public class EBayProductListingService extends BaseService implements IEBayProductListingService {

	@Autowired
	private IEBayProductListingDao productListingDao;
	@Autowired
	private IEBayCategoryService categoryService;
	@Override
	public OperationResult synchronouProductListing(Long marketplace_id,Long category_id) {
		// TODO Auto-generated method stub
		DataSourceTypeManager.set(DataSources.MASTER);
		OperationResult result = new OperationResult();
		
		ProductListingHttps productHttps = new ProductListingHttps();
		if(marketplace_id!=null&&category_id!=null){
		try {
		
		
		
	
		String url = "https://api.ebay.com/ws/api.dll";
		Map<String, String> map = new HashMap<>();
		map.put("X-EBAY-API-SITEID",marketplace_id.toString());
		map.put("X-EBAY-API-COMPATIBILITY-LEVEL", "967");
		map.put("X-EBAY-API-CALL-NAME", "GetCategoryFeatures");
	
			
			String requestXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
					+"<GetCategoryFeaturesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">"
					  +"<RequesterCredentials>"
					  +"<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**Mfb+Vw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEmYSiC5SDpQ2dj6x9nY+seQ**q3YDAA**AAMAAA**a+lc7JZgzUBlqZRxivZzjyionMPWq2fGCNHLxQ923k/f6OqqKAIEfj5EmVXCI6QxJrP2pJqMGMgTcp2Yz5O87GGdHS9MiZHt5AxLJ2skN56kAvdtJQlf0q+3Fft1SfWJgE3wdkrm+m2MAU7vKndoLAdTrFv6h2A4UXNUc2Xi3rV1RON4pxxLLxp79NBt2rpx76fC/5A+dTlW/VVFZ3BCRsJ7HC1yUB1DM6Vpb+DGHy+TW4Yp0wCjJQH7V4peHhXKf2W8AK83LC1CqibdkWXvPPOhqzHssfQg6O+gK11s6WHJsee+OxKspS0dOYq9PUEoFgpURwL7teii53PIvJLWNBWDpv2Bxh2UYKKIlc1IkqjcWYVVwa6yalvv0pRBbcRR4ruzOjpUwrCWvV58PyAN7pUlwHabyHHoo5Qfps/QQYTP7Ac6UojJgA+UazSWuX5EwjULppBPPUzMFHdnSV1jobfUOOXHFcYd/Pw/xFZ/NahsygwsGAELZI7hGm/bIYS6pFoT1HNOL4uutBMNECy7fxQiFbiXNYZLLINRqCFtsiBUHa4Dniwss42CoRQLnwyXKfEuPdS9ZTMFnRBKu9dTQHs1P/zLkWeAP8L0mZAVjNSX0pSEVh4TvokcRUTJsAQlEViSCP8ZbJ+P6NoPDe4XRoyPB53FxJirq61Z2Bqe5X9VK4LReb5OfcZGN1MnhuhZ581CcbaLwwWQWynT0YO8pDIiaNFel9WlzhQ8zrq4fEjODDqFU2Bo9MRTs5ouriyG</eBayAuthToken>"
					  +"</RequesterCredentials>"
					  +"<ErrorLanguage>en_US</ErrorLanguage>"
					  +"<WarningLevel>High</WarningLevel>"
					  +"<CategoryID>"+category_id+"</CategoryID >"
					   +"<ViewAllNodes>true</ViewAllNodes >"
					   + "<FeatureID>EANEnabled</FeatureID >"
					   + "<FeatureID>ISBNEnabled</FeatureID >"
					   + "<FeatureID>UPCEnabled</FeatureID >"
					   + "<FeatureID>ConditionValues</FeatureID >"
					   + "<DetailLevel>ReturnAll</DetailLevel >"
					   + "<AllFeaturesForCategory>true</AllFeaturesForCategory>"
					   + "</GetCategoryFeaturesRequest>";
			
			Document document = productHttps.getPesponseXml(url, map, requestXml);
			
		
		    EBayProductListingModel ebayProductListingModel = productHttps.xmlToModel(document, marketplace_id);
		    if(ebayProductListingModel!=null){
		    EBayProductListingModel eBayProductListingModel2 = internalSelectProductListingsByMIDAndCID(category_id, marketplace_id);
			    if(eBayProductListingModel2==null){
			    	result = insertProductListing(ebayProductListingModel);
			    	result.setDescription("同步数据成功");
			    }else{
			    	ebayProductListingModel.setId(eBayProductListingModel2.getId());
			    	result = updateProductListing(ebayProductListingModel);
			    }
		    }else{
		    	
		    	result.setDescription("Ebay没有同步的类别ID数据");
		    }
		    
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			result.setDescription("synchronou error");
		}
	}else{
		
		result.setDescription("站点或类目ID不能为空");
	}
		return result;
	}

	@Override
	public OperationResult insertProductListing(EBayProductListingModel ebayProductListingModel) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			productListingDao.insertProductListing(ebayProductListingModel);
		} catch (Exception e) {
			// TODO: handle exception
			
			result.setErrorCode(1);
			result.setDescription("insert Error");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public OperationResult updateProductListing(EBayProductListingModel ebayProductListingModel) {
		// TODO Auto-generated method stub
		OperationResult result = new OperationResult();
		try {
			productListingDao.updateProductListing(ebayProductListingModel);
			result.setDescription("同步的类目已存在，更改成功！");
		} catch (Exception e) {
			// TODO: handle exception
			result.setErrorCode(1);
			result.setDescription("update error");
		}
		return result;
	}

	@Override
	public EBayProductListingModel internalSelectProductListingsByMIDAndCID(Long category_id, Long marketplace_id) {
		// TODO Auto-generated method stub
		return productListingDao.internalSelectProductListingsByMIDAndCID(category_id, marketplace_id);
	}

	@Override
	public ResponseResult<ProductListingVO> selectProductListingsByMIDOrCID(Long category_Id,Long marketplace_id,RequestParam param) {
		// TODO Auto-generated method stub
		ResponseResult<ProductListingVO> result = new ResponseResult<>();
		ProductListingVO productListingVO = BeanConvertUtil.mapToObject(param.getParam(), ProductListingVO.class);
		
		List<EBayProductListingModel> ebayProductListingModels = Lists.newArrayList();
		int total=0;
		if(marketplace_id==null){
			 ebayProductListingModels = productListingDao.selectProductListingsByMIDOrCID(param.getStartRow(),param.getEndRow(),0L,category_Id);	
			 total = productListingDao.getTotal(0L,category_Id);
		}else if(marketplace_id!=null){
			
			 ebayProductListingModels = productListingDao.selectProductListingsByMIDOrCID(param.getStartRow(),param.getEndRow(), marketplace_id,category_Id);
			 
			 total = productListingDao.getTotal(marketplace_id,category_Id);
		}
		
		List<ProductListingVO> productListingVOs = Lists.newArrayList();
		
		convertList(ebayProductListingModels, productListingVOs);
		
		result.setRows(productListingVOs);
		result.setTotal(total);
		
		return result;
	}

	private void convertList(List<EBayProductListingModel> source, final List<ProductListingVO> target) {
		CollectionUtil.each(source, new IAction<EBayProductListingModel>() {
			@Override
			public void excute(EBayProductListingModel obj) {
				ProductListingVO productListingVO = new ProductListingVO();
				BeanUtils.copyProperties(obj, productListingVO);
				target.add(productListingVO);
			}
		});
	}

	@Override
	public EBayProductListingModel internalSelectProductListingByMIDOrCID(Long category_Id, Long marketplace_id) {
		// TODO Auto-generated method stub
		return productListingDao.internalSelectProductListingByMIDOrCID(category_Id, marketplace_id);
	}

}
