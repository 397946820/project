package com.it.ocs.customerCenter.service;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.customerCenter.vo.MetadataVO;

public interface IBaseComboboxService {

	public String getValueByField(String field);
	
	public ResponseResult<MetadataVO> selectMappingSkus(RequestParam param);
	
	public OperationResult skuSave(MetadataVO metadataVO);
	
	public OperationResult catagoriesSave(MetadataVO metadataVO);
	
	public OperationResult parentsSave(MetadataVO metadataVO);
	
	public OperationResult skuRemove(Long id);
	
	public OperationResult insertMappingSkus(List<MetadataVO> metadataVOs);
	
	public OperationResult updateMappingSkus(List<MetadataVO> metadataVOs);
	
	
	public List<MetadataVO> selectParents();
	
	public List<MetadataVO> selectCatagories();
	
	public MetadataVO selectMetadatesBySku(String sku);
}
