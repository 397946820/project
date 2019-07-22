package com.it.ocs.itemDescribe.Base;

import java.util.List;

import com.google.common.collect.Lists;
import com.it.ocs.itemDescribe.css.TemplateACss;
import com.it.ocs.itemDescribe.model.EBayInternalContentModel;
import com.it.ocs.itemDescribe.model.EBayPromoteModel;
import com.it.ocs.itemDescribe.model.EBayStoreAddUserEmailModel;
import com.it.ocs.itemDescribe.model.StoreCategoryModel;
import com.it.ocs.synchronou.dao.ITemplateDao;
import com.it.ocs.synchronou.model.EBayStoreCategoryModel;
import com.it.ocs.synchronou.service.ITemplateService;
import com.it.ocs.synchronou.service.impl.TemplateService;
import com.it.ocs.synchronou.util.ValidationUtil;
import com.sun.net.httpserver.Authenticator.Result;

/*
 * 描述：获取模板中布局的类 
 */
public interface BaseTemplate {

	
	public String getTemplate();
	public void setTopPromoteModel(List<EBayPromoteModel> topPromoteModel);
	public void setTemplateService(ITemplateService templateService);
	public void setStoreUrl(String storeUrl);
	public void setEBayStoreCategoryModels(List<StoreCategoryModel> storeCategoryModels);
 	public void setEBayInternalContentModel(EBayInternalContentModel eBayInternalContentModel);
 	public void setStoreAddUserEmailModel(EBayStoreAddUserEmailModel eBayStoreAddUserEmailModel);
 	public void setBottomPromoteModel(List<EBayPromoteModel> eBayPromoteModels);
 	public void setInPromoteModels(List<EBayPromoteModel> inPromoteModels);
 	public void setAdvertImg(String advertImg);
 	public void setAdvertUrl(String advertUrl);
 	public void setSiteId(Long siteId);
}
