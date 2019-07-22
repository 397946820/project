package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.util.ValidationUtil;

public class ParseDisputeListXMLModel extends ParseXMLModel {

	public ParseDisputeListXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode[] columns = {
						    XMLNode.getInstance("DisputeID"),
						    XMLNode.getInstance("DisputeRecordType"),
						    XMLNode.getInstance("DisputeState"),
						    XMLNode.getInstance("DisputeStatus"),
						    XMLNode.getInstance("OtherPartyRole"),
						    XMLNode.getInstance("OtherPartyName"),
						    XMLNode.getInstance("UserRole"),
						    XMLNode.getInstance("TransactionID"),
						    XMLNode.getInstance("ItemID"),
						    XMLNode.getInstance("DisputeReason"),
						    XMLNode.getInstance("DisputeExplanation"),
						    XMLNode.getInstance("DisputeCreatedTime",XMLNode.EBAY_DATE),
						    XMLNode.getInstance("DisputeModifiedTime",XMLNode.EBAY_DATE),
						    XMLNode.getInstance("OrderLineItemID")
		};
		List<Map<String, Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("DisputeArray_Dispute");
		for (Element element : elements) {
			element = this.formateElement(element);
			Map<String, Object> map = parseRecord(element, columns);
			result.add(map);
		}
		return result;
	}

	public int getPage() {
		String count = this.getValueByName(this.getRoot(), "TotalNumberOfPages");
		if (ValidationUtil.isNullOrEmpty(count)) {
			return 0;
		} else {
			return Integer.parseInt(count);
		}
	}
}
