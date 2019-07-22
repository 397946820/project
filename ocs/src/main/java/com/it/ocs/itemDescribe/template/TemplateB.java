package com.it.ocs.itemDescribe.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import com.it.ocs.itemDescribe.Base.BaseTemplate;
import com.it.ocs.itemDescribe.model.EBayInternalContentModel;
import com.it.ocs.itemDescribe.model.EBayPromoteModel;
import com.it.ocs.itemDescribe.model.EBayStoreAddUserEmailModel;
import com.it.ocs.itemDescribe.model.StoreCategoryModel;
import com.it.ocs.itemDescribe.structure.TemplateBStructure;
import com.it.ocs.synchronou.model.EBayStoreCategoryModel;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.ITemplateService;
import com.it.ocs.synchronou.service.impl.TemplateService;



public class TemplateB implements BaseTemplate{
	TemplateBStructure Structure = new TemplateBStructure();
	private ITemplateService templateService;
	private String topPromoteLayout;
	private List<EBayPromoteModel> topPromoteModels;
	private String storeUrl;
	private List<StoreCategoryModel> eBayStoreCategoryModels;
	private String storeCategory;
	private EBayInternalContentModel eBayInternalContentModel;
	private String internalTemplateImages;
	private  EBayStoreAddUserEmailModel eBayStoreAddUserEmailModel;
	private String bottomPromoteLayout;
	private List<EBayPromoteModel> bottomPromoteModels;
	
	
	@Override
	public void setTopPromoteModel(List<EBayPromoteModel> topPromoteModel) {
		// TODO Auto-generated method stub
		this.topPromoteModels = topPromoteModel;
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
	public void setEBayInternalContentModel(EBayInternalContentModel eBayInternalContentModel){
		this.eBayInternalContentModel = eBayInternalContentModel;
	}
	@Override
	public void setEBayStoreCategoryModels(List<StoreCategoryModel> storeCategoryModels) {
		// TODO Auto-generated method stub
		this.eBayStoreCategoryModels = storeCategoryModels;
	}
	@Override
	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}

	@Override
	public void setTemplateService(ITemplateService templateService) {
		this.templateService = templateService;
	}

	public void setStructure(TemplateBStructure structure) {
		Structure = structure;
	}


	public void setTopPromoteLayout(String topPromoteLayout) {
		this.topPromoteLayout = topPromoteLayout;
	}
	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		
		storeCategoryModel();
		internalTemplateImages();
		topPromoteLayout();
		bottomPromoteLayout();
		Map<String , String> map = new HashMap<>();
		TemplateModel templateModel = templateService.getTemplateContent("EBayItemDescribeTemplateB", "ebay");
		String html = templateModel.getContent();
		System.out.println(html);
		map.put("storeUrl", storeUrl);
		map.put("buttonName", eBayStoreAddUserEmailModel.getButtonName());
		
		map.put("topPromoteLayout", topPromoteLayout);
		map.put("storeCategory", storeCategory);
		map.put("internalTemplateImages", internalTemplateImages);
		map.put("productTitle", eBayInternalContentModel.getTitle());
		//map.put("middlePromote", null);
		map.put("description", eBayInternalContentModel.getProduceDescription());
		map.put("payment", eBayInternalContentModel.getPayment());
		map.put("shipment", eBayInternalContentModel.getShipment());
		map.put("aboutUs", eBayInternalContentModel.getAboutUs());
		map.put("return", eBayInternalContentModel.getReturns());
		map.put("faq", eBayInternalContentModel.getFaq());
		map.put("bottomPromoteLayout", bottomPromoteLayout);
		html = TemplateService.formatTemplat(map, html);
		System.out.println(html);
		return html;
	}
	//推广
	public String promoteLayout(List<EBayPromoteModel> eBayPromoteModels) {
		// TODO Auto-generated method stub
		TemplateModel templateModel = templateService.getTemplateContent("EBayTemplateBTopPromote", "ebay");
		String html = templateModel.getContent();
		StringBuffer result = new StringBuffer();
		for (EBayPromoteModel eBayPromoteModel : eBayPromoteModels) {
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
			result.append(TemplateService.formatTemplat(map, html));
		}
		
		return result.toString();
	}
    //商店分类
	public String storeCategoryModel(){
		StringBuffer result = new StringBuffer();
		for (StoreCategoryModel storeCategoryModel : eBayStoreCategoryModels) {
			result.append("<li><a href=\""+storeCategoryModel.getStoreUrl()+"\">"+storeCategoryModel.getCategoryName()+"</a></li>");
			
		}
		this.storeCategory = result.toString();
		
		return storeCategory;
	}
	//内部图片
	public String internalTemplateImages(){
		TemplateModel templateModel = templateService.getTemplateContent("EBayTemplateBImages", "ebay");
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
	//顶部推广
	public String topPromoteLayout(){
		this.topPromoteLayout =  promoteLayout(topPromoteModels);
		return topPromoteLayout;
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
