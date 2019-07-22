package com.it.ocs.synchronou.dao;

import java.util.Map;

import com.it.ocs.synchronou.model.TemplateModel;

public interface ITemplateDao {

	public TemplateModel getTemplateContent(Map<String, String> map);

}
