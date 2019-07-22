package com.it.ocs.seller.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.seller.model.EBayGoodPlaceModel;

@Repository
public interface IEBayGoodPlaceDao extends IBaseDAO<EBayGoodPlaceModel> {

	void deleteGoodPlaceByIds(List<Long> ids);
}
