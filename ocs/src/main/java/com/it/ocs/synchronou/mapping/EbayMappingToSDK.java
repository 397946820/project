//package com.it.ocs.synchronou.mapping;
//
//import java.util.List;
//
//import com.alibaba.druid.pool.GetConnectionTimeoutException;
//import com.ebay.soap.eBLBaseComponents.AmountType;
//import com.ebay.soap.eBLBaseComponents.BuyerPaymentMethodCodeType;
//import com.ebay.soap.eBLBaseComponents.BuyerRequirementDetailsType;
//import com.ebay.soap.eBLBaseComponents.CategoryType;
//import com.ebay.soap.eBLBaseComponents.CurrencyCodeType;
//import com.ebay.soap.eBLBaseComponents.InternationalShippingServiceOptionsType;
//import com.ebay.soap.eBLBaseComponents.ListingDetailsType;
//import com.ebay.soap.eBLBaseComponents.MaximumBuyerPolicyViolationsType;
//import com.ebay.soap.eBLBaseComponents.MaximumItemRequirementsType;
//import com.ebay.soap.eBLBaseComponents.MaximumUnpaidItemStrikesInfoType;
//import com.ebay.soap.eBLBaseComponents.PeriodCodeType;
//import com.ebay.soap.eBLBaseComponents.PictureDetailsType;
//import com.ebay.soap.eBLBaseComponents.ReturnPolicyType;
//import com.ebay.soap.eBLBaseComponents.SalesTaxType;
//import com.ebay.soap.eBLBaseComponents.ShippingDetailsType;
//import com.ebay.soap.eBLBaseComponents.ShippingServiceOptionsType;
//import com.ebay.soap.eBLBaseComponents.ShippingTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.StorefrontType;
//import com.google.common.collect.Lists;
//import com.it.ocs.publication.model.PublicationAddItemModel;
//import com.it.ocs.publication.model.ShipOptionModel;
//import com.it.ocs.synchronou.util.ValidationUtil;
//import com.sun.xml.ws.developer.MemberSubmissionAddressing.Validation;
//
//import net.sf.json.JSONObject;
//
//public class EbayMappingToSDK {
//
//	/**
//	 * 
//	 * @param categoryId
//	 * @return
//	 * 描述：分类映射
//	 */
//	public static CategoryType emptyCategoryTypeById(String categoryId){
//		if(ValidationUtil.isNullOrEmpty(categoryId)){
//			return null;
//			
//		}else {
//			CategoryType categoryType = new CategoryType();
//			categoryType.setCategoryID(categoryId);
//			return categoryType;
//			
//		}
//	}
//	/**
//	 * 
//	 * @param categoryID
//	 * @param category2ID
//	 * @return
//	 * 描述：商店类目
//	 */
//	public static StorefrontType getStoreFrontType(Long categoryID, Long category2ID){
//		StorefrontType storefrontType= new StorefrontType();
//		
//		if(!ValidationUtil.isNull(categoryID)){
//			storefrontType.setStoreCategoryID(categoryID);
//		}
//		if(!ValidationUtil.isNull(category2ID)){
//			
//			storefrontType.setStoreCategory2ID(category2ID);
//			
//		}
//		if(ValidationUtil.isNull(categoryID)&&ValidationUtil.isNull(category2ID)){
//			
//			return null;
//		}
//		return storefrontType;
//	}
//	
//
//	public static AmountType getInternationalAmountType(String value,CurrencyCodeType currency){
//		if(null == value || "".equals(value)){
//    		value = "0";
//    	}
//		AmountType amountType = new AmountType();
//    	Double vl = Double.parseDouble(value);
//    	amountType.setValue(vl);
//    	amountType.setCurrencyID(currency);
//    	return amountType;
//	}
//	/**
//	 * 
//	 * @param value
//	 * @param currency
//	 * @return
//	 * 描述:获取AmountType
//	 */
//    public static AmountType getAmountTypeByStr(String value,CurrencyCodeType currency){
//    	if(value==null){
//    		return null;
//    	}else if(value.equals("0")||value.equals("")){
//    		return null;
//    	}else{
//
//    		AmountType amountType = new AmountType();
//        	Double vl = Double.parseDouble(value);
//        	amountType.setValue(vl);
//        	amountType.setCurrencyID(currency);
//        	return amountType;
//    	}
//    	
//    	
//    }
//    public static AmountType getAmountTypeByDou(Double value,CurrencyCodeType currency){
//    	AmountType amountType = new AmountType();
//    	amountType.setValue(value);
//    	amountType.setCurrencyID(currency);
//    	return amountType;
//    }
//   
//    public static ListingDetailsType getListingDetailsByMinAndMax(Double min,Double max,CurrencyCodeType currency){
//    	if(ValidationUtil.isNull(min)&&ValidationUtil.isNull(max)){
//    		return null;
//    	}else{
//    		ListingDetailsType listingDetailsType = new ListingDetailsType();
//    		listingDetailsType.setBestOfferAutoAcceptPrice(getAmountTypeByDou(max, currency));
//    		listingDetailsType.setMinimumBestOfferPrice(getAmountTypeByDou(min, currency));
//    		return listingDetailsType;
//    	}
//    	
//    }
//
//    /**
//     * 
//     * @param method
//     * @return
//     * 描述：付款方式 
//     */
//    public static BuyerPaymentMethodCodeType[] getPaymentMethod(String method){
//    	BuyerPaymentMethodCodeType[] buyerPaymentMethodCodeTypes=null;
//    	if(ValidationUtil.isNull(method)){
//    		buyerPaymentMethodCodeTypes = new BuyerPaymentMethodCodeType[1];
//    		return buyerPaymentMethodCodeTypes;
//    	}else if(method.contains(",")){
//    		String[] array = method.split(",");
//    		int length = array.length;
//    		buyerPaymentMethodCodeTypes = new BuyerPaymentMethodCodeType[length+1];
//    		for (int i = 0; i < array.length; i++) {
//				buyerPaymentMethodCodeTypes[i] = BuyerPaymentMethodCodeType.fromValue(array[i]);
//			}
//    		return buyerPaymentMethodCodeTypes;
//    	}else if(!ValidationUtil.isNullOrEmpty(method)){
//    		buyerPaymentMethodCodeTypes = new BuyerPaymentMethodCodeType[1];
//    		buyerPaymentMethodCodeTypes[0] = BuyerPaymentMethodCodeType.fromValue(method);
//    		return buyerPaymentMethodCodeTypes;
//    	}else{
//    		buyerPaymentMethodCodeTypes = new BuyerPaymentMethodCodeType[1];
//    		return buyerPaymentMethodCodeTypes;
//    	}
//    	
//    }
//   
//    /**
//     * 
//     * @param publication
//     * @return
//     * 描述：买家要求
//     */
//    public static BuyerRequirementDetailsType getBuyerRequirement(PublicationAddItemModel publication){
//    	if (publication.getAllowAllbuyer()) {
//    		return null;
//		}else{
//			BuyerRequirementDetailsType buyerRequirement = new BuyerRequirementDetailsType();
//
//			//没有PayPal账户
//			buyerRequirement.setLinkedPayPalAccount(publication.getNoPaypai());
//			//主要运送地址在我的运送范围之外
//			buyerRequirement.setShipToRegistrationCountry(publication.getOutShipCountry());
//			//曾收到..个弃标个案，在过去。。天
//			MaximumUnpaidItemStrikesInfoType maximumUnpaidItemStrikesInfo = null;
//			String buyerReq1 = publication.getBuyerReq1();
//			if (!ValidationUtil.isNullOrEmpty(buyerReq1)) {
//				String[] array = buyerReq1.split("\\|");
//				maximumUnpaidItemStrikesInfo = new MaximumUnpaidItemStrikesInfoType();
//				maximumUnpaidItemStrikesInfo.setCount(Integer.parseInt(array[0]));
//				
//				maximumUnpaidItemStrikesInfo.setPeriod(PeriodCodeType.fromValue(array[1]));
//			}
//			//曾收到。。个违反政策检举，在过去 天 
//			MaximumBuyerPolicyViolationsType maximumBuyerPolicyViolationsType = null;
//			String buyerReq2 = publication.getBuyerReq2();
//			if(!ValidationUtil.isNullOrEmpty(buyerReq2)){
//				maximumBuyerPolicyViolationsType = new MaximumBuyerPolicyViolationsType();
//				String[] array = buyerReq2.split("\\|");
//				maximumBuyerPolicyViolationsType.setCount(Integer.parseInt(array[0]));
//				maximumBuyerPolicyViolationsType.setPeriod(PeriodCodeType.fromValue(array[1]));
//			}
//			//信用 指标 等于 或 低于 
//			
//			buyerRequirement.setMinimumFeedbackScore(publication.getBuyerReq3());
//			
//			//在过去。。。。。。
//			MaximumItemRequirementsType maximumItemRequirementsType = null;
//			Integer buyerReq4 = publication.getBuyerReq4();
//			if (!ValidationUtil.isNull(buyerReq4)) {
//				maximumItemRequirementsType = new MaximumItemRequirementsType();
//				maximumItemRequirementsType.setMaximumItemCount(buyerReq4);
//				maximumItemRequirementsType.setMinimumFeedbackScore(publication.getBuyerReq41());
//			}
//			
//			buyerRequirement.setMaximumUnpaidItemStrikesInfo(maximumUnpaidItemStrikesInfo);
//			buyerRequirement.setMaximumBuyerPolicyViolations(maximumBuyerPolicyViolationsType);
//			buyerRequirement.setMaximumItemRequirements(maximumItemRequirementsType);
//    	    return buyerRequirement;
//		}
//    }
//    
//    /**
//     * 
//     * @param publication
//     * @return
//     * 描述：退货政策
//     */
//    public static ReturnPolicyType getReturnPolicyTeyp(PublicationAddItemModel publication){
//    	ReturnPolicyType returnPolicyType = null;
//    	String policyType = publication.getPolicyType();
//    	if(policyType.equalsIgnoreCase("ReturnsAccepted")){
//    		returnPolicyType = new ReturnPolicyType();
//    		returnPolicyType.setReturnsAccepted(policyType);
//    		returnPolicyType.setReturnsAcceptedOption(policyType);
//    		returnPolicyType.setRefundOption(publication.getReturnType());//退货方式
//    		returnPolicyType.setReturnsWithinOption(publication.getReturnDays());//退货天数
//    		returnPolicyType.setExtendedHolidayReturns(publication.getAllowDelay());//节假退货延期
//    		returnPolicyType.setShippingCostPaidByOption(publication.getFaretakeinhander());
//    		returnPolicyType.setRestockingFeeValueOption(publication.getDepreciationRate());
//    		returnPolicyType.setDescription(publication.getReturnDescription());
//    		return returnPolicyType;
//    	}else{
//    		returnPolicyType = new ReturnPolicyType();
//    		returnPolicyType.setReturnsAcceptedOption(policyType);
//    		return returnPolicyType;
//    	}
//    	
//    }
//    
//    
//    public static ShippingDetailsType getShipping(List<ShipOptionModel> shipOptionModels , CurrencyCodeType currency,PublicationAddItemModel publication){
//    	ShippingDetailsType shippingDetailsType = new ShippingDetailsType();
//    	shippingDetailsType.setShippingType(ShippingTypeCodeType.fromValue("Flat"));
//    	List<ShipOptionModel> domesticShipping = Lists.newArrayList();
//    	List<ShipOptionModel> internationalShipping = Lists.newArrayList();
//    	
//    	for (ShipOptionModel shipOptionModel : shipOptionModels) {
//    		String tranKind= shipOptionModel.getTranKind();
//			if (tranKind.equals("0")) {
//				domesticShipping.add(shipOptionModel);
//			}else if (tranKind.equals("1")) {
//				internationalShipping.add(shipOptionModel);
//			}
//		}
//    	int domesticTotal = domesticShipping.size();
//    	int internationalTotal = internationalShipping.size();
//    	if (domesticTotal>0) {
//			ShippingServiceOptionsType[] serviceOptionsTypes = new ShippingServiceOptionsType[domesticTotal];
//			for (int i = 0; i < domesticTotal; i++) {
//				ShipOptionModel shipOptionModel = domesticShipping.get(i);
//				ShippingServiceOptionsType shippingService = new ShippingServiceOptionsType();
//				//运费
//				String domesticShipCost = shipOptionModel.getDomesticShipCost();
//				if (!ValidationUtil.isNullOrEmpty(domesticShipCost)) {
//					shippingService.setShippingServiceCost(getAmountTypeByDou(Double.parseDouble(domesticShipCost), currency));
//				}
//				//优先级
//				String tranOrder = shipOptionModel.getTranOrder();
//				if (!ValidationUtil.isNullOrEmpty(tranOrder)) {
//					shippingService.setShippingServicePriority(Integer.parseInt(tranOrder));
//				}
//				//运输方式
//				shippingService.setShippingService(shipOptionModel.getDomesticShipType());
//				//每件额外费用
//				if(!ValidationUtil.isNull(shipOptionModel.getDomesticExtraCost())){
//					Double shippingServiceAdditional = Double.parseDouble(shipOptionModel.getDomesticExtraCost());
//					
//					if(shippingServiceAdditional>0){
//					shippingService.setShippingServiceAdditionalCost(getAmountTypeByStr(shipOptionModel.getDomesticExtraCost(), currency));
//					}
//				}
//				//AK,HI,PR 额外收费
//				if(!ValidationUtil.isNull(shipOptionModel.getDomesticShipAkHiPr())){
//					Double shippingSurcharge = Double.parseDouble(shipOptionModel.getDomesticShipAkHiPr());
//					if (shippingSurcharge>0) {
//						shippingService.setShippingSurcharge(getAmountTypeByStr(shipOptionModel.getDomesticShipAkHiPr(), currency));
//					}
//				}
//				
//				serviceOptionsTypes[i]=shippingService;
//			}
//			shippingDetailsType.setShippingServiceOptions(serviceOptionsTypes);
//    	}
//    	
//    	if(internationalTotal>0){
//    		InternationalShippingServiceOptionsType[] internationalServices = new InternationalShippingServiceOptionsType[internationalTotal];
//    		for (int i = 0; i < internationalTotal; i++) {
//				InternationalShippingServiceOptionsType internationalService = new InternationalShippingServiceOptionsType();
//				ShipOptionModel shipOptionModel =internationalShipping.get(i);
//				//运输方式
//				internationalService.setShippingService(shipOptionModel.getDomesticShipType());
//				//运费
//				internationalService.setShippingServiceCost(getInternationalAmountType(shipOptionModel.getDomesticShipCost(), currency));
//				//优先级
//				String tranOrder = shipOptionModel.getTranOrder();
//				if (!ValidationUtil.isNullOrEmpty(tranOrder)) {
//					internationalService.setShippingServicePriority(Integer.parseInt(tranOrder));
//				}
//				//每件额外费用
//				internationalService.setShippingServiceAdditionalCost(getAmountTypeByStr(shipOptionModel.getDomesticExtraCost(), currency));
//				String shipLocationIn = shipOptionModel.getShipLocationIn();
//				if(ValidationUtil.isNullOrEmpty(shipLocationIn)){
//					
//				}else{
//					if (shipLocationIn.contains(",")) {
//						String[] shipLocationIns = shipLocationIn.split(",");
//						internationalService.setShipToLocation(shipLocationIns);
//					}else{
//						String[] shipLocationIns = new String[1];
//						shipLocationIns[0] = shipLocationIn;
//						internationalService.setShipToLocation(shipLocationIns);
//					}
//				}
//				
//				internationalServices[i]=internationalService;
//    		}
//    		
//    		shippingDetailsType.setInternationalShippingServiceOption(internationalServices);
//    	}
//    	String salesTax = publication.getSalesTax();
//    	//销售税
//    	if(!ValidationUtil.isNullOrEmpty(salesTax)){
//    		SalesTaxType salesTaxType = new SalesTaxType();
//    		JSONObject jsonObject = JSONObject.fromObject(salesTax);
//    		salesTaxType.setSalesTaxState(jsonObject.getString("type"));
//    		salesTaxType.setShippingIncludedInTax(jsonObject.getBoolean("shippingIncludedInTax"));
//    		salesTaxType.setSalesTaxPercent(Float.parseFloat(jsonObject.getString("percent")));
//    		shippingDetailsType.setSalesTax(salesTaxType);
//    	}
//    	
//    	return shippingDetailsType;
//    }
//}
