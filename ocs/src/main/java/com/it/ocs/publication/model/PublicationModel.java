package com.it.ocs.publication.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.it.ocs.common.model.BaseModel;

public class PublicationModel extends BaseModel {
	private String templateName;//范本名称
	private String ebayAccount;
	private Long siteId;
	private String siteName;
	private String publicationType;//刊登类型
	private String sku ;
	private String productTitle;//主标题
	private String bundledProduct;
	private String productFirstCategoryId;
	private String productSecondCategoryId;
	private String storeFirstCategoryId;
	private String storeSecondCategoryId;
	private String productProperties;
	private String topPromotionType;
	private String footerPromotionType;
	private String inPromotionType;
	private String counterType;
	private String openPageProtect;
	private String templateTitle;//ebay显示模板标题
	private String ebayImages;
	private String templateImages;
	private String appComment;
	private String comments;
	private String discount;
	private String individual;//是否向公众显示买家名称
	private String publicationDays;
	private String price;
	private String original_price;
	private String reserverPrice;
	private String buyoutPrice;
	private String auctionItem;
	private String secondTrading;
	private String secondTradinfo;
	
	private String paypaiAccount;
	private String supportPaypaiInfo;
	private String payDescription;
	
	
	private String policyType;  //退货政策
	private String returnDays;  //退货天数
	private String allowDelay;  //节假退货延期
	private String returnType;   //退货方式
	private String faretakeinhander;//退款承担者
	private String depreciationRate;//折旧费
	private String returnDescription;//退款政策描述
	private String transClauseExisPub;//运输条款是否已在刊登描述中
	private String calCulateinfo;//运费计算项
	private String domesticInfo;//国内运输
	private String domesticOptDay;//处理时间
	private String domesticShipType;//运输方式
	private String domesticShipCost;//运费
	private String domesticExtraCost;//额外每件加收
	private String domesticShipAkHiPr;//AK,HI,PR 额外收费
	private String internationInfo;//国际运输
	private String shipLocationIn;//运送范围
	private String shipLocationOver;//不运送的地方
	
	
	private String featureType;//广告特色
	private String tag;
	private String otherDescription;
	private String onlinePubcount;//保持最大在线刊登数量
	
	private String salesTax;
	private String productAddress;
	private String region;
	private String postCode;
	private String itemId;
	private String productSubtitle;
	private String ebayProductURL;
	
	private String allowAllbuyer;//是否允许所有买家购买
	private String outShipCountry;//主要运送地址在我的运送地址之外
	private String noPaypai;//没有ebay账号
	private String buyerReq1;//多少天内收到几个弃标方案
	private String buyerReq2;//多少天内收到几个违反政策检举
	private String buyerReq3;//信用指数
	private String buyerReq4;//在过去10天内曾出价或购买我的商品，以达到我设定的限制
	private String buyerReq41;//这项限制只适用于买家信用指数等于或低于
	
	private String productEAN;
	private String productISBN;
	private String productUPC;
	
	private String acceptBuyerCounter;//是否接受议价
	private String acceptBuyerCounterMin;
	private String acceptBuyerCounterMax;
	
	private String productCount;//产品数量
	private String sellerBaseCount;//销售比基数
	private String productQuantitySold;//销售数量
	private String productStartPrice;//起始价格
	private String productStatus;//物品状况
	private String publicationDate;//发布时间
	private String publicationDateEnd;//发布失效时间
	
	private String sellerDescription;//卖家描述
	private String store;//商店
	
	private List<Map> domesticTrans;//国内运输
	private List<Map> calCulateTrans;//国际运输
	
	private String oldDescription;//ebay存量数据描述
	private Date timing_publication_date;//定时发布时间
	private Date publication_date;
	private Date end_publication_date;
	private String ending_reason;//下线原因 
	private String variations;//多属性
	private Integer quantity_available;//产品的可销售数量
	private String variations_quantity_available;//多属性产品的销售数量信息
	private Integer  quantity_sold;
	private String  autoPay;
	private String product_mpn;
	private String product_brand;
	private String product_ust;
	private Integer day_number;//定时间隔天数
	private Integer of_number;//定时次数
	private Integer is_plan; //是否定时计划
	private Integer copy_number;//复制的数量
	private Long advert_id; //广告模板
	private String c_date; //创建时间
	private Long correlation_id;//关联
	private Date discount_start_date;//折扣开始时间
    private Date discount_end_date;//折扣结束时间
    private List<ShipOptionModel> shipOptionModels;
    
    
	
