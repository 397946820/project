package com.it.ocs.itemDescribe.structure;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.session.ResultHandler;

import com.google.common.collect.Lists;
import com.it.ocs.itemDescribe.Base.BaseStructure;
import com.it.ocs.itemDescribe.css.TemplateACss;
import com.it.ocs.itemDescribe.model.EBayInternalContentModel;
import com.it.ocs.itemDescribe.model.EBayPromoteModel;
import com.it.ocs.itemDescribe.model.EBayStoreAddUserEmailModel;
import com.it.ocs.itemDescribe.model.StoreCategoryModel;
import com.it.ocs.synchronou.util.ValidationUtil;

public class TemplateAStructure implements BaseStructure {
	public String header;
	public String middleHeader;
	public String leftLayout;
	public String internalContentLayout;
	public String middleSection;
	public String middleEnd;
	public String end;
	@Override
	public String getTemplate(){
		TemplateACss templateACss = new TemplateACss();
		StringBuffer result = new StringBuffer();
		result.append("<!DOCTYPE html><html><head><meta charset=\"utf-8\" /><style type=\"text/css\">"
				+ templateACss.getCss()
				+ "</style></head><body><div id=\"body-body\">");
		result.append(header);
		result.append(middleHeader);
		result.append(middleSection);
		result.append(middleEnd);
		result.append(end);
		result.append("</div></body></html>");
		return result.toString();
	}
	
	@Override
	public String createHeaderPromoteLayout(List<EBayPromoteModel> eBayPromoteModels) {
		// TODO Auto-generated method stub
		StringBuffer headerPromote = new StringBuffer();
		headerPromote.append("<header><ul class=\"header-pro after\">");
		List<EBayPromoteModel> result = Lists.newArrayList();
		if (eBayPromoteModels.size()>6) {
			for(int i=0 ; i<6;i++){
				result.add(eBayPromoteModels.get(i));
			}
		}else{
			result=eBayPromoteModels;
		}
		for (EBayPromoteModel eBayPromoteModel : result) {
			headerPromote.append("<a href=\"");
			headerPromote.append(eBayPromoteModel.getProductAddress());
			headerPromote.append("\" target=\"_blank\">");
			headerPromote.append("<li class=\"pro left\"><div class=\"mask\"></div><img src=\"");
			headerPromote.append(eBayPromoteModel.getImgUrl());
			headerPromote.append("\"/><div class=\"pro-info\">");
			headerPromote.append("<span>");
			headerPromote.append(eBayPromoteModel.getTitle());
			headerPromote.append("</span></a></div><div class=\"price\">");
			headerPromote.append(eBayPromoteModel.getPrice());
			headerPromote.append("</div><div class=\"buynow\">");
			headerPromote.append(eBayPromoteModel.getBuyNow());
			headerPromote.append("</div><div class=\"Freeshipping\">");
			headerPromote.append(eBayPromoteModel.getFreeShipping());
			headerPromote.append("</div></li>");
			headerPromote.append("</a>");
			
		}
		
		headerPromote.append("</ul></header>");
		this.header = headerPromote.toString();
		return header;
	}

	@Override
	public String createStoreCategoryLayout(List<StoreCategoryModel> eBayStoreCategoryModels) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		
		result.append("<ul class=\"store_category_nav left\"><div class=\"pro_mesage_left_title\">store_category</div>");
		
		for (StoreCategoryModel eBayStoreCategoryModel : eBayStoreCategoryModels) {
			
			result.append("<li><a href=\"");
			result.append(eBayStoreCategoryModel.getStoreUrl());
			result.append("\"><div class=\"store_category_nav_icon left\"></div><span class=\"store_category_nav_mes\">");
			result.append(eBayStoreCategoryModel.getCategoryName());
			result.append("</span></a></li>");
		}
		result.append("</ul>");
		
