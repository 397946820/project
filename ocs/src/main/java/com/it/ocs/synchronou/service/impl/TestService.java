//package com.it.ocs.synchronou.service.impl;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//
//import org.apache.bcel.classfile.Field;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiException;
//import com.ebay.sdk.SdkException;
//import com.ebay.sdk.call.GetItemCall;
//import com.ebay.sdk.call.GeteBayDetailsCall;
//import com.ebay.soap.eBLBaseComponents.DetailNameCodeType;
//import com.ebay.soap.eBLBaseComponents.ReturnPolicyDetailsType;
//import com.ebay.soap.eBLBaseComponents.ReturnsWithinDetailsType;
//import com.ebay.soap.eBLBaseComponents.SiteCodeType;
//import com.it.ocs.itemDescribe.css.TemplateACss;
//
//public class TestService {
//
//	/*public static void main(String[] args) {
//		
//		System.out.println(TemplateACss.getCss());
//		File file = new File("C:\\Users\\yangguanbao\\Desktop\\eBay\\base2.css");
//		BufferedReader reader = null;
//		StringBuffer result = new StringBuffer();
//	
//		
//		
//		String tempString = null;
//		int line = 1;
//		try {
//			reader = new BufferedReader(new FileReader(file));
//			while ((tempString= reader.readLine())!=null) {
//				System.out.println("+\""+tempString+"\""+"+\"\\n\"" );
//			}
//			reader.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		BaseEbaySDKService baseService = new BaseEbaySDKService();
//		ApiContext apiContext = baseService.getApiContext("AgAAAA**AQAAAA**aAAAAA**u1RjWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEmYSiC5SDpQ2dj6x9nY+seQ**Xq8DAA**AAMAAA**nzc7lCXNjRxjfZiTVjNJ6hYFYlZxK+5R6x7rr5WSGrTGJTjr3ykfT4YvBuhdDheZSpftIpfgfQMA//5Y1AhRHhckFpPnVU+7t2R7pViACV9wyiDGh2UjZ/XRuh1v5dr6zqUQN8AIo47gJE+AXeeehIC7G77E0DY2/0p+IxIWCtqP9MNjAfMIPWYsOhX4OnqMQgBIQdcMZZ5/3PoQRj/0ELiTn3RqltZq/T9BnyNv14qXBfXcUiijizx2a0OZBv1dfIrog7/ohP/sqBgvgyl31Ebi78Qp/TgIZtjP9r44tbw7kmsk/X1jtjeeXYfKZ+JelJlYZLZX4DTCCobYAZ7E5sbPI7frJFyRBsm7gxV5sZeBPdwDC5hB9pOTQpM27CdOsz2e089ughAEQw8H9ZV+DXUR4Aw3BNr40QwAkh34dBDF7UFo6JU8bWYTeweFT2vAdxUhvEfFjb5FRMcU5A9LK6SUkcoXP5f2DOD9nloZQOw6X6rczVsDeXd2dCPGTK+vpf82gjY+NWbVanPjICAEwFQZqPQe2OesAcbbK5ztYmeftzAvIEoWR71fHMrKDNrVeEFzzP9a9Pdl/C1UZ5Ys/xlLU07BnqL/idcx0+2WdcW3ZKpwMD7M6NobbqAuHBE7pvezSZ1DAVl+h5mrEURUQdOUMCRV5nKNkOYooeORqtKVclyNAOMPnvJwgn1sr/gdLuzoYtgsREiKBv1wJmni2YwcU80d+uLmCk41nqG3n8unVN0CURR+8g5BGSNFVvl1", "https://api.ebay.com/wsapi");
//		GetItemCall itemCall = new GetItemCall(apiContext);
//		itemCall.setSite(SiteCodeType.UK);
//		itemCall.setItemID("162406416520");
//		try {
//			itemCall.getItem();
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SdkException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//		GeteBayDetailsCall eBayDetailsCall = new GeteBayDetailsCall(apiContext);
//		
//		try {
//			DetailNameCodeType[] nameCodeTypes = new DetailNameCodeType[1];
//			nameCodeTypes[0] = DetailNameCodeType.RETURN_POLICY_DETAILS;
//			eBayDetailsCall.setDetailName(nameCodeTypes);
//			eBayDetailsCall.setSite(SiteCodeType.US);
//			eBayDetailsCall.geteBayDetails();
//		    ReturnPolicyDetailsType returnPolicyDetailsType = eBayDetailsCall.getReturnedReturnPolicyDetails();
//		    ReturnsWithinDetailsType[] within= returnPolicyDetailsType.getReturnsWithin();
//		    System.out.println(within[0].getDescription());
//		    System.out.println(within[0].getReturnsWithinOption());
//		    
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}*/
//}
