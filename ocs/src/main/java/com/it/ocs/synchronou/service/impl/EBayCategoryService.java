package com.it.ocs.synchronou.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.synchronou.dao.IEBayCategoryDao;
import com.it.ocs.synchronou.model.EBayCategoryModel;
import com.it.ocs.synchronou.service.IEBayCategoryService;
import com.it.ocs.synchronou.util.CategoryHttps;
import com.it.ocs.synchronou.vo.CategoryVO;

import net.sf.json.JSONObject;

@Service
public class EBayCategoryService extends BaseService implements IEBayCategoryService {
	
	private static final Logger log = Logger.getLogger(EBayCategoryService.class);
	
	@Autowired
	private IEBayCategoryDao categoryDao;
	
	@Override
	public OperationResult insertCategoryList(List<EBayCategoryModel> ebayCategoryModels) {
		OperationResult result = new OperationResult();
		try{
			categoryDao.insertCategoryList(ebayCategoryModels);
		}catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("insert error");
			log.error(e);
		}
		return result;
	}

	@Override
	public OperationResult updateCategoryList(List<EBayCategoryModel> ebayCategoryModels) {
		OperationResult result = new OperationResult();
		try{
			categoryDao.updateCategoryList(ebayCategoryModels);
		}catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("insert error");
			log.error(e);
		}
		return result;
	}

	@Override
	public OperationResult synchronouCategory(CategoryVO categoryVO) {
		OperationResult result = new OperationResult();
		try{
			List<EBayCategoryModel> categoryList = categoryDao.selectCategorysByMarketplaceId(categoryVO.getMarketplace_id());
			List<EBayCategoryModel> updateCategorys =new ArrayList<>();
			CategoryHttps categoryHttps = new CategoryHttps();
			Map<String, String> map = new HashMap<>();
			map.put("Authorization",categoryVO.getAuthorization());
			map.put("Accept", "application/json");
			map.put("Content-Type", "application/json");
			/*map.put("Accept-Encoding", "application/gzip");*/
			JSONObject jsonObject = categoryHttps.getResponseJson("https://api.ebay.com/commerce/taxonomy/v1_beta/category_tree/"+categoryVO.getMarketplace_id(), map);
			categoryHttps.jsonToModel(jsonObject);
			List<EBayCategoryModel> ebayCategoryModels=categoryHttps.getEbayCategoryModels();
			
			for (EBayCategoryModel eBayCategoryModel : ebayCategoryModels) {
				for (EBayCategoryModel category : categoryList) {
					if (eBayCategoryModel.getCategory_id().equals(category.getCategory_id())) {
						updateCategorys.add(eBayCategoryModel);
					}
				}
			}
			ebayCategoryModels.removeAll(updateCategorys);
			int insertCount = ebayCategoryModels.size();
			int updateCount = updateCategorys.size();
			if (insertCount>0) {
				int k=0;
				for(int i=0; i<=insertCount/1000+1;i++){
					List<EBayCategoryModel> list = Lists.newArrayList();
					for(int j=0;j<1000;j++,k++){
						if(k<insertCount){
							ebayCategoryModels.get(k).setLast_update_date(new Date());
						  list.add(ebayCategoryModels.get(k));
						}
					}
					if (list.size()!=0){
						insertCategoryList(list);
					}
				}
			}
			if(updateCount>0){
				int k=0;
				for(int i=0; i<=updateCount/1000+1;i++){
					List<EBayCategoryModel> list = Lists.newArrayList();
					for(int j=0;j<1000;j++,k++){
						if(k<updateCount){
						  list.add(updateCategorys.get(k));
						}
					}
					if (list.size()!=0){
						updateCategoryList(list);
					}
				}
			}
			System.out.println("insert:"+insertCount+"update:"+updateCount);
		}catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("insert error");
			log.error(e);
		}
		return result;
	}

	@Override
	public List<CategoryVO> selectCategorysByMarketplaceId(Long categoryId,Long marketplaceId) {
		List<EBayCategoryModel> bayCategoryModels = new ArrayList<>();
		if(categoryId==null&&marketplaceId==null){
			bayCategoryModels = categoryDao.selectChildCategorysByCIDAndMID(0L, 0L);
		}else if(categoryId!=null&&marketplaceId==null){
			bayCategoryModels = categoryDao.selectChildCategorysByCIDAndMID(categoryId, 0L);
		}else if(categoryId==null&&marketplaceId!=null){
			bayCategoryModels = categoryDao.selectChildCategorysByCIDAndMID(0L, marketplaceId);
		}else{
			bayCategoryModels = categoryDao.selectChildCategorysByCIDAndMID(categoryId, marketplaceId);
		}
		List<CategoryVO> categoryVOs = new ArrayList<>();
		convertList(bayCategoryModels, categoryVOs);
		for (CategoryVO categoryVO : categoryVOs) {
			categoryVO.setState("closed");
		}
		return categoryVOs;
	}

	@Override
	public ResponseResult<CategoryVO> selectChildCategorysByCIDAndMID(Long categoryId, Long marketplaceId) {
		ResponseResult<CategoryVO> result = new ResponseResult<>();
		List<EBayCategoryModel> bayCategoryModels = categoryDao.selectChildCategorysByCIDAndMID(categoryId, marketplaceId);
		List<CategoryVO> categoryVOs = new ArrayList<>();
		convertList(bayCategoryModels, categoryVOs);
		result.setRows(categoryVOs);
		return result;
	}

	@Override
	public EBayCategoryModel selectCategoryByCIDAndMID(Long categoryId, Long marketplaceId) {
		return categoryDao.selectCategoryByCIDAndMID(categoryId, marketplaceId);
	}

	private void convertList(List<EBayCategoryModel> source, final List<CategoryVO> target) {
		CollectionUtil.each(source, new IAction<EBayCategoryModel>() {
			@Override
			public void excute(EBayCategoryModel obj) {
				CategoryVO categoryVO = new CategoryVO();
				BeanUtils.copyProperties(obj, categoryVO);
				target.add(categoryVO);
			}
		});
	}
	@Override
	public List<CategoryVO> selectCategorysByMarketplaceId(Long marketplaceId) {
		List<EBayCategoryModel> eBayCategoryModels = categoryDao.selectCategorysByMarketplaceId(marketplaceId);
		List<CategoryVO> categoryVOs = new ArrayList<>();
		convertList(eBayCategoryModels, categoryVOs);
		return categoryVOs;
	}
	@Override
	public List<CategoryVO> selectCategorysByMarketplaceIds(Long marketplaceId) {
		List<EBayCategoryModel> eBayCategoryModels = categoryDao.selectCategorysByMarketplaceId(marketplaceId);
		List<CategoryVO> categoryVOs = new ArrayList<>();
		convertList(eBayCategoryModels, categoryVOs);
		for (CategoryVO categoryVO : categoryVOs) {
			categoryVO.setState("closed");
		}
		return categoryVOs;
	}

	@Override
	public EBayCategoryModel selectCategoryByPIDAndMIDAndName(Long parentCategoryId, Long marketplaceId,
			String categoryName) {
		return categoryDao.selectCategoryByPIDAndMIDAndName(parentCategoryId, marketplaceId, categoryName);
	}

	@Override
	public List<CategoryVO> selectCategorysByMIDAndGCID(Long categoryId, Long marketplaceId) {
		List<EBayCategoryModel> eBayCategoryModels = categoryDao.selectCategorysByMIDAndGCID(categoryId, marketplaceId);
		List<CategoryVO> categoryVOs = new ArrayList<>();
		convertList(eBayCategoryModels, categoryVOs);
		return categoryVOs;
	}

	@Override
	public List<EBayCategoryModel> selectCategoryIDByMID(Long marketplaceId) {
		return categoryDao.selectCategoryIDByMID(marketplaceId);
	}

	@Override
	public List<EBayCategoryModel> selectCategoryIDByMIDAndCIDMIN(Long categoryId, Long marketplaceId) {
		return categoryDao.selectCategoryIDByMIDAndCIDMIN(categoryId, marketplaceId);
	}

	

}
