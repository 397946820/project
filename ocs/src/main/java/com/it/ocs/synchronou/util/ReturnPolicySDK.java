//package com.it.ocs.synchronou.util;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.ebay.soap.eBLBaseComponents.RefundDetailsType;
//import com.ebay.soap.eBLBaseComponents.RestockingFeeValueDetailsType;
//import com.ebay.soap.eBLBaseComponents.ReturnsAcceptedDetailsType;
//import com.ebay.soap.eBLBaseComponents.ReturnsWithinDetailsType;
//import com.ebay.soap.eBLBaseComponents.ShippingCostPaidByDetailsType;
//
//public class ReturnPolicySDK {
//
//	public static String getRefund(RefundDetailsType[] refundDetailsTypes){
//		 
//		if (refundDetailsTypes.length>0) {
//		    	List<Map<String, String>> maps = new ArrayList<>();
//		    	
//				for (RefundDetailsType refundDetailsType : refundDetailsTypes) {
//					Map<String, String> map = new HashMap<>();
//					map.put("refundOption", refundDetailsType.getRefundOption());
//					map.put("description", refundDetailsType.getDescription());
//					maps.add(map);
//				}
//				
//				return ObjectAndJsonUtil.MapsToJsonArray(maps,null);
//				
//			}else{
//				return null;
//			}
//	}
//	public static String getReturnsWithin(ReturnsWithinDetailsType[] returnsWithinDetailsTypes){
//		if(returnsWithinDetailsTypes.length>0){
//			List<Map<String, String>> maps = new ArrayList<>();
//			for (ReturnsWithinDetailsType returnsWithinDetailsType : returnsWithinDetailsTypes) {
//				Map<String, String> map = new HashMap<>();
//				map.put("returnsWithinOption", returnsWithinDetailsType.getReturnsWithinOption());
//				map.put("description", returnsWithinDetailsType.getDescription());
//				maps.add(map);
//			}
//			return ObjectAndJsonUtil.MapsToJsonArray(maps,null);
//		}else{
//		return null;
//		}
//	}
//	public static String getShippingCostPaidBy(ShippingCostPaidByDetailsType[]  shippingCostPaidByDetailsTypes){
//		
//		if(shippingCostPaidByDetailsTypes.length>0){
//			List<Map<String, String>> maps = new ArrayList<>();
//			for(ShippingCostPaidByDetailsType shippingCostPaidByDetailsType : shippingCostPaidByDetailsTypes){
//				Map<String, String> map = new HashMap<>();
//				map.put("shippingCostPaidByOption", shippingCostPaidByDetailsType.getShippingCostPaidByOption());
//				map.put("description", shippingCostPaidByDetailsType.getDescription());
//				maps.add(map);
//			}
//			return ObjectAndJsonUtil.MapsToJsonArray(maps,null);
//		}else{
//			return null;
//		}
//	}
//	public static String getRestockingFeeValue(RestockingFeeValueDetailsType[] restockingFeeValueDetailsTypes){
//		
//		if(restockingFeeValueDetailsTypes.length>0){
//			List<Map<String, String>> maps = new ArrayList<>();
//			for(RestockingFeeValueDetailsType restockingFeeValueDetailsType : restockingFeeValueDetailsTypes){
//				Map<String, String> map = new HashMap<>();
//				map.put("restockingFeeValueOption", restockingFeeValueDetailsType.getRestockingFeeValueOption());
//				map.put("description", restockingFeeValueDetailsType.getDescription());
//				maps.add(map);
//			}
//			return ObjectAndJsonUtil.MapsToJsonArray(maps,null);
//		}else{
//			return null;
//		}
//	}
//	public static String getReturnsAccepted(ReturnsAcceptedDetailsType[] returnsAcceptedDetailsTypes){
//		
//		if(returnsAcceptedDetailsTypes.length>0){
//			List<Map<String, String>> maps = new ArrayList<>();
//			for(ReturnsAcceptedDetailsType returnsAcceptedDetailsType : returnsAcceptedDetailsTypes){
//				Map<String, String> map = new HashMap<>();
//				map.put("returnsAcceptedOption", returnsAcceptedDetailsType.getReturnsAcceptedOption());
//				map.put("description", returnsAcceptedDetailsType.getDescription());
//				maps.add(map);
//			}
//			return ObjectAndJsonUtil.MapsToJsonArray(maps,null);
//		}else{
//			return null;
//		}
//	}
//}
