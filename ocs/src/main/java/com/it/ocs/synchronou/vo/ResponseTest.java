package com.it.ocs.synchronou.vo;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;

import com.it.ocs.synchronou.service.impl.BaseHttpsService;

public class ResponseTest {

	
	public static void main(String[] args) {
		
		BaseHttpsService baseHttpsService = new BaseHttpsService();
		String url = "https://api.ebay.com/ws/api.dll";
		Map<String, String> map = new HashMap<>();
		map.put("X-EBAY-API-SITEID","0");
		map.put("X-EBAY-API-COMPATIBILITY-LEVEL","967");
		map.put("X-EBAY-API-CALL-NAME","GetSellingManagerInventory");
		map.put("X-EBAY-API-IAF-TOKEN", "AgAAAA**AQAAAA**aAAAAA**Mfb+Vw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEmYSiC5SDpQ2dj6x9nY+seQ**q3YDAA**AAMAAA**a+lc7JZgzUBlqZRxivZzjyionMPWq2fGCNHLxQ923k/f6OqqKAIEfj5EmVXCI6QxJrP2pJqMGMgTcp2Yz5O87GGdHS9MiZHt5AxLJ2skN56kAvdtJQlf0q+3Fft1SfWJgE3wdkrm+m2MAU7vKndoLAdTrFv6h2A4UXNUc2Xi3rV1RON4pxxLLxp79NBt2rpx76fC/5A+dTlW/VVFZ3BCRsJ7HC1yUB1DM6Vpb+DGHy+TW4Yp0wCjJQH7V4peHhXKf2W8AK83LC1CqibdkWXvPPOhqzHssfQg6O+gK11s6WHJsee+OxKspS0dOYq9PUEoFgpURwL7teii53PIvJLWNBWDpv2Bxh2UYKKIlc1IkqjcWYVVwa6yalvv0pRBbcRR4ruzOjpUwrCWvV58PyAN7pUlwHabyHHoo5Qfps/QQYTP7Ac6UojJgA+UazSWuX5EwjULppBPPUzMFHdnSV1jobfUOOXHFcYd/Pw/xFZ/NahsygwsGAELZI7hGm/bIYS6pFoT1HNOL4uutBMNECy7fxQiFbiXNYZLLINRqCFtsiBUHa4Dniwss42CoRQLnwyXKfEuPdS9ZTMFnRBKu9dTQHs1P/zLkWeAP8L0mZAVjNSX0pSEVh4TvokcRUTJsAQlEViSCP8ZbJ+P6NoPDe4XRoyPB53FxJirq61Z2Bqe5X9VK4LReb5OfcZGN1MnhuhZ581CcbaLwwWQWynT0YO8pDIiaNFel9WlzhQ8zrq4fEjODDqFU2Bo9MRTs5ouriyG");
		String requestXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
							+"<GetSellingManagerInventoryRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">"
							+"<ErrorLanguage>en_US</ErrorLanguage>"
							+"<WarningLevel>High</WarningLevel>"
							+"</GetSellingManagerInventoryRequest>";
		Document doc = baseHttpsService.getPesponseXml(url, map, requestXml);
		System.out.println(doc.asXML());
	}
}
