package com.it.ocs.sys.service.support;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;

/**
 * ebay数据映射特殊情况处理模板
 * @author chenyong
 *
 */
public class EbayHttpSupport {
	private static Map<String,Integer> map = null;
	static{
		map = new HashMap<String,Integer>();
		map.put("PictureDetails", 1);
	}
	public static String get(String type,Element element){
		Integer flag = map.get(type);
		if(flag == null){
			flag = 0;
		}
		return find(flag, element);
	}

	private static String find(int type,Element element) {
		switch (type) {
		case 1:
			return pictureDetails(element);

		default:
			return element.getText();
		}
	}
	
	private static String pictureDetails(Element element) {
		// TODO 根据实际情况更改
		return element.asXML();
	}
}
