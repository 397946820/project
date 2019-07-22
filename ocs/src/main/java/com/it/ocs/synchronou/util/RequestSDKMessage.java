//package com.it.ocs.synchronou.util;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.ebay.sdk.ApiCall;
//import com.ebay.sdk.call.AddItemCall;
//import com.ebay.sdk.call.ReviseItemCall;
//import com.ebay.soap.eBLBaseComponents.AbstractResponseType;
//import com.ebay.soap.eBLBaseComponents.AckCodeType;
//import com.ebay.soap.eBLBaseComponents.AmountType;
//import com.ebay.soap.eBLBaseComponents.ErrorType;
//import com.ebay.soap.eBLBaseComponents.FeeType;
//
//public class RequestSDKMessage {
//	
//	public static String getSDKMessage(ApiCall apiCall,String message){
//		StringBuffer stringBuffer = new StringBuffer();
//		if(ValidationUtil.isNull(message)){
//			message="操作";
//		}
//		AbstractResponseType abstractResponseType =  apiCall.getResponseObject();
//		AckCodeType ackCodeType = null;
//		if(!ValidationUtil.isNull(abstractResponseType)){
//			 ackCodeType = abstractResponseType.getAck();
//		}else{
//			return "Web Service framework internal error. execute exception.";
//		}
//		
//		String ack = ackCodeType.value();
//		if(ack.equalsIgnoreCase("Warning")||ack.equalsIgnoreCase("Success")){
//			stringBuffer.append("<b>"+message+"提示：</b>:"+message+"成功！<br/>");
//		}else{
//			stringBuffer.append("<b>"+message+"提示：</b>:"+message+"失败！<br/>");
//		}
//		
//		ErrorType[] errorTypes =  abstractResponseType.getErrors();
//	    //stringBuffer.append("<h5>Errors:</h5><br/>");
//		for (ErrorType errorType : errorTypes) {
//			String red = "red";
//			String orange = "orange";
//			String mg = errorType.getSeverityCode().value();
//			if(mg.equalsIgnoreCase("WARNING")){
//				stringBuffer.append("		<span  style=\"color:"+orange+"\">"+errorType.getShortMessage()+"["+errorType.getSeverityCode()+"-"+errorType.getErrorCode()+"]</span><br/>");
//			}else{
//				stringBuffer.append("		<span  style=\"color:"+red+"\">"+errorType.getShortMessage()+"["+errorType.getSeverityCode()+"-"+errorType.getErrorCode()+"]</span><br/>");
//			}
//			stringBuffer.append("		  LongMessage:"+errorType.getLongMessage()+"<br/>");
//			stringBuffer.append("--------------------------------------------<br/>");
//		}
//		
//		return stringBuffer.toString();
//	}
//	
//	public static Map<String, String> getSDKFeeMessage(FeeType[] feeTypes){
//		StringBuffer feeBuffer = new StringBuffer();
//		Map<String, String> map = new HashMap<>();
//		AmountType totalPrice = new AmountType();
//		double total = 0d;
//		for (FeeType feeType : feeTypes) {
//			AmountType feeAmount = feeType.getFee();
//			AmountType amountType = feeType.getPromotionalDiscount();
//			if (feeAmount.getValue()>0&&!feeType.getName().equals("ListingFee")) {
//				if (!ValidationUtil.isNull(amountType)) {
//					feeBuffer.append(feeType.getName()+":  "+amountType.getValue()+amountType.getCurrencyID()+"<br/>");
//					feeBuffer.append("--------------------------------------------<br/>");
//				}else{
//					feeBuffer.append(feeType.getName()+":  "+feeAmount.getValue()+feeAmount.getCurrencyID()+"<br/>");
//
//					feeBuffer.append("--------------------------------------------<br/>");
//				}
//			}else if(feeType.getName().equals("ListingFee")){
//				if (!ValidationUtil.isNull(amountType)) {
//					total = amountType.getValue();
//				}else{
//
//					total = feeAmount.getValue();
//				}
//			}
//			totalPrice.setValue(totalPrice.getValue()+feeAmount.getValue());
//			totalPrice.setCurrencyID(feeType.getFee().getCurrencyID());
//			
//		}
//		map.put("totalPrice", "<b>总费用:</b>"+total+totalPrice.getCurrencyID()+"<br/>");
//		map.put("feeBuffer", feeBuffer.toString());
//		return map;
//	}
//}
