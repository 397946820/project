package com.it.ocs.seller.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class EbayCategory {

	private static String CHILD="childCategoryTreeNodes";
	private static String CATEGORY="category";
	private static Long categoryTreeId;
	private static Long categoryTreeVersion;
	
	public static void main(String[] args) throws IOException {
			EbayCategory ec = new EbayCategory();
			URL realUrl = new URL("https://api.sandbox.ebay.com/commerce/taxonomy/v1_beta/category_tree/0");
			
			
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("Authorization", "Bearer v^1.1#i^1#f^0#p^3#I^3#r^0#t^H4sIAAAAAAAAAOVXaWwUVRzv9uKohQgGj6DZDvJBm5l9MzuzuzOyG7cHdg0tC7tgqSH1zcyb7cjszDpH240RmwYIRGNKiQIfCP2C2KCWoCZAwCZGwhVM8LZGwOOD8YgJKkiM0TfbbtmWCD1IbOJ+2Ml773/9/v/f/x2gq3zOw1satlyt9Mwq7usCXcUeD10B5pSXVc8rKb6vrAgUCHj6uh7sKu0u+X6ZBdNaRliNrIyhW8jbmdZ0S8hNhgnH1AUDWqol6DCNLMGWhES0cYXAUEDImIZtSIZGeGN1YUKSghIM8kqAQazMKwDP6nmbSSNMsH6OCQb9iAMMR9NsCK9bloNiumVD3Q4TDKCDJOBImksCRuBYgaOpEBNsIbxrkWmpho5FKEBEcuEKOV2zINabhwotC5k2NkJEYtHliZXRWF19U3KZr8BWZCQPCRvajjV2VGvIyLsWag66uRsrJy0kHElClkX4IsMexhoVovlgphB+LtUyUAIIMTzPSjyvcPxtSeVyw0xD++ZxuDOqTCo5UQHptmpnb5VRnA3xaSTZI6MmbCJW53U/qxyoqYqKzDBRXxNdtyZRv5rwJuJx02hXZSS7SGm/n+WCHEMTERtZOIXIbM1CPZVyoC5CY8TdsM2RZI/zV2vosuqmzvI2GXYNwrGj8RliCzKEhVbqK82oYrtxFcqx+UzSfItb2uFaOnab7lYXpXE6vLnhreuQJ8Z1Ktw2aogyBMgvQ+iXJE4OFFDD7fUp0yPiVigaj/vcWJAIs2QamhuQndGghEgJp9dJI1OVBT+nMP6Qgkg5wCskyysKKeI4SFpBCCAkihIf+v+xxLZNVXRsNMqU8Qs5qGEiIRkZFDc0VcoS40Vy+88ILzqtMNFm2xnB5+vo6KA6/JRhpnwMALSvuXFFQmpDaUiMyqq3FibVHGklhLUsVbCzGRxNJyYgdq6niIjflOPQtLM1ThaPE0jT8CdP4jERRsbP/gtUy4U6s0C6+hY2ADMq5XKckoy0z4C4p92p1lzE3okI+UQni/3LyKRMBGVD17IT10s5mMPD2hNTsnA1qOF2xDByHt1en7jXsQYmoaPq7ZjLhpmdJMyxypPQgZJkOLo9FXcjqpPQUBxNUTXNbdepOCxQn0yYOtSytipZoy6n1WXRTCYmz6wuy+/M5Aok17snSaKmmeRBSIFcwI9PC0bhAyJNTwu2jNpVCbWqMwy67mjatHDVofYbyol7/dX/GJco0lCkESABEmmSZQMBUmR5kWTEgMTR+GQGDJgW7sbUTCtlky86LUS1moo3hmR2pp2BDYZlI3l60PCFdGaBcneY/AbDsoyfZGkR/0mQJyEnK2QgGGQnCnncRMGN7oYrvW/syzpSlPvR3Z7joNtzBD/OQRCQdDV4qLxkTWnJHYSl2oiyoC6LRielQoWy1JSOH44mojagbAaqZnG5p/HbF9c9X/Cm71sP7hl91c8poSsKnvhg8fWVMnr+3ZV0EHC0+wJnOboFLLm+WkovKr1L2ka+xZb1Xul+pPb8108lF/a3zi0GlaNCHk9ZUWm3pyi264eXUQtY+tvrv5747Lzvj2O9e72blH0LZ3VeTBx7v+To79TQ6cW7Hkie0rd+t39gsGfo8DMXGnrO9G09tGDTlb96Zp869MaBj3ZTJ08uytTuSw6t2xja89jnVbvfPe9JJF7pv9B8+dxzj2rquVX3er9ZX3Uw/ia9ce/OFy5VnNp/dPBs1fHYkQWP/3xm+8G5m5efXnbptU8bdvnvr/YPHGvulz7OvF0pPTs7PLToq5ovlz75y08Nmw8wDYuX9P8da/tkTUQ/SwcvfriqV4yf6T08r/pKaPMHztbjT9S+092442rVjh+F0NFrPT3XBrZXnPB2vnTn6kHvQMXl98g/96hzd87LnF4vzz+5rf6LQTRcxn8A0ZzloG0RAAA=");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			Map<String, List<String>> map = connection.getHeaderFields();
			  for(String key : map.keySet()){
				  System.out.println(key+"---->" + map.get(key));
			 }
		 StringBuilder json = new StringBuilder();
		 BufferedReader in = new BufferedReader(new InputStreamReader(
		 connection.getInputStream()));
		 String inputLine = null;
		 while ( (inputLine = in.readLine()) != null) {
					  
			  json.append(inputLine);
			  
		 }
		in.close();
		JSONObject jsonObject = JSONObject.fromObject(json.toString());
		
		JSONObject rootJson  =  jsonObject.getJSONObject("rootCategoryNode");
		categoryTreeId = jsonObject.getLong("categoryTreeId");
		categoryTreeVersion = jsonObject.getLong("categoryTreeVersion");
		parsingJson(rootJson);
	}
	
	public static void parsingJson(JSONObject json){
		
		CategoryModel categoryModel = new CategoryModel();
		
		categoryModel.setMarketplace_id(categoryTreeId);
		categoryModel.setCategorytreeversion(categoryTreeVersion);
		if (json.has("leafCategoryTreeNode")) {
			
			categoryModel.setLeafCategoryTreeNode(json.getString("leafCategoryTreeNode"));
		
		}
		if(json.has("parentCategoryTreeNodeHref")){
			String r = json.getString("parentCategoryTreeNodeHref");
			categoryModel.setParentCategoryTreeNodeHref(r);
			categoryModel.setPcategoryId(r.substring(r.indexOf("=")+1, r.length()));
		}
		if(json.has("categoryTreeNodeLevel")){
			categoryModel.setCategoryTreeNodeLevel(json.getString("categoryTreeNodeLevel"));
		}
		JSONObject categoryKey = json.getJSONObject(CATEGORY);
		categoryModel.setCategoryId(categoryKey.getString("categoryId"));
		categoryModel.setCategoryName(categoryKey.getString("categoryName"));
		if(json.has(CHILD)){
			categoryModel.setChildCategoryTreeNodes("true");
			categoryModel.allToString();
			JSONArray childArray = json.getJSONArray(CHILD);
			for(int i=0; i<childArray.size();i++){
				parsingJson(childArray.getJSONObject(i));
			}
			
		}else{
			categoryModel.allToString();
		}
	}

	public Long getCategoryTreeId() {
		return categoryTreeId;
	}

	public void setCategoryTreeId(Long categoryTreeId) {
		this.categoryTreeId = categoryTreeId;
	}

	public Long getCategoryTreeVersion() {
		return categoryTreeVersion;
	}

	public void setCategoryTreeVersion(Long categoryTreeVersion) {
		this.categoryTreeVersion = categoryTreeVersion;
	}

	
	
}
