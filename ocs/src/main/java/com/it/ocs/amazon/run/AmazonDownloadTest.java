package com.it.ocs.amazon.run;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetReportRequest;
import com.it.ocs.amazon.common.AmazonReportServiceClient;
import com.it.ocs.amazon.model.AmazonRequestMode;
import com.it.ocs.amazon.model.ReportRequestListModel;
import com.it.ocs.amazon.order.AmazonRequest;
import com.it.ocs.amazon.order.GetAmazonClient;

public class AmazonDownloadTest {

	public static void main(String[] args) {
		download(null, null);
	}
	public static AmazonReportServiceClient createReportDownloadClient(AmazonRequestMode requestMode){
		 MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
       config.setServiceURL("https://mws.amazonservices.com");
//       return new AmazonReportServiceClient(requestMode.getAccessKey(), requestMode.getSecretKey(), "ocs", "1.0", config);
       
       return new AmazonReportServiceClient("AKIAJRZYZHYY5FVC2PUQ", "Cpb6k2afwvPvU6z3afAqK5Ga+R6cxejoGUIhn9bW", "APP", "1.0", config);
	}
	public static GetReportRequest createReportRequest(AmazonRequestMode requestMode) {
		GetReportRequest request = new GetReportRequest();
//		request.setMerchant(requestMode.getSellerId());
		request.setMerchant("A2PH6LYOY5868R");
//		String reportId = (String)requestMode.getParam().get("reportId");
//		request.setReportId(reportId);
		request.setReportId("14627154281018014");
		return request;
	 }
	public static void download(AmazonRequestMode requestMode,ReportRequestListModel request){
		AmazonReportServiceClient client = createReportDownloadClient(requestMode);
		GetReportRequest getReportRequest = createReportRequest(requestMode);
			try {
				String data = client.downloadReport(getReportRequest);
				System.out.println(data);
			} catch (MarketplaceWebServiceException e) {
				e.printStackTrace();
			}
	}

}
