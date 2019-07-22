package com.it.ocs.synchronou.service;

import java.util.List;
import java.util.Map;

public interface IBaseHttpBody {

	public String startToUpperCase(String string);
	
	public Map<String,Object> VoToMap(Object vo);
	
	public String parameterToXML(Map<String,Object> map,String root);
	
	public String  resultXML(List<String> list);
	
	public String combination(String root,List<String> list);
}
