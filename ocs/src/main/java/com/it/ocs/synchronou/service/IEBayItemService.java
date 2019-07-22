//package com.it.ocs.synchronou.service;
//
//import java.util.List;
//import java.util.Map;
//
//import org.dom4j.Document;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.soap.eBLBaseComponents.ItemType;
//import com.it.ocs.common.OperationResult;
//import com.it.ocs.common.RequestParam;
//import com.it.ocs.itemDescribe.model.EBayPromoteModel;
//import com.it.ocs.publication.model.PublicationAddItemModel;
//import com.it.ocs.publication.model.PublicationModel;
//import com.it.ocs.publication.vo.PublicationVO;
//import com.it.ocs.synchronou.model.EbayAccountModel;
//
//public interface IEBayItemService {
//
//	public OperationResult publishedItem(Long id);
//	
//	public List<EBayPromoteModel> getPromotes(String templateId);
//	
//	public List<EBayPromoteModel> getPromotes(PublicationAddItemModel publicationAddItemModel,String templateId);
//	
//    public OperationResult updateItemType(Long id);
//    
//	public ItemType getItemType(PublicationAddItemModel publicationAddItemModel);
//	
//	public OperationResult verifyAddItem(Long id);
//	
//	public OperationResult verifyLineItem(Long id);
//	
//	public OperationResult timingAddItem(PublicationVO publicationVO);
//
//	public OperationResult verifyAddItem(Map<String, Object> map);
//	
//	public OperationResult endItem(Map<String, Object> map);
//	
//	public OperationResult relistPublicationItem(Long id);
//	
//	public OperationResult synchronouVariations();
//	
//	public OperationResult reviseInventoryStatus(Map<String, Object> map);
//	
//	public Document getItemInfoByIds(EbayAccountModel accountModel,PublicationModel publicationModel);
//	
//	public PublicationModel  parseItemXml(Document doc);
//	
//	public OperationResult getSiteDateById(Long siteId);
//	
//	public OperationResult getLocalBySiteId(String siteDate,Long siteId);
//	
//	public OperationResult updateLineItem(PublicationAddItemModel publicationModel);
//}
