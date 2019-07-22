package com.it.ocs.publication.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.it.ocs.synchronou.model.ParseXMLModel;
import com.it.ocs.synchronou.model.XMLNode;

public class ParseImageUploadResultXMLModel extends ParseXMLModel{

	public ParseImageUploadResultXMLModel(Document document, String nameSpace) {
		super(document, nameSpace);
	}

	@Override
	public List<Map<String, Object>> getResult() {
		XMLNode [] columns = {XMLNode.getInstance("MemberURL"),
							  XMLNode.getInstance("PictureHeight"),
							  XMLNode.getInstance("PictureWidth")};
		List<Map<String,Object>> result = new ArrayList<>();
		List<Element> elements = this.getElementChild("SiteHostedPictureDetails_PictureSetMember");
		for(Element element :elements){
			element = this.formateElement(element);
			Map<String,Object> map = parseRecord(element,columns);
			result.add(map);
		}
		return result;
	}
	
	public Map<String,String> getEbayUrlInfo(){
		Map<String,String> returnMap = new HashMap<>();
		List<Map<String, Object>> list = getResult();
		int width = 0;
		int height = 0;
		String url = "";
		//获取最大的图片
		for(Map<String, Object> map : list){
			int he = Integer.parseInt(map.get("PictureHeight").toString());
			int wi = Integer.parseInt(map.get("PictureWidth").toString());
			if((he+wi) > (width+height)){
				url = map.get("MemberURL").toString();
				//赋值
				width = wi;
				height = he;
			}
		}
		returnMap.put("url", url);
		returnMap.put("xml", this.getRoot().asXML());
		return returnMap;
	}

}
