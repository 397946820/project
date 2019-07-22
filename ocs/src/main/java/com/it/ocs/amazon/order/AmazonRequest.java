package com.it.ocs.amazon.order;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.mws.model.GetReportRequest;
import com.amazonaws.mws.model.GetReportRequestListByNextTokenRequest;
import com.amazonaws.mws.model.GetReportRequestListRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersByNextTokenRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest;
import com.it.ocs.amazon.model.AmazonRequestMode;

public class AmazonRequest {
	public final static int BY_UPDATE_DATE = 0;
	public final static int BY_CREATE_DATE = 1;
	
	public static ListOrdersRequest createOrderRequest(AmazonRequestMode requestMode,int type){
		ListOrdersRequest request = new ListOrdersRequest();
		request.setSellerId(requestMode.getSellerId());

		if(type == BY_CREATE_DATE){
			request.setCreatedAfter(requestMode.getCreatedAfter());
			request.setCreatedBefore(requestMode.getCreatedBefore());
		}
		if(type == BY_UPDATE_DATE){
			request.setLastUpdatedAfter(requestMode.getUpdatedAfter());
			request.setLastUpdatedBefore(requestMode.getUpdatedBefore());
		}
		
		
		List<String> orderStatus = new ArrayList<String>();
		request.setOrderStatus(orderStatus);
		request.setMarketplaceId(requestMode.getMarketplaceId());
		List<String> fulfillmentChannel = new ArrayList<String>();
		request.setFulfillmentChannel(fulfillmentChannel);
		List<String> paymentMethod = new ArrayList<String>();
		request.setPaymentMethod(paymentMethod);
		Integer maxResultsPerPage = 100;
		request.setMaxResultsPerPage(maxResultsPerPage);
		List<String> tfmShipmentStatus = new ArrayList<String>();
		request.setTFMShipmentStatus(tfmShipmentStatus);
		return request;
	}
	
	public static ListOrdersByNextTokenRequest createOrderByNextTokenRequest(AmazonRequestMode requestMode){
		ListOrdersByNextTokenRequest request = new ListOrdersByNextTokenRequest();
	    String sellerId = requestMode.getSellerId();
	    request.setSellerId(sellerId);
	    String nextToken = requestMode.getNextToken();
	    request.setNextToken(nextToken);
	    return request;
    }
	
	public static ListOrderItemsRequest createOrderItemRequest(AmazonRequestMode requestMode){
		ListOrderItemsRequest request = new ListOrderItemsRequest();
        String sellerId = requestMode.getSellerId();
        request.setSellerId(sellerId);
        String amazonOrderId = (String)requestMode.getParam().get("AmazonOrderId");
        request.setAmazonOrderId(amazonOrderId);
        return request;
    }
	
	public static GetReportRequestListRequest createReportRequestListRequest(AmazonRequestMode requestMode){
		GetReportRequestListRequest request = new GetReportRequestListRequest();
		request.setMerchant(requestMode.getSellerId());
		request.setRequestedFromDate(requestMode.getCreatedAfter());
		request.setRequestedToDate(requestMode.getCreatedBefore());
		request.setMaxCount(50);
		return request;
	}
	
	public static GetReportRequestListByNextTokenRequest createReportRequestListByNextTokenRequest(AmazonRequestMode requestMode){
		GetReportRequestListByNextTokenRequest request = new GetReportRequestListByNextTokenRequest();
		request.setMerchant(requestMode.getSellerId());
		request.setNextToken(requestMode.getNextToken());
		return request;
	}
	
	public static GetReportRequest createReportRequest(AmazonRequestMode requestMode){
		GetReportRequest request = new GetReportRequest();
		request.setMerchant(requestMode.getSellerId());
		String reportId = (String)requestMode.getParam().get("reportId");
		request.setReportId(reportId);
		return request;
	}

}
