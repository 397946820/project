//package com.it.ocs.synchronou.util;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.ebay.soap.eBLBaseComponents.ShippingCarrierCodeType;
//import com.ebay.soap.eBLBaseComponents.ShippingPackageCodeType;
//import com.ebay.soap.eBLBaseComponents.ShippingServiceCodeType;
//import com.ebay.soap.eBLBaseComponents.ShippingServicePackageDetailsType;
//import com.ebay.soap.eBLBaseComponents.ShippingTypeCodeType;
//
//public class ShippingServiceDetailsSDK {
//	
//	public static String getShippingServicePackageDetails(ShippingServicePackageDetailsType[] servicePackageDetailsTypes){
//		List<Map<String, String>> maps = new ArrayList<>();
//		for(ShippingServicePackageDetailsType shippingServicePackageDetailsType : servicePackageDetailsTypes){
//			Map<String, String> map = new HashMap<>();
//			map.put("Name", shippingServicePackageDetailsType.getName().value());
//			maps.add(map);
//		}
//		
//		return ObjectAndJsonUtil.MapsToJsonArray(maps,null);
//	}
//	public static String getShippingCarrier(ShippingCarrierCodeType[] shippingCarrierCodeTypes){
//		List<Map<String,String>> maps = new ArrayList<>();
//		for(ShippingCarrierCodeType shippingCarrierCodeType : shippingCarrierCodeTypes){
//			Map<String, String> map = new HashMap<>();
//			map.put("ShippingCarrier", shippingCarrierCodeType.value());
//			maps.add(map);
//		}
//		return ObjectAndJsonUtil.MapsToJsonArray(maps,null);
//	}
//	public static String getShippingPackage(ShippingPackageCodeType[] shippingPackageCodeTypes){
//		List<Map<String, String>> maps = new ArrayList<>();
//		for (ShippingPackageCodeType shippingPackageCodeType : shippingPackageCodeTypes) {
//			Map<String, String> map = new HashMap<>();
//			map.put("ShippingPackage", shippingPackageCodeType.value());
//			maps.add(map);
//		}
//		return ObjectAndJsonUtil.MapsToJsonArray(maps,null);
//	}
//	public static String getServiceType(ShippingTypeCodeType[] serviceCodeTypes){
//		
//		List<Map<String,String>> maps = new ArrayList<>();
//		
//		for (ShippingTypeCodeType shippingTypeCodeType : serviceCodeTypes) {
//			Map<String, String> map = new HashMap<>();
//			map.put("ServiceType", shippingTypeCodeType.value());
//			maps.add(map);
//		}
//		return ObjectAndJsonUtil.MapsToJsonArray(maps,null);
//	}
//	
//}
