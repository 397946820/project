package com.it.ocs.synchronou.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.synchronou.dao.IEBayCategorySpecificsDao;
import com.it.ocs.synchronou.dao.IEBayCategorySpecificsVDao;
import com.it.ocs.synchronou.model.EBayCategoryModel;
import com.it.ocs.synchronou.model.EBayCategorySpecificsModel;
import com.it.ocs.synchronou.model.EBayCategorySpecificsVModel;
import com.it.ocs.synchronou.service.IEBayCategorySpecificsVService;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.it.ocs.synchronou.vo.CategorySpecificsVVO;
import com.it.ocs.synchronou.vo.CategoryVO;

import net.sf.json.JSONObject;
@Service
public class EBayCategorySpecificsVService extends BaseService implements IEBayCategorySpecificsVService {
	private static final Logger log = Logger.getLogger(EBayCategorySpecificsVService.class);
	
	@Autowired
	private IEBayCategorySpecificsVDao categorySpecificsVDao;
	@Autowired
	private IEBayCategorySpecificsDao categorySpecificsDao;
	@Override
	public OperationResult insertCategorySpecificsVList(
			List<EBayCategorySpecificsVModel> eBayCategorySpecificsVModels) {
		OperationResult result = new OperationResult();
		try{
			categorySpecificsVDao.insertCategorySpecificsVList(eBayCategorySpecificsVModels);
		}catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("insert error");
			log.error(e);
		}
		return result;
	}

	@Override
	public OperationResult updateCategorySpecificsVList(List<EBayCategorySpecificsVModel> eCategorySpecificsVModels) {
		OperationResult result = new OperationResult();
		try{
			categorySpecificsVDao.updateCategorySpecificsVList(eCategorySpecificsVModels);
		}catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("update error");
			e.printStackTrace();
			log.error(e);
		}
		return result;
	}

	@Override
	public Long selectCategorySpecificsMaxCID(Long marketplaceId) {
		return categorySpecificsVDao.selectCategorySpecificsMaxCID(marketplaceId);
	}

	@Override
	public List<CategorySpecificsVVO> findSpecificsVByNameAanCIDAanMID(String name, Long category_Id,
			Long marketplace_id) {
		List<EBayCategorySpecificsVModel> eBayCategorySpecificsVModels = categorySpecificsVDao.findCategorySpecificsVList(name, category_Id, marketplace_id);
		List<CategorySpecificsVVO> categorySpecificsVVOs = new ArrayList<>();
		convertList(eBayCategorySpecificsVModels, categorySpecificsVVOs);
		return categorySpecificsVVOs;
	}
	private void convertList(List<EBayCategorySpecificsVModel> source, final List<CategorySpecificsVVO> target) {
		CollectionUtil.each(source, new IAction<EBayCategorySpecificsVModel>() {
			@Override
			public void excute(EBayCategorySpecificsVModel obj) {
				CategorySpecificsVVO categorySpecificsVVO = new CategorySpecificsVVO();
				BeanUtils.copyProperties(obj, categorySpecificsVVO);
				target.add(categorySpecificsVVO);
			}
		});
	}

	@Override
	public List<EBayCategorySpecificsVModel> internalFindSpecificsVByNameAanCIDAanMID(String name, Long category_Id,
			Long marketplace_id) {
		EBayCategorySpecificsModel eBayCategorySpecificsModel = categorySpecificsDao.internalFindSpecificsByNameAanCIDAanMID(name, category_Id, marketplace_id);
		String selectionMode = null;
		if(ValidationUtil.isNull(eBayCategorySpecificsModel)){
		
		}else{
			JSONObject jsonObject = JSONObject.fromObject(eBayCategorySpecificsModel.getValidation_rules());
			if(jsonObject.getString("SelectionMode")!=null){
				selectionMode = jsonObject.getString("SelectionMode");
			}
		}
		
		List<EBayCategorySpecificsVModel> result = categorySpecificsVDao.findCategorySpecificsVList(name, category_Id, marketplace_id);
		for (EBayCategorySpecificsVModel eBayCategorySpecificsVModel : result) {
			eBayCategorySpecificsVModel.setSelectionMode(selectionMode);
		}
	    return result;
	}
}
