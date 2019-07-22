package com.it.ocs.customerCenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.model.BaseComboboxModel;
import com.it.ocs.customerCenter.service.IBaseComboboxService;
import com.it.ocs.customerCenter.vo.MetadataVO;

@Controller
@RequestMapping(value="/BaseCombobox")
public class BaseComboboxController {

	@Autowired
	private IBaseComboboxService baseComboboxService;
	@RequestMapping(value="/getValueByField")
	@ResponseBody
	public String getValueByField(String field){
		return baseComboboxService.getValueByField(field);
	}
	
	@RequestMapping(value="/show")
	public String show(){
		return "/admin/customerCenter/Metadata";
	}
	
	@RequestMapping(value="/skuList")
	@ResponseBody
	public ResponseResult<MetadataVO> skuList(RequestParam param){
		return baseComboboxService.selectMappingSkus(param);
	}
	@RequestMapping(value="/skuSave")
	@ResponseBody
	public OperationResult skuSave(@RequestBody MetadataVO metadataVO){
		return baseComboboxService.skuSave(metadataVO);
	}
	@RequestMapping(value="/catagoriesSave")
	@ResponseBody
	public OperationResult catagoriesSave(@RequestBody MetadataVO metadataVO){
		return baseComboboxService.catagoriesSave(metadataVO);
	}
	@RequestMapping(value="/parentSave")
	@ResponseBody
	public OperationResult parentsSave(@RequestBody MetadataVO metadataVO){
		return baseComboboxService.parentsSave(metadataVO);
	}
	@RequestMapping(value="skuRemove")
	@ResponseBody
	public OperationResult skuRemove(Long id){
		
		return baseComboboxService.skuRemove(id);
	}
	/*@RequestMapping(value="catagoriesRemove")
	@ResponseBody
	public OperationResult catagoriesRemove(Long id){
		
		return baseComboboxService.catagoriesRemove(id);
	}
	@RequestMapping(value="parentsRemove")
	@ResponseBody
	public OperationResult parentsRemove(Long id){
		
		return baseComboboxService.parentsRemove(id);
	}
	@RequestMapping(value="/catagoriesList")
	@ResponseBody
	public ResponseResult<MetadataVO> catagoriesList(RequestParam param){
		return baseComboboxService.selectMappingCatagories(param);
	}
	
	@RequestMapping(value="/parentsList")
	@ResponseBody
	public ResponseResult<MetadataVO> parentsList(RequestParam param){
		return baseComboboxService.selectMappingParents(param);
	}*/
	@RequestMapping(value="/selectParents")
	@ResponseBody
	public List<MetadataVO> selectParents(){
		return baseComboboxService.selectParents();
	}
	
	@RequestMapping(value="/selectCatagories")
	@ResponseBody
	public List<MetadataVO> selectCatagories(){
		return baseComboboxService.selectCatagories();
	}
	
	@RequestMapping(value="/selectMetadatesBySku")
	@ResponseBody
	public MetadataVO selectMetadatesBySku(String sku){
		return baseComboboxService.selectMetadatesBySku(sku);
	}
}