		return result.toString();
	}

	@Override
	public String createStoreAddUserEmailLayout(EBayStoreAddUserEmailModel storeAddUserEmailModel) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		result.append("<div class=\"pro_mesage_left2 left\"><p>");
		result.append(storeAddUserEmailModel.getInstruction());
		result.append("</p><a target=\"_blank\" href=\"");
		result.append(storeAddUserEmailModel.getSignUrl());
		result.append("\"><div class=\"sign\">"+storeAddUserEmailModel.getButtonName()+"</div></a></div>");
		return result.toString();
	}

	@Override
	public String createTemplateInternalPromotelLayout(List<EBayPromoteModel> promoteModels) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		result.append("<ul class=\"also_like_nav left\"><div class=\"pro_mesage_left_title\">you may also like</div>");
		
		for (EBayPromoteModel eBayPromoteModel : promoteModels) {
			
			result.append("<li class=\"also_like_nav_mes\"><img src=\"");
			result.append(eBayPromoteModel.getImgUrl());
			result.append("\"/><p class=\"also_like_mes\"><a href=\"");
			result.append(eBayPromoteModel.getProductAddress());
			result.append("\"><span>");
			result.append(eBayPromoteModel.getTitle());
			result.append("</span></a></p><p class=\"also_like_price\"><a href=\"");
			result.append(eBayPromoteModel.getProductAddress());
			result.append("\"><span>");
			result.append(eBayPromoteModel.getPrice());
			result.append("</span></a></p><p class=\"also_like_price\"><a href=\"");
			result.append(eBayPromoteModel.getProductAddress());
			result.append("\"><span>");
			result.append(eBayPromoteModel.getBuyNow());
			result.append("</span></a></p><p class=\"also_like_price\"><a href=\"");
			result.append(eBayPromoteModel.getProductAddress());
			result.append("\"><span>");
			result.append(eBayPromoteModel.getFreeShipping());
			result.append("</span></a></p></li>");
		}
		result.append("</ul>");
		
		return result.toString();
	}

	@Override
	public String createEndPromotelLayout(List<EBayPromoteModel> promoteModels) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		result.append("<ul class=\"footer_pro after\">");
		List<EBayPromoteModel> promoteModels2 = Lists.newArrayList();
		if (promoteModels2.size()>6) {
			for(int i=0 ; i<6;i++){
				promoteModels2.add(promoteModels.get(i));
			}
		}else{
			promoteModels2=promoteModels;
		}
		for (EBayPromoteModel eBayPromoteModel : promoteModels2) {
			result.append("<a href=\"");
			result.append(eBayPromoteModel.getProductAddress());
			result.append("\" target=\"_blank\">");
			result.append("<li class=\"pro left\"><div class=\"mask\"></div><img src=\"");
			result.append(eBayPromoteModel.getImgUrl());
			result.append("\"/><div class=\"pro-info\">");
			
			result.append("<span>");
			result.append(eBayPromoteModel.getTitle());
			result.append("</span></a></div><div class=\"price\">");
			result.append(eBayPromoteModel.getPrice());
			result.append("</div><div class=\"buynow\">");
			result.append(eBayPromoteModel.getBuyNow());
			result.append("</div><div class=\"Freeshipping\">");
			result.append(eBayPromoteModel.getFreeShipping());
			result.append("</div></li>");
			result.append("</a>");
		}
		result.append("</ul>");
		return result.toString();
	}

	@Override
	public String createEndLayout(List<EBayPromoteModel> promoteModels) {
		// TODO Auto-generated method stub
		StringBuffer resutl = new StringBuffer();
		resutl.append("</div><div class=\"article_footer after\">");
		resutl.append("<div class=\"copy_right\"><p>Payment Shipping Return policy About usFAQ</p><p>Payment Shipping Return policy About usFAQ sasgd hgfhd</p></div>");
		resutl.append("<div class=\"facebook\"><div class=\"facebook_title\"><strong>facebook</strong></div><ul><li class=\"after facebook_content\"><div class=\"facebook-left left after \"><div class=\"icon left\"><img src=\"http://q.ebaystatic.com/aw/pics/icon/iconPos_16x16.gif\"/></div><strong class=\"left\"><span>siper faste delivery,thanks</span></strong>	<br /><div class=\"date left\"><span>jul-03-2017- &nbsp;11:55</span></div></div><div class=\"facebook-right right after\">s******n</div></li></ul></div>	");
		resutl.append("<div class=\"grey_line\"></div>");
		String promoteLayout = createEndPromotelLayout(promoteModels);
		resutl.append(promoteLayout);
		resutl.append("</div>");
		this.middleEnd = resutl.toString();
		return middleEnd;
	}

	@Override
	public String createLastLayout() {
		// TODO Auto-generated method stub
		StringBuffer resutl = new StringBuffer();
		resutl.append("<footer><div class=\"powered\"></div><div class=\"DataCaciques\"><img src=\"https://gate.datacaciques.com/track/img?_lid=151927866548\"/></div></footer>");
		//<img src=\"https://image.pushauction.com/poweredbypushauction.png\"/>
		
		this.end=resutl.toString();
		return end;
	}

	@Override
	public String createInternalContentLayout(EBayInternalContentModel internalContentModel) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		result.append("<div class=\"section_body right after\"><div class=\"pro_display\"><p class=\"introduce\">");
		result.append(internalContentModel.getTitle());
		result.append("</p><div id=\"store_category_content\"><div class=\"container_tab\" >");
		//String[] smallImgUrls = internalContentModel.getSmallImgUrl();
		String[] bigImgUrls = internalContentModel.getBigImgUrl();
		if(!ValidationUtil.isNull(bigImgUrls)){
		for(int i=0;i<bigImgUrls.length;i++){
			
			result.append("<div id=\"c"+(i+1)+"\"><a href=\"#c"+(i+1)+"\" ><div class=\"mask2\"></div><img src=\"");
			result.append(bigImgUrls[i]);
			result.append("\"/></a><div class=\"tab-content\" ><img src=\"");
			result.append(bigImgUrls[i]);
			result.append("\"/></div></div>");
			
		}
		}
		result.append("</div></div></div>");
		result.append("<div class=\"pro_description left after\"><div class=\"produce_descri_title left\"><span >Produce Description</span></div><div class=\"pro_display2 left\">	<div class=\"produce_descri\">");
		result.append(internalContentModel.getProduceDescription());
		result.append("</div></div></div>");
		result.append("<div class=\"pro_description left after\"><div class=\"produce_descri_title left\"><span >Payment</span></div><div class=\"pro_display2 left\"><div class=\"produce_descri\">");
		result.append(internalContentModel.getPayment());
		result.append("</div></div>");
		result.append("<div class=\"produce_descri_title left\"><span >Shipment</span></div><div class=\"pro_display2 left\">	<div class=\"produce_descri\">");
		result.append(internalContentModel.getShipment());
		result.append("</div></div></div>");
		
		result.append("<div class=\"pro_description left after\"><div class=\"produce_descri_title left\"><span >Returns</span></div><div class=\"pro_display2 left\"><div class=\"produce_descri\">");
		result.append(internalContentModel.getReturns());
		result.append("</div></div></div>");
		result.append("	<div class=\"pro_description left after\"><div class=\"produce_descri_title left\"><span >About Us</span></div><div class=\"pro_display2 left\"><div class=\"produce_descri\">");
		result.append(internalContentModel.getAboutUs());
		result.append("</div></div></div>");
		result.append("<div class=\"pro_description left after faq\"><div class=\"produce_descri_title left\"><span >FAQ</span></div><div class=\"pro_display2 left\"><div class=\"produce_descri\">");
		result.append(internalContentModel.getFaq());
		result.append("</div></div></div></div>");
		this.internalContentLayout= result.toString();
		return internalContentLayout;
	}

	@Override
	public String createMiddleTopLayout(String storeUrl) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		result.append("<div class=\"banner\"><div class=\"banner_area\"><ul class=\"banner_nav left after\"><li class=\"left\"><a href=\"");
		result.append(storeUrl);
		result.append("\">Payment</a></li><li class=\"left\"><a href=\"");
		result.append(storeUrl);
		result.append("\">Shipping</a></li><li class=\"left\"><a href=\"");
		result.append(storeUrl);
		result.append("\">Return</a></li><li class=\"left\"><a href=\"");
		result.append(storeUrl);
		result.append("\">About us</a></li><li class=\"left\"><a href=\"");
		result.append(storeUrl);
		result.append("\">FAQ</a></li></ul>");
	    result.append("<div class=\"logo\"><img src=\"https://timage.pushauction.com/v3/DefaultTemplate/A16285/logo.png\" alt=\"logo\" /></div>");
	    result.append("<div class=\"banner_mes right\"><img src=\"https://timage.pushauction.com/v3/DefaultTemplate/A16285/top_banner.png\"/></div></div></div>");
		this.middleHeader=result.toString();
	    return middleHeader;
	}

	@Override
	public String createMiddleContentLayout(String leftLayout, String internalContentLayout) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		result.append("<div class=\"section_background\"><div class=\"section_area  after\">");
		result.append(leftLayout);
		result.append(internalContentLayout);
		result.append("</div>");
		this.middleSection = result.toString(); 
		return middleSection;
	}

	@Override
	public String createLeftLayout(List<StoreCategoryModel> storeCategoryModels, EBayStoreAddUserEmailModel userEmail,
			List<EBayPromoteModel> promoteModels) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		result.append("<aside class=\"left\">");
		String storeCategoryLayout = createStoreCategoryLayout(storeCategoryModels);
		String userEmailLayout = createStoreAddUserEmailLayout(userEmail);
		//String internalPromotelLayout	= createTemplateInternalPromotelLayout(promoteModels, structure);
		result.append(storeCategoryLayout);
		result.append(userEmailLayout);
		//result.append(internalPromotelLayout);
		result.append("</aside>");
		this.leftLayout=result.toString();
		return leftLayout;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getMiddleHeader() {
		return middleHeader;
	}

	public void setMiddleHeader(String middleHeader) {
		this.middleHeader = middleHeader;
	}

	public String getLeftLayout() {
		return leftLayout;
	}

	public void setLeftLayout(String leftLayout) {
		this.leftLayout = leftLayout;
	}

	public String getInternalContentLayout() {
		return internalContentLayout;
	}

	public void setInternalContentLayout(String internalContentLayout) {
		this.internalContentLayout = internalContentLayout;
	}

	public String getMiddleSection() {
		return middleSection;
	}

	public void setMiddleSection(String middleSection) {
		this.middleSection = middleSection;
	}

	public String getMiddleEnd() {
		return middleEnd;
	}

	public void setMiddleEnd(String middleEnd) {
		this.middleEnd = middleEnd;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	
}
