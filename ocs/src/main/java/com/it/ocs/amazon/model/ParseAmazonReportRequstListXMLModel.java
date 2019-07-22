package com.it.ocs.amazon.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.XMLNode;

public class ParseAmazonReportRequstListXMLModel extends ParseXMLModel {

	public ParseAmazonReportRequstListXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode [] columns = {XMLNode.getInstance("ReportType"),
					      XMLNode.getInstance("ReportProcessingStatus"),
					      XMLNode.getInstance("EndDate",XMLNode.AMAZON_REPORT_DATE),
					      XMLNode.getInstance("Scheduled"),
					      XMLNode.getInstance("ReportRequestId"),
					      XMLNode.getInstance("StartedProcessingDate",XMLNode.AMAZON_REPORT_DATE),
					      XMLNode.getInstance("SubmittedDate",XMLNode.AMAZON_REPORT_DATE),
					      XMLNode.getInstance("CompletedDate",XMLNode.AMAZON_REPORT_DATE),
					      XMLNode.getInstance("StartDate",XMLNode.AMAZON_REPORT_DATE),
					      XMLNode.getInstance("GeneratedReportId")
					      };
		List<Map<String,Object>> list = new ArrayList<>();
		List<Element> elements = this.getElementChild("GetReportRequestListResult_ReportRequestInfo");
		if(elements.size() == 0){
			elements = this.getElementChild("GetReportRequestListByNextTokenResult_ReportRequestInfo");
		}
		for(Element element :elements){
			element = this.formateElement(element);
			Map<String,Object> map = parseRecord(element,columns);
			list.add(map);
		}
		return list;
	}
	
	public String getNextToken() {
		return this.getValueByName(this.getRoot(), "NextToken");
	}
	
	public boolean hasNext() {
		String hasNext = this.getValueByName(this.getRoot(), "HasNext");
		return "true".equalsIgnoreCase(hasNext)?true:false;
	}
}
