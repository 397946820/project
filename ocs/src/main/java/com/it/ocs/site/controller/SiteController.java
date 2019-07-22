package com.it.ocs.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.site.service.ISiteService;
import com.it.ocs.site.vo.SiteVO;

@Controller
@RequestMapping(value = "/site")
public class SiteController {
	@Autowired
	private ISiteService siteQueryService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<SiteVO> list(RequestParam param) {
		ResponseResult<SiteVO> sitesPageResult = siteQueryService.querySite(param);
        return sitesPageResult;
    }
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult saveSite(SiteVO param) {
		return siteQueryService.saveSite(param);
	}
	@RequestMapping(value = "/remove",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult removeSite(Long id) {
		return siteQueryService.removeSite(id);
	}
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index() {
		return "admin/site/list";
	}
	
}
