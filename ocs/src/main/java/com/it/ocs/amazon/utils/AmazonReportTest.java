package com.it.ocs.amazon.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.it.ocs.amazon.model.AmazonRequestConfig;
import com.it.ocs.amazon.service.AmazonReportService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.synchronou.model.AmazonAccountModel;

public class AmazonReportTest {

	private static AmazonAccountModel createDeAccount() {
		AmazonAccountModel account = new AmazonAccountModel();
		account.setPlatform("DE");
		account.setSellerId("AV7KSH7XB8RNM");
		account.setSecretKey("oreVInCN3zs+gEdjUmxuVXRIAre+EQf6aLQh6OFS");
		account.setAccessKey("AKIAIQJPPHYX7ZMKPTNQ");
		account.setUrl("https://mws.amazonservices.de");// https://mws.amazonservices.com/FulfillmentOutboundShipment/2010-10-01
		account.setSiteId("A1RKKUPIHCS9HS,A1F83G8C2ARO7P,A1PA6795UKMFR9,A13V1IB3VIYZZH,APJ6JRA9NG5V4");
		return account;
	}
	
	private static AmazonAccountModel createUSAccount() {
		return createAccount("US", "AAF37WJS3P6BT", "HlO24NtOL4BW4jiHI+xtug61gaNZweOLadWhhPlA", "AKIAJKN2XEOEH4DE4NEQ", "https://mws.amazonservices.com", "ATVPDKIKX0DER");
	}
	
	private static AmazonAccountModel createAccount(String platform, String sellerId, String secretKey, String accessKey, String url, String siteId) {
		AmazonAccountModel account = new AmazonAccountModel();
		account.setPlatform(platform);
		account.setSellerId(sellerId);
		account.setSecretKey(secretKey);
		account.setAccessKey(accessKey);
		account.setUrl(url);
		account.setSiteId(siteId);
		return account;
	}

	public static List<String> amazonPOST(AmazonAccountModel account, String reportType) {
		List<String> result = new ArrayList<String>();
		AmazonRequestConfig config = new AmazonRequestConfig(account, "RequestReport", "2009-01-01");
		Map<String, String> queryParam = new HashMap<>();
		queryParam.put("ReportType", reportType);
		//queryParam.put("StartDate", "2018-03-30T00:00:00Z");
		//String[] siteIds = account.getSiteId().split(",");
		/*CollectionUtil.each(siteIds, new IAction<String>() {
			public void excute(String siteId) {
				queryParam.put("MarketplaceIdList.Id.1", siteId);	
				config.setQueryParameters(queryParam);
				result.add(AmazonHttpUtil.amazonPOST(config));
			}
		});*/
		config.setQueryParameters(queryParam);
		String resp = AmazonHttpUtil.amazonPOST(config);
		System.out.println(resp);
		return result;
	}
	
	public static void main(String[] args) {
		//initAccounts();
		AmazonAccountModel account = createDeAccount();
		amazonPOST(account, "_GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_");
		amazonPOST(account, "_GET_RESERVED_INVENTORY_DATA_");
	}

	static ApplicationContext context = null;
	
	private static void initSpring() {
		context = new ClassPathXmlApplicationContext("classpath:spring.xml");
	}
	
	static List<AmazonAccountModel> accounts = new ArrayList<AmazonAccountModel>();
	
	private static void initAccounts() {
		accounts.add(createAccount("US", "AAF37WJS3P6BT", "HlO24NtOL4BW4jiHI+xtug61gaNZweOLadWhhPlA", "AKIAJKN2XEOEH4DE4NEQ", "https://mws.amazonservices.com", "ATVPDKIKX0DER"));
		accounts.add(createAccount("JP", "AL6SHBZ4JONBI", "YCFXlCQ+0PRE5bs1a/b7DHAN/6w0TN3AiprhuKZ7", "AKIAJOUKOHWJYVLMRURA", "https://mws.amazonservices.jp", "A1VC38T7YXB528"));
		accounts.add(createAccount("CA", "A3W1ARFCWJR0HL", "0pPBG0UOWhgro7pWc6BjDMvYam1pMJJQqDt0PFU0", "AKIAJAQWT7A5XPYZ3ANA", "https://mws.amazonservices.ca", "A2EUQ1WTGCTBG2"));
		accounts.add(createAccount("DE", "AV7KSH7XB8RNM", "oreVInCN3zs+gEdjUmxuVXRIAre+EQf6aLQh6OFS", "AKIAIQJPPHYX7ZMKPTNQ", "https://mws.amazonservices.de", "A1RKKUPIHCS9HS,A1F83G8C2ARO7P,A1PA6795UKMFR9,A13V1IB3VIYZZH,APJ6JRA9NG5V4"));
	}
	
