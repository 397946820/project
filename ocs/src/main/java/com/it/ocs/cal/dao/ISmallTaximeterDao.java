package com.it.ocs.cal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.SmallTaximeterModel;

@Repository
public interface ISmallTaximeterDao extends IBaseDao<SmallTaximeterModel> {

	SmallTaximeterModel calculation(SmallTaximeterModel model);

	void refreshAll();

	void refresh(String sku);
}
