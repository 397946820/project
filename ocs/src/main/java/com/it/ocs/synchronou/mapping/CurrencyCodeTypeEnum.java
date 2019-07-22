//package com.it.ocs.synchronou.mapping;
//
//import java.util.HashMap;
//
//import com.ebay.soap.eBLBaseComponents.AmountType;
//import com.ebay.soap.eBLBaseComponents.CurrencyCodeType;
//import com.ebay.soap.eBLBaseComponents.SiteCodeType;
//
//public class CurrencyCodeTypeEnum {
//	
//	public static CurrencyCodeType searchCurrencyBySiteID(Long siteID){
//		
//		HashMap<Long, CurrencyCodeType> map = new HashMap<>();
//		map.put(0L, CurrencyCodeType.USD);
//		map.put(2L, CurrencyCodeType.USD);
//		map.put(3L, CurrencyCodeType.GBP);
//		map.put(77L, CurrencyCodeType.EUR);
//		map.put(15L, CurrencyCodeType.AUD);
//		map.put(71L, CurrencyCodeType.EUR);
//		map.put(100L, null);
//		map.put(101L, CurrencyCodeType.EUR);
//		map.put(146L, CurrencyCodeType.EUR);
//		map.put(186L, CurrencyCodeType.EUR);
//		map.put(203L, CurrencyCodeType.INR);
//		map.put(201L, CurrencyCodeType.HKD);
//		map.put(216L, CurrencyCodeType.SGD);
//		map.put(207L, CurrencyCodeType.MYR);
//		map.put(211L, CurrencyCodeType.PHP);
//		map.put(210L, CurrencyCodeType.USD);
//		map.put(212L, CurrencyCodeType.PLN);
//		map.put(123L, CurrencyCodeType.EUR);
//		map.put(23L, CurrencyCodeType.EUR);
//		map.put(16L, CurrencyCodeType.EUR);
//		map.put(193L, CurrencyCodeType.CHF);
//		map.put(205L, CurrencyCodeType.EUR);
//		
//		if(map.get(siteID)!=null){
//			
//			return map.get(siteID);
//		}else{
//			
//			return null;
//		
//	    }
//	}
//}
