//package com.it.ocs.seller.test;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiCredential;
//import com.ebay.sdk.ApiLogging;
//import com.ebay.sdk.call.GetCategoriesCall;
//import com.ebay.sdk.call.GeteBayOfficialTimeCall;
//import com.ebay.sdk.helper.ConsoleUtil;
//import com.ebay.soap.eBLBaseComponents.CategoryType;
//import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
//
//public class TestMain {
//	 public static void main(String[] args) {
//
//		    try {
//
//		      System.out.println();
//		      System.out.print("+++++++++++++++++++++++++++++++++++++++++++++\n");
//		      System.out.print("+ Welcome to eBay SDK Configuration Tester! +\n");
//		      System.out.print("+++++++++++++++++++++++++++++++++++++++++++++\n");
//		      System.out.println();
//
//		      // Create credential object.
//		      System.out.println("===== Enter Credentials for Making eBay API Call ====");
//
//		      ApiContext apiContext = enterApiContext();
//
//		      // Make one call because Axis takes long time for the first execution.
//		      initSDK(apiContext);
//
//		      // Enter loop to make API calls
//		      int rounds = 5;
//
//		      System.out.println("===== Calling GetCategoriesCall WITH compression ====");
//		      double t1 = testGetCategories(apiContext, true, rounds)/rounds;
//
//		      System.out.println("===== Calling GetCategoriesCall WITHOUT compression ====");
//		      double t2 = testGetCategories(apiContext, false, rounds)/rounds;
//
//		      System.out.println("GetCategoriesCall with compression: " + (new Double(t1)).toString());
//		      System.out.println("GetCategoriesCall no compression: " + (new Double(t2)).toString());
//
//		      // Print out conclusion.
//		      System.out.println("==> Recommendataions <==");
//		      boolean compressionOn = (t1 < t2);
//
//		      String msg = java.text.MessageFormat.format(
//		          "==> It''s recommended to turn {0} compression in SDK on this machine to get the best performance.",
//		          new Object[]{
//		            compressionOn ? "ON" : "OFF"
//		            }
//		          );
//		      System.out.println(msg);
//
//		      msg = java.text.MessageFormat.format(
//		          "==> i.g., for each ApiCall derived object call: ApiCall.setEnableCompression({0})",
//		          new Object[]{
//		            compressionOn ? "true" : "false"
//		            }
//		          );
//		      System.out.println(msg);
//		    }
//		    catch(Exception e) {
//		      e.printStackTrace();
//		    }
//		  }
//
//		  static ApiContext enterApiContext() throws Exception
//		  {
//		    String input;
//
//		    ApiContext apiContext = new ApiContext();
//		    ApiCredential cred = apiContext.getApiCredential();
//		    ApiLogging apiLogging = new ApiLogging();
//		    apiLogging.setLogSOAPMessages(false);
//		    apiLogging.setLogHTTPHeaders(true);
//		    apiContext.setApiLogging(apiLogging);
//
//		    //input = ConsoleUtil.readString("Enter your eBay Authentication Token: ");
//		    cred.seteBayToken("AgAAAA**AQAAAA**aAAAAA**GWvkWA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GlDZeHow2dj6x9nY+seQ**0ScEAA**AAMAAA**XlRylrkXXCoY5UTwzSe/uNQwQLxTK6EYW/p15MKB454clz3lrKW9qzXAIq2oY/EUkzA2YiF2QbwrjfETbqAmBYWxqPr3ht5moulrZQr0y3sMoWTLA56ASPjsXm1TRojVEOezRvrAHKarQq+Gl2mGe9SS7VF7OBSG1TEPdc6BY59w3cEpZqt53j3FpHsjn0N3ZQoDb0NcyEfFRDw3CEPPWL1546z9x455lnjtOCnocwJg83gt3oDcCN+Uuq1KxoEAKboIjAIBr3VcV9mTbIu+gW65Kn2qdbUUtH/Qx22oU0xAjRSbQfpM0vfsfNmckrmgexjS7irpjQ9YFhMlEWM1i6Dk6iA0gubEijkKomxUl77vZCz7b3MV3urZJEbSNyPjGrrCRtH8lo6Th3o0lVdlVJhYmtx0Nw73pO+3UAI22V7lbf+SXqH6UI8dhZJ+zMLVGzgoB79aiEkTLeB+1VK83h21Iv1XdwAw/l5LI+4UwxR2rF4218ZTjRdQEOWgqokdxKsCiEVl7UdEF22+8lnRrjgYBESbkf136mqYvG4O5cFEVRKKVghD2VfTV4gwnWCkmIkrVxIbb0wvEfK2qA5Tas5BNFOazQPVmLchC6CjskzKXGS1ohy6TZ61QlfezEE4r5DHgpjyQOAkIpwKnmo8bD2zZY654lnOakEb4zRzIgVUks20FirrekeR9z/YCNyT0qsSk+6u2BE4oJV0hcVA0zeVzlaGTk0qiTlQjmCOL0/XXRcpFFWRQIhTVh+S2Pd8");
//		     
//
//		   // input = ConsoleUtil.readString("Enter eBay SOAP server URL (e.g., https://api.ebay.com/wsapi): ");
//		      apiContext.setApiServerUrl("https://api.sandbox.ebay.com/wsapi");
//		    return apiContext;
//		  }
//
//		  static double testGetCategories(ApiContext apiContext, boolean enableCompression, int rounds) throws Exception
//		  {
//		    long startTime = java.util.Calendar.getInstance().getTimeInMillis();
//
//		    for (int i = 0; i < rounds; i++)
//		    {
//		      try {
//
//		        System.out.println("Calling GetCategoriesCall - " + (new Integer(i)).toString());
//
//		        GetCategoriesCall api = new GetCategoriesCall(apiContext);
//		        api.setEnableCompression(enableCompression);
//
//		        api.addDetailLevel(DetailLevelCodeType.RETURN_ALL);
//		        api.setLevelLimit(4);
//		        api.setViewAllNodes(true);
//
//		        CategoryType[] cats = api.getCategories();
//		      }
//		      catch (Exception ex) {
//		        throw ex;
//		      }
//		    }
//
//		    long endTime = java.util.Calendar.getInstance().getTimeInMillis();
//
//		    return (endTime - startTime) / (double) rounds;
//		  }
//
//		  static void initSDK(ApiContext ctx) throws Exception
//		  {
//		    GeteBayOfficialTimeCall t = new GeteBayOfficialTimeCall(ctx);
//		    t.geteBayOfficialTime();
//		  }
//}
