package com.it.ocs.itemDescribe.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.it.ocs.itemDescribe.Base.BaseTemplate;
import com.it.ocs.itemDescribe.model.EBayInternalContentModel;
import com.it.ocs.itemDescribe.model.EBayPromoteModel;
import com.it.ocs.itemDescribe.model.EBayStoreAddUserEmailModel;
import com.it.ocs.itemDescribe.model.StoreCategoryModel;
import com.it.ocs.itemDescribe.structure.TemplateBStructure;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.ITemplateService;
import com.it.ocs.synchronou.service.impl.TemplateService;
import com.it.ocs.synchronou.util.ListUtil;
import com.it.ocs.synchronou.util.ValidationUtil;

public class TemplateC implements BaseTemplate {
	TemplateBStructure Structure = new TemplateBStructure();
	private ITemplateService templateService;
	private String topPromoteLayout;
	private List<EBayPromoteModel> topPromoteModels;
	private String inPromoteLayout;
	private List<EBayPromoteModel> inPromoteModels;
	private String storeUrl;
	private List<StoreCategoryModel> eBayStoreCategoryModels;
	private String storeCategory;
	private EBayInternalContentModel eBayInternalContentModel;
	private String internalTemplateImages;
	private  EBayStoreAddUserEmailModel eBayStoreAddUserEmailModel;
	private String bottomPromoteLayout;
	private List<EBayPromoteModel> bottomPromoteModels;
	private String advertImg;
	private String advertUrl;
	private Long siteId;
	@Override
	public String getTemplate() {
		storeCategoryModel();
		internalTemplateImages();
		topPromoteLayout();
		bottomPromoteLayout();
		inPromoteLayout();
		Map<String , String> map = new HashMap<>();
		TemplateModel templateModel = templateService.getTemplateContent("EBayItemDescribeTemplateC", "ebay");
		String html = templateModel.getContent();
		map.put("storeUrl", storeUrl);
		map.put("buttonName", eBayStoreAddUserEmailModel.getButtonName());
		map.put("topPromoteLayout", topPromoteLayout);
		map.put("storeCategory", storeCategory);
		map.put("middlePromote", inPromoteLayout);
		map.put("internalTemplateImages", internalTemplateImages);
		map.put("productTitle", eBayInternalContentModel.getTitle());
		map.put("description", eBayInternalContentModel.getProduceDescription());
		map.put("payment", eBayInternalContentModel.getPayment());
		map.put("shipment", eBayInternalContentModel.getShipment());
		map.put("aboutUs", eBayInternalContentModel.getAboutUs());
		map.put("return", eBayInternalContentModel.getReturns());
		map.put("faq", eBayInternalContentModel.getFaq());
		map.put("bottomPromoteLayout", bottomPromoteLayout);
		map.put("advert", advertImg);
		map.put("advertUrl", advertUrl);
		if(this.siteId.equals(77L)){
			map.put("paymentName", "Zahlung");
			map.put("shipmentName", "Lieferzeit");
			map.put("returnName", "Feedback");
			map.put("aboutUsName", "Kunenservice");
			map.put("FAQName", "Über Uns");
		}else{
			map.put("paymentName", "payment");
			map.put("shipmentName", "Shipment");
			map.put("returnName", "Return");
			map.put("aboutUsName", "About");
			map.put("FAQName", "FAQ");
		}
		html = TemplateService.formatTemplat(map, html);
		String appComment = eBayInternalContentModel.getAppComment();
		if(!ValidationUtil.isNullOrEmpty(appComment)){
			appComment ="<div vocab='http://schema.org/' typeof='Product'><span property='description'>"+appComment+"</span></div>";
		}else{
			appComment="";
		}
		//System.out.println(html);
		return appComment+html;
	}
	
	/**
	 * 描述：推广
	 * @param eBayPromoteModels
	 * @return
	 */
    public String promoteLayout(List<EBayPromoteModel> eBayPromoteModels) {
			TemplateModel templateModel = templateService.getTemplateContent("EBayTemplateCTopPromoteC", "ebay");
			String html = templateModel.getContent();
			StringBuffer result = new StringBuffer();
			List<EBayPromoteModel> eBayPromoteModels2= Lists.newArrayList();
			if(eBayPromoteModels.size()<8){
				eBayPromoteModels2 = ListUtil.getBeforeByCount(eBayPromoteModels, 4, ListUtil.RETAIN);
			}else{
				eBayPromoteModels2 = ListUtil.getBeforeByCount(eBayPromoteModels, 8,ListUtil.RETAIN);
			}
			for (EBayPromoteModel eBayPromoteModel : eBayPromoteModels2) {
				Map<String, String> map = new HashMap<>();
				map.put("imgUrl", eBayPromoteModel.getImgUrl());
				map.put("productAddress", eBayPromoteModel.getProductAddress());
				map.put("title", eBayPromoteModel.getTitle());
				String buyNow = eBayPromoteModel.getBuyNow();
				if(buyNow.equals("Chinese")){
					map.put("listingType", "<img  src=\"https://image.pushauction.com/140/140/c51cbac2-a574-4c90-811c-c6ad65ff28b2/15ab3394-7873-4e9f-aeda-0000419c3c72.jpg\" />");
				}else{
					map.put("listingType", "<img  src=\"https://image.pushauction.com/0/0/c51cbac2-a574-4c90-811c-c6ad65ff28b2/2460d809-cfcb-4313-a2ab-9b0d36db8894.jpg\" />");
				}
				map.put("price", eBayPromoteModel.getPrice());
				String oPrice = eBayPromoteModel.getOriginalPrice();
				if(oPrice==null){
					map.put("originalPrice","");
				}else{
					map.put("originalPrice",oPrice);
				}
			    
				result.append(TemplateService.formatTemplat(map, html));
			}
			
			return result.toString();
		}
	/**
	 * 描述：顶部推广
	 * @return
	 */
	public String topPromoteLayout(){
		if (topPromoteModels.size()<=0) {
			return "";
		}else{
			this.topPromoteLayout =  promoteLayout(topPromoteModels);
		}
		return this.topPromoteLayout;
	}
	
