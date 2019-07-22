package com.it.ocs.synchronou.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseUserCaseEBPXMLModel extends ParseXMLModel {

	public ParseUserCaseEBPXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		List<Map<String, Object>> result = new ArrayList<>();
		XMLNode[] columns = {
				XMLNode.getInstance("agreedRefundAmount",XMLNode.EBAY_PRICE,"currencyId"),
				XMLNode.getInstance("buyerReturnShipment_carrierUsed"),
				XMLNode.getInstance("buyerReturnShipment_deliveryDate",XMLNode.EBAY_DATE),
				XMLNode.getInstance("buyerReturnShipment_deliveryStatus"),
				XMLNode.getInstance("buyerReturnShipment_shippingAddress"),
				XMLNode.getInstance("buyerReturnShipment_shippingCost",XMLNode.EBAY_PRICE,"currencyId"),
				XMLNode.getInstance("buyerReturnShipment_trackingNumber"),
				XMLNode.getInstance("decision"),
				XMLNode.getInstance("decisionDate",XMLNode.EBAY_DATE),
				XMLNode.getInstance("decisionReason"),
				XMLNode.getInstance("decisionReasonDetail_code"),
				XMLNode.getInstance("decisionReasonDetail_content"),
				XMLNode.getInstance("decisionReasonDetail_description"),
				XMLNode.getInstance("detailStatus"),
				XMLNode.getInstance("detailStatusInfo_code"),
				XMLNode.getInstance("detailStatusInfo_content"),
				XMLNode.getInstance("detailStatusInfo_description"),
				XMLNode.getInstance("FVFCredited"),
				XMLNode.getInstance("globalId"),
				XMLNode.getInstance("initialBuyerExpectation"),
				XMLNode.getInstance("initialBuyerExpectationDetail_code"),
				XMLNode.getInstance("initialBuyerExpectationDetail_content"),
				XMLNode.getInstance("initialBuyerExpectationDetail_description"),
				XMLNode.getInstance("notCountedInBuyerProtectionCases"),
				XMLNode.getInstance("openReason"),
				XMLNode.getInstance("paymentDetail_balanceDue"),
				XMLNode.getInstance("returnMerchandiseAuthorization"),
				XMLNode.getInstance("sellerShipment_carrierUsed"),
				XMLNode.getInstance("sellerShipment_deliveryDate",XMLNode.EBAY_DATE),
				XMLNode.getInstance("sellerShipment_deliveryStatus"),
				XMLNode.getInstance("sellerShipment_shippingAddress"),  
				XMLNode.getInstance("sellerShipment_shippingCost",XMLNode.EBAY_PRICE,"currencyId"),
				XMLNode.getInstance("sellerShipment_trackingNumber")
		};
		Element caseDetail = this.getRoot().element("caseDetail");
		caseDetail = this.formateElement(caseDetail);
		Map<String, Object> map = parseRecord(caseDetail, columns);
		//caseSummary
		Map<String, Object> summaryMap = getSummary();
		String caseId = (String)summaryMap.get("caseId_id");
		map.put("case_id", caseId);
		String account = (String)summaryMap.get("user_userId");
		map.put("user_userId", account);
		map.put("caseSummary", summaryMap);
		//appeal
		map.put("appeal", getAppeal(caseDetail,caseId));
		//caseDocumentInfo
		map.put("caseDocumentInfo", getCaseDocumentInfo(caseDetail,caseId));
		//moneyMovement
		map.put("moneyMovement", getMoneyMovement(caseDetail,caseId));
		//responseHistory
		map.put("responseHistory", getResponseHistory(caseDetail,caseId));
		result.add(map);
		return result;
	}

	private List<Map<String, Object>> getResponseHistory(Element caseDetail, String caseId) {
		XMLNode[] columns = {
				//case_id
				XMLNode.getInstance("activity"),
				XMLNode.getInstance("activityDetail_code"),
				XMLNode.getInstance("activityDetail_content"),
				XMLNode.getInstance("activityDetail_description"),
				XMLNode.getInstance("attributes_appealRef"),
				XMLNode.getInstance("attributes_moneyMovementRef"),
				XMLNode.getInstance("attributes_onholdReason"),
				XMLNode.getInstance("attributes_onholdReasonDetail_code"),
				XMLNode.getInstance("attributes_onholdReasonDetail_content"),
				XMLNode.getInstance("attributes_onholdReasonDetail_description"),
				XMLNode.getInstance("author_role"),
				XMLNode.getInstance("author_userId"),
				XMLNode.getInstance("creationDate",XMLNode.EBAY_DATE),
				XMLNode.getInstance("note")

		};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChildByElement(caseDetail, "responseHistory");
		for(Element element : elements){
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			map.put("case_id", caseId);
			result.add(map);
		}
		return result;
	}

	private List<Map<String, Object>> getMoneyMovement(Element caseDetail, String caseId) {
		XMLNode[] columns = {
				//case_id
				//id
				//parentId
				XMLNode.getInstance("amount",XMLNode.EBAY_PRICE,"currencyId"),
				XMLNode.getInstance("fromParty_role"),
				XMLNode.getInstance("fromParty_userId"),
				XMLNode.getInstance("paymentMethod"),
				XMLNode.getInstance("paypalTransactionId"),
				XMLNode.getInstance("status"),
				XMLNode.getInstance("toParty_role"),
				XMLNode.getInstance("toParty_userId"),
				XMLNode.getInstance("transactionDate",XMLNode.EBAY_DATE),
				XMLNode.getInstance("type")
		};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChildByElement(caseDetail, "moneyMovement");
		for(Element element : elements){
			String id = element.attributeValue("id");
			String parentId = element.attributeValue("parentId");
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			map.put("case_id", caseId);
			map.put("id", id);
			map.put("parentId", parentId);
			result.add(map);
		}
		return result;
	}

	private List<Map<String, Object>> getCaseDocumentInfo(Element caseDetail, String caseId) {
		XMLNode[] columns = {
				//case_id
				XMLNode.getInstance("name"),
				XMLNode.getInstance("type"),
				XMLNode.getInstance("uploadDate",XMLNode.EBAY_DATE)
		};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChildByElement(caseDetail, "caseDocumentInfo");
		for(Element element : elements){
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			map.put("case_id", caseId);
			result.add(map);
		}
		return result;
	}

	private List<Map<String, Object>> getAppeal(Element caseDetail, String caseId) {
		XMLNode[] columns = {
				//case_id
				//id
				XMLNode.getInstance("decision"),
				XMLNode.getInstance("decisionReasonDetail_code"),
				XMLNode.getInstance("decisionReasonDetail_content"),
				XMLNode.getInstance("decisionReasonDetail_description")
		};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChildByElement(caseDetail, "appeal");
		for(Element element : elements){
			String id = element.attributeValue("id");
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			map.put("case_id", caseId);
			map.put("id", id);
			result.add(map);
		}
		return result;
	}

	private Map<String, Object> getSummary() {
		Element caseSummary = this.getRoot().element("caseSummary");
		caseSummary = this.formateElement(caseSummary);
		XMLNode[] columns = {
				XMLNode.getInstance("caseId_id"),
				XMLNode.getInstance("caseId_type"),
				XMLNode.getInstance("user_userId"),
				XMLNode.getInstance("otherParty_userId"),
				XMLNode.getInstance("status"),
				XMLNode.getInstance("item_globalId"),
				XMLNode.getInstance("item_itemId"),
				XMLNode.getInstance("item_itemTitle"),
				XMLNode.getInstance("item_transactionId"),
				XMLNode.getInstance("item_transactionDate",XMLNode.EBAY_DATE),
				XMLNode.getInstance("item_transactionPrice",XMLNode.EBAY_PRICE,"currencyId"),
				XMLNode.getInstance("caseQuantity"),
				XMLNode.getInstance("caseAmount",XMLNode.EBAY_PRICE,"currencyId"),
				XMLNode.getInstance("respondByDate",XMLNode.EBAY_DATE),
				XMLNode.getInstance("creationDate",XMLNode.EBAY_DATE),
				XMLNode.getInstance("lastModifiedDate",XMLNode.EBAY_DATE)
		};
		return parseRecord(caseSummary, columns);
	}
	
}
