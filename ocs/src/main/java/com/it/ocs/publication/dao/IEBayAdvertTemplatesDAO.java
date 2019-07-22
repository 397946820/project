package com.it.ocs.publication.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.publication.model.EBayAdvertTemplatesModel;
import com.it.ocs.publication.vo.AdvertTemplatesVO;

public interface IEBayAdvertTemplatesDAO extends IBaseDAO<EBayAdvertTemplatesModel> {
   
	public void insertAdvert(AdvertTemplatesVO advertTemplatesVO);
	
	public void updateAdvert(AdvertTemplatesVO advertTemplatesVO);
	
	public List<EBayAdvertTemplatesModel> selectAdvertTemplates(@Param("advertModel")Map<String, Object> map,@Param("starRow") int startRow,  @Param("endRow")int endRow);

	public Integer getTotal();
	
	public void updateById(@Param("id")Long id);
	
	public EBayAdvertTemplatesModel selectAdvertById(@Param("id")Long id);

	public List<EBayAdvertTemplatesModel> selectAdvertList();
}