	static final String[] types = new String[] { "_GET_AFN_INVENTORY_DATA_", "_GET_FBA_FULFILLMENT_CURRENT_INVENTORY_DATA_",
			"_GET_FBA_FULFILLMENT_MONTHLY_INVENTORY_DATA_", "_GET_FBA_FULFILLMENT_INVENTORY_RECEIPTS_DATA_",
			"_GET_FBA_FULFILLMENT_INVENTORY_SUMMARY_DATA_", "_GET_FBA_FULFILLMENT_INVENTORY_ADJUSTMENTS_DATA_",
			"_GET_FBA_FULFILLMENT_INVENTORY_HEALTH_DATA_", "_GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_",
			"_GET_FBA_MYI_ALL_INVENTORY_DATA_" };

	/*
	public static void main(String[] args) {
		initSpring();
		AmazonAccountModel account = createDeAccount();
		AmazonReportService ars = (AmazonReportService) context.getBean("amazonReportService");
		CollectionUtil.each(types, new IAction<String>() {
			public void excute(String type) {
				String response = amazonPOST(account, type);
				System.out.println(">>> before parse: " + response);
				Map<String, Object> data = ars.parseAmazonFBAInventoryResponseXml(response);
				System.out.println(">>> after parse: " + data);
				if (null != data) {
					//ars.saveRequests((List<Map<String,Object>>) data.get("data"),  account);
				}
				//ars.getReportRequestListByAccount(account);
				//ars.downloadReportByAccount(account);
			}
		});
	}
	*/
	
	/*public static void main(String[] args) {
		initSpring();
		initAccounts();
		String step = "1";
		AmazonReportService ars = (AmazonReportService) context.getBean("amazonReportService");
		if("1".equals(step)) {
			ars.genFBAInventoryRequests();
		} else if("2".equals(step)) {
			CollectionUtil.each(accounts , new IAction<AmazonAccountModel>() {
				@Override
				public void excute(AmazonAccountModel account) {
					ars.getReportRequestListByAccount(account);
				}
			});
		} else if("3".equals(step)) {
			CollectionUtil.each(accounts , new IAction<AmazonAccountModel>() {
				@Override
				public void excute(AmazonAccountModel account) {
					ars.downloadReportByAccount(account);
				}
			});
		}
	}*/
	
	/*
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		initSpring();
		initAccounts();
		String step = "3";
		AmazonReportService ars = (AmazonReportService) context.getBean("amazonReportService");
		CollectionUtil.each(new AmazonAccountModel[] { createUSAccount() } , new IAction<AmazonAccountModel>() {
			public void excute(AmazonAccountModel account) {
				CollectionUtil.each(new String[] { 
						"_GET_RESERVED_INVENTORY_DATA_" }, new IAction<String>() {
					public void excute(String type) {
						if("1".equals(step)) {
							CollectionUtil.each(amazonPOST(account, type), new IAction<String>() {
								public void excute(String response) {
									System.out.println(">>> before parse: " + response);
									Map<String, Object> data = ars.parseAmazonFBAInventoryResponseXml(response);
									System.out.println(">>> after parse: " + data);
									if (null != data) {
										ars.saveRequests((List<Map<String,Object>>) data.get("data"),  account);
									}
								}
							});
						} else if("2".equals(step)) {
							ars.getReportRequestListByAccount(account);
							System.out.println(">>> get report");
						} else if("3".equals(step)) {
							ars.downloadReportByAccount(account);
							System.out.println(">>> download report");
						}
					}
				});
			}
		});
		System.out.println("End");
	}
	*/
}
