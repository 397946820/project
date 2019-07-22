package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class ParseMessageListXMLModel extends ParseXMLModel{

	public ParseMessageListXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode [] columns = {XMLNode.getInstance("Sender"),
							  XMLNode.getInstance("RecipientUserID"),
							  XMLNode.getInstance("SendToName"),
							  XMLNode.getInstance("Subject"),
							  XMLNode.getInstance("MessageID"),
							  XMLNode.getInstance("ExternalMessageID"),
							  XMLNode.getInstance("Flagged",XMLNode.EBAY_BOOLEAN),
							  XMLNode.getInstance("Read",XMLNode.EBAY_BOOLEAN),
							  XMLNode.getInstance("ReceiveDate", XMLNode.EBAY_DATE),
							  XMLNode.getInstance("ExpirationDate", XMLNode.EBAY_DATE),
							  XMLNode.getInstance("ItemID"),
							  XMLNode.getInstance("ResponseEnabled",XMLNode.EBAY_BOOLEAN),
							  XMLNode.getInstance("ResponseURL"),
							  XMLNode.getInstance("FolderID"),
							  XMLNode.getInstance("MessageType"),
							  XMLNode.getInstance("Replied",XMLNode.EBAY_BOOLEAN),
							  XMLNode.getInstance("ItemEndTime",XMLNode.EBAY_DATE),
							  XMLNode.getInstance("ItemTitle"),
							  XMLNode.getInstance("UserResponseDate", XMLNode.EBAY_DATE),
							  XMLNode.getInstance("QuestionType"),
							  XMLNode.getInstance("Text"),
							  XMLNode.getInstance("Content")
			      			 };
		List<Map<String,Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("Messages_Message");
		for(Element element :elements){
			element = this.formateElement(element);
			Map<String,Object> map = parseRecord(element,columns);
			result.add(map);
		}
		return result;
	}

}
