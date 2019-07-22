package com.it.ocs.noArriveRegion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.noArriveRegion.service.INoArriveRegionService;
import com.it.ocs.noArriveRegion.vo.NoArriveRegionVo;

@Controller
@RequestMapping("/noArriveRegion")
public class NoArriveRegionController {

	@Autowired
	private INoArriveRegionService noArriveRegionService;
	
	@RequestMapping("/tolist")
	public String skipList() {
		return "admin/noArriveRegion/list";
	}
	
	@RequestMapping("/findAll")
	public @ResponseBody ResponseResult<NoArriveRegionVo> findAll(RequestParam param) {
		return noArriveRegionService.findAll(param);
	}
	
	@RequestMapping("/saveEdit")
	public @ResponseBody OperationResult saveEdit(NoArriveRegionVo region) {
		return noArriveRegionService.saveEdit(region);
	}
	
	@RequestMapping("/remove")
	public @ResponseBody OperationResult removeNoArriveRegion(String ids) {
		return noArriveRegionService.removeNoArriveRegion(ids);
	}
}
