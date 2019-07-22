package com.it.ocs.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.sys.service.IEbayGetDataService;
import com.it.ocs.sys.service.IItemDataLinkService;
import com.it.ocs.sys.vo.EasyUITreeVO;

@Controller
@RequestMapping(value = "/itemDataStructureLink")
public class ItemDataStructureLinkController {
	
	@Autowired
	private IItemDataLinkService itemDataLinkService;
	
//	@Autowired
	private IEbayGetDataService  iEbayGetDataService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "admin/sys/itemDataStructureLink";
	}
	
	@RequestMapping(value = "/itemDataTree", method = RequestMethod.GET)
	@ResponseBody
	public List<EasyUITreeVO> itemDataTree(long id){
		return itemDataLinkService.itemDataTree(id);
	}
	
	@RequestMapping(value = "/getItemNodeInfo", method = RequestMethod.GET)
	@ResponseBody
	public EasyUITreeVO getItemNodeInfo(long id){
		return itemDataLinkService.getItemNodeInfo(id);
	}
	
	@RequestMapping(value = "/itemSetSave",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult save(@RequestBody Map<String, Object> map) {
		return itemDataLinkService.save(map);
	}
	
	@RequestMapping(value = "/synchronizeEBayOnlineData",method = RequestMethod.GET)
	@ResponseBody
	public void synchronizeEBayOnlineData() {
		iEbayGetDataService.synchronizeEBayOnlineData();
	}
	
	@RequestMapping(value = "/uopdateEBayOnlineData",method = RequestMethod.GET)
	@ResponseBody
	public void uopdateEBayOnlineData() {
		iEbayGetDataService.uopdateEBayOnlineData();
	}
}
