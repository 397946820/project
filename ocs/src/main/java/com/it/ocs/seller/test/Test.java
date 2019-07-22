//package com.it.ocs.seller.test;
//
//import java.io.IOException;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiCredential;
//import com.ebay.sdk.call.AddItemCall;
//import com.ebay.soap.eBLBaseComponents.AmountType;
//import com.ebay.soap.eBLBaseComponents.BuyerPaymentMethodCodeType;
//import com.ebay.soap.eBLBaseComponents.CategoryType;
//import com.ebay.soap.eBLBaseComponents.CountryCodeType;
//import com.ebay.soap.eBLBaseComponents.CurrencyCodeType;
//import com.ebay.soap.eBLBaseComponents.FeesType;
//import com.ebay.soap.eBLBaseComponents.ItemType;
//import com.ebay.soap.eBLBaseComponents.ListingDurationCodeType;
//import com.ebay.soap.eBLBaseComponents.ListingTypeCodeType;
//import com.ebay.soap.eBLBaseComponents.ReturnPolicyType;
//import com.ebay.soap.eBLBaseComponents.ShippingDetailsType;
//import com.ebay.soap.eBLBaseComponents.ShippingServiceCodeType;
//import com.ebay.soap.eBLBaseComponents.ShippingServiceOptionsType;
//import com.ebay.soap.eBLBaseComponents.ShippingTypeCodeType;
//
//public class Test {
//
//		/**
//		 * @param args
//		 */
//		public static void main(String[] args) {
//		    try {
//
//                System.out.print("\n");
//		        System.out.print("+++++++++++++++++++++++++++++++++++++++\n");
//		        System.out.print("+ Welcome to eBay SDK for Java Sample +\n");
//		        System.out.print("+  - HelloWorld                  +\n");
//		        System.out.print("+++++++++++++++++++++++++++++++++++++++\n");
//		        System.out.print("\n");
//
//		        ApiContext apiContext = getApiContext();
//		        
//		        
//		        
//		        ItemType item = new ItemType();
//		        item.setTitle("dlfld asdfadsf cvdsfew");
//		        item.setDescription("两女的 ");
//		        item.setListingType(ListingTypeCodeType.CHINESE);
//		        item.setCurrency(CurrencyCodeType.USD);
//		        AmountType amountType = new AmountType();
//		        amountType.setValue(423.4);
//		        item.setStartPrice(amountType);
//		        item.setListingDuration(ListingDurationCodeType.DAYS_3.value());
//		        item.setLocation("SJC");
//		        item.setCountry(CountryCodeType.US);
//		        CategoryType categoryType = new CategoryType();
//		        categoryType.setCategoryID("377");
//		        item.setPrimaryCategory(categoryType);
//		        item.setQuantity(new Integer(1));
//		        item.setPaymentMethods(new BuyerPaymentMethodCodeType[]{BuyerPaymentMethodCodeType.PAY_PAL});
//		        item.setPayPalEmailAddress("941201063@qq.com");
//		        item.setConditionID(1000);
//		        item.setDispatchTimeMax(Integer.valueOf(1));
//		        
//		        ShippingDetailsType sd = new ShippingDetailsType();
//		        
//		        sd.setApplyShippingDiscount(new Boolean(true));
//		        AmountType amountType2 = new AmountType();
//		        amountType2.setValue(2.8);
//		        sd.setPaymentInstructions("ebay Java SDK test instruction.");
//		        sd.setShippingType(ShippingTypeCodeType.FLAT);
//		        ShippingServiceOptionsType shOptionsType = new ShippingServiceOptionsType();
//		        shOptionsType.setShippingService(ShippingServiceCodeType.SHIPPING_METHOD_STANDARD.value());
//		        
//		        amountType2 = new AmountType();
//		        amountType2.setValue(2.0);
//		        shOptionsType.setShippingServiceAdditionalCost(amountType2);
//		        amountType2 = new AmountType();
//		        amountType2.setValue(1.0);
//		        shOptionsType.setShippingInsuranceCost(amountType2);
//		        sd.setShippingServiceOptions(new ShippingServiceOptionsType[]{shOptionsType});
//		        item.setShippingDetails(sd);
//		        ReturnPolicyType returnPolicyType = new ReturnPolicyType();
//		        returnPolicyType.setReturnsAcceptedOption("ReturnsAccepted");
//		        item.setReturnPolicy(returnPolicyType);
//		        
//		        AddItemCall api = new AddItemCall(apiContext);
//		        api.setItem(item);
//		        FeesType fess = api.addItem();
//		        System.out.println(item.getItemID());
//		        //Set the Picture Server URL for Sandbox 
//		       // apiContext.setEpsServerUrl("https://api.sandbox.ebay.com/ws/api.dll"); 
//
//		       //For production use this URL 
//		       //apiContext.setEpsServerUrl("https://api.ebay.com/ws/api.dll"); 
//
//		       //Set file path of the picture on the local disk  
//		       //To add more pictures, specify the file paths in the pictureFiles array 
//		      /* String [] pictureFiles = {"D:\\s-l1600.jpg"}; 
//		       PictureDetailsType pictureDetailsType = new PictureDetailsType();
//		       
//		       pictureDetailsType.setPictureURL(pictureFiles);*/
//
//		      
//		       // [Step 1] Initialize eBay ApiContext object
//		       /* System.out.println("===== [1] Account Information ====");
//		        ApiContext apiContext = getApiContext();
//		        
//		       GetAccountCall call = new GetAccountCall(apiContext);
//		       call.setAccountEntrySortType(AccountEntrySortTypeCodeType.ACCOUNT_ENTRY_FEE_TYPE_ASCENDING);
//		       */
//		        /*GetItemCall call3 = new GetItemCall(apiContext);
//		        call3.setEnableCompression(true);
//		       // call3.setUseCase(ProductUseCaseCodeType.ADD_ITEM);
//		        call3.addDetailLevel(DetailLevelCodeType.RETURN_ALL);
//		        call3.setItemID("110199011453");
//		        
//		        ItemType itemType = call3.getItem();*/
//		        
//		       // GetCategoriesCall call = new GetCategoriesCall(apiContext);
//		        /*GetStoreCall call2 = new GetStoreCall(apiContext);
//		        
//		        call2.setEnableCompression(true);
//		        call2.setCategoryStructureOnly(true);
//		        call2.addDetailLevel(DetailLevelCodeType.RETURN_ALL);
//		        call2.setLevelLimit(6);
//		        
//		        System.out.println("---"+call2.getEndUserIP());*/
//		        
//		      /*  call.setEnableCompression(true);
//		        call.addDetailLevel(DetailLevelCodeType.RETURN_ALL);
//		        call.setLevelLimit(6);
//		        call.setViewAllNodes(true);
//		        CategoryType[] categoryTypes= call.getCategories();
//		        System.out.println("大小："+categoryTypes.length);*/
//		        
//		       
//		       
//		
//		    }
//		    catch(Exception e) {
//		        System.out.println("Fail to get eBay official time.");
//		        e.printStackTrace();
//		    }
//
//		}
//		
//		  /**
//		   * Populate eBay SDK ApiContext object with data input from user
//		   * @return ApiContext object
//		   */
//		  private static ApiContext getApiContext() throws IOException {
//			  
//			  String input;
//		      ApiContext apiContext = new ApiContext();
//		      
//		      //set Api Token to access eBay Api Server
//		      ApiCredential cred = apiContext.getApiCredential();
//		     // input = ConsoleUtil.readString("Enter your eBay Authentication Token: ");
//		      
//		    		  
//		      cred.seteBayToken("AgAAAA**AQAAAA**aAAAAA**pDciWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GlDZeHow2dj6x9nY+seQ**0ScEAA**AAMAAA**XlRylrkXXCoY5UTwzSe/uNQwQLxTK6EYW/p15MKB454clz3lrKW9qzXAIq2oY/EUkzA2YiF2QbwrjfETbqAmBYWxqPr3ht5moulrZQr0y3sMoWTLA56ASPjsXm1TRojVEOezRvrAHKarQq+Gl2mGe9SS7VF7OBSG1TEPdc6BY59w3cEpZqt53j3FpHsjn0N3ZQoDb0NcyEfFRDw3CEPPWL1546z9x455lnjtOCnocwJg83gt3oDcCN+Uuq1KxoEAKboIjAIBr3VcV9mTbIu+gW65Kn2qdbUUtH/Qx22oU0xAjRSbQfpM0vfsfNmckrmgexjS7irpjQ9YFhMlEWM1i6Dk6iA0gubEijkKomxUl77vZCz7b3MV3urZJEbSNyPjGrrCRtH8lo6Th3o0lVdlVJhYmtx0Nw73pO+3UAI22V7lbf+SXqH6UI8dhZJ+zMLVGzgoB79aiEkTLeB+1VK83h21Iv1XdwAw/l5LI+4UwxR2rF4218ZTjRdQEOWgqokdxKsCiEVl7UdEF22+8lnRrjgYBESbkf136mqYvG4O5cFEVRKKVghD2VfTV4gwnWCkmIkrVxIbb0wvEfK2qA5Tas5BNFOazQPVmLchC6CjskzKXGS1ohy6TZ61QlfezEE4r5DHgpjyQOAkIpwKnmo8bD2zZY654lnOakEb4zRzIgVUks20FirrekeR9z/YCNyT0qsSk+6u2BE4oJV0hcVA0zeVzlaGTk0qiTlQjmCOL0/XXRcpFFWRQIhTVh+S2Pd8");
//		     
//		      //set Api Server Url
//		     //input = ConsoleUtil.readString("Enter eBay SOAP server URL (e.g., https://api.sandbox.ebay.com/wsapi): ");
//		      
//		      apiContext.setApiServerUrl("https://api.sandbox.ebay.com/wsapi");
//		      
//		      return apiContext;
//		  }
//}
