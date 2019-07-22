package com.it.ocs.amazon.order;

import org.apache.log4j.Logger;

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrders;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersException;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersByNextTokenRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersByNextTokenResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ResponseHeaderMetadata;

public class AmazonClientRun {
	private final static Logger log = Logger.getLogger(AmazonClientRun.class);
	
	public static String invokeListOrders(MarketplaceWebServiceOrders client, ListOrdersRequest request) {
		try {
			// Call the service.
			ListOrdersResponse response = client.listOrders(request);
			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
			// We recommend logging every the request id and timestamp of every
			// call.
			log.info("Response:");
			log.info("RequestId: " + rhmd.getRequestId());
			log.info("Timestamp: " + rhmd.getTimestamp());
			String responseXml = response.toXML();
			log.info(responseXml);
			return responseXml;
		} catch (MarketplaceWebServiceOrdersException ex) {
			// Exception properties are important for diagnostics.
			log.info("Service Exception:");
			ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
			if (rhmd != null) {
				log.info("RequestId: " + rhmd.getRequestId());
				log.info("Timestamp: " + rhmd.getTimestamp());
			}
			log.info("Message: " + ex.getMessage());
			log.info("StatusCode: " + ex.getStatusCode());
			log.info("ErrorCode: " + ex.getErrorCode());
			log.info("ErrorType: " + ex.getErrorType());
			log.error("请求订单列表异常", ex);
			//防止请求被扼杀

			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				
			}
			String xml = invokeListOrders(client,request);
			if(!"".equals(xml)){
				return xml;
			}

			return "";
		}
	}

	public static String invokeListOrdersByNextToken(MarketplaceWebServiceOrders client,
			ListOrdersByNextTokenRequest request) {
		try {
			// Call the service.
			ListOrdersByNextTokenResponse response = client.listOrdersByNextToken(request);
			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
			// We recommend logging every the request id and timestamp of every
			// call.
			log.info("Response:");
			log.info("RequestId: " + rhmd.getRequestId());
			log.info("Timestamp: " + rhmd.getTimestamp());
			String responseXml = response.toXML();
			log.info(responseXml);
			return responseXml;
		} catch (MarketplaceWebServiceOrdersException ex) {
			// Exception properties are important for diagnostics.
			log.info("Service Exception:");
			ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
			if (rhmd != null) {
				log.info("RequestId: " + rhmd.getRequestId());
				log.info("Timestamp: " + rhmd.getTimestamp());
			}
			log.info("Message: " + ex.getMessage());
			log.info("StatusCode: " + ex.getStatusCode());
			log.info("ErrorCode: " + ex.getErrorCode());
			log.info("ErrorType: " + ex.getErrorType());
			log.error("根据byNexttoken请求订单异常", ex);
			//防止请求被扼杀
		
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				
			}
			String xml = invokeListOrdersByNextToken(client,request);
			if(!"".equals(xml)){
				return xml;
			}

			return "";
		}
	}

	public static String invokeListOrderItems(MarketplaceWebServiceOrders client, ListOrderItemsRequest request) {
		try {
			// Call the service.
			ListOrderItemsResponse response = client.listOrderItems(request);
			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();

			log.info("Response:");
			log.info("RequestId: " + rhmd.getRequestId());
			log.info("Timestamp: " + rhmd.getTimestamp());
			String responseXml = response.toXML();
			log.info(responseXml);
			return responseXml;
		} catch (MarketplaceWebServiceOrdersException ex) {
			log.info("Service Exception:");
			ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
			if (rhmd != null) {
				log.info("RequestId: " + rhmd.getRequestId());
				log.info("Timestamp: " + rhmd.getTimestamp());
			}
			log.info("Message: " + ex.getMessage());
			log.info("StatusCode: " + ex.getStatusCode());
			log.info("ErrorCode: " + ex.getErrorCode());
			log.info("ErrorType: " + ex.getErrorType());
			log.error("请求订单详情异常", ex);
			//防止请求被扼杀
	
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				
			}
			String xml = invokeListOrderItems(client,request);
			if(!"".equals(xml)){
				return xml;
			}

			return "";
		}
		
	}
}
