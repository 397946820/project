package com.it.ocs.amazon.order;
import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersAsyncClient;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersConfig;
import com.it.ocs.amazon.common.AmazonReportServiceClient;
import com.it.ocs.amazon.model.AmazonRequestMode;
public class GetAmazonClient {
	
	public static MarketplaceWebServiceOrdersAsyncClient createClient(AmazonRequestMode requestMode){
		 MarketplaceWebServiceOrdersConfig config = new MarketplaceWebServiceOrdersConfig();
         config.setServiceURL(requestMode.getServiceURL());
         // Set other client connection configurations here.
         return new MarketplaceWebServiceOrdersAsyncClient(requestMode.getAccessKey(), requestMode.getSecretKey(), "ocs", "1.0", config, null);
	}
	
	public static MarketplaceWebServiceClient createReportClient(AmazonRequestMode requestMode){
		 MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
         config.setServiceURL(requestMode.getServiceURL());
         return new MarketplaceWebServiceClient(requestMode.getAccessKey(), requestMode.getSecretKey(), "ocs", "1.0", config);
	}
	
	public static AmazonReportServiceClient createReportDownloadClient(AmazonRequestMode requestMode){
		 MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
        config.setServiceURL(requestMode.getServiceURL());
        return new AmazonReportServiceClient(requestMode.getAccessKey(), requestMode.getSecretKey(), "ocs", "1.0", config);
	}

}
