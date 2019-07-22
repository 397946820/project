package com.it.ocs.synchronou.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.util.ValidationUtil;

public class ParseMemberMessageListXMLModel extends ParseXMLModel{

	public ParseMemberMessageListXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode [] columns = {XMLNode.getInstance("ItemID"),
							  XMLNode.getInstance("StartTime",XMLNode.EBAY_DATE),
							  XMLNode.getInstance("EndTime",XMLNode.EBAY_DATE),
							  XMLNode.getInstance("ViewItemURL"),
							  XMLNode.getInstance("UserID"),
							  XMLNode.getInstance("CurrentPrice",XMLNode.EBAY_PRICE,"currencyID"),
							  XMLNode.getInstance("Title"),
							  XMLNode.getInstance("MessageType"),
							  XMLNode.getInstance("QuestionType"),
							  XMLNode.getInstance("DisplayToPublic",XMLNode.EBAY_BOOLEAN),
							  XMLNode.getInstance("SenderID"),
							  XMLNode.getInstance("SenderEmail"),
							  XMLNode.getInstance("RecipientID"),
							  XMLNode.getInstance("Subject"),
							  XMLNode.getInstance("Body"),
							  XMLNode.getInstance("MessageID"),
							  XMLNode.getInstance("MessageStatus"),
							  XMLNode.getInstance("CreationDate",XMLNode.EBAY_DATE),
							  XMLNode.getInstance("LastModifiedDate",XMLNode.EBAY_DATE),
							  XMLNode.getInstance("MessageMedia")
				 
    			 			 };
		List<Map<String,Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("MemberMessage_MemberMessageExchange");
		for(Element element :elements){
			element = this.formateElement(element);
			Map<String,Object> map = parseRecord(element,columns);
			result.add(map);
		}
		return result;
	}

	public int getPage() {
		String count = this.getValueByName(this.getRoot(), "TotalNumberOfPages");
		if(ValidationUtil.isNullOrEmpty(count)){
			return 0;
		}else{
			return Integer.parseInt(count);
		}
	}
	
}
