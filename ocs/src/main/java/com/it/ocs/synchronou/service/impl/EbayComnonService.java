package com.it.ocs.synchronou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.synchronou.model.TemplateModel;

@Service
public class EbayComnonService {
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private EbayAccountService ebayAccountService;
	
	@Autowired
	private BaseHttpsService baseHttpService;
	
	public TemplateModel getTemplate(String name,String type){
		return templateService.getTemplateContent(name, type);
	}
}
