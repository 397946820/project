package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.util.ValidationUtil;

public class ParseUserCaseListXMLModel extends ParseXMLModel{

	public ParseUserCaseListXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
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
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("cases_caseSummary");
		for (Element element : elements) {
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			result.add(map);
		}
		return result;
	}
	
	public int getPage() {
		String count = this.getValueByName(this.getRoot(), "totalPages");
		if (ValidationUtil.isNullOrEmpty(count)) {
			return 0;
		} else {
			return Integer.parseInt(count);
		}
	}
}
