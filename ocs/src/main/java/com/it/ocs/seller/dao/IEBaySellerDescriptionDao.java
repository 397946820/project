package com.it.ocs.seller.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.seller.model.EBaySellerDescriptionModel;
@Repository
public interface IEBaySellerDescriptionDao extends IBaseDAO<EBaySellerDescriptionModel>{

	void deleteSellerDescriptionByIds(List<Long> ids);

	public int addSellerDescription(EBaySellerDescriptionModel sdModel);

	public int updateSellerDescription(EBaySellerDescriptionModel sdModel);
}