	public String getInPromotionType() {
		return inPromotionType;
	}
	public void setInPromotionType(String inPromotionType) {
		this.inPromotionType = inPromotionType;
	}
	public List<ShipOptionModel> getShipOptionModels() {
		return shipOptionModels;
	}
	public void setShipOptionModels(List<ShipOptionModel> shipOptionModels) {
		this.shipOptionModels = shipOptionModels;
	}
	public String getProduct_ust() {
		return product_ust;
	}
	public void setProduct_ust(String product_ust) {
		this.product_ust = product_ust;
	}
	public Date getDiscount_start_date() {
		return discount_start_date;
	}
	public void setDiscount_start_date(Date discount_start_date) {
		this.discount_start_date = discount_start_date;
	}
	public Date getDiscount_end_date() {
		return discount_end_date;
	}
	public void setDiscount_end_date(Date discount_end_date) {
		this.discount_end_date = discount_end_date;
	}
	public String getOriginal_price() {
		return original_price;
	}
	public void setOriginal_price(String original_price) {
		this.original_price = original_price;
	}
	private Long correlationCount;//关联在线的总数
	
	private String tNmae;
	
	private Long pre_count;
	
	private Long hit_count;
	
	private String ending_state;//下线状态
	
	
	
	
	public String getEnding_state() {
		return ending_state;
	}
	public void setEnding_state(String ending_state) {
		this.ending_state = ending_state;
	}
	public Long getHit_count() {
		return hit_count;
	}
	public void setHit_count(Long hit_count) {
		this.hit_count = hit_count;
	}
	public String getC_date() {
		return c_date;
	}
	public void setC_date(String c_date) {
		this.c_date = c_date;
	}
	public Long getPre_count() {
		return pre_count;
	}
	public void setPre_count(Long pre_count) {
		this.pre_count = pre_count;
	}
	public String gettNmae() {
		return tNmae;
	}
	public void settNmae(String tNmae) {
		this.tNmae = tNmae;
	}
	public Long getCorrelationCount() {
		return correlationCount;
	}
	public void setCorrelationCount(Long correlationCount) {
		this.correlationCount = correlationCount;
	}
	public Long getCorrelation_id() {
		return correlation_id;
	}
	public void setCorrelation_id(Long correlation_id) {
		this.correlation_id = correlation_id;
	}
	public Long getAdvert_id() {
		return advert_id;
	}
	public void setAdvert_id(Long advert_id) {
		this.advert_id = advert_id;
	}
	public Integer getCopy_number() {
		return copy_number;
	}
	public void setCopy_number(Integer copy_number) {
		this.copy_number = copy_number;
	}
	public Integer getDay_number() {
		return day_number;
	}
	public void setDay_number(Integer day_number) {
		this.day_number = day_number;
	}
	public Integer getOf_number() {
		return of_number;
	}
	public void setOf_number(Integer of_number) {
		this.of_number = of_number;
	}
	public Integer getIs_plan() {
		return is_plan;
	}
	public void setIs_plan(Integer is_plan) {
		this.is_plan = is_plan;
	}
	public String getProduct_mpn() {
		return product_mpn;
	}
	public void setProduct_mpn(String product_mpn) {
		this.product_mpn = product_mpn;
	}
	public String getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}
	public Integer getQuantity_sold() {
		return quantity_sold;
	}
	public void setQuantity_sold(Integer quantity_sold) {
		this.quantity_sold = quantity_sold;
	}
	public Integer getQuantity_available() {
		return quantity_available;
	}
	public void setQuantity_available(Integer quantity_available) {
		this.quantity_available = quantity_available;
	}
	public String getVariations_quantity_available() {
		return variations_quantity_available;
	}
	public void setVariations_quantity_available(String variations_quantity_available) {
		this.variations_quantity_available = variations_quantity_available;
	}
	public String getEnding_reason() {
		return ending_reason;
	}
	public void setEnding_reason(String ending_reason) {
		this.ending_reason = ending_reason;
	}
	public Date getEnd_publication_date() {
		return end_publication_date;
	}
	public void setEnd_publication_date(Date end_publication_date) {
		this.end_publication_date = end_publication_date;
	}
	public Date getPublication_date() {
		return publication_date;
	}
	public void setPublication_date(Date publication_date) {
		this.publication_date = publication_date;
	}
	public Date getTiming_publication_date() {
		return timing_publication_date;
	}
	public void setTiming_publication_date(Date timing_publication_date) {
		this.timing_publication_date = timing_publication_date;
	}
	public void setTiming_publication_date(Timestamp timing_publication_date) {
		this.timing_publication_date = timing_publication_date;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getEbayAccount() {
		return ebayAccount;
	}
	public void setEbayAccount(String ebayAccount) {
		this.ebayAccount = ebayAccount;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getPublicationType() {
		return publicationType;
	}
	public void setPublicationType(String publicationType) {
		this.publicationType = publicationType;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getBundledProduct() {
		return bundledProduct;
	}
	public void setBundledProduct(String bundledProduct) {
		this.bundledProduct = bundledProduct;
	}
	public String getProductFirstCategoryId() {
		return productFirstCategoryId;
	}
	public void setProductFirstCategoryId(String productFirstCategoryId) {
		this.productFirstCategoryId = productFirstCategoryId;
	}
	public String getProductSecondCategoryId() {
		return productSecondCategoryId;
	}
	public void setProductSecondCategoryId(String productSecondCategoryId) {
		this.productSecondCategoryId = productSecondCategoryId;
	}
	public String getStoreFirstCategoryId() {
		return storeFirstCategoryId;
	}
	public void setStoreFirstCategoryId(String storeFirstCategoryId) {
		this.storeFirstCategoryId = storeFirstCategoryId;
	}
	public String getStoreSecondCategoryId() {
		return storeSecondCategoryId;
	}
	public void setStoreSecondCategoryId(String storeSecondCategoryId) {
		this.storeSecondCategoryId = storeSecondCategoryId;
	}
	public String getProductProperties() {
		return productProperties;
	}
	public void setProductProperties(String productProperties) {
		this.productProperties = productProperties;
	}
	public String getTopPromotionType() {
		return topPromotionType;
	}
	public void setTopPromotionType(String topPromotionType) {
		this.topPromotionType = topPromotionType;
	}
	public String getFooterPromotionType() {
		return footerPromotionType;
	}
	public void setFooterPromotionType(String footerPromotionType) {
		this.footerPromotionType = footerPromotionType;
	}
	public String getCounterType() {
		return counterType;
	}
	public void setCounterType(String counterType) {
		this.counterType = counterType;
	}
	public String getOpenPageProtect() {
		return openPageProtect;
	}
	public void setOpenPageProtect(String openPageProtect) {
		this.openPageProtect = openPageProtect;
	}
	public String getTemplateTitle() {
		return templateTitle;
	}
	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}
	public String getEbayImages() {
		return ebayImages;
	}
	public void setEbayImages(String ebayImages) {
		this.ebayImages = ebayImages;
	}
	public String getTemplateImages() {
		return templateImages;
	}
	public void setTemplateImages(String templateImages) {
		this.templateImages = templateImages;
	}
	public String getAppComment() {
		return appComment;
	}
	public void setAppComment(String appComment) {
		this.appComment = appComment;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getIndividual() {
		return individual;
	}
	public void setIndividual(String individual) {
		this.individual = individual;
	}
	public String getPublicationDays() {
		return publicationDays;
	}
	public void setPublicationDays(String publicationDays) {
		this.publicationDays = publicationDays;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getReserverPrice() {
		return reserverPrice;
	}
	public void setReserverPrice(String reserverPrice) {
		this.reserverPrice = reserverPrice;
	}
	public String getBuyoutPrice() {
		return buyoutPrice;
	}
	public void setBuyoutPrice(String buyoutPrice) {
		this.buyoutPrice = buyoutPrice;
	}
	public String getAuctionItem() {
		return auctionItem;
	}
	public void setAuctionItem(String auctionItem) {
		this.auctionItem = auctionItem;
	}
	public String getSecondTrading() {
		return secondTrading;
	}
	public void setSecondTrading(String secondTrading) {
		this.secondTrading = secondTrading;
	}
	public String getSecondTradinfo() {
		return secondTradinfo;
	}
	public void setSecondTradinfo(String secondTradinfo) {
		this.secondTradinfo = secondTradinfo;
	}
	public String getPaypaiAccount() {
		return paypaiAccount;
	}
	public void setPaypaiAccount(String paypaiAccount) {
		this.paypaiAccount = paypaiAccount;
	}
	public String getSupportPaypaiInfo() {
		return supportPaypaiInfo;
	}
	public void setSupportPaypaiInfo(String supportPaypaiInfo) {
		this.supportPaypaiInfo = supportPaypaiInfo;
	}
	public String getPayDescription() {
		return payDescription;
	}
	public void setPayDescription(String payDescription) {
		this.payDescription = payDescription;
	}
	public String getAllowAllbuyer() {
		return allowAllbuyer;
	}
	public void setAllowAllbuyer(String allowAllbuyer) {
		this.allowAllbuyer = allowAllbuyer;
	}
	public String getOutShipCountry() {
		return outShipCountry;
	}
	public void setOutShipCountry(String outShipCountry) {
		this.outShipCountry = outShipCountry;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getReturnDays() {
		return returnDays;
	}
	public void setReturnDays(String returnDays) {
		this.returnDays = returnDays;
	}
	public String getAllowDelay() {
		return allowDelay;
	}
	public void setAllowDelay(String allowDelay) {
		this.allowDelay = allowDelay;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getFaretakeinhander() {
		return faretakeinhander;
	}
	public void setFaretakeinhander(String faretakeinhander) {
		this.faretakeinhander = faretakeinhander;
	}
	public String getDepreciationRate() {
		return depreciationRate;
	}
	public void setDepreciationRate(String depreciationRate) {
		this.depreciationRate = depreciationRate;
	}
	public String getReturnDescription() {
		return returnDescription;
	}
	public void setReturnDescription(String returnDescription) {
		this.returnDescription = returnDescription;
	}
	public String getTransClauseExisPub() {
		return transClauseExisPub;
	}
	public void setTransClauseExisPub(String transClauseExisPub) {
		this.transClauseExisPub = transClauseExisPub;
	}
	public String getCalCulateinfo() {
		return calCulateinfo;
	}
	public void setCalCulateinfo(String calCulateinfo) {
		this.calCulateinfo = calCulateinfo;
	}
	public String getDomesticInfo() {
		return domesticInfo;
	}
	public void setDomesticInfo(String domesticInfo) {
		this.domesticInfo = domesticInfo;
	}
	public String getDomesticOptDay() {
		return domesticOptDay;
	}
	public void setDomesticOptDay(String domesticOptDay) {
		this.domesticOptDay = domesticOptDay;
	}
	public String getDomesticShipType() {
		return domesticShipType;
	}
	public void setDomesticShipType(String domesticShipType) {
		this.domesticShipType = domesticShipType;
	}
	public String getDomesticShipCost() {
		return domesticShipCost;
	}
	public void setDomesticShipCost(String domesticShipCost) {
		this.domesticShipCost = domesticShipCost;
	}
	public String getDomesticExtraCost() {
		return domesticExtraCost;
	}
	public void setDomesticExtraCost(String domesticExtraCost) {
		this.domesticExtraCost = domesticExtraCost;
	}
	public String getDomesticShipAkHiPr() {
		return domesticShipAkHiPr;
	}
	public void setDomesticShipAkHiPr(String domesticShipAkHiPr) {
		this.domesticShipAkHiPr = domesticShipAkHiPr;
	}
	public String getInternationInfo() {
		return internationInfo;
	}
	public void setInternationInfo(String internationInfo) {
		this.internationInfo = internationInfo;
	}
	public String getShipLocationIn() {
		return shipLocationIn;
	}
	public void setShipLocationIn(String shipLocationIn) {
		this.shipLocationIn = shipLocationIn;
	}
	public String getShipLocationOver() {
		return shipLocationOver;
	}
	public void setShipLocationOver(String shipLocationOver) {
		this.shipLocationOver = shipLocationOver;
	}
	public String getFeatureType() {
		return featureType;
	}
	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getOtherDescription() {
		return otherDescription;
	}
	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}
	public String getOnlinePubcount() {
		return onlinePubcount;
	}
	public void setOnlinePubcount(String onlinePubcount) {
		this.onlinePubcount = onlinePubcount;
	}
	public String getSalesTax() {
		return salesTax;
	}
	public void setSalesTax(String salesTax) {
		this.salesTax = salesTax;
	}
	public String getProductAddress() {
		return productAddress;
	}
	public void setProductAddress(String productAddress) {
		this.productAddress = productAddress;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getProductSubtitle() {
		return productSubtitle;
	}
	public void setProductSubtitle(String productSubtitle) {
		this.productSubtitle = productSubtitle;
	}
	public String getEbayProductURL() {
		return ebayProductURL;
	}
	public void setEbayProductURL(String ebayProductURL) {
		this.ebayProductURL = ebayProductURL;
	}
	public String getNoPaypai() {
		return noPaypai;
	}
	public void setNoPaypai(String noPaypai) {
		this.noPaypai = noPaypai;
	}
	public String getBuyerReq1() {
		return buyerReq1;
	}
	public void setBuyerReq1(String buyerReq1) {
		this.buyerReq1 = buyerReq1;
	}
	public String getBuyerReq2() {
		return buyerReq2;
	}
	public void setBuyerReq2(String buyerReq2) {
		this.buyerReq2 = buyerReq2;
	}
	public String getBuyerReq3() {
		return buyerReq3;
	}
	public void setBuyerReq3(String buyerReq3) {
		this.buyerReq3 = buyerReq3;
	}
	public String getBuyerReq4() {
		return buyerReq4;
	}
	public void setBuyerReq4(String buyerReq4) {
		this.buyerReq4 = buyerReq4;
	}
	public String getBuyerReq41() {
		return buyerReq41;
	}
	public void setBuyerReq41(String buyerReq41) {
		this.buyerReq41 = buyerReq41;
	}
	public String getProductEAN() {
		return productEAN;
	}
	public void setProductEAN(String productEAN) {
		this.productEAN = productEAN;
	}
	public String getProductISBN() {
		return productISBN;
	}
	public void setProductISBN(String productISBN) {
		this.productISBN = productISBN;
	}
	public String getProductUPC() {
		return productUPC;
	}
	public void setProductUPC(String productUPC) {
		this.productUPC = productUPC;
	}
	public String getAcceptBuyerCounter() {
		return acceptBuyerCounter;
	}
	public void setAcceptBuyerCounter(String acceptBuyerCounter) {
		this.acceptBuyerCounter = acceptBuyerCounter;
	}
	public String getAcceptBuyerCounterMin() {
		return acceptBuyerCounterMin;
	}
	public void setAcceptBuyerCounterMin(String acceptBuyerCounterMin) {
		this.acceptBuyerCounterMin = acceptBuyerCounterMin;
	}
	public String getAcceptBuyerCounterMax() {
		return acceptBuyerCounterMax;
	}
	public void setAcceptBuyerCounterMax(String acceptBuyerCounterMax) {
		this.acceptBuyerCounterMax = acceptBuyerCounterMax;
	}
	public String getProductCount() {
		return productCount;
	}
	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
	public String getSellerBaseCount() {
		return sellerBaseCount;
	}
	public void setSellerBaseCount(String sellerBaseCount) {
		this.sellerBaseCount = sellerBaseCount;
	}
	public String getProductQuantitySold() {
		return productQuantitySold;
	}
	public void setProductQuantitySold(String productQuantitySold) {
		this.productQuantitySold = productQuantitySold;
	}
	public String getProductStartPrice() {
		return productStartPrice;
	}
	public void setProductStartPrice(String productStartPrice) {
		this.productStartPrice = productStartPrice;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public List<Map> getDomesticTrans() {
		return domesticTrans;
	}
	public void setDomesticTrans(List<Map> domesticTrans) {
		this.domesticTrans = domesticTrans;
	}
	public List<Map> getCalCulateTrans() {
		return calCulateTrans;
	}
	public void setCalCulateTrans(List<Map> calCulateTrans) {
		this.calCulateTrans = calCulateTrans;
	}
	public String getSellerDescription() {
		return sellerDescription;
	}
	public void setSellerDescription(String sellerDescription) {
		this.sellerDescription = sellerDescription;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
	public String getPublicationDateEnd() {
		return publicationDateEnd;
	}
	public void setPublicationDateEnd(String publicationDateEnd) {
		this.publicationDateEnd = publicationDateEnd;
	}
	public String getOldDescription() {
		return oldDescription;
	}
	public void setOldDescription(String oldDescription) {
		this.oldDescription = oldDescription;
	}
	public String getVariations() {
		return variations;
	}
	public void setVariations(String variations) {
		this.variations = variations;
	}
	public String getAutoPay() {
		return autoPay;
	}
	public void setAutoPay(String autoPay) {
		this.autoPay = autoPay;
	}
	
	
}
