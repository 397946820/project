package com.it.ocs.synchronou.model;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import com.it.ocs.synchronou.util.ValidationUtil;

public class ParseMessageSummaryXMLModel extends ParseXMLModel{

	public ParseMessageSummaryXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	protected List<Map<String, Object>> getResult() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getTotalPages() {
		String count = this.getValueByName(this.getRoot(), "Summary_TotalMessageCount");
		if(null != count && !"".equals(count)){
			int total = Integer.parseInt(count);
			int totalPages = total/100;
			if(total%100 != 0){
				totalPages++;
			}
			return totalPages;
		}
		return 0;
	}
}
