package com.it.ocs.itemDescribe.Base;

import java.util.List;

import com.it.ocs.itemDescribe.model.EBayInternalContentModel;
import com.it.ocs.itemDescribe.model.EBayPromoteModel;
import com.it.ocs.itemDescribe.model.EBayStoreAddUserEmailModel;
import com.it.ocs.itemDescribe.model.StoreCategoryModel;

public interface BaseStructure {
	
	public String getTemplate();
	public String createLeftLayout(List<StoreCategoryModel> storeCategoryModels,EBayStoreAddUserEmailModel userEmail,List<EBayPromoteModel> promoteModels);
	public String createHeaderPromoteLayout(List<EBayPromoteModel> eBayPromoteModels);
	
	public String createStoreCategoryLayout(List<StoreCategoryModel> eBayStoreCategoryModels);

	public String createStoreAddUserEmailLayout(EBayStoreAddUserEmailModel storeAddUserEmailModel);
	
	public String createTemplateInternalPromotelLayout(List<EBayPromoteModel> promoteModels);
	
	public String createEndPromotelLayout(List<EBayPromoteModel> promoteModels);
	
	public String createEndLayout(List<EBayPromoteModel> promoteModels);
	
	public String createLastLayout();
	
	public String createInternalContentLayout(EBayInternalContentModel internalContentModels);
	
	public String createMiddleTopLayout(String storeUrl);
	
	public String createMiddleContentLayout(String leftLayout,String internalContentLayout);
}
