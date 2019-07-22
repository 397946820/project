//package com.it.ocs.publication.model;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.ebay.soap.eBLBaseComponents.BrandMPNType;
//import com.ebay.soap.eBLBaseComponents.CountryCodeType;
//import com.ebay.soap.eBLBaseComponents.CurrencyCodeType;
//import com.ebay.soap.eBLBaseComponents.ListingEnhancementsCodeType;
//import com.ebay.soap.eBLBaseComponents.NameValueListArrayType;
//import com.ebay.soap.eBLBaseComponents.NameValueListType;
//import com.ebay.soap.eBLBaseComponents.PictureDetailsType;
//import com.ebay.soap.eBLBaseComponents.PicturesType;
//import com.ebay.soap.eBLBaseComponents.ProductListingDetailsType;
//import com.ebay.soap.eBLBaseComponents.VariationProductListingDetailsType;
//import com.ebay.soap.eBLBaseComponents.VariationSpecificPictureSetType;
//import com.ebay.soap.eBLBaseComponents.VariationType;
//import com.ebay.soap.eBLBaseComponents.VariationsType;
//import com.google.common.collect.Lists;
//import com.it.ocs.synchronou.mapping.CurrencyCodeTypeEnum;
//import com.it.ocs.synchronou.mapping.EbayMappingToSDK;
//import com.it.ocs.synchronou.util.ArrayUtil;
//import com.it.ocs.synchronou.util.ValidationUtil;
//
//
//
//public class PublicationAddItemModel {
//	
//	private PublicationModel pub;
//	private List<ShipOptionModel> shipOptionModels;
//	public Long getAdvert_id(){
//		return pub.getAdvert_id();
//	}
//	public VariationsType getVariations() {
//		Long siteID = pub.getSiteId();
//		String variations = pub.getVariations();
//		CurrencyCodeType currency = CurrencyCodeTypeEnum.searchCurrencyBySiteID(siteID);//币种
//		if(ValidationUtil.isNullOrEmpty(variations)){
//			return null;
//		}else{
//			VariationsType variationsType = new VariationsType();
//			JSONObject jsonObject = JSONObject.parseObject(variations);
//			JSONArray variationArray = jsonObject.getJSONArray("variations");
//			JSONArray pictures = jsonObject.getJSONArray("pictures");
//			JSONArray variationSpecificsSet = jsonObject.getJSONArray("variationSpecificsSet");
//			 int variationSizt = variationArray.size();
//			 VariationType[] variationTypes = new VariationType[variationSizt];
//			 for (int i = 0; i < variationSizt; i++) {
//				VariationType  variationType = new VariationType();
//				JSONObject jsonObject2 = JSONObject.parseObject(variationArray.get(i).toString());
//				variationType.setSKU(jsonObject2.getString("SKU"));
//				variationType.setStartPrice(EbayMappingToSDK.getAmountTypeByDou(jsonObject2.getDouble("StartPrice"), currency));
//				variationType.setQuantity(jsonObject2.getInteger("Quantity"));
//				VariationProductListingDetailsType listingDeails = new VariationProductListingDetailsType();
//				listingDeails.setUPC(jsonObject2.getString("UPC"));
//				listingDeails.setISBN(jsonObject2.getString("ISBN"));
//				listingDeails.setEAN(jsonObject2.getString("EAN"));
//				variationType.setVariationProductListingDetails(listingDeails);
//				JSONArray jsonArray = JSONArray.parseArray(jsonObject2.getJSONArray("NameValueList").toJSONString());
//				NameValueListArrayType nameValueListArrayType = new NameValueListArrayType();
//				int nameValueCount = jsonArray.size();
//				NameValueListType[] nameValueListTypes = new NameValueListType[nameValueCount];
//				for (int j = 0; j < nameValueCount; j++) {
//					NameValueListType nameValueListType = new NameValueListType();
//					JSONObject jsonObject3 = JSONObject.parseObject(jsonArray.get(j).toString());
//					nameValueListType.setName(jsonObject3.getString("Name"));
//					String[] values = new String[1];
//					values[0] = jsonObject3.getString("Value");
//					nameValueListType.setValue(values);
//					nameValueListTypes[j]=nameValueListType;
//				}
//				nameValueListArrayType.setNameValueList(nameValueListTypes);
//				variationType.setVariationSpecifics(nameValueListArrayType);
//				variationTypes[i] = variationType;
//			}
//			variationsType.setVariation(variationTypes);
//			HashMap<String, PicturesType> pictureMap= new HashMap<>();
//			for (Object object : pictures) {
//				JSONObject jsonObject2 = JSONObject.parseObject(object.toString());
//				String name = jsonObject2.getString("VariationSpecificName");
//				String pictureUrl = jsonObject2.getString("PictureURL");
//				String specificValue = jsonObject2.getString("VariationSpecificValue");
//				String[] urls = new String[1];
//				urls[0]=pictureUrl;
//				PicturesType picturesType = new PicturesType();
//				picturesType.setVariationSpecificName(name);
//				VariationSpecificPictureSetType specificPicture = new VariationSpecificPictureSetType();
//				specificPicture.setPictureURL(urls);
//				specificPicture.setVariationSpecificValue(specificValue);
//				if(pictureMap.size()<1){
//					picturesType.setVariationSpecificPictureSet(ArrayUtil.addObjectToArray(picturesType.getVariationSpecificPictureSet(),specificPicture,VariationSpecificPictureSetType.class));
//					pictureMap.put(name,picturesType );
//				}else{
//					if(pictureMap.get(name)!=null){
//						pictureMap.get(name).setVariationSpecificPictureSet(ArrayUtil.addObjectToArray(pictureMap.get(name).getVariationSpecificPictureSet(),specificPicture,VariationSpecificPictureSetType.class));
//						
//					}else{
//						picturesType.setVariationSpecificPictureSet(ArrayUtil.addObjectToArray(picturesType.getVariationSpecificPictureSet(),specificPicture,VariationSpecificPictureSetType.class));
//						pictureMap.put(name,picturesType );
//					}
//				}
//				
//			}
//			int pictureCount = pictureMap.size();
//			PicturesType[] picturesTypes = new PicturesType[pictureCount];
//			int piCount=0;
//			 for (Map.Entry<String, PicturesType> entry : pictureMap.entrySet()) {
//				 picturesTypes[piCount] = entry.getValue();
//				 piCount++;
//		     }
//			variationsType.setPictures(picturesTypes);
//			int count = variationSpecificsSet.size();
//			NameValueListArrayType nameValueListArrayTypes = new NameValueListArrayType();
//			NameValueListType[] nameValueListTypes = new NameValueListType[count];
//			for (int j = 0; j < count; j++) {
//				NameValueListType nameValueListType = new NameValueListType();
//				JSONObject jsonObject2 = JSONObject.parseObject(variationSpecificsSet.get(j).toString());
//				nameValueListType.setName(jsonObject2.getString("Name"));
//				JSONArray jsonArray = jsonObject2.getJSONArray("Values");
//				int size = jsonArray.size();
//				String[] values= new String[size];
//				for (int k = 0; k < size; k++) {
//					values[k]= jsonArray.get(k).toString();
//				}
//				nameValueListType.setValue(values);
//				nameValueListTypes[j] = nameValueListType;
//			}
//			nameValueListArrayTypes.setNameValueList(nameValueListTypes);
//			variationsType.setVariationSpecificsSet(nameValueListArrayTypes);
//			return variationsType;
//		}
//		
//		
//	}
//	public PublicationModel getPub() {
//		return pub;
//	}
//
//	public void setPub(PublicationModel pub) {
//		this.pub = pub;
//	}
//
//	public PublicationAddItemModel(PublicationModel pub){
//		this.pub = pub;
//	}
//	
//	
//	public Boolean getAutoPay(){
//		String bool= pub.getAutoPay();
//		if(!ValidationUtil.isNullOrEmpty(bool)){
//			return Boolean.parseBoolean(bool);
//		}
//		return false;
//		
//	}
//	public String getId(){
//		
//		return pub.getId().toString();
//	}
//	
//	public String getTemplateName() {
//		return pub.getTemplateName();
//	}
//	public Long getSellerDescription() {
//		String sellerDescription = pub.getSellerDescription();
//		if (ValidationUtil.isNullOrEmpty(sellerDescription)) {
//			
//			return null;
//		}else{
//		  return Long.parseLong(sellerDescription);	
//		}
//	}
//	public String getEbayAccount() {
//		return pub.getEbayAccount();
//	}
//	
//	public Long getSiteId() {
//		return pub.getSiteId();
//	}
//	
//	public String getSiteName() {
//		return pub.getSiteName();
//	}
//	
//	public String getPublicationType() {
//		return pub.getPublicationType();
//	}
//	
//	public String getSku() {
//		return pub.getSku();
//	}
//	
//	public String getProductTitle() {
//		return pub.getProductTitle();
//	}
//	
//	public String getBundledProduct() {
//		return pub.getBundledProduct();
//	}
//	
//	public String getProductFirstCategoryId() {
//
//			return pub.getProductFirstCategoryId();
//	}
//	
//	public String getProductSecondCategoryId() {
//			return pub.getProductSecondCategoryId();
//	}
//	
//	public Long getStoreFirstCategoryId() {
//		if(!ValidationUtil.isNullOrEmpty(pub.getStoreFirstCategoryId())){
//			return Long.parseLong(pub.getStoreFirstCategoryId());
//		}
//		return null;
//	}
//	
//	public Long getStoreSecondCategoryId() {
//		if(!ValidationUtil.isNullOrEmpty(pub.getStoreSecondCategoryId())){
//			return Long.parseLong(pub.getStoreSecondCategoryId());
//		}
//		return null;
//	}
//	
//	private String[] jsonArrayToArray(JSONArray jsonArray){
//		String[] arry = new String[]{};
//		arry = jsonArray.toArray(arry);
//		return arry;
//	}
//	public NameValueListArrayType getNameValueListArrayType() {
//		
//		String properties = pub.getProductProperties();
//		if(!ValidationUtil.isNull(properties)){
//			List<NameValueListType> itemSpecifics = new ArrayList<>();
//			JSONArray ja = JSONArray.parseArray(properties);
//			for (int i = 0; i < ja.size(); i++) {
//				NameValueListType nameValueListType = new NameValueListType();
//				JSONObject jb = ja.getJSONObject(i);
//				Set<String> set = jb.keySet();
//				for(String key : set){
//					nameValueListType.setName(key);
//					String[] values = jsonArrayToArray(jb.getJSONArray(key));
//					nameValueListType.setValue(values);
//					itemSpecifics.add(nameValueListType);
//				}
//			}
//			NameValueListType mpn = new NameValueListType();
//			NameValueListType brand = new NameValueListType();
//			if(pub.getSiteId()== 77L){
//				mpn.setName("Herstellernummer");
//				brand.setName("Marke");
//				NameValueListType ust = new NameValueListType();
//				ust.setName("Ust-IdNr");
//				ust.setValue(new String[]{pub.getProduct_ust()});
//				itemSpecifics.add(ust);
//			}else{
//				mpn.setName("MPN");
//				brand.setName("Brand");
//			}
//			mpn.setValue(new String[]{pub.getProduct_mpn()});
//			brand.setValue(new String[]{pub.getProduct_brand()});
//			itemSpecifics.add(mpn);
//			itemSpecifics.add(brand);
//			NameValueListArrayType nameValueListArrayType = new NameValueListArrayType();
//			nameValueListArrayType.setNameValueList(itemSpecifics.toArray(new NameValueListType[itemSpecifics.size()]));
//			return nameValueListArrayType;
//		}else{
//			return null;
//		}
//	}
//	
//	public String getTopPromotionType() {
//		return pub.getTopPromotionType();
//	}
//	
//	public String getInPromotionType(){
//		return pub.getInPromotionType();
//	}
//	public String getFooterPromotionType() {
//		return pub.getFooterPromotionType();
//	}
//	
//	public String getCounterType() {
//		return pub.getCounterType();
//	}
//	
//	public String getOpenPageProtect() {
//		return pub.getOpenPageProtect();
//	}
//	
//	public String getTemplateTitle() {
//		return pub.getTemplateName();
//	}
//	
//	public PictureDetailsType getEbayImages() {
//		PictureDetailsType pictureDetailsType = new PictureDetailsType();
//		String ebayImages = pub.getEbayImages();
//		if(null != ebayImages){
//			ebayImages = ebayImages.replace("http:", "https:");
//		}
//		String[] imgs=null;
//		if(!ValidationUtil.isNullOrEmpty(ebayImages)&&ebayImages.contains(",")){
//			imgs = ebayImages.split(",");
//			
//		}else if(ValidationUtil.isNullOrEmpty(ebayImages)||ebayImages.length()<3){
//			return null;
//			
//		}else{
//			imgs = new String[1];
//			imgs[0]=ebayImages;
//		}
//		pictureDetailsType.setPictureURL(imgs);
//		return pictureDetailsType;
//	}
//	
//	public String[] getTemplateImages() {
//		String templateImages = pub.getTemplateImages();
//		if(null != templateImages){
//			templateImages = templateImages.replace("http:", "https:");
//		}
//		if(ValidationUtil.isNullOrEmpty(templateImages)){
//			return null;
//		}else if(templateImages.contains(",")){
//			
//			return templateImages.split(",");
//		}else{
//			String[] imgs = new String[1];
//			imgs[0]=templateImages;
//			return imgs;
//		}
//		
//	}
//	
//	public String getAppComment() {
//		return pub.getAppComment();
//	}
//	
//	public String getComments() {
//		return pub.getComments();
//	}
//	
//	public String getDiscount() {
//		return pub.getDiscount();
//	}
//	
//	public String getIndividual() {
//		return pub.getIndividual();
//	}
//
//	public String getPublicationDays() {
//		return pub.getPublicationDays();
//	}
//	
//	public String getPrice() {
//		return pub.getPrice();
//	}
//	
//	public String getReserverPrice() {
//		return pub.getReserverPrice();
//	}
//	
//	public String getBuyoutPrice() {
//		return pub.getBuyoutPrice();
//	}
//	
//	public String getAuctionItem() {
//		return pub.getAuctionItem();
//	}
//	
//	public String getSecondTrading() {
//		return pub.getSecondTrading();
//	}
//	
//	/*public Map<String,String> getSecondTradinfo() {
//		String info = pub.getSecondTradinfo();
//		if(!ValidationUtil.isNull(info)){
//			JSONObject json = JSONObject.parseObject(info);
//			Set<String> set = json.keySet();
//			Map<String,String> map = new HashMap<String,String>();
//			for(String key:set){
//				map.put(key, json.getString(key));
//			}
//			return map;
//		}
//		return null;
//	}*/
//	
//	public String getPaypaiAccount() {
//		return pub.getPaypaiAccount();
//	}
//	
//	public String getSupportPaypaiInfo() {
//		return pub.getSupportPaypaiInfo();
//	}
//	
//	public String getPayDescription() {
//		return pub.getPayDescription();
//	}
//	
//	public Boolean getAllowAllbuyer() {
//		
//		return Boolean.parseBoolean(pub.getAllowAllbuyer());
//	}
//	
//	public Boolean getOutShipCountry() {
//		return Boolean.parseBoolean(pub.getOutShipCountry());
//	}
//	
//	public String getPolicyType() {
//		return pub.getPolicyType();
//	}
//	
//	public String getReturnDays() {
//		return pub.getReturnDays();
//	}
//	
//	public Boolean getAllowDelay() {
//		String allowDelay =  pub.getAllowDelay();
//		if(ValidationUtil.isNullOrEmpty(allowDelay)){
//			return null;
//		}else{
//			return Boolean.parseBoolean(allowDelay);
//		}
//	}
//	
//	public String getReturnType() {
//		return pub.getReturnType();
//	}
//	
//	public String getFaretakeinhander() {
//		return pub.getFaretakeinhander();
//	}
//	
//	public String getDepreciationRate() {
//		return pub.getDepreciationRate();
//	}
//	
//	public String getReturnDescription() {
//		return pub.getReturnDescription();
//	}
//	
//	public String getTransClauseExisPub() {
//		return pub.getTransClauseExisPub();
//	}
//	
//	public String getCalCulateinfo() {
//		return pub.getCalCulateinfo();
//	}
//	
//	public String getDomesticInfo() {
//		return pub.getDomesticInfo();
//	}
//	
//	public String getInternationInfo() {
//		return pub.getInternationInfo();
//	}
//	
//	public String[] getShipLocationIn() {
//		String shipLocationIn = pub.getShipLocationIn();
//		String[] array = null;
//		if(ValidationUtil.isNull(shipLocationIn)){
//			 array = new String[1];
//			return array;
//		}else if(shipLocationIn.contains(",")){
//			array = shipLocationIn.split(",");
//			return array;
//		}else {
//			 array = new String[1];
//			array[0] = shipLocationIn;
//			return array;
//		}
//		
//	}
//	
//	public String[] getShipLocationOver() {
//		String shipLocationOver = pub.getShipLocationOver();
//		String[] array=null;
//		List<String> result = Lists.newArrayList();
//		if(!ValidationUtil.isNullOrEmpty(shipLocationOver)&&shipLocationOver.contains(",")){
//			
//			 array = shipLocationOver.split(",");
//			 for (String string : array) {
//				if(!ValidationUtil.isNullOrEmpty(string)){
//					result.add(string);
//				}
//			}
//			 array = new String[result.size()];
//			 result.toArray(array);
//			return array ;
//		}else {
//			if(ValidationUtil.isNullOrEmpty(shipLocationOver)){
//				return array;
//			}else{
//			 array = new String[1];
//			array[0] = shipLocationOver;
//			return array;
//			}
//		}
//	}
//	
//	public ListingEnhancementsCodeType[] getFeatureType() {
//		String featuretype = pub.getFeatureType();
//		if(!ValidationUtil.isNullOrEmpty(featuretype)&&!featuretype.contains(",")){
//			ListingEnhancementsCodeType[] listingEnhancements = new ListingEnhancementsCodeType[0];
//			listingEnhancements[0] = ListingEnhancementsCodeType.fromValue(featuretype);
//			return listingEnhancements;
//		}else if(ValidationUtil.isNull(featuretype)){
//			return null;
//		}else if(featuretype.contains(",")){
//			String[] array = featuretype.split(",");
//			int count = array.length;
//			ListingEnhancementsCodeType[] listingEnhancements = new ListingEnhancementsCodeType[count];
//			for (int i = 0; i < count; i++) {
//				listingEnhancements[i] = ListingEnhancementsCodeType.fromValue(array[i]);
//			}
//			return listingEnhancements;
//		}else{
//
//			return null;
//		}
//	}
//	
//	public String getTag() {
//		return pub.getTag();
//	}
//	
//	public String getOtherDescription() {
//		return pub.getOtherDescription();
//	}
//	
//	public String getOnlinePubcount() {
//		return pub.getOnlinePubcount();
//	}
//	
//	public String getSalesTax() {
//		return pub.getSalesTax();
//	}
//	
//	public String getProductAddress() {
//		return pub.getProductAddress();
//	}
//	
//	public CountryCodeType getRegion() {
//		
//		return CountryCodeType.fromValue(pub.getRegion());
//	}
//	
//	public String getPostCode() {
//		return pub.getPostCode();
//	}
//	
//	public String getItemId() {
//		return pub.getItemId();
//	}
//	
//	public String getProductSubtitle() {
//		return pub.getProductSubtitle();
//	}
//	
//	public String getEbayProductURL() {
//		return pub.getEbayProductURL();
//	}
//	
//	public Boolean getNoPaypai() {
//		return Boolean.parseBoolean(pub.getNoPaypai());
//	}
//	
//	public String getBuyerReq1() {
//		return pub.getBuyerReq1();
//	}
//	
//	public String getBuyerReq2() {
//		//TODO
//		return pub.getBuyerReq2();
//	}
//	
//	public Integer getBuyerReq3() {
//		//TODO
//		String buyerReq3 = pub.getBuyerReq3();
//		if (!ValidationUtil.isNullOrEmpty(buyerReq3)) {
//			return Integer.parseInt(buyerReq3);
//		}else{
//			return null;
//		}
//		
//	}
//	
//	public Integer getBuyerReq4() {
//		//TODO
//		String buyerReq4 = pub.getBuyerReq4();
//		if (!ValidationUtil.isNullOrEmpty(buyerReq4)) {
//			return Integer.parseInt(buyerReq4);
//		}else{
//			return null;
//		}
//		
//	}
//	
//	public Integer getBuyerReq41() {
//		//TODO
//		String buyerReq41 = pub.getBuyerReq41();
//		if (!ValidationUtil.isNullOrEmpty(buyerReq41)) {
//			return Integer.parseInt(buyerReq41);
//		}else{
//			return null;
//		}
//	}
//	
//	
//	public ProductListingDetailsType getProductListingDetail(){
//		ProductListingDetailsType productListingDetailsType = new ProductListingDetailsType();
//
//		String upc = pub.getProductUPC();
//		String eam = pub.getProductEAN();
//		String isbn = pub.getProductISBN();
//		
//		if(!ValidationUtil.isNullOrEmpty(upc)){
//			productListingDetailsType.setUPC(upc);
//		}
//		if(!ValidationUtil.isNullOrEmpty(eam)){
//			productListingDetailsType.setEAN(eam);
//		}
//		if(!ValidationUtil.isNullOrEmpty(isbn)){
//			productListingDetailsType.setISBN(isbn);
//		}
//	
//		return productListingDetailsType;
//	}
//	public String getAcceptBuyerCounter() {
//		return pub.getAcceptBuyerCounter();
//	}
//	
//	public Double getAcceptBuyerCounterMin() {
//		String min = pub.getAcceptBuyerCounterMin();
//		if (ValidationUtil.isNullOrEmpty(min)) {
//			return null;
//		}else{
//			return Double.parseDouble(min);
//		}
//	}
//	
//	public Double getAcceptBuyerCounterMax() {
//		String max = pub.getAcceptBuyerCounterMax();
//		if(ValidationUtil.isNullOrEmpty(max)){
//			return null;
//		}else{
//			return Double.parseDouble(max);
//		}
//	}
//	
//	public String getProductCount() {
//		return pub.getProductCount();
//	}
//	
//	public String getSellerBaseCount() {
//		return pub.getSellerBaseCount();
//	}
//	
//	
//	public String getProductStartPrice() {
//		return pub.getProductStartPrice();
//	}
//	
//	public Integer getProductStatus() {
//		String productStatus = pub.getProductStatus();
//		if (!ValidationUtil.isNullOrEmpty(productStatus)) {
//			return Integer.parseInt(productStatus);
//		}else{
//			return null;
//		}
//	}
//	
//	
//	public Integer getDomesticOptDay() {
//		String  domesticOptDay= pub.getDomesticOptDay();
//		if (ValidationUtil.isNullOrEmpty(domesticOptDay)) {
//			return null;
//		}else{
//			return Integer.parseInt(domesticOptDay); 
//		}
//	}
//
//	public List<ShipOptionModel> getShipOptionModels() {
//		return shipOptionModels;
//	}
//
//	public void setShipOptionModels(List<ShipOptionModel> shipOptionModels) {
//		this.shipOptionModels = shipOptionModels;
//	}
//	
//}
