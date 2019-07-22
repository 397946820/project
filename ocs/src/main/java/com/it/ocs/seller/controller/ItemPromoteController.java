package com.it.ocs.seller.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.vo.PublicationVO;
import com.it.ocs.seller.model.ComboTreeModel;
import com.it.ocs.seller.service.IEBayItemPromoteService;
import com.it.ocs.seller.vo.ItemPromoteVO;

@Controller
@RequestMapping(value="/ItemPromote")
public class ItemPromoteController {
	
	@Autowired
	private IEBayItemPromoteService itemPromoteService ;
	
	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/moduleManagement/itemPromote";
	}
	
	@RequestMapping(value="/findItemPromoteList")
	@ResponseBody 
	public ResponseResult<ItemPromoteVO> findItemPromoteList(RequestParam param){
		return itemPromoteService.findItemPromoteList(param);
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public OperationResult addOrEdit(@RequestBody Map<String, Object> map) {
		return itemPromoteService.addOrEdit(map);
	}
	
	@RequestMapping(value="/searchProduct")
	@ResponseBody 
	public ResponseResult<PublicationVO> searchProduct(RequestParam param){
		return itemPromoteService.searchProduct(param);
	}
	
	@RequestMapping(value="/getTemplateItems/{temId}",method = RequestMethod.GET)
	@ResponseBody 
	public List<PublicationVO> getTemplateItems(@PathVariable String temId){
		return itemPromoteService.getTemplateItems(temId);
	}
	
	@RequestMapping(value="/saveItems",method = RequestMethod.POST)
	@ResponseBody 
	public void saveItems(@RequestBody Map<String, Object> map){
		itemPromoteService.saveItems(map);
	}
	@RequestMapping(value="/deleteItemPromoteByIds")
	@ResponseBody
	public OperationResult deleteItemPromoteByIds(String ids){
		return itemPromoteService.deleteItemPromoteByIds(ids);
	}
	
	@RequestMapping(value="/getItemsByRule",method = RequestMethod.POST)
	@ResponseBody 
	public List<PublicationVO> getItemsByRule(@RequestBody Map<String, Object> map){
		return itemPromoteService.getItemsByRule(map);
	}
	
	
	@RequestMapping(value="/getItemType/{siteId}",method = RequestMethod.POST)
	@ResponseBody 
	public List<ComboTreeModel> getItemType(@PathVariable("siteId")String siteId){
		return itemPromoteService.getItemType(siteId);
	}
	@RequestMapping(value="/getItemStoreType/{siteId}",method = RequestMethod.POST)
	@ResponseBody 
	public List<ComboTreeModel> getItemStoreType(@PathVariable("siteId")String siteId){
		return itemPromoteService.getItemStoreType(siteId);
	}
}
