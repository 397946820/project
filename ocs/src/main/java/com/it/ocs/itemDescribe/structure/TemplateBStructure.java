package com.it.ocs.itemDescribe.structure;

import java.util.List;

import com.it.ocs.itemDescribe.Base.BaseStructure;
import com.it.ocs.itemDescribe.model.EBayInternalContentModel;
import com.it.ocs.itemDescribe.model.EBayPromoteModel;
import com.it.ocs.itemDescribe.model.EBayStoreAddUserEmailModel;
import com.it.ocs.itemDescribe.model.StoreCategoryModel;

public class TemplateBStructure implements BaseStructure {

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createLeftLayout(List<StoreCategoryModel> storeCategoryModels, EBayStoreAddUserEmailModel userEmail,
			List<EBayPromoteModel> promoteModels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createHeaderPromoteLayout(List<EBayPromoteModel> eBayPromoteModels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createStoreCategoryLayout(List<StoreCategoryModel> eBayStoreCategoryModels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createStoreAddUserEmailLayout(EBayStoreAddUserEmailModel storeAddUserEmailModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createTemplateInternalPromotelLayout(List<EBayPromoteModel> promoteModels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createEndPromotelLayout(List<EBayPromoteModel> promoteModels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createEndLayout(List<EBayPromoteModel> promoteModels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createLastLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createInternalContentLayout(EBayInternalContentModel internalContentModels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createMiddleTopLayout(String storeUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createMiddleContentLayout(String leftLayout, String internalContentLayout) {
		// TODO Auto-generated method stub
		return null;
	}

}
