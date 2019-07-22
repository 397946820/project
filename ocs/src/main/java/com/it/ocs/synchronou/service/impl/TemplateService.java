package com.it.ocs.synchronou.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.synchronou.dao.ITemplateDao;
import com.it.ocs.synchronou.model.TemplateModel;
import com.it.ocs.synchronou.service.ITemplateService;
import com.it.ocs.synchronou.util.ValidationUtil;

@Service
public class TemplateService implements ITemplateService{
	@Autowired
	private ITemplateDao templateDao;
	
	
	
	public static String formatTemplat(Map<String,String> map,String templateConent){
		for(Map.Entry<String, String> entry:map.entrySet()){
			if (!ValidationUtil.isNull(entry.getValue())) {
				templateConent = templateConent.replace("${"+entry.getKey()+"}", entry.getValue());
				
			}else{
				templateConent = templateConent.replace("${"+entry.getKey()+"}", "");
			}
			
		}
		return templateConent;
	}


	@Override
	public TemplateModel getTemplateContent(String templateName, String templateType) {
		Map<String,String> map = new HashMap<>();
		map.put("name", templateName);
		map.put("type", templateType);
		return templateDao.getTemplateContent(map);
	}
}
