package com.it.ocs.itemDescribe.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it.ocs.itemDescribe.Base.BaseStructure;
import com.it.ocs.itemDescribe.Base.BaseTemplate;
import com.it.ocs.itemDescribe.css.TemplateACss;
import com.it.ocs.itemDescribe.model.EBayInternalContentModel;
import com.it.ocs.itemDescribe.model.EBayPromoteModel;
import com.it.ocs.itemDescribe.model.EBayStoreAddUserEmailModel;
import com.it.ocs.itemDescribe.model.StoreCategoryModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.ITemplateService;
import com.it.ocs.synchronou.service.impl.TemplateService;

public class TemplateA implements BaseTemplate {
	private ITemplateService templateService;
	private String topPromoteLayout;
	private List<EBayPromoteModel> topPromoteModels;
	private String storeUrl;
	private List<StoreCategoryModel> storeCategoryModels;
	private String storeCategory;
	private EBayInternalContentModel eBayInternalContentModel;
	private String internalTemplateImages;
	private  EBayStoreAddUserEmailModel eBayStoreAddUserEmailModel;
	private String bottomPromoteLayout;
	private List<EBayPromoteModel> bottomPromoteModels;
	

	@Override
	public void setTopPromoteModel(List<EBayPromoteModel> topPromoteModel) {
		// TODO Auto-generated method stub
		this.topPromoteModels=topPromoteModel;
	}

	@Override
	public void setTemplateService(ITemplateService templateService) {
		// TODO Auto-generated method stub
		this.templateService = templateService;
	}

	@Override
	public void setStoreUrl(String storeUrl) {
		// TODO Auto-generated method stub
		this.storeUrl = storeUrl;
	}

	@Override
	public void setEBayStoreCategoryModels(List<StoreCategoryModel> storeCategoryModels) {
		// TODO Auto-generated method stub
		this.storeCategoryModels = storeCategoryModels;
	}

	@Override
	public void setEBayInternalContentModel(EBayInternalContentModel eBayInternalContentModel) {
		// TODO Auto-generated method stub
		this.eBayInternalContentModel = eBayInternalContentModel;
	}

	@Override
	public void setStoreAddUserEmailModel(EBayStoreAddUserEmailModel eBayStoreAddUserEmailModel) {
		// TODO Auto-generated method stub
		this.eBayStoreAddUserEmailModel=eBayStoreAddUserEmailModel;
	}

	@Override
	public void setBottomPromoteModel(List<EBayPromoteModel> eBayPromoteModels) {
		// TODO Auto-generated method stub
		this.bottomPromoteModels=eBayPromoteModels;
	}
	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		topPromoteLayout();
		storeCategoryModel();
		internalTemplateImages();
		bottomPromoteLayout();
		Map<String , String> map = new HashMap<>();
		TemplateModel templateModel = templateService.getTemplateContent("EBayItemDescrideTemplateA", "ebay");
		String html = templateModel.getContent();
		map.put("storeUrl", storeUrl);
		map.put("signUrl", eBayStoreAddUserEmailModel.getSignUrl());
		map.put("buttonName", eBayStoreAddUserEmailModel.getButtonName());
		map.put("topPromoteLayout", topPromoteLayout);
		map.put("storeCategory", storeCategory);
		map.put("internalContentLayout", internalTemplateImages);
		map.put("title", eBayInternalContentModel.getTitle());
		//map.put("middlePromote", null);
		map.put("produceDescription", eBayInternalContentModel.getProduceDescription());
		map.put("payment", eBayInternalContentModel.getPayment());
		map.put("shipment", eBayInternalContentModel.getShipment());
		map.put("aboutUs", eBayInternalContentModel.getAboutUs());
		map.put("returns", eBayInternalContentModel.getReturns());
		map.put("faq", eBayInternalContentModel.getFaq());
		map.put("bottomPromoteLayout", bottomPromoteLayout);
		html = TemplateService.formatTemplat(map, html);
		System.out.println(html);
		return html;
	}
	//推广
	public String promoteLayout(List<EBayPromoteModel> eBayPromoteModels) {
			// TODO Auto-generated method stub
			TemplateModel templateModel = templateService.getTemplateContent("EBayTemplateATopPromote", "ebay");
			String html = templateModel.getContent();
			StringBuffer result = new StringBuffer();
			for (EBayPromoteModel eBayPromoteModel : eBayPromoteModels) {
				Map<String, String> map = new HashMap<>();
				map.put("imgUrl", eBayPromoteModel.getImgUrl());
				map.put("productAddress", eBayPromoteModel.getProductAddress());
				map.put("title", eBayPromoteModel.getTitle());
				String buyNow = eBayPromoteModel.getBuyNow();
				if(buyNow.equals("Chinese")){
					map.put("buyNow", "Chinese");
				}else{
					map.put("buyNow", "Buy it now");
				}
				map.put("price", eBayPromoteModel.getPrice());
				map.put("freeShipping", eBayPromoteModel.getFreeShipping());
				result.append(TemplateService.formatTemplat(map, html));
			}
			
			return result.toString();
		}
	//顶部推广
	public String topPromoteLayout(){
			this.topPromoteLayout =  promoteLayout(topPromoteModels);
			return topPromoteLayout;
		}

		 //商店分类
	public String storeCategoryModel(){
			TemplateModel templateModel = templateService.getTemplateContent("EBayTemplateAStoreCategory", "ebay");
			String html = templateModel.getContent();
			StringBuffer result = new StringBuffer();
			for (StoreCategoryModel storeCategoryModel : storeCategoryModels) {
				Map<String, String> map = new HashMap<>();
				map.put("storeUrl", storeUrl);
				map.put("categoryName", storeCategoryModel.getCategoryName());
				result.append(TemplateService.formatTemplat(map, html));
			}
			this.storeCategory = result.toString();
			
			return storeCategory;
		}
	//内部图片
	public String internalTemplateImages(){
			TemplateModel templateModel = templateService.getTemplateContent("EBayTemplateAImages", "ebay");
			String html = templateModel.getContent();
			StringBuffer result = new StringBuffer();
			String[] bigImgUrls = eBayInternalContentModel.getBigImgUrl();
			
			int size = bigImgUrls.length;
			for (Integer i = 0; i < size; i++) {
				Map<String, String> map = new HashMap<>();

				map.put("bigImgUrls", bigImgUrls[i]);
				Integer index = i+1;
				map.put("index", index.toString());
				
				result.append(TemplateService.formatTemplat(map, html));
				
			}
		
				

			this.internalTemplateImages = result.toString();
			return result.toString();
		}
	//底部推广
	public String bottomPromoteLayout(){
			this.bottomPromoteLayout=promoteLayout(bottomPromoteModels);
			return bottomPromoteLayout;
	}

	@Override
	public void setAdvertImg(String advertImg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAdvertUrl(String advertUrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSiteId(Long siteId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInPromoteModels(List<EBayPromoteModel> inPromoteModels) {
		// TODO Auto-generated method stub
		
	}
	
}
