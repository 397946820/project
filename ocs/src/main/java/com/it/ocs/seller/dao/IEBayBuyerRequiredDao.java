package com.it.ocs.seller.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.seller.model.EBayBuyerRequiredModel;
@Repository
public interface IEBayBuyerRequiredDao extends IBaseDAO<EBayBuyerRequiredModel>{
	
	void deleteBuyerRequireById(List<Long> ids);
	
	

}
