package com.it.ocs.synchronou.service;

import com.it.ocs.synchronou.model.TemplateModel;

public interface ITemplateService {
	public TemplateModel getTemplateContent(String templateName,String templateType);
}
