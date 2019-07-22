package com.it.ocs.publication.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.CategoryResult;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.synchronou.model.EBayCategoryModel;
import com.it.ocs.synchronou.model.EBayStoreCategoryModel;
import com.it.ocs.synchronou.service.IEBayCategoryService;
import com.it.ocs.synchronou.service.IEBayStoreCategoryService;
import com.it.ocs.synchronou.vo.CategoryVO;

/**
 * 选择分类的控制层
 * 
 * @author yecaiqing
 *
 */

@Controller
@RequestMapping("/selectCategory")
public class SelectCategoryController {

	@Autowired
	private IEBayCategoryService categoryService;
//	@Autowired
	private IEBayStoreCategoryService storeService;

	// 根据站点id 和 类目id 查询子类目
	@RequestMapping("/selectCategorysByMarketplaceId")
	public @ResponseBody List<CategoryVO> selectCategorysByMarketplaceId(Long categoryId, Long marketplaceId) {
		return categoryService.selectCategorysByMarketplaceId(categoryId, marketplaceId);
	}
	
	@RequestMapping("/ifThereIs")
	public @ResponseBody OperationResult ifThereIs(Long categoryId, Long marketplaceId) {
		OperationResult result = new OperationResult();
		List<CategoryVO> list = categoryService.selectCategorysByMarketplaceId(categoryId, marketplaceId);
		if (list == null || list.size() == 0) {
			result.setErrorCode(1);
		}
		return result;
	}

	@RequestMapping("/echo")
	public @ResponseBody List<List<CategoryVO>> echo(Long marketplaceId, String categoryId) {
		List<List<CategoryVO>> list = new ArrayList<>();
		String[] ids = categoryId.split(",");
		for (String id : ids) {
			EBayCategoryModel model = categoryService.selectCategoryByCIDAndMID(new Long(id), marketplaceId);
			list.add(categoryService.selectCategorysByMarketplaceId(model.getParent_category_id(), marketplaceId));
		}
		return list;

	}
	
	@RequestMapping("/queryStoreCategory")
	@ResponseBody
	public CategoryResult queryStoreCategory(Long categoryId,String userId){
		CategoryResult result = new CategoryResult();

		StringBuffer name = new StringBuffer("");
		StringBuffer id = new StringBuffer("");
		EBayStoreCategoryModel model = null;
		model = storeService.selectStoreCategoryByCIDAndMID(categoryId, userId);
		if(null == model || categoryId == 0){
			return null;
		}
		Long parentCategoryId = model.getParent_category_id();
		while (parentCategoryId != 0) {
			name.append(model.getName()).append(",");
			id.append(model.getCategory_id()).append(",");
			model =storeService.selectStoreCategoryByCIDAndMID(model.getParent_category_id(), userId);
			if(null == model){
				return null;
			}
			parentCategoryId = model.getParent_category_id();
		}
		name.append(model.getName()).append(",");
		id.append(model.getCategory_id()).append(",");
		
		String[] names = name.toString().split(",");
		String[] ids = id.toString().split(",");
		
		List<String> nameList = Arrays.asList(names);
		List<String> idList = Arrays.asList(ids);
		
		Collections.reverse(nameList);
		Collections.reverse(idList);
		
		name = new StringBuffer("");
		for (int i = 0; i < nameList.size(); i++) {
			if(i == nameList.size() -1) {
				name.append(nameList.get(i));
			} else {
				name.append(nameList.get(i)).append(" > ");
			}
		}
		result.setName(name.toString());
		result.setId(StringUtils.strip(idList.toString(), "[]").replace(" ", ""));
		return result;
	}
	@RequestMapping("/queryCategory")
	public @ResponseBody CategoryResult queryCategory(Long categoryId, Long marketplaceId) {
		CategoryResult result = new CategoryResult();
		
		StringBuffer name = new StringBuffer("");
		StringBuffer id = new StringBuffer("");
		EBayCategoryModel model = null;
		model = categoryService.selectCategoryByCIDAndMID(categoryId, marketplaceId);
		if(null == model || categoryId == 0){
			return null;
		}
		Long parentCategoryId = model.getParent_category_id();
		while (parentCategoryId != 0) {
			name.append(model.getName()).append(",");
			id.append(model.getCategory_id()).append(",");
			model = categoryService.selectCategoryByCIDAndMID(model.getParent_category_id(), marketplaceId);
			if(null == model){
				return null;
			}
			parentCategoryId = model.getParent_category_id();
		}
		name.append(model.getName()).append(",");
		id.append(model.getCategory_id()).append(",");
		
		String[] names = name.toString().split(",");
		String[] ids = id.toString().split(",");
		
		List<String> nameList = Arrays.asList(names);
		List<String> idList = Arrays.asList(ids);
		
		Collections.reverse(nameList);
		Collections.reverse(idList);
		
		name = new StringBuffer("");
		for (int i = 0; i < nameList.size(); i++) {
			if(i == nameList.size() -1) {
				name.append(nameList.get(i));
			} else {
				name.append(nameList.get(i)).append(" > ");
			}
		}
		result.setName(name.toString());
		result.setId(StringUtils.strip(idList.toString(), "[]").replace(" ", ""));
		return result;
	}
}
