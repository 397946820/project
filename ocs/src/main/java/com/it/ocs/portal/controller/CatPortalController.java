package com.it.ocs.portal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.it.ocs.common.RequestParam;

@Controller
@RequestMapping("/portal")
public class CatPortalController {
	@RequestMapping("/cat_portal")
	public String findAll(RequestParam param) {
		return "admin/portal/sku_cat_portal";
	}
	/**
	 * 
	 * @return
	 */
	public List<Map<String,Object>> constractLinearDatas(RequestParam param) {
		
		return null;
	}
}