	//内部推广
	public String inPromoteLayout(){
		if (inPromoteModels.size()<=0) {
			return "";
		}else{
			this.inPromoteLayout = promoteLayout(inPromoteModels);
		}
		
		return inPromoteLayout;
	}
	public String getStoreUrl() {
		return storeUrl;
	}
	public void setTopPromoteLayout(String topPromoteLayout) {
		this.topPromoteLayout = topPromoteLayout;
	}
	public void setInPromoteLayout(String inPromoteLayout){
		this.inPromoteLayout = inPromoteLayout;
	}
	//商店分类
	public String storeCategoryModel(){
			StringBuffer result = new StringBuffer();
			for (StoreCategoryModel storeCategoryModel : eBayStoreCategoryModels) {
				result.append("<a href=\""+storeCategoryModel.getStoreUrl()+"\"><li><font>▶</font>&nbsp;&nbsp;<span>"+storeCategoryModel.getCategoryName()+"</span></li></a>");
			}
			this.storeCategory = result.toString();
			
			return storeCategory;
		}
	public void setStructure(TemplateBStructure structure) {
		Structure = structure;
	}
	//底部推广
		public String bottomPromoteLayout(){
			if (bottomPromoteModels.size()<=0) {
				return "";
			}else{
				this.bottomPromoteLayout =  promoteLayout(bottomPromoteModels);
			}
			return bottomPromoteLayout;
		}
	//内部图片
	public String internalTemplateImages(){
			TemplateModel templateModel = templateService.getTemplateContent("EBayTemplateCImages", "ebay");
			String html = templateModel.getContent();
			StringBuffer result = new StringBuffer();
			String[] bigImgUrls = eBayInternalContentModel.getBigImgUrl();
			if(bigImgUrls!=null){
				int size = bigImgUrls.length;
				for (Integer i = 0; i < size; i++) {
					Map<String, String> map = new HashMap<>();
		
					map.put("templateImage", bigImgUrls[i]);
					Integer index = i+1;
					map.put("index", index.toString());
				
					result.append(TemplateService.formatTemplat(map, html));
					
				}
			}
			this.internalTemplateImages = result.toString();
			return result.toString();
		}
	@Override
	public void setTopPromoteModel(List<EBayPromoteModel> topPromoteModels) {
		this.topPromoteModels = topPromoteModels;
	}
	public void seteBayStoreCategoryModels(List<StoreCategoryModel> eBayStoreCategoryModels) {
		this.eBayStoreCategoryModels = eBayStoreCategoryModels;
	}
	public void setStoreCategory(String storeCategory) {
		this.storeCategory = storeCategory;
	}
	public void seteBayInternalContentModel(EBayInternalContentModel eBayInternalContentModel) {
		this.eBayInternalContentModel = eBayInternalContentModel;
	}
	public void setInternalTemplateImages(String internalTemplateImages) {
		this.internalTemplateImages = internalTemplateImages;
	}
	public void seteBayStoreAddUserEmailModel(EBayStoreAddUserEmailModel eBayStoreAddUserEmailModel) {
		this.eBayStoreAddUserEmailModel = eBayStoreAddUserEmailModel;
	}
	public void setBottomPromoteLayout(String bottomPromoteLayout) {
		this.bottomPromoteLayout = bottomPromoteLayout;
	}
	public void setBottomPromoteModels(List<EBayPromoteModel> bottomPromoteModels) {
		this.bottomPromoteModels = bottomPromoteModels;
	}
	@Override
	public void setTemplateService(ITemplateService templateService) {
		this.templateService = templateService;

	}
	@Override
	public void setStoreUrl(String storeUrl) {
		

	}
	@Override
	public void setEBayStoreCategoryModels(List<StoreCategoryModel> storeCategoryModels) {
		this.eBayStoreCategoryModels = storeCategoryModels;
	}
	@Override
	public void setEBayInternalContentModel(EBayInternalContentModel eBayInternalContentModel) {
		this.eBayInternalContentModel = eBayInternalContentModel;
	}
	@Override
	public void setStoreAddUserEmailModel(EBayStoreAddUserEmailModel eBayStoreAddUserEmailModel) {
		this.eBayStoreAddUserEmailModel=eBayStoreAddUserEmailModel;
	}
	@Override
	public void setBottomPromoteModel(List<EBayPromoteModel> eBayPromoteModels) {
		this.bottomPromoteModels=eBayPromoteModels;
	}
	@Override
	public void setAdvertImg(String advertImg) {
		this.advertImg = advertImg;
	}


	@Override
	public void setAdvertUrl(String advertUrl) {
		this.advertUrl=advertUrl;
	}
	@Override
	public void setSiteId(Long siteId) {
		this.siteId= siteId;
	}
	@Override
	public void setInPromoteModels(List<EBayPromoteModel> inPromoteModels) {
		this.inPromoteModels = inPromoteModels;
	}

}
