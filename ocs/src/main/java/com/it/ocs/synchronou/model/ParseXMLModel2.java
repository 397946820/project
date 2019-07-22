package com.it.ocs.synchronou.model;

import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

public abstract class ParseXMLModel2 extends ParseXMLModel {
	
	public ParseXMLModel2(String xml, String nameSpace) throws DocumentException {
		super(DocumentHelper.parseText(xml), nameSpace);
	}
	
	@Override
	public abstract List<Map<String, Object>> getResult();
	
	public String getNextToken() {
		return this.getValueByName(this.getRoot(), "NextToken");
	}
	
	public boolean hasNext() {
		String hasNext = this.getValueByName(this.getRoot(), "HasNext");
		return "true".equalsIgnoreCase(hasNext);
	}
}
