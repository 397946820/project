//package com.it.ocs.synchronou.mapping;
//
//import java.util.HashMap;
//
//import com.ebay.soap.eBLBaseComponents.ListingTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.SiteCodeType;
//
//public class ListingTypeEnum {
//	public static ListingTypeCodeType searchByListingTypeV(String listingType){
//		HashMap<String, ListingTypeCodeType> map = new HashMap<>();
//		
//		map.put("AdType", ListingTypeCodeType.AD_TYPE);
//		map.put("Auction", ListingTypeCodeType.AUCTION);
//		map.put("Chinese", ListingTypeCodeType.CHINESE);
//		map.put("CustomCode", ListingTypeCodeType.CUSTOM_CODE);
//		map.put("FixedPriceItem", ListingTypeCodeType.FIXED_PRICE_ITEM);
//		map.put("Half", ListingTypeCodeType.HALF);
//		map.put("LeadGeneration", ListingTypeCodeType.LEAD_GENERATION);
//		map.put("PersonalOffer", ListingTypeCodeType.PERSONAL_OFFER);
//		map.put("Shopping", ListingTypeCodeType.SHOPPING);
//		map.put("Unknown", ListingTypeCodeType.UNKNOWN);
//		if(map.get(listingType)!=null){
//			return map.get(listingType);
//		}else{
//			return null;
//		}
//		
//	}
//}
