package com.it.ocs.publication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.synchronou.model.EBayStoreCategoryModel;
import com.it.ocs.synchronou.service.IEBayStoreCategoryService;
import com.it.ocs.synchronou.vo.StoreCategoryVO;

/**
 * 选择商店分类
 * @author yecaiqing
 *
 */

@Controller
@RequestMapping("/storeCategory")
public class SelectStoreCategoryController {

//	@Autowired
	private IEBayStoreCategoryService storeCategoryService;
	
	@RequestMapping("/selectChildStoreCategorysByCIDAndMID")
	public @ResponseBody List<StoreCategoryVO> selectChildStoreCategorysByCIDAndMID(Long categoryId,Long marketplaceId,String userId) {
		return storeCategoryService.selectChildStoreCategorysByCIDAndMID(categoryId,userId);
	}
	
	@RequestMapping("/ifThereIs")
	public @ResponseBody OperationResult ifThereIs(Long categoryId, Long marketplaceId,String userId) {
		OperationResult result = new OperationResult();
		List<StoreCategoryVO> list = storeCategoryService.selectChildStoreCategorysByCIDAndMID(categoryId,userId);
		if (list == null || list.size() == 0) {
			result.setErrorCode(1);
		}
		return result;
	}
	
	@RequestMapping("/echo")
	public @ResponseBody List<List<StoreCategoryVO>> echo(String categoryId,String userId) {
		List<List<StoreCategoryVO>> list = new ArrayList<>();
		String[] ids = categoryId.split(",");
		for (String id : ids) {
			EBayStoreCategoryModel model = storeCategoryService.selectStoreCategoryByCIDAndMID(new Long(id),userId);
			list.add(storeCategoryService.selectChildStoreCategorysByCIDAndMID(model.getParent_category_id(),userId));
		}
		return list;

	}
}
