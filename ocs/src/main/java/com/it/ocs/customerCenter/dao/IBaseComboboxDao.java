package com.it.ocs.customerCenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.customerCenter.model.BaseComboboxModel;
import com.it.ocs.customerCenter.model.MetadataModel;

@Repository
public interface IBaseComboboxDao extends IBaseDAO<BaseComboboxModel> {

	public BaseComboboxModel getValueByField(@Param("field")String field);
	
	public List<MetadataModel> selectMappingSkus(@Param("skuModel")Map<String, Object> map,@Param("startRow")int startRow,@Param("endRow") int endRow);

	public List<MetadataModel> selectMappingCatagories(@Param("catagorieModel")Map<String, Object> map,@Param("startRow")int startRow,@Param("endRow") int endRow);

	public List<MetadataModel> selectMappingParents(@Param("parentModel")Map<String, Object> map,@Param("startRow")int startRow,@Param("endRow") int endRow);

	public Integer getSkuTotal(@Param("skuModel")Map<String, Object> map); 
	
	public void insertMappingSkus(List<MetadataModel> metadataModels);
	
	public void updateMappingSkus(List<MetadataModel> metadataModels);
	
    public void insertMappingCatagories(List<MetadataModel> metadataModels);
	
	public void updateMappingCatagories(List<MetadataModel> metadataModels);

	public void insertMappingParents(List<MetadataModel> metadataModels);
	
	public void updateMappingParents(List<MetadataModel> metadataModels);
	
	public Integer getCatagoriesTotal(@Param("catagorieModel")Map<String, Object> map);

	public Integer getParentTotal(@Param("parentModel")Map<String, Object> map);
	
	public void skuRemoveById(Long id);
	
	public void catagoriesRemoveById(Long id);
	
	public void parentRemoveById(Long id);
	
	public List<MetadataModel> selectParents();
	
	public List<MetadataModel> selectCatagories();
	
	public MetadataModel selectMetadatesBySku(String sku);
}
