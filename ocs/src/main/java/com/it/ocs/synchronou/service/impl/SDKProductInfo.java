//package com.it.ocs.synchronou.service.impl;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.services.client.ClientConfig;
//import com.ebay.services.client.FindingServiceClientFactory;
//import com.ebay.services.finding.FindItemsIneBayStoresRequest;
//import com.ebay.services.finding.FindItemsIneBayStoresResponse;
//import com.ebay.services.finding.FindingServicePortType;
//import com.ebay.services.finding.PaginationInput;
//import com.ebay.services.finding.PaginationOutput;
//import com.it.ocs.synchronou.util.ObjectAndJsonUtil;
//
//public class SDKProductInfo extends BaseEbaySDKService{
//
//	/*public static void main(String[] args) {
//		
//		SDKProductInfo product = new SDKProductInfo();
//		ApiContext apiContext = product.getApiContext("AgAAAA**AQAAAA**aAAAAA**GWvkWA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GlDZeHow2dj6x9nY+seQ**0ScEAA**AAMAAA**XlRylrkXXCoY5UTwzSe/uNQwQLxTK6EYW/p15MKB454clz3lrKW9qzXAIq2oY/EUkzA2YiF2QbwrjfETbqAmBYWxqPr3ht5moulrZQr0y3sMoWTLA56ASPjsXm1TRojVEOezRvrAHKarQq+Gl2mGe9SS7VF7OBSG1TEPdc6BY59w3cEpZqt53j3FpHsjn0N3ZQoDb0NcyEfFRDw3CEPPWL1546z9x455lnjtOCnocwJg83gt3oDcCN+Uuq1KxoEAKboIjAIBr3VcV9mTbIu+gW65Kn2qdbUUtH/Qx22oU0xAjRSbQfpM0vfsfNmckrmgexjS7irpjQ9YFhMlEWM1i6Dk6iA0gubEijkKomxUl77vZCz7b3MV3urZJEbSNyPjGrrCRtH8lo6Th3o0lVdlVJhYmtx0Nw73pO+3UAI22V7lbf+SXqH6UI8dhZJ+zMLVGzgoB79aiEkTLeB+1VK83h21Iv1XdwAw/l5LI+4UwxR2rF4218ZTjRdQEOWgqokdxKsCiEVl7UdEF22+8lnRrjgYBESbkf136mqYvG4O5cFEVRKKVghD2VfTV4gwnWCkmIkrVxIbb0wvEfK2qA5Tas5BNFOazQPVmLchC6CjskzKXGS1ohy6TZ61QlfezEE4r5DHgpjyQOAkIpwKnmo8bD2zZY654lnOakEb4zRzIgVUks20FirrekeR9z/YCNyT0qsSk+6u2BE4oJV0hcVA0zeVzlaGTk0qiTlQjmCOL0/XXRcpFFWRQIhTVh+S2Pd8", "https://api.sandbox.ebay.com/wsapi");
//		ClientConfig clientConfig = product.getClientConfig("yangguan-LedEbay-PRD-9090b840d-c638c240", "http://svcs.ebay.com/services/search/FindingService/v1","EBAY-GB");
//		FindingServicePortType findingServicePortType = FindingServiceClientFactory.getServiceClient(clientConfig);
//		FindItemsIneBayStoresRequest itemsRequest = new FindItemsIneBayStoresRequest();
//		//itemsRequest.setStoreName("casunglight");
//		itemsRequest.setStoreName("LE United Kindom");
//		PaginationInput pi = new PaginationInput();
//		pi.setEntriesPerPage(1);
//		itemsRequest.setPaginationInput(pi);
//		FindItemsIneBayStoresResponse result = findingServicePortType.findItemsIneBayStores(itemsRequest);
//		System.out.println(result.getAck());
//		
//		PaginationOutput paginationOutput = result.getPaginationOutput();
//		Integer itemCount = paginationOutput.getTotalEntries();
//		System.out.println(itemCount);
//		String resultJson = ObjectAndJsonUtil.ObjectToJson(paginationOutput,PaginationOutput.class);
//		System.out.println(resultJson);
//	}
//	*/
//}
