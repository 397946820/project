//package com.it.ocs.synchronou.mapping;
//
//import java.util.HashMap;
//
//import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;
//
//import com.ebay.soap.eBLBaseComponents.SiteCodeType;
//import com.sun.xml.xsom.impl.scd.Iterators.Map;
//
//public class SiteIDEnum {
//	public static Long searchBySiteName(String siteName){
//		HashMap<String, Long> map = new HashMap<>();
//		map.put("US",0L);
//		map.put("CANADA",2L);
//		map.put("UK",3L);
//		map.put("GERMANY",77L);
//		map.put("DE",77L);
//		map.put("AUSTRALIA",15L);
//		map.put("FRANCE",71L);
//		map.put("EBAYMOTORS",100L);
//		map.put("ITALY",101L);
//		map.put("NETHERLANDS",146L);
//		map.put("SPAIN",186L);
//		map.put("INDIA",203L);
//		map.put("HONG_KONG",201L);
//		map.put("SINGAPORE",216L);
//		map.put("MALAYSIA",207L);
//		map.put("PHILIPPINES",211L);
//		map.put("CANADA_FRENCH",210L);
//		map.put("POLAND",212L);
//		map.put("BELGIUM_DUTCH",123L);
//		map.put("BELGIUM_FRENCH",23L);
//		map.put("AUSTRIA",16L);
//		map.put("SWITZERLAND",193L);
//		map.put("IRELAND",205L);
//		return map.get(siteName);
//	}
//	public static SiteCodeType searchBySiteID(Long siteId){
//		HashMap<Long, SiteCodeType> map = new HashMap<>();
//		map.put(0L, SiteCodeType.US);
//		map.put(2L, SiteCodeType.CANADA);
//		map.put(3L, SiteCodeType.UK);
//		map.put(77L, SiteCodeType.GERMANY);
//		map.put(15L, SiteCodeType.AUSTRALIA);
//		map.put(71L, SiteCodeType.FRANCE);
//		map.put(100L, SiteCodeType.E_BAY_MOTORS);
//		map.put(101L, SiteCodeType.ITALY);
//		map.put(146L, SiteCodeType.NETHERLANDS);
//		map.put(186L, SiteCodeType.SPAIN);
//		map.put(203L, SiteCodeType.INDIA);
//		map.put(201L, SiteCodeType.HONG_KONG);
//		map.put(216L, SiteCodeType.SINGAPORE);
//		map.put(207L, SiteCodeType.MALAYSIA);
//		map.put(211L, SiteCodeType.PHILIPPINES);
//		map.put(210L, SiteCodeType.CANADA_FRENCH);
//		map.put(212L, SiteCodeType.POLAND);
//		map.put(123L, SiteCodeType.BELGIUM_DUTCH);
//		map.put(23L, SiteCodeType.BELGIUM_FRENCH);
//		map.put(16L, SiteCodeType.AUSTRIA);
//		map.put(193L, SiteCodeType.SWITZERLAND);
//		map.put(205L, SiteCodeType.IRELAND);
//		if(map.get(siteId)!=null){
//			
//			return map.get(siteId);
//					
//		}
//			return null;
//	}
//	public static Long searchSiteIdByUserId(String userId){
//		HashMap<String, Long> map = new HashMap<>();
//		map.put("uk.le", 3L);
//		map.put("lightingever01", 0L);
//		map.put("le.deutschland", 77L);
//		map.put("nm.deutschland", 77L);
//		map.put("uk.nm", 0L);
//      if(map.get(userId)!=null){
//			
//			return map.get(userId);
//					
//		}
//		return null;
//	}
//	public static String searchGlobalIDBySiteID(Long siteID){
//		HashMap<Long, String> map = new HashMap<>();
//		map.put(0L, "EBAY-US");
//		map.put(3L, "EBAY-GB");
//		map.put(77L, "EBAY-DE");
//		if(map.get(siteID)!=null){
//			return map.get(siteID);
//		}else{
//
//			return null;
//		}
//	}
//	public static Integer searchZone(Integer siteId){
//		Integer zone=0;
//		if(siteId.equals(0)){
//			zone=-7;
//		}else if(siteId.equals(3)){
//			zone=1;
//		}else if(siteId.equals(77)||siteId.equals(71)||siteId.equals(101)){
//			zone=2;
//		}
//		return 8-zone;
//		
//	}
//}
