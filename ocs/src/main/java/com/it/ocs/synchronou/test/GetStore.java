package com.it.ocs.synchronou.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class GetStore {
	private List<StoreModel> storeModels = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		
		URL realUrl = new URL("https://api.ebay.com/ws/api.dll");
		
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                     +" <GetStoreRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">"
					 +"<RequesterCredentials>"
					 +"<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**Mfb+Vw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEmYSiC5SDpQ2dj6x9nY+seQ**q3YDAA**AAMAAA**a+lc7JZgzUBlqZRxivZzjyionMPWq2fGCNHLxQ923k/f6OqqKAIEfj5EmVXCI6QxJrP2pJqMGMgTcp2Yz5O87GGdHS9MiZHt5AxLJ2skN56kAvdtJQlf0q+3Fft1SfWJgE3wdkrm+m2MAU7vKndoLAdTrFv6h2A4UXNUc2Xi3rV1RON4pxxLLxp79NBt2rpx76fC/5A+dTlW/VVFZ3BCRsJ7HC1yUB1DM6Vpb+DGHy+TW4Yp0wCjJQH7V4peHhXKf2W8AK83LC1CqibdkWXvPPOhqzHssfQg6O+gK11s6WHJsee+OxKspS0dOYq9PUEoFgpURwL7teii53PIvJLWNBWDpv2Bxh2UYKKIlc1IkqjcWYVVwa6yalvv0pRBbcRR4ruzOjpUwrCWvV58PyAN7pUlwHabyHHoo5Qfps/QQYTP7Ac6UojJgA+UazSWuX5EwjULppBPPUzMFHdnSV1jobfUOOXHFcYd/Pw/xFZ/NahsygwsGAELZI7hGm/bIYS6pFoT1HNOL4uutBMNECy7fxQiFbiXNYZLLINRqCFtsiBUHa4Dniwss42CoRQLnwyXKfEuPdS9ZTMFnRBKu9dTQHs1P/zLkWeAP8L0mZAVjNSX0pSEVh4TvokcRUTJsAQlEViSCP8ZbJ+P6NoPDe4XRoyPB53FxJirq61Z2Bqe5X9VK4LReb5OfcZGN1MnhuhZ581CcbaLwwWQWynT0YO8pDIiaNFel9WlzhQ8zrq4fEjODDqFU2Bo9MRTs5ouriyG</eBayAuthToken>"
					 +" </RequesterCredentials>"
					 +"<ErrorLanguage>en_US</ErrorLanguage>"
					 +"<WarningLevel>High</WarningLevel>"
					 +"<LevelLimit>3</LevelLimit>"
					 +"</GetStoreRequest>";
		byte[] xmlDate = xml.getBytes();
		
		URLConnection connection = realUrl.openConnection();
		connection.setDoOutput(true);
		
		connection.setRequestProperty("X-EBAY-API-SITEID", "0");
		connection.setRequestProperty("X-EBAY-API-COMPATIBILITY-LEVEL", "967");
		connection.setRequestProperty("X-EBAY-API-CALL-NAME", "GetStore");
		OutputStream ou= connection.getOutputStream();
		ou.write(xmlDate);
		ou.flush();
		
		 StringBuilder text = new StringBuilder();
		 BufferedReader in = new BufferedReader(new InputStreamReader(
		 connection.getInputStream()));
		 String inputLine = null;
		 while ( (inputLine = in.readLine()) != null) {
					  
			 text.append(inputLine);
			  
		 }
		
		System.out.println(text);
		Document doc = null;
		
		try{
			
			doc = DocumentHelper.parseText(text.toString());
			GetStore getStore = new GetStore();
			getStore.xmlToModel(doc);
			for(StoreModel storeModel : getStore.storeModels){
				storeModel.show();
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
			
		
		ou.close();
		in.close();
	}
	
	public List<StoreModel> xmlToModel(Document doc){
		Element roElement = doc.getRootElement();
		Element store = roElement.element("Store");
		Element customCategories = store.element("CustomCategories");
		List<Element> customCategory = customCategories.elements("CustomCategory");
		
		parsingXml(customCategory,0L);
		return storeModels;
	}
	public  void  parsingXml(List<Element> customCategory,Long parent_category_id){
		
		for(int i=0; i<customCategory.size();i++){
			StoreModel storeModel = new StoreModel();
			storeModel.setName(customCategory.get(i).element("Name").getTextTrim().toString());
			storeModel.setCategory_id(Long.parseLong(customCategory.get(i).element("CategoryID").getTextTrim().toString()));
			storeModel.setCategory_order(Long.parseLong(customCategory.get(i).element("Order").getTextTrim().toString()));
			storeModel.setParent_category_id(parent_category_id);
			List<Element> ChildCategory =  customCategory.get(i).elements("ChildCategory");
			if(ChildCategory.size()>0){
				
				
				storeModel.setChild_category("true");
				storeModels.add(storeModel);
				parsingXml(ChildCategory,Long.parseLong(customCategory.get(i).element("CategoryID").getTextTrim().toString()));
				
			}else{
				storeModel.setChild_category("flase");
				storeModels.add(storeModel);
			}
			
			
		}
	}
}
