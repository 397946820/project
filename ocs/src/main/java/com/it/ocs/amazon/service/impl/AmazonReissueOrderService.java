package com.it.ocs.amazon.service.impl;

import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetReportRequestListRequest;
import com.amazonaws.mws.model.GetReportRequestListResponse;
import com.it.ocs.amazon.model.AmazonRequestMode;
import com.it.ocs.amazon.order.AmazonRequest;
import com.it.ocs.amazon.order.GetAmazonClient;
import com.it.ocs.amazon.utils.AmazonUtils;
import com.it.ocs.synchronou.model.AmazonAccountModel;
import com.it.ocs.synchronou.util.UTCTimeUtils;

public class AmazonReissueOrderService {
	public static void main(String[] args) throws MarketplaceWebServiceException {
		AmazonAccountModel account = new AmazonAccountModel();
		account.setPlatform("US");
		account.setSellerId("AAF37WJS3P6BT");
		account.setSecretKey("HlO24NtOL4BW4jiHI+xtug61gaNZweOLadWhhPlA");
		account.setAccessKey("AKIAJKN2XEOEH4DE4NEQ");
		account.setUrl("https://mws.amazonservices.com");//https://mws.amazonservices.com/FulfillmentOutboundShipment/2010-10-01
		account.setSiteId("ATVPDKIKX0DER");
		/*AmazonRequestMode requestMode = AmazonUtils.getRequestModel(account);
		requestMode.setCreatedAfter(UTCTimeUtils.getUTCTimeStr(-24*3));
		AmazonReportServiceClient client = GetAmazonClient.createReissueOrderClient(requestMode);
		ReissueOrderRequest request = AmazonRequest.createReissueOrderListRequest(requestMode);
		String dom = client.getReissueOrderList(request);
		System.out.println(dom);*/
		
		AmazonRequestMode requestMode = AmazonUtils.getRequestModel(account);
		requestMode.setCreatedAfter(UTCTimeUtils.getUTCTimeStr(-24*3));
		requestMode.setCreatedBefore(UTCTimeUtils.getUTCTimeStr(0));
		MarketplaceWebServiceClient client = GetAmazonClient.createReportClient(requestMode);
		GetReportRequestListRequest request = AmazonRequest.createReportRequestListRequest(requestMode);
		GetReportRequestListResponse response = client.getReportRequestList(request);
		System.out.println(response.toXML());
	}
}
