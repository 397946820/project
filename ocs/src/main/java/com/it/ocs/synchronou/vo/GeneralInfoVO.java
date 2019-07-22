//package com.it.ocs.synchronou.vo;
//
//import java.util.List;
//
//import com.ebay.soap.eBLBaseComponents.AmountType;
//import com.ebay.soap.eBLBaseComponents.BuyerPaymentMethodCodeType;
//import com.ebay.soap.eBLBaseComponents.CountryCodeType;
//import com.ebay.soap.eBLBaseComponents.CurrencyCodeType;
//import com.ebay.soap.eBLBaseComponents.ListingTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.SiteCodeType;
//
///**
// * 
// * @author yangguanbao
// * 描述：item的一般信息VO/item 的一级标签
// */
//public class GeneralInfoVO {
//
//	private String country;
//	private String buyItNowPrice;
//	private String reservePrice;
//	private String startPrice;
//	private AmountType classifiedAdPayPerLeadFee;
//	private AmountType buyerGuaranteePrice;
//	private AmountType floorPrice;
//	private AmountType ceilingPrice;
//	private String applicationData;
//	
//	private Boolean autoPay;
//	private Boolean motorsGermanySearchable;
//	private Boolean categoryMappingAllowed;
//	private String description;
//	private Integer giftIcon;
//	private String itemID;
//	private String listingDuration;
//	private String location;
//	private Integer lotSize;
//	private String partnerCode;
//	private String partnerName;
//	private String payPalEmailAddress;
//	private Boolean privateListing;
//	private Integer quantity;
//	private String privateNotes;
//	private String regionID;
//	private Boolean relistLink;
//	private String subTitle;
//	private String title;
//	private String UUID;
//	private String sellerVacationNote;
//	private Long watchCount;
//	private Long hitCount;
//	private Boolean disableBuyerRequirements;
//	private Boolean locationDefaulted;
//	private Boolean thirdPartyCheckout;
//	private Boolean useTaxTable;
//	private Boolean getItFast;
//	private Boolean buyerResponsibleForShipping;
//	private Boolean limitedWarrantyEligible;
//	private String eBayNotes;
//	private Long questionCount;
//	private Boolean relisted;
//	private Integer quantityAvailable;
//	private String SKU;
//	private Boolean categoryBasedAttributesPrefill;
//	private String postalCode;
//	private Boolean shippingTermsInDescription;
//	private String sellerInventoryID;
//	private Integer dispatchTimeMax;
//	private Boolean skypeEnabled;
//	private String skypeID;
//	private Boolean bestOfferEnabled;
//	private Boolean localListing;
//	private Boolean thirdPartyCheckoutIntegration;
//	private Long totalQuestionCount;
//	private Boolean proxyItem;
//	private Integer leadCount;
//	private Integer newLeadCount;
//	private String groupCategoryID;
//	private Boolean bidGroupItem;
//	private Boolean mechanicalCheckAccepted;
//	private Boolean updateSellerInfo;
//	private Boolean updateReturnPolicy;
//	private Boolean integratedMerchantCreditCardEnabled;
//	private Integer itemCompatibilityCount;
//	private Integer conditionID;
//	private String conditionDescription;
//	private String conditionDisplayName;
//	private String taxCategory;
//	private Integer quantityThreshold;
//	private Boolean postCheckoutExperienceEnabled;
//	private Boolean useRecommendedProduct;
//	private String sellerProvidedTitle;
//	private String VIN;
//	private String vinLink;
//	private String VRM;
//	private String vrmLink;
//	private Boolean topRatedListing;
//	private Boolean isIntermediatedShippingEligible;
//	private Long relistParentID;
//	private String conditionDefinition;
//	private Boolean hideFromSearch;
//	private Boolean includeRecommendations;
//	private Boolean eBayNowEligible;
//	private Boolean eBayNowAvailable;
//	private Boolean ignoreQuantity;
//	private Boolean availableForPickupDropOff;
//	private Boolean eligibleForPickupDropOff;
//	private Boolean liveAuction;
//	private Boolean eBayPlus;
//	private Boolean eBayPlusEligible;
//	private Boolean eMailDeliveryAvailable;
//	private String currency;
//	private String listingType;
//	//private List<BuyerPaymentMethodCodeType> paymentMethods;
//	private String paymentMethods;
//	private String site;
//	
//	
//	
//	public String getSite() {
//		return site;
//	}
//	public void setSite(String site) {
//		this.site = site;
//	}
//	public String getPaymentMethods() {
//		return paymentMethods;
//	}
//	public void setPaymentMethods(String paymentMethods) {
//		this.paymentMethods = paymentMethods;
//	}
//	/*public List<BuyerPaymentMethodCodeType> getPaymentMethods() {
//		return paymentMethods;
//	}
//	public void setPaymentMethods(List<BuyerPaymentMethodCodeType> paymentMethods) {
//		this.paymentMethods = paymentMethods;
//	}*/
//	
//	
//	public String getListingType() {
//		return listingType;
//	}
//	public String getCountry() {
//		return country;
//	}
//	public void setCountry(String country) {
//		this.country = country;
//	}
//	public String getCurrency() {
//		return currency;
//	}
//	public void setCurrency(String currency) {
//		this.currency = currency;
//	}
//	public void setListingType(String listingType) {
//		this.listingType = listingType;
//	}
//	
//
//	
//	public String getBuyItNowPrice() {
//		return buyItNowPrice;
//	}
//	public void setBuyItNowPrice(String buyItNowPrice) {
//		this.buyItNowPrice = buyItNowPrice;
//	}
//	public String getReservePrice() {
//		return reservePrice;
//	}
//	public void setReservePrice(String reservePrice) {
//		this.reservePrice = reservePrice;
//	}
//	public String getStartPrice() {
//		return startPrice;
//	}
//	public void setStartPrice(String startPrice) {
//		this.startPrice = startPrice;
//	}
//	public AmountType getClassifiedAdPayPerLeadFee() {
//		return classifiedAdPayPerLeadFee;
//	}
//	public void setClassifiedAdPayPerLeadFee(AmountType classifiedAdPayPerLeadFee) {
//		this.classifiedAdPayPerLeadFee = classifiedAdPayPerLeadFee;
//	}
//	public AmountType getBuyerGuaranteePrice() {
//		return buyerGuaranteePrice;
//	}
//	public void setBuyerGuaranteePrice(AmountType buyerGuaranteePrice) {
//		this.buyerGuaranteePrice = buyerGuaranteePrice;
//	}
//	public AmountType getFloorPrice() {
//		return floorPrice;
//	}
//	public void setFloorPrice(AmountType floorPrice) {
//		this.floorPrice = floorPrice;
//	}
//	public AmountType getCeilingPrice() {
//		return ceilingPrice;
//	}
//	public void setCeilingPrice(AmountType ceilingPrice) {
//		this.ceilingPrice = ceilingPrice;
//	}
//	
//	public String getApplicationData() {
//		return applicationData;
//	}
//	public void setApplicationData(String applicationData) {
//		this.applicationData = applicationData;
//	}
//	public Boolean getAutoPay() {
//		return autoPay;
//	}
//	public void setAutoPay(Boolean autoPay) {
//		this.autoPay = autoPay;
//	}
//	public Boolean getMotorsGermanySearchable() {
//		return motorsGermanySearchable;
//	}
//	public void setMotorsGermanySearchable(Boolean motorsGermanySearchable) {
//		this.motorsGermanySearchable = motorsGermanySearchable;
//	}
//	public Boolean getCategoryMappingAllowed() {
//		return categoryMappingAllowed;
//	}
//	public void setCategoryMappingAllowed(Boolean categoryMappingAllowed) {
//		this.categoryMappingAllowed = categoryMappingAllowed;
//	}
//	public String getDescription() {
//		return description;
//	}
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	public Integer getGiftIcon() {
//		return giftIcon;
//	}
//	public void setGiftIcon(Integer giftIcon) {
//		this.giftIcon = giftIcon;
//	}
//	public String getItemID() {
//		return itemID;
//	}
//	public void setItemID(String itemID) {
//		this.itemID = itemID;
//	}
//	public String getListingDuration() {
//		return listingDuration;
//	}
//	public void setListingDuration(String listingDuration) {
//		this.listingDuration = listingDuration;
//	}
//	public String getLocation() {
//		return location;
//	}
//	public void setLocation(String location) {
//		this.location = location;
//	}
//	public Integer getLotSize() {
//		return lotSize;
//	}
//	public void setLotSize(Integer lotSize) {
//		this.lotSize = lotSize;
//	}
//	public String getPartnerCode() {
//		return partnerCode;
//	}
//	public void setPartnerCode(String partnerCode) {
//		this.partnerCode = partnerCode;
//	}
//	public String getPartnerName() {
//		return partnerName;
//	}
//	public void setPartnerName(String partnerName) {
//		this.partnerName = partnerName;
//	}
//	public String getPayPalEmailAddress() {
//		return payPalEmailAddress;
//	}
//	public void setPayPalEmailAddress(String payPalEmailAddress) {
//		this.payPalEmailAddress = payPalEmailAddress;
//	}
//	public Boolean getPrivateListing() {
//		return privateListing;
//	}
//	public void setPrivateListing(Boolean privateListing) {
//		this.privateListing = privateListing;
//	}
//	public Integer getQuantity() {
//		return quantity;
//	}
//	public void setQuantity(Integer quantity) {
//		this.quantity = quantity;
//	}
//	public String getPrivateNotes() {
//		return privateNotes;
//	}
//	public void setPrivateNotes(String privateNotes) {
//		this.privateNotes = privateNotes;
//	}
//	public String getRegionID() {
//		return regionID;
//	}
//	public void setRegionID(String regionID) {
//		this.regionID = regionID;
//	}
//	public Boolean getRelistLink() {
//		return relistLink;
//	}
//	public void setRelistLink(Boolean relistLink) {
//		this.relistLink = relistLink;
//	}
//	public String getSubTitle() {
//		return subTitle;
//	}
//	public void setSubTitle(String subTitle) {
//		this.subTitle = subTitle;
//	}
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
//	
//	public String getSellerVacationNote() {
//		return sellerVacationNote;
//	}
//	public void setSellerVacationNote(String sellerVacationNote) {
//		this.sellerVacationNote = sellerVacationNote;
//	}
//	public Long getWatchCount() {
//		return watchCount;
//	}
//	public void setWatchCount(Long watchCount) {
//		this.watchCount = watchCount;
//	}
//	public Long getHitCount() {
//		return hitCount;
//	}
//	public void setHitCount(Long hitCount) {
//		this.hitCount = hitCount;
//	}
//	public Boolean getDisableBuyerRequirements() {
//		return disableBuyerRequirements;
//	}
//	public void setDisableBuyerRequirements(Boolean disableBuyerRequirements) {
//		this.disableBuyerRequirements = disableBuyerRequirements;
//	}
//	public Boolean getLocationDefaulted() {
//		return locationDefaulted;
//	}
//	public void setLocationDefaulted(Boolean locationDefaulted) {
//		this.locationDefaulted = locationDefaulted;
//	}
//	public Boolean getThirdPartyCheckout() {
//		return thirdPartyCheckout;
//	}
//	public void setThirdPartyCheckout(Boolean thirdPartyCheckout) {
//		this.thirdPartyCheckout = thirdPartyCheckout;
//	}
//	public Boolean getUseTaxTable() {
//		return useTaxTable;
//	}
//	public void setUseTaxTable(Boolean useTaxTable) {
//		this.useTaxTable = useTaxTable;
//	}
//	public Boolean getGetItFast() {
//		return getItFast;
//	}
//	public void setGetItFast(Boolean getItFast) {
//		this.getItFast = getItFast;
//	}
//	public Boolean getBuyerResponsibleForShipping() {
//		return buyerResponsibleForShipping;
//	}
//	public void setBuyerResponsibleForShipping(Boolean buyerResponsibleForShipping) {
//		this.buyerResponsibleForShipping = buyerResponsibleForShipping;
//	}
//	public Boolean getLimitedWarrantyEligible() {
//		return limitedWarrantyEligible;
//	}
//	public void setLimitedWarrantyEligible(Boolean limitedWarrantyEligible) {
//		this.limitedWarrantyEligible = limitedWarrantyEligible;
//	}
//	public String geteBayNotes() {
//		return eBayNotes;
//	}
//	public void seteBayNotes(String eBayNotes) {
//		this.eBayNotes = eBayNotes;
//	}
//	public Long getQuestionCount() {
//		return questionCount;
//	}
//	public void setQuestionCount(Long questionCount) {
//		this.questionCount = questionCount;
//	}
//	public Boolean getRelisted() {
//		return relisted;
//	}
//	public void setRelisted(Boolean relisted) {
//		this.relisted = relisted;
//	}
//	public Integer getQuantityAvailable() {
//		return quantityAvailable;
//	}
//	public void setQuantityAvailable(Integer quantityAvailable) {
//		this.quantityAvailable = quantityAvailable;
//	}
//	
//	public Boolean getCategoryBasedAttributesPrefill() {
//		return categoryBasedAttributesPrefill;
//	}
//	public void setCategoryBasedAttributesPrefill(Boolean categoryBasedAttributesPrefill) {
//		this.categoryBasedAttributesPrefill = categoryBasedAttributesPrefill;
//	}
//	public String getPostalCode() {
//		return postalCode;
//	}
//	public void setPostalCode(String postalCode) {
//		this.postalCode = postalCode;
//	}
//	public Boolean getShippingTermsInDescription() {
//		return shippingTermsInDescription;
//	}
//	public void setShippingTermsInDescription(Boolean shippingTermsInDescription) {
//		this.shippingTermsInDescription = shippingTermsInDescription;
//	}
//	public String getSellerInventoryID() {
//		return sellerInventoryID;
//	}
//	public void setSellerInventoryID(String sellerInventoryID) {
//		this.sellerInventoryID = sellerInventoryID;
//	}
//	public Integer getDispatchTimeMax() {
//		return dispatchTimeMax;
//	}
//	public void setDispatchTimeMax(Integer dispatchTimeMax) {
//		this.dispatchTimeMax = dispatchTimeMax;
//	}
//	public Boolean getSkypeEnabled() {
//		return skypeEnabled;
//	}
//	public void setSkypeEnabled(Boolean skypeEnabled) {
//		this.skypeEnabled = skypeEnabled;
//	}
//	public String getSkypeID() {
//		return skypeID;
//	}
//	public void setSkypeID(String skypeID) {
//		this.skypeID = skypeID;
//	}
//	public Boolean getBestOfferEnabled() {
//		return bestOfferEnabled;
//	}
//	public void setBestOfferEnabled(Boolean bestOfferEnabled) {
//		this.bestOfferEnabled = bestOfferEnabled;
//	}
//	public Boolean getLocalListing() {
//		return localListing;
//	}
//	public void setLocalListing(Boolean localListing) {
//		this.localListing = localListing;
//	}
//	public Boolean getThirdPartyCheckoutIntegration() {
//		return thirdPartyCheckoutIntegration;
//	}
//	public void setThirdPartyCheckoutIntegration(Boolean thirdPartyCheckoutIntegration) {
//		this.thirdPartyCheckoutIntegration = thirdPartyCheckoutIntegration;
//	}
//	public Long getTotalQuestionCount() {
//		return totalQuestionCount;
//	}
//	public void setTotalQuestionCount(Long totalQuestionCount) {
//		this.totalQuestionCount = totalQuestionCount;
//	}
//	public Boolean getProxyItem() {
//		return proxyItem;
//	}
//	public void setProxyItem(Boolean proxyItem) {
//		this.proxyItem = proxyItem;
//	}
//	public Integer getLeadCount() {
//		return leadCount;
//	}
//	public void setLeadCount(Integer leadCount) {
//		this.leadCount = leadCount;
//	}
//	public Integer getNewLeadCount() {
//		return newLeadCount;
//	}
//	public void setNewLeadCount(Integer newLeadCount) {
//		this.newLeadCount = newLeadCount;
//	}
//	public String getGroupCategoryID() {
//		return groupCategoryID;
//	}
//	public void setGroupCategoryID(String groupCategoryID) {
//		this.groupCategoryID = groupCategoryID;
//	}
//	public Boolean getBidGroupItem() {
//		return bidGroupItem;
//	}
//	public void setBidGroupItem(Boolean bidGroupItem) {
//		this.bidGroupItem = bidGroupItem;
//	}
//	public Boolean getMechanicalCheckAccepted() {
//		return mechanicalCheckAccepted;
//	}
//	public void setMechanicalCheckAccepted(Boolean mechanicalCheckAccepted) {
//		this.mechanicalCheckAccepted = mechanicalCheckAccepted;
//	}
//	public Boolean getUpdateSellerInfo() {
//		return updateSellerInfo;
//	}
//	public void setUpdateSellerInfo(Boolean updateSellerInfo) {
//		this.updateSellerInfo = updateSellerInfo;
//	}
//	public Boolean getUpdateReturnPolicy() {
//		return updateReturnPolicy;
//	}
//	public void setUpdateReturnPolicy(Boolean updateReturnPolicy) {
//		this.updateReturnPolicy = updateReturnPolicy;
//	}
//	public Boolean getIntegratedMerchantCreditCardEnabled() {
//		return integratedMerchantCreditCardEnabled;
//	}
//	public void setIntegratedMerchantCreditCardEnabled(Boolean integratedMerchantCreditCardEnabled) {
//		this.integratedMerchantCreditCardEnabled = integratedMerchantCreditCardEnabled;
//	}
//	public Integer getItemCompatibilityCount() {
//		return itemCompatibilityCount;
//	}
//	public void setItemCompatibilityCount(Integer itemCompatibilityCount) {
//		this.itemCompatibilityCount = itemCompatibilityCount;
//	}
//	public Integer getConditionID() {
//		return conditionID;
//	}
//	public void setConditionID(Integer conditionID) {
//		this.conditionID = conditionID;
//	}
//	public String getConditionDescription() {
//		return conditionDescription;
//	}
//	public void setConditionDescription(String conditionDescription) {
//		this.conditionDescription = conditionDescription;
//	}
//	public String getConditionDisplayName() {
//		return conditionDisplayName;
//	}
//	public void setConditionDisplayName(String conditionDisplayName) {
//		this.conditionDisplayName = conditionDisplayName;
//	}
//	public String getTaxCategory() {
//		return taxCategory;
//	}
//	public void setTaxCategory(String taxCategory) {
//		this.taxCategory = taxCategory;
//	}
//	public Integer getQuantityThreshold() {
//		return quantityThreshold;
//	}
//	public void setQuantityThreshold(Integer quantityThreshold) {
//		this.quantityThreshold = quantityThreshold;
//	}
//	public Boolean getPostCheckoutExperienceEnabled() {
//		return postCheckoutExperienceEnabled;
//	}
//	public void setPostCheckoutExperienceEnabled(Boolean postCheckoutExperienceEnabled) {
//		this.postCheckoutExperienceEnabled = postCheckoutExperienceEnabled;
//	}
//	public Boolean getUseRecommendedProduct() {
//		return useRecommendedProduct;
//	}
//	public void setUseRecommendedProduct(Boolean useRecommendedProduct) {
//		this.useRecommendedProduct = useRecommendedProduct;
//	}
//	public String getSellerProvidedTitle() {
//		return sellerProvidedTitle;
//	}
//	public void setSellerProvidedTitle(String sellerProvidedTitle) {
//		this.sellerProvidedTitle = sellerProvidedTitle;
//	}
//	
//	public String getVinLink() {
//		return vinLink;
//	}
//	public void setVinLink(String vinLink) {
//		this.vinLink = vinLink;
//	}
//	
//	public String getUUID() {
//		return UUID;
//	}
//	public void setUUID(String uUID) {
//		UUID = uUID;
//	}
//	public String getSKU() {
//		return SKU;
//	}
//	public void setSKU(String sKU) {
//		SKU = sKU;
//	}
//	public String getVIN() {
//		return VIN;
//	}
//	public void setVIN(String vIN) {
//		VIN = vIN;
//	}
//	public String getVRM() {
//		return VRM;
//	}
//	public void setVRM(String vRM) {
//		VRM = vRM;
//	}
//	public String getVrmLink() {
//		return vrmLink;
//	}
//	public void setVrmLink(String vrmLink) {
//		this.vrmLink = vrmLink;
//	}
//	public Boolean getTopRatedListing() {
//		return topRatedListing;
//	}
//	public void setTopRatedListing(Boolean topRatedListing) {
//		this.topRatedListing = topRatedListing;
//	}
//	public Boolean getIsIntermediatedShippingEligible() {
//		return isIntermediatedShippingEligible;
//	}
//	public void setIsIntermediatedShippingEligible(Boolean isIntermediatedShippingEligible) {
//		this.isIntermediatedShippingEligible = isIntermediatedShippingEligible;
//	}
//	public Long getRelistParentID() {
//		return relistParentID;
//	}
//	public void setRelistParentID(Long relistParentID) {
//		this.relistParentID = relistParentID;
//	}
//	public String getConditionDefinition() {
//		return conditionDefinition;
//	}
//	public void setConditionDefinition(String conditionDefinition) {
//		this.conditionDefinition = conditionDefinition;
//	}
//	public Boolean getHideFromSearch() {
//		return hideFromSearch;
//	}
//	public void setHideFromSearch(Boolean hideFromSearch) {
//		this.hideFromSearch = hideFromSearch;
//	}
//	public Boolean getIncludeRecommendations() {
//		return includeRecommendations;
//	}
//	public void setIncludeRecommendations(Boolean includeRecommendations) {
//		this.includeRecommendations = includeRecommendations;
//	}
//	public Boolean geteBayNowEligible() {
//		return eBayNowEligible;
//	}
//	public void seteBayNowEligible(Boolean eBayNowEligible) {
//		this.eBayNowEligible = eBayNowEligible;
//	}
//	public Boolean geteBayNowAvailable() {
//		return eBayNowAvailable;
//	}
//	public void seteBayNowAvailable(Boolean eBayNowAvailable) {
//		this.eBayNowAvailable = eBayNowAvailable;
//	}
//	public Boolean getIgnoreQuantity() {
//		return ignoreQuantity;
//	}
//	public void setIgnoreQuantity(Boolean ignoreQuantity) {
//		this.ignoreQuantity = ignoreQuantity;
//	}
//	public Boolean getAvailableForPickupDropOff() {
//		return availableForPickupDropOff;
//	}
//	public void setAvailableForPickupDropOff(Boolean availableForPickupDropOff) {
//		this.availableForPickupDropOff = availableForPickupDropOff;
//	}
//	public Boolean getEligibleForPickupDropOff() {
//		return eligibleForPickupDropOff;
//	}
//	public void setEligibleForPickupDropOff(Boolean eligibleForPickupDropOff) {
//		this.eligibleForPickupDropOff = eligibleForPickupDropOff;
//	}
//	public Boolean getLiveAuction() {
//		return liveAuction;
//	}
//	public void setLiveAuction(Boolean liveAuction) {
//		this.liveAuction = liveAuction;
//	}
//	public Boolean geteBayPlus() {
//		return eBayPlus;
//	}
//	public void seteBayPlus(Boolean eBayPlus) {
//		this.eBayPlus = eBayPlus;
//	}
//	public Boolean geteBayPlusEligible() {
//		return eBayPlusEligible;
//	}
//	public void seteBayPlusEligible(Boolean eBayPlusEligible) {
//		this.eBayPlusEligible = eBayPlusEligible;
//	}
//	public Boolean geteMailDeliveryAvailable() {
//		return eMailDeliveryAvailable;
//	}
//	public void seteMailDeliveryAvailable(Boolean eMailDeliveryAvailable) {
//		this.eMailDeliveryAvailable = eMailDeliveryAvailable;
//	}
//	
//	
//	
//	
//}
